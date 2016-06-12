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

#include "tci.h"
#include "device.h"

#define FIXED_PASSWORD_KEY_SALT "23571317193167127257"
static const size_t SHA256_length = 32;

// info : either returns with SO of length secure_object_length or error.
static TEE_ReturnCode_t encode_failure_record_so(
    const tee_failure_record_t* failure_record,
    uint8_t* secure_object,
    uint32_t secure_object_length)
{
    tlApiResult_t res = TLAPI_OK;
    res = tlApiWrapObjectExt(
                (void*)failure_record,
                0,
                sizeof(tee_failure_record_t),
                secure_object,
                (size_t *)&secure_object_length,
                MC_SO_CONTEXT_TLT,
                MC_SO_LIFETIME_PERMANENT,
                NULL,
                TLAPI_WRAP_DEFAULT);

    if ( (TLAPI_OK != res) ||
         (ENC_FAILURE_RECORD_LEN != secure_object_length))
    {
        tlApiLogPrintf(TATAG"Failure record wrapping failed [%d].", res);
        return TEE_ERR_SECURE_OBJECT;
    }
    return TEE_OK;
}


/* -----------------------------------------------------------------------------
 * @brief   Creates salted password hash from provided data.
 *
 * @param   pwd_hash     [out]  Initialized, 40 bytes long buffer.
 *          data         [in]   Data to be hashed
 *          data_len     [in]   Data length
 *          salt         [in]   8 bytes long salt to be appended to the hash
 *
 * @return  TEE_OK on success, otherwise TEE_ERR_INTERNAL_ERROR.
 *
-------------------------------------------------------------------------------- */
static TEE_ReturnCode_t salt_data(  uint8_t* pwd_hash, uint64_t salt,
                                    const uint8_t* data, size_t data_len)
{
    tlApiResult_t res               = TLAPI_OK;
    tlApiCrSession_t crypto_session = CR_SID_INVALID;
    size_t length                   = SHA256_length + sizeof(salt); // 256 bits of hash + 64 bits of salt;

    // Hash password to make it fixed-length
    res = tlApiMessageDigestInit(&crypto_session, TLAPI_ALG_SHA256);
    if(TLAPI_OK!=res)
    {
        return TEE_ERR_INTERNAL_ERROR;
    }
    res = tlApiMessageDigestDoFinal(crypto_session, data, data_len,
                                                    pwd_hash, &length);
    if(TLAPI_OK!=res)
    {
        return TEE_ERR_INTERNAL_ERROR;
    }

    // Append salt
    memcpy(&pwd_hash[SHA256_length], (uint8_t*)&salt, 8 /*sizeof(salt)*/);

    return TEE_OK;
}


/* -----------------------------------------------------------------------------
 * @brief   Creates HMAC by using SHA-256
 *
 * @param   signature   [out] 32 bit long buffer. Contains produced HMAC if call
 *                            successuful
 *          data        [in]  Data to compute HMAC on
 *          data_len    [in]  Length of data
 *          key         [in]  Key to use with HMAC
 *          key_len     [in]  Length of the key
 *
 * @return  TEE_OK on success, otherwise TEE_ERR_INTERNAL_ERROR.
 *
-------------------------------------------------------------------------------- */
static TEE_ReturnCode_t create_hmac_sha256( uint8_t* signature,
                                            uint8_t* data, size_t data_len,
                                            uint8_t* key, size_t key_len,
                                            uint64_t salt)
{
    // Create HMAC
    tlApiCrSession_t crypto_session  = CR_SID_INVALID;
    tlApiResult_t    res             = TLAPI_OK;
    TEE_ReturnCode_t ret             = TEE_OK;
    tlApiKey_t       api_key         = {0};
    tlApiSymKey_t    sym_key         = { key, key_len };
    size_t           signature_length= SHA256_length;
    static const size_t salted_data_len = SHA256_length + sizeof(salt);
    uint8_t          salted_data[salted_data_len];

    if(salt!=0)
    {
        // Currently KINIBI doesn't support HMAC with salt.
        ret = salt_data(salted_data, salt, data, data_len);
        if( TEE_OK != ret)
            return ret;
        data = &salted_data[0];
        data_len = salted_data_len;
    }

    api_key.symKey = &sym_key;
    res = tlApiSignatureInit(
       &crypto_session, &api_key, TLAPI_MODE_SIGN, TLAPI_ALG_HMAC_SHA_256);

    if(TLAPI_OK!=res)
    {
       return TEE_ERR_INTERNAL_ERROR;
    }

    res = tlApiSignatureSign(
       crypto_session, data, data_len,
       signature, &signature_length);
    if(TLAPI_OK!=res)
    {
       return TEE_ERR_INTERNAL_ERROR;
    }
    return TEE_OK;
}

