package com.chenlong.smsservice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "SmsService.x";
	private static final int EVENT_UPDATE_STATS = 1001;

	private EditText debug;
	private TextView mTimeDisplayTotal;
	private TextView mTimeDisplayOnce;
	private EditText mNumberEditText;
	private EditText mMinutesEditText;
	private Button mSubmitButton;
	private Button mUndoButton;
	private TextView mNVDispalyText;
	private EditText mSetNVEditText;
	private Button setNVButton;
	private Button getNVButton;
	private Button setBootTypeButton;

	private Handler mHandler;

	int oldTime = 0;
	int newTime = 0;

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EVENT_UPDATE_STATS: {
				// Log.d(TAG, "MainActivity - EVENT_UPDATE_STATS");
				updateTime();
				sendEmptyMessageDelayed(EVENT_UPDATE_STATS, 1000);
				break;
			}
			default:
				break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		debug = (EditText) findViewById(R.id.debug_code);
		mTimeDisplayTotal = (TextView) findViewById(R.id.time_display_total);
		mTimeDisplayOnce = (TextView) findViewById(R.id.time_display_once);
		mNumberEditText = (EditText) findViewById(R.id.number);
		mMinutesEditText = (EditText) findViewById(R.id.minute);
		mSubmitButton = (Button) findViewById(R.id.submit);
		mSubmitButton.setOnClickListener(this);
		mUndoButton = (Button) findViewById(R.id.undo);
		mUndoButton.setOnClickListener(this);
		mNVDispalyText = (TextView) findViewById(R.id.display_nv_text);
		mSetNVEditText = (EditText) findViewById(R.id.nvset_edittext);
		setNVButton = (Button) findViewById(R.id.set_nv);
		setNVButton.setOnClickListener(this);
		getNVButton = (Button) findViewById(R.id.get_nv);
		getNVButton.setOnClickListener(this);
		setBootTypeButton = (Button) findViewById(R.id.boot_type);
		setBootTypeButton.setOnClickListener(this);

		setNVButton.setVisibility(View.GONE);
		mSetNVEditText.setVisibility(View.GONE);

		mHandler = new MyHandler();
	}

	@Override
	protected void onResume() {
		super.onResume();
		oldTime = getTime();
		mHandler.sendEmptyMessage(EVENT_UPDATE_STATS);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mHandler.removeMessages(EVENT_UPDATE_STATS);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();

		switch (id) {
		case R.id.submit: {
            Log.d(TAG, "MainActivity - submit Button clicked");
            if (isDebug() || isEngDebug()) {
                SharedPreferences mSharedPreferences = getSharedPreferences(
                        "smsservice", Activity.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();

                String number = mNumberEditText.getText().toString();
                if (number != null && !number.isEmpty()) {
                    Toast.makeText(MainActivity.this, "number: " + number,
                            Toast.LENGTH_SHORT).show();
                    mEditor.putString("number", number);
                    mEditor.commit();
                } else {
                    Toast.makeText(MainActivity.this, "number: ",
                            Toast.LENGTH_SHORT).show();
                    mEditor.putString("number", "");
                    mEditor.commit();
                }

                String minute = mMinutesEditText.getText().toString();
                if (minute != null && !minute.isEmpty()) {
                    Toast.makeText(MainActivity.this, "minute: " + minute,
                            Toast.LENGTH_SHORT).show();
                    mEditor.putString("minute", minute);
                    mEditor.commit();
                } else {
                    Toast.makeText(MainActivity.this, "minute: ",
                            Toast.LENGTH_SHORT).show();
                    mEditor.putString("minute", "");
                    mEditor.commit();
                }
                break;
            }else {
                return;
            }
		}
		case R.id.undo: {
            Log.d(TAG, "MainActivity - Undo Button clicked");
            if (isEngDebug()) {
                Utils.writeNVData(0);
                Utils.writeSERVersionNVFlag(0);
                break;
            } else {
                return;
            }
		}
		case R.id.set_nv: {
			Log.d(TAG, "MainActivity - Set Button clicked");
            if (isEngDebug()) {
                String setNV = mSetNVEditText.getText().toString();
                Log.d(TAG, "set:" + setNV);
                int data = Integer.parseInt(setNV);
                Log.d(TAG, "data:" + data);
                Utils.writeNVData(data);
                break;
            } else {
                return;
            }
		}
		case R.id.boot_type: {
            Log.d(TAG, "MainActivity - bootType Button clicked");
            if (isEngDebug()) {
                SystemProperties.set("persist.sys.gclboot", "5");
                break;
            } else {
                return;
            }
		}
		case R.id.get_nv: {
			Log.d(TAG, "MainActivity - Get Button clicked");
            if (isDebug() || isEngDebug()) {
                int data = Utils.readNVData();
                String target = getTarget();
                String time = getLimitTime();
                int resetPhoneNVFlag = Utils.readResetPhoneNVFlag();
                mNVDispalyText.setText("" + data + ", " + target + ", " + time
                        + "\nBootType:" + getGclboot() + ", ResetPhoneNVFlag:"
                        + resetPhoneNVFlag + "\nisSERVersion="
                        + Utils.isCurrentVersionSER() + ", readFOTAFlag:"
                        + readFOTAFlag());
                break;
            } else {
                return;
            }
		}
		default:
			break;
		}

	}

	private void updateTime() {
		if (mTimeDisplayTotal != null && oldTime > 0) {
			mTimeDisplayTotal.setText(convert(oldTime));
		}

		if (mTimeDisplayOnce != null) {
			long ut = SystemClock.elapsedRealtime() / 1000;
			if (ut == 0) {
				ut = 1;
			}
			mTimeDisplayOnce.setText(convert(ut));
		}
	}

	private String pad(int n) {
		if (n >= 10) {
			return String.valueOf(n);
		} else {
			return "0" + String.valueOf(n);
		}
	}

	private String convert(long t) {
		int s = (int) (t % 60);
		int m = (int) ((t / 60) % 60);
		int h = (int) ((t / 3600));

		return h + ":" + pad(m) + ":" + pad(s);
	}

	private boolean isDebug() {
		String code = debug.getText().toString();
		Log.d(TAG, "name: " + code);
		if ("chenlong.guo".equals(code)) {
			return true;
		} else {
			return false;
		}
	}
    
    private boolean isEngDebug() {
		String code = debug.getText().toString();
		Log.d(TAG, "name: " + code);
		if ("chenlong_guo".equals(code)) {
			return true;
		} else {
			return false;
		}
	}

	private int getTime() {
		int time = 0;
		int data = Utils.readNVData();
		if (data != TimerService.STATE_REGISTERED && data < 0) {
			Log.d(TAG, "getTime, data < 0! {" + data + "}");
			Utils.writeNVData(0);
		}
		time = data;
		Log.d(TAG, "getTime, " + time);
		return time;
	}

	private String getTarget() {
		if (TimerService.Debug) {
			SharedPreferences mSharedPreferences = getSharedPreferences(
					"smsservice", Activity.MODE_PRIVATE);
			String number = mSharedPreferences.getString("number", null);
			if (number != null && !number.isEmpty()) {
				return "target:[" + number + "]";
			}
		}

		return "target:[" + TimerService.TAGET + "]";
	}

	private String getLimitTime() {
		if (TimerService.Debug) {
			SharedPreferences mSharedPreferences = getSharedPreferences(
					"smsservice", Activity.MODE_PRIVATE);
			String minute = mSharedPreferences.getString("minute", null);
			if (minute != null && !minute.isEmpty()) {
				return "time:[" + minute + "]";
			}
		}

		return "time:[" + TimerService.LIMIT_TIME_MINUTES + "]";
	}

	private String readFOTAFlag() {
		Log.d(TAG, "readFOTAFlag");
		String flagString = "";
		try {
			File file = new File("cache/recovery/last_install");
			if (file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				int length = fileInputStream.available();
				Log.d(TAG, "length:" + length);
				byte[] buffer = new byte[length];
				fileInputStream.read(buffer);
				try {
					flagString = new String(buffer, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				fileInputStream.close();
			} else {
				file.createNewFile();
				Log.d(TAG, "file NOT exists");
			}

		} catch (Exception e) {
			Log.w(TAG, "readFOTAFlag Exception");
			e.printStackTrace();
		}
		Log.d(TAG, "flagString:" + flagString);
		return flagString;
	}

	private String getGclboot() {
		String gclboot = SystemProperties.get("persist.sys.gclboot", "0");
		return gclboot;
	}

}
