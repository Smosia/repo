package com.rgk.netmanager;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OneDayAppsStatsActivity extends Activity {
	public static final String EXTRA_DATE = "date";
	public static final String EXTRA_START = "start";
	public static final String EXTRA_END = "end";
	public static final String EXTRA_FORMAT_DATE = "format_date";
        public static final String EXTRA_TYPE = "type";

        private int mType = -1;

	private TextView mTitleLabel;
	private ImageView mBackBtn;

	private ListView appsListView = null;
	private AppItemAdapter mAppItemAdapter = null;

	private FirewallDatabaseHelper mFirewallDatabaseHelper = null;
	private NetAdapter mFirewallService = null;

	private View.OnClickListener backBtnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			onBackPressed();
		}
		
	};

	private final LoaderCallbacks<ArrayList<AppDetail>> mAppsLoaderCallbacks = new LoaderCallbacks<ArrayList<AppDetail>>() {
		@Override
		public Loader<ArrayList<AppDetail>> onCreateLoader(int id, Bundle args) {
			AppsLoader loader = new AppsLoader(OneDayAppsStatsActivity.this);
			loader.setExtras(args);
			return loader;
		}

		@Override
		public void onLoadFinished(Loader<ArrayList<AppDetail>> loader,
				ArrayList<AppDetail> appDetails) {
			mAppItemAdapter.changeData(appDetails);

		}

		@Override
		public void onLoaderReset(Loader<ArrayList<AppDetail>> arg0) {
			// TODO Auto-generated method stub

		}
	};

	public OneDayAppsStatsActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_day_stats_layout);

		/*ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_TITLE
					| ActionBar.DISPLAY_SHOW_HOME);
		}*/

		mTitleLabel = (TextView) findViewById(R.id.one_day_title_label);
		mBackBtn = (ImageView) findViewById(R.id.custom_action_bar_back_btn);
		mBackBtn.setOnClickListener(backBtnClickListener);

		appsListView = (ListView) findViewById(R.id.listview_app);
		mAppItemAdapter = new AppItemAdapter(this);
		appsListView.setAdapter(mAppItemAdapter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mFirewallDatabaseHelper = new FirewallDatabaseHelper(this);
		mFirewallService = NetAdapter.getInstance(this);
		Intent intent = getIntent();
		long start = intent.getLongExtra(EXTRA_START, 0);
		long end = intent.getLongExtra(EXTRA_END, 0);
		int date = intent.getIntExtra(EXTRA_DATE, 0);
                mType = intent.getIntExtra(EXTRA_TYPE, Constant.FILTER_ALL);
                String label = getResources().getString(R.string.one_day_stats_label);
                String formatLabel = String.format(label, intent.getStringExtra(EXTRA_FORMAT_DATE));
                if (mType == Constant.FILTER_SIM1) {
                    formatLabel = "SIM1 " + formatLabel;
                } else if (mType == Constant.FILTER_SIM2) {
                    formatLabel = "SIM2 " + formatLabel;
                } else if (mType == Constant.FILTER_WIFI) {
                    formatLabel = "WIFI " + formatLabel;
                }
		
		mTitleLabel.setText(formatLabel);
		Bundle extras = new Bundle();
		extras.putInt(EXTRA_DATE, date);
		extras.putLong(EXTRA_START, start);
		extras.putLong(EXTRA_END, end);
                extras.putInt(EXTRA_TYPE, mType);
		getLoaderManager().restartLoader(Constant.LOADER_ID_FOR_DAY_STATS, extras,
				mAppsLoaderCallbacks);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void refleshUI() {
		if (mAppItemAdapter != null) {
			mAppItemAdapter.notifyDataSetInvalidated();
		}

	}

	class AppItemAdapter extends BaseAdapter {
		private Context mContext;

		private ArrayList<AppDetail> appDetails = null;

		public AppItemAdapter(Context context) {
			mContext = context;
			appDetails = new ArrayList<AppDetail>();
		}

		@Override
		public int getCount() {
			if (appDetails != null) {
				return appDetails.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return appDetails.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.firewall_item_app, null);
				viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView) convertView
						.findViewById(R.id.app_icon);
				viewHolder.appName = (TextView) convertView
						.findViewById(R.id.app_name);
				viewHolder.mobileDatausage = (TextView) convertView
						.findViewById(R.id.mobile_app_datausage);
				viewHolder.wifiDatausage = (TextView) convertView
						.findViewById(R.id.wifi_app_datausage);
				viewHolder.mobileLayout = convertView
						.findViewById(R.id.mobile_linearLayout);
				viewHolder.mobileLayout.setOnClickListener(mOnClickListener);
				viewHolder.wifiLayout = convertView
						.findViewById(R.id.wifi_linearLayout);
				viewHolder.wifiLayout.setOnClickListener(mOnClickListener);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			AppDetail appDetail = appDetails.get(position);
			viewHolder.mobileLayout.setTag(appDetail);
			viewHolder.wifiLayout.setTag(appDetail);
			viewHolder.icon.setImageDrawable(appDetail.icon);
			viewHolder.appName.setText(appDetail.name);
                        if (mType == Constant.FILTER_ALL) {
			    viewHolder.mobileDatausage.setText("mobile:"
					+ StringUtils.formatBytes(appDetail.sim1Total
							+ appDetail.sim2Total));
			    viewHolder.wifiDatausage.setText("wifi:"
					+ StringUtils.formatBytes(appDetail.wifiTotal));
                            viewHolder.wifiDatausage.setVisibility(View.VISIBLE);
                        } else if (mType == Constant.FILTER_SIM1){
                            viewHolder.mobileDatausage.setText(StringUtils.formatBytes(appDetail.sim1Total));
                            viewHolder.wifiDatausage.setVisibility(View.GONE);
                        } else if (mType == Constant.FILTER_SIM2){
                            viewHolder.mobileDatausage.setText(StringUtils.formatBytes(appDetail.sim2Total));
                            viewHolder.wifiDatausage.setVisibility(View.GONE);
                        } else if (mType == Constant.FILTER_WIFI){
                            viewHolder.mobileDatausage.setText(StringUtils.formatBytes(appDetail.wifiTotal));
                            viewHolder.wifiDatausage.setVisibility(View.GONE);
                        }
			ImageView mobileNetState = (ImageView) viewHolder.mobileLayout
					.findViewById(R.id.mobile_net_state);
			ImageView wifiNetState = (ImageView) viewHolder.wifiLayout
					.findViewById(R.id.wifi_net_state);
			// viewHolder.mobileLayout.setVisibility(View.GONE);
			// viewHolder.wifiLayout.setVisibility(View.GONE);

			boolean enabled = SharePreferenceUtils.getEnabled(mContext);
			if (appDetail.selected_mobile || enabled) {
				mobileNetState
						.setImageResource(R.drawable.firewall_mobile_selected);
			} else {
				mobileNetState
						.setImageResource(R.drawable.firewall_mobile_unselected);
			}
			if (appDetail.selected_wifi || enabled) {
				wifiNetState
						.setImageResource(R.drawable.firewall_wifi_selected);
			} else {
				wifiNetState
						.setImageResource(R.drawable.firewall_wifi_unselected);
			}
			if (enabled) {
				viewHolder.mobileLayout.setEnabled(false);
				viewHolder.wifiLayout.setEnabled(false);
			} else {
				viewHolder.mobileLayout.setEnabled(true);
				viewHolder.wifiLayout.setEnabled(true);
			}

			return convertView;
		}

		public void changeData(ArrayList<AppDetail> appDetails) {
			this.appDetails = appDetails;
			this.notifyDataSetChanged();
		}

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			Log.d(NetApplication.TAG, "[onClick] " + view);
			AppDetail appDetail = (AppDetail) view.getTag();
			switch (view.getId()) {
			case R.id.mobile_linearLayout:
				boolean mobileResult = false;
				try {
					mFirewallService.setFirewallUidChainRule(appDetail.uid,
							AppUtils.NETWORK_TYPE_MOBILE,
							!appDetail.selected_mobile);
					mobileResult = true;
				} catch (NetRemoteException e) {
					mobileResult = false;
					e.printStackTrace();
				}
				if (!mobileResult) {
					Toast.makeText(OneDayAppsStatsActivity.this,
							R.string.toast_set_failed, 1000).show();
					return;
				}
				ImageView mobileNetState = (ImageView) view
						.findViewById(R.id.mobile_net_state);
				appDetail.selected_mobile = !appDetail.selected_mobile;
				if (appDetail.selected_mobile) {
					mobileNetState
							.setImageResource(R.drawable.firewall_mobile_selected);
					mFirewallDatabaseHelper
							.insertDenyUidWithMobile(appDetail.uid);
				} else {
					mobileNetState
							.setImageResource(R.drawable.firewall_mobile_unselected);
					mFirewallDatabaseHelper
							.deleteDenyUidWithMobile(appDetail.uid);
				}
				Toast.makeText(OneDayAppsStatsActivity.this,
						R.string.toast_set_success, 1000).show();
				break;

			case R.id.wifi_linearLayout:
				boolean wifiResult = false;
				try {
					mFirewallService.setFirewallUidChainRule(appDetail.uid,
							AppUtils.NETWORK_TYPE_WIFI,
							!appDetail.selected_wifi);
					wifiResult = true;
				} catch (NetRemoteException e) {
					wifiResult = false;
					e.printStackTrace();
				}
				if (!wifiResult) {
					Toast.makeText(OneDayAppsStatsActivity.this,
							R.string.toast_set_failed, 1000).show();
					return;
				}
				ImageView wifiNetState = (ImageView) view
						.findViewById(R.id.wifi_net_state);
				appDetail.selected_wifi = !appDetail.selected_wifi;
				if (appDetail.selected_wifi) {
					wifiNetState
							.setImageResource(R.drawable.firewall_wifi_selected);
					mFirewallDatabaseHelper
							.insertDenyUidWithWifi(appDetail.uid);
				} else {
					wifiNetState
							.setImageResource(R.drawable.firewall_wifi_unselected);
					mFirewallDatabaseHelper
							.deleteDenyUidWithWifi(appDetail.uid);
				}
				Toast.makeText(OneDayAppsStatsActivity.this,
						R.string.toast_set_success, 1000).show();
				break;
			}

		}

	};

	class ViewHolder {
		ImageView icon;
		TextView appName;
		TextView mobileDatausage;
		TextView wifiDatausage;
		View mobileLayout;
		View wifiLayout;
	}

}
