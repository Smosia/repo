package com.rgk.pedometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootAndShutdownReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		Log.d(PedometerApplication.TAG, "BootAndShutdownReceiver: [onReceive] action " + action);
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			// start service
			if (SharePreferenceUtils.getEnabled(context)) {
				PedometerApplication.getInstance().startStepService();
			}
		} else if (Intent.ACTION_SHUTDOWN.equals(action)) {

		}

	}

}
