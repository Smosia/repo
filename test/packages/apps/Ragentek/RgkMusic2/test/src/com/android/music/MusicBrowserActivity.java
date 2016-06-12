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
 * Copyright (C) 2007 The Android Open Source Project
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
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.SearchManager;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
//A:lixiaobin
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import java.util.List;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.MenuInflater;

import com.mediatek.music.ext.Extensions;
import com.mediatek.music.ext.IMusicTrackBrowser;
import com.mediatek.music.ext.PluginUtils;


public class MusicBrowserActivity extends TabActivity implements MusicUtils.Defs, ServiceConnection, OnTabChangeListener,
        ViewPager.OnPageChangeListener {
    private static final String TAG = "MusicBrowser";
    private static final String ACTIVITY_NAME = PluginUtils.MUSIC_BROWSER_ACTIVITY;

    private static final String ARTIST = "Artist";
    private static final String ALBUM = "Album";
    private static final String SONG = "Song";
    private static final String PLAYLIST = "Playlist";
    private static final String PLAYBACK = "Playback";
    private static final String SAVE_TAB = "activetab";
    static final int ARTIST_INDEX = 0;
    static final int ALBUM_INDEX = 1;
    static final int SONG_INDEX = 2;
    static final int PLAYLIST_INDEX = 3;
    static final int PLAYBACK_INDEX = 4;
    static final int VIEW_PAGER_OFFSCREEN_PAGE_NUM = 3;
    private static final int PLAY_ALL = CHILD_MENU_BASE + 3;

    private static final HashMap<String, Integer> TAB_MAP = new HashMap<String, Integer>(PLAYBACK_INDEX + 1);
    private LocalActivityManager mActivityManager;
    private ViewPager mViewPager;
    private TabHost mTabHost;
    private ArrayList<View> mPagers = new ArrayList<View>(PLAYBACK_INDEX);
    private int mTabCount;
    private int mCurrentTab;
    private MusicUtils.ServiceToken mToken;
    private IMediaPlaybackService mService = null;
    private int mOrientaiton;

	
    /// M: FakeMenu mFakeMenu;
    private View mOverflowMenuButton;
    private PopupMenu mPopupMenu = null;
    private boolean mPopupMenuShowing = false;
    private boolean mHasMenukey = true;
    private int mOverflowMenuButtonId;

    /// M: Whether sdcard is mounted
    private boolean mIsSdcardMounted = true;
    /// M; Indicate whether the searchview is showing
    private boolean mSearchViewShowing = false;
    /// M: Add search button in actionbar when nowplaying not exist
    MenuItem mSearchItem;
//  ImageButton mSearchButton;
    IMusicTrackBrowser mMusicPlugin;
    //a:gaoxueyan 
    ImageView playBtn;
    //A:lixiaobin
    private DrawerLayout drawerLayout;
    private RelativeLayout leftLayout;
    private List<ContentModel> list;
    private ContentAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listView;
    //A DWYBL-1526 yejianming 20150430
    private int mClickPosition = -1;

    //Add DSYYLM-315 yejianming 20160122 (start)
    private static boolean mPermissionReqProcessed = false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    Bundle mSavedInstanceState ;
     //Add DSYYLM-315 yejianming 20160122 (end)

    /// M: Initial tab map hashmap
    static {
        TAB_MAP.put(ARTIST, ARTIST_INDEX);
        TAB_MAP.put(ALBUM, ALBUM_INDEX);
        TAB_MAP.put(SONG, SONG_INDEX);
        TAB_MAP.put(PLAYLIST, PLAYLIST_INDEX);
        TAB_MAP.put(PLAYBACK, PLAYBACK_INDEX);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        PDebug.Start("MusicBrowserActivity.onCreate");
        mSavedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        //Add DSYYLM-315 yejianming 20160122 (start)
        if (getApplicationContext()
          .checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED) {
            requestMusicPermissions();
            mPermissionReqProcessed = false;
        } else {
            mPermissionReqProcessed = true;
            onCreateContinue(mSavedInstanceState);
        }
    }

    private void requestMusicPermissions() {
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_EXTERNAL_STORAGE);
    }

     @Override
     public void onRequestPermissionsResult(int requestCode,
             String permissions[], int[] grantResults) {
            if (requestCode == REQUEST_EXTERNAL_STORAGE) {
                 // If request is cancelled, the result arrays are empty.
                 if (grantResults.length > 0
                     && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                     // permission was granted, yay! Do the
                     // Storage-related task you need to do.
                    mPermissionReqProcessed = true;
                    onCreateContinue(mSavedInstanceState);
                    onResumeContinue();
                 } else {

                     // permission denied, boo! Disable the
                     // functionality that depends on this permission.
                    finish();
                    Toast.makeText(this, R.string.music_storage_permission_deny
                                   , Toast.LENGTH_SHORT).show();
                 }
            }

    }
     
    public void onCreateContinue(Bundle savedInstanceState) {
    //Add DSYYLM-315 yejianming 20160122 (end)
        MusicLogUtils.d(TAG, "onCreate");
        ActionBar actionBar = getActionBar();
	getActionBar().setDisplayHomeAsUpEnabled(true);
	getActionBar().setHomeAsUpIndicator(R.drawable.ic_actionbar_menu);
	getActionBar().setElevation(0);
        //actionBar.hide();
        setContentView(R.layout.main);
	//A:lixiaobin
	drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
	leftLayout = (RelativeLayout) findViewById(R.id.left);
	listView = (ListView) leftLayout.findViewById(R.id.left_listview);
	initData();
	adapter = new ContentAdapter(this, list);
	listView.setAdapter(adapter);
	listView.setOnItemClickListener(new DrawerItemClickListener());
	mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.app_music, R.string.send_file,
				R.string.share_menu) {
	@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
				//getActionBar().setTitle(R.string.send_file);
				//invalidateOptionsMenu();
				//A DWYBL-1526 yejianming 20150430
				afterItemClick(mClickPosition);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
				//getActionBar().setTitle(R.string.share_menu);
				//invalidateOptionsMenu();
			}
	};
	drawerLayout.setDrawerListener(mDrawerToggle);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        PDebug.Start("MusicBrowserActivity.bindToService()");
        mToken = MusicUtils.bindToService(this, this);
        PDebug.End("MusicBrowserActivity.bindToService()");

        mMusicPlugin = Extensions.getPluginObject(getApplicationContext());

        mHasMenukey = ViewConfiguration.get(this).hasPermanentMenuKey();
        PDebug.Start("MusicBrowserActivity.dispatchCreate()");
        mActivityManager = new LocalActivityManager(this, false);
        mActivityManager.dispatchCreate(savedInstanceState);
        PDebug.End("MusicBrowserActivity.dispatchCreate()");

        mTabHost = getTabHost();
        PDebug.Start("MusicBrowserActivity.initTab()");
        initTab();
        PDebug.End("MusicBrowserActivity.initTab()");

        PDebug.Start("MusicBrowserActivity.setCurrentTab()");
        mCurrentTab = MusicUtils.getIntPref(this, SAVE_TAB, ARTIST_INDEX);
        MusicLogUtils.d(TAG, "onCreate mCurrentTab: " + mCurrentTab);
        if ((mCurrentTab < 0) || (mCurrentTab >= mTabCount)) {
            mCurrentTab = ARTIST_INDEX;
        }
        /// M: reset the defalt tab value
        if (mCurrentTab == ARTIST_INDEX) {
            mTabHost.setCurrentTab(ALBUM_INDEX);
        }
        mTabHost.setOnTabChangedListener(this);
        PDebug.End("MusicBrowserActivity.setCurrentTab()");

        PDebug.Start("MusicBrowserActivity.initPager()");
        initPager();
        PDebug.End("MusicBrowserActivity.initPager()");

        PDebug.Start("MusicBrowserActivity.setAdapter()");
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        mViewPager.setAdapter(new MusicPagerAdapter());
        mViewPager.setOnPageChangeListener(this);
        //mViewPager.setOffscreenPageLimit(VIEW_PAGER_OFFSCREEN_PAGE_NUM);
        PDebug.End("MusicBrowserActivity.setAdapter()");
