/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.contacts.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.os.UserManager;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.ProviderStatus;
import android.provider.Settings;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
//import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.contacts.ContactsActivity;
import com.android.contacts.R;
import com.android.contacts.activities.ActionBarAdapter.TabState;
import com.android.contacts.common.ContactsUtils;
import com.android.contacts.common.activity.RequestPermissionsActivity;
import com.android.contacts.common.dialog.ClearFrequentsDialog;
import com.android.contacts.common.util.ImplicitIntentsUtil;
import com.android.contacts.common.widget.FloatingActionButtonController;
import com.android.contacts.editor.EditorIntents;
import com.android.contacts.interactions.ContactDeletionInteraction;
import com.android.contacts.common.interactions.ImportExportDialogFragment;
import com.android.contacts.common.list.ContactEntryListFragment;
import com.android.contacts.common.list.ContactListFilter;
import com.android.contacts.common.list.ContactListFilterController;
import com.android.contacts.common.list.ContactTileAdapter.DisplayType;
import com.android.contacts.interactions.ContactMultiDeletionInteraction;
import com.android.contacts.interactions.ContactMultiDeletionInteraction.MultiContactDeleteListener;
import com.android.contacts.interactions.JoinContactsDialogFragment;
import com.android.contacts.interactions.JoinContactsDialogFragment.JoinContactsListener;
import com.android.contacts.list.MultiSelectContactsListFragment;
import com.android.contacts.list.MultiSelectContactsListFragment.OnCheckBoxListActionListener;
import com.android.contacts.list.ContactTileListFragment;
import com.android.contacts.list.ContactsIntentResolver;
import com.android.contacts.list.ContactsRequest;
import com.android.contacts.list.ContactsUnavailableFragment;
import com.android.contacts.common.list.DirectoryListLoader;
import com.android.contacts.common.preference.DisplayOptionsPreferenceFragment;
import com.android.contacts.list.OnContactBrowserActionListener;
import com.android.contacts.list.OnContactsUnavailableActionListener;
import com.android.contacts.list.ProviderStatusWatcher;
import com.android.contacts.list.ProviderStatusWatcher.ProviderStatusListener;
import com.android.contacts.common.list.ViewPagerTabs;
import com.android.contacts.preference.ContactsPreferenceActivity;
import com.android.contacts.common.util.AccountFilterUtil;
import com.android.contacts.common.util.ViewUtil;
import com.android.contacts.quickcontact.QuickContactActivity;
import com.android.contacts.util.AccountPromptUtils;
import com.android.contacts.common.util.Constants;
import com.android.contacts.common.vcard.VCardCommonArguments;
import com.android.contacts.util.DialogManager;
import com.android.contactsbind.HelpUtils;
import com.android.contacts.util.PhoneCapabilityTester;

import com.mediatek.contacts.ContactsApplicationEx;
import com.mediatek.contacts.ContactsSystemProperties;
import com.mediatek.contacts.ExtensionManager;
import com.mediatek.contacts.model.AccountTypeManagerEx;
import com.mediatek.contacts.simcontact.BootCmpReceiver;
import com.mediatek.contacts.util.Log;
import com.mediatek.contacts.util.PDebug;
import com.mediatek.contacts.util.SetIndicatorUtils;
import com.mediatek.contacts.util.VolteUtils;
import com.mediatek.contacts.vcs.VcsController;
import com.mediatek.contacts.vcs.VcsUtils;
import com.mediatek.contacts.activities.ContactImportExportActivity;
import com.mediatek.contacts.activities.GroupBrowseActivity;
import com.mediatek.contacts.activities.ActivitiesUtils;

import com.mediatek.contacts.list.DropMenu;
import com.mediatek.contacts.list.DropMenu.DropDownMenu;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

//add EWLLL-795 liujingyi 151117 (start)
import android.os.SystemProperties;
import java.util.List;
import android.graphics.Color;
import com.android.contacts.group.GroupBrowseListFragment;
import com.android.contacts.group.GroupBrowseListFragment.OnGroupBrowserActionListener;
import com.mediatek.contacts.activities.GroupBrowseActivity.AccountCategoryInfo;
import com.mediatek.contacts.simcontact.SubInfoUtils;
//add EWLLL-795 liujingyi 151117 (end)

//begin add by zhouzhuobin for JSLEL-743 20140924
import android.provider.ContactsContract.CommonDataKinds.Phone;
import java.io.ByteArrayOutputStream;
import com.mediatek.contacts.ContactData;
import com.mediatek.contacts.MergerData;
import com.mediatek.contacts.ContactInfoData;
import com.mediatek.contacts.ContactInfoDataSet;
import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.ContentProviderOperation;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.database.Cursor;
import android.os.Message;
import android.content.ContentResolver;
import java.util.ArrayList;
import android.os.Handler;
import android.app.AlertDialog;
//end add by zhouzhuobin for JSLEL-743 20140924

/**
 * Displays a list to browse contacts.
 */
