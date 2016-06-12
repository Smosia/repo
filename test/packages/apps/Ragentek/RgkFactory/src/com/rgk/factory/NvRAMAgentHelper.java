package com.rgk.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import android.os.ServiceManager;

import android.os.IBinder;
import android.util.Log;

public class NvRAMAgentHelper {

	private static final String TAG = "NvRAMAgentHelper";
	/**
	 * This value related to
	 * mediatek\custom\(project)\cgen\cfgfileinc\CFG_PRODUCT_INFO_File
	 * .h---PRODUCT_INFO->FactoryData3(first byte)
	 */
	public static final int PSENSOR_INDEX = 360;

	private static NvRAMAgent getNvRAMAgent() {
		NvRAMAgent agent = null;
		try {
			IBinder binder = ServiceManager.getService("NvRAMAgent");
			agent = NvRAMAgent.Stub.asInterface(binder);
			if (agent == null) {
				Log.e(TAG, "getNvRAMAgent null!");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return agent;
	}

	public static int readNVData(int index) {
		int data = -1;
		NvRAMAgent agent = getNvRAMAgent();
		byte[] buff = null;
		try {
			buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
			if (buff != null) {
				data = buff[index];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i(TAG, "readNVData: data = " + data);
		return data;
	}
	//add BUG_ID:JWBLB-2034 xuemingwei 20150915 (start) 
	public static String readNVSn() {
		int data = -1;
		NvRAMAgent agent = getNvRAMAgent();
		String sn="";
		byte[] buff = null;
		byte[] buff1 = new byte[64];
		try {
			buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
			byte b;
			if (buff != null) {
				for(int i=0;i<64;i++) {
					buff1[i] = buff[i];
				}
				sn = new String(buff1,"UTF-8");
				Log.i(TAG, "readNVSn: sn = " + sn);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sn;
	}	
	//add BUG_ID:JWBLB-2034 xuemingwei 20150915 (end) 
	//add BUG_ID:EWLLL-878 xuemingwei 20151201 (start) 
	public static String readNVCsn() {
		int data = -1;
		NvRAMAgent agent = getNvRAMAgent();
		String csn="";
		byte[] buff = null;
		byte[] buff2 = new byte[64];
		try {
			buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
			byte b;
			if (buff != null) {
				for(int i=104;i<168;i++) {
					buff2[i-104] = buff[i];
				}
				csn = new String(buff2,"UTF-8");
				Log.i(TAG, "readNVCsn: csn = " + csn);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return csn;
	}	
	//add BUG_ID:EWLLL-878 xuemingwei 20151201 (end) 	
	public static int readGpsCalibrateNVData(int index) {
		int data = 0;
		NvRAMAgent agent = getNvRAMAgent();
		byte[] buff = null;
		try {
			buff = agent.readFile(Util.AP_CFG_CUSTOM_FILE_GPS_LID);
			if (buff != null) {
				data = buff[index];
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i(TAG, "readGpsCalibrateNVData: data = " + data);
		return data;
	}
	
	public static StringBuilder readNVWifiMac() {
		int data = -1;
		StringBuilder sb = new StringBuilder();
		NvRAMAgent agent = getNvRAMAgent();
		byte[] buff = null;
		try {
			buff = agent.readFile(Util.AP_CFG_RDEB_FILE_WIFI_LID);
			String s = new String(buff,"UTF-8");
			Log.i(TAG, "readNVWifiMac: s=" + s);
			for(int i=0;i<6;i++){
				sb.append(Integer.toHexString(buff[i+4]&0xFF));
				if(i < 5) {
					sb.append(":");
				}
				Log.i(TAG, "readNVWifiMac: sb=" + sb);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}
	
	public static StringBuilder readNVBluetoothMac() {
		int data = -1;
		StringBuilder sb = new StringBuilder();
		NvRAMAgent agent = getNvRAMAgent();
		byte[] buff = null;
		try {
			buff = agent.readFile(1);
			String s = new String(buff,"UTF-8");
			Log.i(TAG, "readNVBlueMac: s=" + s);
			for(int i=0;i<6;i++){
				sb.append(Integer.toHexString(buff[i]&0xFF));
				if(i < 5) {
					sb.append(":");
				}
				Log.i(TAG, "readNVBlueMac: sb=" + sb);
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;
	}
	
	// Write data to nvram
	public static int writeNVData(int index, int value) {
		NvRAMAgent agent = getNvRAMAgent();
		byte[] buff = null;
		try {
			buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buff[index] = (byte) value;

		int result = 0;
		try {
			result = agent
					.writeFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID, buff);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.i(TAG, "writeNVData: result=" + result);
		return result;
	}
	
	public static int  writeVernoToNV(int indexNum ,String str) {
		Log.i(TAG, "writeVernoToNV: str=" + str);	
		NvRAMAgent agent = getNvRAMAgent();
		int result=0;
		if(agent == null) return result;
		byte[] buff = null;
		try {
			buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
			String s = new String(buff,"UTF-8");
			Log.i(TAG, "writeVernoToNV: readNV=" + s);
			byte[] bytes = str.getBytes("UTF-8");
			for(int i=0; i<bytes.length&& i<64; i++) {
				buff[indexNum++] = bytes[i];
				}
				for(int i=bytes.length; i<64; i++) {
				if(buff[indexNum] == 0) {
					break;
				} else {
					buff[indexNum++] = 0;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
		try{
			String s = new String(buff,"UTF-8");
			Log.i(TAG, "writeVernoToNV: writeNV=" + s);
			result = agent.writeFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID,buff);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static void  writeTestResultToNV(int indexNum ,int result) {
		Log.i(TAG, "writeTestResultToNV: result=" + result);	
		NvRAMAgent agent = getNvRAMAgent();
		if(agent == null) return;
		byte[] buff = null;
		try {
			buff = agent.readFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID);
			String s = new String(buff,"UTF-8");
			Log.i(TAG, "writeTestResultToNV: readNV=" + s);
			Log.i(TAG, "writeTestResultToNV buff: " + Integer.toHexString(buff[indexNum]&0xFF));			
			buff[indexNum] = (byte) result;
		} catch (Exception e) {
			e.printStackTrace();		
		}		
		try{
			result = agent.writeFile(Util.AP_CFG_REEB_PRODUCT_INFO_LID,buff);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
}
