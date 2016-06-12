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
#include "buildTag.h"
#include "TlApi/TlApi.h"

#include "tlCommon.h"
#include "tlTeeKeymaster_Api.h"

/* Trustlet stack definition */
DECLARE_TRUSTLET_MAIN_STACK(32768)

/* External functions */
extern _NORETURN void tlTciHandler_Loop(
    const addr_t tciBuffer,
    const uint32_t tciBufferLen);

/* Extern data */
extern session_handle_t session_handles[MAX_SESSION_NUM];
extern bool am_big_endian;

/**
 * Trustlet entry function
 */
_TLAPI_ENTRY void tlMain(
    const addr_t tciBuffer,
    const uint32_t tciBufferLen)
{
    tlApiLogPrintf(TATAG"Build %s %s\n", __DATE__, __TIME__);
    tlApiLogPrintf(MOBICORE_COMPONENT_BUILD_TAG EOL);

    /* Check TCI buffer and its length */
    if ((NULL == tciBuffer) || (sizeof(tci_t) > tciBufferLen))
    {
        /* TCI buffer is too small */
        tlApiLogPrintf(TATAG"tlMain(): TCI buffer too small. Exiting..\n");
        tlApiExit(EXIT_ERROR);
    }

    /* Reset session handles */
    memset(&session_handles[0], 0x0, sizeof(session_handles));

    /* Determine endianness of platform. */
    {const int a = 1; am_big_endian = (*((char*)&a) == 0);}

    /* TCI handler loop */
    tlTciHandler_Loop(tciBuffer, tciBufferLen);
}
