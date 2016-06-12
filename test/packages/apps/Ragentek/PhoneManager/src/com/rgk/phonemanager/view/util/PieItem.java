package com.rgk.phonemanager.view.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.rgk.phonemanager.R;
import com.rgk.phonemanager.ScannerAndCleaner;

public class PieItem extends View {

	private Paint mPaint;

	private int mCount = 0;

	private int[] mBackground;

	private int[] mAnimaColor;

	private int[] mViewWidth;

	private int[] mMaxs;

	private int[] mMins;

	private int[] mValues;

	private int mLineColor;

	private int mCenterX;
	private int mCenterY;

	public PieItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PieItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PieItem(Context context) {
		super(context);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (mCount <= 0) {
			return;
		}
		if (mBackground == null || mBackground.length < mCount) {
			return;
		}
		if (mAnimaColor == null || mAnimaColor.length < mCount) {
			return;
		}
		if (mBackground == null || mBackground.length < mCount) {
			return;
		}
		if (mViewWidth == null || mViewWidth.length < mCount) {
			return;
		}
		if (mMaxs == null || mMaxs.length < mCount) {
			return;
		}
		if (mMins == null || mMins.length < mCount) {
			return;
		}
		if (mValues == null || mValues.length < mCount) {
			return;
		}

		mCenterX = getWidth() / 2;
		mCenterY = getHeight() / 2;

		for (int i = 0; i < mCount; i++) {
			int width = mViewWidth[i];
			int max = mMaxs[i];
			int min = mMins[i];
			int value = mValues[i];

			mPaint.setColor(mBackground[i]);

			int exter_width = width * (max - value) / (max - min);
			mPaint.setStrokeWidth(exter_width);
			RectF oval = new RectF(mCenterX - (width - exter_width / 2),
					mCenterY - (width - exter_width / 2), mCenterX
							+ (width - exter_width / 2), mCenterY
							+ (width - exter_width / 2));

			canvas.drawArc(oval, 360 * i / mCount, 360 / mCount, false, mPaint);

			mPaint.setDither(true);
			mPaint.setAntiAlias(true);
			mPaint.setColor(mAnimaColor[i]);

			mPaint.setSubpixelText(true);
			int inter_width = width - exter_width;
			mPaint.setStrokeWidth(inter_width);
			oval = new RectF(mCenterX - inter_width / 2, mCenterY - inter_width / 2, mCenterX + inter_width / 2, mCenterY + inter_width / 2);
			canvas.drawArc(oval, 360 * i / mCount, 360 / mCount, false, mPaint);

		}

		for (int i = 0; i < mCount; i++) {
			mPaint.setColor(mLineColor);

			mPaint.setStrokeWidth((float) 7.0);
			double a = Math.sin(360 * i / mCount * Math.PI / 180) * mViewWidth[i];
			double b = Math.cos(360 * i / mCount * Math.PI / 180) * mViewWidth[i];
			canvas.drawLine((float) mCenterX, (float) mCenterY, (float) (mCenterX + b), (float) (mCenterY + a), mPaint);
			a = Math.sin(360 * (i + 1) / mCount * Math.PI / 180) * mViewWidth[i];
			b = Math.cos(360 * (i + 1) / mCount * Math.PI / 180) * mViewWidth[i];
			canvas.drawLine((float) mCenterX, (float) mCenterY, (float) (mCenterX + b), (float) (mCenterY + a), mPaint);
		}
		if (ScannerAndCleaner.ismPain()) {
			mPaint.setColor(mLineColor);
			mPaint.setStrokeWidth((float) 2.0);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setAntiAlias(true);
			canvas.drawCircle(mCenterX, mCenterY, 200, mPaint);
		}
	}

	public void setCount(int mCount) {
		this.mCount = mCount;
	}

	public void setValues(int mCount, int[] mBackground, int[] mAnimaColor,
			int[] mViewWidth, int[] mMaxs, int[] mMins, int[] mValues,
			int mLineColor) {
		this.mCount = mCount;
		this.mBackground = mBackground;
		this.mAnimaColor = mAnimaColor;
		this.mViewWidth = mViewWidth;
		this.mMaxs = mMaxs;
		this.mMins = mMins;
		this.mValues = mValues;
		this.mLineColor = mLineColor;
		postInvalidate();
	}
}
