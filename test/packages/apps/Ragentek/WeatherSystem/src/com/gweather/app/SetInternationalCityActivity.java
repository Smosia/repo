package com.gweather.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.gweather.utils.CityNameXMLParser;
import com.gweather.utils.WeatherDataUtil;
import com.gweather.utils.WeatherXMLParser;
import com.gweather.utils.WebAccessTools;
import com.gweather.app.R;
import com.gweather.app.MainActivity.QueryWeatherTask;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SetInternationalCityActivity extends Activity {

	private ArrayList<CityInfo> mCityInfos = new ArrayList<CityInfo>();

	private ArrayAdapter<String> cityInfoAdapter;

	private QueryCityTask mQueryCityTask;

	private WeatherInfo mWeatherInfo = new WeatherInfo();
	private CityInfo mCityInfo;

	private EditText cityName;
	private ImageButton searchCity;
	private ListView cityList;
	private View loadProgressView;

	class QueryCityTask extends AsyncTask<String, Void, String[]> {
		String[] cityInfosStrings = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String url = "http://query.yahooapis.com/v1/public/yql?q=select+*+from+geo.places+where+text='"
					+ params[0] + "*'+and+lang='en-US'";
			String content = new WebAccessTools(getApplicationContext())
					.getWebContent(url);
			// Log.d("guocl", "City_content="+content);
			parseCity(content);

			if (!mCityInfos.isEmpty()) {
				final int count = mCityInfos.size();
				cityInfosStrings = new String[count];
				for (int i = 0; i < count; i++) {
					cityInfosStrings[i] = mCityInfos.get(i).toString();
					Log.d("guocl", "[" + i + "]CityInfo="
							+ mCityInfos.get(i).getWoeid() + ": "
							+ mCityInfos.get(i).toString());
				}
			}
			return cityInfosStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			Log.d("guocl", "QueryCityTask - onPostExecute");
			if (null != result) {
				cityInfoAdapter = new ArrayAdapter<String>(
						SetInternationalCityActivity.this,
						R.layout.simple_list_item, result);
				cityList.setAdapter(cityInfoAdapter);
				cityList.setVisibility(View.VISIBLE);

			} else {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.city_not_found),
						Toast.LENGTH_SHORT).show();
			}

			showLoadingProgress(false);
		}

	}

	private void parseCity(String content) {
		if (null == content || content.isEmpty()) {
			Log.w("guocl", "parseCity content is Empty");
			// Toast.makeText(getApplicationContext(),
			// getResources().getString(R.string.city_not_found),
			// Toast.LENGTH_SHORT).show();
			return;
		}

		mCityInfos.clear();

		SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser mSAXParser = mSAXParserFactory.newSAXParser();
			XMLReader mXmlReader = mSAXParser.getXMLReader();
			CityNameXMLParser handler = new CityNameXMLParser(mCityInfos);
			mXmlReader.setContentHandler(handler);
			StringReader stringReader = new StringReader(content);
			InputSource inputSource = new InputSource(stringReader);
			mXmlReader.parse(inputSource);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	class QueryWeatherTask extends AsyncTask<String, Void, String[]> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String url = "http://query.yahooapis.com/v1/public/yql?q=select+*+from+weather.forecast+where+woeid="
					+ params[0] + "+and+u='c'";
			String content = new WebAccessTools(getApplicationContext())
					.getWebContent(url);
			// Log.d("guocl", "Weather_content="+content);
			parseWeather(content, params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			super.onPostExecute(result);
			saveWeatherToDB();
			

			Intent intent = getIntent();
			if (intent.getBooleanExtra("from_manager", false)) {
				intent.putExtra("refresh", true);
				SetInternationalCityActivity.this.setResult(
						CityMangerActivity.REQUEST_CODE_CITY_ADD, intent);
			} else {
				intent.putExtra("updateWeather", true);
				SetInternationalCityActivity.this.setResult(
						MainActivity.REQUEST_CODE_CITY_SETTING, intent);
			}
			SetInternationalCityActivity.this.finish();
			
			showLoadingProgress(false);
		}
	};

	private void parseWeather(String content, String woeid) {
		if (content == null || content.isEmpty()) {
			Log.w("guocl", "parseWeather content is Empty");
			return;
		}

		mWeatherInfo.getForecasts().clear();

		SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser mSAXParser = mSAXParserFactory.newSAXParser();
			XMLReader mXmlReader = mSAXParser.getXMLReader();
			WeatherXMLParser handler = new WeatherXMLParser(mWeatherInfo, woeid);
			mXmlReader.setContentHandler(handler);
			StringReader stringReader = new StringReader(content);
			InputSource inputSource = new InputSource(stringReader);
			mXmlReader.parse(inputSource);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setWeatherFromInternet(String woeid) {
		Log.d("guocl", "SetCity - setWeatherSituation, woeid:" + woeid);
		showLoadingProgress(true);
		new QueryWeatherTask().execute(woeid);
	}

	private void saveWeatherToDB() {
		Log.d("guocl", "saveWeather");
		boolean hasWoeid = false;
		String woeid = mWeatherInfo.getWoeid();

		ContentResolver mContentResolver = getContentResolver();
		Uri uri = Uri.parse("content://com.gweather.app.weather/gweather");
		Cursor cursor = mContentResolver.query(uri, null, "woeid=?",
				new String[] { woeid }, null);
		if (cursor != null) {
			int count = cursor.getCount();
			if (count > 0) {
				hasWoeid = true;
			}
			cursor.close();
		}

		ContentValues values;
		Log.d("guocl", "hasWoeid = " + hasWoeid);
		if (hasWoeid) {
			values = new ContentValues();
			values.put(WeatherProvider.INDEX, WeatherProvider.CONDITION_INDEX);
			values.put(WeatherProvider.WOEID, woeid);
			values.put(WeatherProvider.NAME, mCityInfo.getName());
			values.put(WeatherProvider.CODE, mWeatherInfo.getCondition()
					.getCode());
			values.put(WeatherProvider.DATE, mWeatherInfo.getCondition()
					.getDate());
			values.put(WeatherProvider.TEMP, mWeatherInfo.getCondition()
					.getTemp());
			values.put(WeatherProvider.TEXT, mWeatherInfo.getCondition()
					.getText());
			values.put(WeatherProvider.UPDATE_TIME,
					mWeatherInfo.getUpdateTime());
			mContentResolver.update(
					uri,
					values,
					WeatherProvider.INDEX + " = ? AND " + WeatherProvider.WOEID
							+ " = ?",
					new String[] {
							Integer.toString(WeatherProvider.CONDITION_INDEX),
							woeid });

			for (int i = 0; i < MainActivity.FORECAST_DAY; i++) {
				values = new ContentValues();
				values.put(WeatherProvider.INDEX, i);
				values.put(WeatherProvider.WOEID, woeid);
				values.put(WeatherProvider.CODE, mWeatherInfo.getForecasts()
						.get(i).getCode());
				values.put(WeatherProvider.DATE, mWeatherInfo.getForecasts()
						.get(i).getDate());
				values.put(WeatherProvider.DAY, mWeatherInfo.getForecasts()
						.get(i).getDay());
				values.put(WeatherProvider.HIGH, mWeatherInfo.getForecasts()
						.get(i).getHigh());
				values.put(WeatherProvider.LOW, mWeatherInfo.getForecasts()
						.get(i).getLow());
				values.put(WeatherProvider.TEXT, mWeatherInfo.getForecasts()
						.get(i).getText());
				mContentResolver.update(uri, values, WeatherProvider.INDEX
						+ " = ? AND " + WeatherProvider.WOEID + " = ?",
						new String[] { Integer.toString(i), woeid });
			}
		} else {
			values = new ContentValues();
			values.put(WeatherProvider.INDEX, WeatherProvider.CONDITION_INDEX);
			values.put(WeatherProvider.WOEID, woeid);
			values.put(WeatherProvider.NAME, mCityInfo.getName());
			values.put(WeatherProvider.CODE, mWeatherInfo.getCondition()
					.getCode());
			values.put(WeatherProvider.DATE, mWeatherInfo.getCondition()
					.getDate());
			values.put(WeatherProvider.TEMP, mWeatherInfo.getCondition()
					.getTemp());
			values.put(WeatherProvider.TEXT, mWeatherInfo.getCondition()
					.getText());
			values.put(WeatherProvider.UPDATE_TIME,
					mWeatherInfo.getUpdateTime());
			mContentResolver.insert(uri, values);

			for (int i = 0; i < MainActivity.FORECAST_DAY; i++) {
				values = new ContentValues();
				values.put(WeatherProvider.INDEX, i);
				values.put(WeatherProvider.WOEID, woeid);
				values.put(WeatherProvider.CODE, mWeatherInfo.getForecasts()
						.get(i).getCode());
				values.put(WeatherProvider.DATE, mWeatherInfo.getForecasts()
						.get(i).getDate());
				values.put(WeatherProvider.DAY, mWeatherInfo.getForecasts()
						.get(i).getDay());
				values.put(WeatherProvider.HIGH, mWeatherInfo.getForecasts()
						.get(i).getHigh());
				values.put(WeatherProvider.LOW, mWeatherInfo.getForecasts()
						.get(i).getLow());
				values.put(WeatherProvider.TEXT, mWeatherInfo.getForecasts()
						.get(i).getText());
				mContentResolver.insert(uri, values);
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_international_city);

		cityName = (EditText) findViewById(R.id.city_name);
		// cityName.addTextChangedListener(cityNameWatcher);
		searchCity = (ImageButton) findViewById(R.id.search_city);
		searchCity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				searchCity();
			}
		});

		cityList = (ListView) findViewById(R.id.city_list);
		cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("guocl", "position = " + position + ", id = " + id);
				int length = mCityInfos.size();
				if (position < length) {
					addCity(mCityInfos.get(position));
				}
			}
		});

		loadProgressView = findViewById(R.id.loading_progress_view);
	}

	protected void onResume() {
		super.onResume();
	};

	@Override
	protected void onDestroy() {
		cityName.addTextChangedListener(null);
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mQueryCityTask != null
				&& mQueryCityTask.getStatus() == AsyncTask.Status.RUNNING) {
			mQueryCityTask.cancel(true);
		}
	}

	private void searchCity() {
		String name = cityName.getText().toString();
		if (name.isEmpty()) {
			// 也許可以給個提示
		} else {
			showLoadingProgress(true);
			mQueryCityTask = new QueryCityTask();
			mQueryCityTask.execute(name);
		}
	}

	private void addCity(CityInfo info) {
		if (mQueryCityTask != null
				&& mQueryCityTask.getStatus() == AsyncTask.Status.RUNNING) {
			mQueryCityTask.cancel(true);
		}

		String woeid = info.getWoeid();
		android.util.Log.d("guocl", "addCity, woeid=" + woeid);
		if (getIntent().getBooleanExtra("isFirstRun", false)) {
			Log.d("guocl", "addCity - updateDefaultCityWoeid");
			WeatherDataUtil.getInstance().updateDefaultCityWoeid(
					SetInternationalCityActivity.this, woeid);
		} else if (WeatherDataUtil.getInstance()
				.getDefaultCityWoeid(SetInternationalCityActivity.this)
				.isEmpty()) {
			WeatherDataUtil.getInstance().updateDefaultCityWoeid(
					SetInternationalCityActivity.this, woeid);
		}
		mCityInfo = info;

		cityList.setVisibility(View.GONE);

		setWeatherFromInternet(woeid);
	}

	private void showLoadingProgress(boolean show) {
		Log.d("guocl", "showLoadingProgress:" + show);
		if (show) {
			loadProgressView.setVisibility(View.VISIBLE);
			searchCity.setEnabled(false);
		} else {
			loadProgressView.setVisibility(View.GONE);
			searchCity.setEnabled(true);
		}
	}

}
