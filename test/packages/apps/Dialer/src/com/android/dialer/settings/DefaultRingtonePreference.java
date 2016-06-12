/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.dialer.settings;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemProperties;
import android.preference.RingtonePreference;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.widget.Toast;

import com.android.dialer.R;
import com.android.internal.telephony.PhoneConstants;
import com.mediatek.audioprofile.AudioProfileManager;

/**
 * RingtonePreference which doesn't show default ringtone setting.
 */
public class DefaultRingtonePreference extends RingtonePreference {
    //add DWYSLM-228 guowen 20160407 start
    private static final int SINGLE_SIMCARD = 1;
    private long mSimId = -1;
    private static final String PREF_SIM_ID_VALUME = "SimIdValume";
    private final AudioProfileManager mProfileManager;
    //add DWYSLM-228 guowen 20160407 end
    public DefaultRingtonePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        //add DWYSLM-228 guowen 20160407 start
        mProfileManager = (AudioProfileManager) context.getSystemService(Context.AUDIO_PROFILE_SERVICE);
        //add DWYSLM-228 guowen 20160407 end
    }

    @Override
    protected void onPrepareRingtonePickerIntent(Intent ringtonePickerIntent) {
        super.onPrepareRingtonePickerIntent(ringtonePickerIntent);
        //add guowen 20160304 start
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_MORE_RINGTONES, true);
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        //add guowen 20160304 end

        /*
         * Since this preference is for choosing the default ringtone, it
         * doesn't make sense to show a 'Default' item.
         */
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
    }

    @Override
    protected void onSaveRingtone(Uri ringtoneUri) {
        if (!Settings.System.canWrite(getContext())) {
            Toast.makeText(
                    getContext(),
                    getContext().getResources().getString(R.string.toast_cannot_write_system_settings),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //modify DWYSLM-228 guowen 20160407 start
        //RingtoneManager.setActualDefaultRingtoneUri(getContext(), getRingtoneType(), ringtoneUri);
        if (!SystemProperties.get("ro.mtk_multisim_ringtone").equals("1")) {
            RingtoneManager.setActualDefaultRingtoneUri(getContext(), getRingtoneType(), ringtoneUri);
        }else {
            SharedPreferences prefs = this.getContext().getSharedPreferences("DefaultRingtonePreference", Context.MODE_PRIVATE);
            mSimId = prefs.getLong(PREF_SIM_ID_VALUME, -1);
            mProfileManager.setRingtoneUri(mProfileManager.getActiveProfileKey(), getRingtoneType(), mSimId, ringtoneUri);
        }
        //modify DWYSLM-228 guowen 20160407 end
    }

    @Override
    protected Uri onRestoreRingtone() {
        //modify DWYSLM-228 guowen 20160407 start
        //return RingtoneManager.getActualDefaultRingtoneUri(getContext(), getRingtoneType());
        Uri uri = null;
        if (!SystemProperties.get("ro.mtk_multisim_ringtone").equals("1")) {
            uri = RingtoneManager.getActualDefaultRingtoneUri(getContext(), getRingtoneType());
        } else {
            uri = mProfileManager.getRingtoneUri(mProfileManager.getActiveProfileKey(), getRingtoneType(), mSimId);
        }
        return uri;
        //modify DWYSLM-228 guowen 20160407 end
    }

    //add DWYSLM-228 guowen 20160407 start
    public void setSimId(long simId) {
        SharedPreferences prefs = this.getContext().getSharedPreferences("DefaultRingtonePreference", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREF_SIM_ID_VALUME, simId);
        editor.commit();
        this.mSimId = simId;
    }
    
    @Override
    protected void onClick() {
        // TODO Auto-generated method stub
        // M: Set different SIM ringtone
        final TelephonyManager mTeleManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        int simNum = SubscriptionManager.from(getContext()).getActiveSubscriptionInfoCount();

        if (SystemProperties.get("ro.mtk_multisim_ringtone").equals("1") && simNum == SINGLE_SIMCARD) {
           int subId = SubscriptionManager.from(getContext()).getActiveSubscriptionIdList()[0];
           setSimId(subId);
        }
        
        if (simNum <= SINGLE_SIMCARD) {
            super.onClick();
        }
    }
    
    void simSelectorOnClick() {
        super.onClick();
    }
    //add DWYSLM-228 guowen 20160407 end
}
