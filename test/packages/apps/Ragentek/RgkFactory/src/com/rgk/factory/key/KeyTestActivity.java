//file create by liunianliang 20130718~20130724

package com.rgk.factory.key;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.AsyncTask;
import com.rgk.factory.Config;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

public class KeyTestActivity extends Activity {

	public static final String TAG = "KeyTestActivity";

	private Button btnHome;
	private Button btnBack;
	private Button btnMenu;
	private Button btnVolumeUp;
	private Button btnVolumeDown;
	private Button btnPower;
	private boolean hadSendBroadcast = false;

	private boolean up = false;
	private boolean down = false;
	private boolean menu = false;
	private boolean back = false;
	private boolean home = false;
	private boolean power = false;

	@Override
	public void finish() {
		// unregisterReceiver(mBroadcastReceiver);
		super.finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

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
		setTitle(R.string.key);
		setContentView(R.layout.activity_key_test);
		RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.key_layout);
	    mLayout.setSystemUiVisibility(0x00002000);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		findViewById(R.id.scrollview).setVerticalScrollBarEnabled(false);

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(mBroadcastReceiver, filter);

		Button pass = (Button) findViewById(R.id.key_pass);
		pass.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				SendBroadcast(Config.PASS);
			}
		});

		Button fail = (Button) findViewById(R.id.key_fail);
		fail.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				SendBroadcast(Config.FAIL);
			}
		});

		initViews();
		boolean hasMenu = ViewConfiguration.get(this).hasPermanentMenuKey();
		Log.i(TAG, "hasPermanentMenuKey="+hasMenu);
		if(!hasMenu) {
			btnMenu.setVisibility(View.GONE);
			menu = true;
		}
	}

	private void initViews() {
		btnHome = (Button) findViewById(R.id.key_home);
		btnBack = (Button) findViewById(R.id.key_back);
		btnMenu = (Button) findViewById(R.id.key_menu);
		btnVolumeUp = (Button) findViewById(R.id.key_volume_up);
		btnVolumeDown = (Button) findViewById(R.id.key_volume_down);
		btnPower = (Button) findViewById(R.id.key_power);
	}

	public void SendBroadcast(String result) {
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			//AutoTestHandle.autoTest(this, TAG);
			//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));

			up = false;
			down = false;
			menu = false;
			back = false;
			home = false;
			power = false;
			finish();
			AutoTestHandle.autoTest(this, TAG);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(TAG, "keyCode=" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			btnVolumeUp.setVisibility(View.GONE);
			up = true;
			if(up && down && back && menu && home && power) {
                SendBroadcast(Config.PASS);
			}
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			btnVolumeDown.setVisibility(View.GONE);
			down = true;
			if(up && down && back && menu && home && power) {
                SendBroadcast(Config.PASS);
			}
			break;
		case KeyEvent.KEYCODE_BACK:
			btnBack.setVisibility(View.GONE);
			back = true;
			if(up && down && back && menu && home && power) {
                SendBroadcast(Config.PASS);
			}
			break;
		case KeyEvent.KEYCODE_MENU:
			btnMenu.setVisibility(View.GONE);
			menu = true;
			if(up && down && back && menu && home && power) {
                SendBroadcast(Config.PASS);
			}
			break;
		case KeyEvent.KEYCODE_HOME:
			btnHome.setVisibility(View.GONE);
			home = true;
			if(up && down && back && menu && home && power) {
                //SendBroadcast(Config.PASS);
				new MyAsyncTask().execute();
			}
			break;
		case KeyEvent.KEYCODE_POWER:
			btnPower.setVisibility(View.GONE);
			power = true;
			if(up && down && back && menu && home && power) {
                SendBroadcast(Config.PASS);
			}
			break;
		}
        Log.i(TAG, "onKeyDown");
		return true;
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "action=" + intent.getAction());
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				btnPower.setVisibility(View.GONE);
			}
		}
	};
	
	class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			Log.i(TAG, "doInBackground()");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i(TAG, "doInBackground() sleep");
			
			return false;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			Log.i(TAG, "onPostExecute():result=" + result);
			SendBroadcast(Config.PASS);
			super.onPostExecute(result);
		}
	}
}
