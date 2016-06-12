package com.rlk.powersavemanagement;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
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
import android.app.StatusBarManager;

public class UltraSwitchPromptActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "PowerSave/UltraSwitchPromptActivity";

	private StatusBarManager mStatusBarManager;
	private TextView closeUltraTv;
	private TextView openUltraTvOne;
	private TextView openUltraTvTwo;
	private TextView openUltraTvThree;
	private Button cancleBt;
	private Button confirmBt;
	private boolean isUltraPowerOn;
	private LinearLayout ultraPromptTV;
	private LinearLayout ultraPromptBt;
	private ProgressBar progressBar;
	
	private boolean switching = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ultra_switch);
		setTitle(R.string.ultra_power_saving);
		cancleBt = (Button) findViewById(R.id.cancel_button);
		confirmBt = (Button) findViewById(R.id.ok_button);
		closeUltraTv = (TextView) findViewById(R.id.close_ultra_power_prompt);
		openUltraTvOne = (TextView) findViewById(R.id.open_ultra_power_prompt_one);
		openUltraTvTwo = (TextView) findViewById(R.id.open_ultra_power_prompt_two);
		openUltraTvThree = (TextView) findViewById(R.id.open_ultra_power_prompt_three);
		ultraPromptTV = (LinearLayout) findViewById(R.id.ultra_power_prompt);
		ultraPromptBt = (LinearLayout) findViewById(R.id.ultra_power_button);
		progressBar = (ProgressBar) findViewById(R.id.progress);
		cancleBt.setOnClickListener(this);
		confirmBt.setOnClickListener(this);
		ActivityContainer.getContainer().putActivity(this);
		mStatusBarManager  = (StatusBarManager)getSystemService(Context.STATUS_BAR_SERVICE);
	}
	
	private void disableHomeAndRecent() {
		if (mStatusBarManager == null) {
			Log.e("zhuwei", "disableHomeAndRecent error...mStatusBarManager is null!");
		} else {
			int flags = StatusBarManager.DISABLE_EXPAND
					| StatusBarManager.DISABLE_HOME
					| StatusBarManager.DISABLE_RECENT
					| StatusBarManager.DISABLE_SEARCH;
			mStatusBarManager.disable(flags);
		}
	}
	
	private void disableNone() {
		if (mStatusBarManager == null) {
			Log.e("zhuwei", "disableNone error...mStatusBarManager is null!");
		} else {
			int flag = StatusBarManager.DISABLE_NONE;
			mStatusBarManager.disable(flag);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
        update();
	}
	
	private void update() {
		isUltraPowerOn = PowerSaveUtils.isUltraPowerOn(getApplicationContext());
		if (switching) {
			ultraPromptTV.setVisibility(View.GONE);
			ultraPromptBt.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
			ultraPromptTV.setVisibility(View.VISIBLE);
			ultraPromptBt.setVisibility(View.VISIBLE);
			closeUltraTv.setVisibility(isUltraPowerOn ? View.VISIBLE : View.GONE);
			openUltraTvOne.setVisibility(isUltraPowerOn ? View.GONE : View.VISIBLE);
			openUltraTvTwo.setVisibility(isUltraPowerOn ? View.GONE
					: (PowerSaveUtils.isSupportScreenMode(this) ? View.VISIBLE
							: View.GONE));
			openUltraTvThree.setVisibility(isUltraPowerOn ? View.GONE
					: View.VISIBLE);
		}
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		ActivityContainer.getContainer().removeActivity(UltraSwitchPromptActivity.class.toString());
		disableNone();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		if (progressBar.getVisibility() != View.VISIBLE) {
			finish();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d(TAG, "ok button begin 3");
		super.onStop();
		Log.d(TAG, "ok button begin 4");
		isUltraPowerOn = PowerSaveUtils.isUltraPowerOn(this);
		Log.d(TAG, "onStop isUltraPowerOn : " + isUltraPowerOn);
		if (!isUltraPowerOn) {
			PowerAppWidgetProviderHios.getInstance().updateWidget(this);
		}
		Log.d(TAG, "ok button begin 5");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancel_button:
			finish();
			break;
		case R.id.ok_button:
			Log.d(TAG, "ok button begin 1");
			switching = true;
			disableHomeAndRecent();
			ultraPromptTV.setVisibility(View.GONE);
			ultraPromptBt.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			UltraSwitchPromptActivity.this.setFinishOnTouchOutside(false);
			updateUltraPowerSettings(getApplicationContext(), !isUltraPowerOn);
			Log.d(TAG, "ok button begin 2");
			break;
		default:
			break;
		}
	}

	public void updateUltraPowerSettings(final Context context,
			final boolean ultraPowerOn) {
		new Thread(new Runnable() {

			@Override
			public synchronized void run() {
				Log.d(TAG, "mUltraPowerObserver isUltraPowerOn:" + ultraPowerOn);
				PowerSaveUtils.setUltraPowerState(context, ultraPowerOn);
				PowerSaveUtils.setDefaultLauncher(context, ultraPowerOn);
				if (ultraPowerOn) {
					PowerSaveUtils.setUltraPowerSettings(context);
				    Helper.removeRecentTask(context, ultraPowerOn);
					int checkSce = 0;
					while (true) {
						try {
							if (checkSce >= 60) {
								Log.e("zhuwei", "updateUltraPowerSettings...checkSec over 60sec!!");
								break;	
							}
							if (PowerSaveUtils
									.checkSettingState(false, context))
								break;
							Thread.sleep(1000);
							checkSce ++;
						} catch (InterruptedException e) {

						}
					}
					PowerSaveUtils.startMainActivity(context);
					Activity ultraSwitchPromptActivity = ActivityContainer.getContainer()
							.getActivity(
									UltraSwitchPromptActivity.this.getClass()
											.toString());
					if (ultraSwitchPromptActivity != null) {
						ultraSwitchPromptActivity.finish();
					}
				} else {
					PowerSaveUtils.restoreBeforeSettings(context, true);
				}
				switching = false;
			}
		}).start();
	}

}
