package com.android.settings.deviceinfo;

import java.io.File;

import com.android.settings.R;
import com.android.settings.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.preference.Preference;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;

public class CyStorageVolumePreference extends Preference {
	private  StorageManager mStorageManager;
	private float mUsedPercent = -1f;
	private int mColor;
	private  VolumeInfo mVolume;
	private Context mContext;
	private  String free;
	private File file;
	private long ROM = 8*1024*1024*1024L;
	
	public CyStorageVolumePreference(Context context, VolumeInfo volume, int color){
		super(context);
		
		mStorageManager = context.getSystemService(StorageManager.class);
		mVolume = volume;
		mColor = color;
		mContext = context;
		setLayoutResource(R.layout.cystorage_volume);
		
		
		setKey(volume.getId());
		setTitle(mStorageManager.getBestVolumeDescription(volume));
		file = mVolume.getPath();
		
		if (volume.isMountedReadable()) {
			final Resources res = context.getResources();
	        final int width = res.getDimensionPixelSize(R.dimen.device_memory_color_width);
	        final int height = res.getDimensionPixelSize(R.dimen.device_memory_color_width);
	        setIcon(createRectShape(width, height, mColor));
			
			final File path = volume.getPath();
            final long freeBytes = path.getFreeSpace();
             long totalBytes = path.getTotalSpace();
            final long usedBytes = totalBytes - freeBytes;
            
            final String used = Formatter.formatFileSize(context, usedBytes);
            String total = Formatter.formatFileSize(context, totalBytes);
            free = Formatter.formatFileSize(context, freeBytes);
            if(file.getPath().equals("/data")){
            	long totalsize = totalBytes+Utils.getSystemTotalSpace();
            	if(totalsize < 4*1024*1024*1024L){
            		totalBytes = 4*1024*1024*1024L;
            	}else if(totalsize < 8*1024*1024*1024L){
            		totalBytes = 8*1024*1024*1024L;
            	}else if (totalsize < 16*1024*1024*1024L){
            		totalBytes = 16*1024*1024*1024L; 
            	}
            	total = Formatter.formatFileSize(context, totalBytes);
            	setSummary(context.getString(R.string.cystorage_volume_summary, total));
            	
            }else{
            	setSummary(context.getString(R.string.cystorage_ram_summary, total));
            }
            final long UsedPercent = ((totalBytes - freeBytes) * 100) / totalBytes;
            if(usedBytes != 0 && UsedPercent == 0){
            	mUsedPercent =  1.0f;
            }else{
            	
            	mUsedPercent = (float) (((totalBytes - freeBytes) * 100) / totalBytes);
            }
        
		}else{
			setSummary(volume.getStateDescription());
            mUsedPercent = -1.0f;
		}
		
	}
	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
		if(mVolume.isMountedReadable()){
			final TextView tv = (TextView)view.findViewById(R.id.text);
			if(file.getPath().equals("/data")){
				
				tv.setText(mContext.getString(R.string.cystorage_volume_text, free));
			}else{
				tv.setText(mContext.getString(R.string.cystorage_ram_text, free));
			}
		}
		
		final CricleProgressBar progress = (CricleProgressBar)view.findViewById(R.id.cricleProgress);
		if(mUsedPercent != -1.0f){
			Log.i("shuin", "mUsedPercent++"+mUsedPercent);
			progress.setCricleProgressColor(mColor);
			progress.setProgressWithAnimation(mUsedPercent);
		
			progress.setTextColor(mColor);
			
		}else{
			progress.setVisibility(View.GONE);
		}
		
	}
	private static ShapeDrawable createRectShape(int width, int height, int color) {
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.setIntrinsicHeight(height);
        shape.setIntrinsicWidth(width);
        shape.getPaint().setColor(color);
        return shape;
    }
}
