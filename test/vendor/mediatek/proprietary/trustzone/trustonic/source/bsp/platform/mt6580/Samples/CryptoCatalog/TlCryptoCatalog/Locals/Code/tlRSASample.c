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

/* Static definitions */
static const uint8_t exp_3[]     = { 0x03 };
static const uint8_t exp_5[]     = { 0x05 };
static const uint8_t exp_17[]    = { 0x11 };
static const uint8_t exp_257[]   = { 0x01, 0x01 };
static const uint8_t exp_65537[] = { 0x01, 0x00, 0x01 };

/* Max SO size for RSA key data*/
#define RSA_KEY_SO_SIZE 4096

/**
 * Generate RSA key pair
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_RSAGenerateKeyPair(
    Message_t* message
){
    uint32_t         modlen;
    uint32_t         half_modlen;
    uint32_t         pub_key_len;
    uint32_t         priv_key_len;
    size_t           so_len;
    tlApiResult_t    tlApiRet;
    tciReturnCode_t  ret = RET_OK;
    uint8_t          struct_buf[RSA_KEY_STRUCT_SIZE] = {0}; /* Structure buffer */
    uint8_t          key_buf[KEY_BUFFER_SIZE] = {0}; /* Key buffer */
    uint8_t          so_buf[KEY_BUFFER_SIZE]  = {0};  /* SO key data buffer */
    uint8_t*         ptr_struct_buf = &struct_buf[0];/* Pointer to the key structure buffer */
    uint8_t*         ptr_key_buf    = &key_buf[0]; /* Pointer to key data buffer  */
    uint8_t*         ptr_so_buf     = &so_buf[0];   /* Pointer to SO key data buffer  */
    key_meta_t       metadata       = {0};
    tlApiRsaKey_t*   rsa_key_pair   = NULL;
    tlApiKeyPair_t   key_pair;

    do
    {

    	/* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            tlApiLogPrintf("[Crypto Catalog] RSAGenerateKeyPair(): INVALID BUFFER ret: %d\n", ret);
            break;
        }

        /* Validate key size */
        if ((message->rsa_gen_key.key_size < RSA_KEY_SIZE_512) ||
            (message->rsa_gen_key.key_size > RSA_KEY_SIZE_4096))
        {
            /* Unsupported key size */
            ret = RET_ERR_INVALID_KEY_SIZE;
            tlApiLogPrintf("[Crypto Catalog] RSAGenerateKeyPair():" \
                    " invalid key size = %d\n", message->rsa_gen_key.key_size);
            break;
        }

        /* Initialize secure object length. The value will be updated later */
        message->rsa_gen_key.so_len = 0;

        /* Calculate modulus length */
        modlen = (message->rsa_gen_key.key_size + 7) / 8;
        half_modlen = modlen / 2;
        pub_key_len = modlen * 2; /* Modulus and exponent */

        /**
         * Create key data structure. Handle public key modulus
         * and exponent first
         */
        STRUCT_M(rsa_key_pair, tlApiRsaKey_t,  ptr_struct_buf);
        STRUCT_D(rsa_key_pair->exponent.value, ptr_struct_buf, modlen);
        STRUCT_D(rsa_key_pair->modulus.value,  ptr_struct_buf, modlen);
        rsa_key_pair->exponent.len = modlen;
        rsa_key_pair->modulus.len  = modlen;

        if (message->rsa_gen_key.type == KEY_PAIR_TYPE_RSA)
        {
            STRUCT_D(rsa_key_pair->privateExponent.value, ptr_struct_buf, modlen);
            rsa_key_pair->privateExponent.len = modlen;

            /* P,Q, DP, DQ and Qinv are all set to 0 */
            memset(&rsa_key_pair->privateCrtKey, 0x0, sizeof(rsa_key_pair->privateCrtKey));
        }
        else if (message->rsa_gen_key.type == KEY_PAIR_TYPE_RSACRT)
        {
            STRUCT_D(rsa_key_pair->privateCrtKey.P.value,    ptr_struct_buf, half_modlen);
            STRUCT_D(rsa_key_pair->privateCrtKey.Q.value,    ptr_struct_buf, half_modlen);
            STRUCT_D(rsa_key_pair->privateCrtKey.DP.value,   ptr_struct_buf, half_modlen);
            STRUCT_D(rsa_key_pair->privateCrtKey.DQ.value,   ptr_struct_buf, half_modlen);
            STRUCT_D(rsa_key_pair->privateCrtKey.Qinv.value, ptr_struct_buf, half_modlen);

            rsa_key_pair->privateCrtKey.P.len    = half_modlen;
            rsa_key_pair->privateCrtKey.Q.len    = half_modlen;
            rsa_key_pair->privateCrtKey.DP.len   = half_modlen;
            rsa_key_pair->privateCrtKey.DQ.len   = half_modlen;
            rsa_key_pair->privateCrtKey.Qinv.len = half_modlen;

            rsa_key_pair->privateExponent.value = NULL;
            rsa_key_pair->privateExponent.len = 0;
        }
        else
        {
            /* Invalid RSA key type */
            ret = RET_ERR_INVALID_KEY_TYPE;
            break;
        }

        /* Copy public key exponent data */
        switch (message->rsa_gen_key.exponent)
        {
            case 3:
                /* Public key exponent 3 */
                memcpy(rsa_key_pair->exponent.value, (uint8_t *) exp_3, sizeof(exp_3));
                rsa_key_pair->exponent.len = sizeof(exp_3);
                break;
            case 5:
                /* Public key exponent 5 */
                memcpy(rsa_key_pair->exponent.value, (uint8_t *) exp_5, sizeof(exp_5));
                rsa_key_pair->exponent.len = sizeof(exp_5);
                break;
            case 17:
                /* Public key exponent 17 */
                memcpy(rsa_key_pair->exponent.value, (uint8_t *) exp_17, sizeof(exp_17));
                rsa_key_pair->exponent.len = sizeof(exp_17);
                break;
            case 257:
                /* Public key exponent 257 */
                memcpy(rsa_key_pair->exponent.value, (uint8_t *) exp_257, sizeof(exp_257));
                rsa_key_pair->exponent.len = sizeof(exp_257);
                break;
            case 65537:
                /* Public key exponent 65537 */
                memcpy(rsa_key_pair->exponent.value, (uint8_t *) exp_65537, sizeof(exp_65537));
                rsa_key_pair->exponent.len = sizeof(exp_65537);
                break;
            default:
                tlApiLogPrintf("[Crypto Catalog] RSAGenerateKeyPair():"\
                        " Invalid public key exponent: 0x%x\n", message->rsa_gen_key.exponent);
                ret = RET_ERR_INVALID_EXPONENT;
                break;

        }

        if (ret != RET_OK)
        {
            /* Cannot proceed further */
            break;
        }

        key_pair.rsaKeyPair = rsa_key_pair;

        /* Generate RSA key pair */
        tlApiRet = tlApiGenerateKeyPair(
                            &key_pair,
                            TLAPI_RSA,
                            modlen);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] RSAGenerateKeyPair():"\
                    " RSA generate key pair generation failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_KEY_GENERATION;
            break;
        }

        metadata.key_type         = KEY_TYPE_RSA;
        metadata.rsa_key.key_size = message->rsa_gen_key.key_size;
        metadata.rsa_key.type     = message->rsa_gen_key.type;
        metadata.rsa_key.pub_mod_len = rsa_key_pair->modulus.len;
        metadata.rsa_key.pub_exp_len = rsa_key_pair->exponent.len;

        if (message->rsa_gen_key.type == KEY_PAIR_TYPE_RSA)
        {
            metadata.rsa_key.type = KEY_PAIR_TYPE_RSA;
            metadata.rsa_key.rsa_priv.priv_exp_len = rsa_key_pair->privateExponent.len;
        }
        else if (message->rsa_gen_key.type == KEY_PAIR_TYPE_RSACRT)
        {
            metadata.rsa_key.type = KEY_PAIR_TYPE_RSACRT;
            metadata.rsa_key.rsa_crt_priv.p_len    = rsa_key_pair->privateCrtKey.P.len;
            metadata.rsa_key.rsa_crt_priv.q_len    = rsa_key_pair->privateCrtKey.Q.len;
            metadata.rsa_key.rsa_crt_priv.dp_len   = rsa_key_pair->privateCrtKey.DP.len;
            metadata.rsa_key.rsa_crt_priv.dq_len   = rsa_key_pair->privateCrtKey.DQ.len;
            metadata.rsa_key.rsa_crt_priv.qinv_len = rsa_key_pair->privateCrtKey.Qinv.len;
        }
        else
        {
            /* Invalid RSA key type */
            ret = RET_ERR_INVALID_KEY_TYPE;
            break;
        }

        /* Copy key data into the buffer */
        COPY_KEY_DATA(ptr_key_buf, &metadata, sizeof(key_meta_t)); /* Key metadata */
        COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->modulus.value,   metadata.rsa_key.pub_mod_len);
        /* same amount as modulus data*/
        COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->exponent.value,  metadata.rsa_key.pub_mod_len);

        if (message->rsa_gen_key.type == KEY_PAIR_TYPE_RSA)
        {
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateExponent.value, metadata.rsa_key.rsa_priv.priv_exp_len);
            priv_key_len = metadata.rsa_key.rsa_priv.priv_exp_len;
        }
        else if (message->rsa_gen_key.type == KEY_PAIR_TYPE_RSACRT)
        {
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateCrtKey.P.value,    metadata.rsa_key.rsa_crt_priv.p_len);
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateCrtKey.Q.value,    metadata.rsa_key.rsa_crt_priv.q_len);
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateCrtKey.DP.value,   metadata.rsa_key.rsa_crt_priv.dp_len);
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateCrtKey.DQ.value,   metadata.rsa_key.rsa_crt_priv.dq_len);
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateCrtKey.Qinv.value, metadata.rsa_key.rsa_crt_priv.qinv_len);
            priv_key_len = metadata.rsa_key.rsa_crt_priv.p_len  +
                           metadata.rsa_key.rsa_crt_priv.q_len  +
                           metadata.rsa_key.rsa_crt_priv.dp_len +
                           metadata.rsa_key.rsa_crt_priv.dq_len +
                           metadata.rsa_key.rsa_crt_priv.qinv_len;
        }
        else
        {
            /* Invalid RSA key type */
            ret = RET_ERR_INVALID_KEY_TYPE;
            break;
        }

        /* Calculate secure object length: metadata + public key data + private key data */
        so_len = MC_SO_SIZE(sizeof(key_meta_t) + pub_key_len, priv_key_len);

        /* Position to the beginning of the buffer */
        ptr_key_buf = &key_buf[0];

        /**
         * The following is how RSA key data will be wrapped
         *
         * |-- Key metadata --|--Public key modulus--|--Public key exponent--|-------Private key data--------|

         * |------------------------------plaintext--------------------------|----------encrypted------------|
         *
         * While wrapping data, key metadata and public key data will be in plaintext. Private key part will be
         * encrypted
         */
        uint32_t key_data_len = message->rsa_gen_key.key_data_len;
        uint8_t* key_data     = (uint8_t*)message->rsa_gen_key.key_data;
        if ((key_data == NULL) ||
            (so_len > key_data_len))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }


        tlApiRet = tlApiWrapObject(
                        ptr_key_buf,
                        sizeof(key_meta_t) + pub_key_len, /* Key metadata and public key data (plaintext) */
                        priv_key_len,  /* Private key (encrypted)*/
                        (mcSoHeader_t*)ptr_so_buf,
                        &so_len,
                        MC_SO_CONTEXT_TLT,
                        MC_SO_LIFETIME_PERMANENT,
                        NULL,
                        TLAPI_WRAP_DEFAULT);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf("[Crypto Catalog] RSAGenerateKeyPair():"\
                    " tlApiWrapObject failed (0x%x)\n",tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        memcpy(key_data, ptr_so_buf, so_len);

        message->rsa_gen_key.so_len = so_len;

    } while(false);

    return ret;
}


