package com.android.sales;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class RegisgerDialog extends Activity implements OnClickListener {
	private Button btnOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_register);
		btnOK = (Button) findViewById(R.id.btn_ok);
		btnOK.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		finish();
	}
}
