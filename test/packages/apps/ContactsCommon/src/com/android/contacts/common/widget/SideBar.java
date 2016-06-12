package com.android.contacts.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.android.contacts.common.R;

import java.util.Arrays;

public class SideBar extends View {
	// touch listener
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26 letters
	public static String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z"};
	private Object[] mSections = b;

	private int choose = -1;
	private Paint paint = new Paint();

	private TextView mTextDialog;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;

		for (int i = 0; i < b.length; i++) {
			// paint.setColor(Color.rgb(33, 65, 98));
			//paint.setColor(Color.parseColor("#cccccc"));
                        paint.setColor(getContext().getResources().getColor(R.color.contacts_side_letter_color));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			if (i == choose) {
				//paint.setColor(Color.parseColor("#FF8F00"));
                                paint.setColor(getContext().getResources().getColor(R.color.contacts_side_letter_focus_color));
				paint.setFakeBoldText(true);
			} else if (contains(mSections, b[i])) {
				//paint.setColor(Color.parseColor("#214162"));
                                paint.setColor(getContext().getResources().getColor(R.color.contacts_side_letter_enable_color));
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			// choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
                        //delete DWQBLJ-259 songqingming 20150909 (start)
			//setBackgroundResource(R.drawable.sidebar_background);
                        //delete DWQBLJ-259 songqingming 20150909 (end)
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (!contains(mSections, b[c])) {
					    return true;
				        }
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					
					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	public void changeSections(Object[] sections) {
		mSections = sections;
		invalidate();
	}

	public void changeCurrentLetter(String letter) {
		int index = Arrays.binarySearch(b, letter);
		if (index < 0) {
			return;
		}
		choose = index;
		invalidate();
	}

	public void setNoSelection() {
		choose = -1;
		invalidate();
	}

	private boolean contains(Object[] objects, Object object) {
		// return objects != null ? Arrays.binarySearch(objects, object) >= 0 : false;
		if (objects == null || object == null) {
			return false;
		}
		boolean ret =false;
		for (Object o : objects) {
			if (object.equals(o)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}
