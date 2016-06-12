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


#include "tlCryptoHandler.h"

/* Static definitions */
static const uint8_t exp_3[]     = { 0x03 };
static const uint8_t exp_5[]     = { 0x05 };
static const uint8_t exp_17[]    = { 0x11 };
static const uint8_t exp_257[]   = { 0x01, 0x01 };
static const uint8_t exp_65537[] = { 0x01, 0x00, 0x01 };

static const uint32_t SECP192R1_BITS = 192;
static const uint32_t SECP224R1_BITS = 224;
static const uint32_t SECP256R1_BITS = 256;
static const uint32_t SECP384R1_BITS = 384;
static const uint32_t SECP521R1_BITS = 521;


/**
 * Generate RSA key pair
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_RSAGenerateKeyPair(
    tci_t* pTci
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            tlApiLogPrintf(TATAG"RSAGenerateKeyPair(): INVALID BUFFER ret: %d\n", ret);
            break;
        }

        /* Validate key size */
        if ((pTci->message.rsa_gen_key.key_size < RSA_KEY_SIZE_512) ||
            (pTci->message.rsa_gen_key.key_size > RSA_KEY_SIZE_4096))
        {
            /* Unsupported key size */
            ret = RET_ERR_INVALID_KEY_SIZE;
            tlApiLogPrintf(TATAG"RSAGenerateKeyPair():" \
                    " invalid key size = %d\n", pTci->message.rsa_gen_key.key_size);
            break;
        }

        /* Initialize secure object length. The value will be updated later */
        pTci->message.rsa_gen_key.so_len = 0;

        /* Calculate modulus length */
        modlen = (pTci->message.rsa_gen_key.key_size + 7) / 8;
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

        if (pTci->message.rsa_gen_key.type == KEY_PAIR_TYPE_RSA)
        {
            STRUCT_D(rsa_key_pair->privateExponent.value, ptr_struct_buf, modlen);
            rsa_key_pair->privateExponent.len = modlen;

            /* P,Q, DP, DQ and Qinv are all set to 0 */
            memset(&rsa_key_pair->privateCrtKey, 0x0, sizeof(rsa_key_pair->privateCrtKey));
        }
        else if (pTci->message.rsa_gen_key.type == KEY_PAIR_TYPE_RSACRT)
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
        switch (pTci->message.rsa_gen_key.exponent)
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
                tlApiLogPrintf(TATAG"RSAGenerateKeyPair():"\
                        " Invalid public key exponent: 0x%x\n", pTci->message.rsa_gen_key.exponent);
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
            tlApiLogPrintf(TATAG"RSAGenerateKeyPair():"\
                    " RSA generate key pair generation failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_KEY_GENERATION;
            break;
        }

        metadata.key_type         = KEY_TYPE_RSA;
        metadata.rsa_key.key_size = pTci->message.rsa_gen_key.key_size;
        metadata.rsa_key.type     = pTci->message.rsa_gen_key.type;
        metadata.rsa_key.pub_mod_len = rsa_key_pair->modulus.len;
        metadata.rsa_key.pub_exp_len = rsa_key_pair->exponent.len;

        if (pTci->message.rsa_gen_key.type == KEY_PAIR_TYPE_RSA)
        {
            metadata.rsa_key.type = KEY_PAIR_TYPE_RSA;
            metadata.rsa_key.rsa_priv.priv_exp_len = rsa_key_pair->privateExponent.len;
        }
        else if (pTci->message.rsa_gen_key.type == KEY_PAIR_TYPE_RSACRT)
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

        if (pTci->message.rsa_gen_key.type == KEY_PAIR_TYPE_RSA)
        {
            COPY_KEY_DATA(ptr_key_buf, rsa_key_pair->privateExponent.value, metadata.rsa_key.rsa_priv.priv_exp_len);
            priv_key_len = metadata.rsa_key.rsa_priv.priv_exp_len;
        }
        else if (pTci->message.rsa_gen_key.type == KEY_PAIR_TYPE_RSACRT)
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
        uint32_t key_data_len = pTci->message.rsa_gen_key.key_data_len;
        uint8_t* key_data     = (uint8_t*)pTci->message.rsa_gen_key.key_data;
        if ((key_data == NULL) ||
            (so_len > key_data_len) ||
            (!isNwdBufferValid(key_data, key_data_len)))
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
            tlApiLogPrintf(TATAG"RSAGenerateKeyPair():"\
                    " tlApiWrapObject failed (0x%x)\n",tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        memcpy(key_data, ptr_so_buf, so_len);

        pTci->message.rsa_gen_key.so_len = so_len;

    } while(false);

    return ret;
}


