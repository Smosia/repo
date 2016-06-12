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

#include "tlkm_rsa.h"
#include "tlkm_rsa_pad.h"
#include "tlCommon.h"

#define KM_RSA_PQ_BYTELEN_MAX (KM_RSA_BYTELEN_MAX/2)
#define KM_RSA_PQ_BYTELEN_MIN (KM_RSA_BYTELEN_MIN/2)

// Returns the number of bytes needed to store the defined number of bits

/**
 * Write a 64-bit number to a buffer in big-endian format without leading zeros.
 *
 * @param[out] buf destination buffer
 * @param[out] len number of bytes written
 * @param a number to write
 *
 * @pre \buf has at least 8 bytes allocated
 */
static void set_u64_be(
    uint8_t *buf,
    uint32_t *len,
    uint64_t a)
{
    *len = 0;
    bool found = false;
    for (int i = 0; i < 8; i++) {
        uint8_t d = (a >> (8*(7-i))) & 0xFF; // i'th most significant digit
        if (!found && (d != 0)) {
            found = true; // found the first nonzero digit
        }
        if (found) {
            buf[*len] = d;
            (*len)++;
        }
    }
}

static bool isLongIntValid(
    const tlApiLongInt_t *longInt,
    uint32_t minLen,
    uint32_t maxLen)
{
    if (longInt->value == NULL)
        return (longInt->len == 0);
    return ((longInt->len >= minLen) && (longInt->len <= maxLen));
}

static bool isRsaKeyBuffersValid( const tlApiRsaKey_t *key) {
    if (!isLongIntValid(&key->exponent, 1, KM_RSA_BYTELEN_MAX))
        return false;
    if (key->exponent.value==NULL)
        return false;
    if ((key->exponent.value[key->exponent.len-1]&0x01)==0)
        return false; // public exponent is even

    if (!isLongIntValid(&key->modulus, KM_RSA_BYTELEN_MIN, KM_RSA_BYTELEN_MAX))
        return false;
    if ((key->modulus.value[key->modulus.len-1] & 0x01)==0)
        return false; // modulus is even
    if (!isLongIntValid(&key->privateExponent, 1, KM_RSA_BYTELEN_MAX))
        return false;
    if (!isLongIntValid(&key->privateCrtKey.P, KM_RSA_PQ_BYTELEN_MIN, KM_RSA_PQ_BYTELEN_MAX))
        return false;
    if (!isLongIntValid(&key->privateCrtKey.Q, KM_RSA_PQ_BYTELEN_MIN, KM_RSA_PQ_BYTELEN_MAX))
        return false;
    if (!isLongIntValid(&key->privateCrtKey.DP, KM_RSA_PQ_BYTELEN_MIN, KM_RSA_PQ_BYTELEN_MAX))
        return false;
    if (!isLongIntValid(&key->privateCrtKey.DQ, KM_RSA_PQ_BYTELEN_MIN, KM_RSA_PQ_BYTELEN_MAX))
        return false;
    if (!isLongIntValid(&key->privateCrtKey.Qinv, KM_RSA_PQ_BYTELEN_MIN, KM_RSA_PQ_BYTELEN_MAX))
        return false;

    if ((key->privateCrtKey.P.value != NULL) &&
        (key->privateCrtKey.Q.value != NULL) &&
        key->privateCrtKey.P.len == key->privateCrtKey.Q.len) {
        if (memcmp(key->privateCrtKey.P.value, key->privateCrtKey.Q.value, key->privateCrtKey.P.len) == 0)
            return false; // p and q are equal
    }

    return true;
}

/**
 * Prepend a message (digest) for signing with PKCS1 v1.5 padding and DIH.
 *
 * @param rsa_op_ctx RSA operation context
 * @param[out] EM buffer to contain full-length encoded message
 *
 * @pre \p EM must have size at least \p rsa_op_ctx->modulusByteLen
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t rsa_pkcs1_1_5_sig_pad(
    const rsa_op_ctx_t *rsa_op_ctx,
    uint8_t *EM)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t msg_len = 0;
    uint32_t offset = 0;
    uint32_t DIHLength = 0;
    size_t modulusByteLen;

    CHECK_NOT_NULL(rsa_op_ctx);
    CHECK_NOT_NULL(EM);

    msg_len = rsa_op_ctx->buf_cursor; // length of (digested) message
    modulusByteLen = rsa_op_ctx->modulusByteLen;

    /* Copy the digest to the right of the buffer */
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        modulusByteLen >= msg_len);
    offset = modulusByteLen - msg_len; // offset of digest in EM
    memcpy(EM + offset, rsa_op_ctx->buf, msg_len);

    /* Prepend the DIH before the digest */
    CHECK_RESULT_OK(encodeDIH(
        rsa_op_ctx->digest, EM, offset, &DIHLength));

    /* Prepend padding bytes before the DIH */
    CHECK_RESULT_OK(padPrivatePkcsV15(
        EM, DIHLength + msg_len, modulusByteLen));

