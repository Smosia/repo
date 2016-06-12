package com.mediatek.engineermode.softwareversion;

import android.app.ActionBar;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.util.Log;
import android.os.SystemProperties; 
//add xianglong 20130524 (start)
import com.android.internal.telephony.TelephonyProperties;
//add xianglong 20130524 (end)
import android.telephony.TelephonyManager;
import android.content.Intent;
import android.view.View;
import com.mediatek.engineermode.R;
import android.os.Handler;
/************************************************************************
History:
2. xianbang.liu@ragentek.com 20130703
   Description:add build time.
1. long.xiang@ragentek.com 2013.05.24 
   Description: modify baseband version display.
************************************************************************/

public class SoftwareVersion extends Activity{
	private static final String TAG = "SoftwareVersion";
	private static TextView m_isoftware_text = null;
	private static TextView m_internal_content = null;

	private static TextView m_esoftware_text = null;
	private static TextView m_external_content = null;
	
	//add xianglong 20130524 (start) 
	private static TextView m_modem_text = null;
	private TextView m_modem_content;
	//add xianglong 20130524 (end) 
	private static TextView m_pcba_text = null;
	private static TextView m_pcba_content = null;
//add liuxianbang 20130703 (start)
    private static TextView m_buildtime_text = null;
//add liuxianbang 20130703 (start)
	private static TextView m_build_type = null;
	private static TextView m_build_type_content = null;
	
	private static TextView m_lcd_info = null;
	private static TextView m_lcd_content = null;
	private static TextView m_camera_info = null;
	private static TextView m_camera_content = null;
	private static TextView m_sd_info = null;
	private static TextView m_sd_content = null;
	private static TextView m_memory_info = null;
	private static TextView m_memory_content = null;
	
	private static StringBuffer m_lcd_buffer = null;
	private static StringBuffer m_camera_buffer = null;
	private static StringBuffer m_sd_buffer = null;	
	private static StringBuffer m_memory_buffer = null;
	private static StringBuffer temp_buffer = null;

	private static final int TP_INFOR_LENTH = 7;
	private static TextView m_tpVersion_info = null;
	private static TextView m_tpVersion_text = null;
	private static TextView m_calibration_text = null;
	//String[] type_array = {"TP IC: ","TP vendor: ","TP only id: ","Current TP fw version: ","TP i2c addr: "};

	//add xianglong 20130524 (start) 
	private static final String UNKNOWN = "unknown";
	private static final String MODEM = getString(TelephonyProperties.PROPERTY_BASEBAND_VERSION);
	//add xianglong 20130524 (end) 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.software_version);
		Intent i = getIntent();
//delete JWYY-130 liuxianbang 20140521 (start)
        	//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                	//WindowManager.LayoutParams.FLAG_FULLSCREEN);
//delete JWYY-130 liuxianbang 20140521 (end)
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


