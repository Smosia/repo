package com.mediatek.settings.plugin;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.mediatek.common.PluginImpl;
import com.mediatek.settings.ext.DefaultDataUsageSummaryExt;

import java.util.List;

/**
 * Data Usage summary info plugin.
 */
@PluginImpl(interfaceName = "com.mediatek.settings.ext.IDataUsageSummaryExt")
public class Op01DataUsageSummaryExt extends DefaultDataUsageSummaryExt {
    private static final String TAG = "Op01DataUsageSummaryExt";
    private static final String[] MCCMNC_TABLE_TYPE_CMCC = {
        "46000", "46002", "46007"};
    private static final String[] MCCMNC_TABLE_TYPE_CT = {
        "45502", "46003", "46011", "46012", "46013"};

    private Context mContext;

    /**
     * Init context.
     * @param context The Context
     */
    public Op01DataUsageSummaryExt(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * app use to judge the Card is CMCC
     * @param slotId
     * @return true is CMCC
     */
    private boolean isCMCCCard(int subId) {
        Log.d("@M_" + TAG, "isCMCCCard, subId = " + subId);
        String simOperator = null;
        simOperator = getSimOperator(subId);
        if (simOperator != null) {
            Log.d("@M_" + TAG, "isCMCCCard, simOperator =" + simOperator);
            for (String mccmnc : MCCMNC_TABLE_TYPE_CMCC) {
                if (simOperator.equals(mccmnc)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * app use to judge the Card is CT.
     * @param subId
     * @return true is CT
     */
    private boolean isCTCard(int subId) {
        Log.d("@M_" + TAG, "isCTCard, subId = " + subId);
        String simOperator = null;
        simOperator = getSimOperator(subId);
        if (simOperator != null) {
            Log.d("@M_" + TAG, "isCTCard, simOperator =" + simOperator);
            for (String mccmnc : MCCMNC_TABLE_TYPE_CT) {
                if (simOperator.equals(mccmnc)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the MCC+MNC (mobile country code + mobile network code)
     * of the provider of the SIM. 5 or 6 decimal digits.
     * Availability: The result of calling getSimState()
     * must be android.telephony.TelephonyManager.SIM_STATE_READY.
     * @param slotId  Indicates which SIM to query.
     * @return MCC+MNC (mobile country code + mobile network code)
     * of the provider of the SIM. 5 or 6 decimal digits.
     */
    private String getSimOperator(int subId) {
        if (subId < 0) {
            return null;
        }
        String simOperator = null;
        int status = TelephonyManager.SIM_STATE_UNKNOWN;
        int slotId = SubscriptionManager.getSlotId(subId);
        if (slotId != SubscriptionManager.INVALID_SIM_SLOT_INDEX) {
             status = TelephonyManager.getDefault().getSimState(slotId);
        }
        if (status == TelephonyManager.SIM_STATE_READY) {
            simOperator = TelephonyManager.getDefault().getSimOperator(subId);
        }
        Log.d("@M_" + TAG, "getSimOperator, simOperator = " + simOperator + " subId = " + subId);
        return simOperator;
    }

    @Override
    public boolean isAllowDataEnable(View view, int subId) {
        Log.d("@M_" + TAG, "isAllowDataEnable, cursubId = " + subId);
        if (subId < 0 || "".equals(getSimOperator(subId))) {
            return true;
        }

        List<SubscriptionInfo> si = SubscriptionManager.from(mContext)
            .getActiveSubscriptionInfoList();
        if (si != null && si.size() > 1) {
            int otherId = SubscriptionManager.INVALID_SUBSCRIPTION_ID;
            for (int i = 0; i < si.size(); i++) {
                SubscriptionInfo subInfo = si.get(i);
                int curId = subInfo.getSubscriptionId();
                if (curId != subId) {
                    otherId = curId;
                    break;
                }
            }
            if (isCMCCCard(otherId) && isCTCard(subId)) {
                if (view != null) {
                    if (view instanceof Switch) {
                        Log.d("@M_" + TAG, "isAllowDataEnable, close switch");
                        TelephonyManager.from(mContext).setDataEnabled(subId, false);
                        ((Switch) view).setChecked(false);
                    }
                    view.setEnabled(false);
                }
                return false;
            }
        }

        if (view != null) {
            if (view instanceof TextView) {
                view.setEnabled(true);
            }
        }

        return true;
    }
}
