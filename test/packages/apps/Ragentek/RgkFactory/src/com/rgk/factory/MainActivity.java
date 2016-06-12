package com.rgk.factory;

/**
 * @author shiguang.du
 * @date 2014.06.17
 */
import java.util.Locale;

import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.DisplayMetrics;
import android.util.Log;
import com.rgk.factory.NvRAMAgentHelper;
// M: luoran bug id: DWYYLM-96 20160105(start)
import android.app.StatusBarManager;
import android.content.Context;
// M: luoran bug id: DWYYLM-96 20160105(end)
import android.os.SystemProperties; 
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class MainActivity extends Activity {
	public static String TAG = "CIT_TEST";
	TextView buttAutoTest, buttItemTest, buttClean, buttResult,changeLanguage;
	public static boolean mAutoTest = false, mAutoBackTest = false;
	private boolean isClickChangeLanguage;
	// M: luoran bug id: DWYYLM-96 20160105(start)
	private StatusBarManager mStatusBarManager;
	// M: luoran bug id: DWYYLM-96 20160105(end)
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	       // M: huangkunming 2014.12.20 @{
	       // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
	    	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
	    	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		// @}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		new MyAsyncTask().execute();
		initLanguage();
		setTitle(R.string.app_name);
		setContentView(R.layout.activity_main);
		//M: luoran bug id: DWYYLM-96 20160105(start)
		mStatusBarManager = (StatusBarManager) this.getSystemService(Context.STATUS_BAR_SERVICE);
		mStatusBarManager.disable(StatusBarManager.DISABLE_EXPAND);
		//M: luoran bug id: DWYYLM-96 20160105(end)
		LinearLayout mLayout = (LinearLayout) findViewById(R.id.main_layout);
	    mLayout.setSystemUiVisibility(0x00002000);
		buttAutoTest = (TextView) this.findViewById(R.id.auto_test);
		buttItemTest = (TextView) this.findViewById(R.id.item_test);
		buttClean = (TextView) this.findViewById(R.id.Clean);
		buttResult = (TextView) this.findViewById(R.id.TestResults);
		changeLanguage = (TextView)this.findViewById(R.id.ChangeLanguage);
		setLanguageButtonVisible();
	}

	public void startBackTest() {
		AutoTestHandle.autoBacktest(this, TAG);
		//sendBroadcast(new Intent(Config.ACTION_AUTO_BACKTEST).putExtra("test_item", TAG));
    }		
	public void autotest(View v) {
		mAutoTest = true;
		mAutoBackTest = true;
		startBackTest();
		StartToAutoTest();
	}

	public void itemtest(View v) {
		mAutoTest = false;
		mAutoBackTest = false;
		Intent mIntent = new Intent(this, ItemTestActivity.class);
		startActivity(mIntent);
	}

	public void clean(View v) {
		mAutoTest = false;
		mAutoBackTest = false;
		if (ResultHandle.DeleteResultFromSystem()) {
			Toast.makeText(this, R.string.msg_clean_success, Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(this, R.string.msg_clean_fail, Toast.LENGTH_LONG)
					.show();
		}
		// sendBroadcast(new Intent(Config.ItemOnClick));
	}

	private void StartToAutoTest() {
		AutoTestHandle.autoTest(this, TAG);
		//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));
	}

	public void testResults(View v) {
		Intent mIntent = new Intent(this, CitTestResult.class);
		startActivity(mIntent);
	}

	public void softversion(View v) {
		Intent mIntent = new Intent(this, SoftwareVersion.class);
		startActivity(mIntent);
	}
	public void setLanguageButtonVisible() {
		String[] locales = getResources().getAssets().getLocales();
		for(String locale : locales	) {
			Log.i(TAG, "setLanguageButtonVisible:Language="+locale);
			if(locale.equals("zh_CN") || locale.equals("zh-CN"))
				return;
		}
		changeLanguage.setVisibility(View.GONE);
	}
	public void initLanguage() {
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();
		isClickChangeLanguage = sp.getBoolean("isClickChangeLanguage", false);
		Log.i(TAG, "initLanguage:isClickChangeLanguage="+isClickChangeLanguage);
		if(isClickChangeLanguage) {
			editor.putBoolean("isClickChangeLanguage", false);
			editor.commit();
			return;
		}
		Resources res = getResources();
		Configuration config = res.getConfiguration();
		Log.i(TAG, "initLanguage:"+config.locale+" defaultEnglish="+Util.defaultEnglish);
		if(getResources().getBoolean(R.bool.defaultEnglish) || KeyCodeReceiver.customOrder) {
			//KeyCodeReceiver.customOrder = false;
			config.locale = Locale.ENGLISH;
		} else {
			config.locale = Locale.SIMPLIFIED_CHINESE;
		}		
		DisplayMetrics dm = res.getDisplayMetrics();
		res.updateConfiguration(config, dm);
	}
	public void changeLanguage(View v) {
		Resources res = getResources();
		Configuration config = res.getConfiguration();
		Log.i(TAG, "changeLanguage:"+config.locale);
		if(!config.locale.equals(Locale.SIMPLIFIED_CHINESE)) {
			config.locale = Locale.SIMPLIFIED_CHINESE;
		} else {
			config.locale = Locale.ENGLISH;
		}		
		DisplayMetrics dm = res.getDisplayMetrics();
		res.updateConfiguration(config, dm);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("isClickChangeLanguage", true);
		editor.commit();
		super.recreate();
	}

	public void resetPhone(View v) {
		showDialog();			    
	}
	//Add BUG_ID: DWYYLM-96 xuemingwei 20160407(start)
	@Override
    protected void onPause() {
	    mStatusBarManager.disable(StatusBarManager.DISABLE_NONE);
        super.onPause();
    }
	//Add BUG_ID: DWYYLM-96 xuemingwei 20160407(end)
	@Override
	public void onDestroy() {
	    //M: luoran bug id: DWYYLM-96 20160105(start)
	    mStatusBarManager.disable(StatusBarManager.DISABLE_NONE);
		//M: luoran bug id: DWYYLM-96 20160105(end)
		mAutoTest = false;
		mAutoBackTest = false;
		super.onDestroy();
	}
	
	private void writeVernoToNV() {
		String internalVersion = SystemProperties.get("ro.internal.version","");
		String externalVersion = SystemProperties.get("ro.build.display.id","");
		StringBuilder MainCamera = getCamera();
		StringBuilder SubCamera = getSubCamera();
		StringBuilder Memory = RAMConvert();
		StringBuilder Sdcard = ROMConvert();
		String Lcd = getLcd();
		String Chip = getChip();
		NvRAMAgentHelper.writeVernoToNV(64*3+40,internalVersion);
		NvRAMAgentHelper.writeVernoToNV(64*6+40,externalVersion);
		NvRAMAgentHelper.writeVernoToNV(64*7+40,MainCamera.toString());
		NvRAMAgentHelper.writeVernoToNV(64*8+40,SubCamera.toString());
		NvRAMAgentHelper.writeVernoToNV(64*9+40,Memory.toString());
		NvRAMAgentHelper.writeVernoToNV(64*10+40,Sdcard.toString());
		NvRAMAgentHelper.writeVernoToNV(64*11+40,Lcd);
		NvRAMAgentHelper.writeVernoToNV(64*12+40,Chip);
	}
	
		public StringBuilder getCamera() {
		StringBuilder result = new StringBuilder();
		try {
			FileInputStream mFileInputStream = new FileInputStream("/proc/rgk_cameraInfo");
			InputStreamReader mInputStreamReader = new InputStreamReader(mFileInputStream);
			BufferedReader mBufferedReader = new BufferedReader(mInputStreamReader);			
			String cameraString = null;
			if((cameraString = mBufferedReader.readLine()) != null) {
				String[] cameraCh = cameraString.split(",");
				result.append(cameraCh[0]);
				//result.append(cameraCh[1]);
		    } else {
		    	result.append("unknow");
		    }
			mFileInputStream.close();
		} catch (Exception e) {
			result.append("No cameraInfo file found");
		}
		return result;
	}

		public StringBuilder getSubCamera() {
		StringBuilder result = new StringBuilder();
		try {
			FileInputStream mFileInputStream = new FileInputStream("/proc/rgk_cameraInfo");
			InputStreamReader mInputStreamReader = new InputStreamReader(mFileInputStream);
			BufferedReader mBufferedReader = new BufferedReader(mInputStreamReader);			
			String cameraString = null;
			if((cameraString = mBufferedReader.readLine()) != null) {
				String[] cameraCh = cameraString.split(",");
				result.append(cameraCh[1]);
				//result.append(cameraCh[1]);
		    } else {
		    	result.append("unknow");
		    }
			mFileInputStream.close();
		} catch (Exception e) {
			result.append("No cameraInfo file found");
		}
		return result;
	}	
	
	public StringBuilder RAMConvert() {
		long org = getTotalMemory();
		Log.i(TAG,"convert()->org="+org);
		double real = org/(1024*1024);	
		Log.i(TAG,"convert()->real="+real);
		if ((real >= 0.0) && (real < 1.0)) {
			real = 1.0;
		} else if ((real >=  1.0) && (real < 2.0)) {
			real = 2.0;
		} else if ((real >= 2.0) && (real < 3.0)) {
			real = 3.0; 
		} 
		Log.i(TAG,"convert()->real="+real);
		//String dst_temp = Integer.toString(real);
		String dst_temp ="" + real;
		Log.i(TAG,"convert()->dst_temp="+dst_temp);
		StringBuilder dst = new StringBuilder(dst_temp);
		dst = dst.append("GB");
		Log.i(TAG,"convert()->dst="+dst);
		//NvRAMAgentHelper.writeVernoToNV(64*8+40,dst.toString());
		return dst;	
	}
	
	public StringBuilder ROMConvert() {
		long org = getEmmcMemory();
		Log.i(TAG,"convert()->org="+org);
		double real = org/(1024*1024*1024);
		Log.i(TAG,"convert()->real="+real);
		if ((real >= 0) && (real < 8)) {
			real = 8;
		} else if ((real >=  8) && (real < 16)) {
			real = 16;
		} else if ((real >= 16) && (real < 32)) {
			real = 32; 
		} else if ((real >= 32) && (real < 64)) {
			real = 64;
		} else {
			real = 128;
		}
		Log.i(TAG,"convert()->real="+real);
		//String dst_temp = Integer.toString(real);
		String dst_temp ="" + real;
		Log.i(TAG,"convert()->dst_temp="+dst_temp);
		StringBuilder dst = new StringBuilder(dst_temp);
		dst = dst.append("GB");
		Log.i(TAG,"convert()->dst="+dst);
		//NvRAMAgentHelper.writeVernoToNV(64*9+40,dst.toString());
		return dst;	
	}
	
	private long getTotalMemory() {
    	String mfileStr = "/proc/meminfo";
    	String str2;
    	String[] arrayOfString = null;
		long initial_memory = 0;
    	FileReader mFileReader;
    	BufferedReader mfileBuff;
    	try {
			mFileReader = new FileReader(mfileStr);
			mfileBuff = new BufferedReader(mFileReader);
			str2 = mfileBuff.readLine();
			
			arrayOfString = str2.split("\\s+");
			for(String num:arrayOfString) {
				Log.i(str2,num+"\t");
			}
			initial_memory = Integer.valueOf(arrayOfString[1]).intValue();
			mfileBuff.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG,"initial_memory = "+initial_memory);
    	return initial_memory;
    }

    private long getEmmcMemory()
    {
    	StorageManager mStorageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
    	StorageVolume[] storageVolumes = mStorageManager.getVolumeList(); 
    	if (storageVolumes != null) {
	    	for (int i=0;i<storageVolumes.length;i++)
	    	{
	    		if(!storageVolumes[i].isRemovable())
	    		{
	    			StatFs stat = new StatFs(storageVolumes[i].getPath());
	    			long blockSize = stat.getBlockSize();
	    			long totalBlocks = stat.getBlockCount();
	    			/*if (totalBlocks != 0) {
	    				in_SDCard = true;
	    			}*/
					Log.d(TAG,"blockSize = "+blockSize+", totalBlocks = "+totalBlocks);
	    			return blockSize * totalBlocks;
	    		}
	    	}
    	}
    	return 0;
    }	
	
	private  String getEmmcAvailMemory()
    {
    	StorageManager mStorageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
    	StorageVolume[] storageVolumes = mStorageManager.getVolumeList(); 
    	if (storageVolumes != null) {
	    	for (int i=0;i<storageVolumes.length;i++)
	    	{
	    		if(!storageVolumes[i].isRemovable())
	    		{
	    			StatFs stat = new StatFs(storageVolumes[i].getPath());
	    			long blockSize = stat.getBlockSize();
	    			long totalBlocks = stat.getBlockCount();
	    			long availableBlocks = stat.getAvailableBlocks();
	    			return Formatter.formatFileSize(this, blockSize * availableBlocks);
	    		}
	    	}
    	}
    	return "";
    }

	public String getLcd() {
		String result = "";
		try {
            FileInputStream fis = new FileInputStream("/proc/rgk_lcdInfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);
            String ch = null;
            while ((ch = br.readLine()) != null) {
            	result = ch;
            }
            fis.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return result;
	}

	public String getChip() {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream("/proc/cpuinfo");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr, 4096);
            String ch = br.readLine();
            String[] strArr = null;
            while(ch != null) {
            	Log.i(TAG,"getChip()->ch="+ch);
            	if(ch.contains("Hardware")) {
            		strArr = ch.split(":");
            		result = strArr[1].trim();
            		break;
            	} else {
            		ch = br.readLine();
            	}
            }
            fis.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return result;
	}
	
    private void showDialog() {
        new AlertDialog.Builder(this)
        .setTitle(R.string.reset_phone_title)
        .setMessage(R.string.reset_phone_message)
        .setPositiveButton(R.string.reset_phone_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
            	sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
            }
        })
        .setNegativeButton(R.string.reset_phone_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
            }
        }).show();
    }
    
    class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i(TAG, "doInBackground()");
			new Util(MainActivity.this).initValue();
			writeVernoToNV();
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			Log.i(TAG, "onPostExecute():result="+result);
			super.onPostExecute(result);
		}
    	
    }
}
