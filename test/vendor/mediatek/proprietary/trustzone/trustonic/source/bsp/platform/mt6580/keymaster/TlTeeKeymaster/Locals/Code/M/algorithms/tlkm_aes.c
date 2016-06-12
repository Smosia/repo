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
#include "tci.h"
#include "keymaster_ta_defs.h"
#include "keymaster_util.h"
#include "tlkm_aes.h"

static keymaster_error_t aes_check_key_params(
    const uint8_t *params,
    uint32_t params_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t min_mac_length;

    if (test_enumerated_tag_data(params, params_len,
        KM_TAG_BLOCK_MODE, KM_MODE_GCM))
    { // GCM mode supported: params should contain unique KM_TAG_MIN_MAC_LENGTH
        CHECK_TRUE(KM_ERROR_MISSING_MIN_MAC_LENGTH,
            KM_ERROR_OK == get_integer_tag_data(params, params_len,
                KM_TAG_MIN_MAC_LENGTH, &min_mac_length));
        CHECK_TRUE(KM_ERROR_UNSUPPORTED_MIN_MAC_LENGTH,
            ((min_mac_length >= 96) && (min_mac_length <= 128)));
    }

end:
    return ret;
}

keymaster_error_t aes_generatekey(
    uint32_t                key_size,
    const uint8_t*          params,
    uint32_t                params_length,
    const void*             own_params,
    size_t                  own_params_length,
    uint8_t*                key_material,
    uint32_t*               key_material_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    tlApiSymKey_t aes_key = {0};
    const uint32_t bytelen = BITS_TO_BYTES(key_size);
    uint8_t *pos = NULL;
    uint32_t extended_params_length = params_length + own_params_length;
    uint32_t key_material_buflen;

    CHECK_NOT_NULL(key_material);
    CHECK_NOT_NULL(key_material_len);

    CHECK_RESULT_OK(aes_check_key_params(params, params_length));

    key_material_buflen = *key_material_len; // length of buffer

    CHECK_TRUE(KM_ERROR_UNSUPPORTED_KEY_SIZE,
        (bytelen == 16) || (bytelen == 24) || (bytelen == 32));

    CHECK_RESULT_OK(tlkm_alloc(&aes_key.key, bytelen));
    aes_key.len = bytelen;

    CHECK_TLAPI_OK(tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM, aes_key.key,
        (size_t*)&aes_key.len));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        aes_key.len == bytelen);

    /* AES keys will be wrapped as:
     * |params_len|params|type|size|keydata|
     */
    *key_material_len = 4 + extended_params_length + 4 + 4 + bytelen;
    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *key_material_len <= key_material_buflen);

    pos = key_material;
    CHECK_RESULT_OK(make_key_material_params(&pos, params, params_length,
        OWN_PARAMS_NB, own_params, own_params_length));

    set_u32_increment_pos(&pos, KM_ALGORITHM_AES);
    set_u32_increment_pos(&pos, key_size);
    set_data_increment_pos(&pos, aes_key.key, bytelen);

end:
    tlApiFree(aes_key.key);
    return ret;
}

