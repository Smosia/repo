/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.android.launcher3;

import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.android.launcher3.compat.LauncherActivityInfoCompat;
import com.android.launcher3.compat.UserManagerCompat;
import com.android.launcher3.compat.UserHandleCompat;

import com.mediatek.launcher3.ext.AllApps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Represents an app in AllAppsView.
 */
public class AppInfo extends ItemInfo {
    private static final String TAG = "Launcher3.AppInfo";

    /**
     * The intent used to start the application.
     */
    Intent intent;

    /**
     * A bitmap version of the application icon.
     */
    Bitmap iconBitmap;

    /**
     * The time at which the app was first installed.
     */
    long firstInstallTime;

    ComponentName componentName;

    /**
     * M: The application icon is show or hide for CT.
     */
    boolean isVisible = true;
    boolean stateChanged;

    static final int DOWNLOADED_FLAG = 1;
    static final int UPDATED_SYSTEM_APP_FLAG = 2;

    int flags = 0;
    int pkgLauncherCount;// Add for app sort feature by wangjian

    AppInfo() {
        itemType = LauncherSettings.BaseLauncherColumns.ITEM_TYPE_SHORTCUT;
    }

    public Intent getIntent() {
        return intent;
    }

    protected Intent getRestoredIntent() {
        return null;
    }

    /**
     * Must not hold the Context.
     */
    public AppInfo(Context context, LauncherActivityInfoCompat info, UserHandleCompat user,
            IconCache iconCache, HashMap<Object, CharSequence> labelCache) {
        this.componentName = info.getComponentName();
        this.container = ItemInfo.NO_ID;

        flags = initFlags(info);
        firstInstallTime = info.getFirstInstallTime();
        iconCache.getTitleAndIcon(this, info, labelCache);
        intent = makeLaunchIntent(context, info, user);
        this.user = user;
    }

