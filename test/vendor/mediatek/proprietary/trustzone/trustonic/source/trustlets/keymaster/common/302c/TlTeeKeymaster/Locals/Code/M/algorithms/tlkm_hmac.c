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
#include "keymaster_util.h"
#include "tlkm_hmac.h"
#include "tlkm_hash.h"
#include "tlCommon.h"

static keymaster_error_t hmac_check_key_params(
    const uint8_t *params,
    uint32_t params_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    keymaster_digest_t digest;
    uint32_t min_mac_length;

    /* Params should contain a unique KM_TAG_DIGEST != KM_TAG_NONE. */
    CHECK_TRUE(KM_ERROR_UNSUPPORTED_DIGEST,
        (KM_ERROR_OK == get_enumerated_tag_data(params, params_len,
            KM_TAG_DIGEST, (uint32_t*)&digest)) && (digest != KM_DIGEST_NONE));

    /* Params should contain a unique KM_TAG_MIN_MAC_LENGTH. */
    CHECK_TRUE(KM_ERROR_MISSING_MIN_MAC_LENGTH,
        KM_ERROR_OK == get_integer_tag_data(params, params_len,
            KM_TAG_MIN_MAC_LENGTH, &min_mac_length));

    CHECK_TRUE(KM_ERROR_UNSUPPORTED_MIN_MAC_LENGTH,
        ((min_mac_length % 8 == 0) &&
         (min_mac_length >= 64) &&
         (min_mac_length <= 8 * digest_length(digest))));

end:
    return ret;
}

keymaster_error_t hmac_generatekey(
    uint32_t                key_size,
    const uint8_t*          params,
    uint32_t                params_length,
    const void*             own_params,
    size_t                  own_params_length,
    uint8_t*                key_material,
    uint32_t*               key_material_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    tlApiSymKey_t hmac_key = {0};
    const uint32_t bytelen = BITS_TO_BYTES(key_size);
    uint8_t *pos = NULL;
    const uint32_t extended_params_length = params_length + own_params_length;
    uint32_t key_material_buflen;

    CHECK_NOT_NULL(key_material);
    CHECK_NOT_NULL(key_material_len);

    CHECK_RESULT_OK(hmac_check_key_params(params, params_length));

    key_material_buflen = *key_material_len; // length of buffer

    CHECK_RESULT_OK(tlkm_alloc(&hmac_key.key, bytelen));
    hmac_key.len = bytelen;

    CHECK_TLAPI_OK(tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM,
        hmac_key.key, (size_t*)&hmac_key.len));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        hmac_key.len == bytelen);

    /* HMAC keys will be wrapped as:
     * |params_len|params|type|size|keydata|
     */
    *key_material_len = 4 + extended_params_length + 4 + 4 + bytelen;
    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *key_material_len <= key_material_buflen);

    pos = key_material;
    CHECK_RESULT_OK(make_key_material_params(&pos, params, params_length,
        OWN_PARAMS_NB, own_params, own_params_length));

    set_u32_increment_pos(&pos, KM_ALGORITHM_HMAC);
    set_u32_increment_pos(&pos, key_size);
    set_data_increment_pos(&pos, hmac_key.key, bytelen);

end:
    tlApiFree(hmac_key.key);
    return ret;
}

static keymaster_error_t make_padding(
    keymaster_digest_t digest,
    uint8_t *pad,
    uint8_t xor_val,
    const uint8_t *key,
    uint32_t key_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t blocksize = digest_blocksize(digest);
    uint32_t digest_len = digest_length(digest);
    uint32_t offset;

    assert(digest_len <= blocksize);

    CHECK_NOT_NULL(pad);
    CHECK_NOT_NULL(key);

    /* 1. Start filling padding with key or hash(key). */
    if (key_len > blocksize) { // pad[:digest_len] <-- hash(key)
        hashOpCtx_t hashOpCtx;
        size_t len = blocksize;
        CHECK_RESULT_OK(digest_begin(digest, &hashOpCtx));
        CHECK_RESULT_OK(digest_update(&hashOpCtx, key, key_len));
        CHECK_RESULT_OK(digest_finish(&hashOpCtx, pad, &len));
        CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
            len == digest_len);
        offset = digest_len;
    } else { // pad[:key_len] <-- key
        memcpy(pad, key, key_len);
        offset = key_len;
    }

    /* 2. Fill the remainder with zeros. */
    for (uint32_t i = offset; i < blocksize; i++) {
        pad[i] = 0;
    }

    /* 3. XOR the bytes. */
    for (uint32_t i = 0; i < blocksize; i++) {
        pad[i] ^= xor_val;
    }

end:
    return ret;
}

