/*
 * Copyright (c) 2013-2015 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

#include "tlStd.h"
#include "TlApi/TlApi.h"
#include "aes.h"

#include "tlCommon.h"
#include "tlCryptoHandler.h"
#include "keymaster_util.h"
#include "tlkm_hash.h"
#include "tlkm_rsa.h"
#include "tlkm_hmac.h"
#include "tlkm_aes.h"
#include "tlkm_ec.h"
#include "tlkm_authentication.h"
#include "tlkm_hw_auth_token.h"

/* Definitions */

/* Maximum size of plain data whose encryption will fit in a blob of given size.
 * Blob consists of 16-byte nonce, ciphertext, and 16-byte tag.
 */
#define DATA_SIZE(blob_size) ((blob_size) - 32)
#define IS_VALID_BLOB_SIZE(blob_size) ((blob_size) >= 32)

/* Session array */
session_handle_t session_handles[MAX_SESSION_NUM];

/* AES operation contexts */
aesOpCtx_t aesOpCtxs[MAX_SESSION_NUM];

/* HMAC operation contexts */
hmacOpCtx_t hmacOpCtxs[MAX_SESSION_NUM];

/* Hash operation contexts */
hashOpCtx_t hashOpCtxs[MAX_SESSION_NUM];

/**
 * Clear all context associated with a session.
 * @param sessionIdx index of session
 */
static void clear_session(
    int sessionIdx)
{
    if ((sessionIdx >= 0) && (sessionIdx < MAX_SESSION_NUM)) {
        memset(&session_handles[sessionIdx], 0, sizeof(session_handle_t));
        memset(&aesOpCtxs[sessionIdx], 0, sizeof(aesOpCtx_t));
        memset(&hmacOpCtxs[sessionIdx], 0, sizeof(hmacOpCtx_t));
        memset(&hashOpCtxs[sessionIdx], 0, sizeof(hashOpCtx_t));
        session_handles[sessionIdx].in_use = false;
        session_handles[sessionIdx].cr_session_handle = CR_SID_INVALID;
    }
}

/**
 * Allocate an index for the session and generate a NWd session ID.
 *
 * If we have reached the session limit, return KM_ERROR_TOO_MANY_OPERATIONS.
 *
 * @param[out] sessionIdx index for new session
 * @param[out] nwd_handle NWd session ID
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t allocate_session(
    int *sessionIdx,
    uint64_t *nwd_handle)
{
    assert((sessionIdx != NULL) && (nwd_handle != NULL));
    for (int i = 0; i < MAX_SESSION_NUM; i++) {
        if (!session_handles[i].in_use) {
            *sessionIdx = i;
            clear_session(i);

            size_t size = sizeof(uint64_t);
            if ((TLAPI_OK != tlApiRandomGenerateData(
                TLAPI_ALG_SECURE_RANDOM, (uint8_t*)nwd_handle, &size)) ||
               (size !=  sizeof(uint64_t)))
            {
                return KM_ERROR_UNKNOWN_ERROR;
            }
            session_handles[i].nwd_session_handle = *nwd_handle;
            session_handles[i].in_use = true;
            return KM_ERROR_OK;
        }
    }
    return KM_ERROR_TOO_MANY_OPERATIONS;
}

/**
 * Retrieve index for existing session with given NWd handle.
 * @param[out] sessionIdx index for existing session
 * @param nwd_handle NWd session ID
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t get_session_idx(
    int *sessionIdx,
    uint64_t nwd_handle)
{
    assert(sessionIdx != NULL);

    for (int i = 0; i < MAX_SESSION_NUM; i++) {
        if ((session_handles[i].nwd_session_handle == nwd_handle) &&
            session_handles[i].in_use)
        {
            *sessionIdx = i;
            return KM_ERROR_OK;
        }
    }
    return KM_ERROR_INVALID_OPERATION_HANDLE;
}

/**
 * Copy an enumerated parameter into a serialized buffer and update the pointer.
 */
static void copy_param_enumerated(
    uint8_t **pos,
    keymaster_tag_t tag,
    uint32_t value)
{
    set_u32_increment_pos(pos, tag);
    set_u32_increment_pos(pos, value);
}

/**
 * Copy a boolean parameter into a serialized buffer and update the pointer.
 */
static void copy_param_boolean(
    uint8_t **pos,
    keymaster_tag_t tag,
    bool value)
{
    set_u32_increment_pos(pos, tag);
    set_u32_increment_pos(pos, value ? 1 : 0);
}

/**
 * Copy a parameter to a given position in buffer.
 *
 * For repeatable parameters, all values are copied.
 *
 * If it's not present, do nothing.
 *
 * Update the position and a counter.
 */
static keymaster_error_t copy_parameter(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    uint8_t **pos,
    uint32_t *count)
{
    assert(count != NULL);
    assert(pos != NULL);
    assert(*pos != NULL);

    if (params_length < 4 ) {
        return KM_ERROR_INSUFFICIENT_BUFFER_SPACE;
    }
    uint32_t n_params = get_u32(params);
    size_t offset = 4;
    bool repeatable = is_repeatable(tag);
    bool found = false;
    for (uint32_t i = 0; i < n_params; i++) {
        if (params_length < offset + 4) {
            return KM_ERROR_INSUFFICIENT_BUFFER_SPACE;
        }
        keymaster_tag_t this_tag = KM_TAG_INVALID;
        uint32_t param_len = param_length(params, params_length, offset, &this_tag);
        if (param_len == 0) {
            return KM_ERROR_INVALID_TAG;
        }
        if (params_length < offset + 4 + param_len) {
            return KM_ERROR_INSUFFICIENT_BUFFER_SPACE;
        }
        if (tag == this_tag) {
            if (found && !repeatable) {
                return KM_ERROR_INVALID_TAG;
            }
            found = true;
            *count += 1;
            memcpy(*pos, params + offset, 4 + param_len);
            *pos += 4 + param_len;
        }
        offset += 4 + param_len; // tag + value
    }

    return KM_ERROR_OK;
}

/**
 * Get the characteristics of a key from plaintext key material.
 *
 * @param key_material plain key material
 * @param key_material_len length of \p key_material buffer
 * @param characteristics buffer to contain serialized characteristics
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t get_characteristics(
    const uint8_t *key_material,
    uint32_t key_material_len,
    uint8_t characteristics[KM_CHARACTERISTICS_SIZE])
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *pos = NULL;
    uint8_t *hw_pos = NULL;
    uint8_t *sw_pos = NULL;
    uint32_t hw_count;
    uint32_t sw_count;

    if ((key_material == NULL) || (characteristics == NULL)) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }

    /* Extract params */
    if (key_material_len < 4) {
        return KM_ERROR_INVALID_KEY_BLOB;
    }
    uint32_t params_len = get_u32(key_material);
    if (params_len > key_material_len - 4) {
        return KM_ERROR_INVALID_KEY_BLOB;
    }
    const uint8_t *params = key_material + 4;

