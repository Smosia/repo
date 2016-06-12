package com.rlk.powersavemanagement;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class PowerAppWidgetProviderHios extends AppWidgetProvider {
	private static final String TAG ="PowerSave/AppWidgetHios";
    
	private static PowerAppWidgetProviderHios sInstance;
	private static boolean isPowerSaveModeButtonEnabled = true;
	private static boolean isPowerSaveOn;
	private static int mBatteryPercentage = PowerSaveService.mBatteryValue;
	private static int mScale = PowerSaveService.mScale;
	private static int[] mAvailableTimes = PowerSaveService.mTimes;
	static synchronized PowerAppWidgetProviderHios getInstance() {
		if (sInstance == null) {
			sInstance = new PowerAppWidgetProviderHios();
		}
		return sInstance;
	}
	static final ComponentName THIS_APPWIDGET = new ComponentName(
			"com.rlk.powersavemanagement",
			"com.rlk.powersavemanagement.PowerAppWidgetProviderHios");
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Log.d(TAG, "onReceive intent.action="+action);
		if(PowerSaveUtils.ACTION_AVAILABLE_TIME.equals(action)){
			mAvailableTimes = intent.getIntArrayExtra("available_time");
			mBatteryPercentage = intent.getIntExtra("battery_level", 100);
			mScale = intent.getIntExtra("battery_sacle", 100);
			updateWidget(context);
		}
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onUpdate");
		updateWidget(context);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
    public void updateWidget(Context context){
    	Log.d(TAG, "updateWidget PowerSaveService.mTimes="+PowerSaveService.mTimes);
    	if(hasInstances(context)){
    		Drawable srcDrawable = context.getResources().getDrawable(R.drawable.widget_battery_remainder_bg);
    		BitmapDrawable bd = (BitmapDrawable)srcDrawable;
    		Bitmap sourceBitmap = bd.getBitmap();
    		RemoteViews views = powerRemoteView(context,sourceBitmap);
    		final AppWidgetManager gm = AppWidgetManager.getInstance(context);
    		gm.updateAppWidget(THIS_APPWIDGET, views);   		
    	}
    }
    public void notifyWidget(Context context,boolean isPowerModeEnable,boolean isPowerSave){
    	isPowerSaveModeButtonEnabled = isPowerModeEnable;
    	isPowerSaveOn = isPowerSave;
    	updateWidget(context);
    }
    public void notifyWidget(Context context,int batteryPercentage,int scale){
    	mBatteryPercentage = batteryPercentage;
    	mScale = scale;
    	updateWidget(context);
    }
    
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		Log.d(TAG, "onEnabled");
		// TODO Auto-generated method stub
		super.onEnabled(context);
		isPowerSaveOn = PowerSaveUtils.isPowerSaveOn(context);
	}

	@Override
	public void onDisabled(Context context) {
		Log.d(TAG, "onDisabled");
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}
	private boolean hasInstances(Context context) {
			try{
				AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
				int[] appWidgetIds = appWidgetManager
					.getAppWidgetIds(new ComponentName(context, this.getClass()));
				// / M: log appWidgetIds status @{
				int widgetLength = (appWidgetIds == null ? 0 : appWidgetIds.length);
				Log.i(TAG, "hasInstances number is " + widgetLength);
				// / @}
				return (widgetLength > 0);
			}catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "hasInstances : no Instance");
				return false;
			}
		}
	private  RemoteViews powerRemoteView(Context context,Bitmap bitmap) {	
	    if(mAvailableTimes==null){
		Log.d(TAG,"powerRemoteView mAvailableTimes is null");
		  mAvailableTimes = new int[2];
		}
		int hour = mAvailableTimes[0];
		int minute = mAvailableTimes[1];
		CharSequence batteryText = String.valueOf(mBatteryPercentage * 100 / mScale) + "%";
		Log.d(TAG, "powerRemoteView mAvailableTimes is:"+hour+" h "+minute+" min "+",batteryPercentage is: "+batteryText);
		boolean isUltraPowerOn = PowerSaveUtils.isUltraPowerOn(context);
		Log.d(TAG,"powerRemoteView isPowerSaveOn :"+isPowerSaveOn+", isUltraPowerOn:"+isUltraPowerOn);
		RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.power_save_widget_hios);
		views.setTextViewText(R.id.widget_standby_hour, hour+"");
		views.setTextViewText(R.id.widget_standby_minute, minute+"");
		views.setTextViewText(R.id.widget_battery_tv, batteryText);
		views.setImageViewBitmap(R.id.widget_battery_remainder_iv, getRectBtimap(context, bitmap, mBatteryPercentage));	
		linkClick(context,views);
		return views;

	}
	private static void linkClick(Context context, RemoteViews views){
		Intent intent;
		PendingIntent pendingIntent;
		final ComponentName serviceName = new ComponentName(context,
				PowerSaveService.class);
		intent = new Intent(context, PowerSaveMainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.power_save_widget, pendingIntent);
		
		intent = new Intent(PowerSaveService.POWER_MODE_STATE_SWITCH_ACTION);	
		intent.setComponent(serviceName);
		pendingIntent = PendingIntent.getService(context, 0, intent,
				PendingIntent.FLAG_ONE_SHOT);
		//views.setOnClickPendingIntent(R.id.widget_power_save_ig,
		//		pendingIntent);
		
		intent = new Intent(PowerSaveService.ULTRA_POWER_STATE_SWITCH_ACTION);
		intent.setComponent(serviceName);
		pendingIntent = PendingIntent.getService(context, 0, intent,
				PendingIntent.FLAG_ONE_SHOT);
		views.setOnClickPendingIntent(R.id.widget_ultra_mode_ig,
				pendingIntent);
	}
	/*public static Bitmap getArcBitmap(Context context,
			Bitmap sourceBitmap,int battery) {		
		float angle =  (float) ((1-battery *1.0/ mScale) * 360);
		Log.d(TAG, "getArcBitmap angle:"+angle);
		Bitmap output = Bitmap.createBitmap(sourceBitmap.getWidth(),
				sourceBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff6B23BE;
		final Paint paint = new Paint();
		final Rect ovalRect = new Rect(0, 0, sourceBitmap.getWidth(),
				sourceBitmap.getHeight());
		final RectF rectF = new RectF(ovalRect);
		canvas.drawColor(0x00000000);
		PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		canvas.setDrawFilter(pfd);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		canvas.drawArc(rectF, -90, angle, true, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
		canvas.drawBitmap(sourceBitmap, ovalRect, ovalRect, paint);
		return output;
	}	*/
	Bitmap outBitmap;
	public  Bitmap getRectBtimap(Context context,
			Bitmap sourceBitmap,int battery){
		int totalLength = sourceBitmap.getWidth();//px
		int height = sourceBitmap.getHeight(); //px
    	float angle =  (float) ((1-battery *1.0/ 100) * 360);
    	float length = (float) (battery *1.0/ 100)*totalLength;
		Log.d(TAG, "getRectBtimap length:"+length);
		recycleBitmap(outBitmap);
		outBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
				sourceBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff6B23BE;
		final Paint paint = new Paint();
		final Rect ovalRect = new Rect(0, 0, sourceBitmap.getWidth(),
				sourceBitmap.getHeight());
		final RectF rectF = new RectF(ovalRect);
		canvas.drawColor(0x00000000);
		PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		canvas.setDrawFilter(pfd);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		//canvas.drawArc(rectF, -90, angle, true, paint);
		canvas.drawRect(0, 0, length, height, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(sourceBitmap, ovalRect, ovalRect, paint);
		return outBitmap;
    }
	 void recycleBitmap(Bitmap bitmap) {
		// for example BookmarkThumbnailWidgetService.java
		if (bitmap != null&&!bitmap.isRecycled()){
			bitmap.recycle();
			Log.d(TAG, "recycleBitmap isRecycled:"+bitmap.isRecycled());
		}
	}
}
