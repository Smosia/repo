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
#include "tlkm_ec.h"
#include "tlCommon.h"
#include "tlkm_hash.h"

/**
 * Get the curve identifier implicitly associated with a key size.
 *
 * @param[out] curve curve identifier
 * @param[in] key_size key size in bytes
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t get_curve(
    uint32_t *curve,
    uint32_t key_size)
{
    assert(curve != NULL);

    switch (key_size) {
        case 192:
            *curve = ECC_CURVE_NIST_P192;
            break;
        case 224:
            *curve = ECC_CURVE_NIST_P224;
            break;
        case 256:
            *curve = ECC_CURVE_NIST_P256;
            break;
        case 384:
            *curve = ECC_CURVE_NIST_P384;
            break;
        case 521:
            *curve = ECC_CURVE_NIST_P521;
            break;
        default:
            return KM_ERROR_UNSUPPORTED_KEY_SIZE;
    }

    return KM_ERROR_OK;
}

keymaster_error_t ec_generatekey(
    uint32_t            key_size,
    const uint8_t*      params,
    uint32_t            params_length,
    const void*         own_params,
    size_t              own_params_length,
    uint8_t*            key_material,
    uint32_t*           key_material_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    tlApiEcdsaKey_t ec_key_pair = {0};
    tlApiKeyPair_t key_pair = {0};
    uint32_t keylen = BITS_TO_BYTES(key_size);
    uint8_t* pos = NULL;
    uint32_t key_material_buflen; // length of buffer
    uint32_t extended_params_length = params_length + own_params_length;
    uint8_t *ec_buffer = NULL;

    CHECK_NOT_NULL(key_material);
    CHECK_NOT_NULL(key_material_len);

    key_material_buflen = *key_material_len;

    /* Update key data buffers */
    CHECK_RESULT_OK(tlkm_alloc(&ec_buffer, MAX_EC_BUFFER_SIZE_IN_BYTES));

    ec_key_pair.x.value = ec_buffer;
    ec_key_pair.y.value = ec_key_pair.x.value + keylen;
    ec_key_pair.privKey.value = ec_key_pair.y.value + keylen;

    ec_key_pair.x.len = keylen;
    ec_key_pair.y.len = keylen;
    ec_key_pair.privKey.len = keylen;

    /* Extract curve type. */
    CHECK_RESULT_OK(get_curve(&ec_key_pair.curveType, key_size));

    /* Generate EC key pair */
    key_pair.ecdsaKeyPair = &ec_key_pair;
    CHECK_TLAPI_OK(tlApiGenerateKeyPair(&key_pair, TLAPI_ECDSA, keylen));

    /* EC keys will be wrapped as:
     * |params_len|params|type|size|ec_metadata|keydata|
     */
    *key_material_len = 4 + extended_params_length + 4 + 4 + KM_EC_METADATA_SIZE
        + ec_key_pair.x.len
        + ec_key_pair.y.len
        + ec_key_pair.privKey.len;
    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *key_material_len <= key_material_buflen);

    pos = key_material;
    CHECK_RESULT_OK(make_key_material_params(&pos, params, params_length,
        OWN_PARAMS_NB, own_params, own_params_length));

    set_u32_increment_pos(&pos, KM_ALGORITHM_EC);
    set_u32_increment_pos(&pos, key_size);

    // key metadata
    set_u32_increment_pos(&pos, ec_key_pair.curveType);
    set_u32_increment_pos(&pos, ec_key_pair.x.len);
    set_u32_increment_pos(&pos, ec_key_pair.y.len);
    set_u32_increment_pos(&pos, ec_key_pair.privKey.len);

    // raw key data
    set_data_increment_pos(&pos, ec_key_pair.x.value, ec_key_pair.x.len);
    set_data_increment_pos(&pos, ec_key_pair.y.value, ec_key_pair.y.len);
    set_data_increment_pos(&pos, ec_key_pair.privKey.value, ec_key_pair.privKey.len);

end:
    tlApiFree(ec_buffer);

    return ret;
}

