package com.gcl.cltorch;

import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;
import android.util.Log;
import android.util.DisplayMetrics;

public class MainActivity extends Activity implements OnTouchListener {

	private static final String FLASH_LIGHT_STATE = "flash_light_state";
	private Uri mUri;

	private static final String TAG = "CLTorch";
	public static final int STATE_ON = 1;

	public static final String ACTION_CAMETA_LED = "com.android.camera_led";
	public static final String ACTION_TORCH_NOTIFY = "com.android.torch_notify";
	private static final String FLASH_LIGHT_STATE2 = "flash_light_state2";
	private NotificationManager mNotificationManager;
	private Notification mNotification;

	private SharedPreferences sp;

	private Camera mCamera = null;
	private Parameters mParameters = null;
	private RelativeLayout rl;
	private int sosFrequency;
	private boolean needNotify;
	private boolean fullscreenSwitch;

	private int torchX_S;
	private int torchX_E;
	private int torchY_S;
	private int torchY_E;

	private int sosX_S;
	private int sosX_E;
	private int sosY_S;
	private int sosY_E;

	private float torchScaleX_S = (float) (320.0 / 720);
	private float torchScaleX_E = (float) (400.0 / 720);
	private float torchScaleY_S = (float) (910.0 / 1280);
	private float torchScaleY_E = (float) (1060.0 / 1280);

	private float sosScaleX_S = (float) (300.0 / 720);
	private float sosScaleX_E = (float) (420.0 / 720);
	private float sosScaleY_S = (float) (1060.0 / 1280);
	private float sosScaleY_E = (float) (1180.0 / 1280);

	private boolean isTorchOpen = false, isSosOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("");
		setActionBar(toolbar);
		UtilLog.logd(TAG, "MainActivity onCreate");

		DisplayMetrics dm = getResources().getDisplayMetrics();
		int heightPixels = dm.heightPixels;
		int widthPixels = dm.widthPixels;

		torchX_S = (int) (widthPixels * torchScaleX_S);
		torchX_E = (int) (widthPixels * torchScaleX_E);
		torchY_S = (int) (heightPixels * torchScaleY_S);
		torchY_E = (int) (heightPixels * torchScaleY_E);

		sosX_S = (int) (widthPixels * sosScaleX_S);
		sosX_E = (int) (widthPixels * sosScaleX_E);
		sosY_S = (int) (heightPixels * sosScaleY_S);
		sosY_E = (int) (heightPixels * sosScaleY_E);

