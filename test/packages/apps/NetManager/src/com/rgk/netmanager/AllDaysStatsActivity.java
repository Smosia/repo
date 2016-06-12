package com.rgk.netmanager;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;

public class AllDaysStatsActivity extends Activity {

	public static final String EXTRA_DAYS_STATS = "days_stats";
        public static final String EXTRA_SIM_STATS = "sim_stats";
        private static final int KEY_FORMAT_DATE = 0x40000001;

        private TextView mTitleView;
	private ListView mListView;
	private DaysListAdapter mAdapter;

	private ImageView mBackBtn;

	private ArrayList<DayStatsInfo> mDayStatsInfos = null;

        private int mSlot = -1;

	private View.OnClickListener backBtnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View arg0) {
			onBackPressed();
		}
		
	};

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> listView, View itemView,
				int position, long id) {
			DayStatsInfo info = (DayStatsInfo) listView
					.getItemAtPosition(position);
			Intent intent = new Intent(AllDaysStatsActivity.this,
					OneDayAppsStatsActivity.class);
			intent.putExtra(OneDayAppsStatsActivity.EXTRA_DATE, info.date);
			intent.putExtra(OneDayAppsStatsActivity.EXTRA_START, info.start);
			intent.putExtra(OneDayAppsStatsActivity.EXTRA_END, info.end);
			intent.putExtra(OneDayAppsStatsActivity.EXTRA_FORMAT_DATE,
					String.valueOf(itemView.getTag(KEY_FORMAT_DATE)));
                        intent.putExtra(OneDayAppsStatsActivity.EXTRA_TYPE, mSlot);
			startActivity(intent);
		}
	};

	public AllDaysStatsActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.days_detail_stats_layout);

                initViews();

		/*
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
					| ActionBar.DISPLAY_SHOW_TITLE
					| ActionBar.DISPLAY_SHOW_HOME);
		}
		*/
	}

	@SuppressWarnings("unchecked")
	private void processIntent(Intent intent) {
		mDayStatsInfos = (ArrayList<DayStatsInfo>) intent
				.getSerializableExtra(EXTRA_DAYS_STATS);
                if (mDayStatsInfos == null) {
                    mSlot = intent.getIntExtra(EXTRA_SIM_STATS, -1);
                    String title = getString(R.string.day_stats_label);
                    if (mSlot == Constant.FILTER_SIM1) {
                        title = "SIM1 " + title;
                    } else if (mSlot == Constant.FILTER_SIM2) {
                        title = "SIM2 " + title;
                    } else {
                        title = "WIFI " + title;
                    }
                    mTitleView.setText(title);
                    Bundle extras = new Bundle();
		    extras.putInt("slot_id", mSlot);
                    getLoaderManager().initLoader(Constant.LOADER_ID_FOR_SIM_MONTH_STATS, 
                            extras, mCallbacks);
                } else {
                    mSlot = Constant.FILTER_ALL;
                    mAdapter.changeData(mDayStatsInfos);
                }

	}

	private void initViews() {
		mBackBtn = (ImageView) this.findViewById(R.id.custom_action_bar_back_btn);
		mBackBtn.setOnClickListener(backBtnClickListener);
                mTitleView = (TextView) this.findViewById(R.id.days_stats_title_label);
		mListView = (ListView) this.findViewById(R.id.days_list_id);
		mListView.setOnItemClickListener(mOnItemClickListener);
		mAdapter = new DaysListAdapter(this);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			this.onBackPressed();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
                processIntent(getIntent());
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

	class DaysListAdapter extends BaseAdapter {
		ArrayList<DayStatsInfo> datas = null;
		Context mContext;

		public DaysListAdapter(Context context) {
			mContext = context;
			datas = new ArrayList<DayStatsInfo>();
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View itemView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			ViewHolder viewHolder = null;
			if (itemView == null) {
				viewHolder = new ViewHolder();
				itemView = inflater
						.inflate(R.layout.days_stats_list_item, null);
				viewHolder.dateLabel = (TextView) itemView
						.findViewById(R.id.days_item_date_label);
				viewHolder.totalStats = (TextView) itemView
						.findViewById(R.id.days_item_date_total_label);
				itemView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) itemView.getTag();
			}
			String dateStr = TimeUtils.formatDate(datas.get(position).date);
			viewHolder.dateLabel.setText(dateStr);
			viewHolder.totalStats
					.setText(StringUtils.formatBytes(datas.get(position).stats));

			itemView.setTag(KEY_FORMAT_DATE, dateStr);

			return itemView;
		}

		public void changeData(ArrayList<DayStatsInfo> datas) {
			this.datas = datas;
			this.notifyDataSetChanged();
		}

	}

	class ViewHolder {
		TextView dateLabel;
		TextView totalStats;
	}

        private LoaderCallbacks<ArrayList<DayStatsInfo>> mCallbacks = new LoaderCallbacks<ArrayList<DayStatsInfo>>() {

		@Override
		public Loader<ArrayList<DayStatsInfo>> onCreateLoader(int arg0, Bundle extras) {
                        DaysStatsLoader loader = new DaysStatsLoader(AllDaysStatsActivity.this);
                        loader.setSimSlotId(extras.getInt("slot_id"));
			return loader;
		}

		@Override
		public void onLoadFinished(Loader<ArrayList<DayStatsInfo>> arg0,
				ArrayList<DayStatsInfo> arg1) {
			mDayStatsInfos = arg1;
                        if (mDayStatsInfos != null) {
			    mAdapter.changeData(mDayStatsInfos);
		        }
		}

		@Override
		public void onLoaderReset(Loader<ArrayList<DayStatsInfo>> arg0) {
			
		}};

        public static class DaysStatsLoader extends AsyncTaskLoader<ArrayList<DayStatsInfo>> {
                private int mSlotId = -1;

		public DaysStatsLoader(Context context) {
			super(context);
		}

                public void setSimSlotId(int slotId) {
			mSlotId = slotId;
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

		@Override
		public ArrayList<DayStatsInfo> loadInBackground() {
			try {
                            if (mSlotId == Constant.FILTER_WIFI) {
                                return AppUtils.getDaysWifiStatsBeforeCurrentDay(getContext());
                            } else {
				return AppUtils.getDaysStatsBeforeCurrentDay(getContext(), mSlotId);
                            }
			} catch (NetRemoteException e) {
				e.printStackTrace();
				return null;
			}
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

}
