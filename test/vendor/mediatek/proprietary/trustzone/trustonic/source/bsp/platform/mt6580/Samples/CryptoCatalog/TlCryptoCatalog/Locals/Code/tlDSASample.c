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

/* Max SO size for DSA key data*/
#define DSA_KEY_SO_SIZE 2048

/**
 * Generate DSA key pair
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_DSAGenerateKeyPair( Message_t* message )
{
    tciReturnCode_t  ret = RET_OK;
    tlApiResult_t  tlApiRet;
    uint8_t        key_buf[512] = {0}; /* Key pair buffer */
    uint8_t        so_buf[DSA_KEY_BUFFER_SIZE] = {0}; /* key data buffer for storing SO */
    uint8_t*       ptr_key_buf  = &key_buf[0];
    uint8_t*       ptr_so_buf   = &so_buf[0];
    key_meta_t     metadata     = {0};
    size_t         so_len       = 0;
    tlApiDsaKey_t  dsa_key_pair = {0};
    tlApiKeyPair_t key_pair     = {0};


    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data = (uint8_t*)message->dsa_gen_key.key_data;
        uint8_t* p       = (uint8_t*)message->dsa_gen_key.p;
        uint8_t* q       = (uint8_t*)message->dsa_gen_key.q;
        uint8_t* g       = (uint8_t*)message->dsa_gen_key.g;
        uint32_t key_data_len = message->dsa_gen_key.key_data_len;
        uint32_t p_len  = message->dsa_gen_key.p_len;
        uint32_t q_len  = message->dsa_gen_key.q_len;
        uint32_t g_len  = message->dsa_gen_key.g_len;
        uint32_t x_len  = message->dsa_gen_key.x_len;
        uint32_t y_len  = message->dsa_gen_key.y_len;
        if ((key_data == NULL) ||
            (p == NULL) ||
            (q == NULL) ||
            (g == NULL) ||
            (key_data_len == 0) ||
            (p_len == 0) ||
            (q_len == 0) ||
            (g_len == 0) ||
            (x_len == 0) ||
            (y_len == 0))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }


        dsa_key_pair.p.value = p;
        dsa_key_pair.q.value = q;
        dsa_key_pair.g.value = g;
        dsa_key_pair.y.value = ptr_key_buf;
        dsa_key_pair.x.value = ptr_key_buf + y_len;
        dsa_key_pair.p.len = p_len;
        dsa_key_pair.q.len = q_len;
        dsa_key_pair.g.len = g_len;
        dsa_key_pair.y.len = y_len;
        dsa_key_pair.x.len = x_len;

        key_pair.dsaKeyPair = &dsa_key_pair;

        /* Generate DSA key pair */
        tlApiRet = tlApiGenerateKeyPair(
                            &key_pair,
                            TLAPI_DSA,
                            p_len);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] DSAGenerateKeyPair():"\
                    " DSA generate key pair generation failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_KEY_GENERATION;
            break;
        }

        /**
         * The following is how DSA key data will be wrapped
         *
         * |-- Key metadata --|--p--|--q--|--g--|--y--|--Private key data (x)--|
         *
         * |--------------plaintext-------------------|------encrypted---------|
         *
         * While wrapping data, key metadata and public key data will be in plaintext. Private key part will be
         * encrypted
         */

        /* Calculate secure object length: metadata + p, q, g, y and x data (x encrypted) */
        so_len = MC_SO_SIZE(sizeof(key_meta_t) + p_len + q_len + g_len + y_len, x_len);

        /* Populate metadata */
        metadata.key_type = KEY_TYPE_DSA;
        metadata.dsa_key.p_len  = p_len;
        metadata.dsa_key.q_len  = q_len;
        metadata.dsa_key.g_len  = g_len;
        metadata.dsa_key.x_len  = x_len; // Private key
        metadata.dsa_key.y_len  = y_len; // Public key y = g ^ x % p

        /**
         * Copy key data into the buffer
         *
         * Both public and private key data are in 32 bit digit array (little endian)
         * in the key context data (pPrv and pPub). We store them as they are so that
         * when we set key context data in DSASign and DSAVerify, we don't need to
         * convert them again
         */
        memcpy(ptr_so_buf, &metadata, sizeof(key_meta_t));
        uint32_t offset = sizeof(key_meta_t);
        memcpy(ptr_so_buf+offset, dsa_key_pair.p.value, p_len);
        offset += p_len;
        memcpy(ptr_so_buf+offset, dsa_key_pair.q.value, q_len);
        offset += q_len;
        memcpy(ptr_so_buf+offset, dsa_key_pair.g.value, g_len);
        offset += g_len;
        memcpy(ptr_so_buf+offset, dsa_key_pair.y.value, y_len);
        offset += y_len;
        memcpy(ptr_so_buf+offset, dsa_key_pair.x.value, x_len);

        /* Position to the beginning of the buffer */
        ptr_so_buf = &so_buf[0];

        tlApiRet = tlApiWrapObject(
                        ptr_so_buf,
                        sizeof(key_meta_t) + p_len + q_len + g_len + y_len,
                        x_len,  /* Private key (encrypted)*/
                        (mcSoHeader_t*)key_data,
                        &so_len,
                        MC_SO_CONTEXT_TLT,
                        MC_SO_LIFETIME_PERMANENT,
                        NULL,
                        TLAPI_WRAP_DEFAULT);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] DSAGenerateKeyPair():"\
                    " tlApiWrapObject failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        message->dsa_gen_key.so_len = so_len;

    } while(false);

    return ret;
}


