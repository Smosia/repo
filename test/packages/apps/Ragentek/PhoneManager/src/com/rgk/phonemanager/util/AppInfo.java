package com.rgk.phonemanager.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;

public class AppInfo {

	private int appId;
	private String appName;
	private String packageName;
	private Drawable appIcon;
	private String activityName;
	private long size;
	private String installDate;
	private boolean isNeddUninstall;
	private boolean isChecked;
	private String codeSize;
	private long longcodeSize;
	private String dataSize;
	private String cacheSize;
	private String processName;
	private String time;
	private int ifUninstall;
	private String apkPath;
	private int installLocation;
	private boolean enabled;
	
	private List<String> autoBootReceivers = new ArrayList<String>();

	public AppInfo() {
		super();
	}

	public AppInfo(String appName, String packageName, Drawable appIcon,
			String activityName, long size) {
		super();
		this.appName = appName;
		this.packageName = packageName;
		this.appIcon = appIcon;
		this.activityName = activityName;
		this.size = size;
	}

	public AppInfo(String appName, String packageName, Drawable appIcon,
			String activityName, long size, String installDate) {
		super();
		this.appName = appName;
		this.packageName = packageName;
		this.appIcon = appIcon;
		this.activityName = activityName;
		this.size = size;
		this.installDate = installDate;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public boolean isNeddUninstall() {
		return isNeddUninstall;
	}

	public void setNeddUninstall(boolean isNeddUninstall) {
		this.isNeddUninstall = isNeddUninstall;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getCodeSize() {
		return codeSize;
	}

	public void setCodeSize(String codeSize) {
		this.codeSize = codeSize;
	}

	public String getDataSize() {
		return dataSize;
	}

	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}

	public String getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(String cacheSize) {
		this.cacheSize = cacheSize;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getIfUninstall() {
		return ifUninstall;
	}

	public void setIfUninstall(int ifUninstall) {
		this.ifUninstall = ifUninstall;
	}

	public String getApkPath() {
		return apkPath;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public int getInstallLocation() {
		return installLocation;
	}

	public void setInstallLocation(int installLocation) {
		this.installLocation = installLocation;
	}

	public long getLongcodeSize() {
		return longcodeSize;
	}

	public void setLongcodeSize(long longcodeSize) {
		this.longcodeSize = longcodeSize;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getAutoBootReceivers() {
		return autoBootReceivers;
	}

	public void setAutoBootReceivers(List<String> autoBootReceivers) {
		this.autoBootReceivers = autoBootReceivers;
	}

}
