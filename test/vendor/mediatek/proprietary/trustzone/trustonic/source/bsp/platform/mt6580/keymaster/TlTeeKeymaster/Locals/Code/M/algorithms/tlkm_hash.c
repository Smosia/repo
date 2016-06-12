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

#include "tlStd.h"
#include "tlkm_hash.h"
#include "keymaster_ta_defs.h"
#include "keymaster_util.h"

/* Generally helpful macros */
#define TLKM_LOAD32B(x, y) \
    {x = ((uint32_t)((y)[0] & 0xff)<<24) | \
         ((uint32_t)((y)[1] & 0xff)<<16) | \
         ((uint32_t)((y)[2] & 0xff)<<8) | \
         ((uint32_t)((y)[3] & 0xff));}
#define TLKM_LOAD64B(x, y) \
    {x = ((uint64_t)((y)[0] & 0xff)<<56) | \
         ((uint64_t)((y)[1] & 0xff)<<48) | \
         ((uint64_t)((y)[2] & 0xff)<<40) | \
         ((uint64_t)((y)[3] & 0xff)<<32) | \
         ((uint64_t)((y)[4] & 0xff)<<24) | \
         ((uint64_t)((y)[5] & 0xff)<<16) | \
         ((uint64_t)((y)[6] & 0xff)<<8) | \
         ((uint64_t)((y)[7] & 0xff));}
#define TLKM_STORE32B(x, y) \
    {(y)[0] = (uint8_t)(((x)>>24) & 0xff); \
     (y)[1] = (uint8_t)(((x)>>16) & 0xff); \
     (y)[2] = (uint8_t)(((x)>>8) & 0xff); \
     (y)[3] = (uint8_t)((x) & 0xff);}
#define TLKM_STORE64B(x, y) \
    {(y)[0] = (uint8_t)(((x)>>56) & 0xff); \
     (y)[1] = (uint8_t)(((x)>>48) & 0xff); \
     (y)[2] = (uint8_t)(((x)>>40) & 0xff); \
     (y)[3] = (uint8_t)(((x)>>32) & 0xff); \
     (y)[4] = (uint8_t)(((x)>>24) & 0xff); \
     (y)[5] = (uint8_t)(((x)>>16) & 0xff); \
     (y)[6] = (uint8_t)(((x)>>8) & 0xff); \
     (y)[7] = (uint8_t)((x) & 0xff);}
#define TLKM_LOAD32L(x, y) \
    {x = ((uint32_t)((y)[0] & 0xff)) | \
         ((uint32_t)((y)[1] & 0xff) << 8) | \
         ((uint32_t)((y)[2] & 0xff) << 16) | \
         ((uint32_t)((y)[3] & 0xff) << 24);}
#define TLKM_STORE32L(x, y) \
    {(y)[0] = (uint8_t)((x) & 0xff); \
     (y)[1] = (uint8_t)(((x) >> 8) & 0xff); \
     (y)[2] = (uint8_t)(((x) >> 16) & 0xff);  \
     (y)[3] = (uint8_t)(((x) >> 24) & 0xff);}
#define TLKM_STORE64L(x, y) \
    {(y)[0] = (uint8_t)((x) & 0xff); \
     (y)[1] = (uint8_t)(((x)>>8) & 0xff); \
     (y)[2] = (uint8_t)(((x)>>16) & 0xff); \
     (y)[3] = (uint8_t)(((x)>>24) & 0xff); \
     (y)[4] = (uint8_t)(((x)>>32) & 0xff); \
     (y)[5] = (uint8_t)(((x)>>40) & 0xff); \
     (y)[6] = (uint8_t)(((x)>>48) & 0xff); \
     (y)[7] = (uint8_t)(((x)>>56) & 0xff);}
#define TLKM_ROL(x, y) ((((uint32_t)(x)<<(uint32_t)((y) & 31)) | \
    (((uint32_t)(x) & 0xFFFFFFFFUL)>>(uint32_t)(32 - ((y) & 31)))) & 0xFFFFFFFFUL)
#define TLKM_ROR(x, y) (((((uint32_t)(x) & 0xFFFFFFFFUL)>>(uint32_t)((y) & 31)) | \
    ((uint32_t)(x)<<(uint32_t)(32 - ((y) & 31)))) & 0xFFFFFFFFUL)
#define Ch(x,y,z) (((x) & (y)) ^ ((~(x)) & (z)))
#define Maj(x,y,z) (((x) & (y)) ^ ((x) & (z)) ^ ((y) & (z)))

