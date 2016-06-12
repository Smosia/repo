/*
 * Copyright (c) 2015 TRUSTONIC LIMITED
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
#include "tci.h"

/**
 * TCI handler loop
 */
_NORETURN void tlTciHandler_Loop(
    const addr_t tciBuffer)
{
    TEE_ReturnCode_t ret = TEE_OK;
    tci_t* pTci = (tci_t*) tciBuffer;
    tciCommandId_t commandId;

    /* The Trustlet main loop */
    for (;;) {

        /* Wait for notification infinitely */
        tlApiWaitNotification(TLAPI_INFINITE_TIMEOUT);

        /* Retrieve command id */
        commandId = pTci->message.command.header.commandId;

        /* Process command message */
        switch (commandId)
        {
            /****************************************************/
            case CMD_ID_TEE_MILLI_SINCE_BOOT:
                ret = tlCryptoHandler_GetMillisecondsSinceBoot(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GET_PASSWORD_KEY:
                ret = tlCryptoHandler_GetPasswordKey(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GET_AUTH_TOKEN_KEY:
                ret = tlCryptoHandler_GetAuthTokenKey(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_GET_RANDOM:
                ret = tlCryptoHandler_GetRandom(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_COMPUTE_PASS_SIGNATURE:
                ret = tlCryptoHandler_ComputePasswordSignature(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_FAILURE_RECORD_DECODE:
                ret = tlCryptoHandler_FailureRecordDecode(pTci);
                break;
            /****************************************************/
            case CMD_ID_TEE_FAILURE_RECORD_ENCODE:
                ret = tlCryptoHandler_FailureRecordEncode(pTci);
                break;
            /****************************************************/
            default:
                /* Unknown command */
                tlApiLogPrintf( TATAG"tlTciHandler_Loop(): "
                                "Received unknown command id (%d)\n",
                                commandId);
                ret = TEE_ERR_UNKONWN_ERROR;
                break;
        }

        /* Set response header */
        pTci->message.response.header.responseId = RSP_ID(commandId);
        pTci->message.response.header.returnCode = ret;

        /* Notify the TLC */
        tlApiNotify();
    }
}
