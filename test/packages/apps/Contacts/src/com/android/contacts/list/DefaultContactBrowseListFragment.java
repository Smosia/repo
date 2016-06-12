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
package com.android.contacts.list;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.contacts.R;
import com.android.contacts.common.activity.RequestPermissionsActivity;
import com.android.contacts.common.list.ContactListAdapter;
import com.android.contacts.common.list.ContactListFilter;
import com.android.contacts.common.list.ContactListFilterController;
import com.android.contacts.common.list.ContactListItemView;
import com.android.contacts.common.list.DefaultContactListAdapter;
import com.android.contacts.common.list.ProfileAndContactsLoader;
import com.android.contacts.common.util.ImplicitIntentsUtil;
import com.android.contacts.editor.ContactEditorFragment;
import com.android.contacts.common.util.AccountFilterUtil;

import com.mediatek.contacts.ExtensionManager;
import com.mediatek.contacts.util.ContactsListUtils;
import com.mediatek.contacts.util.Log;
import com.mediatek.contacts.vcs.VcsController;
import com.mediatek.contacts.widget.WaitCursorView;

//add DWYYL-863 songqingming 20150526 (start)
import com.android.contacts.common.widget.SideBar;
import com.android.contacts.common.widget.SideBar.OnTouchingLetterChangedListener;
import com.android.contacts.common.list.ContactsSectionIndexer;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemProperties;
import com.android.contacts.common.list.PinnedHeaderListView;
import android.os.Bundle;
//add DWYYL-863 songqingming 20150526 (end)

//add show contacts count start
import android.database.Cursor;
import com.android.contacts.common.model.account.AccountType;
import com.mediatek.contacts.util.AccountTypeUtils;
//add show contacts count end

/**
 * Fragment containing a contact list used for browsing (as compared to
 * picking a contact with one of the PICK intents).
 */
public class DefaultContactBrowseListFragment extends ContactBrowseListFragment {
    private static final String TAG = DefaultContactBrowseListFragment.class.getSimpleName();

    private static final int REQUEST_CODE_ACCOUNT_FILTER = 1;

    //add show contacts count start
    private TextView mCounterHeaderView;
    private View mCounterHeaderViewContainer;
    //add show contacts count end
	
    private View mSearchHeaderView;
    private View mAccountFilterHeader;
    private FrameLayout mProfileHeaderContainer;
    private View mProfileHeader;
    private Button mProfileMessage;
    private TextView mProfileTitle;
    private View mSearchProgress;
    private TextView mSearchProgressText;

