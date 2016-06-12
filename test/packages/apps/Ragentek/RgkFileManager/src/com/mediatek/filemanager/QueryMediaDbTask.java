package com.mediatek.filemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mediatek.filemanager.FileManagerOperationActivity.HandlerMessage;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
public class QueryMediaDbTask extends AsyncTask implements HandlerMessage{
    private static final String TAG = "QueryMediaDbTask";
    private static final String BROWSE_FILE_TYPE_TXT = "4";
    private static final String BROWSE_FILE_TYPE_APK = "6";
    private static final String BROWSE_SUFFIX_TXT = ".txt";
    private static final String BROWSE_SUFFIX_APK = ".apk";
    private static final String BROWSE_RECORDING = "Recording";
    private Context mContext;
    private Map<Integer, ArrayList<BrowseInfo>> mMap;
    private Handler mHandler;
    private int mIndex = -1;
    public class BrowseInfo {
        long size;
        String data;
    }
    public QueryMediaDbTask (Context context,Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        mMap = new HashMap<Integer,ArrayList<BrowseInfo>>();
    }
    @Override
    protected Object doInBackground(Object... arg0) {
        synchronized(this) {
            clearData();
            loadDataFromDB();
        }
        return true;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        mHandler.obtainMessage(HANDLER_MSG_BROWSE).sendToTarget();
    }

    public Map<Integer, ArrayList<BrowseInfo>> getMap() {
        return mMap;
    }
    
    private void clearData() {
        mIndex = -1;
        mMap.clear();
    }
    
    private synchronized void loadDataFromDB() {
        queryFromMediaDB(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        queryFromMediaDB(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        queryFromMediaDB(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        queryFromMediaDBByType(MediaStore.Files.getContentUri("external"),BROWSE_SUFFIX_TXT,BROWSE_FILE_TYPE_TXT);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MediaStore.Audio.Media.DATA);
        stringBuilder.append(" LIKE '%");
        stringBuilder.append("/");
        stringBuilder.append(BROWSE_RECORDING);
        stringBuilder.append("/");
        stringBuilder.append("%'");
        String selection = stringBuilder.toString();
        queryFromMediaDB(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,selection,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        queryFromMediaDBByType(MediaStore.Files.getContentUri("external"),BROWSE_SUFFIX_APK,BROWSE_FILE_TYPE_APK);
    }
    
    private synchronized void queryFromMediaDB (Uri uri,String selection,String sort) {
        mIndex++;
        ArrayList<BrowseInfo> browseList = new ArrayList<BrowseInfo>();
        Cursor cursor = null;
        try {
            if (selection == null) {
                cursor = mContext.getContentResolver().query(uri, null, null,null, sort);
            } else {
                cursor = mContext.getContentResolver().query(uri, null, selection,null, sort);
            }
        if (cursor != null) {
            Log.i(TAG,"queryFromMediaDB: "+cursor.getCount());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (this.isCancelled()) {
                    clearData();
                    return;
                }
                BrowseInfo info = new BrowseInfo();
                String data = cursor.getString(cursor  
                    .getColumnIndex(MediaStore.MediaColumns.DATA));
                String size = cursor.getString(cursor
                        .getColumnIndex(MediaStore.MediaColumns.SIZE));
                info.data = data;
                long sizeL = 0L;
                if (!TextUtils.isEmpty(size)) {
                    sizeL = Long.parseLong(size);
                }
                info.size = sizeL;
                cursor.moveToNext();
                browseList.add(info);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.obtainMessage().sendToTarget();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            mMap.put(mIndex, browseList);
        }
    }

    private synchronized void queryFromMediaDBByType(Uri uri,String type,String fileType) {
        mIndex++;
        ArrayList<BrowseInfo> browseList = new ArrayList<BrowseInfo>();
        Cursor cursor = null;
        try {
        String selection = MediaStore.Files.FileColumns.FILE_NAME + " Like ? And "
            + MediaStore.Files.FileColumns.FILE_TYPE + " = ?";
        String[] selectionArgs = new String[]{"%" + type + "%",fileType};
        cursor = mContext.getContentResolver().query(uri,null, selection,selectionArgs, null);
        if (cursor != null) {
            Log.i(TAG,"queryFromMediaDBByType: "+cursor.getCount());
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (this.isCancelled()) {
                    clearData();
                    return;
                }
                String data = cursor.getString(cursor  
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA));
                if (!TextUtils.isEmpty(data)) {
                    if (!data.toLowerCase().endsWith(type)) {
                        cursor.moveToNext();
                        continue;
                    }
                }
                BrowseInfo info = new BrowseInfo();
                String size = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.SIZE));
                info.data = data;
                long sizeL = 0L;
                if (!TextUtils.isEmpty(size)) {
                    sizeL = Long.parseLong(size);
                }
                info.size = sizeL;
                cursor.moveToNext();
                browseList.add(info);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.obtainMessage().sendToTarget();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            mMap.put(mIndex, browseList);
        }
    }
}
