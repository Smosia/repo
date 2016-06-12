package com.ragentek.wakegesture.receiver;

import android.os.ITpGesture;
import android.os.ServiceManager;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class TpGestureEnableReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		Log.e("WakeGesture","junp into  receiver-----");
		boolean is_enable=(Settings.Global.getInt(context.getContentResolver(),
				Settings.Global.BLACK_GESTURE_WAKE, 0) == 1);
		if(is_enable){
			try {
				Log.e("WakeGesture","reOpen-----");
				ITpGesture tp = ITpGesture.Stub.asInterface(ServiceManager.getService("tpgesture"));
				tp.setTpGestureEnabled(true);	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("WakeGesture","reOpen-----fail");
				Settings.Global.putInt(context.getContentResolver(),
						Settings.Global.BLACK_GESTURE_WAKE, 0);
				e.printStackTrace();
			}	
		}
	}

}
