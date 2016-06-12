package com.android.ota;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.android.ota.downloadbean.DownloadProgressListener;
import com.android.ota.downloadbean.FileDownloader;
import android.os.Build;

/***
 *
 * 1. yulong.liang@ragentek.com 20120711 BUG_ID:QYLE-2297
 *  Description: Click cancel,can be delayed three times
 *  2. yulong.liang@ragentek.com 20120718 BUG_ID:QYLE-2708
 *  Description: Switch to store State,Purge notification,Click on check for updates,Tips for downloading
 *  3. yulong.liang@ragentek.com 20121205 BUG_ID:QELS-2495
 *  Description: Remove the download statistics in OTA
 */

public class NotificationDownloadService extends Service {

    private static final String TAG = "NotificationDownloadService";
    MediaPlayer player;
    ////////////////////通知栏进度条参数 ///////////////////
    private RemoteViews view=null;
    private Notification notification=new Notification();
    private NotificationManager manager=null;
    private Intent nofintent=null;
    private PendingIntent pIntent=null;//更新显示
    private int progressMax=100;
    private String downloadURL="";
    FileDownloader loader;
    private Thread notifThread=null;
    private boolean CancelState=false;
    private final Handler handler = new UIHandler();

    private deleteIntentReceiver dReceiver;
    // add BUG_ID:QYLE-2708 20120718 yulong.liang start
    private sdcardReceiver sdkReceiver;
    // add BUG_ID:QYLE-2708 20120718 yulong.liang end
    private static final int DOWNLOAD_THREAD_NUM = 3;//zhaochunqing add

    private int downloadByte;//每次下载多少byte
    private String packageType;//包类型
    private boolean debug=true;
    //Del QELS-2495 20121205 yulong.liang start
    //add download statistics 20121023 yulong.liang start
    //private Thread addDownloadInfoThread = null;
    //add download statistics 20121023 yulong.liang end
//Del  QELS-2495 20121205 yulong.liang end
    @Override

    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override

    public void onCreate() {
        //   Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");

        //注册sdcard监听
        dReceiver=new deleteIntentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.YL_DOWNLOAD_DeleteIntent");
        //注册监听通知栏“清除”监听
        registerReceiver(dReceiver, filter);

        manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        view=new RemoteViews(getPackageName(),R.layout.custom_dialog);
        // add BUG_ID:QYLE-2297 20120711 yulong.liang start
        if(!ActivityUtils.getParameter(getApplicationContext(), "ota_priority").equals("Optional")){
// delete BUG_ID:DELL-1040 20120615 yulong.liang start
            //view.setTextViewText(R.id.tv_notif_title,getResources().getString(R.string.ota_update_priority_Mandatory));
// delete BUG_ID:DELL-1040 20120615 yulong.liang end
        }
        // add BUG_ID:QYLE-2297 20120711 yulong.liang end
        nofintent=new Intent(this,DownloadData.class);
        pIntent=PendingIntent.getActivity(this, 0, nofintent, 0);
        notification.icon=android.R.drawable.stat_sys_download;
        Intent i=new Intent("android.intent.action.YL_DOWNLOAD_DeleteIntent");
        notification.deleteIntent=PendingIntent.getBroadcast(this, 0, i, 0);
        view.setImageViewResource(R.id.image, android.R.drawable.stat_sys_download);//起一个线程用来更新progress

        // add BUG_ID:QYLE-2708 20120718 yulong.liang start
        //注册sdcard监听
        sdkReceiver=new sdcardReceiver();
        IntentFilter filtersdcard = new IntentFilter();
        filtersdcard.addAction(Intent.ACTION_MEDIA_SHARED);//如果SDCard未安装,并通过USB大容量存储共享返回
        filtersdcard.addAction(Intent.ACTION_MEDIA_MOUNTED);//表明sd对象是存在并具有读/写权限
        filtersdcard.addAction(Intent.ACTION_MEDIA_UNMOUNTED);//SDCard已卸掉,如果SDCard是存在但没有被安装
        filtersdcard.addAction(Intent.ACTION_MEDIA_CHECKING);  //表明对象正在磁盘检查
        filtersdcard.addAction(Intent.ACTION_MEDIA_EJECT);  //物理的拔出 SDCARD
        filtersdcard.addAction(Intent.ACTION_MEDIA_REMOVED);  //完全拔出
        filtersdcard.addDataScheme("file"); // 必须要有此行，否则无法收到广播
        registerReceiver(sdkReceiver, filtersdcard);
        // add BUG_ID:QYLE-2708 20120718 yulong.liang end

        if(debug) Log.d(TAG, "3333");
        ActivityUtils.setParameter(this, "download_state", "no");
        if(manager!=null){
            if(debug) Log.d(TAG, "准备---删除记录33---清除通知");
            manager.cancel(0);
        }

    }



