package com.android.phone.recorder;

import java.io.File;
import java.util.List;

import com.android.phone.recorder.RecordStorageLoadTask.StorageListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.provider.Settings;

import com.android.phone.R;

public class AutoCallRecordActivity extends Activity implements OnClickListener {

	private View autoCallRecordSwitchBtn;
	private View storageSelectBtn;
	private View recordFileListBtn;
	private TextView storageSummary;
	private CheckBox mCheckBox;

	private int mDefaultPosition = 0;

	private StorageListener mStorageListener = new StorageListener() {

		@Override
		public void onFinish(List<StorageInfo> infos) {
			String defaultStorage = getDefaultStorage(AutoCallRecordActivity.this);
			if (infos.size() == 1) {
				mDefaultPosition = 0;
			} else if (infos.size() > 1) {
				boolean found = false;
				int sdPosition = 0;
				for (int i = 0; i < infos.size(); i++) {
					StorageInfo info = infos.get(i);
					if (info.getPath().equals(defaultStorage)) {
						found = true;
						mDefaultPosition = i;
						break;
					}
				}
				if (!found) {
					mDefaultPosition = sdPosition;
				}
			}
			setStorageSummary(infos.get(mDefaultPosition).getName());
		}

		@Override
		public void onUpdate(long progress) {
		}

		@Override
		public void onStart() {
		}
	};


