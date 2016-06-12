/*
 * Copyright (C) 2011 The Android Open Source Project
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
package com.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.provider.Settings;

/***
 * some time cann't boot ok , so reset device_provisioned value
 **/
public class ResetDeviceProvisionValue extends BroadcastReceiver {

    private static final String Tag = "ResetDeviceProvidsionValue";
    private static final boolean DBG = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            if(DBG) Log.d(Tag, "begin check device_provisioned value");

            int user_setup_complete = Settings.Secure.getInt(context.getContentResolver(),"user_setup_complete", 0);
            int device_provisioned = Settings.Global.getInt(context.getContentResolver(),"device_provisioned", 0);

            if(DBG) Log.d(Tag, "user_setup_complete="+user_setup_complete + ";device_provisioned=" + device_provisioned);

            if(user_setup_complete == 1 && device_provisioned == 0) {
                if(DBG) Log.d(Tag, "need modify the device_provisioned value to 1");
                Settings.Global.putInt(context.getContentResolver(),"device_provisioned", 1);

                user_setup_complete = Settings.Secure.getInt(context.getContentResolver(),"user_setup_complete", 0);
                device_provisioned = Settings.Global.getInt(context.getContentResolver(),"device_provisioned", 0);
                if(DBG) Log.d(Tag, "set ok => user_setup_complete="+user_setup_complete + ";device_provisioned=" + device_provisioned);
            }
        }
    }
}