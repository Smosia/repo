/*
 * Copyright (c) 2013-2015 TRUSTONIC LIMITED
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

#include "tlCryptoHandler.h"
#include "tlTeeKeymaster_Api.h"

/**
 * TCI handler loop
 */
_NORETURN void tlTciHandler_Loop(
    const addr_t tciBuffer,
    const uint32_t tciBufferLen)
{
    keymaster_error_t ret = KM_ERROR_OK;
    tciCommandId_t commandId;
    tci_t *pTci = (tci_t*)tciBuffer;
    (void)tciBufferLen;

    /* The Trustlet main loop */
    while (1) {
        /* Wait for notification infinitely */
        tlApiWaitNotification(TLAPI_INFINITE_TIMEOUT);

        /* Retrieve command id */
        commandId = pTci->message.command.header.commandId;

        /* Process command message */
        switch (commandId) {
            /****************************************************/
            case CMD_ID_TEE_ADD_RNG_ENTROPY:
                ret = handle_AddRngEntropy(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GENERATE_KEY:
                ret = handle_GenerateKey(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GET_KEY_CHARACTERISTICS:
                ret = handle_GetKeyCharacteristics(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_IMPORT_KEY:
                ret = handle_ImportKey(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_EXPORT_KEY:
                ret = handle_ExportKey(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_BEGIN:
                ret = handle_Begin(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_UPDATE:
                ret = handle_Update(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_FINISH:
                ret = handle_Finish(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_ABORT:
                ret = handle_Abort(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GET_KEY_INFO:
                ret = handle_GetKeyInfo(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GET_OPERATION_INFO:
                ret = handle_GetOperationInfo(pTci);
                break;
            /****************************************************/
            default:
                /* Unknown command */
                tlApiLogPrintf(TATAG"tlTciHandler_Loop(): Received unknown command id (%d)\n",
                        commandId);
                ret = KM_ERROR_UNKNOWN_ERROR;
                break;
        }

        /* Set response header */
        pTci->message.response.header.responseId = RSP_ID(commandId);
        pTci->message.response.header.returnCode = ret;

        /* Notify the TLC */
        tlApiNotify();
    }
}
