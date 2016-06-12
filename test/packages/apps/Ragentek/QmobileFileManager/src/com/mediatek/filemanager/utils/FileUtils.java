/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
 */

package com.mediatek.filemanager.utils;

import android.text.TextUtils;

import com.mediatek.filemanager.FileInfo;
import com.mediatek.filemanager.FileManagerApplication;
import com.mediatek.filemanager.MountPointManager;
import com.mediatek.filemanager.service.FileManagerService;
import com.mediatek.filemanager.service.FileManagerService.OperationEventListener;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.content.ContentValues;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.mediatek.filemanager.R;
import android.media.RingtoneManager;
import android.content.Intent;
import com.mediatek.audioprofile.AudioProfileManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mediatek.telephony.TelephonyManagerEx;
import com.android.internal.telephony.PhoneConstants;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Files.FileColumns;
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
/**
 * A utilize class for basic file operations.
 */
public final class FileUtils {
    private static final String TAG = "FileUtils";
    public static final String UNIT_B = "B";
    public static final String UNIT_KB = "KB";
    public static final String UNIT_MB = "MB";
    public static final String UNIT_GB = "GB";
    public static final String UNIT_TB = "TB";
    private static final int UNIT_INTERVAL = 1024;
    private static final double ROUNDING_OFF = 0.005;
    private static final int DECIMAL_NUMBER = 100;
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
	public static final int SET_PHONE_RINGTONE = 1;
	public static final int SET_MESSAGE_RINGTONE = 2;
	public static final int SET_ALARM_RINGTONE = 3;
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
    public static final int TAB_ON_BROWSE = 0;
	public static final int TAB_ON_FILE = 1;
	public static final int TAB_ON_FTP = 2;
	public static final int TAB_ON_BROWSE_ITEM = 3;
	public static final int TAG_CUR_STATE_NORMAL = -1;
	public static int mCurTabIndex = -1;
	public static final int NORMAL_MODE = 0;
	public static final int SELECT_FILE_MODE = 1;
	public static final int SELECT_PATH_MODE = 2;
	public static int mCurMode = NORMAL_MODE;
	public static boolean mHasFileChanged;
	public static String FTP = "ftp://";
    public static String FTP_COLON = ":";
    public static final String FTP_NOTIFICATION_ACTION = "com.android.FTP_ACTION";
    public static final String FTP_USER_PROPERTIES_PATH = "/storage/sdcard0/.users.properties";
    public static int PORT = 2121;
    /**
     * This method check the file name is valid.
     *
     * @param fileName the input file name
     * @return valid or the invalid type
     */
    public static int checkFileName(String fileName) {
        if (TextUtils.isEmpty(fileName) || fileName.trim().length() == 0) {
            return OperationEventListener.ERROR_CODE_NAME_EMPTY;
        } else {
            try {
                int length = fileName.getBytes("UTF-8").length;
                // int length = fileName.length();
                LogUtils.d(TAG, "checkFileName: " + fileName + ",lenth= " + length);
                if (length > FileInfo.FILENAME_MAX_LENGTH) {
                    LogUtils.d(TAG, "checkFileName,fileName is too long,len=" + length);
                    return OperationEventListener.ERROR_CODE_NAME_TOO_LONG;
                } else {
                    return OperationEventListener.ERROR_CODE_NAME_VALID;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return OperationEventListener.ERROR_CODE_NAME_EMPTY;
            }
        }
    }

    /**
     * This method gets extension of certain file.
     *
     * @param fileName name of a file
     * @return Extension of the file's name
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String extension = null;
        final int lastDot = fileName.lastIndexOf('.');
        if ((lastDot >= 0)) {
            extension = fileName.substring(lastDot + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * This method gets name of certain file from its path.
     *
     * @param absolutePath the file's absolute path
     * @return name of the file
     */
    public static String getFileName(String absolutePath) {
        int sepIndex = absolutePath.lastIndexOf(MountPointManager.SEPARATOR);
        if (sepIndex >= 0) {
            return absolutePath.substring(sepIndex + 1);
        }
        return absolutePath;

    }

    /**
     * This method gets path to directory of certain file(or folder).
     *
     * @param filePath path to certain file
     * @return path to directory of the file
     */
    public static String getFilePath(String filePath) {
        int sepIndex = filePath.lastIndexOf(MountPointManager.SEPARATOR);
        if (sepIndex >= 0) {
            return filePath.substring(0, sepIndex);
        }
        return "";

    }

    /**
     * This method generates a new suffix if a name conflict occurs, ex: paste a file named
     * "stars.txt", the target file name would be "stars(1).txt"
     *
     * @param file the conflict file
     * @return a new name for the conflict file
     */

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
                    LogUtils.v(TAG, "Conflict folder name already contains (): " + fileName
                            + "thread id: " + Thread.currentThread().getId());
                    try {
                        newNumber = Integer.parseInt(numeric);
                        newNumber++;
                        fileName = fileName.substring(0, leftBracketIndex);
                    } catch (NumberFormatException e) {
                        LogUtils.e(TAG, "Fn-findSuffixNumber(): " + e.toString());
                    }
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(fileName).append("(").append(newNumber).append(")").append(ext);
        if (FileUtils.checkFileName(sb.toString()) < 0) {
            return null;
        }
        return new File(parentDir, sb.toString());
    }

