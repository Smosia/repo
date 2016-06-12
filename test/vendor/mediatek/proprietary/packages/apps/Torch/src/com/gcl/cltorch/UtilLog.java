package com.gcl.cltorch;

import android.util.Log;

public class UtilLog {
	
	public static boolean SHOW_LOGS = true;
	
    public static void logv(String tag, String message) {
    	if (SHOW_LOGS)
    	Log.v(tag, message);
    }
    
    public static void logd(String tag, String message) {
    	if (SHOW_LOGS)
    	Log.d(tag, message);
    }
    
    public static void logi(String tag, String message) {
    	if (SHOW_LOGS)
    	Log.i(tag, message);
    }
    
    public static void logw(String tag, String message) {
    	if (SHOW_LOGS)
    	Log.w(tag, message);
    }
    
    public static void loge(String tag, String message) {
    	if (SHOW_LOGS)
    	Log.e(tag, message);
    }
}
