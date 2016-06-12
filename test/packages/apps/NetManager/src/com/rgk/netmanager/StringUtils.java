package com.rgk.netmanager;

public class StringUtils {

    public static String formatBytes(long bytes) {
		double result = bytes;
		if (result > 900) {
			result = result / 1024;
		} else {
			return result + "B";
		}
		if (result > 900) {
			result = result / 1024;
		} else {
			return formatDecimalCount(result) + "KB";
		}
		if (result > 900) {
			result = result / 1024;
		} else {
			return formatDecimalCount(result) + "MB";
		}
		return formatDecimalCount(result) + "GB";
	}

	private static String formatDecimalCount(double result) {
		String str = String.valueOf(result);
		int index = str.indexOf(".");
		if (index > 0 && str.length() - index > 2) {
			str = str.substring(0, index + 2);
		}
		return str;
	}

        public static String formatMBytesByPercent(int percent, int bytesInM) {
		long bytes = convertMtoBytes(percent, bytesInM);
		String str = formatBytes(bytes);
		return str;
	}
	
	public static long convertMtoBytes(int percent, int bytesInM) {
		float f = percent * 1.0f / 100;
		long bytes = (long)(f * bytesInM * Constant.MB_IN_BYTES);
		return bytes;
	}
}