/* Helpful macros for MD5 and SHA1 */
#define F(x,y,z) ((x & y) | ((~(x)) & (z)))
#define G(x,y,z) (((x) & (z)) | ((y) & (~(z))))
#define H(x,y,z) ((x) ^ (y) ^ (z))
#define I(x,y,z) ((y) ^ ((x) | (~(z))))
#define J(x,y,z) (((x) & (y)) | ((x) & (z)) | ((y) & (z)))
#define FF(a, b, c, d, x, s, t) { \
    (a) += F((b), (c), (d)) + (x) + (uint32_t)(t); \
    (a) = TLKM_ROL((a), (s)); \
    (a) += (b); \
}
#define GG(a, b, c, d, x, s, t) { \
    (a) += G((b), (c), (d)) + (x) + (uint32_t)(t); \
    (a) = TLKM_ROL((a), (s)); \
    (a) += (b); \
}
#define HH(a, b, c, d, x, s, t) { \
    (a) += H((b), (c), (d)) + (x) + (uint32_t)(t); \
    (a) = TLKM_ROL((a), (s)); \
    (a) += (b); \
}
#define II(a, b, c, d, x, s, t) { \
    (a) += I((b), (c), (d)) + (x) + (uint32_t)(t); \
    (a) = TLKM_ROL((a), (s)); \
    (a) += (b); \
}

/* Helpful macros for SHA-256 */
#define Sigma0_256(x) (TLKM_ROR(x, 2) ^ TLKM_ROR(x, 13) ^ TLKM_ROR(x, 22))
#define Sigma1_256(x) (TLKM_ROR(x, 6) ^ TLKM_ROR(x, 11) ^ TLKM_ROR(x, 25))
#define sigma0_256(x) (TLKM_ROR(x, 7) ^ TLKM_ROR(x, 18) ^ (((x) & 0xffffffffUL) >> 3))
#define sigma1_256(x) (TLKM_ROR(x, 17) ^ TLKM_ROR(x, 19) ^ (((x) & 0xffffffffUL) >> 10))

/* Helpful macros for SHA-512 */
#define ROTR(x, n) ((x >> n) | (x << (sizeof(x)*8-n)))
#define Sigma0_512(x) (ROTR(x, 28) ^ ROTR(x, 34) ^ ROTR(x, 39))
#define Sigma1_512(x) (ROTR(x, 14) ^ ROTR(x, 18) ^ ROTR(x, 41))
#define sigma0_512(x) (ROTR(x, 1) ^ ROTR(x, 8) ^ (x >> 7))
#define sigma1_512(x) (ROTR(x, 19) ^ ROTR(x, 61) ^ (x >> 6))

/* SHA-256 constant */
static const uint32_t K256[64] = {
    0x428a2f98UL, 0x71374491UL, 0xb5c0fbcfUL, 0xe9b5dba5UL, 0x3956c25bUL,
    0x59f111f1UL, 0x923f82a4UL, 0xab1c5ed5UL, 0xd807aa98UL, 0x12835b01UL,
    0x243185beUL, 0x550c7dc3UL, 0x72be5d74UL, 0x80deb1feUL, 0x9bdc06a7UL,
    0xc19bf174UL, 0xe49b69c1UL, 0xefbe4786UL, 0x0fc19dc6UL, 0x240ca1ccUL,
    0x2de92c6fUL, 0x4a7484aaUL, 0x5cb0a9dcUL, 0x76f988daUL, 0x983e5152UL,
    0xa831c66dUL, 0xb00327c8UL, 0xbf597fc7UL, 0xc6e00bf3UL, 0xd5a79147UL,
    0x06ca6351UL, 0x14292967UL, 0x27b70a85UL, 0x2e1b2138UL, 0x4d2c6dfcUL,
    0x53380d13UL, 0x650a7354UL, 0x766a0abbUL, 0x81c2c92eUL, 0x92722c85UL,
    0xa2bfe8a1UL, 0xa81a664bUL, 0xc24b8b70UL, 0xc76c51a3UL, 0xd192e819UL,
    0xd6990624UL, 0xf40e3585UL, 0x106aa070UL, 0x19a4c116UL, 0x1e376c08UL,
    0x2748774cUL, 0x34b0bcb5UL, 0x391c0cb3UL, 0x4ed8aa4aUL, 0x5b9cca4fUL,
    0x682e6ff3UL, 0x748f82eeUL, 0x78a5636fUL, 0x84c87814UL, 0x8cc70208UL,
    0x90befffaUL, 0xa4506cebUL, 0xbef9a3f7UL, 0xc67178f2UL
};

