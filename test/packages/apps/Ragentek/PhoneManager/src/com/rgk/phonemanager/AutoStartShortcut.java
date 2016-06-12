package com.rgk.phonemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AutoStartShortcut extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent("com.mediatek.security.AUTO_BOOT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
	}
}
