package com.android.settings.emergencyassistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Contacts;
import android.provider.ContactsContract;  
import android.provider.ContactsContract.PhoneLookup;  
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.util.Log;
import android.widget.*;

import com.android.settings.R;

/**
 * Created by maoxunlei on 13-7-26.
 */
public class EmergencyAddContactActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "Log_Mao";

    private View mActionBarView;
    private ActionBar mActionBar;
    private TextView mNoContactItem;
    private Button mAddContactButton;
    private LinearLayout mSelectAllLayout;
    private CheckBox mSelectAllCb;
    private ListView mContactListView;
    private ContactListAdapter mContactListAdapter;

    private boolean deleteContactPressed = false;
    private boolean visibleFlag = false;
    private EmergencyAssistantOperateDB mOperateDB;
    private Context mContext;

    private List<CheckBox> mContactinfo_cb = new ArrayList<CheckBox>();
    private static List<ContactInfo> mContactInfoList = new ArrayList<ContactInfo>();
    private static List<Boolean> isSelectedList = new ArrayList<Boolean>();
    
    private static final int ADD_CONTACT = 100001;
    private static final int SELECT_ALL_CHECKBOX = 100002;
    private static final int REQUEST_CONTACT = 1;
    private static final int ID_CONTACT = 8;
    private static final int CONTACT_MAX_COUNT = 4;

    private static final int PHONE_ID_COLUMN = 0;
    private static final int PHONE_NUMBER_COLUMN = 1;
    private static final int PHONE_LABEL_COLUMN = 2;
    private static final int CONTACT_NAME_COLUMN = 3;
    private static final int PHONE_PHOTO_ID = 4;
    private static final int PHONE_CONTACT_ID = 5;

    public int contactCount = 0;
    public static final String KEY_REQUEST_CODE     = "request_code";
    public static final String KEY_TYPE             = "type";
    public static final int REQUEST_CODE_ISMS_PICK_CONTACT      = 210;
    public static final String CONTACT = "content://contact_selection/" + Integer.toString(ID_CONTACT);
    public static final String KEY_EXSITING_PARTICIPANTS = "existParticipants";
    public static final String KEY_IS_NEED_ORIGINAL_CONTACTS = "is_need_original_contacts";
    private static final String ACTION_START_CONTACT = "android.intent.action.contacts.list.PICKMULTIDATAS";
    private static final Uri CONTACT_URI = Data.CONTENT_URI;

    private static final String[] CALLER_ID_PROJECTION = new String[] {
        Phone._ID,                      // 0
        Phone.NUMBER,                   // 1
        Phone.LABEL,                    // 2
        Phone.DISPLAY_NAME,             // 3
        Phone.PHOTO_ID,                 // 4
        Phone.CONTACT_ID,               // 5
    };

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.emergency_add_contact);
        mContext = this;
        mOperateDB = new EmergencyAssistantOperateDB(mContext);

        mNoContactItem = (TextView) findViewById(R.id.no_contact_item);
        mContactListView = (ListView) findViewById(R.id.contact_list);
        mAddContactButton = (Button) findViewById(R.id.add_contact_button);
        mAddContactButton.setOnClickListener(this);
        mAddContactButton.setTag(ADD_CONTACT);

        mSelectAllLayout = (LinearLayout) findViewById(R.id.select_all);
        mSelectAllLayout.setVisibility(View.GONE);

        mSelectAllCb = (CheckBox) findViewById(R.id.select_all_cb);
        mSelectAllCb.setOnClickListener(this);
        mSelectAllCb.setTag(SELECT_ALL_CHECKBOX);

        if (mContactListView != null) {
            mContactListAdapter = new ContactListAdapter(this);
            mContactListView.setAdapter(mContactListAdapter);
            mContactListView.setOnItemClickListener(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume() {
        super.onResume();

        mContactInfoList = mOperateDB.getContactInfoList();
        if (mContactInfoList != null ) {
            int contactCount = mContactInfoList.size();
            if (contactCount == 0) {
                mNoContactItem.setVisibility(View.VISIBLE);
            } else {
                mNoContactItem.setVisibility(View.GONE);
            }
            isSelectedList.clear();
            for (int i = 0; i < contactCount; i++) {
                isSelectedList.add(false);
            } 
        } else {
            mContactInfoList = new ArrayList<ContactInfo>();
        }

        if (mContactListAdapter != null) {
            mContactListAdapter.notifyDataSetChanged();
        }

        mActionBar = getActionBar();
        if (mActionBar != null) {
            if (mActionBarView == null) {
                contactCount = mContactInfoList.size();
                mActionBarView = new EmergencyAddContactTitleLayout(this, contactCount, onContactListener, deleteContactPressed);
            } else {
                ((EmergencyAddContactTitleLayout) mActionBarView).updateTitleLayout(deleteContactPressed, mContactInfoList.size());
            }
            updateTitle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContactListView != null) {
            mContactListView.setAdapter(null);
            mContactListView.setOnItemClickListener(null);
            mContactListView = null;
            mContactListAdapter = null;
        }
        deleteContactPressed = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        int tag = (Integer) view.getTag();
        switch (tag) {
            case ADD_CONTACT:
                Intent intent = new Intent("android.intent.action.contacts.list.PICKMULTIPHONES");
                intent.setType(Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CONTACT);
                break;
            case SELECT_ALL_CHECKBOX:
                final int count = mContactInfoList.size();
                if (mSelectAllCb.isChecked()) {
                    setListCheckStatus(count, true);
                } else {
                    setListCheckStatus(count, false);
                }
                mContactListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (visibleFlag) {
            ViewHolder ViewHolder = (ViewHolder) view.getTag();
            ViewHolder.cb.toggle();
            if (ViewHolder.cb.isChecked()) {
                isSelectedList.set(position, true);
            } else {
                isSelectedList.set(position, false);
            }
			
            if (getSelectedItemCount() == isSelectedList.size()) {
                mSelectAllCb.setChecked(true);
			} else {
                mSelectAllCb.setChecked(false);
			}
        }
    }

    private int getSelectedItemCount() {
        int count = 0;
        for (int location = isSelectedList.size() - 1; location >= 0; location--) {
            if (isSelectedList.get(location)) {
                count++;
            }
        }
        return count;
    }
	
    // count : the count of list selected
    // flag : set true or false
    private void setListCheckStatus(int count, boolean flag) {
        for (int i = 0; i < isSelectedList.size(); i++) {
            isSelectedList.set(i, flag);
        }
        if (mContactListView == null) {
            mContactListView = (ListView) findViewById(R.id.contact_list);
            mContactListAdapter = new ContactListAdapter(this);
            mContactListView.setAdapter(mContactListAdapter);
            mContactListView.setOnItemClickListener(this);
        }
        int visibleCount = mContactListView.getLastVisiblePosition() - mContactListView.getFirstVisiblePosition()+1;
        if (count > visibleCount) {
            count = visibleCount;
        }
        for (int i = 0; i < count; i++) {
            final LinearLayout layout = (LinearLayout) mContactListView.getChildAt(i);
            final int c = layout.getChildCount();
            for (int j = 0; j < c; j++) {
                final View subView = layout.getChildAt(j);
                if (subView instanceof CheckBox) {
                    ((CheckBox) subView).setChecked(flag);
                    break;
                }
            }
        }
    }

    private void updateTitle() {
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        mActionBar.setCustomView(mActionBarView);
    }
  /** 
    * return if repeat exists in db
    *    : some of current selections have been stored in the emergencyassistant.db.
    */
    private boolean saveContactInfoList(long contactId, Bitmap contactPhoto, String contactName, String contactNumber, int index) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.contactId = contactId;
        contactInfo.contactNumber = contactNumber;
        contactInfo.contactName = contactName;
        contactInfo.contactPhoto = contactPhoto;
        boolean isRepeat = false;

        for(int i = 0; i < mContactInfoList.size(); i++) {
            if (contactInfo.contactId == mContactInfoList.get(i).contactId) {
                isRepeat = true;
            }
        }
        if (!isRepeat) {
            mContactInfoList.add(contactInfo);
            isSelectedList.add(false);
            if (mOperateDB == null) {
                mOperateDB = new EmergencyAssistantOperateDB(mContext);
            }
            try {
                mOperateDB.addContactInfo(contactInfo, contactId);
            } catch (Exception e){
                Log.e(TAG, "addContactInfo error!");
            }
        }
        return isRepeat;
    }

    private void showAddContactDialog(int currentCount) {
        int moreCount = CONTACT_MAX_COUNT - currentCount;
        new AlertDialog.Builder(this)
        .setMessage(getString(R.string.add_emergency_contact_prompt
            , currentCount, moreCount))
        .setTitle(getString(R.string.add_emergency_contact_prompt_title))
        .setPositiveButton(getString(R.string.dlg_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .create()
        .show();
    }

    /**
     * return if items repeat, 
     */
    private boolean updateContactsNumbers(int id, int index) {
        ContentResolver resolver = EmergencyAddContactActivity.this.getContentResolver();
        Uri existNumberURI = ContentUris.withAppendedId(CONTACT_URI, id);
        Cursor cursor = getContentResolver().query(
            existNumberURI, CALLER_ID_PROJECTION, null, null, null);
        boolean ifHasRepeatItems = false;

        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                String contactNumber = allWhite(cursor.getString(PHONE_NUMBER_COLUMN));
                String contactName = cursor.getString(CONTACT_NAME_COLUMN);
                long contactId = cursor.getLong(PHONE_CONTACT_ID);
                long photoid = cursor.getLong(PHONE_PHOTO_ID);
                Bitmap contactPhoto = null;

                if(photoid > 0 ) {
                    Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactId);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                }else {
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.contact_photo);
                }
                Log.d(TAG, "updateContactsNumbers contactId=" + contactId);
                ifHasRepeatItems = saveContactInfoList(contactId, contactPhoto, contactName, contactNumber, index);            
                cursor.moveToNext();
            }
            cursor.close();
        }
        return ifHasRepeatItems;
    }

    private String allWhite(String str) {
        if (str != null) {
            str = str.replaceAll(" ", "");
        }
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG, "onActivityResult() the data is " + data);
        boolean ifAddCountForRepeat = false;
        switch (requestCode) {
            case REQUEST_CONTACT:
                if (data == null) {
                    return;
                }
                long[] contactIds = data.getLongArrayExtra("com.mediatek.contacts.list.pickdataresult");
                Log.d(TAG, "contactIds.length=" + contactIds.length);
                if (contactIds != null) {
                    int currentCount = mContactInfoList.size();
                    int aimCount = contactIds.length;
                    int willBeAddedCount = aimCount;
                    if (currentCount + aimCount > CONTACT_MAX_COUNT) {
                        willBeAddedCount = CONTACT_MAX_COUNT - currentCount;
                    } 
          
                    for (int i = 0; i < willBeAddedCount ; i++) {       
                        ifAddCountForRepeat = updateContactsNumbers((int)contactIds[i], i);
                        if (ifAddCountForRepeat) {
                            ++willBeAddedCount;
                            --aimCount;
                            if (willBeAddedCount > contactIds.length) {
                                break;
                            }
                        }                    
                    }
                    
                    if (currentCount + aimCount > CONTACT_MAX_COUNT) {
                        showAddContactDialog(currentCount);
                    }

                    if (mContactListAdapter != null) {
                        mContactListAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    private View.OnClickListener onContactListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectAllCb.isChecked()) {
                mSelectAllCb.setChecked(false);
            }
            switch(v.getId()) {       
                // image button delete.
                case R.id.delete_contact:
                    deleteContactPressed = true;
                    ((EmergencyAddContactTitleLayout)mActionBarView).updateTitleLayout(deleteContactPressed, mContactInfoList.size());
                    if(mActionBar!= null) {
                        updateTitle();
                    }
                    if (!visibleFlag) {
                        visibleFlag = true;
                    }
                    mSelectAllLayout.setVisibility(View.VISIBLE);
                    mAddContactButton.setVisibility(View.GONE);
                    mContactListAdapter.notifyDataSetChanged();      
                    // Intent intent = new Intent(SendMessageReceiver.SEND_MESSAGE_START);
                    // mContext.sendBroadcast(intent);
                    // mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    break;
                // textview button delete.
                case R.id.delete_contact_button:
                    deleteContactPressed = false;
					Log.i("jingyi", "delete_contact_button");
                    if(mActionBar!= null) {
                        updateTitle();
                    }
                    if (mContactInfoList.size() > 0) {
                        if (visibleFlag) {
                            for (int location = isSelectedList.size() - 1; location >= 0; location--) {
                                Log.d(TAG, "current isSelectedList[" + location +"]=" + isSelectedList.get(location));
                                Log.d(TAG, " mContactInfoList["+location+"]="+mContactInfoList.get(location));
                                if (isSelectedList.get(location)) {
                                    isSelectedList.remove(location);
                                    long index = mContactInfoList.get(location).contactId;
                                    mContactInfoList.remove(location);
                                    mOperateDB.deleteContactInfo(index);
                                    continue;
                                }
                            }
                            visibleFlag = false;
                        }
                    }

                    for (int i = 0; i < isSelectedList.size(); i++) {
                        isSelectedList.set(i, false);
                    }
                    ((EmergencyAddContactTitleLayout)mActionBarView).updateTitleLayout(deleteContactPressed, mContactInfoList.size());
                    mContactListAdapter.notifyDataSetChanged();
                    mSelectAllLayout.setVisibility(View.GONE);
                    mAddContactButton.setVisibility(View.VISIBLE);
                    break;
                // cancel detele contact
                case R.id.cancel_delete_contact:
				Log.i("jingyi", "cancel_delete_contact");
                    deleteContactPressed = false;
                    ((EmergencyAddContactTitleLayout)mActionBarView).updateTitleLayout(deleteContactPressed, mContactInfoList.size());
                    if(mActionBar!= null) {
                        updateTitle();
                    }

                    if (visibleFlag) {
                        visibleFlag = false;
                    }
                    for (int i = 0; i < isSelectedList.size(); i++) {
                        isSelectedList.set(i, false);
                    }
                    
                    mContactListAdapter.notifyDataSetChanged();
                    mSelectAllLayout.setVisibility(View.GONE);
                    mAddContactButton.setVisibility(View.VISIBLE);
                    break;
            }

            if (mContactInfoList != null && mContactInfoList.size() == 0) {
                mNoContactItem.setVisibility(View.VISIBLE);
            } else {
                mNoContactItem.setVisibility(View.GONE);
            }
        }

    };

    private class ContactListAdapter extends BaseAdapter{

        private final LayoutInflater mInflater;

        public ContactListAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (mContactInfoList == null) {
                    mContactInfoList = new ArrayList<ContactInfo>();
            }
        }

        @Override
        public int getCount() {
            //Log.d(TAG, "getCount=" + mContactInfoList.size()); 
            return mContactInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return mContactInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Log.d(TAG, "getView=" + position + ", convertView=" + convertView + ", parent=" + parent); 
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.emergency_contactinfo_list_item, null);
                holder.contactName = (TextView) convertView.findViewById(R.id.contact_name);
                holder.contactNumber = (TextView) convertView.findViewById(R.id.contact_number);
                holder.contactPhoto = (ImageView) convertView.findViewById(R.id.contact_photo);
                holder.cb = (CheckBox) convertView.findViewById(R.id.contactinfo_cb);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ContactInfo contactInfo = null;
            if (mContactInfoList != null & position >= 0) {
                contactInfo = mContactInfoList.get(position);
                if (contactInfo != null) {
                    if (holder.contactName != null)
                        holder.contactName.setText(contactInfo.contactName);
                    if (holder.contactNumber != null)
                        holder.contactNumber.setText(contactInfo.contactNumber);
                    if (holder.contactPhoto != null) {
                        holder.contactPhoto.setImageBitmap(contactInfo.contactPhoto);
                    }
                }
            }

            if (visibleFlag) {
                holder.cb.setVisibility(View.VISIBLE);
            } else {
                holder.cb.setVisibility(View.GONE);
                holder.cb.setChecked(false);
            }
            
            return convertView;
        }
       
    }

    static class ViewHolder {
            TextView contactName;
            TextView contactNumber;
            ImageView contactPhoto;
            CheckBox cb;
    }
}