//   a:     gaoxueyan
        playBtn = (ImageView) findViewById(R.id.rgk_nowplaying_play_btn);

        IntentFilter f = new IntentFilter();
        f.addAction(MusicUtils.SDCARD_STATUS_UPDATE);
        registerReceiver(mSdcardstatustListener, f);
// modify bug_id:JWLYLL-109 gaoxueyan 20150214 start
//        createFakeMenu();
// modify bug_id:JWLYLL-109 gaoxueyan 20150214 start
        /// M: Init search button click listener in nowplaying.
//        initSearchButton();
        PDebug.End("MusicBrowserActivity.onCreate");
        //modify bug_id:JLLYLL-153 gaoxueyan 20150403 start
        registerRgkReceiver();
        //modify bug_id:JLLYLL-153 gaoxueyan 20150403 end
        
    }

	//A:lixiaobin
	private void initData() {
		list = new ArrayList<ContentModel>();
		/*list.add(new ContentModel(R.drawable.ic_search_category_music_song, R.string.play_all));
		list.add(new ContentModel(R.drawable.ic_mp_shuffle_off_btn, R.string.shuffle_all));
		list.add(new ContentModel(R.drawable.ic_mp_partyshuffle_on_btn, R.string.party_shuffle));
		list.add(new ContentModel(R.drawable.ic_playlist_move, R.string.effectspanel));*/

		list.add(new ContentModel(R.string.play_all));
		list.add(new ContentModel(R.string.shuffle_all));
		list.add(new ContentModel(R.string.party_shuffle));
		list.add(new ContentModel(R.string.effectspanel));
	}

    //A DWYBL-1526 yejianming 20150430 (start)
    private void afterItemClick(int position){
        Cursor cursor;
        Intent intent;
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                cursor = MusicUtils.query(MusicBrowserActivity.this,
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            new String[] { MediaStore.Audio.Media._ID },
                            MediaStore.Audio.Media.IS_MUSIC + "=1",
                            null,
                            /// M: add for chinese sorting
                            MediaStore.Audio.Media.TITLE_PINYIN_KEY);
                if (cursor != null) {
                    MusicUtils.playAll(MusicBrowserActivity.this, cursor);
                    cursor.close();
                }
                break;
            case 1:
                cursor = MusicUtils.query(MusicBrowserActivity.this,
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            new String[] { MediaStore.Audio.Media._ID },
                            MediaStore.Audio.Media.IS_MUSIC + "=1",
                            null,
                            /// M: add for chinese sorting
                            MediaStore.Audio.Media.TITLE_PINYIN_KEY);
                if (cursor != null) {
                    MusicUtils.shuffleAll(MusicBrowserActivity.this, cursor);
                    cursor.close();
                }
                break;
            case 2:
                MusicUtils.togglePartyShuffle();
                break;
            case 3:
                MusicUtils.startEffectPanel(MusicBrowserActivity.this);
                break;
            default:
                break;
        }
        mClickPosition = -1;
    }
    //A DWYBL-1526 yejianming 20150430 (end)
    
    private class DrawerItemClickListener implements
        ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            //M DWYBL-1526 yejianming 20150430 (start)
            mClickPosition = position;
            drawerLayout.closeDrawer(leftLayout);
            //M DWYBL-1526 yejianming 20150430 (end)
        }
    }

    @Override
    public void onResume() {
        PDebug.Start("MusicBrowserActivity.onResume");
        super.onResume();
        //Add DSYYLM-315 yejianming 20160122 (start)
        if (mPermissionReqProcessed) {
            onResumeContinue();
        }
    }

    public void onResumeContinue() {
        //Add DSYYLM-315 yejianming 20160122 (end)
        MusicLogUtils.d(TAG, "onResume>>>");

        IntentFilter f = new IntentFilter();
        f.addAction(MediaPlaybackService.META_CHANGED);
        registerReceiver(mTrackListListener, f);
		//A:gaoxueyan
        mTrackListListener.onReceive(this, new Intent(MediaPlaybackService.META_CHANGED));
        PDebug.Start("MusicBrowserActivity.setCurrentTab()");
        mTabHost.setCurrentTab(mCurrentTab);
        PDebug.End("MusicBrowserActivity.setCurrentTab()");
        
        PDebug.Start("MusicBrowserActivity.dispatchResume()");
        mActivityManager.dispatchResume();
        PDebug.End("MusicBrowserActivity.dispatchResume()");
        MusicLogUtils.d(TAG, "onResume<<<");
        PDebug.End("MusicBrowserActivity.onResume");
    }


    private void registerRgkReceiver(){
        IntentFilter f = new IntentFilter();
        f.addAction(MediaPlaybackService.PLAYSTATE_CHANGED);
        f.addAction(MediaPlaybackService.META_CHANGED);
        /// M: listen more status to update UI @{
        f.addAction(MediaPlaybackService.QUIT_PLAYBACK);
        f.addAction(Intent.ACTION_SCREEN_ON);
        f.addAction(Intent.ACTION_SCREEN_OFF);
		/// @}
        registerReceiver(mStatusListener, new IntentFilter(f));
	}
    private BroadcastReceiver mStatusListener = new BroadcastReceiver() {
        @SuppressLint("NewApi")
		@Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            MusicLogUtils.d(TAG, "mStatusListener: " + action);
            if (action.equals(MediaPlaybackService.META_CHANGED)) {
                /// M: Refresh option menu when meta change
//                invalidateOptionsMenu();
            Log.i("gxy", "playBtn="+playBtn.toString());	
              MusicUtils.updateNowPlayingViewState(playBtn);

            } else if (action.equals(MediaPlaybackService.PLAYSTATE_CHANGED)) {
            	MusicUtils.updateNowPlayingViewState(playBtn);
            /// M: Handle more status. {@
            } 
        }
    };
