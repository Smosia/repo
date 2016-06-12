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

#include "tlkm_rsa_pad.h"
#include "tlkm_rsa.h"
#include "tlkm_hash.h"
#include "tlCommon.h"
#include "keymaster_util.h"

// Headers needed by PKCSv15 padding
static const uint8_t pu08MD5DigestInfoHeader[] = {
    0x30, 0x20, 0x30, 0x0c, 0x06, 0x08, 0x2a, 0x86,
    0x48, 0x86, 0xf7, 0x0d, 0x02, 0x05, 0x05, 0x00,
    0x04, 0x10};
static const uint8_t pu08SHA1DigestInfoHeader[] = {
    0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2B, 0x0E,
    0x03, 0x02, 0x1A, 0x05, 0x00, 0x04, 0x14};
static const uint8_t pu08SHA224DigestInfoHeader[] = {
    0x30, 0x2D, 0x30, 0x0D, 0x06, 0x09, 0x60, 0x86,
    0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x04, 0x05,
    0x00, 0x04, 0x1C};
static const uint8_t pu08SHA256DigestInfoHeader[] = {
    0x30, 0x31, 0x30, 0x0D, 0x06, 0x09, 0x60, 0x86,
    0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x05,
    0x00, 0x04, 0x20};
static const uint8_t pu08SHA384DigestInfoHeader[] = {
    0x30, 0x41, 0x30, 0x0D, 0x06, 0x09, 0x60, 0x86,
    0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x02, 0x05,
    0x00, 0x04, 0x30};
static const uint8_t pu08SHA512DigestInfoHeader[] = {
    0x30, 0x51, 0x30, 0x0D, 0x06, 0x09, 0x60, 0x86,
    0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x03, 0x05,
    0x00, 0x04, 0x40};

/** Mask generation function for PSS and OAEP padding. Uses SHA1 hash.
 *
 * @param      pMask         Pointer to the calculated random value (RAM buffer).
 * @param      maskLen       Mask length
 * @param      pMaskSeed     Pointer to the mask seed
 * @param      maskSeedLen   Mask seed length
 * @param      digest        Digest algorithm
 */
static keymaster_error_t vMaskGeneratorFunction(
    uint8_t *pMask,
    uint32_t maskLen,
    uint8_t *pMaskSeed,
    uint32_t maskSeedLen,
    keymaster_digest_t digest)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t c[4] = {0};
    uint8_t pHash[MAX_HASH_LEN] = {0};
    size_t mgfDigestLen = digest_length(digest), updateLen;
    hashOpCtx_t mdData;

    for (uint32_t counter = 0; mgfDigestLen * counter < maskLen; counter++) {
        // Update counter
        c[0] = (uint8_t)(counter >> 24);
        c[1] = (uint8_t)(counter >> 16);
        c[2] = (uint8_t)(counter >> 8);
        c[3] = (uint8_t)(counter >> 0);

        CHECK_RESULT_OK(digest_begin(digest, &mdData));
        CHECK_RESULT_OK(digest_update(&mdData, pMaskSeed, maskSeedLen));
        CHECK_RESULT_OK(digest_update(&mdData, c, sizeof(c)));
        CHECK_RESULT_OK(digest_finish(&mdData, pHash, &mgfDigestLen));
        CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
            mgfDigestLen == digest_length(digest));

        updateLen = maskLen - mgfDigestLen * counter;

        if (updateLen > mgfDigestLen) {
            memcpy(pMask + mgfDigestLen * counter, pHash, mgfDigestLen);
        } else { // last partial block
            memcpy(pMask + mgfDigestLen * counter, pHash, updateLen);
        }
    }
end:
    return ret;
}

#if defined(__ARMCC__)
#pragma push
#pragma O0
#define ATTR_OPT0
#elif defined(__GNUC__)
//#pragma GCC push_options
//#pragma GCC optimize("O0")
#define ATTR_OPT0 __attribute__ ((optimize(0)))
#endif
/* We don't want the compiler to optimize this function to make it non-constant-time */
/**
 * Time invariant version of memcmp. Copy/Paste from crypto driver.
 *
 * Returns '0' if s1 and s2 are same (or n is 0). Otherwise returns
 * a nonzero value
 */
