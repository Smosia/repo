/*
 * Copyright (c) 2015 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

#include "tlkm_authentication.h"
#include "tlkm_hw_auth_token.h"
#include "keymaster_ta_defs.h"
#include "keymaster_util.h"
#include "km_shared_util.h"
#include "tlkm_device.h"
#include "tlkm_hmac.h"
#include "tlCommon.h"

/**
 * Populate an authentication-token structure.
 *
 * @param[out] auth_token structure to populate
 * @param auth_token_blob blob containing token data
 *
 * @retval false parsing error
 */
static bool parse_auth_token(
    hw_auth_token_t *auth_token,
    const keymaster_blob_t *auth_token_blob)
{
    assert(auth_token != NULL);
    assert(auth_token_blob != NULL);

    if (auth_token_blob->data_length != 69) { // size of packed hw_auth_token_t
        return false;
    }
    if (auth_token_blob->data == NULL) {
        return false;
    }

    auth_token->version = *(auth_token_blob->data);
    auth_token->challenge = get_u64(auth_token_blob->data + 1);
    auth_token->user_id = get_u64(auth_token_blob->data + 9);
    auth_token->authenticator_id = get_u64(auth_token_blob->data + 17);
    auth_token->authenticator_type = get_u32(auth_token_blob->data + 25);
    auth_token->timestamp = get_u64(auth_token_blob->data + 29);
    memcpy(auth_token->hmac, auth_token_blob->data + 37, 32);

    return true;
}

static uint8_t hmac_key[32] = {0};
static bool hmac_key_derived = false;

/**
 * On first call, derive the key used to authenticate tokens.
 *
 * The key \p hmac_key is derived once only in the lifetime of the TA. The
 * boolean \p hmac_key_derived is statically initialized to false; if it is
 * false on entry to this function, the key is derived and the flag is set to
 * true; otherwise the function does nothing.
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t get_hmac_key()
{
    if (!hmac_key_derived) {
        const uint8_t salt[] = {'k','e','y','m','a','s','t','e','r','1', 0, 0};

        if (TLAPI_OK != tlApiDeriveKey(
            salt, sizeof(salt),
            hmac_key, 32,
            MC_SO_CONTEXT_SP, /* MC_SPID_SYSTEM */
            MC_SO_LIFETIME_POWERCYCLE))
        {
            memset(hmac_key, 0, 32);
            return KM_ERROR_UNKNOWN_ERROR;
        }

        hmac_key_derived = true;
    }

    return KM_ERROR_OK;
}

/**
 * Verify the HMAC on an authentication-token structure.
 *
 * This function retrieves the per-boot HMAC key from the secure driver, uses it
 * to compute an HMAC-SHA256 over all but the last (HMAC) field of the
 * authentication token, and compares the result with the contents of that
 * field.
 *
 * @param auth_token authentication token
 *
 * @retval false HMAC did not verify
 */
static bool verify_auth_token(
    hw_auth_token_t *auth_token)
{
    uint8_t data[37]; // size of packed hw_auth_token_t minus the HMAC
    uint8_t *pos;
    hmacOpCtx_t hmacOpCtx;
    uint32_t tag_len, input_consumed;

    assert(auth_token != NULL);

    /* Fill data buffer */
    data[0] = auth_token->version;
    pos = data + 1;
    set_u64_increment_pos(&pos, auth_token->challenge);
    set_u64_increment_pos(&pos, auth_token->user_id);
    set_u64_increment_pos(&pos, auth_token->authenticator_id);
    set_u32_increment_pos(&pos, auth_token->authenticator_type);
    set_u64_increment_pos(&pos, auth_token->timestamp);

    /* Verify the HMAC */
    return (KM_ERROR_OK == get_hmac_key())
        && (KM_ERROR_OK == hmac_begin(KM_PURPOSE_VERIFY, NULL, 0, 256,
            hmac_key, 32, KM_DIGEST_SHA_2_256, 256, &tag_len, &hmacOpCtx))
        && (tag_len == 32)
        && (KM_ERROR_OK == hmac_update(data, sizeof(data), &input_consumed,
            &hmacOpCtx))
        && (input_consumed == sizeof(data))
        && (KM_ERROR_OK == hmac_finish_verify(auth_token->hmac, 32, 32, 32,
            &hmacOpCtx));
}