/* SHA-512 constant */
static const uint64_t K512[] = {
    0x428a2f98d728ae22ULL, 0x7137449123ef65cdULL, 0xb5c0fbcfec4d3b2fULL, 0xe9b5dba58189dbbcULL,
    0x3956c25bf348b538ULL, 0x59f111f1b605d019ULL, 0x923f82a4af194f9bULL, 0xab1c5ed5da6d8118ULL,
    0xd807aa98a3030242ULL, 0x12835b0145706fbeULL, 0x243185be4ee4b28cULL, 0x550c7dc3d5ffb4e2ULL,
    0x72be5d74f27b896fULL, 0x80deb1fe3b1696b1ULL, 0x9bdc06a725c71235ULL, 0xc19bf174cf692694ULL,
    0xe49b69c19ef14ad2ULL, 0xefbe4786384f25e3ULL, 0x0fc19dc68b8cd5b5ULL, 0x240ca1cc77ac9c65ULL,
    0x2de92c6f592b0275ULL, 0x4a7484aa6ea6e483ULL, 0x5cb0a9dcbd41fbd4ULL, 0x76f988da831153b5ULL,
    0x983e5152ee66dfabULL, 0xa831c66d2db43210ULL, 0xb00327c898fb213fULL, 0xbf597fc7beef0ee4ULL,
    0xc6e00bf33da88fc2ULL, 0xd5a79147930aa725ULL, 0x06ca6351e003826fULL, 0x142929670a0e6e70ULL,
    0x27b70a8546d22ffcULL, 0x2e1b21385c26c926ULL, 0x4d2c6dfc5ac42aedULL, 0x53380d139d95b3dfULL,
    0x650a73548baf63deULL, 0x766a0abb3c77b2a8ULL, 0x81c2c92e47edaee6ULL, 0x92722c851482353bULL,
    0xa2bfe8a14cf10364ULL, 0xa81a664bbc423001ULL, 0xc24b8b70d0f89791ULL, 0xc76c51a30654be30ULL,
    0xd192e819d6ef5218ULL, 0xd69906245565a910ULL, 0xf40e35855771202aULL, 0x106aa07032bbd1b8ULL,
    0x19a4c116b8d2d0c8ULL, 0x1e376c085141ab53ULL, 0x2748774cdf8eeb99ULL, 0x34b0bcb5e19b48a8ULL,
    0x391c0cb3c5c95a63ULL, 0x4ed8aa4ae3418acbULL, 0x5b9cca4f7763e373ULL, 0x682e6ff3d6b2b8a3ULL,
    0x748f82ee5defb2fcULL, 0x78a5636f43172f60ULL, 0x84c87814a1f0ab72ULL, 0x8cc702081a6439ecULL,
    0x90befffa23631e28ULL, 0xa4506cebde82bde9ULL, 0xbef9a3f7b2c67915ULL, 0xc67178f2e372532bULL,
    0xca273eceea26619cULL, 0xd186b8c721c0c207ULL, 0xeada7dd6cde0eb1eULL, 0xf57d4f7fee6ed178ULL,
    0x06f067aa72176fbaULL, 0x0a637dc5a2c898a6ULL, 0x113f9804bef90daeULL, 0x1b710b35131c471bULL,
    0x28db77f523047d84ULL, 0x32caab7b40c72493ULL, 0x3c9ebe0a15c9bebcULL, 0x431d67c49c100d4cULL,
    0x4cc5d4becb3e42b6ULL, 0x597f299cfc657e2aULL, 0x5fcb6fab3ad6faecULL, 0x6c44198c4a475817ULL
};

/**
 * Increase the length counter by a specified number of bits.
 *
 * @param ctx hash context
 * @param n_bits number of bits to add to the length counter
 *
 * @return KM_ERROR_OK or error
 */
static keymaster_error_t update_length(
    hashOpCtx_t *ctx,
    uint64_t n_bits)
{
    keymaster_error_t ret = KM_ERROR_OK;

    switch (ctx->algorithm) {
        case KM_DIGEST_MD5:
            CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                ctx->md5OpCtx.length + n_bits >= n_bits);
            ctx->md5OpCtx.length += n_bits;
            break;
        case KM_DIGEST_SHA1:
            CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                ctx->sha1OpCtx.length + n_bits >= n_bits);
            ctx->sha1OpCtx.length += n_bits;
            break;
        case KM_DIGEST_SHA_2_224:
        case KM_DIGEST_SHA_2_256:
            CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                ctx->sha2_256OpCtx.length + n_bits >= n_bits);
            ctx->sha2_256OpCtx.length += n_bits;
            break;
        case KM_DIGEST_SHA_2_384:
        case KM_DIGEST_SHA_2_512:
            if (ctx->sha2_512OpCtx.length_low > 0xFFFFFFFFFFFFFFFFULL - n_bits) {
                CHECK_TRUE(KM_ERROR_INVALID_INPUT_LENGTH,
                    ctx->sha2_512OpCtx.length_high < 0xFFFFFFFFFFFFFFFFULL);
                ctx->sha2_512OpCtx.length_high++;
                ctx->sha2_512OpCtx.length_low = n_bits - 0xFFFFFFFFFFFFFFFFULL + ctx->sha2_512OpCtx.length_low - 1;
            } else {
                ctx->sha2_512OpCtx.length_low += n_bits;
            }
            break;
        default:
            return KM_ERROR_UNSUPPORTED_DIGEST;
    }

