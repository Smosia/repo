package com.rgk.phonemanager.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;

public class FileUtils {
	public static void findApkFile(List<APKFileItem> list, String path,
			Context context, Handler handler) {
		File[] files = new File(path).listFiles();
		if (files == null) {
			return;
		}		
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isFile()) {
				if (f.getName().toLowerCase().endsWith(".apk")) {
					analyticApk(list, f, context);
				}
			} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) {
				findApkFile(list, f.getPath(), context, handler);
			}
		}
	}

	private static void analyticApk(List<APKFileItem> list, File file,
			Context context) {
		String archiveFilePath = file.getAbsolutePath();
		if (context == null) {
			return;
		}
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		packageInfo = pm.getPackageArchiveInfo(archiveFilePath,
				PackageManager.GET_ACTIVITIES);
		if (packageInfo == null) {
			// apk Corrupted
			list.add(new APKFileItem(null, null, null, null, file.getName(),
					file.getAbsolutePath(), file.length(), 3));
			return;
		}
		String packageName = packageInfo.packageName;
		int versionCode = packageInfo.versionCode;
		String version = packageInfo.versionName;

		List<PackageInfo> packageinfos = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo pi : packageinfos) {
			String pi_packageName = pi.packageName;
			int pi_versionName = pi.versionCode;

			if (packageName.endsWith(pi_packageName)) {
				// apk installed
				if (versionCode > pi_versionName) {
					// To be updated
					list.add(new APKFileItem(pi.applicationInfo.loadIcon(pm),
							pi.applicationInfo.loadLabel(pm).toString(),
							packageName, version, file.getName(), file
									.getAbsolutePath(), file.length(), 2));

					return;
				}
				// Has been installed
				String named = pi.applicationInfo.loadLabel(pm).toString();
				list.add(new APKFileItem(pi.applicationInfo.loadIcon(pm),
						named, packageName, version, file.getName(), file
								.getAbsolutePath(), file.length(), 1));
				return;
			}
		}
		// Not Installed
		list.add(new APKFileItem(packageInfo.applicationInfo.loadIcon(pm),
				packageInfo.applicationInfo.loadLabel(pm).toString(),
				packageName, version, file.getName(), file.getAbsolutePath(),
				file.length(), 0));
		return;
	}

	public static long getFileSize(File f) {
		long s = 0;

		if (f == null) {
			return 0;
		}
		if (!f.isDirectory()) {
			s = f.length();
			return s;
		} else {
			File flist[] = f.listFiles();
			for (int i = 0; i < flist.length; i++) {
				if (!flist[i].isDirectory()) {
					s = s + flist[i].length();
				} else {
					s = s + getFileSize(flist[i]);
				}
			}
			return s;
		}
	}

	public static void deleteFile(File file) {
		if (file == null || !file.exists()) {
			return;
		}

		if (!file.isDirectory() || file.listFiles() == null
				|| file.listFiles().length <= 0) {
			file.delete();
			return;
		}

		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			deleteFile(f);
		}
		file.delete();
		return;
	}
}