/**
 * RSA sign plain data and return signature data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_RSASign(
    tci_t* pTci
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Retrieve data from the TCI buffer */
        uint32_t key_data_len   = so_len = pTci->message.rsa_sign.key_data_len;
        uint32_t plain_data_len = pTci->message.rsa_sign.plain_data_len;
        uint8_t* key_data       = (uint8_t*)pTci->message.rsa_sign.key_data;
        uint8_t* plain_data     = (uint8_t*)pTci->message.rsa_sign.plain_data;
        size_t   signature_data_len  = pTci->message.rsa_sign.signature_data_len;
        uint8_t* signature_data = (uint8_t*)pTci->message.rsa_sign.signature_data;
        if ((key_data == NULL) ||
            (plain_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (plain_data_len == 0)   ||
            (signature_data_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(plain_data, plain_data_len)) ||
            (!isNwdBufferValid(signature_data, signature_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"RSASign():" \
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
            tlApiLogPrintf(TATAG"RSASign(): tlApiUnwrapObject failed (0x%x)\n",
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

        if (pTci->message.rsa_sign.algorithm == SIG_ALG_RSA_NODIGEST_NOPADDING)
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
                tlApiLogPrintf(TATAG"RSASign():" \
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
                tlApiLogPrintf(TATAG"RSASign():" \
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
            /* The keymaster assumes that RSA signature input data is padded by the TLC */
            ret = RET_ERR_NOT_SUPPORTED;
            break;
        }

        /* Update signature data length accordingly */
        pTci->message.rsa_sign.signature_data_len = signature_data_len;

    } while(false);

    return ret;
}


/**
 * RSA verify signature and return validity status to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_RSAVerify(
    tci_t* pTci
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Retrieve date from the TCI buffer */
        uint32_t key_data_len       =  so_len  = pTci->message.rsa_verify.key_data_len;
        uint32_t plain_data_len     = pTci->message.rsa_verify.plain_data_len;
        uint32_t signature_data_len =  pTci->message.rsa_verify.signature_data_len;
        uint8_t* key_data           = (uint8_t*)pTci->message.rsa_verify.key_data;
        uint8_t* plain_data         = (uint8_t*)pTci->message.rsa_verify.plain_data;
        uint8_t* signature_data     = (uint8_t*)pTci->message.rsa_verify.signature_data;
        if ((key_data == NULL) ||
            (plain_data == NULL) ||
            (signature_data == NULL) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(plain_data, plain_data_len)) ||
            (!isNwdBufferValid(signature_data, signature_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"RSAVerify(): invalid key data length\n");
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
            tlApiLogPrintf(TATAG"RSAVerify():"\
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
        pTci->message.rsa_verify.validity = 0;

        if (pTci->message.rsa_verify.algorithm == SIG_ALG_RSA_NODIGEST_NOPADDING)
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
                tlApiLogPrintf(TATAG"RSAVerify():" \
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
                tlApiLogPrintf(TATAG"RSAVerify(): tlApiCipherDoFinal failed (0x%x)\n",
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
                tlApiLogPrintf(TATAG"RSAVerify(): Data mismatch after cipher operation\n");
                ret = RET_ERR_VERIFY;
                break;
            }

            /* Check if signature is valid */
            pTci->message.rsa_verify.validity = ((0 == memcmp(ptr_signature_buf, plain_data, plain_data_len)) ? 1 : 0);
        }
        else
        {
            /* The keymaster assumes that RSA verify input data is padded by the TLC */
            ret = RET_ERR_NOT_SUPPORTED;
            break;
        }


    } while(false);

    return ret;
}


/**
 * Wrap and return key data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_KeyImport(
        tci_t* pTci
){
    tlApiResult_t    tlApiRet;
    tciReturnCode_t  ret = RET_OK;
    uint8_t          key_buf[KEY_BUFFER_SIZE] = {0};
    uint8_t*         ptr_key_buf    = &key_buf[0];
    key_meta_t       metadata       = {0};
    uint32_t         plain_data_len = 0;
    uint32_t         encrypted_data_len  = 0;
    uint32_t         offset    = 0;
    uint32_t         curve_len = 0;

    do
    {
        /* Validate buffer */
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Handle key data */
        uint8_t* key_data     = (uint8_t*)pTci->message.key_import.key_data;
        uint8_t* so_data      = (uint8_t*)pTci->message.key_import.so_data;
        uint32_t key_data_len = pTci->message.key_import.key_data_len;
        size_t   so_data_len  = pTci->message.key_import.so_data_len;

        if ((key_data == NULL) ||
            (so_data == NULL) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(so_data, so_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        memcpy(&metadata, key_data, sizeof(key_meta_t));

        switch (metadata.key_type)
        {
            //--------------------------------------------------------
            case KEY_TYPE_RSA:
            {
                /* Validate key size */
                if ((metadata.rsa_key.key_size < RSA_KEY_SIZE_512)  ||
                    (metadata.rsa_key.key_size > RSA_KEY_SIZE_4096))
                {
                    /* Unsupported key size */
                    ret = RET_ERR_INVALID_KEY_SIZE;
                    break;
                }

                /* Validate key type. Only RSA or RSA CRT keys are allowed */
                if ((metadata.rsa_key.type != KEY_PAIR_TYPE_RSA) &&
                    (metadata.rsa_key.type != KEY_PAIR_TYPE_RSACRT))
                {
                    /* Unsupported key type */
                    ret = RET_ERR_INVALID_KEY_TYPE;
                    break;
                }

                /* Copy key data into the buffer */
                COPY_KEY_DATA(ptr_key_buf, &metadata, sizeof(key_meta_t)); /* Key length data*/
                offset = sizeof(key_meta_t);
                /* Copy public key modulus */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.pub_mod_len);
                offset += metadata.rsa_key.pub_mod_len;
                /* Copy public key exponent */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.pub_exp_len);
                offset += metadata.rsa_key.pub_exp_len;

                if (metadata.rsa_key.type == KEY_PAIR_TYPE_RSA)
                {
                    /* Copy private key exponent */
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.rsa_priv.priv_exp_len);
                }
                else if (metadata.rsa_key.type == KEY_PAIR_TYPE_RSACRT)
                {
                    /* Copy private key data */
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.rsa_crt_priv.p_len);
                    offset += metadata.rsa_key.rsa_crt_priv.p_len;
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.rsa_crt_priv.q_len);
                    offset += metadata.rsa_key.rsa_crt_priv.q_len;
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.rsa_crt_priv.dp_len);
                    offset += metadata.rsa_key.rsa_crt_priv.dp_len;
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.rsa_crt_priv.dq_len);
                    offset += metadata.rsa_key.rsa_crt_priv.dq_len;
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.rsa_key.rsa_crt_priv.qinv_len);
                }
                else
                {
                    /* Invalid RSA key type */
                    ret = RET_ERR_INVALID_KEY_TYPE;
                    break;
                }

                plain_data_len =  sizeof(key_meta_t) +
                            metadata.rsa_key.pub_mod_len +
                            metadata.rsa_key.pub_exp_len;

                if (metadata.rsa_key.type == KEY_PAIR_TYPE_RSA)
                {
                    encrypted_data_len = metadata.rsa_key.rsa_priv.priv_exp_len;
                }
                else
                {
                    encrypted_data_len = metadata.rsa_key.rsa_crt_priv.p_len  +
                            metadata.rsa_key.rsa_crt_priv.q_len  +
                            metadata.rsa_key.rsa_crt_priv.dp_len +
                            metadata.rsa_key.rsa_crt_priv.dq_len +
                            metadata.rsa_key.rsa_crt_priv.qinv_len;
                }

                break;
            }
            //--------------------------------------------------------
            case KEY_TYPE_DSA:
            {
                /* Validate key data sizes */
                if ((metadata.dsa_key.p_len < DSA_P_BYTES_MIN) ||
                    (metadata.dsa_key.p_len > DSA_P_BYTES_MAX) ||
                    (metadata.dsa_key.q_len < DSA_Q_BYTES_MIN) ||
                    (metadata.dsa_key.q_len > DSA_Q_BYTES_MAX) ||
                    (metadata.dsa_key.g_len > metadata.dsa_key.p_len)  ||
                    (metadata.dsa_key.y_len != metadata.dsa_key.p_len) ||
                    (metadata.dsa_key.x_len != metadata.dsa_key.q_len))
                {
                    /* Invalid key data size */
                    ret = RET_ERR_INVALID_KEY_SIZE;
                    break;
                }

                /* Copy key data into the buffer */
                COPY_KEY_DATA(ptr_key_buf, &metadata, sizeof(key_meta_t));
                offset = sizeof(key_meta_t);
                /* Copy p */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.dsa_key.p_len);
                offset += metadata.dsa_key.p_len;
                /* Copy q */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.dsa_key.q_len);
                offset += metadata.dsa_key.q_len;
                /* Copy g */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.dsa_key.g_len);
                offset += metadata.dsa_key.g_len;
                /* Copy y */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.dsa_key.y_len);
                offset += metadata.dsa_key.y_len;
                /* Copy x (private key) */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.dsa_key.x_len);

                plain_data_len =  sizeof(key_meta_t) +
                            metadata.dsa_key.p_len +
                            metadata.dsa_key.q_len +
                            metadata.dsa_key.g_len +
                            metadata.dsa_key.y_len;

                encrypted_data_len = metadata.dsa_key.x_len;

                break;
            }
            //--------------------------------------------------------
            case KEY_TYPE_ECDSA:
            {
                /* Validate curve */
                switch (metadata.ecdsa_key.curve)
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
                        ret = RET_ERR_INVALID_CURVE;
                        break;
                }

                if (metadata.ecdsa_key.curve_length != curve_len)
                {
                    ret = RET_ERR_INVALID_KEY_SIZE;
                    break;
                }

                /* Copy key data into the buffer */
                COPY_KEY_DATA(ptr_key_buf, &metadata, sizeof(key_meta_t));
                offset = sizeof(key_meta_t);
                /* Copy x */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, curve_len);
                offset += curve_len;
                /* Copy y */
                COPY_KEY_DATA(ptr_key_buf, key_data+offset, curve_len);
                offset += curve_len;
                if (metadata.ecdsa_key.curve_length != 0)
                {
                    /* Copy private key */
                    COPY_KEY_DATA(ptr_key_buf, key_data+offset, metadata.ecdsa_key.curve_length);
                }

                plain_data_len =  sizeof(key_meta_t) + (2*curve_len);

                encrypted_data_len = metadata.ecdsa_key.curve_length;

                break;
            }
            //--------------------------------------------------------
            default:
                 ret = RET_ERR_INVALID_KEY_TYPE;
                 break;
        }

        /**
         * The following is how RSA key data will be wrapped
         *
         * |-- Key metadata --|--Pub key modulus--|--Pub key exponent--|--------------Priv key data---------------|

         * |---------------------------plaintext-----------------------|----------------encrypted-----------------|
         *
         * While wrapping data, public key data will be in plaintext. Private key part will be encrypted
         *
         *
         *
         * The following is how DSA key data will be wrapped
         *
         * |-- Key metadata --|--p--|--q--|--g--|--y--|--Private key data (x)--|
         *
         * |--------------plaintext-------------------|------encrypted---------|
         *
         **
         *
         * The following is how ECDSA key data will be wrapped
         *
         * |-- Key metadata --|--x--|--y--|--Private key data --|
         *
         * |------------plaintext---------|-----encrypted-------|
         *

         * While wrapping data, key metadata and public key data will be in plaintext. Private key part will be
         * encrypted
         */
        if (ret == RET_OK)
        {
            /* Position to the beginning of the buffer */
            ptr_key_buf = &key_buf[0];

            tlApiRet = tlApiWrapObject(
                            ptr_key_buf,
                            plain_data_len,
                            encrypted_data_len,
                            (mcSoHeader_t*)so_data,
                            &so_data_len,
                            MC_SO_CONTEXT_TLT,
                            MC_SO_LIFETIME_PERMANENT,
                            NULL,
                            TLAPI_WRAP_DEFAULT);
            if (TLAPI_OK != tlApiRet)
            {
                tlApiLogPrintf(TATAG"KeyImport(): tlApiWrapObject failed (0x%x)\n",
                        tlApiRet);
                ret = RET_ERR_SECURE_OBJECT;
                break;
            }

            pTci->message.key_import.so_data_len = so_data_len;
        }

    } while(false);

    return ret;
}