TEE_ReturnCode_t tlCryptoHandler_GetMillisecondsSinceBoot( tci_t* pTci )
{
    pTci->message.uptime = get_milliseconds_since_boot();
    return TEE_OK;
}

TEE_ReturnCode_t tlCryptoHandler_GetPasswordKey( tci_t* pTci )
{
    uint8_t derived_key_buff[PASSWORD_KEY_LEN] = {0};
    uint8_t so_buff[ENC_PASSWORD_KEY_LEN] = {0};
    size_t so_len = ENC_PASSWORD_KEY_LEN;

    tlApiResult_t res       = TLAPI_OK;
    TEE_ReturnCode_t ret    = TEE_OK;
    void* nw_symmetric_key  = NULL;

    nw_symmetric_key = (void*)pTci->message.symmetric_key.data;
    if( !tlApiIsNwdBufferValid(nw_symmetric_key, pTci->message.symmetric_key.data_length) )
    {
        tlApiLogPrintf(TATAG"Wrong input parameters");
        return TEE_ERR_INVALID_INPUT;
    }
 /* -------------------------------------------------------------------------
    Derives key from HUK. This key is used as private key for siginig passwords
    enrolled to gatekeeper by the user. tlApiDeriveKey() uses HUK to derive
    password.
    ----------------------------------------------------------------------------*/
    res = tlApiDeriveKey(
            FIXED_PASSWORD_KEY_SALT, 21,
            &derived_key_buff, PASSWORD_KEY_LEN,
            MC_SO_CONTEXT_DEVICE,
            MC_SO_LIFETIME_PERMANENT);

    if( TLAPI_OK != res )
    {
        tlApiLogPrintf(TATAG"Key derivation failed");
        return TEE_ERR_KEY_DERIVATION;
    }

 /* -------------------------------------------------------------------------
    Returned value is wrapped in Secure Object. This object must be accessible
    permanently lifetime and **only** by GK TA.
    ----------------------------------------------------------------------------*/
    res = tlApiWrapObjectExt(
                derived_key_buff,
                0, PASSWORD_KEY_LEN, /* encrypt the whole blob */
                so_buff,             /* wrap in place */
                &so_len,
                MC_SO_CONTEXT_TLT,
                MC_SO_LIFETIME_POWERCYCLE,
                NULL,
                TLAPI_WRAP_DEFAULT);
    if (TLAPI_OK != res || (ENC_PASSWORD_KEY_LEN != so_len))
    {
        tlApiLogPrintf(TATAG"Secure object wrapping has failed [%d].", res);
        return TEE_ERR_SECURE_OBJECT;
    }

    memcpy(nw_symmetric_key, so_buff, so_len);
    pTci->message.symmetric_key.data_length = so_len;

    return ret;
}

TEE_ReturnCode_t tlCryptoHandler_GetRandom( tci_t* pTci )
{
    tlApiResult_t res       = TLAPI_OK;
    TEE_ReturnCode_t ret    = TEE_OK;
    uint8_t* local_random   = NULL;
    size_t local_random_len  = 0;
    void* nw_random         = NULL;

    nw_random = (void*)pTci->message.random.data;
    local_random_len = pTci->message.random.data_length;
    if( !tlApiIsNwdBufferValid(nw_random, local_random_len) )
    {
        return TEE_ERR_INVALID_INPUT;
    }

    do
    {
        local_random = tlApiMalloc(local_random_len, 0);
        if( NULL==local_random )
        {
            ret = TEE_ERR_MEMORY_ALLOCATION_FAILED;
            break;
        }

        res = tlApiRandomGenerateData(
                    TLAPI_ALG_SECURE_RANDOM, local_random, &local_random_len);

        if( (TLAPI_OK != res) ||
            (local_random_len != pTci->message.random.data_length ) )
        {
            tlApiLogPrintf(TATAG"Random data generation failed [%d]", res);
            ret = TEE_ERR_RANDOM_GENERATION;
            break;
        }
        // copy localy generated random number to buffer shared with NWd
        memcpy(nw_random, local_random, local_random_len);
    }
    while(false);

    tlApiFree(local_random);
    return ret;
}

