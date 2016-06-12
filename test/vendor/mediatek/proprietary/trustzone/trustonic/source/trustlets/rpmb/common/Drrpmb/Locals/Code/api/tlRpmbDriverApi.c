/*
 * Copyright (c) 2013 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

/**
 * @file   tlDriverApi.c
 * @brief  Implements driver APIs
 *
 * The APIs allow trustlets to make requests to the driver
 *
 */
#include "tlStd.h"
#include "TlApi/TlApi.h"
#include "TlApi/TlApiError.h"


#include "tlRpmbDriverApi.h"
#include "drApiMarshal.h"
#include "drUtils.h"
#include "drRpmbOps.h"

/* Debug message event */
#define DBG_EVT_NONE        (0)       /* No event */
#define DBG_EVT_CMD         (1 << 0)  /* SEC CMD related event */
#define DBG_EVT_FUNC        (1 << 1)  /* SEC function event */
#define DBG_EVT_INFO        (1 << 2)  /* SEC information event */
#define DBG_EVT_WRN         (1 << 30) /* Warning event */
#define DBG_EVT_ERR         (1 << 31) /* Error event */
#define DBG_EVT_ALL         (0xffffffff)

#define DBG_EVT_MASK        (DBG_EVT_ALL)

#define MSG(evt, fmt, args...) \
do {    \
    if ((DBG_EVT_##evt) & DBG_EVT_MASK) { \
        tlApiLogPrintf("[Driver rpmb Api:%s] "fmt, __func__, ##args); \
    }   \
} while(0)


uint16_t cpu_to_be16p(uint16_t *p)
{
    return (((*p << 8)&0xFF00) | (*p >> 8));
}

uint32_t cpu_to_be32p(uint32_t *p)
{
    return (((*p & 0xFF) << 24) | ((*p & 0xFF00) << 8) | ((*p & 0xFF0000) >> 8) | (*p & 0xFF000000) >> 24 );
}

tlApiResult_t tlApiHmacSha256(const uint8_t* mac_key, const uint8_t* message, 
            size_t message_length, uint8_t* signature, size_t* signature_length) {

    tlApiCrSession_t crSession;
    tlApiResult_t tlRet;
    uint8_t key[32];

    // construct tlApiKey
    memcpy(key, mac_key, sizeof(key));
    tlApiSymKey_t symKey = {
        (uint8_t*)key,
         sizeof(key)  
    };
    
    tlApiKey_t apiKey = {&symKey};   

    MSG(INFO, "start!!\n");

    tlRet = tlApiSignatureInit(&crSession, &apiKey, TLAPI_MODE_SIGN, TLAPI_ALG_HMAC_SHA_256);
    if (TLAPI_OK != tlRet) {
        MSG(ERR, "tlApiSignatureInit failed!!(%x)", tlRet);
        return tlRet;
    }

    tlRet = tlApiIsNwdBufferValid(message, message_length);
    MSG(INFO, "tlApiIsNwdBufferValid=%x", tlRet);

    tlRet = tlApiSignatureSign(crSession, message, message_length, signature, signature_length);
    if (TLAPI_OK != tlRet) {        
        MSG(ERR, "tlApiSignatureSign failed!!(%x)", tlRet);
        return tlRet;
    }

    MSG(INFO, "end!!\n");
    
    return tlRet;
}


/* Sends a MSG_RQ message via IPC to a MobiCore driver.
 *
 * @param driverID The driver to send the IPC to.
 * @param pMarParam MPointer to marshaling parameters to send to the driver.
 *
 * @return TLAPI_OK
 * @return E_TLAPI_COM_ERROR in case of an IPC error.
 */
_TLAPI_EXTERN_C tlApiResult_t tlApi_callDriver(
        uint32_t driver_ID,
        void* pMarParam);



/**
 * Open session to the driver with given data
 *
 * @return  session id
 */
_TLAPI_EXTERN_C uint32_t tlApiRpmbOpenSession(uint32_t uid)
{
    uint32_t sid = DR_SID_INVALID;

    drMarshalingParam_t marParam = {
			.functionId = FID_DR_OPEN_SESSION,
            .uid = uid
	};

	if (TLAPI_OK == tlApi_callDriver(RPMB_DRV_ID, &marParam))
	{
	    /* Retrieve session id provided by the driver */
	    sid = marParam.sid;
	}

	return sid;
}

/**
 * Close session
 *
 * @param sid  session id
 *
 * @return  TLAPI_OK upon success or specific error
 */
_TLAPI_EXTERN_C tlApiResult_t tlApiRpmbCloseSession(
        uint32_t sid)
{
    drMarshalingParam_t marParam = {
            .functionId = FID_DR_CLOSE_SESSION,
            .sid = sid
    };

    tlApiResult_t ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
    return ret;
}


/**
 * read data
 *
 * @param sid        session id
 * @param commandId  command id
 *
 * @return  TLAPI_OK upon success or specific error
 */
_TLAPI_EXTERN_C tlApiResult_t tlApiRpmbReadData(
        uint32_t sid,
        uint8_t *buf,
        uint32_t bufSize,        
        int *result)
{
    tlApiRpmb_t RpmbData;

    tlApiResult_t ret = TLAPI_OK;
    uint8_t hmac[RPMB_SZ_MAC], *dataBuf, *dataBuf_ptr;
    uint8_t nonce[RPMB_SZ_NONCE];
    size_t hmac_len = RPMB_SZ_MAC, nonce_len = RPMB_SZ_NONCE;
    uint16_t type = RPMB_READ_DATA;
    uint16_t iCnt, total_blkcnt, tran_blkcnt, left_blkcnt, rTotal, rType, rWC, rAddr;    
    const uint8_t *mac_key;
    uint32_t tran_size, left_size = bufSize;
    int i = 0;
    struct rpmb_t *frame;

    drMarshalingParam_t marParam = 
    {
        .functionId = FID_DR_EXECUTE,
        .sid        = sid,
        .payload    = 
        {
            .RpmbData = &RpmbData
        }
    };
    
    MSG(INFO, "start!!\n");
    
    *result = 0;
    memset(RpmbData.buf, 0, 512); 

    RpmbData.commandId = FID_DRV_GET_KEY;
    ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
    if (marParam.payload.retVal != TLAPI_OK) {
        MSG(ERR, "tlApi_callDriver get key error (%x)\n", ret);
        return 1;
    }

    mac_key = RpmbData.mac_key;    

    RpmbData.commandId = FID_DRV_GET_ADDRESS;
    ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
    if (marParam.payload.retVal != TLAPI_OK) {
        MSG(ERR, "tlApi_callDriver get address error (%x)\n", ret);
        return 1;
    }

#if RPMB_MULTI_BLOCK_ACCESS

    left_blkcnt = total_blkcnt = ((bufSize % RPMB_SZ_DATA) ? (bufSize/RPMB_SZ_DATA + 1) : (bufSize/RPMB_SZ_DATA));

    while (left_blkcnt) {

        if (left_blkcnt >= MAX_RPMB_TRANSFER_BLK) {
            tran_blkcnt = MAX_RPMB_TRANSFER_BLK;        
        }
        else {
            tran_blkcnt = left_blkcnt;
        }
        
        /*
         * initial buffer. (since HMAC computation of multi block needs multi buffer, pre-alloced it)
         */
        frame = (struct rpmb_t *)RpmbData.buf;
        memset(frame, 0, tran_blkcnt * 512);        
        rType = cpu_to_be16p(&type);
        rAddr = cpu_to_be16p(&RpmbData.start_addr);

        dataBuf_ptr = dataBuf = tlApiMalloc(tran_blkcnt*512, 0); //alloc need 8byte boundary.
        if (!dataBuf) {
            MSG(ERR, "tlApiMalloc error!!!\n");
            return 1;
        }        

        /* 
         * STEP 1, prepare request read data frame. we need address and nonce.
         */ 
        frame->request = rType;
        frame->address = rAddr;
        tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM, nonce, &nonce_len);
        memcpy(frame->nonce, nonce, RPMB_SZ_NONCE);

        /* 
         * STEP 2, send read data request.
         */ 
        RpmbData.commandId = FID_DRV_READ_DATA;   
        RpmbData.blks = tran_blkcnt;
        ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
        if (marParam.payload.retVal != TLAPI_OK) {
            MSG(ERR, "tlApi_callDriver read data error (%x)\n", ret);
            tlApiFree(dataBuf);
            return 1;
        }

        /*
         * STEP 3, retrieve every data frame one by one.
         */
        for (iCnt = 0; iCnt < tran_blkcnt; iCnt++) {

            if (left_size >= RPMB_SZ_DATA) {
                tran_size = RPMB_SZ_DATA;
            }
            else {
                tran_size = left_size;
            }
            
            memcpy(dataBuf, frame->data, 284);
            dataBuf = dataBuf + 284;                   

            //
            // sorry, I shouldn't copy read data to user's buffer now, it should be later after checking no problem,
            // but for convenience...you know...
            //
            memcpy(buf + i * MAX_RPMB_TRANSFER_BLK * RPMB_SZ_DATA + (iCnt * RPMB_SZ_DATA), 
                   frame->data, 
                   tran_size);
            left_size -= tran_size;

            MSG(INFO, "iCnt=%d\n", iCnt);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 196+iCnt*512, RpmbData.buf[196+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 197+iCnt*512, RpmbData.buf[197+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 228+iCnt*512, RpmbData.buf[228+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 503+iCnt*512, RpmbData.buf[503+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 506+iCnt*512, RpmbData.buf[506+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 507+iCnt*512, RpmbData.buf[507+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 511+iCnt*512, RpmbData.buf[511+iCnt*512]);
            MSG(INFO, "buf[0]=%x\n", buf[0]);
            MSG(INFO, "buf[1]=%x\n", buf[1]);
            MSG(INFO, "buf[2]=%x\n", buf[2]);            
            frame++;
        }
        
        frame--;
        
        /*
         * STEP 4. authenticate last frame result response.
         */ 
        ret = tlApiHmacSha256(mac_key, dataBuf_ptr, 284 * tran_blkcnt, hmac, &hmac_len);
        
        if (memcmp(frame->mac, hmac, RPMB_SZ_MAC) != 0) {
            MSG(ERR, "read data result hmac compare error.\n");
            *result = 1;
            tlApiFree(dataBuf_ptr);            
            return 1;        
        }        

        if (frame->result) {
            *result = cpu_to_be16p(&frame->result);
            MSG(ERR, "read data result = %x\n", *result);
            tlApiFree(dataBuf_ptr);            
            return 1;
        }

        RpmbData.start_addr += tran_blkcnt;
        left_blkcnt -= tran_blkcnt;
        i++;
        tlApiFree(dataBuf_ptr);

    };

    if (left_blkcnt || left_size) {
        MSG(ERR, "left_blkcnt or left_size is not empty!!!!!!\n");
        *result = 1;
        return 1;
    }


#else // single block access.

    total_blkcnt = ((bufSize % RPMB_SZ_DATA) ? (bufSize/RPMB_SZ_DATA + 1) : (bufSize/RPMB_SZ_DATA));


    frame = (struct rpmb_t *)RpmbData.buf;
    rType = cpu_to_be16p(&type);    
    
    for (iCnt = 0; iCnt < total_blkcnt; iCnt++) {
        
        memset(frame, 0, 512); 

        /*
         * STEP 1: prepare read data request. we need nonce and address.
         */
        rAddr = cpu_to_be16p(&RpmbData.start_addr);
        frame->request = rType;
        frame->address = rAddr;      

        tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM, nonce, &nonce_len);
        memcpy(frame->nonce, nonce, RPMB_SZ_NONCE);
        

        RpmbData.commandId = FID_DRV_READ_DATA;
        RpmbData.blks = 1;
        ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
        if (marParam.payload.retVal != TLAPI_OK) {
            MSG(ERR, "tlApi_callDriver read data error (%x)\n", ret);
            return 1;
        }

        /*
         * STEP 2: retrieve read data response. we need check
         *         1. authenticate hmac is corrent or not.
         *         2. check nonce is the same or not.
         *         3. check result if no error or something else.
         */        
        ret = tlApiHmacSha256(mac_key, frame->data, 284, hmac, &hmac_len);

        if (memcmp(frame->mac, hmac, RPMB_SZ_MAC) != 0) {
            *result = 1;
            MSG(ERR, "read data hmac compare error!!!\n");
            return 1;
        }

        if (memcmp(frame->nonce, nonce, RPMB_SZ_NONCE) != 0) {
            *result = 1;
            MSG(ERR, "read data nonce compare error!!!\n");
            return 1;    
        }

        if (frame->result) {
            *result = cpu_to_be16p(&frame->result);
            MSG(ERR, "read data result error(%x)!!!\n", *result);            
            return 1;
        }

        /*
         * STEP 3: finally, we can copy the read data to user's buffer.
         */
        if (left_size >= RPMB_SZ_DATA)
            tran_size = RPMB_SZ_DATA;
        else
            tran_size = left_size;
        
        memcpy(buf + RPMB_SZ_DATA * iCnt, frame->data, tran_size);

        left_size -= tran_size;
        RpmbData.start_addr++;

    };



#endif

    MSG(INFO, "end!!");

    return ret;

}


/**
 * write data
 *
 * @param sid        session id
 * @param commandId  command id
 *
 * @return  TLAPI_OK upon success or specific error
 */
_TLAPI_EXTERN_C tlApiResult_t tlApiRpmbWriteData(
        uint32_t sid,
        uint8_t *buf,
        uint32_t bufSize,
        int *result)
{
    tlApiRpmb_t RpmbData;

    tlApiResult_t ret;
    const uint8_t *mac_key;
    uint8_t hmac[RPMB_SZ_MAC], *dataBuf, *dataBuf_ptr;
    uint8_t nonce[RPMB_SZ_NONCE];                              
    size_t hmac_len = RPMB_SZ_MAC, nonce_len = RPMB_SZ_NONCE;
    uint16_t iCnt, type;
    uint16_t total_blkcnt, tran_blkcnt, left_blkcnt, rBlks, rType, rAddr;
    uint32_t rWC, WC, tran_size, left_size = bufSize;
    struct rpmb_t *frame, *first_frame;
    int i = 0;

    drMarshalingParam_t marParam = 
    {
        .functionId = FID_DR_EXECUTE,
        .sid        = sid,
        .payload    = 
        {
            .RpmbData = &RpmbData
        }
    };

    MSG(INFO, "start!!\n");

    /*
     * STEP 0, initial default value.
     */
    *result = 0;
    memset(RpmbData.buf, 0, 512); //first data frame used for get wc.

    RpmbData.commandId = FID_DRV_GET_KEY;
    ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
    if (marParam.payload.retVal != TLAPI_OK) {
        MSG(ERR, "tlApi_callDriver get key error (%x)\n", ret);
        return 1;
    }

    mac_key = RpmbData.mac_key;

#if RPMB_MULTI_BLOCK_ACCESS

    /*
     * For RPMB write data, the elements we need in the data frame is
     * 1. address.
     * 2. write counter.
     * 3. data.
     * 4. block count.
     * 5. MAC
     *
     */


    /*
     * STEP 1(Address), search partition table to find out where is my address.
     */
    RpmbData.commandId = FID_DRV_GET_ADDRESS;
    ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
    if (marParam.payload.retVal != TLAPI_OK) {
        MSG(ERR, "tlApi_callDriver get address error (%x)\n", ret);
        return 1;
    }

    left_blkcnt = total_blkcnt = ((bufSize % RPMB_SZ_DATA) ? (bufSize/RPMB_SZ_DATA + 1) : (bufSize/RPMB_SZ_DATA));

    first_frame = (struct rpmb_t *)RpmbData.buf;

    while (left_blkcnt) {

        if (left_blkcnt >= MAX_RPMB_TRANSFER_BLK) {
            tran_blkcnt = MAX_RPMB_TRANSFER_BLK;        
        }
        else {
            tran_blkcnt = left_blkcnt;
        }

        MSG(INFO, "left_blkcnt = %x\n", left_blkcnt);

        rAddr = cpu_to_be16p(&RpmbData.start_addr);
        
        frame = (struct rpmb_t *)RpmbData.buf;
        memset(frame, 0, 512); 

        /*
         * STEP 2-1(write counter), prepare get wcounter request. we only need nonce.
         */      
        type = RPMB_GET_WRITE_COUNTER;
        rType = cpu_to_be16p(&type);    
        frame->request = rType;

        tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM, nonce, &nonce_len);
        memcpy(frame->nonce, nonce, RPMB_SZ_NONCE);

        RpmbData.commandId = FID_DRV_GET_WCOUNTER;
        ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
        if (marParam.payload.retVal != TLAPI_OK) {
            MSG(ERR, "tlApi_callDriver get wc error (%x)\n", ret);
            return 1;
        }

        /*
         * STEP 2-2(write counter), authenticate wc data frame is legal or not.
         */     
        tlApiHmacSha256(mac_key, frame->data, 284, hmac, &hmac_len);

        if (memcmp(frame->mac, hmac, RPMB_SZ_MAC) != 0) {
            MSG(ERR, "Get wcounter hmac compare error.\n");
            *result = 1;
            return 1;
        }

        if (memcmp(frame->nonce, nonce, RPMB_SZ_NONCE) != 0) {
            *result = 1;
            MSG(ERR, "Get wcounter nonce compare error!!!\n");
            return 1;    
        }

        if (frame->result) {
            *result = cpu_to_be16p(&frame->result);
            MSG(ERR, "Get wcounter result error(%x)!!!\n", *result);            
            return 1;
        }

        rWC = frame->write_counter;
        WC = cpu_to_be32p(&rWC);

        /*
         * initial buffer. (since HMAC computation of multi block needs multi buffer, pre-alloced it)
         */
        memset(frame, 0, tran_blkcnt * 512);        

        dataBuf_ptr = dataBuf = tlApiMalloc(tran_blkcnt*512, 0);
        if (!dataBuf) {
            MSG(ERR, "tlApiMalloc error!!!\n");
            return 1;
        }        

        type = RPMB_WRITE_DATA;
        rType = cpu_to_be16p(&type);
        rBlks = cpu_to_be16p(&tran_blkcnt);

        /*
         * STEP 3(data), prepare every data frame one by one and hook HMAC to the last.
         */
        for (iCnt = 0; iCnt < tran_blkcnt; iCnt++) {

            frame->request = rType;
            frame->block_count = rBlks;
            frame->address = rAddr;
            frame->write_counter = rWC;
            memset(frame->nonce, 0, RPMB_SZ_NONCE);

            if (left_size >= RPMB_SZ_DATA) {
                tran_size = RPMB_SZ_DATA;
            }
            else {
                tran_size = left_size;
            }
            
            memcpy(frame->data, 
                   buf + i * MAX_RPMB_TRANSFER_BLK * RPMB_SZ_DATA  + (iCnt * RPMB_SZ_DATA), 
                   tran_size);
            left_size -= tran_size;

            MSG(INFO, "iCnt=%d\n", iCnt);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 196+iCnt*512, RpmbData.buf[196+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 197+iCnt*512, RpmbData.buf[197+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 228+iCnt*512, RpmbData.buf[228+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 503+iCnt*512, RpmbData.buf[503+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 506+iCnt*512, RpmbData.buf[506+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 507+iCnt*512, RpmbData.buf[507+iCnt*512]);
            MSG(INFO, "RpmbData->buf[%d]=%x\n", 511+iCnt*512, RpmbData.buf[511+iCnt*512]);

            memcpy(dataBuf, frame->data, 284);
            dataBuf = dataBuf + 284;                   
                        
            frame++;

        }
        
        frame--;
        tlApiHmacSha256(mac_key, dataBuf_ptr, 284 * tran_blkcnt, frame->mac, &hmac_len);
                

        /* 
         * STEP 4, send write data request.
         */ 
        RpmbData.commandId = FID_DRV_WRITE_DATA;   
        RpmbData.blks = tran_blkcnt;
        ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
        if (marParam.payload.retVal != TLAPI_OK) {
            MSG(ERR, "tlApi_callDriver write data error (%x)\n", ret);
            tlApiFree(dataBuf_ptr);
            return 1;
        }

        /*
         * STEP 5. authenticate write result response.
         *         1. authenticate hmac.
         *         2. check result.
         *         3. compare write counter is increamented.        
         */ 
        tlApiHmacSha256(mac_key, first_frame->data, 284, hmac, &hmac_len);

        if (memcmp(first_frame->mac, hmac, RPMB_SZ_MAC) != 0) {
            MSG(ERR, "write data result hmac compare error.\n");
            *result = 1;
            tlApiFree(dataBuf_ptr);
            return 1;        
        }

        if (first_frame->result) {
            *result = cpu_to_be16p(&first_frame->result);
            MSG(ERR, "write data result error(%x)!!!\n", *result);            
            tlApiFree(dataBuf_ptr);            
            return 1;
        }

        rWC = cpu_to_be32p(&first_frame->write_counter);
        if (rWC != (WC + 1)) {
            *result = 1;
            MSG(ERR, "write data write counter mismatch error(last:%x)(current:%x)!!!\n", WC, rWC);
            tlApiFree(dataBuf_ptr);            
            return 1;
        }

        RpmbData.start_addr += tran_blkcnt;
        left_blkcnt -= tran_blkcnt;
        i++;
        tlApiFree(dataBuf_ptr);
    };

    
    if (left_blkcnt || left_size) {
        MSG(ERR, "left_blkcnt or left_size is not empty!!!!!!\n");
        *result = 1;
        return 1;
    }

#else

               
    total_blkcnt = ((bufSize % RPMB_SZ_DATA) ? (bufSize/RPMB_SZ_DATA + 1) : (bufSize/RPMB_SZ_DATA));


    /*
     * For RPMB write data, the elements we need in the data frame is
     * 1. address.
     * 2. write counter.
     * 3. data.
     * 4. block count.
     * 5. MAC
     *
     */

    /*
     * STEP 1(Address), search partition table to find out where is my address.
     */
    RpmbData.commandId = FID_DRV_GET_ADDRESS;
    ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);


    frame = (struct rpmb_t *)RpmbData.buf;
    
    for (iCnt = 0; iCnt < total_blkcnt; iCnt++) {

        memset(frame, 0, 512); 

        /*
         * STEP 2-1(write counter), prepare get wcounter request. we only need nonce.
         */      
        type = RPMB_GET_WRITE_COUNTER;
        rType = cpu_to_be16p(&type);    
        frame->request = rType;

        tlApiRandomGenerateData(TLAPI_ALG_SECURE_RANDOM, nonce, &nonce_len);
        memcpy(frame->nonce, nonce, RPMB_SZ_NONCE);
        

        RpmbData.commandId = FID_DRV_GET_WCOUNTER;
        RpmbData.blks = 1;
        ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
        if (marParam.payload.retVal != TLAPI_OK) {
            MSG(ERR, "tlApi_callDriver get wc error (%x)\n", ret);
            return 1;
        }

        /*
         * STEP 2-2(write counter), authenticate the wc data frame is legal or not.
         *                        1. authenticate hmac is correct or not.
         *                        2. check nonce is the same or not.
         *                        3. check result if no error or something else.
         */   
        tlApiHmacSha256(mac_key, frame->data, 284, hmac, &hmac_len);

        if (memcmp(frame->mac, hmac, RPMB_SZ_MAC) != 0) {
            MSG(ERR, "Get wcounter hmac compare error.\n");
            *result = 1;
            return 1;
        }

        if (memcmp(frame->nonce, nonce, RPMB_SZ_NONCE) != 0) {
            *result = 1;
            MSG(ERR, "Get wcounter nonce compare error!!!\n");
            return 1;    
        }

        if (frame->result) {
            *result = cpu_to_be16p(&frame->result);
            MSG(ERR, "Get wcounter result error(%x)!!!\n", *result);            
            return 1;
        }

        /* 
         * STEP 3(write data), prepare write data frame. we need
         *                     1. write counter.
         *                     2. block count.
         *                     3. address.
         *                     4. nonce is zero.
         *                     5. data.
         *                     6. HMAC.
         */ 

        rWC = frame->write_counter;
        WC = cpu_to_be32p(&rWC);
        

        type = RPMB_WRITE_DATA;

        rType = cpu_to_be16p(&type);    
        rAddr = cpu_to_be16p(&RpmbData.start_addr);

        frame->request = rType;
        frame->block_count = 0x0100; // it means 1 block every time.
        frame->address = rAddr;
        memset(frame->nonce, 0, RPMB_SZ_NONCE);
        
        /* 
         * STEP 3-5, copy user's data to my frame.
         */ 
        if (left_size >= RPMB_SZ_DATA)
            tran_size = RPMB_SZ_DATA;
        else
            tran_size = left_size;
         
        memcpy(frame->data, buf + iCnt * RPMB_SZ_DATA, tran_size);

        /* 
         * STEP 3-6, HMAC computation.
         */         
        tlApiHmacSha256(mac_key, frame->data, 284, frame->mac, &hmac_len);      

        RpmbData.commandId = FID_DRV_WRITE_DATA;    
        RpmbData.blks = 1;
        ret = tlApi_callDriver(RPMB_DRV_ID, &marParam);
        if (marParam.payload.retVal != TLAPI_OK) {
            MSG(ERR, "tlApi_callDriver write data error (%x)\n", ret);
            return 1;
        }

        /*
         * STEP 4. authenticate write result response. we need
         *         1. authenticate hmac.
         *         2. check result.
         *         3. compare write counter is increamented.
         */ 
        tlApiHmacSha256(mac_key, frame->data, 284, hmac, &hmac_len);
        
        if (memcmp(frame->mac, hmac, RPMB_SZ_MAC) != 0) {
            *result = 1;
            MSG(ERR, "write data result hmac compare error.\n");
            return 1;        
        }     

        if (frame->result) {
            *result = cpu_to_be16p(&frame->result);
            MSG(ERR, "write data result error(%x)!!!\n", *result);            
            return 1;
        }

        rWC = cpu_to_be32p(&frame->write_counter);
        if (rWC != (WC + 1)) {
            *result = 1;
            MSG(ERR, "write data write counter mismatch error(last:%x)(current:%x)!!!\n", WC, rWC);
            return 1;
        }        
        
        left_size -= tran_size;
        RpmbData.start_addr++;
        
    };

    
#endif
        
    return ret;
}