/**
 * Return public key data to to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_GetPubKey(
        tci_t* pTci
){
    tlApiResult_t    tlApiRet;
    tciReturnCode_t  ret = RET_OK;
    uint8_t          key_buf[KEY_BUFFER_SIZE] = {0};  /* Key buffer */
    uint8_t*         ptr_key_buf = &key_buf[0]; /* Pointer to key data buffer  */
    key_meta_t       keymeta     = {0};
    pub_key_meta_t   pub_keymeta = {0};
    size_t           so_len      = 0;
    uint32_t         data_len    = 0;
    uint32_t         offset      = 0;
    uint32_t         curve_len   = 0;

    do
    {
        /* Validate buffer */
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Unwrap key data */
        uint8_t* key_data      = (uint8_t*)pTci->message.get_pub_key.key_data;
        uint8_t* pub_key_data  = (uint8_t*)pTci->message.get_pub_key.pub_key_data;
        uint32_t key_data_len  = so_len  = pTci->message.get_pub_key.key_data_len;
        uint32_t pub_key_data_len = pTci->message.get_pub_key.pub_key_data_len;

        if ((key_data == NULL) ||
            (pub_key_data == NULL) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(pub_key_data, pub_key_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"GetPubKey(): invalid key data length\n");
            ret = RET_ERR_INVALID_LENGTH;
            break;
        }

        /**
         * Copy key data into internal memory. This is where key data is unwrapped
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
            tlApiLogPrintf(TATAG"GetPubKey(): tlApiUnwrapObject failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        /* Extract public key metadata information */
        memcpy(&keymeta, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));

        /* Check the key type and process accordingly */
        switch (keymeta.key_type)
        {
            //--------------------------------------------------------
            case KEY_TYPE_RSA:
            {
                if ((keymeta.rsa_key.pub_mod_len > MAX_MOD_LENGTH) ||
                    (keymeta.rsa_key.pub_exp_len > MAX_MOD_LENGTH))
                {
                    ret = RET_ERR_INVALID_BUFFER;
                    break;
                }

                data_len = sizeof(pub_key_meta_t) + keymeta.rsa_key.pub_mod_len + keymeta.rsa_key.pub_exp_len;

                /* Make sure that the length of public key data buffer is enough */
                if (pub_key_data_len < data_len)
                {
                    ret = RET_ERR_INVALID_LENGTH;
                    break;
                }

                /* Update RSA public key metadata */
                pub_keymeta.key_type = keymeta.key_type;
                pub_keymeta.rsa_pub_key.pub_mod_len = keymeta.rsa_key.pub_mod_len;
                pub_keymeta.rsa_pub_key.pub_exp_len = keymeta.rsa_key.pub_exp_len;

                /* Copy RSA public key metadata into the public key buffer */
                COPY_KEY_DATA(pub_key_data, &pub_keymeta, sizeof(pub_key_meta_t));

                /* Position to the beginning of key data in unwrap buffer */
                offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);

                /* Copy modulus data */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, keymeta.rsa_key.pub_mod_len);
                offset += keymeta.rsa_key.pub_mod_len;

                /* Copy public exponent data */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, keymeta.rsa_key.pub_exp_len);

                pTci->message.get_pub_key.pub_key_data_len = data_len;
                break;
            }
            //--------------------------------------------------------
            case KEY_TYPE_DSA:
            {
                /* Validate key data sizes */
                if ((keymeta.dsa_key.p_len < DSA_P_BYTES_MIN) ||
                    (keymeta.dsa_key.p_len > DSA_P_BYTES_MAX) ||
                    (keymeta.dsa_key.q_len < DSA_Q_BYTES_MIN) ||
                    (keymeta.dsa_key.q_len > DSA_Q_BYTES_MAX) ||
                    (keymeta.dsa_key.g_len > keymeta.dsa_key.p_len)  ||
                    (keymeta.dsa_key.y_len != keymeta.dsa_key.p_len) ||
                    (keymeta.dsa_key.x_len != keymeta.dsa_key.q_len))
                {
                    /* Invalid key data size */
                    ret = RET_ERR_INVALID_KEY_SIZE;
                    break;
                }

                data_len = sizeof(pub_key_meta_t) +
                          keymeta.dsa_key.p_len  +
                          keymeta.dsa_key.q_len  +
                          keymeta.dsa_key.g_len  +
                          keymeta.dsa_key.y_len;

                /* Make sure that the length of public key data buffer is enough */
                if (pub_key_data_len < data_len)
                {
                    ret = RET_ERR_INVALID_LENGTH;
                    break;
                }

                /* Update DSA public key metadata */
                pub_keymeta.key_type = keymeta.key_type;
                pub_keymeta.dsa_pub_key.p_len = keymeta.dsa_key.p_len;
                pub_keymeta.dsa_pub_key.q_len = keymeta.dsa_key.q_len;
                pub_keymeta.dsa_pub_key.g_len = keymeta.dsa_key.g_len;
                pub_keymeta.dsa_pub_key.y_len = keymeta.dsa_key.y_len;

                /* Copy DSA public key metadata into the public key buffer */
                COPY_KEY_DATA(pub_key_data, &pub_keymeta, sizeof(pub_key_meta_t));

                /* Position to the beginning of key data in unwrap buffer */
                offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);

                /* Copy p */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, keymeta.dsa_key.p_len);
                offset += keymeta.dsa_key.p_len;
                /* Copy q */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, keymeta.dsa_key.q_len);
                offset += keymeta.dsa_key.q_len;
                /* Copy g */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, keymeta.dsa_key.g_len);
                offset += keymeta.dsa_key.g_len;
                /* Copy y */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, keymeta.dsa_key.y_len);

                pTci->message.get_pub_key.pub_key_data_len = data_len;
                break;
            }
            //--------------------------------------------------------
            case KEY_TYPE_ECDSA:
            {
                /* Validate curve */
                switch (keymeta.ecdsa_key.curve)
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
                        ret = RET_ERR_INVALID_CURVE;
                        break;
                }

                /* X and Y length = 2 * curve length */
                data_len = sizeof(pub_key_meta_t) + (2*curve_len);

                /* Make sure that the length of public key data buffer is enough */
                if (pub_key_data_len < data_len)
                {
                    ret = RET_ERR_INVALID_LENGTH;
                    break;
                }

                /* Update ECDSA public key metadata */
                pub_keymeta.key_type = keymeta.key_type;
                pub_keymeta.ecdsa_pub_key.curve  = keymeta.ecdsa_key.curve;

                /* Copy ECDSA public key metadata into the public key buffer */
                COPY_KEY_DATA(pub_key_data, &pub_keymeta, sizeof(pub_key_meta_t));

                /* Position to the beginning of key data in unwrap buffer */
                offset = sizeof(mcSoHeader_t) + sizeof(key_meta_t);

                /* Copy x */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, curve_len);
                offset += curve_len;
                /* Copy y */
                COPY_KEY_DATA(pub_key_data, ptr_key_buf + offset, curve_len);
                offset += curve_len;

                pTci->message.get_pub_key.pub_key_data_len = data_len;
                break;
            }
            //--------------------------------------------------------
            default:
                 ret = RET_ERR_INVALID_KEY_TYPE;
                 break;
        }

    } while(false);

    return ret;
}


