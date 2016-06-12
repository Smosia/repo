package com.mediatek.op.telephony;

import android.content.Context;

import com.android.internal.telephony.dataconnection.ApnSetting;

import com.mediatek.common.PluginImpl;

/**
 * Interface that defines methos which are implemented in IGsmDCTExt
 */

 /** {@hide}. */
@PluginImpl(interfaceName = "com.mediatek.common.telephony.IGsmDCTExt")
public class GsmDCTExtOP18 extends GsmDCTExt {
    // pattern: (mcc+mnc,apn type,apn name)
    private static final String CUSTOMIZE_APN_LIST[] = {
        "405874,default,Jionet",
    };

    /**
     * Constructor.
     * @param context the Context object
     */
    public GsmDCTExtOP18(Context context) {
    }

    /**
     * Customize APN setting for OP18.
     * @param apn the ApnSetting object
     * @return ApnSetting object
     */
    public Object customizeApn(Object apn) {
        ApnSetting apnSetting = (ApnSetting) apn;
        for (String apnElement : CUSTOMIZE_APN_LIST) {
            String[] strAry = apnElement.split(",");
            if (apnSetting.numeric.equals(strAry[0]) && apnSetting.types[0].equals(strAry[1])) {
                return new ApnSetting(apnSetting.id, apnSetting.numeric,
                        apnSetting.carrier, strAry[2], apnSetting.proxy,
                        apnSetting.port, apnSetting.mmsc, apnSetting.mmsProxy,
                        apnSetting.mmsPort, apnSetting.user, apnSetting.password,
                        apnSetting.authType, apnSetting.types,
                        apnSetting.protocol, apnSetting.roamingProtocol,
                        apnSetting.carrierEnabled, apnSetting.bearer, apnSetting.bearerBitmask,
                        apnSetting.profileId, apnSetting.modemCognitive,
                        apnSetting.maxConns, apnSetting.waitTime,
                        apnSetting.maxConnsTime, apnSetting.mtu,
                        apnSetting.mvnoType, apnSetting.mvnoMatchData);
            }
        }
        return apn;
    }
}

