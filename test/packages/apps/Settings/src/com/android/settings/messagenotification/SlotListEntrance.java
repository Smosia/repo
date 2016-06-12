/**
* addFile JWLW-1141 zhangzixiao 20131118	
*/
package com.android.settings.messagenotification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.mediatek.audioprofile.AudioProfileManager;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import com.android.settings.InstrumentedFragment;
import java.util.List;

public class SlotListEntrance extends SettingsPreferenceFragment {
	public static final String KEY_MESSAGE_RINGTONE1 = "pref_key_message_ringtone1";
	public static final String KEY_MESSAGE_RINGTONE2 = "pref_key_message_ringtone2";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        final TelephonyManager tm =
                    (TelephonyManager) getActivity().getSystemService(
                            Context.TELEPHONY_SERVICE);
                            
		addPreferencesFromResource(R.xml.slot_list_entrance);
        
		getActivity().setTitle(R.string.message_notification_sound_title);
		Bundle extras = getArguments();
		String mKey = extras.getString("profileKey");
		MessageNotificationRingtone ringtone1 = 
			(MessageNotificationRingtone) findPreference(KEY_MESSAGE_RINGTONE1);
		MessageNotificationRingtone ringtone2 = 
			(MessageNotificationRingtone) findPreference(KEY_MESSAGE_RINGTONE2);
            
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SubscriptionInfo subInfo = findRecordBySlotId(getActivity(), 0);
        String title = null;
        String number = null;
        if (subInfo != null) {
            title = tm.getSimOperatorNameForSubscription(subInfo.getSubscriptionId());
            if (!TextUtils.isEmpty(subInfo.getDisplayName())) {
            	ringtone1.setTitle(subInfo.getDisplayName());
            } else {
            	ringtone1.setTitle(!TextUtils.isEmpty(title) ? title : getActivity().getString(R.string.pref_title_notification_ringtone1));
            }           
            number = tm.getLine1NumberForSubscriber(subInfo.getSubscriptionId());
            if (!TextUtils.isEmpty(number)) {
            	ringtone1.setSummary(number);
            }
        } else {
            ringtone1.setTitle(R.string.pref_title_notification_ringtone1);
        }
		ringtone1.setKey(mKey);
		ringtone1.setSimId(0);
        
        subInfo = findRecordBySlotId(getActivity(), 1);
        if (subInfo != null) {
            title = tm.getSimOperatorNameForSubscription(subInfo.getSubscriptionId());
            if (!TextUtils.isEmpty(subInfo.getDisplayName())) {
            	ringtone2.setTitle(subInfo.getDisplayName());
            } else {
            	ringtone2.setTitle(!TextUtils.isEmpty(title) ? title : getActivity().getString(R.string.pref_title_notification_ringtone2));
            }
            number = tm.getLine1NumberForSubscriber(subInfo.getSubscriptionId());
            if (!TextUtils.isEmpty(number)) {
            	ringtone2.setSummary(number);
            }
        } else {
            ringtone2.setTitle(R.string.pref_title_notification_ringtone2);
        }
		ringtone2.setKey(mKey);
		ringtone2.setSimId(1);
	}
	
    public SubscriptionInfo findRecordBySlotId(Context context, final int slotId) {
        final List<SubscriptionInfo> subInfoList =
                SubscriptionManager.from(context).getActiveSubscriptionInfoList();
        if (subInfoList != null) {
            final int subInfoLength = subInfoList.size();

            for (int i = 0; i < subInfoLength; ++i) {
                final SubscriptionInfo sir = subInfoList.get(i);
                if (sir.getSimSlotIndex() == slotId) {
                    //Right now we take the first subscription on a SIM.
                    return sir;
                }
            }
        }
        return null;
    }

    @Override
    protected int getMetricsCategory() {
        return InstrumentedFragment.METRICS_AUDIOPROFILE;
    }
}
