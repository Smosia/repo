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

import java.io.File;
import java.io.IOException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RecoverySystem;
import android.util.Log;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.SystemProperties;
import android.os.Environment;
/***
for d208_lava OTA, copy lava company to here. liangyulong 20130703
***/
public class OTAUpdate extends BroadcastReceiver {

    private static final String Tag = "OTAUpdate";
    private static final boolean OTA_DBG = true;
    private static final String ACTION_START_UPDATE = "com.android.suc.startupdate";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_START_UPDATE.equals(intent.getAction())) {
            if(OTA_DBG) Log.d(Tag, "start update intent received");
            String filePath = intent.getStringExtra("filePath");
            Log.d(Tag, "intent.getStringExtra - filePath = " + filePath);
            if("1".equals(SystemProperties.get("ro.mtk_shared_sdcard"))/* && !isExternalSDCardExist(context)*/){
                filePath = "/data/media/0/"+filePath.substring(filePath.lastIndexOf('/')+1);
                Log.d(Tag, "mtk_shared_sdcard is yes - filePath = " + filePath);
            }
            File upgradeFile = new File(filePath);
            try {
            	if(OTA_DBG) Log.d(Tag, "Reboot the phone");
                RecoverySystem.installPackage(context, upgradeFile);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isExternalSDCardExist(Context context) {
        StorageManager storageManager = StorageManager.from(context);
        StorageVolume[] volumes = storageManager.getVolumeList();
        for (int i = 0; i < volumes.length; i++) {
            if (volumes[i].isRemovable() && Environment.MEDIA_MOUNTED.equals(
                    storageManager.getVolumeState(volumes[i].getPath()))) {
                return true;
            }
        }
        return false;
    }
}

