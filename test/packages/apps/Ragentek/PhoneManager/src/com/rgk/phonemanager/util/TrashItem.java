package com.rgk.phonemanager.util;

import android.graphics.drawable.Drawable;

public class TrashItem {

	private Drawable mGroupIcon;
	private String mGroupInfo;
	private long mGroupSize;
	private boolean IsChecked;
	private boolean IsShowProgressBar;
	private String filePath;

	public TrashItem() {
	}

	public TrashItem(Drawable mGroupIcon, String mGroupInfo, long mGroupSize,
			boolean IsChecked, boolean IsShowProgressBar, String filePath) {
		this.mGroupIcon = mGroupIcon;
		this.mGroupInfo = mGroupInfo;
		this.mGroupSize = mGroupSize;
		this.IsChecked = IsChecked;
		this.IsShowProgressBar = IsShowProgressBar;
		this.filePath = filePath;
	}

	public TrashItem(Drawable mGroupIcon, String mGroupInfo, long mGroupSize,
			boolean IsChecked, String filePath) {
		this.mGroupIcon = mGroupIcon;
		this.mGroupInfo = mGroupInfo;
		this.mGroupSize = mGroupSize;
		this.IsChecked = IsChecked;
		this.filePath = filePath;
	}

	public Drawable getmGroupIcon() {
		return mGroupIcon;
	}

	public void setmGroupIcon(Drawable mGroupIcon) {
		this.mGroupIcon = mGroupIcon;
	}

	public String getmGroupInfo() {
		return mGroupInfo;
	}

	public void setmGroupInfo(String mGroupInfo) {
		this.mGroupInfo = mGroupInfo;
	}

	public long getmGroupSize() {
		return mGroupSize;
	}

	public void setmGroupSize(long mGroupSize) {
		this.mGroupSize = mGroupSize;
	}

	public boolean isIsChecked() {
		return IsChecked;
	}

	public void setIsChecked(boolean isChecked) {
		IsChecked = isChecked;
	}

	public boolean isIsShowProgressBar() {
		return IsShowProgressBar;
	}

	public void setIsShowProgressBar(boolean isShowProgressBar) {
		IsShowProgressBar = isShowProgressBar;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "TrashItem [mGroupIcon=" + mGroupIcon + ", mGroupInfo="
				+ mGroupInfo + ", mGroupSize=" + mGroupSize + ", IsChecked="
				+ IsChecked + ", IsShowProgressBar=" + IsShowProgressBar
				+ ", filePath=" + filePath + "]";
	}

}