public class PeopleActivity extends ContactsActivity implements
        View.OnCreateContextMenuListener,
        View.OnClickListener,
        ActionBarAdapter.Listener,
        DialogManager.DialogShowingViewActivity,
        ContactListFilterController.ContactListFilterListener,
        ProviderStatusListener,
        MultiContactDeleteListener,
        JoinContactsListener {

    private static final String TAG = "PeopleActivity";

    private static final String ENABLE_DEBUG_OPTIONS_HIDDEN_CODE = "debug debug!";

    // These values needs to start at 2. See {@link ContactEntryListFragment}.
    private static final int SUBACTIVITY_ACCOUNT_FILTER = 2;

    private final DialogManager mDialogManager = new DialogManager(this);

    private ContactsIntentResolver mIntentResolver;
    private ContactsRequest mRequest;

    private ActionBarAdapter mActionBarAdapter;
    private FloatingActionButtonController mFloatingActionButtonController;
    private View mFloatingActionButtonContainer;
    private boolean wasLastFabAnimationScaleIn = false;

    private ContactTileListFragment.Listener mFavoritesFragmentListener =
            new StrequentContactListFragmentListener();

    private ContactListFilterController mContactListFilterController;

    private ContactsUnavailableFragment mContactsUnavailableFragment;
    private ProviderStatusWatcher mProviderStatusWatcher;
    private Integer mProviderStatus;

    private boolean mOptionsMenuContactsAvailable;

    /**
     * Showing a list of Contacts. Also used for showing search results in search mode.
     */
    private MultiSelectContactsListFragment mAllFragment;
    private ContactTileListFragment mFavoritesFragment;
    //add EWLLL-795 liujingyi 151117 (start)
    private GroupBrowseListFragment mGroupFragment;
    ImageButton mFloatingImageButton;
    private static final int SUBACTIVITY_NEW_GROUP = 4;
    //add EWLLL-795 liujingyi 151117 (end)
	
    /** ViewPager for swipe */
    private ViewPager mTabPager;
    private ViewPagerTabs mViewPagerTabs;
    private TabPagerAdapter mTabPagerAdapter;
    private String[] mTabTitles;
    private final TabPagerListener mTabPagerListener = new TabPagerListener();

    private boolean mEnableDebugMenuOptions;

    /**
     * True if this activity instance is a re-created one.  i.e. set true after orientation change.
     * This is set in {@link #onCreate} for later use in {@link #onStart}.
     */
    private boolean mIsRecreatedInstance;

    /**
     * If {@link #configureFragments(boolean)} is already called.  Used to avoid calling it twice
     * in {@link #onStart}.
     * (This initialization only needs to be done once in onStart() when the Activity was just
     * created from scratch -- i.e. onCreate() was just called)
     */
    private boolean mFragmentInitialized;

    /**
     * This is to disable {@link #onOptionsItemSelected} when we trying to stop the activity.
     */
    private boolean mDisableOptionItemSelected;

    /** Sequential ID assigned to each instance; used for logging */
    private final int mInstanceId;
    private static final AtomicInteger sNextInstanceId = new AtomicInteger();

    //begin add by zhouzhuobin for JSLEL-743 20140924
    private ArrayList<ContactData> mListDatas = new ArrayList<ContactData> ();
    private ArrayList<MergerData> mergerDatas = new ArrayList<MergerData> ();
    private ProgressDialog progressDialog = null;
    private MyAsyncQueryHandler asyncQuery = null;
    private final int MESSAGE_SUCC_MERGE = 0;
    private Handler mergeHandler = new Handler() {
    	@Override
    	public void handleMessage (Message msg) {
    		super.handleMessage(msg);
    		switch (msg.what) {
    		case MESSAGE_SUCC_MERGE:
    			if (progressDialog != null) {
    				progressDialog.dismiss();
    				break;
    			}
    		}
    	}
    };
    //end add by zhouzhuobin for JSLEL-743 20140924
	
    public PeopleActivity() {
        Log.d(TAG, "[PeopleActivity]new");
        mInstanceId = sNextInstanceId.getAndIncrement();
        mIntentResolver = new ContactsIntentResolver(this);
        /** M: Bug Fix for ALPS00407311 @{ */
        mProviderStatusWatcher = ProviderStatusWatcher.getInstance(ContactsApplicationEx
                .getContactsApplication());
        /** @} */
    }

    @Override
    public String toString() {
        // Shown on logcat
        return String.format("%s@%d", getClass().getSimpleName(), mInstanceId);
    }

    public boolean areContactsAvailable() {
        Log.d(TAG, "[areContactsAvailable]mProviderStatus = " + mProviderStatus);
        return ((mProviderStatus != null)
                && mProviderStatus.equals(ProviderStatus.STATUS_NORMAL)) ||
                ExtensionManager.getInstance().getOp01Extension()
                .areContactAvailable(mProviderStatus);
    }

    private boolean areContactWritableAccountsAvailable() {
        return ContactsUtils.areContactWritableAccountsAvailable(this);
    }

    private boolean areGroupWritableAccountsAvailable() {
        return ContactsUtils.areGroupWritableAccountsAvailable(this);
    }

    /**
     * Initialize fragments that are (or may not be) in the layout.
     *
     * For the fragments that are in the layout, we initialize them in
     * {@link #createViewsAndFragments(Bundle)} after inflating the layout.
     *
     * However, the {@link ContactsUnavailableFragment} is a special fragment which may not
     * be in the layout, so we have to do the initialization here.
     *
     * The ContactsUnavailableFragment is always created at runtime.
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d(TAG, "[onAttachFragment]");
        if (fragment instanceof ContactsUnavailableFragment) {
            mContactsUnavailableFragment = (ContactsUnavailableFragment)fragment;
            mContactsUnavailableFragment.setOnContactsUnavailableActionListener(
                    new ContactsUnavailableFragmentListener());
        }
    }

    @Override
    protected void onCreate(Bundle savedState) {
        Log.i(TAG,"[onCreate]");
        super.onCreate(savedState);

        if (RequestPermissionsActivity.startPermissionActivity(this)) {
            Log.i(TAG,"[onCreate]startPermissionActivity,return.");
            return;
        }

        /// M: Add for ALPS02383518, when BootCmpReceiver received PHB_CHANGED intent but has no
        // READ_PHONE permission, marked NEED_REFRESH_SIM_CONTACTS as true. So refresh
        // all SIM contacts after open all permission and back to contacts at here. @{
        boolean needRefreshSIMContacts = getSharedPreferences(getPackageName(),
                Context.MODE_PRIVATE).getBoolean(BootCmpReceiver.NEED_REFRESH_SIM_CONTACTS, false);
        if (needRefreshSIMContacts) {
            Log.d(TAG, "[onCreate] refresh all SIM contacts");
            Intent intent = new Intent(BootCmpReceiver.ACTION_REFRESH_SIM_CONTACT);
            sendBroadcast(intent);
        }
        /// @}

        if (!processIntent(false)) {
            finish();
            Log.w(TAG, "[onCreate]can not process intent:" + getIntent());
            return;
        }

        Log.d(TAG, "[Performance test][Contacts] loading data start time: ["
                + System.currentTimeMillis() + "]");

        mContactListFilterController = ContactListFilterController.getInstance(this);
        mContactListFilterController.checkFilterValidity(false);
        mContactListFilterController.addListener(this);

        mProviderStatusWatcher.addListener(this);

        mIsRecreatedInstance = (savedState != null);

        PDebug.Start("createViewsAndFragments");
        //add EWLLL-795 liujingyi 151117 (start)
        //createViewsAndFragments(savedState);
		if (SystemProperties.getInt("ro.rgk_contact_add_group_tab", 0) == 1) {
            createViewsAndFragmentsAddGroup(savedState);
		} else {
            createViewsAndFragments(savedState);
		}
       //add EWLLL-795 liujingyi 151117 (end)

        /// M: Modify for SelectAll/DeSelectAll Feature. @{
        Button selectcount = (Button) mActionBarAdapter.mSelectionContainer
                .findViewById(R.id.selection_count_text);
        selectcount.setOnClickListener(this);
        /// @}
        getWindow().setBackgroundDrawable(null);

        /**
         * M: For plug-in @{
         * register context to plug-in, so that the plug-in can use
         * host context to show dialog
         */
        /// M: [vcs] VCS featrue. @{
        if (VcsUtils.isVcsFeatureEnable()) {
            Log.i(TAG, "[onCreate]init VCS");
            mVcsController = new VcsController(this, mActionBarAdapter, mAllFragment);
            mVcsController.init();
        }
        /// @}
        /** @} */
        PDebug.End("Contacts.onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        PDebug.Start("onNewIntent");
        setIntent(intent);
        if (!processIntent(true)) {
            finish();
            Log.w(TAG, "[onNewIntent]can not process intent:" + getIntent());
            return;
        }
        Log.d(TAG, "[onNewIntent]");
        mActionBarAdapter.initialize(null, mRequest);

        mContactListFilterController.checkFilterValidity(false);

        // Re-configure fragments.
        configureFragments(true /* from request */);
        initializeFabVisibility();
        invalidateOptionsMenuIfNeeded();
        PDebug.End("onNewIntent");
    }

    /**
     * Resolve the intent and initialize {@link #mRequest}, and launch another activity if redirect
     * is needed.
     *
     * @param forNewIntent set true if it's called from {@link #onNewIntent(Intent)}.
     * @return {@code true} if {@link PeopleActivity} should continue running.  {@code false}
     *         if it shouldn't, in which case the caller should finish() itself and shouldn't do
     *         farther initialization.
     */
    private boolean processIntent(boolean forNewIntent) {
        // Extract relevant information from the intent
        mRequest = mIntentResolver.resolveIntent(getIntent());
//        if (Log.isLoggable(TAG, Log.DEBUG)) {
//            Log.d(TAG, this + " processIntent: forNewIntent=" + forNewIntent
//                    + " intent=" + getIntent() + " request=" + mRequest);
//        }
        if (!mRequest.isValid()) {
            Log.w(TAG, "[processIntent]request is inValid");
            setResult(RESULT_CANCELED);
            return false;
        }

        if (mRequest.getActionCode() == ContactsRequest.ACTION_VIEW_CONTACT) {
            Log.d(TAG, "[processIntent]start QuickContactActivity");
            final Intent intent = ImplicitIntentsUtil.composeQuickContactIntent(
                    mRequest.getContactUri(), QuickContactActivity.MODE_FULLY_EXPANDED);
            ImplicitIntentsUtil.startActivityInApp(this, intent);
            return false;
        }
        return true;
    }

    private void createViewsAndFragments(Bundle savedState) {
        Log.d(TAG,"[createViewsAndFragments]");
        PDebug.Start("createViewsAndFragments, prepare fragments");
        // Disable the ActionBar so that we can use a Toolbar. This needs to be called before
        // setContentView().
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.people_activity);

        final FragmentManager fragmentManager = getFragmentManager();

        // Hide all tabs (the current tab will later be reshown once a tab is selected)
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        mTabTitles = new String[TabState.COUNT];
        mTabTitles[TabState.FAVORITES] = getString(R.string.favorites_tab_label);
        mTabTitles[TabState.ALL] = getString(R.string.all_contacts_tab_label);
        mTabPager = getView(R.id.tab_pager);
        mTabPagerAdapter = new TabPagerAdapter();
        mTabPager.setAdapter(mTabPagerAdapter);
        mTabPager.setOnPageChangeListener(mTabPagerListener);

        // Configure toolbar and toolbar tabs. If in landscape mode, we  configure tabs differntly.
        final Toolbar toolbar = getView(R.id.toolbar);
        setActionBar(toolbar);
        final ViewPagerTabs portraitViewPagerTabs
                = (ViewPagerTabs) findViewById(R.id.lists_pager_header);
        ViewPagerTabs landscapeViewPagerTabs = null;
        if (portraitViewPagerTabs ==  null) {
            landscapeViewPagerTabs = (ViewPagerTabs) getLayoutInflater().inflate(
                    R.layout.people_activity_tabs_lands, toolbar, /* attachToRoot = */ false);
            mViewPagerTabs = landscapeViewPagerTabs;
        } else {
            mViewPagerTabs = portraitViewPagerTabs;
        }
        mViewPagerTabs.setViewPager(mTabPager);

        final String FAVORITE_TAG = "tab-pager-favorite";
        final String ALL_TAG = "tab-pager-all";

        // Create the fragments and add as children of the view pager.
        // The pager adapter will only change the visibility; it'll never create/destroy
        // fragments.
        // However, if it's after screen rotation, the fragments have been re-created by
        // the fragment manager, so first see if there're already the target fragments
        // existing.
        mFavoritesFragment = (ContactTileListFragment)
                fragmentManager.findFragmentByTag(FAVORITE_TAG);
        mAllFragment = (MultiSelectContactsListFragment)
                fragmentManager.findFragmentByTag(ALL_TAG);

        if (mFavoritesFragment == null) {
            mFavoritesFragment = new ContactTileListFragment();
            mAllFragment = new MultiSelectContactsListFragment();

            transaction.add(R.id.tab_pager, mFavoritesFragment, FAVORITE_TAG);
            transaction.add(R.id.tab_pager, mAllFragment, ALL_TAG);
        }

        mFavoritesFragment.setListener(mFavoritesFragmentListener);

        mAllFragment.setOnContactListActionListener(new ContactBrowserActionListener());
        mAllFragment.setCheckBoxListListener(new CheckBoxListListener());

        // Hide all fragments for now.  We adjust visibility when we get onSelectedTabChanged()
        // from ActionBarAdapter.
        transaction.hide(mFavoritesFragment);
        transaction.hide(mAllFragment);

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();

        // Setting Properties after fragment is created
        mFavoritesFragment.setDisplayType(DisplayType.STREQUENT);

        mActionBarAdapter = new ActionBarAdapter(this, this, getActionBar(),
                portraitViewPagerTabs, landscapeViewPagerTabs, toolbar);
        mActionBarAdapter.initialize(savedState, mRequest);

        // Add shadow under toolbar
        ViewUtil.addRectangularOutlineProvider(findViewById(R.id.toolbar_parent), getResources());

        // Configure floating action button
        mFloatingActionButtonContainer = findViewById(R.id.floating_action_button_container);
        final ImageButton floatingActionButton
                = (ImageButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(this);
        mFloatingActionButtonController = new FloatingActionButtonController(this,
                mFloatingActionButtonContainer, floatingActionButton);
        initializeFabVisibility();

        invalidateOptionsMenuIfNeeded();
    }

    private void createViewsAndFragmentsAddGroup(Bundle savedState) {
        PDebug.Start("createViewsAndFragments, prepare fragments");
        // Disable the ActionBar so that we can use a Toolbar. This needs to be called before
        // setContentView().
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.people_activity);

        final FragmentManager fragmentManager = getFragmentManager();

        // Hide all tabs (the current tab will later be reshown once a tab is selected)
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        mTabTitles = new String[TabState.COUNT];
        mTabTitles[TabState.FAVORITES] = getString(R.string.favorites_tab_label);
        mTabTitles[TabState.ALL] = getString(R.string.all_contacts_tab_label);
        //add EWLLL-795 liujingyi start
		mTabTitles[TabState.GROUP] = getString(R.string.groupsLabel);
        //add EWLLL-795 liujingyi end
		
        mTabPager = getView(R.id.tab_pager);
        mTabPagerAdapter = new TabPagerAdapter();
        mTabPager.setAdapter(mTabPagerAdapter);
        mTabPager.setOnPageChangeListener(mTabPagerListener);

        // Configure toolbar and toolbar tabs. If in landscape mode, we  configure tabs differntly.
        final Toolbar toolbar = getView(R.id.toolbar);
        setActionBar(toolbar);
        final ViewPagerTabs portraitViewPagerTabs
                = (ViewPagerTabs) findViewById(R.id.lists_pager_header);
        ViewPagerTabs landscapeViewPagerTabs = null;
        if (portraitViewPagerTabs ==  null) {
            landscapeViewPagerTabs = (ViewPagerTabs) getLayoutInflater().inflate(
                    R.layout.people_activity_tabs_lands, toolbar, /* attachToRoot = */ false);
            mViewPagerTabs = landscapeViewPagerTabs;
        } else {
            mViewPagerTabs = portraitViewPagerTabs;
        }
        mViewPagerTabs.setViewPager(mTabPager);

        final String FAVORITE_TAG = "tab-pager-favorite";
        final String ALL_TAG = "tab-pager-all";
        //add EWLLL-795 liujingyi start
		final String GROUP_TAG = "tab-pager-group";
        //add EWLLL-795 liujingyi end

        // Create the fragments and add as children of the view pager.
        // The pager adapter will only change the visibility; it'll never create/destroy
        // fragments.
        // However, if it's after screen rotation, the fragments have been re-created by
        // the fragment manager, so first see if there're already the target fragments
        // existing.
        mFavoritesFragment = (ContactTileListFragment)
                fragmentManager.findFragmentByTag(FAVORITE_TAG);
        mAllFragment = (MultiSelectContactsListFragment)
                fragmentManager.findFragmentByTag(ALL_TAG);
        //add EWLLL-795 liujingyi start
        mGroupFragment = (GroupBrowseListFragment)
        		fragmentManager.findFragmentByTag(GROUP_TAG);
        //add EWLLL-795 liujingyi end

        if (mFavoritesFragment == null) {
            mFavoritesFragment = new ContactTileListFragment();
            mAllFragment = new MultiSelectContactsListFragment();
            //add EWLLL-795 liujingyi start
            mGroupFragment = new GroupBrowseListFragment();
            //add EWLLL-795 liujingyi end

            transaction.add(R.id.tab_pager, mFavoritesFragment, FAVORITE_TAG);
            transaction.add(R.id.tab_pager, mAllFragment, ALL_TAG);
            //add EWLLL-795 liujingyi start
            transaction.add(R.id.tab_pager, mGroupFragment, GROUP_TAG);
            //add EWLLL-795 liujingyi end
        }

        mFavoritesFragment.setListener(mFavoritesFragmentListener);

        mAllFragment.setOnContactListActionListener(new ContactBrowserActionListener());
        mAllFragment.setCheckBoxListListener(new CheckBoxListListener());
        //add EWLLL-795 liujingyi start
        mGroupFragment.setListener(new GroupBrowserActionListener());
        //add EWLLL-795 liujingyi start
		
        // Hide all fragments for now.  We adjust visibility when we get onSelectedTabChanged()
        // from ActionBarAdapter.
        transaction.hide(mFavoritesFragment);
        transaction.hide(mAllFragment);
        //add EWLLL-795 liujingyi start
        transaction.hide(mGroupFragment);
        //add EWLLL-795 liujingyi end

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();

        // Setting Properties after fragment is created
        mFavoritesFragment.setDisplayType(DisplayType.STREQUENT);

        mActionBarAdapter = new ActionBarAdapter(this, this, getActionBar(),
                portraitViewPagerTabs, landscapeViewPagerTabs, toolbar);
        mActionBarAdapter.initialize(savedState, mRequest);
        PDebug.End("createViewsAndFragments, Configure action bar");

        // Add shadow under toolbar
        ViewUtil.addRectangularOutlineProvider(findViewById(R.id.toolbar_parent), getResources());

        // Configure floating action button
        mFloatingActionButtonContainer = findViewById(R.id.floating_action_button_container);
        final ImageButton floatingActionButton
                = (ImageButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(this);
        mFloatingActionButtonController = new FloatingActionButtonController(this,
                mFloatingActionButtonContainer, floatingActionButton);
        initializeFabVisibility();

        invalidateOptionsMenuIfNeeded();
        PDebug.End("createViewsAndFragments, prepare fragments");
    }
	
    @Override
    protected void onStart() {
        Log.i(TAG, "[onStart]mFragmentInitialized = " + mFragmentInitialized
                + ",mIsRecreatedInstance = " + mIsRecreatedInstance);
        if (!mFragmentInitialized) {
            mFragmentInitialized = true;
            /* Configure fragments if we haven't.
             *
             * Note it's a one-shot initialization, so we want to do this in {@link #onCreate}.
             *
             * However, because this method may indirectly touch views in fragments but fragments
             * created in {@link #configureContentView} using a {@link FragmentTransaction} will NOT
             * have views until {@link Activity#onCreate} finishes (they would if they were inflated
             * from a layout), we need to do it here in {@link #onStart()}.
             *
             * (When {@link Fragment#onCreateView} is called is different in the former case and
             * in the latter case, unfortunately.)
             *
             * Also, we skip most of the work in it if the activity is a re-created one.
             * (so the argument.)
             */
            configureFragments(!mIsRecreatedInstance);
        }
        /// M: register sim change @{
        AccountTypeManagerEx.registerReceiverOnSimStateAndInfoChanged(this, mBroadcastReceiver);
        /// @}
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "[onPause]");
        mOptionsMenuContactsAvailable = false;
        mProviderStatusWatcher.stop();
        /** M: New Feature CR ID: ALPS00112598 */
        SetIndicatorUtils.getInstance().showIndicator(this, false);
        /// M:[vcs] VCS Feature. @{
        if (mVcsController != null) {
            mVcsController.onPauseVcs();
        }
        /// @}
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "[onResume]");
        mProviderStatusWatcher.start();
        updateViewConfiguration(true);

        // Re-register the listener, which may have been cleared when onSaveInstanceState was
        // called.  See also: onSaveInstanceState
        mActionBarAdapter.setListener(this);
        mDisableOptionItemSelected = false;
        if (mTabPager != null) {
            mTabPager.setOnPageChangeListener(mTabPagerListener);
        }
        // Current tab may have changed since the last onSaveInstanceState().  Make sure
        // the actual contents match the tab.
        updateFragmentsVisibility();
        /** M: New Feature CR ID: ALPS00112598 */
        SetIndicatorUtils.getInstance().showIndicator(this, true);

        Log.d(TAG, "[Performance test][Contacts] loading data end time: ["
                + System.currentTimeMillis() + "]");
        /// M: [vcs] VCS feature @{
        if (mVcsController != null) {
            mVcsController.onResumeVcs();
        }
        /// @}
        PDebug.End("Contacts.onResume");
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "[onStop]");
        PDebug.Start("onStop");
        /// M: @{
        if (PhoneCapabilityTester.isUsingTwoPanes(this)) {
            mActionBarAdapter.setSearchMode(false);
            invalidateOptionsMenu();
        }
        /// @
        /// M: unregister sim change @{
        unregisterReceiver(mBroadcastReceiver);
        /// @
        super.onStop();
        PDebug.End("onStop");
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "[onDestroy]");
        PDebug.Start("onDestroy");
        mProviderStatusWatcher.removeListener(this);

        // Some of variables will be null if this Activity redirects Intent.
        // See also onCreate() or other methods called during the Activity's initialization.
        if (mActionBarAdapter != null) {
            mActionBarAdapter.setListener(null);
        }
        if (mContactListFilterController != null) {
            mContactListFilterController.removeListener(this);
        }

		//begin add by zhouzhuobin for JSLEL-743 2010924
        if (progressDialog != null) {
    		progressDialog.dismiss();
        }
        //end add by zhouzhuobin for JSLEL-743 2010924

        /// M: [vcs] VCS feature.
        if (mVcsController != null) {
            mVcsController.onDestoryVcs();
        }

        super.onDestroy();
        PDebug.End("onDestroy");
    }

    private void configureFragments(boolean fromRequest) {
        Log.d(TAG, "[configureFragments]fromRequest = " + fromRequest);
        if (fromRequest) {
            ContactListFilter filter = null;
            int actionCode = mRequest.getActionCode();
            boolean searchMode = mRequest.isSearchMode();
            final int tabToOpen;
            switch (actionCode) {
                case ContactsRequest.ACTION_ALL_CONTACTS:
                    filter = ContactListFilter.createFilterWithType(
                            ContactListFilter.FILTER_TYPE_ALL_ACCOUNTS);
                    tabToOpen = TabState.ALL;
                    break;
                case ContactsRequest.ACTION_CONTACTS_WITH_PHONES:
                    filter = ContactListFilter.createFilterWithType(
                            ContactListFilter.FILTER_TYPE_WITH_PHONE_NUMBERS_ONLY);
                    tabToOpen = TabState.ALL;
                    break;

                case ContactsRequest.ACTION_FREQUENT:
                case ContactsRequest.ACTION_STREQUENT:
                case ContactsRequest.ACTION_STARRED:
                    tabToOpen = TabState.FAVORITES;
                    break;
                case ContactsRequest.ACTION_VIEW_CONTACT:
                    tabToOpen = TabState.ALL;
                    break;
                //add EWLLL-795 liujingyi start
                case ContactsRequest.ACTION_GROUP:
                    tabToOpen = TabState.GROUP;
                    break;
                //add EWLLL-795 liujingyi end
                default:
                    tabToOpen = -1;
                    break;
            }
            if (tabToOpen != -1) {
                mActionBarAdapter.setCurrentTab(tabToOpen);
            }

            if (filter != null) {
                mContactListFilterController.setContactListFilter(filter, false);
                searchMode = false;
            }

            if (mRequest.getContactUri() != null) {
                searchMode = false;
            }

            mActionBarAdapter.setSearchMode(searchMode);
            configureContactListFragmentForRequest();
        }

        configureContactListFragment();

        invalidateOptionsMenuIfNeeded();
    }

    private void initializeFabVisibility() {
        final boolean hideFab = mActionBarAdapter.isSearchMode()
                || mActionBarAdapter.isSelectionMode();
        mFloatingActionButtonContainer.setVisibility(hideFab ? View.GONE : View.VISIBLE);
        mFloatingActionButtonController.resetIn();
        wasLastFabAnimationScaleIn = !hideFab;
    }

    private void showFabWithAnimation(boolean showFab) {
        if (mFloatingActionButtonContainer == null) {
            return;
        }
        if (showFab) {
            if (!wasLastFabAnimationScaleIn) {
                mFloatingActionButtonContainer.setVisibility(View.VISIBLE);
                mFloatingActionButtonController.scaleIn(0);
            }
            wasLastFabAnimationScaleIn = true;

        } else {
            if (wasLastFabAnimationScaleIn) {
                mFloatingActionButtonContainer.setVisibility(View.VISIBLE);
                mFloatingActionButtonController.scaleOut();
            }
            wasLastFabAnimationScaleIn = false;
        }
    }

    @Override
    public void onContactListFilterChanged() {
        if (mAllFragment == null || !mAllFragment.isAdded()) {
            return;
        }

        mAllFragment.setFilter(mContactListFilterController.getFilter());

        invalidateOptionsMenuIfNeeded();
    }

    /**
     * Handler for action bar actions.
     */
    @Override
    public void onAction(int action) {
        Log.d(TAG,"[onAction]action = " + action);
        /// M: [vcs] @{
        if (mVcsController != null) {
            mVcsController.onActionVcs(action);
        }
        /// @}
        switch (action) {
            case ActionBarAdapter.Listener.Action.START_SELECTION_MODE:
                mAllFragment.displayCheckBoxes(true);
                // Fall through:
            case ActionBarAdapter.Listener.Action.START_SEARCH_MODE:
                // Tell the fragments that we're in the search mode or selection mode
                configureFragments(false /* from request */);
                updateFragmentsVisibility();
                invalidateOptionsMenu();
                showFabWithAnimation(/* showFabWithAnimation = */ false);
                break;
            case ActionBarAdapter.Listener.Action.BEGIN_STOPPING_SEARCH_AND_SELECTION_MODE:
                showFabWithAnimation(/* showFabWithAnimation = */ true);
                break;
            case ActionBarAdapter.Listener.Action.STOP_SEARCH_AND_SELECTION_MODE:
                setQueryTextToFragment("");
                updateFragmentsVisibility();
                invalidateOptionsMenu();
                showFabWithAnimation(/* showFabWithAnimation = */ true);
                break;
            case ActionBarAdapter.Listener.Action.CHANGE_SEARCH_QUERY:
                final String queryString = mActionBarAdapter.getQueryString();
                setQueryTextToFragment(queryString);
                updateDebugOptionsVisibility(
                        ENABLE_DEBUG_OPTIONS_HIDDEN_CODE.equals(queryString));
                break;
            default:
                throw new IllegalStateException("Unkonwn ActionBarAdapter action: " + action);
        }
    }

    @Override
    public void onSelectedTabChanged() {
        Log.d(TAG, "[onSelectedTabChanged]");
        /// M: [vcs] @{
        if (mVcsController != null) {
            mVcsController.onSelectedTabChangedEx();
        }
        /// @}
        updateFragmentsVisibility();
    }

    @Override
    public void onUpButtonPressed() {
        Log.d(TAG, "[onUpButtonPressed]");
        onBackPressed();
    }

    private void updateDebugOptionsVisibility(boolean visible) {
        if (mEnableDebugMenuOptions != visible) {
            mEnableDebugMenuOptions = visible;
            invalidateOptionsMenu();
        }
    }

    /**
     * Updates the fragment/view visibility according to the current mode, such as
     * {@link ActionBarAdapter#isSearchMode()} and {@link ActionBarAdapter#getCurrentTab()}.
     */
    private void updateFragmentsVisibility() {
        int tab = mActionBarAdapter.getCurrentTab();

        if (mActionBarAdapter.isSearchMode() || mActionBarAdapter.isSelectionMode()) {
            mTabPagerAdapter.setTabsHidden(true);
        } else {
            // No smooth scrolling if quitting from the search/selection mode.
            final boolean wereTabsHidden = mTabPagerAdapter.areTabsHidden()
                    || mActionBarAdapter.isSelectionMode();
            mTabPagerAdapter.setTabsHidden(false);
            if (mTabPager.getCurrentItem() != tab) {
                mTabPager.setCurrentItem(tab, !wereTabsHidden);
            }
        }
        if (!mActionBarAdapter.isSelectionMode()) {
            mAllFragment.displayCheckBoxes(false);
        }
        invalidateOptionsMenu();
        showEmptyStateForTab(tab);
    }

    private void showEmptyStateForTab(int tab) {
        if (mContactsUnavailableFragment != null) {
            switch (getTabPositionForTextDirection(tab)) {
                case TabState.FAVORITES:
                    mContactsUnavailableFragment.setMessageText(
                            R.string.listTotalAllContactsZeroStarred, -1);
                    break;
                case TabState.ALL:
                    mContactsUnavailableFragment.setMessageText(R.string.noContacts, -1);
                    break;
                //add EWLLL-795 liujingyi start
                case TabState.GROUP:
                    mContactsUnavailableFragment.setMessageText(R.string.noGroups, -1);
                    break;
                //add EWLLL-795 liujingyi end
                default:
                    break;
            }
            // When using the mContactsUnavailableFragment the ViewPager doesn't contain two views.
            // Therefore, we have to trick the ViewPagerTabs into thinking we have changed tabs
            // when the mContactsUnavailableFragment changes. Otherwise the tab strip won't move.
            mViewPagerTabs.onPageScrolled(tab, 0, 0);
        }
    }

    private class TabPagerListener implements ViewPager.OnPageChangeListener {

        // This package-protected constructor is here because of a possible compiler bug.
        // PeopleActivity$1.class should be generated due to the private outer/inner class access
        // needed here.  But for some reason, PeopleActivity$1.class is missing.
        // Since $1 class is needed as a jvm work around to get access to the inner class,
        // changing the constructor to package-protected or public will solve the problem.
        // To verify whether $1 class is needed, javap PeopleActivity$TabPagerListener and look for
        // references to PeopleActivity$1.
        //
        // When the constructor is private and PeopleActivity$1.class is missing, proguard will
        // correctly catch this and throw warnings and error out the build on user/userdebug builds.
        //
        // All private inner classes below also need this fix.
        TabPagerListener() {}

        @Override
        public void onPageScrollStateChanged(int state) {
            if (!mTabPagerAdapter.areTabsHidden()) {
                mViewPagerTabs.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (!mTabPagerAdapter.areTabsHidden()) {
                mViewPagerTabs.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            //add DWYYL-863 songqingming 20150526 (start)
            if (SystemProperties.getInt("ro.rgk_contacts_letter_search", 0) == 1) {
                mAllFragment.closeSideBar();
            }
            //add DWYYL-863 songqingming 20150526 (end)
        }

        @Override
        public void onPageSelected(int position) {
            // Make sure not in the search mode, in which case position != TabState.ordinal().
            if (!mTabPagerAdapter.areTabsHidden()) {
                //add EWLLL-795 liujingyi start
                if (mFloatingImageButton != null) {
                    if (position == 2) {
                	    mFloatingImageButton.setImageResource(R.drawable.ic_add_group_dark);
                    } else {
                    	mFloatingImageButton.setImageResource(R.drawable.ic_person_add_24dp);
                    }
                }
                //add EWLLL-795 liujingyi end
                mActionBarAdapter.setCurrentTab(position, false);
                mViewPagerTabs.onPageSelected(position);
                showEmptyStateForTab(position);
                /// M: [vcs] @{
                if (mVcsController != null) {
                    mVcsController.onPageSelectedVcs();
                }
                /// @}
                invalidateOptionsMenu();
            }
        }
    }

    /**
     * Adapter for the {@link ViewPager}.  Unlike {@link FragmentPagerAdapter},
     * {@link #instantiateItem} returns existing fragments, and {@link #instantiateItem}/
     * {@link #destroyItem} show/hide fragments instead of attaching/detaching.
     *
     * In search mode, we always show the "all" fragment, and disable the swipe.  We change the
     * number of items to 1 to disable the swipe.
     *
     * TODO figure out a more straight way to disable swipe.
     */
    private class TabPagerAdapter extends PagerAdapter {
        private final FragmentManager mFragmentManager;
        private FragmentTransaction mCurTransaction = null;

        private boolean mAreTabsHiddenInTabPager;

        private Fragment mCurrentPrimaryItem;

        public TabPagerAdapter() {
            mFragmentManager = getFragmentManager();
        }

        public boolean areTabsHidden() {
            return mAreTabsHiddenInTabPager;
        }

        public void setTabsHidden(boolean hideTabs) {
            if (hideTabs == mAreTabsHiddenInTabPager) {
                return;
            }
            mAreTabsHiddenInTabPager = hideTabs;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mAreTabsHiddenInTabPager ? 1 : TabState.COUNT;
        }

        /** Gets called when the number of items changes. */
        @Override
        public int getItemPosition(Object object) {
            if (mAreTabsHiddenInTabPager) {
                if (object == mAllFragment) {
                    return 0; // Only 1 page in search mode
                }
            } else {
                if (object == mFavoritesFragment) {
                    return getTabPositionForTextDirection(TabState.FAVORITES);
                }
                if (object == mAllFragment) {
                    return getTabPositionForTextDirection(TabState.ALL);
                }
                //add EWLLL-795 liujingyi start
                if (object == mGroupFragment) {
                    return getTabPositionForTextDirection(TabState.GROUP);
                }
                //add EWLLL-795 liujingyi end
            }
            return POSITION_NONE;
        }

        @Override
        public void startUpdate(ViewGroup container) {
        }

        private Fragment getFragment(int position) {
            position = getTabPositionForTextDirection(position);
            if (mAreTabsHiddenInTabPager) {
                if (position != 0) {
                    // This has only been observed in monkey tests.
                    // Let's log this issue, but not crash
                    Log.w(TAG, "Request fragment at position=" + position + ", eventhough we " +
                            "are in search mode");
                }
                return mAllFragment;
            } else {
                if (position == TabState.FAVORITES) {
                    return mFavoritesFragment;
                } else if (position == TabState.ALL) {
                    return mAllFragment;
                }
                //add EWLLL-795 liujingyi start
                else if (position == TabState.GROUP) {
                    return mGroupFragment;
                }
                //add EWLLL-795 liujingyi end
            }
            throw new IllegalArgumentException("position: " + position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            Fragment f = getFragment(position);
            mCurTransaction.show(f);

            // Non primary pages are not visible.
            f.setUserVisibleHint(f == mCurrentPrimaryItem);
            return f;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            mCurTransaction.hide((Fragment) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            if (mCurTransaction != null) {
                mCurTransaction.commitAllowingStateLoss();
                mCurTransaction = null;
                mFragmentManager.executePendingTransactions();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return ((Fragment) object).getView() == view;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Fragment fragment = (Fragment) object;
            if (mCurrentPrimaryItem != fragment) {
                if (mCurrentPrimaryItem != null) {
                    mCurrentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    fragment.setUserVisibleHint(true);
                }
                mCurrentPrimaryItem = fragment;
            }
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

    private void setQueryTextToFragment(String query) {
        mAllFragment.setQueryString(query, true);
        //modify DWYYL-863 songqingming 20150526 (start)
        //mAllFragment.setVisibleScrollbarEnabled(!mAllFragment.isSearchMode());
        if (SystemProperties.getInt("ro.rgk_contacts_letter_search", 0) == 1) {
            mAllFragment.setVisibleScrollbarEnabled(false);
        } else {
            mAllFragment.setVisibleScrollbarEnabled(!mAllFragment.isSearchMode());
        }
        //modify DWYYL-863 songqingming 20150526 (end)
    }

    private void configureContactListFragmentForRequest() {
        Uri contactUri = mRequest.getContactUri();
        if (contactUri != null) {
            mAllFragment.setSelectedContactUri(contactUri);
        }

        mAllFragment.setFilter(mContactListFilterController.getFilter());
        setQueryTextToFragment(mActionBarAdapter.getQueryString());

        if (mRequest.isDirectorySearchEnabled()) {
            mAllFragment.setDirectorySearchMode(DirectoryListLoader.SEARCH_MODE_DEFAULT);
        } else {
            mAllFragment.setDirectorySearchMode(DirectoryListLoader.SEARCH_MODE_NONE);
        }
    }

    private void configureContactListFragment() {
        // Filter may be changed when this Activity is in background.
        mAllFragment.setFilter(mContactListFilterController.getFilter());

        mAllFragment.setVerticalScrollbarPosition(getScrollBarPosition());
        mAllFragment.setSelectionVisible(false);
    }

    private int getScrollBarPosition() {
        return isRTL() ? View.SCROLLBAR_POSITION_LEFT : View.SCROLLBAR_POSITION_RIGHT;
    }

    private boolean isRTL() {
        final Locale locale = Locale.getDefault();
        return TextUtils.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL;
    }

    @Override
    public void onProviderStatusChange() {
        Log.d(TAG, "[onProviderStatusChange]");
        updateViewConfiguration(false);
    }

    private void updateViewConfiguration(boolean forceUpdate) {
        Log.d(TAG, "[updateViewConfiguration]forceUpdate = " + forceUpdate);
        int providerStatus = mProviderStatusWatcher.getProviderStatus();
        if (!forceUpdate && (mProviderStatus != null)
                && (mProviderStatus.equals(providerStatus))) return;
        mProviderStatus = providerStatus;

        View contactsUnavailableView = findViewById(R.id.contacts_unavailable_view);

        if (mProviderStatus.equals(ProviderStatus.STATUS_NORMAL)) {
            // Ensure that the mTabPager is visible; we may have made it invisible below.
            contactsUnavailableView.setVisibility(View.GONE);
            if (mTabPager != null) {
                mTabPager.setVisibility(View.VISIBLE);
            }

            if (mAllFragment != null) {
                mAllFragment.setEnabled(true);
            }
        } else {
            // If there are no accounts on the device and we should show the "no account" prompt
            // (based on {@link SharedPreferences}), then launch the account setup activity so the
            // user can sign-in or create an account.
            //
            // Also check for ability to modify accounts.  In limited user mode, you can't modify
            // accounts so there is no point sending users to account setup activity.
            final UserManager userManager = (UserManager) getSystemService(Context.USER_SERVICE);
            final boolean disallowModifyAccounts = userManager.getUserRestrictions().getBoolean(
                    UserManager.DISALLOW_MODIFY_ACCOUNTS);
            if (!disallowModifyAccounts && !areContactWritableAccountsAvailable() &&
                    AccountPromptUtils.shouldShowAccountPrompt(this)) {
                Log.i(TAG, "[updateViewConfiguration]return.");
                AccountPromptUtils.neverShowAccountPromptAgain(this);
                AccountPromptUtils.launchAccountPrompt(this);
                return;
            }

            // Otherwise, continue setting up the page so that the user can still use the app
            // without an account.
            if (mAllFragment != null) {
                mAllFragment.setEnabled(false);
            }
            if (mContactsUnavailableFragment == null) {
                mContactsUnavailableFragment = new ContactsUnavailableFragment();
                mContactsUnavailableFragment.setOnContactsUnavailableActionListener(
                        new ContactsUnavailableFragmentListener());
                getFragmentManager().beginTransaction()
                        .replace(R.id.contacts_unavailable_container, mContactsUnavailableFragment)
                        .commitAllowingStateLoss();
            }
            mContactsUnavailableFragment.updateStatus(mProviderStatus);

            // Show the contactsUnavailableView, and hide the mTabPager so that we don't
            // see it sliding in underneath the contactsUnavailableView at the edges.
            /**
             * M: Bug Fix @{
             * CR ID: ALPS00113819 Descriptions:
             * remove ContactUnavaliableFragment
             * Fix wait cursor keeps showing while no contacts issue
             */
            ActivitiesUtils.setAllFramgmentShow(contactsUnavailableView, mAllFragment,
                    this, mTabPager, mContactsUnavailableFragment, mProviderStatus);

            showEmptyStateForTab(mActionBarAdapter.getCurrentTab());
        }

        invalidateOptionsMenuIfNeeded();
    }

    private final class ContactBrowserActionListener implements OnContactBrowserActionListener {
        ContactBrowserActionListener() {}

        @Override
        public void onSelectionChange() {

        }

        @Override
        public void onViewContactAction(Uri contactLookupUri) {
            Log.d(TAG, "[onViewContactAction]contactLookupUri = " + contactLookupUri);
            final Intent intent = ImplicitIntentsUtil.composeQuickContactIntent(contactLookupUri,
                    QuickContactActivity.MODE_FULLY_EXPANDED);
            ImplicitIntentsUtil.startActivityInApp(PeopleActivity.this, intent);
        }

        @Override
        public void onDeleteContactAction(Uri contactUri) {
            Log.d(TAG, "[onDeleteContactAction]contactUri = " + contactUri);
            ContactDeletionInteraction.start(PeopleActivity.this, contactUri, false);
        }

        @Override
        public void onFinishAction() {
            Log.d(TAG, "[onFinishAction]call onBackPressed");
            onBackPressed();
        }

        @Override
        public void onInvalidSelection() {
            ContactListFilter filter;
            ContactListFilter currentFilter = mAllFragment.getFilter();
            if (currentFilter != null
                    && currentFilter.filterType == ContactListFilter.FILTER_TYPE_SINGLE_CONTACT) {
                filter = ContactListFilter.createFilterWithType(
                        ContactListFilter.FILTER_TYPE_ALL_ACCOUNTS);
                mAllFragment.setFilter(filter);
            } else {
                filter = ContactListFilter.createFilterWithType(
                        ContactListFilter.FILTER_TYPE_SINGLE_CONTACT);
                mAllFragment.setFilter(filter, false);
            }
            mContactListFilterController.setContactListFilter(filter, true);
        }
    }

    private final class CheckBoxListListener implements OnCheckBoxListActionListener {
        @Override
        public void onStartDisplayingCheckBoxes() {
            Log.d(TAG, "[onStartDisplayingCheckBoxes]");
            mActionBarAdapter.setSelectionMode(true);
            invalidateOptionsMenu();
        }

        @Override
        public void onSelectedContactIdsChanged() {
            Log.d(TAG, "[onSelectedContactIdsChanged]size = "
                    + mAllFragment.getSelectedContactIds().size());
            mActionBarAdapter.setSelectionCount(mAllFragment.getSelectedContactIds().size());
            invalidateOptionsMenu();
        }

        @Override
        public void onStopDisplayingCheckBoxes() {
            Log.d(TAG, "[onStopDisplayingCheckBoxes]");
            mActionBarAdapter.setSelectionMode(false);
            /// M:[vcs] VCS Feature. @{
            if (mVcsController != null) {
                int count = mAllFragment.getAdapter().getCount();
                if (count <= 0) {
                    mVcsController.onPauseVcs();
                } else {
                    mVcsController.onResumeVcs();
                }
            }
            /// @}
        }
    }

    private class ContactsUnavailableFragmentListener
            implements OnContactsUnavailableActionListener {
        ContactsUnavailableFragmentListener() {}

        @Override
        public void onCreateNewContactAction() {
            Log.d(TAG, "[onCreateNewContactAction]");
            ImplicitIntentsUtil.startActivityInApp(PeopleActivity.this,
                    EditorIntents.createCompactInsertContactIntent());
        }

        @Override
        public void onAddAccountAction() {
            Log.d(TAG, "[onAddAccountAction]");
            Intent intent = new Intent(Settings.ACTION_ADD_ACCOUNT);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            intent.putExtra(Settings.EXTRA_AUTHORITIES,
                    new String[] { ContactsContract.AUTHORITY });
            ImplicitIntentsUtil.startActivityOutsideApp(PeopleActivity.this, intent);
        }

        @Override
        public void onImportContactsFromFileAction() {
            Log.d(TAG, "[onImportContactsFromFileAction]");
            /** M: New Feature.use mtk importExport function,use the
             * encapsulate class do this.@{*/
            ActivitiesUtils.doImportExport(PeopleActivity.this);
            /** @} */

        }
    }

    private final class StrequentContactListFragmentListener
            implements ContactTileListFragment.Listener {
        StrequentContactListFragmentListener() {}

        @Override
        public void onContactSelected(Uri contactUri, Rect targetRect) {
            final Intent intent = ImplicitIntentsUtil.composeQuickContactIntent(contactUri,
                    QuickContactActivity.MODE_FULLY_EXPANDED);
            ImplicitIntentsUtil.startActivityInApp(PeopleActivity.this, intent);
        }

        @Override
        public void onCallNumberDirectly(String phoneNumber) {
            // No need to call phone number directly from People app.
            Log.w(TAG, "unexpected invocation of onCallNumberDirectly()");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "[onCreateOptionsMenu]");

        if (!areContactsAvailable()) {
            Log.i(TAG, "[onCreateOptionsMenu]contacts aren't available, hide all menu items");
            // If contacts aren't available, hide all menu items.
            /// M:Fix option menu disappearance issue when change language. @{
            mOptionsMenuContactsAvailable = false;
            /// @}

            // M: fix ALPS02454655.only sim contacts selected,menu show in screen-left when
            // turn on airmode.@{
            if (menu != null) {
                Log.d(TAG, "[onCreateOptionsMenu] close menu if open!");
                menu.close();
            }
            //@}

            return false;
        }
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.people_options, menu);

        /// M: Op01 will add "show sim capacity" item
        ExtensionManager.getInstance().getOp01Extension().addOptionsMenu(this, menu);

        /// M:OP01 RCS will add people menu item
        ExtensionManager.getInstance().getRcsExtension().addPeopleMenuOptions(menu);

        /// M: [vcs] VCS new feature @{
        if (mVcsController != null) {
            mVcsController.onCreateOptionsMenuVcs(menu);
        }
        /// @}
        PDebug.End("onCreateOptionsMenu");
        return true;
    }

    private void invalidateOptionsMenuIfNeeded() {
        if (isOptionsMenuChanged()) {
            invalidateOptionsMenu();
        }
    }

    public boolean isOptionsMenuChanged() {
        if (mOptionsMenuContactsAvailable != areContactsAvailable()) {
            return true;
        }

        if (mAllFragment != null && mAllFragment.isOptionsMenuChanged()) {
            return true;
        }

        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "[onPrepareOptionsMenu]");
        PDebug.Start("onPrepareOptionsMenu");
        /// M: Fix ALPS01612926,smartbook issue @{
        if (mActionBarAdapter == null) {
            Log.w(TAG, "[onPrepareOptionsMenu]mActionBarAdapter is null,return..");
            return true;
        }
        /// @}
        mOptionsMenuContactsAvailable = areContactsAvailable();
        if (!mOptionsMenuContactsAvailable) {
            Log.w(TAG, "[onPrepareOptionsMenu]areContactsAvailable is false,return..");
            return false;
        }
        // Get references to individual menu items in the menu
        final MenuItem contactsFilterMenu = menu.findItem(R.id.menu_contacts_filter);

        /** M: New Feature @{ */
        final MenuItem groupMenu = menu.findItem(R.id.menu_groups);
        /** @} */
        /// M: [VoLTE ConfCall]
        final MenuItem conferenceCallMenu = menu.findItem(R.id.menu_conference_call);


        final MenuItem clearFrequentsMenu = menu.findItem(R.id.menu_clear_frequents);
        final MenuItem helpMenu = menu.findItem(R.id.menu_help);

        //begin add by zhouzhuobin for JSLEL-743 20140924
        final MenuItem mergeDuplicateMenu = menu.findItem(R.id.menu_mergeduplicate_contact);
        if (!getResources().getBoolean(R.bool.RGK_SUPPORT_REMOVE_DUPLICATE_CONTACT)) {
        	mergeDuplicateMenu.setVisible(false);
        }
        //end add by zhouzhuobin for JSLEL-743 20140924

        final boolean isSearchOrSelectionMode = mActionBarAdapter.isSearchMode()
                || mActionBarAdapter.isSelectionMode();
        if (isSearchOrSelectionMode) {
            contactsFilterMenu.setVisible(false);
            clearFrequentsMenu.setVisible(false);
            helpMenu.setVisible(false);
            /** M: New Feature @{ */
            groupMenu.setVisible(false);
            /** @} */
            /// M: [VoLTE ConfCall]
            conferenceCallMenu.setVisible(false);
        } else {
            switch (getTabPositionForTextDirection(mActionBarAdapter.getCurrentTab())) {
                case TabState.FAVORITES:
                    contactsFilterMenu.setVisible(false);
                    clearFrequentsMenu.setVisible(hasFrequents());
                    break;
                case TabState.ALL:
                    contactsFilterMenu.setVisible(true);
                    clearFrequentsMenu.setVisible(false);
                    break;
                default:
                     break;
            }
            helpMenu.setVisible(HelpUtils.isHelpAndFeedbackAvailable());
        }
        final boolean showMiscOptions = !isSearchOrSelectionMode;
        makeMenuItemVisible(menu, R.id.menu_search, showMiscOptions);
        makeMenuItemVisible(menu, R.id.menu_import_export,
                showMiscOptions && ActivitiesUtils.showImportExportMenu(this));
        makeMenuItemVisible(menu, R.id.menu_accounts, showMiscOptions);
        makeMenuItemVisible(menu, R.id.menu_settings,
                showMiscOptions && !ContactsPreferenceActivity.isEmpty(this));

        final boolean showSelectedContactOptions = mActionBarAdapter.isSelectionMode()
                && mAllFragment.getSelectedContactIds().size() != 0;
        makeMenuItemVisible(menu, R.id.menu_share, showSelectedContactOptions);
        makeMenuItemVisible(menu, R.id.menu_delete, showSelectedContactOptions);
        makeMenuItemVisible(menu, R.id.menu_join, showSelectedContactOptions);
        ///M: Bug fix, if selected contacts just only one, it will show an dialog to remind user.
        makeMenuItemEnabled(menu, R.id.menu_join, mAllFragment.getSelectedContactIds().size() >= 1);

        // Debug options need to be visible even in search mode.
        makeMenuItemVisible(menu, R.id.export_database, mEnableDebugMenuOptions);

        /** M: For VCS new feature */
        ActivitiesUtils.prepareVcsMenu(menu, mVcsController);
        PDebug.End("onPrepareOptionsMenu");

        /// M: [VoLTE ConfCall] @{
        if (!VolteUtils.isVoLTEConfCallEnable(this)) {
            conferenceCallMenu.setVisible(false);
        }
        /// @}

        /// M: add for A1 @ {
        if (SystemProperties.get("ro.mtk_a1_feature").equals("1")) {
            Log.i(TAG, "[onPrepareOptionsMenu]enable a1 feature.");
            groupMenu.setVisible(false);
        }
        /// @ }

        //add EWLLL-795 liujingyi start
        groupMenu.setVisible(SystemProperties.getInt("ro.rgk_contact_add_group_tab", 0) == 1 ? false : true);
        //add EWLLL-795 liujingyi end

        if (SystemProperties.getInt("ro.rgk_merge_contacts_support", 0) != 1) {
            makeMenuItemVisible(menu, R.id.menu_mergeduplicate_contact, false);
        } else {
            //begin add by zzb for JWBLL-1420 20150402
            makeMenuItemVisible(menu, R.id.menu_mergeduplicate_contact, showMiscOptions && 
		                          getTabPositionForTextDirection(mActionBarAdapter.getCurrentTab()) != TabState.FAVORITES);
            //end add by zzb for JWBLL-1420 20150402
		}

        return true;
    }

    /**
     * Returns whether there are any frequently contacted people being displayed
     * @return
     */
    private boolean hasFrequents() {
        return mFavoritesFragment.hasFrequents();
    }

    private void makeMenuItemVisible(Menu menu, int itemId, boolean visible) {
        final MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setVisible(visible);
        }
    }

    private void makeMenuItemEnabled(Menu menu, int itemId, boolean visible) {
        final MenuItem item = menu.findItem(itemId);
        if (item != null) {
            item.setEnabled(visible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "[onOptionsItemSelected] mDisableOptionItemSelected = "
                + mDisableOptionItemSelected);
        if (mDisableOptionItemSelected) {
            return false;
        }

        switch (item.getItemId()) {
            case android.R.id.home: {
                // The home icon on the action bar is pressed
                if (mActionBarAdapter.isUpShowing()) {
                    // "UP" icon press -- should be treated as "back".
                    onBackPressed();
                }
                return true;
            }
            case R.id.menu_settings: {
                final Intent intent = new Intent(this, ContactsPreferenceActivity.class);
                // Since there is only one section right now, make sure it is selected on
                // small screens.
                intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT,
                        DisplayOptionsPreferenceFragment.class.getName());
                // By default, the title of the activity should be equivalent to the fragment
                // title. We set this argument to avoid this. Because of a bug, the following
                // line isn't necessary. But, once the bug is fixed this may become necessary.
                // b/5045558 refers to this issue, as well as another.
                intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT_TITLE,
                        R.string.activity_title_settings);
                startActivity(intent);
                return true;
            }
            case R.id.menu_contacts_filter: {
                AccountFilterUtil.startAccountFilterActivityForResult(
                        this, SUBACTIVITY_ACCOUNT_FILTER,
                        mContactListFilterController.getFilter());
                return true;
            }
            case R.id.menu_search: {
                onSearchRequested();
                return true;
            }
            case R.id.menu_share:
                shareSelectedContacts();
                return true;
            case R.id.menu_join:
                joinSelectedContacts();
                return true;
            case R.id.menu_delete:
                deleteSelectedContacts();
                return true;
            case R.id.menu_import_export: {
                /** M: Change Feature */
                return ActivitiesUtils.doImportExport(this);
            }
            case R.id.menu_clear_frequents: {
                ClearFrequentsDialog.show(getFragmentManager());
                return true;
            }
            case R.id.menu_help:
                HelpUtils.launchHelpAndFeedbackForMainScreen(this);
                return true;
            case R.id.menu_accounts: {
                final Intent intent = new Intent(Settings.ACTION_SYNC_SETTINGS);
                intent.putExtra(Settings.EXTRA_AUTHORITIES, new String[] {
                    ContactsContract.AUTHORITY
                });
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                ImplicitIntentsUtil.startActivityInAppIfPossible(this, intent);
                return true;
            }
            case R.id.export_database: {
                final Intent intent = new Intent("com.android.providers.contacts.DUMP_DATABASE");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                ImplicitIntentsUtil.startActivityOutsideApp(this, intent);
                return true;
            }
            /** M: New feature @{ */
            /** M: [vcs] */
            case R.id.menu_vcs: {
                Log.d(TAG,"[onOptionsItemSelected]menu_vcs");
                if (mVcsController != null) {
                    mVcsController.onVcsItemSelected();
                }
                return true;
            }
            /** M: Group related */
            case R.id.menu_groups: {
                startActivity(new Intent(PeopleActivity.this, GroupBrowseActivity.class));
                return true;
            }
            /** @} */
            /** M: [VoLTE ConfCall]Conference call @{*/
            case R.id.menu_conference_call: {
                Log.d(TAG,"[onOptionsItemSelected]menu_conference_call");
            return ActivitiesUtils.conferenceCall(this);
            }
            /** @} */
            //begin add by zhouzhuobin for swim-129 20140902
            case R.id.menu_mergeduplicate_contact: {	
            	Log.d("zzb","remove the duplicate menu onclick");
            	AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
        				PeopleActivity.this);
            	builder.setMessage(getString(R.string.remove_duplicate_contact_info1));
            	builder.setTitle(getString(R.string.remove_duplicate_contact_title));
            	builder.setPositiveButton(getString(R.string.button_ok),
            			new OnClickListener() {
            			@Override 
            			public void onClick(DialogInterface dialog, int which) {
            				progressDialog = ProgressDialog.show(PeopleActivity.this,null,
            						getString(R.string.remove_duplicate_contact_info));
            				String[] projection = {
            							ContactsContract.RawContacts.CONTACT_ID,
            							ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
            							ContactsContract.RawContacts.ACCOUNT_NAME,
            							ContactsContract.RawContacts.STARRED};
            				String selection = "indicate_phone_or_sim_contact =" + (-1) + " and deleted = " + (0); //only query PHONE account
            				asyncQuery = new MyAsyncQueryHandler(getContentResolver());
            				asyncQuery.startQuery(0, null, ContactsContract.RawContacts.CONTENT_URI, projection, selection, null, null);
            				dialog.dismiss();
            			}
            	});
            	builder.setNegativeButton(getString(R.string.button_cancel),
            			new OnClickListener() {
            		@Override
            		public void onClick(DialogInterface dialog, int which) {
            			dialog.dismiss();
            		}
            	});
            	builder.create().show();
            	return true;
            	 //end add by zhouzhuobin for swim-129 20140902
            }
        }
        return false;
    }

    //begin add by zhouzhuobin for SWIM-129 2010902
    private final class MyAsyncQueryHandler extends AsyncQueryHandler {
    	public MyAsyncQueryHandler(ContentResolver cr) {
    		super(cr);
    	}
    	
    	@Override
    	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
    		Log.d("zzb","-------onQueryComplete-------");
    		switch (token) {
    		case 0: 
    			mListDatas.clear();
    			mergerDatas.clear();
    			if (cursor != null && cursor.getCount() > 0) {
    				while(cursor.moveToNext()) {
    					Log.d("zzb",cursor.toString());
    					
    					ContactData contactData = new ContactData();
    					contactData.setConatctId(cursor.getLong(0));
    					//contactData.setLookupKey(cursor.getString(1));
    					contactData.setDisplayName(cursor.getString(1));
    					contactData.setAccountName(cursor.getString(2));
    					contactData.setStarred(cursor.getInt(3));
    					//contactData.setHasPhoneNum(cursor.getInt(3));
    					mListDatas.add(contactData);
    					//readContacts(contactData.getContactId());
    				}
    				
    				mergeDuplicateContacts();
    				cursor.close();
    			}
			 
			else if (null != progressDialog){
				Log.d("zzb",TAG + "There is no conatct,needn't to merge");
				progressDialog.dismiss();			
			}
		
    			break;
    			
    		}
    	}
    }
    
   
    private ContactInfoDataSet readContacts(long contactId) {
    	ContactInfoDataSet contactInfoDataSet = new ContactInfoDataSet();
    	ArrayList<ContactInfoData> numList = new  ArrayList<ContactInfoData>();
    	ArrayList<ContactInfoData> emailList = new  ArrayList<ContactInfoData>();
    	ArrayList<ContactInfoData> addressList = new  ArrayList<ContactInfoData>();
    	
    	ContactInfoData im = null;
    	ContactInfoData nick = null;
    	//ArrayList<ContactInfoData> identityList = new  ArrayList<ContactInfoData>();
    	ContactInfoData website = null;
    	ContactInfoData note = null;
    	
    	ContactInfoData info = null;
    	ContactInfoData photo = null;
    	byte[] photoData = null;
    	int rawContactId = 0;
    	
    	Uri uri = Uri.parse("content://com.android.contacts/contacts/" + Long.toString(contactId) + "/data");
    	Cursor c = getContentResolver().query(uri, new String[]{"data1","data2","data3","data4","data5","data6","data7","data8","data9","data14","data15","mimetype","raw_contact_id"},null,null,null);
    	if (c != null && c.getCount() > 0) {
    		while (c.moveToNext()) {
    			info = new ContactInfoData();
	            rawContactId = c.getInt(c.getColumnIndex("raw_contact_id"));
    			if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/phone_v2")) {
    				info.setData(1,c.getString(c.getColumnIndex("data1")));
    				info.setData2(c.getInt(c.getColumnIndex("data2")));
    				numList.add(info);
    			} else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/email_v2")) {
    				info.setData(1,c.getString(c.getColumnIndex("data1")));
    				info.setData2(c.getInt(c.getColumnIndex("data2")));
    				emailList.add(info);
    			} else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/postal-address_v2")) {
    				info.setData(1,c.getString(c.getColumnIndex("data1")));
    				info.setData2(c.getInt(c.getColumnIndex("data2")));
    				info.setData(4,c.getString(c.getColumnIndex("data4")));
    				info.setData(5,c.getString(c.getColumnIndex("data5")));
    				info.setData(6,c.getString(c.getColumnIndex("data6")));
    				info.setData(7,c.getString(c.getColumnIndex("data7")));
    				info.setData(8,c.getString(c.getColumnIndex("data8")));
    				info.setData(9,c.getString(c.getColumnIndex("data9")));
    				addressList.add(info);
    			} else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/im")) {  //2
    				im = new ContactInfoData();
    				im.setData(1,c.getString(c.getColumnIndex("data1")));
    				im.setData2(c.getInt(c.getColumnIndex("data2")));
    			}else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/nickname")) {  //3
    				nick = new ContactInfoData();
    				nick.setData(1,c.getString(c.getColumnIndex("data1")));
    				nick.setData2(c.getInt(c.getColumnIndex("data2")));	
    			} else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/website")) { //12
    				website = new ContactInfoData();
    				website.setData(1,c.getString(c.getColumnIndex("data1")));
    				website.setData2(c.getInt(c.getColumnIndex("data2")));
    			} else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/note")) { //13
    				note = new ContactInfoData();
    				note.setData(1,c.getString(c.getColumnIndex("data1")));
    			} else if (c.getString(c.getColumnIndex("mimetype")).equals("vnd.android.cursor.item/photo")) {
    				photo = new ContactInfoData();
                    photo.setPhotoFileId(c.getInt(c.getColumnIndex("data14")));
    				photoData = new byte[0];
    				photoData = c.getBlob(c.getColumnIndex("data15"));
    				if (photoData != null){
    					photo.setPhoto(photoData);
    				}
    				
    			}
    		}
    	}
    	
    	
    		contactInfoDataSet.setNumlist(numList);
    		contactInfoDataSet.setEmaillist(emailList);
    		contactInfoDataSet.setAddresslist(addressList);
    		contactInfoDataSet.setIm(im);
    		contactInfoDataSet.setNote(note);
    		contactInfoDataSet.setWeb(website);
    		contactInfoDataSet.setNick(nick);
    		contactInfoDataSet.setPhoto(photo);
            contactInfoDataSet.setRawContactId(rawContactId);
    
    	//Log.d("zzb","all:" + contactInfoDataSet.toString());
    	
    	if (info != null) info = null;
    	if (im != null) im = null;
    	if (note != null) note = null;
    	if (website != null) website = null;
    	if (nick != null) nick = null;
    	if (photo != null) photo = null;
    	if (photoData != null) photoData = null;
    	c.close();
    	return contactInfoDataSet;
    }
    
   
    private boolean findAllDuplicateContacts(ContactData data) {
		for (MergerData mergerData : mergerDatas) {
			//begin modify by zhouzhuobin for JSLEL-992 20140929
								Log.i("jingyi", "data.getDisplayName()="+data.getDisplayName()+" data.getAccountName()="+data.getAccountName()
					        + " mergerData.getData().getDisplayName()="+mergerData.getData().getDisplayName()+" mergerData.getData().getAccountName()="+mergerData.getData().getAccountName());
			if(mergerData.getData() != null 
					&& data.getDisplayName()!= null
					&& data.getAccountName() != null
					&& data.getDisplayName().equals(mergerData.getData().getDisplayName())
					&& data.getAccountName().equals(mergerData.getData().getAccountName())){
			//end modify by zhouzhuobin for JSLEL-992 20140929
				if(mergerData.getList() != null){
					mergerData.getList().add(data);
				}else{
					ArrayList<ContactData> list = new ArrayList<ContactData>();
					list.add(data);
					mergerData.setList(list);
                    if (data.getStarred() == 1) {
                         mergerData.getData().setStarred(1);
                    }
				}
				return true;
			}
		}
		return false;
	}
    
	private void mergeDuplicateContacts() {
		new Thread(){
			public void run() {
				for (ContactData data : mListDatas) {
					if(!findAllDuplicateContacts(data)){
						MergerData mergerData = new MergerData();
						mergerData.setData(data);
						mergerDatas.add(mergerData);
					}
				}
				for (MergerData mergerData : mergerDatas) {
					//Log.d("zzb", "startMergerContact -- > name = " + mergerData.getData().getDisplayName());
				
					int i = 1;
					ContactInfoDataSet baseInfo = new ContactInfoDataSet();
					baseInfo = readContacts(mergerData.getData().getContactId());
					
					ContactInfoDataSet info = null;
					ArrayList<ContactInfoData> num = new ArrayList<ContactInfoData> ();
					ArrayList<ContactInfoData> email = new ArrayList<ContactInfoData> ();
					ArrayList<ContactInfoData> address = new ArrayList<ContactInfoData> ();
					ContactInfoData im = new ContactInfoData ();
					ContactInfoData web = new ContactInfoData ();
					ContactInfoData nickname = new ContactInfoData ();
					ContactInfoData note = new ContactInfoData ();
					ContactInfoData photo = new ContactInfoData();
					
					
					ContactInfoData nTemp = null;
					ContactInfoData eTemp = null;
					ContactInfoData aTemp = null;
					
					
					ArrayList<ContactInfoData> baseNum = new ArrayList<ContactInfoData> ();
					ArrayList<ContactInfoData> baseEmail = new ArrayList<ContactInfoData> ();
					ArrayList<ContactInfoData> baseAddress = new ArrayList<ContactInfoData> ();
					ContactInfoData baseIm = new ContactInfoData ();
					ContactInfoData baseWeb = new ContactInfoData ();
					ContactInfoData baseNick = new ContactInfoData ();
					ContactInfoData baseNote = new ContactInfoData ();
					
					
					baseNum.clear();
					baseEmail.clear();
					baseAddress.clear();
					
					
					baseNum = baseInfo.getNumlist();
					baseEmail = baseInfo.getEmaillist();
					baseAddress = baseInfo.getAddresslist();
					baseIm = baseInfo.getIm();
					baseWeb = baseInfo.getWeb();
					baseNick = baseInfo.getNick();
					baseNote = baseInfo.getNote();
					photo = baseInfo.getPhoto();
					
					//Log.d("zzb","1----base num:" + baseNum.toString());
					//Log.d("zzb","1----base email:" + baseEmail.toString());
					//Log.d("zzb","1----base address:" + baseAddress.toString());
					
					if(mergerData.getList() != null){
						for (ContactData data : mergerData.getList()) {
							
							Log.d("zzb", "mergerData -- > name = " + data.getDisplayName());
							//create new conatct and delete old contacts	
							
							info = new ContactInfoDataSet();
							num.clear();
							email.clear();
							address.clear();
							
							
							info = readContacts(data.getContactId());
							num = info.getNumlist();
							email = info.getEmaillist();
							address = info.getAddresslist();
							im = info.getIm();
							web = info.getWeb();
							nickname = info.getNick();
							note = info.getNote();
							
							if (photo == null) {
								photo = info.getPhoto();
							}
							
							//Log.d("zzb","2----num:" + num.toString());
							//Log.d("zzb","2----email:" + email.toString());
							//Log.d("zzb","2----address:" + address.toString());
							
							
							//Phone number field treatment
							for (int m = 0; m < baseNum.size(); m ++) {
								for (int n = 0; n < num.size(); n++) {
									if (baseNum.get(m).getData(1).equals(num.get(n).getData(1))) {
										num.remove(n);
									} 
								}
							}
							
							for (int k = 0; k < num.size(); k++) {
								nTemp = new ContactInfoData ();
								nTemp.setData(1,num.get(k).getData(1));
								nTemp.setData2(num.get(k).getData2());
								baseNum.add(nTemp);
							}
							
							
							//email field treatment
							for (int m = 0; m < baseEmail.size(); m++) {
								for (int n = 0; n < email.size(); n++ ) {
									if (baseEmail.get(m).getData(1).equals(email.get(n).getData(1))) {
										email.remove(n);
									}
								}
							}
							
							for (int k = 0; k < email.size(); k++) {
								eTemp = new ContactInfoData ();
								eTemp.setData(1,email.get(k).getData(1));
								eTemp.setData2(email.get(k).getData2());
								baseEmail.add(eTemp);
							}
							
							//address field treatment
							for (int m=0; m < baseAddress.size(); m++) {
								for (int n=0; n < address.size(); n++) {
									if (baseAddress.get(m).getData(1).equals(address.get(n).getData(1))) {
										address.remove(n);
									}
								}
							}
							
							for (int k=0; k < address.size(); k++) {
								aTemp = new ContactInfoData();
								aTemp.setData(1, address.get(k).getData(1));
								aTemp.setData2(address.get(k).getData2());
								aTemp.setData(4, address.get(k).getData(4));
								aTemp.setData(5, address.get(k).getData(5));
								aTemp.setData(6, address.get(k).getData(6));
								aTemp.setData(7, address.get(k).getData(7));
								aTemp.setData(8, address.get(k).getData(8));
								aTemp.setData(9, address.get(k).getData(9));
								baseAddress.add(aTemp);
								
							}
							
							//im field treatment
							if (baseIm == null) {
								if (im != null) {
									baseIm = im;
								}
							}
							
							//web field treatment
							if (baseWeb == null) {
								if (web != null) {
									baseWeb = web;
								}
							}
							
							//nickname field treatment
							if (baseNick == null) {
								if (nickname != null) {
									baseNick = nickname;
								}
							}
							
							//note field treatment
							if (baseNote == null) {
								if (note != null) {
									baseNote = note;
								}
							}
							
							//deleteContact(data.getContactId());
							deleteContact(info.getRawContactId());
							i++;
						}
					}
					if (i >= 2) {
						//deleteContact(mergerData.getData().getContactId());
						deleteContact(baseInfo.getRawContactId());
                        int starred = mergerData.getData().getStarred();
						createNewContact(mergerData.getData().getDisplayName(), starred, baseNum,
																		     baseEmail,
																		     baseAddress,
																		     baseIm,
																		     baseWeb,
																		     baseNick,
																		     baseNote,
																		     photo);
					}
					
					if (nTemp != null) nTemp = null;
					if (eTemp != null) eTemp = null;
					if (info != null) info = null;	
				} 
				mergeHandler.sendEmptyMessage(MESSAGE_SUCC_MERGE);
			};
		}.start();
	}
    
    
	
	private void deleteContact(long contactId) {
		
		if (contactId < 0) {
			Log.e("zzb", "Invalid arguments for deleteContact request");
	        return;
		}
		
	
			
		//Uri contactUri = Uri.parse("content://com.android.contacts/contacts/lookup/" + lookupKey + "/" + contactId);
		//Log.d("zzb","[delete conatct] uri is:" + contactUri.toString());
		getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, ContactsContract.Data._ID + " =?" , new String[]{String.valueOf(contactId)});
	}
	
	private void createNewContact(String displayName
	                              ,int starred
			                      ,ArrayList<ContactInfoData> num
			                      ,ArrayList<ContactInfoData> email
			          			  ,ArrayList<ContactInfoData> address
			          			  ,ContactInfoData im
			          			  ,ContactInfoData web
			          			  ,ContactInfoData nick
			          			  ,ContactInfoData note
			          			  ,ContactInfoData photo){
		
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = getContentResolver();
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		ContentProviderOperation temp = ContentProviderOperation  
		 	            .newInsert(uri)
		 	            //begin modfiy by zhouzhuobin for JWBLL-1360 20150331
		 	            .withValue("account_name", "Phone")
		 	            .withValue("account_type","Local Phone Account")
		 	            .withValue("starred",starred)
		 	            //end modfiy by zhouzhuobin for JWBLL-1360 20150331
		 	            .build();  
		operations.add(temp);  
		
		//displayname
		uri = Uri.parse("content://com.android.contacts/data");
		temp = null;
		temp = ContentProviderOperation.newInsert(uri)
		.withValueBackReference("raw_contact_id", 0) 
		.withValue("mimetype", "vnd.android.cursor.item/name") 
		.withValue("data2",displayName).build();
		operations.add(temp);
		
		//phone number
		if (num != null) {
			for (int i=0; i < num.size(); i++) {
				temp = null;
				temp = ContentProviderOperation.newInsert(uri)
				  .withValueBackReference("raw_contact_id", 0) 
				  .withValue("mimetype", "vnd.android.cursor.item/phone_v2") 
				  .withValue("data1",num.get(i).getData(1))
				  .withValue("data2",num.get(i).getData2())
				  .build();
				 operations.add(temp);
			}
		}
		
		if (email != null) {
			for (int i = 0; i < email.size(); i++) {
				temp = null;
				temp = ContentProviderOperation.newInsert(uri)
				  .withValueBackReference("raw_contact_id", 0) 
				  .withValue("mimetype", "vnd.android.cursor.item/email_v2") 
				  .withValue("data1",email.get(i).getData(1))
				  .withValue("data2",email.get(i).getData2())
				  .build();
				 operations.add(temp);
			}
		}
		
		if(address != null) {
			for (int m = 0; m < address.size(); m ++) {
				temp = null;
				temp = ContentProviderOperation.newInsert(uri)
				  .withValueBackReference("raw_contact_id", 0) 
				  .withValue("mimetype", "vnd.android.cursor.item/postal-address_v2")
				  .withValue("data4",address.get(m).getData(4))
				  .withValue("data5",address.get(m).getData(5))
				  .withValue("data6",address.get(m).getData(6))
				  .withValue("data7",address.get(m).getData(7))
				  .withValue("data8",address.get(m).getData(8))
				  .withValue("data9",address.get(m).getData(9))
				  .withValue("data2",address.get(m).getData2()).build();
				operations.add(temp);
			}
		}
		
		if (im != null) {
			temp = null;
			temp = ContentProviderOperation.newInsert(uri)
			  .withValueBackReference("raw_contact_id", 0) 
			  .withValue("mimetype", "vnd.android.cursor.item/im") 
			  .withValue("data1",im.getData(1))
			  .withValue("data2",im.getData2())
			  .build();
			 operations.add(temp);
		}
		
		if (web != null) {
			temp = null;
			temp = ContentProviderOperation.newInsert(uri)
			  .withValueBackReference("raw_contact_id", 0) 
			  .withValue("mimetype", "vnd.android.cursor.item/website") 
			  .withValue("data1",web.getData(1))
			  .withValue("data2",web.getData2())
			  .build();
			 operations.add(temp);
		}
		
		if (nick != null) {
			temp = null;
			temp = ContentProviderOperation.newInsert(uri)
			  .withValueBackReference("raw_contact_id", 0) 
			  .withValue("mimetype", "vnd.android.cursor.item/nickname") 
			  .withValue("data1",nick.getData(1))
			  .withValue("data2",nick.getData2())
			  .build();
			 operations.add(temp);
		}
		
		if (note != null) {
			temp = null;
			temp = ContentProviderOperation.newInsert(uri)
			  .withValueBackReference("raw_contact_id", 0) 
			  .withValue("mimetype", "vnd.android.cursor.item/note") 
			  .withValue("data1",note.getData(1))
			  .withValue("data2",note.getData2())
			  .build();
			 operations.add(temp);
		}
		
		if (photo != null) {
			temp = null;
			temp = ContentProviderOperation.newInsert(uri)
				.withValueBackReference("raw_contact_id", 0) 
				.withValue("mimetype", "vnd.android.cursor.item/photo")
				.withValue("data14",photo.getPhotoFileId())
				.withValue("data15",photo.getPhoto())
				.build();
				operations.add(temp);
		}
		
		try {
			resolver.applyBatch("com.android.contacts",operations);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store new contact", e);
        }	
	}	
	
	//end add by zhouzhuobin for remove duplicate contact 20140218
	
    @Override
    public boolean onSearchRequested() { // Search key pressed.
        Log.d(TAG, "[onSearchRequested]");
        if (!mActionBarAdapter.isSelectionMode()) {
            mActionBarAdapter.setSearchMode(true);
        }
        return true;
    }

    /**
     * Share all contacts that are currently selected in mAllFragment. This method is pretty
     * inefficient for handling large numbers of contacts. I don't expect this to be a problem.
     */
    private void shareSelectedContacts() {
        Log.d(TAG, "[shareSelectedContacts],set ARG_CALLING_ACTIVITY.");
        final StringBuilder uriListBuilder = new StringBuilder();
        boolean firstIteration = true;
        for (Long contactId : mAllFragment.getSelectedContactIds()) {
            if (!firstIteration)
                uriListBuilder.append(':');
            final Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, contactId);
            final Uri lookupUri = Contacts.getLookupUri(getContentResolver(), contactUri);
            if (lookupUri != null) { ///M:fix  null point exception(AOSP orginal issue:ALPS02246075)
                List<String> pathSegments = lookupUri.getPathSegments();
                uriListBuilder.append(Uri.encode(pathSegments.get(pathSegments.size() - 2)));
            }
            firstIteration = false;
        }
        final Uri uri = Uri.withAppendedPath(
                Contacts.CONTENT_MULTI_VCARD_URI,
                Uri.encode(uriListBuilder.toString()));
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(Contacts.CONTENT_VCARD_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        intent.putExtra(VCardCommonArguments.ARG_CALLING_ACTIVITY,
                PeopleActivity.class.getName());
        ImplicitIntentsUtil.startActivityOutsideApp(this, intent);
    }
    private void joinSelectedContacts() {
        Log.d(TAG, "[joinSelectedContacts]");
        JoinContactsDialogFragment.start(this, mAllFragment.getSelectedContactIds());
    }

    @Override
    public void onContactsJoined() {
        Log.d(TAG, "[onContactsJoined]");
        mActionBarAdapter.setSelectionMode(false);
    }

    private void deleteSelectedContacts() {
        Log.d(TAG, "[deleteSelectedContacts]...");
        ContactMultiDeletionInteraction.start(PeopleActivity.this,
                mAllFragment.getSelectedContactIds());
    }

    @Override
    public void onDeletionFinished() {
        Log.d(TAG, "[onDeletionFinished]");
        mActionBarAdapter.setSelectionMode(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "[onActivityResult]requestCode = " + requestCode
                + ",resultCode = " + resultCode);
        switch (requestCode) {
            case SUBACTIVITY_ACCOUNT_FILTER: {
                AccountFilterUtil.handleAccountFilterResult(
                        mContactListFilterController, resultCode, data);
                break;
            }

            // TODO: Using the new startActivityWithResultFromFragment API this should not be needed
            // anymore
            case ContactEntryListFragment.ACTIVITY_REQUEST_CODE_PICKER:
                if (resultCode == RESULT_OK) {
                    mAllFragment.onPickerResult(data);
                }

// TODO fix or remove multipicker code
//                else if (resultCode == RESULT_CANCELED && mMode == MODE_PICK_MULTIPLE_PHONES) {
//                    // Finish the activity if the sub activity was canceled as back key is used
//                    // to confirm user selection in MODE_PICK_MULTIPLE_PHONES.
//                    finish();
//                }
//                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO move to the fragment

        // Bring up the search UI if the user starts typing
        final int unicodeChar = event.getUnicodeChar();
        if ((unicodeChar != 0)
                // If COMBINING_ACCENT is set, it's not a unicode character.
                && ((unicodeChar & KeyCharacterMap.COMBINING_ACCENT) == 0)
                && !Character.isWhitespace(unicodeChar)) {
            if (mActionBarAdapter.isSelectionMode()) {
                // Ignore keyboard input when in selection mode.
                return true;
            }
            String query = new String(new int[]{ unicodeChar }, 0, 1);
            if (!mActionBarAdapter.isSearchMode()) {
                mActionBarAdapter.setSearchMode(true);
                mActionBarAdapter.setQueryString(query);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "[onBackPressed]");
        if (mActionBarAdapter.isSelectionMode()) {
            mActionBarAdapter.setSelectionMode(false);
            mAllFragment.displayCheckBoxes(false);
            /// M: Fix add contact button disappear bug
            initializeFabVisibility();
        } else if (mActionBarAdapter.isSearchMode()) {
            mActionBarAdapter.setSearchMode(false);
            /// M: Fix add contact button disappear bug
            initializeFabVisibility();
        /** M: New Feature @{ */
        } else if (!ContactsSystemProperties.MTK_PERF_RESPONSE_TIME && isTaskRoot()) {
            // Instead of stopping, simply push this to the back of the stack.
            // This is only done when running at the top of the stack;
            // otherwise, we have been launched by someone else so need to
            // allow the user to go back to the caller.
            moveTaskToBack(false);
        /** @} */
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mActionBarAdapter.onSaveInstanceState(outState);

        // Clear the listener to make sure we don't get callbacks after onSaveInstanceState,
        // in order to avoid doing fragment transactions after it.
        // TODO Figure out a better way to deal with the issue.
        mDisableOptionItemSelected = true;
        mActionBarAdapter.setListener(null);
        if (mTabPager != null) {
            mTabPager.setOnPageChangeListener(null);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // In our own lifecycle, the focus is saved and restore but later taken away by the
        // ViewPager. As a hack, we force focus on the SearchView if we know that we are searching.
        // This fixes the keyboard going away on screen rotation
        if (mActionBarAdapter.isSearchMode()) {
            mActionBarAdapter.setFocusOnSearchView();
        }
    }

    @Override
    public DialogManager getDialogManager() {
        return mDialogManager;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_action_button:
                //modify EWLLL-795 liujingyi start
            	if (getTabPositionForTextDirection(mActionBarAdapter.getCurrentTab()) == TabState.GROUP) {
                    final Intent intent = new Intent(this, GroupEditorActivity.class);
                    intent.setAction(Intent.ACTION_INSERT);
                    startActivityForResult(intent, SUBACTIVITY_NEW_GROUP);
            	} else {
                    Log.d(TAG, "[onClick]floating_action_button");
                    Intent intent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        intent.putExtras(extras);
                    }
                    try {
                        ImplicitIntentsUtil.startActivityInApp(PeopleActivity.this, intent);
                    } catch (ActivityNotFoundException ex) {
                         Toast.makeText(PeopleActivity.this, R.string.missing_app,
                                Toast.LENGTH_SHORT).show();
                    }
                }
                //modify EWLLL-795 liujingyi end
                break;
            /// M: Add for SelectAll/DeSelectAll Feature. @{
            case R.id.selection_count_text:
                Log.d(TAG, "[onClick]selection_count_text");
                // if the Window of this Activity hasn't been created,
                // don't show Popup. because there is no any window to attach .
                if (getWindow() == null) {
                    Log.w(TAG, "[onClick]current Activity window is null");
                    return;
                }
                if (mSelectionMenu == null || !mSelectionMenu.isShown()) {
                    View parent = (View) view.getParent();
                    mSelectionMenu = updateSelectionMenu(parent);
                    mSelectionMenu.show();
                } else {
                    Log.w(TAG, "mSelectionMenu is already showing, ignore this click");
                }
                break;
            /// @}
        default:
            Log.wtf(TAG, "Unexpected onClick event from " + view);
        }
    }

    /**
     * Returns the tab position adjusted for the text direction.
     */
    private int getTabPositionForTextDirection(int position) {
        if (isRTL()) {
            return TabState.COUNT - 1 - position;
        }
        return position;
    }

    /// M: [VCS]Voice Search Contacts Feature @{
    private VcsController mVcsController = null;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mVcsController != null) {
            mVcsController.dispatchTouchEventVcs(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * M: Used to dismiss the dialog floating on.
     *
     * @param v
     */
    @SuppressWarnings({ "UnusedDeclaration" })
    public void onClickDialog(View v) {
        if (mVcsController != null) {
            mVcsController.onVoiceDialogClick(v);
        }
    }
    /// @}

    /// M: Add for SelectAll/DeSelectAll Feature. @{
    private DropDownMenu mSelectionMenu;
    /**
     * add dropDown menu on the selectItems.The menu is "Select all" or
     * "Deselect all"
     *
     * @param customActionBarView
     * @return The updated DropDownMenu
     */
    private DropDownMenu updateSelectionMenu(View customActionBarView) {
        Log.d(TAG, "[updateSelectionMenu]");
        DropMenu dropMenu = new DropMenu(this);
        // new and add a menu.
        DropDownMenu selectionMenu = dropMenu.addDropDownMenu(
                (Button) customActionBarView.findViewById(R.id.selection_count_text),
                R.menu.mtk_selection);

        Button selectView = (Button) customActionBarView.findViewById(R.id.selection_count_text);
        // when click the selectView button, display the dropDown menu.
        selectView.setOnClickListener(this);
        MenuItem item = selectionMenu.findItem(R.id.action_select_all);

        // get mIsSelectedAll from fragment.
        mAllFragment.updateSelectedItemsView();
        //the menu will show "Deselect_All/ Select_All".
        if (mAllFragment.isSelectedAll()) {
            // dropDown menu title is "Deselect all".
            item.setTitle(R.string.menu_select_none);
            dropMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    // clear select all items
                    mAllFragment.updateCheckBoxState(false);
                    mAllFragment.displayCheckBoxes(false);
                    mActionBarAdapter.setSelectionMode(false);
                    initializeFabVisibility();
                    return true;
                }
            });
        } else {
            // dropDown Menu title is "Select all"
            item.setTitle(R.string.menu_select_all);
            dropMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    mAllFragment.updateCheckBoxState(true);
                    mAllFragment.displayCheckBoxes(true);
                    return true;
                }
            });
        }
        return selectionMenu;
    }
    /// @}

    /// M: Listen sim change intent @{
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "[onReceive] Received Intent:" + intent);
            // M: fix ALPS02477744 "select all" menu show left when turn on airmode@{
            if (mSelectionMenu != null && mSelectionMenu.isShown()) {
                Log.i(TAG, "[onReceive] mSelectionMenu is diss!");
                mSelectionMenu.diss();
            }
            //@}

            updateViewConfiguration(true);
            updateFragmentsVisibility();
        }
    };
    /// @}
	
    private final class GroupBrowserActionListener implements OnGroupBrowserActionListener {

        GroupBrowserActionListener() {}

        @Override
        public void onViewGroupAction(Uri groupUri) {
                int simId = -1;
                int subId = SubInfoUtils.getInvalidSubId();
        ///M: For move to other group feature.
                int count = mGroupFragment.getAccountGroupMemberCount();
                String accountType = "";
                String accountName = "";
                Log.i(TAG, "groupUri" + groupUri.toString());
                List uriList = groupUri.getPathSegments();
                Uri newGroupUri = ContactsContract.AUTHORITY_URI.buildUpon()
                        .appendPath(uriList.get(0).toString())
                        .appendPath(uriList.get(1).toString()).build();
                if (uriList.size() > 2) {
                    subId = Integer.parseInt(uriList.get(2).toString());
                    Log.i(TAG, "people subId-----------" + subId);
                }
                if (uriList.size() > 3) {
                    accountType = uriList.get(3).toString();
                }
                if (uriList.size() > 4) {
                    accountName = uriList.get(4).toString();
                }
                Log.i(TAG, "newUri-----------" + newGroupUri);
                Log.i(TAG, "accountType-----------" + accountType);
                Log.i(TAG, "accountName-----------" + accountName);
                Intent intent = new Intent(PeopleActivity.this, GroupDetailActivity.class);
                intent.setData(newGroupUri);
                intent.putExtra("AccountCategory", new AccountCategoryInfo(accountType, subId, accountName, count));
				//intent.putExtra("AccountCategory", new AccountCategoryInfo(accountType, subId, accountName));
                startActivity(intent);
        }
    }
}
