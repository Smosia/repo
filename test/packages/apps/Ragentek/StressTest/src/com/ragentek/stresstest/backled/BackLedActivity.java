package com.ragentek.stresstest.backled;

/**
 * 
 * 
 * @author yangyang.liu
 * @date 2015/07/24
 */
import java.util.Timer;
import java.util.TimerTask;
import com.ragentek.stresstest.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BackLedActivity extends Activity {

	public static String TAG = "BackLedActivity";

	private float mBrightness = 1.0f;
	private Handler mHandler = new Handler();
	WindowManager.LayoutParams mLayoutParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backled);
		LinearLayout mLayout = (LinearLayout) findViewById(R.id.backled_layout);
		mLayout.setSystemUiVisibility(0x00002000);
		start();
	}

	public void start() {

		mHandler.removeCallbacks(mRunnable);
		mHandler.postDelayed(mRunnable, 1000);
	}

	private Runnable mRunnable = new Runnable() {

		public void run() {
			if (mBrightness == 1.0f) {
				mBrightness = 0.0f;
			} else {
				mBrightness = 1.0f;
			}
			mLayoutParams = getWindow().getAttributes();
			mLayoutParams.screenBrightness = mBrightness;
			getWindow().setAttributes(mLayoutParams);
			mHandler.postDelayed(mRunnable, 6000);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
