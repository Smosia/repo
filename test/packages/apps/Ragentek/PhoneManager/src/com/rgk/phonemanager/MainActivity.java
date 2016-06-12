package com.rgk.phonemanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.rgk.phonemanager.util.BaseActivity;
import com.rgk.phonemanager.util.ScoreUtil;
import com.rgk.phonemanager.util.UIHandle;
import com.rgk.phonemanager.view.util.FuncBtnItem;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private final static String TAG = "MainActivity";
	
	private long mExitTime = 0L;
	
	private final static int FUNC_ITEM_APP_CLEAR = 1;
	private final static int FUNC_ITEM_PERMISSIONS = 2;
	private final static int FUNC_ITEM_NET = 3;
	private final static int FUNC_ITEM_AUTO_START = 4;
	
	private ImageView mRadarDot;
	private ImageView mLoadingImg;
	private ImageView mRadarBG1;
	private ImageView mRadarBG2;
	private ImageView mHalo;
	private TextView mScanRes;

	private AnimationDrawable mRadarAnimation;
	private ScaleAnimation mExpandAnimation;
	private ScaleAnimation MShrinkAnimation;
	private AlphaAnimation mHideRadarAnimation;

	private AnimatorSet mAnimatorSet;
	private ArrayList<Animator> mAnimatorList;
	
	private GridView funcBtns;
	
	private List<FuncBtnItem> mFuncBtnItems = new ArrayList<>();
	
	
	private TimerTask getPhoneAppTask;
	
	private final static int MSG_GET_SCORE_END = 1;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_GET_SCORE_END:
				int allRate = msg.arg1;
				showScore(allRate);
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mFuncBtnItems.add(new FuncBtnItem(FUNC_ITEM_APP_CLEAR, R.string.app_clear, R.drawable.main_app_clear));
		mFuncBtnItems.add(new FuncBtnItem(FUNC_ITEM_PERMISSIONS, R.string.app_permissions, R.drawable.main_permission));
		mFuncBtnItems.add(new FuncBtnItem(FUNC_ITEM_NET, R.string.net_management, R.drawable.main_net));
		mFuncBtnItems.add(new FuncBtnItem(FUNC_ITEM_AUTO_START, R.string.auto_start_management, R.drawable.main_auto_start));
		
		funcBtns = (GridView) findViewById(R.id.function_btns);
		funcBtns.setAdapter(new FuncBtnAdapter(MainActivity.this, mFuncBtnItems));
		funcBtns.setOnItemClickListener(funcBtnClickListener);
		
		mRadarDot = (ImageView) findViewById(R.id.radar_dot);
		mLoadingImg = (ImageView) findViewById(R.id.loading_img);
		mRadarBG1 = (ImageView) findViewById(R.id.rader_bg1);
		mRadarBG2 = (ImageView) findViewById(R.id.rader_bg2);
		mScanRes = (TextView) findViewById(R.id.scan_res);
		mHalo = (ImageView) findViewById(R.id.halo);		
		
		initRadar();
		
		starTest();
	}
	
	private void initRadar() {
		if (mRadarAnimation == null) {
			mRadarAnimation = (AnimationDrawable) mRadarDot.getBackground();
		}		
		
		Animation loadingAnim = AnimationUtils.loadAnimation(this,
				R.anim.anim_loading);
		loadingAnim.setInterpolator(new LinearInterpolator());

		if (MShrinkAnimation == null) {
			MShrinkAnimation = new ScaleAnimation(1f, 0f, 1f, 0f,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
					0.5f);
		}	
		MShrinkAnimation.setDuration(800);
		MShrinkAnimation.setInterpolator(new AccelerateInterpolator());
		MShrinkAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				mRadarBG2.setVisibility(View.GONE);
				mScanRes.setVisibility(View.VISIBLE);
				mScanRes.startAnimation(mExpandAnimation);
			}
		});

		if (mExpandAnimation == null) {
			mExpandAnimation = new ScaleAnimation(0.3f, 1f, 0.3f, 1f,
					Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
					0.5f);
		}
		
		mExpandAnimation.setDuration(400);
		mExpandAnimation.setInterpolator(new AccelerateInterpolator());
		mExpandAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {

			}
		});

		if (mHideRadarAnimation == null) {
			mHideRadarAnimation = new AlphaAnimation(1, 0);
		}	
		mHideRadarAnimation.setDuration(300);
		mHideRadarAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mRadarBG1.setVisibility(View.GONE);
			}
		});

		mAnimatorSet = new AnimatorSet();
		mAnimatorSet.setDuration(1500);
		mAnimatorSet.setInterpolator(new DecelerateInterpolator());
		mAnimatorList = new ArrayList<Animator>();
		ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mHalo, "ScaleX",
				1f, 1.15f);
		scaleXAnimator.setRepeatCount(Animation.INFINITE);
		mAnimatorList.add(scaleXAnimator);
		ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mHalo, "ScaleY",
				1f, 1.15f);
		scaleYAnimator.setRepeatCount(Animation.INFINITE);
		mAnimatorList.add(scaleYAnimator);
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mHalo, "Alpha",
				1.0f, 0f);
		alphaAnimator.setRepeatCount(Animation.INFINITE);
		mAnimatorList.add(alphaAnimator);
		mAnimatorSet.playTogether(mAnimatorList);

		mLoadingImg.startAnimation(loadingAnim);
		mAnimatorSet.start();
		mRadarAnimation.start();
	}
	
	private void showScore(int allRate) {
		Typeface mTypeface = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		mScanRes.setText(allRate + "");
		mScanRes.setTypeface(mTypeface);
		
		if (mLoadingImg != null) {
			mLoadingImg.clearAnimation();
			mLoadingImg.setVisibility(View.GONE);
		}
		
		if (mRadarAnimation != null) {
			mRadarAnimation.stop();
		}
		
		if (mRadarDot != null) {
			mRadarDot.setVisibility(View.GONE);
		}
		
		mRadarBG1.setAnimation(mHideRadarAnimation);
		mRadarBG2.setAnimation(MShrinkAnimation);
	}
	
	private void starTest() {
		Timer mQueryPhoneAppTimer = new Timer();
		getPhoneAppTask = new QueryAppTask();
		mQueryPhoneAppTimer.schedule(getPhoneAppTask, 0);
	}
	
	class QueryAppTask extends TimerTask {

		public void run() {
			try {
				int cpuRate = ScoreUtil.getProcessCpuRate();				
				
				int romRate = ScoreUtil.getROMRate();				
				
				int ramRate = ScoreUtil.getRAMRate(MainActivity.this);				
				
				int all = cpuRate + romRate + ramRate;
				Message msg = mHandler.obtainMessage(MSG_GET_SCORE_END);
				msg.arg1 = all;
				// mHandler.sendMessage(msg);
				mHandler.sendMessageDelayed(msg, 4000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	AdapterView.OnItemClickListener funcBtnClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d(TAG, "func Btn Click, position="+position + ", id="+id);
			switch ((int)id) {
			case FUNC_ITEM_APP_CLEAR:
				startActivity(new Intent(MainActivity.this, ScannerAndCleaner.class));
				break;
			case FUNC_ITEM_PERMISSIONS:
                startActivity(new Intent(Intent.ACTION_MANAGE_PERMISSIONS));
				break;
			case FUNC_ITEM_NET:
                ComponentName name = new ComponentName("com.rgk.netmanager", "com.rgk.netmanager.MainNetActivity");
				Intent i = new Intent();
				i.setComponent(name);
				startActivity(i);
				break;
			case FUNC_ITEM_AUTO_START:
                startActivity(new Intent("com.mediatek.security.AUTO_BOOT"));
				break;
			default:
				break;
			}
		}
	};
	
	public class FuncBtnAdapter extends BaseAdapter {

		private Context mContext;
		private List<FuncBtnItem> mList;
		private LayoutInflater mInflater;
		
		public FuncBtnAdapter(Context context, List<FuncBtnItem> mList) {
			super();
			this.mList = mList;
			mContext = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder mHolder; 
			
			if(convertView == null || convertView.getTag() == null) {
				//Time consuming 1 -- inflate
				convertView = mInflater.inflate(R.layout.func_grid_item, null);
				mHolder = new ViewHolder();
				//Time consuming 2 -- findViewById
				mHolder.image = (ImageView) convertView.findViewById(R.id.iv_share_icon);
				mHolder.name = (TextView) convertView.findViewById(R.id.tv_share_name);
				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}
			FuncBtnItem bean = mList.get(position);
			mHolder.image.setImageResource(bean.getImgRes());
			mHolder.name.setText(mContext.getResources().getString(bean.getStringRes()));
			return convertView;
		}
		
		//Google I/O
		class ViewHolder {
			public ImageView image;
			public TextView name;
		}
	}

	public void onBackPressed() {
		long pressTime = System.currentTimeMillis();
		if ((pressTime - mExitTime) > 3000) {
			Toast.makeText(MainActivity.this, getString(R.string.exit_one_more_time), Toast.LENGTH_SHORT).show();
			mExitTime = pressTime;
		} else {
			UIHandle.getInstance().exit();
		}
	}

	public void finish() {
		super.finish();
		overridePendingTransition(0, R.anim.home_page_exit);
	}	
}
