package com.android.settings.deviceinfo;

import java.io.File;

import com.android.settings.R;
import android.app.ActivityManager;
import android.content.Context;
import android.app.ActivityManager.MemoryInfo;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Process;
import android.os.storage.StorageManager;
import android.os.SystemProperties;
import android.preference.Preference;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.internal.util.MemInfoReader;
import com.android.settings.applications.RunningState;

public class CyStorageRamPreference extends Preference {
	private ActivityManager am;
	private float mUsedPercent = -1.0f;
	private int mColor;
	private Context mContext;
	private MemInfoReader mMemInfoReader;
	private long totalSize;
	private String free;
	private long FOREGROUND_APP_MEM;
	private TextView tv;
	private  CricleProgressBar progress;
	public CyStorageRamPreference(Context context, int color,long availmemory){
		super(context);
		Log.i("shus", "CyStorageRamPreference start");
		mColor = color;
		mContext = context;
		setLayoutResource(R.layout.cystorage_volume);
		
		 final Resources res = context.getResources();
	     final int width = res.getDimensionPixelSize(R.dimen.device_memory_color_width);
	     final int height = res.getDimensionPixelSize(R.dimen.device_memory_color_width);
	     setIcon(createRectShape(width, height, mColor));
		setTitle(context.getString(R.string.cyRam));
		mMemInfoReader = new MemInfoReader();
		//ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
		//FOREGROUND_APP_MEM = memInfo.foregroundAppThreshold;
		
		totalSize = updateTotalRamSize();
        final long freeBytes = availmemory;
        final long usedBytes = totalSize - freeBytes;
            
            final String used = Formatter.formatFileSize(context, usedBytes);
            final String total = Formatter.formatFileSize(context, totalSize);
            free = Formatter.formatFileSize(context, freeBytes);
            setSummary(context.getString(R.string.cystorage_ram_summary, total));
            mUsedPercent = (float) ((usedBytes * 100) / totalSize);
            Log.i("shus", "CyStorageRamPreference end");
	}
	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
		tv = (TextView)view.findViewById(R.id.text);
		tv.setText(mContext.getString(R.string.cystorage_ram_text, free));
		
		progress = (CricleProgressBar)view.findViewById(R.id.cricleProgress);
		if(mUsedPercent != -1.0f){
			progress.setCricleProgressColor(mColor);
			progress.setProgressWithAnimation(mUsedPercent);
			progress.setTextColor(mColor);
			
		}
		
	}
	private static ShapeDrawable createRectShape(int width, int height, int color) {
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.setIntrinsicHeight(height);
        shape.setIntrinsicWidth(width);
        shape.getPaint().setColor(color);
        return shape;
    }
	/**
	 * 
	 */
	public void updateCleanRam(long avail){
		
		tv.setText(mContext.getString(R.string.cystorage_ram_text,
				Formatter.formatFileSize(mContext, avail)));
		progress.setProgresses((float) (((totalSize-avail )* 100) / totalSize));
	}
	public long updateTotalRamSize() {
		mMemInfoReader.readMemInfo();
		long mtotalSize = mMemInfoReader.getTotalSize();
		if(mtotalSize < 512*1024*1024L){
			mtotalSize = 512*1024*1024L;
		}else{
			int data = (int)(mtotalSize  / 1024 / 1024 / 1024);
			mtotalSize = (long)(data+1)*1024*1024*1024L ;
		}
		/**
		double data = mtotalSize * 1d / 1024 / 1024 / 1024;
		Log.d("shubin", " data = " + data + ", ceilData = " + Math.ceil(data));
		mtotalSize = (long) (Math.ceil(data) * 1024 * 1024 * 1024);
		Log.d("shubin", "[updateTotalRamSize] totalSize = " + totalSize);
		*/
		return mtotalSize;
	}
	/**
	public long AvailabelRamSize() {
		long extraAvailableSize = 0;
		long availMem = 0;
		final RunningState mState = RunningState.getInstance(mContext);

		mMemInfoReader.readMemInfo();
		final float zramCompressRatio = Process.getZramCompressRatio(); 
		if (SystemProperties.getBoolean("mediatek.virtual_zram_extra", false)) {
			extraAvailableSize += Process.getZramExtraAvailableSize();
		} else {
			final long anonReserve = 15 * 1024 * 1024; // 15MB for reserved annon memory
            long anonToCompress = Process.getLruAnonMemory() - anonReserve - mState.mBackgroundProcessMemory;
            //Log.d(TAG, "Process.getLruAnonMemory() = " + Process.getLruAnonMemory());
           // Log.d(TAG, "mState.mBackgroundProcessMemory = " + mState.mBackgroundProcessMemory);
            if (anonToCompress > 0) {
                extraAvailableSize = (long)(anonToCompress * (1.0f - 1.0f/zramCompressRatio));
            }
            else {
                //Log.d(TAG, "!!!ERROR!!! annonToCompress = " + anonToCompress);
            }
            /// M: Consider following parts to show MemoryFree for foreground app @{
            /// FOREGROUND_APP_MEM: to consider LMK threshold
            /// Mapped, Buffer: Counting caches that are mapped in to processes.
            if (extraAvailableSize > 0) {
                extraAvailableSize = extraAvailableSize
                                    + mMemInfoReader.getMappedSize()
                                    - mMemInfoReader.getBuffersSize()
                                    - FOREGROUND_APP_MEM;
            }
        }
        //Log.d(TAG, "extraAvailableSize = " + extraAvailableSize);
		availMem = (long)(mMemInfoReader.getFreeSize() + mMemInfoReader.getCachedSize()
                + mState.mBackgroundProcessMemory + extraAvailableSize
                + (float)(mState.mBackgroundProcessSwapMemory*1.0f)/zramCompressRatio);
		return availMem;
	}
	*/
}
