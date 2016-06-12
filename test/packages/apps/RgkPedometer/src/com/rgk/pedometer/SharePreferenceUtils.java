package com.rgk.pedometer;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	public static final String PREFS_NAME = "rgk_pedometer_prefs";

	public static final String PREF_ENABLED = "enabled_pedometer_when_back_running";

	public static final String SEX = "sex";

	public static final String BODY_WEIGHT = "weight";
	
	public static final String BODY_HIGH = "high";
	
	public static final String AGE = "age";

	public static final String TARGET_STEPS = "target_steps";

	public static final String HAS_NOTIFY = "has_notify";
	
	public static final int DEFAULT_SEX = 1; // default man
	public static final int DEFAULT_AGE = 20; // default age 20
	public static final int DEFAULT_BODY_HIGH = 170; // default 170cm
	public static final int DEFAULT_BODY_WEIGHT = 60; // default 60kg
	public static final int DEFAULT_TARGET_STEPS = 5000; // default 5000 steps

	public static void setEnabled(Context ctx, boolean enabled) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putBoolean(PREF_ENABLED, enabled).commit();
	}

	public static boolean getEnabled(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getBoolean(PREF_ENABLED, true);
	}

	public static void setHasNotify(Context ctx, boolean hasNotify) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putBoolean(HAS_NOTIFY, hasNotify).commit();
	}

	public static boolean getHasNotify(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getBoolean(HAS_NOTIFY, false);
	}

	public static void setSexValue(Context ctx, int value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putInt(SEX, value).commit();
	}

	public static int getSexValue(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getInt(SEX, DEFAULT_SEX);
	}
	
	public static void setAgeValue(Context ctx, int value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putInt(AGE, value).commit();
	}

	public static int getAgeValue(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getInt(AGE, DEFAULT_AGE);
	}

	public static void setBodyHighValue(Context ctx, int value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putInt(BODY_HIGH, value).commit();
	}

	public static int getBodyHighValue(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getInt(BODY_HIGH, DEFAULT_BODY_HIGH);
	}

	public static void setBodyWeightValue(Context ctx, int value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putInt(BODY_WEIGHT, value).commit();
	}

	public static int getBodyWeightValue(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getInt(BODY_WEIGHT, DEFAULT_BODY_WEIGHT);
	}

	public static void setTargetStepsValue(Context ctx, int value) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		prefs.edit().putInt(TARGET_STEPS, value).commit();
	}

	public static int getTargetStepsValue(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getInt(TARGET_STEPS, DEFAULT_TARGET_STEPS);
	}
	
	public static boolean isMetric(Context ctx) {
        return true;
    }
    
    public static int getStepLength(Context ctx) {
    	// stepLength = bodyHigh x 45%
        return (int)(getBodyHighValue(ctx) * 0.45f);
    }
    
    public static boolean isRunning(Context ctx) {
        return true;
    }

}
