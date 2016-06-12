package com.rgk.pedometer;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class PedometerApplication extends Application {
	public static final String TAG = "PedometerApp";

	private static PedometerApplication mInstance;

	private StepService mStepService;

	private StepServiceConnection mConnection;
	
	//public static boolean hasNotify = false;

	public PedometerApplication() {
	}

	public static PedometerApplication getInstance() {
		return mInstance;
	}

	public StepService getStepService() {
		return mStepService;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "PedometerApplication: onCreate...");

		mInstance = this;

		maybeStartNetService();
	}

	private void maybeStartNetService() {
		if (SharePreferenceUtils.getEnabled(this)) {
			startStepService();
		}
		//startStepService();
	}

	public void startStepService() {
		Log.d(TAG, "PedometerApplication: Bind to StepService.");
		mConnection = new StepServiceConnection();
		// We don't want the service finishes itself just after this connection.
		Intent intent = new Intent(this, StepService.class);
		startService(intent);
		bindService(new Intent(this, StepService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	public void stopStepService() {
		Log.d(TAG, "PedometerApplication: UNBind to StepService.");
		// We don't want the service finish.
		Intent intent = new Intent(this, StepService.class);
		this.unbindService(mConnection);
		this.stopService(intent);
	}

	private class StepServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			Log.d(TAG, "PedometerApplication: Connected from StepService");
			mStepService = ((StepService.StepBinder) binder).getService();
			notifyListener();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "PedometerApplication: Disconnected from StepService");
			mStepService = null;
		}
	}

	/**
	 * 
	 * @return true if the service has been binded,false else.
	 */
	public boolean isServiceBinded() {
		return mStepService != null;
	}

	private OnServiceConnectedListener mListener;

	public void addListener(OnServiceConnectedListener listener) {
		mListener = listener;
	}

	private void notifyListener() {
		if (mListener != null) {
			mListener.onConnected();
		}
	}

	public interface OnServiceConnectedListener {
		void onConnected();
	}

}
