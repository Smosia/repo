package com.mediatek.engineermode.hardwareversion;

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

import android.telephony.TelephonyManager;
import android.content.Intent;
import android.view.View;
import com.mediatek.engineermode.R;

public class HardwareVersionInfo extends Activity{
	private static final String TAG = "HardwareVersionInfo";
	
	private static TextView m_lcd_info = null;
	private static TextView m_lcd_content = null;
	private static TextView m_camera_info = null;
	private static TextView m_camera_content = null;

	private static TextView m_memory_info = null;
	private static TextView m_memory_content = null;
	
	private static StringBuffer m_lcd_buffer = null;
	private static StringBuffer m_camera_buffer = null;
	private static StringBuffer m_memory_buffer = null;
	private static StringBuffer temp_buffer = null;

	private static final int TP_INFOR_LENTH = 7;
	private static TextView m_tpVersion_info = null;
	private static TextView m_tpVersion_text = null;
	//String[] type_array = {"TP IC: ","TP vendor: ","TP only id: ","Current TP fw version: ","TP i2c addr: "};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.hardware_info);
		Intent i = getIntent();
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                	WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
        	init();
        	showHardwareVersionInfoExt();
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

		m_lcd_info = null;
		m_lcd_content = null;
		m_camera_info = null;
		m_camera_content = null;
		m_memory_info = null;
		m_memory_content = null;

		m_tpVersion_info = null;
		m_tpVersion_text = null;
	}
	
	private void init(){
	
		m_lcd_info = (TextView) findViewById(R.id.hd_lcd_info);
		m_lcd_content = (TextView) findViewById(R.id.hd_lcd_content);
		m_camera_info = (TextView) findViewById(R.id.hd_camera_info);
		m_camera_content = (TextView) findViewById(R.id.hd_camera_content);
		m_memory_info =  (TextView) findViewById(R.id.hd_memory_info);
		m_memory_content =  (TextView) findViewById(R.id.hd_memory_content);

		m_lcd_buffer = new StringBuffer();
		m_camera_buffer = new StringBuffer();
		m_memory_buffer = new StringBuffer();

		m_tpVersion_info = (TextView) findViewById(R.id.hd_tp_info_text);
		m_tpVersion_text = (TextView) findViewById(R.id.hd_tp_info_content);
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
	     m_lcd_buffer.append("\r\n");
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
                    "/proc/rgk_cameraInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);

		String ch = null;
		ch = br.readLine();
		Log.i("zhy", "cameraInfo------->"+ch);
		String[] camera = ch.split(",");
		m_camera_buffer.append(camera[0]).append("\n");
		m_camera_buffer.append(camera[1]);
		m_camera_buffer.append("\n\r");
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
                    "/proc/rgk_memInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);
	    //modify the memory info was garbled xianglong 20130410 (start)
		String ch = br.readLine(); 
		String[] memory = ch.split("[?]");
		m_memory_buffer.append(memory[0].trim());
		//m_memory_buffer.append(ch.trim());
	   //modify the memory info was garbled xianglong 20130410 (end)
		m_memory_buffer.append("\r\n");
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

		String ch = null;
		ch = br.readLine();
		//modify MDEYW-48 xianglong 20130708 (start)
		if(ch == null){
		    temp_buffer.append("No TPInfo file found").append("\n");
		}else{
		    String[] tp = ch.split(",");
		    for(int i=0; i<tp.length; i++){
		        temp_buffer.append(tp[i]).append("\n");
		    }
		}
		//modify MDEYW-48 xianglong 20130708 (end)
		//temp_buffer.append(tp[0]).append("\n");
		//temp_buffer.append(tp[1]);
		temp_buffer.append("\r");
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
	
    private void showHardwareVersionInfoExt(){

	m_lcd_info.setText("Lcd:");
	m_lcd_content.setText(m_lcd_buffer);
	m_camera_info.setText("Camera:");
	m_camera_content.setText(m_camera_buffer);
	m_memory_info.setText("Memory:");
	m_memory_content.setText(m_memory_buffer);
	
	m_tpVersion_info.setText("TP Version:");
	m_tpVersion_text.setText(temp_buffer);
    }

    public String[] splitOfComma(String string){		
	String[] buffer = string.split(",");
	return buffer;
    }

}
