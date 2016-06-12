/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
 */

package com.mediatek.filemanager;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.res.Resources;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import com.mediatek.filemanager.AlertDialogFragment.AlertDialogFragmentBuilder;
import com.mediatek.filemanager.AlertDialogFragment.ChoiceDialogFragment;
import com.mediatek.filemanager.AlertDialogFragment.ChoiceDialogFragmentBuilder;
import com.mediatek.filemanager.AlertDialogFragment.EditDialogFragmentBuilder;
import com.mediatek.filemanager.AlertDialogFragment.EditTextDialogFragment;
import com.mediatek.filemanager.AlertDialogFragment.EditTextDialogFragment.EditTextDoneListener;
import com.mediatek.filemanager.service.FileManagerService;
import com.mediatek.filemanager.service.FileManagerService.OperationEventListener;
import com.mediatek.filemanager.service.ProgressInfo;
import com.mediatek.filemanager.utils.DrmManager;
import com.mediatek.filemanager.utils.FileUtils;
import com.mediatek.filemanager.utils.LogUtils;
import com.mediatek.filemanager.utils.OptionsUtils;
import com.mediatek.filemanager.utils.PDebug;
import com.mediatek.filemanager.utils.PermissionUtils;
import com.mediatek.hotknot.HotKnotAdapter;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216
import android.widget.Toast;
import android.app.Activity;
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager;
import com.mediatek.filemanager.QueryMediaDbTask.BrowseInfo;
import com.mediatek.storage.StorageManagerEx;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
//add BUG_ID:DWYBL-2806 guowen 20151029 (start)
import android.telephony.SubscriptionManager;
//add BUG_ID:DWYBL-2806 guowen 20151029 (end)
/**
 * This is main activity in File manager.
 */
