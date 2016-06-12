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

#include "tlStd.h"
#include "TlApi/TlApi.h"


#include "tlCommon.h"
#include "tlCryptoHandler.h"
#include "tlTeeKeymaster_Api.h"


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
            case CMD_ID_TEE_RSA_GEN_KEY_PAIR:
                /* Generate RSA key pair */
                ret = tlCryptoHandler_RSAGenerateKeyPair(pTci);
                break;
            case CMD_ID_TEE_RSA_SIGN:
                /* RSA sign */
                ret = tlCryptoHandler_RSASign(pTci);
                break;
            case CMD_ID_TEE_RSA_VERIFY:
                /* RSA verify */
                ret = tlCryptoHandler_RSAVerify(pTci);
                break;
            case CMD_ID_TEE_KEY_IMPORT:
                /* Key wrap */
                ret = tlCryptoHandler_KeyImport(pTci);
                break;
            case CMD_ID_TEE_GET_PUB_KEY:
                /* Key unwrap */
                ret = tlCryptoHandler_GetPubKey(pTci);
                break;
            case CMD_ID_TEE_DSA_GEN_KEY_PAIR:
                /* Generate DSA key pair */
                ret = tlCryptoHandler_DSAGenerateKeyPair(pTci);
                break;
            case CMD_ID_TEE_DSA_SIGN:
                /* DSA sign */
                ret = tlCryptoHandler_DSASign(pTci);
                break;
            case CMD_ID_TEE_DSA_VERIFY:
                /* DSA verify */
                ret = tlCryptoHandler_DSAVerify(pTci);
                break;
            case CMD_ID_TEE_ECDSA_GEN_KEY_PAIR:
                /* Generate ECDSA key pair */
                ret = tlCryptoHandler_ECDSAGenerateKeyPair(pTci);
                break;
            case CMD_ID_TEE_ECDSA_SIGN:
                /* ECDSA sign */
                ret = tlCryptoHandler_ECDSASign(pTci);
                break;
            case CMD_ID_TEE_ECDSA_VERIFY:
                /* ECDSA verify */
                ret = tlCryptoHandler_ECDSAVerify(pTci);
                break;
            case CMD_ID_TEE_GET_KEY_INFO:
                /* Get key info */
                ret = tlCryptoHandler_GetKeyInfo(pTci);
                break;
            default:
                /* Unknown command */
                tlApiLogPrintf("[TEE Keymaster] tlTciHandler_Loop(): Received unknown command id (%d)\n",
                        commandId);
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
