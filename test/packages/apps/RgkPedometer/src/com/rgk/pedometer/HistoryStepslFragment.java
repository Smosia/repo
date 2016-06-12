package com.rgk.pedometer;

import java.util.List;

import com.rgk.pedometer.TimeUtils.WeekBound;
import com.rgk.pedometer.model.StepInfo;
import com.rgk.pedometer.widget.ChartView;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryStepslFragment extends Fragment {
	private static final int LOADER_ID = 1;
	public static final String EXTRA_START = "start";
	public static final String EXTRA_END = "end";

	private TextView mTitle;
	private TextView todaySteps;
	private TextView todayCalories;
	private TextView currentWeek;
	private TextView todayDate;

	private Button upWeekBtn;
	private Button nextWeekBtn;

	private ChartView mChartView;

	private WeekBound mWeekBound;

	private String mTodayStepsText;

	private long mFirstDateMillis = 0;
	private long mLastDateMillis = 0;

	private StepsQueryTask.Listener mQueryListener = new StepsQueryTask.Listener() {

		@Override
		public void onQueryFinish(Object ret, int type) {
			if (ret == null) {
				return;
			}
			if (StepsQueryTask.TYPE_TODAY == type) {
				StepInfo info = (StepInfo) ret;
				setCurrentSteps(info.walkSteps);
				setCaloriesAndDistance(Float.parseFloat(info.calories),
						Float.parseFloat(info.distance));
			} else if (StepsQueryTask.TYPE_FIRST_DATE == type) {
				mFirstDateMillis = (Long) ret;
			} else if (StepsQueryTask.TYPE_LAST_DATE == type) {
				mLastDateMillis = (Long) ret;
			}
		}
	};

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.up_week:
				if (mWeekBound != null) {
					if (mFirstDateMillis >= mWeekBound.startDayMillis) {
						toastNoMoreWalks();
						return;
					}
					mWeekBound = TimeUtils.getLastWeekBound(mWeekBound);
					currentWeek.setText(mWeekBound.startDayFormat + "-"
							+ mWeekBound.endDayFormat);
					reload();
				}
				break;

			case R.id.next_week:
				if (mWeekBound != null) {
					if (mLastDateMillis <= mWeekBound.endDayMillis) {
						toastNoMoreWalks();
						return;
					}
					mWeekBound = TimeUtils.getNextWeekBound(mWeekBound);
					currentWeek.setText(mWeekBound.startDayFormat + "-"
							+ mWeekBound.endDayFormat);
					reload();
				}
				break;
			}

		}
	};

	private final LoaderCallbacks<List<StepInfo>> mStepsLoaderCallbacks = new LoaderCallbacks<List<StepInfo>>() {

		@Override
		public Loader<List<StepInfo>> onCreateLoader(int token, Bundle args) {
			StepsLoader loader = new StepsLoader(getActivity());
			loader.setExtras(args);
			return loader;
		}

		@Override
		public void onLoadFinished(Loader<List<StepInfo>> arg0,
				List<StepInfo> arg1) {
			int[] oneWeekSteps = { 0, 0, 0, 0, 0, 0, 0 };
			for (int i = 0; i < arg1.size(); i++) {
				StepInfo si = arg1.get(i);
				oneWeekSteps[si.dayInWeek] = si.runSteps + si.walkSteps;
			}

			mChartView.setStepsForOneWeek(oneWeekSteps);
		}

		@Override
		public void onLoaderReset(Loader<List<StepInfo>> arg0) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTodayStepsText = getResources().getString(R.string.today_walk_steps);

		// Get first date and last date
		new StepsQueryTask(mQueryListener)
				.execute(StepsQueryTask.TYPE_FIRST_DATE);
		new StepsQueryTask(mQueryListener)
				.execute(StepsQueryTask.TYPE_LAST_DATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.history_steps_fragment, null);
		mTitle = (TextView) view.findViewById(R.id.action_bar_title);
		mTitle.setText(R.string.title_history_sports);
		todaySteps = (TextView) view.findViewById(R.id.today_finish_steps);
		todayCalories = (TextView) view
				.findViewById(R.id.today_consume_calories);
		todayCalories.setText("今天消耗2006卡=一个冰淇淋+可乐");// 为了调节面，后面修改
		currentWeek = (TextView) view.findViewById(R.id.current_week_label);
		todayDate = (TextView) view.findViewById(R.id.today_date_label);
		todayDate.setText(TimeUtils.getCurrentDateWithFormat());

		upWeekBtn = (Button) view.findViewById(R.id.up_week);
		nextWeekBtn = (Button) view.findViewById(R.id.next_week);
		upWeekBtn.setOnClickListener(mOnClickListener);
		nextWeekBtn.setOnClickListener(mOnClickListener);

		mChartView = (ChartView) view.findViewById(R.id.step_chartview);

		return view;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (PedometerApplication.getInstance().isServiceBinded()) {
			StepService service = PedometerApplication.getInstance()
					.getStepService();
			setCurrentSteps(service.getSteps());
			setCaloriesAndDistance(service.getCalories(), service.getDistance());
		} else {
			new StepsQueryTask(mQueryListener)
					.execute(StepsQueryTask.TYPE_TODAY);
		}

		mWeekBound = TimeUtils.getCurrentWeekBound();

		currentWeek.setText(mWeekBound.startDayFormat + "-"
				+ mWeekBound.endDayFormat);

		reload();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void setCurrentSteps(int steps) {
		todaySteps.setText(String.format(mTodayStepsText, steps));
	}

	public void setCaloriesAndDistance(float calories, float distance) {
		String format = getResources().getString(
				R.string.today_walked_calories_and_distance);
		String label = String.format(format,
				String.valueOf(Math.round(distance)),
				String.valueOf(Math.round(calories)));
		todayCalories.setText(label);
	}

	public void reload() {
		Bundle extras = new Bundle();
		extras.putLong(EXTRA_START, mWeekBound.startDayMillis);
		extras.putLong(EXTRA_END, mWeekBound.endDayMillis);
		getLoaderManager().restartLoader(LOADER_ID, extras,
				mStepsLoaderCallbacks);
	}

	private void toastNoMoreWalks() {
		Toast.makeText(getActivity(), R.string.toast_no_more_record,
				Toast.LENGTH_SHORT).show();
	}

}
