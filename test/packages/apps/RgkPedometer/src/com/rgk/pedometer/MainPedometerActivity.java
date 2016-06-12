package com.rgk.pedometer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.rgk.pedometer.PedometerApplication.OnServiceConnectedListener;
import com.rgk.pedometer.widget.ViewPagerTabs;

public class MainPedometerActivity extends Activity implements View.OnClickListener {
	
	//private Button mSetingsBtn = null;
	public static final String TO_WHERE = "toWhere";
	public static final String TO_STEPS = "toSteps";
	public static final String TO_SUMMARYS = "toSummarys";
	public static final String TO_SETTINGS = "toSettings";
	
	/**
     * Showing a list of Contacts. Also used for showing search results in search mode.
     */
    private TodayStepsFragment mTodayStepsFragment;
    private HistoryStepslFragment mHistoryStepslFragment;
    private SettingsFragment mSettingsFragment;

    /** ViewPager for swipe */
    private ViewPager mTabPager;
    private ViewPagerTabs mViewPagerTabs;
    private TabPagerAdapter mTabPagerAdapter;
    private String[] mTabTitles;
    private int[] mTabIcons;

    private final TabPagerListener mTabPagerListener = new TabPagerListener();

    private boolean isAddListener = false;
    
    class StepCache {
    	int steps;
    	float calories;
    	float distance;
    	
    	StepCache(int st, float cal, float dis) {
    		steps = st;
    		calories = cal;
    		distance = dis;
    	}
    }
    
