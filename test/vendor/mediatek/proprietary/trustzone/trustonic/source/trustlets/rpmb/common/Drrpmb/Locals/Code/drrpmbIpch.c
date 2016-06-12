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

/*
 * @file   drrpmbIpch.c
 * @brief  Implements IPC handler of the driver.
 *
 */

#include "drStd.h"
#include "DrApi/DrApi.h"

#include "DrError.h"
#include "drCommon.h"
#include "drrpmb_Api.h"

#include "drApiMarshal.h"
#include "drSmgmt.h"
#include "drUtils.h"

#include "drRpmbOps.h"

//
// NOTICE THIS!!!
// this is copy from bootable/bootloader/preloader/platform/[project]/security/trustzone
// If preloader has been modified, sync this file.
#include "tz_mem.h" 


DECLARE_STACK(drIpchStack, 1024*16);


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
        drDbgPrintLnf("[Driver rpmb:%s] "fmt, __func__, ##args); \
    }   \
} while(0)


/* Static variables */
static dciMessage_t *message;
static sec_mem_arg_t sec_mem_arg;

#define RPMB_KEY_DUMP 0
#define RPMB_TEST_KEY 0

#if RPMB_TEST_KEY
//
// According to tester.
//
const uint8_t mac_key[32]={0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                           0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                           0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30,
                           0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30};
#endif

/*
 * IPC handler loop. this is the function where IPC messages are handled
 */
