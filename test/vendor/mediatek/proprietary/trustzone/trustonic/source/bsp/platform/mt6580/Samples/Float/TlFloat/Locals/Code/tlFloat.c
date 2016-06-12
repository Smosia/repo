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

#include "tlStd.h"
#include "TlApi/TlApi.h"
#include "tlFloat_Api.h"
#include "math.h"

#define TATAG "TA FLOAT: "

// Trusted Application (TA) tlFloat.

// Reserve 4096 bytes for stack. This size is enough to cipher data and unwrap
// the maximum size of a Data container. However, it should provide some space
// for modification on the code and leave another 2048 byte for global
// variables. Note that the data/bss segment uses full 4KB pages, same for
// the code segment.
DECLARE_TRUSTLET_MAIN_STACK(4096);

tlFloat_param_t secureFloats[4];

#define print_size(x) ({double lo= (double)x; \
                        dbgSDN("[MRO:]: print_size: ",sizeof(lo)); \
                        dbgSHN("[MRO:]: lo 1: ",*(((int*)&lo)+1) );})

/**
 * Process a FLOAT init command message.
 *
 * @return RET_OK if operation has been successfully completed.
 */
static tciReturnCode_t processCmdFloatInit(tlFloat_param_t params[4]) {
    float f1 = 12.34f;
    params[0].f = f1;

    float f2 = (float)12345;
    params[1].f = f2;

    double d3 = 424242424242000000000000000000.77f;
    params[2].f = d3;

    int i4_snan=0x7F800001;
    params[3].f = *(float*)&i4_snan;

    return RET_OK;
}

static tciReturnCode_t processCmdFloatAdd(tlFloat_param_t params[4]) {
    params[2].f = params[0].f + params[1].f;
    return RET_OK;
}

static tciReturnCode_t processCmdFloatMul(tlFloat_param_t params[4]) {
    params[2].f = params[0].f * params[1].f;
    return RET_OK;
}

static tciReturnCode_t processCmdFloatSqrt(tlFloat_param_t params[4]) {
    params[2].f = sqrt(params[0].f);
    return RET_OK;
}

/**
 * TA entry.
 */
