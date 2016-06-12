/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.sim;

import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.TelephonyIntents;
import com.android.settings.R;
import com.android.settings.Settings.SimSettingsActivity;
import com.mediatek.settings.UtilsExt;
import com.mediatek.settings.cdma.CdmaUtils;
import com.mediatek.settings.ext.ISettingsMiscExt;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/// M: Add for CT 6M.
import com.mediatek.settings.FeatureOption;

import java.util.List;

//add BUG_ID:DWLEBM-159 dushuaihui 20160324(start)
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import java.util.Iterator;
import android.text.TextUtils;
//add BUG_ID:DWLEBM-159 dushuaihui 20160324(end)
public class SimSelectNotification extends BroadcastReceiver {
    private static final String TAG = "SimSelectNotification";
    private static final int NOTIFICATION_ID = 1;
	private Context mContext;//add BUG_ID:DWLEBM-159 dushuaihui 20160324

    @Override
    public void onReceive(Context context, Intent intent) {
        /// M: add for auto sanity
        if (UtilsExt.shouldDisableForAutoSanity()) {
            return;
        }
		
        mContext = context;//add BUG_ID:DWLEBM-159 dushuaihui 20160324
		
        /// @}

        /// M: for [C2K 2 SIM Warning] @{
        // can not listen to SIM_STATE_CHANGED because it happened even when SIM switch
        // (switch default data), in which case should not show the C2K 2 SIM warning
        // SIM_STATE_CHANGED is not exact, maybe ICC is ready or absent,
        // but SubscriptionController is not ready.
        List<SubscriptionInfo> subs = SubscriptionManager.from(context)
                .getActiveSubscriptionInfoList();
        final int detectedType = intent.getIntExtra(
                SubscriptionManager.INTENT_KEY_DETECT_STATUS, 0);
        Log.d(TAG, "sub info update, subs = " + subs + ", type = " + detectedType);
        if (detectedType == SubscriptionManager.EXTRA_VALUE_NOCHANGE) {
            return;
        }
        if (subs != null && subs.size() > 1) {
            CdmaUtils.checkCdmaSimStatus(context, subs.size());
        }
        /// @}
        final TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        final SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
        final int numSlots = telephonyManager.getSimCount();
        final boolean isInProvisioning = Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.DEVICE_PROVISIONED, 0) == 0;

        // Do not create notifications on single SIM devices or when provisiong i.e. Setup Wizard.
        if (numSlots < 2 /*|| isInProvisioning*/) {
            return;
        }

        // Cancel any previous notifications
        cancelNotification(context);

        // If sim state is not ABSENT or LOADED then ignore
        String simStatus = intent.getStringExtra(IccCardConstants.INTENT_KEY_ICC_STATE);
        if (!(IccCardConstants.INTENT_VALUE_ICC_ABSENT.equals(simStatus) ||
                IccCardConstants.INTENT_VALUE_ICC_LOADED.equals(simStatus))) {
            Log.d(TAG, "sim state is not Absent or Loaded");
            /// M: maybe ACTION_SUBINFO_RECORD_UPDATED is received, @{
            //  but ICC card don't loaded or absent
            /*
            return;
            */
            /// @}
        } else {
            Log.d(TAG, "simstatus = " + simStatus);
        }

        int state;
        for (int i = 0; i < numSlots; i++) {
            state = telephonyManager.getSimState(i);
            if (!(state == TelephonyManager.SIM_STATE_ABSENT
                    || state == TelephonyManager.SIM_STATE_READY
                    || state == TelephonyManager.SIM_STATE_UNKNOWN)) {
                Log.d(TAG, "All sims not in valid state yet");
                /// M: maybe ACTION_SUBINFO_RECORD_UPDATED is received, @{
                //  but ICC card don't loaded or absent
                /*
                return;
                 */
                /// @}
            }
        }

        List<SubscriptionInfo> sil = subscriptionManager.getActiveSubscriptionInfoList();
        if (sil == null || sil.size() < 1) {
            Log.d(TAG, "Subscription list is empty");
            return;
        }

        // Clear defaults for any subscriptions which no longer exist
        subscriptionManager.clearDefaultsForInactiveSubIds();

        boolean dataSelected = SubscriptionManager.isUsableSubIdValue(
                SubscriptionManager.getDefaultDataSubId());
        boolean smsSelected = SubscriptionManager.isUsableSubIdValue(
                SubscriptionManager.getDefaultSmsSubId());

        // If data and sms defaults are selected, dont show notification (Calls default is optional)
        if (dataSelected && smsSelected) {
            Log.d(TAG, "Data & SMS default sims are selected. No notification");
            return;
        }

        // Create a notification to tell the user that some defaults are missing
        createNotification(context);

        /// M: for plug-in and CT 6M
        if (!isSimDialogNeeded(context)) {
            return;
        }