/**
 * Generate DSA key pair
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_DSAGenerateKeyPair( tci_t* pTci )
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data = (uint8_t*)pTci->message.dsa_gen_key.key_data;
        uint8_t* p       = (uint8_t*)pTci->message.dsa_gen_key.p;
        uint8_t* q       = (uint8_t*)pTci->message.dsa_gen_key.q;
        uint8_t* g       = (uint8_t*)pTci->message.dsa_gen_key.g;
        uint32_t key_data_len = pTci->message.dsa_gen_key.key_data_len;
        uint32_t p_len  = pTci->message.dsa_gen_key.p_len;
        uint32_t q_len  = pTci->message.dsa_gen_key.q_len;
        uint32_t g_len  = pTci->message.dsa_gen_key.g_len;
        uint32_t x_len  = pTci->message.dsa_gen_key.x_len;
        uint32_t y_len  = pTci->message.dsa_gen_key.y_len;
        if ((key_data == NULL) ||
            (p == NULL) ||
            (q == NULL) ||
            (g == NULL) ||
            (key_data_len == 0) ||
            (p_len == 0) ||
            (q_len == 0) ||
            (g_len == 0) ||
            (x_len == 0) ||
            (y_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(p, p_len)) ||
            (!isNwdBufferValid(q, q_len)) ||
            (!isNwdBufferValid(g, g_len)))
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
            tlApiLogPrintf(TATAG"DSAGenerateKeyPair():"\
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
            tlApiLogPrintf(TATAG"DSAGenerateKeyPair():"\
                    " tlApiWrapObject failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        pTci->message.dsa_gen_key.so_len = so_len;

    } while(false);

    return ret;
}


/**
 * DSA sign plain data and return signature data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_DSASign( tci_t* pTci )
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data       = (uint8_t*)pTci->message.dsa_sign.key_data;
        uint8_t* digest_data    = (uint8_t*)pTci->message.dsa_sign.digest_data;
        uint8_t* signature_data = (uint8_t*)pTci->message.dsa_sign.signature_data;
        uint32_t key_data_len   = so_len  = pTci->message.dsa_sign.key_data_len;
        uint32_t digest_data_len    = pTci->message.dsa_sign.digest_data_len;
        size_t   signature_data_len = pTci->message.dsa_sign.signature_data_len;
        if ((key_data == NULL) ||
            (digest_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (digest_data_len == 0) ||
            (signature_data_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(digest_data, digest_data_len)) ||
            (!isNwdBufferValid(signature_data, signature_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > DSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"DSASign(): invalid key data length\n");
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
            tlApiLogPrintf(TATAG"DSASign():" \
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
                                  TLAPI_SIG_DSA_HASHED);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf(TATAG"DSASign():" \
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
            tlApiLogPrintf(TATAG"DSASign():" \
                    " tlApiSignatureSign failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }


        pTci->message.dsa_sign.signature_data_len = signature_data_len;

    } while(false);

    return ret;
}


/**
 * DSA verify signature and return validity status to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_DSAVerify( tci_t* pTci )
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data           = (uint8_t*)pTci->message.dsa_verify.key_data;
        uint8_t* digest_data        = (uint8_t*)pTci->message.dsa_verify.digest_data;
        uint8_t* signature_data     = (uint8_t*)pTci->message.dsa_verify.signature_data;
        uint32_t key_data_len       = so_len  = pTci->message.dsa_verify.key_data_len;
        uint32_t digest_data_len    = pTci->message.dsa_verify.digest_data_len;
        uint32_t signature_data_len = pTci->message.dsa_verify.signature_data_len;
        if ((key_data == NULL) ||
            (digest_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (digest_data_len == 0) ||
            (signature_data_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(digest_data, digest_data_len)) ||
            (!isNwdBufferValid(signature_data, signature_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > DSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"DSAVerify(): invalid key data length\n");
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
            tlApiLogPrintf(TATAG"DSAVerify():" \
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
                                  TLAPI_SIG_DSA_HASHED);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf(TATAG"DSAVerify(): tlApiSignatureInit failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        /* Set initial response as signature not valid */
        pTci->message.dsa_verify.validity = false;

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
            tlApiLogPrintf(TATAG"DSAVerify():" \
                    " tlApiSignatureVerify failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        pTci->message.dsa_verify.validity = validity;

    } while(false);

    return ret;
}