_NORETURN void drIpchLoop( void )
{
    /* Set IPC parameters for initial MSG_RD to IPCH */
    threadid_t              ipcClient = NILTHREAD;
    message_t               ipcMsg    = MSG_RD;
    uint32_t                ipcData   = 0;
    tlApiResult_t           tlRet     = TLAPI_OK;
    drMarshalingParam_ptr   pMarshal;
    taskid_t                taskid = drApiGetTaskid();
    tlApiRpmb_ptr           RpmbData = NULL;
    drApiResult_t           ret = DRAPI_OK;
    drSessionReg_ptr        sessionReg;
    uint16_t wc;  
    int i;

    for(;;)
    {
        MSG(INFO, "drApiIpcCallToIPCH start!!!");
            
        /*
         * When called first time sends ready message to IPC server and
         * then waits for IPC requests
         */
        if (DRAPI_OK != drApiIpcCallToIPCH(&ipcClient, &ipcMsg, &ipcData))
        {
            MSG(ERR, "drApiIpcCallToIPCH failed");
            continue;
        }
        
        MSG(INFO, "ipcMsg=%x, ipcClient=%x", ipcMsg, ipcClient);

        /* Dispatch request */
        switch(ipcMsg)
        {
            case MSG_CLOSE_TRUSTLET:
                /*
                 * Trustlet close message
                 */
                MSG(INFO, "Acknowledging trustlet close");
                /* Close active sessions owned by trustlet, which is being shutdown */
                //For MSG_CLOSE_TRUSTLET message threadId is in ipcData parameter
                //drSmgmtCloseSessionForThread(ipcData);
                ipcMsg = MSG_CLOSE_TRUSTLET_ACK;
                ipcData = TLAPI_OK;
                break;
            case MSG_CLOSE_DRIVER:
                /*
                 * Driver close message
                 */
                MSG(INFO, "MSG_CLOSE_DRIVER");
                ipcMsg = MSG_CLOSE_DRIVER_ACK;
                ipcData = TLAPI_OK;
                break;
            case MSG_RQ:
                MSG(INFO, "MSG_RQ");
#ifdef DR_FEATURE_TL_API                

                /*
                 * Handle incoming IPC requests via TL API.
                 * Map the caller trustlet to access to the marshalling data
                 */
                ret = drApiMapTaskBuffer(THREADID_TO_TASKID(ipcClient),
                                         (addr_t )ipcData,
                                         sizeof(drMarshalingParam_ptr),
                                         MAP_READABLE|MAP_WRITABLE|MAP_ALLOW_NONSECURE,
                                         (void **)&pMarshal);

                if (ret != DRAPI_OK){
                    ret = E_DRAPI_CANNOT_MAP;
                    MSG(ERR, "map task buffer failed");
                    break;
                }

                MSG(INFO, "drApiMapTaskBuffer done.");

                if(pMarshal)
                {
                    /* Process the request */
                    switch (pMarshal->functionId)
                    {

                    case FID_DR_OPEN_SESSION:
                        /*
                         * Handle open session request
                         */
                        MSG(INFO, "FID_DR_OPEN_SESSION");

                        pMarshal->sid = drSmgmtOpenSession(pMarshal->uid, ipcClient);

                        /*
                         * Update IPC status as success. If there is an error with opening session
                         * invalid sid will indicatethat opening session failed
                         */
                        tlRet = TLAPI_OK;
                        break;
                    case FID_DR_CLOSE_SESSION:
                        /*
                         * Handle close session request based on the sid provided by the client
                         */
                        MSG(INFO, "FID_DR_CLOSE_SESSION");

                        sessionReg = drSmgmtGetSessionData(pMarshal->sid);
                        if (sessionReg == NULL) {
                            MSG(ERR, "FID_DR_CLOSE_SESSION, wrong sid!!!");
                            tlRet = E_TLAPI_CR_HANDLE_INVALID;
                            break;
                        } else if (ipcClient != sessionReg->threadid) {
                            MSG(ERR, "FID_DR_CLOSE_SESSION, thread ID is wrong!!!");
                            tlRet = E_TLAPI_CR_HANDLE_INVALID;
                            break;
                        }

                        drSmgmtCloseSession(pMarshal->sid);
                        tlRet = TLAPI_OK;
                        break;
                    case FID_DR_INIT_DATA:
                        /*
                         * Initialize sesion data
                         */
                        MSG(INFO, "FID_DR_INIT_DATA");
                        if (E_DR_SMGMT_OK == drSmgmtSetSessionData(
                                pMarshal->sid,
                                ipcClient,
                                &(pMarshal->payload.RpmbData)))
                        {
                            tlRet = TLAPI_OK;
                        }
                        break;
                    case FID_DR_EXECUTE:
                        /*
                         * TODO: Read registry data compare threadids to make sure that client is allowed to use
                         * registry data. Then execute the command and update tlRet accordingly
                         */
                        MSG(INFO, "FID_DR_EXECUTE");

                        if (pMarshal->sid == DR_SID_INVALID) {
                            MSG(ERR, "FID_DR_EXECUTE, session ID is wrong!!!");
                            tlRet = E_TLAPI_CR_HANDLE_INVALID;
                            break;
                            
                        } else {

                            sessionReg = drSmgmtGetSessionData(pMarshal->sid);
                            if (sessionReg == NULL) {
                                MSG(ERR, "FID_DR_EXECUTE, wrong sid!!!");
                                tlRet = E_TLAPI_CR_HANDLE_INVALID;
                                break;
                            } else if (ipcClient != sessionReg->threadid) {
                                MSG(ERR, "FID_DR_EXECUTE, thread ID is wrong!!!");
                                tlRet = E_TLAPI_CR_HANDLE_INVALID;
                                break;
                            }
                        }

                        ret = drApiMapTaskBuffer(
                                    THREADID_TO_TASKID(ipcClient), 
                                    pMarshal->payload.RpmbData, 
                                    sizeof(tlApiRpmb_t),
                                    MAP_READABLE|MAP_WRITABLE|MAP_ALLOW_NONSECURE,
                                    (void **)&RpmbData);

                        if (ret != DRAPI_OK) {
                            MSG(ERR, "map task buffer failed");
                            tlRet = E_DRAPI_CANNOT_MAP;
                            break;
                        }

                        if (FID_DRV_GET_KEY == RpmbData->commandId) {

                            MSG(INFO, "FID_DRV_GET_KEY");

                            #if RPMB_KEY_DUMP
                            MSG(INFO, "[RPMB][KEY]");
                            for (i=0;i<8;i++)
                                MSG(INFO, "%x, ", sec_mem_arg.msg_auth_key[i]);
                            #endif

                            memcpy(RpmbData->mac_key, sec_mem_arg.msg_auth_key, RPMB_SZ_MAC);

                            #if RPMB_TEST_KEY
                            memcpy(RpmbData->mac_key, mac_key, RPMB_SZ_MAC);
                            #endif

                        } else if (FID_DRV_GET_ADDRESS == RpmbData->commandId) {

                            MSG(INFO, "FID_DRV_GET_ADDRESS");

                            sessionReg = drSmgmtGetSessionData(pMarshal->sid);

                            RpmbData->start_addr = drRpmbGetBlkIdx(sessionReg->mData.uid);
                            MSG(INFO, "get address : %x", RpmbData->start_addr);

                        } else if (FID_DRV_READ_DATA == RpmbData->commandId) {
                        
                            MSG(INFO, "FID_DRV_READ_DATA");

                            RpmbData->result = 0;
                            message->command.header.commandId = DCI_RPMB_CMD_READ_DATA;
                            message->request.type = RPMB_READ_DATA;
                            message->request.addr = RpmbData->start_addr;
                            message->request.blks = RpmbData->blks;

                            memcpy(message->request.frame, RpmbData->buf, 512 * RpmbData->blks);
                            
                            MSG(INFO, "atomicSendReceive read data start.");
                            atomicSendReceive(DRIVER_THREAD_NO_DCIH, NOTIFY_DCIH);
                            MSG(INFO, "atomicSendReceive read data end.");

                            RpmbData->result = message->request.result;


                            memcpy(RpmbData->buf, message->request.frame, 512 * RpmbData->blks);

                            MSG(INFO, "frame[196]=%x", message->request.frame[196]);
                            MSG(INFO, "frame[197]=%x", message->request.frame[197]);
                            MSG(INFO, "frame[198]=%x", message->request.frame[198]);
                            MSG(INFO, "frame[199]=%x", message->request.frame[199]);
                            
                        } else if (FID_DRV_GET_WCOUNTER == RpmbData->commandId) {
                        
                            MSG(INFO, "FID_DRV_GET_WCOUNTER");
                            
                            message->command.header.commandId = DCI_RPMB_CMD_GET_WCNT;
                            message->request.type = RPMB_GET_WRITE_COUNTER;
                            message->request.addr = 0; 
                            message->request.blks = 1;

                            memcpy(message->request.frame, RpmbData->buf, 512);
                            
                            MSG(INFO, "atomicSendReceive get wc start.");
                            atomicSendReceive(DRIVER_THREAD_NO_DCIH, NOTIFY_DCIH);
                            MSG(INFO, "atomicSendReceive get wc end.");
                            
                            memcpy(RpmbData->buf, message->request.frame, 512);

                            wc = cpu_to_be32p(message->request.frame + RPMB_WCOUNTER_BEG);
                            MSG(INFO, "wc=%x", wc);
                            
                        } else if (FID_DRV_WRITE_DATA == RpmbData->commandId) {
                        
                            MSG(INFO, "FID_DRV_WRITE_DATA");
                            
                            message->command.header.commandId = DCI_RPMB_CMD_WRITE_DATA;
                            message->request.type = RPMB_WRITE_DATA;
                            message->request.addr = RpmbData->start_addr; 
                            message->request.blks = RpmbData->blks;

                            memcpy(message->request.frame, RpmbData->buf, 512 * RpmbData->blks);
                            MSG(INFO, "RpmbData->buf[196]=%x", RpmbData->buf[196]);
                            MSG(INFO, "RpmbData->buf[228]=%x", RpmbData->buf[228]);
                            MSG(INFO, "RpmbData->buf[229]=%x", RpmbData->buf[229]);
                            MSG(INFO, "RpmbData->buf[230]=%x", RpmbData->buf[230]);
                            MSG(INFO, "RpmbData->buf[231]=%x", RpmbData->buf[231]);
                            MSG(INFO, "RpmbData->buf[506]=%x", RpmbData->buf[506]);
                            MSG(INFO, "RpmbData->buf[507]=%x", RpmbData->buf[507]);
                            
                            MSG(INFO, "atomicSendReceive write data start.");
                            atomicSendReceive(DRIVER_THREAD_NO_DCIH, NOTIFY_DCIH);
                            MSG(INFO, "atomicSendReceive write data end.");

                            
                            memcpy(RpmbData->buf, message->request.frame, 512);
                        }                       

                        break;                    
                    default:
                        /* Unknown message has been received*/
                        break;
                    }
                }
                
                /* Update response data */
                ipcMsg  = MSG_RS;
                ipcData = 0;
                pMarshal->payload.retVal = tlRet;
                drApiUnmapTaskBuffers(THREADID_TO_TASKID(ipcClient));
#endif                
                break;
            default:
                drApiIpcUnknownMessage(&ipcClient, &ipcMsg, &ipcData);
                /* Unknown message has been received*/
                ipcMsg = (message_t) E_TLAPI_DRV_UNKNOWN;
                ipcData = 0;
                break;
        }
    }
}

