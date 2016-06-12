package com.rgk.factory.buttonlight;

import com.rgk.factory.Config;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.PowerManager;
import com.rgk.factory.R;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ButtonLightActivity extends Activity implements
		View.OnClickListener {

	public static String TAG = "ButtonLightActivity";
	public Button buttonPass, buttonFail;
	private PowerManager pm;
	private boolean hadSendBroadcast = false;
	private PowerManager.WakeLock pWakeLock;
	private static final int MSG_ACQUIRE = 1;
	private static final int MSG_RELEASE = 2;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case MSG_ACQUIRE:
					pWakeLock.acquire();
					mHandler.sendEmptyMessageDelayed(MSG_RELEASE, 1000);
					break;
				case MSG_RELEASE:
					pWakeLock.release();
					mHandler.sendEmptyMessageDelayed(MSG_ACQUIRE, 1000);
					break;
			}			
			super.handleMessage(msg);
		}		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
              // M: huangkunming 2014.12.20 @{
              // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
         	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
         	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
     	       // @}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setTitle(R.string.button_light);
		setContentView(R.layout.activity_buttonlight);
		LinearLayout mLayout = (LinearLayout) findViewById(R.id.buttonlight_layout);
        mLayout.setSystemUiVisibility(0x00002000);
		buttonPass = ((Button) findViewById(R.id.btn_pass));
		buttonPass.setOnClickListener(this);
		buttonFail = ((Button) findViewById(R.id.btn_fail));
		buttonFail.setOnClickListener(this);
		
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pWakeLock=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "ButtonLight");
        pWakeLock.setReferenceCounted(false);
        mHandler.sendEmptyMessageDelayed(MSG_ACQUIRE, 1000);
	}


	@Override
	protected void onPause() {
		if(mHandler != null) {
			mHandler.removeMessages(MSG_ACQUIRE);
			mHandler.removeMessages(MSG_RELEASE);
			mHandler = null;
		}
		pWakeLock.release();
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_pass:
			SendBroadcast(Config.PASS);
			break;
		case R.id.btn_fail:
			SendBroadcast(Config.FAIL);
			break;
		}
	}

	public void SendBroadcast(String result) {
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			AutoTestHandle.autoTest(this, TAG);
			//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));
			finish();
		}
	}

}