keymaster_error_t hmac_begin(
    keymaster_purpose_t purpose,
    const uint8_t *params, uint32_t params_length,
    uint32_t key_size,
    const uint8_t *core_key_data, uint32_t core_key_data_len,
    keymaster_digest_t digest,
    uint32_t min_mac_length,
    uint32_t *tag_len,
    hmacOpCtx_t *hmacOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t i_key_pad[KM_MAX_DIGEST_BLOCKSIZE];
    uint32_t digest_len = digest_length(digest);
    uint32_t tag_len_bits;

    CHECK_NOT_NULL(tag_len);
    CHECK_NOT_NULL(hmacOpCtx);

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        BITS_TO_BYTES(key_size) == core_key_data_len);

    /* Set hmacOpCtx->o_key_pad and i_key_pad. */
    CHECK_RESULT_OK(make_padding(digest, hmacOpCtx->o_key_pad, 0x5c, core_key_data, core_key_data_len));
    CHECK_RESULT_OK(make_padding(digest, i_key_pad, 0x36, core_key_data, core_key_data_len));

    /* Initialize the main hash. */
    CHECK_RESULT_OK(digest_begin(digest, &hmacOpCtx->hashOpCtx));

    /* Feed in i_key_pad. */
    CHECK_RESULT_OK(digest_update(&hmacOpCtx->hashOpCtx, i_key_pad, digest_blocksize(digest)));

    /* Find tag length */
    if (purpose == KM_PURPOSE_SIGN) {
        CHECK_RESULT_OK(get_integer_tag_data(params, params_length,
            KM_TAG_MAC_LENGTH, &tag_len_bits));
    } else {
        tag_len_bits = get_integer_tag_data_with_default(params, params_length,
            KM_TAG_MAC_LENGTH, 8 * digest_len);
    }

    CHECK_TRUE(KM_ERROR_UNSUPPORTED_MAC_LENGTH,
        (tag_len_bits <= 8 * digest_len) &&
        (tag_len_bits % 8 == 0) &&
        (tag_len_bits >= 64));
    CHECK_TRUE(KM_ERROR_INVALID_MAC_LENGTH,
        tag_len_bits >= min_mac_length);
    *tag_len = tag_len_bits / 8;
end:
    return ret;
}


keymaster_error_t hmac_update(
    const uint8_t *input,
    uint32_t input_length,
    uint32_t *input_consumed,
    hmacOpCtx_t *hmacOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;

    CHECK_NOT_NULL(input_consumed);
    *input_consumed = 0;
    CHECK_NOT_NULL(hmacOpCtx);

    CHECK_RESULT_OK(digest_update(&hmacOpCtx->hashOpCtx, input, input_length));
    *input_consumed = input_length;

end:
    return ret;
}

static keymaster_error_t hmac_finish(
    hmacOpCtx_t *hmacOpCtx,
    uint8_t *output,
    size_t *output_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t inner[KM_MAX_DIGEST_LEN];
    size_t inner_len = sizeof(inner);
    keymaster_digest_t digest = hmacOpCtx->hashOpCtx.algorithm;
    hashOpCtx_t ctx;

    assert(output != NULL);
    assert(output_len != NULL);

    CHECK_NOT_NULL(hmacOpCtx);

    /* 1. Finish the inner hash. */
    CHECK_RESULT_OK(digest_finish(&hmacOpCtx->hashOpCtx, inner, &inner_len));

    /* 2. Compute the outer hash. */
    CHECK_RESULT_OK(digest_begin(digest, &ctx));
    CHECK_RESULT_OK(digest_update(&ctx, hmacOpCtx->o_key_pad, digest_blocksize(digest)));
    CHECK_RESULT_OK(digest_update(&ctx, inner, inner_len));
    CHECK_RESULT_OK(digest_finish(&ctx, output, output_len));

end:
    return ret;
}

keymaster_error_t hmac_finish_sign(
    uint8_t *signature,
    uint32_t *signature_length,
    uint32_t tag_len,
    hmacOpCtx_t *hmacOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t outer[KM_MAX_DIGEST_LEN] = {0};
    size_t outer_len = sizeof(outer);

    CHECK_NOT_NULL(signature);
    CHECK_NOT_NULL(signature_length);
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR, /* TLC should have ensured this */
        tag_len <= *signature_length);

    CHECK_RESULT_OK(hmac_finish(hmacOpCtx, outer, &outer_len));

    /* Truncate if necessary. */
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR, /* shouldn't happen */
        tag_len <= outer_len);
    memcpy(signature, outer, tag_len);
    *signature_length = tag_len;

end:
    return ret;
}

keymaster_error_t hmac_finish_verify(
    const uint8_t *signature,
    uint32_t signature_length,
    uint32_t tag_len,
    uint32_t min_tag_len,
    hmacOpCtx_t *hmacOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t outer[KM_MAX_DIGEST_LEN] = {0};
    size_t outer_len = sizeof(outer);

    CHECK_NOT_NULL(signature);

    CHECK_TRUE(KM_ERROR_INVALID_MAC_LENGTH,
        (signature_length >= min_tag_len) && (signature_length <= tag_len));

    CHECK_RESULT_OK(hmac_finish(hmacOpCtx, outer, &outer_len));

    /* Compare. */
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR, /* shouldn't happen */
        tag_len <= outer_len);
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        memcmp(signature, outer, signature_length) == 0);

end:
    if ((ret != KM_ERROR_OK) && (ret != KM_ERROR_INVALID_MAC_LENGTH)) {
        ret = KM_ERROR_VERIFICATION_FAILED;
    }

    return ret;
}
