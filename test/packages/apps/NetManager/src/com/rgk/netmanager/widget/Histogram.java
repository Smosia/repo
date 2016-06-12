package com.rgk.netmanager.widget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.rgk.netmanager.R;
import com.rgk.netmanager.TimeUtils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Histogram extends RelativeLayout {

	private final static String TAG = "Histogram";

	private boolean hasLayout = false;

	private Context mContext;
	private Resources mResources;
	private Handler mHandler = new Handler();

	private HorizontalScrollView mScrollView;
	private LinearLayout container;
	private View line;

	private View bottomLine;
	private int bottomLineColor;

	private long maxValue = 0;

	private int currentDate;
	private int currentDateMax = 0;

	private int maxHeight;

	private long[] mValues;
	private List<HistogramItem> mItems = new ArrayList<HistogramItem>();

	private int itemWidth;
        private int itemHeight;
	private int columnWeekDayHeight;
	private float columnWeekDayTextSize;
	private int columnWeekDayTextColor;
	private int columnNameHeight;
	private int columnDataheight;
	private int columnImageSideGap;
	private int columnImageColor;
	private int dividingLineColor;
	private int columnNameColor;
	private int columnDataColor;
	private float columnNameTextSize;
	private float columnDataTextSize;
	private int todayColor;

	private int columnIamgeBgColor;

	private int[] weekDaysForMonth;
	private String[] weekDaysArray;

	public Histogram(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		Log.e(TAG, "Histogram");
		mContext = context;
		mResources = context.getResources();

		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.Histogram);
		itemWidth = mTypedArray.getDimensionPixelSize(
				R.styleable.Histogram_columnWidth, mResources
						.getDimensionPixelSize(R.dimen.histogram_item_width));
		itemHeight = mTypedArray.getDimensionPixelSize(
				R.styleable.Histogram_columnHeight, mResources
						.getDimensionPixelSize(R.dimen.column_height));
		columnWeekDayHeight = mTypedArray.getDimensionPixelSize(
				R.styleable.Histogram_columnWeekDayHeight, mResources
						.getDimensionPixelSize(R.dimen.column_week_day_height));
		columnWeekDayTextSize = mTypedArray.getDimension(R.styleable.Histogram_columnWeekDayTextSize, 
				mResources.getDimension(R.dimen.column_week_day_text_size));
		columnWeekDayTextColor = mTypedArray.getColor(
				R.styleable.Histogram_columnWeekDayTextColor, mResources
						.getColor(R.color.column_week_day_color));
		columnNameHeight = mTypedArray.getDimensionPixelSize(
				R.styleable.Histogram_columnNameHeight, mResources
						.getDimensionPixelSize(R.dimen.column_name_height));
		columnDataheight = mTypedArray.getDimensionPixelSize(
				R.styleable.Histogram_columnDataHeight, mResources
						.getDimensionPixelSize(R.dimen.column_data_height));
		columnImageSideGap = mTypedArray.getDimensionPixelSize(
				R.styleable.Histogram_columnImageSideGap,
						mResources.getDimensionPixelSize(R.dimen.column_image_gap));
		columnImageColor = mTypedArray.getColor(
				R.styleable.Histogram_columnImageColor, mResources
						.getColor(R.color.column_image_color));
		dividingLineColor = mTypedArray.getColor(
				R.styleable.Histogram_dividingLineColor, mResources
						.getColor(R.color.dividing_line_color));
		columnNameColor = mTypedArray.getColor(
				R.styleable.Histogram_columnNameColor, mResources
						.getColor(R.color.column_name_color));
		columnDataColor = mTypedArray.getColor(
				R.styleable.Histogram_columnDataColor, mResources
						.getColor(R.color.column_data_color));
		columnNameTextSize = mTypedArray.getDimension(R.styleable.Histogram_columnNameTextSize, 
				mResources.getDimension(R.dimen.column_name_text_size));
		columnDataTextSize = mTypedArray.getDimension(R.styleable.Histogram_columnDataTextSize, 
				mResources.getDimension(R.dimen.column_data_text_size));
		columnIamgeBgColor = mTypedArray.getColor(
				R.styleable.Histogram_column_image_bg_color, 
						mResources.getColor(R.color.column_img_bg_color));

		todayColor = mResources.getColor(R.color.column_today_color);

		LayoutInflater.from(context).inflate(R.layout.histogram, this);

		mScrollView = (HorizontalScrollView) findViewById(R.id.scroller);
		container = (LinearLayout) findViewById(R.id.container);
		line = findViewById(R.id.line);

		LayoutParams lp = (LayoutParams) line.getLayoutParams();
		// lp.topMargin = columnNameHeight;
		lp.topMargin = columnNameHeight + columnWeekDayHeight;
		line.setLayoutParams(lp);
		line.setBackgroundColor(dividingLineColor);

		bottomLineColor = mTypedArray.getColor(
				R.styleable.Histogram_bottom_line_color, 
						mResources.getColor(R.color.column_bottom_line_color));
		bottomLine = findViewById(R.id.bottom_line);
		bottomLine.setBackgroundColor(bottomLineColor);

		init();
	}

	public Histogram(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Histogram(Context context) {
		this(context, null);
	}

	private void init() {
		weekDaysArray = mResources.getStringArray(R.array.day_of_week);
		maxHeight = itemHeight - columnWeekDayHeight - columnNameHeight
				- columnDataheight - 10;
	}

	private void updateDate() {
		currentDate = getCurrentDate();
		currentDateMax = getCurrentDateMax();
		mValues = new long[currentDateMax];
		weekDaysForMonth = TimeUtils.getDayOfWeekForCurrentMonth();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		/*maxHeight = container.getMeasuredHeight() - columnNameHeight
				- columnDataheight - 10;*/
		/*maxHeight = container.getMeasuredHeight() - columnWeekDayHeight - columnNameHeight
				- columnDataheight - 10;*/

		if (null != mValues && !hasLayout) {
			hasLayout = true;
			for (int i = 0; i < mValues.length; i++) {
				updateColumnImageHeight(i);
			}

			scrollToPosition(currentDate);
		}
	}

	public void initColumns(long[] values) {
		if (null == values) {
			Log.d(TAG, "addColumns, values is null");
			return;
		}

		updateDate();
		container.removeAllViews();
		mItems.clear();
		String name;
		for (int i = 0; i < mValues.length; i++) {
			if (i < values.length) {
				mValues[i] = values[i];
			} else {
				mValues[i] = 0;
			}
			HistogramItem mHistogramItem;// = new HistogramItem(mContext);
			mHistogramItem = (HistogramItem) LayoutInflater.from(mContext)
					.inflate(R.layout.histogram_item, this, false);
			mHistogramItem.setIndex(i);
			int date = i + 1;
			if (date == currentDate) {
				name = mResources.getString(R.string.today);
				mHistogramItem.setColumnNameColor(todayColor);
			} else {
				name = "" + date;
				mHistogramItem.setColumnNameColor(columnNameColor);
			}
			String dayOfWeek = weekDaysArray[weekDaysForMonth[i]];
			mHistogramItem.setWeekDay(dayOfWeek);
			mHistogramItem.setWeekDayHeight(columnWeekDayHeight);
			mHistogramItem.setWeekDayTextSize(columnWeekDayTextSize);
			mHistogramItem.setWeekDayTextColor(columnWeekDayTextColor);
			mHistogramItem.setName(name);
			mHistogramItem.setValue(mValues[i]);
			mHistogramItem.setColumnNameHeight(columnNameHeight);
			mHistogramItem.setColumeDataHeight(columnDataheight);
			LayoutParams lp = (LayoutParams) mHistogramItem.getLayoutParams();
			lp.width = itemWidth;
			mHistogramItem.setLayoutParams(lp);
			mHistogramItem.setImageSildeGap(columnImageSideGap);
			// mHistogramItem.setImageColor(columnImageColor);
			mHistogramItem.setImageDrawable(R.drawable.histogram_item_stats_bg);
			// mHistogramItem.setColumnNameColor(columnNameColor);
			mHistogramItem.setColumnDataColor(columnDataColor);
			mHistogramItem.setColumnNameTextSize(columnNameTextSize);
			mHistogramItem.setColumnDataTextSize(columnDataTextSize);
			//mHistogramItem.setOnClickListener(mClickListener);
			mHistogramItem.setColumnImageBgColor(columnIamgeBgColor);
			mHistogramItem.setColumnImageBgMargin(columnImageSideGap);
			mHistogramItem.setColumnImageBgHeight(maxHeight);
			
			container.addView(mHistogramItem);
			mItems.add(mHistogramItem);

			if (mValues[i] > maxValue) {
				maxValue = (long) (values[i] * 1.1);
			}
                        updateColumnImageHeight(i);
		}
	}

        public void updateColumns(long[] values) {
		if (null == values) {
			Log.d(TAG, "addColumns, values is null");
			return;
		}

                maxValue = 0;
		
		for (int i = 0; i < mValues.length; i++) {
			if (i < values.length) {
				mValues[i] = values[i];
				if (mValues[i] > maxValue) {
					maxValue = (long) (mValues[i] * 1.1);
				}
			} else {
                                mValues[i] = 0;
			}
		}
		
		for (int i = 0; i < mValues.length; i++) {
			updateColumnImageHeight(i);
			mItems.get(i).setValue(mValues[i]);
		}

		// updateColumnImageBgHeight();
	}

	private void updateColumnImageBgHeight() {
        	int size = mItems.size();
        	for (int i = 0; i < size; i++) {
        		mItems.get(i).setColumnImageBgHeight(maxHeight);
        	}
        }

	public void setCurrentDateColumnVaLue(long value) {
		setColumnVaLue(currentDate - 1, value);
	}

	public void setColumnVaLue(int index, long value) {
		if (null == mValues || 0 == mValues.length || index >= mValues.length) {
			return;
		}
		mValues[index] = value;
		if (value > maxValue) {
			maxValue = (long) (value * 1.1);

			for (int i = 0; i < mValues.length; i++) {
				updateColumnImageHeight(i);
			}
		} else {
			updateColumnImageHeight(index);
		}
		mItems.get(index).setValue(value);
	}

	private void updateColumnImageHeight(int id) {
		float percent = (float) mValues[id] / maxValue;
		int height = (int) (maxHeight * percent);
		mItems.get(id).setImageHeight(height);
	}

	private void scrollToPosition(final int position) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// int offset = container.getMeasuredWidth() -
				// mScrollView.getWidth();
				int offset = (position * itemWidth)
						- ((mScrollView.getWidth() + itemWidth) / 2);
				if (offset < 0) {
					offset = 0;
				}

				Log.e(TAG, "offset: " + offset + ", position: " + position);
				mScrollView.scrollTo(offset, 0);
			}
		});
	}

	private int getCurrentDateMax() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.get(Calendar.DATE);
	}

	public int getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public void setItemOnClickListener(int index, OnClickListener listener) {
		if (index < 0 || index >= mItems.size()) {
			Log.w(TAG, "setItemOnClickListener, invalid index = " + index);
			return;
		}
		mItems.get(index).setOnClickListener(listener);
	}

        public void setItemOnClickListener(OnClickListener listener) {
		int size = mItems.size();
		for (int i = 0; i < size; i++) {
			mItems.get(i).setOnClickListener(listener);
		}
	}
	
	public void setItemNameColor(int index, int color) {
		if (index < 0 || index >= mItems.size()) {
			Log.w(TAG, "setItemNameColor, invalid index = " + index);
			return;
		}
		mItems.get(index).setColumnNameColor(color);
	}
	
	public void setItemName(int index, String name) {
		if (index < 0 || index >= mItems.size()) {
			Log.w(TAG, "setItemNameColor, invalid index = " + index);
			return;
		}
		mItems.get(index).setName(name);
	}

	public void setBottomLineColor(int color) {
		bottomLine.setBackgroundColor(color);
	}
	
}
