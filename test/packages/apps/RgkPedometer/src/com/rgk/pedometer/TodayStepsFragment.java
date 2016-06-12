package com.rgk.pedometer;

import com.rgk.pedometer.model.StepInfo;
import com.rgk.pedometer.widget.CheckSwitchView;
import com.rgk.pedometer.widget.CircleBar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodayStepsFragment extends Fragment implements
		View.OnClickListener {
	private TextView mTitleView;

	private TextView mTodayWalkPercent;
	private TextView mYestodayWalkPercent;
	private TextView mTodayWalkDistance;
	private TextView mTodayWalkCalories;
	private CircleBar mCircleBar;
	
	private CheckSwitchView stepEnabled;
	
	private Context mContext;
	private String mTodayFinishText;
	private String mYestodayFinishText;
	private String mTodayCaloriesText;
	private String mTodayDistanceText;
	
	private StepsQueryTask.Listener mQueryListener = new StepsQueryTask.Listener() {
		
		@Override
		public void onQueryFinish(Object ret, int type) {
			if (ret == null) {
				return;
			}
			if (StepsQueryTask.TYPE_TODAY == type) {
				StepInfo info = (StepInfo)ret;
				setCurrentSteps(info.walkSteps);
				setCalories(Float.parseFloat(info.calories));
				setDistance(Float.parseFloat(info.distance));
			} else if (StepsQueryTask.TYPE_YESTODAY == type) {
				setYestodayFinishedState((Integer)ret);
			}
		}
	};

	public TodayStepsFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTodayFinishText = getResources().getString(R.string.today_finish_steps);
		mYestodayFinishText = getResources().getString(R.string.yestoday_finish_steps);
		mTodayCaloriesText = getResources().getString(R.string.today_walk_calories);
		mTodayDistanceText = getResources().getString(R.string.today_walk_distance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.today_steps_fragment, null);
		mTodayWalkPercent = (TextView) view
				.findViewById(R.id.today_finish_percent);
		mYestodayWalkPercent = (TextView) view
				.findViewById(R.id.yestoday_finish_percent);
		mTodayWalkDistance = (TextView) view
				.findViewById(R.id.today_walk_distance);
		mTodayWalkCalories = (TextView) view
				.findViewById(R.id.today_walk_calories);
		mCircleBar = (CircleBar) view.findViewById(R.id.step_circlebar);

		mTitleView = (TextView) view.findViewById(R.id.action_bar_title);
		mTitleView.setText(R.string.title_totay_sports);
		
		stepEnabled = (CheckSwitchView)view.findViewById(R.id.step_enable_btn);
		stepEnabled.setVisibility(View.VISIBLE);
		stepEnabled.setOnClickListener(this);
		if (SharePreferenceUtils.getEnabled(mContext)) {
			stepEnabled.setCheckItem(CheckSwitchView.STATE_ON);
		} else {
			stepEnabled.setCheckItem(CheckSwitchView.STATE_OFF);
		}

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
		
		mCircleBar.setTargetSteps(SharePreferenceUtils.getTargetStepsValue(mContext));
		//mCircleBar.startCustomAnimation();

		if (PedometerApplication.getInstance().isServiceBinded()) {
			StepService service = PedometerApplication.getInstance().getStepService();
			setCurrentSteps(service.getSteps());
			setCalories(service.getCalories());
			setDistance(service.getDistance());
		} else {
			new StepsQueryTask(mQueryListener).execute(StepsQueryTask.TYPE_TODAY);
		}
		
		new StepsQueryTask(mQueryListener).execute(StepsQueryTask.TYPE_YESTODAY);
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

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.step_enable_btn:
			boolean enabled = SharePreferenceUtils.getEnabled(mContext);
			if (enabled) {
				SharePreferenceUtils.setEnabled(mContext, false);
				stepEnabled.setCheckItem(CheckSwitchView.STATE_OFF);
				PedometerApplication.getInstance().stopStepService();
			} else {
				SharePreferenceUtils.setEnabled(mContext, true);
				stepEnabled.setCheckItem(CheckSwitchView.STATE_ON);
				PedometerApplication.getInstance().startStepService();
			}
			break;
		}
	}

	private void setYestodayFinishedState(int steps) {
		/*long time = System.currentTimeMillis() - TimeUtils.ONE_DAY_MILLIS;
    	Date date = new Date(time);
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	String formatDate = format.format(date);
		steps = StepInfoDao.getInstance().getSteps(formatDate);*/
		int percent = getPercent(steps);
		mYestodayWalkPercent.setText(String.format(mYestodayFinishText, percent));
	}
	
	public void setCurrentSteps(int steps) {
		mCircleBar.setCurrentSteps(steps);
		int percent = getPercent(steps);
		mTodayWalkPercent.setText(String.format(mTodayFinishText, percent));
	}

	private int getPercent(int steps) {
		float f = steps * 1.0f / SharePreferenceUtils.getTargetStepsValue(mContext);
		int percent = (int)Math.floor(f * 100);
		return percent;
	}
	
	public void setTargetSteps(int steps) {
		mCircleBar.setTargetSteps(steps);
	}
	
	public void setCalories(float calories) {
		mTodayWalkCalories.setText(String.format(mTodayCaloriesText, String.valueOf(calories)));
	}
	
	public void setDistance(float distance) {
		mTodayWalkDistance.setText(String.format(mTodayDistanceText, String.valueOf(distance)));
	}
	
	public void refleshPercentView() {
		if (PedometerApplication.getInstance().isServiceBinded()) {
			StepService service = PedometerApplication.getInstance().getStepService();
			setCurrentSteps(service.getSteps());
		} else {
			StepsQueryTask.Listener l = new StepsQueryTask.Listener() {
				
				@Override
				public void onQueryFinish(Object result, int type) {
					if (result == null) {
						return;
					}
					if (StepsQueryTask.TYPE_TODAY == type) {
						StepInfo info = (StepInfo)result;
						setCurrentSteps(info.walkSteps);
					} else if (StepsQueryTask.TYPE_YESTODAY == type) {
						setYestodayFinishedState((Integer)result);
					}
				}
			};
			new StepsQueryTask(l).execute(StepsQueryTask.TYPE_TODAY);
			new StepsQueryTask(l).execute(StepsQueryTask.TYPE_YESTODAY);
		}
	}

}
