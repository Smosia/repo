package com.mediatek.wfc.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.Toast;

import com.android.ims.ImsConfig;
import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.internal.telephony.PhoneConstants;

/**
 * Class to support operator customizations for WFC settings.
 */
public class OP18WfcSettings implements OnPreferenceChangeListener {

    private static final String TAG = "OP18WfcSettings";
    private static final String AOSP_SETTING_WFC_PREFERENCE = "wifi_calling_settings";
    private static final String AOSP_CALL_SETTING_WFC_PREFERENCE
            = "button_wifi_calling_settings_key";
    private static final String OP18_WFC_PREFERENCE_KEY = "op18_wfc_pref_switch";
    public static final String NOTIFY_CALL_STATE = "OP18:call_state_Change";
    public static final String CALL_STATE = "call_state";
    public static final int CALL_STATE_IDLE = 1;
    public static final int CALL_STATE_CS = 2;
    public static final int CALL_STATE_PS = 3;
    static OP18WfcSettings sWfcSettings = null;

    Context mContext;
    Context mAppContext;
    SwitchPreference mWfcSwitch = null;
    PreferenceScreen mPreferenceScreen = null;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (NOTIFY_CALL_STATE.equals(intent.getAction())) {
                if (mWfcSwitch != null) {
                    int callState = intent.getIntExtra(CALL_STATE, CALL_STATE_IDLE);
                     Log.v(TAG, "br call_satte: " + callState);
                    mWfcSwitch.setEnabled(callState == CALL_STATE_PS ? false : true);
                }
            }
        }
    };

    private OP18WfcSettings(Context context) {
       mContext = context;
    }

    /** Returns instance of OP18WfcSettings.
         * @param context context
         * @return OP18WfcSettings
         */
    public static OP18WfcSettings getInstance(Context context) {

        if (sWfcSettings == null) {
            sWfcSettings = new OP18WfcSettings(context);
        }
        return sWfcSettings;
    }

    /** Customize WFC pref as per operator requirement
         * @param context context
         * @param preferenceScreen preferenceScreen
         * @return
         */
    public void customizedWfcPreference(Context context, PreferenceScreen preferenceScreen) {
        mAppContext = context;
        mPreferenceScreen = preferenceScreen;
        Preference wfcSettingsPreference = (Preference) preferenceScreen
                .findPreference(AOSP_SETTING_WFC_PREFERENCE);
        wfcSettingsPreference = wfcSettingsPreference != null ? wfcSettingsPreference
                : (Preference) preferenceScreen.findPreference(AOSP_CALL_SETTING_WFC_PREFERENCE);
        Log.d(TAG, "wfcSettingsPreference: " + wfcSettingsPreference);

        if (wfcSettingsPreference != null) {
            preferenceScreen.removePreference(wfcSettingsPreference);
            mWfcSwitch = (SwitchPreference) preferenceScreen
                    .findPreference(OP18_WFC_PREFERENCE_KEY);
            if (mWfcSwitch == null) {
                mWfcSwitch = new SwitchPreference(context);
                mWfcSwitch.setKey(OP18_WFC_PREFERENCE_KEY);
                mWfcSwitch.setTitle(wfcSettingsPreference.getTitle());
                mWfcSwitch.setOrder(wfcSettingsPreference.getOrder());
                mWfcSwitch.setOnPreferenceChangeListener(this);
                preferenceScreen.addPreference(mWfcSwitch);
            }
        }
        if (mWfcSwitch != null) {
            // Disable switch if PS call ongoing
            Log.d(TAG, "call_state: " + Settings.System.getInt(context.getContentResolver(),
                    CALL_STATE, CALL_STATE_IDLE));
            mWfcSwitch.setEnabled(Settings.System.getInt(context.getContentResolver(),
                    CALL_STATE, CALL_STATE_IDLE) == CALL_STATE_PS ? false : true);
            mWfcSwitch.setChecked(ImsManager.isWfcEnabledByUser(mAppContext));
        }
        Log.d(TAG, "mWfcSwitch: " + mWfcSwitch);
    }

    /** Returns instance of OP18WfcSettings.
     * @return
     */
    public void removeWfcPreference() {
        if (mPreferenceScreen != null && mWfcSwitch != null) {
            mPreferenceScreen.removePreference(mWfcSwitch);
        }
    }


    private boolean isInSwitchProcess() {
        int imsState = PhoneConstants.IMS_STATE_DISABLED;
        try {
         imsState = ImsManager.getInstance(mAppContext, SubscriptionManager
                .getDefaultVoicePhoneId()).getImsState();
        } catch (ImsException e) {
           return false;
        }
        Log.d(TAG, "isInSwitchProcess , imsState = " + imsState);
        return imsState == PhoneConstants.IMS_STATE_DISABLING
                || imsState == PhoneConstants.IMS_STATE_ENABLING;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final boolean isChecked = !mWfcSwitch.isChecked();

        if (isInSwitchProcess()) {
            Toast.makeText(mContext, "Operation not allowed", Toast.LENGTH_SHORT)
                .show();
            return false;
        }

        ImsManager.setWfcSetting(mAppContext, isChecked);
        if (isChecked) {
            ImsManager.setWfcMode(mAppContext,
                    ImsConfig.WfcModeFeatureValueConstants.WIFI_PREFERRED);
        } else {
            ImsManager.setWfcMode(mAppContext,
                    ImsConfig.WfcModeFeatureValueConstants.CELLULAR_ONLY);
        }
        return true;
    }
}
