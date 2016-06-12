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
 * @file   drDciHandler.c
 * @brief  Implements DCI handler of the driver.
 *
 */
#include "stdlib.h"
#include "drStd.h"
#include "DrApi/DrApi.h"

#include "drCommon.h"
#include "drUtils.h"
#include "drrpmb_Api.h"
#include "drRpmbOps.h"


DECLARE_STACK(drDcihStack, 1024*16);


/* Static variables */
static dciMessage_t *message;


/**
 * DCI handler loop. this is the function where notifications from Nwd are handled
 */
_NORETURN void drDcihLoop( void )
{
    dciCommandId_t  commandId;
    dciReturnCode_t ret;
    taskid_t        taskid = drApiGetTaskid();
    int RecCmd;

    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): DCI handler thread is running");

    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): waitForSig");

    waitForSig(DRIVER_THREAD_NO_IPCH, &RecCmd);

    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): waitForSig, got a command, RecCmd=%d", RecCmd);        

    for (;;) {

        ret = RET_OK;

        switch (RecCmd) {

            case NOTIFY_DCIH:
                
                /* Get commandid from IPC */
                drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): commandId(%d)", message->command.header.commandId);
                    
                
                /* Notify Nwd */
                if (DRAPI_OK != drApiNotify())
                {
                    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): drApiNotify failed");
                } else {
                
                    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): drApiNotify success");
                }

                /* Wait for IPC signal */
                if (DRAPI_OK != drApiIpcSigWait())
                {
                    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): drApiIpcSigWait failed");
                    continue;
                } else {
                
                    drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): drApiIpcSigWait success");
                }

                break;

            case NOTIFY_IPCH:
                drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): NOTIFY_IPCH");

                break;

            default:
                drDbgPrintLnf("[Driver Drrpmb] drDcihLoop(): RecCmd default wrong!");
                break;
        } // end of switch

        /* Turn back to IPCH */
        atomicSendReceive(DRIVER_THREAD_NO_IPCH, NOTIFY_IPCH);
    }
}


/**
 * DCI handler thread entry point
 */
_THREAD_ENTRY void drDcih( void )
{
    drDcihLoop();
}


/**
 * DCI handler init
 */
void drDcihInit(
    const addr_t    dciBuffer,
    const uint32_t  dciBufferLen
){
    /* Ensure thread stack is clean */
    clearStack( drDcihStack );

    /* DCI message buffer */
    message = (dciMessage_t*) dciBuffer;

    /**
     * Update DCI handler thread so that notifications will be delivered
     * to DCI handler thread
     */
    if (DRAPI_OK != drUtilsUpdateNotificationThread( DRIVER_THREAD_NO_DCIH ))
    {
        drDbgPrintLnf("[Driver Drrpmb] drDcihInit(): Updating notification thread failed");
    }

    /**
     * Start DCI handler thread. EXH thread becomes local
     * exception handler of DCIH thread
     */
    if (DRAPI_OK != drApiStartThread(
                    DRIVER_THREAD_NO_DCIH,
                    FUNC_PTR(drDcih),
                    getStackTop(drDcihStack),
                    DCIH_PRIORITY,
                    DRIVER_THREAD_NO_EXCH))

    {
            drDbgPrintLnf("[Driver Drrpmb] drDcihInit(): drApiStartThread failed");
    }
}