end:
    return ret;
}

static void md5_begin(
    md5OpCtx_t *ctx)
{
    assert(ctx != NULL);

    ctx->h[0] = 0x67452301UL;
    ctx->h[1] = 0xefcdab89UL;
    ctx->h[2] = 0x98badcfeUL;
    ctx->h[3] = 0x10325476UL;
    ctx->length = 0;
}

static void sha1_begin(
    sha1OpCtx_t *ctx)
{
    assert(ctx != NULL);

    ctx->h[0] = 0x67452301UL;
    ctx->h[1] = 0xefcdab89UL;
    ctx->h[2] = 0x98badcfeUL;
    ctx->h[3] = 0x10325476UL;
    ctx->h[4] = 0xc3d2e1f0UL;
    ctx->length = 0;
}

static void sha2_256_begin(
    sha2_256OpCtx_t *ctx,
    bool is_224)
{
    assert(ctx != NULL);

    if (is_224) {
        ctx->h[0] = 0xc1059ed8UL;
        ctx->h[1] = 0x367cd507UL;
        ctx->h[2] = 0x3070dd17UL;
        ctx->h[3] = 0xf70e5939UL;
        ctx->h[4] = 0xffc00b31UL;
        ctx->h[5] = 0x68581511UL;
        ctx->h[6] = 0x64f98fa7UL;
        ctx->h[7] = 0xbefa4fa4UL;
    } else {
        ctx->h[0] = 0x6a09e667UL;
        ctx->h[1] = 0xbb67ae85UL;
        ctx->h[2] = 0x3c6ef372UL;
        ctx->h[3] = 0xa54ff53aUL;
        ctx->h[4] = 0x510e527fUL;
        ctx->h[5] = 0x9b05688cUL;
        ctx->h[6] = 0x1f83d9abUL;
        ctx->h[7] = 0x5be0cd19UL;
    }
    ctx->length = 0;
    ctx->is_224 = is_224;
}

static void sha2_512_begin(
    sha2_512OpCtx_t *ctx,
    bool is_384)
{
    assert(ctx != NULL);

    if (is_384) {
        ctx->h[0] = 0xcbbb9d5dc1059ed8ULL;
        ctx->h[1] = 0x629a292a367cd507ULL;
        ctx->h[2] = 0x9159015a3070dd17ULL;
        ctx->h[3] = 0x152fecd8f70e5939ULL;
        ctx->h[4] = 0x67332667ffc00b31ULL;
        ctx->h[5] = 0x8eb44a8768581511ULL;
        ctx->h[6] = 0xdb0c2e0d64f98fa7ULL;
        ctx->h[7] = 0x47b5481dbefa4fa4ULL;
    } else {
        ctx->h[0] = 0x6a09e667f3bcc908ULL;
        ctx->h[1] = 0xbb67ae8584caa73bULL;
        ctx->h[2] = 0x3c6ef372fe94f82bULL;
        ctx->h[3] = 0xa54ff53a5f1d36f1ULL;
        ctx->h[4] = 0x510e527fade682d1ULL;
        ctx->h[5] = 0x9b05688c2b3e6c1fULL;
        ctx->h[6] = 0x1f83d9abfb41bd6bULL;
        ctx->h[7] = 0x5be0cd19137e2179ULL;
    }
    ctx->length_low = 0;
    ctx->length_high = 0;
    ctx->is_384 = is_384;
}

