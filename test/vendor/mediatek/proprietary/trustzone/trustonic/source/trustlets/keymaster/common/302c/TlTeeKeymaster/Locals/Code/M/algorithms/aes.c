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
#include "TlApi/TlApi.h"

#include "aes.h"
#include "aes-common.h"
#include "aes-core.h"


/*=============================================================================
 *
 * Static Functions
 *
 *===========================================================================*/

/* --------------------- Symmetric algorithms ----------------------- */

static void static_doCore(
    const aesKeyCtx_t *keyCtx,
    bool encrypt, // true for encryption, false for decryption
    const uint8_t *input,
    uint8_t *output)
{
    if (encrypt) {
        aesImplAesCipher(keyCtx, input, output);
    } else {
        aesImplAesInvCipher(keyCtx, input, output);
    }
}

static void static_doEcb(
    const aesKeyCtx_t *keyCtx,
    bool encrypt,
    const uint8_t *input,
    uint8_t *output,
    uint32_t numBlocks)
{
    for (uint32_t i = 0; i < numBlocks; i++) {
        static_doCore(keyCtx, encrypt, input, output);
        input += AES_BLOCK_SIZE;
        output += AES_BLOCK_SIZE;
    }
}


/**
 * Performs CTR mode.
 * In CTR, CTR Encrypt = CTR Decrypt,
 * except that for CTR encrypt, the input is plaintext,
 * whereas for CTR decrypt, the input is ciphertext.
 *
 * CTR Encrypt: Ci = Pi XOR ENC(Counter-i), with Counter-0=IV.
 * and Counter-i+1 = Counter-i ++
 *
 * CTR Decrypt: Pi = Ci XOR ENC(Counter-i), with Counter-0=IV.
 * and Counter-i+1 = Counter-i ++
 *
 * See NIST SP 800 - 38A.
 *
 * @param input     plain text to encrypt
 * @param output    cipher text. The content is updated, but
 *                   the pointer is left unchanged.
 * @param inputLen  length of \p input
 */
static void static_doCtr(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint8_t *output,
    uint32_t inputLen)
{
    uint8_t *counter = opCtx->opModeCtx.ctrCtx.counter;
    uint8_t *block = opCtx->opModeCtx.ctrCtx.block;
    unsigned int *offset = &opCtx->opModeCtx.ctrCtx.offset;

    assert(opCtx->algorithm == AES_MODE_CTR);

    for (uint32_t i = 0; i < inputLen; i++) {
        if (*offset == 0) { // compute next Enc(counter)
            static_doCore(&opCtx->keyCtx, true, counter, block);
        }

        // compute output byte and increment offset
        output[i] = input[i] ^ block[*offset];
        *offset = (*offset + 1) % AES_BLOCK_SIZE;

        if (*offset == 0) { // increment counter
            for (int32_t k = AES_BLOCK_SIZE - 1; k >= 0; k--) {
                counter[k] = (counter[k] + 1) & 0xFF;
                if (counter[k] != 0) {
                    break;
                }
            }
        }
    }
}

/**
 * CBC-Encrypt: Ci = E(Pi XOR Ci-1), with C0 = IV.
 * See NIST SP 800 - 38A.
 *
 * opCtx->opModeCtx.iv is updated
 *
 * @param opCtx     operation context
 * @param input     plain text to encrypt - must be a multiple
 *                   of cipher's block size
 * @param output    cipher text. The content is updated, but
 *                   the pointer is left unchanged.
 * @param numBlocks  number of cipher's blocks in input.
 */
static void static_doCbcEncrypt(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint8_t *output,
    uint32_t numBlocks)
{
    uint8_t *temp;
    uint32_t i;
    uint32_t j;
    uint8_t block[AES_BLOCK_SIZE];

    temp = opCtx->opModeCtx.iv;
    for (i = 0; i < numBlocks; i++) {
        /* compute Pi XOR Ci-1 */
        for (j = 0; j < AES_BLOCK_SIZE; j++) {
            block[j] = input[j] ^ temp[j];
        }

        /* compute E(Pi XOR Ci-1) */
        static_doCore(&opCtx->keyCtx, true, block, output);

        /* Ci is the next Ci-1 */
        temp = output;

        /* process next block (this does not modify pointers
           when we exit the function) */
        input += AES_BLOCK_SIZE;
        output += AES_BLOCK_SIZE;
    }

    /* copy the last Ci-1 into opCtx->opModeCtx.iv */
    memmove(opCtx->opModeCtx.iv, temp, AES_BLOCK_SIZE);
}

/**
* CBC Decrypt: Pi = D(Ci) XOR Ci-1, with C0=IV
* See NIST SP 800 - 38A.
*
* opCtx->opModeCtx.iv is updated
*
* @param opCtx     operation context
* @param input     cipher text to decrypt - must be a multiple
*                   of cipher's block size
* @param output    plain text. The content is updated, but
*                   the pointer is left unchanged.
* @param numBlocks  number of cipher's blocks in input.
*
*/
static void static_doCbcDecrypt(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint8_t *output,
    uint32_t numBlocks)
{
    uint8_t block[AES_BLOCK_SIZE];
    uint32_t i;
    uint32_t j;

    for (i = 0; i < numBlocks; i++) {
        /* computes D(Ci) and store it to the temporary block */
        static_doCore(&opCtx->keyCtx, false, input, block);

        /* computes D(Ci) XOR Ci-1 in place */
        for (j = 0; j < AES_BLOCK_SIZE; j++) {
            block[j] ^= opCtx->opModeCtx.iv[j];
        }

        /* Store the Ci in the IV. Note: we cannot merely reuse the plaintext buffer
           because maybe input == output */
        memmove(opCtx->opModeCtx.iv, input, AES_BLOCK_SIZE);

        /* Store the plaintext in the destination buffer */
        memmove(output, block, AES_BLOCK_SIZE);

        /* process next block (this does not modify pointers
           when we exit the function) */
        input += AES_BLOCK_SIZE;
        output += AES_BLOCK_SIZE;
    }
}

/*
 * Processes one or multiple blocks
 * Calls the appropriate chaining function
 */
static void static_switchChaining(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint32_t inputLen,
    uint8_t *output)
{
    /* Except for CTR mode, input must be a whole number of blocks. */
    uint32_t nBlocks = inputLen / AES_BLOCK_SIZE;

    switch (opCtx->algorithm) {
        case AES_MODE_CTR:
            static_doCtr(opCtx, input, output, inputLen);
            break;
        case AES_MODE_ECB:
            static_doEcb(&opCtx->keyCtx, opCtx->encrypt, input, output, nBlocks);
            break;
        case AES_MODE_CBC:
        case AES_MODE_CTS:
            if (opCtx->encrypt) {
                static_doCbcEncrypt(opCtx, input, output, nBlocks);
            } else {
                static_doCbcDecrypt(opCtx, input, output, nBlocks);
            }
            break;
        default:
            /* Do nothing. */
            break;
    }
}

/**
 * @param iv IV of length AES_IV_SIZE, for CBC and CTR
 */