/**
 * Generate ECDSA key pair
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_ECDSAGenerateKeyPair( tci_t* pTci )
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data     = (uint8_t*)pTci->message.ecdsa_gen_key.key_data;
        uint32_t key_data_len = pTci->message.ecdsa_gen_key.key_data_len;
        if ((key_data == NULL) ||
            (key_data_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)))
        {
            ret = RET_ERR_KEY_GENERATION;
            break;
        }

        /* Reset SO length value to '0' */
        pTci->message.ecdsa_gen_key.so_len = 0;

        /* Retrieve curve type */
        curve = pTci->message.ecdsa_gen_key.curve;

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
                tlApiLogPrintf(TATAG"ECDSAGenerateKeyPair():"\
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
                tlApiLogPrintf(TATAG"ECDSAGenerateKeyPair(): tlApiWrapObject failed (0x%x)\n",
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
            pTci->message.ecdsa_gen_key.so_len = so_len;
        }

    } while(false);

    return ret;
}


/**
 * ECDSA sign plain data and return signature data to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_ECDSASign( tci_t* pTci )
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_data           = (uint8_t*)pTci->message.ecdsa_sign.key_data;
        uint8_t* digest_data        = (uint8_t*)pTci->message.ecdsa_sign.digest_data;
        uint8_t* signature_data     = (uint8_t*)pTci->message.ecdsa_sign.signature_data;
        uint32_t key_data_len       = so_len  = pTci->message.ecdsa_sign.key_data_len;
        uint32_t digest_data_len    = pTci->message.ecdsa_sign.digest_data_len;
        size_t   signature_data_len = pTci->message.ecdsa_sign.signature_data_len;
        if ((key_data == NULL) ||
            (digest_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (digest_data_len == 0) ||
            (signature_data_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(digest_data, digest_data_len)) ||
            (!isNwdBufferValid(signature_data, signature_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > ECDSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"ECDSASign(): invalid key data length\n");
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
            tlApiLogPrintf(TATAG"ECDSASign(): tlApiUnwrapObject failed (0x%x)\n",
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
                                  TLAPI_SIG_ECDSA_HASHED);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf(TATAG"ECDSASign():" \
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
            tlApiLogPrintf(TATAG"ECDSASign():" \
                    " tlApiSignatureSign failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }


        pTci->message.ecdsa_sign.signature_data_len = signature_data_len;

    } while(false);

    return ret;
}


/**
 * ECDSA verify signature and return validity status to TLC
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_ECDSAVerify( tci_t* pTci )
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
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Set validity to false as default */
        pTci->message.ecdsa_verify.validity = false;

        /* Validate data passed by TLC */
        uint8_t* key_data             = (uint8_t*)pTci->message.ecdsa_verify.key_data;
        uint8_t* digest_data          = (uint8_t*)pTci->message.ecdsa_verify.digest_data;
        uint8_t* signature_data       = (uint8_t*)pTci->message.ecdsa_verify.signature_data;
        uint32_t key_data_len         = so_len  = pTci->message.ecdsa_verify.key_data_len;
        uint32_t digest_data_len      = pTci->message.ecdsa_verify.digest_data_len;
        uint32_t signature_data_len   = pTci->message.ecdsa_verify.signature_data_len;
        if ((key_data == NULL) ||
            (digest_data == NULL) ||
            (signature_data == NULL) ||
            (key_data_len == 0) ||
            (digest_data_len == 0) ||
            (signature_data_len == 0) ||
            (!isNwdBufferValid(key_data, key_data_len)) ||
            (!isNwdBufferValid(digest_data, digest_data_len)) ||
            (!isNwdBufferValid(signature_data, signature_data_len)))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_data_len > ECDSA_KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"ECDSAVerify(): invalid key data length\n");
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
            tlApiLogPrintf(TATAG"ECDSAVerify(): tlApiUnwrapObject failed (0x%x)\n",
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
                                  TLAPI_SIG_ECDSA_HASHED);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf(TATAG"ECDSAVerify(): tlApiSignatureInit failed (0x%x)\n",
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
            tlApiLogPrintf(TATAG"ECDSAVerify():" \
                    " tlApiSignatureVerify failed (0x%x)\n", tlApiRet);
            ret = RET_ERR_SIGN;
            break;
        }

        pTci->message.ecdsa_verify.validity = validity;
    } while(false);

    return ret;
}