    private final OnServiceConnectedListener mOnServiceConnectedListener = new OnServiceConnectedListener() {

	@Override
	public void onConnected() {
		Log.d(PedometerApplication.TAG, "----------onConnected:");
		StepService service = PedometerApplication.getInstance().getStepService();
		service.registerCallback(mCallback);
        service.reloadSettings();
		isAddListener = true;
		
		/*
		if (mTodayStepsFragment != null) {
			mTodayStepsFragment.setCurrentSteps(service.getSteps());
	        mTodayStepsFragment.setCalories(service.getCalories());
	        mTodayStepsFragment.setDistance(service.getDistance());
		}
		if (mHistoryStepslFragment != null) {
			mHistoryStepslFragment.setCurrentSteps(service.getSteps());
	        mHistoryStepslFragment.setCaloriesAndDistance(service.getCalories(), service.getDistance());
		}
		*/
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
        public static int STEPS = 0;
        public static int SUMMARYS = 1;
        public static int SETTINGS = 2;

        public static int COUNT = 3;
        public static int DEFAULT = STEPS;
    }

    private int mCurrentTab = TabState.DEFAULT;
    
    private static final int STEPS_MSG = 1;
    private static final int PACE_MSG = 2;
    private static final int DISTANCE_MSG = 3;
    private static final int SPEED_MSG = 4;
    private static final int CALORIES_MSG = 5;
    
    private Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            switch (msg.what) {
                case STEPS_MSG:
                	StepCache obj = (StepCache)msg.obj;
                	if (obj == null) return;
                	
                    Log.d(PedometerApplication.TAG, "handleMessage[STEPS_MSG]----------steps:"+obj.steps);
                    mTodayStepsFragment.setCurrentSteps(obj.steps);
                    mTodayStepsFragment.setCalories(obj.calories);
                    mTodayStepsFragment.setDistance(obj.distance);
                    mHistoryStepslFragment.setCurrentSteps(obj.steps);
                    mHistoryStepslFragment.setCaloriesAndDistance(obj.calories, obj.distance);
                    
                    maybeShowTargetFinishUI();
                    break;
                case PACE_MSG:
                    /*mPaceValue = msg.arg1;
                    if (mPaceValue <= 0) { 
                        mPaceValueView.setText("0");
                    }
                    else {
                        mPaceValueView.setText("" + (int)mPaceValue);
                    }*/
                    break;
                case DISTANCE_MSG:
                    /*mDistanceValue = ((int)msg.arg1)/1000f;
                    if (mDistanceValue <= 0) { 
                        mDistanceValueView.setText("0");
                    }
                    else {
                        mDistanceValueView.setText(
                                ("" + (mDistanceValue + 0.000001f)).substring(0, 5)
                        );
                    }*/
                    break;
                case SPEED_MSG:
                    /*mSpeedValue = ((int)msg.arg1)/1000f;
                    if (mSpeedValue <= 0) { 
                        mSpeedValueView.setText("0");
                    }
                    else {
                        mSpeedValueView.setText(
                                ("" + (mSpeedValue + 0.000001f)).substring(0, 4)
                        );
                    }*/
                    break;
                case CALORIES_MSG:
                    /*mCaloriesValue = msg.arg1;
                    if (mCaloriesValue <= 0) { 
                        mCaloriesValueView.setText("0");
                    }
                    else {
                        mCaloriesValueView.setText("" + (int)mCaloriesValue);
                    }*/
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
        
    };
    
 // TODO: unite all into 1 type of message
    private StepService.ICallback mCallback = new StepService.ICallback() {
        public void stepsChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, value, 0));
        }
        public void stepsChanged(int steps, float calories, float distance) {
        	StepCache obj = new StepCache(steps, calories, distance);
            mHandler.sendMessage(mHandler.obtainMessage(STEPS_MSG, obj));
        }
        public void paceChanged(int value) {
            mHandler.sendMessage(mHandler.obtainMessage(PACE_MSG, value, 0));
        }
        public void distanceChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(DISTANCE_MSG, (int)(value*1000), 0));
        }
        public void speedChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(SPEED_MSG, (int)(value*1000), 0));
        }
        public void caloriesChanged(float value) {
            mHandler.sendMessage(mHandler.obtainMessage(CALORIES_MSG, (int)(value), 0));
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(PedometerApplication.TAG, "MainNetActivity: [onCreate]");
		
		mIsRecreatedInstance = (savedInstanceState != null);
		
		createViewsAndFragments(savedInstanceState);

		PedometerApplication.getInstance().addListener(mOnServiceConnectedListener);
		
		//initViews();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		maybeShowTargetFinishUI();
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
            if (!isAddListener && PedometerApplication.getInstance().isServiceBinded()) {
            	StepService service = PedometerApplication.getInstance().getStepService();
    			service.registerCallback(mCallback);
                isAddListener = true;
            }
	}

	@Override
	protected void onStop() {
		super.onStop();
        if (isAddListener && PedometerApplication.getInstance().isServiceBinded()) {
            StepService service = PedometerApplication.getInstance().getStepService();
            //PedometerApplication.getInstance().getDataUsageService().removeSimListener(mSimStateListener);
			isAddListener = false;
			service.registerCallback(null);
        }
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		if (intent == null) {
			return;
		}
		
		mRequest = TabState.DEFAULT;
		
		if (TO_SUMMARYS.equals(intent.getStringExtra(TO_WHERE))) {
			mRequest = TabState.SUMMARYS;
		} else if (TO_SETTINGS.equals(intent.getStringExtra(TO_WHERE))) {
			mRequest = TabState.SETTINGS;
		} else {
			mRequest = TabState.STEPS;
		}
		
		configureFragments(true);
	}

    private void createViewsAndFragments(Bundle savedState) {
	setContentView(R.layout.activity_main_pedometer);
		
	mTabTitles = new String[TabState.COUNT];
        mTabTitles[TabState.STEPS] = getString(R.string.steps_tab_label);
        mTabTitles[TabState.SUMMARYS] = getString(R.string.history_tab_label);
        mTabTitles[TabState.SETTINGS] = getString(R.string.settings_tab_label);
        mTabIcons = new int[TabState.COUNT];
        mTabIcons[TabState.STEPS] = R.drawable.ic_tab_steps;
        mTabIcons[TabState.SUMMARYS] = R.drawable.ic_tab_summarys;
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
        
        final String STEPS_TAG = "tab-pager-steps";
        final String SUMMARYS_TAG = "tab-pager-summarys";
        final String SETTINGS_TAG = "tab-pager-settings";

        // Create the fragments and add as children of the view pager.
        // The pager adapter will only change the visibility; it'll never create/destroy
        // fragments.
        // However, if it's after screen rotation, the fragments have been re-created by
        // the fragment manager, so first see if there're already the target fragments
        // existing.
        mTodayStepsFragment = (TodayStepsFragment)
                fragmentManager.findFragmentByTag(STEPS_TAG);
        mHistoryStepslFragment = (HistoryStepslFragment)
                fragmentManager.findFragmentByTag(SUMMARYS_TAG);
        mSettingsFragment = (SettingsFragment)
                fragmentManager.findFragmentByTag(SETTINGS_TAG);

        if (mTodayStepsFragment == null) {
        	mTodayStepsFragment = new TodayStepsFragment();
        	mHistoryStepslFragment = new HistoryStepslFragment();
        	mSettingsFragment = new SettingsFragment();

            transaction.add(R.id.tab_pager, mTodayStepsFragment, STEPS_TAG);
            transaction.add(R.id.tab_pager, mHistoryStepslFragment, SUMMARYS_TAG);
            transaction.add(R.id.tab_pager, mSettingsFragment, SETTINGS_TAG);
        }
        
        // Hide all fragments for now.  We adjust visibility when we get onSelectedTabChanged()
        // from ActionBarAdapter.
        transaction.hide(mTodayStepsFragment);
        transaction.hide(mHistoryStepslFragment);
        transaction.hide(mSettingsFragment);

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        
        mTabPager.setCurrentItem(mCurrentTab);
	}
	
	private void configureFragments(boolean fromRequest) {
        if (fromRequest) {
        	int tabToOpen = -1;
            switch (mRequest) {
                case TabState.STEPS:
                	tabToOpen = TabState.STEPS;
                    break;
                case TabState.SUMMARYS:
                	tabToOpen = TabState.SUMMARYS;
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
            if (object == mTodayStepsFragment) {
                return 0; // Only 1 page in search mode
            } else if (object == mHistoryStepslFragment) {
                return 1;
            }else if (object == mTodayStepsFragment) {
                return 2;
            }
            return POSITION_NONE;
        }

        @Override
        public void startUpdate(ViewGroup container) {
        }

        private Fragment getFragment(int position) {
            if (position == TabState.STEPS) {
                return mTodayStepsFragment;
            } else if (position == TabState.SUMMARYS) {
                return mHistoryStepslFragment;
            } else if (position == TabState.SETTINGS) {
            	return mSettingsFragment;
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
    
    public void onPersonalInfoChanged(int type, int value) {
    	if (type == SettingsFragment.TYPE_AGE) {
    		
		} else if (type == SettingsFragment.TYPE_BODY_HIGH) {
			if (PedometerApplication.getInstance().isServiceBinded()) {
				StepService service = PedometerApplication.getInstance().getStepService();
				service.reloadSettings();
			}
		} else if (type == SettingsFragment.TYPE_BODY_WEIGHT) {
			if (PedometerApplication.getInstance().isServiceBinded()) {
				StepService service = PedometerApplication.getInstance().getStepService();
				service.reloadSettings();
			}
		} else if (type == SettingsFragment.TYPE_TARGET_STEPS) {
			mTodayStepsFragment.setTargetSteps(value);
			mTodayStepsFragment.refleshPercentView();
		}
    }
    
    private void maybeShowTargetFinishUI() {
    	if (SharePreferenceUtils.getHasNotify(this)) {
    		StepNotifier.cancelNotify(this);
    		Intent intent = new Intent(this, NotifyDialog.class);
        	startActivity(intent);
    	}
    }

}
