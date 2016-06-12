package com.android.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.os.ITpGesture;
import android.content.Context;
import android.content.pm.PackageManager;
//add	BUG_ID:JWBLL-1367  xieshanlin  20150407(start)
import android.provider.Settings;
//add	BUG_ID:JWBLL-1367  xieshanlin  20150407( end )

public class TpGestureService extends ITpGesture.Stub {
    private static final String TPGESTURE_FILE = "sys/class/syna/gesenable";
    private Context  mContext;
	
   public  TpGestureService(Context context){
		this.mContext=context;
		//add	BUG_ID:JWBLL-1367  xieshanlin  20150407(start)
		init();
		//add	BUG_ID:JWBLL-1367  xieshanlin  20150407( end )
	}
	
    public boolean getTpGestureEnabled() {
        try {
            FileInputStream fis = new FileInputStream(TPGESTURE_FILE);
            int result = fis.read();
            fis.close();
            return (result != '0');
        } catch (Exception e) {
            return false;
        }
    }

    public void setTpGestureEnabled(boolean on) {
    	/*********
        if (mContext.checkCallingOrSelfPermission(android.Manifest.permission.FLASHLIGHT)
                != PackageManager.PERMISSION_GRANTED &&
                mContext.checkCallingOrSelfPermission(android.Manifest.permission.HARDWARE_TEST)
                != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException("Requires FLASHLIGHT or HARDWARE_TEST permission");
        }********/
        try {
            FileOutputStream fos = new FileOutputStream(TPGESTURE_FILE);
            byte[] bytes = new byte[2];
            bytes[0] = (byte)(on ? '1' : '0');
            bytes[1] = '\n';
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            // fail silently
        }
    }

	//add	BUG_ID:JWBLL-1367  xieshanlin  20150407(start)
	void init(){
        boolean is_enable=(Settings.Global.getInt(mContext.getContentResolver(),
				Settings.Global.BLACK_GESTURE_WAKE, 0) == 1);
		if(is_enable){
			try {
				setTpGestureEnabled(true);
				android.util.Log.v("WakeGesture","open gesenable------success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				android.util.Log.v("WakeGesture","open gesenable------fail---");
				Settings.Global.putInt(mContext.getContentResolver(),
						Settings.Global.BLACK_GESTURE_WAKE, 0);
				e.printStackTrace();
			}	
		}
	}
    //add	BUG_ID:JWBLL-1367  xieshanlin  20150407( end )
	
}
