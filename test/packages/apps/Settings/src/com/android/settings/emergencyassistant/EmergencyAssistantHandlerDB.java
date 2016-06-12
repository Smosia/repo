package com.android.settings.emergencyassistant;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Environment;
import android.util.Log;

public class EmergencyAssistantHandlerDB extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "emergencyassistant.db";
	private static final int DATABASE_VERSION = 2;

	// define emergency contact table
	public static final String TABLE_EMERGENCY_CONTACT = "emergency_contacts";
	public static final String CONTACTINFO_ID = "_Id";
	public static final String CONTACTINFO_CONTACTID = "contactId";
	public static final String CONTACTINFO_NUMBER = "contactNumber";
	public static final String CONTACTINFO_NAME = "contactName";
	public static final String CONTACTINFO_ICON = "contactPhoto";

	public EmergencyAssistantHandlerDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	boolean isNewDB = false;

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_EMERGENCY_CONTACT + "("
			+ CONTACTINFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ CONTACTINFO_CONTACTID + " INTEGER,"
			+ CONTACTINFO_NUMBER + " TEXT,"
			+ CONTACTINFO_NAME + " TEXT, "
			+ CONTACTINFO_ICON + " BLOB )");
		isNewDB = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("Log_Mao", "onUpgrade");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// if (isNewDB) {
		// 	db.execSQL("INSERT INTO " + TABLE_EMERGENCY_CONTACT
		// 		+ " ( " + );
		// }
		super.onOpen(db);
	}

	@Override
	public synchronized void close() {
		super.close();
	}
	

}