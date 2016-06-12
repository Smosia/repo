package com.android.settings;

import android.os.Build;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;

public class NVUtils {
    private static final String TAG = "SmsService.x";
    
    private static final int AP_CFG_REEB_PRODUCT_NEW_INFO_LID = 71;
    private static final int NV_RESET_PHONE_INDEX = 262;
    
    public static final int BOOT_TYPE_RESET_PHONE_STRING = 5;

	public static int writeResetPhoneNVFlag(int indexValue) {
        byte bLoop;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read
				buff[NV_RESET_PHONE_INDEX] = (byte) indexValue;
			} catch (Exception e) {
				e.printStackTrace();
			}
			int flag = 0;
			try {
				flag = agent.writeFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID, buff);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "Settings-Utils > writeResetPhoneNVFlag, " + flag);
			return flag;
		}
        Log.w(TAG, "Settings-Utils > writeResetPhoneNVFlag, return 0 ");
		return 0;
	}
    
}
