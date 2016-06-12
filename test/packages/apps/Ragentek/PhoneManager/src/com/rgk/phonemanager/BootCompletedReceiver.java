package com.rgk.phonemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
	private static final String TAG = "BootCompletedReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		if (null == intent) {
			Log.w(TAG, "intent is null");
			return;
		}
		
		String action = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			Intent mIntent = new Intent(context, RAMMonitorService.class);
			context.startService(mIntent);
		}
	}

}
