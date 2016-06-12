/*
 * Copyright (c) 2014 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

#include <stdlib.h>
#include <string.h>

#include "MobiCoreDriverApi.h"
#include "tlFloat_Api.h"
#include "tlcFloat.h"
#include "log.h"

static const uint32_t DEVICE_ID = MC_DEVICE_ID_DEFAULT;
tciMessage_t *tci;
mcSessionHandle_t sessionHandle;

// -------------------------------------------------------------
mcResult_t tlcOpen(mcSpid_t spid, uint8_t* pTAData,  uint32_t nTASize)
{
    mcResult_t mcRet;
    mcVersionInfo_t versionInfo;

    LOG_I("Opening <t-base device.");
    mcRet = mcOpenDevice(DEVICE_ID);
    if(MC_DRV_OK != mcRet) {
        LOG_E("Error opening device: %d.", mcRet);
        return mcRet;
    }

    mcRet = mcGetMobiCoreVersion(MC_DEVICE_ID_DEFAULT, &versionInfo);
    if(MC_DRV_OK != mcRet) {
        LOG_E("mcGetMobiCoreVersion failed %d.", mcRet);
        mcCloseDevice(DEVICE_ID);
        return mcRet;
    }
    LOG_I("productId        = %s",     versionInfo.productId);
    LOG_I("versionMci       = 0x%08X", versionInfo.versionMci);
    LOG_I("versionSo        = 0x%08X", versionInfo.versionSo);
    LOG_I("versionMclf      = 0x%08X", versionInfo.versionMclf);
    LOG_I("versionContainer = 0x%08X", versionInfo.versionContainer);
    LOG_I("versionMcConfig  = 0x%08X", versionInfo.versionMcConfig);
    LOG_I("versionTlApi     = 0x%08X", versionInfo.versionTlApi);
    LOG_I("versionDrApi     = 0x%08X", versionInfo.versionDrApi);
    LOG_I("versionCmp       = 0x%08X", versionInfo.versionCmp);

    tci = (tciMessage_t*)malloc(sizeof(tciMessage_t));
    if(tci == NULL) {
        LOG_E("Allocation of TCI failed.");
        mcCloseDevice(DEVICE_ID);
        return MC_DRV_ERR_NO_FREE_MEMORY;
    }
    memset(tci, 0, sizeof(tciMessage_t));

    LOG_I("Opening the session.");
    memset(&sessionHandle, 0, sizeof(mcSessionHandle_t));
    // The device ID (default device is used).
    sessionHandle.deviceId = DEVICE_ID;
    //LOG_I_BUF("tlcSampleFloat Trusted Application:", pTAData, nTASize);
    mcRet = mcOpenTrustlet(
            &sessionHandle,
            spid,
            pTAData,
            nTASize,
            (uint8_t*)tci,
            sizeof(tciMessage_t));

    if(MC_DRV_OK != mcRet) {
        LOG_E("Open session failed: %d.", mcRet);
        free(tci);
        tci = NULL;
        mcCloseDevice(DEVICE_ID);
    } else {
        LOG_I("open() succeeded.");
    }

    return mcRet;
}

static void printFloats(tlcFloat_param_t floats[4]) {
    LOG_I(  "\t0: %f - %lf\n"
            "\t1: %f - %lf\n"
            "\t2: %f - %lf\n"
            "\t3: %f - %lf\n",
            floats[0].f, floats[0].d,
            floats[1].f, floats[1].d,
            floats[2].f, floats[2].d,
            floats[3].f, floats[3].d);
}

// -------------------------------------------------------------
static mcResult_t tlcFloatCalc(tlcFloat_param_t params[4], uint32_t cmd) {
    mcResult_t ret;

    printFloats(params);

    if(NULL == tci) {
        LOG_E("TCI has not been set up properly - exiting.");
        return -1;
    }

    do {
        // Prepare command message in TCI.
        tci->command.commandId = cmd;
        memcpy(tci->cmdFLOAT.params, params, sizeof(tci->cmdFLOAT.params));

        // Notify the TA.
        ret = mcNotify(&sessionHandle);
        if(MC_DRV_OK != ret) {
            LOG_E("Notify failed: %d.", ret);
            break;
        }

        // Wait for the TA response.
        ret = mcWaitNotification(&sessionHandle, -1);
        if(MC_DRV_OK != ret) {
            LOG_E("Wait for response notification failed: %d.", ret);
            break;
        }

        // Verify that the TA sent a response.
        if((RSP_ID(cmd) != tci->response.responseId)) {
            LOG_E("TA did not send a response: %d.",
                    tci->response.responseId);
            break;
        }

        // Check the TA return code.
        if(RET_OK != tci->response.returnCode) {
            LOG_E("TA did not send a valid return code: %d.",
                    tci->response.returnCode);
            break;
        }

        // Get the result
        memcpy(params, tci->rspFLOAT.params, sizeof(tci->rspFLOAT.params));
        printFloats(params);

    } while (false);

    return ret;
}

mcResult_t tlcFloatInit(tlcFloat_param_t params[4]) {
    LOG_I("floatInit()");
    return tlcFloatCalc(params, CMD_SAMPLE_FLOAT_INIT);
}

mcResult_t tlcFloatAdd(tlcFloat_param_t params[4]) {
    LOG_I("floatAdd()");
    return tlcFloatCalc(params, CMD_SAMPLE_FLOAT_ADD);
}

mcResult_t tlcFloatMul(tlcFloat_param_t params[4]) {
    LOG_I("floatMul()");
    return tlcFloatCalc(params, CMD_SAMPLE_FLOAT_MUL);
}

mcResult_t tlcFloatSqrt(tlcFloat_param_t params[4]) {
    LOG_I("floatSqrt()");
    return tlcFloatCalc(params, CMD_SAMPLE_FLOAT_SQRT);
}

// -------------------------------------------------------------
void tlcClose(void)
{
    mcResult_t ret;

    LOG_I("Closing the session.");
    ret = mcCloseSession(&sessionHandle);
    if (MC_DRV_OK != ret) {
        LOG_E("Closing session failed: %d.", ret);
        // Continue even in case of error.
    }

    LOG_I("Closing <t-base device.");
    ret = mcCloseDevice(DEVICE_ID);
    if (MC_DRV_OK != ret) {
        LOG_E("Closing <t-base device failed: %d.", ret);
        // Continue even in case of error.
    }
    free(tci);
    tci = NULL;
}
