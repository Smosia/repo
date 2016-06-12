package com.rgk.pedometer.widget;

import com.rgk.pedometer.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleBar extends View {
	private RectF mColorWheelRectangle = new RectF();
	private Paint circleBackgroundPaint;
	private Paint smallFillCirclePaint;
	private Paint mCircleHighlightProgressPaint;
	private Paint mCircleNormalProgressPaint;
	private Paint upTextPaint;
	private Paint downTextPaint;
	private Paint leftTextPaint;
	private Paint linePaint;

	private float circleBackgroundStrokeWidth;
	private float circleProgressStrokeWidth;
	private float halfCircleProgressStrokeWidth;
	private int mProgressAni;
	private int mUpTextSize;
	private int mDownTextSize;
	private int mLeftTextSize;
	private int mDistance;

	BarAnimation anim;

	private int targetSteps = 5000;
	private int currentSteps = 0;
	private final int mMaxProgress = 72;

	private Path mLinePath;

	private int mCircleBarBackgroundColor;
	private int mCircleBarNormalProgressColor;
	private int mCircleBarHighlightColor;
	private int mCircleBarLineColor;
	private int mCircleBarUpTextColor;
	private int mCircleBarDownTextColor;
	private int mCircleBarLeftTextColor;
	private BitmapDrawable mCircleBarMiddleDrawable;

	private String mLeftUpText;
	private String mLeftDownText;
	private String mUnitText;

	private float mLeftLabelLength;

	public CircleBar(Context context) {
		this(context, null);
	}

	public CircleBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CircleBar);
		mCircleBarBackgroundColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_background_color, 0xFFF3F3F3);
		mCircleBarNormalProgressColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_normal_progress_color,
				0xFFFFFFFF);
		mCircleBarHighlightColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_highlight_progress_color,
				0xFFFF7A20);
		mCircleBarLineColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_highlight_progress_color,
				0x4C000000);
		mCircleBarUpTextColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_up_text_color, 0xFFA1A3A6);
		mCircleBarDownTextColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_down_text_color, 0xFFFF7A20);
		mCircleBarLeftTextColor = mTypedArray.getColor(
				R.styleable.CircleBar_circle_bar_left_text_color, 0xFFC5D7E1);
		mCircleBarMiddleDrawable = (BitmapDrawable) mTypedArray
				.getDrawable(R.styleable.CircleBar_circle_bar_middle_icon);

		mLeftUpText = mTypedArray
				.getString(R.styleable.CircleBar_circle_bar_left_up_text);
		mLeftDownText = mTypedArray
				.getString(R.styleable.CircleBar_circle_bar_left_down_text);
		mUnitText = mTypedArray
				.getString(R.styleable.CircleBar_circle_bar_step_unit);

		mUpTextSize = mTypedArray.getDimensionPixelSize(
				R.styleable.CircleBar_circle_bar_up_text_size,
				dip2px(context, 16));
		mDownTextSize = mTypedArray.getDimensionPixelSize(
				R.styleable.CircleBar_circle_bar_down_text_size,
				dip2px(context, 35));
		mLeftTextSize = mTypedArray.getDimensionPixelSize(
				R.styleable.CircleBar_circle_bar_left_text_size,
				dip2px(context, 13));
		mDistance = mTypedArray.getDimensionPixelSize(
				R.styleable.CircleBar_circle_bar_middle_icon_area_height,
				dip2px(context, 100)) / 2;
		circleProgressStrokeWidth = mTypedArray.getDimensionPixelSize(
				R.styleable.CircleBar_circle_bar_progress_stroke_width,
				dip2px(context, 20));
		circleBackgroundStrokeWidth = mTypedArray.getDimensionPixelSize(
				R.styleable.CircleBar_circle_bar_background_stroke_width,
				dip2px(context, 30));

		mTypedArray.recycle();

		halfCircleProgressStrokeWidth = circleProgressStrokeWidth / 2;

		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		mCircleHighlightProgressPaint = new Paint();
		mCircleHighlightProgressPaint.setAntiAlias(true);
		mCircleHighlightProgressPaint.setColor(mCircleBarHighlightColor);
		mCircleHighlightProgressPaint.setStyle(Paint.Style.STROKE);
		mCircleHighlightProgressPaint.setStrokeWidth(circleProgressStrokeWidth);
		// mColorWheelPaint.setShadowLayer(1, 0, 0, 0xFF111111);

		mCircleNormalProgressPaint = new Paint();
		mCircleNormalProgressPaint.setAntiAlias(true);
		mCircleNormalProgressPaint.setColor(mCircleBarNormalProgressColor);
		mCircleNormalProgressPaint.setStyle(Paint.Style.STROKE);
		mCircleNormalProgressPaint.setStrokeWidth(circleProgressStrokeWidth);
		// mDefaultColorWheelPaint.setShadowLayer(1, 0, 0, 0xFF333333);

		circleBackgroundPaint = new Paint();
		circleBackgroundPaint.setAntiAlias(true);
		circleBackgroundPaint.setColor(mCircleBarBackgroundColor);
		circleBackgroundPaint.setStyle(Paint.Style.STROKE);
		circleBackgroundPaint.setStrokeWidth(circleBackgroundStrokeWidth);
		circleBackgroundPaint.setShadowLayer(5, 0, 2, 0xFFA3A3A3);

		smallFillCirclePaint = new Paint();
		smallFillCirclePaint.setAntiAlias(true);
		smallFillCirclePaint.setColor(Color.parseColor("#FFFFFF"));
		smallFillCirclePaint.setStyle(Paint.Style.FILL);
		// smallFillCirclePaint.setShadowLayer(5, 0, 0, 0xFFD2CDC9);

		upTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
		upTextPaint.setAntiAlias(true);
		upTextPaint.setColor(mCircleBarUpTextColor);
		upTextPaint.setStyle(Style.FILL_AND_STROKE);
		upTextPaint.setTextAlign(Align.LEFT);
		upTextPaint.setTextSize(mUpTextSize);

		downTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
		downTextPaint.setAntiAlias(true);
		downTextPaint.setColor(mCircleBarDownTextColor);
		downTextPaint.setStyle(Style.FILL_AND_STROKE);
		downTextPaint.setTextAlign(Align.LEFT);
		downTextPaint.setTextSize(mDownTextSize);

		leftTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
		leftTextPaint.setAntiAlias(true);
		leftTextPaint.setColor(mCircleBarLeftTextColor);
		leftTextPaint.setStyle(Style.FILL_AND_STROKE);
		leftTextPaint.setTextAlign(Align.LEFT);
		leftTextPaint.setTextSize(mLeftTextSize);

		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setColor(mCircleBarLineColor);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setStrokeWidth(1);
		PathEffect effect = new DashPathEffect(new float[] { 4, 3, 4, 3 }, 1);
		linePaint.setPathEffect(effect);

		mLinePath = new Path();

		anim = new BarAnimation();
		anim.setDuration(1000);

		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int halfHeight = getHeight() / 2;
		int halfWidth = getWidth() / 2;

		float r1 = halfWidth - mLeftLabelLength - 16;
		float r2 = halfHeight - 16;
		float radius = Math.min(r1, r2);

		canvas.drawCircle(halfWidth, halfHeight, radius
				- (circleProgressStrokeWidth + 10), smallFillCirclePaint);

		canvas.drawCircle(halfWidth, halfHeight, radius /* + 10 */
				- halfCircleProgressStrokeWidth, circleBackgroundPaint);

		mColorWheelRectangle.top = halfHeight - radius
				+ halfCircleProgressStrokeWidth;
		mColorWheelRectangle.bottom = halfHeight + radius
				- halfCircleProgressStrokeWidth;
		mColorWheelRectangle.left = halfWidth - radius
				+ halfCircleProgressStrokeWidth;
		mColorWheelRectangle.right = halfWidth + radius
				- halfCircleProgressStrokeWidth;

		float startAngle = -90;
		float anglePerProgress = 360.0f / mMaxProgress;
		final float angle = anglePerProgress * 4.0f / 5;
		for (int i = 0; i < mMaxProgress; i++) {
			startAngle += anglePerProgress;
			// Log.d("test", "applyTransformation:---------startAngle=" +
			// startAngle);
			canvas.drawArc(mColorWheelRectangle, startAngle, angle, false,
					mCircleNormalProgressPaint);
		}
		for (int i = 0; i < mProgressAni; i++) {
			startAngle += anglePerProgress;
			// Log.d("test", "applyTransformation:---------startAngle=" +
			// startAngle);
			canvas.drawArc(mColorWheelRectangle, startAngle, angle, false,
					mCircleHighlightProgressPaint);
		}
		drawTextAndLine(canvas);

		drawBitmap(canvas, halfHeight, halfWidth, radius);
	}

	private void drawTextAndLine(Canvas canvas) {
		Rect bounds = new Rect();
		String upText = String.valueOf(targetSteps);
		String downText = String.valueOf(currentSteps);
		if (mUnitText != null) {
			upText += mUnitText;
			downText += mUnitText;
		}

		upTextPaint.getTextBounds(upText, 0, upText.length(), bounds);
		float startY = mColorWheelRectangle.centerY()/* + bounds.height() / 2 */
				- mDistance;
		canvas.drawText(
				upText,
				(mColorWheelRectangle.centerX())
						- (upTextPaint.measureText(upText) / 2), startY,
				upTextPaint);

		mLinePath.reset();
		mLinePath.moveTo(2, startY + 10);
		mLinePath.lineTo(getWidth() - 2, startY + 10);
		canvas.drawPath(mLinePath, linePaint);
		if (mLeftUpText != null) {
			canvas.drawText(mLeftUpText, 5, startY, leftTextPaint);
		}

		downTextPaint.getTextBounds(downText, 0, downText.length(), bounds);
		startY = mColorWheelRectangle.centerY() + bounds.height() / 2
				+ mDistance;
		canvas.drawText(downText, (mColorWheelRectangle.centerX())
				- (downTextPaint.measureText(downText) / 2), startY,
				downTextPaint);

		mLinePath.reset();
		mLinePath.moveTo(2, startY + 10);
		mLinePath.lineTo(getWidth() - 2, startY + 10);
		canvas.drawPath(mLinePath, linePaint);
		if (mLeftDownText != null) {
			canvas.drawText(mLeftDownText, 5, startY, leftTextPaint);
		}
	}

	private void drawBitmap(Canvas canvas, int halfHeight, int halfWidth,
			float radius) {
		if (mCircleBarMiddleDrawable == null) {
			return;
		}
		Bitmap bmp = mCircleBarMiddleDrawable.getBitmap();
		float r = radius - (circleProgressStrokeWidth + 10);
		float y = halfHeight - mDistance + 10;
		
		float d = (float) (Math.sqrt(r * r - (y - halfHeight)
				* (y - halfHeight)));

		int bmpW = bmp.getWidth();
		int bmpH = bmp.getHeight();

		Rect src = new Rect();
		RectF dst = new RectF();
		if (2 * d > bmpW) {
			src.left = 0;
			src.right = bmpW;
			dst.left = halfWidth - bmpW / 2.0f;
			dst.right = halfWidth + bmpW / 2.0f;
		} else {
			src.left = (int) (bmpW / 2 - d);
			src.right = (int) (bmpW / 2 + d);
			dst.left = halfWidth - d;
			dst.right = halfWidth + d;
		}
		if (2 * mDistance > bmpH) {
			src.top = 0;
			src.bottom = bmpH;
			dst.top = halfHeight - bmpH / 2.0f;
			dst.bottom = halfHeight + bmpH / 2.0f;
		} else {
			src.top = (int) (bmpH / 2 - mDistance);
			src.bottom = (int) (bmpH / 2 + mDistance);
			dst.top = halfHeight - mDistance;
			dst.bottom = halfHeight + mDistance;
		}
		canvas.drawBitmap(bmp, src, dst, new Paint());
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * int height = getDefaultSize(getSuggestedMinimumHeight(),
		 * heightMeasureSpec);
		 */

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		mLeftLabelLength = getLeftLabelLenth();

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = (int) (width - mLeftLabelLength * 2);
		}

		setMeasuredDimension(width, height);

	}

	private float getLeftLabelLenth() {
		float leftUpTextLength = mLeftUpText == null ? 0 : leftTextPaint
				.measureText(mLeftUpText);
		float leftDownTextLength = mLeftDownText == null ? 0 : leftTextPaint
				.measureText(mLeftDownText);
		float leftLabelLength = Math.max(leftUpTextLength, leftDownTextLength);
		if (leftLabelLength == 0) {
			leftLabelLength = 60;
		}
		return leftLabelLength;
	}

	public void startCustomAnimation() {
		this.startAnimation(anim);
	}

	public void setTargetSteps(int targetSteps) {
		this.targetSteps = targetSteps;
		postInvalidate();
		// startCustomAnimation();
	}

	/**
	 * When call {@link #setCurrentSteps}, then you should call
	 * {@link #startCustomAnimation} will have effect.
	 * 
	 * @param currentSteps
	 */
	public void setCurrentSteps(int currentSteps) {
		this.currentSteps = currentSteps;
		final float angle = calculateAngle();
		mProgressAni = calculateProgress(angle);
		postInvalidate();
	}

	public class BarAnimation extends Animation {
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			final float angle = calculateAngle();
			final int progress = calculateProgress(angle);
			if (interpolatedTime < 1.0f) {
				mProgressAni = (int) Math.ceil(interpolatedTime * progress);
			} else {
				mProgressAni = progress;
			}
			Log.d("test", "applyTransformation:---------mProgressAni="
					+ mProgressAni);
			postInvalidate();

		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	private float calculateAngle() {
		float angle = currentSteps * 360.0f / targetSteps;
		return angle;
	}

	private int calculateProgress(float angle) {
		int progress = (int) Math.ceil(mMaxProgress * angle / 360);
		return progress;
	}

}