keymaster_error_t digest_begin(
    keymaster_digest_t algorithm,
    hashOpCtx_t *ctx)
{
    keymaster_error_t ret = KM_ERROR_OK;

    CHECK_NOT_NULL(ctx);

    ctx->algorithm = algorithm;
    ctx->curlen = 0;
    memset(ctx->buf, 0, 128);

    switch(algorithm) {
        case KM_DIGEST_MD5:
            ctx->block_size = 64;
            ctx->length_size = 8;
            ctx->digest_size = 16;
            md5_begin(&ctx->md5OpCtx);
            break;
        case KM_DIGEST_SHA1:
            ctx->block_size = 64;
            ctx->length_size = 8;
            ctx->digest_size = 20;
            sha1_begin(&ctx->sha1OpCtx);
            break;
        case KM_DIGEST_SHA_2_224:
            ctx->block_size = 64;
            ctx->length_size = 8;
            ctx->digest_size = 28;
            sha2_256_begin(&ctx->sha2_256OpCtx, true);
            break;
        case KM_DIGEST_SHA_2_256:
            ctx->block_size = 64;
            ctx->length_size = 8;
            ctx->digest_size = 32;
            sha2_256_begin(&ctx->sha2_256OpCtx, false);
            break;
        case KM_DIGEST_SHA_2_384:
            ctx->block_size = 128;
            ctx->length_size = 16;
            ctx->digest_size = 48;
            sha2_512_begin(&ctx->sha2_512OpCtx, true);
            break;
        case KM_DIGEST_SHA_2_512:
            ctx->block_size = 128;
            ctx->length_size = 16;
            ctx->digest_size = 64;
            sha2_512_begin(&ctx->sha2_512OpCtx, false);
            break;
        default:
            ret = KM_ERROR_UNSUPPORTED_DIGEST;
            goto end;
    }

end:
    return ret;
}

static void md5_process(
    md5OpCtx_t *ctx,
    const uint8_t *buf)
{
    assert(ctx != NULL);

    uint32_t a, b, c, d, i, X[16];

    for (i = 0; i < 16; i++) {
      TLKM_LOAD32L(X[i], buf + (4*i));
    }

    a = ctx->h[0];
    b = ctx->h[1];
    c = ctx->h[2];
    d = ctx->h[3];

    FF(a, b, c, d, X[0], 7, 0xd76aa478UL);
    FF(d, a, b, c, X[1], 12, 0xe8c7b756UL);
    FF(c, d, a, b, X[2], 17, 0x242070dbUL);
    FF(b, c, d, a, X[3], 22, 0xc1bdceeeUL);
    FF(a, b, c, d, X[4], 7, 0xf57c0fafUL);
    FF(d, a, b, c, X[5], 12, 0x4787c62aUL);
    FF(c, d, a, b, X[6], 17, 0xa8304613UL);
    FF(b, c, d, a, X[7], 22, 0xfd469501UL);
    FF(a, b, c, d, X[8], 7, 0x698098d8UL);
    FF(d, a, b, c, X[9], 12, 0x8b44f7afUL);
    FF(c, d, a, b, X[10], 17, 0xffff5bb1UL);
    FF(b, c, d, a, X[11], 22, 0x895cd7beUL);
    FF(a, b, c, d, X[12], 7, 0x6b901122UL);
    FF(d, a, b, c, X[13], 12, 0xfd987193UL);
    FF(c, d, a, b, X[14], 17, 0xa679438eUL);
    FF(b, c, d, a, X[15], 22, 0x49b40821UL);

    GG(a, b, c, d, X[1], 5, 0xf61e2562UL);
    GG(d, a, b, c, X[6], 9, 0xc040b340UL);
    GG(c, d, a, b, X[11], 14, 0x265e5a51UL);
    GG(b, c, d, a, X[0], 20, 0xe9b6c7aaUL);
    GG(a, b, c, d, X[5], 5, 0xd62f105dUL);
    GG(d, a, b, c, X[10], 9, 0x2441453UL);
    GG(c, d, a, b, X[15], 14, 0xd8a1e681UL);
    GG(b, c, d, a, X[4], 20, 0xe7d3fbc8UL);
    GG(a, b, c, d, X[9], 5, 0x21e1cde6UL);
    GG(d, a, b, c, X[14], 9, 0xc33707d6UL);
    GG(c, d, a, b, X[3], 14, 0xf4d50d87UL);
    GG(b, c, d, a, X[8], 20, 0x455a14edUL);
    GG(a, b, c, d, X[13], 5, 0xa9e3e905UL);
    GG(d, a, b, c, X[2], 9, 0xfcefa3f8UL);
    GG(c, d, a, b, X[7], 14, 0x676f02d9UL);
    GG(b, c, d, a, X[12], 20, 0x8d2a4c8aUL);

    HH(a, b, c, d, X[5], 4, 0xfffa3942UL);
    HH(d, a, b, c, X[8], 11, 0x8771f681UL);
    HH(c, d, a, b, X[11], 16, 0x6d9d6122UL);
    HH(b, c, d, a, X[14], 23, 0xfde5380cUL);
    HH(a, b, c, d, X[1], 4, 0xa4beea44UL);
    HH(d, a, b, c, X[4], 11, 0x4bdecfa9UL);
    HH(c, d, a, b, X[7], 16, 0xf6bb4b60UL);
    HH(b, c, d, a, X[10], 23, 0xbebfbc70UL);
    HH(a, b, c, d, X[13], 4, 0x289b7ec6UL);
    HH(d, a, b, c, X[0], 11, 0xeaa127faUL);
    HH(c, d, a, b, X[3], 16, 0xd4ef3085UL);
    HH(b, c, d, a, X[6], 23, 0x4881d05UL);
    HH(a, b, c, d, X[9], 4, 0xd9d4d039UL);
    HH(d, a, b, c, X[12], 11, 0xe6db99e5UL);
    HH(c, d, a, b, X[15], 16, 0x1fa27cf8UL);
    HH(b, c, d, a, X[2], 23, 0xc4ac5665UL);

    II(a, b, c, d, X[0], 6, 0xf4292244UL);
    II(d, a, b, c, X[7], 10, 0x432aff97UL);
    II(c, d, a, b, X[14], 15, 0xab9423a7UL);
    II(b, c, d, a, X[5], 21, 0xfc93a039UL);
    II(a, b, c, d, X[12], 6, 0x655b59c3UL);
    II(d, a, b, c, X[3], 10, 0x8f0ccc92UL);
    II(c, d, a, b, X[10], 15, 0xffeff47dUL);
    II(b, c, d, a, X[1], 21, 0x85845dd1UL);
    II(a, b, c, d, X[8], 6, 0x6fa87e4fUL);
    II(d, a, b, c, X[15], 10, 0xfe2ce6e0UL);
    II(c, d, a, b, X[6], 15, 0xa3014314UL);
    II(b, c, d, a, X[13], 21, 0x4e0811a1UL);
    II(a, b, c, d, X[4], 6, 0xf7537e82UL);
    II(d, a, b, c, X[11], 10, 0xbd3af235UL);
    II(c, d, a, b, X[2], 15, 0x2ad7d2bbUL);
    II(b, c, d, a, X[9], 21, 0xeb86d391UL);

    ctx->h[0] += a;
    ctx->h[1] += b;
    ctx->h[2] += c;
    ctx->h[3] += d;
}

