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
 * @file   tlrpmb.c
 * @brief  Trustlet implementation
 *
 * TCI data is processed and handled according to the command id and data
 * given by the associated TLC. Normal world is notified accordingly with
 * relevant return code after processing TCI command data
 *
 */

#include "tlStd.h"
#include "TlApi/TlApi.h"
#include "tlrpmb_Api.h"

#include "tlRpmbDriverApi.h"
#include "tlApirpmb.h"

// Reserve 2048 byte for stack.
DECLARE_TRUSTLET_MAIN_STACK(1024*16);




static tciReturnCode_t tlRpmbRead(tciMessage_t* message)
{
    tlApiCrSession_t  crSession;
    tlApiResult_t     tlRet;
    tciReturnCode_t   ret = RET_OK;
    int result;
    
    tlDbgPrintLnf("Proceeding with addition in SWd");
    tlDbgPrintLnf("tlRpmbRead");


    do
    {
        // Calling driver with IPC. 
      	// Call waits for the answer and blocks the trustlet. 
      	// The function is located inside drrpmb.lib
      	// that is implemented and built within drrpmb 	

        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiOpenSession");

        crSession = tlApiRpmbOpenSession(1);
        if (crSession == 0xFFFFFFFF)
            return RET_ERROR;

        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiReadData start.");

        tlApiRpmbReadData(crSession, message->Buf, message->BufSize, &result);

        if (result) {
            tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiReadData error. (%x)", result);
            ret = RET_ERROR;
        }

        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiCloseSession");    
        
        tlApiRpmbCloseSession(crSession);
        
        
    }while(false);

    message->ResultData = result;
    tlDbgPrintLnf("[Trustlet Tlrpmb] Result (%d)", message->ResultData);


    return ret;    
}

static tciReturnCode_t tlRpmbWrite(tciMessage_t* message)
{
    tlApiCrSession_t  crSession;
    tlApiResult_t     tlRet;
    tciReturnCode_t   ret = RET_OK;
    int result;

    tlDbgPrintLnf("Proceeding with addition in SWd");
    tlDbgPrintLnf("tlRpmbWrite");


    do
    {
        // Calling driver with IPC. 
      	// Call waits for the answer and blocks the trustlet. 
      	// The function is located inside drrpmb.lib
      	// that is implemented and built within drrpmb 	


        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiOpenSession");
        crSession = tlApiRpmbOpenSession(1);
        if (crSession == 0xFFFFFFFF)
            return RET_ERROR;

        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiRpmbWriteData");
        tlDbgPrintLnf("[Trustlet Tlrpmb] message->Buf[0]=%x",message->Buf[0]);
        tlDbgPrintLnf("[Trustlet Tlrpmb] message->Buf[1]=%x",message->Buf[1]);
        tlDbgPrintLnf("[Trustlet Tlrpmb] message->Buf[2]=%x",message->Buf[2]);

        tlApiRpmbWriteData(crSession, message->Buf, message->BufSize, &result);

        if (result) {
            tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiRpmbWriteData error. (%x)", result);
            ret = RET_ERROR;            
        }
        
        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiWriteData, result=%x", result);
        tlDbgPrintLnf("[Trustlet Tlrpmb] tlApiCloseSession");
        
        tlApiRpmbCloseSession(crSession);

        
    }while(false);

    message->ResultData = result;
    
    tlDbgPrintLnf("[Trustlet Tlrpmb] Result (%d)", message->ResultData);

    return ret; 
}


/**
 * Trustlet entry.
 */
_TLAPI_ENTRY void tlMain( const addr_t tciBuffer, const uint32_t tciBufferLen )
{
    tciReturnCode_t ret;
    tciCommandId_t commandId;
    uint8_t *data;
    size_t data_len;
    uint8_t *hmac;
    size_t hmac_len;
            	
    tlApiLogPrintf("[<t Trustlet TlSampleRot13WithDriver], Build " __DATE__ ", " __TIME__ EOL);

    dbgSN("RPMB trustlet is starting");

    {
        uint32_t tlApiVersion;
        mcVersionInfo_t versionInfo;

        ret = tlApiGetVersion(&tlApiVersion);
        if (TLAPI_OK != ret) 
        {
            tlDbgPrintLnf("tlApiGetVersion failed with ret=0x%08X, exit", ret);
            tlApiExit(ret);
        }
        tlDbgPrintLnf("tlApi version 0x%08X, exit", tlApiVersion);

        ret = tlApiGetMobicoreVersion(&versionInfo);
        if (TLAPI_OK != ret) 
        {
            tlDbgPrintLnf("tlApiGetMobicoreVersion failed with ret=0x%08X, exit", ret);
            tlApiExit(ret);
        }
        tlDbgPrintLnf("productId        = %s",     versionInfo.productId);
        tlDbgPrintLnf("versionMci       = 0x%08X", versionInfo.versionMci);
        tlDbgPrintLnf("versionSo        = 0x%08X", versionInfo.versionSo);
        tlDbgPrintLnf("versionMclf      = 0x%08X", versionInfo.versionMclf);
        tlDbgPrintLnf("versionContainer = 0x%08X", versionInfo.versionContainer);
        tlDbgPrintLnf("versionMcConfig  = 0x%08X", versionInfo.versionMcConfig);
        tlDbgPrintLnf("versionTlApi     = 0x%08X", versionInfo.versionTlApi);
        tlDbgPrintLnf("versionDrApi     = 0x%08X", versionInfo.versionDrApi);
        tlDbgPrintLnf("versionCmp       = 0x%08X", versionInfo.versionCmp);
    }

    /* Check if the size of the given TCI is sufficient */
    if ((NULL == tciBuffer) || (sizeof(tciMessage_t) > tciBufferLen))
    {
        dbgSN("TCI error");
        dbgSPN("TCI buffer: ", tciBuffer);
        dbgSPN("TCI buffer length: ", tciBufferLen);
        dbgSDN("sizeof(tciMessage_t): ", sizeof(tciMessage_t));
        tlApiExit(EXIT_ERROR);
    }

    dbgSN("RPMB trustlet is processing the command");
    tciMessage_t* message = (tciMessage_t*) tciBuffer;

    for (;;)
    {
        dbgSN("RPMB trustlet is waiting for a notification to arrive");

        /* Wait for a notification to arrive */
        tlApiWaitNotification(TLAPI_INFINITE_TIMEOUT);

        /* Dereference commandId once for further usage */
        commandId = message->cmdrpmb.header.commandId;

        tlDbgPrintLnf("[Trustlet Tlrpmb]Got a message, commandId=0x%08X", commandId);

        /* Check if the message received is (still) a response */
        if (!IS_CMD(commandId)) 
        {
            tlApiNotify();
            continue;
        }

        /* Process command message */
        switch (commandId) 
        {
            case CMD_RPMB_READ_DATA:
                tlDbgPrintLnf("[Trustlet Tlrpmb] CMD_RPMB_READ_DATA");
                ret = tlRpmbRead(message);

                break;
            case CMD_RPMB_WRITE_DATA:
                tlDbgPrintLnf("[Trustlet Tlrpmb] CMD_RPMB_WRITE_DATA");
                ret = tlRpmbWrite(message);
                
                break;

            default:
              /* Unknown command ID */
              ret = RET_ERR_UNKNOWN_CMD;
              break;
	    }
	
		/* Set up response header */
		message->rsprpmb.header.responseId = RSP_ID(commandId);
		message->rsprpmb.header.returnCode = ret;
		
		dbgSDN("RPMB trustlet is exiting with status ", ret);
		
		/* Notify back the TLC */
		tlApiNotify();
	}
}
