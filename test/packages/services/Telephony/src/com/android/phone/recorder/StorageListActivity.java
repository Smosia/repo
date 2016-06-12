package com.android.phone.recorder;

import java.util.List;

import com.android.phone.recorder.RecordStorageLoadTask.StorageListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.phone.R;

@SuppressLint("HandlerLeak")
public class StorageListActivity extends Activity {
	public static final String RECORD_FILE_PATH = "record_file_path";
	private ListView mListView;
	private StorageListAdapter mAdapter;
	
	private StorageListener mStorageListener = new StorageListener() {

		@Override
		public void onFinish(List<StorageInfo> infos) {
			mAdapter.changeData(infos);
			
		}

		@Override
		public void onUpdate(long progress) {
		}

		@Override
		public void onStart() {
		}};
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> listView, View itemView, int position,
				long id) {
			StorageInfo info = (StorageInfo)mAdapter.getItem(position);
			if (info.getChildFileCount() <= 0) {
                            return;
                        }
                        /*
			Intent intent = new Intent(StorageListActivity.this, RecordFileListActivity.class);
			intent.putExtra(RECORD_FILE_PATH, info.getPath()+"/Auto Call Record");
			startActivity(intent);
                        */
                        Intent intent = new Intent("com.android.ragentek.OPEN_SPECIAL_PATH");
                        intent.putExtra("path", info.getPath()+"/Auto Call Record");
                        startActivity(intent);
		}
		
	};

	public StorageListActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_call_record_file_list);

                getActionBar().setDisplayHomeAsUpEnabled(true);

		mListView = (ListView)findViewById(R.id.record_list);
		mAdapter = new StorageListAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mOnItemClickListener);
		
		GlobalObject.getInstance();//instance GlobalObject
	}

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }
            return false;
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
		requestLoadStorageList(mStorageListener);
	}
	
	private void requestLoadStorageList(StorageListener listener) {
		new RecordStorageLoadTask(this, listener).execute((Void)null);
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
	
	class StorageListAdapter extends BaseAdapter {
		private List<StorageInfo> infos = null;
		
		private LayoutInflater inflater;
		
		public StorageListAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}
		
		public void changeData(List<StorageInfo> infos) {
			this.infos = infos;
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return infos == null ? 0 : infos.size();
		}

		@Override
		public Object getItem(int position) {
			return infos.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHold viewHold;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.auto_call_record_storage_list_item, null);
				viewHold = new ViewHold();
				viewHold.storageIcon = (ImageView) convertView.findViewById(R.id.storage_icon);
				viewHold.storageName = (TextView) convertView.findViewById(R.id.storage_name);
				viewHold.childrenFileCount = (TextView) convertView.findViewById(R.id.storage_file_count);
				convertView.setTag(viewHold);
			} else {
				viewHold = (ViewHold)convertView.getTag();
			}
			StorageInfo storageInfo = infos.get(position);
			if (storageInfo.isRemoveable()) {
				viewHold.storageIcon.setImageResource(R.drawable.auto_call_record_sdcard);
			} else {
				viewHold.storageIcon.setImageResource(R.drawable.auto_call_record_phone_storage);
			}
			viewHold.childrenFileCount.setText("("+storageInfo.getChildFileCount()+")");
			viewHold.storageName.setText(storageInfo.getName());
			
			return convertView;
		}
		
		class ViewHold {
			TextView storageName;
			ImageView storageIcon;
			TextView childrenFileCount;
		}
		
	}

}
