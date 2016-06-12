package com.mediatek.phone.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.ims.ImsManager;

import com.mediatek.common.PluginImpl;
import com.mediatek.phone.ext.DefaultCallFeaturesSettingExt;
import com.mediatek.wfc.plugin.OP06WfcSettings;
import com.mediatek.wfc.plugin.WifiCallingUtils;


@PluginImpl(interfaceName="com.mediatek.phone.ext.ICallFeaturesSettingExt")
public class OP06CallFeaturesSettingExt extends DefaultCallFeaturesSettingExt {
    private static final String TAG = "OP06CallFeaturesSettingExt";
    private static final String AOSP_WFC_PREFERENCE = "button_wifi_calling_settings_key";
    private Context mContext;
    PreferenceActivity mCallSettingActivity = null;
    private boolean mIsWfcReceiverRegistered = false;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action:" + action);
            if (CarrierConfigManager.ACTION_CARRIER_CONFIG_CHANGED.equals(action)) {
                if (mCallSettingActivity != null) {
                    initOtherCallFeaturesSetting(mCallSettingActivity);
                }
            }
        }
    };

    public OP06CallFeaturesSettingExt(Context context) {
        Log.d(TAG, "OP06 Constructor call");
        mContext = context;
    }

   /**
    * Get whether the IMS is IN_SERVICE.
    * @param subId the sub which one user selected.
    * @return true if the ImsPhone is IN_SERVICE, else false.
    */
    private static boolean isImsServiceAvailable(Context context, int subId) {
        boolean isImsReg = false;
        boolean isImsEnabled = ImsManager.isVolteEnabledByPlatform(context);
        Log.d(TAG, "[isImsServiceAvailable] isImsEnabled : " + isImsEnabled);
        if (isImsEnabled) {
            isImsReg = ((TelephonyManager)context
                    .getSystemService(Context.TELEPHONY_SERVICE)).isVolteAvailable();
            Log.d(TAG, "[isImsServiceAvailable] isImsReg = " + isImsReg);
        }
        return isImsReg;
    }

    @Override
   /**
     * Get whether the IMS is IN_SERVICE.
     * @param subId the sub which one user selected.
     * @return true if the ImsPhone is IN_SERVICE, else false.
     */
   public boolean needShowOpenMobileDataDialog(Context context, int subId) {
        Log.d(TAG, "ImsService Not Available, plugin returns true else false");
        return !isImsServiceAvailable(context, subId);
    }

    @Override
    /**
      * Keep the preference enabled after the preference is SET
      * @param subId the sub which one user selected.
      */
    public void onError(Preference preference){
        preference.setEnabled(true);
        Log.d(TAG, "onError, set preference true even after error");
    }

    @Override
    public void initOtherCallFeaturesSetting(PreferenceActivity activity) {
        mCallSettingActivity = activity;
        if (TextUtils.equals(activity.getClass().getSimpleName(), "CallFeaturesSetting")) {
            if (ImsManager.isWfcEnabledByPlatform(activity)) {
                OP06WfcSettings wfcSettings = OP06WfcSettings.getInstance(mContext);
                wfcSettings.customizedWfcPreference(activity, activity.getPreferenceScreen());
            }
        }
    }

    @Override
    public void onCallFeatureSettingsEvent(int event) {
        Log.d(TAG, "CallFeatureSetting event:" + event);
        switch(event) {
            case DefaultCallFeaturesSettingExt.RESUME:
                Log.d(TAG, "mCallSettingActivity: " + mCallSettingActivity);
                if (mCallSettingActivity != null) {
                    Preference wifiCallingSettings
                            = mCallSettingActivity.findPreference(AOSP_WFC_PREFERENCE);
                    Log.d(TAG, "wifiCallingSettings: " + wifiCallingSettings);
                    if (wifiCallingSettings != null) {
                        wifiCallingSettings.setSummary(WifiCallingUtils
                                .getWfcModeSummary(ImsManager.getWfcMode(mContext)));
                    }
                }
                if (ImsManager.isWfcEnabledByPlatform(mContext)) {
                    mContext.registerReceiver(mReceiver, WifiCallingUtils.getIntentFilter());
                    mIsWfcReceiverRegistered = true;
                }
                break;
            case DefaultCallFeaturesSettingExt.PAUSE:
                /* Need to check this to handle Dynamic IMS Switch feature
                * in which IMS can be enable/disabled on the fly */
                if (mIsWfcReceiverRegistered) {
                    mContext.unregisterReceiver(mReceiver);
                    mIsWfcReceiverRegistered = false;
                }
            break;
            default:
            break;
        }
    }
}