#define COPY_PARAM(tag, counter) \
    CHECK_RESULT_OK(copy_parameter(params, params_len, tag, &pos, &counter))
    /* Get the hw_enforced characteristics */
    hw_pos = characteristics;
    pos = hw_pos + 4;
    hw_count = 0;
#define COPY_HW_PARAM(tag) COPY_PARAM(tag, hw_count)
    COPY_HW_PARAM(KM_TAG_ALGORITHM);
    COPY_HW_PARAM(KM_TAG_AUTH_TIMEOUT);
    COPY_HW_PARAM(KM_TAG_BLOB_USAGE_REQUIREMENTS);
    COPY_HW_PARAM(KM_TAG_BLOCK_MODE);
    COPY_HW_PARAM(KM_TAG_BOOTLOADER_ONLY);
    COPY_HW_PARAM(KM_TAG_CALLER_NONCE);
    COPY_HW_PARAM(KM_TAG_DIGEST);
    COPY_HW_PARAM(KM_TAG_KEY_SIZE);
    COPY_HW_PARAM(KM_TAG_MIN_MAC_LENGTH);
    COPY_HW_PARAM(KM_TAG_NO_AUTH_REQUIRED);
    COPY_HW_PARAM(KM_TAG_ORIGIN);
    COPY_HW_PARAM(KM_TAG_PADDING);
    COPY_HW_PARAM(KM_TAG_PURPOSE);
    COPY_HW_PARAM(KM_TAG_RSA_PUBLIC_EXPONENT);
    COPY_HW_PARAM(KM_TAG_ROLLBACK_RESISTANT);
    COPY_HW_PARAM(KM_TAG_USER_AUTH_TYPE);
    COPY_HW_PARAM(KM_TAG_USER_SECURE_ID);
#undef COPY_HW_PARAM
    assert(hw_count <= KM_N_HW_CHARACTERISTICS);
    set_u32(hw_pos, hw_count);
    /* Get the sw_enforced characteristics */
    sw_pos = pos;
    pos = sw_pos + 4;
    sw_count = 0;
#define COPY_SW_PARAM(tag) COPY_PARAM(tag, sw_count)
    COPY_SW_PARAM(KM_TAG_ACTIVE_DATETIME);
    COPY_SW_PARAM(KM_TAG_CREATION_DATETIME);
    COPY_SW_PARAM(KM_TAG_MAX_USES_PER_BOOT);
    COPY_SW_PARAM(KM_TAG_MIN_SECONDS_BETWEEN_OPS);
    COPY_SW_PARAM(KM_TAG_ORIGINATION_EXPIRE_DATETIME);
    COPY_SW_PARAM(KM_TAG_USAGE_EXPIRE_DATETIME);
#undef COPY_SW_PARAM
    assert(sw_count <= KM_N_SW_CHARACTERISTICS);
    set_u32(sw_pos, sw_count);
#undef COPY_PARAM

end:
    return ret;
}