keymaster_error_t ec_begin(
    keymaster_purpose_t purpose,
    uint32_t key_size,
    const uint8_t *core_key_data, uint32_t core_key_data_len,
    keymaster_digest_t digest,
    session_handle_t *session,
    hashOpCtx_t *hashOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    tlApiSigMode_t mode;
    const uint8_t *key_metadata;
    uint32_t curve;
    uint8_t *pos;
    tlApiKey_t key = {0};

    CHECK_NOT_NULL(core_key_data);
    CHECK_NOT_NULL(session);

    /* Set mode */
    if (purpose == KM_PURPOSE_SIGN) {
        mode = TLAPI_MODE_SIGN;
    } else if (purpose == KM_PURPOSE_VERIFY) {
        mode = TLAPI_MODE_VERIFY;
    } else {
        return KM_ERROR_INCOMPATIBLE_PURPOSE;
    }

    /* Extract key data */
    key_metadata = core_key_data;

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        core_key_data_len >= KM_EC_METADATA_SIZE);

    /* Sanity-check key size against curve type from metadata */
    CHECK_RESULT_OK(get_curve(&curve, key_size));
    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        curve == get_u32(key_metadata));

    /* Populate key */
    tlApiEcdsaKey_t ec_key_pair;
    ec_key_pair.curveType = curve;
    ec_key_pair.x.len = get_u32(key_metadata + 4);
    ec_key_pair.y.len = get_u32(key_metadata + 8);
    ec_key_pair.privKey.len = get_u32(key_metadata + 12);

    CHECK_TRUE(KM_ERROR_INVALID_KEY_BLOB,
        core_key_data_len >= KM_EC_METADATA_SIZE
        + ec_key_pair.x.len
        + ec_key_pair.y.len
        + ec_key_pair.privKey.len);

    pos = (uint8_t*)(core_key_data + KM_EC_METADATA_SIZE); // raw key data
    set_ptr_increment_src(&ec_key_pair.x.value, &pos, ec_key_pair.x.len);
    set_ptr_increment_src(&ec_key_pair.y.value, &pos, ec_key_pair.y.len);
    set_ptr_increment_src(&ec_key_pair.privKey.value, &pos, ec_key_pair.privKey.len);

    if (digest == KM_DIGEST_NONE) { // initialize buffering
        session->buffered_input_len = 0;
        CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
            BITS_TO_BYTES(key_size) <= SESSION_INPUT_BUFSIZE);
        session->max_input_len = BITS_TO_BYTES(key_size);
    } else { // initialize digest
        CHECK_RESULT_OK(digest_begin(digest, hashOpCtx));
    }

    /* Initialize the signature/verification operation with the key data */
    key.ecdsaKey = &ec_key_pair;
    CHECK_TLAPI_OK(tlApiSignatureInit(&session->cr_session_handle, &key, mode, TLAPI_SIG_ECDSA_RAW));

end:
    return ret;
}

keymaster_error_t ec_update(
    const uint8_t *input,
    uint32_t input_length,
    uint32_t *input_consumed,
    session_handle_t *session,
    hashOpCtx_t *hashOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;

    CHECK_NOT_NULL(input_consumed);
    *input_consumed = 0;
    CHECK_NOT_NULL(session);
    CHECK_NOT_NULL(hashOpCtx);

    if (hashOpCtx->algorithm == KM_DIGEST_NONE) { // buffer input
        CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
            session->buffered_input_len + input_length <= session->max_input_len);
        memcpy(session->buffered_input + session->buffered_input_len, input, input_length);
        session->buffered_input_len += input_length;
    } else { // update digest
        CHECK_RESULT_OK(digest_update(hashOpCtx, input, input_length));
    }
    *input_consumed = input_length;

end:
    if (ret != KM_ERROR_OK) {
        if (session) { // close the signature session
            (void)tlApiCrAbort(session->cr_session_handle);
        }
    }
    return ret;
}

