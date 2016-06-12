package com.rgk.netmanager;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class NetApplication extends Application {
	public static final String TAG = "NetApp";

	private static NetApplication mInstance;

	private DataUsageService mDataUsageService;

	private NetServiceConnection mConnection;

	public NetApplication() {
	}

	public static NetApplication getInstance() {
		return mInstance;
	}

	public DataUsageService getDataUsageService() {
		return mDataUsageService;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "NetApplication: onCreate...");

		mInstance = this;

		maybeStartNetService();
	}

        private void maybeStartNetService() {
		if (SharePreferenceUtils.getStatsMonitorEnabled(this)) {
			startNetService();
		}
	}

	public void startNetService() {
		Log.d(TAG, "NetApplication: Bind to DataUsageService.");
		mConnection = new NetServiceConnection();
		// We don't want the service finishes itself just after this connection.
		Intent intent = new Intent(this, DataUsageService.class);
		startService(intent);
		bindService(new Intent(this, DataUsageService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

        public void stopNetService() {
		Log.d(TAG, "NetApplication: UNBind to DataUsageService.");
		// We don't want the service finish.
		Intent intent = new Intent(this, DataUsageService.class);
		this.unbindService(mConnection);
		this.stopService(intent);
	}

	private class NetServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			Log.d(TAG, "NetApplication: Connected from DataUsageService");
			mDataUsageService = ((DataUsageService.MyBinder) binder)
					.getService();
                        notifyListener();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "NetApplication: Disconnected from DataUsageService");
		}
	}

	/**
	 * 
	 * @return true if the service has been binded,false else.
	 */
	public boolean isServiceBinded() {
		return mDataUsageService != null;
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
