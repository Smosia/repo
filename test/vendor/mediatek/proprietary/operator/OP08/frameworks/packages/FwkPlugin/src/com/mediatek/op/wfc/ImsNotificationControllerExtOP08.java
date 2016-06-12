package com.mediatek.op.wfc;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.android.ims.ImsConfig;
import com.android.ims.ImsManager;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.TelephonyIntents;

import com.mediatek.common.PluginImpl;
import com.mediatek.ims.WfcReasonInfo;

/** Interface that defineds all methos which are implemented in IMSN.
 */

@PluginImpl(interfaceName = "com.mediatek.common.wfc.IImsNotificationControllerExt")
public class ImsNotificationControllerExtOP08 extends DefaultImsNotificationControllerExt {

    private static final String TAG = "ImsNotificationControllerExtOP08";
    private static final String ACTION_WIRELESS_SETTINGS_LAUNCH
            = "android.settings.WIRELESS_SETTINGS";
    private static final int WFC_NOTIFICATION = 0x11;
    private static final int WFC_ERROR_TITLE =
            com.mediatek.internal.R.string.network_error_notification_title;
    private static final int WFC_ERROR_ICON =
            com.mediatek.internal.R.drawable.wfc_notify_registration_error;
    private static final String ACTION_LAUNCH_WFC_INVALID_SIM_ALERT
            = "mediatek.settings.WFC_INVALID_SIM_DIALOG_LAUNCH";

    BroadcastReceiver mBr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.d(TAG, "Intent action:" + intent.getAction());

            /* Restore screen lock state, even if intent received may not provide its effect */
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                mIsScreenLock = true;
                mNotificationManager.cancel(WFC_NOTIFICATION);
            } else {
                mIsScreenLock = mKeyguardManager.isKeyguardLocked();
            }
            Log.d(TAG, "on receive:screen lock:" + mIsScreenLock);

            if (intent.getAction().equals(TelephonyIntents.ACTION_SIM_STATE_CHANGED)) {
                String simState = intent.getStringExtra(IccCardConstants.INTENT_KEY_ICC_STATE);
                Log.d(TAG, "simState:" + simState);
                // Check Sim absent case
                if (simState.equals(IccCardConstants.INTENT_VALUE_ICC_ABSENT)) {
                    mIsSimPresent = false;
                    Log.d(TAG, "SIM not present");
                /* Reset sim present value on sim state:
                    1) NOT_READY: for normal sim insertion/removal cases
                    2) READY:for hot plugin case,in which state jumps directly from ABSENT to READY
                 */
                } else if (simState.equals(IccCardConstants.INTENT_VALUE_ICC_NOT_READY)
                        || simState.equals(IccCardConstants.INTENT_VALUE_ICC_READY)) {
                    mIsSimPresent = true;
                    Log.d(TAG, "Remove sim error if present");
                    mNotificationManager.cancel(WFC_NOTIFICATION);
                }
            }
            Log.d(TAG, "SIM present:" + mIsSimPresent);
            if (isWifiEnabled()) {
                // Show SIM error only if WFC is enabled by user & Wifi enabled
                if (ImsManager.isWfcEnabledByUser(context) && !mIsSimPresent) {
                    displayWfcErrorNotification();
                    return;
                }
            } else {
                if (!mIsSimPresent) {
                    Log.d(TAG, "Wifi Disabled, remove no sim error noti, if present");
                    mNotificationManager.cancel(WFC_NOTIFICATION);
                }
            }
        }
    };


    private Context mContext;
    private boolean mIsSimPresent = true;
    private boolean mIsScreenLock = false;

    private NotificationManager mNotificationManager;
    private WifiManager mWifiManager;
    private KeyguardManager mKeyguardManager;
    private ContentObserver mWfcSwitchContentObserver;

    /** Constructor.
     * @param context context
     */
    public ImsNotificationControllerExtOP08(Context context) {
        mContext = context;
        mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        mIsScreenLock =  mKeyguardManager.isKeyguardLocked();
    }

    /**
     * Register receiver, observer, listener etc..
     * @param context context
     * @return
     */
    @Override
    public void register(Context context) {
        mContext = context;
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(TelephonyIntents.ACTION_SIM_STATE_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(mBr, filter);
        registerForWfcSwitchChange();
    }

    /**
     * unregister receiver, observer, listener etc..
     * @param context context
     * @return
     */
    @Override
    public void unRegister(Context context) {
        context.unregisterReceiver(mBr);
        unRegisterForWfcSwitchChange();
    }


    private void displayWfcErrorNotification() {
        Log.d(TAG, "in error handling, screen lock:" + mIsScreenLock);
        if (!mIsScreenLock) {
            Notification noti = new Notification.Builder(mContext)
                       .setContentTitle(mContext.getResources().getString(WFC_ERROR_TITLE))
                       .setContentText(mContext.getResources().getString(WfcReasonInfo
                               .getImsStatusCodeString(WfcReasonInfo
                               .CODE_WFC_INCORRECT_SIM_CARD_ERROR)))
                       .setSmallIcon(WFC_ERROR_ICON)
                       .setOngoing(true)
                       .setVisibility(Notification.VISIBILITY_SECRET)
                       .build();
            Intent intent;
            intent = new Intent(ACTION_LAUNCH_WFC_INVALID_SIM_ALERT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            noti.contentIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
            noti.flags |= Notification.FLAG_NO_CLEAR;
            mNotificationManager.notify(WFC_NOTIFICATION, noti);
            Log.d(TAG, "showing sim error notification");
        }
    }

    /* Observes WFC settings changes. Needed for cases when WFC is switch OFF but
     * state_changes intent is received. Ex: WFC error & user switches WCF OFF.
     */
    private void registerForWfcSwitchChange() {
        mWfcSwitchContentObserver = new ContentObserver(new Handler()) {

            @Override
            public void onChange(boolean selfChange) {
                this.onChange(selfChange, Settings.Global.getUriFor(Settings
                        .Global.WFC_IMS_ENABLED));
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                if (Settings.Global.getInt(mContext.getContentResolver(),
                    Settings.Global.WFC_IMS_ENABLED,
                    ImsConfig.FeatureValueConstants.OFF)
                    == ImsConfig.FeatureValueConstants.OFF) {
                    Log.d(TAG, "contentObserver:WFC OFF");
                    if (!mIsSimPresent) {
                        Log.d(TAG, "remove no sim error");
                        mNotificationManager.cancel(WFC_NOTIFICATION);
                    }
                } else {
                    Log.d(TAG, "contentObserver:WFC ON");
                    if (isWifiEnabled() && !mIsSimPresent) {
                        Log.d(TAG, "show error notification");
                        displayWfcErrorNotification();
                    }
                }
            }
        };
        mContext.getContentResolver().registerContentObserver(
                Settings.Global.getUriFor(Settings.Global.WFC_IMS_ENABLED),
                false, mWfcSwitchContentObserver);
    }

    private void unRegisterForWfcSwitchChange() {
        mContext.getContentResolver().unregisterContentObserver(mWfcSwitchContentObserver);
        mWfcSwitchContentObserver = null;
    }

    private boolean isWifiEnabled() {
        int wifiState = mWifiManager.getWifiState();
        Log.d(TAG, "wifi state:" + wifiState);
        return (wifiState != WifiManager.WIFI_STATE_DISABLED);
    }
}

