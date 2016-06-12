package com.mediatek.systemui.plugin;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.ServiceState;
import android.util.Log;

import com.mediatek.common.PluginImpl;
import com.mediatek.systemui.ext.DefaultStatusBarPlugin;
/**
 * M: OP08 implementation of Plug-in definition of Status bar.
 */
@PluginImpl(interfaceName = "com.mediatek.systemui.ext.IStatusBarPlugin")
public class Op08StatusBarPlugin extends DefaultStatusBarPlugin {

    private static final String TAG = "Op08StatusBarPlugin";
    private Context mContext;

    /** Constructor.
     * @param context id
    */
    public Op08StatusBarPlugin(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * Get the current service state for op customized view update.
     * @param serviceState current service state.
     * @param connected current connection state.
     * @return updated connection state.
     */
    public boolean updateSignalStrengthWifiOnlyMode(ServiceState serviceState, boolean connected) {
        int radioTechology = serviceState.getRilDataRadioTechnology();
        if (radioTechology == ServiceState.RIL_RADIO_TECHNOLOGY_IWLAN) {
            Log.d(TAG, "Into updateSignalStrengthWifiOnlyMode return false");
            return false;
        }
        Log.d(TAG, "Into updateSignalStrengthWifiOnlyMode return true");
        return connected;
    }
}
