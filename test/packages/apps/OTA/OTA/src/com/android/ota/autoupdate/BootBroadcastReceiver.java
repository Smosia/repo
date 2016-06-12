package com.android.ota.autoupdate;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.*;
import com.android.ota.ActivityUtils;
import com.android.ota.DelayUpdateDialog;
import com.android.ota.R;
import com.android.ota.UpdateSystem;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import android.view.WindowManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import com.android.ota.LatestVersionDialog;
import android.os.Build;
/**
 * 接受开机广播
 * 注意：只有在开机情况下，成功运行一次后(不能安装运行在sdcard中)，再关机才会运行此接受广播器
 * @author yulong.liang
 *
 */
/***
 *
 * 1. yulong.liang@ragentek.com 20120711 BUG_ID:QYLE-2297
 *  Description: Click cancel,can be delayed three times
 */
@SuppressLint("NewApi")
public class BootBroadcastReceiver extends BroadcastReceiver{
    private int residue_cable;
    private static final String TAG="BootBroadcastReceiver";
    private boolean bol_autoupdate=true;//Whether to automatically update
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        //开机启动第一个程序
        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            ActivityUtils.setParameter(context, "download_state", "no"); //恢复未下载状态
            //判断是否是第一次开机
            String firstBooting=ActivityUtils.getParameter(context, "ota_firstBooting");
            String cota_autoupdate=ActivityUtils.getParameter(context, "yulong_cota_autoupdate");
            boolean bolauto_download =context.getResources().getBoolean(R.bool.allow_wifi_auto_update);
            //fix bug:When customers don't use the default RGK OTA,Set bol_autoupdate is false
            //cota_autoupdate="" 表明是从未设置过 自动更新
            //第一次开机后，设置为自动更新 3天更新一次
            if ((firstBooting.equals(""))&&(cota_autoupdate.equals(""))&&(bol_autoupdate==true)) {
                Log.d(TAG, "first---booting,firstBooting="+firstBooting+",cota_autoupdate="+cota_autoupdate);
                ActivityUtils.setParameter(context, "ota_firstBooting", "no"); //设置为不是第一次开机
                ActivityUtils.setParameter(context, "yulong_cota_autoupdate", "1");
                //开始计时，3天后提醒下载
                Intent itt2 = new Intent(Intent.ACTION_RUN);
                itt2.setComponent(new ComponentName("com.android.ota",
                        "com.android.ota.autoupdate.BootService"));
                ActivityUtils.setNextNotify(context,itt2,true,ActivityUtils.quency_days);
                if(bolauto_download){
                    ActivityUtils.setParameter(context, "allow_wifi_auto_download", "1");
                }
			} else{
				Log.d(TAG,"autoupdate="+bol_autoupdate);
                //1.判断是否自动更新；
                if((ActivityUtils.getParameter(context, "yulong_cota_autoupdate").equals("1"))&&(bol_autoupdate==true)){
                    //2.判断当前日期和以前日期相比，是否等于3天
				/*	String beginTime=ActivityUtils.getParameter(context, "yulong_autoupdate_time");
				SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd");
				String endTime=simdate.format(new Date());
				long loday=ActivityUtils.getDistanceDays(beginTime, endTime);*/
//                    long oldTime=Long.parseLong(ActivityUtils.getParameter(context, "currTime"));
//                    long newTime=System.currentTimeMillis();
//                    Log.d(TAG, "oldTime="+oldTime+"newTime"+newTime);
//					// if(newTime-oldTime>=((60*60)*1000*24*ActivityUtils.quency_days)){
//					if(true){
                        //Toast.makeText(context, "大于3天>>"+loday, 1).show();
                        //跳转到service进行一些列判断处理

                        //下面这句话，暂时别忘了到时添加  （已在AutoUpdate 里添加，这里暂时不需要添加）
                        // ActivityUtils.setParameter(context, "yulong_autoupdate_time",endTime);
                        Intent itt2 = new Intent(Intent.ACTION_RUN);
                    itt2.setComponent(new ComponentName("com.android.ota",
                            "com.android.ota.autoupdate.BootService"));
                        ActivityUtils.setNextNotify(context,itt2,true,ActivityUtils.quency_days);

                        Intent i = new Intent(Intent.ACTION_RUN);
                    i.setComponent(new ComponentName("com.android.ota",
                            "com.android.ota.autoupdate.BootService"));
                        i.putExtra("startingUpService","true");
                        context.startService(i);//开机启动服务
//					} else{
//                        AlarmManager  am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//                        Intent itt2 = new Intent(Intent.ACTION_RUN);
//                        itt2.setAction("com.android.BootService");
//                        PendingIntent pi = PendingIntent.getService(context, 0, itt2, PendingIntent.FLAG_CANCEL_CURRENT);
//                        am.set(AlarmManager.RTC_WAKEUP,oldTime+(60*60)*1000*24*ActivityUtils.quency_days, pi);
//                    }

				} else {
					Log.d(TAG, "first---booting,firstBooting="+firstBooting+",cota_autoupdate="+cota_autoupdate);
					ActivityUtils.setParameter(context, "ota_firstBooting", "no");
					ActivityUtils.setParameter(context, "yulong_cota_autoupdate", "1");
					Intent itt2 = new Intent(Intent.ACTION_RUN);
                    itt2.setComponent(new ComponentName("com.android.ota",
                            "com.android.ota.autoupdate.BootService"));
					ActivityUtils.setNextNotify(context,itt2,true,ActivityUtils.quency_days);
					if(bolauto_download){
						ActivityUtils.setParameter(context, "allow_wifi_auto_download", "1");
					}
                    //Toast.makeText(context, "没有自动更新", 1).show();
                }
            }
            // add BUG_ID:QYLE-2297 20120711 yulong.liang start
            String delaycount=ActivityUtils.getParameter(context, "delaytime_count");
            //Log.d(TAG, "delaycount==="+delaycount);
            Intent ittda = new Intent(context,DelayUpdateDialog.class);
            if(delaycount.equals("1")){
                ActivityUtils.setParameter(context, "delaytime_count","2");
                ActivityUtils.delayNotify(context,ittda,true);
            }
            else if(delaycount.equals("2")){
                ActivityUtils.setParameter(context, "delaytime_count","3");
                ActivityUtils.delayNotify(context,ittda,true);
            }
            // add BUG_ID:QYLE-2297 20120711 yulong.liang end

