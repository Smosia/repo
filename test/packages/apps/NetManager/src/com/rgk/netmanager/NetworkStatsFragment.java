package com.rgk.netmanager;

import java.util.ArrayList;
import java.util.Calendar;

import com.rgk.netmanager.widget.Histogram;
import com.rgk.netmanager.widget.HistogramItem;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
//add DWLEBM-306 songqingming 20160407 (start)
import android.os.Handler;
import android.os.Message;
//add DWLEBM-306 songqingming 20160407 (end)

public class NetworkStatsFragment extends Fragment implements View.OnClickListener{
	
	private ArrayList<DayStatsInfo> mDayStatsInfos = null;

        private ListView mListView;
	private FilterAdapter mAdapter;
        private ArrayList<Integer> mDatas;

        private StatsInfo mStatsInfo;

	private TextView yearAndMonth;

	private int mYear;
	private int mMonth;
	private int mDay;

        private SubInfoUtils mSubInfoUtils;

	//add DWLEBM-306 songqingming 20160407 (start)
	private static final int MSG_LOADER_TODAY_STATS = 1;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_LOADER_TODAY_STATS:
				Log.d(NetApplication.TAG, "NetworkStatsFragment handleMessage():MSG_LOADER_TODAY_STATS");
				getLoaderManager().restartLoader(Constant.LOADER_ID_FOR_TOTAL_STATS, 
                            		null, mTotalStatsCallbacks);
				break;
			}
		}
    	
	};
	//add DWLEBM-306 songqingming 20160407 (end)

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

                @Override
		public void onItemClick(AdapterView<?> listView, View itemView, int position,
				long id) {
                        if (mStatsInfo != null && mDatas != null) {
                            if (mDatas.get(position) == Constant.FILTER_ALL) {
                                if (mStatsInfo.sim1Total+mStatsInfo.sim2Total+mStatsInfo.wifiTotal == 0) {
                                    return;
                                }
                            } else if (mDatas.get(position) == Constant.FILTER_SIM1) {
                                if (mStatsInfo.sim1Total == 0) {
                                    return;
                                }
                            } else if (mDatas.get(position) == Constant.FILTER_SIM2) {
                                if (mStatsInfo.sim2Total == 0) {
                                    return;
                                }
                            } else if (mDatas.get(position) == Constant.FILTER_WIFI) {
                                if (mStatsInfo.wifiTotal == 0) {
                                    return;
                                }
                            }
                        }
			Intent intent = new Intent(NetworkStatsFragment.this.getActivity(), AllDaysStatsActivity.class);
			if (position == 0) {
				intent.putExtra(AllDaysStatsActivity.EXTRA_DAYS_STATS, mDayStatsInfos);
			} else {
				intent.putExtra(AllDaysStatsActivity.EXTRA_SIM_STATS, mDatas.get(position));
			}
			startActivity(intent);
			
		}
        };
	
	private Histogram mHistogram;
	
	private LoaderCallbacks<ArrayList<DayStatsInfo>> mCallbacks = new LoaderCallbacks<ArrayList<DayStatsInfo>>() {

		@Override
		public Loader<ArrayList<DayStatsInfo>> onCreateLoader(int arg0, Bundle arg1) {
			return new DaysStatsLoader(NetworkStatsFragment.this.getActivity());
		}

		@Override
		public void onLoadFinished(Loader<ArrayList<DayStatsInfo>> arg0,
				ArrayList<DayStatsInfo> arg1) {
			mDayStatsInfos = arg1;
                        updateHistogram(arg1);

			//add DWLEBM-306 songqingming 20160407 (start)
			mHandler.sendEmptyMessage(MSG_LOADER_TODAY_STATS);
			//add DWLEBM-306 songqingming 20160407 (end)
		}

		@Override
		public void onLoaderReset(Loader<ArrayList<DayStatsInfo>> arg0) {
			
		}};

        private LoaderCallbacks<StatsInfo> mTotalStatsCallbacks = new LoaderCallbacks<StatsInfo>() {

		@Override
		public Loader<StatsInfo> onCreateLoader(int arg0, Bundle extras) {
			return new StatsLoader(getActivity());
		}

		@Override
		public void onLoadFinished(Loader<StatsInfo> arg0, StatsInfo arg1) {
			mStatsInfo = arg1;
                        if (mAdapter != null) {
                            mAdapter.initDatas();
                            mAdapter.notifyDataSetChanged();
                        }
		}

		@Override
		public void onLoaderReset(Loader<StatsInfo> arg0) {
			
		}};

	public NetworkStatsFragment() {
		// TODO Auto-generated constructor stub
	}
	
	private void updateHistogram(ArrayList<DayStatsInfo> infos) {
            if (infos == null || infos.size() == 0) return;

            long[] daysStats = new long[infos.get(0).date];
            int size = infos.size();
            for (int i = 0; i < daysStats.length; i++) {
                boolean find = false;
                for (int j = 0; j < size; j++) {
                    if (i == infos.get(j).date-1) {
                        find = true;
                        daysStats[i] = infos.get(j).stats;
                        break;
                    }
                }
                if (!find) {
                    daysStats[i] = 0;
                }
            }
            if (mHistogram != null) {
                mHistogram.updateColumns(daysStats);
            }
        }

	private void initHistogram(ArrayList<DayStatsInfo> infos) {
	    if (mHistogram == null) {
		return;
            }

            if (infos == null || infos.size() == 0) {
		mHistogram.initColumns(new long[] {0});
		return;
	    }

            long[] daysStats = new long[infos.get(0).date];
            int size = infos.size();
            for (int i = 0; i < daysStats.length; i++) {
                boolean find = false;
                for (int j = 0; j < size; j++) {
                    if (i == infos.get(j).date-1) {
                        find = true;
                        daysStats[i] = infos.get(j).stats;
                        break;
                    }
                }
                if (!find) {
                    daysStats[i] = 0;
                }
            }

	    mHistogram.initColumns(daysStats);
        }

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
                mSubInfoUtils = SubInfoUtils.getInstance(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initTime();
		View view = inflater.inflate(R.layout.stats_layout_fragment, null);
		yearAndMonth = (TextView)view.findViewById(R.id.stats_fragment_year_month_label);
		yearAndMonth.setText(TimeUtils.getCurrentYearAndMonth());
		mHistogram = (Histogram) view.findViewById(R.id.histogram);
		mHistogram.initColumns(new long[] {0});
                mHistogram.setItemOnClickListener(this);
                mListView = (ListView)view.findViewById(R.id.filter_stats_listview);
		mAdapter = new FilterAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(mOnItemClickListener);

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
		if (checkNeedUpdateTime()) {
			Log.d(NetApplication.TAG, "NetworkStatsFragment onResume():update time===>true");
			yearAndMonth.setText(TimeUtils.getCurrentYearAndMonth());
			initHistogram(mDayStatsInfos);
			mHistogram.setItemOnClickListener(this);
		}
	}

	private void initTime() {
		Calendar c = Calendar.getInstance();
        	mYear = c.get(Calendar.YEAR);
        	mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
	}

	private boolean checkNeedUpdateTime() {
		boolean update = false;
		Calendar c = Calendar.getInstance();
        	int year = c.get(Calendar.YEAR);
        	int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (mYear != year || mMonth != month || mDay != day) {
			mYear = year;
			mMonth = month;
			mDay = day;
			update = true;
		}
		return update;
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

        public void reLoad() {
            getLoaderManager().restartLoader(Constant.LOADER_ID_FOR_MONTH_STATS, null, mCallbacks);
            //delete DWLEBM-306 songqingming 20160407 (start)
            /*
            getLoaderManager().restartLoader(Constant.LOADER_ID_FOR_TOTAL_STATS, 
                            null, mTotalStatsCallbacks);
            */
            //delete DWLEBM-306 songqingming 20160407 (end)
        }

	@Override
	public void onClick(View v) {
                HistogramItem item = (HistogramItem)v;
                int index = item.getIndex();
                long value = item.getValue();
                if (value == 0) return;
                if (mDayStatsInfos != null) {
                    for (int i = 0; i < mDayStatsInfos.size(); i++) {
                        DayStatsInfo info = mDayStatsInfos.get(i);
                        if (index == info.date-1) {
                            startOneDayStatsActivity(info);
                            return;
                        }
                    }
                }
	}

        private void startOneDayStatsActivity(DayStatsInfo info) {
            Intent intent = new Intent(this.getActivity(), OneDayAppsStatsActivity.class);
            intent.putExtra(OneDayAppsStatsActivity.EXTRA_DATE, info.date);
	    intent.putExtra(OneDayAppsStatsActivity.EXTRA_START, info.start);
	    intent.putExtra(OneDayAppsStatsActivity.EXTRA_END, info.end);
            String dateStr = TimeUtils.formatDate(info.date);
	    intent.putExtra(OneDayAppsStatsActivity.EXTRA_FORMAT_DATE, dateStr);
            intent.putExtra(OneDayAppsStatsActivity.EXTRA_TYPE, Constant.FILTER_ALL);
	    startActivity(intent);
        }
	
	private void startFirewallUI() {
		Intent intent = new Intent(this.getActivity(), MainNetActivity.class);
		intent.putExtra(MainNetActivity.TO_WHERE, MainNetActivity.TO_FIREWALL);
		startActivity(intent);
	}
	
	public static class DaysStatsLoader extends AsyncTaskLoader<ArrayList<DayStatsInfo>> {

		public DaysStatsLoader(Context context) {
			super(context);
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

		@Override
		public ArrayList<DayStatsInfo> loadInBackground() {
			try {
				return AppUtils.getDaysStatsBeforeCurrentDay(getContext());
			} catch (NetRemoteException e) {
				e.printStackTrace();
				return null;
			}
                        /** for testing
			ArrayList<DayStatsInfo> ds = new ArrayList<DayStatsInfo>();
			for(int i = 0; i < 10; i ++) {
				DayStatsInfo info = new DayStatsInfo();
				info.start = 1220303200023l;
				info.date = 15;
				info.stats = 1297823223l;
				ds.add(info);
			}
			return ds;
                        */
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

        class FilterAdapter extends BaseAdapter {
                private Context mContext;
		
		public FilterAdapter(Context context) {
                        mContext = context;
			mDatas = new ArrayList<Integer>();
                        initDatas();
		}

                public void initDatas() {
                        mDatas.clear();
                        mDatas.add(Constant.FILTER_ALL); // all stats
                        int[] slots = mSubInfoUtils.getActivatedSlotList();
                        for (int i = 0; i < slots.length; i++) {
                            mDatas.add(slots[i]);
                        }
                        mDatas.add(Constant.FILTER_WIFI);
                }

                @Override
		public int getCount() {
			return mDatas == null ? 0 : mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
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
				itemView = inflater.inflate(R.layout.stats_layout_fragment_item, null);
				viewHolder.label = (TextView)itemView.findViewById(R.id.day_stats_detail_label);
                                viewHolder.totalStats = (TextView)itemView.findViewById(R.id.current_month_total_stats);
                                viewHolder.img = (ImageView)itemView.findViewById(R.id.day_stats_detail_imgview);
				viewHolder.typeImg = (ImageView)itemView.findViewById(R.id.day_stats_detail_type_imgview);
                                itemView.setTag(viewHolder);
			} else {
                                viewHolder = (ViewHolder)itemView.getTag();
			}
                        int type = mDatas.get(position);
                        String label = null;
                        String total = null;
			int typeImgResId = 0;
                        if (type == Constant.FILTER_ALL) {
                            label = getResources().getString(R.string.day_stats_label);
			    typeImgResId = R.drawable.stats_fragment_item_all;
                            if (mStatsInfo != null) {
                                total = StringUtils.formatBytes(mStatsInfo.sim1Total
                                                               +mStatsInfo.sim2Total
                                                               +mStatsInfo.wifiTotal);
                                if (mStatsInfo.sim1Total+mStatsInfo.sim2Total+mStatsInfo.wifiTotal == 0) {
                                    viewHolder.img.setVisibility(View.GONE);
                                } else {
                                    viewHolder.img.setVisibility(View.VISIBLE);
                                }
                            }
                        } else if (type == Constant.FILTER_SIM1) {
                            label = getResources().getString(R.string.sim1_stats_label);
			    typeImgResId = R.drawable.stats_fragment_item_sim1;
                            if (mStatsInfo != null) {
                                total = StringUtils.formatBytes(mStatsInfo.sim1Total);
                                if (mStatsInfo.sim1Total == 0) {
                                    viewHolder.img.setVisibility(View.GONE);
                                } else {
                                    viewHolder.img.setVisibility(View.VISIBLE);
                                }
                            }
                            
                        } else if (type == Constant.FILTER_SIM2) {
                            label = getResources().getString(R.string.sim2_stats_label);
			    typeImgResId = R.drawable.stats_fragment_item_sim2;
                            if (mStatsInfo != null) {
                                total = StringUtils.formatBytes(mStatsInfo.sim2Total);
                                if (mStatsInfo.sim2Total == 0) {
                                    viewHolder.img.setVisibility(View.GONE);
                                } else {
                                    viewHolder.img.setVisibility(View.VISIBLE);
                                }
                            }
                        } else if (type == Constant.FILTER_WIFI) {
                            label = getResources().getString(R.string.wifi_stats_label);
			    typeImgResId = R.drawable.stats_fragment_item_wifi;
                            if (mStatsInfo != null) {
                                total = StringUtils.formatBytes(mStatsInfo.wifiTotal);
                                if (mStatsInfo.wifiTotal == 0) {
                                    viewHolder.img.setVisibility(View.GONE);
                                } else {
                                    viewHolder.img.setVisibility(View.VISIBLE);
                                }
                            }
                        }
			viewHolder.typeImg.setImageResource(typeImgResId);
			viewHolder.label.setText(label);
                        viewHolder.totalStats.setText(total);
			
			return itemView;
		}

                class ViewHolder {
			TextView label;
                        TextView totalStats;
                        ImageView img;
			ImageView typeImg;
		}
		
	}

        static class StatsInfo {
            public long sim1Total;
            public long sim2Total;
            public long wifiTotal;
        }

}