static keymaster_error_t nwd_buf_ok(
    uint8_t *buf,
    uint32_t buflen)
{
    if (buf == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    if (buflen == 0) {
        return KM_ERROR_INVALID_ARGUMENT;
    }
    if (!tlApiIsNwdBufferValid(buf, buflen)) {
        return KM_ERROR_SECURE_HW_COMMUNICATION_FAILED;
    }
    return KM_ERROR_OK;
}

keymaster_error_t handle_AddRngEntropy( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *buf = NULL;
    uint8_t *data = NULL;
    uint32_t data_length = 0;
    uint32_t offset;

    CHECK_NOT_NULL(pTci);

    data = (uint8_t*)pTci->message.add_rng_entropy.rng_data.data;
    data_length = pTci->message.add_rng_entropy.rng_data.data_length;

    CHECK_RESULT_OK(nwd_buf_ok(data, data_length));

    /* Allocate local buffer and copy data into it. */
    CHECK_RESULT_OK(tlkm_alloc(&buf, data_length));
    memcpy(buf, data, data_length);

    /* tlApiAddEntropy() will only accept up to 256 bytes */
    for (offset = 0; offset < data_length; offset += 256) {
        CHECK_TLAPI_OK(tlApiAddEntropy(buf + offset, MIN(data_length - offset, 256)));
    }

end:
    tlApiFree(buf);
    return ret;
}

static void set_own_params(
    uint8_t own_params[OWN_PARAMS_SIZE],
    keymaster_key_origin_t origin)
{
    uint8_t *pos = &own_params[0];
    copy_param_enumerated(&pos, KM_TAG_BLOB_USAGE_REQUIREMENTS, KM_BLOB_STANDALONE);
    copy_param_enumerated(&pos, KM_TAG_ORIGIN, origin);
    copy_param_boolean(&pos, KM_TAG_ROLLBACK_RESISTANT, false);
}

static keymaster_error_t validate_key_creation_params(
    const uint8_t *params,
    uint32_t params_len)
{
    bool user_secure_id_present = contains_tag(params, params_len, KM_TAG_USER_SECURE_ID, false);
    bool auth_required = is_auth_required(params, params_len);
    if (!auth_required && user_secure_id_present) {
        return KM_ERROR_INVALID_TAG;
    }
    if (auth_required && !user_secure_id_present) {
        return KM_ERROR_INVALID_TAG;
    }
    /* Reject if KM_TAG_BLOB_USAGE_REQUIREMENTS = KM_BLOB_REQUIRES_FILE_SYSTEM */
    if (get_enumerated_tag_data_with_default(params, params_len,
        KM_TAG_BLOB_USAGE_REQUIREMENTS, KM_BLOB_STANDALONE) == KM_BLOB_REQUIRES_FILE_SYSTEM)
    {
        return KM_ERROR_INVALID_TAG;
    }
    return KM_ERROR_OK;
}

keymaster_error_t handle_GenerateKey( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *local_params = NULL;
    uint8_t *local_key_blob = NULL;
    uint8_t local_characteristics[KM_CHARACTERISTICS_SIZE];
    uint32_t blob_len = 0;
    uint8_t *params = NULL;
    uint32_t params_length = 0;
    uint8_t *key_blob = NULL;
    uint32_t key_blob_length = 0;
    uint8_t *characteristics = NULL;
    uint32_t characteristics_length = 0;
    uint8_t *key_material = NULL;
    uint32_t key_material_len = 0;
    uint8_t own_params[OWN_PARAMS_SIZE];
    keymaster_algorithm_t key_type;
    uint32_t key_size;

    CHECK_NOT_NULL(pTci);

    params = (uint8_t*)pTci->message.generate_key.params.data;
    params_length = pTci->message.generate_key.params.data_length;
    key_blob = (uint8_t*)pTci->message.generate_key.key_blob.data;
    key_blob_length = pTci->message.generate_key.key_blob.data_length;
    characteristics = (uint8_t*)pTci->message.generate_key.characteristics.data;
    characteristics_length = pTci->message.generate_key.characteristics.data_length;

    CHECK_RESULT_OK(nwd_buf_ok(params, params_length));
    CHECK_RESULT_OK(nwd_buf_ok(key_blob, key_blob_length));

    if (characteristics != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(characteristics, characteristics_length));
        CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
            characteristics_length >= KM_CHARACTERISTICS_SIZE);
    }

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        IS_VALID_BLOB_SIZE(key_blob_length));

    /* Allocate memory for local data */
    CHECK_RESULT_OK(tlkm_alloc(&local_params, params_length));
    CHECK_RESULT_OK(tlkm_alloc(&local_key_blob, key_blob_length));

    /* Copy params into local memory for further processing */
    memcpy(local_params, params, params_length);

    CHECK_RESULT_OK(validate_key_creation_params(local_params, params_length));

    /* Populate own_params */
    set_own_params(own_params, KM_ORIGIN_GENERATED);

    /* Fetch key type from serialized key parameters */
    CHECK_RESULT_OK(get_enumerated_tag_data(local_params, params_length,
        KM_TAG_ALGORITHM, (uint32_t*)&key_type));
    /* Fetch key size (in bits) from serialized key parameters */
    CHECK_RESULT_OK(get_integer_tag_data(local_params, params_length,
        KM_TAG_KEY_SIZE, &key_size));
    key_material_len = DATA_SIZE(key_blob_length);
    CHECK_RESULT_OK(tlkm_alloc(&key_material, key_material_len));

    switch (key_type) {
        case KM_ALGORITHM_AES:
            ret = aes_generatekey(
                key_size,
                local_params, params_length,
                own_params, sizeof(own_params),
                key_material, &key_material_len);
            break;
        case KM_ALGORITHM_HMAC:
            ret = hmac_generatekey(
                key_size,
                local_params, params_length,
                own_params, sizeof(own_params),
                key_material, &key_material_len);
            break;
        case KM_ALGORITHM_RSA:
            ret = rsa_generatekey(
                key_size,
                local_params, params_length,
                own_params, sizeof(own_params),
                key_material, &key_material_len);
            break;
        case KM_ALGORITHM_EC:
            ret = ec_generatekey(
                key_size,
                local_params, params_length,
                own_params, sizeof(own_params),
                key_material, &key_material_len);
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_ALGORITHM;
    }
    if (ret != KM_ERROR_OK) {
        goto end;
    }

    if (characteristics != NULL) {
        /* Get the key characteristics */
        CHECK_RESULT_OK(get_characteristics(
            key_material, key_material_len, local_characteristics));
    }

    /* Encrypt data */
    blob_len = key_blob_length;
    CHECK_RESULT_OK(encrypt_data(key_material, key_material_len,
        local_key_blob, &blob_len));

    /* Copy local data into NWd buffer */
    memcpy(key_blob, local_key_blob, blob_len);
    pTci->message.generate_key.key_blob.data_length = blob_len;
    if (characteristics != NULL) {
        memcpy(characteristics, local_characteristics, KM_CHARACTERISTICS_SIZE);
    }

end:
    tlApiFree(local_params);
    tlApiFree(local_key_blob);
    tlApiFree(key_material);

    return ret;
}

static keymaster_error_t validate_bindings(
    const uint8_t *key_material,
    uint32_t key_material_len,
    const uint8_t *client_id,
    uint32_t client_id_len,
    const uint8_t *app_data,
    uint32_t app_data_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    keymaster_blob_t client_id_blob = {client_id, client_id_len};
    keymaster_blob_t app_data_blob = {app_data, app_data_len};
    uint32_t params_len = 0;

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key_material_len >= 4);
    params_len = get_u32(key_material);
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key_material_len >= 4 + params_len);

    CHECK_TRUE(KM_ERROR_KEY_USER_NOT_AUTHENTICATED,
        test_optional_blob_tag_matches(key_material + 4, params_len,
            KM_TAG_APPLICATION_ID, &client_id_blob));

    CHECK_TRUE(KM_ERROR_KEY_USER_NOT_AUTHENTICATED,
        test_optional_blob_tag_matches(key_material + 4, params_len,
            KM_TAG_APPLICATION_DATA, &app_data_blob));

end:
    return ret;
}

keymaster_error_t handle_GetKeyCharacteristics( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *local_key_blob = NULL;
    uint8_t *local_client_id = NULL;
    uint8_t *local_app_data = NULL;
    uint8_t local_characteristics[KM_CHARACTERISTICS_SIZE];
    uint8_t *key_blob = NULL;
    uint32_t key_blob_length = 0;
    uint8_t *client_id = NULL;
    uint32_t client_id_length = 0;
    uint8_t *app_data = NULL;
    uint32_t app_data_length = 0;
    uint8_t *characteristics = NULL;
    uint32_t characteristics_length = 0;
    uint8_t *key_material = NULL;
    uint32_t key_material_len = 0;

    CHECK_NOT_NULL(pTci);

    /* Get addresses and lengths from buffer */
    key_blob = (uint8_t*)pTci->message.get_key_characteristics.key_blob.data;
    key_blob_length = pTci->message.get_key_characteristics.key_blob.data_length;
    client_id = (uint8_t*)pTci->message.get_key_characteristics.client_id.data;
    client_id_length = pTci->message.get_key_characteristics.client_id.data_length;
    app_data = (uint8_t*)pTci->message.get_key_characteristics.app_data.data;
    app_data_length = pTci->message.get_key_characteristics.app_data.data_length;
    characteristics = (uint8_t*)pTci->message.get_key_characteristics.characteristics.data;
    characteristics_length = pTci->message.get_key_characteristics.characteristics.data_length;

    CHECK_RESULT_OK(nwd_buf_ok(key_blob, key_blob_length));
    if (client_id != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(client_id, client_id_length));
    }
    if (app_data != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(app_data, app_data_length));
    }
    CHECK_RESULT_OK(nwd_buf_ok(characteristics, characteristics_length));

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        IS_VALID_BLOB_SIZE(key_blob_length));
    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        characteristics_length >= KM_CHARACTERISTICS_SIZE);

    key_material_len = DATA_SIZE(key_blob_length);

    /* Allocate local buffers and copy data into them */
    CHECK_RESULT_OK(tlkm_alloc(&local_key_blob, key_blob_length));
    memcpy(local_key_blob, key_blob, key_blob_length);
    if (client_id != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_client_id, client_id_length));
        memcpy(local_client_id, client_id, client_id_length);
    }
    if (app_data != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_app_data, app_data_length));
        memcpy(local_app_data, app_data, app_data_length);
    }

    CHECK_RESULT_OK(tlkm_alloc(&key_material, key_material_len));

    /* Decrypt key blob */
    CHECK_RESULT_OK(decrypt_blob(local_key_blob, key_blob_length,
        key_material, &key_material_len));

    /* Check client_id and app_data */
    CHECK_RESULT_OK(validate_bindings(
        key_material, key_material_len,
        local_client_id, client_id_length,
        local_app_data, app_data_length));

    /* Get the key characteristics */
    CHECK_RESULT_OK(get_characteristics(
        key_material, key_material_len, local_characteristics));

    /* Copy local buffers to message */
    memcpy(characteristics, local_characteristics, KM_CHARACTERISTICS_SIZE);

