/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.phone;

import android.app.Application;
import android.content.res.Configuration;
import android.os.UserHandle;

import com.android.services.telephony.TelephonyGlobals;

//add DWYQLSSMY-890 songqingming 20160504 (start)
import android.os.SystemProperties;
import android.os.Handler;
import android.os.Message;
import android.os.AsyncResult;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import android.util.Log;
import android.content.Context;
import android.content.SharedPreferences;
//add DWYQLSSMY-890 songqingming 20160504 (end)

/**
 * Top-level Application class for the Phone app.
 */
public class PhoneApp extends Application {
    PhoneGlobals mPhoneGlobals;
    TelephonyGlobals mTelephonyGlobals;

    //add DWYQLSSMY-890 songqingming 20160504 (start)
    private static final int MSG_SET = 1;
    private static final int ID_GSM =
            "1".equals(SystemProperties.get("ro.mtk_lte_support")) ? 59 : 8;
    private static final int ID_UMTS =
            "1".equals(SystemProperties.get("ro.mtk_lte_support")) ? 60 : 9;
    private static final String CMD_SET_GSM = "AT+ESBP=1," + ID_GSM + ",";
    private static final String CMD_SET_UMTS = "AT+ESBP=1," + ID_UMTS + ",";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            AsyncResult ar;
            switch (msg.what) {
            case MSG_SET:
                ar = (AsyncResult) msg.obj;
                if (ar.exception != null) {
                    Log.e("sqm", "PhoneApp-----set AMR-WB failed: " + ar.exception.getMessage());
                } else {
                    Log.d("sqm", "PhoneApp-----set AMR-WB success.");
                }
                break;
            default:
                break;
            }
        }
    };
    //add DWYQLSSMY-890 songqingming 20160504 (end)

    public PhoneApp() {
    }

    @Override
    public void onCreate() {
        if (UserHandle.myUserId() == 0) {
            // We are running as the primary user, so should bring up the
            // global phone state.
            mPhoneGlobals = new PhoneGlobals(this);
            mPhoneGlobals.onCreate();

            mTelephonyGlobals = new TelephonyGlobals(this);
            mTelephonyGlobals.onCreate();

            //add DWYQLSSMY-890 songqingming 20160504 (start)
            if (getResources().getBoolean(R.bool.def_on_amr_wb_config)) {
                SharedPreferences sp = getSharedPreferences("amr_wb_config", Context.MODE_PRIVATE);
                if (!sp.getBoolean("amr_wb_on", false)) {
                    sendAtCommand(new String[] {CMD_SET_GSM + 1, ""}, MSG_SET);
                    sendAtCommand(new String[] {CMD_SET_UMTS + 1, ""}, MSG_SET);
            	    sp.edit().putBoolean("amr_wb_on", true).apply();
                }
            }
            //add DWYQLSSMY-890 songqingming 20160504 (end)
        }
    }

    //add DWYQLSSMY-890 songqingming 20160504 (start)
    private void sendAtCommand(String[] command, int msg) {
        Log.d("sqm", "PhoneApp-----sendAtCommand() " + command[0]);
        Phone phone = PhoneFactory.getDefaultPhone();
        if (phone != null) {
            phone.invokeOemRilRequestStrings(command, mHandler.obtainMessage(msg));
        }
    }
    //add DWYQLSSMY-890 songqingming 20160504 (end)
}
