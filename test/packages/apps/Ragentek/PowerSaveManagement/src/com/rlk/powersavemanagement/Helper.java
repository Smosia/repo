package com.rlk.powersavemanagement;

import android.content.Context;
import android.app.ActivityManager;
import android.content.Intent;
import java.util.List;

public class Helper {

	public static void expandPowerSavingModeChange(Context context) {
		// when open power save,add sth...
	}

	public static void expandUltraPowerSettings(Context context) {
		// when open ultra power,add sth...
	}

	public static void expandRestoreBeforeSettings(Context context,
			boolean isUltraPowerMode) {
		// restore settings...
	}

	public static synchronized void removeRecentTask(Context context,
	        boolean isUltraPowerOn) {
	        int maxTasks = 21;
	        final ActivityManager am = (ActivityManager) context
	        .getSystemService(Context.ACTIVITY_SERVICE);
	        final List<ActivityManager.RecentTaskInfo> recentTasks =
                    am.getRecentTasks(maxTasks, ActivityManager.RECENT_IGNORE_UNAVAILABLE
                    | ActivityManager.RECENT_INCLUDE_PROFILES);
	        String []launcherArray = PowerSaveUtils.getLauncherString(context);
	        android.util.Log.d("youfu","launcherArray length: "+launcherArray.length);
	        for (ActivityManager.RecentTaskInfo rt : recentTasks) {
	        String packageName = rt.baseIntent.getComponent().getPackageName();
	        android.util.Log.d("youfu","packageName 0: "+packageName);
	        android.util.Log.d("youfu","launcherArray[0]: "+launcherArray[0]);
	        if (context.getApplicationContext().getPackageName()
	        .equals(packageName)
	        && isUltraPowerOn) {
	        continue;
	        }
	        if (packageName.contains(launcherArray[0])) {
	        continue;
	        }
	        android.util.Log.d("youfu","packageName: "+packageName);
	        Intent dismissIntent = new Intent(
	        "com.android.deskclock.ALARM_DISMISS");
	        context.sendBroadcast(dismissIntent);
	        am.removeTask(rt.persistentId/*,
	        ActivityManager.REMOVE_TASK_KILL_PROCESS*/);
	        }
	    }
}
