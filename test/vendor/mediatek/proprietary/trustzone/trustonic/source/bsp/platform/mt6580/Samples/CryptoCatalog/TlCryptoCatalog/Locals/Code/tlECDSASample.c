/*
 * Copyright (c) 2014 TRUSTONIC LIMITED
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
#include "TlApi/TlApiHeap.h"

#include "tlCryptoCatalog_Common.h"
#include "tlCryptoCatalog.h"
#include "tlCryptoCatalog_Api.h"


#define ECDSA_KEY_SO_SIZE 1024

static const uint32_t SECP192R1_BITS = 192;
static const uint32_t SECP224R1_BITS = 224;
static const uint32_t SECP256R1_BITS = 256;
static const uint32_t SECP384R1_BITS = 384;
static const uint32_t SECP521R1_BITS = 521;

/**
 * Generate ECDSA key pair
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_ECDSAGenerateKeyPair( Message_t* message )
{
    tciReturnCode_t     ret = RET_OK;
    tlApiResult_t       tlApiRet;
    uint32_t            curve       = 0;
    uint32_t            curve_len   = 0;
    key_meta_t          metadata    = {0};
    uint8_t             key_buf[ECDSA_KEY_BUFFER_SIZE] = {0}; /* key data buffer */
    uint8_t             so_buf[ECDSA_KEY_BUFFER_SIZE] = {0};   /* SO data buffer */
    uint8_t*            ptr_so_buf  = &so_buf[0];
    uint8_t*            ptr_key_buf = &key_buf[0];
    uint8_t             x[ECDSA_KEY_DATA_SIZE]       = {0};
    uint8_t             y[ECDSA_KEY_DATA_SIZE]       = {0};
    uint8_t             privKey[ECDSA_KEY_DATA_SIZE] = {0};
    size_t              so_len          = sizeof(so_buf);
    tlApiEcdsaKey_t     ecdsa_key_pair  = {0};
    tlApiKeyPair_t      key_pair        = {0};

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data     = (uint8_t*)message->ecdsa_gen_key.key_data;
        uint32_t key_data_len = message->ecdsa_gen_key.key_data_len;
        if ((key_data == NULL) ||
            (key_data_len == 0))
        {
            ret = RET_ERR_KEY_GENERATION;
            break;
        }

        /* Reset SO length value to '0' */
        message->ecdsa_gen_key.so_len = 0;

        /* Retrieve curve type */
        curve = message->ecdsa_gen_key.curve;

        switch (curve)
        {
            case ECC_CURVE_NIST_P192:
                curve_len = BYTES_PER_BITS(SECP192R1_BITS);
                break;
            case ECC_CURVE_NIST_P224:
                curve_len = BYTES_PER_BITS(SECP224R1_BITS);
                break;
            case ECC_CURVE_NIST_P256:
                curve_len = BYTES_PER_BITS(SECP256R1_BITS);
                break;
            case ECC_CURVE_NIST_P384:
                curve_len = BYTES_PER_BITS(SECP384R1_BITS);
                break;
            case ECC_CURVE_NIST_P521:
                curve_len = BYTES_PER_BITS(SECP521R1_BITS);
                break;
            default:
                ret = RET_ERR_KEY_GENERATION;
                break;
        }

        if (ret == RET_OK)
        {
            ecdsa_key_pair.curveType = curve;
            ecdsa_key_pair.x.value = &x[0];
            ecdsa_key_pair.y.value = &y[0];
            ecdsa_key_pair.privKey.value = &privKey[0];
            ecdsa_key_pair.x.len = sizeof(x);
            ecdsa_key_pair.y.len = sizeof(y);
            ecdsa_key_pair.privKey.len = sizeof(privKey);

            key_pair.ecdsaKeyPair = &ecdsa_key_pair;


            /* Generate ECDSA key pair */
            tlApiRet = tlApiGenerateKeyPair(
                                &key_pair,
                                TLAPI_ECDSA,
                                curve_len);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf("[Crypto Catalog] ECDSAGenerateKeyPair():"\
                        " ECDSA generate key pair generation failed (0x%x)\n", tlApiRet);
                ret = RET_ERR_KEY_GENERATION;
                break;
            }

            /* Populate metadata */
            metadata.key_type = KEY_TYPE_ECDSA;
            metadata.ecdsa_key.curve = curve;
            metadata.ecdsa_key.curve_length = curve_len;

            /**
             * Copy key data into the buffer
             */
            memcpy(ptr_key_buf, &metadata, sizeof(key_meta_t));
            uint32_t offset = sizeof(key_meta_t);
            memcpy(ptr_key_buf + offset, ecdsa_key_pair.x.value, curve_len);
            offset += curve_len;
            memcpy(ptr_key_buf + offset, ecdsa_key_pair.y.value, curve_len);
            offset += curve_len;
            memcpy(ptr_key_buf + offset, ecdsa_key_pair.privKey.value, curve_len);

            /* Position to the beginning of the buffer */
            ptr_key_buf = &key_buf[0];

            tlApiRet = tlApiWrapObject(
                            ptr_key_buf,
                            sizeof(key_meta_t) + (curve_len*2), /* Key metadata and public key data (x and y) */
                            curve_len,  /* Private key (encrypted)*/
                            (mcSoHeader_t*)ptr_so_buf,
                            &so_len,
                            MC_SO_CONTEXT_TLT,
                            MC_SO_LIFETIME_PERMANENT,
                            NULL,
                            TLAPI_WRAP_DEFAULT);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf("[Crypto Catalog] ECDSAGenerateKeyPair(): tlApiWrapObject failed (0x%x)\n",
                        tlApiRet);
                ret = RET_ERR_SECURE_OBJECT;
                break;
            }

            if (key_data_len < so_len)
            {
                ret = RET_ERR_INVALID_LENGTH;
                break;
            }
            /* Copy key wrapped key data to the destination */
            memcpy(key_data, ptr_so_buf, so_len);

            /* Update SO length */
            message->ecdsa_gen_key.so_len = so_len;
        }

    } while(false);

    return ret;
}


