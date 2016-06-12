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

package com.mediatek.filemanager;

import java.net.InetAddress;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.mediatek.filemanager.service.FileManagerService;
import com.mediatek.filemanager.utils.FileUtils;
import com.mediatek.filemanager.utils.LogUtils;
import com.mediatek.filemanager.utils.PDebug;


public class FileManagerApplication extends Application {
    public static final String TAG = "FileManagerApplication";
    private static FileManagerApplication mInstance;
    private final int NOTIFICATIONID = 7890;
    private static int mScreenWidth;

    @Override
    public void onCreate() {
        PDebug.Start("FileManagerApplication - onCreate");
        super.onCreate();
        mInstance = this;
        if (startService(new Intent(this.getApplicationContext(),
                FileManagerService.class)) == null) {
            LogUtils.e(TAG, "startService Fails");
        }
        PDebug.End("FileManagerApplication - onCreate");
    }
    
    public static Context getInstance() {
        return mInstance;
    }
    
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mInstance.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        return mScreenWidth;
    }
    
    /**
     * Get the version from the manifest.
     * 
     * @return The version as a String.
     */
    public static String getVersion() {
        Context context = getInstance();
        String packageName = context.getPackageName();
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getPackageInfo(packageName, 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Unable to find the name " + packageName
                    + " in the package");
            return null;
        }
    }

    public void setupNotification(Context context) {
        Log.d(TAG, "Setting up the notification");
        // Get NotificationManager reference
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager) context
                .getSystemService(ns);

        // get ip address
        InetAddress address = FileUtils.getLocalInetAddress();
        if (address == null) {
            Log.w(TAG, "Unable to retreive the local ip address");
            return;
        }
        String iptext = FileUtils.FTP + address.getHostAddress()
        + FileUtils.FTP_COLON + FileUtils.PORT;

        // Instantiate a Notification
        int icon = R.drawable.notification;
        CharSequence tickerText = String.format(
                context.getString(R.string.notif_server_starting), iptext);
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);

        // Define Notification's message and Intent
        CharSequence contentTitle = context.getString(R.string.notif_title);
        CharSequence contentText = iptext;

        Intent notificationIntent = new Intent(context, FileManagerOperationActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.setAction(FileUtils.FTP_NOTIFICATION_ACTION);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,
                contentIntent);
        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        // Pass Notification to NotificationManager
        nm.notify(NOTIFICATIONID, notification);

        Log.d(TAG, "Notication setup done");
    }

    public void clearNotification(Context context) {
        Log.d(TAG, "Clearing the notifications");
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager) context
                .getSystemService(ns);
        nm.cancelAll();
        Log.d(TAG, "Cleared notification");
    }
}