// A:   gaoxueyan end
    @Override
    public void onPause() {
        MusicLogUtils.d(TAG, "onPause");
//  A:      gaoxueyan
//        unregisterReceiver(mTrackListListener);
        //Add DSYYLM-315 yejianming 20160122
        if (mPermissionReqProcessed) {
                mActivityManager.dispatchPause(false);
                MusicUtils.setIntPref(this, SAVE_TAB, mCurrentTab);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        //Add DSYYLM-315 yejianming 20160122
        if (mPermissionReqProcessed) {
            if (mPopupMenu != null) {
                mPopupMenu.dismiss();
                mPopupMenuShowing = false;
            }
            mActivityManager.dispatchStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        MusicLogUtils.d(TAG, "onDestroy");
        //Add DSYYLM-315 yejianming 20160122
        if (mPermissionReqProcessed) {
        if (mToken != null) {
            MusicUtils.unbindFromService(mToken);
            mService = null;
        }
        unregisterReceiver(mSdcardstatustListener);
		//A:gaoxueyan
        unregisterReceiver(mTrackListListener);
        //modify bug_id:JLLYLL-153 gaoxueyan 20150403 start
        unregisterReceiver(mStatusListener);
      //modify bug_id:JLLYLL-153 gaoxueyan 20150403  end
        mActivityManager.dispatchDestroy(false);
        }
        super.onDestroy();
    }
	//bug_id:DWYBL-1321 gaoxueyan 20150424 start
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return false;
        }
    	// TODO Auto-generated method stub
    	if (mSearchItem != null && event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        boolean b = true;
        //add try for DWYBL-1446 IllegalStateException Can not perform this action after onSaveInstanceState 20150504
        try {
        	b = super.dispatchKeyEvent(event);
        } catch (Exception e){
        	 MusicLogUtils.e(TAG, "dispatchKeyEvent error:" + e);
        }
        return b;
    }
	//end
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /// M: Get the start activity tab index from intent which set by start activity, so that we can return
        /// result to right activity.
        //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return ;
        }
        int startActivityTab = mCurrentTab;
        if (data != null) {
            startActivityTab = data.getIntExtra(MusicUtils.START_ACTIVITY_TAB_ID, mCurrentTab);
        }
        MusicLogUtils.d(TAG, "onActivityResult: startActivityTab = " + startActivityTab);
        Activity startActivity = mActivityManager.getActivity(getStringId(startActivityTab));
        if (startActivity == null) {
            return;
        }
        switch (startActivityTab) {
            case ARTIST_INDEX:
                ((ArtistAlbumBrowserActivity) startActivity).onActivityResult(requestCode, resultCode, data);
                break;

            case ALBUM_INDEX:
                ((AlbumBrowserActivity) startActivity).onActivityResult(requestCode, resultCode, data);
                break;

            case SONG_INDEX:
                ((TrackBrowserActivity) startActivity).onActivityResult(requestCode, resultCode, data);
                break;

            case PLAYLIST_INDEX:
                ((PlaylistBrowserActivity) startActivity).onActivityResult(requestCode, resultCode, data);
                break;

            default:
                MusicLogUtils.d(TAG, "default");
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return ;
        }
        MusicLogUtils.d(TAG, "onConfigurationChanged>>");
        TabWidget tabWidgetTemp = mTabHost.getTabWidget();
        View tabView;
        Activity activity;
        int viewStatusForTab = View.GONE;

        mOrientaiton = newConfig.orientation;
        if (mOrientaiton == Configuration.ORIENTATION_LANDSCAPE) {
            MusicLogUtils.d(TAG, "onConfigurationChanged--LandScape");
            viewStatusForTab = View.VISIBLE;
        }
        /// M: load tab which is alive only for Landscape;
        for (int i = PLAYBACK_INDEX; i < mTabCount; i++) {
            tabView = tabWidgetTemp.getChildTabViewAt(i);
            if (tabView != null) {
                tabView.setVisibility(viewStatusForTab);
            }
        }
        /// M: notify sub Activity for configuration changed;
        for (int i = 0; i < PLAYBACK_INDEX; i++) {
            activity = mActivityManager.getActivity(getStringId(i));
            if (activity != null) {
                activity.onConfigurationChanged(newConfig);
            }
        }

        if (!mHasMenukey) {
            boolean popupMenuShowing = mPopupMenuShowing;
            if (popupMenuShowing && mPopupMenu != null) {
                mPopupMenu.dismiss();
                MusicLogUtils.d(TAG, "changeFakeMenu:mPopupMenu.dismiss()");
            }
            MusicLogUtils.d(TAG, "changeFakeMenu:popupMenuShowing=" + popupMenuShowing);
//modify bug_id:JWLYLL-109 gaoxueyan 20150214 start
//            createFakeMenu();
//modify bug_id:JWLYLL-109 gaoxueyan 20150214 end
            if (!mSearchViewShowing) {
                mOverflowMenuButton.setEnabled(true);
            }
            if (popupMenuShowing && mOverflowMenuButton != null) {
                mOverflowMenuButton.performClick();
                MusicLogUtils.d(TAG, "changeFakeMenu:performClick()");
            }
        }
        if (mService != null) {
            MusicLogUtils.d(TAG, "mSearchViewShowing:" + mSearchViewShowing);
            //A:gaoxueyan
//            if (mSearchViewShowing) {
//                mSearchButton.setVisibility(View.GONE);
//            } else {
//                mSearchButton.setVisibility(View.VISIBLE);
//            }
            MusicUtils.updateNowPlaying(MusicBrowserActivity.this, mOrientaiton);
            updatePlaybackTab();
        }
        MusicLogUtils.d(TAG, "onConfigurationChanged<<");
    }

    /**
     * M: Create fake menu.
     */
