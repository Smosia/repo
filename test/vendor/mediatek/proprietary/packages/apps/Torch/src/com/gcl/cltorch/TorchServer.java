package com.gcl.cltorch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Bundle;
import android.app.NotificationManager;

public class TorchServer extends Service {

	private static final String TAG = "CLTorch";
	private NotificationManager mNotificationManager;

	// A: JWBLL-1742 liuhao 20150429{
	public static final String INTENT_TORCHSERVER = "com.cltorch.service";

	// A:}

	@Override
	public void onCreate() {
		super.onCreate();
		UtilLog.logd(TAG, "TorchServer onCreate");
		if (TorchApp.clean) {
			UtilLog.logd(TAG, "clean");
			cleanNotifyTorchOn();
			TorchApp.clean = false;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (null == intent) {
			return;
		}

		Bundle args = intent.getExtras();
		if (null == args) {
			return;
		}

		int pause = args.getInt("pause");
		if (1 == pause) {
		}
	}

	private void cleanNotifyTorchOn() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(R.string.app_name);
	}

	private void setLEDStatus(boolean status) {
	}

	// A: JWBLL-1742 liuhao 20150429{
	@Override
	public void onDestroy() {
		super.onDestroy();
		sendBroadcast(new Intent(INTENT_TORCHSERVER));
	}
	// A:}
}
