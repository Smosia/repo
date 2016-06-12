/**
* add this file : JEYS-57 zhangzixiao 20131226
*/
package com.android.mms.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SqliteWrapper;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Telephony.Sms;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.MmsSms.PendingMessages;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;

import com.android.mms.R;
import com.android.mms.transaction.ScheduledReceiver;
import com.android.mms.transaction.DefaultRetryScheme;


import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import com.android.internal.telephony.ITelephony;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import android.telephony.SubscriptionManager;
//import android.telephony.SubInfoRecord;
import android.telephony.SubscriptionInfo;
import com.android.mms.MmsApp;

import java.util.List;
public class ScheduledMessageUtils {
    private static final String TAG = "ScheduledMessageUtils";
    private static final String KEY_SCHEDULED_MESSAGES = "key_scheduled_messages";
    private static final String KEY_SCHEDULED_REQUEST_CODE = "key_scheduled_request_code";
    private static final String KEY_SCHEDULED_TIME = "key_scheduled_time";

    public static void add(Context context, Uri uri, Long value) {
	Map<String, Long> scheduledMap = getScheduledMap(context);
	String key = uri.toString();
	scheduledMap.put(key, value);
	startTimer(context, uri, value);
	persist(context, scheduledMap);
    }

    public static void startTimer(Context context, Uri uri, long milliseconds) {
	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	Intent intent = new Intent(ScheduledReceiver.ACTION_SCHEDULED);
	intent.putExtra(ScheduledReceiver.SCHEDULED_URI, uri.toString());
	PendingIntent operation = PendingIntent.getBroadcast(context, 
			getRequestCode(context), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(milliseconds);
	alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), operation);
    }

    private static int getRequestCode(Context context) {
	final int MIN = 19869000;
	final int MAX = 19879000;
	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	int requestCode = sharedPreferences.getInt(KEY_SCHEDULED_REQUEST_CODE, MIN);
	if(requestCode == MAX) {
	    requestCode = MIN;
	} else {
	    requestCode++;
	}
	sharedPreferences.edit().putInt(KEY_SCHEDULED_REQUEST_CODE, requestCode).commit();
	return requestCode;
    }
	
    public static void remove(Context context, Uri uri) {
	Map<String, Long> scheduledMap = getScheduledMap(context);
	String key = uri.toString();
	scheduledMap.remove(key);
	persist(context, scheduledMap);
    }
	
    public static Long get(Context context, String key) {
	return getScheduledMap(context).get(key);
    }
	
    public static Set<String> getKeySet(Context context) {
	return getScheduledMap(context).keySet();
    }
	
    public static int init(Context context) {
	int smsCount = 0;
	Map<String, Long> scheduledMap = new HashMap<String, Long>();
	Set<String> scheduledSet = PreferenceManager
		.getDefaultSharedPreferences(context).getStringSet(KEY_SCHEDULED_MESSAGES, null);
	if(scheduledSet!=null && scheduledSet.size()>0) {
	    for(String scheduled : scheduledSet) {
		try {
		    String[] tmp = scheduled.split("=");
		    Uri uri = Uri.parse(tmp[0]);
		    long milliseconds = Long.parseLong(tmp[1]);
		    if(milliseconds <= System.currentTimeMillis()) {
			smsCount += setToFailed(context, uri);
		    } else if(existUri(context, uri)) {
			scheduledMap.put(tmp[0], milliseconds);
			startTimer(context, uri, milliseconds);
		    }
		} catch(Exception e) {
		    Log.e(TAG, e.toString(), e);
		}
	    }
	}
	persist(context, scheduledMap);
	return smsCount;
    }

    private static boolean existUri(Context context, Uri uri) {
	boolean exist = false;
	Cursor c = null;
	try {
	    c = SqliteWrapper.query(context, context.getContentResolver(), uri, null, null, null, null);
	    if(c!=null && c.getCount()==1) {
		exist = true;
	    }
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	} finally {
	    if(c!=null)
		c.close();
	}
	return exist;
    }

    public static boolean hasSimCard(Context ctx, int simId) {
        Log.v(TAG, "simId = " + simId);
//        SubInfoRecord mSubInfoRecord = SubscriptionManager.getSubInfoForSubscriber((long)simId);
        SubscriptionInfo mSubInfoRecord  = SubscriptionManager.from(MmsApp.getApplication())
                                           .getActiveSubscriptionInfo(simId);
        Log.v(TAG, "mSubInfoRecord = " + mSubInfoRecord + ",mSubInfoRecord.slotId = " + mSubInfoRecord.getSimSlotIndex() );
        if(mSubInfoRecord != null) {
            if(mSubInfoRecord.getSimSlotIndex() >= 0) {
			    return true;
	        }
	    }
        return false;
    }

    public static int setToFailed(Context context, Uri uri) {
	int smsCount = 0;
	ContentValues values = new ContentValues();
	try {
	    if("sms".equals(uri.getHost())) {
	    	values.put(Sms.TYPE, Sms.MESSAGE_TYPE_FAILED);
	    	values.put(Sms.ERROR_CODE, SmsManager.RESULT_ERROR_GENERIC_FAILURE);
	    	values.put(Sms.READ, Integer.valueOf(0));
	    	smsCount = SqliteWrapper.update(context, context.getContentResolver(), uri, values, null, null);
	    } else if("mms".equals(uri.getHost())) {
		moveMmsToOutbox(context, uri);
		long msgId = ContentUris.parseId(uri);
		DefaultRetryScheme scheme = new DefaultRetryScheme(context, 100);
	    	values.put(PendingMessages.ERROR_TYPE, MmsSms.ERR_TYPE_GENERIC_PERMANENT);
	    	values.put(PendingMessages.RETRY_INDEX, scheme.getRetryLimit());
		String where = PendingMessages.MSG_ID + "=" + msgId + " AND " + PendingMessages.PROTO_TYPE + "=" + MmsSms.MMS_PROTO;
	    	SqliteWrapper.update(context, context.getContentResolver(), PendingMessages.CONTENT_URI, values, where, null);
	    }
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	}
	return smsCount;
    }
	
    private static void persist(Context context, Map<String, Long> scheduledMap) {
	Set<String> scheduledSet = new HashSet<String>();
	for(String key : scheduledMap.keySet()) {
	    scheduledSet.add(key + "=" + scheduledMap.get(key));
	}
	PreferenceManager.getDefaultSharedPreferences(context)
		.edit().putStringSet(KEY_SCHEDULED_MESSAGES, scheduledSet).commit();
    }

    private static Map<String, Long> getScheduledMap(Context context) {
	Map<String, Long> scheduledMap = new HashMap<String, Long>();
	Set<String> scheduledSet = PreferenceManager
		.getDefaultSharedPreferences(context).getStringSet(KEY_SCHEDULED_MESSAGES, null);
	try {
	    for(String scheduled : scheduledSet) {
	    	String[] tmp = scheduled.split("=");
	    	if(tmp.length == 2) {
		    scheduledMap.put(tmp[0], Long.parseLong(tmp[1]));
	    	}
	    }
	} catch(Exception e) {
	    Log.e(TAG, e.toString(), e);
	}
	return scheduledMap;
    }

    public static void resetScheduledTime(Context context) {
	PreferenceManager.getDefaultSharedPreferences(context)
			.edit().putLong(KEY_SCHEDULED_TIME, -1L).commit();
    }

    public static long getScheduledTime(Context context) {
	return PreferenceManager.getDefaultSharedPreferences(context).getLong(KEY_SCHEDULED_TIME, -1L);
    }

    public static void setScheduledTime(Context context, long scheduledTime) {
	PreferenceManager.getDefaultSharedPreferences(context)
			.edit().putLong(KEY_SCHEDULED_TIME, scheduledTime).commit();
    }

    /*
    public static boolean needScheduled(Context context) {
	return getScheduledTime(context) > System.currentTimeMillis();
    }
    */

    public static void moveSmsToScheduled(Context context, Uri uri, long scheduledTime) {
	ContentValues values = new ContentValues(2);
	values.put(Sms.TYPE, 8);
	values.put(Telephony.TextBasedSmsColumns.DATE, scheduledTime);
	SqliteWrapper.update(context, context.getContentResolver(), uri, values, null, null);
    }

    public static void moveSmsToQueued(Context context, Uri uri) {
	ContentValues values = new ContentValues(2);
	values.put(Sms.TYPE, Sms.MESSAGE_TYPE_QUEUED);
	values.put(Telephony.TextBasedSmsColumns.DATE, System.currentTimeMillis());
	SqliteWrapper.update(context, context.getContentResolver(), uri, values, null, null);
    }

    public static void moveMmsToScheduled(Context context, Uri uri, long scheduledTime) {
	ContentValues values = new ContentValues(2);
	values.put(Mms.MESSAGE_BOX, 8);
	values.put(Mms.DATE, scheduledTime/1000L);
	SqliteWrapper.update(context, context.getContentResolver(), uri, values, null, null);
    }

    public static void moveMmsToOutbox(Context context, Uri uri) {
	ContentValues values = new ContentValues(2);
	values.put(Mms.MESSAGE_BOX, Mms.MESSAGE_BOX_OUTBOX);
	values.put(Mms.DATE, System.currentTimeMillis() / 1000L);
	SqliteWrapper.update(context, context.getContentResolver(), uri, values, null, null);
    }

    public static boolean supportScheduledMessage(Context context) {
	return context.getResources().getBoolean(R.bool.support_scheduled_message);
    }
    
}