end:
    tlApiFree(local_key_blob);
    tlApiFree(local_client_id);
    tlApiFree(local_app_data);
    tlApiFree(key_material);

    return ret;
}

keymaster_error_t handle_ImportKey( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *local_params = NULL;
    uint8_t *local_data = NULL;
    uint8_t *local_key_blob = NULL;
    uint8_t local_characteristics[KM_CHARACTERISTICS_SIZE];
    uint8_t *key_material = NULL;
    uint32_t key_material_len = 0;
    uint32_t blob_len = 0;
    uint8_t *params = NULL;
    uint32_t params_length = 0;
    uint8_t *data = NULL;
    uint32_t data_length = 0;
    uint8_t *key_blob = NULL;
    uint32_t key_blob_length = 0;
    uint8_t *characteristics = NULL;
    uint32_t characteristics_length = 0;
    uint8_t own_params[OWN_PARAMS_SIZE];
    uint8_t *pos;

    CHECK_NOT_NULL(pTci);

    params = (uint8_t*)pTci->message.import_key.params.data;
    params_length = pTci->message.import_key.params.data_length;
    data = (uint8_t*)pTci->message.import_key.key_data.data;
    data_length = pTci->message.import_key.key_data.data_length;
    key_blob = (uint8_t*)pTci->message.import_key.key_blob.data;
    key_blob_length = pTci->message.import_key.key_blob.data_length;
    characteristics = (uint8_t*)pTci->message.import_key.characteristics.data;
    characteristics_length = pTci->message.import_key.characteristics.data_length;

    CHECK_RESULT_OK(nwd_buf_ok(params, params_length));
    CHECK_RESULT_OK(nwd_buf_ok(data, data_length));
    CHECK_RESULT_OK(nwd_buf_ok(key_blob, key_blob_length));
    if (characteristics != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(characteristics, characteristics_length));
        CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
            characteristics_length >= KM_CHARACTERISTICS_SIZE);
    }

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        IS_VALID_BLOB_SIZE(key_blob_length));

    /* Allocate memory for parameters, key data and key blob */
    CHECK_RESULT_OK(tlkm_alloc(&local_params, params_length));
    CHECK_RESULT_OK(tlkm_alloc(&local_data, data_length));
    CHECK_RESULT_OK(tlkm_alloc(&local_key_blob, key_blob_length));

    /* Copy params and key data into local memory for further processing */
    memcpy(local_params, params, params_length);
    memcpy(local_data, data, data_length);

    CHECK_RESULT_OK(validate_key_creation_params(local_params, params_length));

    /* Populate own_params */
    set_own_params(own_params, KM_ORIGIN_IMPORTED);

    key_material_len = DATA_SIZE(key_blob_length);
    CHECK_RESULT_OK(tlkm_alloc(&key_material, key_material_len));

    // key_material <-- local_data with own_params inserted (and length updated)
    pos = key_material;
    CHECK_RESULT_OK(make_key_material_params(&pos, local_params, params_length,
        OWN_PARAMS_NB, own_params, sizeof(own_params)));

    set_data_increment_pos(&pos, local_data, data_length);

    if (characteristics != NULL) {
        /* Get the key characteristics */
        CHECK_RESULT_OK(get_characteristics(
            key_material, key_material_len, local_characteristics));
    }

    /* Encrypt data */
    blob_len = key_blob_length;
    CHECK_RESULT_OK(encrypt_data(key_material, key_material_len,
        local_key_blob, &blob_len));

    /* Copy local data into NWd buffer */
    memcpy(key_blob, local_key_blob, blob_len);
    pTci->message.import_key.key_blob.data_length = blob_len;
    if (characteristics != NULL) {
        memcpy(characteristics, local_characteristics, KM_CHARACTERISTICS_SIZE);
    }

end:
    tlApiFree(local_params);
    tlApiFree(local_data);
    tlApiFree(local_key_blob);
    tlApiFree(key_material);

    return ret;
}

