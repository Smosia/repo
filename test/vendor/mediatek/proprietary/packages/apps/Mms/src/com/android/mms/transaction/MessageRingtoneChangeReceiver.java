package com.android.mms.transaction;


import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import com.mediatek.setting.NotificationPreferenceActivity;
import android.util.Log;

public class MessageRingtoneChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		String ringUri = extras.getString("ringUri","");
        //add DWYBL-2804 chenshu 20151102 start
        int mSimId = extras.getInt("simId",-1);
		Log.i("Mms","MessageRingtoneChangeReceiver->ringUri="+ringUri);
        //add DWYBL-2804 chenshu 20151102 end
		if(ringUri == ""){
			return;
		}
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE,ringUri);
        //add DWYBL-2804 chenshu 20151102 start
        if (com.mediatek.audioprofile.AudioProfileManager.isSmsDualRingtoneSupport()) {
		    editor.putString(NotificationPreferenceActivity.NOTIFICATION_RINGTONE2,ringUri);
            Log.i("Mms","NOTIFICATION_RINGTONE2");
        }
        //add DWYBL-2804 chenshu 20151102 end
		editor.commit();
	}
}
