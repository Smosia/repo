package com.gweather.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.gweather.app.WeatherInfo.Forecast;
import com.gweather.utils.WeatherDataUtil;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {

	private WeatherInfo mWeatherInfo = new WeatherInfo();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("guocl", "onStartCommand, intent = " + intent);

		if (intent == null) {
			return START_REDELIVER_INTENT;
		}

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(getApplicationContext());
		RemoteViews views = new RemoteViews(this.getPackageName(),
				R.layout.widget_layout);

		int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
		if (intent.getDataString() != null) {
			widgetId = Integer.parseInt(intent.getDataString());
		}

		String intentAction = intent.getAction();
		Log.v("guocl", "widgetId = " + widgetId + ", intentAction = "
				+ intentAction);

		if (AppWidgetManager.INVALID_APPWIDGET_ID == widgetId) {
			if (WeatherWidget.ACTION_INIT.equals(intentAction)) {
				Log.v("guocl", "ACTION_INIT ?");
				setWeatherFromBD();
				if (mWeatherInfo.getForecasts().size() == MainActivity.FORECAST_DAY) {
					updateUI();
				}
			} else if (WeatherWidget.ACTION_UPDATE.equals(intentAction)) {
				Log.v("guocl", "ACTION_UPDATE ?");
				setWeatherFromBD();
				if (mWeatherInfo.getForecasts().size() == MainActivity.FORECAST_DAY) {
					updateUI();
				} else if ("".equals(WeatherDataUtil.getInstance()
						.getDefaultCityWoeid(UpdateWidgetService.this))) {
					setDefaultInfo();
				}
			} else {
				Log.w("guocl", "Action NOT match, " + intentAction);
			}
		} else {
			Log.v("guocl", "VALID_APPWIDGET_ID");
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void setWeatherFromBD() {
		Log.d("guocl", "UpdateWidgetService - setWeatherFromBD");
		String woeid = WeatherDataUtil.getInstance().getDefaultCityWoeid(
				UpdateWidgetService.this);
		WeatherInfo.Forecast forecast = null;
		mWeatherInfo.getForecasts().clear();

		ContentResolver mContentResolver = getContentResolver();
		Uri uri = Uri.parse("content://com.gweather.app.weather/gweather");
		Cursor cursor = mContentResolver.query(uri, null, "woeid=?",
				new String[] { woeid }, WeatherProvider.INDEX);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int cursorIndex = cursor.getColumnIndex(WeatherProvider.INDEX);
				int index = cursor.getInt(cursorIndex);
				Log.d("guocl", "index=" + index);

				if (WeatherProvider.CONDITION_INDEX == index) {// 当前的情况
					mWeatherInfo.setWoeid(woeid);

					mWeatherInfo.getCondition().setIndex(index);
					cursorIndex = cursor.getColumnIndex(WeatherProvider.NAME);
					mWeatherInfo.setName(cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.CODE);
					mWeatherInfo.getCondition().setCode(
							cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.DATE);
					mWeatherInfo.getCondition().setDate(
							cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.TEMP);
					mWeatherInfo.getCondition().setTemp(
							cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.TEXT);
					mWeatherInfo.getCondition().setText(
							cursor.getString(cursorIndex));
					cursorIndex = cursor
							.getColumnIndex(WeatherProvider.UPDATE_TIME);
					mWeatherInfo.setUpdateTime(cursor.getLong(cursorIndex));
				} else {// 预报
					forecast = mWeatherInfo.new Forecast();

					forecast.setIndex(index);
					cursorIndex = cursor.getColumnIndex(WeatherProvider.CODE);
					forecast.setCode(cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.DATE);
					forecast.setDate(cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.TEXT);
					forecast.setText(cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.DAY);
					forecast.setDay(cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.HIGH);
					forecast.setHigh(cursor.getString(cursorIndex));
					cursorIndex = cursor.getColumnIndex(WeatherProvider.LOW);
					forecast.setLow(cursor.getString(cursorIndex));

					mWeatherInfo.getForecasts().add(forecast);
				}

			}
			cursor.close();
		}
	}

	private void updateUI() {
		Log.d("guocl", "UpdateWidgetService - updateUI");
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(getApplicationContext());
		RemoteViews views = new RemoteViews(this.getPackageName(),
				R.layout.widget_layout);

		String name = mWeatherInfo.getName();
		views.setTextViewText(R.id.widget_weathercity, name);
		views.setTextViewText(R.id.widget_weathertemperature, mWeatherInfo
				.getForecasts().get(0).getLow()
				+ "/" + mWeatherInfo.getForecasts().get(0).getHigh());
		views.setTextViewText(R.id.widget_weathercondition, mWeatherInfo
				.getCondition().getText());

		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date());
		int h = Integer.parseInt(hour);
		boolean is_night = false;
		if (h < 7 || h > 18) {
			is_night = true;
		}
		int code = Integer.parseInt(mWeatherInfo.getCondition().getCode());
		int resId;
		boolean isnight = WeatherDataUtil.getInstance().isNight();
		resId = WeatherDataUtil.getInstance().getWeatherImageResourceByCode(
				code, isnight, true);
		if (WeatherDataUtil.INVALID_WEAHTER_IMG_RESOURCE == resId) {
			resId = WeatherDataUtil.getInstance()
					.getWeatherImageResourceByText(
							mWeatherInfo.getCondition().getText(), isnight,
							true);
		}
		views.setImageViewResource(R.id.widget_img, resId);

		int[] appWidgetIds = appWidgetManager
				.getAppWidgetIds(new ComponentName(getApplicationContext(),
						WeatherWidget.class));
		for (int i = 0; i < appWidgetIds.length; i++) {
			appWidgetManager.partiallyUpdateAppWidget(appWidgetIds[i], views);
		}
	}

	private void setDefaultInfo() {
		Log.d("guocl", "UpdateWidgetService - setUnknowInfo");
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(getApplicationContext());
		RemoteViews views = new RemoteViews(this.getPackageName(),
				R.layout.widget_layout);

		String defaultData = getResources().getString(
				R.string.weather_data_default);
		views.setTextViewText(R.id.widget_weathercity, defaultData);
		views.setTextViewText(R.id.widget_weathertemperature, defaultData);
		views.setTextViewText(R.id.widget_weathercondition, defaultData);

		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date());
		int h = Integer.parseInt(hour);
		boolean is_night = false;
		if (h < 7 || h > 18) {
			is_night = true;
		}

		int resId;
		boolean isnight = WeatherDataUtil.getInstance().isNight();
		resId = WeatherDataUtil.getInstance().getWeatherImageResourceByText(
				defaultData, isnight, true);
		views.setImageViewResource(R.id.widget_img, resId);

		int[] appWidgetIds = appWidgetManager
				.getAppWidgetIds(new ComponentName(getApplicationContext(),
						WeatherWidget.class));
		for (int i = 0; i < appWidgetIds.length; i++) {
			appWidgetManager.partiallyUpdateAppWidget(appWidgetIds[i], views);
		}
	}
}