/**
 * ECDSA sign plain data and return signature data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_ECDSASign( Message_t* message )
{
    tciReturnCode_t     ret = RET_OK;
    tlApiResult_t       tlApiRet;
    uint8_t             key_buf[ECDSA_KEY_BUFFER_SIZE];
    uint8_t*            ptr_key_buf    = &key_buf[0];
    size_t              so_len         = 0;
    key_meta_t          metadata       = {0};
    tlApiCrSession_t    session_handle = {0};
    tlApiEcdsaKey_t     ecdsa_key      = {0};
    tlApiKey_t          key            = {0};

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data           = (uint8_t*)message->ecdsa_sign.key_data;
        uint8_t* digest_data        = (uint8_t*)message->ecdsa_sign.digest_data;
        uint8_t* signature_data     = (uint8_t*)message->ecdsa_sign.signature_data;
        uint32_t key_data_len       = so_len  = message->ecdsa_sign.key_data_len;
        uint32_t digest_data_len    = message->ecdsa_sign.digest_data_len;
        size_t   signature_data_len = message->ecdsa_sign.signature_data_len;
        if ((key_data == NULL) ||
            (digest_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (digest_data_len == 0) ||
            (signature_data_len == 0))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > ECDSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSASign(): invalid key data length\n");
            ret = RET_ERR_INVALID_LENGTH;
            break;
        }

        /**
         * Copy key data into internal memory. This is where the private
         * key will be handled while signing data
         */
        memcpy(ptr_key_buf, key_data, key_data_len);

        tlApiRet = tlApiUnwrapObject(
                        (mcSoHeader_t *)ptr_key_buf,
                        key_data_len,
                        NULL,
                        &so_len,
                        TLAPI_UNWRAP_DEFAULT);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSASign(): tlApiUnwrapObject failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }


        /* Extract metadata */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        if (metadata.key_type != KEY_TYPE_ECDSA)
        {
            ret = RET_ERR_INVALID_KEY_TYPE;
            break;
        }

        uint32_t offset = sizeof(mcSoHeader_t) +
                          sizeof(key_meta_t) +
                          metadata.ecdsa_key.curve_length + // x data
                          metadata.ecdsa_key.curve_length;  // y data
        ecdsa_key.privKey.value = ptr_key_buf + offset;
        ecdsa_key.x.value = NULL;
        ecdsa_key.y.value = NULL;

        ecdsa_key.privKey.len = metadata.ecdsa_key.curve_length;
        ecdsa_key.x.len = 0;
        ecdsa_key.y.len = 0;

        ecdsa_key.curveType = metadata.ecdsa_key.curve;

        key.ecdsaKey = &ecdsa_key;

        /* Init signature operation */
        tlApiRet = tlApiSignatureInit(
                                  &session_handle,
                                  &key,
                                  TLAPI_MODE_SIGN,
                                  TLAPI_SIG_ECDSA_RAW);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSASign():" \
                    " tlApiSignatureInit failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        /* Calculate signature */
        tlApiRet = tlApiSignatureSign(
                          session_handle,
                          digest_data,
                          digest_data_len,
                          signature_data,
                          &signature_data_len);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSASign():" \
                    " tlApiSignatureSign failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }


        message->ecdsa_sign.signature_data_len = signature_data_len;

    } while(false);

    return ret;
}


