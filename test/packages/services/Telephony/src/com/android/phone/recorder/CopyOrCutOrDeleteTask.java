package com.android.phone.recorder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

public class CopyOrCutOrDeleteTask extends AsyncTask<String, Long, Integer> {
	public final static String ACTION_COPY_TO_PAST = "copy_to_past";
	public final static String ACTION_CUT_TO_PAST = "cut_to_past";
	public final static String ACTION_TO_DELETE = "to_delete";
	
	private List<FileInfo> mSrcFileInfos;
	private String mDstFolder;
	private Listener mListener;
	private Context mContext;

	public CopyOrCutOrDeleteTask(Context context, List<FileInfo> srcFileInfos,
			String dstFolder, Listener l) {
		mContext = context;
		mSrcFileInfos = srcFileInfos;
		mDstFolder = dstFolder;
		mListener = l;
	}

	@Override
	protected Integer doInBackground(String... args) {
		String action = args[0];
		if (ACTION_COPY_TO_PAST.equals(action)) {
			return copyPast();
		} else if (ACTION_CUT_TO_PAST.equals(action)) {
			cutPasteInSameCard();
		} else if (ACTION_TO_DELETE.equals(action)) {
			deleteFiles();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
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
	
	private int copyPast() {
		List<File> fileList = new ArrayList<File>();
		int ret = FileUtils.getAllFileList(mSrcFileInfos, fileList);
        if (ret < 0) {
        	CallRecordLog.i("CopyOrCutToPastTask>>>copyPast,ret = " + ret);
            return ret;
        }
        
        if (!isEnoughSpace(fileList, mDstFolder)) {
        	CallRecordLog.i("CopyOrCutToPastTask>>>copyPast, not enough space.");
            return FileUtils.ERROR_CODE_NOT_ENOUGH_SPACE;
        }

        HashMap<String, String> pathMap = new HashMap<String, String>();
        if (!fileList.isEmpty()) {
            pathMap.put(fileList.get(0).getParent(), mDstFolder);
        }
        for (File file : fileList) {
            File dstFile = getDstFile(pathMap, file, mDstFolder);
            if (dstFile == null) {
                continue;
            }
            if (file.isDirectory()) {
                mkdir(pathMap, file, dstFile);
            } else {
                ret = FileUtils.copyFile(file, dstFile);
                CallRecordLog.d("CopyOrCutToPastTask>>>copyPast,ret = " + ret);
            }
        }
        return FileUtils.ERROR_CODE_SUCCESS;
	}
	
	private int deleteFiles() {
		List<File> deletefileList = new ArrayList<File>();
        int ret = FileUtils.getAllDeleteFiles(mSrcFileInfos, deletefileList);
        if (ret < 0) {
        	CallRecordLog.i("doInBackground,ret = " + ret);
            return ret;
        }
        
        HashMap<File, FileInfo> deleteFileInfoMap = new HashMap<File, FileInfo>();
        for (FileInfo fileInfo : mSrcFileInfos) {
            deleteFileInfoMap.put(fileInfo.getFile(), fileInfo);
        }
        
        for (File file : deletefileList) {
            deleteFile(file);
        }
        
		return FileUtils.ERROR_CODE_SUCCESS;
	}
	
	private boolean mkdir(HashMap<String, String> pathMap, File srcFile, File dstFile) {
		CallRecordLog.d("CopyOrCutToPastTask>>>mkdir,srcFile = " + srcFile + ",dstFile = " + dstFile);
        if (srcFile.exists() && srcFile.canRead() && dstFile.mkdirs()) {
            pathMap.put(srcFile.getAbsolutePath(), dstFile.getAbsolutePath());
            return true;
        } else {
            return false;
        }
    }
	
	private File getDstFile(HashMap<String, String> pathMap, File file, String defPath) {
		CallRecordLog.d("CopyOrCutToPastTask>>>getDstFile.");
        String curPath = pathMap.get(file.getParent());
        if (curPath == null) {
            curPath = defPath;
        }
        File dstFile = new File(curPath, file.getName());

        return checkFileNameAndRename(dstFile);
    }
	
	private boolean isEnoughSpace(List<File> fileList, String dstFolder) {
		if (fileList == null) {
			return false;
		}
		long total = 0;
		for (File f : fileList) {
			total += f.length();
		}
		File dstFile = new File(dstFolder);
		if (total > dstFile.getUsableSpace()) {
			return false;
		}
		return true;
	}

	private Integer cutPasteInSameCard() {
		CallRecordLog.i("CopyOrCutToPastTask>>>cutPasteInSameCard.");

        for (FileInfo fileInfo : mSrcFileInfos) {
            File newFile = new File(mDstFolder + "/"
                    + fileInfo.getName());
            newFile = checkFileNameAndRename(newFile);

            if (newFile == null) {
            	CallRecordLog.i("CopyOrCutToPastTask>>>cutPasteInSameCard,newFile is null.");
                continue;
            }
            
            if (fileInfo.getFile().renameTo(newFile)) {
                return FileUtils.ERROR_CODE_SUCCESS;
            }
        }

        return FileUtils.ERROR_CODE_SUCCESS;
    }
	
	private Integer cutPasteInDiffCard() {
		CallRecordLog.i("CopyOrCutToPastTask>>>cutPasteInSameCard.");

		int ret = FileUtils.ERROR_CODE_SUCCESS;
        List<File> fileList = new ArrayList<File>();
        ret = FileUtils.getAllFileList(mSrcFileInfos, fileList);
        if (ret < 0) {
        	CallRecordLog.i("cutPasteInDiffCard,ret = " + ret);
            return ret;
        }
        
        if (!isEnoughSpace(fileList, mDstFolder)) {
        	CallRecordLog.i("cutPasteInDiffCard,not enough space.");
            return FileUtils.ERROR_CODE_NOT_ENOUGH_SPACE;
        }
        
        List<File> romoveFolderFiles = new LinkedList<File>();
        HashMap<String, String> pathMap = new HashMap<String, String>();
        if (!fileList.isEmpty()) {
            pathMap.put(fileList.get(0).getParent(), mDstFolder);
        }
        
        for (File file : fileList) {
            File dstFile = getDstFile(pathMap, file, mDstFolder);
            if (dstFile == null) {
                continue;
            }

            if (file.isDirectory()) {
                if (mkdir(pathMap, file, dstFile)) {
                    romoveFolderFiles.add(0, file);
                }
            } else {
                ret = FileUtils.copyFile(file, dstFile);
                CallRecordLog.i("cutPasteInDiffCard ret2 = " + ret);
                if (ret == FileUtils.ERROR_CODE_SUCCESS) {
                    deleteFile(file);
                }
            }
        }
        
        for (File file : romoveFolderFiles) {
            file.delete();
        }
        CallRecordLog.i("cutPasteInDiffCard,return success.");

        return FileUtils.ERROR_CODE_SUCCESS;
    }
	
	private boolean deleteFile(File file) {
        if (file == null) {
        } else {
            if (file.canWrite() && file.delete()) {
                return true;
            } else {
            	CallRecordLog.d("deleteFile fail,file name = " + file.getName());
            }
        }
        return false;
    }
	
	File checkFileNameAndRename(File conflictFile) {
        File retFile = conflictFile;
        while (true) {
            if (isCancelled()) {
            	CallRecordLog.i("checkFileNameAndRename,cancel.");
                return null;
            }
            if (!retFile.exists()) {
            	CallRecordLog.i("checkFileNameAndRename,file is not exist.");
                return retFile;
            }
            retFile = FileUtils.genrateNextNewName(retFile);
            if (retFile == null) {
            	CallRecordLog.i("checkFileNameAndRename,retFile is null.");
                return null;
            }
        }
    }

	interface Listener {
		void onStart();

		void onProgress(long progress);

		void onFinish(int ret);
	}

}