/**
 * Get key info from key blob that is a secure object and
 * return key info to the NWd
 *
 * @return RET_OK upon success or relevant error code
 */
tciReturnCode_t tlCryptoHandler_GetKeyInfo( tci_t* pTci )
{
    tciReturnCode_t  ret = RET_OK;
    tlApiResult_t    tlApiRet;
    uint8_t          key_buf[KEY_BUFFER_SIZE];  /* Key buffer */
    uint8_t*         ptr_key_buf = &key_buf[0]; /* Pointer to key data buffer  */
    size_t           so_len      = 0;

    do
    {
        /* Validate buffer */
        if (pTci == NULL)
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Validate data passed by TLC */
        uint8_t* key_blob     = (uint8_t*)pTci->message.get_key_info.key_blob;
        uint8_t* key_info     = (uint8_t*)pTci->message.get_key_info.key_metadata;
        uint32_t key_blob_len = so_len = pTci->message.get_key_info.key_blob_len;

        if ((key_blob == NULL) ||
            (key_info == NULL) ||
            (!isNwdBufferValid(key_blob, key_blob_len)) ||
            (!isNwdBufferValid(key_info, sizeof(key_meta_t))))
        {
            ret = RET_ERR_INVALID_BUFFER;
            break;
        }

        /* Check if key data length is valid */
        if (key_blob_len > KEY_BUFFER_SIZE)
        {
            tlApiLogPrintf(TATAG"GetKeyInfo(): invalid key blob length\n");
            ret = RET_ERR_INVALID_LENGTH;
            break;
        }

        /**
         * Copy key data into internal memory. This is where key data is unwrapped
         */
        memcpy(ptr_key_buf, key_blob, key_blob_len);

        tlApiRet = tlApiUnwrapObject(
                        (mcSoHeader_t *)ptr_key_buf,
                        key_blob_len,
                        NULL,
                        &so_len,
                        TLAPI_UNWRAP_DEFAULT);
        if (TLAPI_OK != tlApiRet)
        {
            tlApiLogPrintf(TATAG"GetKeyInfo(): tlApiUnwrapObject failed (0x%x)\n",
                    tlApiRet);
            ret = RET_ERR_SECURE_OBJECT;
            break;
        }

        /* Extract key metadata information */
        memcpy(key_info, ptr_key_buf + sizeof(mcSoHeader_t), sizeof(key_meta_t));


    } while(false);

    return ret;
}
