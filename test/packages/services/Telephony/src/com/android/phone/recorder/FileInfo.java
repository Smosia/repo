package com.android.phone.recorder;

import java.io.File;

import android.net.Uri;

public class FileInfo {
	private File mFile;
	
	private String mName;
	private String mPath;
	private boolean isDir;
	private long recordDate;
	private long recordDuration;
	private int simSlot = -1;
	private long mSize;
	private int childrenFileCount;
	private boolean isRootFile;
	
	private boolean checked = false;
	
	public FileInfo() {
		
	}

	public FileInfo(File file) {
		mFile = file;
		mName = mFile.getName();
		mPath = mFile.getAbsolutePath();
		recordDate = mFile.lastModified();
		isDir = mFile.isDirectory();
        if (!isDir) {
            mSize = mFile.length();
        } else {
        	childrenFileCount = mFile.listFiles().length;
        }
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean check) {
		checked = check;
	}
	
	public int getChildrenFileCount() {
		if (isDir) {
			if (isRootFile()) {
				File f = new File(mPath+"/Auto Call Record/");
				if (f != null && f.exists() && f.listFiles() != null) {
					return f.listFiles().length;
				}
			} else {
				if (mFile != null && mFile.exists() && mFile.listFiles() != null) {
					return mFile.listFiles().length;
				}
			}
		}
		return 0;
	}
	
	public File getFile() {
		return mFile;
	}

	public void setFile(File file) {
		this.mFile = file;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public String getPath() {
		return mPath;
	}

	public void setPath(String path) {
		this.mPath = path;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public long getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(long recordDate) {
		this.recordDate = recordDate;
	}

	public long getRecordDuration() {
		return recordDuration;
	}

	public void setRecordDuration(long recordDuration) {
		this.recordDuration = recordDuration;
	}
	
	public void setRootFile(boolean isRootFile) {
		this.isRootFile = isRootFile;
	}
	
	public boolean isRootFile() {
		return isRootFile;
	}

	public int getSimSlot() {
		return simSlot;
	}

	public void setSimSlot(int simSlot) {
		this.simSlot = simSlot;
	}

	public long getSize() {
		return mSize;
	}

	public void setSize(long size) {
		this.mSize = size;
	}
	
	public Uri getUri() {
        return Uri.fromFile(mFile);
    }

}