static void static_resetAes(
    uint32_t algorithm,
    opModeCtx_t *opModeCtx,
    aesKeyCtx_t *keyCtx,
    tlApiSymKey_t *key,
    const uint8_t *iv,
    bool encrypt)
{
    if (encrypt || algorithm == AES_MODE_CTR || algorithm == AES_MODE_CCM || algorithm == AES_MODE_GCM) {
        aesImplAesExpandKey(key, keyCtx);
    } else {
        aesImplAesInvExpandKey(key, keyCtx);
    }

    switch (algorithm) {
        case AES_MODE_CBC:
        case AES_MODE_CTS:
            memmove(opModeCtx->iv, iv, AES_IV_SIZE);
            break;
        case AES_MODE_CTR:
            memmove(opModeCtx->ctrCtx.counter, iv, AES_IV_SIZE);
            opModeCtx->ctrCtx.offset = 0;
            break;
        case AES_MODE_XTS:
            if (iv != NULL) {
                memmove(opModeCtx->xtsCtx.iv, iv, AES_IV_SIZE);
                opModeCtx->xtsCtx.state = 0;
                memset(opModeCtx->xtsCtx.tweak, 0, AES_XTS_TWEAK_SIZE);
                memset(opModeCtx->xtsCtx.scratch, 0, AES_XTS_SCRATCH_SIZE);
            }
            break;
        default:
            /* Nothing to do for ECB, CCM, GCM. */
            break;
    }
}

/* --------------------------- Public functions ------------------------ */


/*=============================================================================
 *
 * INIT / RESET / TERMINATE OPERATION
 *
 *===========================================================================*/

tlApiResult_t aesInitOperation(
    bool encrypt,
    uint32_t algorithm,
    const uint8_t *iv, // IV for CBC, counter for CTR, of length AES_IV_SIZE
    tlApiSymKey_t *key,
    aesOpCtx_t *opCtx)
{
    tlApiResult_t nError = TLAPI_OK;

    if (!AES_IS_VALID_KEY(key)) {
        return E_TLAPI_INVALID_PARAMETER;
    }

    opCtx->encrypt = encrypt;
    opCtx->algorithm = algorithm;
    opCtx->key = key;

    switch (algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
        case AES_MODE_CTR:
            static_resetAes(algorithm, &opCtx->opModeCtx, &opCtx->keyCtx, key, iv, encrypt);
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}

tlApiResult_t aesResetOperation(
    aesOpCtx_t *opCtx,
    bool encrypt,
    const uint8_t *iv)
{
    opCtx->encrypt = encrypt;
    switch (opCtx->algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
        case AES_MODE_CTR:
            static_resetAes(opCtx->algorithm, &opCtx->opModeCtx, &opCtx->keyCtx, opCtx->key, iv, encrypt);
            break;
        default:
            return E_TLAPI_NOT_IMPLEMENTED;
    }

    return TLAPI_OK;
}

void aesTerminateOperation(
    aesOpCtx_t *opCtx)
{
    if (opCtx != NULL) {
        memset(&opCtx->opModeCtx, 0, sizeof(opModeCtx_t));
        memset(&opCtx->keyCtx, 0, sizeof(aesKeyCtx_t));
    }
}

/*=============================================================================
 *
 * ENCRYPTION & DECRYPTION
 *
 *===========================================================================*/

tlApiResult_t aesDoOperation(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint32_t inputLen,
    uint8_t *output,
    bool terminate)
{
    tlApiResult_t nError = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
            /* Input must be block-aligned. */
            if (inputLen % AES_BLOCK_SIZE != 0) {
                return E_TLAPI_INVALID_PARAMETER;
            }
            /* fall through */
        case AES_MODE_CTR:
            static_switchChaining(opCtx, input, inputLen, output);
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    /* Terminate the operation if nError != TLAPI_OK */
    if ((nError != TLAPI_OK) && terminate) {
        aesTerminateOperation(opCtx);
    }
    return nError;
}

/* generic function performing AES-XTS algorithm */
static tlApiResult_t static_doXts(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint32_t inputLen,
    uint8_t *output)
{
    uint32_t nIndex;
    unsigned int nCarry, nRes;
    uint128u_t tweak;
    uint128u_t *pTweak;
    uint128u_t *pScratch;
    uint8_t *state;

    // AES-XTS cannot manage data size less than 16
    if (inputLen < 16) {
        return E_TLAPI_INVALID_PARAMETER;
    }

    aesXtsCtx_t *xtsCtx = &opCtx->opModeCtx.xtsCtx;
    state = &xtsCtx->state;
    pTweak = (uint128u_t *)(xtsCtx->tweak);
    pScratch = (uint128u_t *)(xtsCtx->scratch);
    // Trick to use static_doEcb even if alogorithm is not AES_ECB
    opCtx->algorithm = AES_MODE_ECB;

    aesKeyCtx_t *keyCtx2 = &xtsCtx->keyCtx;

    if (*state == 0) {
        *state = 1;

        // AES_ECB_Encrypt(Key2, Tweak) with Tweak=IV
        static_doEcb(keyCtx2, true, xtsCtx->iv, pTweak->c, 1);

        if (!opCtx->encrypt && (inputLen % 16)) {
            inputLen -= 16;
        }
    }

    while ((inputLen >= 16) || ((*state == 2) && (inputLen < 16))) {
        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            pScratch->c[nIndex] = input[nIndex] ^ pTweak->c[nIndex];
        }
        // AES_ECB_Encrypt/AES_ECB_Decrypt(Key1, scratch)
        static_doEcb(&opCtx->keyCtx, opCtx->encrypt, pScratch->c, pScratch->c, 1);
        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            output[nIndex] = pScratch->c[nIndex] ^= pTweak->c[nIndex];
        }
        input += 16;
        output += 16;
        inputLen -= 16;

        // Convert tweak into a little-endian byte array
        for (nIndex = 0; nIndex < sizeof(pTweak->c); nIndex++) {
            tweak.c[nIndex] = pTweak->c[nIndex];
        }
        nRes = 0x87 & (((int) tweak.d[3]) >> 31);
        nCarry = (unsigned int) (tweak.u[0] >> 63);
        tweak.u[0] = (tweak.u[0] << 1) ^ (uint64_t) nRes;
        tweak.u[1] = (tweak.u[1] << 1) | (uint64_t) nCarry;
        for (nIndex = 0; nIndex < sizeof(pTweak->c); nIndex++) {
            pTweak->c[nIndex] = tweak.c[nIndex];
        }

        if (inputLen == 0) {
            goto end;
        }
    }

    if (opCtx->encrypt) {
        for (nIndex = 0; nIndex < inputLen; ++nIndex) {
            uint8_t c = input[nIndex];
            output[nIndex] = pScratch->c[nIndex];
            pScratch->c[nIndex] = c;
        }
        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            pScratch->c[nIndex] ^= pTweak->c[nIndex];
        }
        // AES_ECB_Encrypt(Key1, scratch)
        static_doEcb(&opCtx->keyCtx, true, pScratch->c, pScratch->c, 1);
        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            pScratch->c[nIndex] ^= pTweak->c[nIndex];
        }
        memmove(output - 16, pScratch->c, 16);
    } else {
        // Convert tweak into a little-endian byte array
        for (nIndex = 0; nIndex < sizeof(pTweak->c); nIndex++) {
            tweak.c[nIndex] = pTweak->c[nIndex];
        }
        nRes = 0x87 & (((int) tweak.d[3]) >> 31);
        nCarry = (unsigned int) (tweak.u[0] >> 63);
        tweak.u[0] = (tweak.u[0] << 1) ^ (uint64_t) nRes;
        tweak.u[1] = (tweak.u[1] << 1) | (uint64_t) nCarry;

        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            pScratch->c[nIndex] = input[nIndex] ^ tweak.c[nIndex];
        }
        // AES_ECB_Decrypt(Key1, scratch)
        static_doEcb(&opCtx->keyCtx, false, pScratch->c, pScratch->c, 1);
        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            pScratch->c[nIndex] ^= tweak.c[nIndex];
        }

        for (nIndex = 0; nIndex < inputLen; ++nIndex) {
            uint8_t c = input[16 + nIndex];
            output[16 + nIndex] = pScratch->c[nIndex];
            pScratch->c[nIndex] = c;
        }

        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            pScratch->c[nIndex] ^= pTweak->c[nIndex];
        }
        // AES_ECB_Decrypt(Key1, scratch)
        static_doEcb(&opCtx->keyCtx, false, pScratch->c, pScratch->c, 1);
        for (nIndex = 0; nIndex < sizeof(pScratch->c); nIndex++) {
            output[nIndex] = pScratch->c[nIndex] ^ pTweak->c[nIndex];
        }
    }

  end:
    opCtx->algorithm = AES_MODE_XTS;

    return TLAPI_OK;
}