    private class FilterHeaderClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            AccountFilterUtil.startAccountFilterActivityForResult(
                        DefaultContactBrowseListFragment.this,
                        REQUEST_CODE_ACCOUNT_FILTER,
                        getFilter());
        }
    }
    private OnClickListener mFilterHeaderClickListener = new FilterHeaderClickListener();

    public DefaultContactBrowseListFragment() {
        setPhotoLoaderEnabled(true);
        // Don't use a QuickContactBadge. Just use a regular ImageView. Using a QuickContactBadge
        // inside the ListView prevents us from using MODE_FULLY_EXPANDED and messes up ripples.
        setQuickContactEnabled(false);
        setSectionHeaderDisplayEnabled(true);
        //modify DWYYL-863 songqingming 20150526 (start)
        //setVisibleScrollbarEnabled(true);
        if (SystemProperties.getInt("ro.rgk_contacts_letter_search", 0) == 1) {
            setVisibleScrollbarEnabled(false);
        } else {
            setVisibleScrollbarEnabled(true);
        }
        //modify DWYYL-863 songqingming 20150526 (end)
    }

    @Override
    public CursorLoader createCursorLoader(Context context) {
        /** M: Bug Fix for ALPS00115673 Descriptions: add wait cursor. @{ */
        Log.d(TAG, "createCursorLoader");
        if (mLoadingContainer != null) {
            mLoadingContainer.setVisibility(View.GONE);
        }
        /** @} */

        return new ProfileAndContactsLoader(context);
    }

    @Override
    protected void onItemClick(int position, long id) {
        Log.d(TAG, "[onItemClick][launch]start");
        final Uri uri = getAdapter().getContactUri(position);
        if (uri == null) {
            return;
        }
        if (ExtensionManager.getInstance().getRcsExtension()
                .addRcsProfileEntryListener(uri, false)) {
            return;
        }
        viewContact(uri);
        Log.d(TAG, "[onItemClick][launch]end");
    }

    @Override
    protected ContactListAdapter createListAdapter() {
        DefaultContactListAdapter adapter = new DefaultContactListAdapter(getContext());
        adapter.setSectionHeaderDisplayEnabled(isSectionHeaderDisplayEnabled());
        adapter.setDisplayPhotos(true);
        adapter.setPhotoPosition(
                ContactListItemView.getDefaultPhotoPosition(/* opposite = */ false));
        return adapter;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.contact_list_content, null);
    }

    @Override
    protected void onCreateView(LayoutInflater inflater, ViewGroup container) {
        super.onCreateView(inflater, container);
        /// Add for ALPS02377518, should prevent accessing SubInfo if has no basic permissions. @{
        if (!RequestPermissionsActivity.hasBasicPermissions(getContext())) {
            Log.i(TAG, "[onCreateView] has no basic permissions");
            return;
        }
        /// @}
        mAccountFilterHeader = getView().findViewById(R.id.account_filter_header_container);
        mAccountFilterHeader.setOnClickListener(mFilterHeaderClickListener);

        //add show contacts count start
        mCounterHeaderView = (TextView) getView().findViewById(R.id.contacts_count);
        mCounterHeaderViewContainer = getView().findViewById(R.id.contacts_count_container);
        //add show contacts count end
		
        // Create an entry for public account and show it from now
        ExtensionManager.getInstance().getRcsExtension()
            .createPublicAccountEntryView(getListView());

        // Create an empty user profile header and hide it for now (it will be visible if the
        // contacts list will have no user profile).
        addEmptyUserProfileHeader(inflater);
        showEmptyUserProfile(false);
        /** M: Bug Fix for ALPS00115673 Descriptions: add wait cursor */
       mWaitCursorView = ContactsListUtils.initLoadingView(this.getContext(),
                getView(), mLoadingContainer, mLoadingContact, mProgress);

        // Putting the header view inside a container will allow us to make
        // it invisible later. See checkHeaderViewVisibility()
        FrameLayout headerContainer = new FrameLayout(inflater.getContext());
        mSearchHeaderView = inflater.inflate(R.layout.search_header, null, false);
        headerContainer.addView(mSearchHeaderView);
        getListView().addHeaderView(headerContainer, null, false);
        checkHeaderViewVisibility();

        mSearchProgress = getView().findViewById(R.id.search_progress);
        mSearchProgressText = (TextView) mSearchHeaderView.findViewById(R.id.totalContactsText);
		
        //add DWYLL-362 songqingming 20150513 (start)
        initSideBar();
        //add DWYLL-362 songqingming 20150513 (end)
    }

    //add DWYYL-863 songqingming 20150526 (start)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = super.onCreateView(inflater, container, savedInstanceState);
        if (SystemProperties.getInt("ro.rgk_contacts_letter_search", 0) == 1) {
            DefaultContactListAdapter adapter = (DefaultContactListAdapter)getAdapter();
            adapter.setOnChangeCursorListener(mOnChangeCursorListener);
            adapter.setOnPinnedSectionTitleChangeListener(mOnPinnedSectionTitleChangeListener);
            PinnedHeaderListView listView = (PinnedHeaderListView)getListView();
            listView.setOnHeaderInvisibleListener(mOnHeaderInvisibleListener);
        }
        return view;
    }

    private PinnedHeaderListView.OnHeaderInvisibleListener mOnHeaderInvisibleListener =
        new PinnedHeaderListView.OnHeaderInvisibleListener() {

		@Override
		public void onHeaderInvisible() {
			if (sideBar != null) {
				sideBar.setNoSelection();
			}
		}
    };

    private DefaultContactListAdapter.OnChangeCursorListener mOnChangeCursorListener =
    	new DefaultContactListAdapter.OnChangeCursorListener() {

		@Override
		public void changed() {
			if (sideBar != null) {
				sideBar.changeSections(getAdapter().getSections());
			}
		}
	};

    private DefaultContactListAdapter.OnPinnedSectionTitleChangeListener mOnPinnedSectionTitleChangeListener =
    	new DefaultContactListAdapter.OnPinnedSectionTitleChangeListener() {

		@Override
		public void onPinnedSectionTitleChanged(String title) {
			if (sideBar != null) {
				sideBar.changeCurrentLetter(title);
			}
		}
	};

    private SideBar sideBar = null;
    private TextView dialogTextView = null;

    private void initSideBar() {
    	sideBar = (SideBar) getView().findViewById(R.id.sidrbar);
	    dialogTextView = (TextView) getView().findViewById(R.id.dialog);
	    sideBar.setTextView(dialogTextView);
        if (SystemProperties.getInt("ro.rgk_contacts_letter_search", 0) == 1) {
            sideBar.setVisibility(View.VISIBLE);
        } else {
            sideBar.setVisibility(View.GONE);
        }
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                if (s == null) {
                    return;
                }
                ContactListAdapter adapter = getAdapter();
                ContactsSectionIndexer indexer = (ContactsSectionIndexer) adapter.getIndexer();
                int section = indexer.getSectionForLetter(s.charAt(0));
                int position = adapter.getPositionForSection(section);
                ListView listView = getListView();
                if (position != -1) {
                    listView.setSelection(position + listView.getHeaderViewsCount());
                }
            }
        });
    }

    //add DWYYL-1780 songqingming 20150624 (start)
    public void setFastScrollDialogBg(int resId) {
        if (dialogTextView == null) {
            return;
        }
        dialogTextView.setBackgroundResource(resId);
    }
    //add DWYYL-1780 songqingming 20150624 (end)

    @Override
    public void onStop() {
	super.onStop();
        if (SystemProperties.getInt("ro.rgk_contacts_letter_search", 0) == 1) {
            closeSideBar();
        }
    }

    public void closeSideBar() {
        if (dialogTextView != null) {
            dialogTextView.setVisibility(View.INVISIBLE);
            sideBar.setBackgroundDrawable(new ColorDrawable(0x00000000));
            sideBar.setNoSelection();
        }
    }
    //add DWYYL-863 songqingming 20150526 (end)
	
    @Override
    protected void setSearchMode(boolean flag) {
        super.setSearchMode(flag);
        checkHeaderViewVisibility();
        if (!flag) showSearchProgress(false);
    }

    /** Show or hide the directory-search progress spinner. */
    private void showSearchProgress(boolean show) {
        if (mSearchProgress != null) {
            mSearchProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private void checkHeaderViewVisibility() {
        //add show contacts count start
		if (getResources().getBoolean(R.bool.show_contacts_count)) {
            if (mCounterHeaderViewContainer != null) {
                Log.i(TAG, "isSearchMode() = " + isSearchMode());
                mCounterHeaderViewContainer.setVisibility(isSearchMode() ? View.GONE : View.VISIBLE);
            }
		}
		
        //add show contacts count end
        updateFilterHeaderView();

        // Hide the search header by default.
        if (mSearchHeaderView != null) {
            mSearchHeaderView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setFilter(ContactListFilter filter) {
        super.setFilter(filter);
        updateFilterHeaderView();
    }

    private void updateFilterHeaderView() {
        if (mAccountFilterHeader == null) {
            return; // Before onCreateView -- just ignore it.
        }
        final ContactListFilter filter = getFilter();
        if (filter != null && !isSearchMode()) {
            final boolean shouldShowHeader = AccountFilterUtil.updateAccountFilterTitleForPeople(
                    mAccountFilterHeader, filter, false);
            mAccountFilterHeader.setVisibility(shouldShowHeader ? View.VISIBLE : View.GONE);
        } else {
            mAccountFilterHeader.setVisibility(View.GONE);
        }
    }

    @Override
    public void setProfileHeader() {
        mUserProfileExists = getAdapter().hasProfile();
        showEmptyUserProfile(!mUserProfileExists && !isSearchMode());

        if (isSearchMode()) {
            ContactListAdapter adapter = getAdapter();
            if (adapter == null) {
                return;
            }

            // In search mode we only display the header if there is nothing found
            if (TextUtils.isEmpty(getQueryString()) || !adapter.areAllPartitionsEmpty()) {
                mSearchHeaderView.setVisibility(View.GONE);
                showSearchProgress(false);
            } else {
                mSearchHeaderView.setVisibility(View.VISIBLE);
                if (adapter.isLoading()) {
                    mSearchProgressText.setText(R.string.search_results_searching);
                    showSearchProgress(true);
                } else {
                    mSearchProgressText.setText(R.string.listFoundAllContactsZero);
                    mSearchProgressText.sendAccessibilityEvent(
                            AccessibilityEvent.TYPE_VIEW_SELECTED);
                    showSearchProgress(false);
                }
            }
            showEmptyUserProfile(false);
        }

        /// M: [VCS] @{
        int count = getContactCount();
        if (mContactsLoadListener != null) {
            mContactsLoadListener.onContactsLoad(count);
        }
        /// @}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ACCOUNT_FILTER) {
            if (getActivity() != null) {
                AccountFilterUtil.handleAccountFilterResult(
                        ContactListFilterController.getInstance(getActivity()), resultCode, data);
            } else {
                Log.e(TAG, "getActivity() returns null during Fragment#onActivityResult()");
            }
        }
    }

    private void showEmptyUserProfile(boolean show) {
        // Changing visibility of just the mProfileHeader doesn't do anything unless
        // you change visibility of its children, hence the call to mCounterHeaderView
        // and mProfileTitle
        Log.d(TAG, "showEmptyUserProfile show : " + show);
        mProfileHeaderContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        mProfileHeader.setVisibility(show ? View.VISIBLE : View.GONE);
        mProfileTitle.setVisibility(show ? View.VISIBLE : View.GONE);
        mProfileMessage.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * This method creates a pseudo user profile contact. When the returned query doesn't have
     * a profile, this methods creates 2 views that are inserted as headers to the listview:
     * 1. A header view with the "ME" title and the contacts count.
     * 2. A button that prompts the user to create a local profile
     */
    private void addEmptyUserProfileHeader(LayoutInflater inflater) {
        ListView list = getListView();
        // Add a header with the "ME" name. The view is embedded in a frame view since you cannot
        // change the visibility of a view in a ListView without having a parent view.
        mProfileHeader = inflater.inflate(R.layout.user_profile_header, null, false);
        mProfileTitle = (TextView) mProfileHeader.findViewById(R.id.profile_title);
        mProfileHeaderContainer = new FrameLayout(inflater.getContext());
        mProfileHeaderContainer.addView(mProfileHeader);
        list.addHeaderView(mProfileHeaderContainer, null, false);

        // Add a button with a message inviting the user to create a local profile
        mProfileMessage = (Button) mProfileHeader.findViewById(R.id.user_profile_button);
        mProfileMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ExtensionManager.getInstance().getRcsExtension()
                       .addRcsProfileEntryListener(null, true)) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
                intent.putExtra(ContactEditorFragment.INTENT_EXTRA_NEW_LOCAL_PROFILE, true);
                ImplicitIntentsUtil.startActivityInApp(getActivity(), intent);
            }
        });
    }

    /** M: Bug Fix For ALPS00115673. @{*/
    private ProgressBar mProgress;
    private View mLoadingContainer;
    private WaitCursorView mWaitCursorView;
    private TextView mLoadingContact;
    /** @} */
    /// M: for vcs
    private VcsController.ContactsLoadListener mContactsLoadListener = null;

    /**
     * M: Bug Fix CR ID: ALPS00279111.
     */
    public void closeWaitCursor() {
        // TODO Auto-generated method stub
        Log.d(TAG, "closeWaitCursor   DefaultContactBrowseListFragment");
        mWaitCursorView.stopWaitCursor();
    }

    /**
     * M: [vcs] for vcs.
     */
    public void setContactsLoadListener(VcsController.ContactsLoadListener listener) {
        mContactsLoadListener = listener;
    }

    /**
     * M: for ALPS01766595.
     */
    private int getContactCount() {
        int count = isSearchMode() ? 0 : getAdapter().getCount();
        if (mUserProfileExists) {
            count -= PROFILE_NUM;
        }
        return count;
    }

    public void setContactsCount() {
        /// M: [VCS] @{
        int count = getContactCount();
        if (mContactsLoadListener != null) {
            mContactsLoadListener.onContactsLoad(count);
        }
        /// @}
    }
	
    //add show contacts count start
    @Override
    protected void showCount(int partitionIndex, Cursor data) {
        /*
         * Bug Fix by Mediatek Begin.
         *   Original Android's code:
         *     xxx
         *   CR ID: ALPS00279111
         *   Descriptions: 
         */
        Log.i(TAG, "showCount is called");
        mWaitCursorView.stopWaitCursor();
        /*
         * Bug Fix by Mediatek End.
         */
//        setSectionHeaderDisplayEnabled(true);
        setVisibleScrollbarEnabled(true);
        if (!isSearchMode() && data != null) {
            //int count = data.getCount();
			int count = getContactCount();
            Log.i(TAG, "showCount count is : " + count);
            if (count != 0) {
                String format = getResources().getQuantityText(
                        R.plurals.listTotalAllContacts, count).toString();
                    mCounterHeaderView.setText(String.format(format, count));
					Log.i(TAG, "mUserProfileExists=" + mUserProfileExists + " " + String.format(format, count));
                ///M:[VCS]@{
                /*if (mListener != null) {
                    mListener.onShowContactsCount(Integer.valueOf(count));
                }*/
                //@}
            } else {
                ///M:[VCS]@{
				/*
                if (mListener != null) {
                    mListener.onShowContactsCount(0);
                }*/
                //@}
                ContactListFilter filter = getFilter();
                int filterType = filter != null ? filter.filterType
                        : ContactListFilter.FILTER_TYPE_ALL_ACCOUNTS;
                switch (filterType) {
                    case ContactListFilter.FILTER_TYPE_ACCOUNT:
                        String accountName;
                        if (AccountType.ACCOUNT_NAME_LOCAL_PHONE.equals(filter.accountName)) {
                            accountName = getString(R.string.account_phone_only);
                            /** M: ALPS913966 cache displayname in account filter and  push to intent @{ */
                            if (accountName == null && filter.displayName != null) {
                                accountName = filter.displayName;
                            }
                            /**@}*/
                        } else {
                            accountName = AccountTypeUtils.getDisplayAccountName(this.getContext(), filter.accountName);
                            /** M: ALPS913966 cache displayname in account filter and  push to intent @{ */
                            if (accountName == null && filter.displayName != null) {
                                accountName = filter.displayName;
                            }
                            /**@}*/
                        }
                        mCounterHeaderView.setText(getString(
                                R.string.listTotalAllContactsZeroGroup, accountName));
                        /*
                         * Bug Fix by Mediatek Begin.
                         *   Original Android's code:
                         *     xxx
                         *   CR ID: ALPS00382262
                         *   Descriptions: 
                         */
                        updateFilterHeaderView();
                        /*
                         * Bug Fix by Mediatek End.
                         */
                        break;
                    case ContactListFilter.FILTER_TYPE_WITH_PHONE_NUMBERS_ONLY:
                        mCounterHeaderView.setText(R.string.listTotalPhoneContactsZero);
                        break;
                    case ContactListFilter.FILTER_TYPE_STARRED:
                        mCounterHeaderView.setText(R.string.listTotalAllContactsZeroStarred);
                        break;
                    case ContactListFilter.FILTER_TYPE_CUSTOM:
                        mCounterHeaderView.setText(R.string.listTotalAllContactsZeroCustom);
                        break;
                    default:
                        mCounterHeaderView.setText(R.string.listTotalAllContactsZero);
                        break;
                }
//                setSectionHeaderDisplayEnabled(false);
                setVisibleScrollbarEnabled(false);
            }
        } else {
            ContactListAdapter adapter = getAdapter();
            if (adapter == null) {
                return;
            }

            // In search mode we only display the header if there is nothing found
            if (TextUtils.isEmpty(getQueryString()) || !adapter.areAllPartitionsEmpty()) {
                mSearchHeaderView.setVisibility(View.GONE);
                showSearchProgress(false);
            } else {
                mSearchHeaderView.setVisibility(View.VISIBLE);
                if (adapter.isLoading()) {
                    mSearchProgressText.setText(R.string.search_results_searching);
                    showSearchProgress(true);
                } else {
                    mSearchProgressText.setText(R.string.listFoundAllContactsZero);
                    mSearchProgressText.sendAccessibilityEvent(
                            AccessibilityEvent.TYPE_VIEW_SELECTED);
                    showSearchProgress(false);
                }
            }
            showEmptyUserProfile(false);
        }
    }
    //add show contacts count end
}
