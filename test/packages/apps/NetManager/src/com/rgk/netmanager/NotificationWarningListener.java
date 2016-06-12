package com.rgk.netmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.rgk.netmanager.R;

public class NotificationWarningListener {
    private static final String DEFAULT_NOTIFICATION_TAG = "NotificationWarningListener";

    private final NotificationManager mNotificationManager;
    private final Context mContext;

    private Bitmap mLargeIcon;

    public NotificationWarningListener(Context context) {
        mContext = context;
	Drawable d = mContext.getResources().getDrawable(R.drawable.ic_launcher);
        BitmapDrawable bd = (BitmapDrawable)d;
        mLargeIcon = bd.getBitmap();
        mNotificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
    }

    public void onStart(int jobId) {
    	final Intent intent = new Intent(mContext, MainNetActivity.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final Notification.Builder builder = new Notification.Builder(mContext);
	String title = mContext.getString(R.string.app_name);
	String content = mContext.getString(R.string.stats_monitor_enabled_setting_title);
	String ticker = mContext.getString(R.string.notify_stats_monitor_service_start);
        builder.setOngoing(true)
                .setTicker(ticker)
		.setLargeIcon(mLargeIcon)
                .setSmallIcon(R.drawable.stats_notify_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
        

        final Notification notification = builder.build();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_TAG, jobId, notification);
    }
    
    public void onStatsUpadte(int jobId, long monthStats, long dayStats) {
    	final Intent intent = new Intent(mContext, MainNetActivity.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final Notification.Builder builder = new Notification.Builder(mContext);
        String dayStatsStr = StringUtils.formatBytes(dayStats);
        String monthStatsStr = StringUtils.formatBytes(monthStats);
        String contentText = mContext.getString(R.string.current_time_stats_used, dayStatsStr, monthStatsStr);
	String title = mContext.getString(R.string.app_name);
        builder.setOngoing(true)
                //.setTicker("NetApp is running...")
		.setLargeIcon(mLargeIcon)
                .setSmallIcon(R.drawable.stats_notify_icon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
        

        final Notification notification = builder.build();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_TAG, jobId, notification);
    }

    public void cancel(int jobId) {
        mNotificationManager.cancel(jobId);
    }

    public void cancelAll() {
        mNotificationManager.cancelAll();
    }

    public void onDayMobileWarning(int jobId, long bytes, long overBytes, int slotId) {
    	final Intent intent = new Intent(mContext, OneDayAppsStatsActivity.class);
	TimeUtils.DayBound dayBound = TimeUtils.getDayBoundForCurrentDay();
	intent.putExtra(OneDayAppsStatsActivity.EXTRA_DATE, dayBound.date);
	intent.putExtra(OneDayAppsStatsActivity.EXTRA_START, dayBound.start);
	intent.putExtra(OneDayAppsStatsActivity.EXTRA_END, dayBound.end);
	String dateStr = TimeUtils.formatDate(dayBound.date);
	intent.putExtra(OneDayAppsStatsActivity.EXTRA_FORMAT_DATE, dateStr);
        intent.putExtra(OneDayAppsStatsActivity.EXTRA_TYPE, slotId);

        final Notification.Builder builder = new Notification.Builder(mContext);
        String dayStatsStr = StringUtils.formatBytes(bytes);
        String overStatsStr = StringUtils.formatBytes(overBytes);
        String sim = slotId == Constant.FILTER_SIM1 ? "SIM1" : slotId == Constant.FILTER_SIM2 ? "SIM2" : "";
        String contentText = mContext.getString(R.string.current_day_stats_warning,
                                         sim, dayStatsStr, overStatsStr);
	String title = mContext.getString(R.string.notify_day_warning_title);
        //builder.setOngoing(true)
	builder.setAutoCancel(true)
        	.setTicker(title)
		.setLargeIcon(mLargeIcon)
                .setSmallIcon(R.drawable.stats_notify_icon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
        
        final Notification notification = builder.build();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_TAG, jobId, notification);
    }

    public void onMonthMobileWarning(int jobId, long bytes, long overBytes, int slotId) {
    	final Intent intent = new Intent(mContext, AllDaysStatsActivity.class);
	intent.putExtra(AllDaysStatsActivity.EXTRA_SIM_STATS, slotId);

        final Notification.Builder builder = new Notification.Builder(mContext);
        String monthStatsStr = StringUtils.formatBytes(bytes);
        String overStatsStr = StringUtils.formatBytes(overBytes);
        String sim = slotId == Constant.FILTER_SIM1 ? "SIM1" : slotId == Constant.FILTER_SIM2 ? "SIM2" : "";
        String contentText = mContext.getString(R.string.current_month_stats_warning,
                                         sim, monthStatsStr, overStatsStr);
	String title = mContext.getString(R.string.notify_month_warning_title);
        //builder.setOngoing(true)
	builder.setAutoCancel(true)
        	.setTicker(title)
		.setLargeIcon(mLargeIcon)
                .setSmallIcon(R.drawable.stats_notify_icon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
        
        final Notification notification = builder.build();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_TAG, jobId, notification);
    }

    public void onMonthMobileLimit(int jobId, long bytes, long overBytes, int slotId) {
    	final Intent intent = new Intent(mContext, OverMonthLimitDialog.class);
	intent.putExtra(OverMonthLimitDialog.EXTRA_SLOT, slotId);
        intent.putExtra(OverMonthLimitDialog.EXTRA_MONTH_STATS, bytes);

        final Notification.Builder builder = new Notification.Builder(mContext);
        String monthStatsStr = StringUtils.formatBytes(bytes);
        String overStatsStr = StringUtils.formatBytes(overBytes);
        String sim = slotId == Constant.FILTER_SIM1 ? "SIM1" : slotId == Constant.FILTER_SIM2 ? "SIM2" : "";
        String contentText = mContext.getString(R.string.current_month_stats_warning,
                                         sim, monthStatsStr, overStatsStr);
	String title = mContext.getString(R.string.notify_month_limit_title);
        //builder.setOngoing(true)
	builder.setAutoCancel(true)
        	.setTicker(title)
		.setLargeIcon(mLargeIcon)
                .setSmallIcon(R.drawable.stats_notify_icon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, intent, 0));
        
        final Notification notification = builder.build();
        mNotificationManager.notify(DEFAULT_NOTIFICATION_TAG, jobId, notification);
    }
}
