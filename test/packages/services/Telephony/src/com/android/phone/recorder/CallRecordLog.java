package com.android.phone.recorder;

import android.util.Log;

public class CallRecordLog {

	private final static String TAG = "AutoCallRecord";

	public CallRecordLog(String msg) {
	}

	public static void d(String msg) {
		Log.d(TAG, msg);

	}

	public static void e(String msg) {
		Log.e(TAG, msg);

	}

	public static void i(String msg) {
		Log.i(TAG, msg);

	}

	public static void v(String msg) {
		Log.v(TAG, msg);

	}

	public static void w(String msg) {
		Log.w(TAG, msg);

	}

}
