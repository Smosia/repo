//file create by xuemingwei 20151130

package com.rgk.factory.fingerprint;

import com.rgk.factory.Config;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.IOException;

//import com.ragentek.Fingerprint; 

public class FingerPrint extends Activity implements View.OnClickListener {

	public static String TAG = "xmw";

	private Button mFingerPrintInfo;
	private boolean hadSendBroadcast = false;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
              // M: huangkunming 2014.12.20 @{
              // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
        	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
        	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
    	       // @}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setTitle(R.string.FingerPrint);
		setContentView(R.layout.fingerprint);
		RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.fingerprint_layout);
        mLayout.setSystemUiVisibility(0x00002000);

		mFingerPrintInfo = (Button) findViewById(R.id.fingerprint_info);

		//mFingerPrintInfo.setClickable();
		//mFingerPrintInfo.setEnabled();

		((Button) findViewById(R.id.fingerprint_pass)).setOnClickListener(this);
		((Button) findViewById(R.id.fingerprint_fail)).setOnClickListener(this);
		mFingerPrintInfo.setOnClickListener(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		filter.addAction("return_result_from_fingerprint");
		filter.addAction("exit_fingerprint_cit_test");
		registerReceiver(mBroadcastReceiver, filter);

		startActivityForResult(new Intent().setClassName(
				"com.goodix.fpsetting",
				"com.goodix.fpsetting.RegisterActivity"), 1000);
		// then the test should let ragentek.Fingerprint to do
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Log.e(TAG, "action=" + intent.getAction());
				if (intent.getAction().equals("return_result_from_fingerprint")) {
				String result = intent.getStringExtra("result");
				Log.e(TAG, "result=" + result);
				if (result.equalsIgnoreCase("ok")) {
					SendBroadcast(Config.PASS);
				} else {
					SendBroadcast(Config.FAIL);
				}
			} 
		}

	};

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.fingerprint_pass:
			SendBroadcast(Config.PASS);
			break;
		case R.id.fingerprint_fail:
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onActivityResult");
		if (1000 == requestCode) {
			if (resultCode == RESULT_OK) {
				Log.i(TAG, "RESULT_OK");
				//SendBroadcast(Config.PASS);
			} else {
				Log.i(TAG, "RESULT_FAIL");
				//SendBroadcast(Config.FAIL);
			}
		}
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

}