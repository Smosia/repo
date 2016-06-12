package com.android.sales;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private final static String TAG = "guocl";
    public final static String SMS_SEND_ACTION = "com.rgk.SMS_SEND_ACTION";
    public final static String SMS_DELIVERED_ACTION = "com.rgk.SMS_DELIVERED_ACTION";
    
	@Override
	public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "SmsReceiver onReceive >>");
        if (intent == null) {
            return;
        }
        
        String action = intent.getAction();
        Log.d(TAG, "action, " + action);
        if (SMS_SEND_ACTION.equals(action)) {
            Log.d(TAG, "$$$$$ - 1");
            
        } else if (SMS_DELIVERED_ACTION.equals(action)) {
            Log.d(TAG, "$$$$$ - 2");
        }
        Log.d(TAG, "SmsReceiver onReceive <<");
	}
}