TEE_ReturnCode_t tlCryptoHandler_ComputePasswordSignature( tci_t* pTci )
{
    tlApiResult_t    res              = TLAPI_OK;
    TEE_ReturnCode_t ret              = TEE_OK;
    size_t           hmac_length = 0;
    uint8_t*         local_hmac  = NULL;
    uint8_t*         nw_hmac     = NULL;
    size_t           data_length      = 0;
    uint8_t*         local_data       = NULL;
    uint8_t*         nw_data          = NULL;
    size_t           key_length       = 0;
    uint8_t*         local_key_so     = NULL;
    uint8_t*         nw_key_so        = NULL;
    uint8_t*         p_key            = NULL;
    uint64_t         salt             = 0;

    // Input validation
    nw_data = (uint8_t*) pTci->message.signature.data.data;
    data_length = pTci->message.signature.data.data_length;
    nw_hmac = (uint8_t*) pTci->message.signature.hmac.data;
    hmac_length = pTci->message.signature.hmac.data_length;
    nw_key_so = (uint8_t*) pTci->message.signature.key.data;
    key_length = pTci->message.signature.key.data_length;
    salt = pTci->message.signature.salt;
    if( !tlApiIsNwdBufferValid(nw_data, data_length)  ||
        (0==data_length) ||
        !tlApiIsNwdBufferValid(nw_hmac, hmac_length) ||
        (SHA256_length!=hmac_length) ||
        !tlApiIsNwdBufferValid(nw_key_so, key_length) ||
        (0==key_length))
    {
        return TEE_ERR_INVALID_INPUT;
    }

    // Signing
    do
    {
        local_hmac = tlApiMalloc(hmac_length, 0);
        if(!local_hmac)
        {
            ret = TEE_ERR_MEMORY_ALLOCATION_FAILED;
            break;
        }
        local_data = tlApiMalloc(data_length, 0);
        if(!local_data)
        {
            ret = TEE_ERR_MEMORY_ALLOCATION_FAILED;
            break;
        }
        local_key_so = tlApiMalloc(key_length, 0);
        if(!local_key_so)
        {
            ret = TEE_ERR_MEMORY_ALLOCATION_FAILED;
            break;
        }

        memcpy(local_data, nw_data, data_length);
        memcpy(local_key_so, nw_key_so, key_length);

        // Each key from NW is wrapped in SO, needs to be unwrapped before use
        res = tlApiUnwrapObjectExt( local_key_so,
                                    key_length,
                                    NULL,
                                    &key_length,
                                    TLAPI_UNWRAP_DEFAULT);

        if(TLAPI_OK != res)
        {
            ret = TEE_ERR_SECURE_OBJECT;
            break;
        }
        p_key = (local_key_so + sizeof(mcSoHeader_t));

        ret = create_hmac_sha256(local_hmac, local_data, data_length, p_key, key_length, salt);
        if( TEE_OK==ret )
        {
            // copy generated hmac to NWd
            memcpy(nw_hmac, local_hmac, hmac_length);
        }
    }
    while(false);

    tlApiFree(local_hmac);
    tlApiFree(local_data);
    tlApiFree(local_key_so);
    return ret;
}

TEE_ReturnCode_t tlCryptoHandler_GetAuthTokenKey( tci_t* pTci )
{
    tlApiResult_t res       = TLAPI_OK;
    TEE_ReturnCode_t ret    = TEE_OK;
    perboot_hmac_key_t hkey = {0};
    size_t so_buff_len      = pTci->message.symmetric_key.data_length;
    void* nw_symmetric_key  = (void*)pTci->message.symmetric_key.data;
    uint8_t* so_buff        = NULL;

    if( !tlApiIsNwdBufferValid(nw_symmetric_key, so_buff_len) ||
        (ENC_AUTH_TOKEN_KEY_LEN != so_buff_len) )
    {
        tlApiLogPrintf(TATAG"Wrong input parameters");
        return TEE_ERR_INVALID_INPUT;
    }

    do
    {
       so_buff = tlApiMalloc(so_buff_len, 0);
       if(NULL == so_buff)
       {
           ret = TEE_ERR_MEMORY_ALLOCATION_FAILED;
           break;
       }
       get_hmac_key(&hkey);
       res = tlApiWrapObjectExt(hkey,
                               0, sizeof(hkey), /* encrypt the whole blob */
                               so_buff, &so_buff_len,
                               MC_SO_CONTEXT_TLT,
                               MC_SO_LIFETIME_POWERCYCLE,
                               NULL,
                               TLAPI_WRAP_DEFAULT );

        if ( (TLAPI_OK != res ) ||
             (ENC_AUTH_TOKEN_KEY_LEN != so_buff_len))
        {
            tlApiLogPrintf(TATAG"Secure object wrapping has failed [%d].", res);
            ret = TEE_ERR_SECURE_OBJECT;
            break;
        }

        memcpy(nw_symmetric_key, so_buff, so_buff_len);
    }
    while(false);

    tlApiFree(so_buff);
    return ret;
}

