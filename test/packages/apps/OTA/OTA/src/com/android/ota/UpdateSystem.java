package com.android.ota;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

import com.android.internal.telephony.ITelephony;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//A: kuangyunfeng 20160219(start)
import android.Manifest;
import android.content.pm.PackageManager;
//A: kuangyunfeng 20160219(end)

import android.os.SystemProperties;//zhaochunqing add 20120903
/***
 *
 * 1. yulong.liang@ragentek 20120530 BUG_ID:QYLE-578
 *  Description: click cancel button,Exception occurred 
 * 2.  yulong.liang@ragentek 20120606 BUG_ID:QYLE-756
 *  Description: During a system update,2 hits click return key,show exception
 * 3. yulong.liang@ragentek.com 20120711 BUG_ID:QYLE-2297
 *  Description: Click cancel,can be delayed three times
 *  5. yulong.liang@ragentek.com 20120718 BUG_ID:QYLE-2700
 *  Description: An hour after selecting download,Click on the update again,an hour,the icon's not gone
 *  6. yulong.liang@ragentek.com 20120719 BUG_ID:QYLE-2713
 *  Description: Main menu to access the updated system,Click the Home Button,Enter again,interface to turn gray 
 *  7. yulong.liang@ragentek.com 20121217 BUG_ID:QELS-2699
 * Description: Modify matching OTA parameters
 * 8. lu.wang@ragentek.com QELS-4235 20130118 
 * Description: modfiy OTA Software to internal version
 */


public class UpdateSystem extends Activity {
    private static String TAG="UpdateSystem";
    private  ProgressDialog dialog=null;
    private IntentFilter iFilter;
    private PowerManager.WakeLock pWakeLock;
    private TextView tv_system_info;
    //add BUG_ID:QYLE-578 liangyulong 20120530(start)
    private Button btn_update,btn_cancel,btn_setting,btn_about;
    //add BUG_ID:QYLE-578 liangyulong 20120530(end)
    private int residue_cable;
    private RadioButton rb_mobile_wifi_download,rb_wifi_download,rb_anhourlater_download;
    private Thread initCheckedThread=null;
    private final int CHECKED_FAIL=1;
    private final int CHECKED_FAIL_NETWORK=2;
    private final int CHECKED_SUCCEED=3;
    private final int ota_server_not_available=4;
    private final int phone_software_version_uptodate=5;
    private final int CHECKED_FAIL_EXTERNAL_STORAGE=6;//zhaochunqing add 20120903
    private boolean debug = false;

    HttpClient httpClient;
    //private String servicePath="http://www.coolpadfuns.com:8080/updsvr/ota/checkupdate?hw=7018&hwv=V1.10&swv=2.3.001.120501.7018&serialno=864670010001838";

    private String servicePath="";
    private String updateSys_State=null;
    private String packInfoANDversionInfo="";

    PendingIntent  pi;

    //A: kuangyunfeng 20160401(start)
    private static final int REQUEST_CODE = 2;
    private static final int RESULT_OK = 3;
    private static final int RESULT_CANCEL = 4;

    private boolean isRegist = false;
    //A: kuangyunfeng 20160401(end)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //A: kuangyunfeng 20160401(start)
        int hasReadPhoneStatePermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        int hasWriteStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        boolean canReadPhoneState = hasReadPhoneStatePermission == PackageManager.PERMISSION_GRANTED;
        boolean canWriteStorage = hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED;

        Log.i("kayun", "Permission canReadPhoneState = " + canReadPhoneState + ", canWriteStorage = " + canWriteStorage);

        if (!canReadPhoneState || !canWriteStorage) {
            Log.i("kayun", "Do not have the permission.");
            //Intent permissionIntent = new Intent(this, PermissionActivity.class);
            Intent permissionIntent = new Intent();
            permissionIntent.setClassName("com.android.ota", "com.android.ota.PermissionActivity");
            startActivityForResult(permissionIntent,REQUEST_CODE);
        } else {
            Log.i("kayun", "Have the permission READ_PHONE_STATE.");
            initSys();
        }
        //A: kuangyunfeng 20160401(end)