keymaster_error_t aes_begin(
    keymaster_purpose_t purpose,
    const uint8_t *params, uint32_t params_length,
    uint32_t key_size,
    const uint8_t *core_key_data, uint32_t core_key_data_len,
    keymaster_block_mode_t mode,
    uint32_t min_mac_length,
    bool caller_nonce,
    bool *return_nonce,
    uint8_t *nonce,
    uint32_t *nonce_len,
    session_handle_t *session,
    aesOpCtx_t *aesOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t algorithm;
    bool encrypt;
    uint8_t iv_bytes[AES_IV_SIZE];
    keymaster_blob_t iv = {0};
    tlApiSymKey_t key = {0};
    uint32_t taglen = 0;
    keymaster_error_t ret1;
    uint32_t required_nonce_len;

    CHECK_NOT_NULL(return_nonce);
    CHECK_NOT_NULL(session);
    CHECK_NOT_NULL(aesOpCtx);

    if (mode == KM_MODE_ECB) {
        algorithm = AES_MODE_ECB;
    } else if (mode == KM_MODE_CBC) {
        algorithm = AES_MODE_CBC;
    } else if (mode == KM_MODE_CTR) {
        algorithm = AES_MODE_CTR;
    } else if (mode == KM_MODE_GCM) {
        algorithm = AES_MODE_GCM;
    } else {
        return KM_ERROR_UNSUPPORTED_BLOCK_MODE;
    }

    /* Encrypt or decrypt? */
    if (purpose == KM_PURPOSE_ENCRYPT) {
        encrypt = true;
    } else if (purpose == KM_PURPOSE_DECRYPT) {
        encrypt = false;
    } else {
        return KM_ERROR_INCOMPATIBLE_PURPOSE;
    }

    /* IV, counter or nonce */
    *return_nonce = false;
    if (algorithm != AES_MODE_ECB) {
        required_nonce_len = (algorithm == AES_MODE_GCM) ? 12 : 16;
        ret1 = get_blob_tag_data(params, params_length, KM_TAG_NONCE, &iv);
        if (ret1 == KM_ERROR_OK) { // caller provided nonce
            CHECK_TRUE(KM_ERROR_CALLER_NONCE_PROHIBITED,
                (purpose == KM_PURPOSE_DECRYPT) || caller_nonce);
            CHECK_TRUE(KM_ERROR_INVALID_NONCE,
                (iv.data_length == required_nonce_len));
        } else if (ret1 == KM_ERROR_INVALID_TAG) { // generate nonce
            CHECK_TRUE(KM_ERROR_MISSING_NONCE,
                purpose == KM_PURPOSE_ENCRYPT);
            iv.data = iv_bytes;
            iv.data_length = required_nonce_len;
            CHECK_TLAPI_OK(tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM,
                iv_bytes, &iv.data_length));
            CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
                iv.data_length == required_nonce_len);
            *return_nonce = true;
            CHECK_NOT_NULL(nonce);
            CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                *nonce_len >= required_nonce_len);
            memcpy(nonce, iv_bytes, required_nonce_len);
            *nonce_len = required_nonce_len;
        } else { // caller provided more than one nonce
            ret = KM_ERROR_INVALID_ARGUMENT;
            goto end;
        }
    }

    /* Key */
    key.key = (uint8_t*)core_key_data;
    key.len = BITS_TO_BYTES(key_size);
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        key.len <= core_key_data_len);

    /* Tag length (for GCM) */
    if (algorithm == AES_MODE_GCM) {
        CHECK_RESULT_OK(get_integer_tag_data(params, params_length,
            KM_TAG_MAC_LENGTH, &taglen));
        CHECK_TRUE(KM_ERROR_INVALID_MAC_LENGTH,
            (taglen >= min_mac_length) && ((taglen == 96) || (taglen == 128)));
        session->taglen = taglen/8;
    }

    /* Populate context from parameters, */
    aesOpCtx->algorithm = algorithm;
    aesOpCtx->encrypt = encrypt;
    aesOpCtx->key = &key;

    /* Initialize operation. */
    switch (algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
        case AES_MODE_CTR:
            CHECK_TLAPI_OK(aesCipherInit(aesOpCtx, iv.data));
            break;
        case AES_MODE_GCM:
            CHECK_TLAPI_OK(aesAEInit(aesOpCtx, iv.data, iv.data_length,
                taglen/8, 0, 0));
            break;
        // no default (other cases impossible, see above)
    }

end:
    return ret;
}

