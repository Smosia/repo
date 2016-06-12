/**************************************************************************
add this file for new feature:JLLB-1490 zhangzixiao 20140710
 **************************************************************************/
package com.android.mms.transaction;

import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.TelephonyIntents;
import com.android.mms.data.Conversation;

//import com.qrd.plugin.feature_query.FeatureQuery;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.AsyncQueryHandler;
import android.net.Uri;
import android.util.Log;
import android.content.ContentResolver;
import android.content.IntentFilter;
import com.android.internal.telephony.IccCardConstants;
import com.android.internal.telephony.PhoneConstants;

//A: RGK-MMS-Feature: SyncSimMessages (START) @{
import android.os.SystemProperties;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import com.android.mms.MmsApp;
//A: RGK-MMS-Feature: SyncSimMessages (END) }@

public class SimStatusReceiver extends BroadcastReceiver{
    private static final String TAG = "SimStatusReceiver";
    private static final String INTENT_KEY_SUBSCRIPTION = "subscription";
	
    private static final int INSERT_MESSAGE_TOKEN  = 1200;
    private static final int DELETE_MESSAGE_TOKEN   = 1201;
    private static final int DELETE_INSERT_SIM1_TOKEN   = 1202;
    private static final int DELETE_INSERT_SIM2_TOKEN   = 1203;
    private static final int DELETE_MESSAGE_SIM1_TOKEN   = 1204;
    private static final int DELETE_MESSAGE_SIM2_TOKEN   = 1205;

    //public static final boolean SYNC = true;
    
    //A: RGK-MMS-Feature: SyncSimMessages (START) @{
    private static boolean mSupportSyncSimSms =  "1".equals(SystemProperties.get("ro.rgk_support_sync_sim_sms"));
    //A: RGK-MMS-Feature: SyncSimMessages (END) }@
	
    private BackgroundQueryHandler mBackgroundQueryHandler = null;
    private boolean mIsDelettingSIMSMS = false;
    private boolean mDeleteThenInsert = false;
    private boolean mIsInsertting = false;
    private Context mContext;

    private static String INTENT_SIM_DISABLED = "com.android.sim.INTENT_SIM_DISABLED";
    @Override
    public void onReceive(Context context, Intent intent) {
	if(!supportSyncSimMessage()) {
	    return;
	}
        String action = intent.getAction();
        mContext = context;

        if(mBackgroundQueryHandler == null) {
            mBackgroundQueryHandler = new BackgroundQueryHandler(context.getContentResolver());
        }

        Log.e(TAG,"SimStatusReceiver->onReceive->action->"+action);
	 Log.e(TAG,"SimStatusReceiver->onReceive->intent->"+intent);
        if (action.equals(TelephonyIntents.ACTION_SIM_STATE_CHANGED)) {
	    String stateExtra = intent.getStringExtra(IccCardConstants.INTENT_KEY_ICC_STATE);
	    int mSubscription = intent.getIntExtra(PhoneConstants.SLOT_KEY, -1);
        //add chenshu
        //get sub id from slot id ,because sub id is auto increment by insert different sim card, and in android 6.0 sms  provider
        //don't have permission to get sub id. face security exception.
        int subId = intent.getIntExtra(PhoneConstants.SUBSCRIPTION_KEY, -1);
        //add chenshu
	    Log.e(TAG,"SimStatusReceiver->onReceive->mSubscription->"+mSubscription+"->stateExtra->"+stateExtra+"--subId-"+subId);
	    if(IccCardConstants.INTENT_VALUE_ICC_LOADED.equals(stateExtra)) {
		    if(mSubscription == 0) {
		        //RemoveAllIccSms(DELETE_INSERT_SIM1_TOKEN);
                RemoveAllIccSms(DELETE_INSERT_SIM1_TOKEN, subId);
		    } else if(mSubscription == 1) {
		        //RemoveAllIccSms(DELETE_INSERT_SIM2_TOKEN);
                RemoveAllIccSms(DELETE_INSERT_SIM2_TOKEN, subId);
                
		    }
	    } else if(IccCardConstants.INTENT_VALUE_ICC_ABSENT.equals(stateExtra)) {
		    if(mSubscription == 0){
		        //RemoveAllIccSms(DELETE_MESSAGE_SIM1_TOKEN);
                RemoveAllIccSms(DELETE_MESSAGE_SIM1_TOKEN, subId);
		    } else if(mSubscription == 1) {
		        //RemoveAllIccSms(DELETE_MESSAGE_SIM2_TOKEN);
                RemoveAllIccSms(DELETE_MESSAGE_SIM2_TOKEN, subId);
		    }
	    }
	} else if(action.equals(Intent.ACTION_BOOT_COMPLETED)) {
	    RemoveAllIccSms(DELETE_MESSAGE_TOKEN, 0);
	} else if(action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
	    Boolean stateExtra = intent.getBooleanExtra("state", false);
	    if(stateExtra) {
		RemoveAllIccSms(DELETE_MESSAGE_TOKEN, 0);
	    }
	} else if(action.equals(INTENT_SIM_DISABLED)) {
        int mSubscription = intent.getIntExtra(PhoneConstants.SLOT_KEY, -1);
	    int mSubId = intent.getIntExtra("Subscription", -1);
        Log.e(TAG,"SimStatusReceiver->onReceive->mSubscription->"+mSubscription);
     	    switch (mSubscription) {
	        case 0:
		        //RemoveAllIccSms(DELETE_MESSAGE_SIM1_TOKEN);
                RemoveAllIccSms(DELETE_MESSAGE_SIM1_TOKEN, mSubId);
		        break;
	        case 1:
		        //RemoveAllIccSms(DELETE_MESSAGE_SIM2_TOKEN);
                RemoveAllIccSms(DELETE_MESSAGE_SIM2_TOKEN, mSubId);
		        break;
	        default :
		        break;
	        }
    	}
    }
	