static void sha1_process(
    sha1OpCtx_t *ctx,
    const uint8_t *buf)
{
    assert(ctx != NULL);

    uint32_t a, b, c, d, e, W[80], i, temp;

    for (i = 0; i < 16; i++) {
        TLKM_LOAD32B(W[i], buf + (4*i));
    }

    for (i = 16; i < 80; i++) {
        W[i] = TLKM_ROL(W[i-3] ^ W[i-8] ^ W[i-14] ^ W[i-16], 1);
    }

    a = ctx->h[0];
    b = ctx->h[1];
    c = ctx->h[2];
    d = ctx->h[3];
    e = ctx->h[4];

    for (i = 0; i < 20; i++) {
        temp = TLKM_ROL(a, 5) + F(b, c, d) + e + W[i] + 0x5a827999UL;
        e = d;
        d = c;
        c = TLKM_ROL(b, 30);
        b = a;
        a = temp;
    }
    for (i = 20; i < 40; i++) {
        temp = TLKM_ROL(a, 5) + H(b, c, d) + e + W[i] + 0x6ed9eba1UL;
        e = d;
        d = c;
        c = TLKM_ROL(b, 30);
        b = a;
        a = temp;
    }
    for (i = 40; i < 60; i++) {
        temp = TLKM_ROL(a, 5) + J(b, c, d) + e + W[i] + 0x8f1bbcdcUL;
        e = d;
        d = c;
        c = TLKM_ROL(b, 30);
        b = a;
        a = temp;
    }
    for (i = 60; i < 80; i++) {
        temp = TLKM_ROL(a, 5) + H(b, c, d) + e + W[i] + 0xca62c1d6UL;
        e = d;
        d = c;
        c = TLKM_ROL(b, 30);
        b = a;
        a = temp;
    }

    ctx->h[0] += a;
    ctx->h[1] += b;
    ctx->h[2] += c;
    ctx->h[3] += d;
    ctx->h[4] += e;
}

