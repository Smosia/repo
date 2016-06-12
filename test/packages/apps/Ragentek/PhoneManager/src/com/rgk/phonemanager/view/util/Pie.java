package com.rgk.phonemanager.view.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

import com.rgk.phonemanager.R;

public class Pie extends RelativeLayout {
	private int mCount = 0;

	private int[] mBackground;

	private int[] mAnimaColor;

	private int[] mViewWidth;

	private int[] mMaxs;

	private int[] mMins;

	private int[] mValues;

	private int mMaxWidth;

	private int mMinWidth;

	private int mLineColor;

	private Context mContext;

	private View mView;
	private RelativeLayout mLayout;
	private PieItem mPieItem;

	private final int WIDTH_READY = 0;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WIDTH_READY:
				mPieItem.setValues(mCount, mBackground, mAnimaColor,
						mViewWidth, mMaxs, mMins, mValues, mLineColor);
				initRotation();
				break;
			default:
				break;
			}
		};
	};

	public Pie(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public Pie(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public Pie(Context context) {
		super(context);
		mContext = context;
	}

	public void init(int count_ex, int[] back_color_ex, int[] anima_color_ex,
			int[] maxs_ex, int[] mins_ex, int[] values_ex, int line_color_ex) {
		this.mCount = count_ex;
		this.mBackground = back_color_ex;
		this.mAnimaColor = anima_color_ex;
		this.mMaxs = maxs_ex;
		this.mMins = mins_ex;
		this.mValues = values_ex;
		this.mLineColor = line_color_ex;
		this.mViewWidth = new int[mCount];

		mView = LayoutInflater.from(mContext).inflate(R.layout.pie, null);
		mLayout = (RelativeLayout) mView.findViewById(R.id.layout);
		mPieItem = (PieItem) mView.findViewById(R.id.pie_item);
		addView(mView);

		getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {

						mMaxWidth = (getMeasuredWidth() > getMeasuredHeight() ? getMeasuredHeight() : getMeasuredWidth()) / 2;
						mMinWidth = mMaxWidth * 4 / 5;
						for (int i = 0; i < mCount; i++) {
							mViewWidth[i] = mMinWidth;
						}
						handler.sendEmptyMessage(WIDTH_READY);
					}
				});
	}

	public void setvalue(int index, int value) {
		setvalue(index, value, false);
	}

	public void setvalue(int index, int value, boolean anima_on) {
		setvalue(index, value, anima_on, null);
	}

	public void setvalue(int index, int value, boolean anima_on,
			AnimaCallBack animaCallBack) {
		if (index < 0 || index >= mCount) {
			return;
		}
		if (value < mMins[index] || value > mMaxs[index]) {
			return;
		}

		if (anima_on) {
			new SetValAnimaThread(animaCallBack, mValues[index], value, index)
					.start();
		} else {
			mValues[index] = value;
			mPieItem.postInvalidate();
		}
	}

	private class SetValAnimaThread extends Thread {
		private AnimaCallBack animaCallBack;
		private int old;
		private int now;
		private int index;
		private final static long TIME = 2000;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			for (int i = 0; i < 800; i++) {
				try {
					Thread.sleep(TIME / 800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mValues[index] = old + (now - old) * (i + 1) / 800;
				mPieItem.postInvalidate();
			}

			if (animaCallBack != null) {
				animaCallBack.afterAnima();
			}
		}

		public SetValAnimaThread(AnimaCallBack animaCallBack, int old, int now,
				int index) {
			super();
			this.animaCallBack = animaCallBack;
			this.old = old;
			this.now = now;
			this.index = index;
		}
	}

	public void expand(int index) {
		expand(index, null);
	}

	public void expand(int index, AnimaCallBack animaCallBack) {
		new ExpandAnimaThread(index, animaCallBack).start();
	}

	private class ExpandAnimaThread extends Thread {
		private final long TIME = 1000;
		private int index;
		private AnimaCallBack animaCallBack;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			for (int i = 0; i < 50; i++) {
				try {
					Thread.sleep(TIME / 50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mViewWidth[index] = mMinWidth + (mMaxWidth - mMinWidth)
						* (i + 1) / 50;
				mPieItem.postInvalidate();
			}

			if (animaCallBack != null) {
				animaCallBack.afterAnima();
			}
		}

		public ExpandAnimaThread(int index, AnimaCallBack animaCallBack) {
			super();
			this.index = index;
			this.animaCallBack = animaCallBack;
		}
	}

	public void shrink(int index) {
		shrink(index, null);
	}

	public void shrink(int index, AnimaCallBack animaCallBack) {
		new ShrinkAnimaThread(index, animaCallBack).start();
	}

	private class ShrinkAnimaThread extends Thread {
		private final long TIME = 1000;
		private int index;
		private AnimaCallBack animaCallBack;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			for (int i = 0; i < 50; i++) {
				try {
					Thread.sleep(TIME / 50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mViewWidth[index] = mMinWidth + (mMaxWidth - mMinWidth)
						* (49 - i) / 50;
				mPieItem.postInvalidate();
			}
			if (animaCallBack != null) {
				animaCallBack.afterAnima();
			}
		}

		public ShrinkAnimaThread(int index, AnimaCallBack animaCallBack) {
			super();
			this.index = index;
			this.animaCallBack = animaCallBack;
		}
	}

	private float start;
	private float space;

	private void initRotation() {
		space = 360 / mCount;
		start = -90 - space / 2;
		mLayout.animate().rotation(start - space * 0).setDuration(0).start();
	}

	public void setPosition(int index) {
		mLayout.animate().rotation(start - space * index).setDuration(1000)
				.start();
	}

	public interface AnimaCallBack {
		public void afterAnima();
	}

}
