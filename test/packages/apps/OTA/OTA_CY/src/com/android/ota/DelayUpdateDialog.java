package com.android.ota;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
/**
 * 
 * @author yulong.liang
 * 1. yulong.liang@ragentek.com 20120711 BUG_ID:QYLE-2297
 *  Description: Click cancel,can be delayed three times
 */
public class DelayUpdateDialog extends Activity {

	private Button install_package_btn;
	private Button delayinstall_package_btn;
	private static final String TAG="DelayUpdateDialog";
	private boolean bolexistpackage=false;
	private boolean bolInstall=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.delaydialog);				
		initData();
		 
	}
	
	private void initData() {
		install_package_btn = (Button) this.findViewById(R.id.install_package);
		delayinstall_package_btn = (Button) this.findViewById(R.id.delayinstall_package);
		install_package_btn.setOnClickListener(new InstallBtnCK());
		delayinstall_package_btn.setOnClickListener(new DelayInstallBtnCK());
		if(ActivityUtils.getParameter(DelayUpdateDialog.this, "delaytime_count").equals("3")){
			delayinstall_package_btn.setVisibility(View.GONE);
		}
		bolexistpackage=false;
		bolInstall=false;
	}
	@Override
	protected void onStart() {
		bolexistpackage=false;
		bolInstall=false;
		super.onStart();
	}
	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		Intent itt2 = new Intent(DelayUpdateDialog.this,DelayUpdateDialog.class);         	  	 
		String delaycount=ActivityUtils.getParameter(DelayUpdateDialog.this, "delaytime_count");
		Log.d(TAG, "onPause delaycount="+delaycount);
		if(delaycount.equals("")){
			if(!bolexistpackage){
				Log.d(TAG, "onPause bolexistpackage="+bolexistpackage);
				if(bolInstall!=true){
					Log.d(TAG, "onPause bolInstrall="+bolInstall);
					ActivityUtils.setParameter(DelayUpdateDialog.this, "delaytime_count","1");
					ActivityUtils.delayNotify(DelayUpdateDialog.this,itt2,true);//һСʱº􍢐Ѱ²װ
				}
			}
		}
		else{
				if(delaycount.equals("1")){
					ActivityUtils.setParameter(DelayUpdateDialog.this, "delaytime_count","2");
					ActivityUtils.delayNotify(DelayUpdateDialog.this,itt2,true);
				}
				else if(delaycount.equals("2")){
					ActivityUtils.setParameter(DelayUpdateDialog.this, "delaytime_count","3");
					ActivityUtils.delayNotify(DelayUpdateDialog.this,itt2,true);
				}
				else{
					ActivityUtils.setParameter(DelayUpdateDialog.this, "delaytime_count","3");
				}
		}
		super.onPause();
	}
	private  class InstallBtnCK implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {					
			//טǴ´�		    
			String path =ActivityUtils.getParameter(DelayUpdateDialog.this, "sdcard_updatepackage_URL"); 
		    Log.d(TAG, "install file:" + path);
			File file=new File(path);
			if(file.exists()){	
				bolInstall=true;
					ActivityUtils.setParameter(DelayUpdateDialog.this, "delaytime_count","");	
					ActivityUtils.setParameter(getApplicationContext(), "sdcard_updatepackage_URL", "");
/*
			Intent intent = new Intent("com.qualcomm.update.REBOOT"); 
	                intent.setData(Uri.fromFile(file));
	                intent.putExtra("ota_update", "true");
	                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Ȑ¼ǣ¬²»¼Ӵ˾䣬Ϟ·¨ʵЖ͸ת
	                startActivity(intent);
*/
			Intent intent = new Intent(DelayUpdateDialog.this,MtkUpgradeRestart.class);
			intent.putExtra("filePath",path);
 			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 					
 			startActivity(intent);
			DelayUpdateDialog.this.finish();
			}
			else{
				bolexistpackage=true;
				Intent itt2 = new Intent(DelayUpdateDialog.this,DelayUpdateDialog.class); 
				ActivityUtils.delayNotify(DelayUpdateDialog.this,itt2,false);//ȡлӔǰµō¨֪
				ActivityUtils.setParameter(DelayUpdateDialog.this, "delaytime_count","");	
				Toast.makeText(DelayUpdateDialog.this, R.string.notexists_package, 1).show();
				DelayUpdateDialog.this.finish();
			}
			
		}
	}
	private  class DelayInstallBtnCK implements View.OnClickListener {
		@Override
		public void onClick(View arg0) {		
			DelayUpdateDialog.this.finish();			
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return false;
		}		
		return super.onKeyDown(keyCode, event);
	}
	/*
	@Override
	public void onAttachedToWindow() {	
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		super.onAttachedToWindow();
	}*/
}
