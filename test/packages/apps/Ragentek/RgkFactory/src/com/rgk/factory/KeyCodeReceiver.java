package com.rgk.factory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class KeyCodeReceiver extends BroadcastReceiver{

	private final String TAG = "KeyCodeReceiver";
	private final String bluOrder = "74655588";
	private final String symphonyOrder = "334766";
	private final String qmobileOrder = "8378464";
	private final String mobiistarOrder = "6600";
	public static boolean customOrder;
	@Override
	public void onReceive(Context context, Intent intent) {		
		String host = intent.getData().getHost();
		Log.i(TAG,"KeyCodeReceiver:host="+host);
		if(host.equals(bluOrder) || host.equals(symphonyOrder) || host.equals(qmobileOrder) || host.equals(mobiistarOrder)) {
			customOrder = true;
		} else {
			customOrder = false;
		}		
		Intent mIntent = new Intent(context, MainActivity.class);
	    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mIntent);
	}

}
