package com.android.systemui.recents.views;

import java.util.Locale;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.systemui.R;

public class CircleProgressBar extends View {
    private static final String TAG = "CircleProgressBar";
    private Paint mCirclePaint;
    private TextPaint mTextPaint;
    private float mProgress;
    private float mAnimProgress;
    private String mText;
    private boolean isPressed;
    private Drawable mDrawable;

    private float innerCircleSpace;
    private int normalBackground;
    private int pressedBackground;
    private int circleBackground;
    private int circleProgressColor;
    private float circleWidth;
    private int startAngle;
    private int sweepAngle;
    private float percentTextSize;
    private float percentTextLargeSize;
    private int percentTextColor;
    private float scaleTextSize;
    private int scaleTextForeColor;
    private int scaleTextbehindColor;

    private AnimatorListener mListener;

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initTypedArray(context, attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initTypedArray(context, attrs);
    }

    public CircleProgressBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mTextPaint = new TextPaint();
        mCirclePaint = new Paint();
        mAnimProgress = mProgress;
        mDrawable = getResources().getDrawable(R.drawable.recents_clear_all_circle_normal);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleProgressBar);
        innerCircleSpace = mTypedArray.getDimension(R.styleable.CircleProgressBar_innerCircleSpace, 20);
        normalBackground = mTypedArray.getResourceId(R.styleable.CircleProgressBar_normalBackground,R.drawable.recents_clear_all_circle_normal);
        pressedBackground = mTypedArray.getResourceId(R.styleable.CircleProgressBar_pressedBackground,R.drawable.recents_clear_all_circle_pressed);
        circleBackground = mTypedArray.getColor(R.styleable.CircleProgressBar_circleProgressNormalColor,Color.WHITE);
        circleProgressColor = mTypedArray.getColor(R.styleable.CircleProgressBar_circleProgressColor, Color.BLACK);
        circleWidth = mTypedArray.getDimension(R.styleable.CircleProgressBar_circleWidth, 5);
        startAngle = mTypedArray.getInteger(R.styleable.CircleProgressBar_startAngle, 0);
        sweepAngle = mTypedArray.getInteger(R.styleable.CircleProgressBar_sweepAngle, 360);
        percentTextSize = mTypedArray.getDimension(R.styleable.CircleProgressBar_percentTextSize, 20);
        percentTextLargeSize = mTypedArray.getDimension(R.styleable.CircleProgressBar_percentTextLargeSize, 25);
        percentTextColor = mTypedArray.getColor(R.styleable.CircleProgressBar_percentTextColor, Color.BLACK);
        scaleTextSize = mTypedArray.getDimension(R.styleable.CircleProgressBar_scaleTextSize, 12);
        scaleTextForeColor = mTypedArray.getColor(R.styleable.CircleProgressBar_scaleTextForeColor, Color.BLACK);
        scaleTextbehindColor = mTypedArray.getColor(R.styleable.CircleProgressBar_scaleTextbehindColor,Color.BLACK);
        mTypedArray.recycle();
    }

    public void setProgress(float progress, boolean animate) {
        float mTempProgress = mProgress;
        mProgress = progress;
        Log.d(TAG, "[setProgress] mProgress = " + mProgress);
        if (animate) {
            startAnim(mTempProgress, mProgress);
        } else {
            mAnimProgress = mProgress;
            postInvalidate();
        }
    }

    public float getProgress() {
        return mProgress;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setAnimatorListener(AnimatorListener listener) {
        mListener = listener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Log.e(TAG, "onTouchEvent");
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            isPressed = true;
            postInvalidate();
            break;
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP:
            isPressed = false;
            postInvalidate();
            break;

        default:
            break;
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw : width = " + getWidth() + ", getheight = " + getHeight());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), isPressed ? pressedBackground : normalBackground);
        int width = Math.min(getWidth(), bitmap.getWidth());
        int height = Math.min(getHeight(), bitmap.getHeight());
        Rect rect = new Rect(0, 0, width, height);
        //A:DELSLM-550 guoshuai 20160225(start)
        PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(pfd);
        //A:DELSLM-550 guoshuai 20160225(end)
        mCirclePaint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, null, rect, mCirclePaint);

        //text paint
        mTextPaint.setTextSize(percentTextLargeSize);
        mTextPaint.setColor(percentTextColor);
        mTextPaint.setTextAlign(Align.LEFT);
        mTextPaint.setAntiAlias(true);
        //draw percentage
        //M:DELSLM-550 guoshuai 20160225(start)
        String progress = String.format(Locale.ENGLISH, "%3.1f", mAnimProgress) + "%";
        int pointIndex = progress.indexOf(".");
        String firstPart = progress.substring(0, pointIndex);
        float firstPartWidth = mTextPaint.measureText(firstPart);
        mTextPaint.setTextSize(percentTextSize);
        String secondPart = progress.substring(pointIndex);
        float secondPartWidth = mTextPaint.measureText(secondPart);
        float proStartX = width / 2 - (firstPartWidth + secondPartWidth) / 2;
        mTextPaint.setTextSize(percentTextLargeSize);
        FontMetrics fm = mTextPaint.getFontMetrics();
        canvas.drawText(firstPart, proStartX, height / 2 - fm.descent + (fm.descent - fm.ascent) / 2, mTextPaint);
        mTextPaint.setTextSize(percentTextSize);
        canvas.drawText(secondPart, proStartX + firstPartWidth, height / 2 - fm.descent + (fm.descent - fm.ascent) / 2, mTextPaint);
        //M:DELSLM-550 guoshuai 20160225(end)

        int innerBitWidth = width;
        int center = innerBitWidth / 2;
        float radius = center - innerCircleSpace / 2;
        mCirclePaint.setColor(circleBackground);
        mCirclePaint.setStyle(Style.STROKE);
        mCirclePaint.setStrokeWidth(circleWidth);
        mCirclePaint.setAntiAlias(true);
        // c5e9a2 5fb907 55a705

        //draw circle progress bar
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(oval, startAngle, sweepAngle, false, mCirclePaint);
        mCirclePaint.setColor(circleProgressColor);
        canvas.drawArc(oval, startAngle, sweepAngle * (mAnimProgress / 100), false, mCirclePaint);

        //draw memory

        if (mText != null && !TextUtils.isEmpty(mText)) {
            mTextPaint.setTextAlign(Align.LEFT);
            mTextPaint.setTextSize(scaleTextSize);
            mTextPaint.setColor(scaleTextForeColor);
            int index = mText.indexOf("/");
            float textMeasureWidth = mTextPaint.measureText(mText);    //M:DELSLM-550 guoshuai 20160225
            String totalStr = mText.substring(index);
            Log.d(TAG, "[onDraw] totalStr = " + totalStr + ", mText = " + mText);
            //M:DELSLM-550 guoshuai 20160225(start)
            float textMeasurefore = textMeasureWidth - mTextPaint.measureText(totalStr);
            float startX = width / 2 - textMeasureWidth / 2;
            //M:DELSLM-550 guoshuai 20160225(end)
            canvas.drawText(mText, 0, index, startX, height - innerCircleSpace / 2, mTextPaint);
            mTextPaint.setColor(scaleTextbehindColor);
            canvas.drawText(mText, index, mText.length(), startX + textMeasurefore, height - innerCircleSpace / 2,
            mTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, " measureWidth = " + measureWidth(widthMeasureSpec) + ", measureHeight = " + measureHeight(heightMeasureSpec));
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int measureSpec) {
        int measure = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        Log.d(TAG, "[measureHeight] : specMode = " + specMode + ", measureSize = " + measureSize);
        if (specMode == MeasureSpec.EXACTLY) {
            Log.e(TAG, " specMode ");
            measure = measureSize;
        } else {
            measure = Math.min(mDrawable.getMinimumHeight(), measureSize);
        }
        return measure;
    }

    private int measureWidth(int measureSpec) {
        int measure = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        Log.d(TAG, "[measureWidth] : specMode = " + specMode + ", measureSize = " + measureSize);
        if (specMode == MeasureSpec.EXACTLY) {
            Log.e(TAG, " specMode ");
            measure = measureSize;
        } else {
            measure = Math.min(mDrawable.getMinimumWidth(), measureSize);
        }
        return measure;
    }

    private void startAnim(float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, 100, end);
        animator.setDuration(1000);
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimProgress = (float) animation.getAnimatedValue();
                postInvalidate();
                if (animation.getAnimatedFraction() == 1.0) {
                    if (mListener != null) {
                        mListener.onAnimationEnd();
                    }
                }
            }
        });
        animator.start();
    }

    public interface AnimatorListener {
        void onAnimationEnd();
    }
}
