package com.ragentek.wakegesture.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

public class Util {
	
	/*****
	 * specific  action
	 * @param keycode
	 * @param context
	 */
	public static void doKeyAction(int keycode) {
    long  actionTime=SystemClock.uptimeMillis();
    sendEvent(KeyEvent.ACTION_DOWN, 0,actionTime,keycode);
    sendEvent(KeyEvent.ACTION_UP, 0,actionTime,keycode);
    //sst end
    }
	
	public static  void sendEvent(int action, int flags, long when,int keyCode) {
        final int repeatCount = (flags & KeyEvent.FLAG_LONG_PRESS) != 0 ? 1 : 0;
        final KeyEvent ev = new KeyEvent(when, when, action, keyCode, repeatCount,
                0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
                flags | KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_VIRTUAL_HARD_KEY,InputDevice.SOURCE_KEYBOARD);
        InputManager.getInstance().injectInputEvent(ev,InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
    }
    
	public  static  String getProgramApplicationName(Context context,PackageManager packageManager,String packageName){
		String  name=null;
		try {
			name=packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
			//packageManager.getActivityInfo(arg0, arg1);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
		
	}
	public  static void putIsConfigurationApplication(Context context,int keyCode,int  flag){
		ContentResolver  resolver=context.getContentResolver();
		if(keyCode==KeyEvent.KEYCODE_C){
		 	
		 	Settings.Global.putInt(
		 			resolver,Settings.Global.BLACK_GESTURE_WAKE_C, flag);
			
		 	}else if(keyCode==KeyEvent.KEYCODE_E){
		 	
		 		Settings.Global.putInt(
			 			resolver,Settings.Global.BLACK_GESTURE_WAKE_E, flag);

			}else if(keyCode==KeyEvent.KEYCODE_M){

				Settings.Global.putInt(
			 			resolver,Settings.Global.BLACK_GESTURE_WAKE_M, flag);
			
			}else if(keyCode==KeyEvent.KEYCODE_O){
			
				Settings.Global.putInt(
			 			resolver,Settings.Global.BLACK_GESTURE_WAKE_O, flag);

				
			}else if(keyCode==KeyEvent.KEYCODE_W){

				Settings.Global.putInt(
			 			resolver,Settings.Global.BLACK_GESTURE_WAKE_W, flag);
			
			}
	}
}
