/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 */
/* MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek Software")
 * have been modified by MediaTek Inc. All revisions are subject to any receiver's
 * applicable license agreements with MediaTek Inc.
 */

/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.android.music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.music.MediaPlaybackActivity;
import com.android.music.MediaPlaybackService;
import com.android.music.MusicBrowserActivity;
import com.android.music.MusicLogUtils;
import com.android.music.MusicUtils;


/**
 * Simple widget to show currently playing album art along
 * with play/pause and next track buttons.  
 */
public class MediaAppWidgetProvider extends AppWidgetProvider {
    static final String TAG = "MusicAppWidget";
    
    public static final String CMDAPPWIDGETUPDATE = "appwidgetupdate";

    private static MediaAppWidgetProvider sInstance;

    public static final String ACTION_UPDATE_OPTIONS="android.appwidget.action.APPWIDGET_UPDATE_OPTIONS";
    private static int mProgress = 0;
    private static String mtotaltime;
    private static String mcurrenttime;
    
    private static CharSequence titleName,artistName,errorState;
    
    private static long mAlbumId = 0;
    private static boolean isPlaying;
    public static boolean isAppWidget = false;

    static synchronized MediaAppWidgetProvider getInstance() {
        if (sInstance == null) {
            sInstance = new MediaAppWidgetProvider();
        }
        return sInstance;
    }

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		MusicLogUtils.i(TAG, "onReceive");
		//add permission judgment
		 if (context
		          .checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
		          != PackageManager.PERMISSION_GRANTED) {
			 MusicLogUtils.i(TAG, "onReceive:permission");
			 Toast.makeText(context, context.getString(R.string.music_storage_permission_deny), Toast.LENGTH_SHORT).show();
			 return;
		 }
	        	
		String action = intent.getAction();
		
		if ("com.mediatek.FMRadio.widget.music.refresh".equals(action)) {
			mProgress = intent.getIntExtra("progress", 0);
			mtotaltime=intent.getStringExtra("totaltime");
			mcurrenttime=intent.getStringExtra("currenttime");
			Log.i("gxy", "widgetprovide.onreceive():"+mProgress+"mtotaltime="+mtotaltime);
			AppWidgetManager gm = AppWidgetManager.getInstance(context);
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.album_appwidget);
	        views.setProgressBar(R.id.widget_music_mini_progressBar, 1000, mProgress, false);
	        views.setTextViewText(R.id.currenttime, mcurrenttime);
	        views.setTextViewText(R.id.totaltime,mtotaltime);
	        gm.updateAppWidget(new ComponentName(context, this.getClass()), views);
		}
//		else if("com.rgk.music.widget.update".equals(action)){
			if(hasInstances(context)){
				updateWidget(context, null);
			}else{
			   MusicLogUtils.d(TAG, "notifyChange: no Instance");
			}
