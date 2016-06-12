package com.gcl.provider.oobe;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "oobe.db";
	private static final int DATABASE_VERSION = 1;

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE oobe (_id integer primary key autoincrement, name varchar(20), value varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVerion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS oobe");
		onCreate(db);
	}

}
