package com.rgk.n2s;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class KeyCodeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {		
	
		Intent mIntent = new Intent(context, MainActivity.class);
	    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mIntent);
	}

}