TEE_ReturnCode_t tlCryptoHandler_FailureRecordEncode( tci_t* pTci )
{
    TEE_ReturnCode_t ret        = TEE_OK;
    void* nw_failure_record     = NULL;
    uint32_t failure_record_len = pTci->message.failure_record.encrypted_failure_record.data_length;
    uint8_t sw_failure_record_so[ENC_FAILURE_RECORD_LEN] = {0};
    tee_failure_record_t sw_failure_record;

    nw_failure_record = (void*)pTci->message.failure_record.encrypted_failure_record.data;
    if( !tlApiIsNwdBufferValid(nw_failure_record, failure_record_len) ||
        (ENC_FAILURE_RECORD_LEN!=failure_record_len) )
    {
        tlApiLogPrintf(TATAG"Wrong input parameters");
        return TEE_ERR_INVALID_INPUT;
    }

    sw_failure_record.version = TEE_FAILURE_RECORD_VERSION;
    sw_failure_record.suid = pTci->message.failure_record.suid;
    sw_failure_record.last_access_timestamp = pTci->message.failure_record.last_access_timestamp;
    sw_failure_record.failure_counter = pTci->message.failure_record.failure_counter;

    if(0==sw_failure_record.last_access_timestamp)
    {
        sw_failure_record.last_access_timestamp = get_milliseconds_since_boot();
    }

    ret = encode_failure_record_so(
        &sw_failure_record,
        sw_failure_record_so,
        failure_record_len);

    if(TEE_OK == ret)
        memcpy(nw_failure_record, sw_failure_record_so, failure_record_len);

    return ret;
}

TEE_ReturnCode_t tlCryptoHandler_FailureRecordDecode( tci_t* pTci )
{
    TEE_ReturnCode_t ret = TEE_OK;
    tlApiResult_t res = TLAPI_OK;
    uint64_t suid     = pTci->message.failure_record.suid;
    void* nw_so       = (void*)pTci->message.failure_record.encrypted_failure_record.data;
    size_t so_len     = pTci->message.failure_record.encrypted_failure_record.data_length;
    uint8_t* sw_so    = NULL;
    tee_failure_record_t* decoded_failure_record = NULL;

    if( !tlApiIsNwdBufferValid(nw_so, so_len) ||
        (ENC_FAILURE_RECORD_LEN!=so_len) )
    {
        tlApiLogPrintf(TATAG"Wrong input parameters");
        return TEE_ERR_INVALID_INPUT;
    }

    do
    {
        sw_so = tlApiMalloc(ENC_FAILURE_RECORD_LEN, 0);
        if( NULL == sw_so )
        {
            ret = TEE_ERR_MEMORY_ALLOCATION_FAILED;
            break;
        }
        memcpy(sw_so, nw_so, ENC_FAILURE_RECORD_LEN);

        res = tlApiUnwrapObjectExt( sw_so,
                                    so_len,
                                    NULL,
                                    &so_len,
                                    TLAPI_UNWRAP_DEFAULT);

        if( (TLAPI_OK != res) ||
            (so_len != sizeof(tee_failure_record_t)) )
        {
            return TEE_ERR_SECURE_OBJECT;
        }
        decoded_failure_record = (tee_failure_record_t*)(sw_so + sizeof(mcSoHeader_t));

        // SEC: don't change anything in out failure_record object until SUID
        //      is verified
        if( (suid == decoded_failure_record->suid) &&
            (TEE_FAILURE_RECORD_VERSION == decoded_failure_record->version) )
        {
            //Device was rebooted or timer reset.
            //Before decoding, reset the last check time to 0
            uint64_t curr_time = get_milliseconds_since_boot();
            if( decoded_failure_record->last_access_timestamp >= curr_time )
            {
                decoded_failure_record->last_access_timestamp = 0;
            }
            memcpy(&(pTci->message.failure_record), decoded_failure_record, sizeof(tee_failure_record_t));
        }
        else
        {
            // SEC: User must not know that SUID validation has failed.
            return TEE_ERR_SECURE_OBJECT;
        }
    } while(false);

    tlApiFree(sw_so);
    return ret;
}
