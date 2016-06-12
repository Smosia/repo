package com.rgk.netmanager;

import com.rgk.netmanager.R;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rgk.netmanager.NetApplication.OnServiceConnectedListener;
import com.rgk.netmanager.DataUsageService.StatsUpdateListener;
import com.rgk.netmanager.DataUsageService.SimStateListener;

public class MainNetActivity extends Activity implements View.OnClickListener {
	
	//private Button mSetingsBtn = null;
	public static final String TO_WHERE = "toWhere";
	public static final String TO_STATS = "toStats";
	public static final String TO_FIREWALL = "toFirewall";
	public static final String TO_SETTINGS = "toSettings";
	
	/**
     * Showing a list of Contacts. Also used for showing search results in search mode.
     */
    private NetworkStatsFragment mNetworkStatsFragment;
    private FirewallFragment mFirewallFragment;
    private NetworkSettingsFragment mNetworkSettingsFragment;

    /** ViewPager for swipe */
    private ViewPager mTabPager;
    private ViewPagerTabs mViewPagerTabs;
    private TabPagerAdapter mTabPagerAdapter;
    private String[] mTabTitles;
    private int[] mTabIcons;

    private final TabPagerListener mTabPagerListener = new TabPagerListener();

	private final StatsUpdateListener mStatsUpdateListener = new StatsUpdateListener() {

		@Override
		public void onUpdate() {
			if (mNetworkStatsFragment != null && mNetworkStatsFragment.isAdded()) {
				mNetworkStatsFragment.reLoad();
			}
			if (mFirewallFragment != null && mFirewallFragment.isAdded()) {
				mFirewallFragment.reLoad();
			}
		}};

	private SimStateListener mSimStateListener = new SimStateListener() {

		@Override
		public void onSimStateChanged(String state, int slotId) {
			if (mNetworkStatsFragment != null && mNetworkStatsFragment.isAdded()) {
				mNetworkStatsFragment.reLoad();
			}
			if (mFirewallFragment != null && mFirewallFragment.isAdded()) {
				mFirewallFragment.reLoad();
			}
			if (mNetworkSettingsFragment != null) {
				mNetworkSettingsFragment.onSimChanged();
			}
		}};

    private boolean isAddListener = false;
    private final OnServiceConnectedListener mOnServiceConnectedListener = new OnServiceConnectedListener() {

	@Override
	public void onConnected() {
		NetApplication.getInstance().getDataUsageService().addListener(mStatsUpdateListener);
		NetApplication.getInstance().getDataUsageService().addSimListener(mSimStateListener);
		isAddListener = true;
    }};
    
    /**
     * To determine which fragment will be show
     */
    private int mRequest;
    
    /**
     * If {@link #configureFragments(boolean)} is already called.  Used to avoid calling it twice
     * in {@link #onStart}.
     * (This initialization only needs to be done once in onStart() when the Activity was just
     * created from scratch -- i.e. onCreate() was just called)
     */
    private boolean mFragmentInitialized;
    
    /**
     * True if this activity instance is a re-created one.  i.e. set true after orientation change.
     * This is set in {@link #onCreate} for later use in {@link #onStart}.
     */
    private boolean mIsRecreatedInstance;
	
    public interface TabState {
        public static int STATS = 0;
        public static int FIREWALL = 1;
        public static int SETTINGS = 2;

        public static int COUNT = 3;
        public static int DEFAULT = STATS;
    }

    private int mCurrentTab = TabState.DEFAULT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(NetApplication.TAG, "MainNetActivity: [onCreate]");
		
		mIsRecreatedInstance = (savedInstanceState != null);
		
		createViewsAndFragments(savedInstanceState);

                NetApplication.getInstance().addListener(mOnServiceConnectedListener);
		