    public static int initFlags(LauncherActivityInfoCompat info) {
        int appFlags = info.getApplicationInfo().flags;
        int flags = 0;
        if ((appFlags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) == 0) {
            flags |= DOWNLOADED_FLAG;

            if ((appFlags & android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                flags |= UPDATED_SYSTEM_APP_FLAG;
            }
        }
        return flags;
    }

	//M. add
    public static int initFlags(PackageInfo pi) {
        int appFlags = pi.applicationInfo.flags;
        int flags = 0;
        if ((appFlags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) == 0) {
            flags |= DOWNLOADED_FLAG;

            if ((appFlags & android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                flags |= UPDATED_SYSTEM_APP_FLAG;
            }
        }
        return flags;
    }

	//M.
	public static long initFirstInstallTime(PackageInfo pi) {
		return pi.firstInstallTime;
	}
	

    public AppInfo(AppInfo info) {
        super(info);
        componentName = info.componentName;
        title = info.title.toString();
        intent = new Intent(info.intent);
        flags = info.flags;
        firstInstallTime = info.firstInstallTime;
        iconBitmap = info.iconBitmap;
        pkgLauncherCount = info.pkgLauncherCount;// Add for app sort feature by wangjian
    }

    @Override
    public String toString() {
        return "ApplicationInfo(title=" + title.toString() + " id=" + this.id
                + " type=" + this.itemType + " container=" + this.container
                + " screen=" + screenId + " cellX=" + cellX + " cellY=" + cellY
                + " spanX=" + spanX + " spanY=" + spanY + " dropPos=" + Arrays.toString(dropPos)
                + " user=" + user + ")";
    }

    public static void dumpApplicationInfoList(String tag, String label, ArrayList<AppInfo> list) {
        Log.d(tag, label + " size=" + list.size());
        for (AppInfo info: list) {
            Log.d(tag, "   title=\"" + info.title + "\" iconBitmap="
                    + info.iconBitmap + " firstInstallTime="
                    + info.firstInstallTime);
        }
    }

    public ShortcutInfo makeShortcut() {
        return new ShortcutInfo(this);
    }

    public static Intent makeLaunchIntent(Context context, LauncherActivityInfoCompat info,
            UserHandleCompat user) {
        long serialNumber = UserManagerCompat.getInstance(context).getSerialNumberForUser(user);
        return new Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .setComponent(info.getComponentName())
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            .putExtra(EXTRA_PROFILE, serialNumber);
    }

    /**
     * M: set app flag and record first install time.
     *
     * @param pm
     * @param packageName
     */
    public void setFlagAndInstallTime(final PackageManager pm, String packageName) {
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            flags = initFlags(pi);
            firstInstallTime = initFirstInstallTime(pi);
        } catch (NameNotFoundException e) {
            Log.d(TAG, "PackageManager.getApplicationInfo failed for " + packageName);
        }
    }
    
    
    /**
     * M: set app flag and record first install time.
     *
     * @param pm
     */
    public void setFlagAndInstallTime(final PackageManager pm) {
        String packageName = getPackageName();
        setFlagAndInstallTime(pm, packageName);
    }

    /**
     * M: Returns the package name that the shortcut's intent will resolve to, or
     * an empty string if none exists.
     * 
     * @return the package name.
     */
    String getPackageName() {
        String packageName = "";
        if (intent != null) {
            packageName = intent.getPackage();
            if (packageName == null && intent.getComponent() != null) {
                packageName = intent.getComponent().getPackageName();
            }        
        }
        return packageName;
    }

    /// M: Customize database columns for app items.
    /*@Override
    void onAddToDatabase(ContentValues values) {
        String titleStr = (title != null) ? title.toString() : null;
        values.put(LauncherSettings.BaseLauncherColumns.TITLE, titleStr);

        String uri = intent != null ? intent.toUri(0) : null;
        values.put(AllApps.INTENT, uri);
    }*/
    
 // Add for app sort feature by wangjian
 	public void initLaunchCount(String packageName) {
 		IUsageStatsManager mUsageStatsService = IUsageStatsManager.Stub.asInterface(ServiceManager
 				.getService("usagestats"));
 		List<UsageStats> stats;
 		ParceledListSlice<UsageStats> slice;
 		if (mUsageStatsService == null) {
 			Log.e(TAG, "Failed to retrieve usagestats service");
 			return;
 		}

 		try {
 			Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -5);
            slice = mUsageStatsService.queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                    cal.getTimeInMillis(), System.currentTimeMillis(),
                    "com.android.launcher3");
 		} catch (RemoteException e) {
 			Log.e(TAG, "Failed initializing usage stats service");
 			return;
 		}
 		if (slice == null) {
 			return;
 		}
 		stats = slice.getList();
 		for (UsageStats ps : stats) {

 			if (ps.getPackageName().compareTo(packageName) == 0) {

 				pkgLauncherCount = ps.mLaunchCount;

 			}
 			return;
 		}
 		pkgLauncherCount = 0;
 	}

 	public int updateLaunchCount(String packageName) {
 		IUsageStatsManager mUsageStatsService = IUsageStatsManager.Stub.asInterface(ServiceManager
 				.getService("usagestats"));
 		List<UsageStats> stats;
 		ParceledListSlice<UsageStats> slice;
 		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -5);
 		if (mUsageStatsService == null) {
 			Log.e(TAG, "Failed to retrieve usagestats service");
 			return 0;
 		}

 		try {
 			slice = mUsageStatsService.queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                    cal.getTimeInMillis(), System.currentTimeMillis(),
                    "com.android.launcher3");
 		} catch (RemoteException e) {
 			Log.e(TAG, "Failed initializing usage stats service");
 			return 0;
 		}
 		if (slice == null) {
 			return 0;
 		}
 		stats = slice.getList();
 		for (UsageStats ps : stats) {

 			if (ps.getPackageName().compareTo(packageName) == 0) {
 				return ps.mLaunchCount;

 			}
 		}
 		return 0;
 	}
 	// Add for app sort feature by wangjian
}