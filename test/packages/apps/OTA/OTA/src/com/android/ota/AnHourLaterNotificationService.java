package com.android.ota;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class AnHourLaterNotificationService extends Service{
    private static final String TAG = "AnHourLaterNotificationService";
    PendingIntent pi;
    NotificationManager anHourmanager;
    String downloadURL;
    int downloadByte;
    private String packageType;//包类型
    private anHourdeleteIntentReceiver adReceiver=null;//清除通知栏广播
    @Override

    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override

    public void onCreate() {
        // Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
        if(adReceiver==null)adReceiver=new anHourdeleteIntentReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.AnHourDOWNLOAD_DeleteIntent");
        //注册监听通知栏“清除”监听
        registerReceiver(adReceiver, filter);
    }



    @Override

    public void onDestroy() {
        //Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
        anHourmanager.cancel(1);
        //取消一小时后下载
        AlarmManager  am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
        if(adReceiver!=null)unregisterReceiver(adReceiver);
    }



    @Override

    public void onStart(Intent intent, int startid) {
        if ((intent != null) && (intent.getExtras() != null)) {
            downloadURL= intent.getExtras().getString("downloadUrl");
            downloadByte=intent.getExtras().getInt("downloadByte");
            //  Log.d(TAG,"==downloadByte=="+downloadByte);
            packageType=intent.getExtras().getString("packageType");
            ActivityUtils.setParameter(getApplicationContext(),
                    "NotifService_downloadURl", downloadURL);
            ActivityUtils.setParameter(getApplicationContext(),
                    "NotifService_downloadByte", downloadByte + "");
            ActivityUtils.setParameter(getApplicationContext(),
                    "NotifService_packageType", packageType);
        }
        else{
            downloadURL=ActivityUtils.getParameter(getApplicationContext(), "NotifService_downloadURl");
            packageType=ActivityUtils.getParameter(getApplicationContext(), "NotifService_packageType");
            String strbyte=ActivityUtils.getParameter(getApplicationContext(), "NotifService_downloadByte");
            if(!strbyte.equals(""))	downloadByte=Integer.parseInt(strbyte);
            else downloadByte=5120;
        }
        if(downloadByte<=0){downloadByte=5210;}
        //开始计时，一小时后下载
        AlarmManager  am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intet = new Intent(this,AnhourlaterDownload.class);
        intet.putExtra("downloadUrl",downloadURL);
        intet.putExtra("downloadByte", downloadByte);
        intet.putExtra("packageType", packageType);
        pi = PendingIntent.getActivity(this, 0, intet, 0);
        //am.set(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+(60*60)*1000 , pi); //  (60*60)*1000
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(60*60)*1000 , pi); //  (60*60)*1000
        addAnHourNotificaction();//发送通知

    }

    /**
     * 添加一个一小时后下载提示的 notification
     */
    private void  addAnHourNotificaction() {
        anHourmanager= (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个Notification
        Notification notific = new Notification();
        // 设置显示在手机最上边的状态栏的图标
        notific.icon =android.R.drawable.ic_lock_idle_low_battery;
        // 当当前的notification被放到状态栏上的时候，提示内容
        //	notific.tickerText =getResources().getString(R.string.anhour_notif_download);
        notific.tickerText =getResources().getString(R.string.anhour_notif_download);
        /***
         * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，该Intent会被触发
         * notification.contentView:我们可以不在状态栏放图标而是放一个view
         * notification.deleteIntent 当当前notification被移除时执行的intent
         * notification.vibrate 当手机震动时，震动周期设置
         */
        // 添加声音提示
        //notific.defaults=Notification.DEFAULT_SOUND;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        //notific.audioStreamType= android.media.AudioManager.ADJUST_LOWER;

        //下边的两个方式可以添加音乐
        //notific.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
        //notific.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
        Intent intent = new Intent(this, AnhourlaterDownload.class);
        intent.putExtra("downloadUrl",downloadURL);
        intent.putExtra("downloadByte", downloadByte);
        intent.putExtra("packageType", packageType);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Intent idt=new Intent("android.intent.action.AnHourDOWNLOAD_DeleteIntent");
        notific.deleteIntent=PendingIntent.getBroadcast(this, 0, idt, 0);
        // 点击状态栏的图标出现的提示信息设置
        notific.setLatestEventInfo(this, this.getResources().getString(R.string.update_sys_title), this.getResources().getString(R.string.click_download_update), pendingIntent);
        anHourmanager.notify(1, notific);

    }
    /**
     * 广播接收器
     * @author user
     * 监听系统通知栏“清除”按钮
     */
    private class anHourdeleteIntentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            stopService(new Intent(context, AnHourLaterNotificationService.class));
        }
    }
}