public class FileManagerOperationActivity extends AbsBaseActivity implements
        AdapterView.OnItemLongClickListener, NfcAdapter.CreateBeamUrisCallback {
    static {
        System.setProperty("java.net.preferIPv6Addresses", "false");
    }
    private static final String TAG = "FileManagerOperationActivity";

    public static final String RENAME_EXTENSION_DIALOG_TAG = "rename_extension_dialog_fragment_tag";
    public static final String RENAME_DIALOG_TAG = "rename_dialog_fragment_tag";
    public static final String DELETE_DIALOG_TAG = "delete_dialog_fragment_tag";
    public static final String FORBIDDEN_DIALOG_TAG = "forbidden_dialog_fragment_tag";
    public static final String INTENT_EXTRA_SELECT_PATH = "select_path";
    private static final String NEW_FILE_PATH_KEY = "new_file_path_key";
    private static final String SAVED_SELECTED_PATH_KEY = "saved_selected_path";
    private static final String CURRENT_VIEW_MODE_KEY = "view_mode_key";
    private static final String CURRENT_POSTION_KEY = "current_postion_key";
    private static final String CURRENT_TOP_KEY = "current_top_key";
    private static final String PREF_SORT_BY = "pref_sort_by";
    private static final String DETAIL_INFO_KEY = "detail_info_key";
    private static final String DETAIL_INFO_SIZE_KEY = "detail_info_size_key";
    private static final String SAVED_SELECTED_TOP_KEY = "saved_selected_top_key";
    private static final String TXT_MIME_TYPE = "text/plain";
    private static final String ACTION_HOTKNOT_RECEIVED = "com.mediatek.hotknot.action.FILEMANAGER_FILE_RECEIVED";
    private static final String STRING_HOTKNOT = "HotKnot";
    private static final String HOTKNOT_INTENT_EXTRA = "?intent=com.mediatek.hotknot.action.FILEMANAGER_FILE_RECEIVED&isMimeType=no";

    private static final String TAKE_SCREENSHOT_ACTION = "com.android.intent.take_screenshot";
    
    private static final String ADD_FILE_ACTION = "com.mediatek.filemanager.ADD_FILE";
    
    private static final String WIFI_SETTINGS_ACTION = "android.net.wifi.PICK_WIFI_NETWORK";
    
	//add Bug_id:JLLB-3515 chenchunyong 20140920 (start)
    public static final String EXTRA_SLOTID = "slotid";
    public static final String INTENT_CARD_SELECT = "com.mediatek.gemini.action.SELECT_SIM";
    public static final int REQUEST_SIM_SELECT = 123;
	public static final int REQUEST_SIM_SELECTMMS = 156;
	//add BUG_ID:DWYBL-2806 guowen 20151029 (start) 
    private int simInfos = -1;
	//add BUG_ID:DWYBL-2806 guowen 20151029 (end)
    private int mSimId=-1;
	//add Bug_id:JLLB-3515 chenchunyong 20140920 (end)
    //the max count of files can be share,if too lager,the Binder will failed.
    private static final int MAX_SHARE_FILES_COUNT = 500;
    public static final int MY_PERMISSIONS_REQUEST_TO_READ_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
	public static final int MY_PERMISSIONS_REQUEST_WRITE_READ_PHONE_STATE = 3;
    private boolean showDeleteOnResume = false;
    private boolean showRenameOnResume = false;
    // private static final int BACKGROUND_COLOR = 0xff848284;

    private View mNavigationView = null;
    private boolean mIsConfigChanged = false;
    private int mOrientationConfig;
    private ActionMode mActionMode;
    public final ActionModeCallBack mActionModeCallBack = new ActionModeCallBack();
    private NfcAdapter mNfcAdapter;
    private FileInfo mTxtFile = null;
    
    private FileManagerPagerAdapter mViewPagerAdapter;
    private ViewPagerOnChangeListener mPagerAdapter;
    private RgkBrowseGridView mGridView;
    private GridViewAdapter mGridViewAdapter;
    private RgkListView mRgkListView;
    private View mViewIpAdress;
    private ImageView mImgWifi;
    private Button mBtnStartServer;
    private TextView mTxtWifiName;
    private TextView mTxtIpAdress;
    private TextView mTxtFtpMsg;
    private ArrayList<BrowseInfo> mBrowseList;
    private Map<Integer,ArrayList<BrowseInfo>> mMap;
    private QueryMediaDbTask mQueryMediaDbTask;
    private UpdateBrowseItemTask mUpdateBrowseItemTask;
    private StorageManager mStorageManager = null;
    private FtpServer mFtpServer;
    private String mStrIpAdress;
    private int mTabTxtColorFocus;
    private int mTabTxtColorNormal;
    private String[] mStrBrowseContents;
    private int[] mImageIdResources = new int[]{
            R.drawable.category_icon_picture_light,
            R.drawable.category_icon_music_light,
            R.drawable.category_icon_video_light,
            R.drawable.category_icon_document_light,
            R.drawable.category_icon_record_light,
            R.drawable.category_icon_apk_light,
    };

    private boolean mIsMounted = true;
    private boolean mIsStartServer;
    
    private BroadcastReceiver mLocaleChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	android.util.Log.i("shasha","broadcastReceiver,onReceiver:intent=");
            LogUtils.d(TAG, "LocaleChangedReceiver: intent=");
            if (Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
            	//M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
                mFileInfoManager.clearShowFilesInfoList(mAdapter);
              //M BUG_ID:DWYYL-926 shasha.fang 20150603(end)
                finish();
                AlertDialogFragment af = (AlertDialogFragment) getFragmentManager().
                        findFragmentByTag(DetailInfoListener.DETAIL_DIALOG_TAG);
                String savedDetailInfo = (af != null ? af.getArguments().
                        getString(DETAIL_INFO_KEY) : null);
                if (savedDetailInfo != null && af != null) {
                    if (mAdapter != null && mAdapter.getCheckedItemsCount() == 1) {
                        FileInfo selectFileInfo = mAdapter.getCheckedFileInfoItemsList().get(0);
                        if (selectFileInfo != null) {
                            DetailInfoListener listener = new DetailInfoListener(selectFileInfo);
                            long size = af.getArguments().getLong(DETAIL_INFO_SIZE_KEY);
                            savedDetailInfo = listener.getDetailInfo(size >= 0 ? size : 0);
                        } else {
                            savedDetailInfo = null;
                        }
                    }
                    af.getArguments().putString(DETAIL_INFO_KEY, savedDetailInfo);
                }
            }
        }
    };

    @Override
    public void onEjected(String unMountPoint) {
        super.onEjected(unMountPoint);
        mIsMounted = false;
        stopServer();
        updateUI();
    }

    public ActionMode getActionMode() {
        return mActionMode;
    }

    @Override
    public void onUnMounted(String unMountPoint) {
        LogUtils.d(TAG, "onUnMounted,unMountPoint :" + unMountPoint);
        if (mCurrentPath.startsWith(unMountPoint) || mMountPointManager.isRootPath(mCurrentPath)) {

            if (mAdapter != null && mAdapter.getMode() == FileInfoAdapter.MODE_EDIT
                    && getActionMode() != null) {
                mActionMode.finish();
            }

            ProgressDialogFragment pf = (ProgressDialogFragment) getFragmentManager()
                    .findFragmentByTag(HeavyOperationListener.HEAVY_DIALOG_TAG);
            if (pf != null) {
                pf.dismissAllowingStateLoss();
            }

            // Restore the detail_dialog
            AlertDialogFragment af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(
                    DetailInfoListener.DETAIL_DIALOG_TAG);

            if (af != null) {
                af.dismissAllowingStateLoss();
            }

            // restore delete dialog
            af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(DELETE_DIALOG_TAG);
            if (af != null) {
                af.dismissAllowingStateLoss();
            }

            af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(
                    RENAME_EXTENSION_DIALOG_TAG);
            if (af != null) {
                af.dismissAllowingStateLoss();
            }

            af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(FORBIDDEN_DIALOG_TAG);
            if (af != null) {
                af.dismissAllowingStateLoss();
            }

            ChoiceDialogFragment sortDialogFragment = (ChoiceDialogFragment) getFragmentManager()
                    .findFragmentByTag(ChoiceDialogFragment.CHOICE_DIALOG_TAG);
            if (sortDialogFragment != null) {
                sortDialogFragment.dismissAllowingStateLoss();
            }

            EditTextDialogFragment renameDialogFragment = (EditTextDialogFragment) getFragmentManager()
                    .findFragmentByTag(RENAME_DIALOG_TAG);
            if (renameDialogFragment != null) {
                renameDialogFragment.dismissAllowingStateLoss();
            }
        }

        super.onUnMounted(unMountPoint);
    }

    @Override
    public void onMounted(String mountPoint) {
        updateUiByChanged();
        mIsMounted = true;
        updateUI();
        super.onMounted(mountPoint);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PDebug.Start("FileManagerOperationActivity -- onCreate");

        LogUtils.d(TAG, "onCreate()");
        // get sort by
        mSortType = getPrefsSortBy();
        mOrientationConfig = this.getResources().getConfiguration().orientation;

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            LogUtils.w(TAG, "mNfcAdapter == null");
        } else if (OptionsUtils.isMtkBeamSurpported()) {
            mNfcAdapter.setMtkBeamPushUrisCallback(this, this);
        }
        IntentFilter localeFilter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        registerReceiver(mLocaleChangedReceiver, localeFilter);
        PDebug.End("FileManagerOperationActivity -- onCreate");
        
        if (mStorageManager == null) {
            mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        }
        registerWifiChangeReceiver();

        if(!PermissionUtils.hasStorageReadPermission(getApplicationContext())){
            PermissionUtils.requestPermission(FileManagerOperationActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    MY_PERMISSIONS_REQUEST_TO_READ_EXTERNAL_STORAGE);
        }
        if(!PermissionUtils.hasStorageWritePermission(getApplicationContext())){
            PermissionUtils.requestPermission(FileManagerOperationActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if(!PermissionUtils.hasStorageWritePermission(getApplicationContext())){
            PermissionUtils.requestPermission(FileManagerOperationActivity.this,
                    Manifest.permission.READ_PHONE_STATE,
                    MY_PERMISSIONS_REQUEST_WRITE_READ_PHONE_STATE);
        }
    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        String action = intent == null ? "" : intent.getAction();
        LogUtils.d(TAG, "onResume action: " + action);
        if (mTxtFile != null) {
           if (mTxtFile.getFileLastModifiedTime() != mTxtFile.getNewModifiedTime()) {
               mTxtFile.updateFileInfo();
           }
           mTxtFile = null;
        }
        registerScreenShotReceiver();
        launchQueryDbTask();
        if (action != null && action.equalsIgnoreCase(ADD_FILE_ACTION)) {
            FileUtils.mCurMode = FileUtils.SELECT_FILE_MODE;
        }
        updateUI();
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterScreenShotReceiver();
        cancelQueryDbTask();
        if (FileUtils.mCurMode == FileUtils.SELECT_FILE_MODE) {
            FileUtils.mCurMode = FileUtils.NORMAL_MODE;
        }
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        stopServerSettings();
        stopServer();
        ((FileManagerApplication)FileManagerApplication.getInstance()).clearNotification(this);
        unregisterWifiChangeReceiver();
        super.onDestroy();
    }
    
    @Override
    public Uri[] createBeamUris(NfcEvent event) {
        LogUtils.d(TAG, "Call createBeamUris() in FileManagerOperationActivity.");

        if (!OptionsUtils.isMtkBeamSurpported()) {
            LogUtils.d(TAG, "MtkBeam is not surpport!");
            return null;
        }

        if (!mAdapter.isMode(FileInfoAdapter.MODE_EDIT)) {
            LogUtils.d(TAG, "current mode is not Edit Mode.");
            return null;
        }
        if (mAdapter.getCheckedItemsCount() == 0) {
            LogUtils.d(TAG, "Edit Mode; select count == 0.");
            return null;
        }

        List<FileInfo> fileInfos = null;
        List<Uri> sendFiles = new ArrayList<Uri>();
        fileInfos = mAdapter.getCheckedFileInfoItemsList();
        for (FileInfo fileInfo : fileInfos) { // check if any folder is selected
            if (fileInfo.isDirectory()) {
                showForbiddenDialog(R.string.folder_beam_forbidden_title,
                        R.string.folder_beam_forbidden_message);
                return null;
            }
        }
        for (FileInfo fileInfo : fileInfos) { // check if any Drm file is
            // selected
            if (fileInfo.isDrmFile()
                    && DrmManager.getInstance().isRightsStatus(fileInfo.getFileAbsolutePath())
                    || !fileInfo.getFile().canRead()) {
                showForbiddenDialog(R.string.drm_beam_forbidden_title,
                        R.string.drm_beam_forbidden_message);
                return null;
            }
            sendFiles.add(fileInfo.getUri());
        }
        LogUtils.d(TAG, "The number of sending files is: " + sendFiles.size());
        Uri[] uris = new Uri[sendFiles.size()];
        sendFiles.toArray(uris);
        return uris;
    }

    protected void showForbiddenDialog(int title, int message) {
        LogUtils.d(TAG, "show ForbiddenDialog...");
        if (mIsAlertDialogShowing) {
            LogUtils.d(TAG, "Another Dialog is exist, return!~~");
            return;
        }
        mIsAlertDialogShowing = true;

        AlertDialogFragment forbiddenDialogFragment = (AlertDialogFragment) getFragmentManager()
                .findFragmentByTag(FORBIDDEN_DIALOG_TAG);
        if (forbiddenDialogFragment != null) {
            forbiddenDialogFragment.dismissAllowingStateLoss();
        }
        AlertDialogFragmentBuilder builder = new AlertDialogFragmentBuilder();
        forbiddenDialogFragment = builder.setTitle(title).setIcon(
                R.drawable.ic_dialog_alert_holo_light).setMessage(message).setCancelable(false)
                .setCancelTitle(R.string.ok).create();
        forbiddenDialogFragment.setOnDialogDismissListener(this);
        forbiddenDialogFragment.show(getFragmentManager(), FORBIDDEN_DIALOG_TAG);
        boolean ret = getFragmentManager().executePendingTransactions();
        LogUtils.d(TAG, "executing pending transactions result: " + ret);
    }

    @Override
    protected void serviceConnected() {
        LogUtils.d(TAG, "serviceConnected...");
        super.serviceConnected();
        if (mSavedInstanceState != null) {
            int mode = mSavedInstanceState.getInt(CURRENT_VIEW_MODE_KEY,
                    FileInfoAdapter.MODE_NORMAL);
            int position = mSavedInstanceState.getInt(CURRENT_POSTION_KEY, 0);
            int top = mSavedInstanceState.getInt(CURRENT_TOP_KEY, -1);
            LogUtils.d(TAG, "serviceConnected mode=" + mode);
            restoreViewMode(mode, position, top);
        }
        mListView.setOnItemLongClickListener(this);
        showOverflewButton();
        loadColors();
        loadBrowseData();
        bindWidgets();
        mMap = new HashMap<Integer,ArrayList<BrowseInfo>>();
        mBrowseList = new ArrayList<BrowseInfo>();
        mTabBrowse.setOnClickListener(this);
        mTabFile.setOnClickListener(this);
        mTabFtp.setOnClickListener(this);
        mViewPagerAdapter = new FileManagerPagerAdapter(mViews);
        mPagerAdapter = new ViewPagerOnChangeListener();
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(mPagerAdapter);
    }

    private void restoreViewMode(int mode, int position, int top) {
        if (mode == FileInfoAdapter.MODE_EDIT) {
            mListView.setFastScrollEnabled(false);
            mAdapter.changeMode(mode);
            mActionMode = startActionMode(mActionModeCallBack);
            mActionModeCallBack.updateActionMode();
            String saveSelectedPath = mSavedInstanceState.getString(SAVED_SELECTED_PATH_KEY);
            if (saveSelectedPath != null && !saveSelectedPath.equals("")) {
                mSelectedFileInfo = new FileInfo(saveSelectedPath);
                mSelectedTop = mSavedInstanceState.getInt(SAVED_SELECTED_TOP_KEY);
            }
        } else {
//            mNavigationView.setVisibility(View.VISIBLE);
            mListView.setFastScrollEnabled(true);
            mAdapter.changeMode(FileInfoAdapter.MODE_NORMAL);
            invalidateOptionsMenu();
        }
        mListView.setSelectionFromTop(position, top);
    }

    protected void restoreDialog() {
        // Restore the heavy_dialog : pasting deleting
        ProgressDialogFragment pf = (ProgressDialogFragment) getFragmentManager()
                .findFragmentByTag(HeavyOperationListener.HEAVY_DIALOG_TAG);
        if (pf != null) {
            if (mService.isBusy(this.getClass().getName())
                    && mService.isHeavyOperationTask(this.getClass().getName())) {
                HeavyOperationListener listener = new HeavyOperationListener(
                        AlertDialogFragment.INVIND_RES_ID);
                mService.reconnected(this.getClass().getName(), listener);
                pf.setCancelListener(listener);
            } else {
                pf.dismissAllowingStateLoss(); }
        }

        String saveSelectedPath = mSavedInstanceState.getString(SAVED_SELECTED_PATH_KEY);
        FileInfo saveSelectedFile = null;
        if (saveSelectedPath != null) {
            saveSelectedFile = new FileInfo(saveSelectedPath);
        }

        // Restore the detail_dialog
        AlertDialogFragment af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(
                DetailInfoListener.DETAIL_DIALOG_TAG);

        if (af != null && saveSelectedFile != null && mService != null) {
            DetailInfoListener listener = new DetailInfoListener(saveSelectedFile);
            af.setDismissListener(listener);
            String savedDetailInfo = af.getArguments().getString(DETAIL_INFO_KEY);
            if (mService.isBusy(this.getClass().getName()) && mService.isDetailTask(this.getClass().getName())) {
                mService.reconnected(this.getClass().getName(), listener);
            } else if (savedDetailInfo != null && !savedDetailInfo.equals("")) {
                TextView mDetailsText = (TextView) af.getDialog().findViewById(R.id.details_text);
                if (mDetailsText != null) {
                    mDetailsText.setText(savedDetailInfo);
                }
            } else if (!mService.isBusy(this.getClass().getName())) {
                af.dismissAllowingStateLoss();
                mService.getDetailInfo(this.getClass().getName(), saveSelectedFile, listener);
            } else {
                af.dismissAllowingStateLoss();
            }
        } else if (af != null && saveSelectedFile == null) {
            af.dismissAllowingStateLoss();
            mIsAlertDialogShowing = false;
        }

        // restore delete dialog
        af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(DELETE_DIALOG_TAG);
        if (af != null) {
            af.setOnDoneListener(new DeleteListener());
        }
        // rename Ext Dialog.
        af = (AlertDialogFragment) getFragmentManager().findFragmentByTag(
                RENAME_EXTENSION_DIALOG_TAG);
        if (af != null) {
            String newFilePath = af.getArguments().getString(NEW_FILE_PATH_KEY);
            if (newFilePath != null && saveSelectedFile != null) {
                af.setOnDoneListener(new RenameExtensionListener(saveSelectedFile, newFilePath));
            }
        }

        ChoiceDialogFragment sortDialogFragment = (ChoiceDialogFragment) getFragmentManager()
                .findFragmentByTag(ChoiceDialogFragment.CHOICE_DIALOG_TAG);
        if (sortDialogFragment != null) {
            sortDialogFragment.setItemClickListener(new SortClickListner());
        }

        EditTextDialogFragment renameDialogFragment = (EditTextDialogFragment) getFragmentManager()
                .findFragmentByTag(RENAME_DIALOG_TAG);
        if (renameDialogFragment != null && saveSelectedFile != null) {
            renameDialogFragment
                    .setOnEditTextDoneListener(new RenameDoneListener(saveSelectedFile));
        }
        super.restoreDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //M:DWYBL-3254 guoshuai 20151202(start)
        //if (mAdapter != null && mAdapter.getCheckedItemsCount() == 1) {
        int checkedItemCountNeedCheck = -1;
        try {
            checkedItemCountNeedCheck = mAdapter.getCheckedItemsCount();
        } catch (java.util.ConcurrentModificationException e) {
            e.printStackTrace ();
        }
        if (mAdapter != null && checkedItemCountNeedCheck == 1) {
        //M:DWYBL-3254 guoshuai 20151202(end)
            FileInfo selectFileInfo = mAdapter.getCheckedFileInfoItemsList().get(0);
            if (selectFileInfo != null) {
                outState.putString(SAVED_SELECTED_PATH_KEY, selectFileInfo.getFileAbsolutePath());
                int pos = mAdapter.getPosition(selectFileInfo);
                LogUtils.d(TAG, "onSaveInstanceSteate selected pos: " + pos);
                View view = mListView.getChildAt(pos);
                int top = -1;
                if (view != null) {
                    top = view.getTop();
                }
                outState.putInt(SAVED_SELECTED_TOP_KEY, top);
            }
        }
        int currentMode = (mAdapter != null) ? mAdapter.getMode() : FileInfoAdapter.MODE_NORMAL;
        outState.putInt(CURRENT_VIEW_MODE_KEY, currentMode);
        if (mListView.getChildCount() > 0) {
            View view = mListView.getChildAt(0);
            if (null == view) {
                LogUtils.d(TAG, "get child at first is null.");
            } else {
                int position = (mListView.getPositionForView(view));
                int top = view.getTop();
                outState.putInt(CURRENT_POSTION_KEY, position);
                outState.putInt(CURRENT_TOP_KEY, top);
            }
        }
    }

    @Override
    protected void setMainContentView() {
        setContentView(R.layout.filemanager_main);

        ActionBar actionBar = getActionBar();
//        if (actionBar != null) {
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View customActionBarView = inflater.inflate(R.layout.actionbar, null);
//
//            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
//                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
//                            | ActionBar.DISPLAY_SHOW_TITLE);
//
//            mNavigationView = customActionBarView.findViewById(R.id.bar_background);
//
//            actionBar.setCustomView(customActionBarView);
//            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.title_bar_bg));
//            actionBar.setSplitBackgroundDrawable(getResources().getDrawable(
//                    R.drawable.actionbar_background));
//        }
    }
    
    /**
     * This method switches edit view to navigation view
     */
    private void switchToNavigationView() {
        LogUtils.d(TAG, "Switch to navigation view");
//        mNavigationView.setVisibility(View.VISIBLE);
        mListView.setFastScrollEnabled(true);

        mAdapter.changeMode(FileInfoAdapter.MODE_NORMAL);
        invalidateOptionsMenu();
    }

    private void switchToEditView(int position, int top) {
        LogUtils.d(TAG, "switchToEditView position and top" + position + "/" + top);
        mAdapter.setChecked(position, true);
        mListView.setSelectionFromTop(position, top);
        switchToEditView();
    }

    private void switchToEditView() {
        LogUtils.d(TAG, "Switch to edit view");
        mListView.setFastScrollEnabled(false);
        mAdapter.changeMode(FileInfoAdapter.MODE_EDIT);
        mActionMode = startActionMode(mActionModeCallBack);
        mActionModeCallBack.updateActionMode();
    }

    private void hotknotShare() {
        List<FileInfo> files = null;
        if (mAdapter.isMode(FileInfoAdapter.MODE_EDIT)) {
            files = mAdapter.getCheckedFileInfoItemsList();
        } else {
            LogUtils.w(TAG, "Maybe dispatch events twice, view mode error.");
            return;
        }
        boolean forbidden = false;

        if (files.size() >= 1) {
            for (FileInfo info : files) {
                if (info.isDrmFile()
                        && DrmManager.getInstance().isRightsStatus(info.getFileAbsolutePath())) {
                    forbidden = true;
                    break;
                }
            }
            if (forbidden) {
                showForbiddenDialog(com.mediatek.internal.R.string.drm_forwardforbidden_title,
                        com.mediatek.internal.R.string.drm_forwardforbidden_message);
                return;
            }
            Uri[] uris = new Uri[files.size()];
            String fPath = files.get(0).getFileAbsolutePath();
            for (int i = 0; i < uris.length; i++) {
                if (0 == i) {
                    uris[i] = Uri.parse("file://" + fPath + HOTKNOT_INTENT_EXTRA);
                } else {
                    uris[i] = Uri.fromFile(files.get(i).getFile());
                }
            }
            Intent sIntent = new Intent();
            sIntent.setAction("com.mediatek.hotknot.action.SHARE");
            sIntent.putExtra("com.mediatek.hotknot.extra.SHARE_URIS", uris);
            try {
                FileManagerOperationActivity.this.startActivity(sIntent);
                if (getActionMode() != null) {
                    mActionMode.finish();
                }
            } catch (ActivityNotFoundException e) {
                LogUtils.d(TAG, "hotknot share activity not found");
            }
            return;
        }
    }
    /**
     * The method shares the files/folders MMS: support only single files BT:
     * support single and multiple files
     */
    private void share() {
        Intent intent;
        boolean forbidden = false;
        List<FileInfo> files = null;
        ArrayList<Parcelable> sendList = new ArrayList<Parcelable>();

        if (mAdapter.isMode(FileInfoAdapter.MODE_EDIT)) {
            files = mAdapter.getCheckedFileInfoItemsList();
        } else {
            LogUtils.w(TAG, "Maybe dispatch events twice, view mode error.");
            return;
        }

        if (files.size() > 1) {
            // send multiple files
            LogUtils.d(TAG, "Share multiple files");
            for (FileInfo info : files) {
                if (info.isDrmFile()
                        && DrmManager.getInstance().isRightsStatus(info.getFileAbsolutePath())) {
                    forbidden = true;
                    break;
                }

                sendList.add(info.getUri());
            }

            if (!forbidden) {
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType(FileUtils.getMultipleMimeType(mService, mCurrentPath, files));
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, sendList);

                try {
                    startActivity(Intent.createChooser(intent, getString(R.string.send_file)));
                } catch (android.content.ActivityNotFoundException e) {
                    LogUtils.e(TAG, "Cannot find any activity", e);
                    // TODO add a toast to notify user; get a function from here
                    // and if(!forbidden)
                    // below
                }
            }
        } else {
            // send single file
            LogUtils.d(TAG, "Share a single file");
            FileInfo fileInfo = files.get(0);
            String mimeType = fileInfo.getFileMimeType(mService);

            if (fileInfo.isDrmFile()
                    && DrmManager.getInstance().isRightsStatus(fileInfo.getFileAbsolutePath())) {
                forbidden = true;
            }

            if (mimeType == null || mimeType.startsWith("unknown")) {
                mimeType = FileInfo.MIMETYPE_UNRECOGNIZED;
            }

            if (!forbidden) {
                intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType(mimeType);
                Uri uri = Uri.fromFile(fileInfo.getFile());
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                LogUtils.d(TAG, "Share Uri file: " + uri);
                LogUtils.d(TAG, "Share file mimetype: " + mimeType);

                try {
                    startActivity(Intent.createChooser(intent, getString(R.string.send_file)));
                } catch (android.content.ActivityNotFoundException e) {
                    LogUtils.e(TAG, "Cannot find any activity", e);
                    // TODO add a toast to notify user
                }
            }
        }

        if (forbidden) {
            showForbiddenDialog(com.mediatek.internal.R.string.drm_forwardforbidden_title,
                    com.mediatek.internal.R.string.drm_forwardforbidden_message);
        } else {
            if (mActionMode != null) {
                mActionMode.finish();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.d(TAG, "onItemClick, position = " + position);
        if (mService != null && mService.isBusy(this.getClass().getName())) {
            LogUtils.d(TAG, "onItemClick, service is busy,return. ");
            return;
        }
        if (mAdapter.isMode(FileInfoAdapter.MODE_NORMAL)) {
            LogUtils.d(TAG, "onItemClick,Selected position: " + position);

            if (position >= mAdapter.getCount() || position < 0) {
                LogUtils.e(TAG, "onItemClick,events error,mFileInfoList.size(): "
                        + mAdapter.getCount());
                return;
            }
            FileInfo selecteItemFileInfo = (FileInfo) mAdapter.getItem(position);

            if (selecteItemFileInfo.isDirectory()) {
                int top = view.getTop();
                LogUtils.v(TAG, "onItemClick,fromTop = " + top);
                addToNavigationList(mCurrentPath, selecteItemFileInfo, top);
                showDirectoryContent(selecteItemFileInfo.getFileAbsolutePath());
            } else {
                // open file here
                if (FileUtils.mCurMode == FileUtils.SELECT_FILE_MODE) {
                    FileUtils.mCurMode = FileUtils.NORMAL_MODE;
                    Intent intent = new Intent();
                    Uri uri = selecteItemFileInfo.getUri();
                    LogUtils.d(TAG, "onItemClick RESULT_OK, uri : " + uri);
                    intent.setData(uri);
                    setResult(RESULT_OK, intent);
                    finish();
                    return;
                }
                boolean canOpen = true;
                String mimeType = selecteItemFileInfo.getFileMimeType(mService);

                if (selecteItemFileInfo.isDrmFile()) {
                    mimeType = DrmManager.getInstance().getOriginalMimeType(
                            selecteItemFileInfo.getFileAbsolutePath());

                    if (TextUtils.isEmpty(mimeType)) {
                        canOpen = false;
                        mToastHelper.showToast(R.string.msg_unable_open_file);
                    }
                }

                if (canOpen) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = selecteItemFileInfo.getUri();
                    LogUtils.d(TAG, "onItemClick,Open uri file: " + uri);
					//add JWYYL-591 dzl 20141223 start
	                uri=FileUtils.tryContentMediaUri(getApplicationContext(), uri);
					//add JWYYL-591 dzl 20141223 end 
                    intent.setDataAndType(uri, mimeType);
                    if (mimeType != null && mimeType.equals(TXT_MIME_TYPE)) {
                        mTxtFile = selecteItemFileInfo;
                    }

                    try {
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException e) {
                        mTxtFile = null;
                        mToastHelper.showToast(R.string.msg_unable_open_file);
                        LogUtils.w(TAG, "onItemClick,Cannot open file: "
                                + selecteItemFileInfo.getFileAbsolutePath());
                    }
                }
            }
        } else {
            LogUtils.d(TAG, "onItemClick,edit view .");
            boolean state = mAdapter.getItem(position).isChecked();
            mAdapter.setChecked(position, !state);
            mActionModeCallBack.updateActionMode();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        if (mService.isBusy(this.getClass().getName())) {
            LogUtils.d(TAG, "onClick, service is busy,return.");
            return;
        }
        int id = view.getId();
        LogUtils.d(TAG, "onClick,id: " + id);

        boolean isMounted = mMountPointManager.isRootPathMount(mCurrentPath);
        if (mAdapter.isMode(FileInfoAdapter.MODE_EDIT) && isMounted) {
            mActionModeCallBack.updateActionMode();
            LogUtils.d(TAG, "onClick,retuen.");
            return;
        }
        switch (view.getId()) {
        case R.id.browse:
            mViewPager.setCurrentItem(FileUtils.TAB_ON_BROWSE);
            break;
        case R.id.file:
            mViewPager.setCurrentItem(FileUtils.TAB_ON_FILE);
            break;
        case R.id.ftp:
            mViewPager.setCurrentItem(FileUtils.TAB_ON_FTP);
            break;
        case R.id.rgk_file_ftp_btn_start_server:
            mIsStartServer = !mIsStartServer;
            updateUI();
            break;
        case R.id.rgk_file_ftp_img_wifi:
            jumpToWifiSettings();
            break;
        }
        super.onClick(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtils.d(TAG, "onCreateOptionsMenu...");
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        menu.clear();
        if (mService == null) {
            LogUtils.i(TAG, "onCreateOptionsMenu, invalid service,return true.");
            return true;
        }
        inflater.inflate(R.menu.navigation_view_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtils.d(TAG, "onPrepareOptionsMenu...");
        //here must return, otherwise menu item will null(because of no inflate menu
        //onCreateOptionsMenu() and this leads null pointer exception
        if (null == mService) {
            LogUtils.i(TAG, "onPrepareOptionsMenu, invalid service,return true.");
            return true;
        }
        if (FileUtils.mCurMode == FileUtils.SELECT_FILE_MODE
                || mViewPager.getCurrentItem() == FileUtils.TAB_ON_FTP) {
            menu.findItem(R.id.create_folder).setVisible(false);
            menu.findItem(R.id.change_mode).setVisible(false);
            menu.findItem(R.id.hide).setVisible(false);
            menu.findItem(R.id.sort).setVisible(false);
            menu.findItem(R.id.paste).setVisible(false);
            menu.findItem(R.id.search).setVisible(false);
            return true;
        }
        if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_BROWSE) {
            menu.findItem(R.id.create_folder).setVisible(false);
            menu.findItem(R.id.change_mode).setVisible(false);
            menu.findItem(R.id.hide).setVisible(false);
            menu.findItem(R.id.sort).setVisible(false);
            menu.findItem(R.id.paste).setVisible(false);
            return true;
        }
        menu.findItem(R.id.create_folder).setVisible(true);
        menu.findItem(R.id.change_mode).setVisible(true);
        menu.findItem(R.id.hide).setVisible(true);
        menu.findItem(R.id.sort).setVisible(true);
        menu.findItem(R.id.paste).setVisible(true);
        LogUtils.d(TAG, "onPrepareOptionsMenu index: "+mViewPager.getCurrentItem());
        if (mCurrentPath != null && mMountPointManager.isRootPath(mCurrentPath)) {
            menu.findItem(R.id.create_folder).setEnabled(false);
            menu.findItem(R.id.search).setEnabled(true);
            // more items
            menu.findItem(R.id.change_mode).setEnabled(false);
            if (getPrefsShowHidenFile()) {
                menu.findItem(R.id.hide).setTitle(R.string.hide_file);
            } else {
                menu.findItem(R.id.hide).setTitle(R.string.show_file);
            }
            menu.findItem(R.id.sort).setEnabled(true);
            menu.findItem(R.id.paste).setVisible(false);
            menu.findItem(R.id.paste).setEnabled(false);
            return true;
        }
        if (mFileInfoManager != null && mFileInfoManager.getPasteCount() > 0
                && FileUtils.mCurTabIndex != FileUtils.TAB_ON_BROWSE_ITEM) {
            menu.findItem(R.id.paste).setVisible(true);
            menu.findItem(R.id.paste).setEnabled(true);
        } else {
            menu.findItem(R.id.paste).setVisible(false);
            menu.findItem(R.id.paste).setEnabled(false);
        }
        if (mCurrentPath != null && !(new File(mCurrentPath)).canWrite()) {
            menu.findItem(R.id.create_folder).setEnabled(false);
            menu.findItem(R.id.paste).setVisible(false);
        } else {
            if (FileUtils.mCurTabIndex == FileUtils.TAB_ON_BROWSE_ITEM) {
                menu.findItem(R.id.create_folder).setEnabled(false);
            } else {
                menu.findItem(R.id.create_folder).setEnabled(true);
            }
        }

        if (mAdapter != null && mAdapter.getCount() == 0) {
            menu.findItem(R.id.search).setEnabled(false);
        } else {
            menu.findItem(R.id.search).setEnabled(true);
        }
        // more items
        if (getPrefsShowHidenFile()) {
            menu.findItem(R.id.hide).setTitle(R.string.hide_file);
        } else {
            menu.findItem(R.id.hide).setTitle(R.string.show_file);
        }
        if ((mAdapter != null && mAdapter.getCount() == 0)
                || (mCurrentPath != null && mMountPointManager.isRootPath(mCurrentPath))) {
            menu.findItem(R.id.change_mode).setEnabled(false);
        } else {
            menu.findItem(R.id.change_mode).setEnabled(true);
        }

        if (mActionMode != null && mActionModeCallBack != null) {
            mActionModeCallBack.updateActionMode();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.d(TAG, "onOptionsItemSelected: " + item.getItemId());

        if (mService != null && mService.isBusy(this.getClass().getName())) {
            LogUtils.i(TAG, "onOptionsItemSelected,service is busy. ");
            return true;
        }

        switch (item.getItemId()) {
        case R.id.create_folder:
            showCreateFolderDialog();
            break;
        case R.id.search:
            Intent intent = new Intent();
            intent.setClass(this, FileManagerSearchActivity.class);
            intent.putExtra(FileManagerSearchActivity.CURRENT_PATH, mCurrentPath);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            break;
        case R.id.paste:
            if (mService != null) {
			//add BUG_ID:JWLWKK-239 zhaolei.ding 20140318 (start)
			int titleID = (mFileInfoManager.getPasteType() == FileInfoManager.PASTE_MODE_CUT) ? R.string.cuting :  R.string.pasting;
            //add BUG_ID:JWLWKK-239 zhaolei.ding 20140318 (end)
                mService.pasteFiles(this.getClass().getName(), mFileInfoManager.getPasteList(),
                        mCurrentPath, mFileInfoManager.getPasteType(), new HeavyOperationListener(
						//add BUG_ID:JWLWKK-239 zhaolei.ding 20140318 (start)
                                //R.string.pasting));
				titleID));
						//add BUG_ID:JWLWKK-239 zhaolei.ding 20140318 (end)
            }
            break;
        case R.id.sort:
            showSortDialog();
            break;
        case R.id.hide:
            if (mService != null) {
                mService.setListType(
                        changePrefsShowHidenFile() ? FileManagerService.FILE_FILTER_TYPE_DEFAULT
                                : FileManagerService.FILE_FILTER_TYPE_ALL, this.getClass()
                                .getName());
                mService.listFiles(this.getClass().getName(), mCurrentPath, new ListListener());
            }
            break;
        case R.id.change_mode:
            switchToEditView();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * This method switches edit view to navigation view
     *
     * @param refresh
     *            whether to refresh the screen after the switch is done
     */
    private void sortFileInfoList() {
        LogUtils.d(TAG, "Start sortFileInfoList()");

        int selection = mListView.getFirstVisiblePosition(); // save current
        // visible position

        // refresh only when paste or delete operation is performed
        mFileInfoManager.sort(mSortType);
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(selection);
        // restore the selection in the navigation view

        LogUtils.d(TAG, "End sortFileInfoList()");
    }

    /**
     * This method sets the sorting type in the preference
     *
     * @param sort
     *            the sorting type
     */
    private void setPrefsSortBy(int sort) {
        mSortType = sort;
        Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt(PREF_SORT_BY, sort);
        editor.commit();
    }

    /**
     * This method gets the sorting type from the preference
     *
     * @return the sorting type
     */
    private int getPrefsSortBy() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return prefs.getInt(PREF_SORT_BY, 0);
    }

    protected void showDeleteDialog() {
        LogUtils.d(TAG, "show DeleteDialog...");
        if (mIsAlertDialogShowing) {
            LogUtils.d(TAG, "Another Dialog is exist, return!~~");
            return;
        }
        int alertMsgId = R.string.alert_delete_multiple;
        if (mAdapter.getCheckedItemsCount() == 1) {
            alertMsgId = R.string.alert_delete_single;
        } else {
            alertMsgId = R.string.alert_delete_multiple;
        }

        if (isResumed()) {
            mIsAlertDialogShowing = true;
            AlertDialogFragmentBuilder builder = new AlertDialogFragmentBuilder();
            AlertDialogFragment deleteDialogFragment = builder.setMessage(alertMsgId).setDoneTitle(
                    R.string.ok).setCancelTitle(R.string.cancel).setIcon(
                    R.drawable.ic_dialog_alert_holo_light).setTitle(R.string.delete).create();
            deleteDialogFragment.setOnDoneListener(new DeleteListener());
            deleteDialogFragment.setOnDialogDismissListener(this);
            deleteDialogFragment.show(getFragmentManager(), DELETE_DIALOG_TAG);
            boolean ret = getFragmentManager().executePendingTransactions();
            LogUtils.d(TAG, "executing pending transactions result: " + ret);
        }
    }

    private class DeleteListener implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            LogUtils.d(TAG, "onClick() method for alertDeleteDialog, OK button");
            if (mService != null) {
                mService.deleteFiles(FileManagerOperationActivity.this.getClass().getName(),
                        mAdapter.getCheckedFileInfoItemsList(), new HeavyOperationListener(
                                R.string.deleting));
            }
            if (mActionMode != null) {
                mActionMode.finish();
            }
        }
    }

    /**
     * The method creates an alert delete dialog
     *
     * @param args
     *            argument, the boolean value who will indicates whether the
     *            selected files just only one. The prompt message will be
     *            different.
     * @return a dialog
     */
    protected void showRenameExtensionDialog(FileInfo srcfileInfo, final String newFilePath) {
        LogUtils.d(TAG, "show RenameExtensionDialog...");

        AlertDialogFragmentBuilder builder = new AlertDialogFragmentBuilder();
        AlertDialogFragment renameExtensionDialogFragment = builder.setTitle(
                R.string.confirm_rename).setIcon(R.drawable.ic_dialog_alert_holo_light).setMessage(
                R.string.msg_rename_ext).setCancelTitle(R.string.cancel).setDoneTitle(R.string.ok)
                .create();
        renameExtensionDialogFragment.getArguments().putString(NEW_FILE_PATH_KEY, newFilePath);
        renameExtensionDialogFragment.setOnDoneListener(new RenameExtensionListener(srcfileInfo,
                newFilePath));
        renameExtensionDialogFragment.show(getFragmentManager(), RENAME_EXTENSION_DIALOG_TAG);
        boolean ret = getFragmentManager().executePendingTransactions();
        LogUtils.d(TAG, "executing pending transactions result: " + ret);
    }

    private class RenameExtensionListener implements OnClickListener {
        private final String mNewFilePath;
        private final FileInfo mSrcFile;

        public RenameExtensionListener(FileInfo fileInfo, String newFilePath) {
            mNewFilePath = newFilePath;
            mSrcFile = fileInfo;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (mService != null) {
                if (mActionMode != null) {
                    mActionMode.finish();
                }
                mService.rename(FileManagerOperationActivity.this.getClass().getName(), mSrcFile,
                        new FileInfo(mNewFilePath), new LightOperationListener(FileUtils
                                .getFileName(mNewFilePath)));
            }
        }

    }

    /**
     * The method creates an alert sort dialog
     *
     * @return a dialog
     */
    protected void showSortDialog() {
        LogUtils.d(TAG, "show SortDialog...");
        if (mIsAlertDialogShowing) {
            LogUtils.d(TAG, "Another Dialog is exist, return!~~");
            return;
        }

        if (isResumed()) {
            mIsAlertDialogShowing = true;
            ChoiceDialogFragmentBuilder builder = new ChoiceDialogFragmentBuilder();
            builder.setDefault(R.array.sort_by, mSortType).setTitle(R.string.sort_by).setCancelTitle(
                    R.string.cancel);
            ChoiceDialogFragment sortDialogFragment = builder.create();
            sortDialogFragment.setItemClickListener(new SortClickListner());
            sortDialogFragment.setOnDialogDismissListener(this);
            sortDialogFragment.show(getFragmentManager(), ChoiceDialogFragment.CHOICE_DIALOG_TAG);
            boolean ret = getFragmentManager().executePendingTransactions();
            LogUtils.d(TAG, "executing pending transactions result: " + ret);
        }
    }

    private class SortClickListner implements OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            if (id != mSortType) {
                setPrefsSortBy(id);
                dialog.dismiss();
                sortFileInfoList();
            }
        }

    }

    protected void showRenameDialog() {
        LogUtils.d(TAG, "show RenameDialog...");
        if (mIsAlertDialogShowing) {
            LogUtils.d(TAG, "Another Dialog showing, return!~~");
            return;
        }
        FileInfo fileInfo = mAdapter.getFirstCheckedFileInfoItem();
        int selection = 0;
        if (fileInfo != null) {
            String name = fileInfo.getFileName();
            String fileExtension = FileUtils.getFileExtension(name);
            selection = name.length();
            if (!fileInfo.isDirectory() && fileExtension != null) {
                selection = selection - fileExtension.length() - 1;
            }
            if (isResumed()) {
                mIsAlertDialogShowing = true;
                EditDialogFragmentBuilder builder = new EditDialogFragmentBuilder();
                builder.setDefault(name, selection).setDoneTitle(R.string.done).setCancelTitle(
                        R.string.cancel).setTitle(R.string.rename);
                EditTextDialogFragment renameDialogFragment = builder.create();
                renameDialogFragment.setOnEditTextDoneListener(new RenameDoneListener(fileInfo));
                renameDialogFragment.setOnDialogDismissListener(this);
                renameDialogFragment.show(getFragmentManager(), RENAME_DIALOG_TAG);
                boolean ret = getFragmentManager().executePendingTransactions();
                LogUtils.d(TAG, "executing pending transactions result: " + ret);
           }
        }
    }

    protected class RenameDoneListener implements EditTextDoneListener {
        FileInfo mSrcfileInfo;

        public RenameDoneListener(FileInfo srcFile) {
            mSrcfileInfo = srcFile;
        }

        @Override
        public void onClick(String text) {
            String newFilePath = mCurrentPath + MountPointManager.SEPARATOR + text;
            if (null == mSrcfileInfo) {
                LogUtils.w(TAG, "mSrcfileInfo is null.");
                return;
            }
            if (FileUtils.mCurTabIndex == FileUtils.TAB_ON_BROWSE_ITEM) {
                String srcFilePath = mSrcfileInfo.getFileAbsolutePath();
                if (!TextUtils.isEmpty(srcFilePath)) {
                    int size = srcFilePath.length();
                    int index = srcFilePath.lastIndexOf("/");
                    if (index < size) {
                        String storage = srcFilePath.substring(0, index);
                        if (!mCurrentPath.equals(storage)) {
                            mCurPathFromBrowseInRename = storage;
                            newFilePath = storage + MountPointManager.SEPARATOR + text;
                        }
                    }
                }
            }
	//modify JSLEL-251  zhaolei.ding 20140917 start
            //if (FileUtils.isExtensionChange(newFilePath, mSrcfileInfo.getFileAbsolutePath())) {
            if (text.trim().indexOf(".") == 0 || FileUtils.isExtensionChange(newFilePath, mSrcfileInfo.getFileAbsolutePath())) {
	//modify JSLEL-251  zhaolei.ding 20140917 end
                showRenameExtensionDialog(mSrcfileInfo, newFilePath);
            } else {
                if (mService != null) {
                    if (mActionMode != null) {
                        mActionMode.finish();
                    }
                    mService.rename(FileManagerOperationActivity.this.getClass().getName(),
                            mSrcfileInfo, new FileInfo(newFilePath), new LightOperationListener(
                                    FileUtils.getFileName(newFilePath)));
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (FileUtils.mCurTabIndex == FileUtils.TAB_ON_BROWSE_ITEM) {
            mViewPager.setCurrentItem(FileUtils.TAB_ON_BROWSE);
            return;
        }
        if (mAdapter != null && mAdapter.isMode(FileInfoAdapter.MODE_EDIT)) {
            if (mActionMode != null) {
                mActionMode.finish();
            }
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String hotknotPath = null;
        LogUtils.d(TAG, "onNewIntent action: " + action);
        if (action != null && action.equals(FileUtils.FTP_NOTIFICATION_ACTION)) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(FileUtils.TAB_ON_FTP);
            }
        }
        if (action != null && action.equalsIgnoreCase(ACTION_HOTKNOT_RECEIVED)) {
            Uri uri = (Uri) intent.getExtra(HotKnotAdapter.EXTRA_DATA);
            if (null != uri) {
                hotknotPath = uri.getPath();
            }
            LogUtils.d(TAG, "onNewIntent: " + hotknotPath);
            if (hotknotPath != null) {
                hotknotPath = hotknotPath.substring(0, hotknotPath.lastIndexOf(STRING_HOTKNOT));
                hotknotPath = hotknotPath + STRING_HOTKNOT;
            }
            if (hotknotPath == null || hotknotPath.isEmpty()) {
                hotknotPath = (mCurrentPath == null ? mMountPointManager.getRootPath() : mCurrentPath);
            }
            if (!mCurrentPath.equalsIgnoreCase(hotknotPath)) {
                if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_BROWSE) {
                    mViewPager.setCurrentItem(FileUtils.TAB_ON_FILE);
                }
                addToNavigationList(mCurrentPath, null, -1);
                showDirectoryContent(hotknotPath);
            }
            return;
        }
        String path = intent.getStringExtra(INTENT_EXTRA_SELECT_PATH);
        if (path != null && mService != null && !mService.isBusy(this.getClass().getName())) {
            File file = new File(path);
            if (!file.exists()) {
                mToastHelper.showToast(getString(R.string.path_not_exists, path));
                path = mMountPointManager.getRootPath();
            }
            if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_BROWSE) {
                mViewPager.setCurrentItem(FileUtils.TAB_ON_FILE);
            }
            addToNavigationList(mCurrentPath, null, -1);
            showDirectoryContent(path);
        }
    }

    @Override
    protected String initCurrentFileInfo() {
        String action = getIntent().getAction();
        String hotknotPath = null;
        if (action != null && action.equalsIgnoreCase(ACTION_HOTKNOT_RECEIVED)) {
            Uri uri = (Uri) getIntent().getExtra(HotKnotAdapter.EXTRA_DATA);
            if (null != uri) {
                hotknotPath = uri.getPath();
            }
            LogUtils.d(TAG, "initCurrentFileInfo: " + hotknotPath);
            if (hotknotPath != null) {
                hotknotPath = hotknotPath.substring(0, hotknotPath.lastIndexOf(STRING_HOTKNOT));
                hotknotPath = hotknotPath + STRING_HOTKNOT;
            }
            if (hotknotPath == null || hotknotPath.isEmpty()) {
                hotknotPath = (mCurrentPath == null ? mMountPointManager.getRootPath() : mCurrentPath);
            }
            return hotknotPath;
        }
        String path = getIntent().getStringExtra(INTENT_EXTRA_SELECT_PATH);
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
            mToastHelper.showToast(getString(R.string.path_not_exists, path));
        }
        return mMountPointManager.getRootPath();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapter, View v, int position, long id) {
        if (FileUtils.mCurMode == FileUtils.SELECT_FILE_MODE) {
            return false;
        }
        if (mAdapter.isMode(FileInfoAdapter.MODE_NORMAL)) {
            if ((!mMountPointManager.isRootPath(mCurrentPath)
                    || FileUtils.mCurTabIndex == FileUtils.TAB_ON_BROWSE_ITEM)
                    && !mService.isBusy(this.getClass().getName())) {
                int top = v.getTop();
                switchToEditView(position, top);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != mOrientationConfig) {
            mIsConfigChanged = true;
            mOrientationConfig = newConfig.orientation;
        }
    }

    @Override
    protected void onPathChanged() {
        super.onPathChanged();
        if (mActionMode != null && mActionModeCallBack != null) {
            mActionModeCallBack.updateActionMode();
        }
    }

    protected class DetailInfoListener implements FileManagerService.OperationEventListener,
            OnDismissListener {
        public static final String DETAIL_DIALOG_TAG = "detaildialogtag";
        private TextView mDetailsText;
        private final String mName;
        private String mSize;
        private String mPath;
        private final String mModifiedTime;
        private final String mPermission;
        private final StringBuilder mStringBuilder = new StringBuilder();
        private long mFileLength = -1;
        //A:DWYQLSSMY-1045 guoshuai 20160510(start)
        private String mEmulated0 = "emulated/0";
        private String mEmulated1 = StorageManagerEx.getExternalStoragePath();
        private int mHaveEmulated0 = -1;
        private int mHaveEmulated1 = -1;
        //A:DWYQLSSMY-1045 guoshuai 20160510(end)

        public DetailInfoListener(FileInfo fileInfo) {
            mStringBuilder.setLength(0);
            mName = mStringBuilder.append(getString(R.string.name)).append(": ").append(
                    fileInfo.getFileName()).append("\n").toString();
            mStringBuilder.setLength(0);
            mSize = mStringBuilder.append(getString(R.string.size)).append(": ").append(
                    FileUtils.sizeToString(0)).append(" \n").toString();
            mStringBuilder.setLength(0);
            mPath = mStringBuilder.append(getString(R.string.path)).append(": ").append(
                    fileInfo.getFileParentPath()).append(" \n").toString();
            //A:DWYQLSSMY-1045 guoshuai 20160510(start)
            mHaveEmulated0 = mPath.indexOf(mEmulated0);
            mHaveEmulated1 = mPath.indexOf(mEmulated1);
            if (mHaveEmulated0 != -1) {
                mPath = mPath.replaceFirst(mEmulated0 ,getString(R.string.phone_storage));
            }
            if (mHaveEmulated1 != -1) {
                mPath = mPath.replaceFirst(mEmulated1 ,getString(R.string.sdcard_storage));
            }
            LogUtils.d(TAG, "mPathReplaceFirst:" + mPath);
            //A:DWYQLSSMY-1045 guoshuai 20160510(end)
            long time = fileInfo.getFileLastModifiedTime();

            mStringBuilder.setLength(0);
            mModifiedTime = mStringBuilder.append(getString(R.string.modified_time)).append(": ")
                    .append(DateFormat.getDateInstance().format(new Date(time))).append("\n")
                    .toString();
            mStringBuilder.setLength(0);
            mPermission = getPermission(fileInfo.getFile());
        }

        public String getDetailInfo(long size) {
            mSize = getString(R.string.size) + ": "
                    + FileUtils.sizeToString(size) + " \n";
            StringBuilder builder = new StringBuilder();
            builder.append(mName).append(mSize).append(mPath).append(mModifiedTime)
            .append(mPermission);
            return builder.toString();
        }

        private void appendPermission(boolean hasPermission, int title) {
            mStringBuilder.append(getString(title) + ": ");
            if (hasPermission) {
                mStringBuilder.append(getString(R.string.yes));
            } else {
                mStringBuilder.append(getString(R.string.no));
            }
        }

        private String getPermission(File file) {
            appendPermission(file.canRead(), R.string.readable);
            mStringBuilder.append("\n");
            appendPermission(file.canWrite(), R.string.writable);
            mStringBuilder.append("\n");
            appendPermission(file.canExecute(), R.string.executable);

            return mStringBuilder.toString();
        }

        @Override
        public void onTaskPrepare() {
            if (isResumed()) {
                AlertDialogFragmentBuilder builder = new AlertDialogFragmentBuilder();
                AlertDialogFragment detailFragment = builder.setCancelTitle(R.string.ok).setLayout(
                        R.layout.dialog_details).setTitle(R.string.details).create();

                detailFragment.setDismissListener(this);
                detailFragment.show(getFragmentManager(), DETAIL_DIALOG_TAG);
                boolean ret = getFragmentManager().executePendingTransactions();
                LogUtils.d(TAG, "executing pending transactions result: " + ret);
                if (detailFragment.getDialog() != null) {
                    mDetailsText = (TextView) detailFragment.getDialog()
                            .findViewById(R.id.details_text);
                    mStringBuilder.setLength(0);
                    if (mDetailsText != null) {
                        mDetailsText.setText(mStringBuilder.append(mName).append(mSize).append(mPath).append(
                                mModifiedTime).append(mPermission).toString());
                        mDetailsText.setMovementMethod(ScrollingMovementMethod.getInstance());
                    }
                }
            } else {
                LogUtils.e(TAG, "onTaskPrepare activity is not resumed");
            }
        }

        @Override
        public void onTaskProgress(ProgressInfo progressInfo) {
            mFileLength = progressInfo.getTotal();
            mSize = getString(R.string.size) + ": "
                    + FileUtils.sizeToString(progressInfo.getTotal()) + " \n";
            if (mDetailsText != null) {
                mStringBuilder.setLength(0);
                mStringBuilder.append(mName).append(mSize).append(mPath).append(mModifiedTime)
                        .append(mPermission);
                mDetailsText.setText(mStringBuilder.toString());
            }
        }

        @Override
        public void onTaskResult(int result) {
            LogUtils.d(TAG, "DetailInfoListener onTaskResult." + mFileLength);
            if (mFileLength == -1) {
                mFileLength = 0;
            }
            AlertDialogFragment detailFragment = (AlertDialogFragment) getFragmentManager().findFragmentByTag(DETAIL_DIALOG_TAG);
            if (detailFragment != null) {
                detailFragment.getArguments().putString(DETAIL_INFO_KEY, mStringBuilder.toString());
                detailFragment.getArguments().putLong(DETAIL_INFO_SIZE_KEY, mFileLength);
            } else {
                // this case may happen in case of this operation already canceled.
                LogUtils.d(TAG, "get detail fragment is null...");
            }
            return;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if (mService != null) {
                LogUtils.d(this.getClass().getName(), "onDismiss");
                mService.cancel(FileManagerOperationActivity.this.getClass().getName());
            }
        }
    }

    protected class HeavyOperationListener implements FileManagerService.OperationEventListener,
            View.OnClickListener {
        int mTitle = R.string.deleting;

        private boolean mPermissionToast = false;
        private boolean mOperationToast = false;
        public static final String HEAVY_DIALOG_TAG = "HeavyDialogFragment";

        // test performance
        // private long beforeTime = 0;

        public HeavyOperationListener(int titleID) {
            mTitle = titleID;
        }

        @Override
        public void onTaskPrepare() {
            // beforeTime = System.currentTimeMillis();
            if (isResumed()) {
                ProgressDialogFragment heavyDialogFragment = ProgressDialogFragment.newInstance(
                        ProgressDialog.STYLE_HORIZONTAL, mTitle, R.string.wait, R.string.cancel);
                heavyDialogFragment.setCancelListener(this);
                heavyDialogFragment.setViewDirection(getViewDirection());
                heavyDialogFragment.show(getFragmentManager(), HEAVY_DIALOG_TAG);
                boolean ret = getFragmentManager().executePendingTransactions();
                LogUtils.d(TAG, "executing pending transactions result: " + ret);
            } else {
                LogUtils.d(TAG, "HeavyOperationListener onTaskResult activity is not resumed.");
            }
        }

        @Override
        public void onTaskProgress(ProgressInfo progressInfo) {
            if (progressInfo.isFailInfo()) {
                switch (progressInfo.getErrorCode()) {
                case OperationEventListener.ERROR_CODE_COPY_NO_PERMISSION:
                    if (!mPermissionToast) {
                        mToastHelper.showToast(R.string.copy_deny);
                        mPermissionToast = true;
                    }
                    break;
                case OperationEventListener.ERROR_CODE_DELETE_NO_PERMISSION:
                    if (!mPermissionToast) {
                        mToastHelper.showToast(R.string.delete_deny);
                        mPermissionToast = true;
                    }
                    break;
                case OperationEventListener.ERROR_CODE_DELETE_UNSUCCESS:
                    if (!mOperationToast) {
                        mToastHelper.showToast(R.string.some_delete_fail);
                        mOperationToast = true;
                    }
                    break;
                case OperationEventListener.ERROR_CODE_PASTE_UNSUCCESS:
                    if (!mOperationToast) {
                        mToastHelper.showToast(R.string.some_paste_fail);
                        mOperationToast = true;
                    }
                    break;
                default:
                    if (!mPermissionToast) {
                        mToastHelper.showToast(R.string.operation_fail);
                        mPermissionToast = true;
                    }
                    break;
                }

            } else {
                FileUtils.mHasFileChanged = true;
                ProgressDialogFragment heavyDialogFragment = (ProgressDialogFragment) getFragmentManager()
                        .findFragmentByTag(HEAVY_DIALOG_TAG);
                if (heavyDialogFragment != null) {
                    heavyDialogFragment.setProgress(progressInfo);
                }
            }
        }

        @Override
        public void onTaskResult(int errorType) {
            LogUtils.d(TAG, "HeavyOperationListener,onTaskResult result = " + errorType);
            switch (errorType) {
            case ERROR_CODE_PASTE_TO_SUB:
                mToastHelper.showToast(R.string.paste_sub_folder);
                break;
            case ERROR_CODE_CUT_SAME_PATH:
                mToastHelper.showToast(R.string.paste_same_folder);
                break;
            case ERROR_CODE_NOT_ENOUGH_SPACE:
                mToastHelper.showToast(R.string.insufficient_memory);
                break;
            case ERROR_CODE_DELETE_FAILS:
                mToastHelper.showToast(R.string.delete_fail);
                break;
            case ERROR_CODE_COPY_NO_PERMISSION:
                mToastHelper.showToast(R.string.copy_deny);
                break;
            case ERROR_CODE_COPY_GREATER_4G_TO_FAT32:
                mToastHelper.showToast(R.string.operation_fail);
                break;
            default:
                mFileInfoManager.updateFileInfoList(mCurrentPath, mSortType);
                mAdapter.notifyDataSetChanged();
                break;
            }
            ProgressDialogFragment heavyDialogFragment = (ProgressDialogFragment) getFragmentManager()
                    .findFragmentByTag(HEAVY_DIALOG_TAG);
            if (heavyDialogFragment != null) {
                heavyDialogFragment.dismissAllowingStateLoss();
            }
            if (mFileInfoManager.getPasteType() == FileInfoManager.PASTE_MODE_CUT) {
                mFileInfoManager.clearPasteList();
                mAdapter.notifyDataSetChanged();
            }
            // final long endTime = System.currentTimeMillis();
            // LogUtils.i(TAG,
            // "HeavyOperationListener, onTaskResult,time cost is:" +
            // (endTime-beforeTime)/1000);

            invalidateOptionsMenu();
        }

        @Override
        public void onClick(View v) {
            if (mService != null) {
                LogUtils.i(this.getClass().getName(), "onClick cancel");
                mService.cancel(FileManagerOperationActivity.this.getClass().getName());
            }
        }
    }

    protected class ActionModeCallBack implements ActionMode.Callback, OnMenuItemClickListener {

        private PopupMenu mSelectPopupMenu = null;
        private boolean mSelectedAll = true;
        private Button mTextSelect = null;

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = layoutInflater.inflate(R.layout.actionbar_edit, null);
            mode.setCustomView(customView);
            mTextSelect = (Button) customView.findViewById(R.id.text_select);
            mTextSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectPopupMenu == null) {
                        mSelectPopupMenu = createSelectPopupMenu(mTextSelect);
                    } else {
                        updateSelectPopupMenu();
                        mSelectPopupMenu.show();
                    }
                }
            });
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.edit_view_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            int selectedCount = mAdapter.getCheckedItemsCount();
            MenuItem cutItem = menu.findItem(R.id.cut);
