package com.android.incallui.floatwindow;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.util.Log;

import com.android.incallui.R;

public class MyWindowManager {

	private final static String TAG = "FloatWindow";

	private FloatWindowView windowView;

	private LayoutParams windowParams;

	/**
	 * use to control the float window
	 */
	private WindowManager mWindowManager;

	private ActivityManager mActivityManager;
	
	private OnClickListener mListener;
	
	private static MyWindowManager mMyWindowManager;
	
	private MyWindowManager() {
		
	}
	
	public static synchronized MyWindowManager getInstance() {
        if (mMyWindowManager == null) {
        	mMyWindowManager = new MyWindowManager();
        }
        return mMyWindowManager;
    }

	/**
	 * create FloatWindowView
	 */
	public void createFloatWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		int width = screenWidth - 32*2; // paddingLeft=16dip,paddingRight=16dip
		Log.d(TAG, "createFloatWindow: screenWidth=" + screenWidth
			+ ", screenHeight=" + screenHeight + ", width=" + width);
		if (windowView == null) {
			windowView = new FloatWindowView(context);
			if (windowParams == null) {
				windowParams = new LayoutParams();
				//windowParams.x = screenWidth / 2 - windowView.getWindowViewWidth() / 2;
				windowParams.x = screenWidth / 2 - width / 2;
				windowParams.y = screenHeight / 2 - windowView.getWindowViewHeight() / 2;
				windowParams.type = LayoutParams.TYPE_PHONE;
				windowParams.format = PixelFormat.RGBA_8888;
				windowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
						| LayoutParams.FLAG_NOT_FOCUSABLE;
				windowParams.gravity = Gravity.LEFT | Gravity.TOP;
				//windowParams.width = windowView.getWindowViewWidth();
				windowParams.width = width;
				windowParams.height = windowView.getWindowViewHeight();
			}
			windowManager.addView(windowView, windowParams);
		}
	}

	/**
	 * remove the float window view
	 * 
	 * @param context
	 */
	public void removeFloatWindow(Context context) {
		if (windowView != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(windowView);
			windowView = null;
		}
	}

	/**
	 * update float window view position
	 */
	public void updatePosition(Context context, int x, int y) {
		if (windowView != null) {
			WindowManager windowManager = getWindowManager(context);
			//windowParams.x = x;
		        windowParams.y = y;
			windowManager.updateViewLayout(windowView, windowParams);
		}
	}

	/**
	 * refresh UI
	 */
	public void updateCallDuration(String durationStr) {
		if (windowView != null) {
			windowView.setCallDuration(durationStr);
		}
	}

	/**
	 * Float view is visible
	 */
	public boolean isWindowShowing() {
		return windowView != null;
	}

	/**
	 * get WindowManager
	 */
	private WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}

	/**
	 * get ActivityManager
	 */
	private ActivityManager getActivityManager(Context context) {
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		}
		return mActivityManager;
	}
	
	public void updatePrimaryInfo(String number, String name, boolean nameIsNumber, String label,
            Drawable photo, boolean isSipCall) {
		if (windowView != null) {
			windowView.setPrimaryInfo(number, name, nameIsNumber, label, photo, isSipCall);
		}
	}
	
	public void updateContactPhoto(Drawable photo) {
		if (windowView != null) {
			windowView.setPhoto(photo);
		}
	}
	
	public void setOnClickListener(OnClickListener listener) {
		//mListener = listener;
		windowView.setListener(listener);
	}

	public void setOnFloatWindowClickListener(OnClickListener listener) {
		windowView.setOnClickListener(listener);
	}

	public void setAudio(int mode) {
		if (windowView != null) {
			windowView.setAudio(mode);
		}
	}

}
