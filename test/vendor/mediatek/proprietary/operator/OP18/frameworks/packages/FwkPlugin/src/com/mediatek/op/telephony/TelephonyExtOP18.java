package com.mediatek.op.telephony;

import android.telephony.Rlog;
import android.text.TextUtils;

import com.android.internal.telephony.PhoneConstants;
import com.mediatek.common.PluginImpl;

/**
 * TelephonyExt OP18 plugin.
 *
 */
@PluginImpl(interfaceName = "com.mediatek.common.telephony.ITelephonyExt")
public class TelephonyExtOP18 extends TelephonyExt {
    private static final String TAG = "TelephonyExtOP18";

    @Override
    public boolean ignoreDataRoaming(String apnType) {
        if (TextUtils.equals(apnType, PhoneConstants.APN_TYPE_IMS)) {
            Rlog.d(TAG, "ignoreDataRoaming, apnType = " + apnType);
            return true;
        }
        return false;
    }
}