/* generic function performing AES-CTS algorithm */
static tlApiResult_t static_doCts(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint32_t inputLen,
    uint8_t *output)
{
    size_t nResidue, nIndex;

    // AES-CTS cannot manage data size less than 17
    if (inputLen < 17) {
        return E_TLAPI_INVALID_PARAMETER;
    }

    if (opCtx->encrypt) {
        if ((nResidue = inputLen % 16) == 0) {
            nResidue = 16;
        }

        inputLen -= nResidue;

        static_doCbcEncrypt(opCtx, input, output, inputLen / AES_BLOCK_SIZE);
        input += inputLen;
        output += inputLen;

        for (nIndex = 0; nIndex < nResidue; ++nIndex) {
            opCtx->opModeCtx.iv[nIndex] ^= input[nIndex];
        }
        aesImplAesCipher(&opCtx->keyCtx, opCtx->opModeCtx.iv, opCtx->opModeCtx.iv);
        memmove(output, output - 16, nResidue);
        memmove(output - 16, opCtx->opModeCtx.iv, 16);
    } else {
        union {
            size_t align;
            unsigned char c[32];
        } tmp;

        if ((nResidue = inputLen % 16) == 0) {
            nResidue = 16;
        }

        inputLen -= 16 + nResidue;

        if (inputLen) {
            static_doCbcDecrypt(opCtx, input, output, inputLen / AES_BLOCK_SIZE);
            input += inputLen;
            output += inputLen;
        }

        aesImplAesInvCipher(&opCtx->keyCtx, input, tmp.c + 16);

        for (nIndex = 0; nIndex < 16; nIndex += sizeof(size_t)) {
            *(size_t*)(tmp.c + nIndex) = *(size_t*)(tmp.c + 16 + nIndex);
        }
        memmove(tmp.c, input + 16, nResidue);
        aesImplAesInvCipher(&opCtx->keyCtx, tmp.c, tmp.c);

        for (nIndex = 0; nIndex < 16; ++nIndex) {
            unsigned char c = input[nIndex];
            output[nIndex] = tmp.c[nIndex] ^ opCtx->opModeCtx.iv[nIndex];
            opCtx->opModeCtx.iv[nIndex] = c;
        }
        for (nResidue += 16; nIndex < nResidue; ++nIndex) {
            output[nIndex] = tmp.c[nIndex] ^ input[nIndex];
        }
    }

    return TLAPI_OK;
}

tlApiResult_t aesCipherInit(
    aesOpCtx_t *opCtx,
    const uint8_t *iv)
{
    tlApiResult_t nError = TLAPI_OK;

    opModeCtx_t *opModeCtx = &opCtx->opModeCtx;

    switch (opCtx->algorithm) {
        case AES_MODE_ECB:
        case AES_MODE_CBC:
        case AES_MODE_CTR:
        case AES_MODE_CTS:
            static_resetAes(opCtx->algorithm, opModeCtx, &opCtx->keyCtx, opCtx->key, iv, opCtx->encrypt);
            break;

        case AES_MODE_XTS:
            // For the first key
            static_resetAes(AES_MODE_XTS, opModeCtx, &opCtx->keyCtx, opCtx->key, NULL, opCtx->encrypt);

            // For the second key // TODO Check
            static_resetAes(AES_MODE_XTS, opModeCtx, &opModeCtx->xtsCtx.keyCtx, opModeCtx->xtsCtx.key, iv, true);
            break;

        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}

tlApiResult_t aesCipherUpdate(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint32_t inputLen,
    uint8_t *output)
{
    tlApiResult_t nResult = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_XTS:
            /* Input must be block-aligned. */
            if (inputLen % AES_BLOCK_SIZE != 0) {
                return E_TLAPI_INVALID_PARAMETER;
            }

            nResult = static_doXts(opCtx, input, inputLen, output);
            break;
        case AES_MODE_CTS:
            /* Input must be block-aligned. */
            if (inputLen % AES_BLOCK_SIZE != 0) {
                return E_TLAPI_INVALID_PARAMETER;
            }
            static_switchChaining(opCtx, input, inputLen, output);
            break;
        default:
            nResult = aesDoOperation(opCtx, input, inputLen, output, false);
            break;
    }

    return nResult;
}


tlApiResult_t aesCipherDoFinal(
    aesOpCtx_t *opCtx,
    const uint8_t *input,
    uint32_t inputLen,
    uint8_t *output)
{
    tlApiResult_t nResult = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_XTS:
            nResult = static_doXts(opCtx, input, inputLen, output);
            break;
        case AES_MODE_CTS:
            nResult = static_doCts(opCtx, input, inputLen, output);
            break;
        default: // ECB, CBC, CTR
            nResult = aesDoOperation(opCtx, input, inputLen, output, false);
            break;
    }

    return nResult;
}


/* counter part of nonce may not be larger than L*8 bits,
 * L is not larger than 8, therefore 64-bit counter... */
static void static_ctr64_inc(
    unsigned char *counter)
{
    unsigned int n = 8;
    uint8_t c;

    counter += 8;
    do {
        --n;
        c = counter[n];
        ++c;
        counter[n] = c;
        if (c) {
            return;
        }
    } while (n);
}