//            if (cutItem != null && OptionsUtils.isMtkHotKnotSupported()) {
//                cutItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//            } else if (cutItem != null && !OptionsUtils.isMtkHotKnotSupported()) {
//                cutItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//            }
            if (!OptionsUtils.isMtkHotKnotSupported()) {
                menu.removeItem(R.id.hotknot_share);
            }

            // enable(disable) copy, cut, and delete icon
            if (selectedCount == 0) {
                menu.findItem(R.id.copy).setEnabled(false);
                menu.findItem(R.id.delete).setEnabled(false);
                menu.findItem(R.id.cut).setEnabled(false);
            } else {
                menu.findItem(R.id.copy).setEnabled(true);
                menu.findItem(R.id.delete).setEnabled(true);
                menu.findItem(R.id.cut).setEnabled(true);
            }

            if (selectedCount == 0 || selectedCount > MAX_SHARE_FILES_COUNT) {
                menu.findItem(R.id.share).setEnabled(false);
                if (OptionsUtils.isMtkHotKnotSupported()) {
                    menu.findItem(R.id.hotknot_share).setEnabled(false);
                }
            } else if (selectedCount == 1) {
                FileInfo fileInfo = mAdapter.getCheckedFileInfoItemsList().get(0);
                if (fileInfo.isDrmFile()
                        && DrmManager.getInstance().isRightsStatus(fileInfo.getFileAbsolutePath())
                        || fileInfo.isDirectory()) {
                    menu.findItem(R.id.share).setEnabled(false);
                    if (OptionsUtils.isMtkHotKnotSupported()) {
                        menu.findItem(R.id.hotknot_share).setEnabled(false);
                    }
                } else {
                    menu.findItem(R.id.share).setEnabled(true);
                    if (OptionsUtils.isMtkHotKnotSupported()) {
                        if (fileInfo.isDirectory()) {
                            menu.findItem(R.id.hotknot_share).setEnabled(false);
                        } else {
                            menu.findItem(R.id.hotknot_share).setEnabled(true);
                        }
                    }
                }
            } else {
                menu.findItem(R.id.share).setEnabled(true);
                if (OptionsUtils.isMtkHotKnotSupported()) {
                    menu.findItem(R.id.hotknot_share).setEnabled(true);
                }
                List<FileInfo> files = mAdapter.getCheckedFileInfoItemsList();
                for (FileInfo info : files) {
                    File file = info.getFile();
                    if (file.isDirectory()) {
                        // break for loop; disable share icon
                        menu.findItem(R.id.share).setEnabled(false);
                        if (OptionsUtils.isMtkHotKnotSupported()) {
                            menu.findItem(R.id.hotknot_share).setEnabled(false);
                        }
                        break;
                    }
                }
            }

            // more items
            // remove (disable) protection info icon
            menu.removeItem(R.id.protection_info);

            if (selectedCount == 0) {
                menu.findItem(R.id.rename).setEnabled(false);
                menu.findItem(R.id.details).setEnabled(false);
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)

				menu.findItem(R.id.set_ring).setVisible(false);
				menu.findItem(R.id.set_mms_ring).setVisible(false);
				menu.findItem(R.id.set_alarm_ring).setVisible(false);
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
            } else if (selectedCount == 1) {
                // enable details icon
                menu.findItem(R.id.details).setEnabled(true);
                // enable rename icon
                if (mAdapter.getCheckedFileInfoItemsList().get(0).getFile().canWrite()) {
                    menu.findItem(R.id.rename).setEnabled(true);
                }
                // enable protection info icon
                FileInfo fileInfo = mAdapter.getCheckedFileInfoItemsList().get(0);
                if (fileInfo.isDrmFile()) {
                    String path = fileInfo.getFileAbsolutePath();
                    if (DrmManager.getInstance().checkDrmObjectType(path)) {
                        String mimeType = DrmManager.getInstance().getOriginalMimeType(path);
                        if (mimeType != null && mimeType.trim().length() != 0) {
                            menu.add(0, R.id.protection_info, 0,
                                    com.mediatek.internal.R.string.drm_protectioninfo_title);
                        }
                    }
                }
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
				String fileMimeType = fileInfo.getFileMimeType(mService);
	            if((null != fileMimeType) && (fileMimeType.contains("audio/") || fileMimeType.contains("application/ogg")) 
					&& !fileInfo.isDrmFile() && getResources().getBoolean(R.bool.config_show_set_ringtone)) {
					menu.findItem(R.id.set_ring).setVisible(true);
					menu.findItem(R.id.set_mms_ring).setVisible(true);
					menu.findItem(R.id.set_alarm_ring).setVisible(true);
				} else {
					menu.findItem(R.id.set_ring).setVisible(false);
					menu.findItem(R.id.set_mms_ring).setVisible(false);
					menu.findItem(R.id.set_alarm_ring).setVisible(false);
				}
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
            } else {
                // disable details icon
                menu.findItem(R.id.details).setEnabled(false);
                // disable rename icon
                menu.findItem(R.id.rename).setEnabled(false);
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
                menu.findItem(R.id.set_ring).setVisible(false);
                menu.findItem(R.id.set_mms_ring).setVisible(false);
				menu.findItem(R.id.set_alarm_ring).setVisible(false);
//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
            }
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
            case R.id.copy:
                mFileInfoManager.savePasteList(FileInfoManager.PASTE_MODE_COPY, mAdapter
                        .getCheckedFileInfoItemsList());
                mode.finish();
                if (FileUtils.mCurTabIndex == FileUtils.TAB_ON_BROWSE_ITEM) {
                    FileUtils.mCurTabIndex = FileUtils.TAG_CUR_STATE_NORMAL;
                    String defaultPath = StorageManagerEx.getDefaultPath();
                    addToNavigationList(mCurrentPath, null, -1);
                    mCurrentPath = defaultPath;
                  //M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
                    mFileInfoManager.clearShowFilesInfoList(mAdapter);
                  //M BUG_ID:DWYYL-926 shasha.fang 20150603(end)
                    mAdapter.notifyDataSetChanged();
                    LogUtils.d(TAG, "defaultPath: "+defaultPath);
                    showDirectoryContent(mCurrentPath);
                }
                break;
            case R.id.hotknot_share:
                hotknotShare();
                break;
            case R.id.cut:
                mFileInfoManager.savePasteList(FileInfoManager.PASTE_MODE_CUT, mAdapter
                        .getCheckedFileInfoItemsList());
                mode.finish();
                break;
            case R.id.delete:
                showDeleteDialog();
                break;
            case R.id.share:
                share();
                break;
            case R.id.rename:
                showRenameDialog();
                break;
            case R.id.details:
                mService.getDetailInfo(FileManagerOperationActivity.this.getClass().getName(),
                        mAdapter.getCheckedFileInfoItemsList().get(0), new DetailInfoListener(
                                mAdapter.getCheckedFileInfoItemsList().get(0)));
                break;
            //add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
            case R.id.set_ring:			
            	setRingtone(FileUtils.SET_PHONE_RINGTONE);
                break;
			case R.id.set_mms_ring:
				setRingtone(FileUtils.SET_MESSAGE_RINGTONE);
                break;
			case R.id.set_alarm_ring:
				setRingtone(FileUtils.SET_ALARM_RINGTONE);
                break;
            //add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
            case R.id.protection_info:
                // calling framework to show a protection info dialog
                String path = mCurrentPath + MountPointManager.SEPARATOR
                        + mAdapter.getCheckedFileInfoItemsList().get(0).getFileName();
                DrmManager.getInstance().showProtectionInfoDialog(
                        FileManagerOperationActivity.this, path);
                if (mActionMode != null) {
                    mActionMode.finish();
                }
                break;
            case R.id.select:
                if (mSelectedAll) {
                    mAdapter.setAllItemChecked(true);
                } else {
                    mAdapter.setAllItemChecked(false);
                }
                updateActionMode();
                invalidateOptionsMenu();
                break;
            default:
                return false;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            switchToNavigationView();
            if (mActionMode != null) {
                mActionMode = null;
            }
            if (mSelectPopupMenu != null) {
                mSelectPopupMenu.dismiss();
                mSelectPopupMenu = null;
            }
        }

        private PopupMenu createSelectPopupMenu(View anchorView) {
            final PopupMenu popupMenu = new PopupMenu(FileManagerOperationActivity.this, anchorView);
            popupMenu.inflate(R.menu.select_popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            return popupMenu;
        }

        private void updateSelectPopupMenu() {
            if (mSelectPopupMenu == null) {
                mSelectPopupMenu = createSelectPopupMenu(mTextSelect);
                return;
            }
            final Menu menu = mSelectPopupMenu.getMenu();
            int selectedCount = mAdapter.getCheckedItemsCount();
            if (mAdapter.getCount() == 0) {
                menu.findItem(R.id.select).setEnabled(false);
            } else {
                menu.findItem(R.id.select).setEnabled(true);
            }
            if (mAdapter.getCount() != selectedCount) {
                menu.findItem(R.id.select).setTitle(R.string.select_all);
                mSelectedAll = true;
            } else {
                menu.findItem(R.id.select).setTitle(R.string.deselect_all);
                mSelectedAll = false;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
            case R.id.select:
                if (mSelectedAll) {
                    mAdapter.setAllItemChecked(true);
                } else {
                    mAdapter.setAllItemChecked(false);
                }
                updateActionMode();
                invalidateOptionsMenu();
                break;
            default:
                return false;
            }
            return true;
        }

        public void updateActionMode() {
            int selectedCount = mAdapter.getCheckedItemsCount();
            String selected = "";
            if (Locale.getDefault().getLanguage().equals("fr") && selectedCount > 1) {
                try {
                    selected = getResources().getString(R.string.mutil_selected);
                } catch (Resources.NotFoundException e) {
                    selected = getResources().getString(R.string.selected);
                }
            } else {
                selected = getResources().getString(R.string.selected);
            }
            selected = "" + selectedCount + " " + selected;
            mTextSelect.setText(selected);

            mActionModeCallBack.updateSelectPopupMenu();
            if (mActionMode != null) {
                mActionMode.invalidate();
            }
        }

    }

    //modify BUG_ID:DWYBL-2806 guowen 20151028 (start)
	//add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (start)
    /*private void setRingtone(int type) {
			FileUtils.setRingtoneByFilePath(FileManagerOperationActivity.this,
	                  mAdapter.getCheckedFileInfoItemsList().get(0).getFileAbsolutePath(),type,mSimId);
	}*/
    //add  BUG_ID:JWYYL-12 zhaolei.ding 20141216 (end)
	private void setRingtone(int type) {
		if(type==FileUtils.SET_PHONE_RINGTONE){
			simInfos = SubscriptionManager.from(this).getActiveSubscriptionInfoCount();
			if(simInfos > 1){
				int[] mSimIds = SubscriptionManager.from(this).getActiveSubscriptionIdList();
				for(int i = 0; i < mSimIds.length; i++){
				    FileUtils.setRingtoneByFilePath(FileManagerOperationActivity.this,mAdapter.getCheckedFileInfoItemsList().get(0).getFileAbsolutePath(),type,mSimIds[i]);	
				}
			}else if(simInfos == 1){
				int[] mSimIds = SubscriptionManager.from(this).getActiveSubscriptionIdList();
				FileUtils.setRingtoneByFilePath(FileManagerOperationActivity.this,mAdapter.getCheckedFileInfoItemsList().get(0).getFileAbsolutePath(),type,mSimIds[0]);
				Log.d(TAG,"=====mSimIds="+mSimIds[0]);
			}else{
				FileUtils.setRingtoneByFilePath(FileManagerOperationActivity.this,mAdapter.getCheckedFileInfoItemsList().get(0).getFileAbsolutePath(),type,mSimId);
			}
		}else{
			FileUtils.setRingtoneByFilePath(FileManagerOperationActivity.this,mAdapter.getCheckedFileInfoItemsList().get(0).getFileAbsolutePath(),type,mSimId);
		}
	}
	//modify BUG_ID:DWYBL-2806 guowen 20151028 (end)
    
    private void showOverflewButton() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void bindWidgets() {
        mGridView = (RgkBrowseGridView) mBrowseView.findViewById(R.id.gridview);
        mGridViewAdapter = new GridViewAdapter();
        mGridView.setAdapter(mGridViewAdapter);
        mGridView.setOnItemClickListener(new BrowseItemClickListener());
        mRgkListView = (RgkListView) mBrowseView.findViewById(R.id.browse_progressbar_listview);
        mProgressBarAdapter = new ProgressBarAdapter();
        mRgkListView.setAdapter(mProgressBarAdapter);
        
        mViewIpAdress = (View) mFtpView.findViewById(R.id.rgk_file_ftp_ip_adress_view);
        mImgWifi = (ImageView) mFtpView.findViewById(R.id.rgk_file_ftp_img_wifi);
        mBtnStartServer = (Button) mFtpView.findViewById(R.id.rgk_file_ftp_btn_start_server);
        mTxtWifiName = (TextView) mFtpView.findViewById(R.id.rgk_file_ftp_wifi_name);
        mTxtIpAdress = (TextView) mFtpView.findViewById(R.id.rgk_file_ftp_ip_adress);
        mTxtFtpMsg = (TextView) mFtpView.findViewById(R.id.rgk_file_ftp_msg);
        mImgWifi.setOnClickListener(this);
        mBtnStartServer.setOnClickListener(this);
    }
    
    private void loadColors() {
        mTabTxtColorFocus = this.getResources().getColor(R.color.custom_actionbar_main);
        mTabTxtColorNormal = this.getResources().getColor(R.color.tab_txt_gray);
    }
    
    private void loadBrowseData() {
        Resources rs = this.getResources();
        mStrBrowseContents = rs.getStringArray(R.array.file_manager_browse_arrays);
    }
    
    private String getTotalSize(ArrayList<BrowseInfo> browseInfoList) {
        int count = browseInfoList == null ? 0: browseInfoList.size();
        long size = 0L;
        for (int i = 0; i < count; i++) {
            BrowseInfo info = browseInfoList.get(i);
            size += info.size;
        }
        return FileUtils.sizeToString(size);
    }
    
    private void launchQueryDbTask() {
        mQueryMediaDbTask = new QueryMediaDbTask(this,mHandler);
        mQueryMediaDbTask.execute();
    }
    
    private void cancelQueryDbTask() {
        if (mQueryMediaDbTask != null && !mQueryMediaDbTask.isCancelled()) {
            mQueryMediaDbTask.cancel(true);
        }
    }
    

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case HandlerMessage.HANDLER_MSG_BROWSE:
                try {
                    mMap = mQueryMediaDbTask.getMap();
                    mGridViewAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case HandlerMessage.HANDLER_MSG_BROWSE_ITEM:
                mViewPager.setCurrentItem(FileUtils.TAB_ON_FILE);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
        
    };
    
    public interface HandlerMessage{
        int HANDLER_MSG_BROWSE = 0;
        int HANDLER_MSG_BROWSE_ITEM = 1;
    }
    
    private class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImageIdResources.length;
        }

        @Override
        public Object getItem(int arg0) {
            return mImageIdResources[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View view, ViewGroup arg2) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.browse_item, null);
                viewHolder.icon = (ImageView) view.findViewById(R.id.file_manager_gridview_item_icon);
                viewHolder.title = (TextView) view.findViewById(R.id.file_manager_gridview_item_title);
                viewHolder.size = (TextView) view.findViewById(R.id.file_manager_gridview_item_size);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            ArrayList<BrowseInfo> browseList = mMap.get(position);
            viewHolder.icon.setImageResource(mImageIdResources[position]);
            int browseListSize = browseList == null ? 0 : browseList.size();
            StringBuilder sb = new StringBuilder();
            sb.append(mStrBrowseContents[position]);
            sb.append("("+browseListSize+")");
            viewHolder.title.setText(sb.toString());
            viewHolder.size.setText(getTotalSize(browseList));
            return view;
        }
    }
    
    private class ViewHolder {
        private ImageView icon;
        private TextView title;
        private TextView size;
    }
    
    private class FileManagerPagerAdapter extends PagerAdapter {
        private List<View> views;
        public FileManagerPagerAdapter(List<View> views) {
            this.views = views;
        }
        
        @Override
        public void destroyItem(View container,int position,Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }
        
        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }
        
        @Override
        public Object instantiateItem(View view,int arg1) {
            ((ViewPager) view).addView(views.get(arg1),0);
            return views.get(arg1);
        }
        
        @Override
        public boolean isViewFromObject(View view,Object arg1) {
            return view == arg1;
        }
    }
    
    private final class ViewPagerOnChangeListener implements OnPageChangeListener{
        private int pageSelected;
        @Override
        public void onPageScrollStateChanged(int arg0) {
            LogUtils.d(TAG,"onPageScrollStateChanged arg0: "+arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            LogUtils.d(TAG,"onPageScrolled arg0: "+arg0);
            LogUtils.d(TAG,"onPageScrolled arg1: "+arg1);
            LogUtils.d(TAG,"onPageScrolled arg2: "+arg2);
            LogUtils.d(TAG,"onPageScrolled FileUtils.mHasFileChanged: "+FileUtils.mHasFileChanged);
            mCustomUnderLine.updateOffset(arg1,arg0);
            if (arg0 == FileUtils.TAB_ON_BROWSE && FileUtils.mHasFileChanged) {
                updateUiByChanged();
            }
        }

        @Override
        public void onPageSelected(final int arg0) {
            LogUtils.d(TAG,"onPageSelected arg0: "+arg0);
            invalidateOptionsMenu();
            if (mAdapter != null && mAdapter.isMode(FileInfoAdapter.MODE_EDIT)) {
                if (getActionMode() != null) {
                    mActionMode.finish();
                }
              //M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
                mFileInfoManager.clearShowFilesInfoList(mAdapter);
              //M BUG_ID:DWYYL-926 shasha.fang 20150603(end)
                mAdapter.notifyDataSetChanged();
                switchToNavigationView();
            }
            if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_BROWSE) {
                resetTabTxtColor();
                mTabBrowse.setTextColor(mTabTxtColorFocus);
            }
            if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_BROWSE
                            && FileUtils.mCurTabIndex == FileUtils.TAB_ON_BROWSE_ITEM) {
                FileUtils.mCurTabIndex = FileUtils.TAG_CUR_STATE_NORMAL;
                if (mCurrentPath != null) {
                    showDirectoryContent(mCurrentPath);
                }
            } else if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_FILE) {
                resetTabTxtColor();
                mTabFile.setTextColor(mTabTxtColorFocus);
                if (FileUtils.mCurMode == FileUtils.SELECT_FILE_MODE) {
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } else if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_FTP) {
                updateUI();
                resetTabTxtColor();
                mTabFtp.setTextColor(mTabTxtColorFocus);
            }
        }
    }

    private void resetTabTxtColor() {
        mTabBrowse.setTextColor(mTabTxtColorNormal);
        mTabFile.setTextColor(mTabTxtColorNormal);
        mTabFtp.setTextColor(mTabTxtColorNormal);
    }
    
    private class BrowseItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                long arg3) {
            FileUtils.mCurTabIndex = FileUtils.TAB_ON_BROWSE_ITEM;
            if (mUpdateBrowseItemTask != null && !mUpdateBrowseItemTask.isCancelled()) {
                mUpdateBrowseItemTask.cancel(true);
            }
            if (mFileInfoManager != null) {
            	//M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
                mFileInfoManager.clearShowFilesInfoList(mAdapter);
              //M BUG_ID:DWYYL-926 shasha.fang 20150603(end)
            }
            mTabManager.refreshBorowseTab(mStrBrowseContents[position]);
            ArrayList<BrowseInfo> browseInfoList = mMap.get(position);
          //M BUG_ID:DWYYL-926 shasha.fang 20150603(start)
            mUpdateBrowseItemTask = new UpdateBrowseItemTask(mFileInfoManager,mHandler,mAdapter);
          //M BUG_ID:DWYYL-926 shasha.fang 20150603(end) 
            mUpdateBrowseItemTask.execute(browseInfoList);
        }
        
    }
    
    private void registerScreenShotReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TAKE_SCREENSHOT_ACTION);
        registerReceiver(mTakeScreenShotReceiver, filter);
    }
    
    private void unregisterScreenShotReceiver() {
        unregisterReceiver(mTakeScreenShotReceiver);
    }
    
    private void updateUiByChanged() {
        cancelQueryDbTask();
        launchQueryDbTask();
       if (mProgressBarAdapter != null) {
           MountPointManager.getInstance().updateMountPointSpaceInfo();
           mProgressBarAdapter.notifyDataSetChanged();
       }
    }
    
    private BroadcastReceiver mTakeScreenShotReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            String action = intent.getAction();
            LogUtils.d(TAG, "onReceive action: "+action);
            if (action != null && action.equals(TAKE_SCREENSHOT_ACTION)) {
                FileUtils.mHasFileChanged = true;
                if (mViewPager.getCurrentItem() == FileUtils.TAB_ON_BROWSE) {
                    if (FileUtils.mHasFileChanged) {
                        FileUtils.mHasFileChanged = false;
                    }
                    updateUiByChanged();
                }
            }
        }
    };
    
    private void registerWifiChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mWifiChangeReceiver, filter);
    }

    private void unregisterWifiChangeReceiver() {
        if (mWifiChangeReceiver != null) {
            unregisterReceiver(mWifiChangeReceiver);
        }
    }

    private void jumpToWifiSettings() {
        Intent intent = new Intent();
        intent.setAction(WIFI_SETTINGS_ACTION);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    
    private void updateUI() {
        if (mTxtWifiName == null || mImgWifi == null || mBtnStartServer== null) {
            return;
        }
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMgr.getConnectionInfo();
        String wifiId = info != null ? info.getSSID() : null;
        Log.d(TAG,"wifiId: "+wifiId);
        boolean isWifiReady = FileUtils.isConnectedUsingWifi();
        Log.d(TAG,"isWifiReady: "+isWifiReady);
        if (!TextUtils.isEmpty(wifiId)) {
            mTxtWifiName.setText(wifiId);
        }
        if (!isWifiReady) {
            mIsStartServer = false;
        }
        if (mIsStartServer) {
            if (mFtpServer == null || mFtpServer.isStopped()) {
                initFtpServer();
                InetAddress address = FileUtils.getLocalInetAddress();
                if (address != null) {
                    mStrIpAdress = FileUtils.FTP + address.getHostAddress()
                            + FileUtils.FTP_COLON + FileUtils.PORT;
                    startFtpServer();
                    startServerSettings();

                } else {
                    stopServer();
                    mStrIpAdress = "";
                    stopServerSettings();
                }
            }
        } else {
            if (mFtpServer != null && !mFtpServer.isStopped()) {
                stopServer();
                stopServerSettings();
            }
        }
        mBtnStartServer.setEnabled(isWifiReady);
        if (isWifiReady) {
            mImgWifi.setImageResource(R.drawable.wifi_state4_light);
            mBtnStartServer.setText((mFtpServer != null && !mFtpServer.isStopped()) ? R.string.rgk_file_ftp_stop_server : R.string.rgk_file_ftp_start_server);
        } else {
            mImgWifi.setImageResource(R.drawable.wifi_state0_light);
            mTxtWifiName.setText(R.string.rgk_file_no_wlan);
            stopServer();
            stopServerSettings();
        }
        boolean isUsbMassStorage = mStorageManager.isUsbMassStorageEnabled();
        Log.d(TAG,"isUsbMassStorage: "+isUsbMassStorage);
        Log.d(TAG,"mIsMounted: "+mIsMounted);
        if (!mIsMounted) {
            mBtnStartServer.setEnabled(false);
        } else if (mIsMounted && isWifiReady){
            mBtnStartServer.setEnabled(!isUsbMassStorage);
        }
        if (isUsbMassStorage) {
            stopServer();
            mStrIpAdress = "";
            stopServerSettings();
        }
    }
    
    private void startServerSettings() {
        mBtnStartServer.setText(R.string.rgk_file_ftp_stop_server);
        mTxtFtpMsg.setVisibility(View.GONE);
        mViewIpAdress.setVisibility(View.VISIBLE);
        mTxtIpAdress.setText(mStrIpAdress);
    }

    private void stopServerSettings() {
        mBtnStartServer.setText(R.string.rgk_file_ftp_start_server);
        mViewIpAdress.setVisibility(View.GONE);
        mTxtFtpMsg.setVisibility(View.VISIBLE);
    }

    private void startFtpServer() {
        try {
            if (mFtpServer != null && mFtpServer.isStopped()) {
                ((FileManagerApplication)FileManagerApplication.getInstance()).setupNotification(this);
                mFtpServer.start();
            }
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }
    private void stopServer() {
        mIsStartServer = false;
        if (mFtpServer != null && !mFtpServer.isStopped()) {
            ((FileManagerApplication)FileManagerApplication.getInstance()).clearNotification(this);
            mFtpServer.stop();
        }
    }

    private BroadcastReceiver mWifiChangeReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            updateUI();
        }
    };

    private void initFtpServer() {
        Log.d(TAG,"initFtpServer.");
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        File file = writeToFile();
        if (file == null) {
            return;
        }
        userManagerFactory.setFile(file);
        serverFactory.setUserManager(userManagerFactory.createUserManager());
        factory.setPort(FileUtils.PORT);
        serverFactory.addListener("default", factory.createListener());
        FtpServer server = serverFactory.createServer();
        Log.d(TAG,"Ftp createServer.");
        this.mFtpServer = server;
    }
    
    private File writeToFile() {
        File file = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = this.getResources().openRawResource(R.raw.users);
            file = new File(FileUtils.FTP_USER_PROPERTIES_PATH);
            if (file.exists()) {
                return file;
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer,0, length);
            }
            out.flush();
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("psm","file : "+file);
       return file;
    }
}
