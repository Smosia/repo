package com.rlk.powersavepreference;

import com.rlk.powersavemanagement.PowerSaveUtils;
import com.rlk.powersavemanagement.R;
import android.preference.SeekBarDialogPreference;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class RingerVolumePreference extends SeekBarDialogPreference implements
SeekBar.OnSeekBarChangeListener {
	private static final String TAG = "RingerVolumePreference";
	private AudioManager mAudioManager;
	
	private TextView mRingContent;
	private TextView mRingLevel;
	private SeekBar mSeekBar;
	private CharSequence mTextValue;
	private String mRingStyle;
	private int mMaxVolume;
	private boolean mIsAlarmRing;
	private SharedPreferences mPowerSavingShPrefs;
	private SharedPreferences.Editor mEditor;
	public RingerVolumePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.PowerSaveRing); 
		mRingStyle = a.getString(R.styleable.PowerSaveRing_streamType);
		a.recycle();
		setWidgetLayoutResource(R.layout.prefrences_custom);
		setDialogLayoutResource(R.layout.preference_dialog_ringervolume);
		mAudioManager = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		mPowerSavingShPrefs = context.getSharedPreferences(PowerSaveUtils.REFS_POWER_SAVING, 0);
		mEditor= mPowerSavingShPrefs.edit();
	}

	
	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		mSeekBar = getSeekBar(view);
		mRingContent = (TextView) view.findViewById(R.id.ring_content);
		mRingLevel = (TextView) view.findViewById(R.id.ring_level);
		mMaxVolume = getSeekBarMaxValue();
		mIsAlarmRing = isAlarmRing(mRingStyle);
		initialText(mRingStyle);
		mSeekBar.setOnSeekBarChangeListener(this);
	}


    private void initialText(String ringStyle){
    	int level = 0;
    	if(mIsAlarmRing){    		

    		 level = mPowerSavingShPrefs.getInt(PowerSaveUtils.alarmRing, getContext().getResources().getInteger(R.integer.power_alarm_ring_value));

    		mRingContent.setText(R.string.alarm_ring_level);
    		//mRingLevel.setText(Math.round((level/(double)mMaxVolume)*100)+"%");
    		
    	}else{

       		 level = mPowerSavingShPrefs.getInt(PowerSaveUtils.ring, getContext().getResources().getInteger(R.integer.power_ring_value));
       		
    		mRingContent.setText(R.string.ring_level);
    		//mRingLevel.setText(Math.round((level/(double)mMaxVolume)*100)+"%"); 
    	}
    	//mRingLevel.setText(level+"%");
    	//mRingLevel.setText(Math.round((level/(double)mMaxVolume)*100)+"%");
    	//mSeekBar.setMax(mMaxVolume);
    	mRingLevel.setText(level+"%");
    	mSeekBar.setMax(100);
    	mSeekBar.setProgress(level);
    }
	private boolean isAlarmRing(String ringStyle){
		if("alarm".equals(ringStyle)){
			return true;
		}
		return false;
	}
   private int getSeekBarMaxValue(){
	  return mAudioManager.getStreamMaxVolume(mIsAlarmRing ? AudioManager.STREAM_ALARM : AudioManager.STREAM_RING);
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
        	//mTextValue = Math.round((mSeekBar.getProgress()/(double)mMaxVolume)*100)+"%";
        	mTextValue = mSeekBar.getProgress()+"%";
        	if(mIsAlarmRing){
        		mEditor.putInt(PowerSaveUtils.alarmRing, mSeekBar.getProgress());
        		////PowerSaveUtils.setRingVolume(getContext(), AudioManager.STREAM_ALARM, mSeekBar.getProgress());
        	}else{
        		mEditor.putInt(PowerSaveUtils.ring, mSeekBar.getProgress());
        		////PowerSaveUtils.setRingVolume(getContext(), AudioManager.STREAM_RING, mSeekBar.getProgress());
        	}     	
        	mEditor.apply();
        	notifyChanged();
        }
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
		//mRingLevel.setText(Math.round((progress/(double)mMaxVolume)*100)+"%");
		mRingLevel.setText(progress+"%");
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