/**
 * RSA sign plain data and return signature data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_RSASign(
    Message_t* message
){
    tlApiResult_t    tlApiRet;
    tciReturnCode_t  ret = RET_OK;
    uint8_t          key_buf[KEY_BUFFER_SIZE]       = {0};  /* Key buffer */
    uint8_t          signature_buf[KEY_BUFFER_SIZE] = {0};  /* Signature local buffer */
    uint8_t*         ptr_key_buf       = &key_buf[0]; /* Pointer to key data buffer  */
    uint8_t*         ptr_signature_buf = &signature_buf[0]; /* Pointer to signature local buffer  */
    tlApiCrSession_t session_handle    = {0};
    tlApiCipherAlg_t cipher_algo;
    size_t           so_len   = 0;
    tlApiRsaKey_t    rsa_key  = {0};
    tlApiKey_t       key      = {0};
    key_meta_t       metadata = {0};

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Retrieve data from the TCI buffer */
        uint32_t key_data_len   = so_len = message->rsa_sign.key_data_len;
        uint32_t plain_data_len = message->rsa_sign.plain_data_len;
        uint8_t* key_data       = (uint8_t*)message->rsa_sign.key_data;
        uint8_t* plain_data     = (uint8_t*)message->rsa_sign.plain_data;
        size_t   signature_data_len  = message->rsa_sign.signature_data_len;
        uint8_t* signature_data = (uint8_t*)message->rsa_sign.signature_data;
        if ((key_data == NULL) ||
            (plain_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (plain_data_len == 0)   ||
            (signature_data_len == 0))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf("[Crypto Catalog] RSASign():" \
                    " invalid key data length = %d\n", key_data_len);
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
            tlApiLogPrintf("[Crypto Catalog] RSASign(): tlApiUnwrapObject failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        /* Extract encrypted data length information from the secure object header */
        mcSoHeader_t* soHeader = (mcSoHeader_t*)key_data;

        /**
         * Extract private key data including modulus and exponent lengths
         * The following will position pointer to the beginning of the private key
         * modulus data
         */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        /* Validate public key modulus and exponent lengths */
        if ((metadata.rsa_key.pub_mod_len > MAX_MOD_LENGTH) ||
            (metadata.rsa_key.pub_exp_len > MAX_MOD_LENGTH))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }


        /* About to parse key data. Set offset to the beginning of public key data */
        uint32_t offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);

        /* Fetch public key data */
        rsa_key.modulus.value = ptr_key_buf + offset;
        rsa_key.modulus.len = metadata.rsa_key.pub_mod_len;
        offset += metadata.rsa_key.pub_mod_len;
        rsa_key.exponent.value = ptr_key_buf + offset;
        rsa_key.exponent.len = metadata.rsa_key.pub_exp_len;

        if (metadata.rsa_key.type == KEY_PAIR_TYPE_RSA)
        {
            /* Validate RSA private key modulus and exponent lengths */
            if (metadata.rsa_key.rsa_priv.priv_exp_len > MAX_MOD_LENGTH)
            {
                ret = RET_ERR_INVALID_BUFFER;
                break;
            }

            /* Fetch private key data */
            offset = sizeof(mcSoHeader_t) + soHeader->plainLen;
            rsa_key.privateExponent.value = ptr_key_buf + offset;
            rsa_key.privateExponent.len = metadata.rsa_key.rsa_priv.priv_exp_len;
            key.rsaKey = &rsa_key;
        }
        else if (metadata.rsa_key.type == KEY_PAIR_TYPE_RSACRT)
        {
            /* Validate RSA CRT private key data */
            if ((metadata.rsa_key.rsa_crt_priv.p_len > MAX_MOD_LENGTH)  ||
                (metadata.rsa_key.rsa_crt_priv.q_len > MAX_MOD_LENGTH)  ||
                (metadata.rsa_key.rsa_crt_priv.dp_len > MAX_MOD_LENGTH) ||
                (metadata.rsa_key.rsa_crt_priv.dq_len > MAX_MOD_LENGTH) ||
                (metadata.rsa_key.rsa_crt_priv.qinv_len > MAX_MOD_LENGTH))
            {
                ret = RET_ERR_INVALID_BUFFER;
                break;
            }

            /* Fetch private key data */
            offset = sizeof(mcSoHeader_t) + soHeader->plainLen;
            rsa_key.privateCrtKey.P.value    = ptr_key_buf + offset;
            offset += metadata.rsa_key.rsa_crt_priv.p_len;
            rsa_key.privateCrtKey.Q.value    = ptr_key_buf + offset;
            offset += metadata.rsa_key.rsa_crt_priv.q_len;
            rsa_key.privateCrtKey.DP.value   = ptr_key_buf + offset;
            offset += metadata.rsa_key.rsa_crt_priv.dp_len;
            rsa_key.privateCrtKey.DQ.value   = ptr_key_buf + offset;
            offset += metadata.rsa_key.rsa_crt_priv.dq_len;
            rsa_key.privateCrtKey.Qinv.value = ptr_key_buf + offset;

            /* Update lengths */
            rsa_key.privateCrtKey.P.len    = metadata.rsa_key.rsa_crt_priv.p_len;
            rsa_key.privateCrtKey.Q.len    = metadata.rsa_key.rsa_crt_priv.q_len;
            rsa_key.privateCrtKey.DP.len   = metadata.rsa_key.rsa_crt_priv.dp_len;
            rsa_key.privateCrtKey.DQ.len   = metadata.rsa_key.rsa_crt_priv.dq_len;
            rsa_key.privateCrtKey.Qinv.len = metadata.rsa_key.rsa_crt_priv.qinv_len;

            rsa_key.privateExponent.value = NULL;
            rsa_key.privateExponent.len = 0;

            key.rsaKey = &rsa_key;
        }
        else
        {
            /* Invalid key type */
            ret = RET_ERR_INVALID_KEY_TYPE;
            break;
        }

        if (message->rsa_sign.algorithm == SIG_ALG_RSA_NODIGEST_NOPADDING)
        {
            cipher_algo = ((metadata.rsa_key.type == KEY_PAIR_TYPE_RSA)
                            ? TLAPI_ALG_RSA_NOPAD : TLAPI_ALG_RSACRT_NOPAD);

            tlApiRet = tlApiCipherInit(
                            &session_handle,
                            cipher_algo,
                            TLAPI_MODE_DECRYPT,
                            &key);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf("[Crypto Catalog] RSASign():" \
                        " tlApiCipherInit failed (0x%x)\n", tlApiRet);
                ret = RET_ERR_SIGN;
                break;
            }

            /* Proceed with decrypting data */
            tlApiRet =  tlApiCipherDoFinal(
                            session_handle,
                            plain_data,
                            plain_data_len,
                            ptr_signature_buf,
                            &signature_data_len);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf("[Crypto Catalog] RSASign():" \
                        " tlApiCipherDoFinal failed (0x%x)\n",
                        tlApiRet);
                ret = RET_ERR_SIGN;
                break;
            }

            /* Copy signature data */
            memcpy(signature_data, ptr_signature_buf, signature_data_len);
        }
        else
        {
            /* RSA signature input data is padded by the TLC */
            ret = RET_ERR_NOT_SUPPORTED;
            break;
        }

        /* Update signature data length accordingly */
        message->rsa_sign.signature_data_len = signature_data_len;

    } while(false);

    return ret;
}


