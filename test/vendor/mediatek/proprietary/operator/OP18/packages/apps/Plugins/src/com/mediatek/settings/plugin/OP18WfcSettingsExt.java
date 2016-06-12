package com.mediatek.settings.plugin;

import android.content.Context;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;

import com.android.ims.ImsManager;
import com.android.settings.widget.SwitchBar;

import com.mediatek.common.PluginImpl;
import com.mediatek.settings.ext.DefaultWfcSettingsExt;
import com.mediatek.wfc.plugin.OP18WfcSettings;

/**
 * Plugin implementation for WFC Settings.
 */

@PluginImpl(interfaceName = "com.mediatek.settings.ext.IWfcSettingsExt")

public class OP18WfcSettingsExt extends DefaultWfcSettingsExt {

    private static final String TAG = "OP18WfcSettingsExt";
    private static final String CALL_STATE = "call_state";
    private static final String PHONE_TYPE = "phone_type";

    private Context mContext;
    private Context mAppContext;
    private SwitchPreference mWfcSwitch = null;
    private OP18WfcSettings  mWfcSettings = null;
    ImsManager mImsManager;
    private SwitchBar mHotspotSwitchBar;

    /** Constructor.
     * @param context context
     */
    public OP18WfcSettingsExt(Context context) {
        super();
        mContext = context;
    }

    /** Customize WFC pref as per operator requirement
         * @param context context
         * @param preferenceScreen preferenceScreen
         * @return
         */
    public void customizedWfcPreference(Context context, PreferenceScreen preferenceScreen) {
        mAppContext = context;
        mWfcSettings = OP18WfcSettings.getInstance(context);
        mWfcSettings.customizedWfcPreference(context, preferenceScreen);
    }

    /** Called on events like onResume/onPause etc from WirelessSettings.
    * @param event resume/puase etc.
    * @return
    */
    @Override
    public void onWirelessSettingsEvent(int event) {
        Log.d(TAG, "Wireless setting event:" + event);
        mWfcSettings = OP18WfcSettings.getInstance(mContext);
        switch(event) {
            case DefaultWfcSettingsExt.PAUSE:
                if (!ImsManager.isWfcEnabledByPlatform(mContext)) {
                    mWfcSettings.removeWfcPreference();
                }
                break;
            default:
                break;
        }
    }
}