        if (sil.size() == 1) {
            //add BUG_ID:DWLEBM-159 dushuaihui 20160324(start)
            // If there is only one subscription, ask if user wants to use if for everything
            /*Intent newIntent = new Intent(context, SimDialogActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            newIntent.putExtra(SimDialogActivity.DIALOG_TYPE_KEY, SimDialogActivity.PREFERRED_PICK);
            newIntent.putExtra(SimDialogActivity.PREFERRED_SIM, sil.get(0).getSimSlotIndex());
            context.startActivity(newIntent);*/
			final SubscriptionInfo sir = sil.get(0);
			final int subId = sir.getSubscriptionId();
			setDefaultDataSubId(context, subId);
			setDefaultSmsSubId(context, subId);
			PhoneAccountHandle phoneAccountHandle = subscriptionIdToPhoneAccountHandle(subId);
			setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
            //add BUG_ID:DWLEBM-159 dushuaihui 20160324(end)
			
        } else if (!dataSelected) {
            // If there are mulitple, ensure they pick default data
            Intent newIntent = new Intent(context, SimDialogActivity.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            newIntent.putExtra(SimDialogActivity.DIALOG_TYPE_KEY, SimDialogActivity.DATA_PICK);
            context.startActivity(newIntent);
        }
    }
	
    //modify BUG_ID:DWLEBM-159 dushuaihui 20160324(start)
    private SubscriptionInfo findRecordBySlotId(List<SubscriptionInfo> list, int slotId){
        for(SubscriptionInfo info : list){
           int mSlotId = info.getSimSlotIndex();
            if(mSlotId == slotId){
		        return info;
            }
        }
        return null;
    }
    //modify BUG_ID:DWLEBM-159 dushuaihui 20160324(end)

    private void createNotification(Context context){
        final Resources resources = context.getResources();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_sim_card_alert_white_48dp)
                .setColor(context.getColor(R.color.sim_noitification))
                .setContentTitle(resources.getString(R.string.sim_notification_title))
                .setContentText(resources.getString(R.string.sim_notification_summary));

        /// M: for plug-in
        customizeSimDisplay(context, builder);

        Intent resultIntent = new Intent(context, SimSettingsActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void cancelNotification(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    ///---------------------------------------MTK-------------------------------------------------

    /**
     * only for plug-in, change "SIM" to "UIM/SIM".
     * @param context the context.
     * @param builder the notification builder.
     */
    private void customizeSimDisplay(
                    Context context,
                    NotificationCompat.Builder builder) {
        Resources resources = context.getResources();
        String title = resources.getString(R.string.sim_notification_title);
        String text = resources.getString(R.string.sim_notification_summary);

        ISettingsMiscExt miscExt = UtilsExt.getMiscPlugin(context);
        title = miscExt.customizeSimDisplayString(
                            title,
                            SubscriptionManager.INVALID_SUBSCRIPTION_ID);
        text = miscExt.customizeSimDisplayString(
                            text,
                            SubscriptionManager.INVALID_SUBSCRIPTION_ID);
        builder.setContentTitle(title);
        builder.setContentText(text);
    }

    /**
     *  add for plug-in and CT 6M.
     *  whether allowed show data pick dailog and preferred sim dialog.
     */
    private boolean isSimDialogNeeded(Context context) {
        boolean isNeeded = false;
        if (!FeatureOption.MTK_CT6M_SUPPORT) {
            isNeeded = UtilsExt.getSimManagmentExtPlugin(context).isSimDialogNeeded();
        }
        return isNeeded;
    }
	
    //add BUG_ID:DWLEBM-159 dushuaihui 20160324(start)
	private void setDefaultSmsSubId(final Context context, final int subId) {
		final SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
		subscriptionManager.setDefaultSmsSubId(subId);
	}
	
	private void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
		final TelecomManager telecomManager = TelecomManager.from(mContext);
		telecomManager.setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
	}
	
	private void setDefaultDataSubId(final Context context, final int subId) {
		final SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
		subscriptionManager.setDefaultDataSubId(subId);
	}
	
	private PhoneAccountHandle subscriptionIdToPhoneAccountHandle(final int subId) {
		final TelecomManager telecomManager = TelecomManager.from(mContext);
        final TelephonyManager telephonyManager = TelephonyManager.from(mContext);
		final Iterator<PhoneAccountHandle> phoneAccounts = telecomManager.getCallCapablePhoneAccounts().listIterator();
		
		if (phoneAccounts.hasNext()) {
			final PhoneAccountHandle phoneAccountHandle = phoneAccounts.next();
			final PhoneAccount phoneAccount = telecomManager.getPhoneAccount(phoneAccountHandle);
			final String phoneAccountId = phoneAccountHandle.getId();
			
			if(phoneAccount.hasCapabilities(PhoneAccount.CAPABILITY_SIM_SUBSCRIPTION)
					&& TextUtils.isDigitsOnly(phoneAccountId)
					&& telephonyManager.getSubIdForPhoneAccount(phoneAccount) == subId) {
				return phoneAccountHandle;
			}
		}
		return null;
	}
    //add BUG_ID:DWLEBM-159 dushuaihui 20160324(end)
}