/**
 * DSA sign plain data and return signature data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_DSASign( Message_t* message )
{
    tciReturnCode_t  ret = RET_OK;
    tlApiResult_t    tlApiRet;
    uint8_t          key_buf[DSA_KEY_BUFFER_SIZE];
    uint8_t*         ptr_key_buf    = &key_buf[0];
    key_meta_t       metadata       = {0};
    size_t           so_len         = 0;
    tlApiCrSession_t session_handle = {0};
    tlApiDsaKey_t    dsa_key        = {0};
    tlApiKey_t       key            = {0};

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data       = (uint8_t*)message->dsa_sign.key_data;
        uint8_t* digest_data    = (uint8_t*)message->dsa_sign.digest_data;
        uint8_t* signature_data = (uint8_t*)message->dsa_sign.signature_data;
        uint32_t key_data_len   = so_len  = message->dsa_sign.key_data_len;
        uint32_t digest_data_len    = message->dsa_sign.digest_data_len;
        size_t   signature_data_len = message->dsa_sign.signature_data_len;
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
        if (key_data_len > DSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf("[Crypto Catalog] DSASign(): invalid key data length\n");
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
            tlApiLogPrintf("[Crypto Catalog] DSASign():" \
                    "tlApiUnwrapObject failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        /* Extract metadata */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        uint32_t offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);
        dsa_key.p.value = ptr_key_buf + offset;
        offset += metadata.dsa_key.p_len;
        dsa_key.q.value = ptr_key_buf + offset;
        offset += metadata.dsa_key.q_len;
        dsa_key.g.value = ptr_key_buf + offset;
        /* Position to the beginning of private key (x)*/
        offset = offset + metadata.dsa_key.g_len +  metadata.dsa_key.y_len;
        dsa_key.x.value = ptr_key_buf + offset;
        dsa_key.y.value = NULL;

        dsa_key.p.len = metadata.dsa_key.p_len;
        dsa_key.q.len = metadata.dsa_key.q_len;
        dsa_key.g.len = metadata.dsa_key.g_len;
        dsa_key.x.len = metadata.dsa_key.x_len;
        dsa_key.y.len = 0;

        key.dsaKey = &dsa_key;

        /* Init signature operation */
        tlApiRet = tlApiSignatureInit(
                                  &session_handle,
                                  &key,
                                  TLAPI_MODE_SIGN,
                                  TLAPI_SIG_DSA_RAW);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] DSASign():" \
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
            tlApiLogPrintf("[Crypto Catalog] DSASign():" \
                    " tlApiSignatureSign failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }


        message->dsa_sign.signature_data_len = signature_data_len;

    } while(false);

    return ret;
}


/**
 * DSA verify signature and return validity status to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_DSAVerify( Message_t* message )
{
    tciReturnCode_t  ret = RET_OK;
    tlApiResult_t    tlApiRet;
    uint8_t          key_buf[DSA_KEY_BUFFER_SIZE] = {0};
    uint8_t*         ptr_key_buf = &key_buf[0];
    key_meta_t       metadata       = {0};
    size_t           so_len;
    tlApiCrSession_t session_handle = {0};
    tlApiDsaKey_t    dsa_key        = {0};
    tlApiKey_t       key            = {0};
    bool             validity       = false;

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data           = (uint8_t*)message->dsa_verify.key_data;
        uint8_t* digest_data        = (uint8_t*)message->dsa_verify.digest_data;
        uint8_t* signature_data     = (uint8_t*)message->dsa_verify.signature_data;
        uint32_t key_data_len       = so_len  = message->dsa_verify.key_data_len;
        uint32_t digest_data_len    = message->dsa_verify.digest_data_len;
        uint32_t signature_data_len = message->dsa_verify.signature_data_len;
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
        if (key_data_len > DSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf("[Crypto Catalog] DSAVerify(): invalid key data length\n");
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
            tlApiLogPrintf("[Crypto Catalog] DSAVerify():" \
                    " tlApiUnwrapObject failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }


        /* Extract metadata */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        uint32_t offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);
        dsa_key.p.value = ptr_key_buf + offset;
        offset += metadata.dsa_key.p_len;
        dsa_key.q.value = ptr_key_buf + offset;
        offset += metadata.dsa_key.q_len;
        dsa_key.g.value = ptr_key_buf + offset;
        /* Position to the beginning of private key (x)*/
        offset += metadata.dsa_key.g_len;
        dsa_key.y.value = ptr_key_buf + offset;

        /* Set the private key value to NULL */
        dsa_key.x.value = NULL;

        dsa_key.p.len = metadata.dsa_key.p_len;
        dsa_key.q.len = metadata.dsa_key.q_len;
        dsa_key.g.len = metadata.dsa_key.g_len;
        dsa_key.y.len = metadata.dsa_key.y_len;
        dsa_key.x.len = 0;

        key.dsaKey = &dsa_key;

        /* Init signature operation */
        tlApiRet = tlApiSignatureInit(
                                  &session_handle,
                                  &key,
                                  TLAPI_MODE_VERIFY,
                                  TLAPI_SIG_DSA_RAW);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] DSAVerify(): tlApiSignatureInit failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        /* Set initial response as signature not valid */
        message->dsa_verify.validity = false;

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
            tlApiLogPrintf("[Crypto Catalog] DSAVerify():" \
                    " tlApiSignatureVerify failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        message->dsa_verify.validity = validity;

    } while(false);

    return ret;
}