static void sha256_process(
    sha2_256OpCtx_t *ctx,
    const uint8_t *buf)
{
    assert(ctx != NULL);

    uint32_t W[64], i, a, b, c, d, e, f, g, h, T1, T2, S[8];

    for (i = 0; i < 16; i++) {
        TLKM_LOAD32B(W[i], buf + (4*i));
    }
    for (i = 16; i < 64; i++) {
        W[i] = sigma1_256(W[i-2]) + W[i-7] + sigma0_256(W[i-15]) + W[i-16];
    }

    a = ctx->h[0];
    b = ctx->h[1];
    c = ctx->h[2];
    d = ctx->h[3];
    e = ctx->h[4];
    f = ctx->h[5];
    g = ctx->h[6];
    h = ctx->h[7];

    for (i = 0; i < 8; i++) {
        S[i] = ctx->h[i];
    }

    for (i = 0; i < 64; i++) {
        T1 = h + Sigma1_256(e) + Ch(e, f, g) + K256[i] + W[i];
        T2 = Sigma0_256(a) + Maj(a, b, c);
        h = g;
        g = f;
        f = e;
        e = d + T1;
        d = c;
        c = b;
        b = a;
        a = T1 + T2;
    }

    ctx->h[0] = a + S[0];
    ctx->h[1] = b + S[1];
    ctx->h[2] = c + S[2];
    ctx->h[3] = d + S[3];
    ctx->h[4] = e + S[4];
    ctx->h[5] = f + S[5];
    ctx->h[6] = g + S[6];
    ctx->h[7] = h + S[7];
}

static void sha512_process(
    sha2_512OpCtx_t *ctx,
    const uint8_t *buf)
{
    assert(ctx != NULL);

    uint64_t W[80], i, a, b, c, d, e, f, g, h, T1, T2, S[8];

    for (i = 0; i < 16; i++) {
        TLKM_LOAD64B(W[i], buf + (sizeof(uint64_t)*i));
    }
    for (i = 16; i < 80; i++) {
        W[i] = sigma1_512(W[i-2]) + W[i-7] + sigma0_512(W[i-15]) + W[i-16];
    }

    a = ctx->h[0];
    b = ctx->h[1];
    c = ctx->h[2];
    d = ctx->h[3];
    e = ctx->h[4];
    f = ctx->h[5];
    g = ctx->h[6];
    h = ctx->h[7];

    for (i = 0; i < 8; i++) {
        S[i] = ctx->h[i];
    }

    for (i = 0; i < 80; i++) {
        T1 = h + Sigma1_512(e) + Ch(e, f, g) + K512[i] + W[i];
        T2 = Sigma0_512(a) + Maj(a, b, c);
        h = g;
        g = f;
        f = e;
        e = d + T1;
        d = c;
        c = b;
        b = a;
        a = T1 + T2;
    }

    ctx->h[0] = a + S[0];
    ctx->h[1] = b + S[1];
    ctx->h[2] = c + S[2];
    ctx->h[3] = d + S[3];
    ctx->h[4] = e + S[4];
    ctx->h[5] = f + S[5];
    ctx->h[6] = g + S[6];
    ctx->h[7] = h + S[7];
}

static keymaster_error_t digest_process(
    hashOpCtx_t *ctx)
{
    assert(ctx != NULL);

    switch (ctx->algorithm) {
        case KM_DIGEST_MD5:
            md5_process(&ctx->md5OpCtx, ctx->buf);
            break;
        case KM_DIGEST_SHA1:
            sha1_process(&ctx->sha1OpCtx, ctx->buf);
            break;
        case KM_DIGEST_SHA_2_224:
        case KM_DIGEST_SHA_2_256:
            sha256_process(&ctx->sha2_256OpCtx, ctx->buf);
            break;
        case KM_DIGEST_SHA_2_384:
        case KM_DIGEST_SHA_2_512:
            sha512_process(&ctx->sha2_512OpCtx, ctx->buf);
            break;
        default:
            return KM_ERROR_UNSUPPORTED_DIGEST;
    }

    return KM_ERROR_OK;
}

keymaster_error_t digest_update(
    hashOpCtx_t *ctx,
    const uint8_t *input,
    size_t input_len)
{
    keymaster_error_t ret = KM_ERROR_OK;

    CHECK_NOT_NULL(ctx);
    if (input_len > 0) {
        CHECK_NOT_NULL(input);
    }

    while (input_len > 0) {
        if ((ctx->curlen + input_len) >= ctx->block_size) {
            memcpy(ctx->buf + ctx->curlen, input, ctx->block_size - ctx->curlen);
            CHECK_RESULT_OK(digest_process(ctx));
            CHECK_RESULT_OK(update_length(ctx, 8 * ctx->block_size));
            input += (ctx->block_size - ctx->curlen);
            input_len -= (ctx->block_size - ctx->curlen);
            ctx->curlen = 0;
        } else {
            memcpy(ctx->buf + ctx->curlen, input, input_len);
            ctx->curlen += input_len;
            input_len = 0;
        }
    }

end:
    return ret;
}