    public class BackgroundQueryHandler extends AsyncQueryHandler{
	
	public BackgroundQueryHandler(ContentResolver contentResolver) {
	    super(contentResolver);
	}
		
	protected void onInsertComplete(int token, Object cookie, Uri uri) {
	    switch(token) {
	    case INSERT_MESSAGE_TOKEN:
	    	break;
	    }
	}
		
        protected void onDeleteComplete(int token, Object cookie, int result) {
	        Uri url = null;
            //add chenshu
            int subId = (int)cookie;
            //add chenshu
     	    ContentValues value = new ContentValues();
            switch(token) {
                case DELETE_INSERT_SIM1_TOKEN:
                    //url = Uri.parse("content://sms/copysim1smstodb");
                    url = Uri.parse("content://sms/copysim1smstodb").buildUpon().appendQueryParameter(PhoneConstants.SUBSCRIPTION_KEY, String.valueOf(subId)).build();;
                    mBackgroundQueryHandler.startInsert(INSERT_MESSAGE_TOKEN, null, url, value);
                    break;
         	    case DELETE_INSERT_SIM2_TOKEN:
		            //url = Uri.parse("content://sms/copysim2smstodb");
                    url = Uri.parse("content://sms/copysim1smstodb").buildUpon().appendQueryParameter(PhoneConstants.SUBSCRIPTION_KEY, String.valueOf(subId)).build();
		            mBackgroundQueryHandler.startInsert(INSERT_MESSAGE_TOKEN, null, url, value);	
		        break;
	        case DELETE_MESSAGE_TOKEN:
		        mIsDelettingSIMSMS = false;
		        break;
            }
    	}		
    }
	
    //modify chenshu add sub id from mms start
    public void RemoveAllIccSms(int token, int subId) {
    //modify chenshu add sub id from mms end
	    Uri url = null;
        if(mIsDelettingSIMSMS == true) return;
        switch(token) {
            case DELETE_MESSAGE_TOKEN:
                mIsDelettingSIMSMS = true;
                url = Uri.parse("content://sms/removeallsimsmsindb");
                break;
            case DELETE_MESSAGE_SIM1_TOKEN:
            case DELETE_INSERT_SIM1_TOKEN:
                //url = Uri.parse("content://sms/removesim1smsindb");
                url = Uri.parse("content://sms/removesim1smsindb").buildUpon().appendQueryParameter(PhoneConstants.SUBSCRIPTION_KEY, String.valueOf(subId)).build();
                break;
            case DELETE_MESSAGE_SIM2_TOKEN:
            case DELETE_INSERT_SIM2_TOKEN:
                //url = Uri.parse("content://sms/removesim2smsindb");
                url = Uri.parse("content://sms/removesim2smsindb").buildUpon().appendQueryParameter(PhoneConstants.SUBSCRIPTION_KEY, String.valueOf(subId)).build();
                break;
        default:
            url = null;
            break;	
        }
	    if(url != null) {
	        //mBackgroundQueryHandler.startDelete(token, null, url, null, null);
            mBackgroundQueryHandler.startDelete(token, subId, url, null, null);
	    }		
        return;
    }

    public static boolean supportSyncSimMessage() {
        return mSupportSyncSimSms; //com.mediatek.common.featureoption.FeatureOption.RGK_AP_SUPPORT_SYNC_SIM_MSG;
    }

}
