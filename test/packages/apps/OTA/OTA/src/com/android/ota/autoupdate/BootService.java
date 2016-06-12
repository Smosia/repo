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
import com.android.ota.NotificationDownloadService;
import com.android.ota.R;
import com.android.ota.UpdateInfoEntity;
import com.android.ota.UpdateSystem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import android.os.Bundle;

//A: kuangyunfeng 20160401(start)
import android.Manifest;
import android.content.pm.PackageManager;
//A: kuangyunfeng 20160401(end)

/***
 *
 * 1. yulong.liang@ragentek.com 20120711 BUG_ID:QYLE-2297
 *  Description: Click cancel,can be delayed three times
 * 2. yulong.liang@ragentek.com 20121217 BUG_ID:QELS-2699
 * Description: Modify matching OTA parameters
 */
public class BootService extends Service {

    private String TAG="BootService";
    private Thread bootThread=null;


    private IntentFilter iFilter;
//    private PowerManager.WakeLock pWakeLock;
    private int residue_cable;

    HttpClient httpClient;
    private String updateSys_State=null;
    // private String servicePath="http://www.coolpadfuns.com:8080/updsvr/ota/checkupdate?hw=7018&hwv=V1.10&swv=2.3.001.120510.7018&serialno=864670010001838";
    private String servicePath="";
    String startingUpService;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override

    public void onCreate() {
        //A: kuangyunfeng 20160401(start)
        int hasReadPhoneStatePermission = getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            Log.i("kayun", "BootService do not have the permission READ_PHONE_STATE.");
            //Intent permissionIntent = new Intent(this, PermissionActivity.class);
            Intent permissionIntent = new Intent();
            permissionIntent.setClassName("com.android.ota", "com.android.ota.PermissionActivity");
            permissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(permissionIntent);
            return;
        } else {
            Log.i("kayun", "BootService have the permission READ_PHONE_STATE.");
        }
        //A: kuangyunfeng 20160401(end)

        Log.d(TAG, "My Service create");
        httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 10000);//请求超时设置
        httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时设置

        getSerialNumberInformation();//组织请求服务器参数

        iFilter=new IntentFilter();//注册监听
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        PowerManager pm=(PowerManager)this.getSystemService(POWER_SERVICE);
//        pWakeLock=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BatteryWaster");
//        pWakeLock.setReferenceCounted(false);

        registerReceiver(mReceiver, iFilter);
