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

#include <stdlib.h>
#include <string.h>

#include "tlcFloat.h"
#include "log.h"

static void printFloats(tlcFloat_param_t floats[4]);
static void returnExitCode(int exitCode);
static size_t getFileContent(const char* pPath, uint8_t** ppContent);

/**
 * Test the Floating Points provided by the <t-base environment.
 */
int main(int argc, char *args[]) {

	mcResult_t ret;
    uint8_t* pTAData = NULL;
    uint32_t nTASize;
    tlcFloat_param_t floats[4];

	LOG_i("Copyright (c) Trustonic Limited 2014");

    nTASize = getFileContent(
                        "66700000000000000000000000000000.tlbin",
                        &pTAData);
    if (nTASize == 0) {
        fprintf(stderr, "Trusted Application not found.\n");
		returnExitCode(2);
    }

	ret = tlcOpen(MC_SPID_TRUSTONIC_OTA, pTAData, nTASize);
	if (MC_DRV_OK != ret) {
		LOG_E("open TL session failed!");
        fprintf(stderr, "Could not open Trusted Application session.\n");
        free(pTAData);
		returnExitCode(2);
	}

    memset(floats, 0, sizeof(floats));
	ret = tlcFloatInit(floats);

    if (ret == MC_DRV_OK) {
        printFloats(floats);
    } else {
        fprintf(stderr, "No response from Trusted Application.\n");
        free(pTAData);
        returnExitCode(2);
    }

    floats[0].f = 100.f;
    floats[1].f = 50.f;
    floats[2].f = 0.f;
	ret = tlcFloatAdd(floats);

    if (ret == MC_DRV_OK) {
        printFloats(floats);
    } else {
        fprintf(stderr, "No response from Trusted Application.\n");
        free(pTAData);
        returnExitCode(2);
    }

    floats[0].f = 50.f;
    floats[1].f = 3.f;
    floats[2].f = 0.f;
	ret = tlcFloatMul(floats);

    if (ret == MC_DRV_OK) {
        printFloats(floats);
    } else {
        fprintf(stderr, "No response from Trusted Application.\n");
        free(pTAData);
        returnExitCode(2);
    }

    floats[0].f = 49.f;
    floats[1].f = 0.f;
    floats[2].f = 0.f;
    ret = tlcFloatSqrt(floats);

    if (ret == MC_DRV_OK) {
        printFloats(floats);
    } else {
        fprintf(stderr, "No response from Trusted Application.\n");
        free(pTAData);
        returnExitCode(2);
    }

    // Close tlc. Note that this frees the cipherText/TCI pointer. Do not use this pointer after tlcClose().
    tlcClose();

    free(pTAData);

    returnExitCode(0);
	return 0;
}

static void printFloats(tlcFloat_param_t floats[4]) {
    printf("Floats:\n"
            "\t0: %f - %lf\n"
            "\t1: %f - %lf\n"
            "\t2: %f - %lf\n"
            "\t3: %f - %lf\n",
            floats[0].f, floats[0].d,
            floats[1].f, floats[1].d,
            floats[2].f, floats[2].d,
            floats[3].f, floats[3].d);
}

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

static void returnExitCode(int exitCode) {
    if (0 != exitCode) {
        LOG_e("Failure");
    }
    else {
        LOG_i("Success");
    }
    fprintf(stderr, "TLC exit code: %08x\n", exitCode);
    exit(exitCode);
}
