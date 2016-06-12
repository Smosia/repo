package com.rgk.pedometer.widget;

import com.rgk.pedometer.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CheckSwitchView extends View {
	public static final int STATE_ON = 1;
	public static final int STATE_OFF = 2;

	private Paint mTextPaint;

	private int mTextPaddingLeft = 5;
	private int mTextPaddingRight = 5;

	private String mLeftText;
	private String mRightText;
	private int mTextSize;

	private int mWhich = 1;

	private Drawable mBackOn;
	private Drawable mBackOff;
	private Drawable mCheckIcon;

	public CheckSwitchView(Context context) {
		this(context, null);
	}

	public CheckSwitchView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CheckSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mBackOn = context.getResources().getDrawable(
				R.drawable.check_switch_icon_on);
		mBackOff = context.getResources().getDrawable(
				R.drawable.check_switch_icon_off);
		mCheckIcon = context.getResources().getDrawable(
				R.drawable.check_switch_icon);

		mLeftText = context.getString(R.string.check_switch_label_off);
		mRightText = context.getString(R.string.check_switch_label_on);

		mTextSize = context.getResources().getDimensionPixelSize(
				R.dimen.sw_text_size);

		init();
	}

	private void init() {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setStyle(Style.FILL_AND_STROKE);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setTextAlign(Align.LEFT);
		mTextPaint.setColor(Color.WHITE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int w = getWidth();
		int h = getHeight();

		FontMetrics fm = mTextPaint.getFontMetrics();
		float baseY = h / 2 + (fm.descent - fm.ascent) / 2 - fm.bottom;
		if (mWhich == 1) {
			mBackOn.setBounds(0, 0, w, h);
			mBackOn.draw(canvas);

			mCheckIcon.setBounds(0, 0, h, h);
			mCheckIcon.draw(canvas);

			float rightTextLen = mTextPaint.measureText(mRightText);
			canvas.drawText(mRightText,
					(3 * w - 3 * mTextPaddingLeft + mTextPaddingRight) / 4
							- rightTextLen / 2, baseY, mTextPaint);
		} else {
			mBackOff.setBounds(0, 0, w, h);
			mBackOff.draw(canvas);

			mCheckIcon.setBounds(w - h, 0, w, h);
			mCheckIcon.draw(canvas);

			float leftTextLen = mTextPaint.measureText(mLeftText);
			canvas.drawText(mLeftText,
					(w + 3 * mTextPaddingLeft - mTextPaddingRight) / 4
							- leftTextLen / 2, baseY, mTextPaint);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = mBackOn.getIntrinsicWidth();
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = mBackOn.getIntrinsicHeight();
		}

		setMeasuredDimension(width, height);
	}

	/**
	 * @param which
	 *            1:left, 2:right
	 */
	public void setCheckItem(int which) {
		mWhich = which;
		this.invalidate();
	}

	public int getCheckItem() {
		return mWhich;
	}

}
