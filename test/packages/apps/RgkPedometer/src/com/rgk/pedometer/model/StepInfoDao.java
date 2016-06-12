package com.rgk.pedometer.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.rgk.pedometer.PedometerApplication;
import com.rgk.pedometer.database.PedometerDatabaseHelper;

public class StepInfoDao {
	private static StepInfoDao mInstance;

	private PedometerDatabaseHelper dbHelper = null;

	private StepInfoDao() {
		dbHelper = new PedometerDatabaseHelper(
				PedometerApplication.getInstance());
	}

	public static StepInfoDao getInstance() {
		if (mInstance == null) {
			mInstance = new StepInfoDao();
		}
		return mInstance;
	}

	public List<StepInfo> getHistorySteps(long start, long end) {
		Cursor c = dbHelper.getHistorySteps(start, end);
		List<StepInfo> infos = new ArrayList<StepInfo>();
		if (c == null) {
			return infos;
		}

		StepInfo info = null;
		while (c.moveToNext()) {
			info = new StepInfo();
			buildStepInfo(c, info);
			infos.add(info);
		}
		c.close();

		return infos;
	}

	private void buildStepInfo(Cursor c, StepInfo info) {
		info.id = c.getInt(c
				.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_ID));
		info.walkSteps = c
				.getInt(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_WALK_STEPS));
		info.runSteps = c
				.getInt(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_RUN_STEPS));
		info.calories = c
				.getString(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_CALORIES));
		info.distance = c
				.getString(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_DISTANCE));
		info.dayInWeek = c
				.getInt(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_TIME_WEEK_DAY));
		info.date = c
				.getInt(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_TIME_ORIGINAL));
		info.formatDate = c
				.getString(c
						.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_TIME_FORMAT));
	}

	public void insert(StepInfo info) {
		Log.d("sqm", "insert: " + info);
		if (info == null) {
			return;
		}
		ContentValues values = new ContentValues();

		if (info.walkSteps > 0) {
			values.put(PedometerDatabaseHelper.COLUMNS_WALK_STEPS,
					info.walkSteps);
		}
		if (info.runSteps > 0) {
			values.put(PedometerDatabaseHelper.COLUMNS_RUN_STEPS, info.runSteps);
		}
		if (!TextUtils.isEmpty(info.calories)) {
			values.put(PedometerDatabaseHelper.COLUMNS_CALORIES, info.calories);
		}
		if (!TextUtils.isEmpty(info.distance)) {
			values.put(PedometerDatabaseHelper.COLUMNS_DISTANCE, info.distance);
		}
		values.put(PedometerDatabaseHelper.COLUMNS_TIME_ORIGINAL, info.date);
		if (info.formatDate != null) {
			values.put(PedometerDatabaseHelper.COLUMNS_TIME_FORMAT,
					info.formatDate);
		}
		values.put(PedometerDatabaseHelper.COLUMNS_TIME_WEEK_DAY,
				info.dayInWeek);

		dbHelper.insert(values);
	}

	public void delete(StepInfo info) {
		if (info == null) {
			return;
		}
		dbHelper.deleteOneDayValue(info.formatDate);
	}

	public void update(StepInfo info) {
		Log.d("sqm", "update: " + info);
		if (info == null) {
			return;
		}
		ContentValues values = new ContentValues();

		if (info.walkSteps > 0) {
			values.put(PedometerDatabaseHelper.COLUMNS_WALK_STEPS,
					info.walkSteps);
		}
		if (info.runSteps > 0) {
			values.put(PedometerDatabaseHelper.COLUMNS_RUN_STEPS, info.runSteps);
		}
		if (!TextUtils.isEmpty(info.calories)) {
			values.put(PedometerDatabaseHelper.COLUMNS_CALORIES, info.calories);
		}
		if (!TextUtils.isEmpty(info.distance)) {
			values.put(PedometerDatabaseHelper.COLUMNS_DISTANCE, info.distance);
		}
		dbHelper.update(values, info.formatDate);
	}

	public void clearHistorys() {
		dbHelper.clearAllHistorys();
	}

	public boolean hasSteps(String formatTime) {
		return dbHelper.hasSteps(formatTime);
	}

	public int getSteps(String formatTime) {
		return dbHelper.getSteps(formatTime);
	}

	public StepInfo getStepInfo(String formatTime) {
		Cursor c = dbHelper.getStepInfo(formatTime);
		if (c == null) {
			return null;
		}

		StepInfo info = null;
		if (c.moveToFirst()) {
			info = new StepInfo();
			buildStepInfo(c, info);
		}
		c.close();

		return info;
	}

	public long getFirstDateMillis() {
		Cursor c = dbHelper.getFirstRow();
		if (c == null) {
			return 0;
		}
		long dateMillis = 0;
		if (c.moveToFirst()) {
			dateMillis = c
					.getLong(c
							.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_TIME_ORIGINAL));
		}
		c.close();

		return dateMillis;
	}

	public long getLastDateMillis() {
		Cursor c = dbHelper.getLastRow();
		if (c == null) {
			return 0;
		}
		long dateMillis = 0;
		if (c.moveToFirst()) {
			dateMillis = c
					.getLong(c
							.getColumnIndexOrThrow(PedometerDatabaseHelper.COLUMNS_TIME_ORIGINAL));
		}
		c.close();

		return dateMillis;
	}

	public StepInfo getFirstStepInfo() {
		Cursor c = dbHelper.getFirstRow();
		if (c == null) {
			return null;
		}

		StepInfo info = null;
		if (c.moveToFirst()) {
			info = new StepInfo();
			buildStepInfo(c, info);
		}
		c.close();

		return info;
	}

	public StepInfo getLastStepInfo() {
		Cursor c = dbHelper.getLastRow();
		if (c == null) {
			return null;
		}

		StepInfo info = null;
		if (c.moveToFirst()) {
			info = new StepInfo();
			buildStepInfo(c, info);
		}
		c.close();

		return info;
	}
}
