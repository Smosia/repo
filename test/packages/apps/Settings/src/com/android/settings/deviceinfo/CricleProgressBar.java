package com.android.settings.deviceinfo;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.deviceinfo.PercentageBarChart.Entry;

public class CricleProgressBar extends View {
	private Context mContext;
	private Paint paint; 
	private boolean isPrivate = true;
	
	private ArrayList<Entry> mEntries;
	
	private int cricleColor;
	
	private int cricleProgressColor;
	private int systemProgressColor;
	private int appsProgressColor;
	private int imageProgressColor;
	private int videoProgressColor;
	private int audioProgressColor;
	private int otherProgressColor;
	private int cacheProgressColor;
	
	private int progressNum;
	
	private float textNum;
	
	private int textColor;

	private float textSize;
	
	private float cricleWidth;

	private int maxProgress;
	
	private int currentProgress;
	private int progress;
	private int percent;
	
	
	
	private int style;
	
	private boolean textIsDisplayable;
	public static final int STROKE = 0;
	public static final int FILL = 1;
	
	public static class Entry  {
		public final int color;
        public final float percentage;
        public final Paint paint;

        protected Entry(int color, float percentage, Paint paint) {
            this.color = color;
            this.percentage = percentage;
            this.paint = paint;
        }

    }
	public CricleProgressBar(Context context) {
		this(context, null);
        this.mContext = context;
	}
	public CricleProgressBar(Context context, AttributeSet attrs) {
		 this(context, attrs, 0);
	        this.mContext = context;
	}

	public CricleProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		Log.i("shubind", "BBBBB");
		this.mContext = context;
		paint = new Paint();
		
		TypedArray mTypedArray = context.
				obtainStyledAttributes(attrs, R.styleable.CricleProgressBar);
		
		cricleColor = mTypedArray.getColor(R.styleable.CricleProgressBar_cricleColor, Color.parseColor("#eaeaea"));
		cricleProgressColor = mTypedArray.getColor(R.styleable.CricleProgressBar_cricleProgressColor, Color.GREEN);
		
		textColor = mTypedArray.getColor(R.styleable.CricleProgressBar_textColor, Color.BLUE);
		textSize = mTypedArray.getDimension(R.styleable.CricleProgressBar_textSize, 25);
		cricleWidth =  mTypedArray.getDimension(R.styleable.CricleProgressBar_cricleWidth, 20);
		maxProgress = mTypedArray.getInteger(R.styleable.CricleProgressBar_maxProgress, 100);
		currentProgress = mTypedArray.getInteger(R.styleable.CricleProgressBar_currentProgress, currentProgress);
		//systemProgress = mTypedArray.getInteger(R.styleable.CricleProgressBar_systemProgress, 0);
		
		style = mTypedArray.getInt(R.styleable.CricleProgressBar_style, 0);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.CricleProgressBar_textIsDisplayable, true);
		mTypedArray.recycle();
		
	}

	private  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.i("shubind", "AAAA");
		/**
		 * 
		 */
		int centre = getWidth()/2;
		int radius = (int)(centre-cricleWidth/2);
		Log.i("shubin", "paint=="+paint);
		paint.setColor(cricleColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(cricleWidth);
		paint.setAntiAlias(true);
		canvas.drawCircle(centre, centre, radius, paint);
		
		/**
		 *
		 */
		
		
		paint.setStrokeWidth(cricleWidth);//
		paint.setColor(cricleProgressColor);
		RectF oval = new RectF(centre - radius, centre - radius,
				centre + radius, centre + radius);//
		switch (style) {
		case STROKE:
			paint.setStyle(Paint.Style.STROKE);
		
			canvas.drawArc(oval, -90, 360 * currentProgress / maxProgress, false, paint);
			
			
			break;
		case FILL:
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if(currentProgress != 0){
				canvas.drawArc(oval, -90, 360 * currentProgress / maxProgress, true, paint);
				
			}
		default:
			break;
		}
		
		if (mEntries != null) {
			for (int i = mEntries.size() -1; i >= 0; --i) {
            	Log.i("shubind", "percent=="+360 * mEntries.get(i).percentage / maxProgress);
            	if(mEntries.get(i).percentage >= 0.0f && mEntries.get(i).percentage<1.0f){
            		canvas.drawArc(oval, -90+progressNum, 360 / maxProgress, false, mEntries.get(i).paint);
            		progressNum = progressNum +  360 / maxProgress;
            		
            		
            	}else{
            		canvas.drawArc(oval, -90+progressNum, (int)(360 *mEntries.get(i).percentage / maxProgress), false, mEntries.get(i).paint);
            		progressNum = progressNum + (int)(360 *mEntries.get(i).percentage / maxProgress);
            		
            	}
            	textNum =  textNum + mEntries.get(i).percentage;
            }
			progressNum = 0;
		}
		/**
		 * 
		 */
		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		int tTextSise = dip2px(mContext, textSize);
		paint.setTextSize(tTextSise);
		paint.setTypeface(Typeface.DEFAULT_BOLD);//
		/**
		int percent = 0;
		if(textNum > 0.0f && textNum < 1.0f){
			percent = 1;
		}else{
			percent =(int)textNum;
		}
		*/
		float textWidth = paint.measureText(percent + "%");//
		if(textIsDisplayable
				&& percent != 0
				&& style == STROKE){
			if(isPrivate){
				canvas.drawText(percent + "%", centre - textWidth/2, centre + tTextSise/2, paint);
				
			}
			
		}
		
		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		int tTextSize = dip2px(mContext, textSize);
		paint.setTextSize(tTextSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		int percents =currentProgress;
		
		float textWidths = paint.measureText(percents + "%");
		if(textIsDisplayable
				//&& percent != 0
				&& style == STROKE){
			if(!isPrivate){
				canvas.drawText(percents + "%", centre - textWidths/2, centre + tTextSize/2, paint);
				
			}
			
		}
		
		
		
	}
	public synchronized int getMax(){
		return maxProgress;
	}
	/**
	 * 
	 */
	public synchronized void setMax(int max){
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.maxProgress =max;
	}
	
	/**
	 * 
	 */
	public  int getProgresses(){
		return currentProgress;
	}
	/**
	 * 
	 */
	public void setPercent(int percent){
		this.percent = percent;
	}
	public  void setProgresses(float progress){
		if(progress < 0){
			throw new IllegalArgumentException("progress not less than 0");
		}
		if(progress > maxProgress){
			currentProgress = maxProgress;
		}
		if(progress <= maxProgress){
			
			this.currentProgress = (int)progress;
			isPrivate = false;
			postInvalidate();
		}
	}
	public void setProgress(int progress){
		this.progress = progress;
		postInvalidate();
	}
	public  Entry setProgress(float  progress,int color){
			isPrivate = true;
			 final Paint p = new Paint();
			 p.setStrokeWidth(cricleWidth);
			 p.setAntiAlias(true);
			 p.setColor(color);
			 p.setStyle(Paint.Style.STROKE);
			return  new Entry(color, progress, p);
		
	}
	
	public void setEntries(ArrayList<Entry> entries) {
        mEntries = entries;
    }
	

	
	public int getCricleColor() {
		return cricleColor;
	}

	public void setCricleColor(int cricleColor) {
		this.cricleColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return cricleProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.cricleProgressColor = cricleProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getCricleWidth() {
		return cricleWidth;
	}

	public void setCricleWidth(float cricleWidth) {
		this.cricleWidth = cricleWidth;
	}
	
	public void setProgressWithAnimation(float progress) {
		Log.i("shubin", "progress----"+progress);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progresses", progress);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }
	
	
	
	
	
	
	
	
}
