package com.rlk.powersavepreference;

import com.rlk.powersavemanagement.PowerSaveUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.rlk.powersavemanagement.R;

public class LowerCheckBoxPreference extends CheckBoxPreference implements OnSeekBarChangeListener{
	private static final String TAG = "PowerSave/LowerCheckBoxPreference";
	private TextView mTitleText;
    private TextView mSummaryText;
    private SeekBar mBatteryBar;
    private View mLowerPowerPref;
    
    private String summary;
    private int mBatteryValue;
    private Context mContext;
    private SharedPreferences mLowerPowerShPrefs;
    private SharedPreferences.Editor mLowerEditor;
    
    private OnPreferenceChangeListener mOnLowerPreferenceChangeListener;
    
	public LowerCheckBoxPreference(Context context, AttributeSet attrs) {	
		super(context, attrs);
		Log.d(TAG, "LowerCheckBoxPreference");
        setLayoutResource(R.layout.lower_power_preference);
        setWidgetLayoutResource(R.layout.preference_widget_checkbox);
        mContext = context;
        mLowerPowerShPrefs = mContext.getSharedPreferences(PowerSaveUtils.REFS_LOW_POWER, 0);
		mLowerEditor = mLowerPowerShPrefs.edit();
		mBatteryValue = mLowerPowerShPrefs.getInt(PowerSaveUtils.lowerBatteryValue,
		context.getResources().getInteger(R.integer.lower_power_battery_value));
		updateSummary();
    }

	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
		Log.d(TAG, "onBindView");
		mLowerPowerPref= view.findViewById(R.id.lower_power_pref);
		mTitleText = (TextView)view.findViewById(android.R.id.title);
		mSummaryText = (TextView)view.findViewById(android.R.id.summary);
		mBatteryBar = (SeekBar) view.findViewById(R.id.seekbar_battery);				
		mBatteryBar.setProgress(mBatteryValue);
		updateSummary();
		mSummaryText.setText(summary);
		mLowerPowerPref.setOnClickListener(mPrefOnclickListener);
		mBatteryBar.setOnSeekBarChangeListener(this);
		mBatteryBar.setEnabled(isChecked());
	}

	private final OnClickListener mPrefOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if (!isEnabled()) {
				return;
			}
			if (isChecked()) {
				setChecked(false);
			} else {
				setChecked(true);
			}
		}
	};
	 @Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		super.setChecked(checked);
		Log.d(TAG, "setChecked");
		PowerSaveUtils.setLowerSaveState(mContext, checked);
		mLowerEditor.putBoolean(PowerSaveUtils.lowerPowerLaunch, false);
		mLowerEditor.commit();	
		PowerSaveUtils.cancleNotification(mContext);
	}

   private void updateSummary(){
	   Log.d(TAG, "updateSummary");
	   summary = mContext.getResources().getString(R.string.battery_level_limit_launch, mBatteryValue);
	   notifyChanged();
	   setSummary(summary);
   }
   
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onProgressChanged");
		mBatteryValue = progress;
		notifyChanged();
		updateSummary();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mLowerEditor.putInt(PowerSaveUtils.lowerBatteryValue, mBatteryValue);
		mLowerEditor.putBoolean(PowerSaveUtils.lowerPowerLaunch, false);
		PowerSaveUtils.cancleNotification(mContext);
		mLowerEditor.commit();
	}

}
