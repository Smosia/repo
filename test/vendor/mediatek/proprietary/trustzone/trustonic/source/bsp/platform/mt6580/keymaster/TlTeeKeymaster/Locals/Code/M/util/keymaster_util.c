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
#include "tci.h"
#include "keymaster_ta_defs.h"
#include "aes.h"
#include "keymaster_util.h"
#include "tlkm_device.h"

/**
 * Endianness of platform, initialized in \p tlMain().
 */
bool am_big_endian;

keymaster_error_t tl_to_km_err(
    tlApiResult_t tret)
{
    switch (tret) {
        case TLAPI_OK:
            return KM_ERROR_OK;
        case E_TLAPI_NOT_IMPLEMENTED:
            return KM_ERROR_UNIMPLEMENTED;
        case E_TLAPI_INVALID_INPUT:
            return KM_ERROR_INVALID_ARGUMENT;
        case E_TLAPI_BUFFER_TOO_SMALL:
        case E_TLAPI_CR_WRONG_OUTPUT_SIZE:
            return KM_ERROR_INSUFFICIENT_BUFFER_SPACE;
        case E_TLAPI_NULL_POINTER:
            return KM_ERROR_UNEXPECTED_NULL_POINTER;
        case E_TLAPI_OUT_OF_MEMORY:
            return KM_ERROR_MEMORY_ALLOCATION_FAILED;
#if TBASE_API_LEVEL >= 7
        case E_TLAPI_INVALID_SIGNATURE:
        case E_TLAPI_INVALID_MAC:
            return KM_ERROR_VERIFICATION_FAILED;
#endif
        case E_TLAPI_CR_ALGORITHM_NOT_AVAILABLE:
        case E_TLAPI_CR_ALGORITHM_NOT_SUPPORTED:
            return KM_ERROR_UNSUPPORTED_ALGORITHM;
        case E_TLAPI_CR_WRONG_INPUT_SIZE:
            return KM_ERROR_INVALID_INPUT_LENGTH;
        case E_TLAPI_CR_OUT_OF_RESOURCES:
            /* crOpenSession() returns this if there aren't enough slots */
            return KM_ERROR_TOO_MANY_OPERATIONS;
        default:
            return KM_ERROR_UNKNOWN_ERROR;
    }
}

static keymaster_tag_type_t keymaster_tag_get_type(
    keymaster_tag_t tag)
{
    return (keymaster_tag_type_t)(tag & 0xF0000000);
}

uint32_t param_length(
    const uint8_t *params,
    uint32_t params_len,
    uint32_t offset,
    keymaster_tag_t *tag)
{
    if ((params == NULL) || (tag == NULL)) {
        return 0;
    }
    if (offset + 4 > params_len) {
        return 0;
    }
    *tag = (keymaster_tag_t)get_u32(params + offset);
    uint32_t param_len;
    switch (keymaster_tag_get_type(*tag)) {
        case KM_ENUM:
        case KM_ENUM_REP:
        case KM_UINT:
        case KM_UINT_REP:
        case KM_BOOL:
            param_len = 4;
            break;
        case KM_ULONG:
        case KM_DATE:
        case KM_ULONG_REP:
            param_len = 8;
            break;
        case KM_BIGNUM:
        case KM_BYTES:
            if (offset + 4 + 4 > params_len) {
                return 0;
            }
            param_len = 4 + get_u32(params + offset + 4);
            break;
        default:
            return 0;
    }
    if (offset + 4 + param_len > params_len) {
        return 0;
    } else {
        return param_len;
    }
}

bool is_repeatable(
    keymaster_tag_t tag)
{
    switch (keymaster_tag_get_type(tag)) {
        case KM_ENUM:
        case KM_UINT:
        case KM_BOOL:
        case KM_ULONG:
        case KM_DATE:
        case KM_BIGNUM:
        case KM_BYTES:
            return false;
        case KM_ENUM_REP:
        case KM_UINT_REP:
        case KM_ULONG_REP:
            return true;
        default:
            return false;
    }
}

/**
 * Find position of a serialized parameter in a buffer.
 *
 * This function accepts both repeatable and non-repeatable tags.
 *
 * @param params serialized parameters
 * @param params_length length of \p params
 * @param tag_to_search tag to search for
 * @param check_unique true - uniqueness must be checked, false - find first occurence
 *
 * @return pointer to relevant section of buffer
 * @retval KM_ERROR_OK tag found and unique
 * @retval KM_ERROR_INVALID_TAG tag not found
 * @retval KM_ERROR_UNKNOWN_ERROR tag repeats, or other parsing error
 */
