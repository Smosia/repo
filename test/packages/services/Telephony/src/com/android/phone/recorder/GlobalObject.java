package com.android.phone.recorder;

import java.util.ArrayList;
import java.util.List;

public class GlobalObject {
	private static GlobalObject mInstance;
	
	private List<FileInfo> mCopyList = new ArrayList<FileInfo>();

	private GlobalObject() {
	}
	
	public static GlobalObject getInstance() {
		if (mInstance == null) {
			mInstance = new GlobalObject();
		}
		return mInstance;
	}
	
	public void addAll(List<FileInfo> list) {
		mCopyList.addAll(list);
	}
	
	public void clear() {
		mCopyList.clear();
	}
	
	public List<FileInfo> getCopyList() {
		return mCopyList;
	}

}
