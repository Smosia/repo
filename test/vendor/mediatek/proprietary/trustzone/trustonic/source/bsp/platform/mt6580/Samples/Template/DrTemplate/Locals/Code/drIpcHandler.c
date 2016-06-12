/*
 * Copyright (c) 2013-2014 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

/**
 * @file   drIpcHandler.c
 * @brief  Implements IPC handler of the driver.
 *
 */

#include "drStd.h"
#include "DrApi/DrApi.h"

#include "drApiMarshal.h"
#include "drCommon.h"
#include "drSmgmt.h"
#include "drTemplate_Api.h"


DECLARE_STACK(drIpchStack, 2048);

/**
 * IPC handler loop. this is the function where IPC messages are handled
 */
_NORETURN void drIpchLoop( void )
{
    /* Set IPC parameters for initial MSG_RD to IPCH */
    threadid_t     ipcClient = NILTHREAD;
    message_t      ipcMsg    = MSG_RD;
    uint32_t       ipcData   = 0;
    uint32_t       ipcMsgId  = 0;
    tlApiResult_t  tlRet     = E_TLAPI_DRV_UNKNOWN;
    drMarshalingParamTemplate_ptr pMarshal;


    for (;;)
    {
        /*
         * When called first time sends ready message to IPC server and
         * then waits for IPC requests
         */
        if (DRAPI_OK != drApiIpcCallToIPCH(&ipcClient, &ipcMsg, &ipcData))
        {
            drDbgPrintLnf("[Driver DrTemplate] drIpchLoop(): drApiIpcCallToIPCH failed");
            continue;
        }

        ipcMsgId=drApiExtractMsgCmd(ipcMsg);

        /* Dispatch request */
        switch (ipcMsgId)
        {
            case MSG_CLOSE_TRUSTLET:
                /* Close active sessions owned by trustlet, which is being shutdown */
                //For MSG_CLOSE_TRUSTLET message threadId is in ipcData parameter
                drSmgmtCloseSessionForThread(ipcData);

                ipcMsg = MSG_CLOSE_TRUSTLET_ACK;
                ipcData = 0;
                break;
            case MSG_CLOSE_DRIVER:
                /* Acknowledge */
                ipcMsg = MSG_CLOSE_DRIVER_ACK;
                ipcData = 0;
                break;
            case MSG_RQ:
                {
                    ipcMsg  = MSG_RS; // send response message...
                    ipcData = E_TLAPI_DRV_INVALID_PARAMETERS;
                }
                break;
           case MSG_RQ_EX:
               if ((drApiExtractMsgLen(ipcMsg))<sizeof(reqPayloadHeader_t)){
                  ipcMsg  = MSG_RS; // send response message...
                  ipcData = E_TLAPI_DRV_INVALID_PARAMETERS;
                  break;
              }
            #ifdef DR_FEATURE_TL_API
                /**
                 * Handle incoming IPC requests via TL API.
                 * Map the caller trustlet to access to the marshaling data
                 */
                pMarshal = (drMarshalingParamTemplate_ptr)drApiMapClientAndParams(
                                            ipcClient,
                                            ipcData);

                if (pMarshal)
                {
                    /* Process the request */
                    switch (pMarshal->header.functionId)
                    {
                    case FID_DR_OPEN_SESSION:
                        /**
                         * Handle open session request
                         */
                        pMarshal->sid = drSmgmtOpenSession(ipcClient);
                        /**
                         * Update IPC status as success. If there is an error with opening session
                         * invalid sid will indicatethat opening session failed
                         */
                        tlRet = TLAPI_OK;
                        break;
                    case FID_DR_CLOSE_SESSION:
                        /**
                         * Handle close session request based on the sid provided by the client
                         */
                        drSmgmtCloseSession(pMarshal->sid);
                        tlRet = TLAPI_OK;
                        break;
                    case FID_DR_INIT_DATA:
                        /**
                         * Initialize sesion data
                         */
                        if (E_DR_SMGMT_OK == drSmgmtSetSessionData(
                                pMarshal->sid,
                                ipcClient,
                                &(pMarshal->payload.sampleData)))
                        {
                            tlRet = TLAPI_OK;
                        }
                        break;
                    case FID_DR_EXECUTE:
                        /**
                         * TODO: Read registry data compare threadids to make sure that client is allowed to use
                         * registry data. Then execute the command and update tlRet accordingly
                         */
                        break;
                    default:
                        /* Unknown message has been received*/
                        break;
                    }
                    pMarshal->header.retVal = tlRet;
                }

                /* Update response data */
                ipcMsg  = MSG_RS;
                ipcData = 0;
            #endif // DR_FEATURE_TL_API
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


/**
 * IPC handler thread entry point
 */
_THREAD_ENTRY void drIpch( void )
{
    drIpchLoop();
}


/**
 * IPC handler init
 */
void drIpchInit(void)
{
    /* ensure thread stack is clean */
    clearStack(drIpchStack);

    /**
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
            drDbgPrintLnf("[Driver DrTemplate] drIpchInit(): drApiStartThread failed");
    }
}
