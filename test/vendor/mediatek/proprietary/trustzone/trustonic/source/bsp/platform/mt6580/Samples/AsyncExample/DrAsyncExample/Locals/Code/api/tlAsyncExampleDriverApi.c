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

/**
 * @file   tlAsyncExampleDriver.c
 * @brief  Implements driver APIs
 *
 * The APIs allow trustlets to make requests to the driver
 *
 */


#include "tlAsyncExampleDriverApi.h"
#include "drApiMarshal.h"

/** tlApi function to call driver via IPC.
 * Sends a MSG_RQ message via IPC to a MobiCore driver.
 *
 * @param driverID The driver to send the IPC to.
 * @param payload MPointer to marshaling parameters to send to the driver.
 * @param payloadSize the size of the payload that will be sent to the driver
 *
 * @return TLAPI_OK
 * @return E_TLAPI_COM_ERROR in case of an IPC error.
 */
_TLAPI_EXTERN_C tlApiResult_t tlApi_callDriverEx(
        uint32_t driver_ID,
        void* payload,
        uint32_t payloadSize);
/**
 * Open session to the driver with given data
 *
 * @return  session id
 */

_TLAPI_EXTERN_C uint32_t tlApi_Async_OpenSession()
{
    uint32_t sid = DR_SID_INVALID;

    drMarshalingParam_t marParam = {
            .header.functionId = FID_DR_OPEN_SESSION,
    };

    if (TLAPI_OK == tlApi_callDriverEx(SAMPLE_DR_ID, (void*)&marParam, sizeof(marParam.header)
                                                                + sizeof(marParam.payload.params)
                                                                + sizeof(marParam.sid)))
    {
        /* Retrieve session id provided by the driver */
        sid = marParam.sid;
    }

    return sid;
}


/**
 * Close session
 *
 * @param sid  session id
 *
 * @return  TLAPI_OK upon success or specific error
 */
_TLAPI_EXTERN_C tlApiResult_t tlApi_Async_CloseSession(
        uint32_t sid)
{
    drMarshalingParam_t marParam = {
            .header.functionId = FID_DR_CLOSE_SESSION,
            .sid = sid
    };

    tlApiResult_t ret = tlApi_callDriverEx(SAMPLE_DR_ID, (void*)&marParam, sizeof(marParam.header)
                                                                    + sizeof(marParam.payload.params)
                                                                    + sizeof(marParam.sid));
    return ret;
}

/**
 * Run test notifications
 *
 * @param sid                     session id
 * @param countOfNotifications    expected count of notifications
 * @param intervalOfNotifications delay between notifications
 *
 * @return  TLAPI_OK upon success or specific error
 */

_TLAPI_EXTERN_C tlApiResult_t tlApi_Async_Start(
        uint32_t sid)
{
    drMarshalingParam_t marParam = {
            .header.functionId = FID_DR_EXECUTE,
            .sid        = sid,

    };
    tlApiResult_t ret = tlApi_callDriverEx(SAMPLE_DR_ID, (void*)&marParam, sizeof(marParam.header)
                                                                    + sizeof(marParam.payload.params)
                                                                    + sizeof(marParam.sid));
    return ret;
};

/** Notification query
 *
 * @param sid        session id
 *
 * @return  Count of notifications since previous query
 */
_TLAPI_EXTERN_C uint32_t tlApi_Async_NotificationQuery(
        uint32_t sid)
{
    uint32_t count = 0;
    drMarshalingParam_t marParam = {
            .header.functionId = FID_DR_ASK_NOT,
            .sid        = sid
    };

    count = tlApi_callDriverEx(SAMPLE_DR_ID, (void*)&marParam, sizeof(marParam.header)
                                                        + sizeof(marParam.payload.params)
                                                        + sizeof(marParam.sid));

    return count;
};