end:
    return ret;
}

static keymaster_error_t rsa_finish_verify_pkcs1(
    const rsa_op_ctx_t *rsa_op_ctx,
    const uint8_t *EM)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t EM0[KM_RSA_BYTELEN_MAX];

    CHECK_NOT_NULL(rsa_op_ctx);
    CHECK_NOT_NULL(EM);

    /* Pad the message. */
    CHECK_RESULT_OK(rsa_pkcs1_1_5_sig_pad(rsa_op_ctx, EM0));

    /* Does it match? */
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        timeInvariant_memcmp(EM, EM0, rsa_op_ctx->modulusByteLen) == 0);

end:
    return ret;
}

static keymaster_error_t rsa_finish_cipher_with_oeap(
    uint8_t *output,
    uint32_t *output_length,
    tlApiCrSession_t cr_session,
    const rsa_op_ctx_t *rsa_op_ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t padded_output[KM_RSA_BYTELEN_MAX] = {0};
    size_t padded_output_maxlen;

    CHECK_NOT_NULL(rsa_op_ctx);

    if (rsa_op_ctx->purpose == KM_PURPOSE_DECRYPT) {
        padded_output_maxlen = rsa_op_ctx->modulusByteLen;

        /* Decrypt with raw RSA */
        CHECK_TLAPI_OK(tlApiCipherDoFinal(cr_session,
            rsa_op_ctx->buf, rsa_op_ctx->buf_cursor,
            padded_output, &padded_output_maxlen));

        /* Unpad plaintext */
        CHECK_RESULT_OK(unpadPkcs1Oaep(output, output_length, padded_output, rsa_op_ctx));
    } else { // KM_PURPOSE_ENCRYPT
        /* Pad plaintext */
        CHECK_RESULT_OK(padPkcs1Oaep(padded_output, rsa_op_ctx));

        /* Encrypt with raw RSA */
        CHECK_TLAPI_OK(tlApiCipherDoFinal(cr_session,
            padded_output, rsa_op_ctx->modulusByteLen,
            output, (size_t*)output_length));
    }

end:
    return ret;
}

static keymaster_error_t rsa_finish_cipher_with_pkcs1_1_5(
    uint8_t *output,
    uint32_t *output_length,
    tlApiCrSession_t cr_session,
    const rsa_op_ctx_t *rsa_op_ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t padded_output[KM_RSA_BYTELEN_MAX] = {0};
    size_t padded_output_maxlen;

    CHECK_NOT_NULL(rsa_op_ctx);

    if (rsa_op_ctx->purpose == KM_PURPOSE_DECRYPT) {
        padded_output_maxlen = rsa_op_ctx->modulusByteLen;

        /* Decrypt with raw RSA */
        CHECK_TLAPI_OK(tlApiCipherDoFinal(cr_session,
            rsa_op_ctx->buf, rsa_op_ctx->buf_cursor,
            padded_output, &padded_output_maxlen));

        /* Unpad plaintext */
        CHECK_RESULT_OK(unpadPkcs1_1_5(output, output_length, padded_output, rsa_op_ctx));
    } else { // KM_PURPOSE_ENCRYPT
        /* Pad plaintext */
        CHECK_RESULT_OK(padPkcs1_1_5(padded_output, rsa_op_ctx));

        /* Encrypt with raw RSA */
        CHECK_TLAPI_OK(tlApiCipherDoFinal(cr_session,
            padded_output, rsa_op_ctx->modulusByteLen,
            output, (size_t*)output_length));
    }

end:
    return ret;
}