tlApiResult_t static_UpdateAesCcm(
    aesOpCtx_t *opCtx,
    const uint8_t *srcData,
    uint32_t srcLen,
    uint8_t *dstData, uint32_t *dstLen)
{
    tlApiResult_t nError = TLAPI_OK;
    aesCcmCtx_t *pAesCcmCtx = &opCtx->opModeCtx.ccmCtx;
    size_t n = 0;
    unsigned int i, L;
    unsigned char flags0 = pAesCcmCtx->nonce.c[0];
    union {
        uint64_t u[2];
        uint8_t c[16];
    } scratch;

    /* Check if required AAD length has been provided yet */
    if (pAesCcmCtx->AADLen != 0) {
        return E_TLAPI_INVALID_PARAMETER;
    }

    pAesCcmCtx->nonce.c[0] = L = flags0 & 7;

    /* Check if first payload block to process */
    if (pAesCcmCtx->processedLen == 0) {
        if (!(flags0 & 0x40)) {
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, pAesCcmCtx->cmac.c);
            if (opCtx->encrypt) {
                pAesCcmCtx->blocks++;
            }
        }

        /* Reconstructed the payload length */
        for (n = 0, i = 15 - L; i < 15; ++i) {
            n |= pAesCcmCtx->nonce.c[i];
            pAesCcmCtx->nonce.c[i] = 0;
            n <<= 8;
        }
        n |= pAesCcmCtx->nonce.c[15];

        pAesCcmCtx->nonce.c[15] = 1;
    }

    /* Check if the payload length has been reached */
    if ((pAesCcmCtx->processedLen + srcLen) > pAesCcmCtx->payloadLen) {
        return E_TLAPI_INVALID_PARAMETER;
    }
    pAesCcmCtx->processedLen += srcLen;

    if (opCtx->encrypt) {
        pAesCcmCtx->blocks += ((srcLen + 15) >> 3) | 1;
        if (pAesCcmCtx->blocks > ((uint64_t) 1 << 61)) {
            return E_TLAPI_INVALID_PARAMETER;
        }
    }

    *dstLen = 0;
    while ((srcLen + pAesCcmCtx->index) >= 16) {
        if (opCtx->encrypt) {
            for (i = pAesCcmCtx->index; i < 16; i++) {
                pAesCcmCtx->cmac.c[i] ^= srcData[i - pAesCcmCtx->index];
            }
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->cmac.c, pAesCcmCtx->cmac.c);
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, scratch.c);
            static_ctr64_inc(pAesCcmCtx->nonce.c);
            for (i = 0; i < 16; i++) {
                dstData[i] = scratch.c[i] ^ srcData[i];
            }
        } else {
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, scratch.c);
            static_ctr64_inc(pAesCcmCtx->nonce.c);
            for (i = 0; i < 16; i++) {
                pAesCcmCtx->cmac.c[i] ^= (dstData[i] = scratch.c[i] ^ srcData[i]);
            }
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->cmac.c, pAesCcmCtx->cmac.c);
        }

        const uint32_t offset = 16 - pAesCcmCtx->index;
        srcData += offset;
        dstData += offset;
        srcLen -= offset;
        *dstLen += offset;
        pAesCcmCtx->index = 0;
    }

    if (srcLen) {
        if (opCtx->encrypt) {
            for (i = 0; i < srcLen; ++i) {
                pAesCcmCtx->cmac.c[i] ^= srcData[i];
            }
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->cmac.c, pAesCcmCtx->cmac.c);
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, scratch.c);
            for (i = 0; i < srcLen; ++i) {
                dstData[i] = scratch.c[i] ^ srcData[i];
            }
        } else {
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, scratch.c);
            for (i = 0; i < srcLen; ++i) {
                pAesCcmCtx->cmac.c[i] ^= dstData[i] = scratch.c[i] ^ srcData[i];
            }
            static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->cmac.c, pAesCcmCtx->cmac.c);
        }
        *dstLen += srcLen;
    }

    /* Check if it is the last payload block */
    if (pAesCcmCtx->processedLen == pAesCcmCtx->payloadLen) {
        for (i = 15 - L; i < 16; ++i) {
            pAesCcmCtx->nonce.c[i] = 0;
        }

        static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, scratch.c);
        pAesCcmCtx->cmac.u[0] ^= scratch.u[0];
        pAesCcmCtx->cmac.u[1] ^= scratch.u[1];

        pAesCcmCtx->nonce.c[0] = flags0;
    }

    return nError;
}

#define GETU32(p) ((uint32_t)(p)[0]<<24|(uint32_t)(p)[1]<<16|(uint32_t)(p)[2]<<8|(uint32_t)(p)[3])
#define PUTU32(p,v) ((p)[0]=(uint8_t)((v)>>24),(p)[1]=(uint8_t)((v)>>16),(p)[2]=(uint8_t)((v)>>8),(p)[3]=(uint8_t)(v))

#define PACK(s) ((size_t)(s)<<(sizeof(size_t)*8-16))
#define REDUCE1BIT(V) \
    do { \
       if (sizeof(size_t)==8) { \
          uint64_t T = (uint64_t)(0xe100000000000000) & (0-(V.lo&1)); \
          V.lo  = (V.hi<<63)|(V.lo>>1); \
          V.hi  = (V.hi>>1 )^T; \
       } \
       else { \
          uint32_t T = 0xe1000000U & (0-(uint32_t)(V.lo&1)); \
          V.lo  = (V.hi<<63)|(V.lo>>1); \
          V.hi  = (V.hi>>1 )^((uint64_t)T<<32); \
       } \
    } while(0)

static void gcm_init_4bit(
    uint128_t Htable[16],
    uint64_t H[2])
{
    uint128_t V;

    Htable[0].hi = 0;
    Htable[0].lo = 0;
    V.hi = H[0];
    V.lo = H[1];

    Htable[8] = V;
    REDUCE1BIT(V);
    Htable[4] = V;
    REDUCE1BIT(V);
    Htable[2] = V;
    REDUCE1BIT(V);
    Htable[1] = V;
    Htable[3].hi = V.hi ^ Htable[2].hi, Htable[3].lo = V.lo ^ Htable[2].lo;
    V = Htable[4];
    Htable[5].hi = V.hi ^ Htable[1].hi, Htable[5].lo = V.lo ^ Htable[1].lo;
    Htable[6].hi = V.hi ^ Htable[2].hi, Htable[6].lo = V.lo ^ Htable[2].lo;
    Htable[7].hi = V.hi ^ Htable[3].hi, Htable[7].lo = V.lo ^ Htable[3].lo;
    V = Htable[8];
    Htable[9].hi = V.hi ^ Htable[1].hi, Htable[9].lo = V.lo ^ Htable[1].lo;
    Htable[10].hi = V.hi ^ Htable[2].hi, Htable[10].lo = V.lo ^ Htable[2].lo;
    Htable[11].hi = V.hi ^ Htable[3].hi, Htable[11].lo = V.lo ^ Htable[3].lo;
    Htable[12].hi = V.hi ^ Htable[4].hi, Htable[12].lo = V.lo ^ Htable[4].lo;
    Htable[13].hi = V.hi ^ Htable[5].hi, Htable[13].lo = V.lo ^ Htable[5].lo;
    Htable[14].hi = V.hi ^ Htable[6].hi, Htable[14].lo = V.lo ^ Htable[6].lo;
    Htable[15].hi = V.hi ^ Htable[7].hi, Htable[15].lo = V.lo ^ Htable[7].lo;
}


static const size_t rem_4bit[16] = {
    PACK(0x0000), PACK(0x1C20), PACK(0x3840), PACK(0x2460),
    PACK(0x7080), PACK(0x6CA0), PACK(0x48C0), PACK(0x54E0),
    PACK(0xE100), PACK(0xFD20), PACK(0xD940), PACK(0xC560),
    PACK(0x9180), PACK(0x8DA0), PACK(0xA9C0), PACK(0xB5E0)
};

