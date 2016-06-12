package com.ragentek.com;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CBService extends Service {

    private final static String TAG = "zzb";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
        Log.v(TAG,"============CBService   onCreate===============");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
        Log.v(TAG,"============CBService   onStart===============");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
        Log.v(TAG,"============CBService   onDestroy===============");
	}

}
