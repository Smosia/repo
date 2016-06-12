package com.android.ota;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.HttpConnectionParams;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
import android.app.Activity;
//import android.app.PendingIntent;
//import android.app.ActionBar;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.res.Resources;
//import android.graphics.drawable.AnimationDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.PowerManager;
//import android.text.method.ScrollingMovementMethod;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.ota.R;
public class MainActivity extends Activity {/*

	private static final String TAG="MainActivity:Tiow";
	private Button systemUpdateBtn;
	private int processDone = 0;
	private Resources res =null;
	private TextView screenText = null;
	private TextView screenTip = null;
	private TextView nowVersion =null;
	private IntentFilter iFilter;
	private PowerManager.WakeLock pWakeLock;
	private final static int PROCESS_CHECK = 1;
    private final static int PROCESS_DOWNLOAD = 2;
//    private final static int PROCESS_BACKUP = 3;
//    private final static int PROCESS_UPGRADE = 4;
    private final static int PROCESS_UPGRADE = 3;
    private int residue_cable;
    private Thread initCheckedThread = null;
	private final int CHECKED_FAIL = 1;
	private final int CHECKED_FAIL_NETWORK = 2;
	private final int CHECKED_SUCCEED = 3;
	private final int ota_server_not_available = 4;
	private final int phone_software_version_uptodate = 5;
	private final int CHECKED_FAIL_EXTERNAL_STORAGE = 6;// zhaochunqing add
														// 20120903
	private boolean debug = false;
	private boolean isDownloading = false;
	private boolean checkPackage = false;
	HttpClient httpClient;
	// private String
	// servicePath="http://www.coolpadfuns.com:8080/updsvr/ota/checkupdate?hw=7018&hwv=V1.10&swv=2.3.001.120501.7018&serialno=864670010001838";

	private String servicePath = "";
	private String updateSys_State = null;
	private String packInfoANDversionInfo = "";

	PendingIntent pi;
	private static final String CHECK_DOWNLOAD_BY_SERVICE = "processDoneFromNotification";
//	private static final String DOWNLOAD_SERVICE = "com";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        initSys();
//        String processDoneStr = ActivityUtils.getParameter(getApplicationContext(), "system_update_process");
//    	processDone = processDoneStr == "" ? processDone : Integer.parseInt(processDoneStr);
//        if(processDone == 0){
//        	processGoing(processDone);
//        	initButton();
//        }
        Log.d(TAG, "onCreate() -------- processDone:"+processDone+" --------- servicePath:"+servicePath);
    }
    @Override
	protected void onResume() {
    	try {
    		initSys();
        	String processDoneStr = ActivityUtils.getParameter(getApplicationContext(), "system_update_process");
        	processDone = processDoneStr == "" ? processDone : Integer.parseInt(processDoneStr);
        	checkPackage = ActivityUtils.getParameter(MainActivity.this,"verify_package").equals("yes");
    		isDownloading = ActivityUtils.getParameter(MainActivity.this,"download_state").equals("yes");
    		Log.d(TAG, "onResume() ----- processDone=" + processDone+ "------ checkPackage=" + checkPackage + " ---------- isDownloading=" + isDownloading);
        	registerReceiver(mReceiver, iFilter);
        	pWakeLock.acquire();
        	if(checkLocalPackage()){
        		checkPackage = true;
        		processDone = PROCESS_UPGRADE;
        	}
            if(processDone == 0){
            	processGoing(processDone);
            	initButton();
            }else{
            	resetProcess();
            }
            Log.d(TAG, "onResume() -------- processDone="+processDone);
		} catch (Exception e) {
			Log.e(TAG, "onResume()"+e.getLocalizedMessage());
		}
		super.onResume();
	}
    @Override
    protected void onPause() {
    	Log.d(TAG, "onPause() -------- processDone="+processDone);
    	hideScreenText();
		//΋³�жϼ쳩ˇ·񻺔ڼ쳩П³̣¬ɧ¹�³�¯processDone
		if (initCheckedThread != null) {
			initCheckedThread.interrupt();
			initCheckedThread = null;
			processDone = 0;
		}
		//΋³�жϼ쳩½ẻ£¬ɧ¹�½ẻΪfalseղ³�¯processDone
		if(!checkResult){
			processDone = 0;
		}
    	ActivityUtils.setParameter(getApplicationContext(),"system_update_process", processDone+"");
    	super.onPause();
    }
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy() -------- processDone="+processDone);
		checkResult = null;
//		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
	    	menu.add(0,1,1, R.string.ota_settings);
	    	menu.add(0,2,2, R.string.local_update);
//			int inState = android.provider.Settings.System.getInt(MainActivity.this.getContentResolver(), "ota_server_url", 0);
//			if (inState == 1 && servicePath.indexOf("ragentek") > -1) {
//			}
	    	initActionBar();
			return true;
		}
	    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			otaSetting();
			break;
		case 2:
			// local updates
			int inState = android.provider.Settings.System.getInt(MainActivity.this.getContentResolver(),"ota_server_url", 0);
			if (inState == 1) {
				File file = new File("/sdcard/update.zip");
				if (file.exists()) {
					Intent intent = new Intent(MainActivity.this,MtkUpgradeRestart.class);
					intent.putExtra("filePath", "/sdcard/update.zip");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else {
					showOnScreen(R.string.notexists_package_local);
				}
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
//	    @Override
//	    public boolean onKeyDown(int keyCode, KeyEvent event) {
//	    	if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//	    		Log.d(TAG,"onKeyDown()   1   ---------------  keyCode="+keyCode);
//	    		keyCode = KeyEvent.KEYCODE_HOME;
//	    		Log.d(TAG,"onKeyDown()   2  ---------------  keyCode="+keyCode+"      key:[back="+ KeyEvent.KEYCODE_BACK+",home="+KeyEvent.KEYCODE_HOME);
//	    	}
//	    	return super.onKeyDown(keyCode, event);
//	    }
	private void resetProcess() {
		Log.d(TAG, "resetProcess() ------  processDone = " + processDone);
		//ɴˇ²»՚¶¨ӥµŁ�є󖱽ӷµ»֍
		if (processDone == 4) {
			return;
		}
		int processNow = processDone;
		for (processDone = 0; processDone <= processNow; processDone++) {
			if (processDone == PROCESS_CHECK) {
				Log.d(TAG, "resetProcess     for----------------processDone = "+processDone);
				checkSystemUpdate();
				if(checkResult){
					lineGoing(PROCESS_CHECK);
					initButton();
				}
			}
			if (processDone == PROCESS_DOWNLOAD) {
				Log.d(TAG, "resetProcess     for----------------processDone = "+processDone);
				if (isDownloading) {
					lineGoing(PROCESS_DOWNLOAD);
					initButton();
					//resetЂ՘״̬µŊ±º򣭅жЊǷ򎨿֣¬ɧ¹�InfoANDversionInfoΪ¿֔򵒓haredPreferencesאȡ³�涄хϢ
					if(packInfoANDversionInfo.equals("")||packInfoANDversionInfo==null){
						packInfoANDversionInfo = ActivityUtils.getParameter(getApplicationContext(), "packInfoANDversionInfo");
					}
					downloadNewVersion(0, packInfoANDversionInfo);
				}
			}
			if (processDone == PROCESS_UPGRADE) {
				Log.d(TAG, "resetProcess     for----------------processDone = "+processDone);
				String path = ActivityUtils.getParameter(MainActivity.this,"sdcard_updatepackage_URL");
				File file = new File(path);
				lineGoing(PROCESS_UPGRADE);
				initButton();
				if (checkLocalPackage()) {
					showScreenTip(R.string.ota_upgrade);
				}
			}
		}
		processDone = processNow;
	}
	private Drawable processPiontWaitForAction = null;
	private Drawable processPiontFail = null;
	private Drawable processPiontOk = null;
	private Drawable refresh = null;
	private Drawable download = null;
	private Drawable upgrade = null;
	private Drawable gone = null;
	private ImageView processCheck = null;
	private ImageView processDownload = null;
	private ImageView processUpgrade = null;
	private void initResourse(){
		Log.d(TAG, "initResourse()");
		res = res == null?this.getResources():res;
		gone = gone==null? res.getDrawable(R.drawable.progress_finish):gone;
		processPiontWaitForAction = processPiontWaitForAction == null ? res .getDrawable(R.drawable.progress_doing) : processPiontWaitForAction;
		processPiontFail = processPiontFail == null ? res .getDrawable(R.drawable.progress_over_normal_fail) : processPiontFail;
		processPiontOk = processPiontOk == null ? res .getDrawable(R.drawable.progress_over_normal_right) : processPiontOk;
		refresh = refresh == null ? res.getDrawable(R.drawable.button_refresh) : refresh;
		download = download == null ? res.getDrawable(R.drawable.button_download) : download;
		upgrade = upgrade == null ? res.getDrawable(R.drawable.button_upgrade): upgrade;
		processCheck = processCheck == null ? (ImageView) findViewById(R.id.process_piont_check) : processCheck;
		processDownload = processDownload == null ? (ImageView) findViewById(R.id.process_piont_download) : processDownload;
		processUpgrade = processUpgrade == null ? (ImageView) findViewById(R.id.process_piont_upgrade) : processUpgrade;
		//»򈡵±ǰ°汾хϢ
		getSerialNumberInformation();
	}
    //checkResult
    private static Boolean checkResult = null;
    private void initButton(){
    	Log.d(TAG, "initButton():checkResult="+checkResult+"---processDone="+processDone);
    	if(checkResult == null){
    		
    	}else if(checkResult){
			processCheck.setBackgroundDrawable(processPiontOk);
		}else{
			processCheck.setBackgroundDrawable(processPiontFail);
		}
		systemUpdateBtn = (Button) findViewById(R.id.system_update_btn);
		switch (processDone) {
		case PROCESS_CHECK:
			systemUpdateBtn.setBackgroundDrawable(refresh);
			systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					Button btn = (Button) v;
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						btn.setBackgroundResource(R.drawable.refresh_down);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						btn.setBackgroundResource(R.drawable.refresh);
						//wait for check result
						lineGoing(PROCESS_CHECK);
						processCheck.setBackgroundDrawable(processPiontWaitForAction);
						checkSystemUpdate();
					}
					return false;
				}
			});
			break;
		case PROCESS_DOWNLOAD:
			//´¦mcheckingͼю±仯£¬²¢¼쳩Ђ՘»·¾³
			processCheck.setBackgroundDrawable(processPiontOk);
			boolean canDownload = checkDownload();
			if(canDownload&&checkResult){
				//ɧ¹�»·¾³OK£¬´¦mdownloadµĽ脦±仯
				lineGoing(PROCESS_DOWNLOAD);
				processDownload.setBackgroundDrawable(processPiontWaitForAction);
			}else{
				showScreenTip(R.string.can_not_download);
				processCheck.setBackgroundDrawable(processPiontFail);
			}
			systemUpdateBtn.setBackgroundDrawable(download);
			systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					Button btn = (Button) v;
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						btn.setBackgroundResource(R.drawable.download_down);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						btn.setBackgroundResource(R.drawable.download);
						try {
							if(!isDownloading){
								//¿ªʼЂ՘
								showScreenTip(R.string.download_state_point);
								downloadDataIntent();
							}else{
								showScreenTip(R.string.download_state_point);
							}
						} catch (Exception e) {
							Log.d(TAG, "initButton:["+processDone+"] :"+e.toString());
							processDownload.setBackgroundDrawable(processPiontFail);
						}
					}
					return false;
				}
			});
			break;
//		case PROCESS_BACKUP:
//			systemUpdateBtn.setBackgroundDrawable(refresh);
//			systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
//				public boolean onTouch(View v, MotionEvent event) {
//					Button btn = (Button) v;
//					if (event.getAction() == MotionEvent.ACTION_DOWN) {
//						btn.setBackgroundResource(R.drawable.refresh_down);
//					} else if (event.getAction() == MotionEvent.ACTION_UP) {
//						btn.setBackgroundResource(R.drawable.refresh);
//						processGone(processDone);
//					}
//					return false;
//				}
//			});
//			break;
		case PROCESS_UPGRADE:
			//´¦mdownloadµĽ脦±仯,²¢¼쳩°�떻Ӕ¼°°²װµɊÒɍ
			if(checkPackage){
				processDownload.setBackgroundDrawable(processPiontOk);
				lineGoing(PROCESS_UPGRADE);
				processUpgrade.setBackgroundDrawable(processPiontWaitForAction);
			}else{
				processDownload.setBackgroundDrawable(processPiontFail);
			}
			systemUpdateBtn.setBackgroundDrawable(upgrade);
			systemUpdateBtn.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					Button btn = (Button) v;
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						btn.setBackgroundResource(R.drawable.upgrade_down);
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						btn.setBackgroundResource(R.drawable.upgrade);
						Toast.makeText(getApplicationContext(), "checkPackeage = "+checkPackage, Toast.LENGTH_SHORT).show();
						if(checkPackage){
							//ɧ¹�OK£¬טǴ°²װ
							upGrade();
						}else{
							//ɧ¹�Fail£¬͡ʾטтЂ՘
							reDownload(null);
						}
					}
					return false;
				}
			});
			break;
		default:
			Toast.makeText(getApplicationContext(), "initButton warm", Toast.LENGTH_SHORT);
			break;
		}
	}
    private boolean processGone(final int processDone){
    	Log.d(TAG, "processGone() ---- processDone = "+processDone);
    	boolean goneProcessFlag = false;
    	try {
    		switch (processDone) {
    		case PROCESS_CHECK:
    			lineGoing(PROCESS_CHECK);
    			processCheck.setBackgroundDrawable(processPiontWaitForAction);
    			goneProcessFlag = checkSystemUpdate();
    			if(goneProcessFlag && checkDownload()){
    				processCheck.setBackgroundDrawable(processPiontOk);
    				lineGoing(processDone);
					processDownload.setBackgroundDrawable(processPiontWaitForAction);
					showScreenTip(R.string.ota_download_version);
    				return true;
    			}else{
    				this.processDone = 0;
    				processCheck.setBackgroundDrawable(processPiontOk);
    			}
    		case PROCESS_DOWNLOAD:
    			processCheck.setBackgroundDrawable(processPiontOk);
    			lineGoing(PROCESS_DOWNLOAD);
    			processDownload.setBackgroundDrawable(processPiontWaitForAction);
    			goneProcessFlag = downloadNewVersion(phone_software_version_uptodate,null);
    			if(goneProcessFlag){
    				processDownload.setBackgroundDrawable(processPiontOk);
    				return true;
    			}
    			return true;
//    		case PROCESS_BACKUP:
//    			lineGoing(PROCESS_BACKUP);
//    			ImageView processBackup = (ImageView) findViewById(R.id.process_piont_backup);
//    			processBackup.setBackgroundDrawable(processPiontWaitForAction);
//    			goneProcessFlag = backupData();
//    			if(goneProcessFlag){
//    				processBackup.setBackgroundDrawable(processPiontOk);
//    				return true;
//    			}
//    			return true;
    		case PROCESS_UPGRADE:
    			lineGoing(PROCESS_UPGRADE);
    			ImageView processUpgrade = (ImageView) findViewById(R.id.process_piont_upgrade);
    			processUpgrade.setBackgroundDrawable(processPiontWaitForAction);
//    			goneProcessFlag = upGrade();
//    			if(goneProcessFlag){
//    				processUpgrade.setBackgroundDrawable(processPiontOk);
//    				return true;
//    			}
    			return true;
    		default:
    			Toast.makeText(getApplicationContext(), "warm", Toast.LENGTH_SHORT);
    			return false;
    		}
		} catch (Exception e) {
			Log.e(TAG, "processGone():"+e.getMessage());
			return false;
		}
    }
    private void lineGoing(int processDone){
    	Log.d(TAG, "lineGoing()  ---- processDone = "+ processDone);
    	ImageView line = null;
    	switch (processDone) {
		case PROCESS_CHECK:
			line = (ImageView) findViewById(R.id.process_line_1);
			line.setBackgroundDrawable(gone);
			break;
		case PROCESS_DOWNLOAD:
			line = (ImageView) findViewById(R.id.process_line_2);
			line.setBackgroundDrawable(gone);
			line = (ImageView) findViewById(R.id.process_line_3);
			line.setBackgroundDrawable(gone);
			break;
		case PROCESS_UPGRADE:
			line = (ImageView) findViewById(R.id.process_line_4);
			line.setBackgroundDrawable(gone);
			line = (ImageView) findViewById(R.id.process_line_5);
			line.setBackgroundDrawable(gone);
			break;
//		case PROCESS_BACKUP:
//			line = (ImageView) findViewById(R.id.process_line_6);
//			line.setBackgroundDrawable(gone);
//			line = (ImageView) findViewById(R.id.process_line_7);
//			line.setBackgroundDrawable(gone);
//			break;
		default:
//			line = (ImageView) findViewById(R.id.process_line_8);
//			line.setBackgroundDrawable(gone);
			//never exists this result
			break;
		}
    }
    private boolean checkSystemUpdate(){
    	Log.d(TAG, "checkSystemUpdate()");
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
    	//Ɛ¶ϵ±ǰˇ·򓑾­՚Ђ՘
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
    private TextView downTitle = null;
    private TextView downMsg = null;
	private boolean downloadNewVersion(int msg , String msgText) {
		processCheck.setBackgroundDrawable(processPiontOk);
		Log.d(TAG, "downloadNewVersion()----- msg="+msg+"-----msgText="+ (msgText == null?"null":msgText.equals("")));
		SimpleDateFormat simdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ActivityUtils.setParameter(this, "update_date",simdate.format(new Date()));
		if (msg != R.string.phone_software_version_uptodate) {// ±ѨҪ¸�		screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
			screenText.setVisibility(View.GONE);
			downTitle = downTitle == null ? (TextView) findViewById(R.id.system_update_title) : downTitle;
			downMsg = downMsg == null ? (TextView) findViewById(R.id.system_update_text) : downMsg;
			// ·V¹τ±¾¹�¹τ±¾¿ʒԽ�¶¯,¶Փ¦µømlҲѨҪʨ׃˴є
			downMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
			// ¹¹լт°汾хϢ
			String versionInfo = res.getString(R.string.download_dialog_info1,upinfo.getNew_Version().trim());
			versionInfo = versionInfo + "\n\n" + MainActivity.this.getString(R.string.show_network_cost);
			// °Ҏļ��®֗¡¯
			String packInfo = "";
			String packSize = "0";
			if (upinfo.getSize().trim().indexOf("M") > -1) {
				packSize = res.getString(R.string.ota_new_version,upinfo.getSize().trim());
				packInfo = res.getString(R.string.update_dialog_sys_info, upinfo .getSize().trim(), upinfo.getDescription().trim());
			} else {
				double dSize = Double.parseDouble(upinfo.getSize().trim());
				double packsize = dSize / 1024 / 1024;
				String strSize = String.valueOf(packsize);
				if (strSize.indexOf(".") > -1)
					strSize = strSize.substring(0, strSize.indexOf(".") + 3);
				packInfo = res.getString(R.string.update_dialog_sys_info, strSize + " M", upinfo.getDescription().trim());
			}
			versionInfo = versionInfo + "\n\n" + packInfo + "\n" + MainActivity.this.getString(R.string.download_dialog_info2);
			downTitle.setText(packSize);
			downMsg.setText(versionInfo);
			downTitle.setVisibility(View.VISIBLE);
			downMsg.setVisibility(View.VISIBLE);
			showScreenTip(R.string.ota_download_version);
			return true;
		}
		return false;
	}
//    private boolean backupData(){
//    	processGoing(processDone);
//    	return true;
//    }
	private boolean upGrade() {
		processGoing(processDone);
		// טǴ´�		String path = ActivityUtils.getParameter(MainActivity.this,"sdcard_updatepackage_URL");
		Log.d(TAG, "install file:" + path);
		File file = new File(path);
		ActivityUtils.setParameter(getApplicationContext(),"sdcard_updatepackage_URL", "");
		ActivityUtils.setParameter(getApplicationContext(),"system_update_process", "");
		if (file.exists()) {
//			bolInstall = true;
			ActivityUtils.setParameter(MainActivity.this,"delaytime_count", "");
			ActivityUtils.setParameter(getApplicationContext(),"sdcard_updatepackage_URL", "");
			
//			 Intent intent = new Intent("com.qualcomm.update.REBOOT");
//			 intent.setData(Uri.fromFile(file)); intent.putExtra("ota_update",
//			 "true");
//			 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Ȑ¼ǣ¬²»¼Ӵ˾䣬Ϟ·¨ʵЖ͸ת
//			 startActivity(intent);
			
			Intent intent = new Intent(MainActivity.this,MtkUpgradeRestart.class);
			intent.putExtra("filePath", path);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			MainActivity.this.finish();
		} else {
//			bolexistpackage = true;
			Intent itt2 = new Intent(MainActivity.this,DelayUpdateDialog.class);
			ActivityUtils.delayNotify(MainActivity.this, itt2, false);// ȡлӔǰµō¨֪
			ActivityUtils.setParameter(MainActivity.this,"delaytime_count", "");
			Toast.makeText(MainActivity.this, R.string.notexists_package,1).show();
			MainActivity.this.finish();
		}
		return true;
	}
    private boolean processGoing(int processDone){
    	Log.d(TAG, "processGoing() ----- processDone="+processDone);
    	if(processDone == 4){
			//ֻ¸�̽⋸º󽫸òϊ�ª¿Ӎ
			ActivityUtils.setParameter(getApplicationContext(),"system_update_process", "");
			processDone = 0;
		}
		final Integer[] process = {0,PROCESS_CHECK, PROCESS_DOWNLOAD, PROCESS_UPGRADE };
		for (Integer integer : process) {
			if(processDone == integer && processDone < 4 ){
	    		this.processDone++;
	    		return true;
	    	}
		}
		Toast.makeText(getApplicationContext(), "System update process error !", Toast.LENGTH_SHORT);
		return false;
    }
    private void initSys(){
    	Log.d(TAG, "initSys()");
		initResourse();
		iFilter = new IntentFilter();// ע²ὠͽ
		iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
		iFilter.addAction(NotificationDownloadService.DOWNLOAD_SERVICE);
		PowerManager pm = (PowerManager) this.getSystemService(POWER_SERVICE);
		pWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BatteryWaster");
		pWakeLock.setReferenceCounted(false);
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,15000);// ȫȳ³¬ʱʨ׃
		httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 15000);// l½ӳ¬ʱʨ׃
	}
    private void checkUpdateSys() {
    	Log.d(TAG, "checkUpdateSys()");
//    	hideScreenTip();
//    	checkIsNewVersion();
    	if (initCheckedThread == null) {
			initCheckedThread = new Thread(new Runnable() {
				@Override
				public void run() {
					if (!ActivityUtils.haveInternet(MainActivity.this)) {// θçˇ·򍨳©
						Message msg = new Message();
						msg.what = CHECKED_FAIL_NETWORK;
						msg.getData().putInt("CHECKED_FAIL_MSG", R.string.wap_network_fail);
						mHandlerinitChecked.sendMessage(msg);
					} else {
						// ȫȳ·�¬»򈡸�¢
						Message msg = new Message();
						int resut = getServiceData();
						switch (resut) {// »򈡷�̐Ə¢
						case 0:
							msg.what = CHECKED_SUCCEED;
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
						mHandlerinitChecked.sendMessage(msg);
					}
				}
			});
			initCheckedThread.start();
		}
    }
	private static UpdateInfoEntity upinfo = new UpdateInfoEntity();

	private void parse(Element element) {
		NodeList nodelist = element.getChildNodes();
		int size = nodelist.getLength();
		for (int i = 0; i < size; i++) {
			// »򈢌ض¨λ׃µîode
			Node element2 = (Node) nodelist.item(i);
			
//			 getNodeName»򈠴agName£¬}ɧ<book>thinking in
//			 android</book>֢¸�mentµçetNodeName·µ»עook
//			 getNodeType·µ»ص±ǰ½ڵ㶄ȷȐ`э£¬ɧElement¡¢Attr¡¢TextµƠgetNodeValue
//			 ·µ»ؽڵ䅚ɝ£¬ɧ¹�ΪText½ڵ㣬ղ·µ»َı¾Śɝ£»·򕲻᷵»׮ull getTextContent
//			 ·µ»ص±ǰ½ڵ䓔¼°Ǥؓ´�µŎı¾ؖ·�֢Щؖ·�ƴ³ʒ»¸��ԃ»§·µ»ء£}ɧ ¶Ҽbook><name>thinking
//			 in android</name><price>12.23</price></book>µ�˷½·¨£¬ղ»᷵»ء°thinking
//			 in android12.23¡±
			
			String tagName = element2.getNodeName();
			if (tagName.equals("srcVersion")) {
				upinfo.setOld_Version(element2.getTextContent());
			}
			if (tagName.equals("dstVersion")) {
				upinfo.setNew_Version(element2.getTextContent());
			}
			if (tagName.equals("description")) {
				String strDes = element2.getTextContent();
				if ((strDes.indexOf("]]") > -1) && (strDes.indexOf("[") > -1)) {
					strDes = strDes.substring(strDes.lastIndexOf("[") + 1,
							strDes.indexOf("]"));
				}
				upinfo.setDescription(strDes);
			}
			if (tagName.equals("downloadURL")) {
				// modify BUG_ID:QELS-2699 liangyulong 20121217(start)
				String durl = element2.getTextContent().replaceAll(" ", "%20");
				upinfo.setDownloadURL(durl);
				// modify BUG_ID:QELS-2699 liangyulong 20121217(end)
			}
			if (tagName.equals("size")) {
				upinfo.setSize(element2.getTextContent());
			}
			if (tagName.equals("priority")) {
				upinfo.setPriority(element2.getTextContent());
			}
			if (tagName.equals("downloadByte")) {
				upinfo.setDownloadByte(Integer.parseInt(element2
						.getTextContent()));
			}
			if (tagName.equals("packageType")) {
				upinfo.setPackageType(element2.getTextContent().trim());
			}
			if (tagName.equals("sessionId")) {
				upinfo.setSessionId(element2.getTextContent());
			}
		}
	}
	//¼쳩Ђ՘»·¾³ˇ·򃻗ዊ	private boolean checkDownload(){
		boolean isSucb = true;
		if(ActivityUtils.CheckNetworkState(MainActivity.this).equals("mobile")){//Ɛ¶ϵ±ǰl½ӵŊƷifi£¬»¹ˇmobile;
			showOnScreen(R.string.setnetwork_open);
			isSucb = false;
		}else if (residue_cable <= 20) {
			showOnScreen(R.string.power_is_too_low);
			isSucb = false;
		} else if (!ActivityUtils.isExistSdcard()) {
			showOnScreen(R.string.sdcard_not_exist);
			isSucb = false;
		} else if (ActivityUtils.isExistSdcard()) {
			long lo = ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
			double size = lo / 1024 / 1024;
			if (size < 300) {
				showOnScreen(R.string.sdcard_space_not_enough);
				isSucb = false;
			}
		}
		Log.d(TAG, "checkDownload() isSucb="+isSucb+" ----- batty:" + residue_cable);
		return isSucb;
	}
	private void initActionBar(){
		ActionBar bar = this.getActionBar();
		bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
		bar.setDisplayUseLogoEnabled (false);
	}
	private void otaSetting(){
		 Intent intent =new Intent(MainActivity.this,com.android.ota.Settings.class);
		 startActivity(intent);
	}
 // »򈡷�˸�¢
	private int getServiceData() {
		int in_success = 0;
		HttpGet hget;
		try {
			hget = new HttpGet(servicePath);
			StringBuffer response = new StringBuffer();
			HttpResponse httpResponse = null;
			// ·¢̍GETȫȳ
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
					// ¶¡·�퓦
					BufferedReader br = new BufferedReader(
							new InputStreamReader(entity.getContent()));
					String line = null;
					while ((line = br.readLine()) != null) {
						// ʹԃresponseτ±¾¿󐕊¾·�퓦
						response.append(line + "\n");
					}
					String str = response.toString();
					updateSys_State = str.substring(str.indexOf("CHECK_UPDATE_RESULT") + 20,str.lastIndexOf("CHECK_UPDATE_RESULT") + 21);
//					 delete for Sales ,liangyulong 20130813 String
//					 register=str
//					 .substring(str.indexOf("CHECK_MARKET_RESULT")+20,
//					 str.lastIndexOf("CHECK_MARKET_RESULT")+21);
//					 if(servicePath.indexOf("ragentek")>-1){
//					 ActivityUtils.registerCellPhone(register); }
//					 
					if (updateSys_State.equals("0")) {// Ԑ¸�
						// ½�l½㏶
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
							// »򈡸�
							Element root = document.getDocumentElement();
							parse(root);
							// ½㏶Ϊ±Ϻ󣬵¯³�¶Ի°¿눉						Resources res = getResources();
							String versionInfo = res.getString(R.string.update_dialog_info, upinfo.getNew_Version().trim());
							String packInfo = "";
							if (upinfo.getSize().trim().indexOf("M") > -1) {
								packInfo = res.getString(R.string.update_dialog_sys_info, upinfo.getSize().trim(), upinfo.getDescription().trim());
							} else {
								double dSize = Double.parseDouble(upinfo.getSize().trim());
								double packsize = dSize / 1024 / 1024;
								String strSize = String.valueOf(packsize);
								if (strSize.indexOf(".") > -1)strSize = strSize.substring(0,strSize.indexOf(".") + 3);
								packInfo = res.getString(R.string.update_dialog_sys_info,strSize + " M", upinfo.getDescription().trim());
							}
							packInfoANDversionInfo = versionInfo + "\n\n"+ packInfo;
							//Ϊ±£֤ÿ´ν�Ŝֽȷ͡ʾт°汾хϢ£¬½«т°汾хϢ±£´畚SharedPreferences
							ActivityUtils.setParameter(getApplicationContext(), "packInfoANDversionInfo", packInfoANDversionInfo);
							in_success = 0;
							// showGetHttpMsg(0,versionInfo+"\n\n"+packInfo);//½�ʾ
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
				processDone = PROCESS_CHECK;
				checkResult = false;
				break;
			case CHECKED_FAIL_NETWORK:
				showOnScreen(R.string.setnetwork);
				processDone = PROCESS_CHECK;
				checkResult = false;
				break;
			case ota_server_not_available:
				showOnScreen(R.string.ota_server_not_available);
				processDone = PROCESS_CHECK;
				checkResult = false;
				break;
			case phone_software_version_uptodate:
				//¸�⋊				if(checkDownload()&&!isDownloading){
					processGoing(processDone);
					downloadNewVersion(R.string.phone_software_version_uptodate, null);
					initButton();
					checkResult = true;
				}
				break;
			case CHECKED_SUCCEED:
				if (upinfo.getPriority().equals("Optional")) {
					// Ǖͨ¸�
					ActivityUtils.setParameter(MainActivity.this,"ota_priority", upinfo.getPriority());
					if(checkDownload()&&!isDownloading){
						checkResult = true;
						processGoing(processDone);
						downloadNewVersion(0, packInfoANDversionInfo);
						initButton();
					}
				} else {// ¸߼¶ԅЈ°�		ActivityUtils.setParameter(MainActivity.this,"ota_priority", upinfo.getPriority());
					// delete BUG_ID:DELL-1040 20120615 yulong.liang start
					// Toast.makeText(UpdateSystem.this,R.string.ota_update_priority_Mandatory,
					// 1).show();
					// delete BUG_ID:DELL-1040 20120615 yulong.liang end
					// add BUG_ID:DELL-1040 20120615 yulong.liang start
					checkResult = checkDownload();
//					if (residue_cable <= 20) {
//						showOnScreen(R.string.power_is_too_low);
//						processDone = PROCESS_CHECK;
//						isSucb = false;
//					} else if (!ActivityUtils.isExistSdcard()) {
//						showOnScreen(R.string.sdcard_not_exist);
//						processDone = PROCESS_CHECK;
//						isSucb = false;
//					} else if (ActivityUtils.isExistSdcard()) {
//						long lo = ActivityUtils.getAvailableStore(ActivityUtils.getSdcardPath());
//						double size = lo / 1024 / 1024;
//						if (size < 300) {
//							showOnScreen(R.string.sdcard_space_not_enough);
//							processDone = PROCESS_CHECK;
//							isSucb = false;
//						}
//					}
					if (checkResult) {
						//¿ªʼЂһ²½²ڗ�					processGoing(processDone);
						checkResult = true;
						downloadDataIntent();
					}
				}
				Log.d(TAG, "mHandlerinitChecked() ----- checkResult="+checkResult +"-----processDone="+processDone);
				// add BUG_ID:DELL-1040 20120615 yulong.liang end
				// add BUG_ID:QYLE-2297 20120711 yulong.liang end
				break;
			}
		};
	};
	
	private void downloadDataIntent() {
		Log.d(TAG, "downloadDataIntent()   ------- processDone="+processDone);
		String fiName = upinfo.getDownloadURL();
		fiName = fiName.substring(fiName.lastIndexOf("/") + 1, fiName.length());
		fiName = ActivityUtils.getSdcardPath() + fiName;
		File file = new File(fiName);// τ¼�¿¼
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
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			// e.printStackTrace();
		} catch (GeneralSecurityException e) {
			Log.e(TAG, e.toString());
			// e.printStackTrace();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			// add BUG_ID:QYLE-2700 20120718 yulong.liang start
			Intent inte = new Intent();
			inte.setAction("android.intent.action.AnHourDOWNLOAD_DeleteIntent");
			sendBroadcast(inte);// ɾ³�¸֪ͨ
			// add BUG_ID:QYLE-2700 20120718 yulong.liang end
			Intent intent = new Intent(MainActivity.this,NotificationDownloadService.class);
			intent.putExtra("downloadUrl", upinfo.getDownloadURL());
			intent.putExtra("downloadByte", upinfo.getDownloadByte());
			intent.putExtra("packageType", upinfo.getPackageType());
			ActivityUtils.setParameter(getApplicationContext(),"system_update_process", processDone+"");
			isDownloading = true;
			startService(intent);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// ֹͣһСʱ¶Ի°¿눉	// stopService(new Intent(this, AnHourLaterNotificationService.class));
	}
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
		int inState = android.provider.Settings.System.getInt(MainActivity.this.getContentResolver(), "ota_server_url", 0);
		String sSVersion = Build.DISPLAY;
		if (inState == 1 && servicePath.indexOf("ragentek") > -1) {
			sSVersion = ActivityUtils.getInternalVersion();
		}
		String versionInfo = res.getString(R.string.serialnumberinformation,ModelNumber, HardwareV, sSVersion, IMEI, Lastchecktime);
		ModelNumber = ModelNumber.replaceAll(" ", "%20");
		// modify QELS-4235 wanglu 20130118 start
		if (servicePath.indexOf("ragentek") > -1) {
			servicePath += "hw=" + ModelNumber + "&hwv=P2&swv="+ ActivityUtils.getInternalVersion() + "&serialno=" + IMEI+ "&flag=1&version=V1.0&beta="+ ActivityUtils.getRegisterParam(MainActivity.this);
		} else {
			servicePath += "hw=" + ModelNumber + "&hwv=P2&swv=" + Build.DISPLAY + "&serialno=" + IMEI;
		}
		changeVersionShow(ActivityUtils.getInternalVersion());
		// modify QELS-4235 wanglu 20130118 end
		// Toast.makeText(this, servicePath, 1).show();
		if (debug)
			Log.d(TAG, servicePath);
		return versionInfo;
	}

	// µ聿хϢ
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				residue_cable = intent.getIntExtra("level", 0);
				pWakeLock.release();
			}
			if (NotificationDownloadService.DOWNLOAD_SERVICE.equals(action)) {
				// ɧ¹�»¹՚Ђ՘Ϊ³ɲ¢Ȓҩ°�/				if (!isDownloading&&checkPackage) {
//					downloadNewVersion(0, packInfoANDversionInfo);
//					processGoing(processDone);
//					initButton();
//					isDownloading = false;
//					showScreenTip(R.string.ota_upgrade);
//				}
				String processDoneStr = ActivityUtils.getParameter(getApplicationContext(), "system_update_process");
		    	processDone = processDoneStr == "" ? processDone : Integer.parseInt(processDoneStr);
		    	checkPackage = ActivityUtils.getParameter(MainActivity.this,"verify_package").equals("yes");
				isDownloading = ActivityUtils.getParameter(MainActivity.this,"download_state").equals("yes");
				Log.d(TAG, "onReceive() ----- processDone=" + processDone+ "------ checkPackage=" + checkPackage + " ---------- isDownloading=" + isDownloading);
				resetProcess();
			}
		}
	};
	private void showOnScreen(int text){
		downTitle = downTitle == null ? (TextView) findViewById(R.id.system_update_title) : downTitle;
		downMsg = downMsg == null ? (TextView) findViewById(R.id.system_update_text) : downMsg;
		downTitle.setVisibility(View.GONE);
		downMsg.setVisibility(View.GONE);
		screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
		screenText.setText(text);
		screenText.setVisibility(View.VISIBLE);
//	    Log.d(TAG,"tips text =" + tips.getText());
	}
	private void hideScreenText(){
		screenText = screenText == null ? (TextView) findViewById(R.id.system_check_msg) : screenText;
		screenText.setVisibility(View.GONE);
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
	private boolean checkLocalPackage() {
		Log.d(TAG, "checkPackage() --- upinfo = "+upinfo+"ota_priority = "+ActivityUtils.getParameter(getApplicationContext(),"ota_priority"));
		int inState = android.provider.Settings.System.getInt(MainActivity.this.getContentResolver(),"ota_server_url", 0);
		File localFile =null;
		if (inState == 1) {
			localFile = new File("/sdcard/update.zip");
		}
		return (localFile ==null?false:localFile.exists());
//		String fiName = ActivityUtils.getParameter(getApplicationContext(), "NotifService_downloadURl");
//		String packageType = ActivityUtils.getParameter(getApplicationContext(), "NotifService_packageType");
//		fiName = fiName.substring(fiName.lastIndexOf("/") + 1, fiName.length());
//		fiName = ActivityUtils.getSdcardPath() + fiName;
//		File file = new File(fiName);// τ¼�¿¼
//		try {
//			Log.d(TAG, "checkPackage()  -------- file.exists = "+file.exists()+" ------ packageType="+packageType);
//			if (file.exists()) {
//				if (packageType.equals("Complete")) {
//					ActivityUtils.verifyCompletePackage(file, null, TAG);
//				} else {
//					ActivityUtils.verifyPackage(file, null, TAG);
//				}
//				showOnScreen(R.string.verify_file_suc);
//				if (ActivityUtils.getParameter(getApplicationContext(),"ota_priority").equals("Optional")) {
//					return true;
//				} else {
//					return true;
//				}
//			}
//		} catch (IOException e) {
//			Log.e(TAG, e.toString());
//			showOnScreen(R.string.verify_file_fail);
//			if (file.exists()) {
//				file.delete();
//			}
//			reDownload(file);
//			return false;
//			// e.printStackTrace();
//		} catch (GeneralSecurityException e) {
//			Log.e(TAG, e.toString());
//			showOnScreen(R.string.verify_file_fail);
//			if (file.exists()) {
//				file.delete();
//			}
//			reDownload(file);
//			// e.printStackTrace();
//			return false;
//		} catch (Exception e) {
//			Log.e(TAG, e.toString());
//			showOnScreen(R.string.verify_file_fail);
//			if (file.exists()) {
//				file.delete();
//			}
//			reDownload(file);
//			return false;
//		}
//		return false;
	}
	private void reDownload(File file){
		if(file==null||!file.exists()){
			processDone = PROCESS_DOWNLOAD;
			isDownloading = false;
			initButton();
		}
	}

*/}