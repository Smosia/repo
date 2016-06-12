/*
* addFile JWLW-1141 zhangzixiao 20131118
*/
package com.android.mms.transaction;

import com.mediatek.setting.NotificationPreferenceActivity;
import com.mediatek.audioprofile.AudioProfileManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

public class RingtoneChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "RingtoneChangeReceiver";
    public static final String ACTION = "rgk.intent.action.MESSAGE_RINGTONE_CHANGE";
    public static final String RINGTONE = "ringtone";
    public static final String RINGTONE1 = "ringtone1";
    public static final String RINGTONE2 = "ringtone2";
    public static final String SET_RINGTONE_DEFAULT = "rgk_set_ringtone_default";

    @Override
    public void onReceive(Context context, Intent intent) {
	if(!AudioProfileManager.isSmsRingtoneSupport()) {
	    return;
	}
	if(intent == null) {
	    return;
	}
	String action = intent.getAction();
	Context mmsContext = null;
	try {
	    mmsContext = context.createPackageContext("com.android.mms", Context.CONTEXT_IGNORE_SECURITY);
	} catch(NameNotFoundException e) {
	    Log.e(TAG, "createPackageContext com.androidlmms Error!");
	    return;
	}
	SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mmsContext);
	Editor editor = preference.edit();
	if(ACTION.equals(action)) {
	    if(AudioProfileManager.isSmsDualRingtoneSupport()) {
		String ringtone1 = intent.getStringExtra(RINGTONE1);
		String ringtone2 = intent.getStringExtra(RINGTONE2);
		boolean commit = false;
		if(ringtone1 != null) {
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE, ringtone1).commit();
		    commit = true;
		}
		if(ringtone2 != null) {
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE2, ringtone2).commit();
		    commit = true;
		}
		if(commit) {
		    editor.commit();
		}
	    } else if(AudioProfileManager.isSmsSingleRingtoneSupport()) {
		String ringtone = intent.getStringExtra(RINGTONE);
		if(ringtone != null) {
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE, ringtone).commit();
		}
	    }
	} else if(Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
	    if(!preference.getBoolean(SET_RINGTONE_DEFAULT, false)) {
		editor.putBoolean(SET_RINGTONE_DEFAULT, true);
		if(AudioProfileManager.isSmsDualRingtoneSupport()) {
		    String ringtoneStr1 = AudioProfileManager.getDefaultMessageRingtone(mmsContext, 0);
		    String ringtoneStr2 = AudioProfileManager.getDefaultMessageRingtone(mmsContext, 1);
		    Log.e("TAG", "SET_RINGTONE_DEFAULT : ringtoneStr1="+ringtoneStr1+",ringtoneStr2="+ringtoneStr2);
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE, ringtoneStr1);
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE2, ringtoneStr2);
	    	} else if(AudioProfileManager.isSmsSingleRingtoneSupport()) {
		    String ringtoneStr = AudioProfileManager.getDefaultMessageRingtone(mmsContext, -1);
		    Log.e("TAG", "SET_RINGTONE_DEFAULT : ringtoneStr="+ringtoneStr);
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE, ringtoneStr);
	    	}
		editor.commit();
	    }
	}
    }

}
