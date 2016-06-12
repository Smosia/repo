/*
 * add by zhangzixiao JBLW-482 20131216 NewFeature:message blacklist
 */
package com.android.mms.ui;

import static android.provider.BaseColumns._ID;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SqliteWrapper;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.Sms;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.util.Log;
import android.view.ActionMode;
import android.view.inputmethod.InputMethodManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
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
public class BlacklistMessageActivity extends Activity {
    private static final int STATE_SHOW_LIST = 1;
    private static final int STATE_SHOW_EMPTY = 2;
    private ListView mListView;
    private TextView emptyText;
    private BlackMessageListAdapter mMsgListAdapter = null;
    private ContentResolver mContentResolver;
    private ActionMode mActionMode;
    private ActionModeCallBack mActionModeCallBack = new ActionModeCallBack();
    private LayoutInflater mInflater;
    private Cursor mCursor = null;
    private AsyncQueryHandler mQueryHandler = null;
    public boolean isQuerying = false;
    public boolean needRefresh = false;
    protected static int mObserverCount = 0; 
    private int blacktextId;
	
    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
	@Override
	public void onChange(boolean selfChange) {
	    if(isQuerying || !needRefresh) {
		mObserverCount++;
	    } else {
		startQuery();
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
        setContentView(R.layout.black_message_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
	updateTitle();
	blacktextId = getIntent().getIntExtra("blacktext_id", -1);

        initListView();
        emptyText = (TextView) findViewById(R.id.empty_message);

	mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        mContentResolver = getContentResolver();
        mQueryHandler = new QueryHandler(mContentResolver, this);
        
        startQuery();
        
        mContentResolver.registerContentObserver(
        		BlackMessageUtils.CONTENT_URI_BLACK_MESSAGES, true, mContentObserver);
    }

    private void updateTitle() {
	String number = getIntent().getStringExtra("number");
	ActionBar actionBar = getActionBar();
	Contact contact = Contact.get(number, false);
	Drawable sDefaultContactImage = getResources().getDrawable(R.drawable.ic_contact_picture);
	Drawable avatarDrawable = contact.getAvatar(this, sDefaultContactImage, -1);
	actionBar.setIcon(avatarDrawable);
	actionBar.setCustomView(R.layout.actionbar_message_title);
	View customView = actionBar.getCustomView();
	TextView mTopTitle = (TextView) customView.findViewById(R.id.tv_top_title);
	TextView mTopSubtitle = (TextView) customView.findViewById(R.id.tv_top_subtitle);
	customView.findViewById(R.id.iv_silent).setVisibility(View.GONE);
	String name = contact.getName();
	mTopTitle.setText(name);
	if(name.equals(number)) {
	    mTopSubtitle.setVisibility(View.GONE);
	} else {
	    mTopSubtitle.setText(number);
	    mTopSubtitle.setVisibility(View.VISIBLE);
	}	
	actionBar.setDisplayShowCustomEnabled(true);
	actionBar.setDisplayShowTitleEnabled(false);
    }

    private void initListView() {
	mListView = (ListView) findViewById(R.id.messages);
	mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	    public boolean onItemLongClick(AdapterView<?> adapter, View v,
			int position, long id) {
		if(mMsgListAdapter.isMode(BlackMessageListAdapter.MODE_NORMAL)) {
		    int top = v.getTop();
		    mListView.setSelectionFromTop(position, top);
		    mListView.setFastScrollEnabled(false);
		    mMsgListAdapter.changeMode(BlackMessageListAdapter.MODE_EDIT);
		    mActionMode = startActionMode(mActionModeCallBack);
		    mMsgListAdapter.switchChecked(id/*mMsgListAdapter.getItemId(position)*/);
		    mActionModeCallBack.updateActionMode();
		    return true;
		}
		return false;
	    }
	});
	mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    public void onItemClick(AdapterView<?> adapter, View v, int position,
			long id) {
		if(mMsgListAdapter.isMode(BlackMessageListAdapter.MODE_EDIT)) {
		    mMsgListAdapter.switchChecked(id/*mMsgListAdapter.getItemId(position)*/);
		    mActionModeCallBack.updateActionMode();
		} else {
		    showDialog(mMsgListAdapter.getBlackMessageItemById(id));
		}
	    }
	});
    }

    private void showDialog(final BlackMessageItem bmItem) {
	if(bmItem == null) return;
	String copy = getResources().getString(R.string.sim_copy_to_phone_memory); //sms
	String back = getResources().getString(R.string.back_to_phone_memory); //mms
	String delete = getResources().getString(R.string.delete);
	final String[] objects;
	if(bmItem.getBlackMsgType() == BlackMessageItem.SMS) {
	    objects = new String[] {copy, delete};
	} else {
	    objects = new String[] {back, delete};
	}
	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	dialog.setTitle(R.string.select_link_title);
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, objects);
	dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int which) {
		boolean res = false;
		switch(which) {
		case 0: //copy&back
		    if(bmItem.getBlackMsgType() == BlackMessageItem.SMS) {
			res = BlackMessageUtils.copySmsFromBlacklist(BlacklistMessageActivity.this, bmItem);
		    } else if(bmItem.getBlackMsgType() == BlackMessageItem.MMS) {
			res = BlackMessageUtils.copyMmsFromBlacklist(BlacklistMessageActivity.this, bmItem);
			if(res) {
			    res = BlackMessageUtils.deleteBlackMessageById(BlacklistMessageActivity.this, bmItem.getId());
			}
		    }
		    break;
		case 1: //delete
		    res = BlackMessageUtils.deleteBlackMessageById(BlacklistMessageActivity.this, bmItem.getId());
		    break;
		}
		if(res) {
		    makeAToast(R.string.done_text);
		}
	    }
	});
	dialog.setNeutralButton(android.R.string.cancel, null).show();
    }

    @Override
    protected void onResume() {
	super.onResume();
	needRefresh = true;
	if(mObserverCount > 0) {
	    startQuery();
	}
    }

    @Override
    protected void onPause() {
	super.onPause();
	needRefresh = false;
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	mContentResolver.unregisterContentObserver(mContentObserver);
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
    
    private void updateState(int state) {
    	switch(state) {
    	case STATE_SHOW_EMPTY:
    	    mListView.setVisibility(View.GONE);
    	    emptyText.setVisibility(View.VISIBLE);
    	    break;
    	case STATE_SHOW_LIST:
    	    mListView.setVisibility(View.VISIBLE);
    	    emptyText.setVisibility(View.GONE);
    	    break;
    	}
    }
    
    private class QueryHandler extends AsyncQueryHandler {
    	private final BlacklistMessageActivity mParent;

	public QueryHandler(ContentResolver cr, BlacklistMessageActivity parent) {
	    super(cr);
	    mParent = parent;
	}

	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
	    if(mObserverCount > 0) {
		BlacklistMessageActivity.this.startQuery();
		return;
	    }
	    isQuerying = false;
	    if(mCursor!=null && !mCursor.isClosed()) {
		stopManagingCursor(mCursor);
		mCursor.close();
	    }
	    mCursor = cursor;
	    if(mCursor != null) {
		if(!mCursor.moveToFirst()) {
		    updateState(STATE_SHOW_EMPTY);
		} else if(mMsgListAdapter == null) {
		    mMsgListAdapter = new BlackMessageListAdapter(mParent, mCursor, false);
		    mMsgListAdapter.initCache();
		    mListView.setAdapter(mMsgListAdapter);
		    updateState(STATE_SHOW_LIST);
		} else {
		    mMsgListAdapter.changeCursor(mCursor);
		    mMsgListAdapter.initCache();
		    updateState(STATE_SHOW_LIST);
		}
		startManagingCursor(mCursor);
	    } else {
		updateState(STATE_SHOW_EMPTY);
	    }
	}

	@Override
	protected void onDeleteComplete(int token, Object cookie, int result) {
	    super.onDeleteComplete(token, cookie, result);
	}
    	
    }
    
    private void startQuery() {
    	try {
    	    mObserverCount = 0;
    	    isQuerying = true;
	    String selection = "blacktext_id="+blacktextId;
    	    mQueryHandler.startQuery(0, null, 
    		BlackMessageUtils.CONTENT_URI_BLACK_MESSAGES, null, selection, null, null);
    	} catch(SQLiteException e) {
    	    SqliteWrapper.checkSQLiteException(this, e);
    	}
    }

    @Override
    public boolean onNavigateUp() {
	finish();
	return true;
    }

    class ActionModeCallBack implements ActionMode.Callback, OnMenuItemClickListener {
	private PopupMenu mSelectPopupMenu = null;
	private boolean mSelectAll = true;
	private Button mTextSelect = null;

	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	    View customView = mInflater.inflate(R.layout.black_actionbar_edit, null);
	    mode.setCustomView(customView);
	    mTextSelect = (Button) customView.findViewById(R.id.text_select);
	    mTextSelect.setOnClickListener(new View.OnClickListener() {
		public void onClick(View arg0) {
		    if(mSelectPopupMenu == null) {
			mSelectPopupMenu = createSelectPopupMenu(mTextSelect);
		    } else {
			updateSelectPopupMenu();
			mSelectPopupMenu.show();
		    }
		}
	    });
	    MenuInflater menuInflater = mode.getMenuInflater();
	    menuInflater.inflate(R.menu.black_edit_view_menu, menu);
			
	    return true;
	}

	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	    int selectedCount = mMsgListAdapter.getCheckedItemsCount();
	    if(selectedCount == 0) {
	  	menu.findItem(R.id.delete).setEnabled(false);
		//menu.findItem(R.id.copy).setEnabled(false);
	    } else {
		menu.findItem(R.id.delete).setEnabled(true);
		//menu.findItem(R.id.copy).setEnabled(true);
	    }
	    return true;
	}
		
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    switch(item.getItemId()) {
	    case R.id.delete:
		boolean res = false;
		if(mMsgListAdapter.isCheckedAll()) {
		    res = BlackMessageUtils.deleteBlackMessagesByBlackTextId(BlacklistMessageActivity.this, blacktextId);
		} else {
		    List<Long> checkedItemIds = mMsgListAdapter.getCheckedItemIds();
		    res = BlackMessageUtils.deleteBlackMessagesByIds(BlacklistMessageActivity.this, checkedItemIds);
		}
		if(res) {
		    makeAToast(R.string.done_text);
		}
		break;
	    /*
	    case R.id.copy:
		Log.e("TAG", "copy");
		break;
	    */
	    default:
		return false;
	    }
	    mode.finish();
	    return true;
	}
		
	public void onDestroyActionMode(ActionMode mode) {
	    mListView.setFastScrollEnabled(true);
	    mMsgListAdapter.changeMode(BlackMessageListAdapter.MODE_NORMAL);
	    invalidateOptionsMenu();
	    if(mActionMode != null) {
		mActionMode = null;
	    }
	    if(mSelectPopupMenu != null) {
		mSelectPopupMenu.dismiss();
		mSelectPopupMenu = null;
	    }
	}
		
	private PopupMenu createSelectPopupMenu(View anchorView) {
	    final PopupMenu popupMenu = new PopupMenu(BlacklistMessageActivity.this, anchorView);
	    popupMenu.inflate(R.menu.black_select_popup_menu);
	    popupMenu.setOnMenuItemClickListener(this);
	    return popupMenu;
	}
		
	private void updateSelectPopupMenu() {
	    if(mSelectPopupMenu == null) {
		mSelectPopupMenu = createSelectPopupMenu(mTextSelect);
		return;
	    }
	    final Menu menu = mSelectPopupMenu.getMenu();
	    int selectedCount = mMsgListAdapter.getCheckedItemsCount();
	    if(mMsgListAdapter.getCount() == 0) {
		menu.findItem(R.id.select).setEnabled(false);
	    } else {
		menu.findItem(R.id.select).setEnabled(true);
		if(mMsgListAdapter.getCount() != selectedCount) {
		    menu.findItem(R.id.select).setTitle(R.string.select_all);
		    mSelectAll = true;
		} else {
		    menu.findItem(R.id.select).setTitle(R.string.deselect_all);
		    mSelectAll = false;
		}
	    }
	}
		
	public boolean onMenuItemClick(MenuItem item) { //OnMenuItemClickListener
	    switch(item.getItemId()) {
	    case R.id.select:
		if(mSelectAll) {
		    mMsgListAdapter.selectAll();
		} else {
		    mMsgListAdapter.clearChecked();
		}
		updateActionMode();
	    }
	    return false;
	}
		
	public void updateActionMode() {
            int selectedCount = mMsgListAdapter.getCheckedItemsCount();
            String selected = getResources().getString(R.string.selected);
            selected = "" + selectedCount + " " + selected;
            mTextSelect.setText(selected);

            mActionModeCallBack.updateSelectPopupMenu();
            if (mActionMode != null) {
                mActionMode.invalidate();
            }
        }
		
    }

}