int ATTR_OPT0 timeInvariant_memcmp(
        const void *s1,
        const void *s2,
        size_t  n)
{
    int result = 0;
    uint8_t *u1 = (uint8_t *)s1;
    uint8_t *u2 = (uint8_t *)s2;

    assert(u1 != NULL);
    assert(u2 != NULL);

    if (n == 0)
    {
        return 0;
    }

    while (n-- > 0)
    {
        result |= (*u1++ ^ *u2++);
    }

    return result;
}
#if defined(__ARMCC__)
#pragma pop
#elif defined(__GNUC__)
//#pragma GCC pop_options --> https://gcc.gnu.org/bugzilla/show_bug.cgi?id=59884
#endif


/**************************************
 **********   OAEP padding   **********
 *************************************/

//------------------------------------------------------------------------------
keymaster_error_t padPkcs1Oaep(
    uint8_t *to,
    const rsa_op_ctx_t *rsa_op_ctx)
{
    keymaster_error_t   ret = KM_ERROR_OK;
    uint8_t             *seed       = NULL;
    uint8_t             *db         = NULL;
    uint8_t             dbMask[KM_RSA_BYTELEN_MAX] = {0};
    size_t emLen, fLen;
    size_t              hLen        = 0; /* Hash length */
    uint32_t            seedLen     = 0; /* Seed length */
    uint16_t            dbLen       = 0; /* DB length */
    uint8_t             seedMask[MAX_HASH_LEN] = {0};
    uint8_t             lHash[MAX_HASH_LEN] = {0};
    hashOpCtx_t         mdData;
    size_t tmpLen;

    CHECK_NOT_NULL(to);
    CHECK_NOT_NULL(rsa_op_ctx);

    emLen = rsa_op_ctx->modulusByteLen - 1;
    fLen = rsa_op_ctx->buf_cursor;

    /* Retrieve digest length based on the algorithm */
    hLen = digest_length(rsa_op_ctx->digest);

    CHECK_TRUE(KM_ERROR_UNSUPPORTED_DIGEST,
        hLen != 0);

    /**
     * The following comments are from RFC 3447 Section 7.1.1
     * https://www.ietf.org/rfc/rfc3447.txt
     */

    /* If mLen > emLen - 2hLen - 1, output ‘‘message too long’’ and stop */
    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        fLen <= emLen - 2 * hLen - 1);

    /**
     * 2a. If the label L is not provided, let L be the empty string. Let
     *     lHash = Hash(L), an octet string of length hLen
     */
    CHECK_RESULT_OK(digest_begin(rsa_op_ctx->digest, &mdData));
    CHECK_RESULT_OK(digest_update(&mdData, NULL, 0));
    CHECK_RESULT_OK(digest_finish(&mdData, lHash, &hLen));

    /* to = 0x00 || SEED || DB */
    to[0] = 0;
    seed = to + 1;
    db = to + hLen + 1;
    dbLen = emLen - hLen;
    seedLen = hLen;

    /**
     * 2b. Generate an octet string PS consisting of k - mLen - 2hLen - 2
     *     zero octets.  The length of PS may be zero.
     */
    memset(db + hLen, 0, emLen - fLen - 2 * hLen - 1);
    db[emLen - fLen - hLen - 1] = 0x01;
    memcpy(db, lHash, hLen);

    /**
     * 2c. Concatenate lHash, PS, a single octet with hexadecimal value
     *     0x01, and the message M to form a data block DB of length k -
     *     hLen - 1 octets as
     *
     *        DB = lHash || PS || 0x01 || M.
     */
    memcpy(db + emLen - fLen - hLen, rsa_op_ctx->buf, fLen);

    /* 2d. Generate a random octet string seed of length hLen. */
    tmpLen = hLen;
    CHECK_TLAPI_OK(tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM,
        seed, &tmpLen));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        tmpLen == hLen);

    /* 2e. Let dbMask = MGF(seed, k - hLen - 1) */
    CHECK_RESULT_OK(vMaskGeneratorFunction(dbMask, dbLen, seed, seedLen, KM_DIGEST_SHA1));

    /* 2f. Let maskedDB = DB \xor dbMask. */
    for (size_t i = 0; i < emLen - hLen; i++) {
        db[i] ^= dbMask[i];
    }

    /* 2g. Let seedMask = MGF(maskedDB, hLen) */
    CHECK_RESULT_OK(vMaskGeneratorFunction(seedMask, hLen, db, dbLen, KM_DIGEST_SHA1));

    /* 2h. Let maskedSeed = seed \xor seedMask. */
    for (size_t i = 0; i < hLen; i++) {
        seed[i] ^= seedMask[i];
    }

    /**
     * 2i. At this point, the encoded message as below:
     *
     *      EM = 0x00 || maskedSeed || maskedDB.
     */
