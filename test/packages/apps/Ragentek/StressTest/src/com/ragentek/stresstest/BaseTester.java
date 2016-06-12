package com.ragentek.stresstest;

/**
 * base tester, every one implement it,can add into test center
 * @author yangyang.liu
 * @date 2015/07/21
 */

import java.lang.reflect.Method;
import java.util.List;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.MotionEvent;
import com.ragentek.stresstest.utils.DebugUtil;

public abstract class BaseTester {

	public final static String TAG = "StressTest";

	public final static int REQEUST_CODE = 10;

	protected Context mContext;

	/**
	 * set mContext when be called
	 * 
	 * @param context
	 */
	public void setContext(Context context) {
		mContext = context;
	}

	/**
	 * start test,must be implemented by sub class
	 * 
	 * @param context
	 * @return
	 */
	public abstract boolean start(Context context);

	/**
	 * stop test,must be implemented by sub class
	 * 
	 * @param context
	 * @return
	 */
	public abstract boolean stop(Context context);

	/**
	 * provider the method to start app by pgkName & className
	 * 
	 * @param pkgName
	 * @param className
	 * @return
	 */
	public boolean startApp(String pkgName, String className) {

		Intent intent = new Intent();
		intent.setClassName(pkgName, className);
		try {
			mContext.startActivity(intent);
		} catch (Exception e) {
			DebugUtil.shortToast(mContext, "Activity: " + className
					+ " not found!");
			return false;
		}

		return true;
	}

	/**
	 * provider the method to stop app by pkgName
	 * 
	 * @param pkgName
	 * @return
	 */
	public boolean stopApp(String pkgName) {
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		try {
			Method forceStopPackage = am.getClass().getDeclaredMethod(
					"forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(am, pkgName);
		} catch (Exception e) {
			e.printStackTrace();
			DebugUtil.logd(TAG, "Unable to stop the app: " + pkgName);
			return false;
		}
		return true;
	}

	/**
	 * start an activity for result
	 * 
	 * @param pkgName
	 * @param className
	 * @param requestCode
	 */
	public boolean startActivity(String pkgName, String className,
			int requestCode) {
		Intent intent = new Intent();
		intent.setClassName(pkgName, className);
		try {
			((Activity) mContext).startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * stop an activity by request code
	 * 
	 * @param requestCode
	 */
	public boolean stopActivity(int requestCode) {
		try {
			((Activity) mContext).finishActivity(requestCode);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * touch pointer inject
	 * 
	 * @param x
	 * @param y
	 */
	public void injectPointer(float x, float y) {
		Instrumentation inst = new Instrumentation();
		MotionEvent downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(),
				SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0);
		inst.sendPointerSync(downEvent);
		MotionEvent upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(),
				SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0);
		inst.sendPointerSync(upEvent);
	}

	/**
	 * key event inject
	 * 
	 * @param keyCode
	 */
	public void injectKeyEvent(int keyCode) {
		Instrumentation inst = new Instrumentation();
		inst.sendKeyDownUpSync(keyCode);
	}

	/**
	 * return true if the app is in foregroud,otherwise return false;
	 * 
	 * @param pkgName
	 * @return
	 */
	public boolean isRunningForegroud(String pkgName) {
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> infoList = am.getRunningTasks(1);
		if (infoList != null && infoList.size() > 0) {
			RunningTaskInfo info = infoList.get(0);
			String forePkgName = info.topActivity.getPackageName();
			if (pkgName.equals(forePkgName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * return true if the activity is on top,otherwise return false;
	 * 
	 * @param className
	 * @return
	 */
	public boolean isTopActivity(String className) {
		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> infoList = am.getRunningTasks(1);
		if (infoList != null && infoList.size() > 0) {
			RunningTaskInfo info = infoList.get(0);
			String topActivity = info.topActivity.getClassName();
			if (className.equals(topActivity)) {
				return true;
			}
		}
		return false;
	}
}