static keymaster_error_t find_param(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag_to_search,
    const uint8_t **pos,
    bool check_unique)
{
    if ((params == NULL) || (params_length < 4) || (pos == NULL)) {
        return KM_ERROR_UNKNOWN_ERROR;
    }

    uint32_t n_params = get_u32(params);

    size_t offset = 4;
    bool found = false;
    *pos = NULL;
    for (uint32_t i = 0; i < n_params; i++) {
        if (params_length < offset + 4) {
            return KM_ERROR_UNKNOWN_ERROR;
        }
        keymaster_tag_t this_tag = KM_TAG_INVALID;
        uint32_t param_len = param_length(params, params_length, offset, &this_tag);
        if ((param_len == 0) || (params_length < offset + 4 + param_len)) {
            return KM_ERROR_UNKNOWN_ERROR;
        }
        if (tag_to_search == this_tag) {
            if (found) { // already found, so not unique: bail out
                return KM_ERROR_UNKNOWN_ERROR;
            }
            found = true;
            *pos = params + offset;
            if(!check_unique)
                return KM_ERROR_OK;
        }
        offset += 4 + param_len; // tag + value
    }
    return found ? KM_ERROR_OK : KM_ERROR_INVALID_TAG;
}

/**
 * Retrieve the unique value of a parameter.
 *
 * @param params serialized parameters
 * @param params_length length of \p params
 * @param tag_to_search tag to search for
 * @param[out] param parameter value
 *
 * @retval KM_ERROR_OK unique value found
 * @retval KM_ERROR_INVALID_TAG tag not found
 * @retval KM_ERROR_UNKNOWN_ERROR tag not unique, or other parsing error
 */
static keymaster_error_t get_tag_data(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag_to_search,
    keymaster_key_param_t *param)
{
    const uint8_t *param_data;
    keymaster_error_t ret = find_param(params, params_length, tag_to_search, &param_data, true);
    if (ret != KM_ERROR_OK) { // not present or not unique
        return ret;
    }

    param_data += 4; // skip to the value

    /* reset parameter */
    memset(param, 0x0, sizeof(keymaster_key_param_t));

    param->tag = tag_to_search;

    switch(keymaster_tag_get_type(tag_to_search))
    {
        case KM_ENUM:
        case KM_ENUM_REP:
            param->enumerated = get_u32(param_data);
            break;
        case KM_UINT:
        case KM_UINT_REP:
            param->integer = get_u32(param_data);
            break;
        case KM_ULONG:
        case KM_ULONG_REP:
            param->long_integer = get_u64(param_data);
            break;
        case KM_DATE:
            param->date_time = get_u64(param_data);
            break;
        case KM_BOOL:
            param->boolean = (get_u32(param_data) != 0);
            break;
        case KM_BIGNUM:
        case KM_BYTES:
            param->blob.data = param_data + 4;
            param->blob.data_length = get_u32(param_data);
            break;
        default:
            return KM_ERROR_UNKNOWN_ERROR;
    }

    return KM_ERROR_OK;
}

