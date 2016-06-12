package com.gweather.app;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfo {
	private String woeid;
	private String name;
	private long updateTime;
	
	private Condition condition = new Condition();
	private List<Forecast> forecasts = new ArrayList<Forecast>();

	public String getWoeid() {
		return woeid;
	}
	
	public void setWoeid(String woeid) {
		this.woeid = woeid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	public Condition getCondition() {
		return condition;
	}
	
	public List<Forecast> getForecasts() {
		return forecasts;
	}

	public class Condition {
		private int index;
		private String code;
		private String date;
		private String temp;
		private String text;
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTemp() {
			return temp;
		}
		public void setTemp(String temp) {
			this.temp = temp;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
		
	}
	
	public class Forecast {
		private int index;
		private String code;
		private String date;
		private String day;
		private String high;
		private String low;
		private String text;
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public String getHigh() {
			return high;
		}
		public void setHigh(String high) {
			this.high = high;
		}
		public String getLow() {
			return low;
		}
		public void setLow(String low) {
			this.low = low;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
}
