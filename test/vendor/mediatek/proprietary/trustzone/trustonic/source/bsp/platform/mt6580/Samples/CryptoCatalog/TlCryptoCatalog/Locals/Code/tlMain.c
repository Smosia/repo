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

#include "tlCryptoCatalog_Common.h"
#include "tlCryptoCatalog_Api.h"

/* Trusted Application heap definition */
DECLARE_TRUSTLET_MAIN_HEAP(32768);

/* Trusted Application stack definition */
DECLARE_TRUSTLET_MAIN_STACK(16384);


/* External functions */
extern _NORETURN void tlTciHandler_Loop(
    const addr_t tciBuffer,
    const uint32_t tciBufferLen);


/**
 * Trusted Application entry function
 */
_TLAPI_ENTRY void tlMain(
    const addr_t tciBuffer,
    const uint32_t tciBufferLen)
{

    /* Check TCI buffer and its length */
    if ((NULL == tciBuffer) || (sizeof(tci_t) > tciBufferLen))
    {
        /* TCI buffer is too small */
        tlApiLogPrintf("[Crypto Catalog] %s(): TCI buffer too small. Exiting...\n", __func__);
        tlApiExit(E_TLAPI_BUFFER_TOO_SMALL);
    }

    /* TCI handler loop */
    tlTciHandler_Loop(tciBuffer, tciBufferLen);
}
