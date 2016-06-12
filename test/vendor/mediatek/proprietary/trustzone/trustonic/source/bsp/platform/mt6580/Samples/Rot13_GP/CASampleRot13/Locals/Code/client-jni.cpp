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

#include <string.h>
#include <stdint.h>
#include <jni.h>
#include <android/log.h>

#include "caSampleRot13.h"

#define LOG_TAG "Rot13JavaClient_jni"

// See for more help about JNI:
// http://java.sun.com/docs/books/jni/html/jniTOC.html
// http://java.sun.com/developer/onlineTraining/Programming/JDCBook/jni.html

#ifdef __cplusplus
#define EXTERN_C extern "C"
#else
#define EXTERN_C
#endif


EXTERN_C JNIEXPORT jboolean JNICALL
Java_android_mobicore_CARot13_open(
    JNIEnv *env,
    jobject obj
)
{
    TEEC_Result nResult;
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "calling caOpen()");
    nResult = caOpen();
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "caOpen() returned %s", (nResult == TEEC_SUCCESS) ? "true" : "false");
    return (nResult == TEEC_SUCCESS);
}

EXTERN_C JNIEXPORT jstring JNICALL
Java_android_mobicore_CARot13_rot13(
    JNIEnv *env,
    jobject obj,
    jstring clearText
)
{
    TEEC_Result nResult;
    char *str;
    uint32_t nLength;
    jstring tmp;
    str = (char *) env->GetStringUTFChars(clearText, NULL);
    if (str == NULL) {
        return NULL; /* OutOfMemoryError already thrown */
    }

    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "clearText: %s", str);

    nLength = strlen(str);
    nResult = caRot13(str, nLength, str, &nLength);
    if (nResult == TEEC_SUCCESS) {
        __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "cipherText: %s", str);
        tmp = env->NewStringUTF(str);
    } else {
        tmp = NULL;
    }
    env->ReleaseStringUTFChars(clearText, str);

    return tmp;
}

EXTERN_C JNIEXPORT jint JNICALL
Java_android_mobicore_CARot13_close(
    JNIEnv *env,
    jobject obj
)
{
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "calling caClose()");
    caClose();
    return 0;
}