end:
    return ret;
}

//------------------------------------------------------------------------------
keymaster_error_t unpadPkcs1Oaep(
    uint8_t             *to,
    uint32_t            *tLen,
    const uint8_t       *from,
    const rsa_op_ctx_t  *rsa_op_ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t       db[KM_RSA_BYTELEN_MAX] = {0};
    uint8_t       em[KM_RSA_BYTELEN_MAX];
    uint8_t       *maskedSeed = NULL;
    uint8_t       *maskedDb   = NULL;
    uint32_t      dbLen       = 0;
    size_t nLen, hLen;
    uint8_t       seed[MAX_HASH_LEN]  = {0};
    uint8_t       lHash[MAX_HASH_LEN] = {0};
    uint32_t      msgLen = 0;
    hashOpCtx_t   mdData;
    size_t tmpLen;
    size_t i;

    CHECK_NOT_NULL(to);
    CHECK_NOT_NULL(tLen);
    CHECK_NOT_NULL(from);
    CHECK_NOT_NULL(rsa_op_ctx);

    nLen = rsa_op_ctx->modulusByteLen;
    hLen = digest_length(rsa_op_ctx->digest);

    /**
     * The following comments are from RFC 3447 Section 7.1.2
     * https://www.ietf.org/rfc/rfc3447.txt
     */

    /* Check the minumum length of nLen*/
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        nLen >= 2*hLen + 2);

    /*
     *  3a. If the label L is not provided, let L be the empty string. Let
     *  lHash = Hash(L), an octet string of length hLen (see the note
     *  in Section 7.1.1)
     */
    tmpLen = hLen;
    CHECK_RESULT_OK(digest_begin(rsa_op_ctx->digest, &mdData));
    CHECK_RESULT_OK(digest_update(&mdData, NULL, 0));
    CHECK_RESULT_OK(digest_finish(&mdData, &lHash[0], &tmpLen));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        tmpLen == hLen);

    /*
     *  3b. Separate the encoded message EM into a single octet Y, an octet
     *  string maskedSeed of length hLen, and an octet string maskedDB
     *  of length nLen - hLen - 1 as
     *
     *     EM = Y || maskedSeed || maskedDB.
     */
    dbLen = nLen - hLen - 1;

    memset(em, 0, nLen);
    memcpy(em, from, nLen);

    /*
     * 3c. Let seedMask = MGF(maskedDB, hLen).
     */
    maskedSeed = em + 1;
    maskedDb   = em + 1 + hLen;

    CHECK_RESULT_OK(vMaskGeneratorFunction(seed, hLen, maskedDb, dbLen, KM_DIGEST_SHA1));

    /*
     * 3d. Let seed = maskedSeed \xor seedMask.
     */
    for (i = 0; i < hLen; i++) {
        seed[i] ^= maskedSeed[i];
    }

    /*
     * 3e. Let dbMask = MGF(seed, k - hLen - 1).
     */
    CHECK_RESULT_OK(vMaskGeneratorFunction(db, dbLen, seed, hLen, KM_DIGEST_SHA1));

    /*
     * 3f. Let DB = maskedDB \xor dbMask.
     */
    for (i = 0; i < dbLen; i++) {
        db[i] ^= maskedDb[i];
    }

    /*
     * 3g. Separate DB into an octet string lHash' of length hLen, a
     * (possibly empty) padding string PS consisting of octets with
     * hexadecimal value 0x00, and a message M as
     *
     *      DB = lHash || PS || 0x01 || M.
     */
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        timeInvariant_memcmp(db, lHash, hLen) == 0);

    /**
     * TODO-2014-12-17-gurel: We need to handle the following part as time invariant to mitigate
     * possible timing attack. We need to find out the following without leaking any timing
     * information
     * 1) first byte is 0x0
     * 2) end of padding which is 0x1
     */
    for (i = hLen; i < dbLen; i++) {
        if (db[i] != 0) {
            break;
        }
    }
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        db[i] == 0x01);

    msgLen = dbLen - ++i;
    memcpy(to, db + i, msgLen);

    /* Update target buffer length as it now contains message */
    *tLen = msgLen;

end:
    return ret;
}


/*************************************
 **********   PSS padding   **********
 ************************************/

