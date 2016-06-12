package com.rgk.factory.notificationlight;

import com.rgk.factory.Config;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rgk.factory.R;

public class NotificationLight extends Activity implements OnClickListener {

	public static String TAG = "NotificationLight";
	public Button notification_click, buttonPass, buttonFail;
	private boolean hadSendBroadcast = false;
	private NotificationManager mNotificationManager;
	private static final int LIGHT_NOTIFICATION_ID = 5;
	final static int MSG_COLOR_R = 0;
	final static int MSG_COLOR_G = 1;
	final static int MSG_COLOR_B = 2;
	Notification.Builder builder;
	Notification notification;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.i(TAG, "handleMessage +"+msg.what);
			mNotificationManager.cancel(LIGHT_NOTIFICATION_ID);
			switch (msg.what) {
			case MSG_COLOR_R:
				notification.ledARGB = 0xffff0000;
				mNotificationManager.notify(LIGHT_NOTIFICATION_ID, notification);
				mHandler.sendEmptyMessageDelayed(MSG_COLOR_G, 2000);
				break;
			case MSG_COLOR_G:
				notification.ledARGB = 0xff00ff00;
				mNotificationManager.notify(LIGHT_NOTIFICATION_ID, notification);
				mHandler.sendEmptyMessageDelayed(MSG_COLOR_B, 2000);
				break;
			case MSG_COLOR_B:
				notification.ledARGB = 0xff0000ff;
				mNotificationManager.notify(LIGHT_NOTIFICATION_ID, notification);
				mHandler.sendEmptyMessageDelayed(MSG_COLOR_R, 2000);
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// M: huangkunming 2014.12.20 @{
		// since window flags full, change FLAG_HOMEKEY_DISPATCHED to
		// privateFlags
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
		getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		// @}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setTitle(R.string.notification_light);
		setContentView(R.layout.notification_light);
		RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.notification_light_layout);
		mLayout.setSystemUiVisibility(0x00002000);
		notification_click = ((Button) findViewById(R.id.notification_click));
		notification_click.setOnClickListener(this);
		buttonPass = ((Button) findViewById(R.id.notificationlight_pass));
		buttonPass.setOnClickListener(this);
		buttonFail = ((Button) findViewById(R.id.notificationlight_fail));
		buttonFail.setOnClickListener(this);

		mNotificationManager = (NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
		builder = new Notification.Builder(this);
		builder.setContentTitle(getResources().getString(
				R.string.notification_light));
		builder.setWhen(System.currentTimeMillis());
		notification = builder.getNotification();
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.ledOnMS = 1000;
		notification.ledOffMS = 2000;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.notification_click:
			mHandler.sendEmptyMessageDelayed(MSG_COLOR_R, 100);
			notification_click.setEnabled(false);
			break;
		case R.id.notificationlight_pass:
			SendBroadcast(Config.PASS);
			break;
		case R.id.notificationlight_fail:
			SendBroadcast(Config.FAIL);
			break;
		}
	}

	@Override
	protected void onPause() {
		if (mNotificationManager != null) {
			mNotificationManager.cancel(LIGHT_NOTIFICATION_ID);
		}
		if(mHandler != null) {
			mHandler.removeMessages(MSG_COLOR_R);
			mHandler.removeMessages(MSG_COLOR_G);
			mHandler.removeMessages(MSG_COLOR_B);
			mHandler = null;
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void SendBroadcast(String result) {
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			AutoTestHandle.autoTest(this, TAG);
			// sendBroadcast(new
			// Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item",
			// TAG));
			finish();
		}
	}
}