    @Override

    public void onDestroy() {
        //  Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
        try{
            if(loader!=null){
                if(debug) Log.d(TAG, "准备---删除记录");
                loader.isPause=true;
                loader=null;
            }
            CancelState=true;//取消状态栏进度
            if(notifThread!=null){
                if(debug) Log.d(TAG, "准备---删除记录11");
                notifThread.interrupt();
                notifThread=null;
                if(manager!=null){if(debug) Log.d(TAG, "准备---删除记录22");
                    manager.cancel(0);
                }
            }
            ActivityUtils.setParameter(this, "download_state", "no");
            if(manager!=null){
                if(debug) Log.d(TAG, "准备---删除记录33");
                manager.cancel(0);
            }
            if(dReceiver!=null){
                if(debug) Log.d(TAG, "准备---删除记录44");
                unregisterReceiver(dReceiver);
            }
            if(sdkReceiver!=null){
                if(debug) Log.d(TAG, "准备---删除记录55");
                unregisterReceiver(sdkReceiver);
            }
//Del QELS-2495 20121205 yulong.liang start
//add download statistics 20121023 yulong.liang start
            /**
             if(addDownloadInfoThread!=null){
             if (!addDownloadInfoThread.isInterrupted())
             addDownloadInfoThread.interrupt();
             }
             **/
            //add download statistics 20121023 yulong.liang end
//Del QELS-2495 20121205 yulong.liang end
        }
        catch(Exception e){
            Log.e(TAG, "Error:"+e.toString());
        }

    }


    int oldresult=0;
    int flogFirst=0;//表明是第一次下载