            //After the upgrade, the boot prompt "Your phone has been upgraded to the latest version"
            boolean bolNum =context.getResources().getBoolean(R.bool.is_coolpad);
            String snNumber=Build.DISPLAY;
            if(bolNum&&snNumber.indexOf("P2")>-1){
                int Is_General_Boot=android.provider.Settings.System.getInt(context.getContentResolver(), "Is_General_Boot",-1);
                Log.d(TAG, "Is_General_Boot="+Is_General_Boot);
                if(Is_General_Boot==-1){
                    Log.d(TAG, "not_General_Boot");
                    android.provider.Settings.System.putInt(context.getContentResolver(), "Is_General_Boot",1);
                    int oldVersion=ActivityUtils.readNVData(252);
                    java.lang.System.out.println("oldVersion="+oldVersion);
                    if(oldVersion==0){
                        String judgeVersion=ActivityUtils.getNeedCompareNumber();
                        int firstTimeVersion=Integer.parseInt(judgeVersion);
                        ActivityUtils.writeNVData(252,firstTimeVersion);
                    }
                    else{
                        String curVersion=ActivityUtils.getNeedCompareNumber();
                        int newVersion=Integer.parseInt(curVersion);
                        java.lang.System.out.println("newVersion="+newVersion);
                        if(oldVersion!=newVersion){
                            ActivityUtils.writeNVData(252,newVersion);

                            Intent diaintent = new Intent(Intent.ACTION_RUN);
                            diaintent.setClass(context, LatestVersionDialog.class);
                            diaintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(diaintent);
                        }
                    }
                }
            }

        }

    }




}
