package com.rgk.factory;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import android.os.SystemProperties;
import java.io.IOException;


public class BootCompletedReceiver extends BroadcastReceiver {

	private int selectedLocation;

	@Override
	public void onReceive(Context context, Intent intent) {
		selectedLocation = Settings.Global.getInt(context.getContentResolver(),
                "android_adb_open", 0);
		if(selectedLocation==1){
			Settings.Global.putInt(context.getContentResolver(),
	                "android_adb_open", 0);
		}
		
		
		new Util(context).initValue();
		int nv=NvRAMAgentHelper.readNVData(298); 
		Log.d("huang", "nv="+nv);
		if(nv==1){
			toRoot(context);
			
			clossAppPermission(context);
		}
	}
	
	@SuppressLint("InlinedApi")
	private void toRoot(Context context) {
		int dev_enable=Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
		if(dev_enable==0){
			Settings.Global.putInt(context.getContentResolver(),
	                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 1);
		}
		int adbEnabled = Settings.Global.getInt(
				context.getContentResolver(), Settings.Global.ADB_ENABLED, 0);
		if(adbEnabled==0){
			Settings.Global.putInt(
					context.getContentResolver(), Settings.Global.ADB_ENABLED, 1);
		}
		
			Settings.Global.putInt(context.getContentResolver(),
	                "android_adb_open", 1);
	}

	private void clossAppPermission(Context context){
		/* Settings.System.getInt(context.getContentResolver(),
	                "permission_control_attached", 0);*/
		Settings.System.getInt(context.getContentResolver(),
				"permission_control_state", 0);
		/*Settings.System.putInt(context.getContentResolver(),
				"permission_control_attached", 0);*/
		Settings.System.putInt(context.getContentResolver(),
				"permission_control_state", 0);
	}
	}
	
	
