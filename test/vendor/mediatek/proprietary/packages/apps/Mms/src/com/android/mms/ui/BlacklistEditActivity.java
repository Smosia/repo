/*
 * add by zhangzixiao JBLW-482 20131216 NewFeature:message blacklist
 */
package com.android.mms.ui;

import static android.provider.BaseColumns._ID;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SqliteWrapper;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.Sms;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.view.inputmethod.InputMethodManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.mms.R;
import com.android.mms.MmsConfig;
import com.android.mms.data.Contact;
//import com.mediatek.encapsulation.android.telephony.EncapsulatedTelephony;

import java.util.ArrayList;
import java.util.List;

/** M:
 * SmsTemplateEditActivity
 */
public class BlacklistEditActivity extends Activity {

    private static String TAG = "MMS/BlacklistEditor";
    private static String NUMBER = "number";
    private static int BLACK_NUMBER_HAS_ALREADY = -1;
    private static int BLACK_NUMBER_NULL = -2;
    private static int BLACK_NUMBER_ILLEAGL = -3;

    /*private Integer mQuickTextId;
    private Integer mMaxQuickTextId;
    private String mQuickText;*/

    @SuppressWarnings("unused")
    private AlertDialog mQuicktextAlertDialog;
    private ListView mListView;
    private Button addButton;
    private EditText mNewBlackText;
    private EditText mOldBlackText;
    private BlacklistAdapter mAdapter;

    //private ArrayAdapter<String> adapter;
    /*private List<Integer> allQuickTextIds = new ArrayList<Integer>();
    private List<String> allQuickTexts = new ArrayList<String>();*/
    private List<BlackRecord> allBlackRecords = new ArrayList<BlackRecord>();
    private BlackRecord mCurBlackRecord = null;
    private final int MAX_EDITABLE_LENGTH = 20;
    private Activity mActivity;

    public boolean canRefresh = false;
    public boolean needRefresh = false;

    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
	@Override
	public void onChange(boolean selfChange) {
	    if(canRefresh) {
		updateAllBlackRecords();
	    } else {
		needRefresh = true;
	    }
	}
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blacklist_edit_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

