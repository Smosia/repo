package com.rlk.powersavemanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class LowerPowerSaveActivity extends Activity implements
		OnCheckedChangeListener, OnSeekBarChangeListener {
	private static final String TAG = "PowerSave/LowerPowerSaveActivity";

	private SharedPreferences mLowerPowerShPrefs;
	private SharedPreferences.Editor mLowerEditor;
	private Switch mActionBarSwitch;
	private LayoutInflater inflater;
	private TextView mBatteryLimit;
	private SeekBar mBatteryBar;
	private int mBatteryValue;
	private boolean mIsLowerSaveOn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lower_power_save);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mActionBarSwitch = (Switch) inflater.inflate(
				com.mediatek.internal.R.layout.imageswitch_layout, null);
		mActionBarSwitch.setPadding(0, 0, 32, 0);
		ActionBar actionbar = getActionBar();
		if (actionbar != null) {
			
			actionbar.setCustomView(mActionBarSwitch,
					new ActionBar.LayoutParams(
							ActionBar.LayoutParams.WRAP_CONTENT,
							ActionBar.LayoutParams.WRAP_CONTENT,
							Gravity.CENTER_VERTICAL | Gravity.END));
			actionbar.setHomeButtonEnabled(true);
			actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
			actionbar.setDisplayHomeAsUpEnabled(true);
		}
		
		initialLayout();
		mLowerPowerShPrefs = getSharedPreferences(
				PowerSaveUtils.REFS_LOW_POWER, 0);
		mLowerEditor = mLowerPowerShPrefs.edit();
		mBatteryBar.setOnSeekBarChangeListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mIsLowerSaveOn = PowerSaveUtils.isLowerSaveOn(this);
		mBatteryValue = mLowerPowerShPrefs.getInt(
				PowerSaveUtils.lowerBatteryValue,
				getResources().getInteger(R.integer.lower_power_battery_value));
		mBatteryLimit.setText(this.getResources().getString(
				R.string.battery_level_limit, mBatteryValue));
		mBatteryBar.setProgress(mBatteryValue);
		mActionBarSwitch.setChecked(mIsLowerSaveOn);
		mActionBarSwitch.setOnCheckedChangeListener(this);
		mBatteryBar.setEnabled(mIsLowerSaveOn);
		getContentResolver().registerContentObserver(
				Settings.Global.getUriFor(Settings.Global.LOWER_SAVE_MODE),
				true, mLowerSaveObserver);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getContentResolver().unregisterContentObserver(mLowerSaveObserver);
	}

	private View getItemView(int context, int status) {
		LinearLayout item_setup_layout = (LinearLayout) inflater.inflate(
				R.layout.lower_power_item_setup, null, false);
		TextView content_tv = (TextView) item_setup_layout
				.findViewById(R.id.item_setup_content);
		TextView status_tv = (TextView) item_setup_layout
				.findViewById(R.id.item_setup_status);
		content_tv.setText(context);
		status_tv.setText(status);
		return item_setup_layout;
	}

	private void initialLayout() {
		mBatteryLimit = (TextView) findViewById(R.id.battery_limit);
		mBatteryBar = (SeekBar) findViewById(R.id.seekbar_battery);
		LinearLayout lower_power_layout = (LinearLayout) findViewById(R.id.lower_power_item);
	}

	private ContentObserver mLowerSaveObserver = new ContentObserver(
			new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			mActionBarSwitch.setChecked(PowerSaveUtils
					.isLowerSaveOn(LowerPowerSaveActivity.this));
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mIsLowerSaveOn = isChecked;
		PowerSaveUtils.setLowerSaveState(this, mIsLowerSaveOn);
		mLowerEditor.putBoolean(PowerSaveUtils.lowerPowerLaunch, false);
		mLowerEditor.commit();
		PowerSaveUtils.cancleNotification(this);
		mBatteryBar.setEnabled(mIsLowerSaveOn);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mBatteryBar.setProgress(progress);
		mBatteryLimit.setText(this.getResources().getString(
				R.string.battery_level_limit, progress));
		mBatteryValue = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mLowerEditor.putInt(PowerSaveUtils.lowerBatteryValue, mBatteryValue);
		mLowerEditor.putBoolean(PowerSaveUtils.lowerPowerLaunch, false);
		PowerSaveUtils.cancleNotification(this);
		mLowerEditor.commit();
	}
}
