/*
 * add by zhangzixiao JBLW-482 20131216 NewFeature:message blacklist
 */
package com.android.mms.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.android.mms.util.SmileyParser2;
//import com.mediatek.encapsulation.com.google.android.mms.EncapsulatedContentType;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.Telephony.Mms;
import android.provider.Telephony.Sms.Inbox;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.mms.R;

public class BlackMessageListAdapter extends CursorAdapter {
    private static final String TAG = "BlackMessageListAdapter";
    public static final int MODE_NORMAL = 1;
    public static final int MODE_EDIT = 2;
    private int mMode = MODE_NORMAL;
    private ForegroundColorSpan mColorSpan = null;
    private Map<Long, BlackMessageItem> CACHE = new HashMap<Long, BlackMessageItem>();
	
    public BlackMessageListAdapter(Context context, Cursor c) {
	super(context, c);
    }


    public BlackMessageListAdapter(Context context, Cursor c, boolean autoRequery) {
	super(context, c, autoRequery);
    }


    public BlackMessageListAdapter(Context context, Cursor c, int flags) {
	super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	return inflater.inflate(R.layout.black_message_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
	BlackMessageItem bmItem = getBlackMessageItemFromCache(cursor);
	TextView blackBodyView = (TextView) view.findViewById(R.id.black_body_view);
	TextView blackExpireView = (TextView) view.findViewById(R.id.black_expire_view);
	TextView blackSimStatus = (TextView) view.findViewById(R.id.black_sim_status);
	TextView blackDateView = (TextView) view.findViewById(R.id.black_date_view);
	if(bmItem.getBlackMsgType() == BlackMessageItem.MMS) {
	    blackBodyView.setVisibility(View.GONE);
	    blackExpireView.setVisibility(View.VISIBLE);
	    blackExpireView.setText(formatExpire(context, bmItem.getMessageSize(), bmItem.getExpiry()));
	} else {
	    blackBodyView.setVisibility(View.VISIBLE);
	    blackExpireView.setVisibility(View.GONE);
	    blackBodyView.setText(formatMessage(bmItem.getBody()));
	    blackDateView.setText(formatDate(context, bmItem.getDate()));
	}
	//zyw
	//blackSimStatus.setText(formatSimStatus(bmItem.getSimId()));
	if(isMode(MODE_EDIT)) {
	    if(bmItem.checked) {
		view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.black_listitem_bg_blue));
	    } else {
		view.setBackgroundColor(Color.WHITE);//Color.TRANSPARENT
	    }
	} else {
	    view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.black_listitem_background));
	}
    }
	
    private String formatExpire(Context context, int messageSize, long expire) {
	String msgSizeText = mContext.getString(R.string.message_size_label)
        	+ String.valueOf((messageSize + 1023) / 1024)
        	+ mContext.getString(R.string.kilobyte);
	String mTimestamp = context.getString(R.string.expire_on, MessageUtils
                .formatTimeStampString(context, expire*1000l));
	return msgSizeText + "\t\n" + mTimestamp;
    }
	
    private CharSequence formatMessage(String body) {
	SpannableStringBuilder buf = new SpannableStringBuilder();
	if (!TextUtils.isEmpty(body)) {
	    //buf.append(parser.addSmileySpans(body));
	    //zyw
	    buf.append(body);
	}
	return buf;
    }
	/*zyw
    private CharSequence formatSimStatus(int mSimId) {
        SpannableStringBuilder buffer = new SpannableStringBuilder();
        // If we're in the process of sending a message (i.e. pending), then we show a "Sending..."
        // string in place of the timestamp.
        //Add sim info
        int simInfoStart = buffer.length();
        CharSequence simInfo = MessageUtils.getSubInfo(mContext, mSimId);
        if (simInfo.length() > 0) {
            buffer.append(simInfo);
        }
        //buffer.setSpan(mTextSmallSpan, 0, buffer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Make the timestamp text not as dark
        int color = mContext.getResources().getColor(R.color.timestamp_color);
        mColorSpan = new ForegroundColorSpan(color);
        buffer.setSpan(mColorSpan, 0, simInfoStart, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return buffer;
    }
	*/
    private String formatDate(Context context, long date) {
	if(date != 0) {
	    return String.format(context.getString(R.string.received_on),
                    MessageUtils.formatTimeStampString(context, date));
	}
	return "";
    }

    public void initCache() {
	Cursor c = getCursor();
	if(isMode(MODE_EDIT)) {
	    List<BlackMessageItem> checkedItems = getCheckedItems();
	    CACHE.clear();
	    for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
	    	BlackMessageItem bmItem = getBlackMessageItemFromCheckedItems(c, checkedItems);
	    	if(bmItem == null) {
		    bmItem = getBlackMessageItemFromCursor(c);
	    	}
	     	CACHE.put(bmItem.getId(), bmItem);
	    }   
	} else {
	    CACHE.clear();
	    for(c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
	    	BlackMessageItem bmItem = getBlackMessageItemFromCursor(c);
	     	CACHE.put(bmItem.getId(), bmItem);
	    } 
	}
    }

    private BlackMessageItem getBlackMessageItemFromCheckedItems(Cursor c, List<BlackMessageItem> checkedItems) {
	Long _id = getLong(c, "_id");
	for(BlackMessageItem bmItem : checkedItems) {
	    if(bmItem.getId() == _id) {
		return bmItem;
	    }
	}
	return null;
    }

    private BlackMessageItem getBlackMessageItemFromCache(Cursor c) {
	Long _id = getLong(c, "_id");
	BlackMessageItem item = CACHE.get(_id);
	if(item == null) {
	    item = getBlackMessageItemFromCursor(c);
	    CACHE.put(_id, item);
 	}
	return item;
    }

    public BlackMessageItem getBlackMessageItemById(long id) {
	for(BlackMessageItem bmItem : CACHE.values()) {
	    if(bmItem.getId() == id) {
		return bmItem;
	    }
 	}
	return null;
    }
	
    private BlackMessageItem getBlackMessageItemFromCursor(Cursor c) {
	BlackMessageItem bmItem = new BlackMessageItem();
	bmItem.setId(getLong(c, "_id"));
	bmItem.setBlacktextId(getInteger(c, "blacktext_id"));
	bmItem.setBlackMsgType(getInteger(c, "black_msg_type"));
	bmItem.setAddress(getString(c, Inbox.ADDRESS));
	bmItem.setDate(getLong(c, Inbox.DATE));
	bmItem.setDateSent(getLong(c, Inbox.DATE_SENT));
	bmItem.setProtocol(getInteger(c, Inbox.PROTOCOL));
	bmItem.setRead(getInteger(c, Inbox.READ));
	bmItem.setSeen(getInteger(c, Inbox.SEEN));
	bmItem.setSubject(getString(c, Inbox.SUBJECT));
	bmItem.setBody(getString(c, Inbox.BODY));
	bmItem.setReplyPathPresent(getInteger(c, Inbox.REPLY_PATH_PRESENT));
	bmItem.setServiceCenter(getString(c, Inbox.SERVICE_CENTER));
	bmItem.setErrorCode(getInteger(c, Inbox.ERROR_CODE));
	//bmItem.setSimId(getInteger(c, "sim_id"));
    bmItem.setSimId(getLong(c, "sub_id"));
	bmItem.setRetrieveText(getString(c, Mms.RETRIEVE_TEXT));
	bmItem.setSub(getString(c, Mms.SUBJECT));
	bmItem.setSubjectCharset(getInteger(c, Mms.SUBJECT_CHARSET));
	bmItem.setRetrieveTextCharset(getInteger(c, Mms.RETRIEVE_TEXT_CHARSET));
	bmItem.setContentLocation(getString(c, Mms.CONTENT_LOCATION));
	bmItem.setContentType(getString(c, Mms.CONTENT_TYPE));
	bmItem.setMessageClass(getString(c, Mms.MESSAGE_CLASS));
	bmItem.setMessageId(getString(c, Mms.MESSAGE_ID));
	bmItem.setResponseText(getString(c, Mms.RESPONSE_TEXT));
	bmItem.setTransactionId(getString(c, Mms.TRANSACTION_ID));
	bmItem.setContentClass(getInteger(c, Mms.CONTENT_CLASS));
	bmItem.setDeliveryReport(getInteger(c, Mms.DELIVERY_REPORT));
	bmItem.setMessageType(getInteger(c, Mms.MESSAGE_TYPE));
	bmItem.setMmsVersion(getInteger(c, Mms.MMS_VERSION));
	bmItem.setPriority(getInteger(c, Mms.PRIORITY));
	bmItem.setReadReport(getInteger(c, Mms.READ_REPORT));
	bmItem.setReadStatus(getInteger(c, Mms.READ_STATUS));
	bmItem.setReportAllowed(getInteger(c, Mms.REPORT_ALLOWED));
	bmItem.setReadStatus(getInteger(c, Mms.READ_STATUS));
	bmItem.setRetrieveStatus(getInteger(c, Mms.RETRIEVE_STATUS));
	bmItem.setStatus(getInteger(c, Mms.STATUS));
	bmItem.setDeliveryTime(getLong(c, Mms.DELIVERY_TIME));
	bmItem.setExpiry(getLong(c, Mms.EXPIRY));
	bmItem.setMessageSize(getInteger(c, Mms.MESSAGE_SIZE));
	bmItem.setFlag1(getInteger(c, "flag1"));
	bmItem.setFlag1(getInteger(c, "flag2"));
	bmItem.setFlag1(getInteger(c, "flag3"));
	return bmItem;
    }
	
    private Long getLong(Cursor c, String columnName) {
	Long v = null;
	try {
	    int columnIndex = c.getColumnIndexOrThrow(columnName);
	    String str = c.getString(columnIndex);
	    if(!TextUtils.isEmpty(str)) {
		v = Long.parseLong(str);
	    }
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	}
	return v;
    }
	
    private Integer getInteger(Cursor c, String columnName) {
	Integer v = null;
	try {
	    int columnIndex = c.getColumnIndexOrThrow(columnName);
	    String str = c.getString(columnIndex);
	    if(!TextUtils.isEmpty(str)) {
		v = Integer.parseInt(str);
	    }
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	}
	return v;
    }
	
    private String getString(Cursor c, String columnName) {
	String v = null;
	try {
	    v = c.getString(c.getColumnIndexOrThrow(columnName));
	} catch(Exception e) {
	    Log.e(TAG, e.toString());
	}
	return v;
    }

    public boolean isMode(int mode) {
	return mMode == mode;
    }

    public void changeMode(int mode) {
	mMode = mode;
	if(mode == MODE_NORMAL) {
	    clearChecked();
	}
    }

    public void clearChecked() {
	for(BlackMessageItem bmItem : CACHE.values()) {
	    bmItem.checked = false;
	}
	notifyDataSetChanged();
    }

    public void selectAll() {
	for(BlackMessageItem bmItem : CACHE.values()) {
	    bmItem.checked = true;
	}
	notifyDataSetChanged();
    }

    public void switchChecked(long id) {
	for(BlackMessageItem bmItem : CACHE.values()) {
	    if(bmItem.getId() == id) {
		bmItem.checked = !bmItem.checked;
		break;
	    }
	}
	notifyDataSetChanged();
    }

    public int getCheckedItemsCount() {
	int count = 0;
	for(BlackMessageItem bmItem : CACHE.values()) {
	    if(bmItem.checked) {
		count ++;
	    }
	}
	return count;
    }

    public List<BlackMessageItem> getCheckedItems() {
	List<BlackMessageItem> checkedItems = new ArrayList<BlackMessageItem>();
	for(BlackMessageItem bmItem : CACHE.values()) {
	    if(bmItem.checked) {
		checkedItems.add(bmItem);
	    }
	}
	return checkedItems;
    }

    public List<Long> getCheckedItemIds() {
	List<Long> checkedItemIds = new ArrayList<Long>();
	for(BlackMessageItem bmItem : CACHE.values()) {
	    if(bmItem.checked) {
		checkedItemIds.add(bmItem.getId());
	    }
	}
	return checkedItemIds;
    }

    public boolean isCheckedAll() {
	for(BlackMessageItem bmItem : CACHE.values()) {
	    if(!bmItem.checked) {
		return false;
	    }
	}
	return true;
    }

}
