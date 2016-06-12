package com.mediatek.contacts.simservice;
 
import com.mediatek.contacts.simservice.SIMProcessorManager.ProcessorCompleteListener;
 
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
 
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;//for usim
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.RawContacts;
 
 
import com.android.contacts.common.model.account.AccountType;
import android.os.RemoteException;
 
import java.util.ArrayList;
import com.mediatek.contacts.simservice.SIMProcessorManager.ProcessorCompleteListener;
import com.mediatek.contacts.simservice.SIMServiceUtils;
import com.mediatek.contacts.simservice.SIMServiceUtils.ServiceWorkData;
import com.mediatek.contacts.simcontact.SimCardUtils;
import android.provider.ContactsContract.PhoneLookup;

import com.android.contacts.R;
 
public class PresetContactsImportProcessor extends SIMProcessorBase {
    private static final String TAG = "PresetContactsImportProcessor";
   
    private static boolean sIsRunningNumberCheck = false;
    private int INSERT_PRESET_NUMBER_COUNT = 0;           //
    private String  INSERT_PRESET_NAME[]    = null;  //
    private String  INSERT_PRESET_NUMBER[] = null;  //
   
    private long mSubId;
    private Context mContext;
 
    public PresetContactsImportProcessor(Context context, long subId, Intent intent,
            ProcessorCompleteListener listener) {
        super(intent, listener);
        mContext = context;
        mSubId = subId;
		
        INSERT_PRESET_NAME = mContext.getResources().getStringArray(R.array.preset_names);
        INSERT_PRESET_NUMBER = mContext.getResources().getStringArray(R.array.preset_numbers);
		if (INSERT_PRESET_NAME != null && INSERT_PRESET_NUMBER != null) {
		    INSERT_PRESET_NUMBER_COUNT = INSERT_PRESET_NAME.length;
		}
    }
 
    @Override
    public int getType() {
        return SIMServiceUtils.SERVICE_WORK_IMPORT_PRESET_CONTACTS;
    }
 
    @Override
    public void doWork() {
        if (isCancelled()) {
            Log.d(TAG, "[doWork]cancel import preset contacts work. Thread id=" + Thread.currentThread().getId());
            return;
        }
        importDefaultReadonlyContact();
    }
   
    private void importDefaultReadonlyContact(){
         Log.i(TAG, "isRunningNumberCheck before: " + sIsRunningNumberCheck);
         if (sIsRunningNumberCheck) {
            return;
         }
         sIsRunningNumberCheck = true;
         for(int i = 0;i < INSERT_PRESET_NUMBER_COUNT; i++)
         {
             Log.i(TAG, "isRunningNumberCheck after: " + sIsRunningNumberCheck);
             Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri
                      .encode(INSERT_PRESET_NUMBER[i]));
             Log.i(TAG, "getContactInfoByPhoneNumbers(), uri = " + uri);
 
             Cursor contactCursor = mContext.getContentResolver().query(uri, new String[] {
                      PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID
         }, null, null, null);
         try {
             if (contactCursor != null && contactCursor.getCount() > 0) {
                  return;
             } else {
                  final ArrayList<ContentProviderOperation> operationList = new ArrayList<ContentProviderOperation>();
                  ContentProviderOperation.Builder builder = ContentProviderOperation
                          .newInsert(RawContacts.CONTENT_URI);
                  ContentValues contactvalues = new ContentValues();
                  contactvalues.put(RawContacts.ACCOUNT_NAME,
                          AccountType.ACCOUNT_NAME_LOCAL_PHONE);
                  contactvalues.put(RawContacts.ACCOUNT_TYPE,
                          AccountType.ACCOUNT_TYPE_LOCAL_PHONE);
                  contactvalues.put(RawContacts.INDICATE_PHONE_SIM,
                          ContactsContract.RawContacts.INDICATE_PHONE);
                  contactvalues.put(RawContacts.IS_SDN_CONTACT, -2);
                  builder.withValues(contactvalues);
                  builder.withValue(RawContacts.AGGREGATION_MODE,
                          RawContacts.AGGREGATION_MODE_DISABLED);
                  operationList.add(builder.build());
 
                  builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
                  builder.withValueBackReference(Phone.RAW_CONTACT_ID, 0);
                  builder.withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
                  builder.withValue(Phone.TYPE, Phone.TYPE_MOBILE);
                  builder.withValue(Phone.NUMBER, INSERT_PRESET_NUMBER[i]);
                  builder.withValue(Data.IS_PRIMARY, 1);
                  operationList.add(builder.build());
 
                  builder = ContentProviderOperation.newInsert(Data.CONTENT_URI);
                  builder.withValueBackReference(StructuredName.RAW_CONTACT_ID, 0);
                  builder.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
                  builder.withValue(StructuredName.DISPLAY_NAME, INSERT_PRESET_NAME[i]);
                  operationList.add(builder.build());
 
                  try {
                      mContext.getContentResolver().applyBatch(
                               ContactsContract.AUTHORITY, operationList);
                  } catch (RemoteException e) {
                      Log.e(TAG, String.format("%s: %s", e.toString(), e.getMessage()));
                  } catch (OperationApplicationException e) {
                      Log.e(TAG, String.format("%s: %s", e.toString(), e.getMessage()));
                  }
 
             }
         } finally {
             // when this service start,but the contactsprovider has not been started yet.
             // the contactCursor perhaps null, but not always.(first load will weekup the provider)
             // so add null block to avoid nullpointerexception
             if (contactCursor != null) {
                  contactCursor.close();
             }
         }//for
         Log.i(TAG, "isRunningNumberCheck insert: " + sIsRunningNumberCheck);
         sIsRunningNumberCheck = false;
         }
    }
}
