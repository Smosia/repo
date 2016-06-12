package com.android.fmradio;

import java.lang.Thread.State;
import java.util.Arrays;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * This class is used to provide engineer mode function for hardware test
 * 
 */
public class FMRadioCITActivity extends Activity {
	public static final String TAG = "FMRadioCITActivity";
	private static final String RDS_RSSI_STRING = "RDS_RSSI_STRING";
	private static final int MSGID_TICK_EVENT = 6;
	private static final int MSGID_INIT_OK = 7;
	private boolean mIsServiceBinded = false;
	private FmService mService = null;
	public static boolean mInCITMode = true;
	
	private boolean mIsPlaying = false; // When start, the radio is not playing.

	// Record whether we are destroying.
	private boolean mIsDestroying = false;
	private boolean mIsTickEventExit = false;

	private TextView mEditFreq = null;
	private TextView mTextRssi = null;

	private Context mContext = null;
	private Thread mTickEventThread = null;

	private int mCurrentStation = FmUtils.DEFAULT_STATION;
	private AudioManager mAudioManager = null;

	/**
	 * FM Radio listener
	 */
	private FmListener mFMRadioListener = new FmListener() {

		/**
		 * call back method from service
		 */
		public void onCallBack(Bundle bundle) {
			Message msg = mHandler.obtainMessage(bundle
					.getInt(FmListener.CALLBACK_FLAG));
			msg.setData(bundle);
			mHandler.sendMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mInCITMode = true;
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.activity_fm_cit);
		// Init FM database
		mContext = getApplicationContext();
		mTextRssi = (TextView) findViewById(R.id.FMR_Status_RSSI);
		mEditFreq = (TextView) findViewById(R.id.FMR_Freq_edit);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		refreshTextStatus(mIsPlaying);
		Log.d(TAG, "end FMRadioCITActivity.onCreate");

	}

	/**
	 * called when bind service
	 */
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		/**
		 * called when bind service
		 * 
		 * @param className
		 *            service name
		 * @param service
		 *            service
		 */
		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.d(TAG, "begin FMRadioCITActivity.onServiceConnected");
                    //M: DWLEBM-244 yejianming 20160406 (start)
                    //mService = ((FmService.ServiceBinder) service).getService();
                    FmService.NRRadioService nrService = (FmService.NRRadioService)service;
                    mService = nrService.getService();
                    //M: DWLEBM-244 yejianming 20160406 (end)
			if (null == mService) {
				Log.e(TAG, "Error: null interface");
				finish();
				return;
			}