    @Override
    public void onStart(Intent intent, int startid) {
        Log.d(TAG, "onStart");
        CancelState=false;//便于下次恢复状态栏进度

        try {
            Log.d(TAG, "onStart-111-"+intent);
            //Log.d(TAG, "onStart-222-"+intent.getExtras());
            if((intent!=null)&&(intent.getExtras()!=null)){
                downloadURL= intent.getExtras().getString("downloadUrl");
                downloadByte=intent.getExtras().getInt("downloadByte");
                packageType=intent.getExtras().getString("packageType");
                ActivityUtils.setParameter(getApplicationContext(), "NotifService_downloadURl", downloadURL);
                ActivityUtils.setParameter(getApplicationContext(), "NotifService_downloadByte", downloadByte+"");
                ActivityUtils.setParameter(getApplicationContext(), "NotifService_packageType", packageType);
            }
            else{
                downloadURL=ActivityUtils.getParameter(getApplicationContext(), "NotifService_downloadURl");
                packageType=ActivityUtils.getParameter(getApplicationContext(), "NotifService_packageType");
                String strbyte=ActivityUtils.getParameter(getApplicationContext(), "NotifService_downloadByte");
                if(!strbyte.equals(""))	downloadByte=Integer.parseInt(strbyte);
                else downloadByte=5120;
            }
            //DCELWYL-848 liangyulong add start
            if(downloadByte<=0){downloadByte=30000;}
            if(packageType==null){packageType="";}
            //DCELWYL-848 liangyulong add end
            if(debug){
                Log.d(TAG, "packageType=="+packageType+","+downloadByte+"==URL=="+downloadURL);
            }
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                /** String fiName=downloadURL;
                 fiName=fiName.substring(fiName.lastIndexOf("/")+1, fiName.length());
                 fiName=Environment.getExternalStorageDirectory()+"/"+fiName;
                 //删除以前断点下载的文件
                 File ifile = new File(fiName);
                 if(ifile.exists()){
                 ifile.delete();
                 }*/



                // add BUG_ID:QYLE-2297 20120711 yulong.liang end
                notifThread=new Thread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            //要保存的文件目录
                            File savedir = new File(ActivityUtils.getSdcardPath());
                            loader = new FileDownloader(NotificationDownloadService.this,downloadURL, savedir, DOWNLOAD_THREAD_NUM,downloadByte);
                            Log.d(TAG, "loader.checkErrorState=="+loader.checkErrorState);
                            if(loader.checkErrorState==true){
                                loader.isPause=false;

                                //存储当前是正在下载状态中
                                Log.d(TAG, "11--download_state=="+ActivityUtils.getParameter(NotificationDownloadService.this, "download_state"));
                                ActivityUtils.setParameter(NotificationDownloadService.this, "download_state", "yes");
                                Log.d(TAG, "22--download_state=="+ActivityUtils.getParameter(NotificationDownloadService.this, "download_state"));
                                // add BUG_ID:QYLE-2297 20120711 yulong.liang start
                                //0次延迟提醒
                                ActivityUtils.setParameter(NotificationDownloadService.this, "delaytime_count","");
                                progressMax=loader.getFileSize();//设置进度条的最大刻度为文件的长度
                                if(debug)Log.d(TAG,"progressMax="+progressMax);
                                loader.download(new DownloadProgressListener() {

                                    @Override
                                    public void onDownloadSize(int size) {//实时获知文件已经下载的数据长度
                                        Message msg = new Message();
                                        msg.what = 1;
                                        msg.getData().putInt("size", size);
                                        //  Log.d(TAG,"size="+size);
                                        //  handler.sendMessage(msg);//发送消息

                                        //发送特定action的广播
                                        Intent inte = new Intent();
                                        inte.setAction("android.intent.action.MY_RECEIVER");
                                        inte.putExtra("progress", size);
                                        inte.putExtra("progressMax", progressMax);
                                        sendBroadcast(inte);

                                        handler.sendMessage(msg);//发送消息
                                    }
                                });

                                ////////end
                            }
                            else{
                                Toast.makeText(NotificationDownloadService.this, R.string.ota_server_not_available, 1).show();
                                stopService(new Intent(NotificationDownloadService.this, NotificationDownloadService.class));
                            }
                        } catch (Exception e) {
                            handler.obtainMessage(-1).sendToTarget();
                        }
                    }
                });
                notifThread.start();//开始下载


            }else{
                Toast.makeText(getApplicationContext(), R.string.sdcard_not_exist,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }


    }
    private final class UIHandler extends Handler {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int downloaded_size = msg.getData().getInt("size");
                    view.setProgressBar(R.id.pb_notif, progressMax, downloaded_size, false);

                    int result = (int) ((float) downloaded_size
                            / progressMax * 100);

                    view.setTextViewText(R.id.tv_notif, result+"%");//关键部分，如果你不重新更新通知，进度条是不会更新的

                    notification.contentView=view;
                    notification.contentIntent=pIntent;

                    if(!CancelState){
                        // Log.d(TAG, "oldresult="+oldresult+",result="+result);
                        if(result>oldresult|| flogFirst==0 ){
                            flogFirst+=1;
                            //Log.d(TAG,flogFirst+"==result>oldresult");
                            oldresult=result;

                            //通知栏通知
                            manager.notify(0, notification);

                        }

                    }
                    else{
                        manager.cancel(0);
                        if(notifThread!=null){
                            notifThread.interrupt();
                            notifThread=null;
                        }
                        // notifThread.interrupt();
                    }


                    if(result>=100){
                        if(notifThread!=null){
                            notifThread.interrupt();
                            notifThread=null;
                        }
//Del QELS-2495 20121205 yulong.liang start
                        //add download statistics 20121023 yulong.liang start
                        /**
                         if (addDownloadInfoThread == null) {
                         addDownloadInfoThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                        Log.d(TAG, ">>>>sTART" );
                        String service_url = ActivityUtils.getWebServiceUrl(NotificationDownloadService.this);
                        String IMEI = ActivityUtils
                        .getPhoneIMEI(NotificationDownloadService.this);
                        String IP = ActivityUtils.getLocalIpAddress();
                        try {
                        int t = WebServiceUtil.addDownloadInfo(
                        service_url, Build.DISPLAY, IMEI, IP);
                        Log.d(TAG, "T==" + t);
                        if (t == 1) {
                        if (!addDownloadInfoThread.isInterrupted())
                        addDownloadInfoThread.interrupt();
                        }
                        } catch (Exception e) {
                        Log.e(TAG,"webService addDownloadInfo Error:"+e.toString());

                        }

                        }
                        });
                         addDownloadInfoThread.start();
                         }
                         **/
                        //add download statistics 20121023 yulong.liang end
//Del QELS-2495 20121205 yulong.liang end
                        //恢复未下载状态
                        ActivityUtils.setParameter(getApplicationContext(), "download_state", "no");

                        manager.cancel(0);
                        Toast.makeText(getApplicationContext(), R.string.download_data_finish,
                                Toast.LENGTH_LONG).show();
//Del QELS-2495 20121205 yulong.liang start
                        //add download statistics 20121023 yulong.liang start
                        /***
                         try {
                         Thread.sleep(2000);
                         } catch (InterruptedException e1) {
                         // TODO Auto-generated catch block
                         Log.e(TAG,"Threadsleep Error:"+e1.toString());
                         }
                         */
                        //add download statistics 20121023 yulong.liang end
//Del QELS-2495 20121205 yulong.liang end
                        //下载完毕，校验下载的更新包是否完整可用
                        String fiName=downloadURL;
                        fiName=fiName.substring(fiName.lastIndexOf("/")+1, fiName.length());
                        fiName=ActivityUtils.getSdcardPath()+fiName;
                        File file = new File(fiName);//文件保存目录

                        try {
                            if(file.exists()){
                                if(packageType.equals("Complete")){
                                    ActivityUtils.verifyCompletePackage(file, null,TAG);
                                }
                                else{
                                    ActivityUtils.verifyPackage(file, null,TAG);
                                }
                                Toast.makeText(getApplicationContext(), R.string.verify_file_suc, 1).show();
                                // modify BUG_ID:QYLE-2297 20120711 yulong.liang start
                                if(ActivityUtils.getParameter(getApplicationContext(), "ota_priority").equals("Optional")){
                                    Log.d(TAG, "11-Optional");
                                    ActivityUtils.setParameter(getApplicationContext(), "sdcard_updatepackage_URL", fiName);
                                    Intent intent = new Intent(NotificationDownloadService.this,DelayUpdateDialog.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//切记，不加此句，无法实现跳转
                                    startActivity(intent);

                                }
                                else{
                                    Log.d(TAG, "Mandatory");
					/*
		     			Intent intent = new Intent("com.qualcomm.update.REBOOT");
		     	                intent.setData(Uri.fromFile(file));
		     	                intent.putExtra("ota_update", "true");
		     	                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//切记，不加此句，无法实现跳转
		     	                startActivity(intent);
		     			*/
// delete BUG_ID:DELL-1040 20120615 yulong.liang start
				/*
				Intent intent = new Intent(NotificationDownloadService.this,MtkUpgradeRestart.class);
				intent.putExtra("filePath",fiName);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				*/
// delete BUG_ID:DELL-1040 20120615 yulong.liang end	
                                    // add BUG_ID:DELL-1040 20120615 yulong.liang start
                                    ActivityUtils.setParameter(getApplicationContext(), "sdcard_updatepackage_URL", fiName);
                                    Intent intent = new Intent(NotificationDownloadService.this,DelayUpdateDialog.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//切记，不加此句，无法实现跳转
                                    startActivity(intent);
                                    // add BUG_ID:DELL-1040 20120615 yulong.liang end
                                }
                                // modify BUG_ID:QYLE-2297 20120711 yulong.liang end

                            }

                        }
                        catch (IOException e) {
                            Log.e(TAG, e.toString());
                            Toast.makeText(getApplicationContext(),R.string.verify_file_fail, 1).show();
                            if(file.exists()){
                                file.delete();
                            }
                            //e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            Log.e(TAG, e.toString());
                            Toast.makeText(getApplicationContext(), R.string.verify_file_fail, 1).show();
                            if(file.exists()){
                                file.delete();
                            }
                            //e.printStackTrace();
                        }
                        catch(Exception e){
                            Log.e(TAG, e.toString());
                            Toast.makeText(getApplicationContext(),R.string.verify_file_fail, 1).show();
                            if(file.exists()){
                                file.delete();
                            }
                        }finally{
                            stopService(new Intent(NotificationDownloadService.this, NotificationDownloadService.class));
                        }

                    }
            }
        }
    }
    /**
     * 广播接收器
     * @author user
     * 监听系统通知栏“清除”按钮
     */
    private class deleteIntentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // modify BUG_ID:QYLE-2297 20120711 yulong.liang start
            //if(ActivityUtils.haveInternet(context)){

            if(ActivityUtils.getParameter(context, "ota_priority").equals("Optional")){
                //stopService(new Intent(context, NotificationDownloadService.class));
                //Toast.makeText(context, R.string.download_data_cancel, 1).show();
                if(ActivityUtils.haveInternet(context)){
                    Log.d(TAG, "deleteIntentReceiver--not stop server");
                    Toast.makeText(context, R.string.ota_download_now, 1).show();
                }
                else{
                    Log.d(TAG, "deleteIntentReceiver-- stop server");
                    Toast.makeText(context, R.string.download_data_cancel, 1).show();
                    stopService(new Intent(context, NotificationDownloadService.class));
                }
            }
            else{
                if(ActivityUtils.haveInternet(context)){
                    // delete BUG_ID:DELL-1040 20120615 yulong.liang start
                    //Toast.makeText(context,R.string.ota_update_priority_Mandatory, 1).show();
                    // delete BUG_ID:DELL-1040 20120615 yulong.liang end
                }
                else{
                    Toast.makeText(context, R.string.download_data_cancel, 1).show();
                    stopService(new Intent(context, NotificationDownloadService.class));
                }
            }
            // modify BUG_ID:QYLE-2297 20120711 yulong.liang end
			/*}
			else{
				Log.d(TAG, "deleteIntentReceiver--stop server");
				stopService(new Intent(context, NotificationDownloadService.class));
			}*/
        }
    }
    // add BUG_ID:QYLE-2708 20120718 yulong.liang start
    //监听sdcard 广播
    private class sdcardReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Log.d(TAG, "sdcardReceiver=="+arg1.getAction());
            if(arg1.getAction().equals("android.intent.action.MEDIA_EJECT")||arg1.getAction().equals("android.intent.action.MEDIA_SHARED")){//sdcard 手机上不可用
                Log.d(TAG, "sdcard--false");
                stopService(new Intent(arg0, NotificationDownloadService.class));
            }
            else if(arg1.getAction().equals("android.intent.action.MEDIA_MOUNTED")){
                Log.d(TAG, "sdcard--true");
            }

        }

    }
    // add BUG_ID:QYLE-2708 20120718 yulong.liang end

}
