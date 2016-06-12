package com.rgk.netmanager;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

public class StatsLoader extends AsyncTaskLoader<NetworkStatsFragment.StatsInfo> {

	private Bundle mExtras;

	public StatsLoader(Context context) {
		super(context);
	}

	public void setExtras(Bundle extras) {
		mExtras = extras;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		forceLoad();
	}

	@Override
	public NetworkStatsFragment.StatsInfo loadInBackground() {
		try {
                    NetworkStatsFragment.StatsInfo info = new NetworkStatsFragment.StatsInfo();
		    info.sim1Total = AppUtils.getTotalStatsForCurrentMonth(getContext(), AppUtils.SIM1);
                    info.sim2Total = AppUtils.getTotalStatsForCurrentMonth(getContext(), AppUtils.SIM2);
                    info.wifiTotal = AppUtils.getTotalStatsForCurrentMonth(getContext(), AppUtils.WIFI);
                    return info;
		} catch (NetRemoteException e) {
			e.printStackTrace();
			return null;
		}
		// return AppUtils.getApps(getContext());
	}

	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		cancelLoad();
	}

	@Override
	protected void onReset() {
		super.onReset();
		cancelLoad();
	}
}