static bool is_pubkey_op(
    session_handle_t *session)
{
    assert(session != NULL);
    switch (session->algorithm) {
        case KM_ALGORITHM_RSA:
            return ((session->purpose == KM_PURPOSE_VERIFY) ||
                    (session->purpose == KM_PURPOSE_ENCRYPT));
        case KM_ALGORITHM_EC:
            return (session->purpose == KM_PURPOSE_VERIFY);
        default:
            return false;
    }
}

bool is_auth_required(
    const uint8_t *key_params,
    uint32_t key_params_len)
{
    return !get_boolean_tag_data_with_default(
        key_params, key_params_len, KM_TAG_NO_AUTH_REQUIRED, false);
}

/**
 * Retrieve an auth token from the parameters and verify the HMAC and version.
 *
 * @param[out] auth_token authenticatoin token
 * @param params serialized operation parameters
 * @param params_len length of \p params
 *
 * @retval true auth token OK
 * @retval false auth token not present or invalid
 */
static bool get_auth_token(
    hw_auth_token_t *auth_token,
    const uint8_t *params,
    uint32_t params_len)
{
    keymaster_blob_t auth_token_blob;

    return
        (KM_ERROR_OK == get_blob_tag_data(params, params_len,
            KM_TAG_AUTH_TOKEN, &auth_token_blob))
        && parse_auth_token(auth_token, &auth_token_blob)
        && verify_auth_token(auth_token)
        && (auth_token->version == 0); // HW_AUTH_TOKEN_VERSION
}

static bool check_data_matches(
    keymaster_tag_t tag,
    const uint8_t *key_params,
    uint32_t key_params_len,
    const uint8_t *op_params,
    uint32_t op_params_len)
{
    keymaster_blob_t required_value = {NULL, 0};
    keymaster_blob_t supplied_value = {NULL, 0};

    keymaster_error_t ret = get_blob_tag_data(key_params, key_params_len, tag, &required_value);
    if (ret == KM_ERROR_OK) {
        return
            (KM_ERROR_OK == get_blob_tag_data(op_params, op_params_len, tag, &supplied_value)) &&
            (required_value.data_length == supplied_value.data_length) &&
            (memcmp(required_value.data, supplied_value.data, required_value.data_length) == 0);
    } else if (ret == KM_ERROR_INVALID_TAG) { // not present in key_params
        return true;
    } else { // not unique, or other error
        return false;
    }
}