/**
 * RSA verify signature and return validity status to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoCatalog_RSAVerify(
    Message_t* message
){
    tlApiResult_t    tlApiRet;
    tciReturnCode_t  ret = RET_OK;
    uint8_t          key_buf[KEY_BUFFER_SIZE]       = {0}; /* Key buffer */
    uint8_t          signature_buf[RSA_MAX_SIG_SIZE]= {0}; /* Encrypted signature buffer */
    uint8_t*         ptr_key_buf = &key_buf[0];  /* Pointer to key data buffer  */
    uint8_t*         ptr_signature_buf = &signature_buf[0]; /* Pointer to encrypted signature data buffer  */
    tlApiCrSession_t session_handle = {0};
    tlApiCipherAlg_t cipher_algo;
    tlApiRsaKey_t    rsa_key  = {0};
    tlApiKey_t       key      = {0};
    key_meta_t       metadata = {0};
    size_t           so_len   = 0;
    size_t           sbuf_len = RSA_MAX_SIG_SIZE; /* Size of encrypted signature data buffer */

    do
    {
        /* Validate buffer */
        if (message == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Retrieve date from the TCI buffer */
        uint32_t key_data_len       =  so_len  = message->rsa_verify.key_data_len;
        uint32_t plain_data_len     = message->rsa_verify.plain_data_len;
        uint32_t signature_data_len =  message->rsa_verify.signature_data_len;
        uint8_t* key_data           = (uint8_t*)message->rsa_verify.key_data;
        uint8_t* plain_data         = (uint8_t*)message->rsa_verify.plain_data;
        uint8_t* signature_data     = (uint8_t*)message->rsa_verify.signature_data;
        if ((key_data == NULL) ||
            (plain_data == NULL) ||
            (signature_data == NULL))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf("[Crypto Catalog] RSAVerify(): invalid key data length\n");
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
            tlApiLogPrintf("[Crypto Catalog] RSAVerify():"\
                    " tlApiUnwrapObject failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        /**
         * Extract Public key data including modulus and exponent lengths
         * The following will position pointer to the beginning of public key
         * modulus data
         */
        memcpy(&metadata, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        if ((metadata.rsa_key.pub_mod_len > MAX_MOD_LENGTH) ||
            (metadata.rsa_key.pub_exp_len > MAX_MOD_LENGTH))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* About to parse key data. Set offset to the beginning of public key data */
        uint32_t offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);

        /* Fetch public key data */
        rsa_key.modulus.value = ptr_key_buf + offset;
        rsa_key.modulus.len = metadata.rsa_key.pub_mod_len;
        offset += metadata.rsa_key.pub_mod_len;
        rsa_key.exponent.value = ptr_key_buf + offset;
        rsa_key.exponent.len =  metadata.rsa_key.pub_exp_len;

        /* P,Q, DP, DQ and Qinv are all set to 0 */
        memset(&rsa_key.privateCrtKey, 0, sizeof(rsa_key.privateCrtKey));
        rsa_key.privateExponent.value = NULL;
        rsa_key.privateExponent.len = 0;
        key.rsaKey = &rsa_key;

        /* Initialize signature validity with default value */
        message->rsa_verify.validity = 0;

        if (message->rsa_verify.algorithm == SIG_ALG_RSA_NODIGEST_NOPADDING)
        {
            /* Verify operation based on RSA public key modulus and exponent */
            cipher_algo = TLAPI_ALG_RSA_NOPAD;

            /* Init cipher */
            tlApiRet = tlApiCipherInit(
                                     &session_handle,
                                     cipher_algo,
                                     TLAPI_MODE_ENCRYPT,
                                     &key);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf("[Crypto Catalog] RSAVerify():" \
                        " tlApiCipherInit failed (0x%x)\n", tlApiRet);
                ret = RET_ERR_VERIFY;
                break;
            }

            /* Proceed with encrypting data */
            tlApiRet =  tlApiCipherDoFinal(
                                    session_handle,
                                    signature_data,
                                    signature_data_len,
                                    ptr_signature_buf,
                                    &sbuf_len);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf("[Crypto Catalog] RSAVerify(): tlApiCipherDoFinal failed (0x%x)\n",
                        tlApiRet);
                ret = RET_ERR_VERIFY;
                break;
            }

            /**
             * After cipher operation, memory pointed by pSbuf contains encrypted data. It must have same content as plainData
             * and same length.
             */
            if (sbuf_len != plain_data_len)
            {
                tlApiLogPrintf("[Crypto Catalog] RSAVerify(): Data mismatch after cipher operation\n");
                ret = RET_ERR_VERIFY;
                break;
            }

            /* Check if signature is valid */
            message->rsa_verify.validity = ((0 == memcmp(ptr_signature_buf, plain_data, plain_data_len)) ? 1 : 0);
        }
        else
        {
            /* RSA verify input data is padded by the TLC */
            ret = RET_ERR_NOT_SUPPORTED;
            break;
        }


    } while(false);

    return ret;
}