//        pWakeLock.acquire();

    }



    @Override

    public void onDestroy() {
        Log.d(TAG, "My Service onDestroy");
    }
    @Override
    public void onStart(Intent intent, int startid) {
        Log.d(TAG, "My Service start");
        try{
            if((intent!=null)&&(intent.getExtras() != null)){
                startingUpService= intent.getExtras().getString("startingUpService");
            }
            Log.d(TAG, "startingUpService=="+startingUpService);
            SimpleDateFormat simdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ActivityUtils.setParameter(this, "update_date", simdate.format(new Date()));
        }
        catch(Exception e){
            Log.d(TAG, "startingUpService Error:----------=="+e.toString());
            e.printStackTrace();
        }
        //3.判断当前网络是否通畅
        //4.判断sdcard 当前是否可用。若可用，容量是否大于150兆
        //5.电池电量是否小余20%
        //6.请求网络服务器

        //判断是否是自动更新
        if(ActivityUtils.getParameter(getApplicationContext(), "yulong_cota_autoupdate").equals("1")){
            bootThread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int i=0;i<10;i++){
                            Thread.sleep(3000);
                            Log.d(TAG, "BootService request----------for i=="+i);
                            if(ActivityUtils.haveInternet(getApplicationContext())){
                                if(getServiceData()==0){
                                    Message msg= new Message();
                                    msg.what=1;
                                    hander.sendMessage(msg);
                                }
                                else{
                                    Log.d(TAG, "No Updates");
						                Log.d(TAG, "next time updates");
                                        Intent itt = new Intent(Intent.ACTION_RUN);
                                    itt.setComponent(new ComponentName("com.android.ota",
                                            "com.android.ota.autoupdate.BootService"));
                                        ActivityUtils.setNextNotify(getApplicationContext(),itt,true,ActivityUtils.quency_days);
                                    stopSelf();
                                }
                                Log.d(TAG, "BootService success----------i=="+i);
                                break;
                            }
                            else{
                                //重新设置一个闹钟，更改设置时间（开机启动检查用）
                                //并重新设置新闹钟，3天后提醒下载
                                //保存当前日期
                                if(startingUpService==null){
                                        Log.d(TAG, "BootService request----------new Deskclock");
                                        Intent itt = new Intent(Intent.ACTION_RUN);
                                    itt.setComponent(new ComponentName("com.android.ota",
                                            "com.android.ota.autoupdate.BootService"));
                                        ActivityUtils.setNextNotify(getApplicationContext(),itt,true,ActivityUtils.quency_days);
                                    stopSelf();
                                    break;
                                }
                                else{
                                    if(i==9){ stopSelf();}
                                }
                            }
                        }

                    } catch (Exception e) {
                        //e.printStackTrace();
                        Log.e(TAG, "BootService Error:----------=="+e.toString());
                    }


                }
            });
            bootThread.start();

        }
    }

    Handler hander=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if(bootThread!=null){
                        bootThread.interrupt();
                        bootThread=null;
                    }
                    Intent itt = new Intent(Intent.ACTION_RUN);
                    itt.setComponent(new ComponentName("com.android.ota",
                            "com.android.ota.autoupdate.BootService"));
                    ActivityUtils.setNextNotify(getApplicationContext(),itt,true,ActivityUtils.quency_days);
                    // modify BUG_ID:QYLE-2297 20120711 yulong.liang start
                    if(upinfo.getPriority().equals("Optional")){
                        Log.d(TAG, "Optional");
                        //普通更新包
                        if(ActivityUtils.getParameter(BootService.this, "download_state").equals("yes")){
                            Log.d(TAG, "Optional--yes");

                        }
                        else{
                            ActivityUtils.setParameter(getApplicationContext(), "ota_priority", upinfo.getPriority());//此处到时修改
                            //允许wifi自动下载
                            if((ActivityUtils.getParameter(getApplicationContext(), "allow_wifi_auto_download").equals("1"))&&(ActivityUtils.CheckNetworkState(getApplicationContext()).equals("wifi"))){
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
                                if(isSuc){
                                    downloadDataIntent();
                                }
                            }
                            else{
                                Intent intent = new Intent(BootService.this,AutoUpdate.class);
                                Bundle bundle=new Bundle();
                                bundle.putParcelable("com.ota.updateinfo", upinfo);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }

                    }
                    else{//高级优先包
                        Log.d(TAG, "Mandatory");
                        if(ActivityUtils.getParameter(BootService.this, "download_state").equals("yes")){
                            Log.d(TAG, "Mandatory--yes");
                        }
                        else{
                            ActivityUtils.setParameter(getApplicationContext(), "ota_priority", upinfo.getPriority());
// delete BUG_ID:DELL-1040 20120615 yulong.liang start
                            // Toast.makeText(getApplicationContext(),R.string.ota_update_priority_Mandatory, 1).show();
// delete BUG_ID:DELL-1040 20120615 yulong.liang end
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
                            if(isSuc){
                                // add BUG_ID:DELL-1040 20120615 yulong.liang start
                                //if(ActivityUtils.CheckNetworkState(getApplicationContext()).equals("wifi")){
                                // add BUG_ID:DELL-1040 20120615 yulong.liang end
                                downloadDataIntent();
                                //}
                            }

                        }
                    }
                    // modify BUG_ID:QYLE-2297 20120711 yulong.liang end
                    stopSelf();
                    break;

            }
        };
    };
    //获取服务器端更新信息
    private int getServiceData(){
        Log.d(TAG, "getServiceData()");
        int in_success=0;
        HttpGet hget;
        try
        {
            hget = new HttpGet(servicePath);
            StringBuffer response=new StringBuffer();
            // 发送GET请求
            HttpResponse httpResponse = httpClient.execute(hget);
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
 Log.d(TAG, "register="+register);
 ActivityUtils.registerCellPhone(register);
 }
 **/

                if(updateSys_State.equals("0")){//有更新包
                    in_success=0;
                    // add BUG_ID:QYLE-2297 20120711 yulong.liang start
                    //进行xml解析
                    String strXml=str.substring(str.indexOf("<?xml"),str.length());
                    InputStream inputStream =new ByteArrayInputStream(strXml.getBytes());
                    try {
                        DocumentBuilderFactory factory = DocumentBuilderFactory
                                .newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(inputStream);
                        // 获取根节点
                        Element root = document.getDocumentElement();
                        parse(root);
                    }
                    catch(Exception e){
                        in_success=1;
                        Log.e(TAG, e.toString());
                    }
                    // add BUG_ID:QYLE-2297 20120711 yulong.liang end
                }
                else{
                    in_success=1;
                }
            }
            else{
                in_success=1;
            }
            return in_success;
        }
        catch (Exception e)
        {
            Log.e(TAG, "error="+e.toString());
            return 1;
        }
    }
    //电量信息
    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(Intent.ACTION_BATTERY_CHANGED.equals(action)){
                residue_cable=intent.getIntExtra("level",0);
                unregisterReceiver(mReceiver);
//                pWakeLock.release();
            }
        }
    };

    /**
     * 获取一些软件版本信息
     * 组织请求语句
     * @return
     */
    private void getSerialNumberInformation() {
        servicePath=ActivityUtils.getServerUrl(getApplicationContext());
        String ModelNumber=Build.MODEL;
        //modify BUG_ID:QELS-2699 liangyulong 20121217(start)
        boolean bolNum =getApplicationContext().getResources().getBoolean(R.bool.config_modelnumber_permit_null);
        if(bolNum==false){
            ModelNumber=ModelNumber.substring(ModelNumber.indexOf(" ")+1, ModelNumber.length());
            ModelNumber=ModelNumber.replaceAll(" ", "");
        }
        //modify BUG_ID:QELS-2699 liangyulong 20121217(end)
        String HardwareV=ActivityUtils.getHardwareVersion();
        String IMEI=ActivityUtils.getPhoneIMEI(this);
        ModelNumber=ModelNumber.replaceAll(" ", "%20");
        //modify QELS-4235 wanglu 20130118 start
        if(servicePath.indexOf("ragentek")>-1){
            servicePath+="hw="+ModelNumber+"&hwv=P2&swv="+ActivityUtils.getInternalVersion()+"&serialno="+IMEI+"&flag=2&version=V1.0&beta="+ActivityUtils.getRegisterParam(getApplicationContext());
        }else{
            servicePath+="hw="+ModelNumber+"&hwv=P2&swv="+Build.DISPLAY+"&serialno="+IMEI;
        }

        //modify QELS-4235 wanglu 20130118 end
        //Log.d(TAG, servicePath);
    }
    // add BUG_ID:QYLE-2297 20120711 yulong.liang start
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
    private void downloadDataIntent(){
        String fiName=upinfo.getDownloadURL();
        fiName=fiName.substring(fiName.lastIndexOf("/")+1, fiName.length());
        fiName=ActivityUtils.getSdcardPath()+fiName;

        File file = new File(fiName);//文件保存目录
        try{
            if(file.exists()){
                ActivityUtils.verifyPackage(file, null,TAG);
                file.delete();
            }
        } catch (IOException e) {
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
            Intent intent = new Intent(BootService.this, NotificationDownloadService.class);
            intent.putExtra("downloadUrl",upinfo.getDownloadURL() );
            intent.putExtra("downloadByte", upinfo.getDownloadByte());
            intent.putExtra("packageType", upinfo.getPackageType());
            startService(intent);
        }
        //停止一小时对话框
        //stopService(new Intent(this, AnHourLaterNotificationService.class));


    }
    // add BUG_ID:QYLE-2297 20120711 yulong.liang end

}