bool authorize_op_begin(
    session_handle_t *session,
    const uint8_t *key_params,
    uint32_t key_params_len,
    const uint8_t *op_params,
    uint32_t op_params_len)
{
    if ((session == NULL) || (key_params == NULL) || (key_params_len < 4)) {
        return false;
    }

    /* If either KM_TAG_APPLICATION_ID or KM_TAG_APPLICATION_DATA is present in
     * key_params, then it must be present in op_params and match.
     */
    if (!check_data_matches(KM_TAG_APPLICATION_ID,
            key_params, key_params_len, op_params, op_params_len) ||
        !check_data_matches(KM_TAG_APPLICATION_DATA,
            key_params, key_params_len, op_params, op_params_len))
    {
        return false;
    }

    if (!is_pubkey_op(session) && is_auth_required(key_params, key_params_len)) {
        session->no_auth_required = false;

        hw_auth_token_t auth_token;
        if (!get_auth_token(&auth_token, op_params, op_params_len)) {
            return false;
        }

        /* Check that at least one of the KM_TAG_USER_SECURE_ID values in the
         * key is found in the user_id or authenticator_id fields of the
         * authentication token. Meanwhile record the values in the session
         * data.
         */
        session->n_user_secure_ids = 0;
        uint32_t n_key_params = get_u32(key_params);
        size_t offset = 4;
        bool user_id_match = false;
        for (uint32_t i = 0; i < n_key_params; i++) {
            if (key_params_len < offset + 4) {
                return false;
            }
            keymaster_tag_t tag = KM_TAG_INVALID;
            uint32_t param_len = param_length(
                key_params, key_params_len, offset, &tag);
            if (param_len == 0) {
                return false;
            }
            if (tag == KM_TAG_USER_SECURE_ID) {
                assert(param_len == 8);
                offset += 4;
                if (key_params_len < offset + param_len) {
                    return false;
                }
                uint64_t value = get_u64(key_params + offset);
                offset += param_len;
                if ((value == auth_token.user_id) ||
                    (value == auth_token.authenticator_id))
                {
                    user_id_match = true;
                }
                if (session->n_user_secure_ids >= KM_MAX_N_USER_SECURE_ID) {
                    return false;
                }
                session->user_secure_ids[session->n_user_secure_ids] = value;
                session->n_user_secure_ids++;
            } else {
                offset += 4 + param_len;
            }
        }
        if (!user_id_match) {
            return false;
        }

        /* Check authenticator_type against the key's KM_TAG_USER_AUTH_TYPE. */
        uint32_t auth_type;
        if (KM_ERROR_OK != get_integer_tag_data(key_params, key_params_len,
            KM_TAG_USER_AUTH_TYPE, &auth_type))
        {
            return false;
        }
        if ((ntoh32(auth_token.authenticator_type) & auth_type) == 0) {
            return false;
        }
        session->auth_type = auth_type;

        /* Check timestamp against the key's KM_TAG_AUTH_TIMEOUT, and set
         * session->auth_timeout.
         */
        if (KM_ERROR_OK == get_integer_tag_data(key_params, key_params_len,
            KM_TAG_AUTH_TIMEOUT, &session->auth_timeout))
        {
            uint64_t now;
            if (TLAPI_OK != tlApiGetSecureTimestamp(&now)) {
                return false;
            }
            if (now >= ntoh64(auth_token.timestamp)
                       + session->auth_timeout * 1000000ull)
            {
                return false;
            }
        } else {
            session->auth_timeout = 0; // if not present
        }
    } else {
        session->no_auth_required = true;
    }

    return true;
}

bool authorize_op_update_finish(
    session_handle_t *session,
    const uint8_t *op_params,
    uint32_t op_params_len)
{
    if (session == NULL) {
        return false;
    }

    if (!session->no_auth_required && (session->auth_timeout == 0)) {
        /* Unless the key was explicitly created with
         * KM_TAG_NO_AUTH_REQUIRED=true or with a KM_TAG_AUTH_TIMEOUT, update()
         * and finish() calls must provide authentication tokens, and the
         * user_id, authenticator_id and authenticator_type fields must match
         * one of the KM_TAG_USER_SECURE_ID fields on the key.
         */
        hw_auth_token_t auth_token;
        if (!get_auth_token(&auth_token, op_params, op_params_len)) {
            return false;
        }

        bool user_id_match = false;
        for (uint32_t i = 0; i < session->n_user_secure_ids; i++) {
            if ((session->user_secure_ids[i] == auth_token.user_id) ||
                (session->user_secure_ids[i] == auth_token.authenticator_id))
            {
                user_id_match = true;
                break;
            }
        }
        if (!user_id_match) {
            return false;
        }
        if ((ntoh32(auth_token.authenticator_type) & session->auth_type) == 0) {
            return false;
        }

        /* The 'challenge' should match the operation handle. */
        if (auth_token.challenge != session->nwd_session_handle) {
            return false;
        }
    }

    return true;
}
