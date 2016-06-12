package com.android.settings.deviceinfo;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.settings.R;

public class MemoryCleanPreference extends Preference {

	private static final String TAG = "MemoryCleanPreference";
	private Context mContext;
	private Button mButton;
	private OnClickListener mListener;
	private String mText;

	public MemoryCleanPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public MemoryCleanPreference(Context context, AttributeSet attrs) {
		this(context, attrs, com.android.internal.R.attr.preferenceStyle);
	}

	public MemoryCleanPreference(Context context) {
		this(context, null);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.cy_storage_memory_clean, parent,
				false);
		return view;
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		mButton = (Button) view.findViewById(R.id.clear_memory);
		mButton.setText(mText);
		mButton.setOnClickListener(mListener);
	}
	
	public void setText(String text){
		mText = text;
//		if(mButton != null){
//			mButton.setText(text);
//		}
	}

	public void setOnclickListener(OnClickListener listener) {
		mListener = listener;
	}

}