		//initViews();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
	    super.onStart();
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
            if (!isAddListener && NetApplication.getInstance().isServiceBinded()) {
                NetApplication.getInstance().getDataUsageService().addListener(mStatsUpdateListener);
                NetApplication.getInstance().getDataUsageService().addSimListener(mSimStateListener);
                isAddListener = true;
            }
	}

	@Override
	protected void onStop() {
		super.onStop();
                if (isAddListener && NetApplication.getInstance().isServiceBinded()) {
			NetApplication.getInstance().getDataUsageService().removeListener(mStatsUpdateListener);
			NetApplication.getInstance().getDataUsageService().removeSimListener(mSimStateListener);
			isAddListener = false;
                }
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		if (intent == null) {
			return;
		}
		
		mRequest = TabState.DEFAULT;
		
		if (TO_FIREWALL.equals(intent.getStringExtra(TO_WHERE))) {
			mRequest = TabState.FIREWALL;
		} else if (TO_SETTINGS.equals(intent.getStringExtra(TO_WHERE))) {
			mRequest = TabState.SETTINGS;
		} else {
			mRequest = TabState.STATS;
		}
		
		configureFragments(true);
	}

    private void createViewsAndFragments(Bundle savedState) {
	setContentView(R.layout.activity_main_net);
		
	mTabTitles = new String[TabState.COUNT];
        mTabTitles[TabState.STATS] = getString(R.string.stats_tab_label);
        mTabTitles[TabState.FIREWALL] = getString(R.string.firewall_tab_label);
        mTabTitles[TabState.SETTINGS] = getString(R.string.settings_tab_label);
        mTabIcons = new int[TabState.COUNT];
        mTabIcons[TabState.STATS] = R.drawable.ic_tab_stats;
        mTabIcons[TabState.FIREWALL] = R.drawable.ic_tab_firewall;
        mTabIcons[TabState.SETTINGS] = R.drawable.ic_tab_settings;

        mTabPager = (ViewPager)findViewById(R.id.tab_pager);
        mTabPagerAdapter = new TabPagerAdapter();
        mTabPager.setAdapter(mTabPagerAdapter);
        mTabPager.setOnPageChangeListener(mTabPagerListener);
        
        mViewPagerTabs = (ViewPagerTabs) findViewById(R.id.tab_pager_header);
        mViewPagerTabs.setViewPager(mTabPager);
        
        final FragmentManager fragmentManager = getFragmentManager();

        // Hide all tabs (the current tab will later be reshown once a tab is selected)
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        final String STATS_TAG = "tab-pager-stats";
        final String FIREWALL_TAG = "tab-pager-firewall";
        final String SETTINGS_TAG = "tab-pager-settings";

        // Create the fragments and add as children of the view pager.
        // The pager adapter will only change the visibility; it'll never create/destroy
        // fragments.
        // However, if it's after screen rotation, the fragments have been re-created by
        // the fragment manager, so first see if there're already the target fragments
        // existing.
        mNetworkStatsFragment = (NetworkStatsFragment)
                fragmentManager.findFragmentByTag(STATS_TAG);
        mFirewallFragment = (FirewallFragment)
                fragmentManager.findFragmentByTag(FIREWALL_TAG);
        mNetworkSettingsFragment = (NetworkSettingsFragment)
                fragmentManager.findFragmentByTag(SETTINGS_TAG);

        if (mNetworkStatsFragment == null) {
        	mNetworkStatsFragment = new NetworkStatsFragment();
        	mFirewallFragment = new FirewallFragment();
        	mNetworkSettingsFragment = new NetworkSettingsFragment();

            transaction.add(R.id.tab_pager, mNetworkStatsFragment, STATS_TAG);
            transaction.add(R.id.tab_pager, mFirewallFragment, FIREWALL_TAG);
            transaction.add(R.id.tab_pager, mNetworkSettingsFragment, SETTINGS_TAG);
        }
        
        // Hide all fragments for now.  We adjust visibility when we get onSelectedTabChanged()
        // from ActionBarAdapter.
        transaction.hide(mNetworkStatsFragment);
        transaction.hide(mFirewallFragment);
        transaction.hide(mNetworkSettingsFragment);

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        
        mTabPager.setCurrentItem(mCurrentTab);
	}
	
	private void configureFragments(boolean fromRequest) {
        if (fromRequest) {
        	int tabToOpen = -1;
            switch (mRequest) {
                case TabState.STATS:
                	tabToOpen = TabState.STATS;
                    break;
                case TabState.FIREWALL:
                	tabToOpen = TabState.FIREWALL;
                    break;

                case TabState.SETTINGS:
                	tabToOpen = TabState.SETTINGS;
                    break;
                default:
                    tabToOpen = -1;
                    break;
            }
            if (tabToOpen > -1) {
                mCurrentTab = tabToOpen;
                mTabPager.setCurrentItem(tabToOpen);
                //configureContactListFragmentForRequest();
	    }
        }

        configureStatsFragment();

        //invalidateOptionsMenuIfNeeded();
    }
	
	private void configureStatsFragment() {
        // Filter may be changed when this Activity is in background.
       /* mAllFragment.setFilter(mContactListFilterController.getFilter());

        mAllFragment.setVerticalScrollbarPosition(getScrollBarPosition());
        mAllFragment.setSelectionVisible(false);*/
    }

	private void initViews() {
		/*mSetingsBtn = (Button)this.findViewById(R.id.data_usage_settings_btn);
		mSetingsBtn.setOnClickListener(this);*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main_net, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		/*case R.id.data_usage_settings_btn:
			Intent intent = new Intent(this, NetSettingsActivity.class);
			startActivity(intent);
			break;*/
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
            //if (!mTabPagerAdapter.isSearchMode()) {
                mViewPagerTabs.onPageScrollStateChanged(state);
            //}
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //if (!mTabPagerAdapter.isSearchMode()) {
                mViewPagerTabs.onPageScrolled(position, positionOffset, positionOffsetPixels);
            //}
        }

        @Override
        public void onPageSelected(int position) {
            // Make sure not in the search mode, in which case position != TabState.ordinal().
            //if (!mTabPagerAdapter.isSearchMode()) {
                mViewPagerTabs.onPageSelected(position);
                invalidateOptionsMenu();
            //}
        }
    }
	
    public class TabPagerAdapter extends PagerAdapter {
        private final FragmentManager mFragmentManager;
        private FragmentTransaction mCurTransaction = null;

        //private boolean mTabPagerAdapterSearchMode;

        private Fragment mCurrentPrimaryItem;

        public TabPagerAdapter() {
            mFragmentManager = getFragmentManager();
        }

        /*public boolean isSearchMode() {
            return mTabPagerAdapterSearchMode;
        }*/

        @Override
        public int getCount() {
            return TabState.COUNT;
        }

        /** Gets called when the number of items changes. */
        @Override
        public int getItemPosition(Object object) {
            if (object == mNetworkStatsFragment) {
                return 0; // Only 1 page in search mode
            } else if (object == mFirewallFragment) {
                return 1;
            }else if (object == mNetworkStatsFragment) {
                return 2;
            }
            return POSITION_NONE;
        }

        @Override
        public void startUpdate(ViewGroup container) {
        }

        private Fragment getFragment(int position) {
            if (position == TabState.STATS) {
                return mNetworkStatsFragment;
            } else if (position == TabState.FIREWALL) {
                return mFirewallFragment;
            } else if (position == TabState.SETTINGS) {
            	return mNetworkSettingsFragment;
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

        @SuppressWarnings("unused")
	public int getPageIconResId(int position) {
            return mTabIcons[position];
        }
    }

}