keymaster_error_t rsa_generatekey(
    uint32_t            key_size,
    const uint8_t*      params,
    uint32_t            params_length,
    const void*         own_params,
    size_t              own_params_length,
    uint8_t*            key_material,
    uint32_t*           key_material_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    tlApiRsaKey_t rsa_key_pair = {{0},{0},{0}, {{0},{0},{0},{0},{0}}};
    tlApiKeyPair_t key_pair = {0};
    uint32_t modlen = BITS_TO_BYTES(key_size);
    uint32_t half_modlen = modlen/2;
    uint8_t *pos = NULL;
    uint64_t pub_exp = 0;
    uint32_t key_material_buflen; // length of buffer
    uint32_t extended_params_length = params_length + own_params_length;
    uint8_t *rsa_buffer = NULL;

    CHECK_NOT_NULL(key_material);
    CHECK_NOT_NULL(key_material_len);

    key_material_buflen = *key_material_len;

    /* Update key data buffers */
    CHECK_RESULT_OK(tlkm_alloc(&rsa_buffer, MAX_RSA_BUFFER_SIZE_IN_BYTES));

    rsa_key_pair.modulus.value = rsa_buffer;
    rsa_key_pair.exponent.value = rsa_key_pair.modulus.value + modlen;
    rsa_key_pair.privateExponent.value = rsa_key_pair.exponent.value + modlen;
    rsa_key_pair.privateCrtKey.P.value = rsa_key_pair.privateExponent.value + modlen;
    rsa_key_pair.privateCrtKey.Q.value = rsa_key_pair.privateCrtKey.P.value + half_modlen;
    rsa_key_pair.privateCrtKey.DP.value = rsa_key_pair.privateCrtKey.Q.value + half_modlen;
    rsa_key_pair.privateCrtKey.DQ.value = rsa_key_pair.privateCrtKey.DP.value + half_modlen;
    rsa_key_pair.privateCrtKey.Qinv.value = rsa_key_pair.privateCrtKey.DQ.value + half_modlen;

    rsa_key_pair.modulus.len = modlen;
    rsa_key_pair.privateExponent.len = modlen;
    rsa_key_pair.privateCrtKey.P.len = half_modlen;
    rsa_key_pair.privateCrtKey.Q.len = half_modlen;
    rsa_key_pair.privateCrtKey.DP.len = half_modlen;
    rsa_key_pair.privateCrtKey.DQ.len = half_modlen;
    rsa_key_pair.privateCrtKey.Qinv.len = half_modlen;

    /* Extract public exponent. If KM_TAG_RSA_PUBLIC_EXPONENT doesn't exist,
     * use 2^16+1 as default.
     */
    CHECK_RESULT_OK(get_long_integer_tag_data(params, params_length,
        KM_TAG_RSA_PUBLIC_EXPONENT, &pub_exp));

    /* Copy public key exponent data (big-endian without leading zeros) */
    CHECK_TRUE(KM_ERROR_INVALID_TAG,
        modlen >= 8);
    set_u64_be(rsa_key_pair.exponent.value, &rsa_key_pair.exponent.len, pub_exp);

    /* Generate RSA key pair */
    key_pair.rsaKeyPair = &rsa_key_pair;
    CHECK_TLAPI_OK(tlApiGenerateKeyPair(&key_pair, TLAPI_RSA, modlen));

    /* RSA keys will be wrapped as:
     * |params_len|params|type|size|rsa_metadata|keydata|
     */
    *key_material_len = 4 + extended_params_length + 4 + 4 + KM_RSA_METADATA_SIZE
        + rsa_key_pair.modulus.len
        + rsa_key_pair.exponent.len
        + rsa_key_pair.privateExponent.len
        + rsa_key_pair.privateCrtKey.P.len
        + rsa_key_pair.privateCrtKey.Q.len
        + rsa_key_pair.privateCrtKey.DP.len
        + rsa_key_pair.privateCrtKey.DQ.len
        + rsa_key_pair.privateCrtKey.Qinv.len;
    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *key_material_len <= key_material_buflen);

    pos = key_material;
    CHECK_RESULT_OK(make_key_material_params(&pos, params, params_length,
        OWN_PARAMS_NB, own_params, own_params_length));

    set_u32_increment_pos(&pos, KM_ALGORITHM_RSA);
    set_u32_increment_pos(&pos, key_size);

    // key metadata
    set_u32_increment_pos(&pos, key_size);
    set_u32_increment_pos(&pos, rsa_key_pair.modulus.len);
    set_u32_increment_pos(&pos, rsa_key_pair.exponent.len);
    set_u32_increment_pos(&pos, rsa_key_pair.privateExponent.len);
    set_u32_increment_pos(&pos, rsa_key_pair.privateCrtKey.P.len);
    set_u32_increment_pos(&pos, rsa_key_pair.privateCrtKey.Q.len);
    set_u32_increment_pos(&pos, rsa_key_pair.privateCrtKey.DP.len);
    set_u32_increment_pos(&pos, rsa_key_pair.privateCrtKey.DQ.len);
    set_u32_increment_pos(&pos, rsa_key_pair.privateCrtKey.Qinv.len);

    // raw key data
    set_data_increment_pos(&pos, rsa_key_pair.modulus.value, rsa_key_pair.modulus.len);
    set_data_increment_pos(&pos, rsa_key_pair.exponent.value, rsa_key_pair.exponent.len);
    set_data_increment_pos(&pos, rsa_key_pair.privateExponent.value, rsa_key_pair.privateExponent.len);
    set_data_increment_pos(&pos, rsa_key_pair.privateCrtKey.P.value, rsa_key_pair.privateCrtKey.P.len);
    set_data_increment_pos(&pos, rsa_key_pair.privateCrtKey.Q.value, rsa_key_pair.privateCrtKey.Q.len);
    set_data_increment_pos(&pos, rsa_key_pair.privateCrtKey.DP.value, rsa_key_pair.privateCrtKey.DP.len);
    set_data_increment_pos(&pos, rsa_key_pair.privateCrtKey.DQ.value, rsa_key_pair.privateCrtKey.DQ.len);
    set_data_increment_pos(&pos, rsa_key_pair.privateCrtKey.Qinv.value, rsa_key_pair.privateCrtKey.Qinv.len);

