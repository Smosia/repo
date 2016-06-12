/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
 */


package com.mediatek.common.mom;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/** @hide */
public final class BootReceiverPolicy {
    private static List<String> mBootIntentFilter = new ArrayList<String>();

    /**
     * Customized boot related intent here. MoMS will monitors
     * applications which receives these intents.
     */
    static  {
        // Must handle this intent, don't removed.
        mBootIntentFilter.add(Intent.ACTION_BOOT_COMPLETED);
        mBootIntentFilter.add("android.intent.action.ACTION_BOOT_IPO");
        // { A : add by kui.li start 2016-03-10
        mBootIntentFilter.add(Intent.ACTION_AIRPLANE_MODE_CHANGED); //"android.intent.action.AIRPLANE_MODE";
        mBootIntentFilter.add(Intent.ACTION_ALL_APPS); //"android.intent.action.ALL_APPS";
        mBootIntentFilter.add(Intent.ACTION_ANSWER); //"android.intent.action.ANSWER";
        mBootIntentFilter.add(Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED); //"android.intent.action.APPLICATION_RESTRICTIONS_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_APP_ERROR); //"android.intent.action.APP_ERROR";
        mBootIntentFilter.add(Intent.ACTION_ASSIST); //"android.intent.action.ASSIST";
        mBootIntentFilter.add(Intent.ACTION_ATTACH_DATA); //"android.intent.action.ATTACH_DATA";
        mBootIntentFilter.add(Intent.ACTION_BATTERY_CHANGED); //"android.intent.action.BATTERY_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_BATTERY_LOW); //"android.intent.action.BATTERY_LOW";
        mBootIntentFilter.add(Intent.ACTION_BATTERY_OKAY); //"android.intent.action.BATTERY_OKAY";
        mBootIntentFilter.add(Intent.ACTION_BUG_REPORT); //"android.intent.action.BUG_REPORT";
        mBootIntentFilter.add(Intent.ACTION_CALL); //"android.intent.action.CALL";
        mBootIntentFilter.add(Intent.ACTION_CALL_BUTTON); //"android.intent.action.CALL_BUTTON";
        mBootIntentFilter.add(Intent.ACTION_CAMERA_BUTTON); //"android.intent.action.CAMERA_BUTTON";
        mBootIntentFilter.add(Intent.ACTION_CHOOSER); //"android.intent.action.CHOOSER";
        mBootIntentFilter.add(Intent.ACTION_CLOSE_SYSTEM_DIALOGS); //"android.intent.action.CLOSE_SYSTEM_DIALOGS";
        mBootIntentFilter.add(Intent.ACTION_CONFIGURATION_CHANGED); //"android.intent.action.CONFIGURATION_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_CREATE_DOCUMENT); //"android.intent.action.CREATE_DOCUMENT";
        mBootIntentFilter.add(Intent.ACTION_CREATE_SHORTCUT); //"android.intent.action.CREATE_SHORTCUT";
        mBootIntentFilter.add(Intent.ACTION_DATE_CHANGED); //"android.intent.action.DATE_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_DEFAULT); //"android.intent.action.VIEW";
        mBootIntentFilter.add(Intent.ACTION_DELETE); //"android.intent.action.DELETE";
        mBootIntentFilter.add(Intent.ACTION_DEVICE_STORAGE_LOW); //"android.intent.action.DEVICE_STORAGE_LOW";
        mBootIntentFilter.add(Intent.ACTION_DEVICE_STORAGE_OK); //"android.intent.action.DEVICE_STORAGE_OK";
        mBootIntentFilter.add(Intent.ACTION_DIAL); //"android.intent.action.DIAL";
        mBootIntentFilter.add(Intent.ACTION_DOCK_EVENT); //"android.intent.action.DOCK_EVENT";
        mBootIntentFilter.add(Intent.ACTION_DREAMING_STARTED); //"android.intent.action.DREAMING_STARTED";
        mBootIntentFilter.add(Intent.ACTION_DREAMING_STOPPED); //"android.intent.action.DREAMING_STOPPED";
        mBootIntentFilter.add(Intent.ACTION_EDIT); //"android.intent.action.EDIT";
        mBootIntentFilter.add(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE); //"android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
        mBootIntentFilter.add(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE); //"android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
        mBootIntentFilter.add(Intent.ACTION_FACTORY_TEST); //"android.intent.action.FACTORY_TEST";
        mBootIntentFilter.add(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT";
        mBootIntentFilter.add(Intent.ACTION_GET_RESTRICTION_ENTRIES); //"android.intent.action.GET_RESTRICTION_ENTRIES";
        mBootIntentFilter.add(Intent.ACTION_GTALK_SERVICE_CONNECTED); //"android.intent.action.GTALK_CONNECTED";
        mBootIntentFilter.add(Intent.ACTION_GTALK_SERVICE_DISCONNECTED); //"android.intent.action.GTALK_DISCONNECTED";
        mBootIntentFilter.add(Intent.ACTION_HEADSET_PLUG); //"android.intent.action.HEADSET_PLUG";
        mBootIntentFilter.add(Intent.ACTION_INPUT_METHOD_CHANGED); //"android.intent.action.INPUT_METHOD_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_INSERT); //"android.intent.action.INSERT";
        mBootIntentFilter.add(Intent.ACTION_INSERT_OR_EDIT); //"android.intent.action.INSERT_OR_EDIT";
        mBootIntentFilter.add(Intent.ACTION_INSTALL_PACKAGE); //"android.intent.action.INSTALL_PACKAGE";
        mBootIntentFilter.add(Intent.ACTION_INTENT_FILTER_NEEDS_VERIFICATION); //"android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION";
        mBootIntentFilter.add(Intent.ACTION_LOCALE_CHANGED); //"android.intent.action.LOCALE_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_MAIN); //"android.intent.action.MAIN";
        mBootIntentFilter.add(Intent.ACTION_MANAGED_PROFILE_ADDED); //"android.intent.action.MANAGED_PROFILE_ADDED";
        mBootIntentFilter.add(Intent.ACTION_MANAGED_PROFILE_REMOVED); //"android.intent.action.MANAGED_PROFILE_REMOVED";
        mBootIntentFilter.add(Intent.ACTION_MANAGE_NETWORK_USAGE); //"android.intent.action.MANAGE_NETWORK_USAGE";
        mBootIntentFilter.add(Intent.ACTION_MANAGE_PACKAGE_STORAGE); //"android.intent.action.MANAGE_PACKAGE_STORAGE";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_BAD_REMOVAL); //"android.intent.action.MEDIA_BAD_REMOVAL";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_BUTTON); //"android.intent.action.MEDIA_BUTTON";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_CHECKING); //"android.intent.action.MEDIA_CHECKING";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_EJECT); //"android.intent.action.MEDIA_EJECT";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_MOUNTED); //"android.intent.action.MEDIA_MOUNTED";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_NOFS); //"android.intent.action.MEDIA_NOFS";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_REMOVED); //"android.intent.action.MEDIA_REMOVED";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_SCANNER_FINISHED); //"android.intent.action.MEDIA_SCANNER_FINISHED";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //"android.intent.action.MEDIA_SCANNER_SCAN_FILE";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_SCANNER_STARTED); //"android.intent.action.MEDIA_SCANNER_STARTED";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_SHARED); //"android.intent.action.MEDIA_SHARED";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_UNMOUNTABLE); //"android.intent.action.MEDIA_UNMOUNTABLE";
        mBootIntentFilter.add(Intent.ACTION_MEDIA_UNMOUNTED); //"android.intent.action.MEDIA_UNMOUNTED";
        mBootIntentFilter.add(Intent.ACTION_MY_PACKAGE_REPLACED); //"android.intent.action.MY_PACKAGE_REPLACED";
        mBootIntentFilter.add(Intent.ACTION_NEW_OUTGOING_CALL); //"android.intent.action.NEW_OUTGOING_CALL";
        mBootIntentFilter.add(Intent.ACTION_OPEN_DOCUMENT); //"android.intent.action.OPEN_DOCUMENT";
        mBootIntentFilter.add(Intent.ACTION_OPEN_DOCUMENT_TREE); //"android.intent.action.OPEN_DOCUMENT_TREE";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_ADDED); //"android.intent.action.PACKAGE_ADDED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_CHANGED); //"android.intent.action.PACKAGE_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_DATA_CLEARED); //"android.intent.action.PACKAGE_DATA_CLEARED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_FIRST_LAUNCH); //"android.intent.action.PACKAGE_FIRST_LAUNCH";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_FULLY_REMOVED); //"android.intent.action.PACKAGE_FULLY_REMOVED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_INSTALL); //"android.intent.action.PACKAGE_INSTALL";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_NEEDS_VERIFICATION); //"android.intent.action.PACKAGE_NEEDS_VERIFICATION";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_REMOVED); //"android.intent.action.PACKAGE_REMOVED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_REPLACED); //"android.intent.action.PACKAGE_REPLACED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_RESTARTED); //"android.intent.action.PACKAGE_RESTARTED";
        mBootIntentFilter.add(Intent.ACTION_PACKAGE_VERIFIED); //"android.intent.action.PACKAGE_VERIFIED";
        mBootIntentFilter.add(Intent.ACTION_PASTE); //"android.intent.action.PASTE";
        mBootIntentFilter.add(Intent.ACTION_PICK); //"android.intent.action.PICK";
        mBootIntentFilter.add(Intent.ACTION_PICK_ACTIVITY); //"android.intent.action.PICK_ACTIVITY";
        mBootIntentFilter.add(Intent.ACTION_POWER_CONNECTED); //"android.intent.action.ACTION_POWER_CONNECTED";
        mBootIntentFilter.add(Intent.ACTION_POWER_DISCONNECTED); //"android.intent.action.ACTION_POWER_DISCONNECTED";
        mBootIntentFilter.add(Intent.ACTION_POWER_USAGE_SUMMARY); //"android.intent.action.POWER_USAGE_SUMMARY";
        mBootIntentFilter.add(Intent.ACTION_PROCESS_TEXT); //"android.intent.action.PROCESS_TEXT";
        mBootIntentFilter.add(Intent.ACTION_PROVIDER_CHANGED); //"android.intent.action.PROVIDER_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_QUERY_PACKAGE_RESTART); //"android.intent.action.QUERY_PACKAGE_RESTART";
        mBootIntentFilter.add(Intent.ACTION_QUICK_CLOCK); //"android.intent.action.QUICK_CLOCK";
        mBootIntentFilter.add(Intent.ACTION_REBOOT); //"android.intent.action.REBOOT";
        mBootIntentFilter.add(Intent.ACTION_RUN); //"android.intent.action.RUN";
        mBootIntentFilter.add(Intent.ACTION_SCREEN_OFF); //"android.intent.action.SCREEN_OFF";
        mBootIntentFilter.add(Intent.ACTION_SCREEN_ON); //"android.intent.action.SCREEN_ON";
        mBootIntentFilter.add(Intent.ACTION_SEARCH); //"android.intent.action.SEARCH";
        mBootIntentFilter.add(Intent.ACTION_SEARCH_LONG_PRESS); //"android.intent.action.SEARCH_LONG_PRESS";
        mBootIntentFilter.add(Intent.ACTION_SEND); //"android.intent.action.SEND";
        mBootIntentFilter.add(Intent.ACTION_SENDTO); //"android.intent.action.SENDTO";
        mBootIntentFilter.add(Intent.ACTION_SEND_MULTIPLE); //"android.intent.action.SEND_MULTIPLE";
        mBootIntentFilter.add(Intent.ACTION_SET_WALLPAPER); //"android.intent.action.SET_WALLPAPER";
        mBootIntentFilter.add(Intent.ACTION_SHUTDOWN); //"android.intent.action.ACTION_SHUTDOWN";
        mBootIntentFilter.add(Intent.ACTION_SYNC); //"android.intent.action.SYNC";
        mBootIntentFilter.add(Intent.ACTION_SYSTEM_TUTORIAL); //"android.intent.action.SYSTEM_TUTORIAL";
        mBootIntentFilter.add(Intent.ACTION_TIMEZONE_CHANGED); //"android.intent.action.TIMEZONE_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_TIME_CHANGED); //"android.intent.action.TIME_SET";
        mBootIntentFilter.add(Intent.ACTION_TIME_TICK); //"android.intent.action.TIME_TICK";
        mBootIntentFilter.add(Intent.ACTION_UID_REMOVED); //"android.intent.action.UID_REMOVED";
        mBootIntentFilter.add(Intent.ACTION_ULTRA_POWER_MODE); //"android.intent.action.ULTRA_POWER_MODE";
        mBootIntentFilter.add(Intent.ACTION_UMS_CONNECTED); //"android.intent.action.UMS_CONNECTED";
        mBootIntentFilter.add(Intent.ACTION_UMS_DISCONNECTED); //"android.intent.action.UMS_DISCONNECTED";
        mBootIntentFilter.add(Intent.ACTION_UNINSTALL_PACKAGE); //"android.intent.action.UNINSTALL_PACKAGE";
        mBootIntentFilter.add(Intent.ACTION_USER_BACKGROUND); //"android.intent.action.USER_BACKGROUND";
        mBootIntentFilter.add(Intent.ACTION_USER_FOREGROUND); //"android.intent.action.USER_FOREGROUND";
        mBootIntentFilter.add(Intent.ACTION_USER_INITIALIZE); //"android.intent.action.USER_INITIALIZE";
        mBootIntentFilter.add(Intent.ACTION_USER_PRESENT); //"android.intent.action.USER_PRESENT";
        mBootIntentFilter.add(Intent.ACTION_VIEW); //"android.intent.action.VIEW";
        mBootIntentFilter.add(Intent.ACTION_VOICE_COMMAND); //"android.intent.action.VOICE_COMMAND";
        mBootIntentFilter.add(Intent.ACTION_WALLPAPER_CHANGED); //"android.intent.action.WALLPAPER_CHANGED";
        mBootIntentFilter.add(Intent.ACTION_WEB_SEARCH); //"android.intent.action.WEB_SEARCH";

        mBootIntentFilter.add(ConnectivityManager.CONNECTIVITY_ACTION); //"android.net.conn.CONNECTIVITY_CHANGE";
        mBootIntentFilter.add(ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED); //"android.net.conn.BACKGROUND_DATA_SETTING_CHANGED";
        mBootIntentFilter.add(ConnectivityManager.ACTION_CAPTIVE_PORTAL_SIGN_IN); //"android.net.conn.CAPTIVE_PORTAL";

        mBootIntentFilter.add(WifiManager.WIFI_STATE_CHANGED_ACTION); //"android.net.wifi.WIFI_STATE_CHANGED";
        mBootIntentFilter.add(WifiManager.NETWORK_STATE_CHANGED_ACTION); //"android.net.wifi.STATE_CHANGE";
        mBootIntentFilter.add(WifiManager.NETWORK_IDS_CHANGED_ACTION); //"android.net.wifi.NETWORK_IDS_CHANGED";
        mBootIntentFilter.add(WifiManager.RSSI_CHANGED_ACTION); //"android.net.wifi.RSSI_CHANGED";
        mBootIntentFilter.add(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION); //"android.net.wifi.SCAN_RESULTS";
        mBootIntentFilter.add(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION); //"android.net.wifi.supplicant.CONNECTION_CHANGE";
        mBootIntentFilter.add(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION); //"android.net.wifi.supplicant.STATE_CHANGE";
        mBootIntentFilter.add(WifiManager.WIFI_CREDENTIAL_CHANGED_ACTION); //"android.net.wifi.WIFI_CREDENTIAL_CHANGED";
        mBootIntentFilter.add(WifiManager.ACTION_PICK_WIFI_NETWORK); //"android.net.wifi.PICK_WIFI_NETWORK";
        mBootIntentFilter.add(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE); //"android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE";
        mBootIntentFilter.add(WifiManager.CONFIGURED_NETWORKS_CHANGED_ACTION); //"android.net.wifi.CONFIGURED_NETWORKS_CHANGE";

        mBootIntentFilter.add("android.accounts.LOGIN_ACCOUNTS_CHANGED");
        mBootIntentFilter.add("android.provider.Telephony.SMS_RECEIVED");
        mBootIntentFilter.add("com.google.android.c2dm.intent.RECEIVE");
        mBootIntentFilter.add("com.google.android.c2dm.intent.REGISTRATION");
        mBootIntentFilter.add("com.android.vending.INSTALL_REFERRER");
        mBootIntentFilter.add("com.google.android.gms.gcm.ACTION_SCHEDULE");
        mBootIntentFilter.add("android.intent.action.BADGE_COUNT_UPDATE");
        mBootIntentFilter.add("com.android.contacts.ACTION_PHB_LOAD_FINISHED");
        mBootIntentFilter.add("android.media.VIBRATE_SETTING_CHANGED");
        mBootIntentFilter.add("android.intent.action.DROPBOX_ENTRY_ADDED");
        mBootIntentFilter.add("com.mediatek.search.SEARCH_ENGINE_CHANGED");
        mBootIntentFilter.add("com.google.android.intent.action.GCM_RECONNECT");
        mBootIntentFilter.add("com.google.android.gms.security.snet.BOOT_COMPLETED");
        mBootIntentFilter.add("com.google.android.gms.update.STATUS_CHANGED");
        mBootIntentFilter.add("com.google.android.checkin.CHECKIN_COMPLETE");
        mBootIntentFilter.add("android.intent.action.ACTION_SUBINFO_CONTENT_CHANGE");
        mBootIntentFilter.add("com.android.server.action.NETWORK_STATS_UPDATED");
        mBootIntentFilter.add("android.intent.action.PRECISE_DATA_CONNECTION_STATE_CHANGED");
        mBootIntentFilter.add("com.android.providers.calendar.intent.CalendarProvider2");
        mBootIntentFilter.add("android.net.conn.DATA_ACTIVITY_CHANGE");
        mBootIntentFilter.add("android.telecom.action.PHONE_ACCOUNT_REGISTERED");
        mBootIntentFilter.add("com.android.deskclock.ON_QUARTER_HOUR");
        mBootIntentFilter.add("com.android.mail.ACTION_NOTIFY_DATASET_CHANGED");
        mBootIntentFilter.add("com.mediatek.action.UNREAD_CHANGED");
        mBootIntentFilter.add("android.search.action.SEARCHABLES_CHANGED");
        mBootIntentFilter.add("com.google.android.gm.intent.ACTION_PROVIDER_CREATED");
        mBootIntentFilter.add("com.android.mail.action.update_notification");
        mBootIntentFilter.add("com.google.android.apps.hangouts.RENEW_ACCOUNT_REGISTRATION");
        mBootIntentFilter.add("com.mediatek.lbs.action.NLP_BIND_REQUEST");
        mBootIntentFilter.add("com.cootek.presentation.action.START_WORK");
        mBootIntentFilter.add("org.simalliance.openmobileapi.action.SIM1_STATE_CHANGED");
        mBootIntentFilter.add("com.android.launcher.action.ACTION_PACKAGE_DOWNLOADING");
        mBootIntentFilter.add("android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED");
        mBootIntentFilter.add("android.intent.action.RESET_MAIN_ACCESS");
        mBootIntentFilter.add("com.google.android.gcm.CONNECTED");
        mBootIntentFilter.add("com.google.android.gcm.DISCONNECTED");
        mBootIntentFilter.add("com.google.android.gms.gcm.ACTION_CHECK_QUEUE");
        mBootIntentFilter.add("com.google.android.apps.photos.actionqueue.INTERNAL_ACTION");
        mBootIntentFilter.add("com.google.android.apps.photos.jobqueue.EXECUTE_JOBS");
        mBootIntentFilter.add("android.stkDialog.TIMEOUT");
        mBootIntentFilter.add("android.intent.action.stk.session_end");
        mBootIntentFilter.add("com.android.internal.telephony.data-stall");
        mBootIntentFilter.add("android.content.syncmanager.SYNC_ALARM");
        mBootIntentFilter.add("com.cootek.presentation.action.CHECK_STATUS_TOAST");
        mBootIntentFilter.add("com.cootek.presentation.action.CHECK_DUMMY_TOAST");
        mBootIntentFilter.add("com.cootek.presentation.action.CHECK_DESKTOP_SHORTCUT_TOAST");
        mBootIntentFilter.add("action_hide_recents_activity");
        mBootIntentFilter.add("android.intent.action.CLEAR_DNS_CACHE");
        mBootIntentFilter.add("com.android.server.NetworkTimeUpdateService.action.POLL");
        mBootIntentFilter.add("com.android.debug.count.start.time");
		
        // A : end }
    }

    public static List<String> getBootPolicy() {
        return mBootIntentFilter;
    }

    public static boolean match(String intent) {
        return mBootIntentFilter.contains(intent);
    }
}
