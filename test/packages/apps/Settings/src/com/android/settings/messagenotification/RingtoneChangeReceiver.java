/*
* addFile JWLW-1141 zhangzixiao 20131118
*/
package com.android.settings.messagenotification;

import com.mediatek.audioprofile.AudioProfileManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.android.internal.telephony.TelephonyIntents;
import com.android.internal.telephony.PhoneConstants;
import android.os.Bundle;
import android.util.Log;

public class RingtoneChangeReceiver extends BroadcastReceiver {
    private static final String TAG = "SettingsMessageRingtoneReceiver";
    public static final String ACTION = "rgk.intent.action.MESSAGE_RINGTONE_CHANGE_BY_MMS";
    //add DWLJE-864 chenshu 20150922 start
    public static final String ACTION_MESSAGE_RINGTONE_CHANGE = "rgk.intent.action.MESSAGE_RINGTONE_CHANGE";
    public static final String ACTION_FILE_MANAGER_MESSAGE_RINGTONE_CHANGE = "com.mediatek.filemanager.CHANGE_MMS_RINGTONE";
    //add DWLJE-864 chenshu 20150922 end
    public static final String RINGTONE = "ringtone";
    public static final String RINGTONE1 = "ringtone1";
    public static final String RINGTONE2 = "ringtone2";
    public static final String SET_RINGTONE_DEFAULT = "rgk_set_ringtone_default";
    public static final String KEY_MESSAGE_RINGTONE1 = "pref_key_message_ringtone1";
    public static final String KEY_MESSAGE_RINGTONE2 = "pref_key_message_ringtone2";
    public static final String KEY_MESSAGE_NAME1 = "pref_key_message_name";
    public static final String KEY_MESSAGE_NAME2 = "pref_key_message_name2";

    @Override
  public void onReceive(Context context, Intent intent) {
		if(!AudioProfileManager.isSmsRingtoneSupport()) {
		    return;
		}
		if(intent == null) {
		    return;
		}
		String action = intent.getAction();
		Context settingsContext = null;
		try {
		    settingsContext = context.createPackageContext("com.android.settings", Context.CONTEXT_IGNORE_SECURITY);
		} catch(NameNotFoundException e) {
		    Log.e(TAG, "createPackageContext com.android.settings Error!");
		    return;
		}
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(settingsContext);
		Editor editor = preference.edit();
        //add DWYJESE-28 chenshu 20151109 start
        if (ACTION_FILE_MANAGER_MESSAGE_RINGTONE_CHANGE.equals(action)){
            Bundle extras = intent.getExtras();
		    String ringUri = extras.getString("ringUri","");
            if(ringUri == ""){
			   return;
		    }
            if(AudioProfileManager.isSmsDualRingtoneSupport()) {
		        Log.i("Mms","MessageRingtoneChangeReceiver->ringUri="+ringUri);
                editor.putString(KEY_MESSAGE_RINGTONE1, ringUri).commit();
                editor.putString(KEY_MESSAGE_RINGTONE2, ringUri).commit();
                editor.commit();
		    } else if(AudioProfileManager.isSmsSingleRingtoneSupport()) {
                Log.i("Mms","MessageRingtoneChangeReceiver->ringUri="+ringUri);
                editor.putString("pref_key_message_ringtone", ringUri).commit();
		    }
        }
        //add DWYJESE-28 chenshu 20151109 end
        //Modify DWLJE-864 chenshu 20150922 start
		//if(ACTION.equals(action)) {
        if(ACTION.equals(action) || ACTION_MESSAGE_RINGTONE_CHANGE.equals(action)) {
        //Modify DWLJE-864 chenshu 20150922 end
		    if(AudioProfileManager.isSmsDualRingtoneSupport()) {
				String ringtone1 = intent.getStringExtra(RINGTONE1);
                android.util.Log.e(TAG,"ringtone1:"+ringtone1);
				String ringtone2 = intent.getStringExtra(RINGTONE2);
				boolean commit = false;
				if(ringtone1 != null) {
				    editor.putString(KEY_MESSAGE_RINGTONE1, ringtone1).commit();
				    commit = true;
				}
				if(ringtone2 != null) {
				    editor.putString(KEY_MESSAGE_RINGTONE2, ringtone2).commit();
				    commit = true;
				}
				if(commit) {
				    editor.commit();
				}
		  } else if(AudioProfileManager.isSmsSingleRingtoneSupport()) {
				String ringtone = intent.getStringExtra(RINGTONE);
				if(ringtone != null) {
				    editor.putString("pref_key_message_ringtone", ringtone).commit();
				}
		  }
		} else if (TelephonyIntents.SPN_STRINGS_UPDATED_ACTION.equals(action)) {
            String name = null;
            if (intent.getBooleanExtra(TelephonyIntents.EXTRA_SHOW_PLMN, false)) {
                final String plmn = intent.getStringExtra(TelephonyIntents.EXTRA_PLMN);
                if (plmn != null) {
                    name =  plmn;
                }
            } else if (intent.getBooleanExtra(TelephonyIntents.EXTRA_SHOW_SPN, false)) {
                final String spn = intent.getStringExtra(TelephonyIntents.EXTRA_SPN);
                if (spn != null) {
                    name = spn;
                }
            }
            int slot = intent.getIntExtra(PhoneConstants.SLOT_KEY, -1);
            switch (slot) {
            case 0:
                preference.edit().putString(KEY_MESSAGE_NAME1, name).commit();
                break;
            case 1:
                preference.edit().putString(KEY_MESSAGE_NAME2, name).commit();
                break;
                
            default:
                break;
            }
            
        }
  }

}
