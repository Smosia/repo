/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/***********************************************************
 * History:
 * 1. kezhu.xiang@ragentek.com BUG_ID:JWBLB-654 20150529
 * Description: modify the icon of EventInfoActivity
 ***********************************************************/

package com.android.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * A custom view to draw the day of the month in the today button in the options menu
 */

public class DayOfCalendarDrawable extends Drawable {

    private String mDayOfMonth = "01";
    private String mDayOfWeek;
    private final Paint mDatePaint;
    private final Paint mWeekPaint;
    private final Rect mTextBounds = new Rect();

    public DayOfCalendarDrawable(Context c) {
        final float mDensity = c.getResources().getDisplayMetrics().density;
        mDatePaint = new Paint();
        mWeekPaint = new Paint();
        mDatePaint.setAlpha(255);
        mWeekPaint.setAlpha(255);
        mDatePaint.setColor(0xFF000000);
        mWeekPaint.setColor(0xFFFF0000);
        mDatePaint.setTypeface(Typeface.MONOSPACE);
        mWeekPaint.setTypeface(Typeface.MONOSPACE);
        mDatePaint.setTextSize(34F * mDensity);
        mWeekPaint.setTextSize(8F * mDensity);
        mDatePaint.setAntiAlias(true);
        mWeekPaint.setAntiAlias(true);
    }

    @Override
    public void draw(Canvas canvas) {
        mDatePaint.getTextBounds(mDayOfMonth,0,mDayOfMonth.length(),mTextBounds);
        int width1 = mTextBounds.right - mTextBounds.left;
        int height1 = mTextBounds.bottom - mTextBounds.top;
        int width2 = canvas.getWidth();
        int height2 = canvas.getHeight();

        Rect rectweek = new Rect();
        mWeekPaint.getTextBounds(mDayOfWeek,0,mDayOfWeek.length(),rectweek);
        int widthWeek1 = rectweek.right - rectweek.left;
        int heightWeek1 = rectweek.bottom - rectweek.top;

        canvas.drawText(mDayOfMonth,(width2 - width1)/2 - mTextBounds.left, 8+(height2 - height1)/2 - mTextBounds.top,mDatePaint);
        canvas.drawText(mDayOfWeek,(width2 - widthWeek1)/2 - rectweek.left,6+heightWeek1/2- rectweek.top,mWeekPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mDatePaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // Ignore
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    public void setDayOfMonth(int day) {
        mDayOfMonth = String.format("%02d", day);
        invalidateSelf();
    }
    public void setDayOfWeek(String day){
        mDayOfWeek = day ;
        invalidateSelf();
    }
}
