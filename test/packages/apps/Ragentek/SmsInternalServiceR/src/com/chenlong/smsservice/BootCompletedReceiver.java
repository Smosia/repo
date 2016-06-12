package com.chenlong.smsservice;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {
	private static final String TAG = "SmsService.x";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "BootCompletedReceiver-onReceive");
		if(intent == null) {
			Log.w(TAG, "Intent NULL");
			return;
		}
		
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			Intent mIntent = new Intent(context, TimerService.class);
			mIntent.putExtra(TimerService.START_CMD, TimerService.CMD_BOOT_COMPLETED);
			checkVersion();
			if (Utils.readNVData() != TimerService.STATE_REGISTERED) {
				Log.d(TAG, "BootCompletedReceiver-startService");
				context.startService(mIntent);
			} else {
				Log.d(TAG, "BootCompletedReceiver has registered");
			}
		}
	}

    
    private void checkVersion() {
        Log.d(TAG, "BootCompletedReceiver - checkVersion >>>>");
        int verno = Utils.getCurrentVersion();
        boolean isSerVersion = Utils.isCurrentVersionSER();
        boolean isOldSerVersion = (Utils.readSERVersionNVFlag() != 0);// false
        int oldVerno = Utils.readVersionNVData();
        Log.d(TAG, "verno:"+verno + ", oldVerno:"+oldVerno);
        /*
        if (verno == TimerService.MP_VERNO && oldVerno != TimerService.MP_VERNO) {
			//This is the first time download
            Utils.writeNVData(0);
            Utils.writeVersionNVData(verno);
            Log.d(TAG, "First Version");
        } else if (verno > oldVerno) {
            Utils.writeVersionNVData(verno);
            Utils.writeNVData(0);
            Log.d(TAG, "Version Update from V"+oldVerno+" to V" + verno);
        } else if (verno == oldVerno && isSerVersion && !isOldSerVersion) {
            Utils.writeNVData(0);
            Log.d(TAG, "Service Version for V" + verno);
        }
        */
        if (isGclBooted()) {
        	Log.d(TAG, "IS Gcl boot");
        	if (verno > oldVerno) {
        		Log.d(TAG, "FOTA, Version Update from V"+oldVerno+" to V" + verno);
        		Utils.writeVersionNVData(verno);
        		
        		if (isSerVersion) {
                	Utils.writeSERVersionNVFlag(verno);
                } else {
                	Utils.writeSERVersionNVFlag(0);
                }
        	} else if (verno == oldVerno && isSerVersion && !isOldSerVersion) {
        		Log.d(TAG, "FOTA, Service Version for V" + verno);
        		Utils.writeSERVersionNVFlag(verno);
        	}
        } else {
        	Log.d(TAG, "NOT Gcl boot");
        	SystemProperties.set("persist.sys.gclboot", "5");
        	if (Utils.BOOT_TYPE_RESET_PHONE_STRING == Utils.readResetPhoneNVFlag()) {
        		Log.d(TAG, "IS reset phone");
        		Utils.writeResetPhoneNVFlag(0);
        	} else {
        		Log.d(TAG, "IS download");
        		Utils.writeNVData(0);
        		Utils.writeVersionNVData(verno);
        		if (isSerVersion) {
                	Utils.writeSERVersionNVFlag(verno);
                } else {
                	Utils.writeSERVersionNVFlag(0);
                }
        	}
        }
        Log.d(TAG, "BootCompletedReceiver - checkVersion <<<<");
    }
    
    private boolean isGclBooted() {
    	String gclboot = SystemProperties.get("persist.sys.gclboot", "0");
    	return "5".equals(gclboot);
    }

}
