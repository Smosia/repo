package com.android.settings.deviceinfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.os.storage.VolumeInfo;
import com.android.settings.R;
import com.android.settings.Utils;

public class CyPrivateProgressPre extends Preference  {
	private String ROM = "8.00GB";
	private TextView tv_rom;
	private CricleProgressBar progress;
	private String total;
	private int percent;
	private final ArrayList<CricleProgressBar.Entry> mEntries = new ArrayList<>();
	public CyPrivateProgressPre(Context context,VolumeInfo volume){
		super(context);
		setLayoutResource(R.layout.cystorage_private);
		final File path = volume.getPath();
        final long freeBytes = path.getFreeSpace();
        long totalBytes = path.getTotalSpace();
        final long usedBytes = totalBytes - freeBytes;
        final String used = Formatter.formatFileSize(context, usedBytes);
		if(path.getPath().equals("/data")){
			long totalsize = totalBytes+Utils.getSystemTotalSpace();
        	if(totalsize < 4*1024*1024*1024L){
        		totalBytes = 4*1024*1024*1024L;
        	}else if(totalsize < 8*1024*1024*1024L){
        		totalBytes = 8*1024*1024*1024L;
        	}else if (totalsize < 16*1024*1024*1024L){
        		totalBytes = 16*1024*1024*1024L; 
        	}
		}
		percent =  (int) (((totalBytes - freeBytes) * 100) / totalBytes);
		total = Formatter.formatFileSize(context, totalBytes);
	}
	
	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
		tv_rom = (TextView)view.findViewById(R.id.tv_rom);
		tv_rom.setText(total);
		
		progress = (CricleProgressBar)view.findViewById(R.id.cricleProgress);
		progress.setTextSize(30);
		progress.setCricleWidth(25);
		progress.setTextColor(Color.parseColor("#ff6e6e6e"));
		progress.setPercent(percent);
		progress.setEntries(mEntries);
	}
	
	 public void addEntry(float percentage, int color) {
		 if(progress != null){
			 
			 mEntries.add(progress.setProgress(percentage, color));
		 }
	        
	    }
	 
	 public void commit() {
	        if (progress != null) {
	        	progress.invalidate();
	        }
	    }

	    public void clear() {
	        mEntries.clear();
	    }
	
	
}
