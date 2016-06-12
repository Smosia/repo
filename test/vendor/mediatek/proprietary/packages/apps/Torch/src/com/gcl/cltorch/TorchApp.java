package com.gcl.cltorch;

import android.app.Application;

public class TorchApp extends Application {

	
        private static final String TAG = "CLTorch";
        public static boolean clean = false;
        
	@Override
	public void onCreate() {
		UtilLog.logd(TAG, "TorchApp onCreate");
                clean = true;
	}
}
