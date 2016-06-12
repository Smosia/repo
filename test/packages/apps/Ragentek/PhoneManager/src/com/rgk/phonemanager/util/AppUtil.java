package com.rgk.phonemanager.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class AppUtil {
	 List<ApplicationInfo> appList;
	public AppUtil(Context context) {
		appList=null;
		
		PackageManager pm = context.getPackageManager();
		appList = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
	}
	public static List<AppInfo> getAllAppInfo(Context context) {
		List<ResolveInfo> apps = new ArrayList<ResolveInfo>();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PackageManager pm = context.getPackageManager();
		apps = pm.queryIntentActivities(mainIntent, 0);
		List<AppInfo> appList = new ArrayList<AppInfo>();
		for (ResolveInfo resolveInfo : apps) {
			String appName = (String) resolveInfo.loadLabel(context
					.getPackageManager());
			Drawable appIcon = resolveInfo
					.loadIcon(context.getPackageManager());
			String pkgName = resolveInfo.activityInfo.packageName;
			String activityName = resolveInfo.activityInfo.name;
			AppInfo appInfo = new AppInfo(appName, pkgName, appIcon,
					activityName, 0);
			appList.add(appInfo);
			
		}
		return appList;
	}
	
	public static List<AppInfo> getSystemApp(Context context) {
		List<AppInfo> appList = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if (!filterApp(pi.applicationInfo)) {
				AppInfo info = new AppInfo();
				info.setAppIcon(pi.applicationInfo.loadIcon(pm));
				info.setPackageName(pi.packageName);
				String appName = pi.applicationInfo.loadLabel(pm).toString();
				info.setAppName(appName);
				String activityName = getActivityName(appName, pi.packageName,
						context);
				if(StrUtils.isEmpty(activityName)){
					continue;
				}
				info.setActivityName(activityName);
				info.setInstallDate(StrUtils.getTime(pi.firstInstallTime));
				info.setApkPath(pi.applicationInfo.sourceDir);
				appList.add(info);
			}
		}
		return appList;
	}

	public static boolean filterApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return true;
		}
		return false;
	}

	public static List<AppInfo> getUserAppInfo(Context context) {
		List<AppInfo> apps = new ArrayList<AppInfo>();

		PackageManager pm = context.getPackageManager();
		List<PackageInfo> pakageinfos = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo packageInfo : pakageinfos) {
			if(packageInfo.packageName.endsWith("com.rgk.phonemanager")){
				continue;
			}
			if (filterApp(packageInfo.applicationInfo)) {
				AppInfo info = new AppInfo();
				info.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
				info.setAppName(packageInfo.applicationInfo.loadLabel(pm)
						.toString());
				info.setPackageName(packageInfo.packageName);
				info.setInstallDate(StrUtils
						.getTime(packageInfo.firstInstallTime));
				apps.add(info);
			}
		}
		return apps;
	}

	public static AppInfo ifInstalledApp(String packageName, Context context) {
		List<AppInfo> installedApp = AppUtil.getAllAppInfo(context);
		if (packageName == null) {
			return null;
		}
		for (int i = 0; i < installedApp.size(); i++) {
			if (packageName.equals(installedApp.get(i).getPackageName())) {
				return installedApp.get(i);
			}
		}
		return null;
	}

	public static AppInfo getAppByPackageName(String packageName,
			Context context) {
		List<AppInfo> apps = getResourceApp(context);
		if (StrUtils.isEmpty(packageName)) {
			return null;
		}
		for (int i = 0; i < apps.size(); i++) {
			AppInfo app = apps.get(i);
			if (packageName.equals(app.getPackageName())) {
				return app;
			}
		}
		return null;
	}

	public static boolean ifInstallSDCard(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		ApplicationInfo appInfo;
		try {
			appInfo = pm.getApplicationInfo(packageName, 0);
			if ((appInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<AppInfo> getResourceApp(Context context) {
		List<AppInfo> appList = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
				AppInfo info = new AppInfo();
				info.setAppIcon(pi.applicationInfo.loadIcon(pm));
				info.setPackageName(pi.packageName);
				String appName = pi.applicationInfo.loadLabel(pm).toString();
				String appNamenew = getAppNameByPackageName(appName, pi.packageName, context);
				info.setAppName(appNamenew);
				info.setProcessName(pi.applicationInfo.processName);
				String activityName = getActivityName(appNamenew, pi.packageName,
						context);
				if(StrUtils.isEmpty(activityName)){
					continue;
				}
				info.setActivityName(activityName);
				info.setTime(StrUtils.getCurrentTime());
				info.setInstallDate(StrUtils.getTime(pi.firstInstallTime));
				info.setApkPath(pi.applicationInfo.sourceDir);				
				appList.add(info);
			}
		}
		return appList;
	}
	
	public static List<StatisticsItem> getStatistics(Context context){
		List<StatisticsItem> items = new ArrayList<StatisticsItem>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
				StatisticsItem item = new StatisticsItem();
				item.setPackageName(pi.packageName);
				item.setInstalledNum(1);
				item.setUsedFrequency(0);
				items.add(item);
			}
		}
		return items;
	}
	
	public static List<AppInfo> getSDCardApp(Context context) {
		List<AppInfo> appList = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
				if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0) {
					if (!pi.packageName.equals("com.rgk.phonemanager")) {
						AppInfo info = new AppInfo();
						info.setAppIcon(pi.applicationInfo.loadIcon(pm));
						String appName = pi.applicationInfo.loadLabel(pm)
								.toString();
						info.setAppName(appName);
						info.setPackageName(pi.packageName);
						info.setProcessName(pi.applicationInfo.processName);
						info.setActivityName(getActivityName(appName,
								pi.packageName, context));
						info.setTime(StrUtils.getCurrentTime());
						info.setInstallDate(StrUtils
								.getTime(pi.firstInstallTime));
						appList.add(info);
					}
				}
			}
		}
		return appList;
	}

	public static List<AppInfo> getPhoneApp(Context context) {
		List<AppInfo> appList = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
				if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
					if (!pi.packageName.equals("com.rgk.phonemanager")) {
						AppInfo info = new AppInfo();
						info.setAppIcon(pi.applicationInfo.loadIcon(pm));
						String appName = pi.applicationInfo.loadLabel(pm)
								.toString();
						info.setAppName(appName);
						info.setPackageName(pi.packageName);
						info.setProcessName(pi.applicationInfo.processName);
						info.setActivityName(getActivityName(appName,
								pi.packageName, context));
						info.setTime(StrUtils.getCurrentTime());
						info.setInstallDate(StrUtils
								.getTime(pi.firstInstallTime));
						appList.add(info);
					}
				}
			}
		}
		return appList;
	}
	
	public static Object[] getMoveApps(Context context) {
		Object[] objects = new Object[2];
		List<AppInfo> sdcardAppList = new ArrayList<AppInfo>();
		List<AppInfo> phonAappList = new ArrayList<AppInfo>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if ((pi.applicationInfo.flags & pi.applicationInfo.FLAG_SYSTEM) <= 0) {
				if (pi.packageName.equals("com.rgk.phonemanager")) {
					continue;
				}
				AppInfo info = new AppInfo();
				info.setAppIcon(pi.applicationInfo.loadIcon(pm));
				String appName = pi.applicationInfo.loadLabel(pm)
						.toString();
				info.setAppName(appName);
				info.setPackageName(pi.packageName);
				info.setProcessName(pi.applicationInfo.processName);
				info.setActivityName(getActivityName(appName,
						pi.packageName, context));
				info.setTime(StrUtils.getCurrentTime());
				info.setInstallDate(StrUtils
						.getTime(pi.firstInstallTime));
				if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
					phonAappList.add(info);
				} else{
					sdcardAppList.add(info);
				}
			}
		}
		objects[0] = phonAappList;
		objects[1] = sdcardAppList;
		return objects;
	}

	public static List<AppInfo> getRunningProcess(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<AppInfo> list = new ArrayList<AppInfo>();
		List<RunningTaskInfo> rList = am.getRunningTasks(100);
		for (RunningTaskInfo rt : rList) {
			ComponentName componentName = rt.baseActivity;
			if (null != componentName) {
				AppInfo installApp = ifInstalledApp(
						componentName.getPackageName(), context);
				if (installApp != null) {
					list.add(installApp);
				}
			}

		}
		return list;
	}

	public static Drawable getAppIconByPackageName(String packageName, Context context) {
		List<AppInfo> aList = getAllAppInfo(context);
		for (int i = 0; i < aList.size(); i++) {
			if (null != packageName && !"".equals(packageName)
					&& packageName.equals(aList.get(i).getPackageName())) {
				return aList.get(i).getAppIcon();
			}
		}
		return null;
	}

	public static String getActivityName(String appName, String packageName,
			Context context) {
		List<AppInfo> allApps = getAllAppInfo(context);
		for (int i = 0; i < allApps.size(); i++) {
			AppInfo app = allApps.get(i);
			if (app.getAppName().equals(appName)
					&& app.getPackageName().endsWith(packageName)) {
				return app.getActivityName();
			} 
		}
		return "";
	}
	
	public static String getAppNameByPackageName(String appName, String packageName, Context context){
		List<AppInfo> allApps = getAllAppInfo(context);
		for (int i = 0; i < allApps.size(); i++) {
			AppInfo app = allApps.get(i);
			if (app.getPackageName().equals(packageName)) {
				return app.getAppName();
			}
		}
		return appName;
	}
	public  ApplicationInfo getApplicationInfo(String pkgName) {
		
		if (pkgName == null) {
			return null;
		}
		for (ApplicationInfo appinfo : appList) {
			if (pkgName.equals(appinfo.processName)) {
				return appinfo;
			}
		}
		return null;
	}
}
