package com.rgk.pedometer.widget;

import com.rgk.pedometer.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private Paint mTextPaint;
	private Paint mLinePaint;
	private Paint mStrokePathPaint;
	private Paint mFillPathPaint;
	private Paint mFillCirclePaint;

	private Path mStrokePath;
	private Path mFillPath;

	private float mTextSize;
	private int mTextColor;
	private int mLineColor;
	private int mPathStrokeColor;
	private int mPathFillColor;
	private int mPathPointColor;

	private String[] mWeeks = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
	private int[] mSteps = null;

	private int[] levelSteps = { 20000, 15000, 10000, 7000, 4000, 1000 };
	private Point[] mPoints;
	private int mMaxStep;

	private static final int SPACE_SIZE = 10;

	public ChartView(Context context) throws Exception {
		this(context, null);
	}

	public ChartView(Context context, AttributeSet attrs) throws Exception {
		this(context, attrs, 0);
	}

	public ChartView(Context context, AttributeSet attrs, int defStyle)
			throws Exception {
		super(context, attrs, defStyle);
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.ChartView);
		mTextSize = mTypedArray.getDimensionPixelSize(
				R.styleable.ChartView_chart_text_size, dip2px(context, 10));
		mTextColor = mTypedArray.getColor(
				R.styleable.ChartView_chart_text_color, 0xFFA1A3A6);
		mLineColor = mTypedArray.getColor(
				R.styleable.ChartView_chart_line_color, 0xFFA1A3A6);
		mPathStrokeColor = mTypedArray.getColor(
				R.styleable.ChartView_chart_path_stroke_color, 0xFF80E3F9);
		mPathFillColor = mTypedArray.getColor(
				R.styleable.ChartView_chart_path_fill_color, 0x4C67D7EF);
		mPathPointColor = mTypedArray.getColor(
				R.styleable.ChartView_chart_path_point_color, 0xFF80E3F9);

		CharSequence[] weeks = mTypedArray
				.getTextArray(R.styleable.ChartView_chart_bottom_weeks);
		if (weeks != null) {
			if (weeks.length != 7) {
				throw new Exception("chart_bottom_weeks arrays must be 7 items");
			}
			for (int i = 0; i < weeks.length; i++) {
				mWeeks[i] = weeks[i].toString();
			}
		}

		mTypedArray.recycle();

		init();
	}

	private void init() {
		mTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(mTextColor);
		mTextPaint.setStyle(Style.FILL_AND_STROKE);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setTextSize(mTextSize);

		mLinePaint = new Paint();
		mLinePaint.setAntiAlias(true);
		mLinePaint.setColor(mLineColor);

		mStrokePathPaint = new Paint();
		mStrokePathPaint.setAntiAlias(true);
		mStrokePathPaint.setColor(mPathStrokeColor);
		mStrokePathPaint.setStyle(Paint.Style.STROKE);
		mStrokePathPaint.setStrokeWidth(1);

		mFillPathPaint = new Paint();
		mFillPathPaint.setAntiAlias(true);
		mFillPathPaint.setColor(mPathFillColor);
		mFillPathPaint.setStyle(Paint.Style.FILL);

		mFillCirclePaint = new Paint();
		mFillCirclePaint.setAntiAlias(true);
		mFillCirclePaint.setColor(mPathPointColor);
		mFillCirclePaint.setStyle(Paint.Style.FILL);

		mStrokePath = new Path();
		mFillPath = new Path();

		setStepsForOneWeek(new int[] { 0, 0, 6500, 9000, 15000, 8500, 18000 });
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawColor(Color.WHITE);
		int height = getHeight();
		int width = getWidth();

		// 1. draw levelSteps
		final float verticalGap = (height - SPACE_SIZE) / 7;
		Rect bounds = new Rect();
		String valueText = String.valueOf(1000);
		for (int i = 1; i < 7; i++) {
			valueText = String.valueOf(levelSteps[i - 1]);
			float startY = verticalGap * i + SPACE_SIZE;
			canvas.drawLine(mTextPaint.measureText(valueText) + 5, startY,
					width, startY, mLinePaint);
			mTextPaint.getTextBounds(valueText, 0, valueText.length(), bounds);
			canvas.drawText(valueText, 0, startY + bounds.height() / 2,
					mTextPaint);
		}

		bounds.setEmpty();

		// 2. draw weeks
		float len = mTextPaint.measureText(mWeeks[0]);
		float startX = mTextPaint.measureText(valueText) + 5 + 10;

		final float firstBottomTextX = startX;

		final float horizontalGap = (width - startX - len * 7 - 10) / 6;
		float endY = height - SPACE_SIZE;
		FontMetrics f = mTextPaint.getFontMetrics();
		float baseY = endY - f.descent;
		endY = endY - (f.descent - f.ascent);
		for (int i = 0; i < mWeeks.length; i++) {
			canvas.drawText(mWeeks[i], startX, baseY, mTextPaint);
			startX += horizontalGap + len;
		}

		// 3. draw points and path
		if (mPoints == null || mPoints.length != 7) {
			return;
		}

		buildXY(mPoints, horizontalGap, verticalGap, len, firstBottomTextX,
				endY);
		generatePath(mPoints, len, firstBottomTextX, endY);
		canvas.drawPath(mFillPath, mFillPathPaint);
		canvas.drawPath(mStrokePath, mStrokePathPaint);

		drawPoints(canvas, mPoints);

	}

	private void drawPoints(Canvas canvas, Point[] points) {
		for (int i = 0; i < points.length; i++) {
			canvas.drawCircle(points[i].x, points[i].y, 3, mFillCirclePaint);
		}
	}

	private void buildXY(Point[] points, float horizontalGap,
			final float verticalGap, final float len,
			final float firstBottomTextX, final float endY) {
		if (points == null || points.length != 7) {
			return;
		}
		float pathStartX = firstBottomTextX + len / 2;
		// 1
		points[0].x = pathStartX;
		points[0].y = getY(points[0].steps, verticalGap, endY);
		// 2
		pathStartX += len + horizontalGap;
		points[1].x = pathStartX;
		points[1].y = getY(points[1].steps, verticalGap, endY);
		// 3
		pathStartX += len + horizontalGap;
		points[2].x = pathStartX;
		points[2].y = getY(points[2].steps, verticalGap, endY);
		// 4
		pathStartX += len + horizontalGap;
		points[3].x = pathStartX;
		points[3].y = getY(points[3].steps, verticalGap, endY);
		// 5
		pathStartX += len + horizontalGap;
		points[4].x = pathStartX;
		points[4].y = getY(points[4].steps, verticalGap, endY);
		// 6
		pathStartX += len + horizontalGap;
		points[5].x = pathStartX;
		points[5].y = getY(points[5].steps, verticalGap, endY);
		// 7
		pathStartX += len + horizontalGap;
		points[6].x = pathStartX;
		points[6].y = getY(points[6].steps, verticalGap, endY);
	}

	private float getY(final int step, final float verticalGap, final float endY) {
		int totalSteps = 0;
		int currentSteps = 0;
		float totalPx = verticalGap;
		float px = 0;
		if (step < 1000) {
			final float lastY = endY - SPACE_SIZE;
			totalSteps = 1000;
			currentSteps = step;
			totalPx = lastY - verticalGap * 6 - SPACE_SIZE;
			px = lastY;
		} else if (step >= 1000 && step < 4000) {
			totalSteps = 3000;
			currentSteps = step - 1000;
			px = verticalGap * 6 + SPACE_SIZE;
		} else if (step >= 4000 && step < 7000) {
			totalSteps = 3000;
			currentSteps = step - 4000;
			px = verticalGap * 5 + SPACE_SIZE;
		} else if (step >= 7000 && step < 10000) {
			totalSteps = 3000;
			currentSteps = step - 7000;
			px = verticalGap * 4 + SPACE_SIZE;
		} else if (step >= 10000 && step < 15000) {
			totalSteps = 5000;
			currentSteps = step - 10000;
			px = verticalGap * 3 + SPACE_SIZE;
		} else if (step >= 15000 && step < 20000) {
			totalSteps = 5000;
			currentSteps = step - 15000;
			px = verticalGap * 2 + SPACE_SIZE;
		} else if (step >= 20000) {
			totalSteps = 5000;
			if (mMaxStep > 25000) {
				totalSteps = mMaxStep - 20000;
			}
			currentSteps = step - 20000;
			px = verticalGap * 1 + SPACE_SIZE;
		}
		return px - calculateCurrentPx(totalSteps, currentSteps, totalPx);
	}

	private float calculateCurrentPx(int totalSteps, int currentSteps,
			float totalPx) {
		if (totalSteps == 0) {
			return 0;
		}
		return currentSteps * totalPx / totalSteps;
	}

	private void generatePath(Point[] points, final float len,
			final float firstBottomTextX, final float endY) {
		mStrokePath.reset();
		mFillPath.reset();
		Rect bounds = new Rect();
		mTextPaint.getTextBounds(mWeeks[0], 0, mWeeks[0].length(), bounds);

		// 1
		mStrokePath.moveTo(points[0].x, points[0].y);
		mFillPath.moveTo(points[0].x, points[0].y);
		// 2
		mStrokePath.lineTo(points[1].x, points[1].y);
		mFillPath.lineTo(points[1].x, points[1].y);
		// 3
		mStrokePath.lineTo(points[2].x, points[2].y);
		mFillPath.lineTo(points[2].x, points[2].y);
		// 4
		mStrokePath.lineTo(points[3].x, points[3].y);
		mFillPath.lineTo(points[3].x, points[3].y);
		// 5
		mStrokePath.lineTo(points[4].x, points[4].y);
		mFillPath.lineTo(points[4].x, points[4].y);
		// 6
		mStrokePath.lineTo(points[5].x, points[5].y);
		mFillPath.lineTo(points[5].x, points[5].y);
		// 7
		mStrokePath.lineTo(points[6].x, points[6].y);
		mFillPath.lineTo(points[6].x, points[6].y);

		final float lastY = endY - SPACE_SIZE;
		mFillPath.lineTo(points[6].x, lastY);
		mFillPath.lineTo(firstBottomTextX + len / 2, lastY);
		mFillPath.lineTo(firstBottomTextX + len / 2, points[0].y);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		// int min = Math.min(width, height);
		setMeasuredDimension(width, height);
	}

	public void setStepsForOneWeek(int[] steps) {
		mSteps = steps;
		mPoints = generatePoints(steps);
		mMaxStep = getMax(steps);
		this.postInvalidate();
	}

	private Point[] generatePoints(int[] steps) {
		Point[] points = new Point[steps.length];
		for (int i = 0; i < steps.length; i++) {
			points[i] = new Point();
			points[i].steps = steps[i];
			points[i].day = i;
			// points[i].range = getRangeValue(steps[i]);
		}
		return points;
	}

	private int getMax(int[] steps) {
		if (steps == null || steps.length == 0) {
			return 0;
		}
		int max = steps[0];
		for (int i = 1; i < steps.length; i++) {
			if (max < steps[i]) {
				max = steps[i];
			}
		}
		return max;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	class Point {
		int steps;
		int day;
		// int range;
		float x;
		float y;
	}

}
