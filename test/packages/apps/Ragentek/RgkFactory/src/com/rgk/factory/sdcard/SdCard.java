
//file create by liunianliang 20130718~20130724

package com.rgk.factory.sdcard;

import java.io.File;

import com.rgk.factory.Config;
import com.rgk.factory.NvRAMAgentHelper;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
public class SdCard extends Activity implements View.OnClickListener {

	public static String TAG = "Sdcard";
	private boolean hadSendBroadcast = false;
    private boolean in_SDCard = false;
	private boolean exter_SDCard = false;
	private Handler mHanler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setTitle(R.string.SDcard);
		setContentView(R.layout.sdcard);
		RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.sdcard_layout);
		mLayout.setSystemUiVisibility(0x00002000);
		((Button) findViewById(R.id.sdcard_pass)).setOnClickListener(this);
		((Button) findViewById(R.id.sdcard_fail)).setOnClickListener(this);

		((TextView) findViewById(R.id.internal_total)).setText(getResources().getString(R.string.total)+getEmmcMemory());
		((TextView) findViewById(R.id.internal_free))
				.setText(getResources().getString(R.string.free)+getEmmcAvailMemory());
		//NvRAMAgentHelper.writeVernoToNV(64*9+40,getEmmcAvailMemory());
		((TextView) findViewById(R.id.external_total))
				.setText(getResources().getString(R.string.total)+getSDCardMemory());
		((TextView) findViewById(R.id.external_free))
				.setText(getResources().getString(R.string.free)+getSDCardAvailMemory());
		mHanler.postDelayed(runnable, 3000);
	}

	private Runnable runnable = new Runnable() {
	        @Override
			public void run() {
	            try {
	                Thread.currentThread().sleep(1500);
					if(in_SDCard && exter_SDCard) {
                        SendBroadcast(Config.PASS);
					} else {
                        SendBroadcast(Config.FAIL);
					}
				} catch (Exception e) {
	                e.printStackTrace();
				}
			}
		};


	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		   case R.id.sdcard_pass:
			   SendBroadcast(Config.PASS);
			   break;
		   case R.id.sdcard_fail:
			   SendBroadcast(Config.FAIL);
			   break;
		}
	}
	
    private  String getEmmcMemory()
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
	    			if (totalBlocks != 0) {
	    				in_SDCard = true;
	    			}
	    			return Formatter.formatFileSize(this, blockSize * totalBlocks);
	    		}
	    	}
    	}
    	return "";
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
    
    private  String getSDCardMemory()
    {
    	StorageManager mStorageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
    	StorageVolume[] storageVolumes = mStorageManager.getVolumeList(); 
    	if (storageVolumes != null) {
	    	for (int i=0;i<storageVolumes.length;i++)
	    	{
	    		if(storageVolumes[i].isRemovable())
	    		{
	    			StatFs stat = new StatFs(storageVolumes[i].getPath());
	    			long blockSize = stat.getBlockSize();
	    			long totalBlocks = stat.getBlockCount();
	    			if (totalBlocks != 0) {
	    				((TextView) findViewById(R.id.external_sdcard))
	    						.setText(getResources().getString(
	    								R.string.inserted_externalsdcard));
	    				exter_SDCard = true;
	    			}
	    			return Formatter.formatFileSize(this, blockSize * totalBlocks);
	    		}
	    	}
    	}
    	return "";
    }
    
    private  String getSDCardAvailMemory()
    {
    	StorageManager mStorageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
    	StorageVolume[] storageVolumes = mStorageManager.getVolumeList(); 
    	if (storageVolumes != null) {
	    	for (int i=0;i<storageVolumes.length;i++)
	    	{
	    		if(storageVolumes[i].isRemovable())
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

	public void SendBroadcast(String result) {
		mHanler.removeCallbacks(runnable);
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			AutoTestHandle.autoTest(this, TAG);
			//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));
			finish();
		}
	}

}