//------------------------------------------------------------------------------
keymaster_error_t padPkcs1Pss(
    uint8_t *pEM,
    const rsa_op_ctx_t *rsa_op_ctx,
    size_t sLen)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t MP[PSS_MP_PREFIX_LEN + MAX_HASH_LEN + MAX_SALT_SIZE] = {0};
    uint8_t H[MAX_HASH_LEN] = {0};
    uint8_t *maskedDB = NULL;
    uint8_t *salt = NULL;
    hashOpCtx_t mdData;
    size_t emLen, hLen, dbLen, psLen, tmpLen;

    /* 1. Input validation */
    CHECK_NOT_NULL(pEM);
    CHECK_NOT_NULL(rsa_op_ctx);

    emLen = rsa_op_ctx->modulusByteLen;
    hLen = rsa_op_ctx->buf_cursor;

    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        (emLen <= KM_RSA_BYTELEN_MAX) && (sLen <= MAX_SALT_SIZE));

    /**
     * 2. Let mHash = Hash(M), an octet string of length hLen
     *
     * The input provided as 'message to be encoded' is already hashed
     */

    /**
     *  3.  If emLen < hLen + sLen + 2, throw an error.
     *
     * Specification requires that emLen >= hLen + sLen + 2, because emLen has
     * structure EM=maskedDB || H || bc,  where maskedDB has length emLen-hLen-1,
     * this condition guarantees that maskedDB is at least single octet long.
     * This is mandatory, because DB must contain single byte 0x01
     */
    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        emLen >= hLen + sLen + 2);

    /**
     * 4.  Generate a random octet string salt of length sLen; if sLen = 0,
     *  then salt is the empty string.
     *
     * 5.  Let
     *     M' = (0x)00 00 00 00 00 00 00 00 || mHash || salt;
     *
     *  M' is an octet string of length 8 + hLen + sLen with eight
     *  initial zero octets.
     */
    memset(MP, 0, sizeof(MP));
    memcpy(MP + PSS_MP_PREFIX_LEN, rsa_op_ctx->buf, hLen);
    tmpLen = sLen;
    salt = MP + PSS_MP_PREFIX_LEN + hLen;

    CHECK_TLAPI_OK(tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM,
        salt, &tmpLen));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        tmpLen == sLen);

    /* 6.  Let H = Hash(M'), an octet string of length hLen. */
    tmpLen = hLen;
    CHECK_RESULT_OK(digest_begin(rsa_op_ctx->digest, &mdData));
    CHECK_RESULT_OK(digest_update(&mdData, MP, PSS_MP_PREFIX_LEN + hLen + sLen));
    CHECK_RESULT_OK(digest_finish(&mdData, H, &tmpLen));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        tmpLen == hLen);

    /**
     * 7.  Generate an octet string PS consisting of emLen - sLen - hLen - 2
     *     zero octets.  The length of PS may be 0.
     *
     * 8.  Let DB = PS || 0x01 || salt; DB is an octet string of length
     *     emLen - hLen - 1.
     */
    memset(pEM, 0, emLen);
    maskedDB = pEM;
    dbLen = emLen - hLen - 1;

    /* 9.  Let dbMask = MGF(H, emLen - hLen - 1) */
    CHECK_RESULT_OK(vMaskGeneratorFunction(maskedDB, dbLen, H, hLen, rsa_op_ctx->digest));

    /* 10. Let maskedDB = DB \xor dbMask */
    psLen = dbLen - sLen - 1;
    maskedDB[psLen] ^= 0x01;
    for (size_t i = 0; i < sLen; i++) {
        maskedDB[psLen + 1 + i] ^= salt[i];
    }

    /* NB. The key size is a multiple of 8, and emBits = 8emLen - 1. */

    /**
     * 11. Set the leftmost 8emLen - emBits bits of the leftmost octet in
     *     maskedDB to zero.
     */
    maskedDB[0] &= 0x7F;

    /* 12. Let EM = maskedDB || H || 0xbc */
    memcpy(pEM + dbLen, H, hLen);

    pEM[emLen - 1] = PSS_PADDING_TRAILER;

end:
    return ret;
}

