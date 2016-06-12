package com.rlk.powersavemanagement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Settings.System;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;

public class UltraMain extends Activity implements View.OnClickListener,
		CompoundButton.OnCheckedChangeListener {
	private static final String TAG = "PowerSave/UltraMain";
	private Switch mActionBarSwitch;
	private TextClock amPmTc;
	private TextClock clockTimeTc;
	// private TextClock dateTc;
	private DateView dateTc;
	private TextView mStandbyHour;
	private TextView mStandbyMinute;
	private TextClock weekTc;
	private ImageView phoneIv;
	private ImageView smsmmsIv;
	private ImageView notebookIv;
	private ImageView clockIv;
	private ImageView soundRecordIv;
	private ImageView calculatorIv;
	private TextView phoneTv;
	private TextView smsmmsTv;
	private TextView notebookTv;
	private TextView calculatorTv;
	private TextView soundRecordTv;
	private TextView clockTv;
	private LayoutInflater inflater;
	private PackageManager mPm;
	private String[] packageName = { "com.android.dialer", "com.android.mms",
			"com.rlk.powersavemanagement", "com.android.calculator2",
			"com.android.soundrecorder", "com.android.deskclock" };
	private String[] className = { "com.android.dialer.DialtactsActivity",
			"com.android.mms.ui.BootActivity",
			"com.rlk.powersavemanagement.PowerSaveMainActivity",
			"com.android.calculator2.Calculator",
			"com.android.soundrecorder.SoundRecorder",
			"com.android.deskclock.DeskClock" };
	private static List<ResolveInfo> mAppsList;
	private static final String SYSTEM = "/system/fonts/";
	private static final String SYSTEM_FONT_TIME_BACKGROUND = SYSTEM
			+ "AndroidClock.ttf";
	String SYSTEM_FONT_AMPM_BACKGROUND = SYSTEM + "AndroidClock.ttf";
	private static Typeface sClockTypeface;
	private static Typeface sAmPmTypeface;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		Log.d(TAG, "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*
		setContentView(R.layout.ultra_main_view);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY,
				WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY);
		*/
		setContentView(R.layout.ultra_main_view);
                getWindow().setFlags(0X08000000,0X08000000);
		mPm = getPackageManager();
		initialLayout();
		String str = getDateFormat(this);
		ActivityContainer.getContainer().putActivity(this);
	}

	private String getLabelForPhone(String pkgName) {

		PackageManager mPm = getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mainIntent.setPackage(packageName[0]);
		mAppsList = mPm.queryIntentActivities(mainIntent, 0);
		if (mAppsList != null && mAppsList.size() > 0) {
			ResolveInfo info = mAppsList.get(0);
			CharSequence label = info.activityInfo
					.loadLabel(getPackageManager());
			return (String) label;
		}
		return getAppName(pkgName);
	}

	private void initialLayout() {
		mActionBarSwitch = (Switch) findViewById(R.id.imageswitch);
		phoneIv = ((ImageView) findViewById(R.id.phone_imgView));
		notebookIv = ((ImageView) findViewById(R.id.notebook_imgView));
		smsmmsIv = ((ImageView) findViewById(R.id.smsmms_imgView));
		clockIv = ((ImageView) findViewById(R.id.alarmclock_imgView));
		soundRecordIv = ((ImageView) findViewById(R.id.soundrecord_imgView));
		calculatorIv = ((ImageView) findViewById(R.id.calculator_imgView));
		phoneTv = ((TextView) findViewById(R.id.phone_tv));
		notebookTv = ((TextView) findViewById(R.id.notebook_tv));
		soundRecordTv = ((TextView) findViewById(R.id.soundrecord_tv));
		clockTv = ((TextView) findViewById(R.id.alarmclock_tv));
		smsmmsTv = ((TextView) findViewById(R.id.smsmms_tv));
		calculatorTv = ((TextView) findViewById(R.id.calculator_tv));
		// dateTc = ((TextClock) findViewById(R.id.date));
		dateTc = ((DateView) findViewById(R.id.date));
		weekTc = ((TextClock) findViewById(R.id.week));
		clockTimeTc = ((TextClock) findViewById(R.id.the_clock));
		amPmTc = ((TextClock) findViewById(R.id.the_clock2));
		mStandbyHour = ((TextView) findViewById(R.id.standby_hour));
		mStandbyMinute = ((TextView) findViewById(R.id.standby_minute));
		// phoneIv.setImageDrawable(getAppIcon(packageName[0]));
		// notebookIv.setImageDrawable(getAppIcon(packageName[1]));
		// smsmmsIv.setImageDrawable(getAppIcon(packageName[2]));
		// clockIv.setImageDrawable(getAppIcon(packageName[3]));
		phoneTv.setText(getLabelForPhone(packageName[0]));
		notebookTv.setText(getAppName(packageName[2]));
		smsmmsTv.setText(getAppName(packageName[1]));
		clockTv.setText(getAppName(packageName[5]));
		calculatorTv.setText(getAppName(packageName[3]));
		soundRecordTv.setText(getAppName(packageName[4]));
		if (sClockTypeface == null) {
			sClockTypeface = Typeface
					.createFromFile(SYSTEM_FONT_TIME_BACKGROUND);
		}
		if (sAmPmTypeface == null) {
			sAmPmTypeface = Typeface
					.createFromFile(SYSTEM_FONT_AMPM_BACKGROUND);
		}
		dateTc.setTypeface(sClockTypeface);
		weekTc.setTypeface(sClockTypeface);
		clockTimeTc.setTypeface(sClockTypeface);
		amPmTc.setTypeface(sAmPmTypeface);
		phoneIv.setOnClickListener(this);
		notebookIv.setOnClickListener(this);
		smsmmsIv.setOnClickListener(this);
		clockIv.setOnClickListener(this);
		soundRecordIv.setOnClickListener(this);
		calculatorIv.setOnClickListener(this);
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.ultra_menu, paramMenu);
		return true;
	}

	public Drawable getAppIcon(String packname) {
		try {
			ApplicationInfo info = mPm.getApplicationInfo(packname, 0);
			return info.loadIcon(mPm);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return getAppIcon(getPackageName());
	}

	public String getAppName(String packname) {
		Log.d(TAG, "getAppName");
		try {
			ApplicationInfo info = mPm.getApplicationInfo(packname, 0);
			Log.d(TAG, "getAppName 11");
			return info.loadLabel(mPm).toString();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "getAppName 22");
		return getAppName(getPackageName());
	}

	protected void onDestroy() {
		getContentResolver().unregisterContentObserver(mUltraPowerObserver);
		// unregisterReceiver(mTimeChangerReceiver);
		Log.d(TAG, "onDestroy");
		ActivityContainer.getContainer().removeActivity(UltraMain.class.toString());
		super.onDestroy();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onStop");
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case R.id.ultra_settings:
			startActivity(new Intent(this, UltraSettings.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		unregisterReceiver(mBatteryInfoReceiver);
		unregisterReceiver(mReceiver);
	}

	protected void onResume() {
		super.onResume();

		boolean isUltraModeOn = PowerSaveUtils
				.isUltraPowerOn(getApplicationContext());
		Log.d(TAG, "onResume isUltraModeOn = " + isUltraModeOn);
		mActionBarSwitch.setChecked(isUltraModeOn);
		mActionBarSwitch.setOnCheckedChangeListener(this);
		registerReceiver(mBatteryInfoReceiver, new IntentFilter(
				"com.rlk.powersavemanagement.ACTION_AVAILABLE_TIME"));
		IntentFilter localIntentFilter = new IntentFilter();
		/*
		 * localIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
		 * localIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		 * localIntentFilter.addAction(Intent.ACTION_TIME_TICK);
		 * localIntentFilter.addAction(Intent.ACTION_LOCALE_CHANGED);
		 */
		localIntentFilter.addAction("launcher.intent.action.DESKTOP_SORT_OUT");
		registerReceiver(mReceiver, localIntentFilter);
		dateTc.updateClock();
		getContentResolver().registerContentObserver(
				Settings.Global.getUriFor(Settings.Global.ULTRA_POWER_MODE),
				true, mUltraPowerObserver);
	}

	private ContentObserver mUltraPowerObserver = new ContentObserver(
			new Handler()) {
		public void onChange(boolean paramBoolean) {

		}
	};

	private String getDateFormat(Context paramContext) {
		String str = Settings.System.getString(
				paramContext.getContentResolver(),
				android.provider.Settings.System.DATE_FORMAT);
		if ((str == null) || (str.length() == 0)) {
			str = "yyyy-MM-dd";
		}
		String format = DateFormat.getDateFormat(paramContext).format(
				new Date());
		// String format = DateFormat.getDateFormatForSetting(paramContext,
		// str).format(Calendar.getInstance().getTime());
		return format;
	}

	public void onBackPressed() {
		// super.onBackPressed();
	}

	public void onCheckedChanged(CompoundButton paramCompoundButton,
			boolean paramBoolean) {
		Log.d(TAG, "onCheckedChanged stated:" + paramBoolean);
		if (!paramBoolean) {
			startActivity(new Intent(this, UltraSwitchPromptActivity.class));
		}
		// PowerSaveUtils.setUltraPowerState(this, paramBoolean);
		// finish();
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {

		case R.id.phone_imgView:
			setActivity(this, new ComponentName(packageName[0], className[0]));
			break;
		case R.id.calculator_imgView:
			setActivity(this, new ComponentName(packageName[3], className[3]));
			break;
		case R.id.smsmms_imgView:
			setActivity(this, new ComponentName(packageName[1], className[1]));
			break;
		case R.id.alarmclock_imgView:
			setActivity(this, new ComponentName(packageName[5], className[5]));
			break;
		case R.id.notebook_imgView:
			setActivity(this, new ComponentName(packageName[2], className[2]));
			break;
		case R.id.soundrecord_imgView:
			setActivity(this, new ComponentName(packageName[4], className[4]));
			break;
		default:
			break;
		}
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if ("launcher.intent.action.DESKTOP_SORT_OUT".equals(intent
					.getAction())) {
				UltraMain.this.finish();
			} else {
				String dateFormat = getDateFormat(context);
				// dateTc.setFormat12Hour(dateFormat);
				// dateTc.setFormat24Hour(dateFormat);
				dateTc.setText(getDateFormat(context));
			}
		}

	};

	private BroadcastReceiver mBatteryInfoReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (PowerSaveUtils.ACTION_AVAILABLE_TIME.equals(action)) {
				Log.i(TAG, "ACTION_AVAILABLE_TIME");
				int[] times = intent.getIntArrayExtra("available_time");
				mStandbyHour.setText(times[0] + "");
				mStandbyMinute.setText(times[1] + "");

			}
		}

	};

	final void setActivity(Context paramContext,
			ComponentName paramComponentName) {
		Intent localIntent = new Intent("android.intent.action.MAIN");
		localIntent.addCategory("android.intent.category.LAUNCHER");
		localIntent.setComponent(paramComponentName);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		paramContext.startActivity(localIntent);
	}
}
