package com.rgk.phonemanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class StrUtils {

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
	private static final long DATA_GB = 1024 * 1024 * 1024;
	private static final long DATA_M = 1024 * 1024;
	private static final long DATA_KB = 1024;

	/**
	 * Whether string is empty or not.
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	@SuppressLint("SimpleDateFormat")
	public static String dateToString(Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}

	public static String getCurrentTime() {
		Date now = new Date(System.currentTimeMillis());
		String time = new SimpleDateFormat(DATE_FORMAT_ONE).format(now);
		return time;
	}

	public static String getCurrentDate() {
		Date now = new Date(System.currentTimeMillis());
		String time = new SimpleDateFormat(DATE_FORMAT).format(now);
		return time;
	}

	public static int compareToNow(String date) {
		if (isEmpty(date)) {
			return 0;
		}
		long now = 0;
		long time = 0;
		int res = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			time = sdf.parse(date).getTime();
			now = sdf.parse(getCurrentDate()).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		float temp = (now - time) / 1000 / 86400;
		if (temp > 0 && temp < 1) {
			return 1;
		}
		res = (int) Math.floor(temp);
		return res;
	}

	public static String formatFileSize(long length) {
		String result = null;
		int sub_string = 0;
		if (length >= DATA_GB) {
			sub_string = String.valueOf((float) length / DATA_GB).indexOf(".");
			result = ((float) length / DATA_GB + "00").substring(0,
					sub_string + 2) + "GB";
		} else if (length >= DATA_M) {
			sub_string = String.valueOf((float) length / DATA_M).indexOf(".");
			result = ((float) length / DATA_M + "00").substring(0,
					sub_string + 2) + "MB";
		} else if (length >= DATA_KB) {
			sub_string = String.valueOf((float) length / DATA_KB).indexOf(".");
			result = ((float) length / DATA_KB + "00").substring(0,
					sub_string + 2) + "KB";
		} else if (length < DATA_KB)
			result = Long.toString(length) + "B";
		return result;
	}

	public static String formatFileSizeByInteger(long length) {
		String result = null;
		String sub_string = "";
		if (length >= DATA_GB) {
			sub_string = String.valueOf(length / DATA_GB);
			result = sub_string + "GB";
		} else if (length >= DATA_M) {
			sub_string = String.valueOf(length / DATA_M);
			result = sub_string + "MB";
		} else if (length >= DATA_KB) {
			sub_string = String.valueOf(length / DATA_KB);
			result = sub_string + "KB";
		} else if (length < DATA_KB)
			result = Long.toString(length) + "B";
		return result;
	}

	public static String getTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date date = new Date(time);
		String sDateTime = sdf.format(date);
		return sDateTime;
	}

	public static String convertStorage(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	public static String getSpanTime(String date) {
		String result = "";
		try {
			long now = System.currentTimeMillis();
			if (isEmpty(date)) {
				return null;
			}
			long time = new SimpleDateFormat(DATE_FORMAT_ONE).parse(date)
					.getTime();
			long leftTime = (now - time) / 1000;
			long minute = (leftTime % 3600) / 60;
			long hour = leftTime / 3600;
			long day = leftTime / 86400;
			if (day < 1 && day > 0) {
				result = 1 + "";
			} else {
				result = day + "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String formatApkPath(String apkPath) {
		return apkPath.substring(1, apkPath.length());
	}

	public static int forNumber(long size) {
		String val = StrUtils.formatFileSize(size);
		if (val.endsWith("MB")) {
			val = val.substring(0, val.indexOf("MB"));
			float f = Float.parseFloat(val);
			return (int) f;
		}
		if (val.endsWith("GB")) {
			val = val.substring(0, val.indexOf("GB"));
			float f = Float.parseFloat(val) * 1024;
			return (int) f;
		}
		return 0;
	}

	public static float getCodeSize(String size) {
		if (size.toUpperCase().endsWith("KB")) {
			size = size.substring(0, size.indexOf("KB"));
			float f = Float.parseFloat(size);
			return (int) f;
		}
		if (size.toUpperCase().endsWith("MB")) {
			size = size.substring(0, size.indexOf("MB"));
			float f = Float.parseFloat(size) * 1024;
			return (int) f;
		}
		if (size.toUpperCase().endsWith("GB")) {
			size = size.substring(0, size.indexOf("GB"));
			float f = Float.parseFloat(size) * 1024 * 1024;
			return (int) f;
		}
		return 0;
	}

	public static String formatToHz(String hz) {
		long size = Long.parseLong(hz) / 1000;
		return size + "MHZ";
	}
}
