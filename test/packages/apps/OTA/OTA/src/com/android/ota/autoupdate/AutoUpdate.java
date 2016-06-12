package com.android.ota.autoupdate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import android.content.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.android.ota.ActivityUtils;
import com.android.ota.AnHourLaterNotificationService;
import com.android.ota.NotificationDownloadService;
import com.android.ota.R;
import com.android.ota.UpdateInfoEntity;
import com.android.ota.UpdateSystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.*;
import android.widget.Toast;

import android.os.PowerManager;

/***
 *
 * 1. yulong.liang@ragentek 20120530 BUG_ID:QYLE-578
 *  Description: click cancel button,Exception occurred 
 * 2. yulong.liang@ragentek.com 20121217 BUG_ID:QELS-2699
 * Description: Modify matching OTA parameters
 */
public class AutoUpdate extends Activity{
    private String TAG="AutoUpdate";
    // private String servicePath="http://www.coolpadfuns.com:8080/updsvr/ota/checkupdate?hw=7018&hwv=V1.10&swv=2.3.001.120510.7018&serialno=864670010001838";
    private String servicePath="";
    private String updateSys_State=null;

    private TextView tv_download_hint_auto;
    private RadioButton r1,r2,r3;
    //add BUG_ID:QYLE-578 liangyulong 20120530(start)
    private Button btn_update_auto,btn_cancel_auto;
    //add BUG_ID:QYLE-578 liangyulong 20120530(end)
    private int residue_cable;
    private IntentFilter iFilter;
    private PowerManager.WakeLock pWakeLock;
    private UpdateInfoEntity upinfo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.autoupdate);
        if(upinfo==null){
            upinfo=(UpdateInfoEntity)getIntent().getParcelableExtra("com.ota.updateinfo");
        }
        initSystem();
    }
    private void initSystem() {

        tv_download_hint_auto=(TextView)this.findViewById(R.id.tv_download_hint_auto);
        r1=(RadioButton)this.findViewById(R.id.rb_wifi_download_auto);
        r2=(RadioButton)this.findViewById(R.id.rb_mobile_wifi_download_auto);
        r3=(RadioButton)this.findViewById(R.id.rb_anhourlater_download_auto);

        iFilter=new IntentFilter();//注册监听
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        PowerManager pm=(PowerManager)this.getSystemService(POWER_SERVICE);
        pWakeLock=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BatteryWaster");
        pWakeLock.setReferenceCounted(false);

        if(upinfo!=null){
            //防止内容过多，让滚动条到底部
            final ScrollView scrollv=(ScrollView)this.findViewById(R.id.scroll_download_auto);
            scrollv.post(new Runnable() {
                @Override
                public void run() {
                    scrollv.scrollTo(0, 150);
                }
            });
            Resources res = getResources();
            String versionInfo = res.getString(R.string.download_dialog_info1,upinfo.getNew_Version().trim());
            versionInfo=versionInfo+"\n\n"+this.getString(R.string.show_network_cost);

            String packInfo="";
            if(upinfo.getSize().trim().indexOf("B")>-1){
                packInfo= res.getString(R.string.update_dialog_sys_info,upinfo.getSize().trim(),upinfo.getDescription().trim());
            }
            else{
                double dSize=Double.parseDouble(upinfo.getSize().trim());
                double packsize=dSize/1024/1024;
                String strSize=String.valueOf(packsize);
                if(strSize.indexOf(".")>-1) strSize=strSize.substring(0, strSize.indexOf(".")+3);
                packInfo= res.getString(R.string.update_dialog_sys_info,strSize+" M",upinfo.getDescription().trim());
            }

            versionInfo=versionInfo+"\n\n"+packInfo+"\n"+this.getString(R.string.download_dialog_info2);
            tv_download_hint_auto.setText(versionInfo);

        }
        else{
            Intent itt = new Intent(Intent.ACTION_RUN);
            itt.setComponent(new ComponentName("com.android.ota",
                    "com.android.ota.autoupdate.BootService"));
            ActivityUtils.setNextNotify(getApplicationContext(),itt,true,ActivityUtils.quency_days);
            this.finish();
        }
        //add BUG_ID:QYLE-578 liangyulong 20120530(start)
        btn_update_auto=(Button)this.findViewById(R.id.btn_update_auto);
        btn_update_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(r1.isChecked()){//如果是使用wifi下载，判断wifi是否连接上
                    //判断当前连接的是wifi，还是mobile;
                    if(ActivityUtils.CheckNetworkState(AutoUpdate.this).equals("mobile")){
                        showUnableNetworkTips(R.string.setnetwork_open);
                    }
                    else{
                        //开始下载系统，更新
                        downloadDataIntent();
                    }
                }
                else if(r2.isChecked()){//如果使用移动网络或者wifi下载
                    //开始下载系统，更新
                    downloadDataIntent();
                }
                else if(r3.isChecked()){//开始计时，一小时后下载

                    //zhaochunqing add 下载前保证存储方式是外部
                    //if("1".equals(SystemProperties.get("persist.sys.emmcsdcard.enabled"))){
                    //Toast.makeText(AutoUpdate.this, R.string.use_external_storage_mode,1).show();
                    //}
                    //else{
                    //zhaochunqing add

                    //发送消息到通知栏,并开始计时
                    Intent intt = new Intent(AutoUpdate.this, AnHourLaterNotificationService.class);
                    intt.putExtra("downloadUrl",upinfo.getDownloadURL() );
                    intt.putExtra("downloadByte", upinfo.getDownloadByte());
                    intt.putExtra("packageType", upinfo.getPackageType());
                    startService(intt);
                    AutoUpdate.this.finish();
                    //}//zhaochunqing add
                }
                SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd");
                ActivityUtils.setParameter(AutoUpdate.this, "yulong_autoupdate_time",simdate.format(new Date()));
                //重新设置新闹钟，3天后提醒下载
                // add to BootService,so delete
				/*
				Intent itt = new Intent(Intent.ACTION_RUN);
	       	  	        itt.setAction("com.android.BootService");  
	       	                ActivityUtils.setNextNotify(getApplicationContext(),itt,true,ActivityUtils.quency_days);
				*/
            }
        });
        btn_cancel_auto=(Button)this.findViewById(R.id.btn_cancel_auto);
        btn_cancel_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd");
                ActivityUtils.setParameter(AutoUpdate.this, "yulong_autoupdate_time",simdate.format(new Date()));

                //重新设置新闹钟，3天后提醒下载
                // add to BootService,so delete
		/*
		  Intent itt = new Intent(Intent.ACTION_RUN);
	       	  itt.setAction("com.android.BootService");  
	       	  ActivityUtils.setNextNotify(getApplicationContext(),itt,true,ActivityUtils.quency_days);
               */
                AutoUpdate.this.finish();
            }
        });

        //add BUG_ID:QYLE-578 liangyulong 20120530(end)
    }

    private void downloadDataIntent(){
        boolean isSuc=true;
        if(residue_cable<=20){
            Toast.makeText(getApplicationContext(),R.string.power_is_too_low, 1).show();
            isSuc=false;
        }
        else if(!ActivityUtils.isExistSdcard()){
            Toast.makeText(getApplicationContext(),R.string.sdcard_not_exist, 1).show();
            isSuc=false;
        }
        else if(ActivityUtils.isExistSdcard()){
            long lo=ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
            double size=lo/1024/1024;
            if(size<300){
                Toast.makeText(getApplicationContext(),R.string.sdcard_space_not_enough, 1).show();
                isSuc=false;
            }
        }
        if(!isSuc){this.finish();return ;}

        String fiName=upinfo.getDownloadURL();
        fiName=fiName.substring(fiName.lastIndexOf("/")+1, fiName.length());
        fiName=ActivityUtils.getSdcardPath()+fiName;

        File file = new File(fiName);//文件保存目录
        try{
            if(file.exists()){
                ActivityUtils.verifyPackage(file, null,TAG);
                file.delete();
            }
        }catch (IOException e) {
            Log.i(TAG, e.toString());
            //e.printStackTrace();
        } catch (GeneralSecurityException e) {
            Log.i(TAG, e.toString());
            //e.printStackTrace();
        }
        catch(Exception e){
            Log.i(TAG, e.toString());
        }
        finally{
            Intent intent = new Intent(this, NotificationDownloadService.class);
            intent.putExtra("downloadUrl",upinfo.getDownloadURL() );
            intent.putExtra("downloadByte", upinfo.getDownloadByte());
            intent.putExtra("packageType", upinfo.getPackageType());
            startService(intent);
            this.finish();
        }
        //停止一小时对话框
        //stopService(new Intent(this, AnHourLaterNotificationService.class));


    }
    @Override
    protected void onResume() {
        registerReceiver(mReceiver, iFilter);
        pWakeLock.acquire();
        super.onResume();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showUnableNetworkTips(int id)    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.unable_netstate);
        builder.setMessage(id);
        builder.setPositiveButton(R.string.btn_settings_val1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 如果没有网络连接，则进入网络设置界面
                        //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        startActivity(new Intent("android.settings.SETTINGS")); //QELS-4450 liangyulong modify change to settings
                    }
                });
        builder.setNegativeButton(R.string.btn_cancel_val, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }

    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(Intent.ACTION_BATTERY_CHANGED.equals(action)){
                residue_cable=intent.getIntExtra("level",0);
                unregisterReceiver(mReceiver);
                pWakeLock.release();
            }
        }
    };
}
