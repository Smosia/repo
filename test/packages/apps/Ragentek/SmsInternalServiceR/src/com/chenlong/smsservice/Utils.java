package com.chenlong.smsservice;

import android.os.Build;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;

public class Utils {
    private static final String TAG = "SmsService.x";
    
    private static final int AP_CFG_REEB_PRODUCT_NEW_INFO_LID = 71;
    private static final int SIZE = 4;
    private static final int VERSION_INDEX = 3;
    
    private static final int NV_INDEX = 256;
    private static final int NV_VERSION_INDEX = 260;
    private static final int NV_SER_VERSION_INDEX = 261;
    private static final int NV_RESET_PHONE_INDEX = 262;
    
    public static final int BOOT_TYPE_RESET_PHONE_STRING = 5;
    
    public static int readNVData() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		int b = 0;
        byte bLoop;
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read
				// buffer from nvra
				if (buff != null) {
                    for (int i = 0; i < SIZE; i++) {
                        bLoop = buff[NV_INDEX+i];
                        b += (bLoop & 0xFF) << (8 * i);
                    }
					//b = buff[NV_INDEX];
					if (b == -1)
						b = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.d(TAG, "Utils > readNVData, " + b);
		return b;
	}

	// Write data to nvram
	public static int writeNVData(int indexValue) {
        byte bLoop;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read 71
                for (int i = 0; (i < 4) && (i < SIZE); i++) {           //size = 4 , 0,1,2,3
                    bLoop =  (byte) ((indexValue >> (8 * i)) & 0xFF);
                    buff[NV_INDEX+i] = bLoop;                           //256,257,258,259 write(0) : 0 0 0 0
                }
				//buff[NV_INDEX] = (byte) indexValue;
			} catch (Exception e) {
				e.printStackTrace();
			}
			int flag = 0;
			try {
				flag = agent.writeFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID, buff);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "Utils > writeNVData, " + flag);
			return flag;
		}
        Log.w(TAG, "Utils > return 0 ");
		return 0;
	}

    public static int readVersionNVData() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		int b = 0;
        byte bLoop;
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read
				// buffer from nvra
				if (buff != null) {
					b = buff[NV_VERSION_INDEX];
					if (b == -1)
						b = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.d(TAG, "Utils > readVersionNVData, " + b);
		return b;
	}
	// Write data to nvram
	public static int writeVersionNVData(int indexValue) {
        byte bLoop;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read 71
				buff[NV_VERSION_INDEX] = (byte) indexValue; //260 1
			} catch (Exception e) {
				e.printStackTrace();
			}
			int flag = 0;
			try {
				flag = agent.writeFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID, buff);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "Utils > writeVersionNVData, " + flag);
			return flag;
		}
        Log.w(TAG, "Utils > writeVersionNVData, return 0 ");
		return 0;
	}
    
        
    public static int readSERVersionNVFlag() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		int b = 0;
        byte bLoop;
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read 71
				// buffer from nvra
				if (buff != null) {
					b = buff[NV_SER_VERSION_INDEX];//261
					if (b == -1)
						b = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.d(TAG, "Utils > readSERVersionNVFlag, " + b);
		return b;
	}
	
	public static int writeSERVersionNVFlag(int indexValue) {
        byte bLoop;
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read 71
				buff[NV_SER_VERSION_INDEX] = (byte) indexValue; //261
			} catch (Exception e) {
				e.printStackTrace();
			}
			int flag = 0;
			try {
				flag = agent.writeFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID, buff);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "Utils > writeSERVersionNVFlag, " + flag);
			return flag;
		}
        Log.w(TAG, "Utils > writeSERVersionNVFlag, return 0 ");
		return 0;
	}
	
    public static int readResetPhoneNVFlag() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		int b = 0;
        byte bLoop;
		if (binder != null) {
			NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
			byte[] buff = null;
			try {
				buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read 71
				// buffer from nvra
				if (buff != null) {
					b = buff[NV_RESET_PHONE_INDEX];//262
					if (b == -1)
						b = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Log.d(TAG, "Utils > readResetPhoneNVFlag, " + b);
		return b;
	}
    
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
			Log.d(TAG, "Utils > writeResetPhoneNVFlag, " + flag);
			return flag;
		}
        Log.w(TAG, "Utils > writeResetPhoneNVFlag, return 0 ");
		return 0;
	}
	
    public static int getCurrentVersion() {
        String verion = Build.DISPLAY;
        Log.d(TAG, "Build.DISPLAY:"+Build.DISPLAY); 
        String[] datas = verion.split("_");
        String vernoString = datas[VERSION_INDEX].replace("V", "").trim();
        int verno = Integer.parseInt(vernoString);
        Log.d(TAG, "getCurrentVersion:"+verno); 
        return verno;
    }
    
    public static boolean isCurrentVersionSER() {
    	Log.d(TAG, "isCurrentVersionSER");
    	String verion = Build.DISPLAY;
       
        String[] datas = verion.split("_");
        String serString = datas[datas.length - 1].trim();
        Log.d(TAG, "serString:"+serString);
        if ("SER".equals(serString)) {
            return true;
        }
        return false;
    }
}