    /**
     * This method converts a size to a string
     *
     * @param size the size of a file
     * @return the string represents the size
     */
    public static String sizeToString(long size) {
        String unit = UNIT_B;
        if (size < DECIMAL_NUMBER) {
            LogUtils.d(TAG, "sizeToString(),size = " + size);
            return Long.toString(size) + " " + unit;
        }

        unit = UNIT_KB;
        double sizeDouble = (double) size / (double) UNIT_INTERVAL;
        if (sizeDouble > UNIT_INTERVAL) {
            sizeDouble = (double) sizeDouble / (double) UNIT_INTERVAL;
            unit = UNIT_MB;
        }
        if (sizeDouble > UNIT_INTERVAL) {
            sizeDouble = (double) sizeDouble / (double) UNIT_INTERVAL;
            unit = UNIT_GB;
        }
        if (sizeDouble > UNIT_INTERVAL) {
            sizeDouble = (double) sizeDouble / (double) UNIT_INTERVAL;
            unit = UNIT_TB;
        }

        // Add 0.005 for rounding-off.
        long sizeInt = (long) ((sizeDouble + ROUNDING_OFF) * DECIMAL_NUMBER); // strict to two
        // decimal places
        double formatedSize = ((double) sizeInt) / DECIMAL_NUMBER;
        LogUtils.d(TAG, "sizeToString(): " + formatedSize + unit);

        if (formatedSize == 0) {
            return "0" + " " + unit;
        } else {
            return Double.toString(formatedSize) + " " + unit;
        }
    }

    /**
     * This method gets the MIME type from multiple files (order to return: image->video->other)
     *
     * @param service service of FileManager
     * @param currentDirPath the current directory
     * @param files a list of files
     * @return the MIME type of the multiple files
     */
    public static String getMultipleMimeType(FileManagerService service, String currentDirPath,
            List<FileInfo> files) {
        String mimeType = null;

        for (FileInfo info : files) {
            mimeType = info.getFileMimeType(service);
            if ((null != mimeType)
                    && (mimeType.startsWith("image/") || mimeType.startsWith("video/"))) {
                break;
            }
        }

        if (mimeType == null || mimeType.startsWith("unknown")) {
            mimeType = FileInfo.MIMETYPE_UNRECOGNIZED;
        }
        LogUtils.d(TAG, "Multiple files' mimetype is " + mimeType);
        return mimeType;
    }

