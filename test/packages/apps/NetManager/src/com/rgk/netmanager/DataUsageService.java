package com.rgk.netmanager;

import java.util.ArrayList;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.android.server.net.BaseNetworkObserver;
import android.net.INetworkManagementEventObserver;
import android.net.LinkAddress;
import android.net.RouteInfo;
import android.net.INetworkPolicyListener;

public class DataUsageService extends Service {

        private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

        private NotificationWarningListener mNotificationListener;

	// listen for stats update events
	final IntentFilter statsFilter = new IntentFilter(
			"com.android.server.action.NETWORK_STATS_UPDATED");

	private ArrayList<StatsUpdateListener> mStatsUpdateListeners = new ArrayList<StatsUpdateListener>();

        private ArrayList<SimStateListener> mSimStateListeners = new ArrayList<SimStateListener>();

	private NetAdapter mNetAdapter = null;

	private Handler mHandler = null;

	private MyBinder mBinder;

        private final long thresholdBytes = 100 * 1024; // 100KB

        private SubInfoUtils mSubInfoUtils;

    /**
     * Observer that watches for {@link INetworkManagementService} alerts.
     */
    private INetworkManagementEventObserver mAlertObserver = new BaseNetworkObserver() {
        
        @Override
    	public void interfaceStatusChanged(String iface, boolean up) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [interfaceStatusChanged] iface=="+iface+",up="+up);
    	}

