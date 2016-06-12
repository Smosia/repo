package com.android.server.policy;

import java.util.ArrayList;
import java.util.List;
import com.android.internal.R;

import android.content.DialogInterface;

import android.animation.AnimatorSet;
import android.animation.AnimatorSet.Builder;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.AudioManager;
import android.view.WindowManagerPolicy.WindowManagerFuncs;
import android.util.ArraySet;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.app.WallpaperManager;
import android.graphics.Rect;
import android.renderscript.Element;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.view.SurfaceControl;
import android.graphics.Canvas;
import android.view.Surface;
import android.graphics.Paint;

import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.graphics.drawable.BitmapDrawable;

public class GlobalActionsDialog extends Dialog implements View.OnClickListener{
	private static final String TAG = "GlobalActionsDialog";
	private List<PressAction> actions;
	private float width;
	private float height;
	private RelativeLayout mLayout;
	private WindowManagerFuncs mWindowManagerFuncs;
	private Context mContext;
	private AudioManager mAudioManager;
	private Bitmap mBmp;

    private static final int SCALE_RATIO = 8;
    private static final int SCALE_DELTA = 1;
    private static final int BLUR_RADIUS = 3;

	private static final int MESSAGE_DISMISS = 0;
	private static final int MESSAGE_START_RESUME_ANIMATOR = 1;
	private static final int MESSAGE_SILENT_MODE_CHANGED = 2;
	private static final int MESSAGE_TO_CENTER = 3;
	private static final int MESSAGE_ANIMATION_STOP = 4;
	
	private static final String CONFIG_CHANGED = "config_changed";
	
	private AirplaneMode airplaneMode;
	private SilentMode silentMode;
	private PowerOff powerOff;
	private Reboot reboot;

	private boolean isInCenter;
	private int itemInCenter;
	
    /* Valid settings for global actions keys.
     * see config.xml config_globalActionList */
    public static final String GLOBAL_ACTION_KEY_POWER = "power";
    public static final String GLOBAL_ACTION_KEY_AIRPLANE = "airplane";
    public static final String GLOBAL_ACTION_KEY_BUGREPORT = "bugreport";
    public static final String GLOBAL_ACTION_KEY_SILENT = "silent";
    public static final String GLOBAL_ACTION_KEY_USERS = "users";
    public static final String GLOBAL_ACTION_KEY_SETTINGS = "settings";
    public static final String GLOBAL_ACTION_KEY_LOCKDOWN = "lockdown";
    public static final String GLOBAL_ACTION_KEY_VOICEASSIST = "voiceassist";
    public static final String GLOBAL_ACTION_KEY_ASSIST = "assist";
    public static final String GLOBAL_ACTION_KEY_REBOOT = "reboot";
    public static final String GLOBAL_ACTION_KEY_DATA = "data_connection";

	private static final int TIME_DISPLAY_ANIMATION = 300;


