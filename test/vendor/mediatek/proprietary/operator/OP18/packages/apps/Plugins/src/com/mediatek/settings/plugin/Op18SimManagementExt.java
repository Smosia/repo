package com.mediatek.settings.plugin;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.preference.PreferenceFragment;
import android.telecom.TelecomManager;
import android.telephony.RadioAccessFamily;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.ims.ImsManager;
import com.android.internal.telephony.TelephonyIntents;

import com.mediatek.common.PluginImpl;
import com.mediatek.internal.telephony.ITelephonyEx;
import com.mediatek.op18.plugin.DataSwitchDialog;
import com.mediatek.settings.ext.DefaultSimManagementExt;

import java.util.List;

/**
 * plugin impl.
 */
@PluginImpl (interfaceName = "com.mediatek.settings.ext.ISimManagementExt")

/**
 * Handle selection of non-lte sim for default data and disable SIM dialog at bootup.
 */
public class Op18SimManagementExt extends DefaultSimManagementExt {

    private static final String TAG = "Op18SimManagementExt";

    private Context mContext;
    PreferenceFragment mPrefFragment;
    private AlertDialog mAlertDlg;
    private int mToCloseSlot = -1;
    private int mSimMode;

    private static final String LTE_SUPPORT = "1";
    private static final String VOLTE_SUPPORT = "1";
    // Subinfo record change listener.
    private BroadcastReceiver mSubReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "mSubReceiver action = " + action);
                if (action.equals(TelephonyIntents.ACTION_SUBINFO_CONTENT_CHANGE)
                        || action.equals(TelephonyIntents.ACTION_SUBINFO_RECORD_UPDATED)) {
                        int[] subids = SubscriptionManager.from(mContext).
                                getActiveSubscriptionIdList();
                        if (subids == null || subids.length <= 1) {
                            if (mAlertDlg != null && mAlertDlg.isShowing()) {
                                Log.d(TAG, "onReceive dealWithDataConnChanged dismiss AlertDlg");
                                mAlertDlg.dismiss();
                                if (mPrefFragment != null) {
                                    mPrefFragment.getActivity().unregisterReceiver(mSubReceiver);
                                }
                            }
                        }
                }
            }
    };

    /**
     * Initialize plugin context.
     * @param context context
     */
    public Op18SimManagementExt(Context context) {
        super();
        mContext = context;
        Log.d(TAG, "mContext = " + mContext);
    }

    /**
     * Disable SIM dialog.
     * @return false To disable sim dialog.
     */
    @Override
    public boolean isSimDialogNeeded() {
        Log.d(TAG, "set isSimDialogNeeded false");
        setDefaultPickForSmsAndCall();
        return false;
    }

    /**
     * Set default pick for sms and call.
     */
    private void setDefaultPickForSmsAndCall() {
        //final ArrayList<String> list = new ArrayList<String>();
        final SubscriptionManager subscriptionManager = SubscriptionManager.from(mContext);
        final List<SubscriptionInfo> subInfoList =
                subscriptionManager.getActiveSubscriptionInfoList();
        final int selectableSubInfoLength = subInfoList == null ? 0 : subInfoList.size();
        final TelecomManager telecomManager = TelecomManager.from(mContext);
        int subId = subscriptionManager.getDefaultSubId();
        Log.d(TAG, "subId: " + subId + "selectableSubInfoLength: " + selectableSubInfoLength);

        if (selectableSubInfoLength == 1) {
            // Set default SMS pick
            subscriptionManager.setDefaultSmsSubId(subId);
            Log.d(TAG, "set Default SMS PICK");

            //Set default Call pick
            PhoneAccountHandle selectedAccountHandle =
                    telecomManager.getUserSelectedOutgoingPhoneAccount();
            final List<PhoneAccountHandle> accountHandles =
                    telecomManager.getCallCapablePhoneAccounts();
            Log.d(TAG, "selectedAccountHandle: " + selectedAccountHandle);
            for (PhoneAccountHandle handle : accountHandles) {
                final PhoneAccount account = telecomManager.getPhoneAccount(handle);
                if (account != null) {
                    telecomManager.setUserSelectedOutgoingPhoneAccount(handle);
                    Log.d(TAG, "set Default CALL PICK");
                    break;
                }
            }
        }
    }

    @Override
    public boolean switchDefaultDataSub(Context context, int subId) {
        if (isShowConfirmDialog(subId)) {
            Intent start = new Intent(mContext, DataSwitchDialog.class);
            start.putExtra("subId", subId);
            //start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(start);
            Log.d(TAG, "switchDefaultDataSub(), " + subId);
            return true;
        } else {
            Log.d(TAG, "switchDefaultDataSub(), false");
            return false;
        }
    }

    /**
     * app use to judge LTE open.
     * @return true is LTE open
     */
    private boolean isLTESupport() {
        boolean isSupport = LTE_SUPPORT.equals(
                SystemProperties.get("ro.mtk_lte_support")) ? true : false;
        Log.d(TAG, "isLTESupport(): " + isSupport);
        return isSupport;
    }

    /**
     * app use to judge LTE open.
     * @return true is LTE open
     */
    private boolean isVoLTESupport() {
        boolean isSupport = VOLTE_SUPPORT.equals(
                SystemProperties.get("ro.mtk_volte_support")) ? true : false;
        Log.d(TAG, "isVoLTESupport(): " + isSupport);
        return isSupport;
    }

    /**
     * get the LTE Capability subId.
     * @return the LTE Capability subId
     */
    private int getLTECapabilitySubId() {
        int subId = -1;
        ITelephonyEx iTelEx = ITelephonyEx.Stub.asInterface(ServiceManager
                                      .getService(Context.TELEPHONY_SERVICE_EX));
        if (iTelEx != null) {
            try {
                int phoneId = iTelEx.getMainCapabilityPhoneId();
                Log.d("@M_" + TAG, "subId : " + subId + ", PhoneId : " + phoneId);
                if (phoneId >= 0) {
                    subId = SubscriptionManager.getSubIdUsingPhoneId(iTelEx
                                    .getMainCapabilityPhoneId());
                    }
            } catch (RemoteException e) {
                Log.d("@M_" + TAG, "getLTECapabilitySubId FAIL to getSubId" + e.getMessage());
            }
        }
        return subId;
    }

   /**
    * app use to judge if need confirm before switch data.
    * @return false is no need confirm
    */
    private boolean isShowConfirmDialog(int switchtoSubId) {
        boolean imsRegStatus = false;

        if (!isLTESupport() || !isVoLTESupport()) {
            Log.d(TAG, "isShowConfirmDialog(): isLTESupport() or isVoLTESupport() not support");
            return imsRegStatus;
        } else if (TelecomManager.from(mContext).isInCall()) {
            Log.d(TAG, "isShowConfirmDialog(): inCall, don't switch");
            return imsRegStatus;
        }

        try {
            int curDataSubId = SubscriptionManager.getDefaultDataSubId();
            ImsManager imsManager = ImsManager.getInstance(
                        mContext, SubscriptionManager.getPhoneId(curDataSubId));
                imsRegStatus = imsManager.getImsRegInfo();
            Log.d(TAG, "imsRegStatus = " + imsRegStatus + " curDataSubId: " + curDataSubId);
            if (imsRegStatus == false) {
                return false;
            }
        } catch (Exception ex) {
            Log.d(TAG, "Fail to get Ims Status");
        }
        int subId = getLTECapabilitySubId();
        int switchToSlotId = SubscriptionManager.getSlotId(switchtoSubId);
        Log.d(TAG, "subId:" + subId + "switchtoSubId:" + switchtoSubId);
        //currently no major 4G card
        if (subId < 0) {
            return false;
        } else if (imsRegStatus && switchtoSubId != subId) {
            //switch data to default 4G card, only data switch, 4G isn't switch
            Log.d(TAG, "switch Data from LTE SIM to 2G/3G SIM");
            return true;
        } else {
            Log.d(TAG, "default case");
            return false;
        }
        //return true;
    }
    /**
     * Set data state.
     * @param subid subscription id
     */
    public void setDataState(int subid) {
        if (subid != SubscriptionManager.INVALID_SUBSCRIPTION_ID) {
            int curConSubId = SubscriptionManager.getDefaultDataSubId();
            TelephonyManager tm = TelephonyManager.from(mContext);
            Log.d(TAG, "setDataState,curConSubId: " + curConSubId + "subid:" + subid);
            if (curConSubId == subid) {
                return;
            }
            if (tm.getDataEnabled(curConSubId) || tm.getDataEnabled(subid)) {
                Log.d(TAG, "setDataState: setDataEnabled curConSubId false");
                tm.setDataEnabled(curConSubId, false);
                tm.setDataEnabled(subid, true);
            }
         }
     }

    /**
     * Sets subId in dataUsage true.
     * @param subid subscription id
     */
    public void setDataStateEnable(int subid) {
        TelephonyManager tm = TelephonyManager.from(mContext);
        if (tm.getDataEnabled(subid)) {
            Log.d(TAG, "setDataStateEnable true subId:" + subid);
            tm.setDataEnabled(subid, true);
        }
    }
}