    	@Override
    	public void interfaceRemoved(String iface) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [interfaceRemoved] iface=="+iface);
    	}

    	@Override
    	public void addressUpdated(String iface, LinkAddress address) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [addressUpdated] iface=="+iface+",address="+address);
    	}

    	@Override
    	public void addressRemoved(String iface, LinkAddress address) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [addressRemoved] iface=="+iface+",address="+address);
    	}

    	@Override
    	public void interfaceLinkStateChanged(String iface, boolean up) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [interfaceLinkStateChanged] iface=="+iface+",up="+up);
    	}

    	@Override
    	public void interfaceAdded(String iface) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   interfaceAdded iface=="+iface);
    	}

    	@Override
    	public void interfaceClassDataActivityChanged(String label, boolean active, long tsNanos) {
        	// default no-op
		Log.d(NetApplication.TAG, "mAlertObserver]   [interfaceClassDataActivityChanged] label=="+label);
    	}

    	@Override
    	public void limitReached(String limitName, String iface) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [limitReached] limitName=="+limitName+",iface="+iface);
    	}

    	@Override
    	public void interfaceDnsServerInfo(String iface, long lifetime, String[] servers) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [interfaceDnsServerInfo] lifetime=="+lifetime+",iface="+iface);
    	}

    	@Override
    	public void routeUpdated(RouteInfo route) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [routeUpdated] route=="+route);
    	}

    	@Override
    	public void routeRemoved(RouteInfo route) {
        	// default no-op
		// Log.d(NetApplication.TAG, "mAlertObserver]   [routeRemoved] route=="+route);
    	}
    };

    private INetworkPolicyListener mPolicyListener = new INetworkPolicyListener.Stub() {
        @Override
        public void onUidRulesChanged(int uid, int uidRules) {
            // Log.d(NetApplication.TAG, "mPolicyListener]   [onUidRulesChanged] uid=="+uid+",uidRules="+uidRules);
        }

        @Override
        public void onMeteredIfacesChanged(String[] meteredIfaces) {
            Log.d(NetApplication.TAG, "mPolicyListener]   [onMeteredIfacesChanged] ");
            mNetAdapter.advisePersistThreshold(thresholdBytes);
        }

        @Override
        public void onRestrictBackgroundChanged(boolean restrictBackground) {
            // Log.d(NetApplication.TAG, "mPolicyListener]   [onRestrictBackgroundChanged] restrictBackground="+restrictBackground);
        }
    };

	public class MyBinder extends Binder {
		public DataUsageService getService() {
			Log.d(NetApplication.TAG, "DataUsageService: [getService]");
			return DataUsageService.this;
		}
	}

	private Handler.Callback mHandlerCallback = new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			Log.d(NetApplication.TAG, "DataUsageService: mHandlerCallback [handleMessage]");

			// mNetAdapter.forceUpdate();
			long dayMobileTotalBytes = 0;
			long monthMobileTotalBytes = 0;
                        long daySim1Bytes = 0;
                        long daySim2Bytes = 0;
                        long monthSim1Bytes = 0;
                        long monthSim2Bytes = 0;
			try {
				mNetAdapter.initMobileNetworkStatsHistoryCache(AppUtils.SIM1);
				mNetAdapter.initMobileNetworkStatsHistoryCache(AppUtils.SIM2);
				long[] dayTimes = TimeUtils.getTime(TimeUtils.DAY_TYPE);
				daySim1Bytes = mNetAdapter.getMobileTotalBytesFromCache(
						AppUtils.SIM1, dayTimes[0], dayTimes[1]);
				daySim2Bytes = mNetAdapter.getMobileTotalBytesFromCache(
						AppUtils.SIM2, dayTimes[0], dayTimes[1]);
				dayMobileTotalBytes = daySim1Bytes + daySim2Bytes;
				long[] monthTimes = TimeUtils.getTime(TimeUtils.MONTH_TYPE);
				monthSim1Bytes = mNetAdapter.getMobileTotalBytesFromCache(
						AppUtils.SIM1, monthTimes[0], monthTimes[1]);
				monthSim2Bytes = mNetAdapter.getMobileTotalBytesFromCache(
						AppUtils.SIM2, monthTimes[0], monthTimes[1]);
				monthMobileTotalBytes = monthSim1Bytes + monthSim2Bytes;
			} catch (NetRemoteException e) {
				e.printStackTrace();
			}

                        // Check day warning notify
                        maybeDayMobileWarningNotification(daySim1Bytes, Constant.FILTER_SIM1);
                        maybeDayMobileWarningNotification(daySim2Bytes, Constant.FILTER_SIM2);

                        // Check month warning notify
                        maybeMonthMobileWarningNotification(monthSim1Bytes, Constant.FILTER_SIM1);
                        maybeMonthMobileWarningNotification(monthSim2Bytes, Constant.FILTER_SIM2);

                        // Check month limit notify
                        maybeMonthMobileLimitNotification(monthSim1Bytes, Constant.FILTER_SIM1);
                        maybeMonthMobileLimitNotification(monthSim2Bytes, Constant.FILTER_SIM2);

                        updateNotify(monthMobileTotalBytes, dayMobileTotalBytes);

			notifyListener();

			return true;
		}

	};

	private BroadcastReceiver mStatsReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(NetApplication.TAG,
					"DataUsageService: mStatsReceiver]   onReceive");

			// update data usage(day and month)
			mHandler.obtainMessage(1).sendToTarget();
		}
	};

        private BroadcastReceiver mSimListener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String action = arg1.getAction();
			if (ACTION_SIM_STATE_CHANGED.equals(action)) {
				String state = arg1.getStringExtra("ss");
				int slot = arg1.getIntExtra("slot", 0);
				notifySimStateChanged(state, slot);
			}
		}
		
	};

	public DataUsageService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(NetApplication.TAG, "DataUsageService: [onCreate]");

		mBinder = new MyBinder();

		mNetAdapter = NetAdapter.getInstance(this);

                mNotificationListener = new NotificationWarningListener(this);

                mSubInfoUtils = SubInfoUtils.getInstance(this);

		HandlerThread thread = new HandlerThread("update_stats");
		thread.start(); 
                mHandler = new Handler(thread.getLooper(), mHandlerCallback);

                mNetAdapter.registerObserver(mAlertObserver);
                mNetAdapter.registerNetworkPolicyListener(mPolicyListener);
                mNetAdapter.advisePersistThreshold(thresholdBytes);

                // listen for stats update events
                this.registerReceiver(mStatsReceiver, statsFilter,
				"android.permission.READ_NETWORK_USAGE_HISTORY", mHandler);

                // listen for sim state update events
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(ACTION_SIM_STATE_CHANGED);
                this.registerReceiver(mSimListener, intentFilter);

		showNotify();
	}

	private void showNotify() {
		Log.d(NetApplication.TAG, "DataUsageService: [showNotify]");
		mNotificationListener.onStart(Constant.NOTIFY_ID_FOR_START);
	}

        private void updateNotify(long monthStats, long dayStats) {
		Log.d(NetApplication.TAG, "DataUsageService: [updateNotify]");
		mNotificationListener.onStatsUpadte(Constant.NOTIFY_ID_FOR_START, monthStats, dayStats);
	}

	private void maybeDayMobileWarningNotification(long bytes, int slotId) {
		int defaultDataSlotId = mSubInfoUtils.getDefaultDataSlotId();
		if (slotId != defaultDataSlotId || !mSubInfoUtils.getDataEnabled(this)) {
			return;
		}
		if (slotId == Constant.FILTER_SIM1 && 
			(!SharePreferenceUtils.getSim1DayWarningEnabled(this) ||
				SharePreferenceUtils.getSim1DayWarningValue(this) == 0)) {
			return;
		}
		if (slotId == Constant.FILTER_SIM2 && 
			(!SharePreferenceUtils.getSim2DayWarningEnabled(this) ||
				SharePreferenceUtils.getSim2DayWarningValue(this) == 0)) {
			return;
		}
		long dayWarningValue = 0;
		if (slotId == Constant.FILTER_SIM1) {
			int dayWarningPercent = SharePreferenceUtils.getSim1DayWarningValue(this);
			int monthLimitMB = SharePreferenceUtils.getSim1MonthLimitValue(this);
			dayWarningValue = StringUtils.convertMtoBytes(dayWarningPercent, monthLimitMB);
		} else if (slotId == Constant.FILTER_SIM2) {
			int dayWarningPercent = SharePreferenceUtils.getSim2DayWarningValue(this);
			int monthLimitMB = SharePreferenceUtils.getSim2MonthLimitValue(this);
			dayWarningValue = StringUtils.convertMtoBytes(dayWarningPercent, monthLimitMB);
		}
		if (bytes > dayWarningValue) {
			// notify
			mNotificationListener.onDayMobileWarning(Constant.NOTIFY_ID_FOR_DAY_WARNING, 
							bytes, 
							bytes-dayWarningValue,
							slotId);
		}
	}

	private void maybeMonthMobileWarningNotification(long bytes, int slotId) {
		int defaultDataSlotId = mSubInfoUtils.getDefaultDataSlotId();
		if (slotId != defaultDataSlotId || !mSubInfoUtils.getDataEnabled(this)) {
			return;
		}
		if (slotId == Constant.FILTER_SIM1 && 
			(!SharePreferenceUtils.getSim1MonthWarningEnabled(this) ||
				SharePreferenceUtils.getSim1MonthWarningValue(this) == 0)) {
			return;
		}
		if (slotId == Constant.FILTER_SIM2 && 
			(!SharePreferenceUtils.getSim2MonthWarningEnabled(this) ||
				SharePreferenceUtils.getSim2MonthWarningValue(this) == 0)) {
			return;
		}
		long monthWarningValue = 0;
		if (slotId == Constant.FILTER_SIM1) {
			int monthWarningPercent = SharePreferenceUtils.getSim1MonthWarningValue(this);
			int monthLimitMB = SharePreferenceUtils.getSim1MonthLimitValue(this);
			monthWarningValue = StringUtils.convertMtoBytes(monthWarningPercent, monthLimitMB);
		} else if (slotId == Constant.FILTER_SIM2) {
			int monthWarningPercent = SharePreferenceUtils.getSim2MonthWarningValue(this);
			int monthLimitMB = SharePreferenceUtils.getSim2MonthLimitValue(this);
			monthWarningValue = StringUtils.convertMtoBytes(monthWarningPercent, monthLimitMB);
		}
		if (bytes > monthWarningValue) {
			// notify
			mNotificationListener.onMonthMobileWarning(Constant.NOTIFY_ID_FOR_MONTH_WARNING, 
							bytes,
							bytes-monthWarningValue,
							slotId);
		}
	}

	private void maybeMonthMobileLimitNotification(long bytes, int slotId) {
		int defaultDataSlotId = mSubInfoUtils.getDefaultDataSlotId();
		if (slotId != defaultDataSlotId || !mSubInfoUtils.getDataEnabled(this)) {
			return;
		}
		if (slotId == Constant.FILTER_SIM1 && 
                        /*(!SharePreferenceUtils.getSim1MonthLimitEnabled(this) ||*/
				SharePreferenceUtils.getSim1MonthLimitValue(this) == 0/*)*/) {
			return;
		}
		if (slotId == Constant.FILTER_SIM2 && 
                        /*(!SharePreferenceUtils.getSim2MonthLimitEnabled(this) ||*/
				SharePreferenceUtils.getSim2MonthLimitValue(this) == 0/*)*/) {
			return;
		}
		long monthLimitValue = 0;
		if (slotId == Constant.FILTER_SIM1) {
			int monthLimitMB = SharePreferenceUtils.getSim1MonthLimitValue(this);
			monthLimitValue = monthLimitMB * Constant.MB_IN_BYTES;
		} else if (slotId == Constant.FILTER_SIM2) {
			int monthLimitMB = SharePreferenceUtils.getSim2MonthLimitValue(this);
			monthLimitValue = monthLimitMB * Constant.MB_IN_BYTES;
		}
		if (bytes > monthLimitValue) {
			// notify
			mNotificationListener.onMonthMobileLimit(Constant.NOTIFY_ID_FOR_MONTH_LIMIT, 
							bytes,
							bytes-monthLimitValue,
							slotId);
		}
	}

	public void dayMobileWarningUpdate(long bytes, int slotId) {
		long[] dayTimes = TimeUtils.getTime(TimeUtils.DAY_TYPE);
		long dayStats = 0;
		try {
			mNetAdapter.forceUpdate();
			dayStats = mNetAdapter.getMobileTotalBytes(slotId, dayTimes[0], dayTimes[1]);
		} catch (NetRemoteException e) {
			e.printStackTrace();
		}
		maybeDayMobileWarningNotification(dayStats, slotId);
	}

	public void monthMobileWarningUpdate(long bytes, int slotId) {
		long[] dayTimes = TimeUtils.getTime(TimeUtils.MONTH_TYPE);
		// long totalStats = 0;
                long monthStats = 0;
		try {
			mNetAdapter.forceUpdate();
			//long monthSim1Stats = mNetAdapter.getMobileTotalBytes(AppUtils.SIM1, dayTimes[0], dayTimes[1]);
			//long monthSim2Stats = mNetAdapter.getMobileTotalBytes(AppUtils.SIM2, dayTimes[0], dayTimes[1]);
			//totalStats = monthSim1Stats + monthSim2Stats;
                        monthStats = mNetAdapter.getMobileTotalBytes(slotId, dayTimes[0], dayTimes[1]);
		} catch (NetRemoteException e) {
			e.printStackTrace();
		}
		maybeMonthMobileWarningNotification(monthStats, slotId);
	}

	public void monthMobileLimitUpdate(long bytes, int slotId) {
                long[] dayTimes = TimeUtils.getTime(TimeUtils.MONTH_TYPE);
		long monthStats = 0;
		try {
			mNetAdapter.forceUpdate();
			monthStats = mNetAdapter.getMobileTotalBytes(slotId, dayTimes[0], dayTimes[1]);
		} catch (NetRemoteException e) {
			e.printStackTrace();
		}
		maybeMonthMobileLimitNotification(monthStats, slotId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(NetApplication.TAG, "DataUsageService: [onBind]");
		return mBinder;
	}

        @Override
	public void onDestroy() {
		super.onDestroy();
                mStatsUpdateListeners.clear();
		mSimStateListeners.clear();
		mNetAdapter.unregisterObserver(mAlertObserver);
                mNetAdapter.unregisterNetworkPolicyListener(mPolicyListener);
                this.unregisterReceiver(mStatsReceiver);
                this.unregisterReceiver(mSimListener);
                mNotificationListener.cancelAll();
	}

	private void notifyListener() {
		for (StatsUpdateListener listener : mStatsUpdateListeners) {
			listener.onUpdate();
		}
	}

	public void addListener(StatsUpdateListener listener) {
            mStatsUpdateListeners.add(listener);
	}

        public void removeListener(StatsUpdateListener listener) {
            mStatsUpdateListeners.remove(listener);
	}

	public interface StatsUpdateListener {
		void onUpdate();
	}

	private void notifySimStateChanged(String state, int slotId) {
    		for (SimStateListener listener : mSimStateListeners) {
    			listener.onSimStateChanged(state, slotId);
    		}
    	}

	public void addSimListener(SimStateListener listener) {
        	mSimStateListeners.add(listener);
	}

        public void removeSimListener(SimStateListener listener) {
        	mSimStateListeners.remove(listener);
	}

	public interface SimStateListener {
		void onSimStateChanged(String state, int slotId);
	}

}