static void gcm_gmult_4bit(
    uint64_t Xi[2],
    const uint128_t Htable[16])
{
    uint128_t Z;
    int cnt = 15;
    size_t rem, nlo, nhi;
    const union {
        long one;
        char little;
    } is_endian = {1};

    nlo = ((const uint8_t *) Xi)[15];
    nhi = nlo >> 4;
    nlo &= 0xf;

    Z.hi = Htable[nlo].hi;
    Z.lo = Htable[nlo].lo;

    while (1) {
        rem = (size_t) Z.lo & 0xf;
        Z.lo = (Z.hi << 60) | (Z.lo >> 4);
        Z.hi = (Z.hi >> 4);
        if (sizeof(size_t) == 8) {
            Z.hi ^= rem_4bit[rem];
        } else {
            Z.hi ^= (uint64_t) rem_4bit[rem] << 32;
        }

        Z.hi ^= Htable[nhi].hi;
        Z.lo ^= Htable[nhi].lo;

        if (--cnt < 0) {
            break;
        }

        nlo = ((const uint8_t *) Xi)[cnt];
        nhi = nlo >> 4;
        nlo &= 0xf;

        rem = (size_t) Z.lo & 0xf;
        Z.lo = (Z.hi << 60) | (Z.lo >> 4);
        Z.hi = (Z.hi >> 4);
        if (sizeof(size_t) == 8) {
            Z.hi ^= rem_4bit[rem];
        } else {
            Z.hi ^= (uint64_t) rem_4bit[rem] << 32;
        }

        Z.hi ^= Htable[nlo].hi;
        Z.lo ^= Htable[nlo].lo;
    }

    if (is_endian.little) {
        uint8_t *p = (uint8_t *) Xi;
        uint32_t v;
        v = (uint32_t) (Z.hi >> 32);
        PUTU32(p, v);
        v = (uint32_t) (Z.hi);
        PUTU32(p + 4, v);
        v = (uint32_t) (Z.lo >> 32);
        PUTU32(p + 8, v);
        v = (uint32_t) (Z.lo);
        PUTU32(p + 12, v);
    } else {
        Xi[0] = Z.hi;
        Xi[1] = Z.lo;
    }
}


/*
 * Streamed gcm_mult_4bit, see CRYPTO_gcm128_[en|de]crypt for
 * details... Compiler-generated code doesn't seem to give any
 * performance improvement, at least not on x86[_64]. It's here
 * mostly as reference and a placeholder for possible future
 * non-trivial optimization[s]...
 */
static void gcm_ghash_4bit(
    uint64_t Xi[2],
    const uint128_t Htable[16],
    const uint8_t * inp,
    size_t len)
{
    uint128_t Z;
    int cnt;
    size_t rem, nlo, nhi;
    const union {
        long one;
        char little;
    } is_endian = {1};

    do {
        cnt = 15;
        nlo = ((const uint8_t *) Xi)[15];
        nlo ^= inp[15];
        nhi = nlo >> 4;
        nlo &= 0xf;

        Z.hi = Htable[nlo].hi;
        Z.lo = Htable[nlo].lo;

        while (1) {
            rem = (size_t) Z.lo & 0xf;
            Z.lo = (Z.hi << 60) | (Z.lo >> 4);
            Z.hi = (Z.hi >> 4);
            if (sizeof(size_t) == 8) {
                Z.hi ^= rem_4bit[rem];
            } else {
                Z.hi ^= (uint64_t) rem_4bit[rem] << 32;
            }

            Z.hi ^= Htable[nhi].hi;
            Z.lo ^= Htable[nhi].lo;

            if (--cnt < 0) {
                break;
            }

            nlo = ((const uint8_t *) Xi)[cnt];
            nlo ^= inp[cnt];
            nhi = nlo >> 4;
            nlo &= 0xf;

            rem = (size_t) Z.lo & 0xf;
            Z.lo = (Z.hi << 60) | (Z.lo >> 4);
            Z.hi = (Z.hi >> 4);
            if (sizeof(size_t) == 8) {
                Z.hi ^= rem_4bit[rem];
            } else {
                Z.hi ^= (uint64_t) rem_4bit[rem] << 32;
            }

            Z.hi ^= Htable[nlo].hi;
            Z.lo ^= Htable[nlo].lo;
        }

        if (is_endian.little) {
            uint8_t *p = (uint8_t *) Xi;
            uint32_t v;
            v = (uint32_t) (Z.hi >> 32);
            PUTU32(p, v);
            v = (uint32_t) (Z.hi);
            PUTU32(p + 4, v);
            v = (uint32_t) (Z.lo >> 32);
            PUTU32(p + 8, v);
            v = (uint32_t) (Z.lo);
            PUTU32(p + 12, v);
        } else {
            Xi[0] = Z.hi;
            Xi[1] = Z.lo;
        }
    } while (inp += 16, len -= 16);
}

#define GCM_MUL(ctx,Xi) gcm_gmult_4bit(ctx->Xi.u,ctx->Htable)
#define GHASH(ctx,in,len) gcm_ghash_4bit((ctx)->Xi.u,(ctx)->Htable,in,len)
/* GHASH_CHUNK is "stride parameter" missioned to mitigate cache
 * trashing effect. In other words idea is to hash data while it's
 * still in L1 cache after encryption pass... */
#define GHASH_CHUNK (3*1024)