	mActivity = this;
        addButton = (Button) findViewById(R.id.blackText_add_button);
        addButton.setTextSize(13);
        addButton.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                case R.id.blackText_add_button:
                    addBlackText();
                    break;
                default:
                    break;
                }
            }
        });

        mListView = (ListView) findViewById(R.id.black_text_list);
        mListView.setOnItemClickListener(new OnItemClickListener () {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
		/*
                InputMethodManager inputM =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getWindow() != null && getWindow().getCurrentFocus() != null) {
                    inputM.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
                mCurBlackRecord = allBlackRecords.get(position);
                showEditDialog();
		*/
		BlackRecord record = allBlackRecords.get(position);
		Intent intent = new Intent(mActivity, BlacklistMessageActivity.class);
		intent.putExtra("blacktext_id", record.id);
		intent.putExtra("number", record.number);
		startActivity(intent);
            }    
        });
	mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	    public boolean onItemLongClick(AdapterView<?> adapter, View v,
			int position, long id) {
		InputMethodManager inputM =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(getWindow() != null && getWindow().getCurrentFocus() != null) {
                    inputM.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
                mCurBlackRecord = allBlackRecords.get(position);
                showEditDialog();
		return true;
	    }
	});
        
        mNewBlackText = (EditText) findViewById(R.id.new_black_text);
        mNewBlackText.setHint(R.string.type_to_number);

        updateAllBlackRecords();
	getContentResolver().registerContentObserver(
        		BlackMessageUtils.CONTENT_URI_BLACK_MESSAGES, true, mContentObserver);
    }

    @Override
    protected void onResume() {
	super.onResume();
	if(needRefresh) {
	    updateAllBlackRecords();
	    needRefresh = false;
	}
	canRefresh = true;
	if(mAdapter != null) {
	    mAdapter.notifyDataSetChanged();
	}
    }

    @Override
    protected void onPause() {
	super.onPause();
	canRefresh = false;
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	getContentResolver().unregisterContentObserver(mContentObserver);
    }

    private void showEditDialog() {
        mQuicktextAlertDialog = new AlertDialog.Builder(this)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle(R.string.number_editor)
        .setMessage(mCurBlackRecord.number)
        .setPositiveButton(R.string.edit, new EditButtonListener())
        .setNeutralButton(R.string.delete, new DeleteButtonListener())
        .setNegativeButton(android.R.string.cancel, new CancelButtonListener())
        .show();
    }

    // 4ButtonListener
    private class EditButtonListener implements OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            showEditDialog(mCurBlackRecord.number);
        }
    }

    private class UpdateButtonListener implements OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            String newBlacktext = mOldBlackText.getText().toString();
            int i = updateNumber(mCurBlackRecord.id, newBlacktext);
            dialog.dismiss();
            if (i > 0) {
                mCurBlackRecord = null;
                updateAllBlackRecords();
                makeAToast(R.string.modify_successful);
            } else if (i == BLACK_NUMBER_HAS_ALREADY) {
                makeAToast(R.string.already_have_number);
            } else if (i == BLACK_NUMBER_NULL) {
                makeAToast(R.string.number_cannot_empty);
                showEditDialog(mCurBlackRecord.number);
            } else if (i == BLACK_NUMBER_ILLEAGL) {
		makeAToast(R.string.illeage_number);
	    } else {
                makeAToast(R.string.modify_unsuccessful);
            }
        }
    }

    private class DeleteButtonListener implements OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            int i = delNumber(mCurBlackRecord.id);
            updateAllBlackRecords();
            dialog.dismiss();
            if (i > 0) {
                mCurBlackRecord = null;
                makeAToast(R.string.delete_successful);
            } else {
                makeAToast(R.string.delete_unsuccessful);
            }
        }
    }

    private class CancelButtonListener implements OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    private void addBlackText() {
        String currentText = mNewBlackText.getText().toString().trim();
        if (currentText.equals("")) {
            makeAToast(R.string.number_cannot_empty);
            return;
        }
	if(!isValidAddress(currentText)) {
	    makeAToast(R.string.illeage_number);
            return;
	}
        if (currentText.length() != 0) {
            if (addNumber(currentText)) {
                mNewBlackText.setText("");
                updateAllBlackRecords();
                makeAToast(getString(R.string.add_successful) + " : \n" + currentText);
            } else {
                makeAToast(R.string.already_have_number);
            }
        } else {
            makeAToast(R.string.number_cannot_empty);
        }
        return;
    }

    private boolean addNumber(String str) {
        if (hasNumberAlready(str)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(NUMBER, str);
        getContentResolver().insert(BlackMessageUtils.CONTENT_URI_BLACKTEXT, values);
        return true;
    }

    private int delNumber(Integer id) {
        return getContentResolver().delete(BlackMessageUtils.CONTENT_URI_BLACKTEXT, _ID + "=" + id, null);
    }
    
    private int updateNumber(Integer id, String number) {
        if (number.trim().equals("")) {
            return BLACK_NUMBER_NULL;
        } else if (hasNumberAlready(number)) {
            return BLACK_NUMBER_HAS_ALREADY;
        } else if (!isValidAddress(number)) {
	    return BLACK_NUMBER_ILLEAGL;
	}
        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(NUMBER, number);
        return getContentResolver().update(BlackMessageUtils.CONTENT_URI_BLACKTEXT, values, _ID + "=" + id, null);
    }

    private Cursor getBacklist() {
	String[] projection = new String[] {"_id", "number", "message_count"};
        Cursor cursor = getContentResolver().query(BlackMessageUtils.CONTENT_URI_BLACKTEXT, projection, null, null, null);
        return cursor;
    }
    
    
    private boolean hasNumberAlready(String str) {
        for (BlackRecord br : allBlackRecords) {
            if (PhoneNumberUtils.compare(this, br.number, str)) {
                return true;
            }
        }
        return false;
    }

    private void updateAllBlackRecords() {
        Cursor cursor = getBacklist();
        allBlackRecords.clear();
        //List<String> allBlackTexts = new ArrayList<String>();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int _id = cursor.getInt(0);
                    String blackNumber = cursor.getString(1);
                    //allBlackTexts.add(blackNumber);
		    int messageCount = cursor.getInt(2);
                    BlackRecord blackRecord = new BlackRecord(_id, blackNumber, messageCount);
                    allBlackRecords.add(blackRecord);
                }
            } finally {
                cursor.close();
            }
        }
        //adapter = new ArrayAdapter<String>(this, R.layout.quick_text_edit_item, allBlackTexts);
	mAdapter = new BlacklistAdapter(allBlackRecords);
        mListView.setAdapter(mAdapter);
	if(allBlackRecords.size() == 0) {
	    TextView empty = (TextView) findViewById(R.id.empty);
	    mListView.setVisibility(View.GONE);
	} else {
	    mListView.setVisibility(View.VISIBLE);
	}
    }

    private void makeAToast(Integer strId){
        if (strId != null) {
            Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
        }
    }

    private void makeAToast(String message){
        if (message != null && !message.equals("")) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    private void showEditDialog(String quickText) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(BlacklistEditActivity.this);
        mOldBlackText = new EditText(dialog.getContext());
        mOldBlackText.setHint(R.string.type_to_number);
        mOldBlackText.computeScroll();
        mOldBlackText.setText(quickText);
        mOldBlackText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_EDITABLE_LENGTH)});
        mOldBlackText.setMaxLines(4);
        mQuicktextAlertDialog = dialog
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle(R.string.number_editor)
        .setView(mOldBlackText)
        .setPositiveButton(android.R.string.ok, new UpdateButtonListener())
        .setNegativeButton(android.R.string.cancel, new CancelButtonListener())
        .show();
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }

    private boolean isValidAddress(String number) {
	return MessageUtils.isValidMmsAddress(number)
		|| MessageUtils.isWellFormedSmsAddress(number.replaceAll(" |-", ""))
		|| Mms.isEmailAddress(number);
    }
    
    class BlackRecord {
    	BlackRecord(Integer id, String number, Integer messageCount) {
    	    this.id = id;
    	    this.number = number;
	    this.messageCount = messageCount;
    	}
    	Integer id;
    	String number;
	Integer messageCount;
    }

    class BlacklistAdapter extends BaseAdapter {
    	private List<BlackRecord> mBlackRecords;
    	public BlacklistAdapter(List<BlackRecord> mBlackRecords) {
    		this.mBlackRecords = mBlackRecords;
    	}
	@Override
	public int getCount() {
	    return mBlackRecords.size();
	}
	@Override
	public Object getItem(int position) {
	    return mBlackRecords.get(position);
	}
	@Override
	public long getItemId(int position) {
	    return mBlackRecords.get(position).id;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    RelativeLayout layout = null;
	    if(convertView != null && convertView instanceof RelativeLayout) {
		layout = (RelativeLayout) convertView;
	    } else {
		layout = (RelativeLayout) BlacklistEditActivity.this.getLayoutInflater()
			.inflate(R.layout.black_list_item, null);
	    } 
	    final BlackRecord record = mBlackRecords.get(position);
	    String addr = record.number;
	    QuickContactBadge mAvatar = (QuickContactBadge) layout.findViewById(R.id.avatar);
	    TextView numberText = (TextView) layout.findViewById(R.id.number);
	    TextView subNumberText = (TextView) layout.findViewById(R.id.sub_number);
	    TextView countText = (TextView) layout.findViewById(R.id.count);
	    /*
	    countText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	    countText.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		    Intent intent = new Intent(mActivity, BlacklistMessageActivity.class);
		    intent.putExtra("blacktext_id", record.id);
		    intent.putExtra("number", record.number);
		    startActivity(intent);
		}
	    });
	    */
	    /* Avatar */
	    Drawable sDefaultContactImage = getResources().getDrawable(R.drawable.ic_contact_picture);
	    Contact contact = Contact.get(addr, true);
	    Drawable avatarDrawable = contact.getAvatar(mActivity, sDefaultContactImage, -1);
	    String number = contact.getNumber();
            if (Mms.isEmailAddress(number)) {
                mAvatar.assignContactFromEmail(number, true);
            } else {
                mAvatar.assignContactFromPhone(number, true);
            }
            mAvatar.setImageDrawable(avatarDrawable);
            /* Number&Count */
	    String name = contact.getName();
	    if(number.equals(name)) {
		numberText.setText(number);
		subNumberText.setVisibility(View.GONE);
	    } else {
		numberText.setText(name);
		subNumberText.setText(number);
		subNumberText.setVisibility(View.VISIBLE);
	    }
            countText.setText(""+record.messageCount);
	    return layout;
	}
    };

}