	public AutoCallRecordActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_call_record_layout);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		initViews();
	}

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }
            return false;
        }

	private void initViews() {
		autoCallRecordSwitchBtn = findViewById(R.id.auto_record_btn);
		mCheckBox = (CheckBox) findViewById(R.id.auto_call_record_switch);
		storageSelectBtn = findViewById(R.id.storage_location_btn);
		recordFileListBtn = findViewById(R.id.record_file_list_btn);
		autoCallRecordSwitchBtn.setOnClickListener(this);
		storageSelectBtn.setOnClickListener(this);
		recordFileListBtn.setOnClickListener(this);
		storageSummary = (TextView) findViewById(R.id.storage_location_summary);

		if (getAutoCallRecordValue() == 1) {
			mCheckBox.setChecked(true);
                } else {
			mCheckBox.setChecked(false);
		}
	}

	private void setStorageSummary(String text) {
		if (storageSummary != null) {
			storageSummary.setText(text);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		requestLoadStorageList(mStorageListener);
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.auto_record_btn:
			if (mCheckBox.isChecked()) {
				mCheckBox.setChecked(false);
				setAutoCallRecordValue(false);
			} else {
				mCheckBox.setChecked(true);
				setAutoCallRecordValue(true);
			}
			break;

		case R.id.storage_location_btn:
			showStorageSelectDialog();
			break;

		case R.id.record_file_list_btn:
			toStorageList();
			break;
		}

	}

    private int getAutoCallRecordValue() {
        int autoRecordValue = Settings.Global.getInt(this.getContentResolver(),
                    Settings.Global.TELEPHONY_AUTO_CALL_RECORD_CONFIG, 0);
        CallRecordLog.d("AutoCallRecordActivity>>>getAutoCallRecordValue(): autoRecordValue="+autoRecordValue);
        return autoRecordValue;
    }

    private void setAutoCallRecordValue(boolean autoRecordEnable) {
        Settings.Global.putInt(this.getContentResolver(), 
                Settings.Global.TELEPHONY_AUTO_CALL_RECORD_CONFIG, autoRecordEnable ? 1 : 0);
    }

	private void showStorageSelectDialog() {
		new StorageSelectDialog(this, mDefaultPosition).show(
				getFragmentManager(), "storage_select");
	}

	private void toStorageList() {
		Intent intent = new Intent(this, StorageListActivity.class);
		startActivity(intent);
                /*
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File f = new File("/storage/sdcard0/mtklog");
		intent.setDataAndType(Uri.fromFile(f), "*");
		startActivity(intent);
                */
	}

	@SuppressLint("ValidFragment")
	public class StorageSelectDialog extends DialogFragment {
		private Context mContext;
		private int mSelectPosition = 0;

		public StorageSelectDialog(Context context, int selectPosition) {
			mContext = context;
			mSelectPosition = selectPosition;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			View v = inflater.inflate(R.layout.auto_call_record_storage_list_select_dialog, null);
			final ListView listView = (ListView)v.findViewById(R.id.storage_select_list);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View itemView,
						int position, long id) {
					int count = listView.getAdapter().getCount();
					if (count > 1) {
						if (mSelectPosition != position) {
							mSelectPosition = position;
							StorageListAdapter ad = (StorageListAdapter)listView.getAdapter();
							ad.setChecked(position);
						}
					}
					
				}});
			final StorageListAdapter adapter = new StorageListAdapter(mContext, mSelectPosition);
			listView.setAdapter(adapter);
			requestLoadStorageList(new StorageListener() {

				@Override
				public void onFinish(List<StorageInfo> infos) {
					/*
					String defaultStorage = getDefaultStorage(mContext);
					if (infos.size() == 1) {
						mDefaultPosition = 0;
						mSelectPosition = 0;
						adapter.setChecked(mSelectPosition);
					} else if (infos.size() > 1) {
						boolean found = false;
						int sdPosition = 0;
						for (int i = 0; i < infos.size(); i++) {
							StorageInfo info = infos.get(i);
							if (info.getPath().equals(defaultStorage)) {
								found = true;
								mDefaultPosition = i;
								mSelectPosition = i;
								adapter.setChecked(i);
								break;
							}
						}
						if (!found) {
							mDefaultPosition = sdPosition;
							mSelectPosition = sdPosition;
							adapter.setChecked(sdPosition);
						}
					}
					*/
					adapter.changeData(infos);
				}

				@Override
				public void onUpdate(long progress) {
				}

				@Override
				public void onStart() {
				}});
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.storage_select_dialog_title)
				.setView(v)
				.setNegativeButton(android.R.string.cancel, null)
				.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							mDefaultPosition = mSelectPosition;
							StorageInfo info = (StorageInfo)adapter.getItem(mDefaultPosition);
							setStorageSummary(info.getName());
							setDefaultStorage(mContext, info.getPath());
						}
					});
			return builder.create();
		}
	}
	
	private void requestLoadStorageList(StorageListener listener) {
		new RecordStorageLoadTask(this, listener).execute((Void)null);
	}

	private void setDefaultStorage(Context context, String path) {
		CallRecordLog.d("AutoCallRecordActivity>>>setDefaultStorage(): path="+path);
		Settings.Global.putString(context.getContentResolver(), 
			Settings.Global.AUTO_CALL_RECORD_DEFAULT_STORAGE, path);
	}

	private String getDefaultStorage(Context context) {
		String path = Settings.Global.getString(context.getContentResolver(), 
    				Settings.Global.AUTO_CALL_RECORD_DEFAULT_STORAGE);
		CallRecordLog.d("AutoCallRecordActivity>>>getDefaultStorage(): path="+path);
		return path;
	}
	
	class StorageListAdapter extends BaseAdapter {
		private List<StorageInfo> mDatas;
		
		private Context mContext;
		
		private LayoutInflater inflater;
		
		private int checkedPosition;
		
		public StorageListAdapter(Context context, int checkedPosition) {
			mContext = context;
			this.checkedPosition = checkedPosition;
			inflater = LayoutInflater.from(mContext);
		}
		
		public void setChecked(int position) {
			checkedPosition = position;
			this.notifyDataSetChanged();
		}
		
		public int getCheckedPosition() {
			return checkedPosition;
		}
		
		public void changeData(List<StorageInfo> data) {
			mDatas = data;
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mDatas == null ? 0 : mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = inflater.inflate(R.layout.auto_call_record_storage_dialog_list_item, null);
			TextView text = (TextView)v.findViewById(R.id.select_storage_label);
			RadioButton rd = (RadioButton)v.findViewById(R.id.select_storage_radioButton);
			
			text.setText(mDatas.get(position).getName());
			if (position == checkedPosition) {
				rd.setChecked(true);
			} else {
				rd.setChecked(false);
			}
			return v;
		}
		
	}

}
