package com.android.incallui.floatwindow;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.telecom.AudioState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.incallui.FloatWindowAdapter;
import android.util.Log;

import com.android.incallui.R;

public class FloatWindowView extends LinearLayout {

	private final static String TAG = "FloatWindow";

	// Constants for Drawable.setAlpha()
	private static final int HIDDEN = 0;
	private static final int VISIBLE = 255;

	private int viewWidth;
	private int viewHeight;

	private static int statusBarHeight;
	private float xInScreen;
	private float yInScreen;
	private float xDownInScreen;
	private float yDownInScreen;
	private float xInView;
	private float yInView;
	
	private ImageView mPhoto;
	private TextView mName;
	private TextView mDuration;
	private Button mHangupBtn;
	private Button mOutSoundBtn;

	public FloatWindowView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.call_float_window, this);
		View view = findViewById(R.id.big_window_layout);
		//view.setBackgroundResource(R.drawable.float_window_bg);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		mPhoto = (ImageView)view.findViewById(R.id.contact_photo);
		mName = (TextView)view.findViewById(R.id.contact_name);
		mDuration = (TextView)view.findViewById(R.id.call_duration);
		mHangupBtn = (Button) findViewById(R.id.hangup);
		mOutSoundBtn = (Button) findViewById(R.id.out_sound_mode);

		setAudio(FloatWindowAdapter.getAudioMode());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.d(TAG, "onTouchEvent -----------");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//Log.d(TAG, "onTouchEvent ACTION_DOWN");
			xInView = event.getX();
			yInView = event.getY();
			xDownInScreen = event.getRawX();
			yDownInScreen = event.getRawY() - getStatusBarHeight();
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			return super.onTouchEvent(event);

		case MotionEvent.ACTION_MOVE:
			//Log.d(TAG, "onTouchEvent ACTION_MOVE");
			xInScreen = event.getRawX();
			yInScreen = event.getRawY() - getStatusBarHeight();
			updateViewPosition();
			break;

		case MotionEvent.ACTION_UP:
			//Log.d(TAG, "onTouchEvent ACTION_UP");
			if (/*Math.abs(xDownInScreen - xInScreen) <=5 && */Math.abs(yDownInScreen - yInScreen) <= 5) {
				Log.d(TAG, "[onTouchEvent]--------------to perform click");
				return super.onTouchEvent(event);
			}
			break;
		default:
			break;
		}
		return true;
	}

	private int getStatusBarHeight() {
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}

	private void updateViewPosition() {
		int x = (int) (xInScreen - xInView);
		int y = (int) (yInScreen - yInView);
		MyWindowManager.getInstance().updatePosition(getContext(), x, y);
	}
	
	public int getWindowViewWidth() {
		return viewWidth;
	}
	
	public int getWindowViewHeight() {
		return viewHeight;
	}
	
	public void setPrimaryInfo(String number, String name, boolean nameIsNumber, String label,
            Drawable photo, boolean isSipCall) {
		mName.setText(name);
		if (photo != null) {
			mPhoto.setImageDrawable(photo);
		}
	}
	
	public void setPhoto(Drawable photo) {
		mPhoto.setImageDrawable(photo);
	}
	
	public void setCallDuration(String durationStr) {
		mDuration.setText(durationStr);
	}
	
	public void setListener(OnClickListener listener) {
		mHangupBtn.setOnClickListener(listener);
		mOutSoundBtn.setOnClickListener(listener);
	}

	public void setAudio(int mode) {
		Log.d(TAG, "setAudio mode-------"+mode);
		final LayerDrawable layers = (LayerDrawable) mOutSoundBtn
				.getBackground();
		if (AudioState.ROUTE_SPEAKER == mode) {
			layers.findDrawableByLayerId(R.id.speakphoneBackgroundItem)
					.setAlpha(0x3a);
			layers.findDrawableByLayerId(R.id.speakphoneFrontIcon).setAlpha(
					VISIBLE);
		} else {
			layers.findDrawableByLayerId(R.id.speakphoneBackgroundItem)
					.setAlpha(VISIBLE);
			layers.findDrawableByLayerId(R.id.speakphoneFrontIcon).setAlpha(
					HIDDEN);
		}
	}
}