keymaster_error_t handle_ExportKey( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *key_blob = NULL;
    uint32_t key_blob_length = 0;
    uint8_t *client_id = NULL;
    uint32_t client_id_length = 0;
    uint8_t *app_data = NULL;
    uint32_t app_data_length = 0;
    uint8_t *key_data = NULL;
    uint32_t key_data_length = 0;
    uint8_t *core_pub_data = NULL; // for export
    uint8_t *key_material = NULL; // for decrypted blob
    uint32_t key_material_len = 0;
    uint8_t *local_key_blob = NULL;
    uint8_t *local_client_id = NULL;
    uint8_t *local_app_data = NULL;
    uint32_t params_len;
    keymaster_algorithm_t key_type;
    uint8_t *core_key_data;
    uint32_t core_key_data_len;

    CHECK_NOT_NULL(pTci);

    key_blob = (uint8_t*)pTci->message.export_key.key_blob.data;
    key_blob_length = pTci->message.export_key.key_blob.data_length;
    client_id = (uint8_t*)pTci->message.get_key_characteristics.client_id.data;
    client_id_length = pTci->message.get_key_characteristics.client_id.data_length;
    app_data = (uint8_t*)pTci->message.get_key_characteristics.app_data.data;
    app_data_length = pTci->message.get_key_characteristics.app_data.data_length;
    key_data = (uint8_t*)pTci->message.export_key.key_data.data;
    key_data_length = pTci->message.export_key.key_data.data_length;

    CHECK_RESULT_OK(nwd_buf_ok(key_blob, key_blob_length));
    if (client_id != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(client_id, client_id_length));
    }
    if (app_data != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(app_data, app_data_length));
    }
    CHECK_RESULT_OK(nwd_buf_ok(key_data, key_data_length));

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        IS_VALID_BLOB_SIZE(key_blob_length));


    /* Allocate local buffers and copy data into them */
    CHECK_RESULT_OK(tlkm_alloc(&local_key_blob, key_blob_length));
    memcpy(local_key_blob, key_blob, key_blob_length);
    if (client_id != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_client_id, client_id_length));
        memcpy(local_client_id, client_id, client_id_length);
    }
    if (app_data != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_app_data, app_data_length));
        memcpy(local_app_data, app_data, app_data_length);
    }

    /* Allocate memory for core_pub_data */
    CHECK_RESULT_OK(tlkm_alloc(&core_pub_data, key_data_length));

    /* Decrypt key_blob --> key_material */
    key_material_len = DATA_SIZE(key_blob_length);
    CHECK_RESULT_OK(tlkm_alloc(&key_material, key_material_len));
    CHECK_RESULT_OK(decrypt_blob(local_key_blob, key_blob_length,
        key_material, &key_material_len));

    /* Check client_id and app_data */
    CHECK_RESULT_OK(validate_bindings(
        key_material, key_material_len,
        local_client_id, client_id_length,
        local_app_data, app_data_length));

    /* Get the key type. */
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key_material_len >= 4);
    params_len = get_u32(key_material);
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key_material_len >= 4 + params_len + 4 + 4);
    core_key_data = key_material + 4 + params_len + 4 + 4;
    core_key_data_len = key_material_len - (4 + params_len + 4 + 4);
    key_type = (keymaster_algorithm_t)get_u32(key_material + 4 + params_len);

    /* Extract the public key information */
    switch (key_type) {
        case KM_ALGORITHM_AES:
        case KM_ALGORITHM_HMAC:
            ret = KM_ERROR_UNIMPLEMENTED;
            break;
        case KM_ALGORITHM_RSA:
            ret = rsa_get_pub_data(core_pub_data, core_key_data_len,
                core_key_data, core_key_data_len);
            break;
        case KM_ALGORITHM_EC:
            ret = ec_get_pub_data(core_pub_data, core_key_data_len,
                core_key_data, core_key_data_len);
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_ALGORITHM;
    }
    if (ret != KM_ERROR_OK) {
        goto end;
    }

    /* Copy core_pub_data into NWd buffer. */
    memcpy(key_data, core_pub_data, key_data_length);

end:
    tlApiFree(local_key_blob);
    tlApiFree(local_client_id);
    tlApiFree(local_app_data);
    tlApiFree(key_material);
    tlApiFree(core_pub_data);

    return ret;
}

