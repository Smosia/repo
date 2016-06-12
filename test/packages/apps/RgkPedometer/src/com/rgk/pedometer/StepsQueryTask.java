package com.rgk.pedometer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.rgk.pedometer.model.StepInfoDao;

import android.os.AsyncTask;

/**
 * The Param must be formatDate, eg : 20160120
 * @author qingming.song
 *
 */
public class StepsQueryTask extends AsyncTask<Integer, Integer, Object> {
	public static final int TYPE_TODAY = 1;
	public static final int TYPE_YESTODAY = 2;
	
	public static final int TYPE_FIRST_DATE = 3;
	public static final int TYPE_LAST_DATE = 4;
	
	private Listener mListener;
	private int mType;
	
	public StepsQueryTask(Listener listener) {
		mListener = listener;
	}

	@Override
	protected Object doInBackground(Integer... args) {
		if (args == null) {
			return null;
		}
		mType = args[0];
		
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	
		if (mType == TYPE_TODAY) {
			long time = System.currentTimeMillis();
	    	Date date = new Date(time);
	    	String formatDate = format.format(date);
	    	return StepInfoDao.getInstance().getStepInfo(formatDate);
		} else if (mType == TYPE_YESTODAY) {
			long time = System.currentTimeMillis() - TimeUtils.ONE_DAY_MILLIS;
	    	Date date = new Date(time);
	    	String formatDate = format.format(date);
	    	return StepInfoDao.getInstance().getSteps(formatDate);
		} else if (mType == TYPE_FIRST_DATE) {
			return StepInfoDao.getInstance().getFirstDateMillis();
		} else if (mType == TYPE_LAST_DATE) {
			return StepInfoDao.getInstance().getLastDateMillis();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if (mListener != null) {
			mListener.onQueryFinish(result, mType);
		}
	}
	
	public interface Listener {
		void onQueryFinish(Object result, int type);
	}

}
