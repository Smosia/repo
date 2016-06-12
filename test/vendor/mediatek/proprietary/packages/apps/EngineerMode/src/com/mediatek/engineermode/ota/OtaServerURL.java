package com.mediatek.engineermode.ota;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;
import com.mediatek.engineermode.R;
/***
history:
1. BUG_ID:JWLW-338 yulong.liang@rangentek.com 2013096
   Des:create OTA server switch
**/
public class OtaServerURL extends Activity implements OnClickListener{
	private RadioButton offical;
	private RadioButton test;
	private static final String OTA_SERVER_URL = "ota_server_url";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ota_server);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		offical = (RadioButton) findViewById(R.id.ota_offical);
		test = (RadioButton) findViewById(R.id.ota_test);
		int getOtaServer = android.provider.Settings.System.getInt(
				this.getContentResolver(), OTA_SERVER_URL, 0);
		if (getOtaServer == 0) {
			offical.setChecked(true);
		} else {
			test.setChecked(true);
		}
		offical.setOnClickListener(this);
		test.setOnClickListener(this);
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		boolean setTo = false;
		switch (v.getId()) {
		case R.id.ota_offical:
			setTo = android.provider.Settings.System.putInt(
					this.getContentResolver(), OTA_SERVER_URL, 0);
			break;
		case R.id.ota_test:
			setTo = android.provider.Settings.System.putInt(
					this.getContentResolver(), OTA_SERVER_URL, 1);
		}
		if (!setTo) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.ota_set_fail),
					Toast.LENGTH_SHORT).show();
		}
	}
}
