/**
* addFile JWLW-1141 zhangzixiao 20131118
*/
package com.android.settings.messagenotification;

import com.mediatek.audioprofile.AudioProfileManager;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.RingtonePreference;
import android.util.AttributeSet;
import android.util.Log;
import com.mediatek.settings.UtilsExt;
import com.mediatek.settings.ext.IAudioProfileExt;

public class MessageNotificationRingtone extends RingtonePreference {
    private static final String TAG = "MessageNotificationRingtone";
    private final AudioProfileManager mProfileManager;
    private String mKey;
    private long simId = -1l;
    private IAudioProfileExt mExt;
    private Context mContext;
    

    public MessageNotificationRingtone(Context context, AttributeSet attrs) {
	super(context, attrs);
	mContext = context;
	mProfileManager = (AudioProfileManager) context.getSystemService(context.AUDIO_PROFILE_SERVICE);
    }

    @Override
    protected void onPrepareRingtonePickerIntent(Intent ringtonePickerIntent) {
	super.onPrepareRingtonePickerIntent(ringtonePickerIntent);
	//mExt = UtilsExt.getAudioProfilePlgin(mContext);
	//mExt.setRingtonePickerParams(ringtonePickerIntent);
	//ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
	//add BUG_ID:DWYBL-2483 guowen 20151027 (start)
	ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
	//add BUG_ID:DWYBL-2483 guowen 20151027 (start)
	ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_MORE_RINGTONES, true);
    }

    @Override
    protected void onSaveRingtone(Uri ringtoneUri) {
	super.onSaveRingtone(ringtoneUri);
	Log.e(TAG, "onSaveRingtone : uri="+ringtoneUri+",simId="+simId);
    //String activeKey = mProfileManager.getActiveProfileKey();
	mProfileManager.setRingtoneUri(mKey, AudioProfileManager.TYPE_MESSAGE_NOTIFICATION, simId, ringtoneUri);
	Log.d("DaiHongyi", "AudioProfileService->resetMessageNotification->support dual ringtone->send boardcast");
	Intent intent = new Intent("rgk.intent.action.MESSAGE_RINGTONE_CHANGE");
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

    @Override
    protected Uri onRestoreRingtone() {
	//return super.onRestoreRingtone();
	//Uri uri = super.onRestoreRingtone();
	//String mKeyNow = mProfileManager.getActiveProfileKey();
	Uri uri = mProfileManager.getRingtoneUri(mKey, AudioProfileManager.TYPE_MESSAGE_NOTIFICATION, simId);
    if (uri!=null) {
        Log.e(TAG, "onRestoreRingtone : before uri="+uri);
        if (!isSmsCustomRingtoneSupport()) {
           if (uri.toString().equals("content://settings/system/notification_sound")){
                uri=RingtoneManager.getActualDefaultRingtoneUri(mContext, getRingtoneType());
           }
        }  else {
        //Add DWYQLSSMY-57 chenshu 20160328 start
           if (uri.toString().equals("content://settings/system/message_ringtone")) {
                uri = RingtoneManager.getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_MESSAGE_RINGTONE);
           }
        }
        //Add DWYQLSSMY-57 chenshu 20160328 end
    }
	return uri;
    }
	
    public void setKey(String key) {
	mKey = key;
    }
	
    public void setSimId(long simId) {
	this.simId = simId;
    }
    //Add DWYQLSSMY-57 chenshu 20160328 start
	private static boolean isSmsCustomRingtoneSupport() {
		return android.os.SystemProperties.getBoolean("ro.sms.custom.ringtones.support", false);
    }
	//Add DWYQLSSMY-57 chenshu 20160328 end
}