/**
 * Encode a raw integer in DER form
 *
 * @param[out] dest encoding
 * @param[in,out] dest_len length of \p dest (buffer size on entry, length written on exit)
 * @param src raw integer (big-endian)
 * @param src_len length of \p src
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t encode_integer(
    uint8_t *dest,
    size_t *dest_len,
    const uint8_t *src,
    uint8_t src_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    size_t dest_pos = 0;
    uint8_t src_pos = 0;
    uint8_t src_num_len, src_num_len0;

    CHECK_NOT_NULL(dest);
    CHECK_NOT_NULL(dest_len);
    CHECK_NOT_NULL(src);

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *dest_len >= 2);
    dest[0] = 0x02;
    dest_pos = 2; // we'll write the length later...

    /* Skip leading zeros in src */
    while ((src_pos < src_len) && (src[src_pos] == 0)) {
        src_pos++;
    }
    src_num_len = src_len - src_pos; // length with leading zeros stripped
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        src_num_len <= MAX_EC_CURVE_SIZE_BYTES);

    if ((src_num_len > 0) && ((src[src_pos] & 0x80) != 0)) {
        CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
            *dest_len > dest_pos);
        dest[dest_pos] = 0x00; // leading zero
        dest_pos++;
        src_num_len0 = src_num_len + 1;
    } else {
        src_num_len0 = src_num_len;
    }

    //...now write the length (which may include a leading zero)
    dest[1] = src_num_len0;

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *dest_len >= dest_pos + src_num_len);
    memcpy(&dest[dest_pos], &src[src_pos], src_num_len);
    dest_pos += src_num_len;

    *dest_len = dest_pos;

end:
    return ret;
}

/**
 * Decode a DER-encoded integer to raw form with fixed length
 *
 * @param[out] dest raw integer (big-endian)
 * @param dest_len length of \p dest
 * @param src DER-encoded integer
 * @param[in,out] src_len length of \p src (buffer size on entry, amount written on exit)
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t decode_integer(
    uint8_t *dest,
    uint8_t dest_len,
    const uint8_t *src,
    size_t *src_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    size_t src_num_len, src_num_len0, dest_zeros;
    size_t src_num_pos;

    CHECK_NOT_NULL(dest);
    CHECK_NOT_NULL(src);
    CHECK_NOT_NULL(src_len);

    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        *src_len >= 2);
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        src[0] == 0x02);
    src_num_len0 = (size_t)src[1];
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        *src_len >= 2 + src_num_len0);

    /* Skip leading zeros in src */
    src_num_pos = 0;
    while ((src_num_pos < src_num_len0) && (src[2 + src_num_pos] == 0)) {
        src_num_pos++;
    }

    src_num_len = src_num_len0 - src_num_pos;
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        dest_len >= src_num_len);
    dest_zeros = dest_len - src_num_len;

    /* Write to the destination buffer. */
    memset(dest, 0, dest_zeros);
    memcpy(&dest[dest_zeros], &src[2 + src_num_pos], src_num_len);

    /* Set the length written */
    *src_len = 2 + src_num_len0;

end:
    return ret;
}

/**
 * DER-encode a raw signature (r,s).

 * @param[out] sig encoded signature
 * @param[in,out] sig_len length of \p signature (buffer size on entry, length of encoding on exit)
 * @param raw_sig raw signature (two concatenated big-endian integers each of size \p curve_len)
 * @param curve_len size of each integer in bytes
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t ec_der_encode_sig(
    uint8_t *sig,
    size_t *sig_len,
    const uint8_t *raw_sig,
    size_t curve_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    size_t r_len, s_len; // ASN1 lengths
    size_t pos;
    size_t seq_len; // length of sequence data
    size_t seq_len_len; // length of length field for sequence

    CHECK_NOT_NULL(raw_sig);
    CHECK_NOT_NULL(sig);
    CHECK_NOT_NULL(sig_len);

    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        curve_len <= MAX_EC_CURVE_SIZE_BYTES); // <= 66

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *sig_len >= 2 * curve_len + 9); // we'll need at most this

    pos = 0;

    sig[pos] = 0x30;
    pos++;

    /* We'll insert the sequence length later... */

    /* Write r */
    r_len = *sig_len - pos; // buffer space available
    CHECK_RESULT_OK(encode_integer(
        &sig[pos], &r_len, raw_sig, curve_len));
    pos += r_len;

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        pos <= *sig_len);

    /* Write s */
    s_len = *sig_len - pos; // buffer space available
    CHECK_RESULT_OK(encode_integer(
        &sig[pos], &s_len, raw_sig + curve_len, curve_len));
    pos += s_len;

    /* Now insert the sequence length */
    seq_len = r_len + s_len;
    seq_len_len = (seq_len < 0x80) ? 1 : 2;
    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        pos + seq_len_len <= *sig_len);
    memmove(&sig[1 + seq_len_len], &sig[1], seq_len);
    sig[seq_len_len] = seq_len;
    if (seq_len_len == 2) { // long form
        sig[1] = 0x81;
    }

    /* And the length of the whole encoding */
    *sig_len = 1 + seq_len_len + seq_len;