static tlApiResult_t static_UpdateAesGcm(
    aesOpCtx_t *opCtx,
    const uint8_t *srcData,
    uint32_t srcLen,
    uint8_t *dstData,
    uint32_t *dstLen)
{
    aesGcmCtx_t *pAesGcmCtx = &opCtx->opModeCtx.gcmCtx;

    uint32_t TempdstLen;

    const union {
        long one;
        char little;
    } is_endian = {1};
    unsigned int n, ctr;
    size_t i;
    uint64_t mlen = pAesGcmCtx->len.u[1];

    mlen += srcLen;
    if (mlen > (((uint64_t) (1) << 36) - 32) || (sizeof(srcLen) == 8 && mlen < srcLen)) {
        return E_TLAPI_INVALID_PARAMETER;
    }

    pAesGcmCtx->len.u[1] = mlen;

    TempdstLen = *dstLen;
    *dstLen = 0;

    if (pAesGcmCtx->ares) {
        /* First call to encrypt finalizes GHASH(AAD) */
        GCM_MUL(pAesGcmCtx, Xi);
        pAesGcmCtx->ares = 0;
    }

    if (is_endian.little) {
        ctr = GETU32(pAesGcmCtx->Yi.c + 12);
    } else {
        ctr = pAesGcmCtx->Yi.d[3];
    }

    n = pAesGcmCtx->mres;

    assert(16 % sizeof(size_t) == 0);

    if (n) {
        while (n && srcLen) {
            if (opCtx->encrypt) {
                pAesGcmCtx->Xi.c[n] ^= *dstData = *srcData ^ pAesGcmCtx->EKi.c[n];
                srcData++;
                dstData++;
                --srcLen;
            } else {
                uint8_t c = *srcData;
                *dstData = c ^ pAesGcmCtx->EKi.c[n];
                pAesGcmCtx->Xi.c[n] ^= c;
                srcData++;
                dstData++;
                --srcLen;
            }
            n = (n + 1) % 16;
            (*dstLen)++;
        }
        if (n == 0) {
            GCM_MUL(pAesGcmCtx, Xi);
        } else {
            pAesGcmCtx->mres = n;
            return TLAPI_OK;
        }
    }

    while (srcLen >= GHASH_CHUNK) {
        size_t j = GHASH_CHUNK;

        if (!opCtx->encrypt) {
            GHASH(pAesGcmCtx, srcData, GHASH_CHUNK);
        }
        while (j) {
            static_doCore(&opCtx->keyCtx, true, pAesGcmCtx->Yi.c, pAesGcmCtx->EKi.c);
            ++ctr;
            if (is_endian.little) {
                PUTU32(pAesGcmCtx->Yi.c + 12, ctr);
            }
            else {
                pAesGcmCtx->Yi.d[3] = ctr;
            }
            for (i = 0; i < 16; i += sizeof(size_t)) {
                *(size_t*)dstData = *(size_t*)srcData ^ *(size_t*)(pAesGcmCtx->EKi.c + i);
                srcData += sizeof(size_t);
                dstData += sizeof(size_t);
            }
            j -= 16;
        }
        if (opCtx->encrypt) {
            GHASH(pAesGcmCtx, dstData - GHASH_CHUNK, GHASH_CHUNK);
        }
        srcLen -= GHASH_CHUNK;
        *dstLen += GHASH_CHUNK;
    }

    i = (srcLen & (size_t) - 16);

    if (i > 0) {
        size_t j = i;
        if (!opCtx->encrypt) {
            GHASH(pAesGcmCtx, srcData, i);
        }

        while (srcLen >= 16) {
            static_doCore(&opCtx->keyCtx, true, pAesGcmCtx->Yi.c, pAesGcmCtx->EKi.c);
            ++ctr;
            if (is_endian.little) {
                PUTU32(pAesGcmCtx->Yi.c + 12, ctr);
            } else {
                pAesGcmCtx->Yi.d[3] = ctr;
            }
            for (i = 0; i < 16; i += sizeof(size_t)) {
                *(size_t*)dstData = *(size_t*)srcData ^ *(size_t*)(pAesGcmCtx->EKi.c + i);
                srcData += sizeof(size_t);
                dstData += sizeof(size_t);
            }
            srcLen -= 16;
            *dstLen += 16;
        }
        if (opCtx->encrypt) {
            GHASH(pAesGcmCtx, dstData - j, j);
        }
    }

    if (srcLen) {
        static_doCore(&opCtx->keyCtx, true, pAesGcmCtx->Yi.c, pAesGcmCtx->EKi.c);
        ++ctr;
        if (is_endian.little) {
            PUTU32(pAesGcmCtx->Yi.c + 12, ctr);
        } else {
            pAesGcmCtx->Yi.d[3] = ctr;
        }

        while (srcLen--) {
            if (opCtx->encrypt) {
                pAesGcmCtx->Xi.c[n] ^= dstData[n] = srcData[n] ^ pAesGcmCtx->EKi.c[n];
            } else {
                uint8_t c = srcData[n];
                pAesGcmCtx->Xi.c[n] ^= c;
                dstData[n] = c ^ pAesGcmCtx->EKi.c[n];
            }
            ++n;
            (*dstLen)++;
        }
    }

    pAesGcmCtx->mres = n;
    if (TempdstLen < *dstLen) {
        return E_TLAPI_BUFFER_TOO_SMALL;
    }
    return TLAPI_OK;
}

static tlApiResult_t static_DoFinalAesGcm(aesGcmCtx_t * pAesGcmCtx)
{
    const union {
        long one;
        char little;
    } is_endian = {1};
    uint64_t alen = pAesGcmCtx->len.u[0] << 3;
    uint64_t clen = pAesGcmCtx->len.u[1] << 3;

    if (pAesGcmCtx->mres) {
        GCM_MUL(pAesGcmCtx, Xi);
    }

    if (is_endian.little) {
        uint8_t *p = pAesGcmCtx->len.c;

        pAesGcmCtx->len.u[0] = alen;
        pAesGcmCtx->len.u[1] = clen;

        alen = (uint64_t) GETU32(p) << 32 | GETU32(p + 4);
        clen = (uint64_t) GETU32(p + 8) << 32 | GETU32(p + 12);
    }

    pAesGcmCtx->Xi.u[0] ^= alen;
    pAesGcmCtx->Xi.u[1] ^= clen;
    GCM_MUL(pAesGcmCtx, Xi);

    pAesGcmCtx->Xi.u[0] ^= pAesGcmCtx->EK0.u[0];
    pAesGcmCtx->Xi.u[1] ^= pAesGcmCtx->EK0.u[1];

    return TLAPI_OK;
}