keymaster_error_t handle_Begin( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    keymaster_purpose_t purpose;
    uint8_t *params = NULL;
    uint32_t params_length = 0;
    uint8_t *key_blob = NULL;
    uint32_t key_blob_length = 0;
    uint8_t *out_params = NULL;
    uint32_t out_params_length = 0;
    uint32_t out_params_length_returned = 0;
    uint8_t *local_params = NULL;
    uint8_t *local_key_blob = NULL;
    uint8_t *key_material = NULL; // for decrypted blob
    uint32_t key_material_len = 0;
    int sessionIdx = -1;
    uint64_t nwd_session_id = 0;
    session_handle_t *session;
    keymaster_algorithm_t algorithm;
    const uint8_t *key_params;
    uint32_t key_params_len; // length of params stored in blob
    const uint8_t *key_data;
    uint32_t key_size;
    const uint8_t *core_key_data; // key_metadata | raw_key_data
    uint32_t core_key_data_len;
    keymaster_block_mode_t mode;
    keymaster_digest_t digest;
    keymaster_padding_t padding;
    uint32_t min_mac_length = 0;
    bool caller_nonce;
    bool return_nonce;
    uint8_t nonce[16];
    uint32_t nonce_len = 16;
    bool require_digest = false;
    CHECK_NOT_NULL(pTci);

    purpose = pTci->message.begin.purpose;
    params = (uint8_t*)pTci->message.begin.params.data;
    params_length = pTci->message.begin.params.data_length;
    key_blob = (uint8_t*)pTci->message.begin.key_blob.data;
    key_blob_length = pTci->message.begin.key_blob.data_length;
    out_params = (uint8_t*)pTci->message.begin.out_params.data;
    out_params_length = pTci->message.begin.out_params.data_length;
    pTci->message.begin.out_params.data_length = 0; // will be set later

    CHECK_RESULT_OK(nwd_buf_ok(params, params_length));
    CHECK_RESULT_OK(nwd_buf_ok(key_blob, key_blob_length));
    if (out_params != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(out_params, out_params_length));
    }

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        IS_VALID_BLOB_SIZE(key_blob_length));

    /* Allocate session and Nwd handle. */
    CHECK_RESULT_OK(allocate_session(&sessionIdx, &nwd_session_id));
    session = &session_handles[sessionIdx];

    /* Make local copies of buffers. */
    CHECK_RESULT_OK(tlkm_alloc(&local_params, params_length));
    CHECK_RESULT_OK(tlkm_alloc(&local_key_blob, key_blob_length));
    memcpy(local_params, params, params_length);
    memcpy(local_key_blob, key_blob, key_blob_length);

    /* Decrypt blob */
    key_material_len = DATA_SIZE(key_blob_length);
    CHECK_RESULT_OK(tlkm_alloc(&key_material, key_material_len));
    CHECK_RESULT_OK(decrypt_blob(local_key_blob, key_blob_length,
        key_material, &key_material_len));
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key_material_len >= 4);
    key_params_len = get_u32(key_material);
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key_material_len >= 4 + key_params_len + 8);
    key_params = key_material + 4;
    key_data = key_params + key_params_len;

    /* Get key type (algorithm), key size and core key data. */
    algorithm = (keymaster_algorithm_t)get_u32(key_data);
    key_size = get_u32(key_data + 4);
    core_key_data = key_data + 8;
    core_key_data_len = key_material_len - (4 + key_params_len + 8);

    /* Update session data */
    session->algorithm = algorithm;
    session->key_size = key_size;
    session->purpose = purpose;

    /* Check purpose is allowed by the algorithm. */
    CHECK_TRUE(KM_ERROR_UNSUPPORTED_PURPOSE,
        check_algorithm_purpose(algorithm, purpose));

    /* Authorize the operation. */
    CHECK_TRUE(KM_ERROR_KEY_USER_NOT_AUTHENTICATED,
        authorize_op_begin(session,
            key_params, key_params_len, local_params, params_length));

    /* Find min MAC length. */
    min_mac_length = get_integer_tag_data_with_default(
        key_params, key_params_len, KM_TAG_MIN_MAC_LENGTH, 0);

    /* Return KM_ERROR_INVALID_KEY_BLOB if key contains KM_TAG_BOOTLOADER_ONLY and bootloader finished executing */
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        (!contains_tag(key_params, key_params_len, KM_TAG_BOOTLOADER_ONLY, true)));

    /* Begin operation. */
    switch (algorithm) {
        case KM_ALGORITHM_AES:
            /* Check purpose is allowed by the key. */
            CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PURPOSE,
                test_enumerated_tag_data(key_params, key_params_len,
                    KM_TAG_PURPOSE, purpose));
            /* What mode? */
            CHECK_RESULT_OK(get_enumerated_tag_data(local_params, params_length,
                KM_TAG_BLOCK_MODE, (uint32_t *)&mode));
            /* Check mode is allowed by the key. */
            CHECK_TRUE(KM_ERROR_INCOMPATIBLE_BLOCK_MODE,
                test_enumerated_tag_data(key_params, key_params_len,
                    KM_TAG_BLOCK_MODE, mode));
            /* What padding mode? */
            if (KM_ERROR_OK == get_enumerated_tag_data(local_params, params_length,
                KM_TAG_PADDING, (uint32_t *)&padding))
            {
                /* Check padding mode is allowed by the key. */
                CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PADDING_MODE,
                    test_enumerated_tag_data(key_params, key_params_len,
                        KM_TAG_PADDING, padding));
            } else {
                padding = KM_PAD_NONE;
            }
            if (padding == KM_PAD_PKCS7) {
                /* PKCS7 padding is allowed only for ECB and CBC */
                CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PADDING_MODE,
                    (mode == KM_MODE_ECB) || (mode == KM_MODE_CBC));
                session->pkcs7_padding = true;
            } else {
                CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PADDING_MODE,
                    padding == KM_PAD_NONE);
                session->pkcs7_padding = false;
            }
            /* Check caller_nonce policy on key */
            caller_nonce = get_boolean_tag_data_with_default(
                key_params, key_params_len, KM_TAG_CALLER_NONCE, false);

            CHECK_RESULT_OK(aes_begin(
                purpose,
                local_params, params_length,
                key_size,
                core_key_data, core_key_data_len,
                mode, min_mac_length, caller_nonce,
                &return_nonce,
                nonce, &nonce_len,
                session,
                &aesOpCtxs[sessionIdx]));
            if (return_nonce) { // copy nonce to out_params, serialized
                uint8_t *pos;
                CHECK_TRUE(KM_ERROR_OUTPUT_PARAMETER_NULL,
                    out_params != NULL);
                CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                    out_params_length >= TEE_BEGIN_OUT_PARAMS_SIZE);
                pos = out_params;
                set_u32_increment_pos(&pos, 1); // one param
                set_u32_increment_pos(&pos, KM_TAG_NONCE); // param tag
                set_u32_increment_pos(&pos, nonce_len); // blob length
                set_data_increment_pos(&pos, nonce, nonce_len); // blob data
                out_params_length_returned = 4 + 4 + 4 + nonce_len;
            }
            break;
        case KM_ALGORITHM_HMAC:
            /* Check purpose is allowed by the key. */
            CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PURPOSE,
                test_enumerated_tag_data(key_params, key_params_len,
                    KM_TAG_PURPOSE, purpose));
            /* What digest? */
            CHECK_RESULT_OK(get_enumerated_tag_data(key_params, key_params_len,
                KM_TAG_DIGEST, (uint32_t *)&digest));
            /* If digest is present in params, it must match that one. */
            CHECK_TRUE(KM_ERROR_INCOMPATIBLE_DIGEST,
                test_optional_enumerated_tag_matches(local_params, params_length,
                    KM_TAG_DIGEST, digest));
            /* Find min MAC length */
            session->min_taglen = BITS_TO_BYTES(get_integer_tag_data_with_default(
                key_params, key_params_len, KM_TAG_MIN_MAC_LENGTH, 64));
            CHECK_RESULT_OK(hmac_begin(
                purpose,
                local_params, params_length,
                key_size,
                core_key_data, core_key_data_len,
                digest, min_mac_length,
                &(session->taglen),
                &hmacOpCtxs[sessionIdx]));
            CHECK_TRUE(KM_ERROR_INVALID_MAC_LENGTH,
                session->taglen >= session->min_taglen);
            break;
        case KM_ALGORITHM_RSA:
            /* What padding mode? */
            CHECK_TRUE(KM_ERROR_UNSUPPORTED_PADDING_MODE,
                KM_ERROR_OK == get_enumerated_tag_data(local_params, params_length,
                    KM_TAG_PADDING, (uint32_t *)&padding));

            /* What digest? If not provided default to KM_DIGEST_NONE. */
            digest = KM_DIGEST_NONE;
            require_digest = (KM_ERROR_OK == get_enumerated_tag_data(
                local_params, params_length, KM_TAG_DIGEST, (uint32_t*)&digest));

            if( (purpose==KM_PURPOSE_SIGN) || (purpose==KM_PURPOSE_VERIFY) || (padding==KM_PAD_RSA_OAEP) )
            {
                // for signing, verification and OAEP operations a digest must be specified in local_params
                CHECK_TRUE(KM_ERROR_INCOMPATIBLE_DIGEST, require_digest);
            }

            CHECK_RESULT_OK(rsa_verify_key_params(
                key_params, key_params_len, purpose, padding, digest, require_digest));
            CHECK_RESULT_OK(rsa_begin(
                purpose,
                key_size,
                core_key_data, core_key_data_len,
                padding,
                digest,
                session));
            break;
        case KM_ALGORITHM_EC:
            /* What digest? */
            digest = (keymaster_digest_t)get_enumerated_tag_data_with_default(
                local_params, params_length, KM_TAG_DIGEST, KM_DIGEST_NONE);
            if (purpose == KM_PURPOSE_SIGN) {
                /* Private-key operation: check purpose and digest are allowed. */
                /* Check purpose is allowed by the key. */
                CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PURPOSE,
                    test_enumerated_tag_data(key_params, key_params_len,
                        KM_TAG_PURPOSE, purpose));
                CHECK_TRUE(KM_ERROR_INCOMPATIBLE_DIGEST,
                    test_enumerated_tag_data(key_params, key_params_len,
                        KM_TAG_DIGEST, digest));
            }
            CHECK_RESULT_OK(ec_begin(
                purpose,
                key_size,
                core_key_data, core_key_data_len,
                digest,
                session,
                &hashOpCtxs[sessionIdx]));
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_ALGORITHM;
            goto end;
    }

    pTci->message.begin.out_params.data_length = out_params_length_returned;

    /* Assign session handle */
    pTci->message.begin.handle = nwd_session_id;

