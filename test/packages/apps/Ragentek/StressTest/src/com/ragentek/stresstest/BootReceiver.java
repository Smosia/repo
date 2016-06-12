package com.ragentek.stresstest;

/**
 * receive the broadcast boot completed,and start the activity ControlCenter
 * @author yangyang.liu
 * @date 2015/07/20
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import com.ragentek.stresstest.utils.DebugUtil;

public class BootReceiver extends BroadcastReceiver {

	private static final String TAG = "StressTest";
	private static final boolean DEBUG = true;
	PowerManager pm;
	Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		String action = intent.getAction();
		if (DEBUG) {
			DebugUtil.logd(TAG, "onReceive action:" + action);
		}
		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			SharedPreferences sp = context.getSharedPreferences(
					ControlCenter.SP_FILENAME, Context.MODE_PRIVATE);
			boolean rebootMode = sp.getBoolean(ControlCenter.SKEY_REBOOT_MODE,
					false);
			if (DEBUG) {
				DebugUtil.logd(TAG, "rebootMode:" + action);
			}
			if (rebootMode) {
				startControlCenter();
			}
		}
	}

	/**
	 * start self by start activity
	 */
	private void startControlCenter() {
		Intent intent = new Intent();
		intent.setClass(mContext, ControlCenter.class);
		intent.putExtra(ControlCenter.START_FROM_BOOTRECEIVER, true);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
}
