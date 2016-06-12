package com.mediatek.phone.plugin;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.ims.ImsManager;

import com.mediatek.common.PluginImpl;
import com.mediatek.phone.ext.DefaultCallFeaturesSettingExt;
import com.mediatek.wfc.plugin.WfcSettings;

/**
 * Plugin implementation for WFC Settings plugin
 */

@PluginImpl(interfaceName = "com.mediatek.phone.ext.ICallFeaturesSettingExt")
public class OP16CallFeaturesSettingExt extends DefaultCallFeaturesSettingExt {
    private static final String TAG = "OP16CallFeaturesSettingExt";
    private WfcSettings mWfcSettings = null;
    private Context mContext;

    private boolean mIsWfcReceiverRegistered = false;

     /** Constructor.
     * @param context context
     */
    public OP16CallFeaturesSettingExt(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void initOtherCallFeaturesSetting(PreferenceActivity activity) {

        Log.d(TAG, "initOtherCallFeaturesSetting" + activity.getClass().getSimpleName());
        if (TextUtils.equals(activity.getClass().getSimpleName(), "CallFeaturesSetting")) {
            if (ImsManager.isWfcEnabledByPlatform(activity)) {
                mWfcSettings = WfcSettings.getInstance(activity);
                mWfcSettings.customizedWfcPreference(activity, activity.getPreferenceScreen());
            }
        }
    }

    @Override
    /** Called on events like onResume/onPause etc from CallFeatureSettings.
     * @param event resume/puase etc.
     * @return
     */
    public void onCallFeatureSettingsEvent(int event) {
        Log.d("@M_" + TAG, "CallFeature setting event:" + event);
        mWfcSettings = WfcSettings.getInstance(mContext);
        switch(event) {
            case DefaultCallFeaturesSettingExt.RESUME:
                if (ImsManager.isWfcEnabledByPlatform(mContext)) {
                    mWfcSettings.register(mContext);
                    mIsWfcReceiverRegistered = true;
                }
                break;

            case DefaultCallFeaturesSettingExt.PAUSE:
                /* Need to check this to handle Dynamic IMS Switch feature
                * in which IMS can be enable/disabled on the fly */
                if (mIsWfcReceiverRegistered) {
                    mWfcSettings.unRegister(mContext);
                    mIsWfcReceiverRegistered = false;
                }
                if (!ImsManager.isWfcEnabledByPlatform(mContext)) {
                    mWfcSettings.removeWfcPreference();
                }
                break;
            default:
                break;
        }
    }
}
