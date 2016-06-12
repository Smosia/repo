package com.android.settings.deviceinfo;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.R;

public class CyMemoryPreference extends Preference {
	private static final String TAG = "CyMemoryPreference";
	private Context mContext ;
	private String mSummaryLeftText;
	private String mValueLeftText;
	private String mValueRightText;
	private int mProgressColor;
	private int mTextColor;
	private int mProgress;
	private TextView mSummaryLeft;
	private TextView mValueLeft;
	private TextView mValueRight;
	private CricleProgressBar mProgressBar;
	
	
	public CyMemoryPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initTypedArray(context, attrs);
		// setLayoutResource(R.layout.memory_preference);
	}

	public CyMemoryPreference(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.preferenceStyle);
	}

	public CyMemoryPreference(Context context) {
		this(context, null);
	}
	
	private void initTypedArray(Context context, AttributeSet attrs) {
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CyMemoryPreference);
		mSummaryLeftText = mTypedArray
				.getString(R.styleable.CyMemoryPreference_summaryLeft);
		mValueLeftText = mTypedArray
				.getString(R.styleable.CyMemoryPreference_valueLeft);
		mValueRightText = mTypedArray
				.getString(R.styleable.CyMemoryPreference_valueRight);
		mProgressColor = mTypedArray
				.getColor(R.styleable.CyMemoryPreference_ProgressColor,Color.GREEN);
		mTextColor =  mTypedArray
				.getColor(R.styleable.CyMemoryPreference_textColors,Color.GREEN);
		mProgress = mTypedArray
				.getInteger(R.styleable.CyMemoryPreference_Progress,2);
		mTypedArray.recycle();
	} 
	
	@Override
	protected View onCreateView(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.cystorage_volume, parent, false);
		return view;
	}
	
	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		mSummaryLeft = (TextView) view.findViewById(R.id.title);
		mValueLeft = (TextView) view.findViewById(R.id.summary);
		mValueRight = (TextView) view.findViewById(R.id.text);
		mProgressBar = (CricleProgressBar)view.findViewById(R.id.cricleProgress);
		//refreshValue();
	}
	
	
	public void setSummaryLeft(String title){
		mSummaryLeftText = title;
		if (mSummaryLeft != null) {
			mSummaryLeft.setText(title);
		}
	}
	 public void setValueLeft(String value){
		 mValueLeftText = value ;
		 if (mValueLeft != null) {
			 mValueLeft.setText(value);
			}
		 
	 }
	 public void setValueRight(String text){
		 mValueRightText = text ;
		 if (mValueRight != null) {
			 mValueRight.setText(text);
			}
		 
	 }
	 
	 public void setProgressColor(int mProgressColor){
		 mProgressBar.setCricleProgressColor(mProgressColor);
		 
	 }
	 
	 public void setProgress(int progress){
		// mProgressBar.setCurrentProgress(progress);
		 
	 }
	 public void setTextColor(int textcolor){
		 mProgressBar.setTextColor(textcolor);
		 
	 }
}
