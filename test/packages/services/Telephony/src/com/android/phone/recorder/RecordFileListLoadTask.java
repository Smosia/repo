package com.android.phone.recorder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.os.storage.StorageManager;

public class RecordFileListLoadTask extends AsyncTask<String, Long, List<FileInfo>> {
	private Message mMsg;
	private StorageManager mStorageManager;
	
	public RecordFileListLoadTask(Context context, Message msg) {
		mMsg = msg;
		mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
	}

	@Override
	protected List<FileInfo> doInBackground(String... args) {
		if (args[0] == null) {
			return null;
		}
		return loadRecordFiles(args[0]);
	}
	
	private List<FileInfo> loadRecordFiles(String path) {
		CallRecordLog.d("RecordFileListLoadTask>>>loadRecordFiles(): path="+path);
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		
		//for testing
				FileInfo f = new FileInfo();
				f.setName("record1.txt");
				f.setPath(null);
				f.setDir(true);
				f.setRootFile(false);
				fileInfos.add(f);
				FileInfo f2 = new FileInfo();
				f2.setName("record2.txt");
				f2.setPath(null);
				f2.setDir(true);
				f2.setRootFile(false);
				fileInfos.add(f2);
				if (true) {
					return fileInfos;
				}
				
		File[] files = null;
		File dir = new File(path);
		if (dir.exists()) {
			files = dir.listFiles();
			if (files == null) {
				CallRecordLog.d("RecordFileListLoadTask>>>loadRecordFiles,directory is null");
				return null;
			}
		} else {
			CallRecordLog.w("RecordFileListLoadTask>>>loadRecordFiles,directory is not exist.");
			return null;
		}
		long total = files.length;
		CallRecordLog.d("RecordFileListLoadTask>>>loadRecordFiles, total = " + total);
		for (int i = 0; i < total; i++) {
			fileInfos.add(new FileInfo(files[i]));
		}
		CallRecordLog.d("RecordFileListLoadTask>>>loadRecordFiles SUCCESS");

		return fileInfos;
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(List<FileInfo> result) {
		super.onPostExecute(result);
		if (mMsg != null) {
			mMsg.obj = result;
			mMsg.sendToTarget();
		}
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	

}
