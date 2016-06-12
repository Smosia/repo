package com.ragentek.stresstest.utils;

/**
 * utils for debug
 * @author yangyang.liu
 * @date 2015/07/24
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class DebugUtil {

	/**
	 * show toast
	 * 
	 * @param context
	 * @param s
	 */
	public static void longToast(Context context, Object s) {
		if (s == null)
			return;
		Toast.makeText(context, s + "", Toast.LENGTH_LONG).show();
	}

	/**
	 * show toast
	 * 
	 * @param context
	 * @param s
	 */
	public static void shortToast(Context context, Object s) {
		if (s == null)
			return;
		Toast.makeText(context, s + "", Toast.LENGTH_SHORT).show();
	}

	/**
	 * print e log
	 * 
	 * @param TAG
	 * @param e
	 */
	public static void loge(String TAG, Object e) {
		if (e == null)
			return;
		Thread mThread = Thread.currentThread();
		StackTraceElement[] mStackTrace = mThread.getStackTrace();
		String mMethodName = mStackTrace[3].getMethodName();
		e = "[" + mMethodName + "] " + e;
		Log.e(TAG, e + "");
	}

	/**
	 * print d log
	 * 
	 * @param TAG
	 * @param s
	 */
	public static void logd(String TAG, Object s) {
		Thread mThread = Thread.currentThread();
		StackTraceElement[] mStackTrace = mThread.getStackTrace();
		String mMethodName = mStackTrace[3].getMethodName();

		s = "[" + mMethodName + "] " + s;
		Log.d(TAG, s + "");
	}
}