_TLAPI_ENTRY void tlMain(const addr_t tciBuffer, const uint32_t tciBufferLen) {
    tciReturnCode_t ret;
    tlApiLogPrintf(TATAG "Starting.\n");

    // Show tbase version.
    uint32_t tlApiVersion = 0;
    mcVersionInfo_t versionInfo;

    memset(&versionInfo, 0, sizeof(versionInfo));

    tlApiLogPrintf(TATAG "tbase info.\n");

    ret = tlApiGetVersion(&tlApiVersion);
    if(TLAPI_OK != ret) {
        tlApiLogPrintf(TATAG "Error, tlApiGetVersion failed with "
                "ret=0x%08X, exit.\n", ret);
        tlApiExit(ret);
    }
    tlApiLogPrintf("tlApi version    =  0x%08X\n", tlApiVersion);

    ret = tlApiGetMobicoreVersion(&versionInfo);
    if(TLAPI_OK != ret) {
        tlApiLogPrintf(TATAG "Error, tlApiGetMobicoreVersion "
                "failed with ret=0x%08X, exit.\n", ret);
        tlApiExit(ret);
    }
    tlApiLogPrintf("productId        = %s\n", versionInfo.productId);
    tlApiLogPrintf("versionMci       = 0x%08X\n", versionInfo.versionMci);
    tlApiLogPrintf("versionSo        = 0x%08X\n", versionInfo.versionSo);
    tlApiLogPrintf("versionMclf      = 0x%08X\n", versionInfo.versionMclf);
    tlApiLogPrintf("versionContainer = 0x%08X\n", versionInfo.versionContainer);
    tlApiLogPrintf("versionMcConfig  = 0x%08X\n", versionInfo.versionMcConfig);
    tlApiLogPrintf("versionTlApi     = 0x%08X\n", versionInfo.versionTlApi);
    tlApiLogPrintf("versionDrApi     = 0x%08X\n", versionInfo.versionDrApi);
    tlApiLogPrintf("versionCmp       = 0x%08X\n", versionInfo.versionCmp);

    // Check if the size of the given TCI is sufficient.
    if((NULL == tciBuffer) || (sizeof(tciMessage_t) > tciBufferLen)) {
        tlApiLogPrintf(TATAG "Error, invalid TCI size.\n");
        tlApiLogPrintf("TCI buffer: %x.\n", tciBuffer);
        tlApiLogPrintf("TCI buffer length: %d.\n", tciBufferLen);
        tlApiLogPrintf("sizeof(tciMessage_t): %d.\n", sizeof(tciMessage_t));
        tlApiExit(EXIT_ERROR);
    }

    tciMessage_t* message = (tciMessage_t*) tciBuffer;

    // TA main loop running infinitely.
    for(;;) {
        // Wait for a notification to arrive
        // (INFINITE timeout is recommended -> not polling!)
        tlApiWaitNotification(TLAPI_INFINITE_TIMEOUT);

        // Copy commandId in SWd.
        tciCommandId_t commandId = message->command.commandId;

        tlDbgPrintf(TATAG "Got a message!, commandId=%x.\n",
                commandId);

        // Check if the message received is (still) a response.
        if(!IS_CMD(commandId)) {
            // Tell the NWd a response is still pending (optional).
            tlApiNotify();
            continue;
        }

        // Copy the input data into secure memory
        memcpy(secureFloats, message->cmdFLOAT.params, sizeof(secureFloats));

        tlApiLogPrintf(TATAG "Input floats as ints: %i %i %i %i\n",
            (unsigned int)secureFloats[0].f,
            (unsigned int)secureFloats[1].f,
            (unsigned int)secureFloats[2].f,
            (unsigned int)secureFloats[3].f);

        // Process command message.
        switch(commandId) {
            //-----------------------------------------------
            case CMD_SAMPLE_FLOAT_INIT:
                // Float operators call.
                ret = processCmdFloatInit(secureFloats);
                break;
            //-----------------------------------------------
            case CMD_SAMPLE_FLOAT_ADD:
                // Float operators call.
                ret = processCmdFloatAdd(secureFloats);
                break;
            //-----------------------------------------------
            case CMD_SAMPLE_FLOAT_MUL:
                // Float operators call.
                ret = processCmdFloatMul(secureFloats);
                break;
            //-----------------------------------------------
            case CMD_SAMPLE_FLOAT_SQRT:
                // Float operators call.
                ret = processCmdFloatSqrt(secureFloats);
                break;
            //-----------------------------------------------
            default:
                // Unknown commandId.
                tlApiLogPrintf(TATAG "Error, unknow commandId=%x.\n");
                dbgBlob("TCI:", tciBuffer, 32);
                ret = RET_ERR_UNKNOWN_CMD;
                break;
        } // end switch (commandId).

        // Set up response header -> mask response ID and set return code.
        message->response.responseId = RSP_ID(commandId);
        message->response.returnCode = ret;

        // Copy the output data into secure memory
        memcpy(message->rspFLOAT.params, secureFloats, sizeof(secureFloats));

        tlApiLogPrintf(TATAG "Output cast to int: %i %i %i %i\n",
            (unsigned int)secureFloats[0].f,
            (unsigned int)secureFloats[1].f,
            (unsigned int)secureFloats[2].f,
            (unsigned int)secureFloats[3].f);
#define UPPER(x) *((uint32_t*)&(x)+1)
#define LOWER(x) *(uint32_t*)&(x)
        tlApiLogPrintf(TATAG "Output floats in hex: %p %p %p %p\n",
            LOWER(secureFloats[0].f),
            LOWER(secureFloats[1].f),
            LOWER(secureFloats[2].f),
            LOWER(secureFloats[3].f));
        tlApiLogPrintf(TATAG "Output doubles in hex:\n");
        tlApiLogPrintf(TATAG "\t0 %p-%p\n", UPPER(secureFloats[0].d), LOWER(secureFloats[0].d));
        tlApiLogPrintf(TATAG "\t1 %p-%p\n", UPPER(secureFloats[1].d), LOWER(secureFloats[1].d));
        tlApiLogPrintf(TATAG "\t2 %p-%p\n", UPPER(secureFloats[2].d), LOWER(secureFloats[2].d));
        tlApiLogPrintf(TATAG "\t3 %p-%p\n", UPPER(secureFloats[3].d), LOWER(secureFloats[3].d));

       tlDbgPrintf(TATAG "returning %d.\n",ret);
        // Notify back the TLC
        tlApiNotify();
    }
}
