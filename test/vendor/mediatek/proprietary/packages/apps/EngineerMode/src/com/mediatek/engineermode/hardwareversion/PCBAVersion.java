package com.mediatek.engineermode.hardwareversion;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.mediatek.engineermode.NvRAMAgent;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.app.ActionBar;
import com.mediatek.engineermode.R;

public class PCBAVersion extends Activity{
	private static final String TAG = "PCBAVersion";
	private static TextView m_vpcba_text = null;
	private static TextView m_cba_version_content = null;
	private static final int AP_CFG_REEB_PRODUCT_NEW_INFO_LID = 45;  //92 kk should be  45

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.pcba_version);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
       		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                	WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
        	init();
        	showSoftwareVersionExt();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		m_vpcba_text = null;
		m_cba_version_content = null;
	}
	
	private void init(){
		m_vpcba_text = (TextView) findViewById(R.id.pcba_vertion_text);
		m_cba_version_content = (TextView) findViewById(R.id.pcba_vertion_content);
	}   

	public static String getHardwareVersion(){
		boolean blSupported = true;
		//modify for not get pcbaverison from nv ping.wang 
		if(blSupported){
			return "V1.2";
		}else{
		    	byte[] read = readNVData();
			Log.e("NV", "read  == " + read);
		    	StringBuffer hardVersion = new StringBuffer();
		    	if(null!=read){
		
		    		for(int i = 255; i < 270; i++){
		    			if(0!=read[i]){
		    				hardVersion.append((char)read[i]);
						//Log.e("NV", "read["+i+"] == " + read[i]);
						Log.e("NV", "11-hardVersion  == " + (char)read[i]);
						Log.e("NV", "22-hardVersion  == " + Integer.toHexString(read[i]));
		    			}
				}
		    		if(hardVersion.length()>0){
					Log.e("NV", "hardVersion.toString()  == " + hardVersion.toString());
		    			return hardVersion.toString();
		    		}else{
		    			return "V1.2";
		    		}
		    	}else{
		    		return "V1.2";
		    	}
		}
	   }

	public static byte[]  readNVData() {
		IBinder binder = ServiceManager.getService("NvRAMAgent");
		NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
		byte[] buff = null;
		try {
		   // read buffer from nvram
		   buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);
		} catch (RemoteException e) {
		   e.printStackTrace();
		}

		return buff ;
    	}

	private void showSoftwareVersionExt(){
		m_vpcba_text.setText("PCBA Version:");
		String internalVersion = getHardwareVersion();
		m_cba_version_content.setText(internalVersion);	
	}

}
