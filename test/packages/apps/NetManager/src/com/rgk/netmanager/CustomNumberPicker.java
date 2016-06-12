package com.rgk.netmanager;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class CustomNumberPicker extends NumberPicker {

	public CustomNumberPicker(Context context) {
		super(context);
	}

	public CustomNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomNumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
/*
		@SuppressWarnings("unchecked")
		Class<CustomNumberPicker> pickerCls = (Class<CustomNumberPicker>) this
				.getClass();
		Field field;
		try {
			field = pickerCls.getDeclaredField("mSelectionDivider");
			field.setAccessible(true);
			field.set(this, new ColorDrawable(0xff44a21d));
		} catch (Exception e) {
			e.printStackTrace();
		}
*/

	}

	public void addView(View child) {
        	super.addView(child);
        	updateView(child);
    	}

	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        	super.addView(child, params);
        	updateView(child);
    	}

	public void addView(View child, int index, LayoutParams params) {
		super.addView(child, index, params);
		updateView(child);
	}

	private void updateView(View view) {
		if (view instanceof EditText) {
			((EditText) view).setTextColor(0xff44a21d);
		}
	}

}
