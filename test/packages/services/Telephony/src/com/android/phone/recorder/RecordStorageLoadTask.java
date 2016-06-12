package com.android.phone.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.IMountService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;

public class RecordStorageLoadTask extends AsyncTask<Void, Long, List<StorageInfo>> {
	private StorageManager mStorageManager;
	
	private StorageListener mListener;

        private Context mContext;
	
	public RecordStorageLoadTask(Context context, StorageListener listener) {
                mContext = context;
		mListener = listener;
		mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
	}

	@Override
	protected List<StorageInfo> doInBackground(Void... args) {
		return getStorages();
	}
	
    private List<StorageInfo> getStorages() {
        List<StorageInfo> storages = new ArrayList<StorageInfo>();
        if (null == mStorageManager) {
            CallRecordLog.w("RecordStorageLoadTask>>>[getStorages]SM is null!");
            return storages;
        }
        /*
        StorageInfo info = new StorageInfo("/storage/sdcard0", "Phone storage");
        storages.add(info);
        info = new StorageInfo("/storage/sdcard1", "SD Card");
        storages.add(info);*/
        StorageVolume volumes[] = mStorageManager.getVolumeList();
        if (volumes != null) {
            CallRecordLog.d("RecordStorageLoadTask>>>[getStorages]volumes are " + volumes);
            for (StorageVolume volume : volumes) {
                if (!Environment.MEDIA_MOUNTED.equals(mStorageManager.getVolumeState(volume.getPath()))) {
                    continue;
                }
                StorageInfo info = new StorageInfo(volume.getPath(), 
                        volume.getDescription(mContext), volume.isRemovable());
                CallRecordLog.d("RecordStorageLoadTask>>>[getStorages]StorageInfo: " + info);
                storages.add(info);
            }
        }

        Collections.sort(storages, new Comparator<StorageInfo>(){

		@Override
		public int compare(StorageInfo lhs, StorageInfo rhs) {
			return rhs.getName().compareToIgnoreCase(lhs.getName());
		}});

        return storages;
    }

    private boolean checkSDCardAvaliable(final String path) {
        if (TextUtils.isEmpty(path)) {
            CallRecordLog.w("RecordStorageLoadTask>>>[checkSDCardAvaliable]path is null!");
            return false;
        }

        String volumeState = "";
        try {
            IMountService mountService = IMountService.Stub.asInterface(ServiceManager
                    .getService("mount"));
            volumeState = mountService.getVolumeState(path);
        } catch (RemoteException e) {
            CallRecordLog.e("RecordStorageLoadTask>>>[checkSDCardAvaliable]catch exception:");
            e.printStackTrace();
        }

        return Environment.MEDIA_MOUNTED.equals(volumeState);
    }

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(List<StorageInfo> result) {
		super.onPostExecute(result);
		if (mListener != null) {
			mListener.onFinish(result);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mListener != null) {
			mListener.onStart();
		}
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		super.onProgressUpdate(values);
		if (mListener != null) {
			mListener.onUpdate(values[0]);
		}
	}
	
	public interface StorageListener {
		void onFinish(List<StorageInfo> infos);
		void onUpdate(long progress);
		void onStart();
	}

}