end:
    if (ret != KM_ERROR_OK) {
        clear_session(sessionIdx);
    }
    tlApiFree(local_params);
    tlApiFree(local_key_blob);
    tlApiFree(key_material);
    return ret;
}

keymaster_error_t handle_Update( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;

    uint8_t *params = NULL;
    uint32_t params_length = 0;
    uint8_t *input = NULL;
    uint32_t input_length = 0;
    uint8_t *output = NULL;
    uint32_t output_length = 0;
    uint8_t *local_params = NULL;
    uint8_t *local_input = NULL;
    uint8_t *local_output = NULL;
    int sessionIdx = -1;
    uint64_t nwd_session_id = 0;
    session_handle_t *session = NULL;
    uint32_t input_consumed = 0;

    CHECK_NOT_NULL(pTci);

    params = (uint8_t*)pTci->message.update.params.data;
    params_length = pTci->message.update.params.data_length;
    input = (uint8_t*)pTci->message.update.input.data;
    input_length = pTci->message.update.input.data_length;
    output = (uint8_t*)pTci->message.update.output.data;
    output_length = pTci->message.update.output.data_length;

    if (params != NULL) { // params may be NULL
        CHECK_RESULT_OK(nwd_buf_ok(params, params_length));
    }
    if (input != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(input, input_length));
    }
    if (output != NULL) {
        CHECK_RESULT_OK(nwd_buf_ok(output, output_length));
    }

    /* Locate session. */
    nwd_session_id = pTci->message.update.handle;
    CHECK_RESULT_OK(get_session_idx(&sessionIdx, nwd_session_id));
    session = &session_handles[sessionIdx];

    /* Make local copies of buffers. */
    if (params != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_params, params_length));
        memcpy(local_params, params, params_length);
    }
    if (input != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_input, input_length));
        memcpy(local_input, input, input_length);
    }
    if (output != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_output, output_length));
        memcpy(local_output, output, output_length);
    }

    /* Authorize the operation. */
    CHECK_TRUE(KM_ERROR_KEY_USER_NOT_AUTHENTICATED,
        authorize_op_update_finish(session, local_params, params_length));

    /* Check algorithm associated with the session entry */
    switch(session->algorithm) {
        case KM_ALGORITHM_AES:
            ret = aes_update(
                local_params, params_length,
                local_input, input_length,
                &input_consumed,
                local_output, &output_length,
                session,
                &aesOpCtxs[sessionIdx]);
            break;
        case KM_ALGORITHM_HMAC:
            ret = hmac_update(
                local_input, input_length,
                &input_consumed,
                &hmacOpCtxs[sessionIdx]);
            output_length = 0;
            break;
        case KM_ALGORITHM_RSA:
            ret = rsa_update(
                local_input, input_length,
                &input_consumed,
                session);
            output_length = 0;
            break;
        case KM_ALGORITHM_EC:
            ret = ec_update(
                local_input, input_length,
                &input_consumed,
                session,
                &hashOpCtxs[sessionIdx]);
            output_length = 0;
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_ALGORITHM;
            break;
    }
    if (ret != KM_ERROR_OK) {
        goto end;
    }

    pTci->message.update.input_consumed = input_consumed;

    if ((output != NULL) && (local_output != NULL)) {
        memcpy(output, local_output, output_length);
        pTci->message.update.output.data_length = output_length;
    }

end:
    // If this method returns an error code other than KM_ERROR_OK, the operation
    // is aborted, so session must be cleared.
    if(ret != KM_ERROR_OK) {
        clear_session(sessionIdx);
    }

    tlApiFree(local_params);
    tlApiFree(local_input);
    tlApiFree(local_output);
    return ret;
}

keymaster_error_t handle_Finish( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;

    uint8_t *params = NULL;
    uint32_t params_length = 0;
    uint8_t *signature = NULL;
    uint32_t signature_length = 0;
    uint8_t *output = NULL;
    uint32_t output_length = 0;
    uint8_t *local_params = NULL;
    uint8_t *local_signature = NULL;
    uint8_t *local_output = NULL;
    int sessionIdx = -1;
    uint64_t nwd_session_id = 0;
    session_handle_t *session = NULL;

    CHECK_NOT_NULL(pTci);

    params = (uint8_t*)pTci->message.finish.params.data;
    params_length = pTci->message.finish.params.data_length;
    signature = (uint8_t*)pTci->message.finish.signature.data;
    signature_length = pTci->message.finish.signature.data_length;
    output = (uint8_t*)pTci->message.finish.output.data;
    output_length = pTci->message.finish.output.data_length;

    /* Locate session. */
    nwd_session_id = pTci->message.finish.handle;
    CHECK_RESULT_OK(get_session_idx(&sessionIdx, nwd_session_id));
    session = &session_handles[sessionIdx];

    if (params != NULL) { // params may be NULL
        CHECK_RESULT_OK(nwd_buf_ok(params, params_length));
    }
    if (signature != NULL) { // signature may be NULL
        CHECK_RESULT_OK(nwd_buf_ok(signature, signature_length));
    }
    if (output != NULL) { // output may be NULL
        CHECK_RESULT_OK(nwd_buf_ok(output, output_length));
    }

    /* Make local copies of buffers. */
    if (params != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_params, params_length));
        memcpy(local_params, params, params_length);
    }
    if (signature != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_signature, signature_length));
        memcpy(local_signature, signature, signature_length);
    }
    if (output != NULL) {
        CHECK_RESULT_OK(tlkm_alloc(&local_output, output_length));
    }

    /* Authorize the operation. */
    CHECK_TRUE(KM_ERROR_KEY_USER_NOT_AUTHENTICATED,
        authorize_op_update_finish(session, local_params, params_length));

    /* Finish operation. */
    switch(session->algorithm) {
        case KM_ALGORITHM_AES:
            ret = aes_finish(
                local_output, &output_length,
                session,
                &aesOpCtxs[sessionIdx]);
            break;
        case KM_ALGORITHM_HMAC:
            if (session->purpose == KM_PURPOSE_SIGN) {
                ret = hmac_finish_sign(
                    local_output, &output_length,
                    session->taglen,
                    &hmacOpCtxs[sessionIdx]);
            } else {
                ret = hmac_finish_verify(
                    local_signature, signature_length,
                    session->taglen, session->min_taglen,
                    &hmacOpCtxs[sessionIdx]);
                output_length = 0;
            }
            break;
        case KM_ALGORITHM_RSA:
            if (session->purpose == KM_PURPOSE_SIGN) {
                ret = rsa_finish_sign(
                    local_output, &output_length,
                    session);
            } else if (session->purpose == KM_PURPOSE_VERIFY) {
                ret = rsa_finish_verify(
                    local_signature, signature_length,
                    session);
                output_length = 0;
            } else {
                ret = rsa_finish_cipher(
                    local_output, &output_length,
                    session);
            }
            break;
        case KM_ALGORITHM_EC:
            if (session->purpose == KM_PURPOSE_SIGN) {
                ret = ec_finish_sign(
                    local_output, (size_t*)&output_length,
                    session,
                    &hashOpCtxs[sessionIdx]);
            } else {
                ret = ec_finish_verify(
                    local_signature, signature_length,
                    session,
                    &hashOpCtxs[sessionIdx]);
                output_length = 0;
            }
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_ALGORITHM;
            break;
    }
    if (ret != KM_ERROR_OK) {
        goto end;
    }

    /* Copy output buffer. */
    if ((output != NULL) && (local_output != NULL)) {
        memcpy(output, local_output, output_length);
    }

    pTci->message.finish.output.data_length = output_length;