keymaster_error_t aes_update(
    const uint8_t *params,
    uint32_t params_length,
    const uint8_t *input,
    uint32_t input_length,
    uint32_t *input_consumed,
    uint8_t *output,
    uint32_t *output_length,
    session_handle_t *session,
    aesOpCtx_t *aesOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t algorithm;
    keymaster_blob_t aad = {0};
    uint32_t taglen;
    uint32_t outlen;
    uint8_t *buf;
    uint32_t buffered_len;
    uint32_t free_buf_len;
    bool buffer_full_block;

    CHECK_NOT_NULL(input_consumed);
    *input_consumed = 0;
    CHECK_NOT_NULL(session);
    CHECK_NOT_NULL(aesOpCtx);
    CHECK_NOT_NULL(output_length);

    algorithm = aesOpCtx->algorithm;

    /* Double-check that mode and padding are consistent. */
    if (session->pkcs7_padding) {
        CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
            (algorithm == AES_MODE_ECB) || (algorithm == AES_MODE_CBC));
    }

    /* For AEAD, first update the AAD */
    if (algorithm == AES_MODE_GCM) {
        ret = get_blob_tag_data(params, params_length,
            KM_TAG_ASSOCIATED_DATA, &aad); // may be empty
        if ((ret == KM_ERROR_OK) && (aad.data_length != 0)) { // some AAD
            CHECK_NOT_NULL(aad.data);
            CHECK_TLAPI_OK(aesAEUpdateAAD(aesOpCtx, aad.data, aad.data_length));
        } else if (ret == KM_ERROR_INVALID_TAG) { // no AAD (OK)
            ret = KM_ERROR_OK;
        } else { // more than one AAD tag provided
            ret = KM_ERROR_INVALID_ARGUMENT;
            goto end;
        }
    }

    buf = session->buffered_input;
    buffered_len = session->buffered_input_len;
    free_buf_len = AES_BLOCK_SIZE - buffered_len; // space left in buffer

    switch (algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
            /* We need to buffer. Fpr PKCS7 decryption we must buffer up to
             * AES_BLOCK_SIZE bytes. Otherwise we must buffer up to
             * AES_BLOCK_SIZE-1 bytes.
             */
            buffer_full_block = (session->pkcs7_padding && !aesOpCtx->encrypt);
            if (input_length <= free_buf_len - (buffer_full_block ? 0 : 1)) {
                /* Just chuck everything into the buffer. */
                memcpy(buf + buffered_len, input, input_length);
                session->buffered_input_len += input_length;
                *output_length = 0;
            } else {
                uint32_t n_blocks = (input_length - free_buf_len - (buffer_full_block ? 1 : 0)) / AES_BLOCK_SIZE;
                uint32_t last_sublock_len = input_length - free_buf_len - AES_BLOCK_SIZE * n_blocks;
                CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                    *output_length >= AES_BLOCK_SIZE * (1 + n_blocks));
                /* 1. Fill up the buffer and process it. */
                memcpy(buf + buffered_len, input, free_buf_len);
                CHECK_TLAPI_OK(aesCipherUpdate(aesOpCtx, buf, AES_BLOCK_SIZE, output));
                /* 2. Process whole blocks from the remainder. */
                CHECK_TLAPI_OK(aesCipherUpdate(aesOpCtx,
                    input + free_buf_len, AES_BLOCK_SIZE * n_blocks, output + AES_BLOCK_SIZE));
                *output_length = AES_BLOCK_SIZE * (1 + n_blocks);
                /* 3. Chuck the rest into the buffer. */
                memcpy(buf, input + free_buf_len + AES_BLOCK_SIZE * n_blocks, last_sublock_len);
                /* 4. Set the buffer length. */
                session->buffered_input_len = last_sublock_len;
            }
            break;
        case AES_MODE_CTR:
            CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                *output_length >= input_length);
            CHECK_TLAPI_OK(aesCipherUpdate(aesOpCtx,
                input, input_length, output));
            *output_length = input_length;
            break;
        case AES_MODE_GCM:
            if (aesOpCtx->encrypt) {
                CHECK_TLAPI_OK(aesAEUpdate(aesOpCtx,
                    input, input_length, output, output_length));
            } else {
                /* It gets messy here. Since the tag is appended to the
                 * ciphertext, we don't know where it starts, and so must
                 * buffer (and not decrypt) the last taglen bytes of input.
                 */
                taglen = session->taglen;
                CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
                    taglen <= 16);
                if (buffered_len + input_length <= taglen) {
                    /* Just buffer all the input. */
                    *output_length = 0;
                    memcpy(buf + buffered_len, input, input_length);
                    session->buffered_input_len += input_length;
                } else if (input_length >= taglen) {
                    /* Process all the buffer and then all but taglen bytes
                     * of the input; copy the last taglen bytes into the
                     * buffer.
                     */
                    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                        *output_length >= buffered_len + input_length - taglen);

                    outlen = buffered_len;
                    CHECK_TLAPI_OK(aesAEUpdate(aesOpCtx,
                        buf, buffered_len, output, &outlen));

                    outlen = input_length - taglen;
                    CHECK_TLAPI_OK(aesAEUpdate(aesOpCtx,
                        input, input_length - taglen, output + buffered_len, &outlen));

                    *output_length = buffered_len + input_length - taglen;
                    memcpy(buf, input + input_length - taglen, taglen);
                    session->buffered_input_len = taglen;
                } else {
                    /* Process buflen + inplen - taglen bytes of buffer;
                     * move remaining bytes of buffer to start of buffer and
                     * append input to them.
                     */
                    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                        *output_length >= buffered_len + input_length - taglen);

                    outlen = buffered_len + input_length - taglen;
                    CHECK_TLAPI_OK(aesAEUpdate(aesOpCtx,
                        buf, buffered_len + input_length - taglen, output, &outlen));
                    *output_length = buffered_len + input_length - taglen;
                    memmove(buf, buf + buffered_len + input_length - taglen, taglen - input_length);
                    memcpy(buf + taglen - input_length, input, input_length);
                    session->buffered_input_len = taglen;
                }
            }
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_BLOCK_MODE;
            goto end;
    }

    *input_consumed = input_length;