//		}

		
	}
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        MusicLogUtils.i(TAG, "onUpdate");
        // Send broadcast intent to any running MediaPlaybackService so it can
        // wrap around with an immediate update.
        isAppWidget = true;
        Intent updateIntent = new Intent(context, MediaPlaybackService.class);
        updateIntent.setAction(MediaPlaybackService.SERVICECMD);
        updateIntent.putExtra(MediaPlaybackService.CMDNAME,
                MediaAppWidgetProvider.CMDAPPWIDGETUPDATE);
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        updateIntent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
        context.startService(updateIntent);
       
    }
   

    
    /**
     * Initialize given widgets to default state, where we launch Music on default click
     * and hide actions if service not running.
     */
    private void defaultAppWidget(Context context, int[] appWidgetIds) {
        final Resources res = context.getResources();
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.album_appwidget);
        views.setViewVisibility(R.id.title, View.GONE);
        /// M: Set default play/pause button image resource
        views.setImageViewResource(R.id.control_play, R.drawable.rgk_music_btn_play);
        views.setTextViewText(R.id.artist, res.getText(R.string.widget_initial_text));

        linkButtons(context, views, false /* not playing */);
        pushUpdate(context, appWidgetIds, views);
    }
    
    private void pushUpdate(Context context, int[] appWidgetIds, RemoteViews views) {
        // Update specific list of appWidgetIds if given, otherwise default to all
        final AppWidgetManager gm = AppWidgetManager.getInstance(context);
        MusicLogUtils.i(TAG, "pushUpdate");
        /// M: update app widget @{
        if (appWidgetIds != null) {
            gm.updateAppWidget(appWidgetIds, views);
        } else {
            gm.updateAppWidget(new ComponentName(context, this.getClass()), views);
        }
        /// @}
    }
    
    /**
     * Check against {@link AppWidgetManager} if there are any instances of this widget.
     */
    private boolean hasInstances(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, this.getClass()));
        /// M: log appWidgetIds status @{
        int widgetLength = (appWidgetIds == null ? 0 : appWidgetIds.length);
        MusicLogUtils.i(TAG, "hasInstances number is " + widgetLength);
        /// @}
        return (widgetLength > 0);
    }

    /**
     * Handle a change notification coming over from {@link MediaPlaybackService}
     */
    void notifyChange(MediaPlaybackService service, String what) {
        MusicLogUtils.i(TAG, "notifyChange");
        /// M: add QUIT_PLAYBACK status to update widget and log something @{
        if (hasInstances(service)) {
            if (MediaPlaybackService.META_CHANGED.equals(what) ||
                    MediaPlaybackService.PLAYSTATE_CHANGED.equals(what) ||
                    MediaPlaybackService.QUIT_PLAYBACK.equals(what)) {
                performUpdate(service, null);
            } else {
                MusicLogUtils.d(TAG, "notifyChange(" + what + "):discard!");
            }
        } else {
            MusicLogUtils.d(TAG, "notifyChange: no Instance");
        }
        /// @}
    }
    
    /**
     * Update all active widget instances by pushing changes 
     */
    void performUpdate(MediaPlaybackService service, int[] appWidgetIds) {
        MusicLogUtils.d(TAG, "performUpdate");
        final Resources res = service.getResources();
        final RemoteViews views = new RemoteViews(service.getPackageName(), R.layout.album_appwidget);        
        titleName = service.getTrackName();
        artistName = service.getArtistName();
        errorState = null;
        mAlbumId = service.getAlbumId();
 //modify bug_id:JSLEL-1500 gaoxueyan 20141020 start  
       // Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.rgk_default_album_icon);
        Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.rgk_default_nowplaying_thumb_1);
        int w = icon.getWidth();
        int h = icon.getHeight();
        /// M: call getArtwork to get album art bitmap
       // Bitmap b = MusicUtils.getArtwork(service.getApplicationContext(), -1, mAlbumId , true);
        Bitmap b = MusicUtils.getArtwork(service.getApplicationContext(), -1, mAlbumId , false);
		//modify bug_id:JSLEL-1500 gaoxueyan 20141020 end
        if(b==null){
        	b = icon;
        }
        b = Bitmap.createScaledBitmap(b, w, h, true);
        
        /// M: If artist name get from database is equal to "unknown",use unknown_artist_name replace.
        if (MediaStore.UNKNOWN_STRING.equals(artistName)) {
            artistName = res.getString(R.string.unknown_artist_name);
        }

        // Format title string with track number, or show SD card message
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_SHARED) ||
                status.equals(Environment.MEDIA_UNMOUNTED)) {
            /// M: Remove check external storage @{
            //if (android.os.Environment.isExternalStorageRemovable()) {
                errorState = res.getText(R.string.sdcard_busy_title);
            //} else {
            //    errorState = res.getText(R.string.sdcard_busy_title_nosdcard);
            //}
            /// @}
        } else if (status.equals(Environment.MEDIA_REMOVED)) {
            /// M: Remove check external storage @{
            //if (android.os.Environment.isExternalStorageRemovable()) {
                errorState = res.getText(R.string.sdcard_missing_title);
            //} else {
            //    errorState = res.getText(R.string.sdcard_missing_title_nosdcard);
            //}
            /// @}
        } else if (titleName == null) {
            errorState = res.getText(R.string.widget_initial_text);
        }
        
        if (errorState != null) {
            /// M: Show error state to user
            views.setViewVisibility(R.id.artist, View.VISIBLE);
            views.setViewVisibility(R.id.title, View.GONE);
            views.setTextViewText(R.id.artist, errorState);
            views.setImageViewBitmap(R.id.music_photo, icon);
            
        } else {
            /// M: No error, so show normal titles @{
            final String httpHeader = "http://";
            if ((titleName != null) && titleName.toString().startsWith(httpHeader)) {
                views.setViewVisibility(R.id.title, View.VISIBLE);
                views.setViewVisibility(R.id.artist, View.GONE);
                views.setTextViewText(R.id.title, titleName);
            } else {
                views.setViewVisibility(R.id.title, View.VISIBLE);
                views.setViewVisibility(R.id.artist, View.VISIBLE);
                views.setTextViewText(R.id.title, titleName);
                views.setTextViewText(R.id.artist, artistName);
            }
    if(b!=null){
            	views.setImageViewBitmap(R.id.music_photo, b);
            	
            }else{
            	views.setImageViewBitmap(R.id.music_photo, icon);
            	
            }
            /// @}
        }
        
        // Set correct drawable for pause state
        isPlaying = service.isPlaying();
        if (isPlaying) {
            views.setImageViewResource(R.id.control_play, R.drawable.rgk_music_btn_pause);
        } else {
            views.setImageViewResource(R.id.control_play, R.drawable.rgk_music_btn_play);
        }
        
        views.setProgressBar(R.id.widget_music_mini_progressBar, 1000,mProgress, false);
        views.setTextViewText(R.id.currenttime, mcurrenttime);
        views.setTextViewText(R.id.totaltime,mtotaltime);
        MusicLogUtils.i(TAG, "performUpdate,Track is " + titleName + 
            " Artist is " + artistName + " Error is " + errorState + " Playing is " + isPlaying); 

        // Link actions buttons to intents
        linkButtons(service, views, isPlaying);
        
        pushUpdate(service, appWidgetIds, views);
    }

    private void updateWidget(Context context, int[] appWidgetIds) {
    	final Resources res = context.getResources();
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.album_appwidget);
		//modify bug_id:JSLEL-1500 gaoxueyan 20141020 start
       // Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.rgk_default_album_icon);
        Bitmap icon = BitmapFactory.decodeResource(res, R.drawable.rgk_default_nowplaying_thumb_1);
        int w = icon.getWidth();
        int h = icon.getHeight();
        /// M: call getArtwork to get album art bitmap
       // Bitmap b = MusicUtils.getArtwork(context, -1, mAlbumId , true);
        Bitmap b = MusicUtils.getArtwork(context, -1, mAlbumId , false);
		//modify bug_id:JSLEL-1500 gaoxueyan 20141020 end
        if(b==null){
        	b = icon;
        }
        b = Bitmap.createScaledBitmap(b, w, h, true);
        

