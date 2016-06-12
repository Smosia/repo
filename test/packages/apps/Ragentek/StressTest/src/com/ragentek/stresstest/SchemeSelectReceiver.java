package com.ragentek.stresstest;

/**
 * SchemeSelectReceiver,receive the command of *#*#8#*#*
 * @author yangyang.liu
 * @date 2015/07/22
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SchemeSelectReceiver extends BroadcastReceiver {

	public final static String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SECRET_CODE_ACTION)) {
			Intent newIntent = new Intent(Intent.ACTION_MAIN);
			newIntent.setClass(context, ControlCenter.class);
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(newIntent);
		}
	}
}