keymaster_error_t get_enumerated_tag_data(
    const uint8_t*      params,
    uint32_t            params_length,
    keymaster_tag_t     tag,
    uint32_t            *value)
{
    if (value == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    keymaster_key_param_t key_param;
    keymaster_error_t ret = get_tag_data(params, params_length, tag, &key_param);
    if (ret == KM_ERROR_OK) {
        *value = key_param.enumerated;
    }
    return ret;
}

uint32_t get_enumerated_tag_data_with_default(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    uint32_t default_value)
{
    uint32_t value;
    if (KM_ERROR_OK != get_enumerated_tag_data(params, params_length, tag, &value)) {
        value = default_value;
    }
    return value;
}

keymaster_error_t get_boolean_tag_data(
    const uint8_t*      params,
    uint32_t            params_length,
    keymaster_tag_t     tag,
    bool*               value)
{
    if (value == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    keymaster_key_param_t key_param;
    keymaster_error_t ret = get_tag_data(params, params_length, tag, &key_param);
    if (ret == KM_ERROR_OK) {
        *value = key_param.boolean;
    }
    return ret;
}

bool get_boolean_tag_data_with_default(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    bool default_value)
{
    bool value;
    if (KM_ERROR_OK != get_boolean_tag_data(params, params_length, tag, &value)) {
        value = default_value;
    }
    return value;
}

keymaster_error_t get_integer_tag_data(
    const uint8_t*      params,
    uint32_t            params_length,
    keymaster_tag_t     tag,
    uint32_t*           value)
{
    if (value == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    keymaster_key_param_t key_param;
    keymaster_error_t ret = get_tag_data(params, params_length, tag, &key_param);
    if (ret == KM_ERROR_OK) {
        *value = key_param.integer;
    }
    return ret;
}

uint32_t get_integer_tag_data_with_default(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    uint32_t default_value)
{
    uint32_t value;
    if (KM_ERROR_OK != get_integer_tag_data(params, params_length, tag, &value)) {
        value = default_value;
    }
    return value;
}

keymaster_error_t get_long_integer_tag_data(
    const uint8_t*      params,
    uint32_t            params_length,
    keymaster_tag_t     tag,
    uint64_t*           value)
{
    if (value == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    keymaster_key_param_t key_param;
    keymaster_error_t ret = get_tag_data(params, params_length, tag, &key_param);
    if (ret == KM_ERROR_OK) {
        *value = key_param.long_integer;
    }
    return ret;
}

keymaster_error_t get_date_time_tag_data(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    uint64_t *value)
{
    if (value == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    keymaster_key_param_t key_param;
    keymaster_error_t ret = get_tag_data(params, params_length, tag, &key_param);
    if (ret == KM_ERROR_OK) {
        *value = key_param.date_time;
    }
    return ret;
}

keymaster_error_t get_blob_tag_data(
    const uint8_t*      params,
    uint32_t            params_length,
    keymaster_tag_t     tag,
    keymaster_blob_t*   value)
{
    if (value == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    keymaster_key_param_t key_param;
    keymaster_error_t ret = get_tag_data(params, params_length, tag, &key_param);
    if (ret == KM_ERROR_OK) {
        *value = key_param.blob;
    }
    return ret;
}

bool test_enumerated_tag_data(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    uint32_t value)
{
    if (keymaster_tag_get_type(tag) != KM_ENUM_REP) {
        return false;
    }
    if (params == NULL) {
        return false;
    }
    if (params_length < 4) {
        return false;
    }
    uint32_t n_params = get_u32(params);
    size_t offset = 4;
    for (uint32_t i = 0; i < n_params; i++) {
        if (params_length < offset + 4) {
            return false;
        }
        keymaster_tag_t this_tag = KM_TAG_INVALID;
        uint32_t param_len = param_length(params, params_length, offset, &this_tag);
        if (param_len == 0) {
            return false;
        }
        if (tag == this_tag) {
            offset += 4;
            if (params_length < offset + 4) {
                return false;
            }
            uint32_t this_value = get_u32(params + offset);
            offset += 4;
            if (value == this_value) {
                return true;
            }
        } else {
            offset += 4 + param_len;
        }
    }

    return false; // tag not found
}

bool test_optional_enumerated_tag_matches(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    uint32_t value)
{
    if (keymaster_tag_get_type(tag) != KM_ENUM_REP) {
        return false;
    }
    if (params == NULL) {
        return false;
    }
    if (params_length < 4) {
        return false;
    }
    uint32_t n_params = get_u32(params);
    size_t offset = 4;
    for (uint32_t i = 0; i < n_params; i++) {
        if (params_length < offset + 4) {
            return false;
        }
        keymaster_tag_t this_tag = KM_TAG_INVALID;
        uint32_t param_len = param_length(params, params_length, offset, &this_tag);
        if (param_len == 0) {
            return false;
        }
        if (tag == this_tag) {
            offset += 4;
            if (params_length < offset + 4) {
                return false;
            }
            uint32_t this_value = get_u32(params + offset);
            offset += 4;
            if (value != this_value) {
                return false; // conflicting value found
            }
        } else {
            offset += 4 + param_len;
        }
    }
    return true; // conflicting value not found
}

bool test_optional_blob_tag_matches(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    const keymaster_blob_t *value)
{
    if (keymaster_tag_get_type(tag) != KM_BYTES) {
        return false;
    }
    if (params == NULL) {
        return false;
    }
    if (value == NULL) {
        return false;
    }
    if (params_length < 4) {
        return false;
    }
    uint32_t n_params = get_u32(params);
    size_t offset = 4;
    for (uint32_t i = 0; i < n_params; i++) {
        if (params_length < offset + 4) {
            return false;
        }
        keymaster_tag_t this_tag = KM_TAG_INVALID;
        uint32_t param_len = param_length(params, params_length, offset, &this_tag);
        if (param_len == 0) {
            return false;
        }
        if (tag == this_tag) {
            offset += 4;
            if (params_length < offset + param_len) {
                return false;
            }
            if (param_len < 4) { // sanity check
                return false;
            }
            uint32_t data_length = get_u32(params + offset);
            offset += 4;
            if (param_len != 4 + data_length) { // sanity check
                return false;
            }
            bool match = ((data_length == value->data_length) &&
                (value->data != NULL) &&
                (memcmp(params + offset, value->data, data_length) == 0));
            offset += data_length;
            if (!match) {
                return false; // conflicting value found
            }
        } else {
            offset += 4 + param_len;
        }
    }
    return true; // conflicting value not found
}

bool contains_tag(
    const uint8_t *params,
    uint32_t params_length,
    keymaster_tag_t tag,
    bool unique)
{
    const uint8_t *unused_pos;
    return (KM_ERROR_OK == find_param(params, params_length, tag, &unused_pos, unique));
}

/* Parameters for AES-GCM encryption/decryption of key data. */
#define WRAPPING_KEY_LEN 16
#define WRAPPING_NONCE_LEN 16
#define WRAPPING_TAG_LEN 16

static uint8_t wrapping_key_data[WRAPPING_KEY_LEN] = {0};
static tlApiSymKey_t wrapping_key = {NULL, 0};

/**
 * On first call, derive the key used to encrypt and decrypt key blobs.
 *
 * The key \p wrapping_key is derived once only in the lifetime of the TA. It is
 * statically initialized to NULL; if it is NULL on entry to this function, the
 * key is derived; otherwise the function does nothing. If key derivation fails,
 * we ensure the key is set to NULL on exit.
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t get_wrapping_key()
{
    if (wrapping_key.key == NULL) {
        uint8_t state_data[KM_DEVICE_STATE_SIZE];
        keymaster_error_t ret = get_device_state(state_data);
        if (ret != KM_ERROR_OK) {
            return ret;
        }

        if (TLAPI_OK != tlApiDeriveKey(
            state_data, KM_DEVICE_STATE_SIZE,
            wrapping_key_data, WRAPPING_KEY_LEN,
            MC_SO_CONTEXT_TLT, MC_SO_LIFETIME_PERMANENT))
        {
            memset(wrapping_key_data, 0, WRAPPING_KEY_LEN);
            wrapping_key.key = NULL;
            wrapping_key.len = 0;
            return KM_ERROR_UNKNOWN_ERROR;
        }

        wrapping_key.key = wrapping_key_data;
        wrapping_key.len = WRAPPING_KEY_LEN;
    }

    return KM_ERROR_OK;
}

keymaster_error_t encrypt_data(
    const uint8_t *data,
    uint32_t data_len,
    uint8_t *blob,
    uint32_t *blob_len)
{
    if ((blob == NULL) || (blob_len == NULL)) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }

    // 1. derive key
    keymaster_error_t ret = get_wrapping_key();
    if (ret != KM_ERROR_OK) {
        return ret;
    }

    // 2. calculate length available for ciphertext
    uint8_t nonce[WRAPPING_NONCE_LEN] = {0};
    uint8_t tag[WRAPPING_TAG_LEN];
    if (*blob_len < WRAPPING_NONCE_LEN + WRAPPING_TAG_LEN) {
        return KM_ERROR_INSUFFICIENT_BUFFER_SPACE;
    }
    uint32_t ct_len = *blob_len - WRAPPING_NONCE_LEN - WRAPPING_TAG_LEN;

    // 3. generate random nonce
    size_t randomLen = WRAPPING_NONCE_LEN;
    if ((TLAPI_OK != tlApiRandomGenerateData(
        TLAPI_ALG_SECURE_RANDOM, nonce, &randomLen)) ||
        (randomLen != WRAPPING_NONCE_LEN))
    {
        return KM_ERROR_UNKNOWN_ERROR;
    }

    // 4. copy nonce to start of blob
    memcpy(blob, nonce, WRAPPING_NONCE_LEN);

    // 5. encrypt data
    aesOpCtx_t aesOpCtx = {0};
    aesOpCtx.algorithm = AES_MODE_GCM;
    aesOpCtx.key = &wrapping_key;
    aesOpCtx.encrypt = true;
    if (TLAPI_OK != aesAEInit(&aesOpCtx, nonce, WRAPPING_NONCE_LEN, WRAPPING_TAG_LEN, 0, 0)) {
        return KM_ERROR_UNKNOWN_ERROR;
    }
    uint32_t tagLen = WRAPPING_TAG_LEN;
    if ((TLAPI_OK != aesAEEncryptFinal(&aesOpCtx,
        data, data_len,
        blob + WRAPPING_NONCE_LEN, &ct_len,
        tag, &tagLen)) ||
        (tagLen != WRAPPING_TAG_LEN))
    {
        return KM_ERROR_UNKNOWN_ERROR;
    }

    // 6. copy tag to end of ciphertext
    assert(WRAPPING_NONCE_LEN + ct_len + WRAPPING_TAG_LEN <= *blob_len);
    memcpy(blob + WRAPPING_NONCE_LEN + ct_len, tag, WRAPPING_TAG_LEN);

    // 7. set blob_len
    *blob_len = WRAPPING_NONCE_LEN + ct_len + WRAPPING_TAG_LEN;

    return KM_ERROR_OK;
}

keymaster_error_t decrypt_blob(
    const uint8_t *blob,
    uint32_t blob_len,
    uint8_t *data,
    uint32_t *data_len)
{
    if (blob == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }

    // 1. derive key
    keymaster_error_t ret = get_wrapping_key();
    if (ret != KM_ERROR_OK) {
        return ret;
    }

    // 2. read nonce and tag
    uint8_t nonce[WRAPPING_NONCE_LEN];
    uint8_t tag[WRAPPING_TAG_LEN];
    if (blob_len < WRAPPING_NONCE_LEN + WRAPPING_TAG_LEN) {
        return KM_ERROR_INVALID_INPUT_LENGTH;
    }
    uint32_t ct_len = blob_len - WRAPPING_NONCE_LEN - WRAPPING_TAG_LEN;
    memcpy(nonce, blob, WRAPPING_NONCE_LEN);
    memcpy(tag, blob + WRAPPING_NONCE_LEN + ct_len, WRAPPING_TAG_LEN);

    // 3. decrypt data
    aesOpCtx_t aesOpCtx = {0};
    aesOpCtx.algorithm = AES_MODE_GCM;
    aesOpCtx.key = &wrapping_key;
    aesOpCtx.encrypt = false;
    if (TLAPI_OK != aesAEInit(&aesOpCtx, nonce, WRAPPING_NONCE_LEN, WRAPPING_TAG_LEN, 0, 0)) {
        return KM_ERROR_UNKNOWN_ERROR;
    }
    if (TLAPI_OK != aesAEDecryptFinal(&aesOpCtx,
        blob + WRAPPING_NONCE_LEN, ct_len,
        data, data_len,
        tag, WRAPPING_TAG_LEN))
    {
        return KM_ERROR_INVALID_KEY_BLOB;
    }

    return KM_ERROR_OK;
}

keymaster_error_t tlkm_alloc(
    uint8_t **a,
    uint32_t l)
{
    if (a == NULL) {
        return KM_ERROR_UNEXPECTED_NULL_POINTER;
    }
    *a = (uint8_t*)tlApiMalloc(l, 0);;
    if (*a == NULL) {
        return KM_ERROR_MEMORY_ALLOCATION_FAILED;
    }
    return KM_ERROR_OK;
}

keymaster_error_t make_key_material_params(
    uint8_t **pos,
    const uint8_t *params,
    uint32_t params_len,
    uint32_t n_own_params,
    const uint8_t *own_params,
    uint32_t own_params_len)
{
    if (params_len < 4) {
        return KM_ERROR_UNKNOWN_ERROR;
    }

    uint32_t extended_params_length = params_len + own_params_len;
    set_u32_increment_pos(pos, extended_params_length);
    uint32_t extended_params_count = get_u32(params) + n_own_params;
    set_u32_increment_pos(pos, extended_params_count);
    set_data_increment_pos(pos, params + 4, params_len - 4);
    set_data_increment_pos(pos, own_params, own_params_len);

    return KM_ERROR_OK;
}

uint32_t ntoh32(
    uint32_t a)
{
    if (am_big_endian) {
        return a;
    } else {
        uint32_t b = 0;
        for (int i = 0; i < 4; i++) {
            b |= ((a >> (8*i)) & 0xFF) << (8*(3-i));
        }
        return b;
    }
}

uint64_t ntoh64(
    uint64_t a)
{
    if (am_big_endian) {
        return a;
    } else {
        uint64_t b = 0;
        for (int i = 0; i < 8; i++) {
            b |= ((a >> (8*i)) & 0xFF) << (8*(7-i));
        }
        return b;
    }
}
