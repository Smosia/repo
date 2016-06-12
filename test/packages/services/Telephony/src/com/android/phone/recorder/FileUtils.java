package com.android.phone.recorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

public class FileUtils {
	
	public final static int ERROR_CODE_SUCCESS = 0;
	public final static int ERROR_CODE_UNSUCCESS = -1;
	public final static int ERROR_CODE_NAME_EMPTY = -2;
	public final static int ERROR_CODE_PASTE_UNSUCCESS = -3;
	public final static int ERROR_CODE_NOT_ENOUGH_SPACE = -4;
	
	private final static int BUFFER_SIZE = 2048 * 1024;

	public FileUtils() {

	}
	
	public static int copyFile(File srcFile, File dstFile) {
        if ((srcFile == null) || (dstFile == null)) {
        	CallRecordLog.i("FileUtils>>>copyFile, invalid parameter.");
            return ERROR_CODE_PASTE_UNSUCCESS;
        }

        FileInputStream in = null;
        FileOutputStream out = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        int ret = ERROR_CODE_SUCCESS;
        try {
        	if (!srcFile.exists()) {
        		CallRecordLog.i("FileUtils>>>copyFile, src file is not exist.");
                return ERROR_CODE_PASTE_UNSUCCESS;
            }
            if (!dstFile.createNewFile()) {
            	CallRecordLog.i("FileUtils>>>copyFile, create new file fail.");
                return ERROR_CODE_PASTE_UNSUCCESS;
            }
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(dstFile);

            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                // Copy data from in stream to out stream
                out.write(buffer, 0, len);
            }
        } catch (IOException ioException) {
        	CallRecordLog.e("FileUtils>>>copyFile,io exception!");
            ioException.printStackTrace();
            ret = ERROR_CODE_PASTE_UNSUCCESS;
        } finally {
        	try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.flush();
                    out.getFD().sync();
                    out.close();
                    CallRecordLog.e("FileUtils>>>copyFile, close output stream!");
                }
            } catch (IOException ioException) {
            	CallRecordLog.e("FileUtils>>>copyFile,io exception 2!");
                ioException.printStackTrace();
                ret = ERROR_CODE_PASTE_UNSUCCESS;
            } finally {
            	CallRecordLog.d("FileUtils>>>copyFile,update 100%.");
            }
        }

        return ret;
    }

	public static int getAllFileList(List<FileInfo> srcList, List<File> resultList) {
		int ret = ERROR_CODE_UNSUCCESS;
        for (FileInfo fileInfo : srcList) {
            ret = getAllFile(fileInfo.getFile(), resultList);
            if (ret < 0) {
                break;
            }
        }

        return ret;
    }
	
	public static int getAllFile(File srcFile, List<File> fileList) {
        fileList.add(srcFile);
        if (srcFile.isDirectory() && srcFile.canRead()) {
            File[] files = srcFile.listFiles();
            if (files == null) {
                return ERROR_CODE_UNSUCCESS;
            }
            for (File file : files) {
                int ret = getAllFile(file, fileList);
                if (ret < 0) {
                    return ret;
                }
            }
        }
        return ERROR_CODE_SUCCESS;
    }
	
	public static int getAllDeleteFiles(List<FileInfo> fileInfoList, List<File> deleteList) {
	        int ret = ERROR_CODE_SUCCESS;
	        for (FileInfo fileInfo : fileInfoList) {
	            ret = getAllDeleteFile(fileInfo.getFile(), deleteList);
	            if (ret < 0) {
	                break;
	            }
	        }
	        return ret;
	    }
	
	public static int getAllDeleteFile(File deleteFile, List<File> deleteList) {
        if (deleteFile.isDirectory()) {
            deleteList.add(0, deleteFile);
            if (deleteFile.canWrite()) {
                File[] files = deleteFile.listFiles();
                if (files == null) {
                	CallRecordLog.i("getAllDeleteFile,files is null. ");
                    return ERROR_CODE_UNSUCCESS;
                }
                for (File file : files) {
                    getAllDeleteFile(file, deleteList);
                }
            }
        } else {
            deleteList.add(0, deleteFile);
        }
        return ERROR_CODE_SUCCESS;
    }
	
	public static File genrateNextNewName(File file) {
        String parentDir = file.getParent();
        String fileName = file.getName();
        String ext = "";
        int newNumber = 0;
        if (file.isFile()) {
            int extIndex = fileName.lastIndexOf(".");
            if (extIndex != -1) {
                ext = fileName.substring(extIndex);
                fileName = fileName.substring(0, extIndex);
            }
        }

        if (fileName.endsWith(")")) {
            int leftBracketIndex = fileName.lastIndexOf("(");
            if (leftBracketIndex != -1) {
                String numeric = fileName.substring(leftBracketIndex + 1, fileName.length() - 1);
                if (numeric.matches("[0-9]+")) {
                	CallRecordLog.v("Conflict folder name already contains (): " + fileName
                            + "thread id: " + Thread.currentThread().getId());
                    try {
                        newNumber = Integer.parseInt(numeric);
                        newNumber++;
                        fileName = fileName.substring(0, leftBracketIndex);
                    } catch (NumberFormatException e) {
                    	CallRecordLog.e("Fn-findSuffixNumber(): " + e.toString());
                    }
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(fileName).append("(").append(newNumber).append(")").append(ext);
        return new File(parentDir, sb.toString());
    }

}
