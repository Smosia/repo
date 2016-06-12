package com.rgk.phonemanager.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * The activity manage, it will control all viable activity<br>
 * if user exit the application, then the method logout() will be called, and
 * the memory cache will be clean.
 * 
 */
public class UIHandle {

	private List<Activity> activityList = null;
	private static UIHandle instance;
	private Activity mCurrentActivity;

	private UIHandle() {
		activityList = new LinkedList<Activity>();
	}

	public static UIHandle getInstance() {
		if (instance == null) {
			instance = new UIHandle();
		}
		return instance;
	}

	public void removeAll() {
		for (Activity activity : activityList) {
			if (null != activity) {
				activity.finish();
			}
		}
		activityList.clear();
		mCurrentActivity = null;
	}

	public void addActivity(Activity activity) {
		mCurrentActivity = activity;
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		if (activityList.contains(activity)) {
			activityList.remove(activity);
		}
	}

	public Activity getCurrentActivity() {
		return mCurrentActivity;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void logout() {
		removeAll();
	}

	public void saveTop() {
		for (int i = 0; i < activityList.size() - 1; i++) {
			if (null != activityList.get(i)) {
				activityList.get(i).finish();
			}
		}
	}

	public void exit() {
		removeAll();
		activityList = null;
		instance = null;
	}
}