//    modify bug_id:JWLYLL-109 gaoxueyan 20150214 start
//    private void createFakeMenu() {
//        if (mHasMenukey) {
//            MusicLogUtils.d(TAG, "createFakeMenu Quit when there has Menu Key");
//            return;
//        }
//        if (mOrientaiton == Configuration.ORIENTATION_LANDSCAPE) {
//            mOverflowMenuButtonId = R.id.overflow_menu;
//            mOverflowMenuButton = findViewById(R.id.overflow_menu);
//        } else {
//            mOverflowMenuButtonId = R.id.overflow_menu_nowplaying;
//            mOverflowMenuButton = findViewById(R.id.overflow_menu_nowplaying);
//            View parent = (View) mOverflowMenuButton.getParent();
//            if (parent != null) {
//                parent.setVisibility(View.VISIBLE);
//            }
//        }
//        mOverflowMenuButton.setVisibility(View.VISIBLE);
//        mOverflowMenuButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                MusicLogUtils.d(TAG, "createFakeMenu:onClick()");
//                if (v.getId() == mOverflowMenuButtonId) {
//                    final PopupMenu popupMenu = new PopupMenu(MusicBrowserActivity.this, mOverflowMenuButton);
//                    mPopupMenu = popupMenu;
//                    final Menu menu = popupMenu.getMenu();
//                    onCreateOptionsMenu(menu);
//                    popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//                        public boolean onMenuItemClick(MenuItem item) {
//                            return onOptionsItemSelected(item);
//                        }
//                    });
//                    popupMenu.setOnDismissListener(new OnDismissListener() {
//                        public void onDismiss(PopupMenu menu) {
//                            mPopupMenuShowing = false;
//                            MusicLogUtils.d(TAG, "createFakeMenu:onDismiss() called");
//                            return;
//                        }
//                    });
//                    onPrepareOptionsMenu(menu);
//                    mPopupMenuShowing = true;
//                    if (popupMenu != null) {
//                        MusicLogUtils.d(TAG, "createFakeMenu:popupMenu.show()");
//                        popupMenu.show();
//                    }
//                }
//            }
//        });
//    }
//    modify bug_id:JWLYLL-109 gaoxueyan 20150214 end
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return false;
        }
        menu.add(0, PLAY_ALL, 0, R.string.play_all);
        menu.add(0, PARTY_SHUFFLE, 0, R.string.party_shuffle);
        menu.add(0, SHUFFLE_ALL, 0, R.string.shuffle_all);
        menu.add(0, EFFECTS_PANEL, 0, R.string.effects_list_title);        
        /// M: Add search view
        mSearchItem = MusicUtils.addSearchView(this, menu, mQueryTextListener);
        /// M:create menu ADD_FOLDER_TO_PLAY,ADD_FOLDER_AS_PLAYLIST,ADD_SONG_TO_PLAY when plugin need
        Bundle options = new Bundle();
        options.putInt(PluginUtils.TAB_INDEX, mCurrentTab);
        mMusicPlugin.onCreateOptionsMenu(menu, ACTIVITY_NAME, options);
        return true;
    }

    /**
     * M: When edit Text ,do query follow the message of the query
     */
    SearchView.OnQueryTextListener mQueryTextListener = new SearchView.OnQueryTextListener() {
        public boolean onQueryTextSubmit(String query) {
            Intent intent = new Intent();
            intent.setClass(MusicBrowserActivity.this, QueryBrowserActivity.class);
            intent.putExtra(SearchManager.QUERY, query);
            startActivity(intent);
            mSearchItem.collapseActionView();//bug_id:DWYBL-1321 gaoxueyan 20150424
            return true;
        }

        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MusicUtils.setPartyShuffleMenuIcon(menu);
         //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return false;
        }
        super.onPrepareOptionsMenu(menu);
        if (!mIsSdcardMounted) {
            MusicLogUtils.w(TAG, "Sdcard is not mounted, don't show option menu!");
            return false;
        }
        /// M: Only show play all in song activity.
        menu.findItem(PLAY_ALL).setVisible(mCurrentTab == SONG_INDEX);        
        /// M: Show shuffle all in all activity except playlist activity.
        menu.findItem(SHUFFLE_ALL).setVisible(mCurrentTab != PLAYLIST_INDEX);
        /// M: Only show effect menu when effect class is enable.
        MusicUtils.setEffectPanelMenu(getApplicationContext(), menu);
        /// M: Search button can only show on one of place between nowplaying and action bar, when action bar exist,
        /// it should show on action bar, otherwise show on nowplaying, if nowplaying not exist(such as landscape in
        /// MusicBrowserActivity), show it in option menu.
        //mSearchItem.setVisible(mOrientaiton == Configuration.ORIENTATION_LANDSCAPE);
        /// M: show the specific extral option menu when plugin need
        Bundle options = new Bundle();
        options.putInt(PluginUtils.TAB_INDEX, mCurrentTab);
        mMusicPlugin.onPrepareOptionsMenu(menu, ACTIVITY_NAME, options);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Cursor cursor;
        Intent intent;
	//A:lixiaobin
	if(mDrawerToggle.onOptionsItemSelected(item)){
			return true;
	}
        switch (item.getItemId()) {
            case PLAY_ALL:
                cursor = MusicUtils.query(this,
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Audio.Media._ID },
                        MediaStore.Audio.Media.IS_MUSIC + "=1",
                        null,
                        /// M: add for chinese sorting
                        MediaStore.Audio.Media.TITLE_PINYIN_KEY);
                if (cursor != null) {
                    MusicUtils.playAll(this, cursor);
                    cursor.close();
                }
                return true;

            case PARTY_SHUFFLE:
                MusicUtils.togglePartyShuffle();
                return true;

            case SHUFFLE_ALL:
                cursor = MusicUtils.query(this,
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Audio.Media._ID },
                        MediaStore.Audio.Media.IS_MUSIC + "=1",
                        null,
                        /// M: add for chinese sorting
                        MediaStore.Audio.Media.TITLE_PINYIN_KEY);
                if (cursor != null) {
                    MusicUtils.shuffleAll(this, cursor);
                    cursor.close();
                }
                return true;

            case EFFECTS_PANEL:
                return MusicUtils.startEffectPanel(this);

            case R.id.search:
                onSearchRequested();
                mSearchViewShowing = true;
                return true;

            default:
                /// seleted the extral option menu item when plugin need
                Bundle options = new Bundle();
                options.putInt(PluginUtils.TAB_INDEX, mCurrentTab);
                mMusicPlugin.onOptionsItemSelected(getApplicationContext(),
                                                   item,
                                                   ACTIVITY_NAME,
                                                   this,
                                                   null,
                                                   options);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //add bug_id:DWYBL-621 gaoxueyan 20150414 start
    public boolean onSearchRequested() {
        if (mSearchItem != null) {
            mSearchItem.expandActionView();
        }
        return true;
    }
	//end
    /**
     * M: Implements receive track ListListener broadcast
     */
    private BroadcastReceiver mTrackListListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MusicLogUtils.d(TAG, "mTrackListListener");
            if (mService != null) {
			//A:gaoxueyan
                MusicUtils.updateRgkNowPlaying(MusicBrowserActivity.this, mOrientaiton);
                updatePlaybackTab();
            }
        }
    };

    /**
     * M: Implements receive SDCard status broadcast
     */
    private BroadcastReceiver mSdcardstatustListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIsSdcardMounted = intent.getBooleanExtra(MusicUtils.SDCARD_STATUS_ONOFF, false);

            View view;
            if (mIsSdcardMounted) {
                MusicLogUtils.d(TAG, "Sdcard normal");
                view = findViewById(R.id.normal_view);
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
                view = findViewById(R.id.sd_message);
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                view = findViewById(R.id.sd_icon);
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                view = findViewById(R.id.sd_error);
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                /// M: update nowplaying when sdcard mounted
                if (mService != null) {
				//A:gaoxueyan
                    MusicUtils.updateRgkNowPlaying(MusicBrowserActivity.this, mOrientaiton);
                }
            } else {
                MusicLogUtils.d(TAG, "Sdcard error");
                view = findViewById(R.id.normal_view);
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
                view = findViewById(R.id.sd_icon);
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
                TextView testview = (TextView) findViewById(R.id.sd_message);
                if (testview != null) {
                    testview.setVisibility(View.VISIBLE);
                    int message = intent.getIntExtra(MusicUtils.SDCARD_STATUS_MESSAGE, R.string.sdcard_error_message);
                    testview.setText(message);
                }
                view = findViewById(R.id.sd_error);
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    /**
     * get current tab id though index
     *
     * @param index
     * @return
     */
    private String getStringId(int index) {
        String tabStr = ARTIST;
        switch (index) {
            case ALBUM_INDEX:
                tabStr = ALBUM;
                break;
            case SONG_INDEX:
                tabStr = SONG;
                break;
            case PLAYLIST_INDEX:
                tabStr = PLAYLIST;
                break;
            case PLAYBACK_INDEX:
                tabStr = PLAYBACK;
                break;
            case ARTIST_INDEX:
            default:
                MusicLogUtils.d(TAG, "ARTIST_INDEX or default");
                break;
        }
        return tabStr;
    }

    /**
     * initial tab host
     */
    private void initTab() {
         //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return ;
        }
        MusicLogUtils.d(TAG, "initTab>>");
        final TabWidget tabWidget = (TabWidget) getLayoutInflater().inflate(R.layout.buttonbar, null);
        mOrientaiton = getResources().getConfiguration().orientation;
        mTabCount = tabWidget.getChildCount();
        View tabView;
        /// M:remove fake menu
        if (mHasMenukey) {
            mTabCount--;
        }
        for (int i = 0; i < mTabCount; i++) {
            tabView = tabWidget.getChildAt(0);
            if (tabView != null) {
                tabWidget.removeView(tabView);
            }
            MusicLogUtils.d(TAG, "addTab:" + i);
            mTabHost.addTab(mTabHost.newTabSpec(getStringId(i)).setIndicator(tabView).setContent(android.R.id.tabcontent));
        }
        if (mOrientaiton == Configuration.ORIENTATION_PORTRAIT) {
            TabWidget tabWidgetTemp = mTabHost.getTabWidget();
            for (int i = PLAYBACK_INDEX; i < mTabCount; i++) {
                tabView = tabWidgetTemp.getChildTabViewAt(i);
                if (tabView != null) {
                    tabView.setVisibility(View.GONE);
                }
                MusicLogUtils.d(TAG, "set tab gone:" + i);
            }
        }
        MusicLogUtils.d(TAG, "initTab<<");
    }

    /**
     * get current view
     *
     * @param index
     * @return View
     */
    private View getView(int index) {
        MusicLogUtils.d(TAG, "getView>>>index = " + index);
        View view = null;
        Intent intent = new Intent(Intent.ACTION_PICK);
        switch (index) {
            case ARTIST_INDEX:
                intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/artistalbum");
                break;
            case ALBUM_INDEX:
                intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/album");
                break;
            case SONG_INDEX:
                intent.setDataAndType(Uri.EMPTY, "vnd.android.cursor.dir/track");
                break;
            case PLAYLIST_INDEX:
                intent.setDataAndType(Uri.EMPTY, MediaStore.Audio.Playlists.CONTENT_TYPE);
                break;
            default:
                MusicLogUtils.d(TAG, "default");
                return null;
        }
        intent.putExtra("withtabs", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view = mActivityManager.startActivity(getStringId(index), intent).getDecorView();
        MusicLogUtils.d(TAG, "getView<<<");
        return view;
    }

    /**
     * initial view pager
     */
    private void initPager() {
        mPagers.clear();
        View view = null;
        for (int i = 0; i <= PLAYLIST_INDEX; i++) {
            view = (i == mCurrentTab) ? getView(i) : null;
            mPagers.add(view);
        }
    }

    /**
     * update play back tab info
     */
    private void updatePlaybackTab() {
        final int drawalbeTopPostion = 1;
        final int opaqueFull = 255; // 100%
        final int opaqueHalf = 128; // 50%
        TabWidget tabWidgetTemp = mTabHost.getTabWidget();
        TextView tabView = (TextView) tabWidgetTemp.getChildTabViewAt(PLAYBACK_INDEX);
        boolean enable = true;
        long id = -1;
        Drawable[] drawables;
        Drawable drawableTop = null;
        int drawableTopAlpha = opaqueFull;

        if (tabView == null) {
            return;
        }
        try {
            if (mService != null) {
                id = mService.getAudioId();
            }
        }
        catch (RemoteException ex) {
            MusicLogUtils.e(TAG, "updatePlaybackTab getAudioId remote excption:" + ex);
        }
        if (id == -1) {
            enable = false;
            drawableTopAlpha = opaqueHalf;
        }
        tabView.setEnabled(enable);
        drawables = tabView.getCompoundDrawables();
        drawableTop = drawables[drawalbeTopPostion];
        if (drawableTop != null) {
            drawableTop.setAlpha(drawableTopAlpha);
        }
        MusicLogUtils.d(TAG, "updatePlaybackTab:" + enable);
    }

    /**
     * for service connect
     */
    public void onServiceConnected(ComponentName className, IBinder service) {
        //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return ;
        }
        mService = IMediaPlaybackService.Stub.asInterface(service);
        String shuf = getIntent().getStringExtra("autoshuffle");
        if (mService != null) {
            if (Boolean.valueOf(shuf).booleanValue()) {
                try {
                    mService.setShuffleMode(MediaPlaybackService.SHUFFLE_AUTO);
                }
                catch (RemoteException ex) {
                    MusicLogUtils.e(TAG, "onServiceConnected setShuffleMode remote excption:" + ex);
                }
            }
			//A:gaoxueyan
            MusicUtils.updateRgkNowPlaying(MusicBrowserActivity.this, mOrientaiton);
            updatePlaybackTab();
        }
    }

    public void onServiceDisconnected(ComponentName className) {
        //Add DSYYLM-315 yejianming 20160122
        if(!mPermissionReqProcessed) {
            return ;
        }
        mService = null;
        finish();
    }

    /**
     * OnTabChangeListener for TabHost
     *
     * @param tabId
     */
    public void onTabChanged(String tabId) {
        int tabIndex = TAB_MAP.get(tabId);
        MusicLogUtils.d(TAG, "onTabChanged-tabId:" + tabId);
        // MusicLogUtils.d(TAG, "onTabChanged-tabIndex:" + tabIndex);
        if ((tabIndex >= ARTIST_INDEX) && (tabIndex <= PLAYLIST_INDEX)) {
            mViewPager.setCurrentItem(tabIndex);
            mCurrentTab = tabIndex;
        } else if (tabIndex == PLAYBACK_INDEX) {
            Intent intent = new Intent(this, MediaPlaybackActivity.class);
            startActivity(intent);
        }
    }

    /**
     * OnPageChangeListener for ViewPager
     *
     * @param position
     */
    public void onPageSelected(int position) {
        MusicLogUtils.d(TAG, "onPageSelected-position:" + position);
        mTabHost.setCurrentTab(position);
    }

    /**
     * onPageScrolled
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * onPageScrollStateChanged
     *
     * @param state
     */
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * MusicPagerAdapter for scroll page
     */
    private class MusicPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
            ViewPager viewPager = ((ViewPager) container);
            // MusicLogUtils.d(TAG, "destroyItem-position:" + position);
            viewPager.removeView(mPagers.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ViewPager viewPager = ((ViewPager) container);
            View view = mPagers.get(position);
            MusicLogUtils.d(TAG, "instantiateItem-position:" + position);
            if (view == null) {
                view = getView(position);
                mPagers.remove(position);
                mPagers.add(position, view);
                mActivityManager.dispatchResume();
            }
            viewPager.addView(view);
            return mPagers.get(position);
        }

        public int getCount() {
            // MusicLogUtils.d(TAG, "getCount:" + mPagers.size());
            return mPagers.size();
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == null ? false : view.equals(object);
        }
    }

    /**
     * M: init search button, set on click listener and search dialog on dismiss listener, disable search button
     * when search dialog has shown and enable it after dismiss search dialog.
     */
