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
 * Low level touch API stubs for generic platform
 */


#include "drStd.h"
#include "DrApi/DrApi.h"
#include "drError.h"
#include "device.h"
#include "drTuiHal.h"


#define TAG "[PLAT TOUCH] "

/* Stack for ISR thread */
DECLARE_STACK(touchThreadStack, 2048);

// defines for generic touch screen
#define TOUCH_INTR_NUM  0
#define I2C_INTR_NUM    0

static drTuiHalError_t touchRead(drTouchState_t *state, drTouchCoordinates_t *tsXY)
{
    // Attach touch interrupt to SWd
    if ( DRAPI_OK != drApiIntrAttach(I2C_INTR_NUM, INTR_MODE_FALLING_EDGE) ) {
        drDbgPrintLnf(TAG "%s:%i i2c interrupt attach error", __func__, __LINE__);
        return E_TUI_HAL_INTERNAL;
    }

    // TODO: Read touch event on i2c bus

    if (DRAPI_OK != drApiIntrDetach(I2C_INTR_NUM)) {
        drDbgPrintLnf(TAG "%s:%i i2c interrupt detach error", __func__, __LINE__);
        return E_TUI_HAL_INTERNAL;
    }

    return TUI_HAL_OK;
}

drTuiHalError_t tuiHalTouchGetInfo(drTouchInfo_ptr touchSize)
{
    drDbgPrintLnf(TAG "%s", __func__);

    // TODO: return real metrics
    touchSize->width = 1024;
    touchSize->height = 1024;

    return TUI_HAL_OK;
}

/**
 * Touch thread entry
 */
_THREAD_ENTRY void touchThreadEntry(void)
{
    drTouchCoordinates_t tsXY = {0xFFFF,0xFFFF};
    uint32_t touching = 0;
    uint32_t intNum = 0;
    drTouchState_t state = PEN_UP;

    drDbgPrintLnf(TAG "%s: TOUCH thread is running.", __func__);

    // Attach touch interrupt to SWd
    if ( DRAPI_OK != drApiIntrAttach(TOUCH_INTR_NUM, INTR_MODE_FALLING_EDGE) ) {
        drDbgPrintLnf(TAG "%s:%i touch interrupt attach error", __func__, __LINE__);
        drApiThreadSleep(TIME_INFINITE);
    }

    // Touch detection loop
    for (;;) {
        // Unlock Touch hardware
        drTouchUnlock();

        // Wait for touch IRQ
        if ( DRAPI_OK != drApiWaitForIntr(TOUCH_INTR_NUM, TIME_INFINITE, &intNum) ) {
            drDbgPrintLnf(TAG "%s:%i touch interrupt detach error", __func__, __LINE__);
            drApiThreadSleep(TIME_INFINITE);
        }

        // Lock touch hardware
        drTouchLock();

        // TODO: Acknowledge touch IRQ

        // Read touch events
        do {
            // Read touch event on i2c bus
        	touchRead(&state, &tsXY);

            // Report touch event
            drTouchReport(state, tsXY);

            // TODO: Check if still touching

        } while (touching);
    } // end for(;;)
}

drTuiHalError_t tuiHalTouchOpen()
{
    drTuiHalError_t retHal = E_TUI_HAL_INTERNAL;
    drApiResult_t r;

    drDbgPrintLnf(TAG "%s", __func__);

    // TODO: Initialize i2c bus connected to the touch device

    // TODO: Initialize touch module

    // Start thread
    clearStack(touchThreadStack);
    r = drApiStartThread(DRIVER_THREAD_NO_TOUCHH,           // thread number
            FUNC_PTR(touchThreadEntry),     // function
            getStackTop(touchThreadStack),  // stack
            TOUCHH_PRIORITY,                // priority
            DRIVER_THREAD_NO_EXCH);         // exeption handler
    if (r == DRAPI_OK) {
        retHal = TUI_HAL_OK;
    } else {
        drDbgPrintLnf(TAG "%s: drApiStartThread failed, error %x", __func__, r);
    }

    return retHal;
}

/**
 * @brief  Release all resources related to the TSP and kill the Touch thread.
 * @return drTuiHalError_t
 */
drTuiHalError_t tuiHalTouchClose()
{
    drTuiHalError_t ret = E_TUI_HAL_INTERNAL;

    drDbgPrintLnf(TAG "%s", __func__);

    // TODO: Reset Touch device
    // to avoid NWd to read data related to the last coordinates of the user finger.

    // TODO: Release i2c

    // Kill Touch thread
    if (DRAPI_OK == drApiStopThread(DRIVER_THREAD_NO_TOUCHH)) {
        ret = TUI_HAL_OK;
    }

    return ret;
}
