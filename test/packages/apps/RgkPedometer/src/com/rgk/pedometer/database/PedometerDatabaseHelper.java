package com.rgk.pedometer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PedometerDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "pedometer.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_STEPS = "steps";

	public static final String COLUMNS_ID = "_id";
	public static final String COLUMNS_WALK_STEPS = "walk_steps";
	public static final String COLUMNS_RUN_STEPS = "run_steps";
	public static final String COLUMNS_CALORIES = "calories";
	public static final String COLUMNS_DISTANCE = "distance";
	public static final String COLUMNS_TIME_ORIGINAL = "time_original";
	public static final String COLUMNS_TIME_FORMAT = "time_format"; //year+month+date £º20160115
	public static final String COLUMNS_TIME_WEEK_DAY = "week_day";

	private Context mContext = null;

	public PedometerDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_STEPS + "(" 
				+ COLUMNS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ COLUMNS_WALK_STEPS + " INTEGER NOT NULL DEFAULT 0," 
				+ COLUMNS_RUN_STEPS + " INTEGER NOT NULL DEFAULT 0," 
				+ COLUMNS_CALORIES + " TEXT," 
				+ COLUMNS_DISTANCE + " TEXT," 
				+ COLUMNS_TIME_WEEK_DAY + " INTEGER NOT NULL DEFAULT 0," 
				+ COLUMNS_TIME_ORIGINAL + " INTEGER NOT NULL DEFAULT 0," 
				+ COLUMNS_TIME_FORMAT + " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);

	}

	public Cursor getHistorySteps(long start, long end) {
		return query(start, end);
	}
	
	public int getSteps(String formatDate) {
		String sql = "SELECT walk_steps FROM " + TABLE_STEPS + " WHERE "
				+ COLUMNS_TIME_FORMAT + "=" + formatDate;
		Cursor c = getReadableDatabase().rawQuery(sql, null);
		if (c == null) {
			return 0;
		}
		int steps = 0;
		if (c.moveToFirst()) {
			steps = c.getInt(0);
		}
		c.close();
		return steps;
	}
	
	public Cursor getStepInfo(String formatDate) {
		String sql = "SELECT * FROM " + TABLE_STEPS + " WHERE "
				+ COLUMNS_TIME_FORMAT + "=" + formatDate;
		return getReadableDatabase().rawQuery(sql, null);
	}
	
	public boolean hasSteps(String formatTime) {
		String sql = "SELECT _id FROM " + TABLE_STEPS + " WHERE "
				+ COLUMNS_TIME_FORMAT + "=" + formatTime;
		return getReadableDatabase().rawQuery(sql, null).getCount() > 0;
	}

	private Cursor query(long start, long end) {
		String sql = "SELECT * FROM " + TABLE_STEPS + " WHERE "
				+ COLUMNS_TIME_ORIGINAL + ">=" + start + " AND "
				+ COLUMNS_TIME_ORIGINAL + "<=" + end;
		return getReadableDatabase().rawQuery(sql, null);
	}

	public void insert(ContentValues values) {
		Log.d("sqm", "insert: "+values);
		getWritableDatabase().insert(TABLE_STEPS, null, values);
	}
	
	public void update(ContentValues values, String formatTime) {
		Log.d("sqm", "update: formatTime="+formatTime+", "+values);
		getWritableDatabase().update(TABLE_STEPS, values, 
				COLUMNS_TIME_FORMAT + "=" + formatTime, null);
	}

	public void deleteHistorySteps(long start, long end) {
		getWritableDatabase().execSQL(
				"DELETE FROM " + TABLE_STEPS + " WHERE "
						+ COLUMNS_TIME_ORIGINAL + ">=" + start + " AND "
						+ COLUMNS_TIME_ORIGINAL + "<=" + end);
	}
	
	public void deleteOneDayValue(String formatTime) {
		getWritableDatabase().execSQL(
				"DELETE FROM " + TABLE_STEPS + " WHERE "
						+ COLUMNS_TIME_FORMAT + "=" + formatTime);
	}

	public void clearAllHistorys() {
		getWritableDatabase().execSQL("DELETE FROM " + TABLE_STEPS);
	}
	
	public Cursor getFirstRow() {
		String sql = "SELECT * FROM steps WHERE _id = (SELECT MIN(_id) FROM steps)";
		return getReadableDatabase().rawQuery(sql, null);
	}
	
	public Cursor getLastRow() {
		String sql = "SELECT * FROM steps WHERE _id = (SELECT MAX(_id) FROM steps)";
		return getReadableDatabase().rawQuery(sql, null);
	}

}