end:
    return ret;
}

/**
 * Decode a DER signature to a raw (r,s) signature.
 *
 * @param[out] raw_sig raw signature, a buffer of length 2 * \p curve_len
 * @param curve_len length of each integer in the raw signature
 * @param sig encoded signature
 * @param sig_len length of \p sig
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t ec_der_decode_sig(
    uint8_t *raw_sig,
    size_t curve_len,
    const uint8_t *sig,
    size_t sig_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    size_t r_len, s_len; // ASN1 lengths
    size_t seq_len; // length of sequence data
    size_t seq_len_len; // length of length field for sequence

    CHECK_NOT_NULL(raw_sig);
    CHECK_NOT_NULL(sig);

    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        sig_len >= 2);
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        sig[0] == 0x30);

    if (sig[1] < 0x80) { // short form
        seq_len = sig[1];
        seq_len_len = 1;
    } else { // long form
        CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
            sig[1] == 0x81);
        CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
            sig_len >= 3);
        seq_len = sig[2];
        seq_len_len = 2;
    }

    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        sig_len == 1 + seq_len_len + seq_len);

    /* Write r */
    r_len = seq_len;
    CHECK_RESULT_OK(decode_integer(
        raw_sig, curve_len, sig + 1 + seq_len_len, &r_len));

    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        sig_len >= 1 + seq_len_len + r_len);

    /* Write s */
    s_len = sig_len - 1 - seq_len_len - r_len;
    CHECK_RESULT_OK(decode_integer(
        raw_sig + curve_len, curve_len, sig + 1 + seq_len_len + r_len, &s_len));

    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        seq_len == r_len + s_len);

end:
    return ret;
}

/**
 * Adjust the padded message buffer to be left-aligned.
 *
 * @param[in,out] digest buffer
 * @param key_size key size in bits
 *
 * @pre \p digest has length \p BITS_TO_BYTES(key_size)
 * @pre \p digest[0] == 0
 *
 * @return KM_ERROR_OK or KM_ERROR_INVALID_INPUT_LENGTH
 */
static keymaster_error_t adjust_buffer(
    uint8_t *digest,
    uint32_t key_size)
{
    size_t curve_len = BITS_TO_BYTES(key_size);
    uint32_t n_bits = 8*curve_len - key_size; // < 8

    if (n_bits > 0) { // shift left by n_bits
        uint32_t u = 8 - n_bits;
        uint8_t carry = 0;
        for (int i = curve_len-1; i >= 0; i--) {
            uint8_t t = digest[i];
            digest[i] = (t << n_bits) | carry;
            carry = t >> u;
        }
        assert(carry == 0);
    }
    return KM_ERROR_OK;
}

/**
 * Prepare a buffer for a raw ECDSA operation.
 *
 * @param[out] message to pass to ECDSA operation
 * @param key_size key size in bits
 * @param buf accumulated message
 * @param buf_len length of \p buf
 *
 * @pre \p digest has length at least \p key_size bits
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t prepare_buffer(
    uint8_t *digest,
    uint32_t key_size,
    const uint8_t *buf,
    size_t buf_len)
{
    keymaster_error_t ret = KM_ERROR_OK;
    size_t curve_len, zero_len;

    CHECK_NOT_NULL(digest);
    CHECK_NOT_NULL(buf);

    curve_len = BITS_TO_BYTES(key_size);

    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        buf_len <= curve_len);

    /* Zero-pad to full length, */
    zero_len = curve_len - buf_len;
    memset(digest, 0, zero_len);
    memcpy(digest + zero_len, buf, buf_len);

    if (buf_len < curve_len) {
        /* Shift left by the excess number of bits if any. */
        CHECK_RESULT_OK(adjust_buffer(digest, key_size));
    }

    /* If buf_len == curve_len, do nothing. Any excess bits on the right of buf
     * will be ignored by the ECDSA operation.
     */

end:
    return ret;
}

