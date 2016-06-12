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
 * HW Timer handling code
 *
 */

#include "drStd.h"
#include "DrApi/DrApi.h"

#include "drCommon.h"
#include "timer.h"
#include "timerUtils.h"
#include "drSmgmt.h"
#include "asyncExample.h"


/* Virtual Address of the timer mapping
 * (don't choose 0x00100000 address), because it overrides the dci
 * It is preferable to use mapping from 0x00080000 - 0x000E0000
 */
#define TIMER_BASE              0x00080000

testSession_t testSession[MAX_DR_SESSIONS];

/* Stack for ISR thread */
DECLARE_STACK(timerIsrStack,2048);


void irqReceived (void)
{
    uint32_t i;
    for (i=0; i<MAX_DR_SESSIONS; i++) {
        drSessionReg_ptr drSession= drSmgmtGetSessionData(i);
        if (drSession != NULL) {
            if (drSession->state == SESSION_STATE_ACTIVE) {
                testSession[i].wCounter++;
                tlApiResult_t ret = drApiNotifyClient(drSession->threadid);

                if (ret) {
                    drDbgPrintf("[DrAsyncExample:sid]: 0x%08X.",i);
                    drDbgPrintLnf(" Thread does not exist: 0x%08X",drSession->threadid);
                    drSmgmtCloseSession(i);
                }
            }

        }
    }
}

/* Start ISR thread triggered by driver IRQs */
drApiResult_t drTimerIsrInit(void)
{
    clearStack(timerIsrStack);

    drApiResult_t r= drApiStartThread(
            DRIVER_THREAD_NO_IRQH,      // thread number
            FUNC_PTR(timerIsr),         // function
            getStackTop(timerIsrStack), // stack
            IRQH_PRIORITY,              //priority
            DRIVER_THREAD_NO_EXCH);     //exeption handler
    if (r) {
        drDbgPrintLnf("[Driver DrAsyncExample] timerISR(): drApiStartThread failed, error 0x%08X", r);
    }
    return r;
}


/* ISR thread, waiting timer IRQ */
_THREAD_ENTRY void timerIsr(void)
{
    drApiResult_t ret;

    drDbgPrintLnf("[Driver DrAsyncExample] timerIsr()");

#ifndef NO_HW_TIMER
    uint32_t irq_num = plat_getIrqNumber();
    /* Attach to interrupt, so interrupts can occur now */
    ret = drApiIntrAttach(irq_num,INTR_MODE_RAISING_EDGE);
    if (ret != DRAPI_OK)
    {
        drDbgPrintLnf("[Driver DrAsyncExample] %s(): drApiIntrAttach failed, error 0x%08X", __func__, ret);
        (void)drApiStopThread(NILTHREAD);
    }

#endif

    /* IRQ loop */
    do {
        ret = drApiIpcSigWait();
        if (ret != DRAPI_OK)
        {
            drDbgPrintLnf("[Driver DrAsyncExample] %s(): drApiIpcSigWait failed, error 0x%08X", __func__, ret);
            break;
        }
        //drDbgPrintLnf("[Driver DrAsyncExample] Got signal");

#ifndef NO_HW_TIMER
        plat_clearInterrupt();

        ret = drApiWaitForIntr(irq_num, TIME_INFINITE, NULL);
        if (ret != DRAPI_OK)
        {
            drDbgPrintLnf("[Driver DrAsyncExample] %s(): drApiWaitForIntr failed, error 0x%08X", __func__, ret);
            break;
        }
        drDbgPrintLnf("[Driver DrAsyncExample] IRQ");

#else
        //* HW -timer is not in use. Wait a moment and then call irqReceived()
        for (volatile uint32_t i=0; i<10000000; i++);
        drDbgPrintLnf("[Driver DrAsyncExample] simulated IRQ");
#endif

        irqReceived();
    } while(ret == DRAPI_OK);

#ifndef NO_HW_TIMER
     drApiIntrDetach(irq_num);
#endif

    (void)drApiStopThread(NILTHREAD);
}

/* Initialize driver */
void timerInit(void)
{

#ifndef NO_HW_TIMER
    uint32_t   phys_addr;
    phys_addr=plat_getTimerBase();

    /* Map hardware address space for accessing timer HW */
    /* TODO: better error handling */

    drDbgPrintLnf("[Driver DrAsyncExample] timerInit phys_adr = 0x%p",phys_addr);
    if (phys_addr){
        if (DRAPI_OK != drApiMapPhysPage4KBWithHardware((page4KB_ptr)TIMER_BASE,
                (page4KB_ptr)phys_addr)) {
            drDbgPrintLnf("[DrAsyncrTest] timerInit() err: cannot map timer");
            return;
        }
    }
    plat_timerInit(TIMER_BASE);
#endif
}

/* Stop driver */
void timerShutdown(void)
{
#ifndef NO_HW_TIMER
    plat_timerShutdown();
#endif

    if (DRAPI_OK != drApiStopThread(DRIVER_THREAD_NO_IRQH)) {
        drDbgPrintLnf("[Driver DrAsyncExample] timerShutdown() err: cannot shutdown ISR thread");
    }

    if (DRAPI_OK !=  drApiUnmapPage4KB((page4KB_ptr)TIMER_BASE)) {
        drDbgPrintLnf("[Driver DrAsyncExample] timerShutdown() err: cannot unmap timer");
    }

}