end:
    tlApiFree(rsa_buffer);

    return ret;
}

static keymaster_error_t rsa_check_mode(
    keymaster_purpose_t purpose,
    keymaster_padding_t padding,
    keymaster_digest_t digest)
{
#define CASE_ALL_DIGESTS \
    case KM_DIGEST_MD5: \
    case KM_DIGEST_SHA1: \
    case KM_DIGEST_SHA_2_224: \
    case KM_DIGEST_SHA_2_256: \
    case KM_DIGEST_SHA_2_384: \
    case KM_DIGEST_SHA_2_512

    switch (purpose) {
        case KM_PURPOSE_ENCRYPT:
        case KM_PURPOSE_DECRYPT:
            switch (padding) {
                case KM_PAD_NONE:
                case KM_PAD_RSA_PKCS1_1_5_ENCRYPT:
                    switch (digest) {
                        case KM_DIGEST_NONE:
                            return KM_ERROR_OK;
                        CASE_ALL_DIGESTS:
                        default:
                            return KM_ERROR_UNSUPPORTED_DIGEST;
                    }
                case KM_PAD_RSA_OAEP:
                    switch (digest) {
                        case KM_DIGEST_NONE:
                            return KM_ERROR_INCOMPATIBLE_DIGEST;
                        CASE_ALL_DIGESTS:
                            return KM_ERROR_OK;
                        default:
                            return KM_ERROR_UNSUPPORTED_DIGEST;
                    }
                case KM_PAD_RSA_PSS:
                case KM_PAD_RSA_PKCS1_1_5_SIGN:
                    return KM_ERROR_INCOMPATIBLE_PADDING_MODE;
                default:
                    return KM_ERROR_UNSUPPORTED_PADDING_MODE;
            }
        case KM_PURPOSE_SIGN:
        case KM_PURPOSE_VERIFY:
            switch (padding) {
                case KM_PAD_NONE:
                    switch (digest) {
                        case KM_DIGEST_NONE:
                            return KM_ERROR_OK;
                        CASE_ALL_DIGESTS:
                            return KM_ERROR_INCOMPATIBLE_DIGEST;
                        default:
                            return KM_ERROR_UNSUPPORTED_DIGEST;
                    }
                case KM_PAD_RSA_PSS:
                    switch (digest) {
                        case KM_DIGEST_NONE:
                            return KM_ERROR_INCOMPATIBLE_DIGEST;
                        CASE_ALL_DIGESTS:
                            return KM_ERROR_OK;
                        default:
                            return KM_ERROR_UNSUPPORTED_DIGEST;
                    }
                case KM_PAD_RSA_PKCS1_1_5_SIGN:
                    switch (digest) {
                        /* KM_DIGEST_NONE doesn't really make sense here, but we
                         * have to support it anyway, consistently with the
                         * implementation in openssl's RSA_private_encrypt().
                         */
                        case KM_DIGEST_NONE:
                        CASE_ALL_DIGESTS:
                            return KM_ERROR_OK;
                        default:
                            return KM_ERROR_UNSUPPORTED_DIGEST;
                    }
                case KM_PAD_RSA_OAEP:
                case KM_PAD_RSA_PKCS1_1_5_ENCRYPT:
                    return KM_ERROR_INCOMPATIBLE_PADDING_MODE;
                default:
                    return KM_ERROR_UNSUPPORTED_PADDING_MODE;
            }
        default:
            return KM_ERROR_UNSUPPORTED_PURPOSE;
    }

#undef CASE_ALL_DIGESTS
}