/**
 * ECDSA verify signature and return validity status to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_ECDSAVerify( Message_t* message )
{
    tciReturnCode_t     ret = RET_OK;
    tlApiResult_t       tlApiRet;
    uint8_t             key_buf[ECDSA_KEY_BUFFER_SIZE] = {0};
    uint8_t*            ptr_key_buf    = &key_buf[0];
    key_meta_t          metadata       = {0};
    size_t              so_len         = 0;
    tlApiCrSession_t    session_handle = {0};
    tlApiEcdsaKey_t     ecdsa_key      = {0};
    tlApiKey_t          key            = {0};
    bool                validity       = false;

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Set validity to false as default */
        message->ecdsa_verify.validity = false;

        /* Validate data passed by TLC */
        uint8_t* key_data             = (uint8_t*)message->ecdsa_verify.key_data;
        uint8_t* digest_data          = (uint8_t*)message->ecdsa_verify.digest_data;
        uint8_t* signature_data       = (uint8_t*)message->ecdsa_verify.signature_data;
        uint32_t key_data_len         = so_len  = message->ecdsa_verify.key_data_len;
        uint32_t digest_data_len      = message->ecdsa_verify.digest_data_len;
        uint32_t signature_data_len   = message->ecdsa_verify.signature_data_len;
        if ((key_data == NULL) ||
            (digest_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (digest_data_len == 0) ||
            (signature_data_len == 0))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > ECDSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSAVerify(): invalid key data length\n");
            ret = RET_ERR_INVALID_LENGTH;
            break;
        }

        /**
         * Copy key data into internal memory. This is where the public
         * key will be handled while verifying signature data
         */
        memcpy(ptr_key_buf, key_data, key_data_len);

        tlApiRet = tlApiUnwrapObject(
                        (mcSoHeader_t *)ptr_key_buf,
                        key_data_len,
                        NULL,
                        &so_len,
                        TLAPI_UNWRAP_DEFAULT);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSAVerify(): tlApiUnwrapObject failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        /* Extract metadata */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        if (metadata.key_type != KEY_TYPE_ECDSA)
        {
            ret = RET_ERR_INVALID_KEY_TYPE;
            break;
        }

        /* Extract metadata */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        uint32_t offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);
        ecdsa_key.x.value = ptr_key_buf + offset;
        offset += metadata.ecdsa_key.curve_length;
        ecdsa_key.y.value = ptr_key_buf + offset;

        /* Set the private key value to NULL */
        ecdsa_key.privKey.value = NULL;
        ecdsa_key.privKey.len = 0;

        /* Update curve type */
        ecdsa_key.curveType = metadata.ecdsa_key.curve;

        /* Both x and y lengths are same as curve length */
        ecdsa_key.x.len = ecdsa_key.y.len = metadata.ecdsa_key.curve_length;

        key.ecdsaKey = &ecdsa_key;

        /* Init signature operation */
        tlApiRet = tlApiSignatureInit(
                                  &session_handle,
                                  &key,
                                  TLAPI_MODE_VERIFY,
                                  TLAPI_SIG_ECDSA_RAW);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSAVerify(): tlApiSignatureInit failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        /* Verify signature */
        tlApiRet = tlApiSignatureVerify(
                                session_handle,
                                digest_data,
                                digest_data_len,
                                signature_data,
                                signature_data_len,
                                &validity);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] ECDSAVerify():" \
                    " tlApiSignatureVerify failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        message->ecdsa_verify.validity = validity;
    } while(false);

    return ret;
}

