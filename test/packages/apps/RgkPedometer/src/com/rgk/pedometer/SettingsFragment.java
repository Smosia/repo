package com.rgk.pedometer;

import java.lang.reflect.Field;

import com.rgk.pedometer.widget.SwitchView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.NumberPicker;

public class SettingsFragment extends Fragment implements View.OnClickListener {
	public static final int TYPE_AGE = 1;
	public static final int TYPE_BODY_HIGH = 2;
	public static final int TYPE_BODY_WEIGHT = 3;
	public static final int TYPE_TARGET_STEPS = 4;
	
	private static final String[] mTargetSteps = {"1000", "2000", "3000", "4000", "5000",
		"6000", "7000", "8000", "9000", "10000", "12000", "15000", "18000", "20000"};

	private Context mContext;

	private TextView mTitleView;

	private SwitchView mSexSwitchView;
	private Button mAgeBtn;
	private Button mBodyHighBtn;
	private Button mBodyWeightBtn;
	private Button mTargetStepsBtn;

	private View mView;

	public SettingsFragment() {

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.settings_fragment, null);
		initViews();
		bindData();
		
		return mView;
	}

	private void initViews() {
		mTitleView = (TextView) mView.findViewById(R.id.action_bar_title);
		mTitleView.setText(R.string.title_settings);
		mSexSwitchView = (SwitchView) mView.findViewById(R.id.sex_value);
		mSexSwitchView.setOnClickListener(this);
		mAgeBtn = (Button) mView.findViewById(R.id.age_value);
		mAgeBtn.setOnClickListener(this);
		mBodyHighBtn = (Button) mView.findViewById(R.id.body_high_value);
		mBodyHighBtn.setOnClickListener(this);
		mBodyWeightBtn = (Button) mView.findViewById(R.id.body_weight_value);
		mBodyWeightBtn.setOnClickListener(this);
		mTargetStepsBtn = (Button) mView.findViewById(R.id.target_steps_value);
		mTargetStepsBtn.setOnClickListener(this);
	}
	
	private void bindData() {
		setSex(SharePreferenceUtils.getSexValue(mContext));
		setAge(SharePreferenceUtils.getAgeValue(mContext));
		setBodyHigh(SharePreferenceUtils.getBodyHighValue(mContext));
		setBodyWeight(SharePreferenceUtils.getBodyWeightValue(mContext));
		setTargetSteps(SharePreferenceUtils.getTargetStepsValue(mContext));
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onClick(View view) {
		//Log.d("test", "onClick view=" + view);
		switch (view.getId()) {
		case R.id.sex_value:
			if (mSexSwitchView.getCheckItem() == 1) {
				mSexSwitchView.setCheckItem(2);
				SharePreferenceUtils.setSexValue(mContext, 2);
			} else {
				mSexSwitchView.setCheckItem(1);
				SharePreferenceUtils.setSexValue(mContext, 1);
			}
			break;
		case R.id.age_value:
			PersonalInfoEditorDialog.show(this, TYPE_AGE);
			break;
		case R.id.body_high_value:
			PersonalInfoEditorDialog.show(this, TYPE_BODY_HIGH);
			break;
		case R.id.body_weight_value:
			PersonalInfoEditorDialog.show(this, TYPE_BODY_WEIGHT);
			break;
		case R.id.target_steps_value:
			PersonalInfoEditorDialog.show(this, TYPE_TARGET_STEPS);
			break;
		}
	}

	public void setSex(int sex) {
		mSexSwitchView.setCheckItem(sex);
	}
	
	public void setAge(int age) {
		mAgeBtn.setText(String.valueOf(age));
	}

	public void setBodyHigh(int bodyHigh) {
		mBodyHighBtn.setText(String.valueOf(bodyHigh));
	}

	public void setBodyWeight(int bodyWeight) {
		mBodyWeightBtn.setText(String.valueOf(bodyWeight));
	}

	public void setTargetSteps(int targetSteps) {
		mTargetStepsBtn.setText(String.valueOf(targetSteps));
	}

	public static class PersonalInfoEditorDialog extends DialogFragment {
		private static final String EXTRA_MONTH_LIMIT_VALUE = "month_limit_value";

		private static SettingsFragment targetFragment;

		private static int mType;

		public static void show(SettingsFragment parent, int type) {
			targetFragment = parent;
			mType = type;
			final PersonalInfoEditorDialog dialog = new PersonalInfoEditorDialog();
			// Bundle args = new Bundle();
			// dialog.setArguments(args);
			dialog.show(parent.getFragmentManager(),
					"personal_info_settings_dialog");
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Context context = getActivity();

			final AlertDialog.Builder builder = new AlertDialog.Builder(context, 
					R.style.SettingsDialogTheme);
			final LayoutInflater dialogInflater = LayoutInflater.from(builder
					.getContext());

			final View view = dialogInflater.inflate(
					R.layout.personal_value_editor_dialog, null, false);
			final NumberPicker numberPicker = (NumberPicker) view
					.findViewById(R.id.number_picker);

			final TextView unitView = (TextView) view
					.findViewById(R.id.dialog_number_editor_unit);

			// builder.setTitle(R.string.month_limit_setting_title);
			final View customTitleView = dialogInflater.inflate(
					R.layout.custom_dialog_title, null);
			final ImageView icon = (ImageView) customTitleView
					.findViewById(R.id.icon);
			// icon.setImageResource(R.drawable.dialog_icon);
			final TextView titleView = (TextView) customTitleView
					.findViewById(R.id.title);
			// titleView.setText(R.string.month_limit_setting_title);

			int titleRes = 0;
			int maxValue = Integer.MAX_VALUE;
			int minValue = 0;
			int oldValue = 0;
			int unitRes = 0;

			if (mType == TYPE_AGE) {
				titleRes = R.string.title_age;
				maxValue = 150;
				minValue = 5;
				oldValue = SharePreferenceUtils.getAgeValue(context);
				unitRes = R.string.unit_age;
			} else if (mType == TYPE_BODY_HIGH) {
				titleRes = R.string.title_body_high;
				maxValue = 250;
				minValue = 100;
				oldValue = SharePreferenceUtils.getBodyHighValue(context);
				unitRes = R.string.unit_body_high;
			} else if (mType == TYPE_BODY_WEIGHT) {
				titleRes = R.string.title_body_weight;
				maxValue = 200;
				minValue = 15;
				oldValue = SharePreferenceUtils.getBodyWeightValue(context);
				unitRes = R.string.unit_body_weight;
			} else if (mType == TYPE_TARGET_STEPS) {
				titleRes = R.string.title_target_steps;
				maxValue = mTargetSteps.length - 1;
				minValue = 0;
				oldValue = indexOf(SharePreferenceUtils.getTargetStepsValue(context));
				unitRes = R.string.unit_target_steps;
				numberPicker.setDisplayedValues(mTargetSteps);
			}

			if (titleRes > 0) {
				titleView.setText(titleRes);
			}

			numberPicker.setMaxValue(maxValue);
			numberPicker.setMinValue(minValue);
			numberPicker.setValue(oldValue);
			numberPicker.setWrapSelectorWheel(false);
			setNumberPickerDividerColor(numberPicker);

			if (unitRes > 0) {
				unitView.setText(unitRes);
			}

			builder.setCustomTitle(customTitleView);
			builder.setView(view);
			final Dialog dialog = builder.create();

			final Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
			okBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					try {
						numberPicker.clearFocus();

						int values = numberPicker.getValue();

						if (mType == TYPE_AGE) {
							SharePreferenceUtils.setAgeValue(context,
									values);
							targetFragment.setAge(values);
						} else if (mType == TYPE_BODY_HIGH) {
							SharePreferenceUtils.setBodyHighValue(context,
									values);
							targetFragment.setBodyHigh(values);
						} else if (mType == TYPE_BODY_WEIGHT) {
							SharePreferenceUtils.setBodyWeightValue(context,
									values);
							targetFragment.setBodyWeight(values);
						} else if (mType == TYPE_TARGET_STEPS) {
							int targetSteps = Integer.parseInt(mTargetSteps[values]);
							values = targetSteps;
							SharePreferenceUtils.setTargetStepsValue(context,
									targetSteps);
							targetFragment.setTargetSteps(targetSteps);
						}

						// 通知service，去更新service里面的设置值，用于计算热量消耗
						// PedometerApplication.getInstance().getStepService();

						MainPedometerActivity activity = (MainPedometerActivity) getActivity();
						activity.onPersonalInfoChanged(mType, values);

						if (dialog != null) {
							dialog.dismiss();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});

			return dialog;
		}
		
		private void setNumberPickerDividerColor(NumberPicker numberPicker) {
			NumberPicker picker = numberPicker;
			Field[] fields = NumberPicker.class.getDeclaredFields();
			for (Field f : fields) {
				if(f.getName().equals("mSelectionDivider")) {
					f.setAccessible(true);
					try {
						f.set(picker, new ColorDrawable(0xFFFF7A20));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		private int indexOf(int targetSteps) {
			int index = -1;
			for (int i = 0; i < mTargetSteps.length; i++) {
				if (targetSteps == Integer.parseInt(mTargetSteps[i])) {
					index = i;
					break;
				}
			}
			
			return index;
		}
	}

}