			mService.registerFmRadioListener(mFMRadioListener);
			if (!isServiceInit()) {
				Log.d(TAG, "FM service is not init.");
				initService(mCurrentStation);
				refreshTextStatus(false);
				Log.d(TAG, "onService connect.mCurrentStation: "
						+ mCurrentStation);
				mService.powerUpAsync(FmUtils.computeFrequency(mCurrentStation));

			} else {
				Log.d(TAG, "FM service is already init.");
				if (isDeviceOpen()) {
					// Get the current frequency in service and save it into
					// database.
					int iFreq = getFrequency();
					if (FmUtils.isValidStation(iFreq)) {
						if (mCurrentStation != iFreq) {
							Log.d(TAG,
									"The frequency in FM service is not same as in database.");
							mCurrentStation = iFreq;
							// Save the current station frequency into data
							// base.
							// FmRadioStation.setCurrentStation(mContext,
							// mCurrentStation);
						} else {
							Log.d(TAG,
									"The frequency in FM service is same as in database.");
						}
					} else {
						Log.e(TAG, "Error: invalid frequency in service.");
					}

					// mIsPlaying = isPowerUp();

					if (mIsPlaying) {
						Log.d(TAG, "FM is already power up.");
						refreshTextStatus(true);
					}

				} else {
					// This is theoretically never happen.
					Log.e(TAG, "Error: FM device is not open");
				}
				mHandler.sendEmptyMessage(MSGID_INIT_OK);
			}
			Log.d(TAG, "<<< FMRadioCITActivity.onServiceConnected");
		}

		public void onServiceDisconnected(ComponentName className) {
			Log.d(TAG, ">>> FMRadioCITActivity.onServiceDisconnected");
			mService = null;
			Log.d(TAG, "<<< FMRadioCITActivity.onServiceDisconnected");
		}
	};
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mIsDestroying) {
				return;
			}
			Bundle bundle;
			switch (msg.what) {
			case MSGID_INIT_OK:
				break;
			case MSGID_TICK_EVENT:
				bundle = msg.getData();
				mTextRssi.setText(bundle.getString(RDS_RSSI_STRING));
				break;

			case FmListener.MSGID_POWERUP_FINISHED:
				bundle = msg.getData();
				mIsPlaying = bundle.getBoolean(FmListener.KEY_IS_POWER_UP);
				mHandler.sendEmptyMessage(MSGID_INIT_OK);
				refreshTextStatus(true);
				break;

			case FmListener.MSGID_TUNE_FINISHED:
				bundle = msg.getData();
				float frequency = bundle
						.getFloat(FmListener.KEY_TUNE_TO_STATION);
				mCurrentStation = FmUtils.computeStation(frequency);
				refreshTextStatus(true);
				break;

			case FmListener.LISTEN_PS_CHANGED:
			case FmListener.LISTEN_RT_CHANGED:
				bundle = msg.getData();
				break;
			case FmListener.LISTEN_RDSSTATION_CHANGED:
				bundle = msg.getData();
				mCurrentStation = bundle.getInt(FmListener.KEY_RDS_STATION);
				break;
			default:
				Log.d(TAG, "invalid view id");
			}
			Log.d(TAG, "<<< handleMessage");
		}

	};

	private void refreshTextStatus(boolean on) {
		if (!on) {
			mTextRssi.setText("X");
			mEditFreq.setText(FmUtils.formatStation(mCurrentStation));
		} else {
		}
	}

	private boolean isServiceInit() {
		if (null != mService) {
			return mService.isServiceInited();
		}
		return false;
	}

	private void initService(int iCurrentStation) {
		if (null != mService) {
			mService.initService(iCurrentStation);
		}
	}

	private boolean isDeviceOpen() {
		if (null != mService) {
			return mService.isDeviceOpen();
		}
		return false;
	}

	private int getFrequency() {
		if (null != mService) {
			return mService.getFrequency();
		}
		return 0;
	}

	@Override
	protected void onStart() {
		super.onStart();
		mIsServiceBinded = bindService(new Intent(FMRadioCITActivity.this,
				FmService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
		if (!mIsServiceBinded) {
			Log.e(TAG, "Error: Cannot bind FM service");
			finish();
			return;
		} else {
			Log.d(TAG, "Bind FM service successfully.");
		}

		startTest();// Add by sunjie for CIT
	}

	public void onResume() {
		super.onResume();
		readTickEvent();
	}

	public void onPause() {
		mHandler.removeMessages(MSGID_TICK_EVENT);
		stopTickEventThread();
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "start FMRadioActivity.onStop");
		if (mIsServiceBinded) {
			unbindService(mServiceConnection);
			mIsServiceBinded = false;
		}
		Log.d(TAG, "end FMRadioActivity.onStop");
		super.onStop();
	}

	/**
	 * called when activity destroy
	 */
	public void onDestroy() {
		Log.d(TAG, ">>> FMRadioCITActivity.onDestroy");
		mIsDestroying = true;
		mHandler.removeCallbacks(null);
		// Should powerdown FM.
		if (mIsPlaying) {
			Log.d(TAG, "FM is Playing. So stop it.");
			// powerDown();
			mService.powerDownAsync();
			mIsPlaying = false;
		}
		if (null != mService) {
			mService.unregisterFmRadioListener(mFMRadioListener);
			mService.stopSelf();
		}
		mService = null;
		mFMRadioListener = null;
		Log.d(TAG, "<<< FMRadioCITActivity.onDestroy");
		stopTest();// Add by sunjie for CIT
		super.onDestroy();
	}

	// Add by sunjie for CIT begin
	private final float[] freqs = { 88.5f, 105.8f, 98.0f, 108.0f };
	private boolean result = false;
	private boolean isRunning = false;
	private final int RSSITH = -95;

	private void startTest() {
		isRunning = true;
		if (mThread != null) {
			// M:JSLEL-1887 huangyouzhong {
			State state = mThread.getState();
			Log.i(TAG, "state=" + state);
			if (state.equals(State.NEW)) {
				mThread.start();
			}
			// M}
		}
	}

	private void stopTickEventThread() {
		Log.d(TAG, ">>> stopTickEventThread");
		if (null != mTickEventThread) {
			mIsTickEventExit = true;
			mTickEventThread = null;
		}
		Log.d(TAG, "<<< stopTickEventThread");
	}

	private void stopTest() {
		isRunning = false;
		sendBroadcast("");
	}

	private Thread mThread = new Thread(new Runnable() {

		@Override
		public void run() {
			int i = 0;
			int rssi = 0;
			while (isRunning && i < freqs.length && !result) {
				if (mService != null) {
					tuneToStation(FmUtils.computeStation(freqs[i]));
					handler.sendEmptyMessage(i);
					i++;
					try {
						Thread.sleep(3000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					rssi = readRssi();
					if (rssi >= RSSITH) {
						result = true;
					}
				} else {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			Log.i(TAG, "isRunning=" + isRunning + " result=" + result);
			if (isRunning && result) {
				handler.sendEmptyMessage(-1);// success
			} else {
				handler.sendEmptyMessage(-2);// fail
			}
		}
	});

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			if (what == -1) {
				sendBroadcast("ok");
				// setResult(RESULT_OK);
				finish();
			} else if (what == -2) {
				sendBroadcast("");
				finish();
			} else {
				mEditFreq.setText(String.valueOf(freqs[what]));
			}
		}
	};

	private void sendBroadcast(String result) {
		Intent intent = new Intent("return_result_form_fm");
		intent.putExtra("result", result);
		sendBroadcast(intent);
	}

	// Add by sunjie for CIT end
	private void tuneToStation(final int iStation) {
		mService.tuneStationAsync(FmUtils.computeFrequency(iStation));
	}

	public int readRssi() {
		if (null != mService) {
			return mService.getRssi();
		}
		return 0;
	}

	public void readTickEvent() {
		final int interval = 1000;
		if (null != mTickEventThread) {
			return;
		}
		mTickEventThread = new Thread() {
			public void run() {
				Log.d(TAG, ">>> tick envent Thread run()");
				while (true) {
					if (mIsTickEventExit) {
						break;
					}

					if (isDeviceOpen()) {
						Bundle bundle = new Bundle();
						bundle.putString(RDS_RSSI_STRING,
								String.format("%d", readRssi()));
						Message msg = mHandler.obtainMessage(MSGID_TICK_EVENT);
						msg.setData(bundle);
						mHandler.sendMessage(msg);
					}
					// Do not handle other events.
					// Sleep 500ms to reduce inquiry frequency
					try {
						final int hundredMillisecond = 1000;
						Thread.sleep(hundredMillisecond);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Log.d(TAG, "get tick information");
				}
				Log.d(TAG, "<<< tick envent Thread run()");
			}
		};
		Log.d(TAG, "Start tick event Thread.");
		mTickEventThread.start();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/**
	 * get chip id
	 * 
	 * @return chip id
	 */
	public String getChipId() {
		int[] hardwareVersion = getHardwareVersion();
		int chipId = 0;
		if (null != hardwareVersion) {
			chipId = hardwareVersion[0];
		}
		return getStringValue(chipId);
	}

	public int[] getHardwareVersion() {
		int[] hardwareVersion = null;
		if (null != mService) {
			hardwareVersion = mService.getHardwareVersion();
		}
		Log.v(TAG, "getHardwareversion: " + Arrays.toString(hardwareVersion));
		return hardwareVersion;
	}

	public String getStringValue(int convertData) {
		StringBuilder temp = new StringBuilder();
		int quotient = convertData;
		int remainder = 0;
		while (quotient > 0) {
			final int hexadecimal = 16;
			remainder = quotient % hexadecimal;
			quotient = quotient / hexadecimal;
			temp = temp.append(remainder);
		}
		return temp.reverse().toString();
	}

	public int readCapArray() {
		if (null != mService) {
			return mService.getCapArray();
		}
		return 0;
	}

	public boolean getStereoMono() {
		if (null != mService) {
			return mService.getStereoMono();
		}
		return false;
	}

	public void setStereoMono(boolean isMono) {
	
		mService.setStereoMono(isMono);
	}

	public void switchAntenna(int type) {
		mService.switchAntennaAsync(type);
	}

	public int readRdsBler() {
		if (null != mService) {
			return mService.getRdsBler();
		}
		return 0;
	}

	public String getEcoVersion() {
		int[] hardwareVersion = getHardwareVersion();
		int ecoVersion = 0;
		if (null != hardwareVersion) {
			ecoVersion = hardwareVersion[1];
		}
		return "E" + getStringValue(ecoVersion);
	}

	public String getPatchVersion() {
		int[] hardwareVersion = getHardwareVersion();
		int patchVersion = 0;
		if (null != hardwareVersion) {
			final int patchVersionPosition = 3;
			patchVersion = hardwareVersion[patchVersionPosition];
		}
		String patchStr = getStringValue(patchVersion);
		float patch = 0;
		if (null != patchStr) {
			try {
				final int hundred = 100;
				patch = Float.parseFloat(patchStr) / hundred;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return Float.toString(patch);
	}

	public String getDspVersion() {
		int[] hardwareVersion = getHardwareVersion();
		int dspVersion = 0;
		if (null != hardwareVersion) {
			dspVersion = hardwareVersion[2];
		}
		return "V" + getStringValue(dspVersion);
	}
}
