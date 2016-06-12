package com.rgk.phonemanager;

import com.rgk.phonemanager.util.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TrashQuickClearSuccess extends BaseActivity implements
		OnClickListener {	
	private Button mTrashClearFinish;
	private ImageView mImageViewStar;
	private RotateAnimation mAnimation;
	private TranslateAnimation mTranslateAnimation;
	private FrameLayout mHideFrameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.trash_quick_clear_success);

		initView();
		initAnimation();
	}

	private void initView() {
		mImageViewStar = (ImageView) findViewById(R.id.iv_star);
		mTrashClearFinish = (Button) findViewById(R.id.trash_clear_finish);
		mTrashClearFinish.setOnClickListener(this);
		mHideFrameLayout = (FrameLayout) findViewById(R.id.hide_layout);
	}

	OnClickListener mImgOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	protected void initAnimation() {
		mAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setDuration(1500);
		mAnimation.setInterpolator(new LinearInterpolator());
		mAnimation.setRepeatCount(Animation.INFINITE);
		mImageViewStar.setAnimation(mAnimation);
		mTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF,
				0, Animation.RELATIVE_TO_SELF, 0);
		mTranslateAnimation.setDuration(1500);
		mTranslateAnimation.setInterpolator(new AccelerateInterpolator());
		mTranslateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				mHideFrameLayout.setVisibility(View.GONE);
				mAnimation.start();
			}
		});

		mHideFrameLayout.startAnimation(mTranslateAnimation);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View arg0) {
		finish();
	}
}
