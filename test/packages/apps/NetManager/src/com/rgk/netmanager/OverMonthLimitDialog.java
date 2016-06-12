package com.rgk.netmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;
import android.telephony.TelephonyManager;

public class OverMonthLimitDialog extends Activity implements OnClickListener{
	public static final String EXTRA_SLOT = "slot";
	public static final String EXTRA_MONTH_STATS = "month_stats";

	private int mSlotId;
	private long mStatsBytes;

	private ImageButton dismissBtn;
	
	private Button disableNetworkBtn;
	private Button resetBtn;

	private TextView mContentTextView;

	private TelephonyManager mTelephonyManager;

	public OverMonthLimitDialog() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.over_limit_dialog_layout);

		mTelephonyManager = TelephonyManager.from(this);

		mSlotId = getIntent().getIntExtra(EXTRA_SLOT, 0);
		mStatsBytes = getIntent().getLongExtra(EXTRA_MONTH_STATS, 0);
		
		initViews();
	}

	private void initViews() {
		dismissBtn = (ImageButton)this.findViewById(R.id.over_limit_dialog_dismiss);
		dismissBtn.setOnClickListener(this);
		
		disableNetworkBtn = (Button)this.findViewById(R.id.close_net_button);
		disableNetworkBtn.setOnClickListener(this);

		resetBtn = (Button)this.findViewById(R.id.reset_button);
		resetBtn.setOnClickListener(this);

		mContentTextView = (TextView)this.findViewById(R.id.over_limit_dialog_body);
		updateContent();
	}

	public void updateContent() {
		String monthStats = StringUtils.formatBytes(mStatsBytes);
		String contentText = this.getString(R.string.over_limit_message, monthStats);
		mContentTextView.setText(contentText);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			return;
		}
		mSlotId = intent.getIntExtra(EXTRA_SLOT, 0);
		mStatsBytes = intent.getLongExtra(EXTRA_MONTH_STATS, 0);
		updateContent();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.over_limit_dialog_dismiss:
			finish();
			break;
			
		case R.id.close_net_button:
			closeDataConnection();
			finish();
			break;
			
		case R.id.reset_button:
			startSettingUi();
			finish();
			break;
		}
	}

	private void startSettingUi() {
		final Intent intent = new Intent(this, MainNetActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		intent.putExtra(MainNetActivity.TO_WHERE, MainNetActivity.TO_SETTINGS);
		startActivity(intent);
	}

	private void closeDataConnection() {
		try {
            		boolean enabled = mTelephonyManager.getDataEnabled();
            		Log.d(NetApplication.TAG, "OverMonthLimitDialog closeDataConnection() data state=" + enabled);
			if (enabled) {
				mTelephonyManager.setDataEnabled(false);
			}
        	} catch (NullPointerException e) {
			Log.d(NetApplication.TAG, "OverMonthLimitDialog failed get TelephonyManager exception:" + e);
        	}
	}

}