tciReturnCode_t tlCryptoCatalog_RSASample(void)
{
    tciReturnCode_t tlApiRet=RET_OK;
    Message_t message;

    void* key_data = NULL;
    uint32_t key_data_len;
    void* signature_data = NULL;
    uint32_t signature_data_len;
    void* msg = NULL;
    uint32_t msg_len;

    tlApiLogPrintf("[Crypto Catalog] %s\n", __func__);

    key_data_len = RSA_KEY_SO_SIZE;
    key_data = tlApiMalloc(key_data_len,0);
    if (key_data == NULL)
    {
        tlApiRet = RET_ERR_OUT_OF_MEMORY;
        tlApiLogPrintf("[Crypto Catalog] tlApiMalloc(): key_data allocation failed (0x%x)\n",
                tlApiRet);
    }

    if (tlApiRet == RET_OK)
    {
        msg_len = key_data_len>>3; /* bits to bytes */
        msg = tlApiMalloc(msg_len,0);
        if (msg == NULL)
        {
            tlApiRet = RET_ERR_OUT_OF_MEMORY;
            tlApiLogPrintf("[Crypto Catalog] tlApiMalloc(): msg allocation failed (0x%x)\n",
                    tlApiRet);
        }
    }

    if (tlApiRet == RET_OK)
    {
        signature_data_len = msg_len;
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
        message.rsa_gen_key.type = KEY_PAIR_TYPE_RSA;
        message.rsa_gen_key.key_size = RSA_KEY_SIZE_4096;
        message.rsa_gen_key.exponent = 3;
        message.rsa_gen_key.key_data = key_data;
        message.rsa_gen_key.key_data_len = key_data_len;

        tlApiRet = tlCryptoCatalog_RSAGenerateKeyPair(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_RSAGenerateKeyPair(): (0x%x)\n",
            tlApiRet);
    }

    if (tlApiRet == RET_OK)
    {
        memset((void*)msg, 0x12, msg_len);

        message.rsa_sign.key_data = key_data;
        message.rsa_sign.key_data_len = key_data_len;
        message.rsa_sign.plain_data = msg;
        message.rsa_sign.plain_data_len = msg_len;
        message.rsa_sign.algorithm = SIG_ALG_RSA_NODIGEST_NOPADDING;
        message.rsa_sign.signature_data = signature_data;
        message.rsa_sign.signature_data_len = signature_data_len;

        tlApiRet = tlCryptoCatalog_RSASign(&message);
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_RSASign(): (0x%x)\n",
            tlApiRet);
    }

    if (tlApiRet == RET_OK)
    {
        tlApiRet = tlCryptoCatalog_RSAVerify(&message);
        if (!message.rsa_verify.validity)
        {
            tlApiRet = RET_ERR_VERIFY;
        }
        else
        {
            tlApiLogPrintf("[Crypto Catalog] SUCCESS\n");
        }
        tlApiLogPrintf("[Crypto Catalog] tlCryptoCatalog_RSAVerify(): (0x%x)\n",
            tlApiRet);
    }

    tlApiFree(key_data);
    tlApiFree(signature_data);
    tlApiFree(msg);

    return tlApiRet;
}