        // ActivityUtils.setParameter(this, "download_state", "no"); //恢复未下载状态
    }

    //A: kuangyunfeng 20160401(start)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("kayun", "UpdateSystem - onActivityResult");
        if (requestCode == REQUEST_CODE) {
            Log.d("kayun", "requestCode = " + requestCode + ", resultCode = " + resultCode);
            if(resultCode == RESULT_OK){
                initSys();
            } else if (resultCode == RESULT_CANCEL) {
                finish();
            }
        }
    }
    //A: kuangyunfeng 20160401(end)

    private void initSys(){
        packInfoANDversionInfo="";
        tv_system_info=(TextView)this.findViewById(R.id.tv_system_info);
        tv_system_info.setText(getSerialNumberInformation());

        iFilter=new IntentFilter();//注册监听
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        PowerManager pm=(PowerManager)this.getSystemService(POWER_SERVICE);
        pWakeLock=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BatteryWaster");
        pWakeLock.setReferenceCounted(false);


        httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 15000);//请求超时设置
        httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 15000);//连接超时设置

        //add BUG_ID:QYLE-578 liangyulong 20120530(start)
        btn_update=(Button)this.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                checkUpdateSys();
            }
        });
        btn_cancel=(Button)this.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                isCancelDig=true;
                UpdateSystem.this.finish();


            }
        });
        //add BUG_ID:QYLE-578 liangyulong 20120530(end)
        btn_setting=(Button)this.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkSystemRate();
            }
        });
        btn_about=(Button)this.findViewById(R.id.btn_about);
        btn_about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                About();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //检查更新
        menu.add(0, 1, 1, R.string.btn_update_val).setIcon(R.drawable.check_now);
        menu.add(0, 2, 2, R.string.btn_settings_val2).setIcon(android.R.drawable.ic_menu_preferences);
        menu.add(0, 3, 3, R.string.btn_about_val3).setIcon(android.R.drawable.ic_menu_info_details);
        int inState=android.provider.Settings.System.getInt(UpdateSystem.this.getContentResolver(), "ota_server_url",0);
        if(inState==1&&servicePath.indexOf("ragentek")>-1){
            menu.add(0, 4, 4, R.string.local_update).setIcon(android.R.drawable.ic_menu_info_details);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                checkUpdateSys();//检查更新
                break;
            case 2:
                checkSystemRate();//更新检查频率
                break;
            case 3:
                About();//about
                break;
            case 4:
                //local updates
                int inState=android.provider.Settings.System.getInt(UpdateSystem.this.getContentResolver(), "ota_server_url",0);
                if(inState==1){
                    File file = new File("/sdcard/update.zip");
                    if(file.exists()){
                        Intent intent = new Intent(UpdateSystem.this,MtkUpgradeRestart.class);
                        intent.putExtra("filePath","/sdcard/update.zip");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, R.string.notexists_package_local, 1).show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //检查更新
    private void checkUpdateSys(){
        //判断当前是否已经在下载
        Log.d(TAG, "download_state=="+ActivityUtils.getParameter(this, "download_state"));
        if(ActivityUtils.getParameter(this, "download_state").equals("yes")){
            Toast.makeText(this, R.string.please_try_again_later, 1).show();
            return ;
        }
        // add BUG_ID:QYLE-2297 20120711 yulong.liang start
        String delaycount=ActivityUtils.getParameter(this, "delaytime_count");
        Log.d(TAG, "delaycount=="+delaycount+",residue_cable="+residue_cable);
        if(delaycount.equals("1")||delaycount.equals("2")||delaycount.equals("3")){
            if(residue_cable<=20){
                showTextToast(R.string.power_is_too_low);
                return ;
            }
            else{
                Intent intentdelay=new Intent(this,DelayUpdateDialog.class);
                startActivity(intentdelay);
                return ;
            }
        }
        // add BUG_ID:QYLE-2297 20120711 yulong.liang end
        showDialog(0);
        if(initCheckedThread==null){
            initCheckedThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    if(!ActivityUtils.haveInternet(UpdateSystem.this)){//网络是否通畅
                        Message msg= new Message();
                        msg.what=CHECKED_FAIL_NETWORK;
                        msg.getData().putInt("CHECKED_FAIL_MSG",R.string.wap_network_fail);
                        mHandlerinitChecked.sendMessage(msg);

                    }
                    else{

                        //请求服务器，获取更新信息
                        Message msg= new Message();
                        int resut = getServiceData();
                        Log.d(TAG,"---------------------PPP");
                        switch (resut) {//获取服务器端信息
                            case 0:
                                msg.what=CHECKED_SUCCEED;
                                break;
                            case R.string.ota_server_not_available:
                                msg.what=ota_server_not_available;
                                break;
                            case R.string.phone_software_version_uptodate:
                                msg.what=phone_software_version_uptodate;
                                break;
                            case 1:
                                msg.what=ota_server_not_available;
                                break;
                        }
                        mHandlerinitChecked.sendMessage(msg);
                    }
                }
            });

            initCheckedThread.start();
        }
    }
    //更新检查频率
    private void checkSystemRate() {
        Intent intent =new Intent(UpdateSystem.this,com.android.ota.Settings.class);
        startActivity(intent);
    	/*String strChecked= ActivityUtils.getParameter(UpdateSystem.this, "yulong_cota_autoupdate");
    	int imChecked=0;
    	if(strChecked.equals("")||strChecked.equals("0")){
    		imChecked=0;
    	}
    	else if(strChecked.equals("1")){
    		imChecked=1;
    	}
    	new AlertDialog.Builder(this).setTitle(R.string.update_check_Rate).setSingleChoiceItems(new String[]{this.getResources().getString(R.string.update_check_Rate_never),this.getResources().getString(R.string.update_check_Rate_three)},imChecked,
	                new DialogInterface.OnClickListener(){
	                        public void onClick(DialogInterface dialog, int which) {
	                        switch(which){
	                        case 0:
	                               dialog.dismiss();
	                               ActivityUtils.setParameter(UpdateSystem.this, "yulong_cota_autoupdate", "0");//不自动更新
	                              // ActivityUtils.setParameter(UpdateSystem.this, "yulong_autoupdate_date","");
	                               //取消3天提醒下载	                             
	                             Intent itt1 = new Intent(Intent.ACTION_RUN);
	         		     itt1.setAction("com.android.BootService");  
	                             ActivityUtils.setNextNotify(UpdateSystem.this,itt1,false,ActivityUtils.quency_days);
	                               break;
	                        case 1:
	                        	   dialog.dismiss();
	                        	   ActivityUtils.setParameter(UpdateSystem.this, "yulong_cota_autoupdate", "1");//自动更新
	                        	   //保存当前日期
	                        	  // SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd");
	                        	 //  ActivityUtils.setParameter(UpdateSystem.this, "yulong_autoupdate_time",simdate.format(new Date()));
	                        	 
	                        	   //开始计时，3天后提醒下载
	                              Intent itt2 = new Intent(Intent.ACTION_RUN);
	         		       itt2.setAction("com.android.BootService");  
	         		      ActivityUtils.setNextNotify(UpdateSystem.this,itt2,true,ActivityUtils.quency_days);
	                        	  // ActivityUtils.setParameter(UpdateSystem.this, "yulong_autoupdate_time","2012-5-9");
	                        	 break;
	                       
	                        }
	      }}).setNegativeButton(R.string.btn_cancel_val,null).show();
		  */

    }

    //about
    private void About(){
        Resources res = getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.OTA_about_title);
        builder.setPositiveButton(R.string.btn_ok_val, null);
        builder.setMessage(R.string.about_dialog_info);
        builder.show();
    }

    //加载进度条
    private Handler mHandlerinitChecked = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int resultFlag = msg.getData().getInt("CHECKED_FAIL_MSG");
            if(initCheckedThread!=null){
                if (!initCheckedThread.isInterrupted()) {
                    Log.d(TAG, "initCheckedThread==isInterrupted");
                    initCheckedThread.interrupt();
                }
                initCheckedThread = null;
            }
            Log.d(TAG, "mHandlerinitChecked=="+msg.what);
            switch(msg.what){
                case CHECKED_FAIL:
                    dialog.dismiss();
                    showTextToast(resultFlag);
                    break;
                case CHECKED_FAIL_NETWORK:
                    dialog.dismiss();
                    showUnableNetworkTips(R.string.setnetwork);
                    break;
                case ota_server_not_available:
                    dialog.dismiss();
                    showErrorMsg(R.string.update_sys_title,R.string.ota_server_not_available);
                    break;
                case phone_software_version_uptodate:
                    dialog.dismiss();
                    showGetHttpMsg(R.string.phone_software_version_uptodate,null);
                    break;
                case CHECKED_SUCCEED:
                    dialog.dismiss();
                    // add BUG_ID:QYLE-2297 20120711 yulong.liang start
                    if(upinfo.getPriority().equals("Optional")){
                        //普通更新包
                        ActivityUtils.setParameter(UpdateSystem.this, "ota_priority", upinfo.getPriority());
                        showGetHttpMsg(0,packInfoANDversionInfo);//进行提示
                    }
                    else{//高级优先包
                        ActivityUtils.setParameter(UpdateSystem.this, "ota_priority", upinfo.getPriority());
                        // delete BUG_ID:DELL-1040 20120615 yulong.liang start
                        // Toast.makeText(UpdateSystem.this,R.string.ota_update_priority_Mandatory, 1).show();
                        // delete BUG_ID:DELL-1040 20120615 yulong.liang end
                        // add BUG_ID:DELL-1040 20120615 yulong.liang start
                        boolean isSucb=true;
                        if(residue_cable<=20){
                            showTextToast(R.string.power_is_too_low);
                            isSucb=false;
                        }
                        else if(!ActivityUtils.isExistSdcard()){
                            showTextToast(R.string.sdcard_not_exist);
                            isSucb=false;
                        }
                        else if(ActivityUtils.isExistSdcard()){
                            long lo=ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
                            double size=lo/1024/1024;
                            if(size<300){
                                showTextToast(R.string.sdcard_space_not_enough);
                                isSucb=false;
                            }
                        }
                        if(isSucb){
                            downloadDataIntent();
                        }
                        // add BUG_ID:DELL-1040 20120615 yulong.liang end
                    }
                    //add BUG_ID:QYLE-2297 20120711 yulong.liang end
                    break;


            }


        }

    };

    @Override
    protected void onResume() {
        Log.d("kayun", "UpdateSystem - onResume");
        isCancelDig = false;
        //M: kuangyunfeng 20160401(start)
        if(iFilter == null){
            iFilter=new IntentFilter();//注册监听
            iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        }
        if(pWakeLock == null){
            PowerManager pm=(PowerManager)this.getSystemService(POWER_SERVICE);
            pWakeLock=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BatteryWaster");
            pWakeLock.setReferenceCounted(false);
        }
        isRegist = true;
        //M: kuangyunfeng 20160401(end)
        registerReceiver(mReceiver, iFilter);
        pWakeLock.acquire();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        if(initCheckedThread!=null){
            initCheckedThread.interrupt();
            initCheckedThread=null;
        }
        super.onDestroy();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        dialog=new ProgressDialog(this);
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setTitle(getResources().getString(R.string.progress_update));
        dialog.setMessage(getResources().getString(R.string.progress_update));
        dialog.setIndeterminate(false);
        dialog.setCancelable(false); //Update QYLE-756 20120606 fix bug:upate state is false
        return dialog;
    }
    //获取服务器端更新信息
    private int getServiceData(){
        Log.d(TAG, "getServiceData()");
        int in_success=0;
        HttpGet hget;
        try
        {
            hget = new HttpGet(servicePath);
            StringBuffer response=new StringBuffer();
            HttpResponse httpResponse = null;
            // 发送GET请求
            try {

                httpResponse = httpClient.execute(hget);
            } catch (Exception e) {
                Log.e(TAG, "Error-HttpResonse:"+e.toString());
                return R.string.ota_server_not_available;
            }
            Log.d(TAG, "getServiceData()--code--11111111111");
            if(httpResponse.getStatusLine().getStatusCode()==200){
                Log.d(TAG, "getServiceData()--code--200");
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null)
                {

                    // 读取服务器响应
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(entity.getContent()));
                    String line = null;
                    while ((line = br.readLine()) != null)
                    {
                        // 使用response文本框显示服务器响应
                        response.append(line + "\n");
                    }
                    String str=response.toString();
                    updateSys_State=str.substring(str.indexOf("CHECK_UPDATE_RESULT")+20, str.lastIndexOf("CHECK_UPDATE_RESULT")+21);
/** delete for Sales ,liangyulong 20130813
 String register=str.substring(str.indexOf("CHECK_MARKET_RESULT")+20, str.lastIndexOf("CHECK_MARKET_RESULT")+21);
 if(servicePath.indexOf("ragentek")>-1){
 ActivityUtils.registerCellPhone(register);
 }
 **/
                    if(updateSys_State.equals("0")){//有更新包
                        //进行xml解析
                        String strXml=str.substring(str.indexOf("<?xml"),str.length());
                        if(debug){
                            Log.d(TAG, "value xml=="+strXml);
                        }
                        InputStream inputStream =new ByteArrayInputStream(strXml.getBytes());
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory
                                    .newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(inputStream);
                            // 获取根节点
                            Element root = document.getDocumentElement();
                            parse(root);
                            //解析完毕后，弹出更新对话框
                            Resources res = getResources();
                            String versionInfo = res.getString(R.string.update_dialog_info,upinfo.getNew_Version().trim());
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
                            packInfoANDversionInfo=versionInfo+"\n\n"+packInfo;
                            in_success=0;
                            //showGetHttpMsg(0,versionInfo+"\n\n"+packInfo);//进行提示
                        }
                        catch(Exception e){
                            in_success=1;
                            Log.e(TAG, e.toString());
                        }
                    }
                    else{
                        //showGetHttpMsg(R.string.phone_software_version_uptodate,null);
                        return R.string.phone_software_version_uptodate;
                    }
                }
            }
            else{
                //showErrorMsg(R.string.update_sys_title,R.string.ota_server_not_available);
                return R.string.ota_server_not_available;
            }
            return in_success;
        }
        catch (Exception e)
        {
            //showErrorMsg(R.string.update_sys_title,R.string.ota_server_not_available);
            Log.e(TAG, "Error:"+e.toString());
            e.printStackTrace();
        }
        return R.string.ota_server_not_available;
    }
    private void showDownloadTips(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.download_update_sys_title);
        //builder.setMessage(R.string.setnetwork);
        final LinearLayout tabview = (LinearLayout)getLayoutInflater()
                .inflate( R.layout.download, null);
        //防止内容过多，让滚动条到底部
        final ScrollView scrollv=(ScrollView)tabview.findViewById(R.id.scroll_download);
        scrollv.post(new Runnable() {
            @Override
            public void run() {
                scrollv.scrollTo(0, 150);

            }
        });

        TextView t_hint=(TextView)tabview.findViewById(R.id.tv_download_hint);
        Resources res = getResources();
        String versionInfo = res.getString(R.string.download_dialog_info1,upinfo.getNew_Version().trim());
        versionInfo=versionInfo+"\n\n"+UpdateSystem.this.getString(R.string.show_network_cost);
        //把文件大小更新为‘兆’
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
        versionInfo=versionInfo+"\n\n"+packInfo+"\n"+UpdateSystem.this.getString(R.string.download_dialog_info2);
        t_hint.setText(versionInfo);

        // 设置对话框显示的View对象
        builder.setView(tabview);
        builder.setPositiveButton(R.string.btn_ok_val,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rb_mobile_wifi_download=(RadioButton)tabview.findViewById(R.id.rb_mobile_wifi_download);
                        rb_wifi_download=(RadioButton)tabview.findViewById(R.id.rb_wifi_download);
                        rb_anhourlater_download=(RadioButton)tabview.findViewById(R.id.rb_anhourlater_download);
                        if(rb_wifi_download.isChecked()){//如果是使用wifi下载，判断wifi是否连接上
                            //判断当前连接的是wifi，还是mobile;
                            if(ActivityUtils.CheckNetworkState(UpdateSystem.this).equals("mobile")){
                                showUnableNetworkTips(R.string.setnetwork_open);
                            }
                            else{
                                dialog.cancel();
                                //开始下载系统，更新
                                downloadDataIntent();
                            }
                        }
                        else if(rb_mobile_wifi_download.isChecked()){//如果使用移动网络或者wifi下载
                            dialog.cancel();
                            //开始下载系统，更新
                            downloadDataIntent();
                        }
                        else if(rb_anhourlater_download.isChecked()){//开始计时，一小时后下载

                            if(debug)Log.d(TAG, "downloadByte="+upinfo.getDownloadByte());
                            //发送消息到通知栏,并开始计时
                            Intent intt = new Intent(UpdateSystem.this, AnHourLaterNotificationService.class);
                            intt.putExtra("downloadByte", upinfo.getDownloadByte());
                            intt.putExtra("downloadUrl",upinfo.getDownloadURL() );
                            intt.putExtra("packageType", upinfo.getPackageType());
                            startService(intt);
                        }

                    }
                });
        builder.setNegativeButton(R.string.btn_cancel_val, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UpdateSystem.this, R.string.update_cancel, 1).show();
                dialog.cancel();
            }
        });
        //add BUG_ID:QYLE-2713 liangyulong 20120719(start)
        if(isCancelDig==false){
            builder.create();
            builder.show();
        }
        //add BUG_ID:QYLE-2713 liangyulong 20120719(end)
    }
    private void downloadDataIntent(){
        String fiName=upinfo.getDownloadURL();
        fiName=fiName.substring(fiName.lastIndexOf("/")+1, fiName.length());
        fiName=ActivityUtils.getSdcardPath()+fiName;

        File file = new File(fiName);//文件保存目录
        try{
            if(file.exists()){
                if(upinfo.getPackageType().equals("Complete")){
                    if(debug)Log.d(TAG, "getPackageType=="+upinfo.getPackageType());
                    ActivityUtils.verifyCompletePackage(file, null,TAG);
                }
                else{
                    if(debug)Log.d(TAG, "getPackageType=="+upinfo.getPackageType());
                    ActivityUtils.verifyPackage(file, null,TAG);
                }

                file.delete();
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            //e.printStackTrace();
        } catch (GeneralSecurityException e) {
            Log.e(TAG, e.toString());
            //e.printStackTrace();
        }
        catch(Exception e){
            Log.e(TAG, e.toString());
        }
        finally{
            // add BUG_ID:QYLE-2700 20120718 yulong.liang start
            Intent inte = new Intent();
            inte.setAction("android.intent.action.AnHourDOWNLOAD_DeleteIntent");
            sendBroadcast(inte);//删除通知栏通知
            // add BUG_ID:QYLE-2700 20120718 yulong.liang end

            Intent intent = new Intent(UpdateSystem.this, NotificationDownloadService.class);
            intent.putExtra("downloadUrl",upinfo.getDownloadURL() );
            intent.putExtra("downloadByte", upinfo.getDownloadByte());
            intent.putExtra("packageType", upinfo.getPackageType());
            startService(intent);

            Toast.makeText(this, R.string.download_state_point, 1).show();

            try {
                Thread.sleep(1000);
                this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        //停止一小时对话框
        //stopService(new Intent(this, AnHourLaterNotificationService.class));


    }

    //弹出更新的对话框
    private void showGetHttpMsg(int msg,String msg2)    {
        //保存此次更新的时间
        SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ActivityUtils.setParameter(this, "update_date", simdate.format(new Date()));

        AlertDialog.Builder builderUpdateDialog = new AlertDialog.Builder(UpdateSystem.this);
        builderUpdateDialog.setIcon(android.R.drawable.ic_dialog_alert);
        builderUpdateDialog.setTitle(R.string.update_sys_title);
        if(msg!=R.string.phone_software_version_uptodate){//表示需要更新
            builderUpdateDialog.setMessage(msg2);
            builderUpdateDialog.setPositiveButton(R.string.now_update_btn,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {    						boolean isSuc=true;
                            tv_system_info.setText(getSerialNumberInformation()); //改变当前更新时间
                            if(residue_cable<=20){
                                showTextToast(R.string.power_is_too_low);
                                isSuc=false;
                            }
                            else if(!ActivityUtils.isExistSdcard()){
                                showTextToast(R.string.sdcard_not_exist);
                                isSuc=false;
                            }
                            else if(ActivityUtils.isExistSdcard()){
                                long lo=ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
                                double size=lo/1024/1024;
                                if(size<300){
                                    showTextToast(R.string.sdcard_space_not_enough);
                                    isSuc=false;
                                }
                            }

                            if(isSuc)showDownloadTips();//开始下载
                            else dialog.cancel();




                        }
                    });

            builderUpdateDialog.setNegativeButton(R.string.btn_cancel_val, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    dialog1.cancel();
                    tv_system_info.setText(getSerialNumberInformation());//改变当前更新时间
                }
            });
        }
        else{
            builderUpdateDialog.setMessage(msg);
            builderUpdateDialog.setNegativeButton(R.string.btn_ok_val, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    tv_system_info.setText(getSerialNumberInformation());//改变当前更新时间
                }
            });
        }
        //add BUG_ID:QYLE-2713 liangyulong 20120719(start)
        if(builderUpdateDialog!=null&&isCancelDig==false){
            builderUpdateDialog.create();
            builderUpdateDialog.show();
        }
        //add BUG_ID:QYLE-2713 liangyulong 20120719(end)
    }
    private void showErrorMsg(int title,int id)    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(title);
        builder.setMessage(id);
        builder.setNegativeButton(R.string.btn_ok_val, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //add BUG_ID:QYLE-2713 liangyulong 20120719(start)
        if(isCancelDig==false){
            builder.create();
            builder.show();
        }
        //add BUG_ID:QYLE-2713 liangyulong 20120719(end)
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
                        startActivity(new Intent("android.settings.SETTINGS")); //zhaochunqing modify change to settings
                    }
                });
        builder.setNegativeButton(R.string.btn_cancel_val, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        //add BUG_ID:QYLE-2713 liangyulong 20120719(start)
        if(isCancelDig==false){
            builder.create();
            builder.show();
        }
        //add BUG_ID:QYLE-2713 liangyulong 20120719(start)
    }

    /**
     * 获取一些软件版本信息
     *
     * @return
     */
    private String getSerialNumberInformation() {
        servicePath=ActivityUtils.getServerUrl(getApplicationContext());
        String ModelNumber=Build.MODEL;
        //modify BUG_ID:QELS-2699 liangyulong 20121217(start)
        boolean bolNum =getResources().getBoolean(R.bool.config_modelnumber_permit_null);
        if(bolNum==false){
            ModelNumber=ModelNumber.substring(ModelNumber.indexOf(" ")+1, ModelNumber.length());
            ModelNumber=ModelNumber.replaceAll(" ", "");
        }
        //modify BUG_ID:QELS-2699 liangyulong 20121217(end)
        String HardwareV=ActivityUtils.getHardwareVersion();
        String IMEI=ActivityUtils.getPhoneIMEI(this);
        String Lastchecktime= ActivityUtils.getParameter(this, "update_date");
        if(Lastchecktime.equals("")){Lastchecktime=this.getResources().getString(R.string.not_check_update);}
        int inState=android.provider.Settings.System.getInt(UpdateSystem.this.getContentResolver(), "ota_server_url",0);
	/*String sSVersion=Build.DISPLAY;			
	if(inState==1&&servicePath.indexOf("ragentek")>-1){
		sSVersion=ActivityUtils.getInternalVersion();
	}*/
        Resources res = getResources();
        String versionInfo = res.getString(R.string.serialnumberinformation,
                ModelNumber,
                HardwareV,
    			 Build.DISPLAY, //sSVersion,
                IMEI,
                Lastchecktime);
        ModelNumber=ModelNumber.replaceAll(" ", "%20");
        //modify QELS-4235 wanglu 20130118 start
        if(servicePath.indexOf("ragentek")>-1){
            servicePath+="hw="+ModelNumber+"&hwv=P2&swv="+ActivityUtils.getInternalVersion()+"&serialno="+IMEI+"&flag=1&version=V1.0&beta="+ActivityUtils.getRegisterParam(UpdateSystem.this);
        }
        else{
            servicePath+="hw="+ModelNumber+"&hwv=P2&swv="+Build.DISPLAY+"&serialno="+IMEI;
        }
        //modify QELS-4235 wanglu 20130118 end
        // Toast.makeText(this, servicePath, 1).show();
        if(debug) Log.d(TAG, servicePath);
        return versionInfo;
    }


    //电量信息
    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(Intent.ACTION_BATTERY_CHANGED.equals(action)){
                residue_cable=intent.getIntExtra("level",0);
                //M: kuangyunfeng 20160401(start)
                //unregisterReceiver(mReceiver);
                if(isRegist){
                    unregisterReceiver(mReceiver);
                }
                //M: kuangyunfeng 20160401(end)
                pWakeLock.release();
            }
        }
    };



    /**
     *
     * @param element 将要进行遍历的节点，服务器端获取的xml
     */
    UpdateInfoEntity upinfo=new UpdateInfoEntity();
    private void parse(Element element) {
        NodeList nodelist = element.getChildNodes();
        int size = nodelist.getLength();
        for (int i = 0; i < size; i++) {
            // 获取特定位置的node
            Node element2 = (Node) nodelist.item(i);
				/* getNodeName获取tagName，例如<book>thinking in android</book>这个Element的getNodeName返回book
				 * getNodeType返回当前节点的确切类型，如Element、Attr、Text等
				 * getNodeValue 返回节点内容，如果当前为Text节点，则返回文本内容；否则会返回null
				 * getTextContent 返回当前节点以及其子代节点的文本字符串，这些字符串会拼成一个字符串给用户返回。例如
				 * 对<book><name>thinking in android</name><price>12.23</price></book>调用此方法，则会返回“thinking in android12.23”
				 */
            String tagName = element2.getNodeName();
            if (tagName.equals("srcVersion")){
                upinfo.setOld_Version(element2.getTextContent());
            }
				if (tagName.equals("dstShowVersion")){		
                upinfo.setNew_Version(element2.getTextContent());
            }
            if (tagName.equals("description")){
                String strDes=element2.getTextContent();
                if((strDes.indexOf("]]")>-1)&&(strDes.indexOf("[")>-1)){
                    strDes=strDes.substring(strDes.lastIndexOf("[")+1,strDes.indexOf("]"));
                }
                upinfo.setDescription(strDes);
            }
            if (tagName.equals("downloadURL")){
                //modify BUG_ID:QELS-2699 liangyulong 20121217(start)
                String durl=element2.getTextContent().replaceAll(" ", "%20");
                upinfo.setDownloadURL(durl);
                //modify BUG_ID:QELS-2699 liangyulong 20121217(end)

            }
            if (tagName.equals("size")){
                upinfo.setSize(element2.getTextContent());
            }
            if (tagName.equals("priority")){
                upinfo.setPriority(element2.getTextContent());
            }
            if (tagName.equals("downloadByte")){
                upinfo.setDownloadByte(Integer.parseInt(element2.getTextContent()));
            }
            if (tagName.equals("packageType")){
                upinfo.setPackageType(element2.getTextContent().trim());
            }
            if (tagName.equals("sessionId")){
                upinfo.setSessionId(element2.getTextContent());
            }
        }
    }
    //add BUG_ID:QYLE-2713 liangyulong 20120719(start)
    private boolean isCancelDig=false;
    @Override
    protected void onPause() {
        if(dialog!=null){
            Log.d(TAG, "onPause()--dialog");
            isCancelDig=true;
            dialog.dismiss();
            dialog.cancel();
            //this.finish();
        }

        super.onPause();
    }
    private Toast toast = null;
    private void showTextToast(int text) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
		/*
		@Override
		protected void onRestart() {
			Log.d(TAG, "onRestart()--builderUpdateDialog");
			if(builderUpdateDialog!=null){
				Log.d(TAG, "onRestart()--builderUpdateDialog-11111111111");
				//this.finish();
			}
			super.onRestart();
		}*/
    //add BUG_ID:QYLE-2713 liangyulong 20120719(end)
}
