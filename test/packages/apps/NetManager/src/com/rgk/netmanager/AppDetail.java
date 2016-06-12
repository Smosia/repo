package com.rgk.netmanager;

import android.graphics.drawable.Drawable;

public class AppDetail implements Comparable<AppDetail> {
	public static final int MONTH = 1;
	public static final int DAY = 2;
	
	int timeType;
	int uid;
	String name;
	boolean selected_wifi;
	boolean selected_mobile;
	long wifiTotal;
	long sim1Total;
	long sim2Total;
	Drawable icon;

	public AppDetail() {

	}

	public AppDetail(int uid, String name, boolean selected_wifi,
			boolean selected_mobile, Drawable icon, int timeType) {
		this.uid = uid;
		this.name = name;
		this.selected_wifi = selected_wifi;
		this.selected_mobile = selected_mobile;
		this.icon = icon;
		this.timeType = timeType;

	}

	@Override
	public int compareTo(AppDetail arg) {
		long left = arg.sim1Total + arg.sim2Total + arg.wifiTotal;
		long right = sim1Total + sim2Total + wifiTotal;
		return left < right ? -1 : (left == right ? 0 : 1);
	}

	@Override
	public String toString() {
		return "[uid,name,selected_wifi,selected_mobile,wifiTotal,sim1Total,sim2Total]="
				+ "["
				+ uid
				+ ","
				+ name
				+ ","
				+ selected_wifi
				+ ","
				+ selected_mobile + "," + wifiTotal + "," + sim1Total + "," + sim2Total + "]";
	}
}
