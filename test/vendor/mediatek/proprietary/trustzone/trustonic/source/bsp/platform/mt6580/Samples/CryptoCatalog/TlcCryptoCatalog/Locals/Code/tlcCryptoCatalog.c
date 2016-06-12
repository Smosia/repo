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

/*
 * @file   tlcCryptoCatalog.c
 * @brief  Crypto catalog Trusted Application Connector main file
 *
 */
#include <stdio.h>

#include "MobiCoreDriverApi.h"
#include "tlCryptoCatalog_Api.h"


#define LOG_TAG "TlcCryptoCatalog"
#include "log.h"

static mcSessionHandle_t   gSessionHandle;


static void returnExitCode(int exitCode)
{
   if (0 != exitCode)
   {
      LOG_E("Failure");
   }
   else
   {
      LOG_I("Success");
   }
   fprintf(stderr, "TLC exit code: %08x\n", exitCode);
}

/**
 * getFileContent is used to read Trusted Application binary data and put it to buffer
 */
static size_t getFileContent(
    const char* pPath,
    uint8_t** ppContent)
{
    FILE*   pStream;
    long    filesize;
    uint8_t* content = NULL;

   /* Open the file */
   pStream = fopen(pPath, "rb");
   if (pStream == NULL)
   {
      fprintf(stderr, "Error: Cannot open file: %s.\n", pPath);
      return 0;
   }

   if (fseek(pStream, 0L, SEEK_END) != 0)
   {
      fprintf(stderr, "Error: Cannot read file: %s.\n", pPath);
      goto error;
   }

   filesize = ftell(pStream);
   if (filesize < 0)
   {
      fprintf(stderr, "Error: Cannot get the file size: %s.\n", pPath);
      goto error;
   }

   if (filesize == 0)
   {
      fprintf(stderr, "Error: Empty file: %s.\n", pPath);
      goto error;
   }

   /* Set the file pointer at the beginning of the file */
   if (fseek(pStream, 0L, SEEK_SET) != 0)
   {
      fprintf(stderr, "Error: Cannot read file: %s.\n", pPath);
      goto error;
   }

   /* Allocate a buffer for the content */
   content = (uint8_t*)malloc(filesize);
   if (content == NULL)
   {
      fprintf(stderr, "Error: Cannot read file: Out of memory.\n");
      goto error;
   }

   /* Read data from the file into the buffer */
   if (fread(content, (size_t)filesize, 1, pStream) != 1)
   {
      fprintf(stderr, "Error: Cannot read file: %s.\n", pPath);
      goto error;
   }

   /* Close the file */
   fclose(pStream);
   *ppContent = content;

   /* Return number of bytes read */
   return (size_t)filesize;

error:
   if (content  != NULL)
   {
      free(content);
   }
   fclose(pStream);
   return 0;

}

/**
 * initSession is used to open session to trustlet
 */
int initSession(tciMessage_ptr *pTci)
{
    uint32_t            deviceId = MC_DEVICE_ID_DEFAULT;
    mcResult_t          mcRet;
    uint8_t*            pTrustletData = NULL;
    uint32_t            nTrustletSize;

    /* Initialize session handle data */
    memset(&gSessionHandle, 0, sizeof(mcSessionHandle_t));

    /* Open <t-base device */
    LOG_I("Opening <t-base device");
    mcRet = mcOpenDevice(deviceId);
    if (MC_DRV_OK != mcRet)
    {
        LOG_E("mcOpenDevice returned: %d\n", mcRet);
        return -1;
    }

    /* Allocating WSM for TCI */
    *pTci = (tciMessage_t*)malloc(sizeof(tciMessage_t));
    if (*pTci == NULL)
    {
        LOG_E("Allocation of TCI failed");
        mcCloseDevice(deviceId);
        return MC_DRV_ERR_NO_FREE_MEMORY;
    }
    memset(*pTci, 0, sizeof(tciMessage_t));

    nTrustletSize = getFileContent("07130000000000000000000000000000.tlbin", &pTrustletData);
    if (nTrustletSize == 0)
    {
        LOG_E("Trusted Application not found");
        free(*pTci);
        *pTci = NULL;
        mcCloseDevice(deviceId);
        return MC_DRV_ERR_TRUSTLET_NOT_FOUND;
    }

    LOG_I("Opening the session");
    memset(&gSessionHandle, 0, sizeof(gSessionHandle));
    gSessionHandle.deviceId = deviceId; // The device ID (default device is used)
    mcRet = mcOpenTrustlet(
            &gSessionHandle,
            MC_SPID_RESERVED_TEST, /* mcSpid_t */
            pTrustletData,
            nTrustletSize,
            (uint8_t *) *pTci,
            sizeof(tciMessage_t));

    // Whatever the result is, free the buffer
    free(pTrustletData);

    if (MC_DRV_OK != mcRet)
    {
        LOG_E("Open session failed: %d", mcRet);
        free(*pTci);
        *pTci = NULL;
        mcCloseDevice(deviceId);
        return -1;
    }
    else
    {
        LOG_I("open() succeeded");
    }

    return MC_DRV_OK;

}

int runCommand(tciMessage_ptr pTci,uint32_t nCommandId)
{
  mcResult_t          mcRet;

  pTci->command.header.commandId = nCommandId;

  /* Notify the trustlet */
  mcRet = mcNotify(&gSessionHandle);
  if (MC_DRV_OK != mcRet)
  {
      LOG_E("mcNotify returned: %d\n", mcRet);
      return mcRet;
  }

  LOG_I("Wait response for command\n");

  /* Wait for notification from Swd */
  if (MC_DRV_OK != mcWaitNotification(&gSessionHandle, MC_INFINITE_TIMEOUT))
  {
      LOG_E("mcWait failed\n");
      return mcRet;
  }

  /* Check the response status set by the TA. */
  if(RET_OK != pTci->response.header.returnCode)
  {
      LOG_E("Sample failed %x\n",pTci->response.header.returnCode);
      return mcRet;
  }
  LOG_I("SUCCESS\n");
  return MC_DRV_OK;
}

/**
 * main function
 */
int main(int argc, char *args[])
{
    mcResult_t          mcRet = MC_DRV_OK;
    tciMessage_ptr      pTci   = NULL;

    LOG_i("Copyright (c) Trustonic Limited 2015");

    if (0 != initSession(&pTci))
    {
        return 0;
    }
    if (argc == 2)
    {
        if (isdigit(args[1][0]))
        {
            int cmd = args[1][0] - '0';

            LOG_I("Run Sample %d\n",cmd);
            mcRet = runCommand(pTci,cmd);
            if (MC_DRV_OK != mcRet)
            {
                goto end;
            }
        }
        else
        {
        printf("Usage: tlcCryptoCatalog <n>\nwith n =\n1 - RSA Sample\n2 - DSA Sample\n3 - ECDSA Sample\n");
        }
    }
    else
    {
        LOG_I("Run RSA Sample\n");
        mcRet = runCommand(pTci, CMD_ID_RUN_RSA_SAMPLE);
        if (MC_DRV_OK != mcRet)
        {
            goto end;
        }
        LOG_I("Run DSA Sample\n");
        mcRet = runCommand(pTci, CMD_ID_RUN_DSA_SAMPLE);
        if (MC_DRV_OK != mcRet)
        {
            goto end;
        }
        LOG_I("Run ECDSA Sample\n");
        mcRet = runCommand(pTci, CMD_ID_RUN_ECDSA_SAMPLE);
        if (MC_DRV_OK != mcRet)
        {
            goto end;
        }
    }

end:
    free(pTci);
    returnExitCode(mcRet);

    return 0;
}