	public GlobalActionsDialog(Context context, WindowManagerFuncs windowManagerFuncs) {
		//super(context, R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		super(context, R.style.Theme_NoTitleBar_Fullscreen);
        Log.d(TAG, "construction start");
		mWindowManagerFuncs = windowManagerFuncs;
        actions = new ArrayList<PressAction>();
		mContext = context;
		isInCenter = false;
		
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		mBmp = getScreenshot(mContext, BLUR_RADIUS);
		
		getWindow().getAttributes().setTitle("GlobalActions");
		
		View decorView = getWindow().getDecorView();
		
		Drawable backgroundDrawable = null;
        if(mBmp != null){
            backgroundDrawable = new BitmapDrawable(mBmp);
		}else{
            Log.i(TAG, "mBackBitmap is null.");
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            backgroundDrawable = wallpaperManager.getDrawable();
		}
        if (backgroundDrawable != null) {
            getWindow().getDecorView().setBackground(new LayerDrawable(new Drawable[]{backgroundDrawable,
                context.getResources().getDrawable(R.drawable.power_menu_background)}));
            //getWindow().getDecorView().setBackground(backgroundDrawable);
        }
		
        if (!mContext.getResources().getBoolean(
                com.android.internal.R.bool.config_sf_slowBlur)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }
		
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getRealMetrics(dm);

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.format = PixelFormat.TRANSLUCENT;
		lp.gravity = Gravity.LEFT | Gravity.TOP;
		lp.height = dm.heightPixels;

		Display display = wm.getDefaultDisplay();
		width = (float) (display.getWidth());
		height = (float) (display.getHeight());
		
		Log.d(TAG, "width: " + width);
		Log.d(TAG, "height: " + height);

		mAudioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
				
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(mBroadcastReceiver, filter);
        Log.d(TAG, "construction end");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate start");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.power_menu_dialog);
		mLayout = (RelativeLayout) findViewById(R.id.power_menu_dialog_container);

		mLayout.setOnClickListener(this);
		mLayout.setClickable(false);
					
		addItems();
		initialPosition();
		addView();

        Log.d(TAG, "onCreate end");
	}
	
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)
                    || Intent.ACTION_SCREEN_OFF.equals(action)) {
                String reason = intent.getStringExtra(PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY);
                if (!PhoneWindowManager.SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS.equals(reason)) {
                    mHandler.sendEmptyMessage(MESSAGE_DISMISS);
                }
            }
        }
    };
	
	private void initialPosition(){
		switch (actions.size()) {
		case 1:
		
			break;
		case 2:
			actions.get(0).edgePointX = -width / 2f;
			actions.get(0).edgePointY = 0;
			actions.get(0).internalPointX = -width / 5f;
			actions.get(0).internalPointY = 0;
			
			actions.get(1).edgePointX = width / 2f;
			actions.get(1).edgePointY = 0;
			actions.get(1).internalPointX = width / 5f;
			actions.get(1).internalPointY = 0;
			
			break;
		case 3:
			actions.get(0).edgePointX = 0;
			actions.get(0).edgePointY = -height / 2f;
			actions.get(0).internalPointX = 0;
			actions.get(0).internalPointY = -height / 5f;
			
			actions.get(1).edgePointX = -width / 2f;
			actions.get(1).edgePointY = 3f * height / 8f;
			actions.get(1).internalPointX = -width / 5f;
			actions.get(1).internalPointY = height / 5f;
			
			actions.get(2).edgePointX = width / 2f;
			actions.get(2).edgePointY = 3f * height / 8f;
			actions.get(2).internalPointX = width / 5f;
			actions.get(2).internalPointY = height / 5f;
			break;
		case 4:				
			actions.get(0).edgePointX = -width / 2f;
			actions.get(0).edgePointY = -height / 4f;
			actions.get(0).internalPointX = -width / 5f;
			actions.get(0).internalPointY = -height / 7f;
			
			actions.get(1).edgePointX = width / 2f;
			actions.get(1).edgePointY = -height / 4f;
			actions.get(1).internalPointX = width / 5f;
			actions.get(1).internalPointY = -height / 7f;
			
			actions.get(2).edgePointX = -width / 2f;
			actions.get(2).edgePointY = height / 4f;
			actions.get(2).internalPointX = -width / 5f;
			actions.get(2).internalPointY = height / 7f;
			
			actions.get(3).edgePointX = width / 2f;
			actions.get(3).edgePointY = height / 4f;
			actions.get(3).internalPointX = width / 5f;
			actions.get(3).internalPointY = height / 7f;
			break;
		case 5:
			
			break;

		default:
			break;
		}
	}

	private void addView() {
		for (PressAction action : actions) {
			mLayout.addView(action.getView());
			action.setTranslation(action.edgePointX, action.edgePointY);
		}
	}

	private AnimatorSet getShowAnimation() {
		AnimatorSet animatorSet = new AnimatorSet();
		Builder builder;
		builder = animatorSet.play(actions.get(0).getShowAnimation());
		for(int i = 1; i < actions.size(); i++){
			builder.with(actions.get(i).getShowAnimation());
		}
		return animatorSet;
	}
	
	
	private AnimatorSet getDismissAnimatorSet() {
		AnimatorSet animatorSet = new AnimatorSet();
		Builder builder;
		builder = animatorSet.play(actions.get(0).getDisappearAnimation());
		for(int i = 1; i < actions.size(); i++){
			builder.with(actions.get(i).getDisappearAnimation());
		}
		return animatorSet;
	}
	
	private AnimatorSet getCenterAnimation() {
		AnimatorSet animatorSet = new AnimatorSet();
		Builder builder;
		builder = animatorSet.play(actions.get(itemInCenter).getCenterAnimation());
		for(int i = 0; i < actions.size(); i++){
			if(i != itemInCenter)
			builder.with(actions.get(i).getDisappearAnimation());
		}
		return animatorSet;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_DISMISS:
				dismiss();
				break;
			case MESSAGE_ANIMATION_STOP:
				mLayout.setClickable(true);
				break;
			case MESSAGE_START_RESUME_ANIMATOR:
				for(PressAction action : actions){
					action.startResumeAnimation();
				}
				break;
			case MESSAGE_SILENT_MODE_CHANGED:
				silentMode.refreshState();
                mHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS, 1200);
				break;
			case MESSAGE_TO_CENTER:
				for(PressAction action : actions){
					if(action.position != itemInCenter){
						action.getView().setVisibility(View.INVISIBLE);
					}
				}
				break;
			default: 
				break;
			}
		}
	};

	@Override
	public void show() {
        Log.d(TAG, "show start");
		super.show();
		getShowAnimation().start();
		mHandler.sendEmptyMessageDelayed(MESSAGE_START_RESUME_ANIMATOR, TIME_DISPLAY_ANIMATION);
		mHandler.sendEmptyMessageDelayed(MESSAGE_ANIMATION_STOP, TIME_DISPLAY_ANIMATION);
	}

	private void addItems() {
		powerOff = new PowerOff();
		airplaneMode = new AirplaneMode();
		silentMode = new SilentMode();
		reboot = new Reboot();
	
		String[] defaultActions = getContext().getResources().getStringArray(
				R.array.config_rgk_globalActionsList);
		ArraySet<String> addedKeys = new ArraySet<String>();
		for (int i = 0; i < defaultActions.length; i++) {
			String action = defaultActions[i];

			if (addedKeys.contains(action)) {
				continue;
			}

			if (GLOBAL_ACTION_KEY_POWER.equals(action)) {
				powerOff.position = actions.size();
				actions.add(powerOff);
                Log.d(TAG, "add power item");
			} else if (GLOBAL_ACTION_KEY_AIRPLANE.equals(action)) {
				airplaneMode.position = actions.size();
				actions.add(airplaneMode);
                Log.d(TAG, "add airplane item");
			} else if (GLOBAL_ACTION_KEY_SILENT.equals(action)) {
				silentMode.position = actions.size();
				actions.add(silentMode);
                Log.d(TAG, "add silent item");
			} else if (GLOBAL_ACTION_KEY_REBOOT.equals(action)) {
				reboot.position = actions.size();
				actions.add(reboot);
                Log.d(TAG, "add reboot item");
			}else {
				Log.e(TAG, "Invalid global action " + action);
				continue;
			}
			addedKeys.add(action);
		}
	}

	private void changeAirplaneModeSystemSetting() {
		boolean airplaneModeOn = Settings.Global.getInt(
				mContext.getContentResolver(),
				Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
		Settings.Global.putInt(mContext.getContentResolver(),
				Settings.Global.AIRPLANE_MODE_ON, airplaneModeOn ? 0 : 1);
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
		intent.putExtra("state", !airplaneModeOn);
		mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
	}

	private void changeSilentModeSystemSetting() {
		mAudioManager.setRingerMode((mAudioManager.getRingerMode() + 1) % 3);
	}

	private float getDegreesForRotation(int value) {
		switch (value) {
		case Surface.ROTATION_90:
			return 360f - 90f;
		case Surface.ROTATION_180:
			return 360f - 180f;
		case Surface.ROTATION_270:
			return 360f - 270f;
		}
		return 0f;
	}
	
	
    private Bitmap getScreenshot(Context context, int radius) {
        long start = System.currentTimeMillis();
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Matrix displayMatrix = new Matrix();
        display.getRealMetrics(displayMetrics);
        final int w = displayMetrics.widthPixels;
        final int h = displayMetrics.heightPixels;
        float[] dims = {w, h};
        float degrees = getDegreesForRotation(display.getRotation());
        boolean requiresRotation = (degrees > 0);
        if (requiresRotation) {
            // Get the dimensions of the device in its native orientation
            displayMatrix.reset();
            displayMatrix.preRotate(-degrees);
            displayMatrix.mapPoints(dims);
            dims[0] = Math.abs(dims[0]);
            dims[1] = Math.abs(dims[1]);
        }

        Bitmap bp = SurfaceControl.screenshot((int) dims[0], (int) dims[1]);
        if (bp == null) return null;

        //clip bitmap
        Bitmap ss = Bitmap.createBitmap((int) dims[0]/SCALE_RATIO, (int) dims[1]/SCALE_RATIO, Bitmap.Config.ARGB_8888);
        if (ss == null) return null;
        Canvas c = new Canvas(ss);
        c.clipRect(0, 0, dims[0] / SCALE_RATIO, dims[1] / SCALE_RATIO);
        Rect srcRect = new Rect(0, 0, bp.getWidth(), bp.getHeight());
        Rect dstRect = new Rect(0, 0, ss.getWidth() - SCALE_DELTA, ss.getHeight());
        c.drawBitmap(bp, srcRect, dstRect, null);
        c.setBitmap(null);
        bp.recycle();
        bp = ss;

        //rotate bitmap
        if (requiresRotation) {
            ss = Bitmap.createBitmap(w / SCALE_RATIO, h / SCALE_RATIO, Bitmap.Config.ARGB_8888);
            if (ss == null) return null;
            c = new Canvas(ss);
            c.translate(ss.getWidth() / 2, ss.getHeight() / 2);
            c.rotate(degrees);
            c.translate(-dims[0] / (2 * SCALE_RATIO), -dims[1] / (2 * SCALE_RATIO));
            c.drawBitmap(bp, 0, 0, null);
            c.setBitmap(null);
            bp.recycle();
            bp = ss;
        }

        //blur bitmap
        Bitmap bitmap = bp.copy(bp.getConfig(), true);

        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, bp, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);
        rs.destroy();

        long end = System.currentTimeMillis();
        Log.d(TAG, "Take time " + (end - start));
        return bitmap;
    }
	
	private abstract class PressAction implements
			android.view.View.OnClickListener {
		private ImageView icon;
		private Button button;
		private TextView title;
		private View view;
		public AnimatorSet resumeAnimation;
		public float edgePointX;
		public float edgePointY;
		public float internalPointX;
		public float internalPointY;
		public int position;

		public PressAction(int iconResId, int titleResId) {
			view = createView(iconResId, titleResId);
		}

		public void startResumeAnimation(){
		
		}

		public void endResumeAnimation() {
			if (resumeAnimation != null && resumeAnimation.isRunning()) {
				resumeAnimation.start();
			}
		}

		private View createView(int iconResId, int titleResId) {
			View view = getLayoutInflater().inflate(R.layout.power_menu_item,
					null, false);
			icon = (ImageView) view.findViewById(R.id.item_drawable);
			button = (Button) view.findViewById(R.id.item_button);
			title = (TextView) view.findViewById(R.id.item_title);

			title.setText(titleResId);
			icon.setImageResource(iconResId);

			button.setOnClickListener(this);

			return view;
		}

		public View getView() {
			return view;
		}

		public ImageView getIcon() {
			return icon;
		}

		public void setTitle(int titleResId) {
			title.setText(titleResId);
		}
		
		public void setTranslation(float translationX, float translationY){
			view.setTranslationX(translationX);
			view.setTranslationY(translationY);
		}
		
		public AnimatorSet getShowAnimation(){
			AnimatorSet animatorSet = new AnimatorSet();
			Builder builder;
			builder = animatorSet.play(
							ObjectAnimator.ofFloat(view, "translationX",
									view.getTranslationX(), internalPointX)).with(
							ObjectAnimator.ofFloat(view, "translationY",
									view.getTranslationY(), internalPointY));
			if(isInCenter && position == itemInCenter){
				builder.with(
					ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1f)).with(
							ObjectAnimator.ofFloat(view, "scaleY",
									1.5f , 1f));
			}
			animatorSet.setDuration(TIME_DISPLAY_ANIMATION);
			return animatorSet;
		}
		
		public AnimatorSet getCenterAnimation(){
			AnimatorSet animatorSet = new AnimatorSet();
			Builder builder;
			builder = animatorSet.play(
							ObjectAnimator.ofFloat(view, "translationX",
									view.getTranslationX(), 0)).with(
							ObjectAnimator.ofFloat(view, "translationY",
									view.getTranslationY(), 0));
			builder.with(
				ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f)).with(
						ObjectAnimator.ofFloat(view, "scaleY",
								1f , 1.5f));
			animatorSet.setDuration(TIME_DISPLAY_ANIMATION);
			return animatorSet;
		}
		
		public AnimatorSet getDisappearAnimation(){
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.play(
					ObjectAnimator.ofFloat(view, "translationX",
							view.getTranslationX(), edgePointX)).with(
					ObjectAnimator.ofFloat(view, "translationY",
							view.getTranslationY(), edgePointY));
			animatorSet.setDuration(TIME_DISPLAY_ANIMATION);
			return animatorSet;
		}
	}

	private final class AirplaneMode extends PressAction {
		private ImageView icon;
		private float translationX;
		private float translationY;

		private AirplaneMode() {
			super(R.drawable.global_action_drawable_airplane, R.string.global_actions_toggle_airplane_mode);
			icon = getIcon();
		}

		@Override
		public void onClick(View arg0) {
			if (resumeAnimation.isRunning()) {
				resumeAnimation.end();
			}
			icon.setTranslationX(translationX);
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.play(
					ObjectAnimator.ofFloat(icon, "translationY", translationY,
							translationY - icon.getMeasuredHeight())).before(
					GlobalActionsDialog.this.getDismissAnimatorSet());
			animatorSet.setDuration(300);
			animatorSet.start();
			changeAirplaneModeSystemSetting();
            mHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS, 300 + TIME_DISPLAY_ANIMATION);
		}

		@Override
		public void startResumeAnimation() {
			translationX = icon.getTranslationX();
			translationY = icon.getTranslationY();
			if (resumeAnimation == null) {
				AnimatorSet animatorSet = new AnimatorSet();
				ObjectAnimator animator = ObjectAnimator.ofFloat(icon,
						"translationX", translationX,
						translationX - icon.getMeasuredWidth()/8f, translationX
								+ icon.getMeasuredWidth()/8f, translationX);
				animator.setRepeatCount(ObjectAnimator.INFINITE);
				animatorSet.play(animator);
				animatorSet.setDuration(2000);

				resumeAnimation = animatorSet;
			}
			
			boolean airplaneModeOn = Settings.Global.getInt(
				mContext.getContentResolver(),
				Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
			
			if (!resumeAnimation.isRunning() && airplaneModeOn) {
				resumeAnimation.start();
			}
		}
	}
	
	private final class SilentMode extends PressAction {
		private ImageView icon;

		private SilentMode() {
			super(R.drawable.silent_mode_normal, R.string.global_action_toggle_silent_mode);
			icon = getIcon();
			refreshState();
		}
		
		public void refreshState(){
			int cureentSilentMode = mAudioManager.getRingerMode();
			switch(cureentSilentMode){
			case AudioManager.RINGER_MODE_NORMAL:
				icon.setImageResource(R.drawable.silent_mode_normal);
				break;
			case AudioManager.RINGER_MODE_SILENT:
				icon.setImageResource(R.drawable.silent_mode_silent);
				break;
			case AudioManager.RINGER_MODE_VIBRATE:
				icon.setImageResource(R.drawable.silent_mode_vibrate);
				break;
			default:
			
				break;
			}
		}

		@Override
		public void onClick(View arg0) {
			AnimatorSet animatorSet = new AnimatorSet();
			ObjectAnimator animator = ObjectAnimator.ofFloat(icon, "rotation", 0f, -30f, 30f, 0f);
			animator.setRepeatCount(3);
			animatorSet.play(animator).before(GlobalActionsDialog.this.getDismissAnimatorSet());
			animatorSet.setDuration(300);
			animatorSet.start();

            mAudioManager.setRingerMode((mAudioManager.getRingerMode() + 1) % 3);

            mHandler.sendEmptyMessageDelayed(MESSAGE_SILENT_MODE_CHANGED, 300);
		}
	}
	
	private final class PowerOff extends PressAction {
		private ImageView icon;

		private PowerOff() {
			super(R.drawable.global_action_drawable_power_off, R.string.global_action_power_off);
			icon = getIcon();
		}

		@Override
		public void onClick(View arg0) {
			if(isInCenter){
				mWindowManagerFuncs.shutdown(false);
				dismiss();
			}else{
				isInCenter = true;
				itemInCenter = position;
				mLayout.setClickable(false);
				GlobalActionsDialog.this.getCenterAnimation().start();
				mHandler.sendEmptyMessageDelayed(MESSAGE_TO_CENTER, TIME_DISPLAY_ANIMATION);
				mHandler.sendEmptyMessageDelayed(MESSAGE_ANIMATION_STOP, TIME_DISPLAY_ANIMATION);
			}
		}
	}
	
	
	private final class Reboot extends PressAction {
		private ImageView icon;

		private Reboot() {
			super(R.drawable.global_action_drawable_reboot, R.string.global_action_reboot);
			icon = getIcon();
		}

		@Override
		public void onClick(View arg0) {
		
			if(isInCenter){
				mWindowManagerFuncs.reboot(false);
				dismiss();
			}else{
				isInCenter = true;
				itemInCenter = position;
				mLayout.setClickable(false);
				GlobalActionsDialog.this.getCenterAnimation().start();
				mHandler.sendEmptyMessageDelayed(MESSAGE_TO_CENTER, TIME_DISPLAY_ANIMATION);
				mHandler.sendEmptyMessageDelayed(MESSAGE_ANIMATION_STOP, TIME_DISPLAY_ANIMATION);
			}
		}
	}
	
	
    @Override
    public void onClick(View arg0) {
		if(isInCenter){
			for(PressAction action : actions){
				action.getView().setVisibility(View.VISIBLE);
			}
			mLayout.setClickable(false);
			getShowAnimation().start();
			isInCenter = false;
			mHandler.sendEmptyMessageDelayed(MESSAGE_ANIMATION_STOP, TIME_DISPLAY_ANIMATION);
		}else{
			Log.d(TAG, "isInCenter is false");
			mLayout.setClickable(false);
			getDismissAnimatorSet().start();
			mHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS, TIME_DISPLAY_ANIMATION);
		}
	}
}
