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

package com.trustonic.tsdk.sample;

import android.util.Log;

public class Constants {
    // Log tag.
    public static final String LOG_TAG = "TSdkSample";
    // Show progress indicator (to disable if running on QEMU).
    public static final boolean PROGRESS_INDICATOR_ENABLED = true;
    public static final String MC_SYSTEM_REGISTRY_PATH = "/system/app/mcRegistry";
    public static final String MC_REGISTRY_PATH = "/data/app/mcRegistry";
    public static final String ROT13_CONT_FILE = "04010000000000000000000000000000.A2000000.tlcont";
    public static final String AES_CONT_FILE = "07020000000000000000000000000000.A2000000.tlcont";
    public static final String RSA_CONT_FILE = "07040000000000000000000000000000.A2000000.tlcont";
    public static final String SHA256_CONT_FILE = "06010000000000000000000000000000.A2000000.tlcont";

}

