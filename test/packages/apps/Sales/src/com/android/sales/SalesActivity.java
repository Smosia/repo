package com.android.sales;

import com.android.sales.ActivityUtils.OnRecordChangeLister;

import android.R.anim;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.SystemProperties;
import android.view.View;
import android.view.View.OnClickListener;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Button;
import android.text.TextUtils;

public class SalesActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnRecordChangeLister {

	private TextView tvIsRegistered;
	private TextView tvTrackingName;
	private RelativeLayout mModeLayout;
	private CheckBox mMode;
	private RelativeLayout mBlockMCCLayout;
	private CheckBox mBlockMCC;
	private TextView mServer;
	private TextView mTime;
	private SalesUtil salesUtil;
	private Button mSendSMS;
	private Button mClear;
	public static final String TAG = "SalesActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();

		int register = ActivityUtils.whetherRegister();
		tvIsRegistered.setText(register == 1 ? R.string.register_yes
				: R.string.register_no);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() != TelephonyManager.SIM_STATE_READY || tm.getCellLocation() == null) {
			tvTrackingName.setText("");
		} else {
			String imei1 = Params.getParams(getApplicationContext(), 0).getIMEI();
			String imei2 = Params.getParams(getApplicationContext(), 1).getIMEI();
			String lac = Params.getParams(getApplicationContext(), 0).getLAC();
			String cellID = Params.getParams(getApplicationContext(), 0).getCellID();
			String modelName = SystemProperties.get("ro.product.model", "")
					.replace(" ", "").replace("QMobile", "").trim();
			String trackingName = "NOIR IMEI " + modelName + " " + imei1 + " "
					+ imei2 + " " + lac + " " + cellID;
			tvTrackingName.setText("Tracking name: " + trackingName);
		}
	}

	private void initView() {
		salesUtil = new SalesUtil(this);

		tvIsRegistered = (TextView) findViewById(R.id.isRegistered);
		tvTrackingName = (TextView) findViewById(R.id.trackingName);

		mModeLayout = (RelativeLayout) findViewById(R.id.mModeLayout);
		mModeLayout.setOnClickListener(this);
		mMode = (CheckBox) findViewById(R.id.mMode);
		mMode.setChecked(salesUtil.getMode());

		mMode.setOnCheckedChangeListener(this);
		mBlockMCCLayout = (RelativeLayout) findViewById(R.id.mBlockMCCLayout);
		mBlockMCCLayout.setOnClickListener(this);

		mBlockMCC = (CheckBox) findViewById(R.id.mBlockMCC);
		mBlockMCC.setChecked(salesUtil.getBlockMCC460());
		mBlockMCC.setOnCheckedChangeListener(this);

		mServer = (TextView) findViewById(R.id.mServer);
		mServer.setOnClickListener(this);

		mTime = (TextView) findViewById(R.id.mTime);
		mTime.setOnClickListener(this);

		mSendSMS = (Button) findViewById(R.id.mSendSMS);
		mSendSMS.setOnClickListener(this);

		mClear = (Button) findViewById(R.id.mClear);
		mClear.setOnClickListener(this);

		ActivityUtils.setOnRecordChangeLister(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.mModeLayout:
			mMode.setChecked(!mMode.isChecked());
			break;
		case R.id.mBlockMCCLayout:
			mBlockMCC.setChecked(!mBlockMCC.isChecked());
			break;
		case R.id.mServer:
			setServer();
			break;
		case R.id.mTime:
			setTime();
			break;
		case R.id.mSendSMS:
			setAlarm();
			break;
		case R.id.mClear:
			if (ActivityUtils.whetherRegister() == 1) {
				ActivityUtils.writeNVData(253, 0);
			} else {
				makeToaset(R.string.register_cleared);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.mMode:
			salesUtil.setMode(arg1);
			break;
		case R.id.mBlockMCC:
			salesUtil.setBlockMCC460(arg1);
			break;
		default:
			break;
		}
	}

	private void setServer() {
		View view = getLayoutInflater().inflate(R.layout.dialog_server, null);
		final EditText etServer = (EditText) view.findViewById(R.id.etTextView);
		etServer.setText(salesUtil.getServer());
		new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
				.setTitle(R.string.server_number)
				.setView(view)
				.setNegativeButton(R.string.dialog_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								String newServer = etServer.getText()
										.toString().trim();
								if (newServer != null && !newServer.equals("")) {
									salesUtil.setServer(newServer);
									makeToaset(R.string.update_server);
									arg0.dismiss();
								} else {
									Toast.makeText(SalesActivity.this,
											R.string.server_empty,
											Toast.LENGTH_SHORT).show();
								}
							}
						})
				.setPositiveButton(R.string.dialog_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create().show();
	}

	private void setTime() {
		new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
				.setTitle(R.string.set_time)
				.setSingleChoiceItems(R.array.times_entry,
						salesUtil.getCheckedItem(),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								int[] values = getResources().getIntArray(
										R.array.times_values);
								salesUtil.setCheckedItem(arg1);
								salesUtil.setTime(values[arg1]);
								makeToaset(R.string.update_time);
								arg0.dismiss();
							}
						})
				.setNeutralButton(R.string.dialog_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create().show();
	}

	private void setAlarm() {
		Log.d(TAG, "BOOT start");
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		// if there is no SIM card, return
		if (tm.getSimState() != TelephonyManager.SIM_STATE_READY || tm.getCellLocation() == null) {
			makeToaset(R.string.sim_card_not_ready);
			return;
		}

		if (!salesUtil.getMode()) {
			makeToaset(R.string.turn_on_mode);
			return;
		}
		if (salesUtil.getBlockMCC460()) {
			if (tm.getSimOperator().startsWith("460")) {
				makeToaset(R.string.block_mcc_toast);
				return;
			}
		}
		// if the user has registered, return
		int register = ActivityUtils.whetherRegister();
		if (register == 1) {
			makeToaset(R.string.registered);
			return;
		}
		boolean needShow = salesUtil.getFirstBootWithSIM();
		Log.d(TAG, "needShow:" + needShow);
		if (needShow) {
			Log.d(TAG, "register=" + register);
			Intent showDialog = new Intent("com.android.sales.RegisgerDialog");
			showDialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(showDialog);
			salesUtil.setFirstBootWithSIM(true);
		}
		Log.d(TAG, "Ready to send SMS three hours later");
		Intent amintent = new Intent(this, SalesBootBroadcast.class);
		amintent.setAction("com.android.sales.count.start.time");
		PendingIntent sender = null;
		sender = PendingIntent.getBroadcast(this, 0, amintent, 0);
		long interval = salesUtil.getTime();
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval,
				sender);
		CharSequence[] times_entry = getResources().getStringArray(
				R.array.times_entry);
		makeToaset(getResources().getString(R.string.dialog_time_send_sms)
				.replace("time", times_entry[salesUtil.getCheckedItem()]));
	}

	private void makeToaset(int textID) {
		Toast.makeText(SalesActivity.this, textID, Toast.LENGTH_SHORT).show();
	}

	private void makeToaset(String text) {
		Toast.makeText(SalesActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRecordChanged(boolean registered) {
		tvIsRegistered.setText(registered ? R.string.register_yes
				: R.string.register_no);
	}
}
