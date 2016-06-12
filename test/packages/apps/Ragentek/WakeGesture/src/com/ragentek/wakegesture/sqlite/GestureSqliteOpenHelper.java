package com.ragentek.wakegesture.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GestureSqliteOpenHelper extends SQLiteOpenHelper {
    
	public static final  int  VERSION=1;
	public static final  String  DB_NAME="GESTURE_DB";
	public GestureSqliteOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        db.execSQL("create table gestureSettings(id INTEGER PRIMARY KEY AUTOINCREMENT,key INTEGER,key_description varchar(64),action_type INTEGER,information_one varchar(64),"
        		+ "information_second varchar(64),information_three varchar(64))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