tlApiResult_t aesAEInit(
    aesOpCtx_t *opCtx,
    const uint8_t *nonce,
    uint32_t nonceLen,
    uint32_t tagLen,
    uint32_t AADLen,
    uint32_t payloadLen)
{
    tlApiResult_t nError = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_CCM:
            {
                aesCcmCtx_t *pAesCcmCtx = &opCtx->opModeCtx.ccmCtx;

                /* Determine the L parameter as a trade-off between the maximum message size
                   and the size of the nonce. Valid values of L range between 2 and 8 octets
                   (the value L=1 is reserved).
                 */
                unsigned int L = 1;
                while (payloadLen >= ((unsigned int) 1 << (8 * (++L))));

                static_resetAes(AES_MODE_CCM, &opCtx->opModeCtx, &opCtx->keyCtx, opCtx->key, NULL, true);
                pAesCcmCtx->tagLen = 8*tagLen;
                pAesCcmCtx->AADLen = AADLen;
                pAesCcmCtx->payloadLen = payloadLen;
                pAesCcmCtx->processedLen = 0;
                pAesCcmCtx->blocks = 0;
                pAesCcmCtx->index = 0;

                /* Check if nonce length is compatible with the length required by the algorithm */
                if (nonceLen != (15 - L)) {
                    return E_TLAPI_INVALID_PARAMETER;
                }

                memset(pAesCcmCtx->cmac.c, 0, sizeof(pAesCcmCtx->cmac.c));
                memset(pAesCcmCtx->nonce.c, 0, sizeof(pAesCcmCtx->nonce.c));
                pAesCcmCtx->nonce.c[0] = ((uint8_t) (L - 1) & 7) | (uint8_t) (((8*tagLen - 2) / 2) & 7) << 3;

                if ((sizeof(payloadLen) == 8) && (L >= 4)) {
                    pAesCcmCtx->nonce.c[8] = (uint8_t) (payloadLen >> (56 % (sizeof(payloadLen) * 8)));
                    pAesCcmCtx->nonce.c[9] = (uint8_t) (payloadLen >> (48 % (sizeof(payloadLen) * 8)));
                    pAesCcmCtx->nonce.c[10] = (uint8_t) (payloadLen >> (40 % (sizeof(payloadLen) * 8)));
                    pAesCcmCtx->nonce.c[11] = (uint8_t) (payloadLen >> (32 % (sizeof(payloadLen) * 8)));
                } else {
                    *(uint32_t *) (&pAesCcmCtx->nonce.c[8]) = 0;
                }

                pAesCcmCtx->nonce.c[12] = (uint8_t) (payloadLen >> 24);
                pAesCcmCtx->nonce.c[13] = (uint8_t) (payloadLen >> 16);
                pAesCcmCtx->nonce.c[14] = (uint8_t) (payloadLen >> 8);
                pAesCcmCtx->nonce.c[15] = (uint8_t) payloadLen;

                pAesCcmCtx->nonce.c[0] &= ~0x40;    /* clear Adata flag */
                memmove(&pAesCcmCtx->nonce.c[1], nonce, 15 - L);
            }
            break;

        case AES_MODE_GCM:
            {
                aesGcmCtx_t *pAesGcmCtx = &opCtx->opModeCtx.gcmCtx;
                const union {
                long one;
                char little;
                } is_endian = {
                1};
                unsigned int ctr;

                static_resetAes(AES_MODE_GCM, &opCtx->opModeCtx, &opCtx->keyCtx, opCtx->key, NULL, true);
                memset(pAesGcmCtx, 0, sizeof(aesGcmCtx_t));
                pAesGcmCtx->tagLen = 8*tagLen;
                static_doCore(&opCtx->keyCtx, true, pAesGcmCtx->H.c, pAesGcmCtx->H.c);

                if (is_endian.little) {
                    /* H is stored in host byte order */
                    uint8_t *p = pAesGcmCtx->H.c;
                    uint64_t hi, lo;
                    hi = (uint64_t) GETU32(p) << 32 | GETU32(p + 4);
                    lo = (uint64_t) GETU32(p + 8) << 32 | GETU32(p + 12);
                    pAesGcmCtx->H.u[0] = hi;
                    pAesGcmCtx->H.u[1] = lo;
                }

                gcm_init_4bit(pAesGcmCtx->Htable, pAesGcmCtx->H.u);

                /* Set Initial Vector */

                pAesGcmCtx->Yi.u[0] = 0;
                pAesGcmCtx->Yi.u[1] = 0;
                pAesGcmCtx->Xi.u[0] = 0;
                pAesGcmCtx->Xi.u[1] = 0;
                pAesGcmCtx->len.u[0] = 0;    /* AAD length */
                pAesGcmCtx->len.u[1] = 0;    /* message length */
                pAesGcmCtx->ares = 0;
                pAesGcmCtx->mres = 0;

                if (nonceLen == 12) {
                    memmove(pAesGcmCtx->Yi.c, nonce, 12);
                    pAesGcmCtx->Yi.c[15] = 1;
                    ctr = 1;
                } else {
                    size_t i;
                    uint64_t len0 = nonceLen;

                    while (nonceLen >= 16) {
                        for (i = 0; i < 16; ++i) {
                            pAesGcmCtx->Yi.c[i] ^= ((uint8_t *) nonce)[i];
                        }
                        GCM_MUL(pAesGcmCtx, Yi);
                        nonce = (uint8_t *) nonce + 16;
                        nonceLen -= 16;
                    }
                    if (nonceLen) {
                        for (i = 0; i < nonceLen; ++i) {
                            pAesGcmCtx->Yi.c[i] ^= ((uint8_t *) nonce)[i];
                        }
                        GCM_MUL(pAesGcmCtx, Yi);
                    }
                    len0 <<= 3;
                    if (is_endian.little) {
                        pAesGcmCtx->Yi.c[8] ^= (uint8_t) (len0 >> 56);
                        pAesGcmCtx->Yi.c[9] ^= (uint8_t) (len0 >> 48);
                        pAesGcmCtx->Yi.c[10] ^= (uint8_t) (len0 >> 40);
                        pAesGcmCtx->Yi.c[11] ^= (uint8_t) (len0 >> 32);
                        pAesGcmCtx->Yi.c[12] ^= (uint8_t) (len0 >> 24);
                        pAesGcmCtx->Yi.c[13] ^= (uint8_t) (len0 >> 16);
                        pAesGcmCtx->Yi.c[14] ^= (uint8_t) (len0 >> 8);
                        pAesGcmCtx->Yi.c[15] ^= (uint8_t) (len0);
                    } else {
                        pAesGcmCtx->Yi.u[1] ^= len0;
                    }

                    GCM_MUL(pAesGcmCtx, Yi);

                    if (is_endian.little) {
                        ctr = GETU32(pAesGcmCtx->Yi.c + 12);
                    } else {
                        ctr = pAesGcmCtx->Yi.d[3];
                    }
                }

                static_doCore(&opCtx->keyCtx, true, pAesGcmCtx->Yi.c, pAesGcmCtx->EK0.c);
                ++ctr;
                if (is_endian.little) {
                    PUTU32(pAesGcmCtx->Yi.c + 12, ctr);
                } else {
                    pAesGcmCtx->Yi.d[3] = ctr;
                }
            }
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}

tlApiResult_t aesAEUpdateAAD(
    aesOpCtx_t *opCtx,
    const uint8_t *AADdata,
    uint32_t AADdataLen)
{
    tlApiResult_t nError = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_CCM:
            {
                aesCcmCtx_t *pAesCcmCtx = &opCtx->opModeCtx.ccmCtx;
                uint8_t i;

                if (AADdataLen == 0) {
                    return (TLAPI_OK);
                }

                /* Check if AAD length has been reached */
                if (AADdataLen > pAesCcmCtx->AADLen) {
                    return E_TLAPI_INVALID_PARAMETER;
                }

                pAesCcmCtx->AADLen -= AADdataLen;
                pAesCcmCtx->nonce.c[0] |= 0x40;    /* set Adata flag */
                static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->nonce.c, pAesCcmCtx->cmac.c);
                pAesCcmCtx->blocks++;

                if (AADdataLen < (0x10000 - 0x100)) {
                    pAesCcmCtx->cmac.c[0] ^= (uint8_t) (AADdataLen >> 8);
                    pAesCcmCtx->cmac.c[1] ^= (uint8_t) AADdataLen;
                    i = 2;
                } else if (sizeof(AADdataLen) == 8 && AADdataLen >= (size_t) 1 << (32 % (sizeof(AADdataLen) * 8))) {
                    pAesCcmCtx->cmac.c[0] ^= 0xFF;
                    pAesCcmCtx->cmac.c[1] ^= 0xFF;
                    pAesCcmCtx->cmac.c[2] ^= (uint8_t) (AADdataLen >> (56 % (sizeof(AADdataLen) * 8)));
                    pAesCcmCtx->cmac.c[3] ^= (uint8_t) (AADdataLen >> (48 % (sizeof(AADdataLen) * 8)));
                    pAesCcmCtx->cmac.c[4] ^= (uint8_t) (AADdataLen >> (40 % (sizeof(AADdataLen) * 8)));
                    pAesCcmCtx->cmac.c[5] ^= (uint8_t) (AADdataLen >> (32 % (sizeof(AADdataLen) * 8)));
                    pAesCcmCtx->cmac.c[6] ^= (uint8_t) (AADdataLen >> 24);
                    pAesCcmCtx->cmac.c[7] ^= (uint8_t) (AADdataLen >> 16);
                    pAesCcmCtx->cmac.c[8] ^= (uint8_t) (AADdataLen >> 8);
                    pAesCcmCtx->cmac.c[9] ^= (uint8_t) AADdataLen;
                    i = 10;
                } else {
                    pAesCcmCtx->cmac.c[0] ^= 0xFF;
                    pAesCcmCtx->cmac.c[1] ^= 0xFE;
                    pAesCcmCtx->cmac.c[2] ^= (uint8_t) (AADdataLen >> 24);
                    pAesCcmCtx->cmac.c[3] ^= (uint8_t) (AADdataLen >> 16);
                    pAesCcmCtx->cmac.c[4] ^= (uint8_t) (AADdataLen >> 8);
                    pAesCcmCtx->cmac.c[5] ^= (uint8_t) AADdataLen;
                    i = 6;
                }

                do {
                    for (; i < 16 && AADdataLen; i++, AADdataLen--) {
                        pAesCcmCtx->cmac.c[i] ^= *AADdata;
                        AADdata = AADdata + 1;
                    }
                    static_doCore(&opCtx->keyCtx, true, pAesCcmCtx->cmac.c, pAesCcmCtx->cmac.c);
                    pAesCcmCtx->blocks++;
                    i = 0;
                } while (AADdataLen);
            }
            break;
        case AES_MODE_GCM:
            {
                aesGcmCtx_t *pAesGcmCtx = &opCtx->opModeCtx.gcmCtx;
                size_t i;
                unsigned int n;
                uint64_t alen = pAesGcmCtx->len.u[0];

                if (pAesGcmCtx->len.u[1]) {
                    return E_TLAPI_INVALID_PARAMETER;
                }

                alen += AADdataLen;
                /* Check if AAD length has been reached */
                if (alen > ((uint64_t) (1) << 61) || (sizeof(AADdataLen) == 8 && alen < AADdataLen)) {
                    return E_TLAPI_INVALID_PARAMETER;
                }
                pAesGcmCtx->len.u[0] = alen;

                n = pAesGcmCtx->ares;
                if (n) {
                    while (n && AADdataLen) {
                        pAesGcmCtx->Xi.c[n] ^= *AADdata;
                        AADdata = AADdata + 1;
                        --AADdataLen;
                        n = (n + 1) % 16;
                    }
                    if (n == 0) {
                        GCM_MUL(pAesGcmCtx, Xi);
                    } else {
                        pAesGcmCtx->ares = n;
                        return (TLAPI_OK);
                    }
                }

                i = (AADdataLen & (size_t) - 16);

                if (i > 0) {
                    GHASH(pAesGcmCtx, AADdata, i);
                    AADdata = AADdata + i;
                    AADdataLen -= i;
                }

                if (AADdataLen) {
                    n = (unsigned int) AADdataLen;
                    for (i = 0; i < AADdataLen; ++i) {
                        pAesGcmCtx->Xi.c[i] ^= AADdata[i];
                    }
                }

                pAesGcmCtx->ares = n;
            }
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}

