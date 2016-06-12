package com.rgk.netmanager.widget;


import java.math.BigDecimal;

import com.rgk.netmanager.R;
import com.rgk.netmanager.StringUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistogramItem extends RelativeLayout {
	private final static long ONE_KB = 1024l;
	private final static long ONE_MB = 1024 * 1024l;
	private final static long ONE_GB = 1024 * 1024 * 1024l;
	
	private final static String TAG = "HistogramItem";
	
	private TextView columnWeekDay;
	private TextView columnName;
	private View columnImage;
	private TextView columnData;
	private View columnIamgeBg;
	
	private int index;
	private String name;
	private long value;
	private String data;
	private int imageHeight;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setWeekDay(String day) {
		columnWeekDay.setText(day);
	}
	
	public void setWeekDayTextColor(int color) {
		columnWeekDay.setTextColor(color);
	}
	
	public void setWeekDayTextSize(float size) {
		columnWeekDay.setTextSize(size);
	}

	public void setWeekDayHeight(int height) {
		LayoutParams lp = (LayoutParams) columnWeekDay.getLayoutParams();
		lp.height = height;
		columnWeekDay.setLayoutParams(lp);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		columnName.setText(name);
	}
	
	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
		setDataByValue(value);
		
	}
	
	private void  setDataByValue(long value) {
		if (0 != value) {
			columnData.setVisibility(View.VISIBLE);
		} else {
			columnData.setVisibility(View.INVISIBLE);
		}
		// setData(convertValueToData(value));
		setData(StringUtils.formatBytes(value));
	}

	private void setData(String data) {
		this.data = data;
		columnData.setText(data);
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		LayoutParams layoutParams = (LayoutParams)columnImage.getLayoutParams();
		layoutParams.height = imageHeight;
		columnImage.setLayoutParams(layoutParams);
		this.imageHeight = imageHeight;
		
	}

	public HistogramItem(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		Log.e(TAG, "HistogramItem");
		
		LayoutInflater.from(context).inflate(R.layout.histogram_item_layout, this);
		
		columnWeekDay = (TextView) findViewById(R.id.column_week_day);
		columnName = (TextView) findViewById(R.id.column_name);
		columnImage = findViewById(R.id.column_image);
		columnData = (TextView) findViewById(R.id.column_data);
		columnIamgeBg = findViewById(R.id.column_image_bg);
	}

	public HistogramItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HistogramItem(Context context) {
		this(context, null);
	}
	
	private String convertValueToData(long value) {
		Log.e(TAG, "convertValueToData, value = " + value);

		String data = "0B";
		float valueAdjusted;

		if (value < (ONE_KB * 0.9)) {
			data = value + "B";
		} else if (value < (ONE_MB * 0.9)) {
			valueAdjusted = adjustValue((float) value / ONE_KB);
			data = valueAdjusted + "KB";
		} else if (value < (ONE_GB * 0.9)) {
			valueAdjusted = adjustValue((float) value / ONE_MB);
			data = valueAdjusted + "MB";
		} else {
			valueAdjusted = adjustValue((float) value / ONE_GB);
			data = valueAdjusted + "GB";
		}

		return data;
	}
	
	private float adjustValue(float value) {
		BigDecimal mBigDecimal = new BigDecimal(value);
		return mBigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	public void setColumnNameHeight(int height) {
		LayoutParams lp = (LayoutParams) columnName.getLayoutParams();
		lp.height = height;
		columnName.setLayoutParams(lp);
	}
	
	public void setColumeDataHeight(int height) {
		LayoutParams lp = (LayoutParams) columnData.getLayoutParams();
		lp.height = height;
		columnData.setLayoutParams(lp);
	}
	
	public void setImageSildeGap(int gap) {
		LayoutParams lp = (LayoutParams) columnImage.getLayoutParams();
		lp.leftMargin = gap;
		lp.rightMargin = gap;
		columnImage.setLayoutParams(lp);
	}
	
	public void setImageColor(int color) {
		columnImage.setBackgroundColor(color);
	}

	public void setImageDrawable(int resId) {
		columnImage.setBackgroundResource(resId);
	}
	
	public void setColumnNameColor(int color) {
		columnName.setTextColor(color);
	}
	
	public void setColumnDataColor(int color) {
		columnData.setTextColor(color);
	}
	
	public void setColumnNameTextSize(float size) {
		columnName.setTextSize(size);
	}
	
	public void setColumnDataTextSize(float size) {
		columnData.setTextSize(size);
	}

	public void setColumnImageBgMargin(int margin) {
		LayoutParams lp = (LayoutParams) columnIamgeBg.getLayoutParams();
		lp.leftMargin = margin;
		lp.rightMargin = margin;
		columnIamgeBg.setLayoutParams(lp);
	}

	public void setColumnImageBgColor(int color) {
		columnIamgeBg.setBackgroundColor(color);
	}
    
	public void setColumnImageBgHeight(int height) {
		LayoutParams lp = (LayoutParams) columnIamgeBg.getLayoutParams();
		lp.height = height;
		columnIamgeBg.setLayoutParams(lp);
	}

}
