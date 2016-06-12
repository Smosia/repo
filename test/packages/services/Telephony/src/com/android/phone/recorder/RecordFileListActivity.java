package com.android.phone.recorder;

import java.util.ArrayList;
import java.util.List;

import com.android.phone.recorder.DropMenu.DropDownMenu;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.android.phone.R;

@SuppressLint("HandlerLeak")
public class RecordFileListActivity extends Activity implements View.OnClickListener, 
                PopupMenu.OnMenuItemClickListener{
	private ActionBar mActionBar;
	private PopupMenu mOverflowMenu;
	
	private Button selectItems;
	private ImageView share;
	private ImageView moreOption;
	
	private DropDownMenu mSelectionMenu;
	
	private ListView mListView;
	private RecordFileListAdapter mAdapter;
	
	private List<FileInfo> mSelectedListItems = new ArrayList<FileInfo>();
	
	//private List<FileInfo> mCopyListItems = new ArrayList<FileInfo>();
	private GlobalObject mGlobalObject;
	
	private String mStoragePath;
	
	public final static int MODE_VIEW = 1;
	public final static int MODE_EDIT = 2;
	
	public final static int PRIMARY_RECORD_FILE_LIST_PAGE = 1;
	public final static int SECOND_RECORD_FILE_LIST_PAGE = 2;
	private int currentPage = PRIMARY_RECORD_FILE_LIST_PAGE;
	
	public final static int EVENT_GET_PRIMARY_RECORD_FILE_LIST_DONE = 2;
	public final static int EVENT_GET_SECOND_RECORD_FILE_LIST_DONE = 3;
	
	private final static String ACTION_COPY = "action_copy";
	private final static String ACTION_CUT = "action_cut";
	private final static String ACTION_DELETE = "action_delete";
	private final static String ACTION_PASTE = "action_paste";
	
	private String step1 = null;
	
	private String secondPath;
	
	private Handler mHandler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			CallRecordLog.d("RecordFileListActivity>>>handleMessage(): what="+msg.what);
			super.handleMessage(msg);
			switch(msg.what) {
			case EVENT_GET_PRIMARY_RECORD_FILE_LIST_DONE:
				mAdapter.changeData((List<FileInfo>)msg.obj);
				currentPage = PRIMARY_RECORD_FILE_LIST_PAGE;
				break;
				
			case EVENT_GET_SECOND_RECORD_FILE_LIST_DONE:
				mAdapter.changeData((List<FileInfo>)msg.obj);
				currentPage = SECOND_RECORD_FILE_LIST_PAGE;
				break;
			}
		}
		
	};
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> listView, View itemView, int position,
				long id) {
			FileInfo f = (FileInfo)mAdapter.getItem(position);
			if (mAdapter.getMode() == MODE_EDIT) {
				CheckBox checkBox = (CheckBox)itemView.findViewById(R.id.file_checked);
				if (f.isChecked()) {
					checkBox.setChecked(false);
					f.setChecked(false);
					mSelectedListItems.remove(f);
				} else {
					checkBox.setChecked(true);
					f.setChecked(true);
					mSelectedListItems.add(f);
				}
				if (shouldShare()) {
					share.setEnabled(true);
				} else {
					share.setEnabled(false);
				}
				updateSelectItems(mSelectedListItems.size());
			} else {
				if (f.isDir() && f.getChildrenFileCount() > 0) {
					requestLoadSecondRecordFileList(f.getPath());
					secondPath = f.getPath();
				}
			}
			
		}
		
	};
	
	private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View itemView,
				int position, long id) {
			if (mAdapter.getMode() == MODE_VIEW) {
				FileInfo f = (FileInfo)mAdapter.getItem(position);
				f.setChecked(true);
				mSelectedListItems.add(f);
				mAdapter.changeMode(MODE_EDIT);
				
				if (!f.isDir()) {
					share.setEnabled(true);
				} else {
					share.setEnabled(false);
				}
				selectItems.setVisibility(View.VISIBLE);
				updateSelectItems(mSelectedListItems.size());
			}
			return true;
		}
		
	};
	
	private void updateSelectItems(final int count) {
		selectItems.setText("selected("+count+")");
	}
	
	private boolean shouldShare() {
		if (mSelectedListItems == null) {
			return false;
		}
		
		for (FileInfo f : mSelectedListItems) {
			if (f.isDir()) {
				return false;
			}
		}
		return mSelectedListItems.size() > 0;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_call_record_file_list);
		mGlobalObject = GlobalObject.getInstance();
		mActionBar = getActionBar();
		initActionBar();
		mStoragePath = getIntent().getStringExtra(StorageListActivity.RECORD_FILE_PATH);
		mListView = (ListView)findViewById(R.id.record_list);
		mAdapter = new RecordFileListAdapter(this, MODE_VIEW);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mOnItemClickListener);
		mListView.setOnItemLongClickListener(mOnItemLongClickListener);
		
		requestLoadPrimaryRecordFileList(mStoragePath);
	}

	@SuppressLint("NewApi")
	private void initActionBar() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customActionBarView = inflater.inflate(R.layout.auto_call_record_actionbar_custom_layout, null);

        // set dropDown menu on selectItems.
        selectItems = (Button) customActionBarView.findViewById(R.id.select_items);
        selectItems.setOnClickListener(this);
        selectItems.setVisibility(View.GONE);
        
        share = (ImageView) customActionBarView.findViewById(R.id.share);
        share.setEnabled(false);
        share.setOnClickListener(this);

        moreOption = (ImageView) customActionBarView.findViewById(R.id.more_option);
        moreOption.setOnClickListener(this);
        mOverflowMenu = buildOptionsMenu(customActionBarView);
        moreOption.setOnTouchListener(mOverflowMenu.getDragToOpenListener());

        // Show the custom action bar but hide the home icon and title
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        mActionBar.setCustomView(customActionBarView);
	}
	
	private OptionsPopupMenu buildOptionsMenu(View invoker) {
        final OptionsPopupMenu popupMenu = new OptionsPopupMenu(this, invoker) {
            @Override
            public void show() {
            	MenuItem copyMenu = getMenu().findItem(R.id.menu_copy);
            	MenuItem cutMenu = getMenu().findItem(R.id.menu_cut);
            	MenuItem deleteMenu = getMenu().findItem(R.id.menu_delete);
            	MenuItem pasteMenu = getMenu().findItem(R.id.menu_paste);
                if (mSelectedListItems.size() > 0) {
                	copyMenu.setEnabled(true);
                	cutMenu.setEnabled(true);
                	deleteMenu.setEnabled(true);
                } else {
                	copyMenu.setEnabled(false);
                	cutMenu.setEnabled(false);
                	deleteMenu.setEnabled(false);
                }
                
                if (mGlobalObject.getCopyList().size() > 0) {
                	pasteMenu.setEnabled(true);
                } else {
                	pasteMenu.setEnabled(false);
                }
                
                super.show();
            }
        };
        /** @} */
        popupMenu.inflate(R.menu.auto_call_record_list_options_menu);
        final Menu menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(this);
        return popupMenu;
    }

	@Override
	public void onBackPressed() {
		if (mAdapter.getMode() == MODE_EDIT) {
			mSelectedListItems.clear();
			updateSelectItems(mSelectedListItems.size());
			selectItems.setVisibility(View.GONE);
			mAdapter.clearCheckedAll();
			mAdapter.changeMode(MODE_VIEW);
		} else {
			if (currentPage == SECOND_RECORD_FILE_LIST_PAGE) {
				requestLoadPrimaryRecordFileList(mStoragePath);
			} else {
				super.onBackPressed();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void requestLoadPrimaryRecordFileList(String path) {
		Message msg = mHandler.obtainMessage(EVENT_GET_PRIMARY_RECORD_FILE_LIST_DONE);
		new RecordFileListLoadTask(this, msg).execute(path);
	}
	
	private void requestLoadSecondRecordFileList(String path) {
		Message msg = mHandler.obtainMessage(EVENT_GET_SECOND_RECORD_FILE_LIST_DONE);
		new RecordFileListLoadTask(this, msg).execute(path);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mSelectedListItems.clear();
		updateSelectItems(mSelectedListItems.size());
	}
	
	class RecordFileListAdapter extends BaseAdapter {
		private List<FileInfo> fileInfos = null;
		
		private LayoutInflater inflater;
		
		private int mMode;
		
		public RecordFileListAdapter(Context context, int mode) {
			inflater = LayoutInflater.from(context);
			mMode = mode;
		}
		
		public void changeData(List<FileInfo> fileInfos) {
			this.fileInfos = fileInfos;
			this.notifyDataSetChanged();
		}
		
		public void changeMode(int mode) {
			mMode = mode;
			this.notifyDataSetChanged();
		}
		
		public int getMode() {
			return mMode;
		}
		
		public void clearCheckedAll() {
			if (fileInfos == null) {
				return;
			}
			for (FileInfo info : fileInfos) {
				info.setChecked(false);
			}
			this.notifyDataSetChanged();
		}
		
		public void checkedAll() {
			if (fileInfos == null) {
				return;
			}
			for (FileInfo info : fileInfos) {
				info.setChecked(true);
			}
			this.notifyDataSetChanged();
		}
		
		public List<FileInfo> getAllFileInfos() {
			return fileInfos;
		}

		@Override
		public int getCount() {
			return fileInfos == null ? 0 : fileInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return fileInfos.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHold viewHold;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.auto_call_record_file_list_item, null);
				viewHold = new ViewHold();
				viewHold.fileIcon = (ImageView) convertView.findViewById(R.id.file_icon);
				viewHold.fileName = (TextView) convertView.findViewById(R.id.file_name);
				viewHold.childrenFileCount = (TextView) convertView.findViewById(R.id.file_count);
				viewHold.checkBox = (CheckBox) convertView.findViewById(R.id.file_checked);
				convertView.setTag(viewHold);
			} else {
				viewHold = (ViewHold)convertView.getTag();
			}
			FileInfo fileInfo = fileInfos.get(position);
			if (fileInfo.isDir()) {
				viewHold.fileIcon.setImageResource(R.drawable.ic_launcher);
				viewHold.childrenFileCount.setText("("+fileInfo.getChildrenFileCount()+")");
			} else {
				viewHold.fileIcon.setImageResource(R.drawable.ic_launcher);
				//viewHold.childrenFileCount.setText(String.valueOf(fileInfo.getSize()));
			}
			if (mMode ==MODE_EDIT) {
				viewHold.checkBox.setVisibility(View.VISIBLE);
				if (fileInfo.isChecked()) {
					viewHold.checkBox.setChecked(true);
				} else {
					viewHold.checkBox.setChecked(false);
				}
			} else {
				viewHold.checkBox.setVisibility(View.GONE);
			}
			viewHold.fileName.setText(fileInfo.getName());
			
			return convertView;
		}
		
		class ViewHold {
			TextView fileName;
			ImageView fileIcon;
			TextView childrenFileCount;
			CheckBox checkBox;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_option:
			mOverflowMenu.show();
			break;
		case R.id.share:
			share();
			break;
		case R.id.select_items:
			if (getWindow() == null) {
				CallRecordLog.w("onClick,current Activity dinsow is null");
                return;
            }
            if (mSelectionMenu == null || !mSelectionMenu.isShown()) {
                View parent = (View) v.getParent();
                mSelectionMenu = updateSelectionMenu(parent);
                mSelectionMenu.show();
            } else {
            	CallRecordLog.w("mSelectionMenu is already showing, ignore this click");
            }
			break;
		}
		
	}
	
	private DropDownMenu updateSelectionMenu(View customActionBarView) {
        DropMenu dropMenu = new DropMenu(this);
        // new and add a menu.
        DropDownMenu selectionMenu = dropMenu.addDropDownMenu((Button) customActionBarView
                .findViewById(R.id.select_items), R.menu.auto_call_record_menu_all_selection);

        Button selectView = (Button) customActionBarView
                .findViewById(R.id.select_items);
        // when click the selectView button, display the dropDown menu.
        selectView.setOnClickListener(this);
        MenuItem item = selectionMenu.findItem(R.id.action_select_all);

        boolean isSelectedAll = mSelectedListItems.size() == mAdapter.getCount();
        // if select all items, the menu is "Deselect all"; else the menu is "Select all".
        if (isSelectedAll) {
            // dropDown menu title is "Deselect all".
            item.setTitle(R.string.menu_unselect_all);
            // click the menu, deselect all items
            dropMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    // clear select all items
                	clearSelect();
                    return false;
                }
            });
        } else {
            // dropDown Menu title is "Select all"
            item.setTitle(R.string.menu_select_all);
            // click the menu, select all items.
            dropMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    // select all of itmes
                	selectAll();
                    return false;
                }
            });
        }
        return selectionMenu;
    }
	
	private void selectAll() {
		if (mAdapter != null) {
			mAdapter.checkedAll();
			mSelectedListItems.clear();
			mSelectedListItems.addAll(mAdapter.getAllFileInfos());
			updateSelectItems(mSelectedListItems.size());
		}
	}
	
	private void clearSelect() {
		if (mAdapter != null) {
			mAdapter.clearCheckedAll();
			mSelectedListItems.clear();
			updateSelectItems(mSelectedListItems.size());
		}
	}
	
	private class OptionsPopupMenu extends PopupMenu {
        @SuppressLint("NewApi")
		public OptionsPopupMenu(Context context, View anchor) {
            super(context, anchor, Gravity.END);
        }

        @Override
        public void show() {
            final Menu menu = getMenu();
           /* final MenuItem clearFrequents = menu.findItem(R.id.menu_clear_frequents);
            clearFrequents.setVisible(mListsFragment != null &&
                    mListsFragment.getSpeedDialFragment() != null &&
                    mListsFragment.getSpeedDialFragment().hasFrequents());*/
            super.show();
        }
    }

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		int itemId = menuItem.getItemId();
		switch (itemId) {
		case R.id.menu_copy:
			step1 = ACTION_COPY;
			mGlobalObject.clear();
			mGlobalObject.addAll(mSelectedListItems);
			//mSelectedListItems.clear();
			break;
		case R.id.menu_cut:
			step1 = ACTION_CUT;
			mGlobalObject.clear();
			mGlobalObject.addAll(mSelectedListItems);
			//mSelectedListItems.clear();
			break;
		case R.id.menu_delete:
			new CopyOrCutOrDeleteTask(this, mGlobalObject.getCopyList(), 
					null, null).execute(CopyOrCutOrDeleteTask.ACTION_TO_DELETE);
			break;
		case R.id.menu_paste:
			if (ACTION_COPY.equals(step1)) {
				if (currentPage == SECOND_RECORD_FILE_LIST_PAGE) {
					new CopyOrCutOrDeleteTask(this, mGlobalObject.getCopyList(), 
							secondPath, null).execute(CopyOrCutOrDeleteTask.ACTION_COPY_TO_PAST);
				} else if (currentPage == PRIMARY_RECORD_FILE_LIST_PAGE) {
					new CopyOrCutOrDeleteTask(this, mGlobalObject.getCopyList(), 
							mStoragePath, null).equals(CopyOrCutOrDeleteTask.ACTION_COPY_TO_PAST);
				}
			} else if (ACTION_CUT.equals(step1)) {
				if (currentPage == SECOND_RECORD_FILE_LIST_PAGE) {
					new CopyOrCutOrDeleteTask(this, mGlobalObject.getCopyList(), 
							secondPath, null).execute(CopyOrCutOrDeleteTask.ACTION_CUT_TO_PAST);
				} else if (currentPage == PRIMARY_RECORD_FILE_LIST_PAGE) {
					new CopyOrCutOrDeleteTask(this, mGlobalObject.getCopyList(), 
							mStoragePath, null).equals(CopyOrCutOrDeleteTask.ACTION_CUT_TO_PAST);
				}
			}
			break;
		}
		return true;
	}
	
	private void share() {
        Intent intent;
        List<FileInfo> files = mSelectedListItems;
        ArrayList<Parcelable> sendList = new ArrayList<Parcelable>();
        
        if (files.size() > 1) {
            // send multiple files
        	CallRecordLog.d("Share multiple files");
            for (FileInfo info : files) {
                sendList.add(info.getUri());
            }
            
            intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            //intent.setType(FileUtils.getMultipleMimeType(mService, mStoragePath, files));
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, sendList);

            try {
                startActivity(Intent.createChooser(intent, getString(R.string.send_file)));
            } catch (android.content.ActivityNotFoundException e) {
            	CallRecordLog.e("Cannot find any activity");
                // TODO add a toast to notify user; get a function from here
                // and if(!forbidden)
                // below
            }
        } else {
            // send single file
        	CallRecordLog.d("Share a single file");
            FileInfo fileInfo = files.get(0);
            String mimeType = null;//fileInfo.getFileMimeType(mService);

            if (mimeType == null || mimeType.startsWith("unknown")) {
                //mimeType = FileInfo.MIMETYPE_UNRECOGNIZED;
            }
            
            intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType(mimeType);
            Uri uri = Uri.fromFile(fileInfo.getFile());
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            CallRecordLog.d("Share Uri file: " + uri);
            CallRecordLog.d("Share file mimetype: " + mimeType);

            try {
                startActivity(Intent.createChooser(intent, getString(R.string.send_file)));
            } catch (android.content.ActivityNotFoundException e) {
            	CallRecordLog.e("Cannot find any activity");
            }
        }
	}

}
