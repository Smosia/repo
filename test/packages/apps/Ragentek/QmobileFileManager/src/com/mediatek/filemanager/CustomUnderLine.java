package com.mediatek.filemanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomUnderLine extends View{
    private static final String TAG = "CustomUnderLine";
    private static final int TAB_COUNTS = 3;
    private Paint mPaint;
    private int mColor;
    private int mScreenWidth;
    private int mLineWidth;
    private int mCurIndex;
    private float mOffset;

    public CustomUnderLine(Context context) {
        super(context);
    }
    
    public CustomUnderLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mColor = Color.argb(255, 0, 150, 136);
        mPaint.setColor(mColor);
        mScreenWidth = FileManagerApplication.getScreenWidth();
        mLineWidth = mScreenWidth / TAB_COUNTS;
        Log.d(TAG,"mScreenWidth: "+mScreenWidth);
        Log.d(TAG,"mLineWidth: "+mLineWidth);
    }

    public void updateOffset(float offset,int index) {
        Log.d(TAG,"mOffset: "+mOffset);
        mOffset = (float)( index * mLineWidth + (offset * mScreenWidth) / TAB_COUNTS);
        invalidate();
    }
    
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG,"draw mOffset: "+mOffset);
        Log.d(TAG,"draw mLineWidth: "+mLineWidth);
        canvas.drawLine(mOffset, 0, (mOffset + mLineWidth), 0, mPaint);
    }
}
