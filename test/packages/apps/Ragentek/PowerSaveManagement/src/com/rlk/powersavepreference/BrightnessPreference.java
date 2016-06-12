package com.rlk.powersavepreference;

import com.rlk.powersavemanagement.PowerSaveUtils;
import com.rlk.powersavemanagement.R;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.preference.SeekBarDialogPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CheckBox;
import android.widget.TextView;

public class BrightnessPreference extends SeekBarDialogPreference implements
	    SeekBar.OnSeekBarChangeListener,
		CheckBox.OnCheckedChangeListener {
    private static final String TAG = "PowerSave/BrightnessPreference";
	
	private SeekBar mSeekBar;
	private CheckBox mCheckBox;
	
	private TextView mBrightnessValue;
	private CharSequence mTextValue;
	private boolean mAutomaticAvailable;
	private SharedPreferences mPowerSavingShPrefs;
	private SharedPreferences.Editor mEditor;
	
	private int mOldBrightness;
	 private int mOldAutomatic;
	
	public BrightnessPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		SensorManager mgr = (SensorManager)context.getSystemService(
                Context.SENSOR_SERVICE);       
		mAutomaticAvailable = mgr.getDefaultSensor(Sensor.TYPE_LIGHT) != null;
		setWidgetLayoutResource(R.layout.prefrences_custom);
		setDialogLayoutResource(R.layout.preference_dialog_brightness);
		mPowerSavingShPrefs = context.getSharedPreferences(PowerSaveUtils.REFS_POWER_SAVING, 0);
		mEditor= mPowerSavingShPrefs.edit();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d(TAG, "isChecked:"+isChecked);
		mSeekBar.setEnabled(!isChecked);
	}

	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		mSeekBar = getSeekBar(view);
		mCheckBox = (CheckBox) view.findViewById(R.id.automatic_mode);
		mBrightnessValue = (TextView) view.findViewById(R.id.brightness_value);
		
		mOldBrightness = getBrightness();
		mSeekBar.setProgress(mOldBrightness);
		mBrightnessValue.setText(Math.round(mOldBrightness/255d)+"%");
		mOldAutomatic = getBrightnessMode();
		mCheckBox.setChecked(mOldAutomatic == 1);
		mSeekBar.setEnabled(!(mOldAutomatic == 1));
		mBrightnessValue.setText(Math.round((mOldBrightness/255d)*100)+"%");
		mCheckBox.setOnCheckedChangeListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
		TextView myTextView = (TextView) view
				.findViewById(R.id.preference_value);
		if (myTextView != null) {
			final CharSequence text = getPrefrenceValue();
			myTextView.setText(text);
		}

	}
	@Override
    protected void onDialogClosed(boolean positiveResult) {
        Log.d(TAG, "onDialogClosed");
        super.onDialogClosed(positiveResult);

        final ContentResolver resolver = getContext().getContentResolver();
        if (positiveResult) {
        	int autoMode = mCheckBox.isChecked() ? 1 : 0;
        	mTextValue = (autoMode==1) ? getContext().getResources().getString(R.string.auto): Math.round((mSeekBar.getProgress()/255d)*100)+"%";
        	mEditor.putInt(PowerSaveUtils.brightness_mode, autoMode);
        	if(autoMode != 1){
        		mEditor.putInt(PowerSaveUtils.brightness, mSeekBar.getProgress());
        	}    	
        	mEditor.commit();
        	////PowerSaveUtils.setBrightness(getContext(), autoMode ,mSeekBar.getProgress());
        	notifyChanged();
        }
    }

	private int getBrightness( ) {
        int brightness = mPowerSavingShPrefs.getInt(PowerSaveUtils.brightness,getContext().getResources().getInteger(R.integer.power_brightness));
        return brightness;
    }
	private int getBrightnessMode( ) {
        int brightnessMode = mPowerSavingShPrefs.getInt(PowerSaveUtils.brightness_mode, getContext().getResources().getInteger(R.integer.power_brightness_mode));
        return brightnessMode;
    }
	public CharSequence getPrefrenceValue() {
		return mTextValue;
	}

	public void setPrefrenceValue(CharSequence text) {
		if (text == null && mTextValue != null || text != null
				&& !text.equals(mTextValue)) {
			mTextValue = text;
			notifyChanged();
		}
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		mSeekBar.setProgress(progress);
		mBrightnessValue.setText(Math.round((progress/255d)*100)+"%");
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
