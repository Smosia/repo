package com.android.ota;
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

import android.os.*;
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

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.ScrollView;
//A: kuangyunfeng 20160219(start)
import android.Manifest;
import android.content.pm.PackageManager;
//A: kuangyunfeng 20160219(end)

import com.android.ota.R;
import com.android.ota.ActivityUtils.OtaVersion;
@SuppressLint("NewApi")
public class UpdateSystem extends Activity {
    private static final String TAG="UpdateSystem:TIOW";

    // status define
    public interface Status{
        final int NONE = 00; // 未做任何处理
        final int CHECKING = 10; // checking
        final int CHECK_FAIL = 11; // check fail, 网络异常之类
        final int CHECK_NO_VERSION = 12; // check net success , 网络正常无版本
        final int CHECK_SUCCESS = 13; // check net success , 有版本
        final int DOWNLOAD_PAUSE = 19;
        final int DOWNLOADING = 20; // downloading
        final int DOWNLOAD_SUCCESS = 21; // download success
        final int DOWNLOAD_FAIL = 22; // download fail, 异常
        final int DOWNLOAD_ERROR_PACKAGE = 23;	// error package
        final int DOWNLOAD_NOT = 24;		//下载失败

        final int UPGRADEING = 30;
        final int UPGRADE_FAIL = 31;

        final String TAG = "process_status";
    }
    private Button systemUpdateBtn;

    private Resources res =null;
    private TextView screenText = null;
    private TextView screenTip = null;
    private TextView nowVersion =null;
    private IntentFilter iFilter;
    private PowerManager.WakeLock pWakeLock;
    private int residue_cable;
    private Thread initCheckedThread = null;
    private final int CHECKED_FAIL = 1;
    private final int CHECKED_FAIL_NETWORK = 2;
    private final int CHECKED_SUCCEED = 3;
    private final int ota_server_not_available = 4;
    private final int phone_software_version_uptodate = 5;
    private RadioButton rb_mobile_wifi_download,rb_wifi_download;
    private CheckHandler checkHandler;
    private LoadingDialog loadingDialog;

    private boolean debug = false;
    private boolean isDownloading = false;
    private boolean checkPackage = false;
    private static int sStatus = Status.NONE;
    HttpClient httpClient;

    private String servicePath = "";
    private String updateSys_State = null;
    private String packInfoANDversionInfo = "";

