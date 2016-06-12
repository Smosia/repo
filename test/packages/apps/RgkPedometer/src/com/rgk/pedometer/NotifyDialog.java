package com.rgk.pedometer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class NotifyDialog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notify_dialog_layout);

		findViewById(R.id.notify_dialog_container).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
