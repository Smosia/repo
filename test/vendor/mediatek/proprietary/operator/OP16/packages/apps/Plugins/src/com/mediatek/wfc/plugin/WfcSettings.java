package com.mediatek.wfc.plugin;

import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
//import android.view.View;
import android.widget.Toast;

import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.RILConstants;


/**
 * Customized WFC Setting implementation.
 */
public class WfcSettings implements OnPreferenceChangeListener {

    private static final String TAG = "OP16WfcSettings";
    public static final String WFC_PREFERENCE_KEY = "wfc_pref";
    // WFC preference key used in WirelessSettings
    private static final String AOSP_WFC_PREF_KEY_SETTINGS = "wifi_calling_settings";
    // WFC preference key used in Call FeatureSettings
    private static final String AOSP_WFC_PREF_KEY_CALL = "button_wifi_calling_settings_key";

    private PreferenceScreen mPreferenceScreen = null;
    static WfcSettings sWfcSettings = null;

    Context mContext;
    Context mAppContext;
    SwitchPreference mWfcSwitch = null;
    private CallStateListener mCallListener;

    private WfcSettings(Context context) {
       mContext = context;
       mCallListener = new CallStateListener();
    }

    /** Provides instance of plugin.
     * @param context context
     * @return WfcSettings
     */
    public static WfcSettings getInstance(Context context) {

        if (sWfcSettings == null) {
            sWfcSettings = new WfcSettings(context);
        }
        return sWfcSettings;
    }

    /** Customize wfc setting.
     * @param context context
     * @param preferenceScreen preferenceScreen
     * @return
     */
    public void customizedWfcPreference(Context context, PreferenceScreen preferenceScreen) {
        mAppContext = context;
        mPreferenceScreen = preferenceScreen;
        Preference wfcSettingsPreference = (Preference) preferenceScreen
                .findPreference(AOSP_WFC_PREF_KEY_SETTINGS);
        wfcSettingsPreference = wfcSettingsPreference == null ?
                (Preference) preferenceScreen.findPreference(AOSP_WFC_PREF_KEY_CALL)
                : wfcSettingsPreference;
        if (wfcSettingsPreference != null) {
            preferenceScreen.removePreference(wfcSettingsPreference);
            Log.d(TAG, "removing AOSP wfc pref: " + wfcSettingsPreference);
        }
        mWfcSwitch = (SwitchPreference) preferenceScreen
                .findPreference(WFC_PREFERENCE_KEY);

        if (mWfcSwitch == null) {
            mWfcSwitch = new SwitchPreference(context);
            mWfcSwitch.setKey(WFC_PREFERENCE_KEY);
            if (wfcSettingsPreference != null) {
            mWfcSwitch.setTitle(wfcSettingsPreference.getTitle());
            mWfcSwitch.setOrder(wfcSettingsPreference.getOrder());
            }
            mWfcSwitch.setOnPreferenceChangeListener(this);
            preferenceScreen.addPreference(mWfcSwitch);
       }
       if (mWfcSwitch != null) {
        mWfcSwitch.setChecked(ImsManager.isWfcEnabledByUser(mAppContext));
      }
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
        return true;
    }

    /** Registers listener/receiver.
     * @param context context
     * @return
     */
    public void register(Context context) {
        ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .listen(mCallListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /** Unregisters listener/receiver.
     * @param context context
     * @return
     */
    public void unRegister(Context context) {
        ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .listen(mCallListener, PhoneStateListener.LISTEN_NONE);
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

    /** Removes WFC preference
     * @return
     */
    public void removeWfcPreference() {
        if (mPreferenceScreen != null && mWfcSwitch != null) {
            mPreferenceScreen.removePreference(mWfcSwitch);
        }
   }

    /** Call State listener: disables WFC pref on IMS call
     */

    private class CallStateListener extends PhoneStateListener {
    @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // TODO Auto-generated method stub
            int phoneType = getPhoneType();
            Log.d(TAG, "in onCallStateChanged state:" + state + ", phoneType:" + phoneType);
            Log.d(TAG, "mWfcSwitch: " + mWfcSwitch);
            switch(state) {
                case TelephonyManager.CALL_STATE_OFFHOOK:
                case TelephonyManager.CALL_STATE_RINGING:
                    if (mWfcSwitch != null && phoneType == RILConstants.IMS_PHONE) {
                        mWfcSwitch.setEnabled(false);
                    }
                break;

                case TelephonyManager.CALL_STATE_IDLE:
                default:
                    if (mWfcSwitch != null) {
                        mWfcSwitch.setEnabled(true);
                    }
                break;
            }
        }
        }

    private int getPhoneType() {
        int phoneType = TelephonyManager.PHONE_TYPE_NONE;
        for (int i = 0; i < TelephonyManager.getDefault().getSimCount(); i++) {
            Log.d(TAG, "simCount: " + i);
            int[] subIds = SubscriptionManager.getSubId(i);
            if (subIds == null || subIds.length == 0) {
                continue;
            }
            Log.d(TAG, "subIds: " + subIds);
            if (TelephonyManager.getDefault().getCallState(subIds[0])
                        != TelephonyManager.CALL_STATE_IDLE) {
                phoneType = TelephonyManager.getDefault().getCurrentPhoneType(subIds[0]);
                break;
            }
        }
        return phoneType;
    }
}
