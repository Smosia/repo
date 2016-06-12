package com.android.fmradio;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
//A:DDSYYLM-9 wanglin 20160107 {
public class FmActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		Log.d("qinzhaojin", " is AirPlane Mode == " + isAirplaneModeOn(this));
	   if(isAirplaneModeOn(this)){
		   	Toast.makeText(this, R.string.cannot_startup_during_airplane, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}  else {
			Intent intent = new Intent();
			intent.setClass(FmActivity.this, FmMainActivity.class);
			startActivity(intent);
			finish();
		}
	}

    private boolean isAirplaneModeOn(Context context){
	    	return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	    }
}
//A}