keymaster_error_t rsa_verify_key_params(const uint8_t* key_params,
                                        uint32_t key_params_len,
                                        keymaster_purpose_t purpose,
                                        keymaster_padding_t padding,
                                        keymaster_digest_t digest,
                                        bool must_check_digest)
{
    keymaster_error_t ret = KM_ERROR_OK;

    if ( (purpose == KM_PURPOSE_DECRYPT) || (purpose == KM_PURPOSE_SIGN) ) {
        /* Private-key operation: check purpose, digest and padding allowed. */
        CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PURPOSE,
            test_enumerated_tag_data(key_params, key_params_len,
                KM_TAG_PURPOSE, purpose));
        CHECK_TRUE(KM_ERROR_INCOMPATIBLE_PADDING_MODE,
            test_enumerated_tag_data(key_params, key_params_len,
                KM_TAG_PADDING, padding));

        if(must_check_digest)
        {
            // Check if user has requested specific digest or it is
            // KM_PAD_RSA_PKCS1_1_5_SIGN padding
            CHECK_TRUE(KM_ERROR_INCOMPATIBLE_DIGEST,
                test_enumerated_tag_data(key_params, key_params_len,
                    KM_TAG_DIGEST, digest));
        }
    }

end:
    return ret;
}

keymaster_error_t rsa_begin(
    keymaster_purpose_t purpose,
    uint32_t key_size,
    const uint8_t *core_key_data, uint32_t core_key_data_len,
    keymaster_padding_t padding,
    keymaster_digest_t digest,
    session_handle_t *session)
{
    keymaster_error_t ret = KM_ERROR_OK;
    const uint8_t *key_metadata;
    tlApiKey_t api_key = {0};
    tlApiRsaKey_t rsa_key_pair = {{0},{0},{0}, {{0},{0},{0},{0},{0}}};
    uint8_t *pos;

    CHECK_NOT_NULL(core_key_data);
    CHECK_NOT_NULL(session);

    CHECK_RESULT_OK(rsa_check_mode(purpose, padding, digest));

    /* Extract key data. */
    key_metadata = core_key_data;

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        core_key_data_len >= KM_RSA_METADATA_SIZE);

    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        get_u32(key_metadata) == key_size); // sanity check

    rsa_key_pair.modulus.len = get_u32(key_metadata + 4);
    rsa_key_pair.exponent.len = get_u32(key_metadata + 8);
    rsa_key_pair.privateExponent.len = get_u32(key_metadata + 12);
    rsa_key_pair.privateCrtKey.P.len = get_u32(key_metadata + 16);
    rsa_key_pair.privateCrtKey.Q.len = get_u32(key_metadata + 20);
    rsa_key_pair.privateCrtKey.DP.len = get_u32(key_metadata + 24);
    rsa_key_pair.privateCrtKey.DQ.len = get_u32(key_metadata + 28);
    rsa_key_pair.privateCrtKey.Qinv.len = get_u32(key_metadata + 32);

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        core_key_data_len >= KM_RSA_METADATA_SIZE
            + rsa_key_pair.modulus.len
            + rsa_key_pair.exponent.len
            + rsa_key_pair.privateExponent.len
            + rsa_key_pair.privateCrtKey.P.len
            + rsa_key_pair.privateCrtKey.Q.len
            + rsa_key_pair.privateCrtKey.DP.len
            + rsa_key_pair.privateCrtKey.DQ.len
            + rsa_key_pair.privateCrtKey.Qinv.len);

    pos = (uint8_t*)(core_key_data + KM_RSA_METADATA_SIZE); // raw key data
    set_ptr_increment_src(&rsa_key_pair.modulus.value, &pos, rsa_key_pair.modulus.len);
    set_ptr_increment_src(&rsa_key_pair.exponent.value, &pos, rsa_key_pair.exponent.len);
    set_ptr_increment_src(&rsa_key_pair.privateExponent.value, &pos, rsa_key_pair.privateExponent.len);
    set_ptr_increment_src(&rsa_key_pair.privateCrtKey.P.value, &pos, rsa_key_pair.privateCrtKey.P.len);
    set_ptr_increment_src(&rsa_key_pair.privateCrtKey.Q.value, &pos, rsa_key_pair.privateCrtKey.Q.len);
    set_ptr_increment_src(&rsa_key_pair.privateCrtKey.DP.value, &pos, rsa_key_pair.privateCrtKey.DP.len);
    set_ptr_increment_src(&rsa_key_pair.privateCrtKey.DQ.value, &pos, rsa_key_pair.privateCrtKey.DQ.len);
    set_ptr_increment_src(&rsa_key_pair.privateCrtKey.Qinv.value, &pos, rsa_key_pair.privateCrtKey.Qinv.len);

    /* Set API key. */
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR, isRsaKeyBuffersValid(&rsa_key_pair));
    api_key.rsaKey = &rsa_key_pair;

    session->rsa_op_ctx.purpose = purpose;
    session->rsa_op_ctx.padding = padding;
    session->rsa_op_ctx.digest = digest;
    session->rsa_op_ctx.modulusByteLen = rsa_key_pair.modulus.len;
    memcpy(session->rsa_op_ctx.modulus, rsa_key_pair.modulus.value, rsa_key_pair.modulus.len);

    /* Determine how we will proecss the input data when it arrives. */
    switch (padding) {
        case KM_PAD_NONE: // no digest
            session->rsa_op_ctx.update_method = rsa_op_buffer_input;
            break;
        case KM_PAD_RSA_OAEP: // encryption, buffer input
            session->rsa_op_ctx.update_method = rsa_op_buffer_input;
            break;
        case KM_PAD_RSA_PSS:
            // the size of the RSA key must be at least 22 bytes larger than the output size of the digest
            if( (digest_length(digest)+22) > session->rsa_op_ctx.modulusByteLen)
            {
                return KM_ERROR_INCOMPATIBLE_DIGEST;
            }
            session->rsa_op_ctx.update_method = rsa_op_digest_input;
            break;
        case KM_PAD_RSA_PKCS1_1_5_ENCRYPT:
            session->rsa_op_ctx.update_method = rsa_op_buffer_input;
            break;
        case KM_PAD_RSA_PKCS1_1_5_SIGN:
            if (digest == KM_DIGEST_NONE) {
                session->rsa_op_ctx.update_method = rsa_op_buffer_input;
            } else {
                session->rsa_op_ctx.update_method = rsa_op_digest_input;
            }
            break;
        default: // shouldn't get here
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

    /* Initialize processing of input. */
    session->rsa_op_ctx.buf_cursor = 0;
    if (session->rsa_op_ctx.update_method == rsa_op_digest_input) {
        CHECK_RESULT_OK(digest_begin(digest, &session->rsa_op_ctx.hashCtx));
    }

    /* Initialize operation. Note that all operations are performed as cipher
     * operations with TLAPI_ALG_RSA_NOPAD. (Padding is handled in the TA.)
     */
    CHECK_TLAPI_OK(tlApiCipherInit(&session->cr_session_handle,
        TLAPI_ALG_RSA_NOPAD,
        ((purpose == KM_PURPOSE_ENCRYPT) || (purpose == KM_PURPOSE_VERIFY))
            ? TLAPI_MODE_ENCRYPT : TLAPI_MODE_DECRYPT,
        &api_key));

end:
    return ret;
}

