package com.android.phone.recorder;

import java.io.File;

public class StorageInfo {
	private String mPath;
	private String mName;
	private boolean mIsRemoveable;
	
	private File mFile;
	
	public StorageInfo(String path, String name, boolean isRemoveable) {
		mPath = path;
		mName = name;
		mIsRemoveable = isRemoveable;

		if (mPath == null || mName == null) {
			throw new NullPointerException("The mPath and mName must not be null");
		}
		mFile = new File(mPath+"/Auto Call Record");
	}
	
	public String getPath() {
		return mPath;
	}
	
	public String getName() {
		return mName;
	}
	
	public int getChildFileCount() {
		if (mFile == null) {
			return 0;
		}
		if (!mFile.exists()) {
			return 0;
		}
		File[] files = mFile.listFiles();
		if (files == null) {
			return 0;
		}
		return files.length;
	}

	public boolean isRemoveable() {
		return mIsRemoveable;
	}

        @Override
	public String toString() {
		return "mName=" + mName + ", mPath=" + mPath;
	}
	
}
