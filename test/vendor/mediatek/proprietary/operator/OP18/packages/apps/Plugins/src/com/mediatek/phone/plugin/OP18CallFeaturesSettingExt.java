package com.mediatek.phone.plugin;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.text.TextUtils;
import android.util.Log;

import com.android.ims.ImsManager;

import com.mediatek.common.PluginImpl;
import com.mediatek.phone.ext.DefaultCallFeaturesSettingExt;
import com.mediatek.wfc.plugin.OP18WfcSettings;

@PluginImpl(interfaceName="com.mediatek.phone.ext.ICallFeaturesSettingExt")
public class OP18CallFeaturesSettingExt extends DefaultCallFeaturesSettingExt {
    private static final String TAG = "OP18CallFeaturesSettingExt";
    private OP18WfcSettings mWfcSettings = null;
    private Context mContext;

    /** Constructor.
     * @param context context
     */
    public OP18CallFeaturesSettingExt(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void initOtherCallFeaturesSetting(PreferenceActivity activity) {

        Log.d(TAG, "initOtherCallFeaturesSetting" + activity.getClass().getSimpleName());
        if (TextUtils.equals(activity.getClass().getSimpleName(), "CallFeaturesSetting")) {
            if (ImsManager.isWfcEnabledByPlatform(activity)) {
                mWfcSettings = OP18WfcSettings.getInstance(activity);
                mWfcSettings.customizedWfcPreference(activity, activity.getPreferenceScreen());
            }
        }
    }
    
    @Override
    public boolean needShowOpenMobileDataDialog(Context context, int subId) {
        Log.d(TAG, "needShowOpenMobileDataDialog false");
        return false;
    }

    @Override
    /** Called on events like onResume/onPause etc from CallFeatureSettings.
     * @param event resume/puase etc.
     * @return
     */
    public void onCallFeatureSettingsEvent(int event) {
        Log.d("@M_" + TAG, "CallFeature setting event:" + event);
        mWfcSettings = OP18WfcSettings.getInstance(mContext);
        switch(event) {
            case DefaultCallFeaturesSettingExt.PAUSE:
                if (!ImsManager.isWfcEnabledByPlatform(mContext)) {
                    mWfcSettings.removeWfcPreference();
                }
                break;
            default:
                break;
        }
    }
}
