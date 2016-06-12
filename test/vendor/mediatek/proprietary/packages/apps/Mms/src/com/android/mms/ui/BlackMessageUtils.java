/*
 * add by zhangzixiao JBLW-482 20131216 NewFeature:message blacklist
 */

package com.android.mms.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map.Entry;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SqliteWrapper;
import android.net.Uri;
import android.provider.Telephony.Mms;
import android.provider.Telephony.Sms;
import android.provider.Telephony.Mms.Part;
import android.provider.Telephony.Sms.Inbox;
import android.provider.Telephony.Threads;
import android.provider.Telephony.WapPush;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.android.mms.MmsApp;
import com.android.mms.MmsPluginManager;
import com.android.mms.util.Recycler;
import com.android.mms.util.ThreadCountManager;
import com.android.mms.widget.MmsWidgetProvider;
import com.google.android.mms.pdu.BlackPduHeaders;
import com.google.android.mms.pdu.CharacterSets;
import com.google.android.mms.pdu.EncodedStringValue;
import com.google.android.mms.pdu.NotificationInd;
import com.google.android.mms.pdu.PduHeaders;
/*
import com.mediatek.encapsulation.MmsLog;
import com.mediatek.encapsulation.android.telephony.EncapsulatedSimInfoManager;
import com.mediatek.encapsulation.android.telephony.EncapsulatedSmsMessageEx;
import com.mediatek.encapsulation.android.telephony.EncapsulatedTelephony;
import com.mediatek.mms.ext.ISmsReceiver;
*/
//import com.mediatek.mms.ext.IMmsSettingsExt;
//import com.mediatek.mms.ext.ISmsReceiverExt;
import android.os.SystemProperties;//add BUG_ID:JWYYL-81 zhangyuwen 20141216 

/**
 * An utility class for managing black messages.
 */
