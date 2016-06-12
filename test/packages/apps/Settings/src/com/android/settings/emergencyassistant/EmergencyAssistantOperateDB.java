package com.android.settings.emergencyassistant;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;

import android.util.Log;

public class EmergencyAssistantOperateDB{

	private static final String TAG = "Log_Mao";

	protected EmergencyAssistantHandlerDB mDB;
	protected SQLiteDatabase mDbSessions;

	protected Context mContext;
	protected ContactInfo mContactData;
	protected static List<ContactInfo> mContactDataList; 
	private static EmergencyAssistantOperateDB operateDb = null;
	protected static final int CONTACT_COUNT = 4;

	public EmergencyAssistantOperateDB(Context context) {
		mContext = context;
		mContactData = new ContactInfo();
		mContactDataList = new ArrayList<ContactInfo>();
		try {
			mDB = new EmergencyAssistantHandlerDB(mContext);
			mDbSessions = mDB.getReadableDatabase();
			//mDbSessions = mDB.getWritableDatabase();
			loadContactInfo();
		} catch (SQLException e) {
			throw new RuntimeException("can't open session database");
		}
	}

	protected void loadContactInfo() {
		String[] columns = new String[] {EmergencyAssistantHandlerDB.CONTACTINFO_ID,
				EmergencyAssistantHandlerDB.CONTACTINFO_CONTACTID,
				EmergencyAssistantHandlerDB.CONTACTINFO_NUMBER,
				EmergencyAssistantHandlerDB.CONTACTINFO_NAME,
				EmergencyAssistantHandlerDB.CONTACTINFO_ICON
		};
		if (mContactDataList == null) {
			mContactDataList = new ArrayList<ContactInfo>();
		}

		Cursor cursor = null;
		try {
			cursor = mDbSessions.query(EmergencyAssistantHandlerDB.TABLE_EMERGENCY_CONTACT, columns, null, null,
					null, null, null);
			Log.d(TAG, "loadContactInfo cursor=" + cursor);
			if (cursor != null && cursor.getCount() > 0) {
				Log.d(TAG, "loadContactInfo cursor count=" + cursor.getCount());
				cursor.moveToFirst();
				do {
					ContactInfo contactData = new ContactInfo();
					contactData.contactId = cursor.getInt(1);
					contactData.contactNumber = cursor.getString(2);
					contactData.contactName = cursor.getString(3);
					BitmapFactory.Options option = new BitmapFactory.Options();
					option.inPreferredConfig = Bitmap.Config.RGB_565;
					byte[] photo = cursor.getBlob(4);
					if (photo == null) {
						contactData.contactPhoto = null;
					} else {
						contactData.contactPhoto = BitmapFactory.decodeByteArray(photo, 0, photo.length, option);
					}
					Log.d(TAG, "loadContactInfo mxl contactData.contactNumber=" + contactData.contactNumber
						+ ", contactData.contactName =" + contactData.contactName );
					mContactDataList.add(contactData);
				} while(cursor.moveToNext());
				
			}

		} catch (Exception e) {
			Log.e(TAG, "loadContactInfo error!");

		} finally {
			if (cursor != null) {
				cursor.close();
			}
			columns = null;
		}
	}

	public List<ContactInfo> getContactInfoList() {
		Log.d(TAG, "getContactInfoList mContactInfoList.size=" + mContactDataList.size());
		for (int i = 0; i < mContactDataList.size(); i++) {
			Log.d(TAG, "mContactDataList[" + i +"].contactName=" + mContactDataList.get(i).contactName
				 + ", contactNumber=" + mContactDataList.get(i).contactNumber);
		}
		return mContactDataList;
	}

  /**
	* save the current contact info to db emergencyassistant.db
	*/
	public boolean addContactInfo(ContactInfo data, long index) throws Exception{
		if (data == null)
			return false;
		
		Log.d(TAG, "data.contactId=" + data.contactId);
		ContentValues value = null;
		Bitmap photo = null;
		try {
			value = new ContentValues();
			value.put(EmergencyAssistantHandlerDB.CONTACTINFO_CONTACTID, data.contactId);
			value.put(EmergencyAssistantHandlerDB.CONTACTINFO_NUMBER, data.contactNumber);
			value.put(EmergencyAssistantHandlerDB.CONTACTINFO_NAME, data.contactName);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			photo = data.contactPhoto;
			photo.compress(Bitmap.CompressFormat.JPEG, 100, os);
			value.put(EmergencyAssistantHandlerDB.CONTACTINFO_ICON, os.toByteArray());
			mDbSessions.insert(EmergencyAssistantHandlerDB.TABLE_EMERGENCY_CONTACT, null, value);
			os.flush();
			os.close();
			os = null;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Failed to write the emergencyassistant.db!");
		} finally {
			value = null;
		}
		//loadContactInfo();
		return true;
	}

  /**
	* delete contact info from emergencyassistant.db
	*/
	public void deleteContactInfo(long index) {
		Log.d(TAG, "deleteContactInfo index=" +index);
		mDbSessions.delete(EmergencyAssistantHandlerDB.TABLE_EMERGENCY_CONTACT
			, EmergencyAssistantHandlerDB.CONTACTINFO_CONTACTID+"="+index, null);
	}

	@Override
	protected void finalize()throws Throwable {
		super.finalize();
		if(mDbSessions != null)
			mDbSessions.close();
		if(mDB != null)
			mDB.close();
		mDB = null;
		mDbSessions = null;
	}

}