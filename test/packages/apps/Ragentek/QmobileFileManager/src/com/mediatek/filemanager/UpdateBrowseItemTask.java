package com.mediatek.filemanager;

import java.io.File;
import java.util.ArrayList;

import com.mediatek.filemanager.FileManagerOperationActivity.HandlerMessage;
import com.mediatek.filemanager.QueryMediaDbTask.BrowseInfo;

import android.os.AsyncTask;
import android.os.Handler;

public class UpdateBrowseItemTask extends AsyncTask implements HandlerMessage{
    private FileInfoManager mFileInfoManager;
    private Handler mHandler;
  //A BUG_ID:DWYYL-926 shasha.fang 20150603(start)
    private FileInfoAdapter mAdapter;
    //A BUG_ID:DWYYL-926 shasha.fang 20150603(end)
    //M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
    public UpdateBrowseItemTask(FileInfoManager fm,Handler handler,FileInfoAdapter adapter) {
    	 //M BUG_ID:DWYYL-926 shasha.fang 20150603(end)
        this.mFileInfoManager = fm;
        this.mHandler = handler;
        //A BUG_ID:DWYYL-926 shasha.fang 20150603(start)
        this.mAdapter=adapter;
      //A BUG_ID:DWYYL-926 shasha.fang 20150603(end)
    }
    
    @Override
    protected Object doInBackground(Object... arg0) {
        synchronized(this) {
            ArrayList<BrowseInfo> browseList = (ArrayList<BrowseInfo>) arg0[0];
            int size = browseList == null ? 0 : browseList.size();
            
            for (int i = 0; i < size; i++) {
                if (this.isCancelled()) {
                	//M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
                    mFileInfoManager.clearShowFilesInfoList(mAdapter);
                  //M BUG_ID:DWYYL-926 shasha.fang 20150603(end)
                    return true;
                }
                String data = browseList.get(i).data;
                FileInfo fileInfo = new FileInfo(new File(data));
                mFileInfoManager.updateShowFilesInfoList(fileInfo);
             }
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
        mHandler.obtainMessage(HANDLER_MSG_BROWSE_ITEM).sendToTarget();
    }

    
}
