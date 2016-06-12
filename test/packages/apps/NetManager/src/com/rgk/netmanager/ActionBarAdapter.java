package com.rgk.netmanager;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ActionBarAdapter {
	public static final String SCREEN_MAIN = "main";
	public static final String SCREEN_ALL_DAYS = "all_days";
	public static final String SCREEN_ONE_DAY = "one_day";

	private ActionBar mActionBar;
	private String mWhichScreen;
	private Context mContext;

	private OnClickListener mOnClickListener;

	public ActionBarAdapter(ActionBar actionBar, String whichScreen,
			Context context, OnClickListener onClickListener) {
		mActionBar = actionBar;
		mWhichScreen = whichScreen;
		mContext = context;
		mOnClickListener = onClickListener;
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customActionBarView = inflater.inflate(R.layout.custom_action_bar,
				null);

		ImageButton backBtn = (ImageButton) customActionBarView
				.findViewById(R.id.custom_action_bar_back_btn);
		if (SCREEN_MAIN.equals(mWhichScreen)) {
			backBtn.setVisibility(View.GONE);
		} else {
			backBtn.setVisibility(View.VISIBLE);
		}

		if (mOnClickListener != null) {
			backBtn.setOnClickListener(mOnClickListener);
		}

		// Show the custom action bar but hide the home icon and title
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_SHOW_TITLE);
		mActionBar.setCustomView(customActionBarView);
	}

}
