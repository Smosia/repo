package com.rgk.netmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FirewallFragment extends Fragment {

	private ListView appsListView = null;
	private AppItemAdapter mAppItemAdapter = null;
	
	private FirewallDatabaseHelper mFirewallDatabaseHelper = null;
	private NetAdapter mFirewallService = null;
	
	private final LoaderCallbacks<ArrayList<AppDetail>> mAppsLoaderCallbacks = new LoaderCallbacks<
			ArrayList<AppDetail>>() {
        @Override
        public Loader<ArrayList<AppDetail>> onCreateLoader(int id, Bundle args) {
            AppsLoader loader = new AppsLoader(getActivity());
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
		mFirewallDatabaseHelper = new FirewallDatabaseHelper(getActivity());
		mFirewallService = NetAdapter.getInstance(this.getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.firewall_layout_fragment, null);
		appsListView = (ListView) view.findViewById(R.id.listview_app);

		mAppItemAdapter = new AppItemAdapter();
		appsListView.setAdapter(mAppItemAdapter);

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
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		super.onStart();
                reLoad();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	public void refleshUI() {
		if (mAppItemAdapter != null) {
			mAppItemAdapter.notifyDataSetInvalidated();
		}
		
	}
	
	public void reLoad() {
		getLoaderManager().restartLoader(Constant.LOADER_ID_FOR_FIREWALL, null, mAppsLoaderCallbacks);
	}

	class AppItemAdapter extends BaseAdapter {
		
		private ArrayList<AppDetail> appDetails = null;
		
		public AppItemAdapter() {
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
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.firewall_item_app, null);
				viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView)convertView.findViewById(R.id.app_icon);
				viewHolder.appName = (TextView)convertView.findViewById(R.id.app_name);
				viewHolder.mobileDatausage = (TextView)convertView.findViewById(R.id.mobile_app_datausage);
				viewHolder.wifiDatausage = (TextView)convertView.findViewById(R.id.wifi_app_datausage);
				viewHolder.mobileLayout = convertView.findViewById(R.id.mobile_linearLayout);
				viewHolder.mobileLayout.setOnClickListener(mOnClickListener);
				viewHolder.wifiLayout = convertView.findViewById(R.id.wifi_linearLayout);
				viewHolder.wifiLayout.setOnClickListener(mOnClickListener);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			AppDetail appDetail = appDetails.get(position);
			viewHolder.mobileLayout.setTag(appDetail);
			viewHolder.wifiLayout.setTag(appDetail);
			viewHolder.icon.setImageDrawable(appDetail.icon);
			viewHolder.appName.setText(appDetail.name);
			viewHolder.mobileDatausage.setText("mobile:"
                                +StringUtils.formatBytes(appDetail.sim1Total+appDetail.sim2Total));
			viewHolder.wifiDatausage.setText("wifi:"
                                +StringUtils.formatBytes(appDetail.wifiTotal));
			ImageView mobileNetState = (ImageView)viewHolder.mobileLayout.findViewById(R.id.mobile_net_state);
			ImageView wifiNetState = (ImageView)viewHolder.wifiLayout.findViewById(R.id.wifi_net_state);
			
			boolean enabled = SharePreferenceUtils.getEnabled(getActivity());
			if (appDetail.selected_mobile || enabled) {
				mobileNetState.setImageResource(R.drawable.firewall_mobile_selected);
			} else {
				mobileNetState.setImageResource(R.drawable.firewall_mobile_unselected);
			}
			if (appDetail.selected_wifi || enabled) {
				wifiNetState.setImageResource(R.drawable.firewall_wifi_selected);
			} else {
				wifiNetState.setImageResource(R.drawable.firewall_wifi_unselected);
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
		
		public void changeData(ArrayList<AppDetail> appDetails){
			this.appDetails = appDetails;
			this.notifyDataSetChanged();
		}

	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			Log.d("firewall", "[onClick] " + view);
			AppDetail appDetail = (AppDetail)view.getTag();
			switch (view.getId()) {
				case R.id.mobile_linearLayout:
					boolean mobileResult = false;
					try {
						mFirewallService.setFirewallUidChainRule(appDetail.uid, 
								AppUtils.NETWORK_TYPE_MOBILE, !appDetail.selected_mobile);
						mobileResult = true;
					} catch (NetRemoteException e) {
						mobileResult = false;
						e.printStackTrace();
					}
					if (!mobileResult) {
						Toast.makeText(getActivity(), R.string.toast_set_failed, 1000).show();
						return;
					}
					ImageView mobileNetState = (ImageView)view.findViewById(R.id.mobile_net_state);
					appDetail.selected_mobile = !appDetail.selected_mobile;
					if (appDetail.selected_mobile) {
						mobileNetState.setImageResource(R.drawable.firewall_mobile_selected);
						mFirewallDatabaseHelper.insertDenyUidWithMobile(appDetail.uid);
					} else {
						mobileNetState.setImageResource(R.drawable.firewall_mobile_unselected);
						mFirewallDatabaseHelper.deleteDenyUidWithMobile(appDetail.uid);
					}
					Toast.makeText(getActivity(), R.string.toast_set_success, 1000).show();
					break;
					
				case R.id.wifi_linearLayout:
					boolean wifiResult = false;
					try {
						mFirewallService.setFirewallUidChainRule(appDetail.uid, 
								AppUtils.NETWORK_TYPE_WIFI, !appDetail.selected_wifi);
						wifiResult = true;
					} catch (NetRemoteException e) {
						wifiResult = false;
						e.printStackTrace();
					}
					if (!wifiResult) {
						Toast.makeText(getActivity(), R.string.toast_set_failed, 1000).show();
						return;
					}
					ImageView wifiNetState = (ImageView)view.findViewById(R.id.wifi_net_state);
					appDetail.selected_wifi = !appDetail.selected_wifi;
					if (appDetail.selected_wifi) {
						wifiNetState.setImageResource(R.drawable.firewall_wifi_selected);
						mFirewallDatabaseHelper.insertDenyUidWithWifi(appDetail.uid);
					} else {
						wifiNetState.setImageResource(R.drawable.firewall_wifi_unselected);
						mFirewallDatabaseHelper.deleteDenyUidWithWifi(appDetail.uid);
					}
					Toast.makeText(getActivity(), R.string.toast_set_success, 1000).show();
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
