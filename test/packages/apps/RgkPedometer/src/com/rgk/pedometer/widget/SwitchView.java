package com.rgk.pedometer.widget;

import com.rgk.pedometer.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;

public class SwitchView extends View {

	private Paint mBackgroundPaint;
	private Paint mFrontPaint;

	private Paint mTextPaint;

	private int mPaddingLeft = 10;
	private int mPaddingRight = 10;
	private int mPaddingTop = 5;
	private int mPaddingBottom = 5;

	private int mTextPaddingLeft = 20;
	private int mTextPaddingRight = 20;
	private int mTextPaddingTop = 10;
	private int mTextPaddingBottom = 10;

	private String mLeftText;
	private String mRightText;

	private int mTextHeight;

	private int mWhich = 1;

	private int mBackgroundColor;
	private int mOverColor;
	private int mTextSize;

	public SwitchView(Context context) {
		this(context, null);
	}

	public SwitchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.SwitchView);
		mBackgroundColor = mTypedArray.getColor(
				R.styleable.SwitchView_switch_background_color, 0xFFFF7A20);
		mOverColor = mTypedArray.getColor(
				R.styleable.SwitchView_switch_over_color, 0xFFFFFFFF);
		mTextSize = mTypedArray.getDimensionPixelSize(
				R.styleable.SwitchView_switch_text_size, 60);
		mLeftText = mTypedArray
				.getString(R.styleable.SwitchView_switch_left_text);
		if (mLeftText == null) {
			mLeftText = "M";
		}
		mRightText = mTypedArray
				.getString(R.styleable.SwitchView_switch_right_text);
		if (mRightText == null) {
			mRightText = "F";
		}
		
		mTypedArray.recycle();

		init();
	}

	private void init() {
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setAntiAlias(true);
		mBackgroundPaint.setStyle(Style.FILL);
		mBackgroundPaint.setColor(mBackgroundColor);

		mFrontPaint = new Paint();
		mFrontPaint.setAntiAlias(true);
		mFrontPaint.setStyle(Style.FILL);
		mFrontPaint.setColor(mOverColor);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setStyle(Style.FILL_AND_STROKE);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setTextAlign(Align.LEFT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int w = getWidth();
		int h = getHeight();

		RectF rect = new RectF(0, 0, w, h);
		canvas.drawRoundRect(rect, 6, 6, mBackgroundPaint);
		RectF overRect = new RectF();
		if (mWhich == 1) {
			overRect.left = mPaddingLeft;
			overRect.right = w / 2 - mPaddingRight;
		} else {
			overRect.left = w / 2 + mPaddingLeft;
			overRect.right = w - mPaddingRight;
		}
		overRect.top = mPaddingTop;
		overRect.bottom = h - mPaddingBottom;
		canvas.drawRoundRect(overRect, 6, 6, mFrontPaint);

		FontMetrics fm = mTextPaint.getFontMetrics();
		float baseY = h / 2 + (fm.descent - fm.ascent) / 2 - fm.bottom;

		if (mWhich == 1) {
			mTextPaint.setColor(mBackgroundColor);
		} else {
			mTextPaint.setColor(mOverColor);
		}
		float delta = overRect.width() / 2 - mTextPaint.measureText(mLeftText) / 2;
		canvas.drawText(mLeftText, mPaddingLeft + delta, baseY,
				mTextPaint);

		if (mWhich == 1) {
			mTextPaint.setColor(mOverColor);
		} else {
			mTextPaint.setColor(mBackgroundColor);
		}
		float rightTextLen = mTextPaint.measureText(mRightText);
		delta = overRect.width() / 2 - rightTextLen / 2;
		canvas.drawText(
				mRightText,
				w - (mPaddingRight + delta + rightTextLen), 
				baseY, 
				mTextPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		String text = mLeftText + mRightText;

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			float wrapWidth = (mPaddingLeft + mPaddingRight + mTextPaddingLeft + mTextPaddingRight)
					* 2 + mTextPaint.measureText(text);
			width = (int) FloatMath.ceil(wrapWidth);
			if (widthMode == MeasureSpec.AT_MOST) {
				width = Math.min(widthSize, width);
			}
		}

		Rect bounds = new Rect();
		mTextPaint.getTextBounds(text, 0, text.length(), bounds);
		mTextHeight = bounds.height();

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			float wrapHeight = (mPaddingTop + mPaddingBottom + mTextPaddingTop + mTextPaddingBottom)
					+ mTextHeight;
			height = (int) FloatMath.ceil(wrapHeight);
			if (heightMode == MeasureSpec.AT_MOST) {
				height = Math.min(heightSize, height);
			}
		}

		setMeasuredDimension(width, height);
	}

	/**
	 * @param which 1:left, 2:right
	 */
	public void setCheckItem(int which) {
		mWhich = which;
		this.invalidate();
	}

	public int getCheckItem() {
		return mWhich;
	}

}
