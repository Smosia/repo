package com.rgk.netmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NetworkSettingsFragment extends Fragment implements
		View.OnClickListener {

	private Context mContext;

	private static final String TAG_DAY_WARNING_EDITOR = "day_warning_editor";
	private static final String TAG_MONTH_WARNING_EDITOR = "month_warning_editor";
	private static final String TAG_MONTH_LIMIT_EDITOR = "month_limit_editor";

	public static final String BROADCAST_MONTH_LIMIT_UPDATE = "month_limit_update";
	public static final String BROADCAST_MONTH_WARNING_UPDATE = "month_warning_update";
	public static final String BROADCAST_DAY_WARNING_UPDATE = "day_warning_update";

	/** @hide */
	public static final long KB_IN_BYTES = 1024;
	/** @hide */
	public static final long MB_IN_BYTES = KB_IN_BYTES * 1024;
	/** @hide */
	public static final long GB_IN_BYTES = MB_IN_BYTES * 1024;

	private View mStatsMonitorContainer;
	private View mBodyContainer;
	private ImageView mStatsMonitorEnableImgview;

	private TextView sim1MonthLimitValue;
	private TextView sim1MonthWarningValue;
	private TextView sim1DayWarningValue;

	private TextView sim2MonthLimitValue;
	private TextView sim2MonthWarningValue;
	private TextView sim2DayWarningValue;

	private View sim1SettingContainer;
	private View sim2SettingContainer;

	private View sim1MonthLimitContainer;
	private View sim1MonthWarningContainer;
	private View sim1DayWarningContainer;

	private View sim2MonthLimitContainer;
	private View sim2MonthWarningContainer;
	private View sim2DayWarningContainer;

	private ImageButton sim1MonthLimitEnable;
	private ImageButton sim1MonthWarningEnable;
	private ImageButton sim1DayWarningEnable;

	private ImageButton sim2MonthLimitEnable;
	private ImageButton sim2MonthWarningEnable;
	private ImageButton sim2DayWarningEnable;

	private boolean mSim1MonthLimitEnableState = false;
	private boolean mSim1MonthWarningEnableState = false;
	private boolean mSim1DayWarningEnableState = false;
	private boolean mSim2MonthLimitEnableState = false;
	private boolean mSim2MonthWarningEnableState = false;
	private boolean mSim2DayWarningEnableState = false;

	private boolean mStatsMonitorEnabled = false;

	private View mView;

        private SubInfoUtils mSubInfoUtils;

	public NetworkSettingsFragment() {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
                mSubInfoUtils = SubInfoUtils.getInstance(mContext);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.net_settings, null);
		initViews();
		updateSimSettingEnabled();
		return mView;
	}

	private void initViews() {
		mStatsMonitorContainer = mView
				.findViewById(R.id.stats_monitor_enabled_setting_container);
		mStatsMonitorContainer.setOnClickListener(this);
		mBodyContainer = mView.findViewById(R.id.body_container);
		mStatsMonitorEnableImgview = (ImageView) mView
				.findViewById(R.id.stats_monitor_enabled_setting_imgview);

		sim1SettingContainer = mView
				.findViewById(R.id.sim1_settings_container);
		sim2SettingContainer = mView
				.findViewById(R.id.sim2_settings_container);

		sim1MonthLimitValue = (TextView) mView
				.findViewById(R.id.sim1_month_limit_value);
		sim1MonthWarningValue = (TextView) mView
				.findViewById(R.id.sim1_month_warning_value);
		sim1DayWarningValue = (TextView) mView
				.findViewById(R.id.sim1_day_warning_value);

		sim2MonthLimitValue = (TextView) mView
				.findViewById(R.id.sim2_month_limit_value);
		sim2MonthWarningValue = (TextView) mView
				.findViewById(R.id.sim2_month_warning_value);
		sim2DayWarningValue = (TextView) mView
				.findViewById(R.id.sim2_day_warning_value);

		sim1MonthLimitContainer = mView
				.findViewById(R.id.sim1_month_limit_setting_container);
		sim1MonthLimitContainer.setOnClickListener(this);
		sim1MonthWarningContainer = mView
				.findViewById(R.id.sim1_month_warning_setting_container);
		sim1MonthWarningContainer.setOnClickListener(this);
		sim1DayWarningContainer = mView
				.findViewById(R.id.sim1_day_warning_setting_container);
		sim1DayWarningContainer.setOnClickListener(this);

		sim2MonthLimitContainer = mView
				.findViewById(R.id.sim2_month_limit_setting_container);
		sim2MonthLimitContainer.setOnClickListener(this);
		sim2MonthWarningContainer = mView
				.findViewById(R.id.sim2_month_warning_setting_container);
		sim2MonthWarningContainer.setOnClickListener(this);
		sim2DayWarningContainer = mView
				.findViewById(R.id.sim2_day_warning_setting_container);
		sim2DayWarningContainer.setOnClickListener(this);

		sim1MonthLimitEnable = (ImageButton) mView
				.findViewById(R.id.sim1_month_limit_enabled);
		sim1MonthLimitEnable.setOnClickListener(this);
		sim1MonthWarningEnable = (ImageButton) mView
				.findViewById(R.id.sim1_month_warning_enabled);
		sim1MonthWarningEnable.setOnClickListener(this);
		sim1DayWarningEnable = (ImageButton) mView
				.findViewById(R.id.sim1_day_warning_enabled);
		sim1DayWarningEnable.setOnClickListener(this);

		sim2MonthLimitEnable = (ImageButton) mView
				.findViewById(R.id.sim2_month_limit_enabled);
		sim2MonthLimitEnable.setOnClickListener(this);
		sim2MonthWarningEnable = (ImageButton) mView
				.findViewById(R.id.sim2_month_warning_enabled);
		sim2MonthWarningEnable.setOnClickListener(this);
		sim2DayWarningEnable = (ImageButton) mView
				.findViewById(R.id.sim2_day_warning_enabled);
		sim2DayWarningEnable.setOnClickListener(this);

		mStatsMonitorEnabled = SharePreferenceUtils
				.getStatsMonitorEnabled(mContext);
		setStatsMonitorEnabled(mStatsMonitorEnabled);

		mSim1DayWarningEnableState = SharePreferenceUtils
				.getSim1DayWarningEnabled(mContext);
		mSim1MonthWarningEnableState = SharePreferenceUtils
				.getSim1MonthWarningEnabled(mContext);
		// mSim1MonthLimitEnableState =
		// SharePreferenceUtils.getSim1MonthLimitEnabled(mContext);

		mSim2DayWarningEnableState = SharePreferenceUtils
				.getSim2DayWarningEnabled(mContext);
		mSim2MonthWarningEnableState = SharePreferenceUtils
				.getSim2MonthWarningEnabled(mContext);
		// mSim2MonthLimitEnableState =
		// SharePreferenceUtils.getSim2MonthLimitEnabled(mContext);

		setSim1DayWarningEnabled(mSim1DayWarningEnableState);
		setSim1MonthWarningEnabled(mSim1MonthWarningEnableState);
		// setSim1MonthLimitEnabled(mSim1MonthLimitEnableState);
		setSim2DayWarningEnabled(mSim2DayWarningEnableState);
		setSim2MonthWarningEnabled(mSim2MonthWarningEnableState);
		// setSim2MonthLimitEnabled(mSim2MonthLimitEnableState);

		int sim1MonthLimitMB = SharePreferenceUtils
				.getSim1MonthLimitValue(mContext);
		refreshSim1MonthLimitValue(sim1MonthLimitMB);
		int sim1DayWarningPercent = SharePreferenceUtils
				.getSim1DayWarningValue(mContext);
		String sim1DayWarning = StringUtils.formatMBytesByPercent(sim1DayWarningPercent, sim1MonthLimitMB);
		refreshSim1DayWarningValue(sim1DayWarning);
		int sim1MonthWarningPercent = SharePreferenceUtils
				.getSim1MonthWarningValue(mContext);
		refreshSim1MonthWarningValue(sim1MonthWarningPercent);

		int sim2MonthLimitMB = SharePreferenceUtils
				.getSim2MonthLimitValue(mContext);
		refreshSim2MonthLimitValue(sim2MonthLimitMB);
		int sim2DayWarningPercent = SharePreferenceUtils
				.getSim2DayWarningValue(mContext);
		String sim2DayWarning = StringUtils.formatMBytesByPercent(sim2DayWarningPercent, sim2MonthLimitMB);
		refreshSim2DayWarningValue(sim2DayWarning);
		int sim2MonthWarningPercent = SharePreferenceUtils
				.getSim2MonthWarningValue(mContext);
		refreshSim2MonthWarningValue(sim2MonthWarningPercent);
	}

	private void setStatsMonitorEnabled(boolean enabled) {
		mStatsMonitorEnableImgview
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
		if (enabled) {
			mBodyContainer.setVisibility(View.VISIBLE);
		} else {
			mBodyContainer.setVisibility(View.GONE);
		}
	}

	private void setSim1DayWarningEnabled(boolean enabled) {
		sim1DayWarningEnable
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
	}

	private void setSim1MonthWarningEnabled(boolean enabled) {
		sim1MonthWarningEnable
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
	}

	private void setSim1MonthLimitEnabled(boolean enabled) {
		sim1MonthLimitEnable
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
	}

	private void setSim2DayWarningEnabled(boolean enabled) {
		sim2DayWarningEnable
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
	}

	private void setSim2MonthWarningEnabled(boolean enabled) {
		sim2MonthWarningEnable
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
	}

	private void setSim2MonthLimitEnabled(boolean enabled) {
		sim2MonthLimitEnable
				.setImageResource(enabled ? R.drawable.net_toggle_button_on
						: R.drawable.net_toggle_button_off);
	}

	public void refreshSim1MonthLimitValue(int value) {
		if (value > 0) {
			mSim1MonthLimitEnableState = true;
		} else {
			mSim1MonthLimitEnableState = false;
		}
		sim1MonthLimitValue.setText(value + "MB");
	}

	public void refreshSim1DayWarningValue(String value) {
		sim1DayWarningValue.setText(value);
	}

	public void refreshSim1MonthWarningValue(int value) {
		sim1MonthWarningValue.setText(value + "%");
	}

	public void refreshSim2MonthLimitValue(int value) {
		if (value > 0) {
			mSim2MonthLimitEnableState = true;
		} else {
			mSim2MonthLimitEnableState = false;
		}
		sim2MonthLimitValue.setText(value + "MB");
	}

	public void refreshSim2DayWarningValue(String value) {
		sim2DayWarningValue.setText(value);
	}

	public void refreshSim2MonthWarningValue(int value) {
		sim2MonthWarningValue.setText(value + "%");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
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
		Log.d("test", "onClick view=" + view);
		switch (view.getId()) {
		/*
		 * case R.id.sim1_month_limit_enabled: mSim1MonthLimitEnableState =
		 * !mSim1MonthLimitEnableState;
		 * setSim1MonthLimitEnabled(mSim1MonthLimitEnableState);
		 * SharePreferenceUtils.setSim1MonthLimitEnabled(mContext,
		 * mSim1MonthLimitEnableState); break;
		 */

		case R.id.sim1_month_limit_setting_container:
			MonthLimitEditorDialog.show(this, Constant.FILTER_SIM1);
			break;

		case R.id.sim1_month_warning_enabled:
			if (mSim1MonthLimitEnableState) {
				mSim1MonthWarningEnableState = !mSim1MonthWarningEnableState;
				setSim1MonthWarningEnabled(mSim1MonthWarningEnableState);
				SharePreferenceUtils.setSim1MonthWarningEnabled(mContext,
						mSim1MonthWarningEnableState);
			} else {
				Toast.makeText(mContext,
						R.string.toast_setting_month_limit_value,
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.sim1_month_warning_setting_container:
			if (mSim1MonthWarningEnableState) {
				MonthWarningEditorDialog.show(this, Constant.FILTER_SIM1);
			}
			break;

		case R.id.sim1_day_warning_enabled:
			if (mSim1MonthLimitEnableState) {
				mSim1DayWarningEnableState = !mSim1DayWarningEnableState;
				setSim1DayWarningEnabled(mSim1DayWarningEnableState);
				SharePreferenceUtils.setSim1DayWarningEnabled(mContext,
						mSim1DayWarningEnableState);
			} else {
				Toast.makeText(mContext,
						R.string.toast_setting_month_limit_value,
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.sim1_day_warning_setting_container:
			if (mSim1DayWarningEnableState) {
				DayWarningEditorDialog.show(this, Constant.FILTER_SIM1);
			}
			break;

		/*
		 * case R.id.sim2_month_limit_enabled: mSim2MonthLimitEnableState =
		 * !mSim2MonthLimitEnableState;
		 * setSim2MonthLimitEnabled(mSim2MonthLimitEnableState);
		 * SharePreferenceUtils.setSim2MonthLimitEnabled(mContext,
		 * mSim2MonthLimitEnableState); break;
		 */

		case R.id.sim2_month_limit_setting_container:
			MonthLimitEditorDialog.show(this, Constant.FILTER_SIM2);
			break;

		case R.id.sim2_month_warning_enabled:
			if (mSim2MonthLimitEnableState) {
				mSim2MonthWarningEnableState = !mSim2MonthWarningEnableState;
				setSim2MonthWarningEnabled(mSim2MonthWarningEnableState);
				SharePreferenceUtils.setSim2MonthWarningEnabled(mContext,
						mSim2MonthWarningEnableState);
			} else {
				Toast.makeText(mContext,
						R.string.toast_setting_month_limit_value,
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.sim2_month_warning_setting_container:
			if (mSim2MonthWarningEnableState) {
				MonthWarningEditorDialog.show(this, Constant.FILTER_SIM2);
			}
			break;

		case R.id.sim2_day_warning_enabled:
			if (mSim2MonthLimitEnableState) {
				mSim2DayWarningEnableState = !mSim2DayWarningEnableState;
				setSim2DayWarningEnabled(mSim2DayWarningEnableState);
				SharePreferenceUtils.setSim2DayWarningEnabled(mContext,
						mSim2DayWarningEnableState);
			} else {
				Toast.makeText(mContext,
						R.string.toast_setting_month_limit_value,
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.sim2_day_warning_setting_container:
			if (mSim2DayWarningEnableState) {
				DayWarningEditorDialog.show(this, Constant.FILTER_SIM2);
			}
			break;

		case R.id.stats_monitor_enabled_setting_container:
			mStatsMonitorEnabled = !mStatsMonitorEnabled;
			setStatsMonitorEnabled(mStatsMonitorEnabled);
			SharePreferenceUtils.setStatsMonitorEnabled(mContext,
					mStatsMonitorEnabled);
			if (mStatsMonitorEnabled) {
				NetApplication.getInstance().startNetService();
			} else {
				NetApplication.getInstance().stopNetService();
			}
			break;

		}
	}

	public void onSimChanged() {
		updateSimSettingEnabled();
	}

	private void updateSimSettingEnabled() {
		int[] slots = mSubInfoUtils.getActivatedSlotList();
		if (slots == null || slots.length == 0) {
			setSim1Enabled(false);
			setSim2Enabled(false);
		} if (slots.length == 1) {
			if (slots[0] == Constant.FILTER_SIM1) {
				setSim1Enabled(true);
                	} else {
				setSim1Enabled(false);
			}

			if (slots[0] == Constant.FILTER_SIM2) {
				setSim2Enabled(true);
			} else {
				setSim2Enabled(false);
			}
		} else if (slots.length == 2) {
			setSim1Enabled(true);
			setSim2Enabled(true);
		}
	}

	private void setSim1Enabled(boolean enabled) {
		sim1MonthLimitContainer.setClickable(enabled);
		sim1MonthWarningContainer.setClickable(enabled);
		sim1DayWarningContainer.setClickable(enabled);
		sim1MonthLimitEnable.setEnabled(enabled);
		sim1MonthWarningEnable.setEnabled(enabled);
		sim1DayWarningEnable.setEnabled(enabled);
	}

	private void setSim2Enabled(boolean enabled) {
		sim2MonthLimitContainer.setClickable(enabled);
		sim2MonthWarningContainer.setClickable(enabled);
		sim2DayWarningContainer.setClickable(enabled);
		sim2MonthLimitEnable.setEnabled(enabled);
		sim2MonthWarningEnable.setEnabled(enabled);
		sim2DayWarningEnable.setEnabled(enabled);
	}

	public static class MonthLimitEditorDialog extends DialogFragment {
		private static final String EXTRA_MONTH_LIMIT_VALUE = "month_limit_value";

		private static NetworkSettingsFragment targetFragment;

		private static int mSlotId = -1;

		public static void show(NetworkSettingsFragment parent, int slotId) {
			targetFragment = parent;
			mSlotId = slotId;
			final Bundle args = new Bundle();
			int limitMB = 0;
			if (mSlotId == Constant.FILTER_SIM1) {
				limitMB = SharePreferenceUtils.getSim1MonthLimitValue(parent
						.getActivity());
			} else if (mSlotId == Constant.FILTER_SIM2) {
				limitMB = SharePreferenceUtils.getSim2MonthLimitValue(parent
						.getActivity());
			}
			args.putInt(EXTRA_MONTH_LIMIT_VALUE, limitMB);

			final MonthLimitEditorDialog dialog = new MonthLimitEditorDialog();
			dialog.setArguments(args);
			dialog.show(parent.getFragmentManager(), TAG_MONTH_LIMIT_EDITOR);
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Context context = getActivity();

			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			final LayoutInflater dialogInflater = LayoutInflater.from(builder
					.getContext());

			final View view = dialogInflater.inflate(
					R.layout.month_limit_value_editor, null, false);
			final NumberPicker bytesPicker = (NumberPicker) view
					.findViewById(R.id.bytes);

			final int monthLimitMB = getArguments().getInt(
					EXTRA_MONTH_LIMIT_VALUE);

			bytesPicker.setMaxValue(Integer.MAX_VALUE);
			bytesPicker.setMinValue(1); // min bytes for 1M
			bytesPicker.setValue(monthLimitMB > 0 ? monthLimitMB : 100); // default bytes for 100M

			bytesPicker.setWrapSelectorWheel(false);

			// builder.setTitle(R.string.month_limit_setting_title);
			View customTitleView = dialogInflater.inflate(R.layout.custom_dialog_title, null);
			ImageView icon = (ImageView)customTitleView.findViewById(R.id.icon);
			icon.setImageResource(R.drawable.dialog_icon);
			TextView titleView = (TextView)customTitleView.findViewById(R.id.title);
			titleView.setText(R.string.month_limit_setting_title);
			builder.setCustomTitle(customTitleView);

			builder.setView(view);
			final Dialog dialog = builder.create();

			final Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
                        okBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					try {
						bytesPicker.clearFocus();

						int mb = bytesPicker.getValue();

						if (mSlotId == Constant.FILTER_SIM1) {
							SharePreferenceUtils
									.setSim1MonthLimitValue(context,
											mb);
							targetFragment
									.refreshSim1MonthLimitValue(mb);
						} else if (mSlotId == Constant.FILTER_SIM2) {
							SharePreferenceUtils
									.setSim2MonthLimitValue(context,
											mb);
							targetFragment
									.refreshSim2MonthLimitValue(mb);
						}

						NetApplication.getInstance()
								.getDataUsageService()
								.monthMobileLimitUpdate(mb * MB_IN_BYTES, mSlotId);

						if (dialog != null) {
							dialog.dismiss();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});

			// builder.setView(view);
/*
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							try {
								bytesPicker.clearFocus();

								int mb = bytesPicker.getValue();

								if (mSlotId == Constant.FILTER_SIM1) {
									SharePreferenceUtils
											.setSim1MonthLimitValue(context,
													mb);
									targetFragment
											.refreshSim1MonthLimitValue(mb);
								} else if (mSlotId == Constant.FILTER_SIM2) {
									SharePreferenceUtils
											.setSim2MonthLimitValue(context,
													mb);
									targetFragment
											.refreshSim2MonthLimitValue(mb);
								}

								NetApplication.getInstance()
										.getDataUsageService()
										.monthMobileLimitUpdate(mb * MB_IN_BYTES, mSlotId);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
*/

			return dialog;
		}
	}

	public static class DayWarningEditorDialog extends DialogFragment {
		private static final String EXTRA_DAY_WARNING_VALUE = "day_warning_value";
		private static final String EXTRA_MONTH_LIMIT_VALUE = "month_limit_value";

		private static NetworkSettingsFragment targetFragment;

		private static int mSlotId = -1;

		public static void show(NetworkSettingsFragment parent, int slotId) {
			targetFragment = parent;
			mSlotId = slotId;
			final Bundle args = new Bundle();
			int limitMB = 0;
			int warningPercent = 0;
			if (mSlotId == Constant.FILTER_SIM1) {
				limitMB = SharePreferenceUtils.getSim1MonthLimitValue(parent
						.getActivity());
				warningPercent = SharePreferenceUtils
						.getSim1DayWarningValue(parent.getActivity());
			} else if (mSlotId == Constant.FILTER_SIM2) {
				limitMB = SharePreferenceUtils.getSim2MonthLimitValue(parent
						.getActivity());
				warningPercent = SharePreferenceUtils
						.getSim2DayWarningValue(parent.getActivity());
			}
			args.putInt(EXTRA_DAY_WARNING_VALUE, warningPercent);
			args.putInt(EXTRA_MONTH_LIMIT_VALUE, limitMB);

			final DayWarningEditorDialog dialog = new DayWarningEditorDialog();
			dialog.setArguments(args);
			dialog.show(parent.getFragmentManager(), TAG_DAY_WARNING_EDITOR);
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Context context = getActivity();

			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			final LayoutInflater dialogInflater = LayoutInflater.from(builder
					.getContext());

			final View view = dialogInflater.inflate(
					R.layout.day_warning_value_editor, null, false);
			final SeekBar editorView = (SeekBar) view
					.findViewById(R.id.dialog_day_warning_value_editor_view);
			final TextView value = (TextView) view
					.findViewById(R.id.dialog_day_warning_value);

			final int dayWarningPercent = getArguments().getInt(
					EXTRA_DAY_WARNING_VALUE);
			final int monthLimitMB = getArguments().getInt(
					EXTRA_MONTH_LIMIT_VALUE);

			String warningValue = StringUtils.formatMBytesByPercent(dayWarningPercent, monthLimitMB);
			String result = dayWarningPercent + "%" + " (" + warningValue + ")";
			value.setText(result);

			editorView.setMax(100);
			editorView.setProgress(dayWarningPercent);
			editorView
					.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

						@Override
						public void onProgressChanged(SeekBar arg0, int arg1,
								boolean arg2) {
							int percent = arg1;
							String progress = StringUtils.formatMBytesByPercent(percent, monthLimitMB);
							String result = percent + "%" + " (" + progress + ")";
							value.setText(result);
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub

						}

					});
			// builder.setTitle(R.string.day_warning_setting_title);
			View customTitleView = dialogInflater.inflate(R.layout.custom_dialog_title, null);
			ImageView icon = (ImageView)customTitleView.findViewById(R.id.icon);
			icon.setImageResource(R.drawable.dialog_icon);
			TextView titleView = (TextView)customTitleView.findViewById(R.id.title);
			titleView.setText(R.string.day_warning_setting_title);
			builder.setCustomTitle(customTitleView);

			builder.setView(view);

			final Dialog dialog = builder.create();

			final Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
                        okBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					final int percent = editorView.getProgress();
					String warningValue = StringUtils.formatMBytesByPercent(percent, monthLimitMB);
					if (mSlotId == Constant.FILTER_SIM1) {
						SharePreferenceUtils.setSim1DayWarningValue(
								context, percent);
						targetFragment
								.refreshSim1DayWarningValue(warningValue);
					} else if (mSlotId == Constant.FILTER_SIM2) {
						SharePreferenceUtils.setSim2DayWarningValue(
								context, percent);
						targetFragment
								.refreshSim2DayWarningValue(warningValue);
					}

					final long bytes = StringUtils.convertMtoBytes(percent, monthLimitMB);
					NetApplication.getInstance().getDataUsageService()
							.dayMobileWarningUpdate(bytes, mSlotId);

					if (dialog != null) {
						dialog.dismiss();
					}
				}

			});
/*
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							final int percent = editorView.getProgress();
							String warningValue = StringUtils.formatMBytesByPercent(percent, monthLimitMB);
							if (mSlotId == Constant.FILTER_SIM1) {
								SharePreferenceUtils.setSim1DayWarningValue(
										context, percent);
								targetFragment
										.refreshSim1DayWarningValue(warningValue);
							} else if (mSlotId == Constant.FILTER_SIM2) {
								SharePreferenceUtils.setSim2DayWarningValue(
										context, percent);
								targetFragment
										.refreshSim2DayWarningValue(warningValue);
							}

							final long bytes = StringUtils.convertMtoBytes(percent, monthLimitMB);
							NetApplication.getInstance().getDataUsageService()
									.dayMobileWarningUpdate(bytes, mSlotId);
						}
					});
*/

			return dialog;
		}
	}

	/**
	 * Dialog to edit month warning value
	 */
	public static class MonthWarningEditorDialog extends DialogFragment {
		private static final String EXTRA_MONTH_WARNING_VALUE = "month_warning_value";
		private static final String EXTRA_MONTH_LIMIT_VALUE = "month_limit_value";

		private static NetworkSettingsFragment targetFragment;

		private static int mSlotId = -1;

		public static void show(NetworkSettingsFragment parent, int slotId) {
			targetFragment = parent;
			mSlotId = slotId;
			final Bundle args = new Bundle();
			int limitMB = 0;
			int warningPercent = 0;
			if (mSlotId == Constant.FILTER_SIM1) {
				limitMB = SharePreferenceUtils.getSim1MonthLimitValue(parent
						.getActivity());
				warningPercent = SharePreferenceUtils
						.getSim1MonthWarningValue(parent.getActivity());
			} else if (mSlotId == Constant.FILTER_SIM2) {
				limitMB = SharePreferenceUtils.getSim2MonthLimitValue(parent
						.getActivity());
				warningPercent = SharePreferenceUtils
						.getSim2MonthWarningValue(parent.getActivity());
			}
			args.putInt(EXTRA_MONTH_WARNING_VALUE, warningPercent);
			args.putInt(EXTRA_MONTH_LIMIT_VALUE, limitMB);

			final MonthWarningEditorDialog dialog = new MonthWarningEditorDialog();
			dialog.setArguments(args);
			dialog.show(parent.getFragmentManager(), TAG_MONTH_WARNING_EDITOR);
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Context context = getActivity();

			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			final LayoutInflater dialogInflater = LayoutInflater.from(builder
					.getContext());

			final View view = dialogInflater.inflate(
					R.layout.day_warning_value_editor, null, false);
			final SeekBar editorView = (SeekBar) view
					.findViewById(R.id.dialog_day_warning_value_editor_view);
			final TextView value = (TextView) view
					.findViewById(R.id.dialog_day_warning_value);

			final int monthWarningPercent = getArguments().getInt(
					EXTRA_MONTH_WARNING_VALUE);
			final int monthLimitMB = getArguments().getInt(
					EXTRA_MONTH_LIMIT_VALUE);

			String warningValue = StringUtils.formatMBytesByPercent(monthWarningPercent, monthLimitMB);
			String result = monthWarningPercent + "%" + " (" + warningValue + ")";
			value.setText(result);

			editorView.setMax(100);
			editorView.setProgress(monthWarningPercent);
			editorView
					.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

						@Override
						public void onProgressChanged(SeekBar arg0, int arg1,
								boolean arg2) {
							int percent = arg1;
							String warningValue = StringUtils.formatMBytesByPercent(percent, monthLimitMB);
							String result = percent + "%" + " (" + warningValue + ")";
							value.setText(result);
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub

						}

					});

			// builder.setTitle(R.string.month_warning_setting_title);
			View customTitleView = dialogInflater.inflate(R.layout.custom_dialog_title, null);
			ImageView icon = (ImageView)customTitleView.findViewById(R.id.icon);
			icon.setImageResource(R.drawable.dialog_icon);
			TextView titleView = (TextView)customTitleView.findViewById(R.id.title);
			titleView.setText(R.string.month_warning_setting_title);
			builder.setCustomTitle(customTitleView);

			builder.setView(view);

			final Dialog dialog = builder.create();

			final Button okBtn = (Button) view.findViewById(R.id.dialog_ok);
                        okBtn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					int percent = editorView.getProgress();
					if (mSlotId == Constant.FILTER_SIM1) {
						SharePreferenceUtils.setSim1MonthWarningValue(
								context, percent);
						targetFragment
								.refreshSim1MonthWarningValue(percent);
					} else if (mSlotId == Constant.FILTER_SIM2) {
						SharePreferenceUtils.setSim2MonthWarningValue(
								context, percent);
						targetFragment
								.refreshSim2MonthWarningValue(percent);
					}

					final long bytes = StringUtils.convertMtoBytes(percent, monthLimitMB);
					NetApplication.getInstance().getDataUsageService()
							.monthMobileWarningUpdate(bytes, mSlotId);

					if (dialog != null) {
						dialog.dismiss();
					}
				}

			});
/*
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int percent = editorView.getProgress();
							if (mSlotId == Constant.FILTER_SIM1) {
								SharePreferenceUtils.setSim1MonthWarningValue(
										context, percent);
								targetFragment
										.refreshSim1MonthWarningValue(percent);
							} else if (mSlotId == Constant.FILTER_SIM2) {
								SharePreferenceUtils.setSim2MonthWarningValue(
										context, percent);
								targetFragment
										.refreshSim2MonthWarningValue(percent);
							}

							final long bytes = StringUtils.convertMtoBytes(percent, monthLimitMB);
							NetApplication.getInstance().getDataUsageService()
									.monthMobileWarningUpdate(bytes, mSlotId);
						}
					});
*/

			return dialog;
		}
	}

}
