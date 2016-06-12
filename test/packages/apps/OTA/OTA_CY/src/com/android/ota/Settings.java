package com.android.ota;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import android.content.Intent;
import android.preference.PreferenceCategory;

public class Settings extends PreferenceActivity implements OnPreferenceClickListener,OnPreferenceChangeListener {
	CheckBoxPreference updateCheckBoxPreference;
	ListPreference updateRatePreference;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.xml.settings);
	        updateCheckBoxPreference=(CheckBoxPreference)findPreference("allow_download");
	        updateRatePreference=(ListPreference)findPreference("update_rate");	       
	        updateRatePreference.setOnPreferenceClickListener(this);
	        updateRatePreference.setOnPreferenceChangeListener(this);
			boolean bolauto =this.getResources().getBoolean(R.bool.allow_wifi_auto_update);
			if(bolauto){
				 updateCheckBoxPreference.setOnPreferenceClickListener(this);
				 //自动下载设置，状态初始化
				String auto_download=ActivityUtils.getParameter(Settings.this, "allow_wifi_auto_download");
				if(auto_download.equals("")||auto_download.equals("0")){
					 ActivityUtils.setParameter(Settings.this, "allow_wifi_auto_download", "0");
					 updateCheckBoxPreference.setChecked(false);
				}
				else if(auto_download.equals("1")){
					 updateCheckBoxPreference.setChecked(true);
				}
			}
			else {
			      ActivityUtils.setParameter(Settings.this, "allow_wifi_auto_download", "0");
				  updateCheckBoxPreference.setChecked(false);
				  PreferenceCategory pcategory=(PreferenceCategory)findPreference("download_settings");
				  this.getPreferenceScreen().removePreference(pcategory);				
			}
			
			//更新设置，状态初始化
	        String strChecked= ActivityUtils.getParameter(Settings.this, "yulong_cota_autoupdate");
			int imChecked=0;
			if(strChecked.equals("")||strChecked.equals("0")){
				imChecked=0;
				updateCheckBoxPreference.setEnabled(false);
				updateCheckBoxPreference.setSummary(R.string.disable_wifi_download);
			}
			else if(strChecked.equals("1")){
				imChecked=1;
			}
			updateRatePreference.setValueIndex(imChecked);	        
	        updateRatePreference.setSummary(updateRatePreference.getEntry());
	        initActionBar();
	   }
	@Override
	public boolean onPreferenceClick(Preference preference) {
		 if (preference == updateCheckBoxPreference) {
	            if (updateCheckBoxPreference.isChecked()) {
				   ActivityUtils.setParameter(Settings.this, "allow_wifi_auto_download", "1");//允许自动下载	               
	            } else {
					ActivityUtils.setParameter(Settings.this, "allow_wifi_auto_download", "0");//不允许wifi自动下载	            	
	            }
	     } 		
		return true;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {	
		if (preference == updateRatePreference) {		
			//Toast.makeText(Settings.this, "newValue="+newValue, 1).show();
			boolean bolauto =this.getResources().getBoolean(R.bool.allow_wifi_auto_update);
			int value=Integer.parseInt((String) newValue);
			int index=0;
			if(value==0){
				   ActivityUtils.setParameter(Settings.this, "yulong_cota_autoupdate", "0");//不自动更新
				   ActivityUtils.setParameter(Settings.this, "allow_wifi_auto_download", "0");//wifi 不自动下载
				   //禁止掉wifi自动下载选项			  
				   if(bolauto){
						updateCheckBoxPreference.setEnabled(false);
						updateCheckBoxPreference.setChecked(false);
						updateCheckBoxPreference.setSummary(R.string.disable_wifi_download);						
				   }
	               //取消3天提醒下载	                             
	               Intent itt1 = new Intent(Intent.ACTION_RUN);
	         	   itt1.setAction("com.android.BootService");  
	               itt1.setPackage(getPackageName());
	               ActivityUtils.setNextNotify(Settings.this,itt1,false,ActivityUtils.quency_days);
				   index=0;
			}
			else if(value==3){
				  ActivityUtils.setParameter(Settings.this, "yulong_cota_autoupdate", "1");//自动更新
				  //启用wifi自动下载选项
				  if(bolauto){
					  updateCheckBoxPreference.setEnabled(true);
					  updateCheckBoxPreference.setChecked(true);
					  updateCheckBoxPreference.setSummary(R.string.allow_wifi_download);					 
					  ActivityUtils.setParameter(Settings.this, "allow_wifi_auto_download", "1");					  
				  }				  
				   //开始计时，3天后提醒下载
				  Intent itt2 = new Intent(Intent.ACTION_RUN);
	         	  itt2.setAction("com.android.BootService");  
	         	  itt2.setPackage(getPackageName());
	         	  ActivityUtils.setNextNotify(Settings.this,itt2,true,ActivityUtils.quency_days);
				  index=1;
			}			
			updateRatePreference.setSummary(updateRatePreference.getEntries()[index]);
        }
		 return true;
	}
	private void initActionBar(){
//		ActionBar bar = this.getActionBar();
//		bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);
//		bar.setDisplayUseLogoEnabled (false);
	}
}
