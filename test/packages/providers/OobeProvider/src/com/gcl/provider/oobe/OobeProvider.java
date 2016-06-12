package com.gcl.provider.oobe;

import android.R.integer;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class OobeProvider extends ContentProvider {

	private static final String AUTHORITIES = "com.gcl.provider.oobe";

	private static final int OOBE = 1;
	private static final int ITEM = 2;

	private String USER_DIR = "";
	private String USERS_ITEM = "";

	private DbHelper dbHelper;
	private static UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITIES, "oobe", OOBE);
		uriMatcher.addURI(AUTHORITIES, "oobe/#", ITEM);

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case OOBE:
			count = db.delete("oobe", selection, selectionArgs);
			return count;
		case ITEM:
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			count = db.delete("oobe", where, selectionArgs);
			return count;

		default:
			throw new IllegalArgumentException("Unknow Uri: " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case OOBE:
			return AUTHORITIES + "/oobe";
		case ITEM:
			return AUTHORITIES + "/oobe";
		default:
			throw new IllegalArgumentException("Unknow Uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i("guocl", "insert: " + values + "\nuri: " + uri);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case OOBE:
			long rowId = db.insert("oobe", "name", values);
			Uri insertUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(uri, null);
			return insertUri;
			
		default:
			throw new IllegalArgumentException("Unknow Uri: " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		switch (uriMatcher.match(uri)) {
		case OOBE:
			return database.query("oobe", projection, selection, selectionArgs,
					null, null, sortOrder);

		case ITEM:
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}

			return database.query("oobe", projection, where, selectionArgs,
					null, null, sortOrder);

		default:
			throw new IllegalArgumentException("Unknow Uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case OOBE:
			count = db.update("oobe", values, selection, selectionArgs);
			return count;
		case ITEM:
			long id = ContentUris.parseId(uri);
			String where = "_id=" + id;
			if (selection != null && !"".equals(selection)) {
				where = selection + " and " + where;
			}
			count = db.update("oobe", values, where, selectionArgs);
			return count;
		default:
			throw new IllegalArgumentException("Unknow Uri: " + uri);
		}
	}

}
