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
	 private String packageType;//°�
	 private anHourdeleteIntentReceiver adReceiver=null;//ȥ³�¸¹㲥
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
		//ע²ὠͽ֪ͨ8¡°ȥ³��
		registerReceiver(adReceiver, filter);
    }



    @Override

    public void onDestroy() {
         //Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
         Log.d(TAG, "onDestroy");
         anHourmanager.cancel(1);
         //ȡлһСʱº�֍
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
    	  //¿ªʼ¼Ǌ±£¬һСʱº�֍
		  AlarmManager  am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
          Intent intet = new Intent(this,AnhourlaterDownload.class);   
          intet.putExtra("downloadUrl",downloadURL);
      	  intet.putExtra("downloadByte", downloadByte);
      	  intet.putExtra("packageType", packageType);
          pi = PendingIntent.getActivity(this, 0, intet, 0);   
          //am.set(AlarmManager.RTC_WAKEUP,SystemClock.elapsedRealtime()+(60*60)*1000 , pi); //  (60*60)*1000
          am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(60*60)*1000 , pi); //  (60*60)*1000
          addAnHourNotificaction();//·¢֪̍ͨ

    }
    
    /**
	 * ͭ¼Ԓ»¸�¡ʱº�ٌ⊾µ notification
	 */
	private void  addAnHourNotificaction() {
		anHourmanager= (NotificationManager) this
		.getSystemService(Context.NOTIFICATION_SERVICE);
		// ´´½¨һ¸�ification
		Notification notific = new Notification();
		// ʨ׃Дʾ՚˖»�±ߵŗ´̬8µō¼±苊		
		notific.icon =android.R.drawable.ic_lock_idle_low_battery;
		// µ±µ±ǰµîotification±»·ŵ½״̬8ʏµŊ±º򣭌⊾Śɝ
	//	notific.tickerText =getResources().getString(R.string.anhour_notif_download);
		notific.tickerText =getResources().getString(R.string.anhour_notif_download);
		/***
		 * notification.contentIntent:һ¸�dingIntent¶Տ󣬵±ԃ»§µ㼷״̬8ʏµō¼±늱£¬¸ntent»ᱻ´¥·¢
		 * notification.contentView:ϒć¿ʒԲ»՚״̬8·ƍ¼±그ˇ·ƒ»¸�w
		 * notification.deleteIntent µ±µ±ǰnotification±»ӆ³�еéntent
		 * notification.vibrate µ±˖»�ʱ£¬ְ¶¯לǚʨ׃
		 */
		// ͭ¼ԉ�ʾ
		//notific.defaults=Notification.DEFAULT_SOUND;
		// audioStreamTypeµŖµ±ِꀵdioManagerאµŖµ£¬´�Ь¥µń£ʽ
		//notific.audioStreamType= android.media.AudioManager.ADJUST_LOWER;
		
		//Ђ±ߵŁ½¸�½¿ʒՌ���ӴV
		//notific.sound = Uri.parse("file:///sdcard/notification/ringer.mp3"); 
		//notific.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6"); 
		Intent intent = new Intent(this, AnhourlaterDownload.class);
        intent.putExtra("downloadUrl",downloadURL);
      	intent.putExtra("downloadByte", downloadByte);
      	intent.putExtra("packageType", packageType);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		Intent idt=new Intent("android.intent.action.AnHourDOWNLOAD_DeleteIntent");
		notific.deleteIntent=PendingIntent.getBroadcast(this, 0, idt, 0);
		// µ㼷״̬8µō¼±괶ЖµŌ⊾хϢʨ׃
		notific.setLatestEventInfo(this, this.getResources().getString(R.string.update_sys_title), this.getResources().getString(R.string.click_download_update), pendingIntent);
		anHourmanager.notify(1, notific);
		
	}
	/**
	 * ¹㲥½Ԋֆ� * @author user
	 * ¼፽ϵͳ֪ͨ8¡°ȥ³�¥
	 */
	private class anHourdeleteIntentReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			stopService(new Intent(context, AnHourLaterNotificationService.class));
		}
	}
}