		rl = (RelativeLayout) findViewById(R.id.rl);
		rl.setOnTouchListener(this);
		init();
	}

	private void init() {
		UtilLog.logd(TAG, "init");
		TorchApp.clean = false;
		sp = getSharedPreferences("com.gcl.cltorch_preferences",
				MODE_WORLD_WRITEABLE);
		mUri = Settings.System.getUriFor(FLASH_LIGHT_STATE);
		getContentResolver().registerContentObserver(mUri, true, mObserver);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_CAMETA_LED);
		filter.addAction(Intent.ACTION_REBOOT);
		filter.addAction(Intent.ACTION_SHUTDOWN);
		filter.addAction(ACTION_TORCH_NOTIFY);
		registerReceiver(cameraLaunchedReceiver, filter);
	
		boolean enabled = isEnabled();
		setViewVisible(enabled);
		// A: DWYLL-487 liuhao 20150508{
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(TorchServer.INTENT_TORCHSERVER);
		registerReceiver(mReceiver, filter2);
		// A:}
	}

	@Override
	protected void onResume() {
		super.onResume();

		boolean isFullscreenSwitch = getResources().getBoolean(
				R.bool.def_fullscreen_switch_on);
		if (isFullscreenSwitch) {
			fullscreenSwitch = sp.getBoolean(
					TorchSettings.PRE_FULL_SCREEN_SWITCH_STRING, true);
		} else {
			fullscreenSwitch = sp.getBoolean(
					TorchSettings.PRE_FULL_SCREEN_SWITCH_STRING, false);
		}

		sosFrequency = Integer.parseInt(sp.getString(
				TorchSettings.PRE_SOS_FREQUENCY, "550"));
		UtilLog.logd(TAG, "fullscreenSwitch = " + fullscreenSwitch);
		// A:JLLB-4320 huangyouzhong {
		if (mCamera != null) {
			try {
				mParameters = mCamera.getParameters();
			} catch (RuntimeException e) {
				releaseObject();
				isTorchOpen = false;
				isSosOpen = false;
				rl.setBackgroundResource(R.drawable.torch_sos_off);
			}
		}
		// A}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Bundle args = new Bundle();
		args.putInt("pause", 1);
		Intent intent = new Intent();
		intent.setClass(this, TorchServer.class);
		intent.putExtras(args);
		startService(intent);
		// A: JLLYLSY-423 huangerhui 20150923{
		if (!isTorchOpen && !isSosOpen) {
			releaseObject();
		}
		// A}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();

		switch (id) {
		case R.id.menu_settings:
			UtilLog.logd(TAG, "menu_settings");
			Intent intent = new Intent();
			intent.setClass(this, TorchSettings.class);
			startActivity(intent);
			return true;
		case R.id.menu_exit:
			cleanNotifyTorchOn();
			System.exit(0);
			UtilLog.logd(TAG, "menu_exit");
			return true;
		}

		return false;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		return super.onMenuOpened(featureId, menu);
	}
	//A:JLLYLSY-620 huangerhui 20151029{
	long prelongTime=0;
	long pcTime=1000;
	//A:}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//A:JLLYLSY-620 huangerhui 20151029{
		if(prelongTime==0){
			prelongTime=new Date().getTime();
		}else{
			long currTime=new Date().getTime();
			pcTime=currTime-prelongTime;
			prelongTime=currTime;
		}
		if(pcTime<300)return true;
		//A:}
		float x = event.getX();
		float y = event.getY();
		boolean effectTorch = false;
		boolean effectSos = false;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (fullscreenSwitch
					|| (x >= torchX_S && x <= torchX_E && y >= torchY_S && y <= torchY_E)) {
				effectTorch = true;
			}
			if (x >= sosX_S && x <= sosX_E && y >= sosY_S && y <= sosY_E) {
				effectSos = true;
			}
			Log.i(TAG, "onTouch:effectTorch:" + effectTorch + " effectSos:"
					+ effectSos);
			if (effectSos) {
				if (!isSosOpen) {
					openSOS();
				} else {
					closeSOS();
				}
				return true;
			}
			if (effectTorch) {
				if (isSosOpen) {

					return false;
				}
				if (isTorchOpen) {
					changeFlashlightState(false);
				} else {
					changeFlashlightState(true);
				}
				return true;
			}
		}
		return false;
	}

	private void changeFlashlightState(boolean b) {
		// TODO Auto-generated method stub
		Settings.System.putInt(this.getContentResolver(), FLASH_LIGHT_STATE2, b ? 1 : 0);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		releaseObject();
		cleanNotifyTorchOn();
		unregisterReceiver(cameraLaunchedReceiver);

		getContentResolver().unregisterContentObserver(mObserver);
		// A: DWYLL-436 liuhao 20150506{
		if (isEnabled()) {
			changeFlashlightState(false);
		}
		// A:}
		// A: DWYLL-487 liuhao 20150508{
		unregisterReceiver(mReceiver);
		// A:}

	}

	private boolean notifyTorchOn() {
		needNotify = sp.getBoolean(TorchSettings.PRE_NOTIFY_STRING, true);
		if (needNotify) {
			Log.v(TAG, "needNotify=" + needNotify);
			Intent intent = new Intent(MainActivity.this, MainActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					intent, 0);
			mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			mNotification = new Notification();
			mNotification.icon = R.drawable.ic_launcher;
			if (isSosOpen) {
				mNotification.setLatestEventInfo(this, getResources()
						.getString(R.string.notify_sos_on), getResources()
						.getString(R.string.notify_sos_on), pendingIntent);
			} else {
				mNotification
						.setLatestEventInfo(
								this,
								getResources().getString(
										R.string.notify_torch_on_title),
								getResources().getString(
										R.string.notify_torch_on_text),
								pendingIntent);
			}
			mNotificationManager.notify(R.string.app_name, mNotification);
			return true;
		}
		return false;
	}

	private void cleanNotifyTorchOn() {
		if (mNotification != null) {
			mNotification = null;
		}
		if (mNotificationManager != null) {
			mNotificationManager.cancel(R.string.app_name);
		}
	}

	BroadcastReceiver cameraLaunchedReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent == null) {
				return;
			}
			String act = intent.getAction();
			Log.v(TAG, act);
			if (ACTION_CAMETA_LED.equals(act)
					|| Intent.ACTION_REBOOT.equals(act)
					|| Intent.ACTION_SHUTDOWN.equals(act)) {
				releaseObject();
				cleanNotifyTorchOn();
				finish();
			} else if (ACTION_TORCH_NOTIFY.equals(act)) {
				needNotify = sp.getBoolean(TorchSettings.PRE_NOTIFY_STRING,
						true);
				if (!needNotify) {
					if (isSosOpen || isTorchOpen) {
						cleanNotifyTorchOn();
					}
				}
			}
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case STATE_ON:

				try {
					mParameters
							.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
					mCamera.setParameters(mParameters);
				} catch (Exception ex) {
				}
				mHandler.postDelayed(mRunnable, 300);// A: BUG_ID:JWBLB-1147

				// huangerhui 2015820
				sosFrequency = Integer.parseInt(sp.getString(
						TorchSettings.PRE_SOS_FREQUENCY, "550"));

				// M: BUG_ID:JWBLB-1147 huangerhui 2015820{
				// sendEmptyMessageDelayed(STATE_ON, sosFrequency);

				sendEmptyMessageDelayed(STATE_ON, sosFrequency + 300);
				// M:}
				break;
			}
			super.handleMessage(msg);
		}
	};
	// A: BUG_ID:JWBLB-1147 huangerhui 2015820{
	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(mParameters);
			} catch (Exception ex) {
			}
		}
	};

	// A:}
	private void initObject() {
		try {
			if (null == mCamera) {
				try {
					mCamera = Camera.open();
					mParameters = mCamera.getParameters();

				} catch (RuntimeException e) {
					Toast.makeText(this, R.string.no_camera_prompt, 1).show();

				}
			}
		} catch (Exception e) {

		}
	}

	private void releaseObject() {
		if (null != mCamera) {
			mCamera.release();
			mCamera = null;
		}
		if (null != mParameters) {
			mParameters = null;
		}
		if (null != mHandler) {
			mHandler.removeMessages(STATE_ON);
			// A:bugid:JLLYLSY-495 huangerhui 20150929{
			mHandler.removeCallbacks(mRunnable);

			// A}
		}
	}

	/*
	 * private void openLight() { isTorchOpen = true; initObject();
	 * rl.setBackgroundResource(R.drawable.torch_on); try { String flashMode =
	 * mParameters.getFlashMode();
	 * 
	 * if (!flashMode.equals(Parameters.FLASH_MODE_TORCH)){// A
	 * 
	 * mParameters.setFlashMode(Parameters.FLASH_MODE_TORCH); }
	 * 
	 * mCamera.setParameters(mParameters); } catch (Exception ex) {
	 * UtilLog.logd("huangerhui", "exception"); } notifyTorchOn(); }
	 */

	/*
	 * private void closeLight() { isTorchOpen = false;
	 * rl.setBackgroundResource(R.drawable.torch_sos_off); try {
	 * mParameters.setFlashMode(Parameters.FLASH_MODE_OFF);
	 * mCamera.setParameters(mParameters); } catch (Exception ex) { }
	 * cleanNotifyTorchOn(); }
	 */
	private boolean torchOn = false;
	
	private void openSOS() {

		if (isTorchOpen) {
			torchOn = true;
			changeFlashlightState(false);
		}
		while (true) {
			try{
			Thread.sleep(10);}
			catch(Exception e){}
			if (!isEnabled()) {
				isSosOpen = true;
				initObject();
				if (torchOn) {
					rl.setBackgroundResource(R.drawable.torch_sos_on);
				} else {
					rl.setBackgroundResource(R.drawable.sos_on);
				}
				mHandler.sendEmptyMessage(STATE_ON);
				notifyTorchOn();
				break;
			}
		}
		
	}

	private void closeSOS() {
		isSosOpen = false;
		releaseObject();
		cleanNotifyTorchOn();
		if (torchOn) {
			torchOn = false;
			changeFlashlightState(true);
		} else {
			rl.setBackgroundResource(R.drawable.torch_sos_off);
		}
		
	}
	/*private Intent intent;
	private void sendFlashlightBroadcast(boolean enabled) {
		if(intent==null){
		intent = new Intent("com.gcl.torch.aciton.request_flashlight");
		}
		intent.putExtra("on", enabled);
		sendBroadcast(intent);
	}*/

	private ContentObserver mObserver = new ContentObserver(new Handler()) {
		public void onChange(boolean selfChange) {
			boolean enabled = isEnabled();
			setViewVisible(enabled);
		};
	};

	private boolean isEnabled() {
		return Settings.System.getInt(MainActivity.this.getContentResolver(),
				FLASH_LIGHT_STATE, 0) != 0;
		
	}

	private void setViewVisible(boolean enabled) {
		// TODO Auto-generated method stub
		if (enabled) {
			if (!torchOn) {
				rl.setBackgroundResource(R.drawable.torch_on);
				notifyTorchOn();
				isTorchOpen = true;
				changeFlashlightState(true);
			}
		} else {
			if (!torchOn) {
				rl.setBackgroundResource(R.drawable.torch_sos_off);
				cleanNotifyTorchOn();
				isTorchOpen = false;
				changeFlashlightState(false);
			}
		}
	}

	// A: DWYLL-487 liuhao 20150508{
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			releaseObject();
			if (isEnabled()) {
				changeFlashlightState(false);
				cleanNotifyTorchOn();
			}
		}
	};
	// A:}
}
