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

#include "tlkm_device.h"

#include "keymaster_ta_defs.h"

/* specific include to get GP API TEE_GetPropertyAsBinaryBlock() available in legacy Trustlet */
#if (TBASE_API_LEVEL <= 5)
#include "tlkm_device_legacy_API_wrapper.h"
#endif

/* Control whenever we use an dummy stub or if deviceState is based on TEE/Bootloader status */
#define USE_HARDCODED_DEVICE_STATE_STUB 1



keymaster_error_t get_device_state(
    uint8_t device_state[KM_DEVICE_STATE_SIZE])
{
    keymaster_error_t kmError = KM_ERROR_OK;

#if defined (USE_HARDCODED_DEVICE_STATE_STUB)
    /* FIXME This is dummy code. Use data from secure driver. */
    memset(device_state, 0x0C, KM_DEVICE_STATE_SIZE);

#else
    TEE_Result TEEApiError = TLAPI_OK;
    size_t device_state_size = sizeof(device_state);

    TEEApiError = TEE_GetPropertyAsBinaryBlock(TEE_PROPSET_TEE_IMPLEMENTATION, "trustonic.android.deviceState", device_state, &device_state_size);
    //tlApiLogPrintf("************device_state[0,1,2,3]=Ox%x,Ox%x,Ox%x,Ox%x\n",((uint32_t *)device_state)[0],((uint32_t *)device_state)[1],((uint32_t *)device_state)[2],((uint32_t *)device_state)[3]);
    //tlApiLogPrintf("************device_state[4,5,6,7]=Ox%x,Ox%x,Ox%x,Ox%x\n",((uint32_t *)device_state)[4],((uint32_t *)device_state)[5],((uint32_t *)device_state)[6],((uint32_t *)device_state)[7]);
    //tlApiLogPrintf("************ret=Ox%x\n",TEEApiError);

    if (TEEApiError != TLAPI_OK)
    {
        kmError = KM_ERROR_SECURE_HW_ACCESS_DENIED;
    }
#endif

    return kmError;
}
