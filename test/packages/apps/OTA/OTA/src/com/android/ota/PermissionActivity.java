
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
package com.android.ota;

import android.app.Activity;
import android.app.ListActivity;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.widget.Toast;
import android.util.Log;
import android.os.Bundle;
import java.util.List;
import java.util.ArrayList;
import android.content.Intent;

/**
 * Permission handling activity for dream service.
 */
public class PermissionActivity extends Activity {

    private static final String TAG = "PermissionActivity";
    private static final int REQUEST_CODE = 2;
    private static final int RESULT_OK = 3;
    private static final int RESULT_CANCEL = 4;

    //public static final String FIRST_TIME_ENTER="first_enter";
    private Intent mIntent;
    //private List<String> permissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        mIntent = new Intent();
        //permissionList = new ArrayList<String>();
        
        int hasReadPhoneStatePermission = getApplicationContext()
                        .checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        int hasWriteStoragePermission = getApplicationContext()
                        .checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        boolean canReadPhoneState = hasReadPhoneStatePermission == PackageManager.PERMISSION_GRANTED;
        boolean canWriteStorage = hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED;

        Log.i("kayun", "Permission canReadPhoneState = " + canReadPhoneState 
                                + ", canWriteStorage = " + canWriteStorage);

        String[] permissions = null;
        if (!canReadPhoneState && !canWriteStorage) {
            permissions = new String[]{Manifest.permission.READ_PHONE_STATE, 
                                       Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else if(!canReadPhoneState) {
            permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
        } else if(!canWriteStorage) {
            permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else {
        }

        //String[] permissions =  (String[]) permissionList.toArray();

        if (permissions != null && permissions.length != 0) {
            Log.d("kayun", "We need to ask for permissions now.");
            this.requestPermissions(permissions, 1);
        }else{
            // permission has been granted, continue as usual
            Log.d("kayun", "We do not need to ask for permissions now.");
            setResult(RESULT_OK,mIntent);
            finish();
        }
        
        /*
        if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            Log.d("kayun", "We need to ask for permissions now.");
            this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }else{
            // permission has been granted, continue as usual
            Log.d("kayun", "We do not need to ask for permissions now.");
            setResult(RESULT_OK,mIntent);
            finish();
        }
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        switch (requestCode) {
            case 1:
                /*
                if (permissions.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("kayun", "Permission granted.");
                    setResult(RESULT_OK, mIntent);
                }else{
                    Log.d("kayun", "Permission denied.");
                    setResult(RESULT_CANCEL,mIntent);
                }
                */
                boolean isGranted = true;
                for(int i = 0;i < grantResults.length;i++){
                    isGranted = isGranted && (grantResults[i] == PackageManager.PERMISSION_GRANTED);
                    if(!isGranted){
                        Log.d("kayun", "request permissions is not granted.");
                        break;
                    }
                }
                if (permissions.length != 0 && isGranted){
                    Log.d("kayun", "Permission granted.");
                    setResult(RESULT_OK, mIntent);
                }else{
                    Log.d("kayun", "Permission denied.");
                    setResult(RESULT_CANCEL,mIntent);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
        finish();
    }
}