//            if(b!=null){
        views.setImageViewBitmap(R.id.music_photo, b);
//            }else{
//            	views.setImageViewBitmap(R.id.music_photo, icon);
//            }
            
//            CharSequence titleName,artistName,errorState;
//            
//            long mAlbumId = 0;
//            
//            boolean isPlaying;
            
            if (titleName != null) {
    			views.setTextViewText(R.id.title, titleName);
    		} else {
    			views.setTextViewText(R.id.title,
    					res.getString(R.string.musicbrowserlabel));
    		}
    		if(artistName!=null){
    			views.setTextViewText(R.id.artist, artistName);
    			
    		}else{
    			views.setTextViewText(R.id.artist, res.getString(R.string.unknown_artist_name));
    		}
    		views.setProgressBar(R.id.widget_music_mini_progressBar, 1000,
    				mProgress, false);
    		 views.setTextViewText(R.id.currenttime, mcurrenttime);
 	        views.setTextViewText(R.id.totaltime,mtotaltime);
        // Set correct drawable for pause state
        if (isPlaying) {
            views.setImageViewResource(R.id.control_play, R.drawable.rgk_music_btn_pause);
        } else {
            views.setImageViewResource(R.id.control_play, R.drawable.rgk_music_btn_play);
        }
        
        views.setProgressBar(R.id.widget_music_mini_progressBar, 1000,mProgress, false);
        views.setTextViewText(R.id.currenttime, mcurrenttime);
        views.setTextViewText(R.id.totaltime,mtotaltime);
        MusicLogUtils.i(TAG, "performUpdate,Track is " + titleName + 
            " Artist is " + artistName + " Error is " + errorState + " Playing is " + isPlaying); 

        // Link actions buttons to intents
        linkButtons(context, views, isPlaying);
        
        pushUpdate(context, appWidgetIds, views);
      }
    
    
    /**
     * Link up various button actions using {@link PendingIntents}.
     * 
     * @param playerActive True if player is active in background, which means
     *            widget click will launch {@link MediaPlaybackActivity},
     *            otherwise we launch {@link MusicBrowserActivity}.
     */
    private void linkButtons(Context context, RemoteViews views, boolean playerActive) {
        // Connect up various buttons and touch events
        Intent intent;
        PendingIntent pendingIntent;
        final ComponentName serviceName = new ComponentName(context, MediaPlaybackService.class);
        
        if (playerActive) {
            intent = new Intent(context, MediaPlaybackActivity.class);
            pendingIntent = PendingIntent.getActivity(context,
                    0 /* no requestCode */, intent, 0 /* no flag */);
            views.setOnClickPendingIntent(R.id.album_appwidget, pendingIntent);
        } else {
            intent = new Intent(context, MusicBrowserActivity.class);
            pendingIntent = PendingIntent.getActivity(context,
                    0 /* no requestCode */, intent,  0 /* no flag */);
            views.setOnClickPendingIntent(R.id.album_appwidget, pendingIntent);
        }
        //add by lixiaobin 20130515 BUG_ID:JEEY-104 start
        intent = new Intent(MediaPlaybackService.PREVIOUS_ACTION);
        intent.setComponent(serviceName);
        /// M: set this pendingintent to use one time only
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, PendingIntent.FLAG_ONE_SHOT);
        views.setOnClickPendingIntent(R.id.control_prev, pendingIntent);
	//add by lixiaobin 20130515 BUG_ID:JEEY-104 end
        intent = new Intent(MediaPlaybackService.TOGGLEPAUSE_ACTION);
        intent.setComponent(serviceName);
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, 0 /* no flag */);
        views.setOnClickPendingIntent(R.id.control_play, pendingIntent);
        
        intent = new Intent(MediaPlaybackService.NEXT_ACTION);
        intent.setComponent(serviceName);
        /// M: set this pendingintent to use one time only
        pendingIntent = PendingIntent.getService(context,
                0 /* no requestCode */, intent, PendingIntent.FLAG_ONE_SHOT);
        views.setOnClickPendingIntent(R.id.control_next, pendingIntent);
    }

    public static class PackageDataClearedReceiver extends BroadcastReceiver {
        private static final String ACTION_PACKAGE_DATA_CLEARED = "com.mediatek.intent.action.SETTINGS_PACKAGE_DATA_CLEARED";

        public void onReceive(Context context, Intent intent) {
            if (!ACTION_PACKAGE_DATA_CLEARED.equals(intent.getAction())) {
                return;
            }
            String pkgName = intent.getStringExtra("packageName");
            MusicLogUtils.v(TAG, "PackageDataClearedReceiver recevied pkgName = " + pkgName);
            if (pkgName != null && pkgName.equals(context.getPackageName())) {
                MediaAppWidgetProvider mediaAppWidgetProvider = MediaAppWidgetProvider
                        .getInstance();
                if (mediaAppWidgetProvider == null) {
                    MusicLogUtils.v(TAG, "mediaAppWidgetProvider is null ");
                    return;
                }
                mediaAppWidgetProvider.defaultAppWidget(context, null);
            }
        }

    }
}
