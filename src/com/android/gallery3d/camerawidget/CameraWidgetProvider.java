package com.android.gallery3d.camerawidget;
import com.android.gallery3d.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.RemoteViews;
import android.appwidget.AppWidgetProvider;




public class CameraWidgetProvider extends AppWidgetProvider{
	private static final int  BUTTON_PICTURE=0;
	private static final int VIDAO =1;
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews view=buildUpdate(context);
		
	        for (int i = 0; i < appWidgetIds.length; i++) {
	            appWidgetManager.updateAppWidget(appWidgetIds[i], view);
	        }
	        
	}
	
	
	static RemoteViews buildUpdate(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        views.setOnClickPendingIntent(R.id.photo_widget, getLaunchPendingIntent(context, BUTTON_PICTURE));
       
        views.setOnClickPendingIntent(R.id.video_widget, getLaunchPendingIntent(context, VIDAO));
        return views;
    }
	
	 private static PendingIntent getLaunchPendingIntent(Context context,
	            int buttonId) {
		 PendingIntent pi=null;
		 Intent imageCaptureIntent = new Intent();
		
		 if (buttonId==BUTTON_PICTURE) {
			 imageCaptureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			 imageCaptureIntent.putExtra("CameraWidget", 6);
			 
			
		}else {
			
			imageCaptureIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
			 imageCaptureIntent.putExtra("CameraWidget", 5);
		}
		 pi= PendingIntent.getActivity(context, 0 /* no requestCode */,
	        		imageCaptureIntent,PendingIntent.FLAG_UPDATE_CURRENT /* no flags */);
		return pi;
		
		
		
		
	 }


	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		Log.i("shasha", "intent="+intent.getAction());
	}       
	 

}
