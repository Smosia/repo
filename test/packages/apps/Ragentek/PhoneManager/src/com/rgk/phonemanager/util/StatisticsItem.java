package com.rgk.phonemanager.util;

public class StatisticsItem {

	private String packageName;
	private int usedFrequency;
	private int installedNum;

	public StatisticsItem() {
		super();
	}

	public StatisticsItem(String packageName, int usedFrequency,
			int installedTime) {
		super();
		this.packageName = packageName;
		this.usedFrequency = usedFrequency;
		this.installedNum = installedTime;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getUsedFrequency() {
		return usedFrequency;
	}

	public void setUsedFrequency(int usedFrequency) {
		this.usedFrequency = usedFrequency;
	}

	public int getInstalledNum() {
		return installedNum;
	}

	public void setInstalledNum(int installedNum) {
		this.installedNum = installedNum;
	}

}
