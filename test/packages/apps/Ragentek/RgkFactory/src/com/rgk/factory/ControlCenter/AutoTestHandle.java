package com.rgk.factory.ControlCenter;
import com.rgk.factory.CitTestResult;
import com.rgk.factory.Config;
import com.rgk.factory.MainActivity;
import com.rgk.factory.Util;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.rgk.factory.R;

public class AutoTestHandle extends BroadcastReceiver {

	private static String TAG = "AutoTestHandle";
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String mTestItem = intent.getStringExtra("test_item");
		Log.i(TAG, "action="+action+" test_item="+mTestItem);
		if(action.equals(Config.ACTION_START_AUTO_TEST) && MainActivity.mAutoTest) {
			autoTest(context,mTestItem);
		} else if(action.equals(Config.ACTION_AUTO_BACKTEST) && MainActivity.mAutoBackTest) {
			autoBacktest(context,mTestItem);
		}
	}
	public static void autoTest(Context context, String testItem) {
		if(!MainActivity.mAutoTest) return;
		int i = 0;
		int count = Util.autoTestList.size();
		Log.i(TAG, "autoTest():test_item="+testItem);
		for (i = 0; i < count; i++) {
			if (testItem.equals(Util.autoTestList.get(i))) {
				i++;
				break;
			}
		}
		if(testItem.equals(MainActivity.TAG)) {
			i = 0;
		}
		if (i == count) {
			i = 0;
			MainActivity.mAutoTest = false;
			context.startActivity((new Intent(context,CitTestResult.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
		} else {
			context.startActivity((new Intent()).setClass(context,Util.autoTestClass.get(i)).setFlags(
					Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
	public static void autoBacktest(Context context, String testItem) {
		if(!MainActivity.mAutoBackTest) return;
		int i = 0;
		int count = Util.backtestList.size();
		Log.i(TAG, "autoBacktest():test_item="+testItem);
		for (i = 0; i < count; i++) {
			if (testItem.equals(Util.backtestList.get(i))) {
				i++;
				break;
			}
		}
		if(testItem.equals(MainActivity.TAG)) {
			i = 0;
		}
		if (i == count) {
			i = 0;
			MainActivity.mAutoBackTest = false;
		} else {
			context.startService((new Intent()).setClass(context, Util.backtestClass.get(i)).setFlags(
					Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
}