    /**
     * This method checks weather extension of certain file(not folder) is changed.
     *
     * @param newFilePath path to file before modified.(Here modify means rename).
     * @param oldFilePath path to file after modified.
     * @return true for extension changed, false for not changed.
     */
    public static boolean isExtensionChange(String newFilePath, String oldFilePath) {
        File oldFile = new File(oldFilePath);
        if (oldFile.isDirectory()) {
            return false;
        }
        String origFileExtension = FileUtils.getFileExtension(oldFilePath);
        String newFileExtension = FileUtils.getFileExtension(newFilePath);
        if (((origFileExtension != null) && (!origFileExtension.equals(newFileExtension)))
                || ((newFileExtension != null) && (!newFileExtension.equals(origFileExtension)))) {
            return true;
        }
        return false;
    }
	//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
	public static synchronized void setRingtoneByFilePath(Context context, String filePath,int type,int sin_id){
		//if (sin_id == -1) {
			//sin_id = getSimSlotid();//dzl 1215
		//}
		 String[] cols = new String[] {
				 MediaStore.Audio.Media._ID, 
				 MediaStore.Audio.Media.TITLE
				 };
		 String[] whereArgs = new String[] {filePath};
		 String where = MediaStore.Audio.Media.DATA +"= ?";
		 Cursor cursor = query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	                cols, where , whereArgs, null);
			setRingtone(context,type,cursor,sin_id); 		
	}
	public static void setRingtone(Context context, int type , Cursor cursor, int mSlot) {
		if(cursor == null){
			return;
		 }
		cursor.moveToFirst();
		int id = 0;
		try {
			id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
		} catch (Exception e) {
			e.printStackTrace();
		 }
        ContentResolver resolver = context.getContentResolver();
        Uri ringUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
		LogUtils.d(TAG, "ringUri: " + ringUri);
        try {
            ContentValues values = new ContentValues(1);
            if(type == SET_PHONE_RINGTONE){
           		values.put(MediaStore.Audio.Media.IS_RINGTONE, "1");
            } else if(type == SET_MESSAGE_RINGTONE){
            	values.put(MediaStore.Audio.Media.IS_NOTIFICATION, "1");
            } else if(type == SET_ALARM_RINGTONE) {
				values.put(MediaStore.Audio.Media.IS_ALARM, "1");
			}
            resolver.update(ringUri, values, null, null);
        } catch (UnsupportedOperationException ex) {
			LogUtils.d(TAG, "UnsupportedOperationException: ");
            return;
        }
        try {
        	if (cursor.getCount() == 1) {
        		String message = context.getResources().getString(R.string.toast_ringtone_p)+"\""+cursor.getString(1);
        		String str = "";
        		if(type == SET_PHONE_RINGTONE) {
        			if(id > 0) {
        				AudioProfileManager profileManager = (AudioProfileManager) context.getSystemService(Context.AUDIO_PROFILE_SERVICE);
        				String activeProfileKey = profileManager.getActiveProfileKey();        				
        				profileManager.setRingtoneUri(activeProfileKey, AudioProfileManager.TYPE_RINGTONE, mSlot, ringUri);
        				str = context.getResources().getString(R.string.toast_ringtone_l);
        				LogUtils.d("TAG", "activeProfileKey="+activeProfileKey+",mSlot="+mSlot+",ringUri="+ringUri.toString());
        			} else {
        				LogUtils.d(TAG, "id error : id="+id);
        			}
        		} else if(type == SET_MESSAGE_RINGTONE) {
					//modify setting Mms Ringtone 20141024 start
					    Intent intent = new Intent("com.mediatek.filemanager.CHANGE_MMS_RINGTONE");
					    intent.putExtra("ringUri",ringUri.toString());
					    intent.putExtra("simId",mSlot);
					    context.sendBroadcast(intent);
					    str = context.getResources().getString(R.string.toast_mms_ringtone_l);
					    AudioProfileManager profileManager1 = (AudioProfileManager) context.getSystemService(Context. AUDIO_PROFILE_SERVICE);
    				    String activeProfileKey1 = profileManager1.getActiveProfileKey();
    				    profileManager1.setRingtoneUri(activeProfileKey1, AudioProfileManager.TYPE_MESSAGE_NOTIFICATION, 0, ringUri);
                        profileManager1.setRingtoneUri(activeProfileKey1, AudioProfileManager.TYPE_MESSAGE_NOTIFICATION, 1, ringUri);
					//modify setting Mms Ringtone 20141024 end
		        } else if(type == SET_ALARM_RINGTONE) {
					RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM, ringUri);
					str = context.getResources().getString(R.string.toast_alarm_ringtone_l);
	            }
        		message += "\"" +str;
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public static Cursor query(Context context, Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder) {
        return query(context, uri, projection, selection, selectionArgs, sortOrder, 0);
    }
    public static Cursor query(Context context, Uri uri, String[] projection,
            String selection, String[] selectionArgs, String sortOrder, int limit) {
        try {
            ContentResolver resolver = context.getContentResolver();
            if (resolver == null) {
                return null;
            }
            if (limit > 0) {
                uri = uri.buildUpon().appendQueryParameter("limit", "" + limit).build();
            }
            return resolver.query(uri, projection, selection, selectionArgs, sortOrder);
         } catch (UnsupportedOperationException ex) {
            return null;
        }
    }
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
//add dzl start
    /**
     * try to convert the file uri to content uri
     * 
     * @param fileUri
     * @return if the file is media file,it will return the content uri.
     *         eg:file://mnt/sdcard/1.mp3 will return content://media/xx but
     *         file://mnt/sdcard/1.vcs will return file://mnt/sdcard/1.vcs
     */
    public static Uri tryContentMediaUri(Context context, Uri fileUri) {
    	
        if (null == fileUri) {
            return null;
        }

        String scheme = fileUri.getScheme();
        if (!ContentResolver.SCHEME_FILE.equals(scheme)) {
            return fileUri;
        } else {
            // the media file exist, if not, return null
            String path = fileUri.getPath();
            LogUtils.d(TAG, "tryContentMediaUri:for " + path);
            if (!new File(path).exists()) {
                LogUtils.w(TAG, "tryContentMediaUri(),file is not exist! file:" + path);
                return null;
            }
        }

        Cursor cursor = null;
        try {
            // for file kinds of uri, query media database
            Uri uri = android.provider.MediaStore.Files.getContentUri("external");
            cursor = context.getContentResolver().query(uri, new String[] { FileColumns.MEDIA_TYPE, FileColumns._ID },
                    FileColumns.DATA + "=?", new String[] { fileUri.getPath() }, null);
            if (null != cursor && cursor.moveToNext()) {
                long id = cursor.getLong(1);
                int type = cursor.getInt(0);
				LogUtils.d(TAG, "dzl type" + type);
                if (type == FileColumns.MEDIA_TYPE_IMAGE) {
					LogUtils.d(TAG, "dzl FileColumns.MEDIA_TYPE_IMAGE" );
                    fileUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);
                } else if (type == FileColumns.MEDIA_TYPE_VIDEO) {
                  fileUri = Uri.parse(MediaStore.Video.Media.EXTERNAL_CONTENT_URI + "/" + id);
                } else if (type == FileColumns.MEDIA_TYPE_AUDIO) {
                	//M:BUG_ID:DWYYL-674 shasha.fang 20150519 (start)
                	return fileUri;
                //fileUri = Uri.parse(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + id);
                //M:BUG_ID:DWYYL-674 shasha.fang 20150519 (end)
                } else {
                    // do-nothing
                    LogUtils.d(TAG, "tryContentMediaUri(),the file is not a media file.");
                }
            }
        } finally {
            if (null != cursor) {
                cursor.close();
                cursor = null;
            }
        }
        return fileUri;
    }

    public static void tryContentMediaUri(Context context, Intent intent, String path) {
        if (path == null) {
            return;
        }
        Cursor cursor = null;
        Uri uri = null;
        String mimeType = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Files.getContentUri("external"),
                    new String[] { FileColumns._ID, FileColumns.MEDIA_TYPE, FileColumns.MIME_TYPE },
                    FileColumns.DATA + "=?",
                    new String[] { path },
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(FileColumns._ID));
                int mediaType = cursor.getInt(cursor.getColumnIndex(FileColumns.MEDIA_TYPE));
                mimeType = cursor.getString(cursor.getColumnIndex(FileColumns.MIME_TYPE));
                if (mediaType == FileColumns.MEDIA_TYPE_IMAGE) {
                    uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                } else if (mediaType == FileColumns.MEDIA_TYPE_VIDEO) {
                    uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                } else if (mediaType == FileColumns.MEDIA_TYPE_AUDIO) {
                    uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                } else {
                    // do-nothing
                    mimeType = null;
                    LogUtils.d(TAG, "tryContentMediaUri ,the file is not a media file.");
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (mimeType != null) {
            intent.setType(mimeType);
        }
        if (uri != null) {
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }
        LogUtils.d(TAG, "tryContentMediaUri with real mimeType = " + mimeType + ", uri = " + uri);
    }
    //add dzl end
    
    public static InetAddress getLocalInetAddress() {
        if (!isConnectedToLocalNetwork()) {
            Log.e(TAG, "getLocalInetAddress called and no connection");
            return null;
        }
        // @TODO: next if block could probably be removed
        if (isConnectedUsingWifi()) {
            Context context = FileManagerApplication.getInstance();
            WifiManager wm = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            int ipAddress = wm.getConnectionInfo().getIpAddress();
            if (ipAddress == 0)
                return null;
            return intToInet(ipAddress);
        }
        // This next part should be able to get the local ip address, but in
        // some case
        // I'm receiving the routable address
        try {
            Enumeration<NetworkInterface> netinterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (netinterfaces.hasMoreElements()) {
                NetworkInterface netinterface = netinterfaces.nextElement();
                Enumeration<InetAddress> adresses = netinterface
                        .getInetAddresses();
                while (adresses.hasMoreElements()) {
                    InetAddress address = adresses.nextElement();
                    // this is the condition that sometimes gives problems
                    if (!address.isLoopbackAddress()
                            && !address.isLinkLocalAddress())
                        return address;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean isConnectedToLocalNetwork() {
        Context context = FileManagerApplication.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        // @TODO: this is only defined starting in api level 13
        final int TYPE_ETHERNET = 0x00000009;
        return ni != null
                && ni.isConnected() == true
                && (ni.getType() & (ConnectivityManager.TYPE_WIFI | TYPE_ETHERNET)) != 0;
    }
    
    public static boolean isConnectedUsingWifi() {
        Context context = FileManagerApplication.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected() == true
                && ni.getType() == ConnectivityManager.TYPE_WIFI;
    }
    
    public static InetAddress intToInet(int value) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = byteOfInt(value, i);
        }
        try {
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            // This only happens if the byte array has a bad length
            return null;
        }
    }
    
    public static byte byteOfInt(int value, int which) {
        int shift = which * 8;
        return (byte) (value >> shift);
    }
}