tciReturnCode_t tlCryptoCatalog_ECDSASample(void)
{
    tciReturnCode_t tlApiRet=RET_OK;
    Message_t message;

    void* key_data = NULL;
    uint32_t key_data_len;
    void* signature_data = NULL;
    uint32_t signature_data_len;

    tlApiLogPrintf("[Crypto Catalog] %s\n", __func__);

    const teeEccCurveType_t   ec_curves[] = {
            TEE_ECC_CURVE_NIST_P192,
            TEE_ECC_CURVE_NIST_P224,
            TEE_ECC_CURVE_NIST_P256,
            TEE_ECC_CURVE_NIST_P384,
            TEE_ECC_CURVE_NIST_P521
    };

    key_data_len = ECDSA_KEY_SO_SIZE;
    key_data = tlApiMalloc(key_data_len,0);
    if (key_data == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlApiMalloc(): key_data allocation failed (0x%x)\n",
                tlApiRet);
    }

    if (tlApiRet == RET_OK)
    {
        signature_data_len = key_data_len;
        signature_data = tlApiMalloc(signature_data_len,0);
        if (signature_data == NULL)
        {
            tlApiRet = RET_ERR_OUT_OF_MEMORY;
            tlApiLogPrintf("[Crypto Catalog] tlApiMalloc(): signature_data allocation failed (0x%x)\n",
                    tlApiRet);
        }
    }

    if (tlApiRet == RET_OK)
    {
        message.ecdsa_gen_key.key_data     = key_data;
        message.ecdsa_gen_key.key_data_len = key_data_len;
        message.ecdsa_gen_key.curve        = ec_curves[0];

        tlApiRet = tlCryptoCatalog_ECDSAGenerateKeyPair(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_ECDSAGenerateKeyPair(): (0x%x)\n",
            tlApiRet);
    }

    if (tlApiRet == RET_OK)
    {
        message.ecdsa_sign.key_data           = key_data;
        message.ecdsa_sign.key_data_len       = key_data_len;
        message.ecdsa_sign.signature_data     = signature_data;
        message.ecdsa_sign.signature_data_len = signature_data_len;
        message.ecdsa_sign.digest_data        = (void*)test_digest_sha512;
        message.ecdsa_sign.digest_data_len    = sizeof(test_digest_sha512);

        tlApiRet = tlCryptoCatalog_ECDSASign(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_ECDSASign(): (0x%x)\n",
            tlApiRet);
    }

    if (tlApiRet == RET_OK)
    {
        tlApiRet = tlCryptoCatalog_ECDSAVerify(&message);
        if (!message.ecdsa_verify.validity)
        {
            tlApiRet = RET_ERR_VERIFY;
        }
        else
        {
            tlApiLogPrintf("[Crypto Catalog] SUCCESS\n");
        }
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_ECDSAVerify(): (0x%x)\n",
            tlApiRet);
    }


    tlApiFree(key_data);
    tlApiFree(signature_data);

    return tlApiRet;
}