	//add xianglong 20130524 (start)
	private static String getString(String property) {
            	return SystemProperties.get(property, UNKNOWN);
        }
	//add xianglong 20130524 (end)
	private void init(){
		//add liuxianbang 20130703 (start)
        m_buildtime_text = (TextView) findViewById(R.id.build_time);
		//add liuxianbang 20130703 (start)
		m_isoftware_text = (TextView) findViewById(R.id.internal_vertion_text);
		m_internal_content = (TextView) findViewById(R.id.vertion_content);
		m_esoftware_text = (TextView) findViewById(R.id.external_vertion_text);
		m_external_content = (TextView) findViewById(R.id.external_content);
		//add xianglong 20130524 (start) 
		m_modem_text = (TextView) findViewById(R.id.modem_vertion_text);
		m_modem_content = (TextView) findViewById(R.id.modem_content);
		//add xianglong 20130524 (end)
		m_pcba_text = (TextView) findViewById(R.id.pcba_vertion_text);
		m_pcba_content = (TextView) findViewById(R.id.pcba_content);
		m_build_type =  (TextView) findViewById(R.id.build_type);
		m_build_type_content = (TextView) findViewById(R.id.build_type_content);

		m_lcd_info = (TextView) findViewById(R.id.lcd_info);
		m_lcd_content = (TextView) findViewById(R.id.lcd_content);
		m_camera_info = (TextView) findViewById(R.id.camera_info);
		m_camera_content = (TextView) findViewById(R.id.camera_content);
		m_sd_info = (TextView) findViewById(R.id.sd_info);
		m_sd_content = (TextView) findViewById(R.id.sd_content);		
		m_memory_info =  (TextView) findViewById(R.id.memory_info);
		m_memory_content =  (TextView) findViewById(R.id.memory_content);

		m_lcd_buffer = new StringBuffer();
		m_camera_buffer = new StringBuffer();
		m_sd_buffer = new StringBuffer();		
		m_memory_buffer = new StringBuffer();

		m_tpVersion_info = (TextView) findViewById(R.id.tp_infor_text);
		m_tpVersion_text = (TextView) findViewById(R.id.tp_infor_content);
		m_calibration_text = (TextView) findViewById(R.id.calibration);
		temp_buffer = new StringBuffer();		
		//temp_buffer.append("TP Version Info:\n\n");

	try {
            FileInputStream fis = new FileInputStream(
                    "/proc/rgk_lcdInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);

	    String ch = null;
	    while ((ch = br.readLine()) != null){
		m_lcd_buffer.append(ch);
	    }
	   // m_lcd_buffer.append("\r\n");
	    fis.close();
	} catch (FileNotFoundException e) {
		m_lcd_buffer.append("No lcdinfo file found");
            Log.i(TAG, "No lcdinfo file found");
        } catch (IOException e) {
        	m_lcd_buffer.append("Error reading lcdinfo file");
        	m_lcd_buffer.append(e);
            Log.w(TAG, "Error reading lcdinfo file", e);
        }
        
		try {
            FileInputStream fis = new FileInputStream(
                    "/proc/rgk_lcd_params_Info");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);

	    String ch = null;
	    while ((ch = br.readLine()) != null){
		m_lcd_buffer.append("\n").append("CLK:").append(ch);
	    }
	    fis.close();
	} catch (FileNotFoundException e) {
		m_lcd_buffer.append("No lcdclkinfo file found");
            Log.i(TAG, "No lcdclkinfo file found");
        } catch (IOException e) {
        	m_lcd_buffer.append("Error reading lcdclkinfo file");
        	m_lcd_buffer.append(e);
            Log.w(TAG, "Error reading lcdclkinfo file", e);
        }
		
        try {
            FileInputStream fis = new FileInputStream(
                    "/proc/rgk_cameraInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);

		String ch = null;
		ch = br.readLine();
		//modify xianglong 20130607 DLEL-19 (start)
		if(ch != null){
		    Log.i("zhy", "cameraInfo------->"+ch);
		    String[] camera = ch.split(",");
		    m_camera_buffer.append(camera[0]).append("\n");
		    m_camera_buffer.append(camera[1]);
		}else{SystemProperties.get("ro.build.date","unknow");
		    m_camera_buffer.append("CameraInfo is null");
		}
		//modify xianglong 20130607 DLEL-19 (end)
		//m_camera_buffer.append("\n\r");
		fis.close();
	} catch (FileNotFoundException e) {
		m_camera_buffer.append("No cameraInfo file found");
            Log.i(TAG, "No cameraInfo file found");
        } catch (IOException e) {
        	m_camera_buffer.append("Error reading cameraInfo file");
        	m_camera_buffer.append(e);
            Log.w(TAG, "Error reading cameraInfo file", e);
        }

		try {
            FileInputStream fis = new FileInputStream(
                    "/proc/rgk_camera_Mclk_Info");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);

		String ch = null;
		ch = br.readLine();
		if(ch != null){
		    Log.i(TAG, "cameraMclkInfo------->"+ch);
		    String[] cameraMclk = ch.split(",");
		    m_camera_buffer.append("\n").append(cameraMclk[0]).append("\n");
		    m_camera_buffer.append(cameraMclk[1]);
		}else{
			SystemProperties.get("ro.build.date","unknow");
		    m_camera_buffer.append("CameraMclkInfo is null");
		}
		fis.close();
	} catch (FileNotFoundException e) {
			m_camera_buffer.append("No cameraMclkInfo file found");
            Log.i(TAG, "No cameraInfo file found");
        } catch (IOException e) {
        	m_camera_buffer.append("Error reading cameraMclkInfo file");
        	m_camera_buffer.append(e);
            Log.w(TAG, "Error reading cameraMclkInfo file", e);
        }
		
		try {
            FileInputStream fis = new FileInputStream(
                    "/proc/msdc_clkInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);

		String ch = null;
		ch = br.readLine();
		if(ch != null){
		    Log.i(TAG, "sdMclkInfo------->"+ch);
		    String[] sd = ch.split(",");
		    m_sd_buffer.append(sd[0]).append("\n");
		    m_sd_buffer.append(sd[1]);
		}else{
			SystemProperties.get("ro.build.date","unknow");
		    m_sd_buffer.append("sdMclkInfo is null");
		}
		fis.close();
	} catch (FileNotFoundException e) {
			m_sd_buffer.append("No sdMclkInfo file found");
            Log.i(TAG, "No sdMclkInfo file found");
        } catch (IOException e) {
        	m_sd_buffer.append("Error reading sdMclkInfo file");
        	m_sd_buffer.append(e);
            Log.w(TAG, "Error reading sdMclkInfo file", e);
        }
		
	try {
            FileInputStream fis = new FileInputStream(
                    "/proc/rgk_memInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);
		//modify the memory info was garbled xianglong 20130410 (start)
		String ch = br.readLine(); 
		//modify xianglong 20130607 DLEL-19 (start)
		if(ch != null){
		    //modify JWLW-530 xianglong 20130926 (start)
		    //String[] memory = ch.split("[?]");
		    String[] memory = ch.split(" ");
		    //modify JWLW-530 xianglong 20130926 (end)
		    m_memory_buffer.append(memory[0].trim());
		}else{
		    m_memory_buffer.append("MemInfo is null");
		}
		//modify xianglong 20130607 DLEL-19 (end)
		//if(ch.length() > 15){
		//	m_memory_buffer.append(ch.substring(15, ch.length()).trim());
		//}else{
		//	m_memory_buffer.append(ch.trim());
		//}
		//m_memory_buffer.append(ch.trim());
		//m_memory_buffer.append("\r\n");
		 //modify the memory info was garbled xianglong 20130410 (end)
		fis.close();
	} catch (FileNotFoundException e) {
		m_memory_buffer.append("No msm_nand_info file found");
            Log.i(TAG, "No msm_nand_info file found");
        } catch (IOException e) {
        	m_memory_buffer.append("Error reading msm_nand_info file");
        	m_memory_buffer.append(e);
            Log.w(TAG, "Error reading msm_nand_info file", e);
        }
	
	try {
            FileInputStream fis = new FileInputStream(
                    "/proc/rgk_tpInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);
		//modify xianglong 20130607 DLEL-19 (start)
		String ch = br.readLine();
		if(ch != null){
		    String[] tp = ch.split(",");
		    for(int i=0; i<tp.length; i++){
		        temp_buffer.append(tp[i]).append("\n");
		    }
		}else{
		    temp_buffer.append("TPInfo is null").append("\n");
		}
		//modify xianglong 20130607 DLEL-19 (end)
		//temp_buffer.append(tp[0]).append("\n");
		//temp_buffer.append(tp[1]);
		//temp_buffer.append("\r");
		fis.close();
	} catch (FileNotFoundException e) {
		temp_buffer.append("No TPInfo file found");
            Log.i(TAG, "No TPInfo file found");
        } catch (IOException e) {
        	temp_buffer.append("Error reading TPInfo file");
        	temp_buffer.append(e);
            Log.w(TAG, "Error reading TPInfo file", e);
        }
    }   
	
    private void showSoftwareVersionExt(){
//add liuxianbang 20130703 (start)
        m_buildtime_text.setText("Build Time:"+SystemProperties.get("ro.build.date","unknow"));
//add liuxianbang 20130703 (start)
	m_isoftware_text.setText("Internal Version:");
	//modify JLLLODM-443 xianglong 20130813 (start)
	//String internalVersion = SystemProperties.get("ro.custom.build.version","");
	String internalVersion = SystemProperties.get("ro.internal.version","");
	//modify JLLLODM-443 xianglong 20130813 (end)
	if(internalVersion.equals("")){
	    internalVersion = SystemProperties.get("ro.build.display.id","");
	}	
	m_internal_content.setText(internalVersion);

	m_esoftware_text.setText("Software Version:");
	String externalVersion = SystemProperties.get("ro.build.display.id","");
	if(externalVersion.equals("")){
	    //modify JLLLODM-443 xianglong 20130813 (start)
	    //externalVersion = SystemProperties.get("ro.custom.build.version","");
	    externalVersion = SystemProperties.get("ro.internal.version","");
	    //modify JLLLODM-443 xianglong 20130813 (end)
	}	
	m_external_content.setText(externalVersion);

	//add xianglong 20130524 (start)
	m_modem_text.setText("Modem Version:");
	//m_modem_content.setText(getString(TelephonyProperties.PROPERTY_BASEBAND_VERSION));
    showModem();
	//add xianglong 20130524 (end)
	String pcba = showPcbaInfo();
	m_pcba_text.setText("PCBA Version:");
	m_pcba_content.setText(pcba);
	if("255".equals(pcba) || null == pcba || pcba.length() == 0) {
		m_pcba_text.setVisibility(View.GONE);
		m_pcba_content.setVisibility(View.GONE);
	}

	m_build_type.setText("Build type:");
	m_build_type_content.setText(new StringBuffer().append(Build.TYPE).toString());

	m_lcd_info.setText("Lcd:");
	m_lcd_content.setText(m_lcd_buffer);
	m_camera_info.setText("Camera:");
	m_camera_content.setText(m_camera_buffer);
	m_sd_info.setText("SD:");
	m_sd_content.setText(m_sd_buffer);	
	m_memory_info.setText("Memory:");
	m_memory_content.setText(m_memory_buffer);
	
	m_tpVersion_info.setText("TP Version:");
	m_tpVersion_text.setText(temp_buffer);
	
	 String serial = Build.SERIAL;
	 Log.i(TAG, "serial = "+serial);
     if (serial != null && !serial.equals("")) {
	 if(serial.trim().toUpperCase().endsWith(" 10P")) {
    		 m_calibration_text.setText("Calibration: Calibrated");
    	 } else {
    		 m_calibration_text.setText("Calibration: Uncalibrated");
    	 }
     }	
    }

    public String[] splitOfComma(String string){		
	String[] buffer = string.split(",");
	return buffer;
    }
	
	private String showPcbaInfo() {
		String str="";
		try {
			FileInputStream fis = new FileInputStream("/proc/rgk_pcbInfo");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr, 255);
			str = br.readLine();
			Log.i(TAG, "showPcbaInfo:"+str);
			fis.close();
			isr.close();
			br.close();
		} catch (Exception e) {
			return null;
		}
		return str;
	}
	private Handler mHandler = new Handler();;
	private void showModem() {
		String str = SystemProperties.get(TelephonyProperties.PROPERTY_BASEBAND_VERSION, "");
		if(str.length() != 0) {
			m_modem_content.setText(str);
		} else {
			m_modem_content.setText("Reading...");
			mHandler.postDelayed(runnable, 100);
		}
	}
	private Runnable runnable = new Runnable() {		
		@Override
		public void run() {
			showModem();
		}
	};

}