tlApiResult_t aesAEUpdate(
    aesOpCtx_t *opCtx,
    const uint8_t *srcData,
    uint32_t srcLen,
    uint8_t *dstData,
    uint32_t *dstLen)
{
    tlApiResult_t nError = TLAPI_OK;

    if (srcLen == 0) {
        /* [AE] The reason for special-casing this is to allow further calls to
         * aesAEUpdateAAD() before the first actual input, which would otherwise
         * be invalidated even if no input is supplied here.
         */
        *dstLen = 0;
        return TLAPI_OK;
    }

    switch (opCtx->algorithm) {
        case AES_MODE_CCM:
            nError = static_UpdateAesCcm(opCtx, srcData, srcLen, dstData, dstLen);
            break;
        case AES_MODE_GCM:
            nError = static_UpdateAesGcm(opCtx, srcData, srcLen, dstData, dstLen);
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}

tlApiResult_t aesAEEncryptFinal(
    aesOpCtx_t * opCtx,
    const uint8_t *srcData,
    uint32_t srcLen,
    uint8_t *dstData,
    uint32_t *dstLen,
    uint8_t *tag,
    uint32_t *tagLen)
{
    tlApiResult_t nError = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_CCM:
            {
                aesCcmCtx_t *pAesCcmCtx = &opCtx->opModeCtx.ccmCtx;

                nError = static_UpdateAesCcm(opCtx, srcData, srcLen, dstData, dstLen);
                if (nError == TLAPI_OK) {
                /* Check if output tag buffer is large enough */
                    if (8*(*tagLen) < pAesCcmCtx->tagLen) {
                        return E_TLAPI_BUFFER_TOO_SMALL;
                    }

                    /* copy tag field */
                    memmove(tag, &pAesCcmCtx->cmac, pAesCcmCtx->tagLen/8);
                    *tagLen = pAesCcmCtx->tagLen/8;
                }
            }
            break;
        case AES_MODE_GCM:
            {
                aesGcmCtx_t *pAesGcmCtx = &opCtx->opModeCtx.gcmCtx;

                nError = static_UpdateAesGcm(opCtx, srcData, srcLen, dstData, dstLen);
                if (nError == TLAPI_OK) {
                    nError = static_DoFinalAesGcm(pAesGcmCtx);
                    if (nError == TLAPI_OK) {
                        /* Check if output tag buffer is large enough */
                        if (8*(*tagLen) < pAesGcmCtx->tagLen) {
                            return E_TLAPI_BUFFER_TOO_SMALL;
                        }

                        /* copy tag field */
                        memmove(tag, pAesGcmCtx->Xi.c, pAesGcmCtx->tagLen/8);
                        *tagLen = pAesGcmCtx->tagLen/8;
                    }
                }
            }
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}

tlApiResult_t aesAEDecryptFinal(
    aesOpCtx_t *opCtx,
    const uint8_t *srcData,
    uint32_t srcLen,
    uint8_t *dstData,
    uint32_t *dstLen,
    const uint8_t *tag,
    uint32_t tagLen)
{
    tlApiResult_t nError = TLAPI_OK;

    switch (opCtx->algorithm) {
        case AES_MODE_CCM:
            {
                aesCcmCtx_t *pAesCcmCtx = &opCtx->opModeCtx.ccmCtx;

                nError = static_UpdateAesCcm(opCtx, srcData, srcLen, dstData, dstLen);
                if (nError == TLAPI_OK) {
                    /* compare tag field */
                    if ((8*tagLen != pAesCcmCtx->tagLen) || (memcmp(tag, &pAesCcmCtx->cmac.u[0], tagLen) != 0)) {
#if TBASE_API_LEVEL >= 7
                        return E_TLAPI_INVALID_MAC;
#else
                        return E_TLAPI_INVALID_INPUT;
#endif
                    }
                }
            }
            break;
        case AES_MODE_GCM:
            {
                aesGcmCtx_t *pAesGcmCtx = &opCtx->opModeCtx.gcmCtx;

                nError = static_UpdateAesGcm(opCtx, srcData, srcLen, dstData, dstLen);
                if (nError == TLAPI_OK) {
                    nError = static_DoFinalAesGcm(pAesGcmCtx);
                    if (nError == TLAPI_OK) {
                        /* compare tag field */
                        if ((8*tagLen != pAesGcmCtx->tagLen) || (memcmp(tag, pAesGcmCtx->Xi.c, tagLen) != 0)) {
#if TBASE_API_LEVEL >= 7
                            return E_TLAPI_INVALID_MAC;
#else
                            return E_TLAPI_INVALID_INPUT;
#endif
                        }
                    }
                }
            }
            break;
        default:
            nError = E_TLAPI_NOT_IMPLEMENTED;
            break;
    }

    return nError;
}
