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

#include "tlStd.h"
#include "TlApi/TlApi.h"

#include "mcVersionHelper.h"
#include "tlCryptoCatalog_Common.h"
#include "tlCryptoCatalog_Api.h"

/**
 * TCI handler loop
 */
_NORETURN void tlTciHandler_Loop(
    const addr_t tciBuffer,
    const uint32_t tciBufferLen)
{
    tciReturnCode_t  ret = RET_OK;
    tciCommandId_t   commandId;

    tci_t* pTci = (tci_t*) tciBuffer;

    /* The Trustlet main loop */
    for (;;) {

        /* Wait for notification infinitely */
        tlApiWaitNotification(TLAPI_INFINITE_TIMEOUT);

        /* Retrieve command id */
        commandId = pTci->message.command.header.commandId;

        /* Process command message */
        switch (commandId)
        {
            case CMD_ID_RUN_RSA_SAMPLE:
                ret = tlCryptoCatalog_RSASample();
                break;
            case CMD_ID_RUN_DSA_SAMPLE:
                ret = tlCryptoCatalog_DSASample();
                break;
            case CMD_ID_RUN_ECDSA_SAMPLE:
                ret = tlCryptoCatalog_ECDSASample();
                break;
            default:
                /* Unknown command */
                ret = RET_ERR_UNKNOWN_CMD;
                break;
        }

        /* Set response header */
        pTci->message.response.header.responseId = RSP_ID(commandId);
        pTci->message.response.header.returnCode = ret;

        /* Notify the TLC */
        tlApiNotify();
    }
}
