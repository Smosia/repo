package com.gweather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gweather.app.CityInfo;
import com.gweather.app.R;
import com.gweather.app.SetInternationalCityActivity;
import com.gweather.app.WeatherProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

public class WeatherDataUtil {
	public final static String WEATHER_SP = "gweather";
	
	public static final int INVALID_WEAHTER_IMG_RESOURCE = -1;
	
	public static final int CODE_THUNDERSTORMS = 4;
	public static final int CODE_RAIN_AND_SNOW = 5;
	public static final int CODE_SHOWERS = 11;
	public static final int CODE_RAIN = 12;
	public static final int CODE_SNOW_SHOWERS = 14;
	public static final int CODE_BREEZY = 23;//有微风
	public static final int CODE_WINDY = 24;//有风
	public static final int CODE_CLOUDY = 26;
	public static final int CODE_MOSTLY_CLOUDY = 28;
	public static final int CODE_PARTLY_CLOUDY = 30;
	public static final int CODE_CLEAR = 31;
	public static final int CODE_SUNNY = 32;
	public static final int CODE_MOSTLY_CLEAR = 33;
	public static final int CODE_MOSTLY_SUNNY = 34;
	public static final int CODE_SCATTERED_SHOWERS = 39;
	//public static final int CODE_SCATTERED_SHOWERS = 45;//零星阵雨
	public static final int CODE_SCATTERED_THUNDERSTORMS = 47;//零星雷雨
	
	public static final String TEXT_CLOUDY = "cloudy";
	public static final String TEXT_SUNNY = "sunny";
	public static final String TEXT_CLEAR = "clear";
	public static final String TEXT_SHOWERS = "showers";
	public static final String TEXT_THUNDERSTORMS = "thunderstorms";
	public static final String TEXT_RAIN = "rain";
	//下面都是自己猜的
	public static final String TEXT_FOG = "fog";
	public static final String TEXT_SNOW = "snow";
	public static final String TEXT_SLEET = "sleet";
	public static final String TEXT_SAND = "sand";
	
	
	private static WeatherDataUtil mWeatherDataUtil;
	private WeatherDataUtil(){}
	public static WeatherDataUtil getInstance() {
		if(mWeatherDataUtil == null) {
			mWeatherDataUtil = new WeatherDataUtil();
		}
		return mWeatherDataUtil;
	}
	
	public int getWeatherImageResourceByCode(int code, boolean isnight, boolean isWidget) {
		Log.d("guocl", "WeatherDataUtil - updateWeatherImageByCode:"+code);
		switch (code) {
		case CODE_CLOUDY:
		case CODE_MOSTLY_CLOUDY:
		case CODE_PARTLY_CLOUDY:
			if (isWidget) {
				return R.drawable.widget42_icon_cloudy_day;
			} else {
				return R.drawable.weather_icon_cloudy_day;
			}
		case CODE_BREEZY:
		case CODE_WINDY:
			if (isWidget) {
				return R.drawable.widget42_icon_windy_day;
			} else {
				return R.drawable.weather_icon_windy_day;
			}
		case CODE_SUNNY:
		case CODE_MOSTLY_SUNNY:
		case CODE_CLEAR:
		case CODE_MOSTLY_CLEAR:
			if (isWidget) {
				if (isnight) {
					return R.drawable.widget42_icon_sun_day_night;
				} else {
					return R.drawable.widget42_icon_sun_day;
				}
			} else {
				if (isnight) {
					return R.drawable.weather_icon_sun_day_night;
				} else {
					return R.drawable.weather_icon_sun_day;
				}
			}
		case CODE_SCATTERED_SHOWERS:
		//case CODE_SCATTERED_SHOWERS_2:
			if (isWidget) {
				return R.drawable.widget42_icon_dayu_day;
			} else {
				return R.drawable.weather_icon_dayu_day;
			}
		case CODE_SNOW_SHOWERS:
			if (isWidget) {
				return R.drawable.widget42_icon_daxue_day;
			} else {
				return R.drawable.weather_icon_daxue_day;
			}
		case CODE_THUNDERSTORMS:
		case CODE_SCATTERED_THUNDERSTORMS:
			if (isWidget) {
				return R.drawable.widget42_icon_leizhenyu_day;
			} else {
				return R.drawable.weather_icon_leizhenyu_day;
			}
		case CODE_RAIN:
			if (isWidget) {
				return R.drawable.widget42_icon_rain_day;
			} else {
				return R.drawable.weather_icon_rain_day;
			}
		case CODE_RAIN_AND_SNOW:
			if (isWidget) {
				return R.drawable.widget42_icon_yujiaxue_day;
			} else {
				return R.drawable.weather_icon_yujiaxue_day;
			}
		default:
				return INVALID_WEAHTER_IMG_RESOURCE;
		}

	}
	