keymaster_error_t rsa_update(
    const uint8_t *input,
    uint32_t input_length,
    uint32_t *input_consumed,
    session_handle_t *session)
{
    keymaster_error_t ret = KM_ERROR_OK;
    CHECK_NOT_NULL(input_consumed);
    *input_consumed = 0;
    CHECK_NOT_NULL(session);

    /* Process input. */
    switch (session->rsa_op_ctx.update_method) {
        case rsa_op_buffer_input:
            CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                input_length + session->rsa_op_ctx.buf_cursor <= session->rsa_op_ctx.modulusByteLen);
            memcpy(session->rsa_op_ctx.buf + session->rsa_op_ctx.buf_cursor, input, input_length);
            session->rsa_op_ctx.buf_cursor += input_length;
            break;
        case rsa_op_digest_input:
            CHECK_RESULT_OK(digest_update(&session->rsa_op_ctx.hashCtx, input, input_length));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }
    *input_consumed = input_length;

end:
    if (ret != KM_ERROR_OK) {
        if (session) {
            (void)tlApiCrAbort(session->cr_session_handle);
        }
    }
    return ret;
}

/**
 * Zero-pad buffer and check it is in range for RSA operation.
 *
 * @param rsa_op_ctx RSA operation context
 *
 * @retval true if the buffer is numerically less than the modulus
 */
