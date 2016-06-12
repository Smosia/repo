package com.gweather.app;

import java.util.ArrayList;
import java.util.List;

import com.gweather.utils.WeatherDataUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CityMangerActivity extends Activity {
	public static final int REQUEST_CODE_CITY_ADD = 1001;
	public static final int CITY_COUNT_MAX = 10;

	private ImageView addCity;
	private ListView cityList;
	private CityListAdapter mCityListAdapter;

	private List<CityListItem> items = new ArrayList<CityListItem>();

	private int deletePosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_manager);
		initUI();
		refreshCityList();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CODE_CITY_ADD:
			if (data != null && data.getBooleanExtra("refresh", false)) {
				refreshCityList();
				// 通知主界面更新
				WeatherDataUtil.getInstance().setNeedUpdateMainUI(
						CityMangerActivity.this, true);
			}
			break;

		default:
			break;
		}

	}

	private void initUI() {
		addCity = (ImageView) findViewById(R.id.add_city);
		addCity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (items.size() < CITY_COUNT_MAX) {
					Intent intent = new Intent(CityMangerActivity.this,
							SetInternationalCityActivity.class);
					intent.putExtra("from_manager", true);
					startActivityForResult(intent, REQUEST_CODE_CITY_ADD);
				} else {
					Toast.makeText(
							CityMangerActivity.this,
							getResources().getString(R.string.city_max_toast,
									CITY_COUNT_MAX), Toast.LENGTH_SHORT).show();
				}		
			}
		});

		cityList = (ListView) findViewById(R.id.city_list);
	}

	private void refreshCityList() {
		Log.d("guocl", "CityManagerActivity - refreshCityList");
		ContentResolver mContentResolver = getContentResolver();
		items.clear();
		CityListItem item;
		Uri weatherUri = Uri
				.parse("content://com.gweather.app.weather/gweather");

		Cursor cursor = mContentResolver.query(weatherUri, null, "gIndex=?",
				new String[] { Integer
						.toString(WeatherProvider.CONDITION_INDEX) }, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int cursorIndex = cursor.getColumnIndex(WeatherProvider.WOEID);
				String woeid = cursor.getString(cursorIndex);
				cursorIndex = cursor.getColumnIndex(WeatherProvider.NAME);
				String name = cursor.getString(cursorIndex);
				cursorIndex = cursor.getColumnIndex(WeatherProvider.TEMP);
				String weather = cursor.getString(cursorIndex);
				cursorIndex = cursor.getColumnIndex(WeatherProvider.CODE);
				int code = cursor.getInt(cursorIndex);
				cursorIndex = cursor.getColumnIndex(WeatherProvider.TEXT);
				String text = cursor.getString(cursorIndex);

				Log.d("guocl", "woeid=" + woeid);
				Log.d("guocl", "name=" + name);
				Log.d("guocl", "weather=" + weather);
				Log.d("guocl", "code=" + code);
				Log.d("guocl", "text=" + text);
				item = new CityListItem(woeid, name);
				item.setText(text);
				item.setWeather(weather);
				items.add(item);
			}
			cursor.close();
		}

		if (items.size() < CITY_COUNT_MAX) {
			addCity.setImageResource(R.drawable.add_city);
		} else {
			addCity.setImageResource(R.drawable.add_city_disabled);
		}

		mCityListAdapter = new CityListAdapter(CityMangerActivity.this, items);
		cityList.setAdapter(mCityListAdapter);
		cityList.setOnItemLongClickListener(longClickListener);
	}

	AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Log.d("guocl", "CityManagerActivity - onItemLongClick:" + position);
			deletePosition = position;

			String title = getResources().getString(R.string.delete_city,
					items.get(position).name);
			final AlertDialog dialog = new AlertDialog.Builder(
					CityMangerActivity.this,
					AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
					.setTitle(title)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									deleteCityFromDb(items.get(deletePosition).woeid);
									refreshCityList();
									// 通知主界面更新
									WeatherDataUtil.getInstance()
											.setNeedUpdateMainUI(
													CityMangerActivity.this,
													true);
								}
							}).setNeutralButton(android.R.string.cancel, null)
					.create();
			dialog.show();
			return true;
		}
	};

	private void deleteCityFromDb(String woeid) {
		Log.d("guocl", "CityManager - deleteCityFromDb:" + woeid);
		if (!woeid.isEmpty()) {
			ContentResolver mContentResolver = getContentResolver();

			Uri weatherUri = Uri
					.parse("content://com.gweather.app.weather/gweather");
			mContentResolver.delete(weatherUri, WeatherProvider.WOEID + "=?",
					new String[] { woeid });

			String defaultWoeid = WeatherDataUtil.getInstance()
					.getDefaultCityWoeid(CityMangerActivity.this);

			if (defaultWoeid.equals(woeid)) {
				WeatherDataUtil.getInstance().updateDefaultCityWoeid(
						CityMangerActivity.this, "");

				Cursor cursor = mContentResolver.query(weatherUri, null,
						"gIndex=?", new String[] { Integer
								.toString(WeatherProvider.CONDITION_INDEX) },
						null);
				if (cursor != null) {
					if (cursor.moveToFirst()) {
						int cursorIndex = cursor
								.getColumnIndex(WeatherProvider.WOEID);
						WeatherDataUtil.getInstance().updateDefaultCityWoeid(
								CityMangerActivity.this,
								cursor.getString(cursorIndex));
					}
					cursor.close();
				}

				// 更新控件
				startUpdateService(CityMangerActivity.this,
						WeatherWidget.ACTION_UPDATE,
						AppWidgetManager.INVALID_APPWIDGET_ID);
			}
		}
	}

	private void startUpdateService(Context context, String action, int widgetId) {
		Log.d("guocl", "CityManager - startUpdateService");
		Intent intent = new Intent(context, UpdateWidgetService.class);
		intent.setAction(action);
		intent.setData(Uri.parse(String.valueOf(widgetId)));
		context.startService(intent);
	}

	class CityListAdapter extends BaseAdapter {
		private List<CityListItem> mList;
		private LayoutInflater mInflater;

		public CityListAdapter(Context context, List<CityListItem> mList) {
			super();
			this.mList = mList;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder mHolder;

			if (convertView == null || convertView.getTag() == null) {
				// Time consuming 1 -- inflate
				convertView = mInflater.inflate(R.layout.city_list_item, null);
				mHolder = new ViewHolder();
				// Time consuming 2 -- findViewById
				mHolder.name = (TextView) convertView.findViewById(R.id.name);
				mHolder.image = (ImageView) convertView
						.findViewById(R.id.image);
				mHolder.text = (TextView) convertView.findViewById(R.id.text);
				mHolder.weather = (TextView) convertView
						.findViewById(R.id.weather);
				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}
			CityListItem bean = mList.get(position);
			mHolder.name.setText(bean.name);
			mHolder.image.setImageResource(bean.imgRes);
			mHolder.text.setText(bean.text);
			mHolder.weather.setText(bean.weather + "℃");
			return convertView;
		}

		// Google I/O
		class ViewHolder {
			public TextView name;
			public ImageView image;
			public TextView text;
			public TextView weather;
		}
	}

	class CityListItem {
		public String woeid;
		public String name;
		public int imgRes;
		public String text;
		public String weather;

		public CityListItem(String woeid, String name) {
			super();
			this.woeid = woeid;
			this.name = name;
		}

		public void setImgRes(int imgRes) {
			this.imgRes = imgRes;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
}
