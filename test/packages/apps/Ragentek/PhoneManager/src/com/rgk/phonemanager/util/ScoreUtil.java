package com.rgk.phonemanager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

public class ScoreUtil {

	public final static int INTERVALS_BETWEEN_GET_CPU_RATE = 360;
	public final static int WEIGHTS_TOTLE = 100;
	public final static float WEIGHTS_CPU_INFO = 0.1f;
	public final static float WEIGHTS_RAM_INFO = 0.7f;
	public final static float WEIGHTS_ROM_INFO = 0.2f;

	// get CPU info
	public static int getProcessCpuRate() {

		long[] first = getCpuData();
		try {
			Thread.sleep(INTERVALS_BETWEEN_GET_CPU_RATE);
		} catch (Exception e) {
		}

		long[] second = getCpuData();

		long user_pass = second[0] - first[0];
		long system_pass = second[1] - first[1];
		long irq_pass = second[2] - first[2];
		long idle_pass = second[3] - first[3];

		float rate = (float) (idle_pass * WEIGHTS_TOTLE)
				/ (user_pass + system_pass + irq_pass + idle_pass);
		float score = (float) (1.3 * rate + 30);

		if (score > 100 || score < 0) {
			score = 100;
		}
        android.util.Log.d("guocl", "getProcessCpuRate:");
        android.util.Log.d("guocl", "rate="+rate);
        android.util.Log.d("guocl", "score="+score);
		score *= WEIGHTS_CPU_INFO;

		return (int) score;
	}

	public static long[] getCpuData() {
		long[] res = new long[4];
		String[] cpuInfos;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/stat")), 1000);
			String load = reader.readLine();
			reader.close();
			cpuInfos = load.split(" ");

			res[0] = Long.parseLong(cpuInfos[2]);
			res[1] = Long.parseLong(cpuInfos[4]);
			res[2] = Long.parseLong(cpuInfos[6] + cpuInfos[7] + cpuInfos[8]);
			res[3] = Long.parseLong(cpuInfos[5]);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return res;
	}

	// get ROM info
	public static int getROMRate() {
		long totalSize = getTotalExternalMemorySize();

		long availableSize = getAvailableExternalMemorySize();

		float rate = (float) ((availableSize * WEIGHTS_TOTLE) / totalSize);
		float score = (float) (4* rate + 10);
		if (score > 100 || score < 0) {
			score = 100;
		}
        android.util.Log.d("guocl", "getROMRate:");
        android.util.Log.d("guocl", "rate="+rate);
        android.util.Log.d("guocl", "score="+score);
		score *= WEIGHTS_ROM_INFO;

		return (int) score;
	}

	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return 0;
		}
	}

	public static long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return 0;
		}
	}

	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	// get RAM info
	public static int getRAMRate(Context context) {
		if (context == null) {
			return 0;
		}
		MemoryInfo memoryInfo = new MemoryInfo();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		activityManager.getMemoryInfo(memoryInfo);

		float rate = (float) (memoryInfo.availMem * WEIGHTS_TOTLE)
				/ memoryInfo.totalMem;
        
		float score = (float) (2 * rate + 30);
		if (score > 100 || score < 0) {
			score = 100;
		}
        android.util.Log.d("guocl", "getRAMRate:");
        android.util.Log.d("guocl", "rate="+rate);
        android.util.Log.d("guocl", "score="+score);
		score *= WEIGHTS_RAM_INFO;
		return (int) (score);
	}

}
