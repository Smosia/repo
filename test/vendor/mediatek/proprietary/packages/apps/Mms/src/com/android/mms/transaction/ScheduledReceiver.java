package com.android.mms.transaction;

import com.android.mms.ui.ScheduledMessageUtils;
import com.android.mms.util.SendingProgressTokenManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SqliteWrapper;
import android.net.Uri;
import android.util.Log;
import android.provider.Telephony.Mms;
import com.google.android.mms.pdu.PduHeaders;
import android.content.ContentValues;
import com.android.internal.telephony.PhoneConstants;
/**
 * add this file : JEYS-57 zhangzixiao 20131226
 */
public class ScheduledReceiver extends BroadcastReceiver {
    private static final String TAG = "ScheduledReceiver";
    public static final String ACTION_SCHEDULED = "rgk.action.scheduled";
    public static final String SCHEDULED_URI = "scheduled_uri";
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String action = intent.getAction();
	Log.i(TAG, "receiver:action="+action+",uri="+intent.getStringExtra(SCHEDULED_URI));
        if(ACTION_SCHEDULED.equals(action)) {
            String uriStr = intent.getStringExtra(SCHEDULED_URI);
            Uri uri = Uri.parse(uriStr);
            if(isSms(uri)) {
        	sendSms(uri);
            } else if(isMms(uri)) {
        	sendMms(uri);
            }
        }
    }
    
    private boolean isSms(Uri uri) {
    	return "sms".equals(uri.getHost());
    }
    
    private boolean isMms(Uri uri) {
    	return "mms".equals(uri.getHost());
    }
    
    private void sendSms(Uri uri) {
    	Cursor c = null;
    	try {
	    ScheduledMessageUtils.remove(mContext, uri);
    	    String[] projection = new String[] { "_id","sub_id" };
    	    c = SqliteWrapper.query(mContext, mContext.getContentResolver(), uri, projection, null, null, null);
    	    if(c!=null && c.getCount()==1 && c.moveToFirst()) {
		int simId = c.getInt(1);
		
		if(!ScheduledMessageUtils.hasSimCard(mContext, simId)) {
			Log.v(TAG,"didn't send broadcast");
		    ScheduledMessageUtils.setToFailed(mContext, uri);
		    MessagingNotification.notifySendFailed(mContext, true);
		} else { 
		    ScheduledMessageUtils.moveSmsToQueued(mContext, uri);
		    Intent sentIt = new Intent(SmsReceiverService.ACTION_SEND_MESSAGE, null, mContext,
    		                SmsReceiver.class);
    		    //sentIt.putExtra(EncapsulatedPhone.GEMINI_SIM_ID_KEY, simId);
				//sentIt.putExtra(Mms.SUB_ID, simId);
				Log.v(TAG,"send broadcast");
    		    mContext.sendBroadcast(sentIt);   
		}
    	    }
    	} catch(Exception e) {
    	    Log.e(TAG, e.toString(), e);
    	} finally {
    	    if(c!=null)
    		c.close();
    	}
    }
    
    public void sendMms(Uri uri) {
    	Cursor c = null;
    	try {
	    ScheduledMessageUtils.remove(mContext, uri);
    	    String[] projection = new String[] { "_id","thread_id","sub_id" };  
    	    c = SqliteWrapper.query(mContext, mContext.getContentResolver(), uri, projection, null, null, null);
    	    if(c!=null && c.getCount()==1 && c.moveToFirst()) {
		int simId = c.getInt(2);
		
		
		if(!ScheduledMessageUtils.hasSimCard(mContext, simId)) {
		    ContentValues stateValues = new ContentValues(2);
		    stateValues.put(Mms.READ, 0);
		    stateValues.put(Mms.STATUS, PduHeaders.STATUS_UNREACHABLE);
		    SqliteWrapper.update(mContext, mContext.getContentResolver(), uri, stateValues, null,null);
		    MessagingNotification.notifySendFailed(mContext, true);
		    ScheduledMessageUtils.setToFailed(mContext, uri);
		} else {
 		    ScheduledMessageUtils.moveMmsToOutbox(mContext, uri);
    		    long messageId = c.getLong(0);
    		    long threadId = c.getLong(1);
		    // Start MMS transaction service
    	            SendingProgressTokenManager.put(messageId, threadId);
    	            /// M:Code analyze 004, ad extra flags,set trasaction_type as send_transaction,aim to
    	            /// launch sendTransaction to send message @{
    	            Intent transactionIntent = new Intent(mContext, TransactionService.class);
    	            transactionIntent.putExtra(TransactionBundle.URI, uri.toString());
    	            transactionIntent.putExtra(TransactionBundle.TRANSACTION_TYPE, Transaction.SEND_TRANSACTION);
    	            /// M: add if for gemini
    	            /*if (EncapsulatedFeatureOption.MTK_GEMINI_SUPPORT) {        
    	            	transactionIntent.putExtra(EncapsulatedPhone.GEMINI_SIM_ID_KEY, simId);
    	            }*/
					Log.v(TAG," simId = " + simId);
                    // modify  BUG_ID: JWBLB-607   zhangyuwen  20150526(start)
                    //transactionIntent.putExtra(PhoneConstants.SUBSCRIPTION_KEY, (long)simId);
                    transactionIntent.putExtra(PhoneConstants.SUBSCRIPTION_KEY, simId);
                    // modify  BUG_ID: JWBLB-607   zhangyuwen  20150526(end)

    	            mContext.startService(transactionIntent);
		}
    	    }
    	} catch(Exception e) {
    	    Log.e(TAG, e.toString(), e);
    	} finally {
    	    if(c!=null)
    		c.close();
    	}
    }
}
