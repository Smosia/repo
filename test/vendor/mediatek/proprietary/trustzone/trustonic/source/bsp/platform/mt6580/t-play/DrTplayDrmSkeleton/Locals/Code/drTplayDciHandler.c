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
 * @file   drDciHandler.c
 * @brief  Implements DCI handler of the driver.
 *
 */

#include "drStd.h"
#include "DrApi/DrApi.h"
#include "tlTplayDci.h"

#include "drTplayUtils.h"
#include "drTplayCommon.h"

#include "TlApi/TlApiTplay.h"

DECLARE_STACK(drDcihStack, 2048);

/* Static variables */
static dciMessage_t *message;

/**
 * DCI handler loop. this is the function where notifications from Nwd are handled
 */
_NORETURN void drDcihLoop(void) {
    dciCommandId_t commandId;
    dciReturnCode_t ret;
    drDbgPrintLnf("[DRM Driver] drDcihLoop(): drDcihLoop started");

    /* Notifies Nwd that DCI thread is starting */
    if (DRAPI_OK != drApiNotify()) {
        dbgSN("[DRM Driver] drDcihLoop(): Failed to notify Nwd about thread starting");;
    }

    for (;;) {
        /* Initialize return value */
        ret = TLAPI_DRM_OK;

        /* Wait for IPC signal */
        if (DRAPI_OK != drApiIpcSigWait()) {
            drDbgPrintLnf("[DRM Driver] drDcihLoop(): drApiIpcSigWait failed");
            continue;
        }

        dbgSN("[DRM Driver] drDcihLoop(): received IPC Signal ");

        /* Get commandid */
        commandId = message->command.header.commandId;

        /* Get & process DCI command */
        switch (commandId) {
            /**
             * COMMANDS THAT WILL BE CALLED FROM THE OMX COMPONENT SHOULD BE IMPLEMENTED HERE
             * For Example :
             */
            case FID_DR_MANAGE_HANDLE:
                dbgSN("[DRM Driver] drDcihLoop(): Manage Handle Function");
                if (message->command.manageHandleData.handlePhys != 0) {
                    gOutputHandlePhysAddr = (phys_addr_t) message->command.manageHandleData.handlePhys;
                }
                if (message->command.manageHandleData.handleToPhysTablePhys != 0) {
                    gHandle2PhysTablePhysAddr = message->command.manageHandleData.handleToPhysTablePhys;
                    gHandle2PhysTableSize = message->command.manageHandleData.handleToPhysTableSize / sizeof(handleToPhys_t);
                }
                drDbgPrintf("[DRM Driver] drDcihLoop(): gHandlePhysAddr=0x%08x%08x, gTablePhysAddr=0x%08x%08x, gTableSize=0x%08x\n",
                            HIGHU32(gOutputHandlePhysAddr), LOWU32(gOutputHandlePhysAddr),
                            HIGHU32(gHandle2PhysTablePhysAddr), LOWU32(gHandle2PhysTablePhysAddr), gHandle2PhysTableSize);
                /* WARNING / TODO :
                 * - gOutputHandlePhysAddr can be accessed from NWd
                 * - gHandle2PhysTablePhysAddr MUST be in secure memory : CHECK TO BE DONE !
                 */
                //isAddressSecure()
                //decode()
                break;

            case FID_DR_DECODE_INITIALIZE:
                dbgSN("[DRM Driver] drDcihLoop(): Decode Initialize Function");
                //isAddressSecure()
                //decode()
                break;

            case FID_DR_DECODE_PROCESS:
                dbgSN("[DRM Driver] drDcihLoop(): Decoding Process Function");
                //sendToScreen()
                break;

            case FID_DR_DISPLAY_CONTENT:
                dbgSN("[DRM Driver] drDcihLoop(): Displaying Function");
                //sendToScreen()
                break;

            default:
                dbgSHN("[DRM Driver] drDcihLoop(): Command not supported %d", commandId);
                break;
        }

        message->response.header.responseId = RSP_ID(commandId);
        message->response.header.returnCode = ret;

        /* Notify Nwd */
        if (DRAPI_OK != drApiNotify()) {
            drDbgPrintLnf("[DRM Driver] drDcihLoop(): drApiNotify failed");
        }
    }
}

/**
 * DCI handler thread entry point
 */
_THREAD_ENTRY void drDcih(void) {
    drDcihLoop();
}

/**
 * DCI handler init
 */
void drDcihInit(const addr_t dciBuffer, const uint32_t dciBufferLen) {
    drDbgPrintLnf("[DRM Driver] drDcihInit(): init");

    /* Ensure thread stack is clean */
    clearStack( drDcihStack );

    /* DCI message buffer */
    message = (dciMessage_t*) dciBuffer;

    /**
     * Update DCI handler thread so that notifications will be delivered
     * to DCI handler thread
     */
    if (DRAPI_OK != drUtilsUpdateNotificationThread(DRIVER_THREAD_NO_DCIH)) {
        drDbgPrintLnf("[DRM Driver] drDcihInit(): Updating notification thread failed");
    }

    /**
     * Start DCI handler thread. EXH thread becomes local
     * exception handler of DCIH thread
     */
    if (DRAPI_OK != drApiStartThread(DRIVER_THREAD_NO_DCIH,
                                     FUNC_PTR(drDcih),
                                     getStackTop(drDcihStack),
                                     DCIH_PRIORITY,
                                     DRIVER_THREAD_NO_EXCH))

    {
        drDbgPrintLnf("[DRM Driver] drDcihInit(): drApiStartThread failed");
    }
}