static bool zero_pad_and_check_buf_in_range(
    rsa_op_ctx_t *rsa_op_ctx)
{
    uint32_t modulusByteLen = rsa_op_ctx->modulusByteLen;
    uint8_t *buf = rsa_op_ctx->buf;

    memmove(buf + modulusByteLen - rsa_op_ctx->buf_cursor, buf, rsa_op_ctx->buf_cursor);
    memset(buf, 0, modulusByteLen - rsa_op_ctx->buf_cursor);
    rsa_op_ctx->buf_cursor = modulusByteLen;

    for (uint32_t i = 0; i < modulusByteLen; i++) {
        if (buf[i] < rsa_op_ctx->modulus[i]) {
            return true;
        }
        if (buf[i] > rsa_op_ctx->modulus[i]) {
            return false;
        }
    }
    return false;
}

keymaster_error_t rsa_finish_sign(
    uint8_t*            signature,
    uint32_t*           signature_length,
    session_handle_t    *session)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t EM[KM_RSA_BYTELEN_MAX];
    uint32_t modulusByteLen;

    CHECK_NOT_NULL(signature_length);
    CHECK_NOT_NULL(session);

    modulusByteLen = session->rsa_op_ctx.modulusByteLen;

    /* Finalize any ongoing digest, and put data into buf */
    switch (session->rsa_op_ctx.update_method) {
        case rsa_op_buffer_input: // no more to do
            break;
        case rsa_op_digest_input:
            session->rsa_op_ctx.buf_cursor = MAX_RSA_OUTPUT_LENGTH;
            CHECK_RESULT_OK(digest_finish(&session->rsa_op_ctx.hashCtx,
                session->rsa_op_ctx.buf, &session->rsa_op_ctx.buf_cursor));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

    memset(EM, 0, modulusByteLen);

    switch (session->rsa_op_ctx.padding) {
        case KM_PAD_NONE: // no digest, rsa_op_buffer_input
            CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
                zero_pad_and_check_buf_in_range(&session->rsa_op_ctx));
            CHECK_TLAPI_OK(tlApiCipherDoFinal(session->cr_session_handle,
                session->rsa_op_ctx.buf, session->rsa_op_ctx.buf_cursor,
                signature, (size_t*)signature_length));
            break;
        case KM_PAD_RSA_PSS: // rsa_op_digest_input
            CHECK_RESULT_OK(padPkcs1Pss(EM, &session->rsa_op_ctx, 20));
            CHECK_TLAPI_OK(tlApiCipherDoFinal(session->cr_session_handle,
                EM, modulusByteLen, signature, (size_t*)signature_length));
            break;
        case KM_PAD_RSA_PKCS1_1_5_SIGN: // rsa_op_buffer_input or rsa_op_digest_input
            CHECK_RESULT_OK(rsa_pkcs1_1_5_sig_pad(&session->rsa_op_ctx, EM));
            CHECK_TLAPI_OK(tlApiCipherDoFinal(session->cr_session_handle,
                EM, modulusByteLen, signature, (size_t*)signature_length));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

end:
    if (ret != KM_ERROR_OK) {
        if (session) {
            (void)tlApiCrAbort(session->cr_session_handle);
        }
    }
    return ret;
}

