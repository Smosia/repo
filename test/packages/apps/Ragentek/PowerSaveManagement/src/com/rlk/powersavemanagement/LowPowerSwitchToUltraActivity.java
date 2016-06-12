package com.rlk.powersavemanagement;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LowPowerSwitchToUltraActivity extends Activity implements OnClickListener{
	private static final String TAG = "PowerSave/LowPowerSwitchToUltra";
	private SharedPreferences mLowerSharedPref;
	private SharedPreferences.Editor mLowerEditor;
	private Button cancleBt;
	private Button confirmBt;
	protected static final String SCREEN_OFF = "screen_off";
	private int mBattery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        Intent intent = getIntent();
        if(intent !=null){       	
        	mBattery = intent.getIntExtra("battery", 0);
        	Log.d(TAG, "mBattery:"+mBattery);
        }
		setContentView(R.layout.low_power_switch_ultra);
		setTitle();
		cancleBt = (Button)findViewById(R.id.cancel_button);
		confirmBt = (Button)findViewById(R.id.ok_button);
		cancleBt.setOnClickListener(this);
		confirmBt.setOnClickListener(this);
		mLowerSharedPref = getSharedPreferences(PowerSaveUtils.REFS_LOW_POWER, 0);
		mLowerEditor = mLowerSharedPref.edit();
		mLowerEditor.putBoolean(PowerSaveUtils.need_show_prompt_switch_ultra, false);
		mLowerEditor.commit();
	}
    private void setTitle(){
    	String title = this.getResources().getString(R.string.battery_low_title);
		title = title+"("+mBattery+"%)";
		setTitle(title);
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		// TODO Auto-generated method stub
		super.onDestroy();
	}
    
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(mReceiver);
		Log.d(TAG, "onPause");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		finish();
	}
   
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
				mBattery = intent.getIntExtra("level", 0);
				setTitle();
				if(status == BatteryManager.BATTERY_STATUS_CHARGING){
					LowPowerSwitchToUltraActivity.this.finish();
				}
			}
		}		
	};  
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.cancel_button:
			finish();
			break;
		case R.id.ok_button:
			startActivity(new Intent(this,UltraSwitchPromptActivity.class));
			finish();
			break;
		default:
			break;
		}
	}
}
