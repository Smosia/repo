package com.rgk.phonemanager;

import com.rgk.phonemanager.util.NotificationUtil;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class RAMMonitorService extends Service {
	private static final String TAG = "RAMMonitorService";

	private static final int MSG_CHECK_RAM = 1001;
	private static final long MEMORY_LOW = 100 * 1024 * 1024;
	private static final int NOTIFICATION_ID = 123;

	private MemoryInfo memoryInfo = new MemoryInfo();

	private Handler mHandler = new TimeHandler();

	private class TimeHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_CHECK_RAM:
				if (isLowMemory()) {
					NotificationUtil.getUtil().notifyLowMemory(RAMMonitorService.this);
				} else {
					NotificationUtil.getUtil().cancelLowMemory(RAMMonitorService.this);
				}
				sendEmptyMessageDelayed(MSG_CHECK_RAM, 60000);
				break;

			default:
				break;
			}
		}
	}

	private boolean isLowMemory() {
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.getMemoryInfo(memoryInfo);
		Log.d(TAG, "memoryInfo.availMem=" + memoryInfo.availMem);
		if (memoryInfo.availMem > MEMORY_LOW) {
			return false;
		}
		return true;
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		mHandler.sendEmptyMessage(MSG_CHECK_RAM);
		return super.onStartCommand(intent, flags, startId);
	}
}
