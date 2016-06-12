package com.rlk.powersavemanagement;

import java.io.IOException;

import com.rlk.powersavepreference.BrightnessPreference;
import com.rlk.powersavepreference.LowerCheckBoxPreference;
import com.rlk.powersavepreference.RingerVolumePreference;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class PowerSavingModeActivity extends PreferenceActivity implements
		OnCheckedChangeListener, Preference.OnPreferenceChangeListener,
		PowerSavePromptDialogFragment.OnSlectDialogClickListener {
	private static final String TAG = "PowerSave/ModeActivity";

	private SharedPreferences mPowerSavingShPrefs;
	private SharedPreferences.Editor mEditor;
	private AudioManager mAudioManager;

	private static final String POWER_SAVE_SETTING_KEY = "power_save_mode_set";
	private PreferenceCategory powerSaveSettingsCategory;
	private static final String LOWER_POWER_KEY = "lower power";
	private LowerCheckBoxPreference lowerPowerPref;

	private Switch actionBarSwitch;
	private CheckBoxPreference screenPowerSavingPref;
	private CheckBoxPreference wifiPref;
	private CheckBoxPreference wifiHotspotSavingPref;
	private CheckBoxPreference bluetoothSavingPref;
	private CheckBoxPreference dataPref;
	private CheckBoxPreference threeGPref;
	private CheckBoxPreference vibrationPref;
	private CheckBoxPreference tactileFeedbackPref;
	private CheckBoxPreference gpsPref;
	private CheckBoxPreference flashLightPref;
	private CheckBoxPreference speakerPref;
	private CheckBoxPreference keyTongPref;
	private CheckBoxPreference autoSyncPref;
	private BrightnessPreference brightnessPref;
	private RingerVolumePreference ringPref;
	private RingerVolumePreference alarmRingPref;
	private static final String WIFI_KEY = "wifi";
	private static final String WIFI_HOTSPOT_KEY = "wifi hotspot";
	private static final String BLUETOOTH_KEY = "bluetooth";
	private static final String DATA_KEY = "data";
	private static final String THREEG_KEY = "3g";
	private static final String VIBRATION_KEY = "vibration";
	private static final String TACTILE_FEEDBACK_KEY = "tactile feedback";
	private static final String GPS_KEY = "gps";
	private static final String FLASH_LIGHT_KEY = "flash light";
	private static final String SPEAKER_KEY = "speaker";
	private static final String KEY_TONG_KEY = "key tong";
	private static final String AUTO_SYNC_KEY = "auto sync";
	private static final String BRIGHTNESS_KEY = "brightness";
	private static final String RING_KEY = "ring";
	private static final String ALARM_RING_KEY = "alarm ring";
	private int mBightnessMode;
	private int mBightness;
	private int mRingLevel;
	private int mAlarmRingLevel;
	private boolean mIsPowerSaveOn;
	private double mMaxVolumeRing;
	private double mMaxVolumeAlarmRing;
	private FragmentManager mFragmentManager = null;
	private static final String PROMPT_SETTINGS = "PromptSettings";
	private static final int ENABLE_SWITCH = 201;
	private static final int ENABLE_SWITCH_TIME = 1500;
	private boolean isSwitchEnd = true;
	private boolean isPowerSaveEnable = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		actionBarSwitch = (Switch) inflater.inflate(
				com.mediatek.internal.R.layout.imageswitch_layout, null);
		actionBarSwitch.setPadding(0, 0, 32, 0);
		ActionBar actionbar = getActionBar();
		if (actionbar != null) {
			actionbar.setCustomView(actionBarSwitch,
					new ActionBar.LayoutParams(
							ActionBar.LayoutParams.WRAP_CONTENT,
							ActionBar.LayoutParams.WRAP_CONTENT,
							Gravity.CENTER_VERTICAL | Gravity.END));
			actionbar.setHomeButtonEnabled(true);
			// actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
			// ActionBar.DISPLAY_HOME_AS_UP
			// | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
			actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_TITLE);
			actionbar.setDisplayHomeAsUpEnabled(true);
		}
		mFragmentManager = getFragmentManager();
		addPreferencesFromResource(R.xml.power_save_mode);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mPowerSavingShPrefs = getSharedPreferences(
				PowerSaveUtils.REFS_POWER_SAVING, 0);
		mEditor = mPowerSavingShPrefs.edit();
		initialLayout();
		actionBarSwitch.setEnabled(true);
		mMaxVolumeAlarmRing = (double) getAlarmRingMaxVolume();
		mMaxVolumeRing = (double) getRingMaxVolume();
	}

	private void initialLayout() {
		powerSaveSettingsCategory = (PreferenceCategory) findPreference(POWER_SAVE_SETTING_KEY);
		lowerPowerPref = (LowerCheckBoxPreference) findPreference(LOWER_POWER_KEY);
		wifiPref = (CheckBoxPreference) findPreference(WIFI_KEY);
		wifiHotspotSavingPref = (CheckBoxPreference) findPreference(WIFI_HOTSPOT_KEY);
		bluetoothSavingPref = (CheckBoxPreference) findPreference(BLUETOOTH_KEY);
		dataPref = (CheckBoxPreference) findPreference(DATA_KEY);
		threeGPref = (CheckBoxPreference) findPreference(THREEG_KEY);
		vibrationPref = (CheckBoxPreference) findPreference(VIBRATION_KEY);
		tactileFeedbackPref = (CheckBoxPreference) findPreference(TACTILE_FEEDBACK_KEY);
		gpsPref = (CheckBoxPreference) findPreference(GPS_KEY);
		flashLightPref = (CheckBoxPreference) findPreference(FLASH_LIGHT_KEY);
		// speakerPref = (CheckBoxPreference) findPreference(SPEAKER_KEY);
		keyTongPref = (CheckBoxPreference) findPreference(KEY_TONG_KEY);
		autoSyncPref = (CheckBoxPreference) findPreference(AUTO_SYNC_KEY);
		brightnessPref = (BrightnessPreference) findPreference(BRIGHTNESS_KEY);
		ringPref = (RingerVolumePreference) findPreference(RING_KEY);
		alarmRingPref = (RingerVolumePreference) findPreference(ALARM_RING_KEY);

		screenPowerSavingPref.setOnPreferenceChangeListener(this);
		wifiPref.setOnPreferenceChangeListener(this);
		wifiHotspotSavingPref.setOnPreferenceChangeListener(this);
		bluetoothSavingPref.setOnPreferenceChangeListener(this);
		dataPref.setOnPreferenceChangeListener(this);
		threeGPref.setOnPreferenceChangeListener(this);
		vibrationPref.setOnPreferenceChangeListener(this);
		tactileFeedbackPref.setOnPreferenceChangeListener(this);
		gpsPref.setOnPreferenceChangeListener(this);
		flashLightPref.setOnPreferenceChangeListener(this);
		// speakerPref.setOnPreferenceChangeListener(this);
		keyTongPref.setOnPreferenceChangeListener(this);
		autoSyncPref.setOnPreferenceChangeListener(this);
		if (!PowerSaveUtils.MTK_GPS_SUPPORT) {
			powerSaveSettingsCategory.removePreference(gpsPref);
		}
		if (!PowerSaveUtils.isSupportScreenMode(this)) {
			powerSaveSettingsCategory.removePreference(screenPowerSavingPref);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mIsPowerSaveOn = PowerSaveUtils.isPowerSaveOn(this);
		Log.d(TAG, "onResume isPowerSaveOn :" + mIsPowerSaveOn);
		mBightnessMode = mPowerSavingShPrefs.getInt(
				PowerSaveUtils.brightness_mode,
				getResources().getInteger(R.integer.power_brightness_mode));
		mBightness = mPowerSavingShPrefs.getInt(PowerSaveUtils.brightness,
				getResources().getInteger(R.integer.power_brightness));
		mRingLevel = mPowerSavingShPrefs.getInt(PowerSaveUtils.ring,
				getResources().getInteger(R.integer.power_ring_value));
		mAlarmRingLevel = mPowerSavingShPrefs.getInt(PowerSaveUtils.alarmRing,
				getResources().getInteger(R.integer.power_alarm_ring_value));
		actionBarSwitch.setChecked(mIsPowerSaveOn);
		actionBarSwitch.setOnCheckedChangeListener(this);
		getContentResolver().registerContentObserver(
				Settings.Global.getUriFor(Settings.Global.POWERSAVE_ON), true,
				mPowerSaveObserver);
		// //updatePreferenceState(mIsPowerSavingEnable);;
		brightnessPref.setPrefrenceValue(mBightnessMode == 1 ? getResources()
				.getString(R.string.auto) : Math
				.round((mBightness / 255d) * 100) + "%");
		/*
		 * ringPref.setPrefrenceValue(Math .round((mRingLevel / mMaxVolumeRing)
		 * * 100) + "%"); alarmRingPref .setPrefrenceValue(Math
		 * .round((mAlarmRingLevel / mMaxVolumeAlarmRing) * 100) + "%");
		 */
		ringPref.setPrefrenceValue(mRingLevel + "%");
		alarmRingPref.setPrefrenceValue(mAlarmRingLevel + "%");
		boolean isNeedShowPromt = mPowerSavingShPrefs.getBoolean(
				PowerSaveUtils.need_show_prompt, true);
		if (isNeedShowPromt) {
			showPromptDialog();
		}
		IntentFilter intetnFilter = new IntentFilter();
		intetnFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		intetnFilter.addAction(WifiManager.WIFI_AP_STATE_CHANGED_ACTION);
		intetnFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(mReceiver, intetnFilter);
		// actionBarSwitch.setEnabled(PowerSaveUtils.isPowerSaveEnable(this));
		isPowerSaveEnable = PowerSaveUtils
				.isPowerSaveEnable(/* PowerSavingModeActivity.this */getApplicationContext());
		actionBarSwitch.setEnabled(isSwitchEnd && isPowerSaveEnable);
	}

	private ContentObserver mPowerSaveObserver = new ContentObserver(
			new Handler()) {
		@Override
		public void onChange(boolean selfChange) {
			mIsPowerSaveOn = PowerSaveUtils
					.isPowerSaveOn(/* PowerSavingModeActivity.this */getApplicationContext());
			Log.d(TAG, "Mode Observer isPowerSaveOn :" + mIsPowerSaveOn);
			actionBarSwitch.setChecked(mIsPowerSaveOn);
			isSwitchEnd = false;
			actionBarSwitch.setEnabled(isSwitchEnd && isPowerSaveEnable);
			mHandler.sendEmptyMessageDelayed(ENABLE_SWITCH, ENABLE_SWITCH_TIME);
		}
	};

	private int getRingMaxVolume() {
		return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
	}

	private int getAlarmRingMaxVolume() {
		return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		dismissPromptDialog();
		getContentResolver().unregisterContentObserver(mPowerSaveObserver);
		unregisterReceiver(mReceiver);
		isSwitchEnd = true;
		mHandler.removeMessages(ENABLE_SWITCH);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		if (!BRIGHTNESS_KEY.equals(key) && !RING_KEY.equals(key)
				&& !ALARM_RING_KEY.equals(key) && !LOWER_POWER_KEY.equals(key)) {
			boolean isChecked = ((CheckBoxPreference) preference).isChecked();
			setModeChange(key, isChecked);
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		String key = preference.getKey();

		boolean isChecked = (Boolean) newValue;
		setModeChange(key, isChecked);
		return true;
	}

	private void setModeChange(String key, boolean isChecked) {
		if (WIFI_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isWifiOpened, isChecked);
			mEditor.apply();
		} else if (WIFI_HOTSPOT_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isWifiHotspotOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setWifiAPEnable(this, false); }
			 */
			mEditor.apply();
		} else if (BLUETOOTH_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isBluetoothOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setBtEnable(false); }
			 */
			mEditor.apply();
		} else if (DATA_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isDataOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setMobileDataEnable(this, false); }
			 */
			mEditor.apply();
		} else if (THREEG_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isThreeGOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setNetworkMode(this, false); }
			 */
			mEditor.apply();
		} else if (VIBRATION_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isVibrationOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setVibration(this, false); }
			 */
			mEditor.apply();
		} else if (TACTILE_FEEDBACK_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isTactileFeedbackOpened,
					isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setTactileFeedback(this, false); }
			 */
			mEditor.apply();
		} else if (GPS_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isGpsOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setGpsEnable(this, false); }
			 */
			mEditor.apply();
		} else if (FLASH_LIGHT_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isFlashLightOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setFlashLight(this, false); }
			 */
			mEditor.apply();
		}
		/*
		 * if (SPEAKER_KEY.equals(key)) {
		 * mEditor.putBoolean(PowerSaveUtils.isSpeakerOpened, isChecked); }
		 */
		else if (KEY_TONG_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isKeyTongOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setKeyTone(this, false); }
			 */
			mEditor.apply();
		} else if (AUTO_SYNC_KEY.equals(key)) {
			mEditor.putBoolean(PowerSaveUtils.isAutoSyncOpened, isChecked);
			/*
			 * if(isChecked){ PowerSaveUtils.setAutoSyncEnable(this, false); }
			 */
			mEditor.apply();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d(TAG, "onCheckedChanged");
		if (mIsPowerSaveOn != isChecked) {
			mIsPowerSaveOn = isChecked;
			PowerSaveUtils.setPowerSaveState(this, isChecked, false);
			PowerSaveUtils.cancleNotification(this);
		}
	}

	private void showPromptDialog() {
		PowerSavePromptDialogFragment newFragment = PowerSavePromptDialogFragment
				.newInstance();
		newFragment.show(mFragmentManager, PROMPT_SETTINGS);
		mFragmentManager.executePendingTransactions();
	}

	private void dismissPromptDialog() {
		PowerSavePromptDialogFragment newFragment = (PowerSavePromptDialogFragment) mFragmentManager
				.findFragmentByTag(PROMPT_SETTINGS);
		if (null != newFragment) {
			newFragment.dismissAllowingStateLoss();
		}
	}

	@Override
	public void onSelectDialogClick(boolean isChecked) {
		// TODO Auto-generated method stub
		mEditor.putBoolean(PowerSaveUtils.need_show_prompt, isChecked);
		mEditor.commit();
	}

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			// WifiManager.WIFI_STATE_CHANGED_ACTION
			// WifiManager.WIFI_AP_STATE_CHANGED_ACTION
			// BluetoothAdapter.ACTION_STATE_CHANGED
			isPowerSaveEnable = PowerSaveUtils
					.isPowerSaveEnable(/* PowerSavingModeActivity.this */getApplicationContext());
			actionBarSwitch.setEnabled(isSwitchEnd && isPowerSaveEnable);
		}
	};
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ENABLE_SWITCH:
				isSwitchEnd = true;
				isPowerSaveEnable = PowerSaveUtils
						.isPowerSaveEnable(/* PowerSavingModeActivity.this */getApplicationContext());
				actionBarSwitch.setEnabled(isSwitchEnd && isPowerSaveEnable);
				break;
			default:
				break;
			}
		}

	};
}