keymaster_error_t ec_finish_sign(
    uint8_t *signature,
    size_t *signature_length,
    session_handle_t *session,
    hashOpCtx_t *hashOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t digest[SESSION_INPUT_BUFSIZE];
    size_t digest_len = sizeof(digest);
    uint32_t key_size;
    size_t curve_len;
    uint8_t raw_sig[2*MAX_EC_CURVE_SIZE_BYTES] = {0};
    size_t raw_sig_len = sizeof(raw_sig);

    CHECK_NOT_NULL(session);
    CHECK_NOT_NULL(hashOpCtx);

    key_size = session->key_size;
    curve_len = BITS_TO_BYTES(key_size);

    if (hashOpCtx->algorithm == KM_DIGEST_NONE) {
        CHECK_RESULT_OK(prepare_buffer(digest, key_size, session->buffered_input, session->buffered_input_len));
        digest_len = curve_len;
    } else { // finish the digest
        CHECK_RESULT_OK(digest_finish(hashOpCtx, digest, &digest_len));
    }

    /* Do the ECDSA signature operation. */
    CHECK_TLAPI_OK(tlApiSignatureSign(session->cr_session_handle,
        digest, digest_len, raw_sig, &raw_sig_len));

    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        raw_sig_len == 2 * curve_len);

    /* DER-encode the raw signature. */
    CHECK_RESULT_OK(ec_der_encode_sig(
        signature, signature_length, raw_sig, curve_len));

end:
    if (ret != KM_ERROR_OK) {
        if (session) {
            // close the signature session
            (void)tlApiCrAbort(session->cr_session_handle);
        }
    }
    return ret;
}

keymaster_error_t ec_finish_verify(
    const uint8_t *signature,
    uint32_t signature_length,
    session_handle_t *session,
    hashOpCtx_t *hashOpCtx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t digest[SESSION_INPUT_BUFSIZE];
    size_t digest_len = sizeof(digest);
    bool validity = false;
    uint8_t raw_sig[2*MAX_EC_CURVE_SIZE_BYTES];
    uint32_t key_size;
    size_t curve_len;

    CHECK_NOT_NULL(session);
    CHECK_NOT_NULL(hashOpCtx);

    key_size = session->key_size;
    curve_len = BITS_TO_BYTES(key_size);

    if (hashOpCtx->algorithm == KM_DIGEST_NONE) {
        CHECK_RESULT_OK(prepare_buffer(digest, key_size, session->buffered_input, session->buffered_input_len));
        digest_len = curve_len;
    } else { // finish the digest
        CHECK_RESULT_OK(digest_finish(hashOpCtx, digest, &digest_len));
    }

    /* DER-decode the signature. */
    CHECK_RESULT_OK(ec_der_decode_sig(
        raw_sig, curve_len, signature, signature_length));

    /* Do the ECDSA verification operation. */
    CHECK_TLAPI_OK(tlApiSignatureVerify(session->cr_session_handle,
        digest, digest_len, raw_sig, 2 * curve_len, &validity));
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        validity);

end:
    if (ret != KM_ERROR_OK) { // close the verification session
        if (session) {
            (void)tlApiCrAbort(session->cr_session_handle);
        }
        ret = KM_ERROR_VERIFICATION_FAILED;
    }
    return ret;
}

keymaster_error_t ec_get_pub_data(
    uint8_t *core_pub_data,
    uint32_t core_pub_data_len,
    const uint8_t *core_key_data,
    uint32_t core_key_data_len)
{
    if (core_key_data_len < KM_EC_METADATA_SIZE) {
        return KM_ERROR_INVALID_KEY_BLOB;
    }
    uint32_t curve = get_u32(core_key_data);
    uint32_t x_len = get_u32(core_key_data + 4);
    uint32_t y_len = get_u32(core_key_data + 8);
    /* uint32_t a_len = get_u32(core_key_data + 12); */
    if ((core_key_data_len < KM_EC_METADATA_SIZE + x_len + y_len) ||
        (core_pub_data_len < 12 + x_len + y_len))
    {
        return KM_ERROR_INVALID_KEY_BLOB;
    }

    set_u32(core_pub_data, curve);
    set_u32(core_pub_data + 4, x_len);
    set_u32(core_pub_data + 8, y_len);
    memcpy(core_pub_data + 12, core_key_data + KM_EC_METADATA_SIZE, x_len);
    memcpy(core_pub_data + 12 + x_len, core_key_data + KM_EC_METADATA_SIZE + x_len, y_len);

    return KM_ERROR_OK;
}
