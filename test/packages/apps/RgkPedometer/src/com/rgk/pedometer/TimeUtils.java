package com.rgk.pedometer;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {
	
	public static final long ONE_DAY_MILLIS = 24 * 3600 * 1000L;

	public static final int DAY_TYPE = 1;
	public static final int MONTH_TYPE = 2;
	
	public static class DayBound {
		long start;
		long end;
		int date;
	}

	public static long[] getTime(int type, long date) {
		long[] time = new long[2];
		// Calculate the time: start and end
		Calendar c = Calendar.getInstance();
		// long t = System.currentTimeMillis();
		c.setTimeInMillis(date);
		if (DAY_TYPE == type) {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			time[0] = c.getTimeInMillis();
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
			time[1] = c.getTimeInMillis();
		} else if (MONTH_TYPE == type) {
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			time[0] = c.getTimeInMillis();
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
			c.set(Calendar.DAY_OF_MONTH, 0);
			time[1] = c.getTimeInMillis();
		}
		return time;
	}

	public static long[] getTime(int type) {
		long[] time = new long[2];
		// Calculate the time: start and end
		Calendar c = Calendar.getInstance();
		// long t = System.currentTimeMillis();
		// c.setTime(new Date(t));
		if (DAY_TYPE == type) {
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			time[0] = c.getTimeInMillis();
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
			time[1] = c.getTimeInMillis();
		} else if (MONTH_TYPE == type) {
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			time[0] = c.getTimeInMillis();
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
			c.set(Calendar.DAY_OF_MONTH, 0);
			time[1] = c.getTimeInMillis();
		}
		return time;
	}

	public static DayBound[] getDayBoundsBeforeCurrentDay() {
		// Calculate the time: start and end
		Calendar c = Calendar.getInstance();
		// long t = System.currentTimeMillis();
		// c.setTime(new Date(t));
		int currentDay = c.get(Calendar.DAY_OF_MONTH);

		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		DayBound[] days = new DayBound[currentDay];
		for (int i = 0; i < currentDay; i++) {
			days[currentDay - i - 1] = new DayBound();
			days[currentDay - i - 1].date = c.get(Calendar.DAY_OF_MONTH);
			days[currentDay - i - 1].start = c.getTimeInMillis();
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
			days[currentDay - i - 1].end = c.getTimeInMillis();
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 2);
		}
		return days;
	}

	public static DayBound getDayBoundForCurrentDay() {
		Calendar c = Calendar.getInstance();
		// long t = System.currentTimeMillis();
		// c.setTime(new Date(t));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		DayBound day = new DayBound();
		day.date = c.get(Calendar.DAY_OF_MONTH);
		day.start = c.getTimeInMillis();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
		day.end = c.getTimeInMillis();
		return day;
	}

	public static int[] getDayOfWeekForCurrentMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.roll(Calendar.DAY_OF_MONTH, -1);
		int maxDay = c.get(Calendar.DAY_OF_MONTH);
		int[] days = new int[maxDay];
		for (int i = 0; i < maxDay; i++) {
			c.set(Calendar.DAY_OF_MONTH, i + 1);
			days[i] = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return days;
	}

	public static String formatDate(int date) {
		Calendar c = Calendar.getInstance();
		long t = System.currentTimeMillis();
		c.setTime(new Date(t));
		int month = c.get(Calendar.MONTH) + 1;
		String lan = Locale.getDefault().getLanguage();
		String result = "";
		if ("zh".equalsIgnoreCase(lan)) {
			String monthDay = PedometerApplication.getInstance().getResources()
					.getString(R.string.month_day);
			result = String.format(monthDay, month, date);
		} else {
			result = mapMonth.get(month) + " " + date;
		}
		return result;
	}

	public static String getCurrentYearAndMonth() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String lan = Locale.getDefault().getLanguage();
		String result = "";
		if ("zh".equalsIgnoreCase(lan)) {
			String monthDay = PedometerApplication.getInstance().getResources()
					.getString(R.string.year_month);
			result = String.format(monthDay, year, month);
		} else {
			result = mapMonth.get(month) + " " + year;
		}
		return result;
	}

	private static final HashMap<Integer, String> mapMonth = new HashMap<Integer, String>();
	static {
		mapMonth.put(1, "January");
		mapMonth.put(2, "February");
		mapMonth.put(3, "March");
		mapMonth.put(4, "April");
		mapMonth.put(5, "May");
		mapMonth.put(6, "June");
		mapMonth.put(7, "July");
		mapMonth.put(8, "August");
		mapMonth.put(9, "September");
		mapMonth.put(10, "October");
		mapMonth.put(11, "November");
		mapMonth.put(12, "December");
	}

	
	
	/******************************follow code for pedometer*********************************/
	
	
	public static class WeekBound {
		long startDayMillis;
		long endDayMillis;
		String startDayFormat;
		String endDayFormat;
	}
	
	public static WeekBound getCurrentWeekBound() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		int day = c.get(Calendar.DAY_OF_WEEK)/*-1*/;
		long millis = (7 - day + 1) * ONE_DAY_MILLIS - 1;
		long endDayInWeekMillis = c.getTimeInMillis() + millis;
		
		WeekBound wb = new WeekBound();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		buildLastWeekBound(endDayInWeekMillis, wb, c, format);
		
		return wb;
	}
	
	public static WeekBound getLastWeekBound(WeekBound currentWeek) {
		long endDayInWeekMillis = currentWeek.startDayMillis - 1;
		WeekBound wb = new WeekBound();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		buildLastWeekBound(endDayInWeekMillis, wb, format);
		return wb;
	}
	
	public static WeekBound getNextWeekBound(WeekBound currentWeek) {
		long startDayInWeekMillis = currentWeek.endDayMillis + 1;
		WeekBound wb = new WeekBound();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		buildNextWeekBound(startDayInWeekMillis, wb, format);
		return wb;
	}
	
	public static List<WeekBound> getHistoryWeekBounds(long firstStepTimeMillis) {
		List<WeekBound> list = new ArrayList<WeekBound>();
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		int day = c.get(Calendar.DAY_OF_WEEK)/*-1*/;
		long millis = (7 - day + 1) * ONE_DAY_MILLIS - 1;
		long endDayInWeekMillis = c.getTimeInMillis() + millis;
		
		WeekBound wb = new WeekBound();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		long startDayInWeekMillis = buildLastWeekBound(endDayInWeekMillis, wb, c, format);
		list.add(wb);
		
		for (int i = 0; i < 9; i++) {
			endDayInWeekMillis = startDayInWeekMillis - 1;
			if (firstStepTimeMillis > endDayInWeekMillis) {
				return list;
			}
			wb = new WeekBound();
			startDayInWeekMillis = buildLastWeekBound(endDayInWeekMillis, wb, c, format);
			list.add(wb);
		}
		
		return list;
	}
	
	private static long buildLastWeekBound(long endDayInWeekMillis, WeekBound wb,
			Calendar c, SimpleDateFormat format) {
		wb.endDayMillis = endDayInWeekMillis;
		c.setTimeInMillis(endDayInWeekMillis);
		Date endDate = c.getTime();
		String endDateFormat = format.format(endDate);
		wb.endDayFormat = endDateFormat;
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long startDayInWeekMillis = c.getTimeInMillis() - (7 - 1) * ONE_DAY_MILLIS;
		wb.startDayMillis = startDayInWeekMillis;
		c.setTimeInMillis(startDayInWeekMillis);
		Date startDate = c.getTime();
		String startDateFormat = format.format(startDate);
		wb.startDayFormat = startDateFormat;
		return startDayInWeekMillis;
	}
	
	private static long buildLastWeekBound(long endDayInWeekMillis, WeekBound wb,
			SimpleDateFormat format) {
		wb.endDayMillis = endDayInWeekMillis;
		Date endDate = new Date(endDayInWeekMillis);
		String endDateFormat = format.format(endDate);
		wb.endDayFormat = endDateFormat;
		
		long startDayInWeekMillis = endDayInWeekMillis - 7 * ONE_DAY_MILLIS + 1;
		wb.startDayMillis = startDayInWeekMillis;
		Date startDate = new Date(startDayInWeekMillis);
		String startDateFormat = format.format(startDate);
		wb.startDayFormat = startDateFormat;
		return startDayInWeekMillis;
	}
	
	private static long buildNextWeekBound(long startDayInWeekMillis, WeekBound wb,
			SimpleDateFormat format) {
		wb.startDayMillis = startDayInWeekMillis;
		Date startDate = new Date(startDayInWeekMillis);
		String startDateFormat = format.format(startDate);
		wb.startDayFormat = startDateFormat;
		
		long endDayInWeekMillis = startDayInWeekMillis + 7 * ONE_DAY_MILLIS - 1;
		wb.endDayMillis = endDayInWeekMillis;
		Date endDate = new Date(endDayInWeekMillis);
		String endDateFormat = format.format(endDate);
		wb.endDayFormat = endDateFormat;
		return endDayInWeekMillis;
	}
	
	private static final HashMap<Integer, String> mapWeekDay = new HashMap<Integer, String>();
	static {
		mapWeekDay.put(1, "SUN");
		mapWeekDay.put(2, "MON");
		mapWeekDay.put(3, "TUE");
		mapWeekDay.put(4, "WED");
		mapWeekDay.put(5, "THU");
		mapWeekDay.put(6, "FRI");
		mapWeekDay.put(7, "SAT");
	}
	
	public static String getCurrentDateWithFormat() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		String dateFormat = format.format(date);
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		return dateFormat + "." + mapWeekDay.get(day);
	}
	
}