end:
    /* Always clear all session data. Whether it completes successfully or
       returns an error, this method finalizes the operation and invalidates
       the provided operation handle. */
    clear_session(sessionIdx);

    tlApiFree(local_params);
    tlApiFree(local_signature);
    tlApiFree(local_output);

    return ret;
}

keymaster_error_t handle_Abort( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    int sessionIdx = -1;
    uint64_t nwd_session_id = 0;

    CHECK_NOT_NULL(pTci);

    /* Locate session. */
    nwd_session_id = pTci->message.finish.handle;
    CHECK_RESULT_OK(get_session_idx(&sessionIdx, nwd_session_id));

    if (session_handles[sessionIdx].cr_session_handle != CR_SID_INVALID) {
        (void)tlApiCrAbort(session_handles[sessionIdx].cr_session_handle);
    }

    /* Clear all session data. */
    clear_session(sessionIdx);

end:
    return ret;
}

keymaster_error_t handle_GetKeyInfo( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t *key_blob = NULL;
    uint32_t key_blob_length = 0;
    uint8_t *local_key_blob = NULL;
    uint8_t *key_material = NULL;
    uint32_t key_material_len = 0;
    uint32_t params_len = 0;
    uint8_t *key_data = NULL;

    CHECK_NOT_NULL(pTci);

    key_blob = (uint8_t*)pTci->message.get_key_info.key_blob.data;
    key_blob_length = pTci->message.get_key_info.key_blob.data_length;

    CHECK_RESULT_OK(nwd_buf_ok(key_blob, key_blob_length));

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        IS_VALID_BLOB_SIZE(key_blob_length));

    /* Make local copy of buffer. */
    CHECK_RESULT_OK(tlkm_alloc(&local_key_blob, key_blob_length));
    memcpy(local_key_blob, key_blob, key_blob_length);

    /* Decrypt blob */
    key_material_len = DATA_SIZE(key_blob_length);
    CHECK_RESULT_OK(tlkm_alloc(&key_material, key_material_len));
    CHECK_RESULT_OK(decrypt_blob(local_key_blob, key_blob_length,
        key_material, &key_material_len));

    /* Extract key type and size */
    params_len = get_u32(key_material);
    key_data = key_material + 4 + params_len;
    pTci->message.get_key_info.key_type = (keymaster_algorithm_t)get_u32(key_data);
    pTci->message.get_key_info.key_size = get_u32(key_data + 4);

end:
    tlApiFree(local_key_blob);
    tlApiFree(key_material);

    return ret;
}

keymaster_error_t handle_GetOperationInfo( tci_t* pTci )
{
    keymaster_error_t ret = KM_ERROR_OK;
    int sessionIdx = -1;
    uint32_t data_length = 0;
    keymaster_purpose_t purpose;
    keymaster_algorithm_t algorithm;
    uint32_t key_size;

    CHECK_NOT_NULL(pTci);

    /* Locate session. */
    CHECK_RESULT_OK(get_session_idx(&sessionIdx,
        pTci->message.get_operation_info.handle));

    purpose = session_handles[sessionIdx].purpose;
    algorithm = session_handles[sessionIdx].algorithm;
    key_size = session_handles[sessionIdx].key_size;

    // We could do better here, but this is good enough.
    switch(algorithm) {
        case KM_ALGORITHM_RSA:
            /* output needed for encrypt, decrypt and sign */
            if (purpose != KM_PURPOSE_VERIFY) {
                data_length = BITS_TO_BYTES(key_size);
            }
            break;
        case KM_ALGORITHM_AES:
            if ((aesOpCtxs[sessionIdx].algorithm == AES_MODE_GCM) &&
                (purpose == KM_PURPOSE_ENCRYPT))
            { // output needed for GCM encrypt
                data_length = BITS_TO_BYTES(
                    aesOpCtxs[sessionIdx].opModeCtx.gcmCtx.tagLen);
            } else if (session_handles[sessionIdx].pkcs7_padding)
            { // output needed for PKCS7 padding with ECB or CBC
                data_length = 16;
            }
            break;
        case KM_ALGORITHM_HMAC:
            /* output needed for sign */
            if (purpose == KM_PURPOSE_SIGN) {
                data_length = session_handles[sessionIdx].taglen;
            }
            break;
        case KM_ALGORITHM_EC:
            /* output needed for sign */
            if (purpose == KM_PURPOSE_SIGN) {
                data_length = 2 * BITS_TO_BYTES(key_size) + 9;
            }
            break;
        default:
            break;
    }

    pTci->message.get_operation_info.algorithm = algorithm;
    pTci->message.get_operation_info.data_length = data_length;

end:
    return ret;
}