public class BlackMessageUtils {
    private static final String TAG = "BlackMessageUtils";
	/** add JBLW-482 zhangzixiao 20131216 start */
	public static final Uri CONTENT_URI_BLACKTEXT = Uri.parse("content://mms-sms/blacktext");
	public static final Uri CONTENT_URI_BLACK_MESSAGES = Uri.parse("content://mms-sms/black-messages");
	//public static final String SIM_ID = "sim_id";
    public static final String SIM_ID = "sub_id";
	//public static final String GEMINI_SIM_ID_KEY = "simId";
    public static final String GEMINI_SIM_ID_KEY = "sub_id";
	/** add JBLW-482 zhangzixiao 20131216 end */
    private static final HashMap<Integer, String> ENCODED_STRING_COLUMN_NAME_MAP;
    private static final HashMap<Integer, String> CHARSET_COLUMN_NAME_MAP;
    private static final HashMap<Integer, String> TEXT_STRING_COLUMN_NAME_MAP;
    private static final HashMap<Integer, String> OCTET_COLUMN_NAME_MAP;
    private static final HashMap<Integer, String> LONG_COLUMN_NAME_MAP;
    static {
	ENCODED_STRING_COLUMN_NAME_MAP = new HashMap<Integer, String>();
        ENCODED_STRING_COLUMN_NAME_MAP.put(PduHeaders.RETRIEVE_TEXT, Mms.RETRIEVE_TEXT);
        ENCODED_STRING_COLUMN_NAME_MAP.put(PduHeaders.SUBJECT, Mms.SUBJECT);
        CHARSET_COLUMN_NAME_MAP = new HashMap<Integer, String>();
        CHARSET_COLUMN_NAME_MAP.put(PduHeaders.SUBJECT, Mms.SUBJECT_CHARSET);
        CHARSET_COLUMN_NAME_MAP.put(PduHeaders.RETRIEVE_TEXT, Mms.RETRIEVE_TEXT_CHARSET);
        TEXT_STRING_COLUMN_NAME_MAP = new HashMap<Integer, String>();
        TEXT_STRING_COLUMN_NAME_MAP.put(PduHeaders.CONTENT_LOCATION, Mms.CONTENT_LOCATION);
        TEXT_STRING_COLUMN_NAME_MAP.put(PduHeaders.CONTENT_TYPE, Mms.CONTENT_TYPE);
        TEXT_STRING_COLUMN_NAME_MAP.put(PduHeaders.MESSAGE_CLASS, Mms.MESSAGE_CLASS);
        TEXT_STRING_COLUMN_NAME_MAP.put(PduHeaders.MESSAGE_ID, Mms.MESSAGE_ID);
        TEXT_STRING_COLUMN_NAME_MAP.put(PduHeaders.RESPONSE_TEXT, Mms.RESPONSE_TEXT);
        TEXT_STRING_COLUMN_NAME_MAP.put(PduHeaders.TRANSACTION_ID, Mms.TRANSACTION_ID);
        OCTET_COLUMN_NAME_MAP = new HashMap<Integer, String>();
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.CONTENT_CLASS, Mms.CONTENT_CLASS);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.DELIVERY_REPORT, Mms.DELIVERY_REPORT);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.MESSAGE_TYPE, Mms.MESSAGE_TYPE);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.MMS_VERSION, Mms.MMS_VERSION);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.PRIORITY, Mms.PRIORITY);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.READ_REPORT, Mms.READ_REPORT);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.READ_STATUS, Mms.READ_STATUS);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.REPORT_ALLOWED, Mms.REPORT_ALLOWED);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.RETRIEVE_STATUS, Mms.RETRIEVE_STATUS);
        OCTET_COLUMN_NAME_MAP.put(PduHeaders.STATUS, Mms.STATUS);
        LONG_COLUMN_NAME_MAP = new HashMap<Integer, String>();
        LONG_COLUMN_NAME_MAP.put(PduHeaders.DATE, Mms.DATE);
        LONG_COLUMN_NAME_MAP.put(PduHeaders.DELIVERY_TIME, Mms.DELIVERY_TIME);
        LONG_COLUMN_NAME_MAP.put(PduHeaders.EXPIRY, Mms.EXPIRY);
        LONG_COLUMN_NAME_MAP.put(PduHeaders.MESSAGE_SIZE, Mms.MESSAGE_SIZE);
        LONG_COLUMN_NAME_MAP.put(PduHeaders.DATE_SENT, Mms.DATE_SENT);
    }
	/*
	private static int sSlotId;   
	public static void setSmsSlotId(int slot) {       
		sSlotId = slot;    
	}
	public static int getMessageSimId(SmsMessage sms) {  
			   
		return sSlotId;    
				   
	}
	*/
    public static long getNumberInBlacklist(Context context, String address) {
    	if(TextUtils.isEmpty(address)) {
    		return -1l;
    	}
    	if(Mms.isEmailAddress(address)) {
    		address = Mms.extractAddrSpec(address);
    	}
    	boolean isEmail = Mms.isEmailAddress(address);
        boolean isPhoneNumber = Mms.isPhoneNumber(address);
        String refinedAddress = isEmail ? address.toLowerCase() : address;
        String selection = "number=?";
        String[] selectionArgs;
        if (!isPhoneNumber || (address != null && address.length() > 15)) {
            selectionArgs = new String[] { refinedAddress };
        } else {
            boolean mUseStrictPhoneNumberComparation =
                context.getResources().getBoolean(com.android.internal.R.bool.config_use_strict_phone_number_comparation);
            selection += " OR " + String.format(Locale.ENGLISH, "PHONE_NUMBERS_EQUAL(number, ?, %d)",
                        (mUseStrictPhoneNumberComparation ? 1 : 0));
            selectionArgs = new String[] { refinedAddress, refinedAddress };
        }
        Cursor c = null;
        try {
        	Uri uri = CONTENT_URI_BLACKTEXT;
        	c = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        	if(c != null && c.getCount() > 0 && c.moveToFirst()) {
        		return c.getLong(c.getColumnIndexOrThrow("_id"));
        	}
        } finally {
        	if(c!=null)
        		c.close();
        }
        return -1;
    }
	
    public static void insertSmsToBlacklist(Context context, SmsMessage[] msgs, int error, long blacktextId) {
   	SmsMessage sms = msgs[0];
   	if(sms.getMessageClass()== SmsMessage.MessageClass.CLASS_0 || sms.isReplace()) {
   	    return;
   	}
   	ContentValues values = extractContentValues(sms);
        values.put(Sms.ERROR_CODE, error);
        values.put("blacktext_id", blacktextId);
        values.put("black_msg_type", BlackMessageItem.SMS);
        int pduCount = msgs.length;
        // M:Code analyze 034, fix bug ALPS00352897,fix long messages splicing error @{
        //zyw
        //ISmsReceiverExt smsReceiverPlugin = (ISmsReceiverExt) MmsPluginManager.getMmsPluginObject(MmsPluginManager.MMS_PLUGIN_TYPE_SMS_RECEIVER);
        //if (smsReceiverPlugin != null) { //values.put(Inbox.BODY, ...);
            extractSmsBody(msgs, sms, values);
        //}
        /// @}
        /*
        Log.e(MmsApp.TXN_TAG, "slotid = " + EncapsulatedSmsMessageEx.getMessageSimId(sms));
        EncapsulatedSimInfoManager si = EncapsulatedSimInfoManager.getSimInfoBySlot(context, EncapsulatedSmsMessageEx.getMessageSimId(sms));
        if (null == si) {
            Log.e(MmsApp.TXN_TAG, "SmsReceiverService:SIMInfo is null for slot "
                        + EncapsulatedSmsMessageEx.getMessageSimId(sms));
            return;
        }
        values.put(SIM_ID, (int)si.getSimId());
		*/
        int messageSize = 0;
        if (pduCount == 1) {
            messageSize = sms.getPdu().length;
        } else {
            for (int i = 0; i < pduCount; i++) {
                sms = msgs[i];
                messageSize += sms.getPdu().length;
            }
        }
        values.put(Mms.MESSAGE_SIZE, messageSize);
        
        ContentResolver resolver = context.getContentResolver();
        SqliteWrapper.insert(context, resolver, CONTENT_URI_BLACK_MESSAGES, values);
    }
	
    private static ContentValues extractContentValues(SmsMessage sms) {
        ContentValues values = new ContentValues();

        values.put(Inbox.ADDRESS, sms.getDisplayOriginatingAddress());
        values.put(Inbox.DATE, Long.valueOf(System.currentTimeMillis()));
        values.put(Inbox.DATE_SENT, Long.valueOf(sms.getTimestampMillis()));
        values.put(Inbox.PROTOCOL, sms.getProtocolIdentifier());
        values.put(Inbox.READ, 1);
        values.put(Inbox.SEEN, 1);
        if (sms.getPseudoSubject().length() > 0) {
            values.put(Inbox.SUBJECT, sms.getPseudoSubject());
        }
        values.put(Inbox.REPLY_PATH_PRESENT, sms.isReplyPathPresent() ? 1 : 0);
        values.put(Inbox.SERVICE_CENTER, sms.getServiceCenterAddress());
        return values;
    }
	
    public static boolean copySmsFromBlacklist(Context context, BlackMessageItem bmItem) {
	ContentValues values = new ContentValues();
	values.put(Inbox.ADDRESS, bmItem.getAddress());
	values.put(Inbox.DATE, /*bmItem.getDate()*/System.currentTimeMillis());
	values.put(Inbox.DATE_SENT, bmItem.getDateSent());
	values.put(Inbox.PROTOCOL, bmItem.getProtocol());
	values.put(Inbox.READ, bmItem.getRead());
	values.put(Inbox.SEEN, bmItem.getSeen());
	values.put(Inbox.SUBJECT, bmItem.getSubject());
	values.put(Inbox.REPLY_PATH_PRESENT, bmItem.getReplyPathPresent());
	values.put(Inbox.SERVICE_CENTER, bmItem.getServiceCenter());
	values.put(Inbox.ERROR_CODE, bmItem.getErrorCode());
	values.put(Inbox.BODY, bmItem.getBody());
	values.put(SIM_ID, bmItem.getSimId());
	values.put(Mms.MESSAGE_SIZE, bmItem.getMessageSize());
	Uri insertedUri = SqliteWrapper.insert(context, context.getContentResolver(), 
		Inbox.CONTENT_URI, values);
	long threadId = -1l;
	Cursor cur = SqliteWrapper.query(context, context.getContentResolver(), insertedUri,
                new String[] {"thread_id"}, null, null, null);
        try {
            if (cur != null && cur.moveToFirst()) {
                threadId = cur.getLong(0);
            }
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        ThreadCountManager.getInstance().isFull(threadId, context, ThreadCountManager.OP_FLAG_INCREASE);
        Recycler.getSmsRecycler().deleteOldMessagesByThreadId(context, threadId);
        MmsWidgetProvider.notifyDatasetChanged(context);
	    return true; //simple return true, Subsequent expansion
	}
	
    public static void insertMmsToBlacklist(Context context, NotificationInd pdu, long blacktextId, 
		long simId, String serviceAddr) {
	BlackPduHeaders header = pdu.getBlackPduHeaders();
        ContentValues values = new ContentValues();
        values.put("blacktext_id", blacktextId);
        values.put("black_msg_type", BlackMessageItem.MMS);
	values.put("sim_id", simId);
	values.put("read", 1);
	values.put("seen", 1);
	values.put(WapPush.SERVICE_ADDR, serviceAddr);
        Set<Entry<Integer, String>> set;
        set = ENCODED_STRING_COLUMN_NAME_MAP.entrySet();
        for (Entry<Integer, String> e : set) {
            int field = e.getKey();
            EncodedStringValue encodedString = header.getEncodedStringValue(field);
            if (encodedString != null) {
                String charsetColumn = CHARSET_COLUMN_NAME_MAP.get(field);
                values.put(e.getValue(), toIsoString(encodedString.getTextString()));
                values.put(charsetColumn, encodedString.getCharacterSet());
            }
        }
        set = TEXT_STRING_COLUMN_NAME_MAP.entrySet();
        for (Entry<Integer, String> e : set){
            byte[] text = header.getTextString(e.getKey());
            if (text != null) {
                values.put(e.getValue(), toIsoString(text));
            }
        }
        set = OCTET_COLUMN_NAME_MAP.entrySet();
        for (Entry<Integer, String> e : set){
            int b = header.getOctet(e.getKey());
            if (b != 0) {
                values.put(e.getValue(), b);
            }
        }
        set = LONG_COLUMN_NAME_MAP.entrySet();
        for (Entry<Integer, String> e : set){
            long l = header.getLongInteger(e.getKey());
            if (l != -1L) {
                values.put(e.getValue(), l);
            }
        }
        EncodedStringValue v = header.getEncodedStringValue(PduHeaders.FROM);
        String number = v.getString();
        values.put(Inbox.ADDRESS, number);
        SqliteWrapper.insert(context, context.getContentResolver(), 
        		CONTENT_URI_BLACK_MESSAGES, values);
        
    }
	
    public static String toIsoString(byte[] bytes) {
        try {
            return new String(bytes, CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (NullPointerException e) {
            return "";
        }
    }
	
    public static boolean copyMmsFromBlacklist(Context context, BlackMessageItem bmItem) {
	ContentValues values = new ContentValues();
	values.put(Mms.RETRIEVE_TEXT, bmItem.getRetrieveText());
	values.put(Mms.SUBJECT, bmItem.getSub());
	values.put(Mms.SUBJECT_CHARSET, bmItem.getSubjectCharset());
	values.put(Mms.RETRIEVE_TEXT_CHARSET, bmItem.getRetrieveTextCharset());
	values.put(Mms.CONTENT_LOCATION, bmItem.getContentLocation());
	values.put(Mms.CONTENT_TYPE, bmItem.getContentType());
	values.put(Mms.MESSAGE_CLASS, bmItem.getMessageClass());
	values.put(Mms.MESSAGE_ID, bmItem.getMessageId());
	values.put(Mms.RESPONSE_TEXT, bmItem.getResponseText());
	values.put(Mms.TRANSACTION_ID, bmItem.getTransactionId());
	values.put(Mms.CONTENT_CLASS, bmItem.getContentClass());
	values.put(Mms.DELIVERY_REPORT, bmItem.getDeliveryReport());
	values.put(Mms.MESSAGE_TYPE, bmItem.getMessageType());
	values.put(Mms.MMS_VERSION, bmItem.getMmsVersion());
	values.put(Mms.PRIORITY, bmItem.getPriority());
	values.put(Mms.READ_REPORT, bmItem.getReadReport());
	values.put(Mms.READ_STATUS, bmItem.getReadStatus());
	values.put(Mms.REPORT_ALLOWED, bmItem.getReportAllowed());
	values.put(Mms.RETRIEVE_STATUS, bmItem.getRetrieveStatus());
	values.put(Mms.STATUS, bmItem.getStatus());
	values.put(Mms.DATE, /*bmItem.getDate()*/Math.round(System.currentTimeMillis()*1.0/1000));
	values.put(Mms.DELIVERY_TIME, bmItem.getDeliveryTime());
	values.put(Mms.EXPIRY, bmItem.getExpiry());
	values.put(Mms.MESSAGE_SIZE, bmItem.getMessageSize());
	values.put(Mms.DATE_SENT, bmItem.getDateSent());
	values.put(SIM_ID, bmItem.getSimId());
	values.put(WapPush.SERVICE_ADDR, bmItem.getServiceCenter());
	values.put(Mms.READ, 1);
	values.put(Mms.SEEN, 1);
	String recipient = bmItem.getAddress();
	long threadId = Threads.getOrCreateThreadId(context, recipient);
	values.put(Mms.THREAD_ID, threadId);
	values.put("need_notify", false);
	Uri res = SqliteWrapper.insert(context, context.getContentResolver(), Mms.Inbox.CONTENT_URI, values);
	long msgId = ContentUris.parseId(res);
	EncodedStringValue addr = new EncodedStringValue(recipient);
	persistFromAddr(context, addr, msgId);
	values = new ContentValues(1);
	values.put(Part.MSG_ID, msgId);
	values.put("need_notify", true);
	long dummyId = System.currentTimeMillis();
	SqliteWrapper.update(context, context.getContentResolver(),
                Uri.parse("content://mms/" + dummyId + "/part"),
                values, null, null);
	return true; //simple return true, Subsequent expansion
    }
	
    private static void persistFromAddr(Context mContext, EncodedStringValue addr, long msgId) {
	Uri uri = Uri.parse("content://mms/" + msgId + "/addr");
	ContentValues values = new ContentValues();
	ArrayList<String> strValues = new ArrayList<String>();
	strValues.add(toIsoString(addr.getTextString()));
        strValues.add(String.valueOf(addr.getCharacterSet()));
        strValues.add(String.valueOf(PduHeaders.FROM));
        values.putStringArrayList("addresses",strValues);
        try{
            SqliteWrapper.insert(mContext, mContext.getContentResolver(), uri, values);
        } catch (Exception e){
            Log.e(TAG, "persistAddress, excepiton....");
        }
    }

    public static boolean deleteBlackMessageById(Context mContext, Long id) {
	if(id==null || id<=0) return false;
	try {
	    String where = "_id="+id;
	    SqliteWrapper.delete(mContext, mContext.getContentResolver(), 
		    CONTENT_URI_BLACK_MESSAGES, where, null);
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	    return false;
	}
	return true;
    }
	
    public static boolean deleteBlackMessagesByIds(Context mContext, List<Long> ids) {
	if(ids==null || ids.size()<=0) return false;
	try {
	    StringBuilder sb = new StringBuilder("_id IN (");
	    for(int i=0;i<ids.size();i++) {
		sb.append(ids.get(i));
		if(i==ids.size()-1) {
		    sb.append(")");
		} else {
		    sb.append(",");
		}
	    }
	    String where = sb.toString();
	    SqliteWrapper.delete(mContext, mContext.getContentResolver(), 
		    CONTENT_URI_BLACK_MESSAGES, where, null);
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	    return false;
	}
	return true;
    }
	
    public static boolean deleteBlackMessagesByBlackTextId(Context mContext, Integer blackTextId) {
	if(blackTextId==null || blackTextId<=0) return false;
	try {
	    String where = "blacktext_id="+blackTextId;
	    SqliteWrapper.delete(mContext, mContext.getContentResolver(), 
		    CONTENT_URI_BLACK_MESSAGES, where, null);
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	    return false;
	}
	return true;
    }

    public static boolean supportBlacklist() {
		if("1".equals(SystemProperties.get("ro.rgk_support_msg_blacklist"))){
			return true;
		}
		return false;			
    }

    public static void extractSmsBody(SmsMessage[] msgs, SmsMessage sms, ContentValues values) {
        int pduCount = msgs.length;

        Log.d(TAG, "SmsReceiverImpl.extractSmsBody, pduCount=" + pduCount);

        if (pduCount == 1) {
            // There is only one part, so grab the body directly.
            values.put(Inbox.BODY, replaceFormFeeds(sms.getDisplayMessageBody()));
        } else {
            // Build up the body from the parts.
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < pduCount; i++) {
                sms = msgs[i];
                if (sms.mWrappedSmsMessage != null) {
                    body.append(sms.getDisplayMessageBody());
                }
            }
            values.put(Inbox.BODY, replaceFormFeeds(body.toString()));
        }
    }

    public static String replaceFormFeeds(String s) {
        // Some providers send formfeeds in their messages. Convert those formfeeds to newlines.
        return s == null ? "" : s.replace('\f', '\n');
    }
}
