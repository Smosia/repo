package com.android.ota;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.Window;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
public class LatestVersionDialog extends Activity {
	private Button cancelButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		java.lang.System.out.println("20130219 yulong");
		setContentView(R.layout.latestversiondialog);	
		cancelButton = (Button) this.findViewById(R.id.btn_lastest_ok);
		cancelButton.setOnClickListener(new CancelButton());
	}
	public class CancelButton implements OnClickListener {

		public void onClick(View v) {			
			LatestVersionDialog.this.finish();
			
		}
	}
}
