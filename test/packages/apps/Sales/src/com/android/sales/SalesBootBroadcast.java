package com.android.sales;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.telephony.SubscriptionManager;

public class SalesBootBroadcast extends BroadcastReceiver {
	private String TAG = "SalesBootBroadcast";
	private static PendingIntent sender = null;
	private Context mContext;
	private SalesUtil salesUtil;
	private TelephonyManager tm;
	private AlarmManager am;

	@Override
	public void onReceive(Context context, Intent intent) {
		salesUtil = new SalesUtil(context);
		String action = intent.getAction();
		mContext = context;
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			setAlarm();
		}
		if (action.equals("com.android.sales.count.start.time")) {
			Log.d(TAG, "send SMS to register");
			if (tm.getSimState() != TelephonyManager.SIM_STATE_READY || tm.getCellLocation() == null) {
				return;
			}
			String imei1 = Params.getParams(context, 0).getIMEI();
			String imei2 = Params.getParams(context, 1).getIMEI();
			String lac = Params.getParams(context, 0).getLAC();
			String cellID = Params.getParams(context, 0).getCellID();
			String modelName = SystemProperties.get("ro.product.model", "")
					.replace(" ", "").replace("QMobile", "").trim();
			String trackingName = "NOIR IMEI " + modelName + " " + imei1 + " "
					+ imei2 + " " + lac + " " + cellID;
			SmsManager sm = SmsManager.getDefault();
            int subId = SubscriptionManager.getDefaultSmsSubId();
			if(subId < 0){
                int[] list = SubscriptionManager.from(context).getActiveSubscriptionIdList();
                if(list != null && list.length > 0){
                    subId = list[0];
                }
            }
            if(subId > 0){
			    sm.sendTextMessage(subId, salesUtil.getServer(), null, trackingName, null,
					    null);
			    ActivityUtils.writeNVData(253, 1);
			    am.cancel(sender);
            }
		}
	}

	private void setAlarm() {
		Log.d(TAG, "BOOT start");
		// if there is no SIM card, return
		if (tm.getSimState() != TelephonyManager.SIM_STATE_READY) {
			return;
		}
		if (!salesUtil.getMode()) {
			return;
		}
		if (salesUtil.getBlockMCC460()) {
			if (tm.getSimOperator().startsWith("460")) {
				return;
			}
		}
		// if the user has registered, return
		int register = ActivityUtils.whetherRegister();
		if (register == 1) {
			return;
		}
		boolean needShow = salesUtil.getFirstBootWithSIM();
		Log.d(TAG, "needShow:" + needShow);
		if (needShow) {
			Log.d(TAG, "register=" + register);
			Intent showDialog = new Intent("com.android.sales.RegisgerDialog");
			showDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(showDialog);
			salesUtil.setFirstBootWithSIM(false);
		}
		Log.d(TAG, "Ready to send SMS three hours later");
		Intent amintent = new Intent(mContext, SalesBootBroadcast.class);
		amintent.setAction("com.android.sales.count.start.time");
		if (sender == null)
			sender = PendingIntent.getBroadcast(mContext, 0, amintent, 0);
		long interval = salesUtil.getTime();
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval,
				sender);
	}

}