/*
 * IPC handler thread entry point
 */
_THREAD_ENTRY void drIpch( void )
{
    drIpchLoop();
}

/**
 * IPC handler thread init
 */
void drIpchInit(
    const addr_t    dciBuffer,
    const uint32_t  dciBufferLen
){
    uint8_t *tee_parm_vaddr = 0;

    /* ensure thread stack is clean */
    clearStack(drIpchStack);

    drSmgmtInit();

    /* map buffer where is come from preloader so that we can get the key and rpmb quantity.*/
    drApiMapPhysicalBuffer((uint64_t)TEE_PARAMETER_BASE, 0x1000, MAP_READABLE, (void **)&tee_parm_vaddr);    
    memcpy((void *)&sec_mem_arg, (uint8_t *)(tee_parm_vaddr + (TEE_PARAMETER_ADDR - TEE_PARAMETER_BASE)), sizeof(sec_mem_arg_t));
    drApiUnmapBuffer(tee_parm_vaddr);
    
    drRpmbPartInit(sec_mem_arg.rpmb_size); 
    MSG(INFO, "rpmb size is %x", sec_mem_arg.rpmb_size);


    /* DCI message buffer */
    message = (dciMessage_t*) dciBuffer;

    /*
     * DCI buffer data can be accessed via message
     * pointer.
     */

    /*
     * Start IPC handler thread. Exception handler thread becomes local
     * exception handler of IPC handler thread
     */
    if (DRAPI_OK != drApiStartThread(
                    DRIVER_THREAD_NO_IPCH,
                    FUNC_PTR(drIpch),
                    getStackTop(drIpchStack),
                    IPCH_PRIORITY,
                    DRIVER_THREAD_NO_EXCH))

    {
        MSG(ERR, "drApiStartThread failed");
    }
}
