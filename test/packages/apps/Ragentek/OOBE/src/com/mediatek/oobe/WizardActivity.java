package com.mediatek.oobe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;




public class WizardActivity extends Activity {

    private static final String TAG = "OOBE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Log.d(TAG, "WizardActivity - onCreate");
        Intent oobeMainIntent = new Intent(this, MainActivity.class);
        oobeMainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(oobeMainIntent);
        finish();
	}
}