//------------------------------------------------------------------------------
keymaster_error_t unpadPkcs1Pss(
    const uint8_t *pEM,
    const rsa_op_ctx_t *rsa_op_ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint8_t pWork[KM_RSA_BYTELEN_MAX];
    uint8_t hHash[MAX_HASH_LEN];
    hashOpCtx_t mdData;
    uint8_t padding[PSS_PADDING1_SIZE];
    size_t emLen, hLen, dbLen, tmpLen, pos;

    memset(pWork, 0 , sizeof(pWork));

    CHECK_NOT_NULL(pEM);
    CHECK_NOT_NULL(rsa_op_ctx);

    emLen = rsa_op_ctx->modulusByteLen;
    hLen = rsa_op_ctx->buf_cursor;
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        hLen <= MAX_HASH_LEN);

    /**
     * 1 & 2. message to be verified is a digest data. No need for digest calculation
     *        or maximum message length check (as stated in RFC 3447)
     */

    /**
     * 3.  If emLen < hLen + sLen + 2, throw an exception.
     */
    /* NB. We don't know sLen. But we insist it be at least 20. */
    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        emLen >= hLen + 22);

    /**
     * 4.  If the rightmost octet of EM does not have hexadecimal value
     *     0xbc, output "inconsistent" and stop.
     */
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        pEM[emLen - 1] == PSS_PADDING_TRAILER);

    /**
     * 5.  Let maskedDB be the leftmost emLen - hLen - 1 octets of EM, and
     *     let H be the next hLen octets.
     */
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        hLen + 1 <= emLen);
    dbLen = emLen - hLen - 1; // dbLen >= 21
    memcpy(hHash, pEM + dbLen, hLen);

    /* NB. The key size is a multiple of 8, and emBits = 8emLen - 1. */

    /**
     * 6.  If the leftmost 8emLen - emBits bits of the leftmost octet in
     *     maskedDB are not all equal to zero, output "inconsistent" and
     *     stop.
     */
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        (pEM[0x00] & 0x80) == 0);

    /* 7.  Let dbMask = MGF(H, emLen - hLen - 1). */
    CHECK_RESULT_OK(vMaskGeneratorFunction(pWork, dbLen, hHash, hLen, rsa_op_ctx->digest));

    /* 8.  Let DB = maskedDB \xor dbMask. */
    for (size_t i = 0; i < dbLen; i++) {
        pWork[i] ^= pEM[i];
    }

    /**
     *  9.  Set the leftmost 8emLen - emBits bits of the leftmost octet in DB
     *      to zero.
     */
    pWork[0] &= 0x7F;

    /**
     * 10. If the emLen - hLen - sLen - 2 leftmost octets of DB are not zero
     *     or if the octet at position emLen - hLen - sLen - 1 (the leftmost
     *     position is "position 1") does not have hexadecimal value 0x01,
     *     throw exception
     */
    /* NB. We don't know sLen. Count zeros. */
    pos = 0;
    while ((pos < dbLen - 20) && pWork[pos] == 0) {
        pos++;
    }
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        pos < dbLen - 20);
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        pWork[pos] == 0x01);
    /* NB. Now we know sLen = dbLen - 1 - pos */

    /**
     * 11.  Let salt be the last sLen octets of DB.
     *
     * 12.  Let
     *      M' = (0x)00 00 00 00 00 00 00 00 || mHash || salt ;
     *
     *      M' is an octet string of length 8 + hLen + sLen with eight
     *      initial zero octets.
     *
     * 13.  Let H' = Hash(M'), an octet string of length hLen.
     */
    tmpLen = hLen;
    memset(padding, 0, PSS_PADDING1_SIZE);
    CHECK_RESULT_OK(digest_begin(rsa_op_ctx->digest, &mdData));
    CHECK_RESULT_OK(digest_update(&mdData, padding, PSS_PADDING1_SIZE));
    CHECK_RESULT_OK(digest_update(&mdData, rsa_op_ctx->buf, hLen));
    CHECK_RESULT_OK(digest_update(&mdData, pWork + pos + 1, dbLen - pos - 1));
    CHECK_RESULT_OK(digest_finish(&mdData, pWork, &tmpLen));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        tmpLen == hLen);

    /* 14. Check that H = H' */
    CHECK_TRUE(KM_ERROR_VERIFICATION_FAILED,
        timeInvariant_memcmp(hHash, pWork, hLen) == 0);

end:
    return ret;
}

/**************************************
 *******   PKCS1 v1.5 padding   *******
 *************************************/

keymaster_error_t padPkcs1_1_5(
    uint8_t *to,
    const rsa_op_ctx_t *rsa_op_ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t modulusByteLen, mLen;
    size_t psLen, tmpLen;

    CHECK_NOT_NULL(to);
    CHECK_NOT_NULL(rsa_op_ctx);

    modulusByteLen = rsa_op_ctx->modulusByteLen;
    mLen = rsa_op_ctx->buf_cursor;

    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        mLen + 11 <= modulusByteLen);

    to[0] = 0;
    to[1] = PKCS_PUBLIC_KEY_BLOCKTYPE;
    psLen = modulusByteLen - mLen - 3;
    tmpLen = psLen;
    CHECK_TLAPI_OK(tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM,
        to + 2, &tmpLen));
    CHECK_TRUE(KM_ERROR_UNKNOWN_ERROR,
        tmpLen == psLen);
    /* Eliminate zeros from PS. Note that the method below gives a slight
     * bias towards 1s. The security impact of this is minor.
     */
    for (size_t i = 0; i < psLen; i++) {
        if (to[2 + i] == 0) {
            to[2 + i] = 1;
        }
    }
    to[2 + psLen] = 0;
    memcpy(2 + to + psLen + 1, rsa_op_ctx->buf, mLen);