//  \ private void initSearchButton() {
//        mSearchButton = (ImageButton) findViewById(R.id.search_menu_nowplaying);
//        final View blankView = this.findViewById(R.id.blank_between_search_and_overflow);
//        final View nowPlayingView = this.findViewById(R.id.nowplaying);
//        if (mSearchButton != null) {
//            mSearchButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mOverflowMenuButton != null) {
//                        mOverflowMenuButton.setEnabled(false);
//                    }
//                    mSearchButton.setVisibility(View.GONE);
//                    onSearchRequested();
//                    if (blankView.getVisibility() == View.VISIBLE) {
//                        blankView.setVisibility(View.GONE);
//                    }
//                    mSearchViewShowing = true;
//                }
//            });
//            SearchManager searchManager = (SearchManager) this
//                    .getSystemService(Context.SEARCH_SERVICE);
//            searchManager.setOnDismissListener(new SearchManager.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    if (mOverflowMenuButton != null) {
//                        mOverflowMenuButton.setEnabled(true);
//                    }
//                    mSearchButton.setVisibility(View.VISIBLE);
//                    if (nowPlayingView.getVisibility() != View.VISIBLE && !mHasMenukey) {
//                        blankView.setVisibility(View.VISIBLE);
//                    }
//                    mSearchViewShowing = false;
//                    MusicLogUtils.d(TAG, "Search dialog on dismiss, enalbe search button");
//                }
//            });
//        }
//    }
}
