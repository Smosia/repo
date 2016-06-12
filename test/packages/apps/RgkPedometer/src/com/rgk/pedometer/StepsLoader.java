package com.rgk.pedometer;

import java.util.List;

import com.rgk.pedometer.model.StepInfo;
import com.rgk.pedometer.model.StepInfoDao;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

public class StepsLoader extends AsyncTaskLoader<List<StepInfo>> {

	private Bundle mExtras;

	public StepsLoader(Context context) {
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
	public List<StepInfo> loadInBackground() {
		long start = 0;
		long end = 0;
		if (mExtras != null) {
			start = mExtras.getLong(HistoryStepslFragment.EXTRA_START, 0);
			end = mExtras.getLong(HistoryStepslFragment.EXTRA_END, 0);
		}
		return StepInfoDao.getInstance().getHistorySteps(start, end);
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
