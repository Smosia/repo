/*
* addFile JWLW-1141 zhangzixiao 20131118
*/
package com.android.mms.ui;

import com.mediatek.audioprofile.AudioProfileManager;
import com.mediatek.audioprofile.AudioProfileManager.Scenario;

import android.content.Context;
import android.net.Uri;
import android.preference.RingtonePreference;
import android.util.AttributeSet;
import android.util.Log;
import android.media.RingtoneManager;
import android.content.Intent;
import com.mediatek.setting.NotificationPreferenceActivity;

public class MessageRingtonePreference extends RingtonePreference {
	private static final String TAG = "MessageRingtonePreference";
	private final AudioProfileManager profileManager;
    private Context mContext;
	public MessageRingtonePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
        mContext = context;
		profileManager = (AudioProfileManager) context.getSystemService(Context.AUDIO_PROFILE_SERVICE);
	}

       @Override
       protected void onPrepareRingtonePickerIntent(Intent ringtonePickerIntent) {
            super.onPrepareRingtonePickerIntent(ringtonePickerIntent);
		    ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_MORE_RINGTONES, true);
            ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
            //ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, onRestoreRingtone());
       }
	
	@Override
	protected void onSaveRingtone(Uri ringtoneUri) {
		super.onSaveRingtone(ringtoneUri);
		if(supportDualRingtone() || supportSingleRingtone()) {
		    String activeKey = profileManager.getActiveProfileKey();
			long simId = getSimId();
			Log.e(TAG, "onSaveRingtone : uri="+ringtoneUri+",simId="+simId+",activeKey="+activeKey);
			profileManager.setRingtoneUri(activeKey, AudioProfileManager.TYPE_MESSAGE_NOTIFICATION, 
					simId, ringtoneUri);
            Intent intent = new Intent("rgk.intent.action.MESSAGE_RINGTONE_CHANGE_BY_MMS");
	        if(ringtoneUri != null){
		        if(simId == 0) {
			        intent.putExtra("ringtone1", ringtoneUri.toString());
		        } else if(simId == 1){
			        intent.putExtra("ringtone2", ringtoneUri.toString());
		        }
	        }else{
		        if(simId == 0) {
			        intent.putExtra("ringtone1", "");
		        } else if(simId == 1){
			        intent.putExtra("ringtone2", "");
		        }

	        }
	        mContext.sendBroadcast(intent);
		}
		
	}

	@Override
	protected Uri onRestoreRingtone() {
        Uri uri = super.onRestoreRingtone();;
        if (supportDualRingtone()) {
		    String mKey = profileManager.getActiveProfileKey();
            long simId = getSimId();
            uri=profileManager.getRingtoneUri(mKey, AudioProfileManager.TYPE_MESSAGE_NOTIFICATION, simId);
            if (uri != null) {
	            Log.e(TAG, "onRestoreRingtone : uri="+uri+",simId="+simId);
                Log.e(TAG, "onRestoreRingtone : before uri="+uri);
                if (!isSmsCustomRingtoneSupport()) {
                    if (uri.toString().equals("content://settings/system/notification_sound")){
                        uri=RingtoneManager.getActualDefaultRingtoneUri(mContext, getRingtoneType());
                    }
                } else {
                    //Add DWYQLSSMY-57 chenshu 20160328 start
                    if (uri.toString().equals("content://settings/system/message_ringtone")) {
                        uri = RingtoneManager.getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_MESSAGE_RINGTONE);
                    }
                    //Add DWYQLSSMY-57 chenshu 20160328 end
                }
            }
        } else {
            if (uri != null) {
                Log.e(TAG, "onRestoreRingtone : uri="+uri);
                if (uri.toString().equals("content://settings/system/notification_sound")){
                    uri=RingtoneManager.getActualDefaultRingtoneUri(mContext, getRingtoneType());
                }
            }
        }
		return uri;
	}

	private long getSimId() {
		if(supportDualRingtone()) {
		    if(NotificationPreferenceActivity.NOTIFICATION_RINGTONE2.equals(getKey())) {
			return 1l;
		    } else {
			return 0l;
		    }
		} else if(supportSingleRingtone()) {
		    return -1l;
		}
		return -2l;
	}

    	private boolean supportDualRingtone() {
        	return com.mediatek.audioprofile.AudioProfileManager.isSmsDualRingtoneSupport();
    	}

	private boolean supportSingleRingtone() {
		return com.mediatek.audioprofile.AudioProfileManager.isSmsSingleRingtoneSupport();
    	}
    //Add DWYQLSSMY-57 chenshu 20160328 start
	private static boolean isSmsCustomRingtoneSupport() {
		return android.os.SystemProperties.getBoolean("ro.sms.custom.ringtones.support", false);
    }
	//Add DWYQLSSMY-57 chenshu 20160328 end
}
