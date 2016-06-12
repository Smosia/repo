package com.rgk.phonemanager.util;

import com.rgk.phonemanager.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

@SuppressLint("NewApi")
public class NotificationUtil {
	private static final int NOTIFICATION_ID_LOW_MEMORY = 123;
	
	private static NotificationUtil mNotificationUtil;
	
	private NotificationUtil() {
		
	}
	
	public static NotificationUtil getUtil() {
		if(mNotificationUtil == null) {
			mNotificationUtil = new NotificationUtil();
		}
		return mNotificationUtil;
	}
	
	public void notifyLowMemory(Context context) {
		Notification.Builder builder = new Notification.Builder(context);
		//Intent intent = new Intent(RAMMonitorService.this, MainActivity.class);
        Intent intent = new Intent();
		ComponentName componentName = new ComponentName(
				"com.rgk.phonemanager",
				"com.rgk.phonemanager.MainActivity");
		intent.setComponent(componentName);
		PendingIntent pendingIntent = PendingIntent.getActivity(
				context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		builder.setContentTitle(context.getResources().getString(R.string.memory_low));
		builder.setContentText(context.getResources().getString(R.string.memory_low));
		builder.setTicker(context.getResources().getString(R.string.memory_low));
		builder.setSmallIcon(R.drawable.noti_low_mem);
		builder.setOngoing(true);
        //builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		Notification notification = builder.build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID_LOW_MEMORY, notification);
	}
	
	public void cancelLowMemory(Context context) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_ID_LOW_MEMORY);
	}
	
}
