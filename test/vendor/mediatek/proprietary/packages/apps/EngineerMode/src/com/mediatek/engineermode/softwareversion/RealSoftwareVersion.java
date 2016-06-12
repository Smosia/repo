package com.mediatek.engineermode.softwareversion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemProperties; 
import android.view.WindowManager;
import android.widget.TextView;
import com.mediatek.engineermode.R;
import android.util.Log;
public class RealSoftwareVersion extends Activity {
	private static final String TAG = "SimpleSoftwareVersion";
	private static TextView mRealSoftwareVersionTittle = null;
	private static TextView mRealSoftwareVersionContent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//modify by shidongdong 20131022 start
		//this.setContentView(R.layout.software_version);
		this.setContentView(R.layout.real_software_version);
		//modify by shidongdong 20131022 end
		Intent i = getIntent();
//delete JWYY-130 liuxianbang 20140521 (start)
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				//WindowManager.LayoutParams.FLAG_FULLSCREEN);
//delete JWYY-130 liuxianbang 20140521 (end)
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mRealSoftwareVersionTittle = (TextView) findViewById(R.id.real_version_tittle);
		mRealSoftwareVersionContent = (TextView) findViewById(R.id.real_version_content);
		//showSoftwareVersionExt();
		String host = i.getStringExtra("host");
        Log.i("RealSoftwareVersion","host="+host);
		String custom = getResources().getString(R.string.custom);
		//modify by luoran(start)
        if("9875".equals(host) || custom.equals("qmobile")) {
            showCusSoftwareVersionExt();
		} else {
            showSoftwareVersionExt();
        }
		//modify by luoran(end)
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mRealSoftwareVersionTittle = null;
		mRealSoftwareVersionContent = null;
	}

	private void showSoftwareVersionExt() {
		mRealSoftwareVersionTittle.setText("Real Internal Version:");

		String internalVersion = null;
		
		if(SystemProperties.get("ro.internal.version.rgk", "").equals("")) {

		    internalVersion = SystemProperties.get("ro.internal.version", "");
		}else {
		    internalVersion = SystemProperties.get("ro.internal.version.rgk", "");
		}
		mRealSoftwareVersionContent.setText(internalVersion);
	}
	
	private void showCusSoftwareVersionExt() {
		mRealSoftwareVersionTittle.setText("Software Version:");

		String internalVersion = null;
		/**
		if(SystemProperties.get("ro.internal.version.rgk", "").equals("")) {
		    internalVersion = SystemProperties.get("ro.internal.version", "");
		}else {
		    internalVersion = SystemProperties.get("ro.internal.version.rgk", "");
		}**/
		internalVersion = SystemProperties.get("ro.build.display.id", "");
		mRealSoftwareVersionContent.setText(internalVersion);
	}
}
