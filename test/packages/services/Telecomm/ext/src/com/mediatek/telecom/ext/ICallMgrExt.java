package com.mediatek.telecom.ext;

public interface ICallMgrExt {

    /**
     * should build call capabilities.
     *
     * @param smsCapability can response via sms.
     *
     * @return capalilities of the call.
     * @internal
     */
    int buildCallCapabilities(boolean smsCapability);

    /**
     * get max number of ringing call.
     *
     * @return maxmum of the ringing call.
     */
    int getMaximumRingingCall();
}