    PendingIntent pi;
    private static final String CHECK_DOWNLOAD_BY_SERVICE = "processDoneFromNotification";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        systemUpdateBtn = (Button) findViewById(R.id.system_update_btn);
        loadingDialog = LoadingDialog.createDialog(this);
        //A: kuangyunfeng 20160219(start)
        /*int hasReadPhoneStatePermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE}, 1);
            return;
        }
        */
        //A: kuangyunfeng 20160219(end)
    }

    private static final int REQUEST_CODE = 2;
    private static final int RESULT_OK = 3;
    private static final int RESULT_CANCEL = 4;

    @Override
    protected void onResume() {
        //A: kuangyunfeng 20160219(start)
        int hasReadPhoneStatePermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            Log.i("kayun", "Do not have the permission READ_PHONE_STATE.");
            //Intent permissionIntent = new Intent(this, PermissionActivity.class);
            Intent permissionIntent = new Intent();
            permissionIntent.setClassName("com.android.ota", "com.android.ota.PermissionActivity");
            startActivityForResult(permissionIntent,REQUEST_CODE);
        } else {
            Log.i("kayun", "Have the permission READ_PHONE_STATE.");
            init();
        }
        //A: kuangyunfeng 20160219(end)
        super.onResume();
    }

    private boolean isRegist = false;

    private void init(){ 
        initSys();
        String statusStr = ActivityUtils.getParameter(getApplicationContext(),Status.TAG);
        sStatus = statusStr == ""?sStatus:Integer.parseInt(statusStr);
        if(sStatus == Status.NONE){
//        	initButton();
            updateSystemProcess();
        }else{
            if(sStatus==Status.DOWNLOADING||sStatus==Status.DOWNLOAD_SUCCESS){
                showProgressRate();
            }
            Log.d(TAG, "dexin:1 ------- sStatus:"+sStatus);
            caseProcess();
        }
        try {
            Log.d(TAG, "onResume() ------- sStatus:"+sStatus);
            isRegist = true;
            registerReceiver(mReceiver, iFilter);
            pWakeLock.acquire();
            updateSystemProcess();
            if(sStatus==Status.DOWNLOAD_PAUSE){
                screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
                screenText.setVisibility(View.GONE);
                downTitle = downTitle == null? (TextView) findViewById(R.id.system_update_title):downTitle;
                downTitle.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "onResume()"+e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            Log.d("kayun", "requestCode = " + requestCode + ", resultCode = " + resultCode);
            if(resultCode == RESULT_OK){
                init();
            } else if (resultCode == RESULT_CANCEL) {
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        if(sStatus==Status.CHECK_NO_VERSION)
            sStatus = Status.NONE;
        if(sStatus!=Status.UPGRADEING)
            ActivityUtils.setParameter(getApplicationContext(),Status.TAG, sStatus+"");
        hideScreenText();
        //退出时判断检查是否还在检查线程，如果是则初始化processDone
        if (initCheckedThread != null) {
            initCheckedThread.interrupt();
            initCheckedThread = null;
        }
        if(isRegist){
            unregisterReceiver(mReceiver);
        }
        Log.d(TAG, "onPause() -------- sStatus="+sStatus);
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//		 menu.add(0,1,1, R.string.btn_update_val);
        menu.add(0,2,2, R.string.local_update);
        menu.add(0,3,3, R.string.ota_settings);
        initActionBar();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case 1:
                if (sStatus == Status.DOWNLOADING) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.ota_check_version);
                    builder.setMessage(R.string.stop_download_task_to_check);
                    builder.setPositiveButton(R.string.btn_ok_val,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,	int which) {
                                    UpdateSystem.this.stopService(new Intent(NotificationDownloadService.PROGRESS_BAR_STATE));
                                    UpdateSystem.this.stopService(new Intent(NotificationDownloadService.DOWNLOAD_SERVICE));
                                    UpdateSystem.this.stopService(new Intent(NotificationDownloadService.PROGRESS_BAR_MAX));
                                    sStatus = Status.NONE;
                                    ActivityUtils.setParameter(getApplicationContext(), Status.TAG, sStatus+"");
                                    isDownloading = false;
                                    hideScreenText();
                                    Log.d(TAG, "dexin:2 ------- sStatus:"+sStatus);
                                    caseProcess();
                                }
                            });
                    builder.setNegativeButton(R.string.btn_cancel_val,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                } else {
                    sStatus = Status.NONE;
                    ActivityUtils.setParameter(getApplicationContext(), Status.TAG, sStatus+"");
                    hideScreenText();
                    Log.d(TAG, "dexin:3 ------- sStatus:"+sStatus);
                    caseProcess();
                }
                return true;
            case 3:
                otaSetting();
                break;
            case 2:
                //M:BUG_ID:JLLB-3714 wuqi 20140808  (start)
                if (sStatus == Status.DOWNLOADING) {
                    // 当前正在下载文件，不允许进行本地升级
                    Toast.makeText(getApplicationContext(),R.string.ota_download_now_can_update, Toast.LENGTH_LONG).show();
                } else {
                    checkHandler = new CheckHandler();
                    CheckThread checkThread = new CheckThread();
                    new Thread(checkThread).start();
                    loadingDialog.show();
                }
                //M:BUG_ID:JLLB-3714 wuqi 20140808 (  end)
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class CheckHandler extends Handler {
        public CheckHandler(){
            
        }
        public CheckHandler(Looper l){
            super(l);
        }
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG,"handleMessage--0");
            switch (msg.what){
                case 1:
                    Log.i(TAG,"handleMessage--1");
                    loadingDialog.hide();
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateSystem.this);
                    builder.setTitle(R.string.process_upgrade);
                    builder.setMessage(R.string.yesorno_install_package);
                    builder.setPositiveButton(R.string.btn_ok_val,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) {
                                    upGrade();
                                }
                            });
                    builder.setNegativeButton(R.string.btn_cancel_val,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    break;
                case 2:
                    Log.i(TAG,"handleMessage--2");
                    loadingDialog.hide();
                    // 如果升级包不合法则直接让用户重新check
                    Toast.makeText(getApplicationContext(), R.string.check_package_not_valid, Toast.LENGTH_LONG).show();
                    sStatus = Status.NONE;
                    Log.d(TAG, "dexin:4 ------- sStatus:"+sStatus);
                    caseProcess();
                    break;
                default:
                    break;
            }
        }
    }
    class CheckThread implements Runnable {
        @Override
        public void run() {
            Log.i(TAG,"CheckThread--1");
            boolean isLocalUpdate = isLocalPagkageCanUpdate();
            Message msg = new Message();
            if(isLocalUpdate){
                msg.what=1;
            }else {
                msg.what=2;
            }
            checkHandler.sendMessage(msg);
        }
    }
    private Drawable processPiontWaitForAction = null;
    private Drawable processPiontFail = null;
    private Drawable processPiontOk = null;
    private Drawable refresh = null;
    private Drawable download = null;
    private Drawable upgrade = null;
    private Drawable lineGone = null;
    private Drawable lineNone = null;
    private ImageView processCheck = null;
    private ImageView processDownload = null;
    private ImageView processUpgrade = null;
    private ProgressBar progressBar = null;
    private LinearLayout progressContainer = null;
    private TextView progressState = null;
    private void initResourse(){
        Log.d(TAG, "initResourse()");
        res = res == null?this.getResources():res;
        lineGone = lineGone==null? res.getDrawable(R.drawable.progress_finish):lineGone;
        lineNone = lineNone==null? res.getDrawable(R.drawable.progress_finish_normal):lineNone;
        processPiontWaitForAction = processPiontWaitForAction == null ? res .getDrawable(R.drawable.progress_doing) : processPiontWaitForAction;
        processPiontFail = processPiontFail == null ? res .getDrawable(R.drawable.progress_over_normal_fail) : processPiontFail;
        processPiontOk = processPiontOk == null ? res .getDrawable(R.drawable.progress_over_normal_right) : processPiontOk;
        refresh = refresh == null ? res.getDrawable(R.drawable.button_refresh) : refresh;
        download = download == null ? res.getDrawable(R.drawable.button_download) : download;
        upgrade = upgrade == null ? res.getDrawable(R.drawable.button_upgrade): upgrade;
        processCheck = processCheck == null ? (ImageView) findViewById(R.id.process_piont_check) : processCheck;
        processDownload = processDownload == null ? (ImageView) findViewById(R.id.process_piont_download) : processDownload;
        processUpgrade = processUpgrade == null ? (ImageView) findViewById(R.id.process_piont_upgrade) : processUpgrade;
        progressBar = progressBar ==null?(ProgressBar) findViewById(R.id.system_update_progress):progressBar;
        progressContainer = progressContainer == null? (LinearLayout) findViewById(R.id.progress_container):progressContainer;
        progressState = progressState == null ? (TextView) findViewById(R.id.system_update_progress_state):progressState;
        downTitle = downTitle == null ? (TextView) findViewById(R.id.system_update_title) : downTitle;
        downMsg = downMsg == null ? (TextView) findViewById(R.id.system_update_text) : downMsg;
        //获取当前版本信息
        getSerialNumberInformation();
        //getSerialNumberInformation();
    }
    /**
     * The button which in OTA UI center be initialize its background by OTA process , when this button be key up OTA process go on .
     * */
    private void updateSystemProcess(){
        Log.d(TAG, "updateSystemProcess():  ------sStatus="+sStatus);

        switch (sStatus) {
            case Status.NONE:
                // add  BUG_ID: no   zhangyuwen  20140807(start)
            case Status.CHECK_NO_VERSION:
                // add  BUG_ID: no   zhangyuwen  20140807(end)
            case Status.CHECK_FAIL:
            case Status.DOWNLOAD_FAIL:
            case Status.DOWNLOAD_ERROR_PACKAGE:
            case Status.DOWNLOAD_NOT:
                showScreenTip(R.string.ota_check_version);
                processDownload.setBackgroundResource(R.drawable.progress_over_normal02);
                systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        Button btn = (Button) v;
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            btn.setBackgroundResource(R.drawable.refresh_down);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            btn.setBackgroundResource(R.drawable.refresh);
                            lineGoing(Status.CHECK_SUCCESS);
                            processCheck.setBackgroundDrawable(processPiontWaitForAction);
                            checkSystemUpdate();
                        }
                        return false;
                    }
                });
                break;
            //如果检查网络环境OK的情况下，初始化下载按钮并绑定点击事件
            case Status.CHECK_SUCCESS:
            case Status.DOWNLOAD_PAUSE:
                showScreenTip(R.string.ota_download_version);
                processCheck.setBackgroundDrawable(processPiontOk);
                final boolean isNewVersion = downloadNewVersion(0, packInfoANDversionInfo);
                systemUpdateBtn.setBackgroundDrawable(download);
                systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        Button btn = (Button) v;
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            btn.setBackgroundResource(R.drawable.download_down);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            btn.setBackgroundResource(R.drawable.download);
                            lineGoing(Status.DOWNLOAD_SUCCESS);
                            processDownload.setBackgroundDrawable(processPiontWaitForAction);
                            try {
                                if(isNewVersion){
                                    //开始下载
                                    if(!isDownloading&&sStatus!=Status.DOWNLOADING){
                                        if(checkDownload()){
                                            showDownloadTips();
                                        }
                                    }else{
                                        Log.d(TAG, "garan    ------- sStatus="+sStatus);
                                        Toast.makeText(getApplicationContext(), R.string.ota_download_now_can_update, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                Log.d(TAG, "updateSystemProcess:["+sStatus+"] :"+e.toString());
                                processDownload.setBackgroundDrawable(processPiontFail);
                            }
                        }
                        return false;
                    }
                });
                break;
            case Status.DOWNLOADING:
                systemUpdateBtn.setBackgroundResource(R.drawable.pause);
                systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            systemUpdateBtn.setBackgroundResource(R.drawable.download_down);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            systemUpdateBtn.setBackgroundResource(R.drawable.pause);
                            try {
                                Log.d(TAG, "dexin.su:shishi");
                                Intent inte = new Intent();
                                inte.setAction(NotificationDownloadService.STOP_DOWNLOAD_ACTION);
                                sendBroadcast(inte);
                                sStatus = Status.DOWNLOAD_PAUSE;
                                ActivityUtils.setParameter(getApplicationContext(), Status.TAG, sStatus + "");
                                isDownloading = false;
                                Log.d(TAG, "dexin:5 ------- sStatus:"+sStatus);
                                caseProcess();
                            } catch (Exception e) {
                                Log.d(TAG, "updateSystemProcess:[" + sStatus + "] :" + e.toString());
                                processDownload.setBackgroundDrawable(processPiontFail);
                            }
                        }
                        return false;
                    }
                });
                break;
            case Status.DOWNLOAD_SUCCESS:
                processDownload.setBackgroundDrawable(processPiontOk);
                systemUpdateBtn.setBackgroundDrawable(upgrade);
                showScreenTip(R.string.ota_upgrade);
                systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        Button btn = (Button) v;
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            btn.setBackgroundResource(R.drawable.upgrade_down);
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            btn.setBackgroundResource(R.drawable.upgrade);
                            //M:BUG_ID:JLLB-3716 wuqi 20140808 (start)
                            //处理升级
                            if (isLocalPagkageCanUpdate()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateSystem.this);
                                builder.setTitle(R.string.process_upgrade);
                                builder.setMessage(R.string.yesorno_install_package);
                                builder.setPositiveButton(R.string.btn_ok_val,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,int which) {
                                                upGrade();
                                            }
                                        });
                                builder.setNegativeButton(R.string.btn_cancel_val,new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder.show();
                            }else{
                                //在检查包不合法后检查本地包是否存在
                                if(checkLocalPackage()){
                                    showOnScreen(R.string.verify_file_fail);
                                }else{
                                    showOnScreen(R.string.notexists_package_local);
                                }
                                //如果包有问题，不论怎么样都重置
                                sStatus = Status.NONE;
                                Log.d(TAG, "dexin:6 ------- sStatus:"+sStatus);
                                caseProcess();
                            }
                            //M:BUG_ID:JLLB-3716 wuqi 20140808 (  end)
                        }
                        return false;
                    }
                });
                break;
            default:
                break;
        }

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
                        if(rb_wifi_download.isChecked()){//如果是使用wifi下载，判断wifi是否连接上
                            //判断当前连接的是wifi，还是mobile;
                            if(ActivityUtils.CheckNetworkState(UpdateSystem.this).equals("mobile")){
                                showUnableNetworkTips(R.string.setnetwork_open);
                            }
                            else{
                                dialog.cancel();
                                //开始下载系统，更新
                                lineGoing(Status.DOWNLOADING);
                                processDownload.setBackgroundDrawable(processPiontWaitForAction);
                                showScreenTip(R.string.download_state_point);
                                downloadDataIntent();
                            }
                        }
                        else if(rb_mobile_wifi_download.isChecked()){//如果使用移动网络或者wifi下载
                            dialog.cancel();
                            //开始下载系统，更新
                            lineGoing(Status.DOWNLOADING);
                            processDownload.setBackgroundDrawable(processPiontWaitForAction);
                            showScreenTip(R.string.download_state_point);
                            downloadDataIntent();
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
        //if(isCancelDig==false){
        builder.create();
        builder.show();
        // }
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
    }
    /**
     * this is do OTA process,before do process it will change some UI 
     * 弃用
     * */