static void store_length(
    hashOpCtx_t *ctx)
{
    assert(ctx != NULL);

    switch (ctx->algorithm) {
        case KM_DIGEST_MD5:
            /* block size 64, length size 8, little-endian */
            TLKM_STORE64L(ctx->md5OpCtx.length, ctx->buf + 56);
            break;
        case KM_DIGEST_SHA1:
            /* block size 64, length size 8, big-endian */
            TLKM_STORE64B(ctx->sha1OpCtx.length, ctx->buf + 56);
            break;
        case KM_DIGEST_SHA_2_224:
        case KM_DIGEST_SHA_2_256:
            /* block size 64, length size 8, big-endian */
            TLKM_STORE64B(ctx->sha2_256OpCtx.length, ctx->buf + 56);
            break;
        case KM_DIGEST_SHA_2_384:
        case KM_DIGEST_SHA_2_512:
            /* block size 128, length size 16, big-endian */
            TLKM_STORE64B(ctx->sha2_512OpCtx.length_high, ctx->buf + 112);
            TLKM_STORE64B(ctx->sha2_512OpCtx.length_low, ctx->buf + 120);
            break;
        default:
            assert(false);
    }
}

static void get_result(
    hashOpCtx_t *ctx,
    uint8_t *digest)
{
    int i, n_words;

    assert(ctx != NULL);

    switch (ctx->algorithm) {
        case KM_DIGEST_MD5:
            for  (i = 0; i < 4; i++) {
                TLKM_STORE32L(ctx->md5OpCtx.h[i], digest + 4*i);
            }
            break;
        case KM_DIGEST_SHA1:
            for (i = 0; i < 5; i++) {
                TLKM_STORE32B(ctx->sha1OpCtx.h[i], digest + 4*i);
            }
            break;
        case KM_DIGEST_SHA_2_224:
        case KM_DIGEST_SHA_2_256:
            n_words = ctx->sha2_256OpCtx.is_224 ? 7 : 8;
            for (i = 0; i < n_words; i++) {
                TLKM_STORE32B(ctx->sha2_256OpCtx.h[i], digest + 4*i);
            }
            break;
        case KM_DIGEST_SHA_2_384:
        case KM_DIGEST_SHA_2_512:
            n_words = ctx->sha2_512OpCtx.is_384 ? 6 : 8;
            for (i = 0; i < n_words; i++) {
                TLKM_STORE64B(ctx->sha2_512OpCtx.h[i], digest + 8*i);
            }
            break;
        default:
            assert(false);
    }
}

keymaster_error_t digest_finish(
    hashOpCtx_t *ctx,
    uint8_t *digest,
    size_t *digest_len)
{
    keymaster_error_t ret = KM_ERROR_OK;

    CHECK_NOT_NULL(ctx);
    CHECK_NOT_NULL(digest);
    CHECK_NOT_NULL(digest_len);

    CHECK_TRUE(KM_ERROR_INSUFFICIENT_BUFFER_SPACE,
        *digest_len >= ctx->digest_size);

    CHECK_RESULT_OK(update_length(ctx, 8 * ctx->curlen));

    ctx->buf[ctx->curlen++] = 0x80;
    if (ctx->curlen > (ctx->block_size - ctx->length_size)) {
        while (ctx->curlen < ctx->block_size) {
            ctx->buf[ctx->curlen++] = 0;
        }
        CHECK_RESULT_OK(digest_process(ctx));
        ctx->curlen = 0;
    }
    while (ctx->curlen < (ctx->block_size - ctx->length_size)) {
        ctx->buf[ctx->curlen++] = 0;
    }
    store_length(ctx);
    CHECK_RESULT_OK(digest_process(ctx));

    get_result(ctx, digest);
    *digest_len = ctx->digest_size;

end:
    return ret;
}

size_t digest_length(
    keymaster_digest_t algorithm)
{
    switch(algorithm)
    {
        case KM_DIGEST_MD5:         return 16;
        case KM_DIGEST_SHA1:        return 20;
        case KM_DIGEST_SHA_2_224:   return 28;
        case KM_DIGEST_SHA_2_256:   return 32;
        case KM_DIGEST_SHA_2_384:   return 48;
        case KM_DIGEST_SHA_2_512:   return 64;
        default:                    return  0;
    }
}

size_t digest_blocksize(
    keymaster_digest_t digest)
{
    switch (digest) {
        case KM_DIGEST_MD5:
        case KM_DIGEST_SHA1:
        case KM_DIGEST_SHA_2_224:
        case KM_DIGEST_SHA_2_256:
            return 64;
        case KM_DIGEST_SHA_2_384:
        case KM_DIGEST_SHA_2_512:
            return 128;
        default:
            return 0;
    }
}