	public int getWeatherImageResourceByText(String text, boolean isnight, boolean isWidget) {
		Log.d("guocl", "WeatherDataUtil - updateWeatherImageByText:"+text);
		if (isCondition(TEXT_SUNNY, text) || isCondition(TEXT_CLEAR, text)) {
			if (isWidget) {
				if (isnight) {
					return R.drawable.widget42_icon_sun_day_night;
				} else {
					return R.drawable.widget42_icon_sun_day;
				}
			} else {
				if (isnight) {
					return R.drawable.weather_icon_sun_day_night;
				} else {
					return R.drawable.weather_icon_sun_day;
				}
			}
		} else if (isCondition(TEXT_CLOUDY, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_cloudy_day;
			} else {
				return R.drawable.weather_icon_cloudy_day;
			}
		} else if (isCondition(TEXT_THUNDERSTORMS, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_leizhenyu_day;
			} else {
				return R.drawable.weather_icon_leizhenyu_day;
			}
		} else if (isCondition(TEXT_SHOWERS, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_dayu_day;
			} else {
				return R.drawable.weather_icon_dayu_day;
			}
		} else if (isCondition(TEXT_RAIN, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_rain_day;
			} else {
				return R.drawable.weather_icon_rain_day;
			}
		} else if (isCondition(TEXT_SNOW, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_xue_day;
			} else {
				return R.drawable.weather_icon_xue_day;
			}
		} else if (isCondition(TEXT_FOG, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_fog_day;
			} else {
				return R.drawable.weather_icon_fog_day;
			}
		} else if (isCondition(TEXT_SLEET, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_yujiaxue_day;
			} else {
				return R.drawable.weather_icon_yujiaxue_day;
			}
		} else if (isCondition(TEXT_SAND, text)) {
			if (isWidget) {
				return R.drawable.widget42_icon_sandstorm_day;
			} else {
				return R.drawable.weather_icon_sandstorm_day;
			}
		}
		Log.w("guocl", "WeatherDataUtil - updateWeatherImageByText:["+text+"] No match!");
		if (isWidget) {
			return R.drawable.widget42_icon_nodata;
		} else {
			return R.drawable.weather_icon_nodata;
		}
	}
	
	private boolean isCondition(String condition, String text) {
		text = text.toLowerCase();
		if (text.contains(condition)) {
			return true;
		}
		return false;
	}
	
	public boolean isNight() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
    	String hour = sdf.format(new Date());
    	int h = Integer.parseInt(hour);

    	if(h < 7 || h > 18) {
    		return true;
    	}
    	
    	return false;
	}
	
	public String getDefaultCityWoeid(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				WEATHER_SP, Context.MODE_PRIVATE);
		return sp.getString("woeid", "");
	}
	
	public void updateDefaultCityWoeid(Context context, String woeid) {
		Log.d("guocl", "updateDefaultCityWoeid:"+woeid);
		SharedPreferences.Editor editor = context.getSharedPreferences(
				WEATHER_SP, Context.MODE_PRIVATE).edit();
		editor.putString("woeid", woeid);
		editor.commit();
	}
	
	public boolean getNeedUpdateMainUI(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				WEATHER_SP, Context.MODE_PRIVATE);
		return sp.getBoolean("main_update", false);
	}
	
	public void setNeedUpdateMainUI(Context context, boolean needUpdate) {
		Log.d("guocl", "setNeedUpdateMainUI:"+needUpdate);
		SharedPreferences.Editor editor = context.getSharedPreferences(
				WEATHER_SP, Context.MODE_PRIVATE).edit();
		editor.putBoolean("main_update", needUpdate);
		editor.commit();
	}
}