end:
    return ret;
}

keymaster_error_t aes_finish(
    uint8_t *output,
    uint32_t *output_length,
    session_handle_t *session,
    aesOpCtx_t *aesOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t algorithm;
    uint8_t tag[16];
    uint32_t taglen;
    uint32_t siglen;
    const uint32_t outbuflen = *output_length;

    CHECK_NOT_NULL(session);
    CHECK_NOT_NULL(aesOpCtx);
    algorithm = aesOpCtx->algorithm;

    switch (algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
            if (session->pkcs7_padding) {
                if (aesOpCtx->encrypt) {
                    /* Pad the buffer and encrypt it. */
                    uint32_t n_padding_bytes = AES_BLOCK_SIZE - session->buffered_input_len;
                    memset(session->buffered_input + session->buffered_input_len, n_padding_bytes, n_padding_bytes);
                    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                        *output_length >= AES_BLOCK_SIZE);
                    CHECK_TLAPI_OK(aesCipherUpdate(aesOpCtx,
                        session->buffered_input, AES_BLOCK_SIZE, output));
                } else {
                    /* Decrypt the buffer and unpad it. */
                    uint8_t plain[AES_BLOCK_SIZE] = {0};
                    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                        session->buffered_input_len == AES_BLOCK_SIZE);
                    CHECK_TLAPI_OK(aesCipherUpdate(aesOpCtx,
                        session->buffered_input, AES_BLOCK_SIZE, plain));
                    uint32_t n_padding_bytes = plain[AES_BLOCK_SIZE - 1];
                    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
                        (n_padding_bytes >= 1) && (n_padding_bytes <= AES_BLOCK_SIZE));
                    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
                        *output_length >= AES_BLOCK_SIZE - n_padding_bytes);
                    for (uint8_t i = 2; i <= n_padding_bytes; i++) {
                        CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
                            plain[AES_BLOCK_SIZE - i] == n_padding_bytes);
                    }
                    memcpy(output, plain, AES_BLOCK_SIZE - n_padding_bytes);
                    *output_length = AES_BLOCK_SIZE - n_padding_bytes;
                }
            } else {
                CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                    session->buffered_input_len == 0);
            }
            break;
        case AES_MODE_CTR:
            /* Nothing to do. */
            break;
        case AES_MODE_GCM:
            taglen = session->taglen;
            CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
                taglen <= 16);
            if (aesOpCtx->encrypt) {
                CHECK_NOT_NULL(output);
                CHECK_NOT_NULL(output_length);
                siglen = taglen;
                CHECK_TLAPI_OK(aesAEEncryptFinal(aesOpCtx,
                    NULL, 0, output, output_length, tag, &siglen));
                CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
                    siglen == taglen);
                /* Append tag to output. */
                CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
                    *output_length + taglen <= outbuflen);
                memcpy(output + *output_length, tag, taglen);
                *output_length += taglen;
            } else {
                /* We should have tag already in buffered input. */
                CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
                    session->buffered_input_len == taglen);
                CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
                    TLAPI_OK == aesAEDecryptFinal(aesOpCtx, NULL, 0,
                    output, output_length, session->buffered_input, taglen));
            }
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_BLOCK_MODE;
            goto end;
    }

end:
    return ret;
}