end:
    return ret;
}

keymaster_error_t unpadPkcs1_1_5(
    uint8_t *to,
    uint32_t *tLen,
    const uint8_t *from,
    const rsa_op_ctx_t *rsa_op_ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t modulusByteLen, offset, mLen;

    CHECK_NOT_NULL(to);
    CHECK_NOT_NULL(tLen);
    CHECK_NOT_NULL(from);
    CHECK_NOT_NULL(rsa_op_ctx);

    modulusByteLen = rsa_op_ctx->modulusByteLen;

    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        from[0] == 0);
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        from[1] == PKCS_PUBLIC_KEY_BLOCKTYPE);

    /* Skip PS   */
    offset = 2;
    while ((offset < modulusByteLen) && (from[offset] != 0)) {
        offset++;
    }
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        offset < modulusByteLen);
    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        from[offset] == 0);
    offset++;

    mLen = modulusByteLen - offset;

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *tLen >= mLen);
    memcpy(to, from + offset, mLen);
    *tLen = mLen;

end:
    return ret;
}

//------------------------------------------------------------------------------
keymaster_error_t padPrivatePkcsV15(
    uint8_t *data,
    uint32_t msg_len,
    uint32_t modulusLength)
{
    keymaster_error_t ret = KM_ERROR_OK;
    uint32_t padLength;

    CHECK_NOT_NULL(data);

    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        msg_len <= modulusLength);
    padLength = modulusLength - msg_len;
    CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
        padLength >= MIN_PKCS1_V15_PAD_LENGTH);

    /* padding = 00 || BT || PS || 00 */
    data[0] = 0x00;
    data[1] = PKCS_PRIVATE_KEY_BLOCKTYPE;
    memset(data + 2, 0xFF, padLength - 3);
    data[padLength - 1] = 0x00;

end:
    return ret;
}

//------------------------------------------------------------------------------
keymaster_error_t encodeDIH(
    keymaster_digest_t  digestAlgorithm,
    uint8_t             *digest,
    uint32_t            sOffset,
    uint32_t            *DIHLength)
{
    keymaster_error_t ret = KM_ERROR_OK;
    const uint8_t *pDIH = NULL;
    *DIHLength = 0;

    CHECK_NOT_NULL(digest);
    CHECK_NOT_NULL(DIHLength);

    switch (digestAlgorithm) {
        case KM_DIGEST_NONE:
            break;
        case KM_DIGEST_MD5:
            pDIH = pu08MD5DigestInfoHeader;
            *DIHLength = sizeof(pu08MD5DigestInfoHeader);
            break;
        case KM_DIGEST_SHA1:
            pDIH = pu08SHA1DigestInfoHeader;
            *DIHLength = sizeof(pu08SHA1DigestInfoHeader);
            break;
        case KM_DIGEST_SHA_2_224:
            pDIH = pu08SHA224DigestInfoHeader;
            *DIHLength = sizeof(pu08SHA224DigestInfoHeader);
            break;
        case KM_DIGEST_SHA_2_256:
            pDIH = pu08SHA256DigestInfoHeader;
            *DIHLength = sizeof(pu08SHA256DigestInfoHeader);
            break;
        case KM_DIGEST_SHA_2_384:
            pDIH = pu08SHA384DigestInfoHeader;
            *DIHLength = sizeof(pu08SHA384DigestInfoHeader);
            break;
        case KM_DIGEST_SHA_2_512:
            pDIH = pu08SHA512DigestInfoHeader;
            *DIHLength = sizeof(pu08SHA512DigestInfoHeader);
            break;
        default:
            return KM_ERROR_UNSUPPORTED_DIGEST;
    }

    CHECK_TRUE(KM_ERROR_INVALID_ARGUMENT,
        sOffset >= *DIHLength);

    memcpy(digest + sOffset - *DIHLength, pDIH, *DIHLength);

end:
    return ret;
}
