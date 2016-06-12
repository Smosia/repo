package com.gweather.app;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.gweather.utils.Utils;
import com.gweather.utils.WeatherDataUtil;
import com.gweather.utils.WeatherXMLParser;
import com.gweather.utils.WebAccessTools;
import com.gweather.view.ScrollControlLayout;
import com.gweather.view.WeatherInfoMainView;
import com.gweather.app.R;
import com.gweather.app.CityMangerActivity.CityListItem;
import com.gweather.app.WeatherInfo.Forecast;

import android.R.integer;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final int FORECAST_DAY = 5;
	public static final int REQUEST_CODE_CITY_SETTING = 0;
	public static final String SETTINGS_SP = "settings_sp";
	public static final String SETTINGS_AUTO_REFRESH_ENABLE = "settings_auto_enable";
	public static final String SETTINGS_AUTO_REFRESH = "settings_auto_refresh";
	public static final String SETTINGS_WIFI_ONLY = "settings_wifi_only";
	public static final int SETTINGS_AUTO_REFRESH_6H = 6;
	public static final int SETTINGS_AUTO_REFRESH_12H = 12;
	public static final int SETTINGS_AUTO_REFRESH_24H = 24;

	public enum MenuState {
		OPEN, CLOSE;
	}

	private ScrollControlLayout weatherInfoMainContainer;
	private WeatherInfoMainView weatherInfoMainView;

	private View mainLayoutView;
	private View menuView;
	private View mainContentView;
	private ImageView refresh;
	private ImageView setting;
	private TextView refreshTimeText;
	private View loadProgressView;

	private View menuAutoRefresh;
	private ImageView menuCheckAutoRefresh;
	private View menuAuto6;
	private ImageView menuCheckAuto6h;
	private View menuAuto12;
	private ImageView menuCheckAuto12h;
	private View menuAuto24;
	private ImageView menuCheckAuto24h;
	private View menuWifiOnly;
	private ImageView menuCheckWifiOnly;
	private View menuSetCity;

	private List<WeatherInfo> mWeatherInfoList = new ArrayList<WeatherInfo>();
	private int updateCityCount = 0;
	private int updateFinishedCityCount = 0;

	private MenuState menuState = MenuState.CLOSE;

	class QueryWeatherTask extends AsyncTask<Integer, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Integer... index) {
			String woeid = mWeatherInfoList.get(index[0].intValue()).getWoeid();
			String url = "http://query.yahooapis.com/v1/public/yql?q=select+*+from+weather.forecast+where+woeid="
					+ woeid + "+and+u='c'";
			String content = new WebAccessTools(getApplicationContext())
					.getWebContent(url);
			// Log.d("guocl", "Weather_content="+content);
			parseWeather(content, index[0].intValue());
			return index[0];
		}

		@Override
		protected void onPostExecute(Integer index) {

			super.onPostExecute(index);
			saveWeatherToDB(index.intValue());

			updateFinishedCityCount++;
			if (updateFinishedCityCount == updateCityCount) {
				updateUI();
				// 更新控件
				startUpdateService(MainActivity.this,
						WeatherWidget.ACTION_UPDATE,
						AppWidgetManager.INVALID_APPWIDGET_ID);
				showLoadingProgress(false);
			}
		}
	};

	private void parseWeather(String content, int index) {
		if (content == null || content.isEmpty()) {
			Log.w("guocl", "parseWeather content is Empty");
			return;
		}

		mWeatherInfoList.get(index).getForecasts().clear();

		SAXParserFactory mSAXParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser mSAXParser = mSAXParserFactory.newSAXParser();
			XMLReader mXmlReader = mSAXParser.getXMLReader();
			WeatherXMLParser handler = new WeatherXMLParser(
					mWeatherInfoList.get(index), mWeatherInfoList.get(index)
							.getWoeid());
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置窗口特征,为不显示标题
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		Log.d("guocl", "MainActivity - onCreate");
		initUI();

		// 判断程序是否是第一次运行
		boolean isFirstRun = isFirstRun();

		Log.d("guocl", "MainActivity - isFirstRun=" + isFirstRun);
		if (isFirstRun) {
			// 第一次跳转到设置城市的Activity
			Intent intent = new Intent(MainActivity.this,
					SetInternationalCityActivity.class);
			intent.putExtra("isFirstRun", isFirstRun);
			startActivityForResult(intent, REQUEST_CODE_CITY_SETTING);
		} else {
			setWeatherFromBD();
		}
	}

	private boolean isFirstRun() {
		ContentResolver mContentResolver = getContentResolver();
		Uri uri = Uri.parse("content://com.gweather.app.weather/gweather");
		Cursor cursor = mContentResolver.query(uri, null, null, null,
				WeatherProvider.INDEX);
		if (cursor != null) {
			if (cursor.getCount() == 0) {
				return true;
			}
			cursor.close();
		}
		return false;
	}

	private void initUI() {
		mainLayoutView = findViewById(R.id.main_layout);
		mainContentView = findViewById(R.id.main_content);
		menuView = findViewById(R.id.menu);
		weatherInfoMainContainer = (ScrollControlLayout) findViewById(R.id.main_container);
		weatherInfoMainContainer
				.setOnScreenChangedListener(new ScrollControlLayout.OnScreenChangedListener() {
					@Override
					public void screenChange(int curScreen) {
						Log.d("guocl", "screenChange = " + curScreen);
						for (int i = 0; i < mWeatherInfoList.size(); i++) {
							Log.d("guocl", "[" + i + "] "
									+ mWeatherInfoList.get(i).getWoeid());
						}

						WeatherDataUtil.getInstance().updateDefaultCityWoeid(
								MainActivity.this,
								mWeatherInfoList.get(curScreen).getWoeid());
						// 更新控件
						startUpdateService(MainActivity.this,
								WeatherWidget.ACTION_UPDATE,
								AppWidgetManager.INVALID_APPWIDGET_ID);
					}
				});
		refresh = (ImageView) findViewById(R.id.refresh);
		refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences sp = getSharedPreferences(SETTINGS_SP,
						Context.MODE_PRIVATE);
				if (sp.getBoolean(SETTINGS_WIFI_ONLY, getResources().getBoolean(R.bool.config_wifi_only_enable))) {
					if (Utils.isNetworkTypeWifi(MainActivity.this)) {
						setWeatherFromInternet();
					} else {
						Log.d("guocl", "SETTINGS_WIFI_ONLY, network type NOT WIFI.");
					}
				} else {
					setWeatherFromInternet();
				}
			}
		});
		setting = (ImageView) findViewById(R.id.settings);
		setting.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//Menu功能暂时关闭
				Intent intent = new Intent(MainActivity.this,
						CityMangerActivity.class);
				startActivity(intent);
				//showMenu(true);
			}
		});

		refreshTimeText = (TextView) findViewById(R.id.latest_refresh_time);
		loadProgressView = findViewById(R.id.loading_progress_view);

		initMenu();
	}

	private void initMenu() {
		menuAutoRefresh = findViewById(R.id.menu_auto_refresh);
		menuAutoRefresh.setOnClickListener(menuItemOnClickListener);
		menuCheckAutoRefresh = (ImageView) findViewById(R.id.menu_check_auto_refresh);

		menuAuto6 = findViewById(R.id.menu_auto_6h);
		menuAuto6.setOnClickListener(menuItemOnClickListener);
		menuCheckAuto6h = (ImageView) findViewById(R.id.menu_check_auto_6h);

		menuAuto12 = findViewById(R.id.menu_auto_12h);
		menuAuto12.setOnClickListener(menuItemOnClickListener);
		menuCheckAuto12h = (ImageView) findViewById(R.id.menu_check_auto_12h);

		menuAuto24 = findViewById(R.id.menu_auto_24h);
		menuAuto24.setOnClickListener(menuItemOnClickListener);
		menuCheckAuto24h = (ImageView) findViewById(R.id.menu_check_auto_24h);

		menuWifiOnly = findViewById(R.id.menu_wifi_only);
		menuWifiOnly.setOnClickListener(menuItemOnClickListener);
		menuCheckWifiOnly = (ImageView) findViewById(R.id.menu_check_wifi_only);

		menuSetCity = findViewById(R.id.menu_set_city);
		menuSetCity.setOnClickListener(menuItemOnClickListener);

		SharedPreferences sp = getSharedPreferences(SETTINGS_SP,
				Context.MODE_PRIVATE);
		boolean isAutoRefreshEnable = sp.getBoolean(SETTINGS_AUTO_REFRESH_ENABLE, getResources()
				.getBoolean(R.bool.config_auto_refresh_enable));
		if (isAutoRefreshEnable) {
			menuCheckAutoRefresh.setImageResource(R.drawable.checkbox_checked);
		} else {
			menuCheckAutoRefresh.setImageResource(R.drawable.checkbox_normal);
		}

		switch (sp.getInt(SETTINGS_AUTO_REFRESH,
				getResources().getInteger(R.integer.config_auto_refresh))) {
		case SETTINGS_AUTO_REFRESH_6H:
			if (isAutoRefreshEnable) {
				menuCheckAuto6h.setImageResource(R.drawable.checkbox_checked);
				menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal);
				menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal);
			} else {
				menuCheckAuto6h.setImageResource(R.drawable.checkbox_checked_disable);
				menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal_disable);
				menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal_disable);
			}
			break;
		case SETTINGS_AUTO_REFRESH_12H:
			if (isAutoRefreshEnable) {
				menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal);
				menuCheckAuto12h.setImageResource(R.drawable.checkbox_checked);
				menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal);
			} else {
				menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal_disable);
				menuCheckAuto12h.setImageResource(R.drawable.checkbox_checked_disable);
				menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal_disable);
			}
			break;
		case SETTINGS_AUTO_REFRESH_24H:
			if (isAutoRefreshEnable) {
				menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal);
				menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal);
				menuCheckAuto24h.setImageResource(R.drawable.checkbox_checked);
			} else {
				menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal_disable);
				menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal_disable);
				menuCheckAuto24h.setImageResource(R.drawable.checkbox_checked_disable);
			}
			break;
		default:
			break;
		}

		if (sp.getBoolean(SETTINGS_WIFI_ONLY,
				getResources().getBoolean(R.bool.config_wifi_only_enable))) {
			menuCheckWifiOnly.setImageResource(R.drawable.checkbox_checked);
		} else {
			menuCheckWifiOnly.setImageResource(R.drawable.checkbox_normal);
		}
	}

	private void updateUI() {
		Log.d("guocl", "MainActivity - updateUI");
		Log.d("guocl", "Weather Info Size: " + mWeatherInfoList.size());
		if (mWeatherInfoList.size() < 1
				&& mWeatherInfoList.get(0).getForecasts().size() < FORECAST_DAY) {
			Log.w("guocl", "update Failed");
			return;
		}

		String temperature = "";
		String tmp = "";// 保存用的
		WeatherInfo info = null;

		int defScreen = 0;
		String defWoeid = WeatherDataUtil.getInstance().getDefaultCityWoeid(
				MainActivity.this);
		Log.d("guocl", "defWoeid=" + defWoeid);

		weatherInfoMainContainer.removeAllViews();
		for (int i = 0; i < mWeatherInfoList.size(); i++) {
			info = mWeatherInfoList.get(i);

			if (defWoeid.equals(info.getWoeid())) {
				defScreen = i;
				Log.d("guocl", "defScreen=" + defScreen);
			}

			String date = info.getForecasts().get(0).getDate() + "("
					+ info.getForecasts().get(0).getDay() + ")";
			temperature = info.getCondition().getTemp() + "℃";
			tmp = info.getForecasts().get(0).getLow() + "℃ /"
					+ info.getForecasts().get(0).getHigh() + "℃";
			String text = info.getCondition().getText();
			int code = Integer.parseInt(info.getCondition().getCode());
			int resId;
			boolean isnight = WeatherDataUtil.getInstance().isNight();
			resId = WeatherDataUtil.getInstance()
					.getWeatherImageResourceByCode(code, isnight, false);
			if (WeatherDataUtil.INVALID_WEAHTER_IMG_RESOURCE == resId) {
				resId = WeatherDataUtil.getInstance()
						.getWeatherImageResourceByText(
								info.getCondition().getText(), isnight, false);
			}

			weatherInfoMainView = new WeatherInfoMainView(MainActivity.this);
			weatherInfoMainView.bindView(date, info.getName(), resId, text,
					temperature, tmp);
			for (int j = 0; j < (FORECAST_DAY - 1); j++) {
				weatherInfoMainView.updateForeCastItem(j, info.getForecasts()
						.get(j));
			}
			weatherInfoMainContainer.addView(weatherInfoMainView);
		}

		weatherInfoMainContainer.setDefaultScreen(defScreen);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date mDate = new Date(mWeatherInfoList.get(0).getUpdateTime());
		String refreshTime = getResources().getString(R.string.refresh_time,
				format.format(mDate));
		refreshTimeText.setText(refreshTime);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("guocl", "onResume");
		if (WeatherDataUtil.getInstance()
				.getNeedUpdateMainUI(MainActivity.this)) {
			showLoadingProgress(true);
			WeatherDataUtil.getInstance().setNeedUpdateMainUI(
					MainActivity.this, false);
			setWeatherFromBD();
			showLoadingProgress(false);
		}
	}

	@Override
	// 得到设置页面的回退
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_CITY_SETTING:
			setWeatherFromBD();
			break;

		default:
			break;
		}
	}

	// 由城市码设置天气情况,并将得到的信息保存在文件中
	private void setWeatherFromInternet() {
		Log.d("guocl", "MainActivity - setWeatherSituation");
		showLoadingProgress(true);
		updateCityCount = mWeatherInfoList.size();
		updateFinishedCityCount = 0;
		for (int i = 0; i < updateCityCount; i++) {
			new QueryWeatherTask().execute(i);
		}
	}

	// 从数据库重获取天气数据
	private void setWeatherFromBD() {
		Log.d("guocl", "MainActivity - setWeatherFromBD");
		String woeid = "";

		ContentResolver mContentResolver = getContentResolver();

		WeatherInfo.Forecast forecast = null;
		mWeatherInfoList.clear();

		Uri uri = Uri.parse("content://com.gweather.app.weather/gweather");
		Cursor cursor = mContentResolver.query(uri, null, "gIndex=?",
				new String[] { Integer
						.toString(WeatherProvider.CONDITION_INDEX) }, null);
		WeatherInfo info;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int cursorIndex = cursor.getColumnIndex(WeatherProvider.WOEID);
				woeid = cursor.getString(cursorIndex);
				info = new WeatherInfo();
				info.setWoeid(woeid);
				mWeatherInfoList.add(info);
			}
			cursor.close();
		}

		if (mWeatherInfoList.size() == 0) {
			// 沒內容,
			finish();
		}

		Log.d("guocl", "setWeatherFromBD:" + woeid);
		for (WeatherInfo mInfo : mWeatherInfoList) {

			cursor = mContentResolver.query(uri, null, "woeid=?",
					new String[] { mInfo.getWoeid() }, WeatherProvider.INDEX);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					int cursorIndex = cursor
							.getColumnIndex(WeatherProvider.INDEX);
					int index = cursor.getInt(cursorIndex);
					Log.d("guocl", "index=" + index);

					if (WeatherProvider.CONDITION_INDEX == index) {// 当前的情况
						mInfo.getCondition().setIndex(index);

						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.NAME);
						mInfo.setName(cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.CODE);
						mInfo.getCondition().setCode(
								cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.DATE);
						mInfo.getCondition().setDate(
								cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.TEMP);
						mInfo.getCondition().setTemp(
								cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.TEXT);
						mInfo.getCondition().setText(
								cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.UPDATE_TIME);
						mInfo.setUpdateTime(cursor.getLong(cursorIndex));
					} else {// 预报
						forecast = mInfo.new Forecast();

						forecast.setIndex(index);
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.CODE);
						forecast.setCode(cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.DATE);
						forecast.setDate(cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.TEXT);
						forecast.setText(cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.DAY);
						forecast.setDay(cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.HIGH);
						forecast.setHigh(cursor.getString(cursorIndex));
						cursorIndex = cursor
								.getColumnIndex(WeatherProvider.LOW);
						forecast.setLow(cursor.getString(cursorIndex));

						mInfo.getForecasts().add(forecast);
					}
				}
				cursor.close();
			}
		}
		if (mWeatherInfoList.size() > 0
				&& mWeatherInfoList.get(0).getForecasts().size() == FORECAST_DAY) {
			updateUI();
			startUpdateService(MainActivity.this, WeatherWidget.ACTION_UPDATE,
					AppWidgetManager.INVALID_APPWIDGET_ID);
		}
	}

	private void saveWeatherToDB(int index) {
		Log.d("guocl", "saveWeather: " + index);
		boolean hasWoeid = false;
		WeatherInfo mInfo = mWeatherInfoList.get(index);
		ContentResolver mContentResolver = getContentResolver();
		Uri uri = Uri.parse("content://com.gweather.app.weather/gweather");
		Cursor cursor = mContentResolver.query(uri, null, "woeid=?",
				new String[] { mInfo.getWoeid() }, null);
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
			values.put(WeatherProvider.WOEID, mInfo.getWoeid());
			values.put(WeatherProvider.CODE, mInfo.getCondition().getCode());
			values.put(WeatherProvider.DATE, mInfo.getCondition().getDate());
			values.put(WeatherProvider.TEMP, mInfo.getCondition().getTemp());
			values.put(WeatherProvider.TEXT, mInfo.getCondition().getText());
			values.put(WeatherProvider.UPDATE_TIME, mInfo.getUpdateTime());
			mContentResolver.update(
					uri,
					values,
					WeatherProvider.INDEX + " = ? AND " + WeatherProvider.WOEID
							+ " = ?",
					new String[] {
							Integer.toString(WeatherProvider.CONDITION_INDEX),
							mInfo.getWoeid() });

			for (int i = 0; i < FORECAST_DAY; i++) {
				values = new ContentValues();
				values.put(WeatherProvider.INDEX, i);
				values.put(WeatherProvider.WOEID, mInfo.getWoeid());
				values.put(WeatherProvider.CODE, mInfo.getForecasts().get(i)
						.getCode());
				values.put(WeatherProvider.DATE, mInfo.getForecasts().get(i)
						.getDate());
				values.put(WeatherProvider.DAY, mInfo.getForecasts().get(i)
						.getDay());
				values.put(WeatherProvider.HIGH, mInfo.getForecasts().get(i)
						.getHigh());
				values.put(WeatherProvider.LOW, mInfo.getForecasts().get(i)
						.getLow());
				values.put(WeatherProvider.TEXT, mInfo.getForecasts().get(i)
						.getText());
				mContentResolver.update(uri, values, WeatherProvider.INDEX
						+ " = ? AND " + WeatherProvider.WOEID + " = ?",
						new String[] { Integer.toString(i), mInfo.getWoeid() });
			}
		} else {
			values = new ContentValues();
			values.put(WeatherProvider.INDEX, WeatherProvider.CONDITION_INDEX);
			values.put(WeatherProvider.WOEID, mInfo.getWoeid());
			values.put(WeatherProvider.CODE, mInfo.getCondition().getCode());
			values.put(WeatherProvider.DATE, mInfo.getCondition().getDate());
			values.put(WeatherProvider.TEMP, mInfo.getCondition().getTemp());
			values.put(WeatherProvider.TEXT, mInfo.getCondition().getText());
			values.put(WeatherProvider.UPDATE_TIME, mInfo.getUpdateTime());
			mContentResolver.insert(uri, values);

			for (int i = 0; i < FORECAST_DAY; i++) {
				values = new ContentValues();
				values.put(WeatherProvider.INDEX, i);
				values.put(WeatherProvider.WOEID, mInfo.getWoeid());
				values.put(WeatherProvider.CODE, mInfo.getForecasts().get(i)
						.getCode());
				values.put(WeatherProvider.DATE, mInfo.getForecasts().get(i)
						.getDate());
				values.put(WeatherProvider.DAY, mInfo.getForecasts().get(i)
						.getDay());
				values.put(WeatherProvider.HIGH, mInfo.getForecasts().get(i)
						.getHigh());
				values.put(WeatherProvider.LOW, mInfo.getForecasts().get(i)
						.getLow());
				values.put(WeatherProvider.TEXT, mInfo.getForecasts().get(i)
						.getText());
				mContentResolver.insert(uri, values);
			}
		}
	}

	private void showLoadingProgress(boolean show) {
		Log.d("guocl", "Main - showLoadingProgress:" + show);
		if (show) {
			loadProgressView.setVisibility(View.VISIBLE);
			refresh.setEnabled(false);
			setting.setEnabled(false);
			weatherInfoMainContainer.setEnabled(false);
			weatherInfoMainContainer.setTouchMove(false);
		} else {
			loadProgressView.setVisibility(View.GONE);
			refresh.setEnabled(true);
			setting.setEnabled(true);
			weatherInfoMainContainer.setEnabled(true);
			weatherInfoMainContainer.setTouchMove(true);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && menuState == MenuState.OPEN) {
			showMenu(false);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void showMenu(boolean show) {
		Log.d("guocl", "showMenu:" + show);

		final int mainContentWidth = mainContentView.getWidth();
		final int menuWidth = getResources().getDimensionPixelSize(
				R.dimen.menu_width);
		if (show) {
			refresh.setEnabled(false);
			setting.setEnabled(false);
			weatherInfoMainContainer.setEnabled(false);
			weatherInfoMainContainer.setTouchMove(false);
			menuView.setVisibility(View.VISIBLE);

			mainContentView.setTranslationX(-menuWidth);
			Animation mTranslateAnimation = new TranslateAnimation(menuWidth,
					0, 0, 0);
			mTranslateAnimation.setDuration(350);
			mainContentView.startAnimation(mTranslateAnimation);

			menuView.setTranslationX(mainContentWidth - menuWidth);
			Animation mTranslateAnimation2 = new TranslateAnimation(menuWidth,
					0, 0, 0);
			mTranslateAnimation2.setDuration(350);
			menuView.startAnimation(mTranslateAnimation2);
			menuState = MenuState.OPEN;
			Log.d("guocl", "mainContentWidth:" + mainContentWidth);
			Log.d("guocl", "menuView:" + menuView);
		} else {
			mainContentView.setTranslationX(0);
			menuView.setTranslationX(0);
			menuView.setVisibility(View.GONE);

			Animation mTranslateAnimation = new TranslateAnimation(-menuWidth,
					0, 0, 0);
			mTranslateAnimation.setDuration(350);
			mainContentView.startAnimation(mTranslateAnimation);
			Animation mTranslateAnimation2 = new TranslateAnimation(
					mainContentWidth - menuWidth, mainContentWidth, 0, 0);
			mTranslateAnimation2.setDuration(350);
			menuView.startAnimation(mTranslateAnimation2);

			refresh.setEnabled(true);
			setting.setEnabled(true);
			weatherInfoMainContainer.setEnabled(true);
			weatherInfoMainContainer.setTouchMove(true);

			menuState = MenuState.CLOSE;
		}
	}

	private View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_auto_refresh: {
				SharedPreferences sp = getSharedPreferences(SETTINGS_SP,
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				boolean isAutoRefreshEnable = sp.getBoolean(SETTINGS_AUTO_REFRESH_ENABLE, getResources()
						.getBoolean(R.bool.config_auto_refresh_enable));
				if (isAutoRefreshEnable) {
					editor.putBoolean(SETTINGS_AUTO_REFRESH_ENABLE, false);
					menuCheckAutoRefresh
							.setImageResource(R.drawable.checkbox_normal);
				} else {
					menuCheckAutoRefresh
							.setImageResource(R.drawable.checkbox_checked);
					editor.putBoolean(SETTINGS_AUTO_REFRESH_ENABLE, true);
				}
				
				switch (sp.getInt(SETTINGS_AUTO_REFRESH,
						getResources().getInteger(R.integer.config_auto_refresh))) {
				case SETTINGS_AUTO_REFRESH_6H:
					if (!isAutoRefreshEnable) {
						menuCheckAuto6h.setImageResource(R.drawable.checkbox_checked);
						menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal);
						menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal);
					} else {
						menuCheckAuto6h.setImageResource(R.drawable.checkbox_checked_disable);
						menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal_disable);
						menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal_disable);
					}
					break;
				case SETTINGS_AUTO_REFRESH_12H:
					if (!isAutoRefreshEnable) {
						menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal);
						menuCheckAuto12h.setImageResource(R.drawable.checkbox_checked);
						menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal);
					} else {
						menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal_disable);
						menuCheckAuto12h.setImageResource(R.drawable.checkbox_checked_disable);
						menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal_disable);
					}
					break;
				case SETTINGS_AUTO_REFRESH_24H:
					if (!isAutoRefreshEnable) {
						menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal);
						menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal);
						menuCheckAuto24h.setImageResource(R.drawable.checkbox_checked);
					} else {
						menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal_disable);
						menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal_disable);
						menuCheckAuto24h.setImageResource(R.drawable.checkbox_checked_disable);
					}
					break;
				default:
					break;
				}
				
				editor.commit();
				Log.d("guocl", "menu_auto_refresh");
				break;
			}
			case R.id.menu_auto_6h: {
				menuAutoReflashTimeChecked(SETTINGS_AUTO_REFRESH_6H);
				break;
			}
			case R.id.menu_auto_12h: {
				menuAutoReflashTimeChecked(SETTINGS_AUTO_REFRESH_12H);
				break;
			}
			case R.id.menu_auto_24h: {
				menuAutoReflashTimeChecked(SETTINGS_AUTO_REFRESH_24H);
				break;
			}
			case R.id.menu_wifi_only: {
				SharedPreferences sp = getSharedPreferences(SETTINGS_SP,
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();

				if (sp.getBoolean(SETTINGS_WIFI_ONLY, getResources()
						.getBoolean(R.bool.config_wifi_only_enable))) {
					editor.putBoolean(SETTINGS_WIFI_ONLY, false);
					menuCheckWifiOnly
							.setImageResource(R.drawable.checkbox_normal);
				} else {
					menuCheckWifiOnly
							.setImageResource(R.drawable.checkbox_checked);
					editor.putBoolean(SETTINGS_WIFI_ONLY, true);
				}
				editor.commit();
				break;
			}

			case R.id.menu_set_city:
				Intent intent = new Intent(MainActivity.this,
						CityMangerActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	private void menuAutoReflashTimeChecked(int checkedTime) {
		SharedPreferences sp = getSharedPreferences(SETTINGS_SP,
				Context.MODE_PRIVATE);
		if (!sp.getBoolean(SETTINGS_AUTO_REFRESH_ENABLE, getResources()
				.getBoolean(R.bool.config_auto_refresh_enable))) {
			Log.i("guocl", "Auto reflash NOT enable.");
			return;
		}
		SharedPreferences.Editor editor = sp.edit();
		switch (checkedTime) {
		case SETTINGS_AUTO_REFRESH_6H:
			menuCheckAuto6h.setImageResource(R.drawable.checkbox_checked);
			menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal);
			menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal);
			
			editor.putInt(SETTINGS_AUTO_REFRESH, checkedTime);
			break;
		case SETTINGS_AUTO_REFRESH_12H:
			menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal);
			menuCheckAuto12h.setImageResource(R.drawable.checkbox_checked);
			menuCheckAuto24h.setImageResource(R.drawable.checkbox_normal);
			
			editor.putInt(SETTINGS_AUTO_REFRESH, checkedTime);
			break;
		case SETTINGS_AUTO_REFRESH_24H:
			menuCheckAuto6h.setImageResource(R.drawable.checkbox_normal);
			menuCheckAuto12h.setImageResource(R.drawable.checkbox_normal);
			menuCheckAuto24h.setImageResource(R.drawable.checkbox_checked);
			
			editor.putInt(SETTINGS_AUTO_REFRESH, checkedTime);
			break;

		default:
			break;
		}

		editor.commit();
	}

	private void startUpdateService(Context context, String action, int widgetId) {
		Log.d("guocl", "Main - startUpdateService");
		Intent intent = new Intent(context, UpdateWidgetService.class);
		intent.setAction(action);
		intent.setData(Uri.parse(String.valueOf(widgetId)));
		context.startService(intent);
	}

}