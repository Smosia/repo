package com.rgk.phonemanager.util;

import android.graphics.drawable.Drawable;

public class APKFileItem {
	private Drawable apkicon;
	private String apkName;
	private String apkPackName;
	private String apkVersion;
	private String apkFileName;
	private String apkFileAbsolutePath;
	private long apkFileSize;
	private boolean isChecked;
	private boolean isNeedInstall;
	private String systemApkPath;

	private int type;

	public APKFileItem() {
		super();
	}

	public APKFileItem(Drawable apkicon, String apkName, String apkPackName,
			String apkVersion, String apkFileName, String apkFileAbsolutePath,
			long apkFileSize, int type) {
		this.apkicon = apkicon;
		this.apkName = apkName;
		this.apkPackName = apkPackName;
		this.apkVersion = apkVersion;
		this.apkFileName = apkFileName;
		this.apkFileAbsolutePath = apkFileAbsolutePath;
		this.apkFileSize = apkFileSize;
		this.type = type;
	}

	public Drawable getApkicon() {
		return apkicon;
	}

	public void setApkicon(Drawable apkicon) {
		this.apkicon = apkicon;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getApkPackName() {
		return apkPackName;
	}

	public void setApkPackName(String apkPackName) {
		this.apkPackName = apkPackName;
	}

	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public String getApkFileName() {
		return apkFileName;
	}

	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}

	public String getApkFileAbsolutePath() {
		return apkFileAbsolutePath;
	}

	public void setApkFileAbsolutePath(String apkFileAbsolutePath) {
		this.apkFileAbsolutePath = apkFileAbsolutePath;
	}

	public long getApkFileSize() {
		return apkFileSize;
	}

	public void setApkFileSize(long apkFileSize) {
		this.apkFileSize = apkFileSize;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSystemApkPath() {
		return systemApkPath;
	}

	public void setSystemApkPath(String systemApkPath) {
		this.systemApkPath = systemApkPath;
	}

	public boolean isNeedInstall() {
		return isNeedInstall;
	}

	public void setNeedInstall(boolean isNeedInstall) {
		this.isNeedInstall = isNeedInstall;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public String toString() {
		return "APKFileItem [apkicon=" + apkicon + ", apkName=" + apkName
				+ ", apkPackName=" + apkPackName + ", apkVersion=" + apkVersion
				+ ", apkFileName=" + apkFileName + ", apkFileAbsolutePath="
				+ apkFileAbsolutePath + ", apkFileSize=" + apkFileSize
				+ ", type=" + type + "]";
	}

}