//    private boolean processGone(final int processDone){
//    	Log.d(TAG, "processGone() ---- processDone = "+processDone);
//    	boolean goneProcessFlag = false;
//    	try {
//    		switch (processDone) {
//    		case PROCESS_CHECK:
//    			lineGoing(PROCESS_CHECK);
//    			processCheck.setBackgroundDrawable(processPiontWaitForAction);
//    			goneProcessFlag = checkSystemUpdate();
//    			if(goneProcessFlag && checkDownload()){
//    				processCheck.setBackgroundDrawable(processPiontOk);
//    				lineGoing(processDone);
//					processDownload.setBackgroundDrawable(processPiontWaitForAction);
//					showScreenTip(R.string.ota_download_version);
//    				return true;
//    			}else{
//    				this.processDone = 0;
//    				processCheck.setBackgroundDrawable(processPiontOk);
//    			}
//    		case PROCESS_DOWNLOAD:
//    			processCheck.setBackgroundDrawable(processPiontOk);
//    			lineGoing(PROCESS_DOWNLOAD);
//    			processDownload.setBackgroundDrawable(processPiontWaitForAction);
//    			goneProcessFlag = downloadNewVersion(phone_software_version_uptodate,null);
//    			if(goneProcessFlag){
//    				processDownload.setBackgroundDrawable(processPiontOk);
//    				return true;
//    			}
//    			return true;
////    		case PROCESS_BACKUP:
////    			lineGoing(PROCESS_BACKUP);
////    			ImageView processBackup = (ImageView) findViewById(R.id.process_piont_backup);
////    			processBackup.setBackgroundDrawable(processPiontWaitForAction);
////    			goneProcessFlag = backupData();
////    			if(goneProcessFlag){
////    				processBackup.setBackgroundDrawable(processPiontOk);
////    				return true;
////    			}
////    			return true;
//    		case PROCESS_UPGRADE:
//    			lineGoing(PROCESS_UPGRADE);
//    			ImageView processUpgrade = (ImageView) findViewById(R.id.process_piont_upgrade);
//    			processUpgrade.setBackgroundDrawable(processPiontWaitForAction);
////    			goneProcessFlag = upGrade();
////    			if(goneProcessFlag){
////    				processUpgrade.setBackgroundDrawable(processPiontOk);
////    				return true;
////    			}
//    			return true;
//    		default:
//    			Toast.makeText(getApplicationContext(), "warm", Toast.LENGTH_SHORT);
//    			return false;
//    		}
//		} catch (Exception e) {
//			Log.e(TAG, "processGone():"+e.getMessage());
//			return false;
//		}
//    }
    private void caseProcess(){
        Log.d(TAG, "caseProcess() ---- sStatus"+sStatus);
        if(sStatus>=Status.CHECKING){
            lineGoing(Status.CHECK_SUCCESS);
            processCheck.setBackgroundDrawable(processPiontWaitForAction);
            systemUpdateBtn.setBackgroundDrawable(refresh);
            if(sStatus == Status.CHECK_FAIL){
                processCheck.setBackgroundDrawable(processPiontFail);
                sStatus = Status.NONE;
                return;
            }
            // modify  BUG_ID: no   zhangyuwen  20140807(start)
/*
    		if(sStatus >= Status.CHECK_NO_VERSION||sStatus >= Status.CHECK_SUCCESS){
    			processCheck.setBackground(processPiontOk);
    			systemUpdateBtn.setBackgroundDrawable(download);
    		}
*/
            if(sStatus >= Status.CHECK_SUCCESS){
                processCheck.setBackgroundDrawable(processPiontOk);
                systemUpdateBtn.setBackgroundDrawable(download);
            }
            if(sStatus == Status.CHECK_NO_VERSION){
                processCheck.setBackgroundDrawable(res.getDrawable(R.drawable.progress_over_normal01));
                systemUpdateBtn.setBackgroundDrawable(refresh);
                //如果没有新的版本信息，就清理数据
                ActivityUtils.clearSharedPreferences(getApplicationContext());
                sStatus = Status.NONE;
            }
            // modify  BUG_ID: no   zhangyuwen  20140807(end)
        }
        if(sStatus>=Status.DOWNLOADING){
            lineGoing(Status.DOWNLOADING);
            processDownload.setBackgroundDrawable(processPiontWaitForAction);
            if(sStatus >= Status.DOWNLOAD_SUCCESS){
                processDownload.setBackgroundDrawable(processPiontOk);
            }
            if(sStatus == Status.DOWNLOAD_FAIL||sStatus == Status.DOWNLOAD_NOT||sStatus == Status.DOWNLOAD_ERROR_PACKAGE){
                Log.d(TAG, "dexin:woca ---- sStatus"+sStatus);
                Toast.makeText(getApplicationContext(), R.string.check_package_not_valid, Toast.LENGTH_LONG).show();
                processDownload.setBackgroundDrawable(processPiontFail);
                systemUpdateBtn.setBackgroundDrawable(refresh);
                sStatus = Status.NONE;
            }
        }
        if(sStatus>=Status.UPGRADEING){
            if(sStatus == Status.UPGRADE_FAIL){
                processDownload.setBackgroundDrawable(processPiontFail);
                systemUpdateBtn.setBackgroundDrawable(refresh);
                sStatus = Status.NONE;
                return;
            }else{
                lineGoing(Status.UPGRADEING);
                processUpgrade.setBackgroundDrawable(processPiontWaitForAction);
            }
        }
        if(sStatus==Status.NONE){
            hideScreenText();
            hideProgressRate();
            lineGoing(Status.NONE);
            processCheck.setBackgroundResource(R.drawable.progress_over_normal01);
            processDownload.setBackgroundResource(R.drawable.progress_over_normal02);
            processUpgrade.setBackgroundResource(R.drawable.progress_over_normal03);
            updateSystemProcess();
            return;
        }
        Log.d(TAG, "caseProcess()   before updateSystemProcess ---- sStatus"+sStatus);
        updateSystemProcess();
    }
    /**
     * change process line background image 
     * */
    private void lineGoing(int status){
        Log.d(TAG, "lineGoing()  ---- sStatus = "+ status);
        ImageView line = null;
        switch (status) {
            case Status.NONE:
                line = (ImageView) findViewById(R.id.process_line_1);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_2);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_3);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_4);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_5);
                line.setBackgroundDrawable(lineNone);
                break;
            case Status.CHECK_SUCCESS:
                line = (ImageView) findViewById(R.id.process_line_1);
                line.setBackgroundDrawable(lineGone);
                line = (ImageView) findViewById(R.id.process_line_2);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_3);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_4);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_5);
                line.setBackgroundDrawable(lineNone);
                break;
            case Status.DOWNLOADING:
                line = (ImageView) findViewById(R.id.process_line_2);
                line.setBackgroundDrawable(lineGone);
                line = (ImageView) findViewById(R.id.process_line_3);
                line.setBackgroundDrawable(lineGone);
                line = (ImageView) findViewById(R.id.process_line_4);
                line.setBackgroundDrawable(lineNone);
                line = (ImageView) findViewById(R.id.process_line_5);
                line.setBackgroundDrawable(lineNone);
                break;
            case Status.UPGRADEING:
                line = (ImageView) findViewById(R.id.process_line_4);
                line.setBackgroundDrawable(lineGone);
                line = (ImageView) findViewById(R.id.process_line_5);
                line.setBackgroundDrawable(lineGone);
                break;
            default:
                //never exists this result
                break;
        }
    }
    /**
     * check system version function
     * */
    private boolean checkSystemUpdate(){
        Log.d(TAG, "checkSystemUpdate() sStatus="+sStatus);
        try {
            hideScreenText();
            checkUpdateSys();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "checkSystemUpdate()"+e.toString());
            return false;
        }
    }
    private void checkIsNewVersion(){
        //判断当前是否已经在下载
        if(ActivityUtils.getParameter(this, "download_state").equals("yes")){
            showOnScreen(R.string.please_try_again_later);
            return ;
        }
        // add BUG_ID:QYLE-2297 20120711 yulong.liang start
        String delaycount=ActivityUtils.getParameter(this, "delaytime_count");
        Log.d(TAG, "delaycount=="+delaycount+",residue_cable="+residue_cable + ",download_state== "+ActivityUtils.getParameter(this, "download_state"));
        if(delaycount.equals("1")||delaycount.equals("2")||delaycount.equals("3")){
            if(residue_cable<=20){
                showOnScreen(R.string.power_is_too_low);
                return ;
            }
            else{
                Intent intentdelay=new Intent(this,DelayUpdateDialog.class);
                startActivity(intentdelay);
                return ;
            }
        }
    }
    /**
     *  Download new system function
     * */
    private String verPackageSize = "";
    private TextView downTitle = null;
    private TextView downMsg = null;
    private boolean downloadNewVersion(int msg , String msgText) {
        Log.d(TAG, "downloadNewVersion() ------- sStatus = "+sStatus+"----- msg="+msg+"-----msgText="+ (msgText == null?"null":msgText.equals("")));
        SimpleDateFormat simdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ActivityUtils.setParameter(this, "update_date",simdate.format(new Date()));
        if (msg != R.string.phone_software_version_uptodate) {// 表示需要更新
            initByLocalVersion();
            screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
            screenText.setVisibility(View.GONE);
            downTitle = downTitle == null ? (TextView) findViewById(R.id.system_update_title) : downTitle;
            downMsg = downMsg == null ? (TextView) findViewById(R.id.system_update_text) : downMsg;
            // 防止文本过多，使文本可以进行滚动,对应的xml也需要设置属性
            downMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
            String packSize = upinfo.getSize();
            if(!ActivityUtils.getParameter(getApplicationContext(), "packInfoANDversionInfo").equals("")){
                packInfoANDversionInfo = packInfoANDversionInfo ==""?ActivityUtils.getParameter(getApplicationContext(), "packInfoANDversionInfo"):packInfoANDversionInfo;
            }
            verPackageSize = packSize;
            packSize = res.getString(R.string.ota_new_version,packSize);
            String versionInfo = packInfoANDversionInfo;
            hideScreenText();
            downTitle.setText(packSize);
            downMsg.setText(versionInfo);
            downTitle.setVisibility(View.VISIBLE);
            downMsg.setVisibility(View.VISIBLE);
            showScreenTip(R.string.ota_download_version);
            return true;
        }
        return false;
    }
    /**
     * upgrade system function
     * */
    private boolean upGrade() {
        // 重启代码
        String path = ActivityUtils.getParameter(UpdateSystem.this,"sdcard_updatepackage_URL");
        if(path == null || "".equals(path)) { // ota file not exists
            // check local sdcard/update.zip
            path = "/sdcard/update.zip";
        } else {
            path = path.substring(path.lastIndexOf("/") + 1,path.length());
            path = ActivityUtils.getSdcardPath() + path;
        }
        Log.d(TAG, "install file:" + path);
        File file = new File(path);
        if (file.exists()) {
            //当重启更新之前，清理掉所有的OTA相关数据
            Intent intent = new Intent(UpdateSystem.this,MtkUpgradeRestart.class);
            intent.putExtra("filePath", path);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ActivityUtils.clearSharedPreferences(getApplicationContext());
            startActivity(intent);
            sStatus = Status.UPGRADEING;
            UpdateSystem.this.finish();
        } else {
            Intent itt2 = new Intent(UpdateSystem.this,DelayUpdateDialog.class);
            ActivityUtils.delayNotify(UpdateSystem.this, itt2, false);// 取消以前的通知
            ActivityUtils.setParameter(UpdateSystem.this,"delaytime_count", "");
            Toast.makeText(UpdateSystem.this, R.string.notexists_package,1).show();
            UpdateSystem.this.finish();
        }
        return true;
    }
    private void initSys(){
        Log.d(TAG, "initSys()");
        initResourse();
        iFilter = iFilter == null? new IntentFilter():iFilter;// 注册监听
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        iFilter.addAction(NotificationDownloadService.DOWNLOAD_SERVICE);
        iFilter.addAction(NotificationDownloadService.PROGRESS_BAR_STATE);
        PowerManager pm = (PowerManager) this.getSystemService(POWER_SERVICE);
        pWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BatteryWaster");
        pWakeLock.setReferenceCounted(false);
        httpClient = httpClient ==null?  new DefaultHttpClient():httpClient;
        httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,15000);// 请求超时设置
        httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 15000);// 连接超时设置
    }
    private void checkUpdateSys() {
        if (initCheckedThread == null) {
            initCheckedThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!ActivityUtils.haveInternet(UpdateSystem.this)) {// 网络是否通畅
                        Message msg = new Message();
                        msg.what = CHECKED_FAIL_NETWORK;
                        msg.getData().putInt("CHECKED_FAIL_MSG", R.string.wap_network_fail);
                        mHandlerinitChecked.sendMessage(msg);
                    } else {
                        // 请求服务器，获取更新信息
                        Message msg = new Message();
                        int resut = getServiceData();
                        switch (resut) {// 获取服务器端信息
                            case 0:
                                msg.what = CHECKED_SUCCEED;
                                //为保证后续每次都可以读到版本信息
                                initByLocalVersion();
                                break;
                            case R.string.ota_server_not_available:
                                msg.what = ota_server_not_available;
                                break;
                            case R.string.phone_software_version_uptodate:
                                msg.what = phone_software_version_uptodate;
                                break;
                            case 1:
                                msg.what = ota_server_not_available;
                                break;
                        }
                        Log.d(TAG, "checkUpdateSys() sStatus="+sStatus+"---- resut"+resut );
                        mHandlerinitChecked.sendMessage(msg);
                    }
                }
            });
            initCheckedThread.start();
        }
    }
    /**
     *
     * @param element
     *            将要进行遍历的节点，服务器端获取的xml
     *            ADD:在ActivityUtils添加了方法用于把XML的信息保存在名字为ota_version的SharedPreferences中
     */
    private static UpdateInfoEntity upinfo = UpdateInfoEntity.getInstance();
    private void parse(Element element) {
        final Context ctx = getApplicationContext();
        NodeList nodelist = element.getChildNodes();
        int size = nodelist.getLength();
        for (int i = 0; i < size; i++) {
            // 获取特定位置的node
            Node element2 = (Node) nodelist.item(i);
			/*
			 * getNodeName获取tagName，例如<book>thinking in
			 * android</book>这个Element的getNodeName返回book
			 * getNodeType返回当前节点的确切类型，如Element、Attr、Text等 getNodeValue
			 * 返回节点内容，如果当前为Text节点，则返回文本内容；否则会返回null getTextContent
			 * 返回当前节点以及其子代节点的文本字符串，这些字符串会拼成一个字符串给用户返回。例如 对<book><name>thinking
			 * in android</name><price>12.23</price></book>调用此方法，则会返回“thinking
			 * in android12.23”
			 */
            String tagName = element2.getNodeName();
            if (tagName.equals(OtaVersion.OLD_VERSION)) {
                String oldVersion = element2.getTextContent();
                upinfo.setOld_Version(oldVersion);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.OLD_VERSION, oldVersion);
            }
            if (tagName.equals(OtaVersion.NEW_VERSION)) {
                String newVersion = element2.getTextContent();
                upinfo.setNew_Version(newVersion);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.NEW_VERSION, newVersion);
            }
            if (tagName.equals(OtaVersion.DESCRIPTION)) {
                String strDes = element2.getTextContent();
                if ((strDes.indexOf("]]") > -1) && (strDes.indexOf("[") > -1)) {
                    strDes = strDes.substring(strDes.lastIndexOf("[") + 1,
                            strDes.indexOf("]"));
                }
                upinfo.setDescription(strDes);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.DESCRIPTION, strDes);
            }
            if (tagName.equals(OtaVersion.URL)) {
                // modify BUG_ID:QELS-2699 liangyulong 20121217(start)
                String durl = element2.getTextContent().replaceAll(" ", "%20");
                upinfo.setDownloadURL(durl);
                // modify BUG_ID:QELS-2699 liangyulong 20121217(end)
                ActivityUtils.setOtaVersion(ctx, OtaVersion.URL, durl);
            }
            if (tagName.equals(OtaVersion.SIZE)) {
                String xmlSize = element2.getTextContent();
                upinfo.setSize(xmlSize);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.SIZE, xmlSize);
            }
            if (tagName.equals(OtaVersion.PRIORITY)) {
                String priority = element2.getTextContent();
                upinfo.setPriority(priority);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.PRIORITY, priority);
            }
            if (tagName.equals(OtaVersion.DOWNLOAD_BYTE)) {
                int bytes = Integer.parseInt(element2.getTextContent());
                upinfo.setDownloadByte(bytes);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.DOWNLOAD_BYTE, bytes+"");
            }
            if (tagName.equals(OtaVersion.PACKAGE_TYPE)) {
                String type = element2.getTextContent().trim();
                upinfo.setPackageType(type);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.PACKAGE_TYPE, type);
            }
            if (tagName.equals(OtaVersion.SESSION_ID)) {
                String sessionId = element2.getTextContent();
                upinfo.setSessionId(sessionId);
                ActivityUtils.setOtaVersion(ctx, OtaVersion.SESSION_ID, sessionId);
            }
        }
    }
    //根据本地保存的版本信息对于内容为空的升级信息进行初始化
    private void initByLocalVersion(){
        Log.d(TAG, "initByLocalVersion()");
        try {
            final Context ctx = getApplicationContext();
            upinfo = UpdateInfoEntity.getInstance();
            if(upinfo.getNew_Version()==null||upinfo.getNew_Version().equals("")){
                upinfo.setOld_Version(ActivityUtils.getOtaVersion(ctx, OtaVersion.OLD_VERSION));
                upinfo.setNew_Version(ActivityUtils.getOtaVersion(ctx, OtaVersion.NEW_VERSION));
                upinfo.setDescription(ActivityUtils.getOtaVersion(ctx, OtaVersion.DESCRIPTION));
                upinfo.setDownloadURL(ActivityUtils.getOtaVersion(ctx, OtaVersion.URL));
                upinfo.setSize(ActivityUtils.getOtaVersion(ctx, OtaVersion.SIZE));
                upinfo.setPriority(ActivityUtils.getOtaVersion(ctx, OtaVersion.PRIORITY));
                upinfo.setDownloadByte(Integer.parseInt(ActivityUtils.getOtaVersion(ctx, OtaVersion.DOWNLOAD_BYTE)));
                upinfo.setPackageType(ActivityUtils.getOtaVersion(ctx, OtaVersion.PACKAGE_TYPE));
                upinfo.setSessionId(ActivityUtils.getOtaVersion(ctx, OtaVersion.SESSION_ID));
            }
        } catch (Exception e) {
            Log.d(TAG, "initByLocalVersion():"+e.toString());
        }
    }
    //检查下载环境是否满足
    private boolean checkDownload(){
        Log.d(TAG, "checkDownload()  -------"+ residue_cable);
        boolean isSucb = true;
        if (residue_cable <= 20) {
            showOnScreen(R.string.power_is_too_low);
            isSucb = false;
        } else if (!ActivityUtils.isExistSdcard()) {
            showOnScreen(R.string.sdcard_not_exist);
            isSucb = false;
        } else if (ActivityUtils.isExistSdcard()) {
            long lo = ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
            if(upinfo.getSize().contains("B")){
                if(upinfo.getSize().contains("M")&&lo < (Double.parseDouble(upinfo.getSize().replace("MB",""))+50)*1024*1024){
                    showOnScreen(R.string.sdcard_space_not_enough);
                    isSucb = false;
                }else if(lo<51*1024*1024){
                    showOnScreen(R.string.sdcard_space_not_enough);
                    isSucb = false;
                }
            }else if(lo < (Double.parseDouble(upinfo.getSize())+50*1024*1024)) {
                showOnScreen(R.string.sdcard_space_not_enough);
                isSucb = false;
            }
        }
        Log.d(TAG, "checkDownload() isSucb="+isSucb+" ----- batty:" + residue_cable);
        return isSucb;
    }
    private void initActionBar(){
        ActionBar bar = this.getActionBar();
        // delete  BUG_ID: no   zhangyuwen  20140807(start)
        //bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
        // delete  BUG_ID: no   zhangyuwen  20140807(end)		
        bar.setDisplayUseLogoEnabled (false);
    }
    private void otaSetting(){
        Intent intent =new Intent(UpdateSystem.this,com.android.ota.Settings.class);
        startActivity(intent);
    }
    // 获取服务器端更新信息
    private int getServiceData() {
        int in_success = 0;
        HttpGet hget;
        try {
            hget = new HttpGet(servicePath);
            StringBuffer response = new StringBuffer();
            HttpResponse httpResponse = null;
            // 发送GET请求
            try {
                httpResponse = httpClient.execute(hget);
            } catch (Exception e) {
                Log.e(TAG, "Error-HttpResonse:" + e.toString());
                return R.string.ota_server_not_available;
            }
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.d(TAG, "getServiceData()--code--200");
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    // 读取服务器响应
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(entity.getContent()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        // 使用response文本框显示服务器响应
                        response.append(line + "\n");
                    }
                    String str = response.toString();
                    updateSys_State = str.substring(str.indexOf("CHECK_UPDATE_RESULT") + 20,str.lastIndexOf("CHECK_UPDATE_RESULT") + 21);
                    /**
                     * delete for Sales ,liangyulong 20130813 String
                     * register=str
                     * .substring(str.indexOf("CHECK_MARKET_RESULT")+20,
                     * str.lastIndexOf("CHECK_MARKET_RESULT")+21);
                     * if(servicePath.indexOf("ragentek")>-1){
                     * ActivityUtils.registerCellPhone(register); }
                     **/
                    if (updateSys_State.equals("0")) {// 有更新包
                        // 进行xml解析
                        String strXml = str.substring(str.indexOf("<?xml"),
                                str.length());
                        if (debug) {
                            Log.d(TAG, "value xml==" + strXml);
                        }
                        InputStream inputStream = new ByteArrayInputStream(strXml.getBytes());
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(inputStream);
                            // 获取根节点
                            Element root = document.getDocumentElement();
                            parse(root);
                            // 解析完毕后，弹出更新对话框
//							String versionInfo = res.getString(R.string.update_dialog_info, "");
                            //处理掉内容中的版本信息
//							final int fN1 = versionInfo.indexOf("(");
//							final int fN2 = versionInfo.indexOf(")");
//							final String removeStr = versionInfo.substring(fN1-1, fN2+1);
//							versionInfo = versionInfo.replace(removeStr, "");
                            String packInfo = "";
                            if (upinfo.getSize().trim().indexOf("M") > -1) {
                                packInfo = res.getString(R.string.update_dialog_sys_info, upinfo.getSize().trim(), "\n"+upinfo.getDescription().trim());
                            } else {
                                //处理单位小于1M的文件
                                String sizeStr = upinfo.getSize().trim();
                                sizeStr = sizeStr.toLowerCase().replace("kb","");
                                double dSize = Double.parseDouble(sizeStr);
                                double packsize = dSize / 1024 ;
                                String strSize = String.valueOf(packsize);
                                if (strSize.indexOf(".") > -1)strSize = strSize.substring(0,strSize.indexOf(".") + 3);
                                packInfo = res.getString(R.string.update_dialog_sys_info,strSize + " M", upinfo.getDescription().trim());
                            }
                            packInfoANDversionInfo = packInfo;
//							packInfoANDversionInfo = packInfo+"\n\n"+res.getString(R.string.download_dialog_info2);
                            //为保证每次进入都能正确提示新版本信息，将新版本信息保存在SharedPreferences
                            in_success = 0;
                            if(ActivityUtils.getParameter(getApplicationContext(), "packInfoANDversionInfo").equals("")){
                                ActivityUtils.setParameter(getApplicationContext(), "packInfoANDversionInfo", packInfoANDversionInfo);
                            }
                            // showGetHttpMsg(0,versionInfo+"\n\n"+packInfo);//进行提示
                        } catch (Exception e) {
                            in_success = 1;
                            Log.e(TAG, e.toString());
                        }
                    } else {
                        // showGetHttpMsg(R.string.phone_software_version_uptodate,null);
                        return R.string.phone_software_version_uptodate;
                    }
                }
            } else {
                // showErrorMsg(R.string.update_sys_title,R.string.ota_server_not_available);
                return R.string.ota_server_not_available;
            }
            return in_success;
        } catch (Exception e) {
            // showErrorMsg(R.string.update_sys_title,R.string.ota_server_not_available);
            Log.e(TAG, "Error:" + e.toString());
            e.printStackTrace();
        }
        return R.string.ota_server_not_available;
    }
    private Handler mHandlerinitChecked = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int resultFlag = msg.getData().getInt("CHECKED_FAIL_MSG");
            if (initCheckedThread != null) {
                if (!initCheckedThread.isInterrupted()) {
                    initCheckedThread.interrupt();
                }
                initCheckedThread = null;
            }
            Log.d(TAG, "mHandlerinitChecked() ---- msg.what==" + msg.what);
            switch (msg.what) {
                case CHECKED_FAIL:
                    showOnScreen(resultFlag);
                    sStatus = Status.CHECK_FAIL;
                    break;
                case CHECKED_FAIL_NETWORK:
                    showOnScreen(R.string.setnetwork);
                    sStatus = Status.CHECK_FAIL;
                    break;
                case ota_server_not_available:
                    showOnScreen(R.string.ota_server_not_available);
                    sStatus = Status.CHECK_FAIL;
                    break;
                case phone_software_version_uptodate:
                    //代表当前版本是最新版本
                    sStatus = Status.CHECK_NO_VERSION;
                    showOnScreen(R.string.version_is_newest);
                    updateSystemProcess();
                    break;
                case CHECKED_SUCCEED:
                    if (upinfo.getPriority().equals("Optional")) {
                        // 普通更新包
                        ActivityUtils.setParameter(UpdateSystem.this,"ota_priority", upinfo.getPriority());
                        if(sStatus!=Status.DOWNLOADING){
                            sStatus = Status.CHECK_SUCCESS;
                            updateSystemProcess();
                        }
                    } else {// 高级优先包
                        ActivityUtils.setParameter(UpdateSystem.this,"ota_priority", upinfo.getPriority());
                        if(sStatus!=Status.DOWNLOADING){
                            sStatus = Status.CHECK_SUCCESS;
                            updateSystemProcess();
                        }
                        if(checkDownload()){
                            if(!isDownloading&&sStatus!=Status.DOWNLOADING){
                                showDownloadTips();
                            }
                        }
                    }
                    break;
            }
            if(sStatus==Status.CHECK_FAIL){
                Log.d(TAG, "dexin:7 ------- sStatus:"+sStatus);
                caseProcess();
            }
        };
    };

    /**
     * download system update
     * */
    private void downloadDataIntent() {
        String fiName = upinfo.getDownloadURL();
        fiName = fiName.substring(fiName.lastIndexOf("/") + 1, fiName.length());
        fiName = ActivityUtils.getSdcardPath() + fiName;
        File file = new File(fiName);// 文件保存目录
        Log.d(TAG, "downloadDataIntent() --------- upinfo.new"+upinfo.getNew_Version());
        try {
            if (file.exists()) {
                if (upinfo.getPackageType().equals("Complete")) {
                    if (debug)
                        Log.d(TAG, "getPackageType==" + upinfo.getPackageType());
                    ActivityUtils.verifyCompletePackage(file, null, TAG);
                } else {
                    if (debug)
                        Log.d(TAG, "getPackageType==" + upinfo.getPackageType());
                    ActivityUtils.verifyPackage(file, null, TAG);
                }
                file.delete();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            sStatus = Status.DOWNLOAD_ERROR_PACKAGE;
            e.printStackTrace();
        } finally {
            // add BUG_ID:QYLE-2700 20120718 yulong.liang start
            Intent inte = new Intent();
            inte.setAction("android.intent.action.AnHourDOWNLOAD_DeleteIntent");
            sendBroadcast(inte);// 删除通知栏通知
            // add BUG_ID:QYLE-2700 20120718 yulong.liang end
            if(sStatus !=Status.DOWNLOADING){
                Intent intent = new Intent(UpdateSystem.this,NotificationDownloadService.class);
                intent.putExtra("downloadUrl", upinfo.getDownloadURL());
                intent.putExtra("downloadByte", upinfo.getDownloadByte());
                intent.putExtra("packageType", upinfo.getPackageType());
                startService(intent);
                sStatus = Status.DOWNLOADING;
                updateSystemProcess();
                isDownloading = true;
            }else if(sStatus!=Status.CHECK_SUCCESS){
                sStatus = Status.DOWNLOAD_FAIL;
                Log.d(TAG, "dexin:8 ------- sStatus:"+sStatus);
                caseProcess();
            }else{
                Log.d(TAG, "downloadDataIntent      error status, download is can not gone!!!!");
            }
//			ActivityUtils.setParameter(getApplicationContext(),"system_update_process", processDone+"");
            ActivityUtils.setParameter(getApplicationContext(),Status.TAG, sStatus+"");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                isDownloading = false;
                sStatus = Status.DOWNLOAD_FAIL;
            }

        }
        // 停止一小时对话框
        // stopService(new Intent(this, AnHourLaterNotificationService.class));
    }
    /**
     * 获取一些软件版本信息
     *
     * @return
     */
    private String getSerialNumberInformation() {
        Log.d(TAG, "getSerialNumberInformation()");
        servicePath = ActivityUtils.getServerUrl(getApplicationContext());
        String ModelNumber = Build.MODEL;
        // modify BUG_ID:QELS-2699 liangyulong 20121217(start)
        boolean bolNum = getResources().getBoolean(R.bool.config_modelnumber_permit_null);
        if (bolNum == false) {
            ModelNumber = ModelNumber.substring(ModelNumber.indexOf(" ") + 1,ModelNumber.length());
            ModelNumber = ModelNumber.replaceAll(" ", "");
        }
        // modify BUG_ID:QELS-2699 liangyulong 20121217(end)
        String HardwareV = ActivityUtils.getHardwareVersion();
        String IMEI = ActivityUtils.getPhoneIMEI(this);
        String Lastchecktime = ActivityUtils.getParameter(this, "update_date");
        if (Lastchecktime.equals("")) {
            Lastchecktime = this.getResources().getString(R.string.not_check_update);
        }
        int inState = android.provider.Settings.System.getInt(UpdateSystem.this.getContentResolver(), "ota_server_url", 0);
        String sSVersion = Build.DISPLAY;
        if (inState == 1 && servicePath.indexOf("ragentek") > -1) {
            sSVersion = ActivityUtils.getInternalVersion();
//			sSVersion = "J608_CY_L1_V0.5.0_S0804";
        }
        String versionInfo = res.getString(R.string.serialnumberinformation,ModelNumber, HardwareV, sSVersion, IMEI, Lastchecktime);
        ModelNumber = ModelNumber.replaceAll(" ", "%20");
        // modify QELS-4235 wanglu 20130118 start
        if (servicePath.indexOf("ragentek") > -1) {
//			servicePath += "hw=" + ModelNumber + "&hwv=P2&swv="+ sSVersion + "&serialno=" + IMEI+ "&flag=1&version=V1.0&beta="+ ActivityUtils.getRegisterParam(UpdateSystem.this);
            servicePath += "hw=" + ModelNumber + "&hwv=P2&swv="+ ActivityUtils.getInternalVersion() + "&serialno=" + IMEI+ "&flag=1&version=V1.0&beta="+ ActivityUtils.getRegisterParam(UpdateSystem.this);
        } else {
            servicePath += "hw=" + ModelNumber + "&hwv=P2&swv=" + Build.DISPLAY + "&serialno=" + IMEI;
        }
        // modify  BUG_ID: no   zhangyuwen  20140807(start)
        //changeVersionShow(ActivityUtils.getInternalVersion());
        changeVersionShow(ActivityUtils.getExternalVersion());
        // modify  BUG_ID: no   zhangyuwen  20140807(start)
        // modify QELS-4235 wanglu 20130118 end
        // Toast.makeText(this, servicePath, 1).show();
        if (debug)
            Log.d(TAG, servicePath);
        return versionInfo;
    }
    // 电量信息
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                residue_cable = intent.getIntExtra("level", 0);
                pWakeLock.release();
            }
            if (NotificationDownloadService.DOWNLOAD_SERVICE.equals(action)) {
                // 如果后台还在下载完成
                Log.d(TAG, "dexin:9(1) ------- sStatus:"+sStatus);
                if(sStatus==Status.DOWNLOAD_PAUSE){
                    ActivityUtils.setParameter(getApplicationContext(),Status.TAG, Status.DOWNLOAD_PAUSE+"");
                }else if(sStatus!=Status.DOWNLOADING){
                    String statusStr = ActivityUtils.getParameter(getApplicationContext(),Status.TAG);
                    sStatus = statusStr == ""?sStatus:Integer.parseInt(statusStr);
                    if(sStatus==Status.DOWNLOAD_SUCCESS){
                        showScreenTip(R.string.ota_upgrade);
                    }
                }
                Log.d(TAG, "dexin:9 ------- sStatus:"+sStatus);
                caseProcess();
            }
            //对于界面进度条进行同步，只接收当前进度和最大长度即可
            if(NotificationDownloadService.PROGRESS_BAR_STATE.equals(action)){
//				Log.d(TAG, "garan   ----   notifi------b"+isDownloading);
                isDownloading = intent.getExtras().getBoolean("is_downloading");
//				Log.d(TAG, "garan  ------  notifi ------ e "+isDownloading);
                showProgressRate();
            }
        }
    };
    //显示当前下载进度
    private void showProgressRate(){
        final String sizeStr = ActivityUtils.getParameter(getApplicationContext(),"progress");
        int progressSize = sizeStr ==""? 0:Integer.parseInt(sizeStr);
        final String maxStr = ActivityUtils.getParameter(getApplicationContext(),"progressMax");
        int progressMax = maxStr==""? 0: Integer.parseInt(maxStr);
        //如果max为零
        if(progressMax == 0){
            return;
        }
        int rate = (int) ((float) progressSize/ progressMax * 100);
        if(rate>0){
            if(downMsg.getVisibility()!= View.VISIBLE){
                // reset下载状态的时候，判断是否为空，如果packInfoANDversionInfo为空则从SharedPreferences中取出保存的信息
                if (packInfoANDversionInfo == null|| packInfoANDversionInfo.equals("")) {
                    packInfoANDversionInfo = ActivityUtils.getParameter(getApplicationContext(),"packInfoANDversionInfo");
                }
                downloadNewVersion(0, packInfoANDversionInfo);
            }
            downTitle.setVisibility(View.GONE);
            showScreenTip(R.string.download_state_point);
            progressContainer.setLayoutParams(downTitle.getLayoutParams());
            progressContainer.setVisibility(View.VISIBLE);
            progressBar.setMax(progressMax);
            progressBar.setProgress(progressSize);
            //告知当前下载的大小
            progressState.setText(getSizeString(progressSize) + "/"+verPackageSize);
        }
        //当进度走到100的时候，应该做的操作
        if(rate>=100){
            String statusStr = ActivityUtils.getParameter(getApplicationContext(),Status.TAG);
            sStatus = statusStr == "" ? sStatus : Integer.parseInt(statusStr);
            if (sStatus == Status.DOWNLOAD_SUCCESS) {
                showScreenTip(R.string.ota_upgrade);
                hideProgressRate();
                Log.d(TAG, "dexin:10 ------- sStatus:"+sStatus);
                caseProcess();
            }else{
                Log.d(TAG, "showProgressRate()-------   sStatus"+sStatus);
            }
        }
    }

    private void hideProgressRate() {
        // progressBar.setVisibility(View.GONE);
        progressContainer.setVisibility(View.GONE);
        progressBar.setProgress(0);
        downMsg.setVisibility(View.GONE);
        isDownloading = false;
        ActivityUtils.setParameter(getApplicationContext(), "progress", "");
        ActivityUtils.setParameter(getApplicationContext(), "progressMax", "");
    }
    private void showOnScreen(int text){
        downTitle.setVisibility(View.GONE);
        downMsg.setVisibility(View.GONE);
        screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
        screenText.setText(text);
        screenText.setVisibility(View.VISIBLE);
    }
    private void hideScreenText(){
        screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
        screenText.setVisibility(View.GONE);
        downTitle = downTitle == null? (TextView) findViewById(R.id.system_update_title):downTitle;
        downTitle.setVisibility(View.GONE);
        downMsg = downMsg == null?(TextView) findViewById(R.id.system_update_text):downMsg;
        downMsg.setVisibility(View.GONE);
    }
    private void showScreenTip(int text){
        screenTip = screenTip ==null?(TextView) findViewById(R.id.ota_message_tips):screenTip;
        screenTip.setVisibility(View.VISIBLE);
        screenTip.setText(text);
    }
    private void hideScreenTip(){
        screenTip = screenTip ==null?(TextView) findViewById(R.id.ota_message_tips):screenTip;
        screenTip.setText(" ");
    }
    private void changeVersionShow(String text){
        Log.d(TAG, "changeVersionShow --------text="+text);
        nowVersion = nowVersion == null ? (TextView)findViewById(R.id.now_version):nowVersion;
        if(text!=null&&!text.equals("")){
            text = res.getString(R.string.ota_version_base, text);
            nowVersion.setText(text);
            nowVersion.setVisibility(View.VISIBLE);
        }else{
            nowVersion.setVisibility(View.GONE);
        }
    }
    //检查本地是否有update.zip文件
    private boolean checkLocalPackage() {
        Log.d(TAG, "checkPackage() --- upinfo = "+upinfo);
        int inState = android.provider.Settings.System.getInt(UpdateSystem.this.getContentResolver(),"ota_server_url", 0);
        File localFile =null;
        if (inState == 1) {
            //为确保版本信息没有问题，在使用前对于该信息进行判断
            try {
                if(upinfo.getNew_Version()==null||upinfo.getNew_Version().equals("")){
                    initByLocalVersion();
                }
                //String fiName = upinfo.getDownloadURL();
                //fiName = fiName.substring(fiName.lastIndexOf("/") + 1,fiName.length());
                //fiName = ActivityUtils.getSdcardPath() + fiName;
                String fiName = upinfo.getDownloadURL();
                if(fiName == null || "".equals(fiName)) { // ota file not exists
                    // check local sdcard/update.zip
                    fiName = "/sdcard/update.zip";
                } else {
                    fiName = fiName.substring(fiName.lastIndexOf("/") + 1,fiName.length());
                    fiName = ActivityUtils.getSdcardPath() + fiName;
                }
                localFile = new File(fiName);// 文件保存目录
            } catch (Exception e) {
                Log.d(TAG, "checkLocalPackage():"+e.toString());
                return false;
            }
        }
        return (localFile ==null?false:localFile.exists());
    }
    //A:BUG_ID:JLLB-3714 wuqi 20140808 (start)
    //检查本地文件是否有问题
    private boolean isLocalPagkageCanUpdate() {
        String fiName = upinfo.getDownloadURL();
        if(fiName == null || "".equals(fiName)) { // ota file not exists
            // check local sdcard/update.zip
            fiName = "/sdcard/update.zip";
        } else {
            fiName = fiName.substring(fiName.lastIndexOf("/") + 1,fiName.length());
            fiName = ActivityUtils.getSdcardPath() + fiName;
        }
        File localFile = new File(fiName);// 文件保存目录
        try {
            if (localFile.exists()) {
                if (upinfo.getPackageType()!=null&&upinfo.getPackageType().equals("Complete")) {
                    ActivityUtils.verifyCompletePackage(localFile, null,TAG);
                } else {
                    ActivityUtils.verifyPackage(localFile, null, TAG);
                }
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "isLocalPagkageCanUpdate():" + e.toString());
            sStatus = Status.UPGRADE_FAIL;
            return false;
        }
    }
    //A:BUG_ID:JLLB-3714 wuqi 20140808 (  end)
    private String getSizeString(long size) {
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size * 100 / 1024;
        }
        return String.valueOf((size / 100)) + "."+ ((size % 100) < 10 ? "0" : "") + String.valueOf((size % 100))+ "MB";
    }
}
