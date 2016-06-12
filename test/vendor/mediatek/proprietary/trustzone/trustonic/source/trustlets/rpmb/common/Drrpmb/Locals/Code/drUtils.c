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

#include "drStd.h"

#include "drUtils.h"
#include "drCommon.h"
#include "DrApi/DrApi.h"


/**
 * Exchange registers of current thread or another thread.
 *
 * @param  threadNo   Thread no
 * @param  ip         ip value to be set
 * @param  sp         sp value to be set
 *
 * @retval DRAPI_OK or relevant error code.
 */
drApiResult_t drUtilsRestartThread(
    threadno_t threadNo,
    addr_t ip,
    addr_t sp )
{
    drApiResult_t ret  = E_INVALID;
    uint32_t      ctrl = THREAD_EX_REGS_IP | THREAD_EX_REGS_SP;

    /* Set ip and sp registers */
    ret = drApiThreadExRegs(threadNo,
                            ctrl,
                            ip,
                            sp);
    if (ret != DRAPI_OK)
    {
        return ret;
    }

    /* Resume thread */
    ret = drApiResumeThread(threadNo);

    return ret;
}

/**
 * Makes request to RTM for updating notification thread
 *
 * @param  threadNo   Thread no
 *
 * @retval E_OK or relevant error code.
 */
drApiResult_t drUtilsUpdateNotificationThread( threadno_t threadNo )
{
    drApiResult_t ret =  E_INVALID;
    threadid_t  notificationThread;

    /* Retrieve task id */
    taskid_t task = drApiGetTaskid();
    if (task == NILTASK)
    {
        return ret;
    }

    /* Retrieve thread id based on task id and thread no */
    notificationThread = drApiTaskidGetThreadid(task, threadNo);

    /* IPC data for setting notification handler */
    threadid_t  ipcClient = notificationThread;
    message_t   ipcMsg    = MSG_SET_NOTIFICATION_HANDLER;
    uint32_t    ipcData   = 0;

    /* Make IPC call to IPCH and wait for a response */
    ret = drApiIpcCallToIPCH(&ipcClient, &ipcMsg, &ipcData);

    return ret;
}


static __attribute__((noinline)) uint32_t ipc(
    threadid_t partner,
    word_t mr0) 
{

    register uint32_t reg0 __asm__("r0") = partner;
    register uint32_t reg1 __asm__("r1") = mr0;
    register uint32_t reg2 __asm__("r2") = 0;
    register uint32_t reg3 __asm__("r3") = 0;
    register uint32_t reg4 __asm__("r4") = 0x0C000000;  // = IPC_SEND_SHORT_RECV_SHORT
                                                        // | IPC_TIMEOUT_SEND_INF_REVC_INF
                                                        // | IPC_FLAG_SEND_ERR_IGNORE

    __asm__ volatile (
        "swi #0x11 \n"
        : "+r"(reg0), "+r"(reg1), "+r"(reg2), "+r"(reg3), "+r"(reg4)
    );

    return reg0;
}

uint32_t atomicSendReceive(
    const threadid_t ThId,
    uint32_t param1)
{

    threadid_t ipcPartner = TASKID_GET_THREADID(NILTASK,ThId);
    drDbgPrintLnf(" %s() 1: ThId = %x ipcPartner= %x", __func__, ThId, ipcPartner);
    uint32_t ret = ipc(ipcPartner, param1);

    drDbgPrintLnf(" %s() 2: ThId = %x ipcPartner= %x", __func__, ThId, ipcPartner);
    return ret;
}

static __attribute__((noinline)) uint32_t recv_short(
    threadid_t partner,
    word_t *mr0) 
{

    register uint32_t reg0 __asm__("r0") = partner;
    register uint32_t reg1 __asm__("r1") = 0;
    register uint32_t reg2 __asm__("r2") = 0;
    register uint32_t reg3 __asm__("r3") = 0;
    register uint32_t reg4 __asm__("r4") = 0x61000000|TIME_INFINITE; // IPC_RECV_SHORT | IPC_FLAG_TIMEOUT_RECV

    __asm__ volatile (
        "swi #0x11 \n"
        : "+r"(reg0), "+r"(reg1), "+r"(reg2), "+r"(reg3), "+r"(reg4)
    );

    if (E_OK == reg0)
    {
        *mr0 = reg2;
    }
    
    return reg0;
}

uint32_t waitForSig(const threadid_t ThId, uint32_t *Cmd)
{
    uint32_t ret;
    uint32_t RecParm;
    threadid_t ipcPartner = TASKID_GET_THREADID(NILTASK,ThId);

    drDbgPrintLnf(" %s() 1: ThId = %x ipcPartner= 0x%x", __func__, ThId, ipcPartner);

    ret = recv_short(ipcPartner, (word_t *) &RecParm);

    drDbgPrintLnf(" %s() 2: ThId = %x ipcPartner= 0x%x", __func__, ThId, ipcPartner);

    if (E_OK == ret) {
        *Cmd = RecParm;
    }

    return ret;
}




