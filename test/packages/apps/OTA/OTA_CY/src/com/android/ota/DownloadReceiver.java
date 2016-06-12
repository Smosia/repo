package com.android.ota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.ota.UpdateSystem.Status;

public class DownloadReceiver extends BroadcastReceiver {
	private static final String TAG = "DownloadReceiver";
	@Override
	public void onReceive(Context ctx, Intent intent) {
		String action = intent.getAction();
		if (NotificationDownloadService.DOWNLOAD_SERVICE.equals(action)) {
			final String processDoneStr = ActivityUtils.getParameter(ctx, Status.TAG);
	    	ActivityUtils.setParameter(ctx, Status.TAG,processDoneStr);
	    	Log.d(TAG, "Action:{"+NotificationDownloadService.DOWNLOAD_SERVICE+"}"+"status"+processDoneStr);
		}
		if(NotificationDownloadService.PROGRESS_BAR_STATE.equals(action)){
			final String sizeStr = ActivityUtils.getParameter(ctx,"progress");
			ActivityUtils.setParameter(ctx, "progress", sizeStr);
			final String maxStr = ActivityUtils.getParameter(ctx,"progressMax");
			ActivityUtils.setParameter(ctx, "progressMax", maxStr);
			final String processDoneStr = ActivityUtils.getParameter(ctx, Status.TAG);
			ActivityUtils.setParameter(ctx, Status.TAG, processDoneStr);
		}
	}
}