tciReturnCode_t tlCryptoCatalog_DSASample(void)
{
    tciReturnCode_t tlApiRet = RET_OK;
    Message_t message;

    void* key_data = NULL;
    uint32_t key_data_len = DSA_KEY_SO_SIZE;
    void* signature = NULL;
    uint32_t signature_data_len = 320; /* TODO check size */
    void* msg = NULL;
    uint32_t msg_data_len = DSA_KEY_SO_SIZE; /* TODO check size */
    void* p_data = NULL;
    uint32_t p_data_len = sizeof(p);
    void* q_data = NULL;
    uint32_t q_data_len = sizeof(q);
    void* g_data = NULL;
    uint32_t g_data_len = sizeof(g);

    tlApiLogPrintf("[Crypto Catalog] %s\n", __func__);

    key_data = tlApiMalloc(key_data_len,0);
    if (key_data == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample(): key_data allocation failed (0x%x)\n",
                tlApiRet);
    }

    signature = tlApiMalloc(signature_data_len,0);
    if (signature == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample(): signature allocation failed (0x%x)\n",
                tlApiRet);
    }

    msg = tlApiMalloc(msg_data_len,0);
    if (msg == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample(): msg allocation failed (0x%x)\n",
                tlApiRet);
    }

    p_data = tlApiMalloc(p_data_len,0);
    if (p_data == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample(): p_data allocation failed (0x%x)\n",
                tlApiRet);
    }
    q_data = tlApiMalloc(q_data_len,0);
    if (q_data == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample(): q_data allocation failed (0x%x)\n",
                tlApiRet);
    }
    g_data = tlApiMalloc(g_data_len,0);
    if (g_data == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample(): g_data allocation failed (0x%x)\n",
                tlApiRet);
    }

    if (RET_OK == tlApiRet)
    {
        /* Initialize buffer */
        memset(key_data, 0, key_data_len);
        memset(signature, 0, signature_data_len);
        memset(msg, 0xAB, msg_data_len);
        memcpy(p_data, p, p_data_len);
        memcpy(q_data, q, q_data_len);
        memcpy(g_data, g, g_data_len);
        /* Set parameters */
        message.dsa_gen_key.p = p_data;
        message.dsa_gen_key.q = q_data;
        message.dsa_gen_key.g = g_data;
        message.dsa_gen_key.p_len = p_data_len;
        message.dsa_gen_key.q_len = q_data_len;
        message.dsa_gen_key.g_len = g_data_len;
        message.dsa_gen_key.x_len = q_data_len; /* Same as q length*/
        message.dsa_gen_key.y_len = p_data_len; /* Same as p length*/
        message.dsa_gen_key.key_data = key_data;
        message.dsa_gen_key.key_data_len = key_data_len;

        tlApiRet = tlCryptoCatalog_DSAGenerateKeyPair(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSAGenerateKeyPair(): (0x%x)\n",
                tlApiRet);
    }
    if (RET_OK == tlApiRet)
    {
        /* Set parameters */
        message.dsa_sign.key_data = key_data;
        message.dsa_sign.key_data_len = message.dsa_gen_key.so_len;
        message.dsa_sign.digest_data = msg;
        message.dsa_sign.digest_data_len = msg_data_len;
        message.dsa_sign.signature_data = signature;
        message.dsa_sign.signature_data_len = signature_data_len;

        tlApiRet = tlCryptoCatalog_DSASign(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASign(): (0x%x)\n",
                tlApiRet);
    }

    if (RET_OK == tlApiRet)
    {
        tlApiRet = tlCryptoCatalog_DSAVerify(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSAVerify(): (0x%x)\n",
                tlApiRet);

        if (!message.dsa_verify.validity)
        {
            tlApiRet = RET_ERR_VERIFY;
        }
        else
        {
            tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_DSASample: SUCCESS\n");
        }
    }

    tlApiFree(key_data);
    tlApiFree(signature);
    tlApiFree(msg);
    tlApiFree(p_data);
    tlApiFree(q_data);
    tlApiFree(g_data);

    return tlApiRet;
}