keymaster_error_t rsa_finish_verify(
    const uint8_t*      signature,
    uint32_t            signature_length,
    session_handle_t    *session)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t EM[KM_RSA_BYTELEN_MAX];
    size_t EM_len;
    uint32_t modulusByteLen;

    CHECK_NOT_NULL(session);

    modulusByteLen = session->rsa_op_ctx.modulusByteLen;

    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        signature_length == modulusByteLen);

    /* Finalize any ongoing digest, and put data into buf */
    switch (session->rsa_op_ctx.update_method) {
        case rsa_op_buffer_input: // no more to do
            break;
        case rsa_op_digest_input:
            session->rsa_op_ctx.buf_cursor = MAX_RSA_OUTPUT_LENGTH;
            CHECK_RESULT_OK(digest_finish(&session->rsa_op_ctx.hashCtx,
                session->rsa_op_ctx.buf, &session->rsa_op_ctx.buf_cursor));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        session->rsa_op_ctx.buf_cursor <= modulusByteLen);

    memset(EM, 0, modulusByteLen);

    /* 'Encrypt' signature with public key and raw RSA. */
    EM_len = modulusByteLen;
    CHECK_TLAPI_OK(tlApiCipherDoFinal(session->cr_session_handle,
        signature, signature_length, EM, &EM_len));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        EM_len == modulusByteLen);

    /* Verify. */
    switch (session->rsa_op_ctx.padding) {
        case KM_PAD_NONE: // check that EM == buf with possible leading zeros
            CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
                zero_pad_and_check_buf_in_range(&session->rsa_op_ctx));
            CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
                timeInvariant_memcmp(EM, session->rsa_op_ctx.buf, modulusByteLen) == 0);
            break;
        case KM_PAD_RSA_PSS:
            CHECK_RESULT_OK(unpadPkcs1Pss(EM, &session->rsa_op_ctx));
            break;
        case KM_PAD_RSA_PKCS1_1_5_SIGN:
            CHECK_RESULT_OK(rsa_finish_verify_pkcs1(&session->rsa_op_ctx, EM));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

end:
    if (ret != KM_ERROR_OK) {
        if (session) {
            (void)tlApiCrAbort(session->cr_session_handle);
        }
    }
    return ret;
}

keymaster_error_t rsa_finish_cipher(
    uint8_t*            output,
    uint32_t*           output_length,
    session_handle_t    *session)
{
    keymaster_error_t ret = KM_ERROR_OK;
    CHECK_NOT_NULL(session);

    /* Finalize any ongoing digest, and put data into buf */
    switch (session->rsa_op_ctx.update_method) {
        case rsa_op_buffer_input: // no more to do
            break;
        case rsa_op_digest_input:
            session->rsa_op_ctx.buf_cursor = MAX_RSA_OUTPUT_LENGTH;
            CHECK_RESULT_OK(digest_finish(&session->rsa_op_ctx.hashCtx,
                session->rsa_op_ctx.buf, &session->rsa_op_ctx.buf_cursor));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

    /* Finalize cipher. */
    switch (session->rsa_op_ctx.padding) {
        case KM_PAD_NONE:
            CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
                zero_pad_and_check_buf_in_range(&session->rsa_op_ctx));
            CHECK_TLAPI_OK(tlApiCipherDoFinal(session->cr_session_handle,
                session->rsa_op_ctx.buf, session->rsa_op_ctx.buf_cursor,
                output, (size_t*)output_length));
            break;
        case KM_PAD_RSA_OAEP:
            CHECK_RESULT_OK(rsa_finish_cipher_with_oeap(
                output, output_length, session->cr_session_handle, &session->rsa_op_ctx));
            break;
        case KM_PAD_RSA_PKCS1_1_5_ENCRYPT:
            CHECK_RESULT_OK(rsa_finish_cipher_with_pkcs1_1_5(
                output, output_length, session->cr_session_handle, &session->rsa_op_ctx));
            break;
        default:
            ret = KM_ERROR_UNKNOWN_ERROR;
            goto end;
    }

end:
    if (ret != KM_ERROR_OK) {
        if (session) {
            (void)tlApiCrAbort(session->cr_session_handle);
        }
    }
    return ret;
}

keymaster_error_t rsa_get_pub_data(
    uint8_t *core_pub_data,
    uint32_t core_pub_data_len,
    const uint8_t *core_key_data,
    uint32_t core_key_data_len)
{
    if (core_key_data_len < KM_RSA_METADATA_SIZE) {
        return KM_ERROR_INVALID_KEY_BLOB;
    }
    uint32_t keysize = get_u32(core_key_data);
    uint32_t n_len = get_u32(core_key_data + 4);
    uint32_t e_len = get_u32(core_key_data + 8);
    if ((core_key_data_len < KM_RSA_METADATA_SIZE + n_len + e_len) ||
        (core_pub_data_len < 12 + n_len + e_len))
    {
        return KM_ERROR_INVALID_KEY_BLOB;
    }

    set_u32(core_pub_data, keysize);
    set_u32(core_pub_data + 4, n_len);
    set_u32(core_pub_data + 8, e_len);
    memcpy(core_pub_data + 12, core_key_data + KM_RSA_METADATA_SIZE, n_len);
    memcpy(core_pub_data + 12 + n_len, core_key_data + KM_RSA_METADATA_SIZE + n_len, e_len);

    return KM_ERROR_OK;
}
