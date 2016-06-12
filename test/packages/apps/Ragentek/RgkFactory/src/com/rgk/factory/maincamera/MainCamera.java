
//file create by liunianliang 20130718~20130724

package com.rgk.factory.maincamera;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.rgk.factory.R;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaActionSound;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.rgk.factory.Config;
import com.rgk.factory.ItemTestActivity;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

public class MainCamera extends ActivityBase implements View.OnClickListener,
		PreviewFrameLayout.OnSizeChangedListener, Listener,
		OnLayoutChangeListener, SurfaceHolder.Callback {


	private static final String TAG_PROFILE = "MainCamera";
	public static String TAG = "MainCamera";
	private int mCameraId = Config.MainCamera;
	
    // The degrees of the device rotated clockwise from its natural orientation.
    private int mOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;

	// multiple cameras support
	private int mNumberOfCameras;
	private int mFrontCameraId;
	private int mBackCameraId;

	private boolean mOpenCameraFail = false;
	private boolean mCameraDisabled = false;

	private boolean mPausing;
	private boolean mFirstTimeInitialized;
    private View mPreviewFrame;  // Preview frame area.

	private SurfaceHolder mSurfaceHolder = null;
	private LinearLayout mLinearLayout = null;

	private Parameters mInitialParams;
	private static final int PREVIEW_STOPPED = 0;
	private static final int IDLE = 1; // preview is active
	// Focus is in progress. The exact focus state is in Focus.java.
	private static final int FOCUSING = 2;
	private static final int SNAPSHOT_IN_PROGRESS = 3;
	private int mCameraState = PREVIEW_STOPPED;

	private boolean mSnapshotOnIdle = false;

	// The subset of parameters we need to update in setCameraParameters().
	private static final int UPDATE_PARAM_INITIALIZE = 1;
	private static final int UPDATE_PARAM_ZOOM = 2;
	private static final int UPDATE_PARAM_PREFERENCE = 4;
	private static final int UPDATE_PARAM_ALL = -1;

	// The display rotation in degrees. This is only valid when mCameraState is
	// not PREVIEW_STOPPED.
	private int mDisplayRotation;
	// The value for android.hardware.Camera.setDisplayOrientation.
	private int mDisplayOrientation;

	private final CameraErrorCb mErrorCallback = new CameraErrorCb();

	private class CameraErrorCb implements
			android.hardware.Camera.ErrorCallback {
		private static final String TAG = "CameraErrorCallback";

		public void onError(int error, android.hardware.Camera camera) {
			Log.e(TAG, "Got camera error callback. error=" + error);
			if (error == android.hardware.Camera.CAMERA_ERROR_SERVER_DIED) {
				// We are not sure about the current state of the app (in
				// preview or
				// snapshot or recording). Closing the app is better than
				// creating a
				// new Camera object.
				throw new RuntimeException("Media server died.");
			} else if (error == android.hardware.Camera.CAMERA_ERROR_UNKNOWN) {
				stopCamera();
				Util.showErrorAndFinish(MainCamera.this,
						R.string.cannot_connect_camera);
			}
		}
	}
	private Button mTakePictureButton = null;
	private Button mPreviewButton = null;
	//private ByteArrayInputStream mByteArrayInputStream;
	//private Drawable mDrawable;
	private boolean mHadSendBroadcast = false;
	
	//add huruilong
	private boolean mHadTackPic = false;
	int mDisplayWith ;
	int mDisplayHeight ;
	Display mDisplay;
	WindowManager mWindowManager;
	//end

	// add huruilong autofocuscallback
	private static boolean sIsAutoFocusCallback = false;
	private FocusManager mFocusManager;
	private RotateLayout mFocusAreaIndicator;
    private PreviewFrameLayout mPreviewFrameLayout;
	
	private MediaActionSound mCameraSound;
	// we should cache preview width and height for onConfigurationChanged().
	private int mPreviewFrameWidth;
	private int mPreviewFrameHeight;
	private Parameters mParameters; 
	public static final double [] RATIOS = new double[]{1.3333,1.5,1.6667,1.7778};
	
	private Camera.PictureCallback mPictureCallBack = new Camera.PictureCallback() {

		// @SuppressWarnings("deprecation")
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			LogUtils.logD(TAG, "onPictureTaken");
			/*try {
				if (mByteArrayInputStream != null) {
					mByteArrayInputStream.close();
					mByteArrayInputStream = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mByteArrayInputStream = new ByteArrayInputStream(data);*/
			/**
			 * Drawable d = BitmapDrawable.createFromStream(bais, Environment
			 * .getExternalStorageDirectory().getAbsolutePath() + "/img.jpeg");
			 **/
			//if (mDrawable != null) {
			//	mDrawable.setCallback(null);
			//	mDrawable = null;
			//}
//			stopPreview();
			stopCamera();
			//mDrawable = BitmapDrawable.createFromStream(mByteArrayInputStream,
			//		null);
//			mLinearLayout.setBackgroundDrawable(mDrawable);
			//add huruilong
			mFocusManager.updateFocusUI();
			//end
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	};
	
	@Override
	public void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
	       // M: huangkunming 2014.12.20 @{
	       // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
	    	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
	    	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
		// @}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		LogUtils.logD(TAG, "onCreate");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		/*
		 * To reduce startup time, we start the camera open and preview threads.
		 * We make sure the preview is started at the end of onCreate.
		 */
		mCameraOpenThread.start();
		setContentView(R.layout.maincamera);
		FrameLayout mLayout = (FrameLayout) findViewById(R.id.camera_root);
	    mLayout.setSystemUiVisibility(0x00002000);
		//add huruilong
		mWindowManager = getWindowManager();
		mDisplay = mWindowManager.getDefaultDisplay();
		mDisplayHeight = mDisplay.getHeight();
		mDisplayWith = mDisplay.getWidth();
		//end
		mLinearLayout = (LinearLayout) findViewById(R.id.maincamera_view_layout);
		mTakePictureButton = (Button) findViewById(R.id.main_take);
		mPreviewButton = (Button) findViewById(R.id.main_priview);
		
		mCameraSound = new MediaActionSound();
        // startPreview needs this.
        mPreviewFrameLayout = (PreviewFrameLayout) findViewById(R.id.frame);

        // Set touch focus listener.
//        setSingleTapUpListener(mPreviewFrameLayout);
        //Set Long Press for Object Tracking listener.
//        setLongPressListener(mPreviewFrameLayout);
        mPreviewFrameLayout.addOnLayoutChangeListener(this);
        mPreviewFrameLayout.setOnSizeChangedListener(this);
        
		// for focus manager used
		mFocusAreaIndicator = (RotateLayout) findViewById(R.id.focus_indicator_rotate_layout);
		// Not required, but reduces latency when playback is requested later.
		mCameraSound.load(MediaActionSound.FOCUS_COMPLETE);
		
		mTakePictureButton.setOnClickListener(this);
		mPreviewButton.setOnClickListener(this);

		((Button) findViewById(R.id.maincarmera_pass)).setOnClickListener(this);
		((Button) findViewById(R.id.maincarmera_fail)).setOnClickListener(this);
		// don't set mSurfaceHolder here. We have it set ONLY within
		// surfaceChanged / surfaceDestroyed, other parts of the code
		// assume that when it is set, the surface is also set.
		SurfaceView preview = (SurfaceView) findViewById(R.id.camera_preview);
		SurfaceHolder holder = preview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		// Make sure camera device is opened.
		try {
			mCameraOpenThread.join();
			mCameraOpenThread = null;
			if (mOpenCameraFail) {
				Util.showErrorAndFinish(this, R.string.cannot_connect_camera);
				return;
			} else if (mCameraDisabled) {
				Util.showErrorAndFinish(this, R.string.camera_disabled);
				return;
			}
		} catch (InterruptedException ex) {
			// ignore
		}
		mCameraPreviewThread.start();

		mNumberOfCameras = CameraHolder.instance().getNumberOfCameras();
		mBackCameraId = CameraHolder.instance().getBackCameraId();
		mFrontCameraId = CameraHolder.instance().getFrontCameraId();
		// Wait until the camera settings are retrieved.
		synchronized (mCameraPreviewThread) {
			try {
				mCameraPreviewThread.wait();
			} catch (InterruptedException ex) {
				// ignore
			}
		}
		// Do this after starting preview because it depends on camera
		// parameters.
		initializeIndicatorControl();
		// Make sure preview is started.
		try {
			mCameraPreviewThread.join();
		} catch (InterruptedException ex) {
			// ignore
		}
		mCameraPreviewThread = null;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mHadTackPic = false;
		LogUtils.logD(TAG, "onPause)=");
		//mOrientationListener.disable();
		if (mCameraSound != null) {
			mCameraSound.release();
			mCameraSound = null;
		}
		if (mFocusManager != null) {
			mFocusManager.onCameraReleased();
		}
		clearFocusAndFace();
        mPausing = true;
        stopCamera();
		mPreviewButton.setVisibility(View.VISIBLE);
    	super.onPause();
    	mPausing = true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		 // Make sure we have a surface in the holder before proceeding.
        if (holder.getSurface() == null) {
            Log.d(TAG, "holder.getSurface() == null");
            return;
        }

        Log.v(TAG, "surfaceChanged. w=" + width + ". h=" + height);

        // We need to save the holder for later use, even when the mCameraDevice
        // is null. This could happen if onResume() is invoked after this
        // function.
        mSurfaceHolder = holder;

        // The mCameraDevice will be null if it fails to connect to the camera
        // hardware. In this case we will show a dialog and then finish the
        // activity, so it's OK to ignore it.
        if (mCameraDevice == null) return;

        // Sometimes surfaceChanged is called after onPause or before onResume.
        // Ignore it.
        if (mPausing || isFinishing()) return;

        // Set preview display if the surface is being created. Preview was
        // already started. Also restart the preview if display rotation has
        // changed. Sometimes this happens when the device is held in portrait
        // and camera app is opened. Rotation animation takes some time and
        // display rotation in onCreate may not be what we want.
        if (mCameraState == PREVIEW_STOPPED) {
            startPreview();
        } else {
//            if (Util.getDisplayRotation(this) != mDisplayRotation) {
//            }
            setDisplayOrientation();
            if (holder.isCreating()) {
                // Set preview display if the surface is being created and preview
                // was already started. That means preview display was set to null
                // and we need to set it now.
                setPreviewDisplay(holder);
            }
        }

        // If first time initialization is not finished, send a message to do
        // it later. We want to finish surfaceChanged as soon as possible to let
        // user see preview first.
        if (!mFirstTimeInitialized) {
            initializeFirstTime();
        } else {
            initializeSecondTime();
        }
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		stopPreview();
        mSurfaceHolder = null;
	}

	@Override
	protected void doOnResume() {
		// TODO Auto-generated method stub

		LogUtils.logD(TAG, "onResume");
		if (mOpenCameraFail || mCameraDisabled)
			return;
		mPausing = false;
		// Start the preview if it is not started.
		if (mCameraState == PREVIEW_STOPPED) {
			try {
				mCameraDevice = Util.openCamera(this, mCameraId);
				initializeCapabilities();
				startPreview();
			} catch (CameraHardwareException e) {
				Util.showErrorAndFinish(this, R.string.cannot_connect_camera);
				return;
			} catch (CameraDisabledException e) {
				Util.showErrorAndFinish(this, R.string.camera_disabled);
				return;
			}
		}

		if (mSurfaceHolder != null) {
			// If first time initialization is not finished, put it in the
			// message queue.
			if (!mFirstTimeInitialized) {
				initializeFirstTime();
			} else {
				initializeSecondTime();
			}
		}
		keepScreenOnAwhile();

        if (mCameraState == IDLE) {
        }
		mHadTackPic = false;

		mCameraSound = new MediaActionSound();
        // startPreview needs this.
        mPreviewFrameLayout = (PreviewFrameLayout) findViewById(R.id.frame);

        // Set touch focus listener.
//        setSingleTapUpListener(mPreviewFrameLayout);
        //Set Long Press for Object Tracking listener.
//        setLongPressListener(mPreviewFrameLayout);
        mPreviewFrameLayout.addOnLayoutChangeListener(this);
        mPreviewFrameLayout.setOnSizeChangedListener(this);
        
		// for focus manager used
		mFocusAreaIndicator = (RotateLayout) findViewById(R.id.focus_indicator_rotate_layout);
		// Not required, but reduces latency when playback is requested later.
		mCameraSound.load(MediaActionSound.FOCUS_COMPLETE);
	}

	public Camera getCameraDevice() {
		return mCameraDevice;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		clearFocusAndFace();
		if (mFocusManager != null) {
			mFocusManager.setFocusAreaIndicator(mFocusAreaIndicator);
			View mFocusIndicator = mFocusAreaIndicator
					.findViewById(R.id.focus_indicator);
			// Set the length of focus indicator according to preview frame
			// size.
			int len = Math.min(mPreviewFrameWidth, mPreviewFrameHeight) / 4;
			ViewGroup.LayoutParams layout = mFocusIndicator.getLayoutParams();
			layout.width = len;
			layout.height = len;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LogUtils.logD(TAG, "ontouchevent");
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
		    if(!mHadTackPic){
			    mHandler.removeMessages(RESET_TOUCH_FOCUS);
			    onSingleTapUp(event.getX(), event.getY());
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onLayoutChange(View view, int i, int j, int k, int l, int i1,
			int j1, int k1, int l1) {
		// TODO Auto-generated method stub
		LogUtils.logD(TAG, "onLayoutChange()=");

//		if (mFocusManager != null) {
//			mFocusManager.setFocusAreaIndicator(mFocusAreaIndicator);
//			View mFocusIndicator = mFocusAreaIndicator
//					.findViewById(R.id.focus_indicator);
//			// Set the length of focus indicator according to preview frame
//			// size.
//			int len = Math.min(mPreviewFrameWidth, mPreviewFrameHeight) / 4;
//			ViewGroup.LayoutParams layout = mFocusIndicator.getLayoutParams();
//			layout.width = len;
//			layout.height = len;
//		}
	}

	@Override
	public void autoFocus() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        mFocusStartTime = System.currentTimeMillis();
		LogUtils.logD(TAG, "AutoFocus()=");
		try {
			mCameraDevice.autoFocus(mAutoFocusCallback);
		} catch(Exception e){			
		}	
	}

	@Override
	public void cancelAutoFocus() {
		// TODO Auto-generated method stub
		LogUtils.logD(TAG, "cancelAutoFocus()=");
		if(mCameraDevice != null) {
			mCameraDevice.cancelAutoFocus();
		}		
		applyFocusCapabilities(!sIsAutoFocusCallback);
        sIsAutoFocusCallback = false;
	}

	@Override
	public void playSound(int soundId) {
		// TODO Auto-generated method stub
		LogUtils.logD(TAG, "playSound()=");
		mCameraSound.play(soundId);
	}

	@Override
	public void onSizeChanged(int width, int height) {
		// TODO Auto-generated method stub
		LogUtils.logD(TAG, "onSizeChanged() width=" + width);
		LogUtils.logD(TAG, "onSizeChanged() height=" + height);
		if (mFocusManager != null) {
			mFocusManager.setPreviewSize(width, height);
		}
		mPreviewFrameWidth = width;
		mPreviewFrameHeight = height;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.main_take:
			mTakePictureButton.setVisibility(View.GONE);
			mPreviewButton.setVisibility(View.VISIBLE);
			break;
		case R.id.main_priview:
			// mTakePicture.setVisibility(View.VISIBLE);
			mHadTackPic = true;
			mPreviewButton.setVisibility(View.GONE);
			if (mCameraDevice != null)
				try {
					mCameraDevice.takePicture(null, null, mPictureCallBack);
				} catch(Exception e) {					
				}
		        LogUtils.logD(TAG, "onClick take picture");				
			break;
		case R.id.maincarmera_pass:
			SendBroadcast(Config.PASS);
			break;
		case R.id.maincarmera_fail:
			SendBroadcast(Config.FAIL);
			break;
		}
	}	
	
	public void onSingleTapUp(float x, float y){
		 String focusMode = null;
        if (mFocusManager != null) {
            focusMode = mFocusManager.getCurrentFocusMode();
        }
        LogUtils.logD(TAG, "onSingleTapUp focusMode " + focusMode);
        if (focusMode == null || Parameters.FOCUS_MODE_INFINITY.equals(focusMode)) {
            return;
        }
        if (mCameraDevice == null
                || mFocusManager == null ) {
            return;
        }

        // Check if metering area or focus area is supported.
        if (!mFocusManager.getFocusAreaSupported()
                && !mFocusManager.getMeteringAreaSupported()) {
            return;
        }

        Matrix m = new Matrix();
        m.setRotate(0, mDisplayWith / 2, mDisplayHeight / 2);
        Matrix inv = new Matrix();
        m.invert(inv);
        float[] pts = new float[] {x, y};
        inv.mapPoints(pts);
        LogUtils.logD(TAG, "onSingleTapUp x " + (int) (pts[0] + 0.5f) +  " y " + (int) (pts[1] + 0.5f));
        mFocusManager.onSingleTapUp((int) (pts[0] + 0.5f), (int) (pts[1] + 0.5f));
//        mFocusManager.onSingleTapUp((int) x, (int) y);
	}

	private void clearFocusAndFace() {
		if (mFocusManager != null) {
			mFocusManager.removeMessages();
		}
	}

	private void initializeFocusManager() {
		LogUtils.logD(TAG, "initializeFocusManager");
		if (mFocusManager != null) {
			mFocusManager.removeMessages();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mFocusManager.clearFocusOnContinuous();
				}
			});
			mFocusManager.release();
		}
		// Create FocusManager object. startPreview needs it.
		CameraInfo info = getCameraInfo();
		boolean mirror = (info.facing == CameraInfo.CAMERA_FACING_FRONT);
		String[] defaultFocusModes = { "auto", "infinity", "macro",
				"continuous-video", "continuous-picture" };// SettingChecker.getModeDefaultFocusModes(this,
															// mCameraActor.getMode());
		// modify huruilong
		/*
		 * mFocusManager = new FocusManager(this, mPreferences,
		 * defaultFocusModes, mFocusAreaIndicator, mInitialParams,
		 * mCameraActor.getFocusManagerListener(), mirror, getMainLooper(),
		 * SettingChecker.getModeContinousFocusMode(mCameraActor.getMode()));
		 */
		mFocusManager = new FocusManager(this, defaultFocusModes,
				mFocusAreaIndicator, mParameters, this, mirror,
				getMainLooper(), "continuous-picture");
		mFocusManager.setPreviewSize(mPreviewFrameWidth, mPreviewFrameHeight);
		mFocusManager.setDisplayOrientation(0);
	}

	public CameraInfo getCameraInfo() {
		CameraInfo info = new CameraInfo();
		try {
			Camera.getCameraInfo(Config.MainCamera, info);
		} catch(RuntimeException re) {
			re.printStackTrace();
		}
		
		return info;
	}

	public FocusManager getFocusManager() {
		return mFocusManager;
	}

	private final AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback();
	private final AutoFocusMoveCallback mAutoFocusMoveCallback = new AutoFocusMoveCallback();

	private long mFocusStartTime;

	private List<OnOrientationListener> mOrientationListeners = new CopyOnWriteArrayList<OnOrientationListener>();

	public boolean addOnOrientationListener(OnOrientationListener l) {
		if (!mOrientationListeners.contains(l)) {
			return mOrientationListeners.add(l);
		}
		return false;
	}

	public boolean removeOnOrientationListener(OnOrientationListener l) {
		return mOrientationListeners.remove(l);
	}

	private List<OnParametersReadyListener> mParametersListeners = new CopyOnWriteArrayList<OnParametersReadyListener>();

	public boolean addOnParametersReadyListener(OnParametersReadyListener l) {
		if (!mParametersListeners.contains(l)) {
			return mParametersListeners.add(l);
		}
		return false;
	}

	public boolean removeOnParametersReadyListener(OnParametersReadyListener l) {
		return mParametersListeners.remove(l);
	}

	public interface OnOrientationListener {
		void onOrientationChanged(int orientation);
	}

	public interface OnParametersReadyListener {
		void onCameraParameterReady();
	}

	private MyOrientationEventListener mOrientationListener;

	private class MyOrientationEventListener extends OrientationEventListener {

		public MyOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(int orientation) {
			// We keep the last known orientation. So if the user first orient
			// the camera then point the camera to floor or sky, we still have
			// the correct orientation.
			if (orientation == ORIENTATION_UNKNOWN) {
				return;
			}
			for (int i = 0; i < mOrientationListeners.size(); i++) {
				mOrientationListeners.get(i).onOrientationChanged(orientation);
			}
		}
	}

	public android.hardware.Camera.AutoFocusMoveCallback getAutoFocusMoveCallback() {
		LogUtils.logD(TAG, "PhotoActor.getAutoFocusMoveCallback");
		return mAutoFocusMoveCallback;
	}

	private static final int RESET_TOUCH_FOCUS = 0;
	private static final int RESET_TOUCH_FOCUS_DELAY = 500;
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			boolean focused = (Boolean) msg.obj;
			switch (msg.arg1) {
			case RESET_TOUCH_FOCUS:
				if(mFocusManager != null)
					mFocusManager.onAutoFocus(focused);
				sIsAutoFocusCallback = true;
				break;
			default:
				break;
			}
		}
	};
	
	private final class AutoFocusCallback implements
			android.hardware.Camera.AutoFocusCallback {
		@Override
		public void onAutoFocus(boolean focused, android.hardware.Camera camera) {
			LogUtils.logD(TAG, "onAutoFocus focused " + focused);
			Message message = new Message();
			message.obj = focused;
			message.arg1 = RESET_TOUCH_FOCUS;
			mHandler.removeMessages(RESET_TOUCH_FOCUS);
			mHandler.sendMessageDelayed(message, RESET_TOUCH_FOCUS_DELAY);
		}
	}

	private final class AutoFocusMoveCallback implements
			android.hardware.Camera.AutoFocusMoveCallback {
		@Override
		public void onAutoFocusMoving(boolean moving,
				android.hardware.Camera camera) {
			LogUtils.logD(TAG, "onAutoFocusMoving");
			if(mFocusManager != null)
				mFocusManager.onAutoFocusMoving(moving);
		}
	}

	public void setFocusParameters() {
		// TODO Auto-generated method stub
		LogUtils.logE(TAG, "setFocusParameters");
        applyParameterForFocus(!sIsAutoFocusCallback);
        sIsAutoFocusCallback = false;
	}

	// add huruilong end

	private void applyParameterForFocus(boolean setArea) {
		// TODO Auto-generated method stub
		LogUtils.logD(TAG, "applyParameterForFocus()=");
		applyFocusCapabilities(setArea);
        //Note: here doesn't fetch parameters from server
        //set the focus mode to server
            applyParametersToServer();
	}
	
	// apply focus capability and mode 
	//must modify hrl
	//5
	public void applyFocusCapabilities(boolean setArea) {
		LogUtils.logD(TAG, "applyFocusCapabilities()=");
		if (mFocusManager.getAeLockSupported()) {
			mParameters.setAutoExposureLock(mFocusManager.getAeLock());
		}
		if (mFocusManager.getAwbLockSupported()) {
			mParameters.setAutoWhiteBalanceLock(mFocusManager.getAwbLock());
		}
		if (mFocusManager.getFocusAreaSupported() && setArea) {
			mParameters.setFocusAreas(mFocusManager.getFocusAreas());
		}
		if (mFocusManager.getMeteringAreaSupported() && setArea) {
			// Use the same area for focus and metering.
			mParameters.setMeteringAreas(mFocusManager.getMeteringAreas());
		}

		overrideFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		mParameters.setFocusMode(mFocusManager.getFocusMode());
		LogUtils.logD(TAG, "applyFocusCapabilities() mFocusManager.getFocusMode()= " + mFocusManager.getFocusMode());
		// Here set continuous callback
		applyContinousCallback();
	}
	
    //Apply parameters to server, so native camera can apply current settings.
    //Note: mCameraDevice.setParameters() consumes long time, please don't use it unnecessary.
    public void applyParametersToServer() {
        if (mCameraDevice == null || mParameters == null) {
            LogUtils.logD(TAG, "mCameraDevice or mParameters is null");
            return;
        }
        if (mCameraDevice != null && mParameters != null) {
        	try {
            mCameraDevice.setParameters(mParameters);
        	} catch(RuntimeException re){
        		re.printStackTrace();
        	}
        }
        
        LogUtils.logD(TAG, "applyParameterToServer() mParameters=" + mParameters.flatten() + ", mCameraDevice=" + mCameraDevice);
    }
    
    private void setPicturePreview(Parameters parameters){
    	setPicturePreview(parameters, null);
    }
    
    private void setPicturePreview(Parameters parameters, String value){
    	LogUtils.logD(TAG, "setPicturePreview()");
    	
    	 if (value == null) {
             initialCameraPictureSize(this, parameters);
         } else {
             List<Size> supported = parameters.getSupportedPictureSizes();
             String targetRatio = "1.7778";
             setCameraPictureSize(value, supported, parameters, targetRatio, this);
         }
    	 
    	double previewRatio = Double.parseDouble(Util.PICTURE_RATIO_4_3);
        //if (!"1.3333".equals(Util.PICTURE_RATIO_4_3)) {
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            if (metrics.widthPixels > metrics.heightPixels) {
                previewRatio = (double) metrics.widthPixels / metrics.heightPixels;
            } else {
                previewRatio = (double) metrics.heightPixels / metrics.widthPixels;
            }
        //}
        // Set a preview size that is closest to the viewfinder height and has
        // the right aspect ratio.
        List<Size> sizes = parameters.getSupportedPreviewSizes();
        Size optimalSize = Util.getOptimalPreviewSize(this, sizes,
                previewRatio, false, true);
        Size original = parameters.getPreviewSize();
        LogUtils.logE(TAG, "setPicturePreview original " + original);
        LogUtils.logE(TAG, "setPicturePreview optimalSize.width " + optimalSize.width);
        LogUtils.logE(TAG, "setPicturePreview optimalSize.height " + optimalSize.height);
        if (!original.equals(optimalSize)) {
        	parameters.setPreviewSize(optimalSize.width, optimalSize.height);

        }
    }

    public static void initialCameraPictureSize(Context context, Parameters parameters) {
        /// M: here we find the full screen picture size for default, not first one in arrays.xml
        List<String> supportedRatios = buildPreviewRatios(context, parameters);
        String ratioString = null;
        if (supportedRatios != null && supportedRatios.size() > 0) {
            ratioString = supportedRatios.get(supportedRatios.size() - 1);
        }
        List<String> supportedSizes = buildSupportedPictureSize(context, parameters, ratioString);

        if (supportedSizes != null && supportedSizes.size() > 0) {
            String findPictureSize = supportedSizes.get(supportedSizes.size() - 1);
            Point ps = getSize(findPictureSize);
            parameters.setPictureSize(ps.x, ps.y);
        }
    }
    
    private static List<String> buildPreviewRatios(Context context, Parameters parameters) {
        List<String> supportedRatios = new ArrayList<String>();
        String findString = null;
        if (context != null && parameters != null) {
            double find = findFullscreenRatio(context, parameters);
            supportedRatios.add(getRatioString(4d / 3)); //add standard ratio
            findString = getRatioString(find);
            if (!supportedRatios.contains(findString)) { //add full screen ratio
                supportedRatios.add(findString);
            }
        }
        LogUtils.logD(TAG, "buildPreviewRatios(" + parameters + ") add supportedRatio " + findString);
        return supportedRatios;
    }
    
    private static final DecimalFormat DECIMAL_FORMATOR = new DecimalFormat("######.####",
            new DecimalFormatSymbols(Locale.ENGLISH));
    public static String getRatioString(double ratio) {
        return DECIMAL_FORMATOR.format(ratio);
    }
    
    public static Point getSize(String sizeString) {
        Point size = null;
        int index = sizeString.indexOf('x');
        if (index != -1) {
            int width = Integer.parseInt(sizeString.substring(0, index));
            int height = Integer.parseInt(sizeString.substring(index + 1));
            size = new Point(width, height);
        }
        LogUtils.logE(TAG, "getSize(" + sizeString + ") return " + size);
        return size;
    }
    
    public static boolean setCameraPictureSize(String candidate, List<Size> supported,
            Parameters parameters, String targetRatio, Context context) {
    	LogUtils.logE(TAG, "setCameraPictureSize(" + candidate + ")");
        int index = candidate.indexOf('x');
        if (index == -1) {
            return false;
        }
        List<String> supportedRatioSizes = buildSupportedPictureSize(context, parameters, targetRatio);
        candidate = find(candidate, supportedRatioSizes);
        index = candidate == null ? -1 : candidate.indexOf('x');
        int width = Integer.parseInt(candidate.substring(0, index));
        int height = Integer.parseInt(candidate.substring(index + 1));
        parameters.setPictureSize(width, height);
        return true;
    }
    
    public static String find(String current, List<String> supportedList) {
        String supported = current;
        int index = current.indexOf('x');
        if (index != -1 && supportedList != null && !supportedList.contains(current)) {
            //find other appropriate size
            int size = supportedList.size();
            Point findPs = getSize(supportedList.get(size - 1));
            Point candidatePs = getSize(current);
            for (int i = size - 2; i >= 0; i--) {
                Point ps = getSize(supportedList.get(i));
                if (ps != null && Math.abs(ps.x - candidatePs.x) <
                        Math.abs(findPs.x - candidatePs.x)) {
                    findPs = ps;
                }
            }
            supported = buildSize(findPs.x, findPs.y);
        }
        if (!supportedList.contains(supported)) {
            supported = supportedList.get(0);
        }


        LogUtils.logV(TAG, "find(" + current + ") return " + supported);
        return supported;
    }
    
    
    public static List<String> buildSupportedPictureSize(Context context, Parameters parameters, String targetRatio) {
        ArrayList<String> list = new ArrayList<String>();
        double ratio = 0;
        if (targetRatio == null) {
            ratio = findFullscreenRatio(context, parameters);
        } else {
            try {
                ratio = Double.parseDouble(targetRatio);
            } catch (NumberFormatException e) {
            	LogUtils.logE(TAG, "buildSupportedPictureSize() bad ratio: " + targetRatio + e);
                ratio = findFullscreenRatio(context, parameters);
            }
        }
        List<Size> sizes = parameters.getSupportedPictureSizes();
        if (sizes != null) {
            for (Size size : sizes) {
                if (toleranceRatio(ratio, (double)size.width / size.height)) {
                    list.add(buildSize(size.width, size.height));
                }
            }
        }
        LogUtils.logE(TAG, "buildSupportedPictureSize(" + parameters + ", " + targetRatio + ")" + list.size());
        for (String added : list) {
        	LogUtils.logE(TAG, "buildSupportedPictureSize() add " + added);
        }
        return list;
    }
    
    public static String buildSize(int width, int height) {
        return "" + width + "x" + height;
    }
    
    public static double findFullscreenRatio(Context context, Parameters parameters) {
        double find = 4d / 3;
        if (context != null && parameters != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            double fullscreen;
            if (metrics.widthPixels > metrics.heightPixels) {
                fullscreen = (double)metrics.widthPixels / metrics.heightPixels;
            } else {
                fullscreen = (double)metrics.heightPixels / metrics.widthPixels;
            }
            
            for (int i=0; i < RATIOS.length ;i++) {
                if (Math.abs(RATIOS[i] - fullscreen) < Math.abs(fullscreen - find)) {
                    find = RATIOS[i];
                }
            }
                    }
        List<Size> sizes = parameters.getSupportedPictureSizes();
        if (sizes != null) {
            for (Size size : sizes) {
                if (toleranceRatio(find, (double)size.width / size.height)) {
                	LogUtils.logE(TAG, "findFullscreenRatio(" + parameters + ") return " + find);
                    return find;
                } 
            }
            find = 4d / 3;
        }
        LogUtils.logE(TAG, "findFullscreenRatio(" + parameters + ") return " + find);
        return find;
    }
    
    private static boolean toleranceRatio(double target, double candidate) {
        boolean tolerance = true;
        if (candidate > 0) {
            tolerance = Math.abs(target - candidate) <= Util.ASPECT_TOLERANCE;
        }
        LogUtils.logE(TAG, "toleranceRatio(" + target + ", " + candidate + ") return " + tolerance);
        return tolerance;
    }
	// add huruilong end
    public void SendBroadcast(String result) {
		LogUtils.logD(TAG, "SendBroadcast()  mHadTackPic " + mHadTackPic);
		mHadTackPic = true;
		if (!mHadSendBroadcast) {
			mHadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			stopCamera();
			AutoTestHandle.autoTest(this, TAG);
			//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));
			finish();
		}
	}
    
	protected void overrideFocusMode(String focusMode) {
		mFocusManager.overrideFocusMode(focusMode);

	}

	public void applyContinousCallback() {
		LogUtils.logD(TAG, "applyContinousCallback() mCameraDevice=" + mCameraDevice);
		// Here set AutoFocusMoveCallback dynamically.
		if (mCameraDevice != null) {
			if (mFocusManager.getContinousFocusSupported()) {
				mCameraDevice.setAutoFocusMoveCallback(getAutoFocusMoveCallback());
//				mCameraDevice.autoFocus(mAutoFocusCallback);
			} else {
				mCameraDevice.setAutoFocusMoveCallback(null);
//				mCameraDevice.autoFocus(null);
			} 
		}
	}
	


	Thread mCameraPreviewThread = new Thread(new Runnable() {
		public void run() {
			initializeCapabilities();
			startPreview();
		}
	});

	Thread mCameraOpenThread = new Thread(new Runnable() {
		public void run() {
			try {
				qcameraUtilProfile("open camera");
				mCameraDevice = Util.openCamera(MainCamera.this, mCameraId);
				qcameraUtilProfile("camera opended");
			} catch (CameraHardwareException e) {
				mOpenCameraFail = true;
			} catch (CameraDisabledException e) {
				mCameraDisabled = true;
			}
		}
	});

	private void initializeCapabilities() {
		mInitialParams = mCameraDevice.getParameters();
		// mFocusManager.initializeParameters(mInitialParams);
		// mFocusAreaSupported = (mInitialParams.getMaxNumFocusAreas() > 0
		// && isSupported(Parameters.FOCUS_MODE_AUTO,
		// mInitialParams.getSupportedFocusModes()));
	}

	private void startPreview() {
		LogUtils.logD(TAG, "startPreview()=");
		if (mPausing || isFinishing())
			return;

		mHadTackPic = false;
		qcameraUtilProfile("start preview & set parms");

		// mFocusManager.resetTouchFocus();

		mCameraDevice.setErrorCallback(mErrorCallback);

		// If we're previewing already, stop the preview first (this will blank
		// the screen).
		if (mCameraState != PREVIEW_STOPPED) {
			// Always stop preview regardless of ZSL mode. ZSL mode case is
			// handled in onPictureTaken()
			// ~punits
			stopPreview();
		}

		setPreviewDisplay(mSurfaceHolder);
		setDisplayOrientation();

		setCameraParameters(UPDATE_PARAM_ALL);

		// Inform the mainthread to go on the UI initialization.
		if (mCameraPreviewThread != null) {
			synchronized (mCameraPreviewThread) {
				mCameraPreviewThread.notify();
			}
		}

		initializeFocusManager();
		mCameraDevice.setAutoFocusMoveCallback(getAutoFocusMoveCallback());
		if(mFocusManager == null) return;
		mFocusManager.resetTouchFocus();
		if (Parameters.FOCUS_MODE_CONTINUOUS_PICTURE.equals(mFocusManager.getFocusMode()))
			mCameraDevice.cancelAutoFocus();
		mFocusManager.setAeLock(false);
		mFocusManager.setAwbLock(false);
		
		setFocusParameters();
		mFocusManager.onPreviewStarted();
        for (OnParametersReadyListener listener : mParametersListeners) {
            if (listener != null) {
                listener.onCameraParameterReady();
            }
        }
		try {
			qcameraUtilProfile("start preview");
			Log.v(TAG, "startPreview");
			mCameraDevice.startPreview();
		} catch (Throwable ex) {
			closeCamera();
			throw new RuntimeException("startPreview failed", ex);
		}

		setCameraState(IDLE);
		// mFocusManager.onPreviewStarted();

	}

	private void stopPreview() {
		if (mCameraDevice != null && mCameraState != PREVIEW_STOPPED) {
			mCameraDevice.cancelAutoFocus(); // Reset the focus.
			mCameraDevice.stopPreview();
			// mFaceDetectionStarted = false;
			mCameraDevice.release();
			mCameraDevice = null;
			sIsAutoFocusCallback = false;
		}
		setCameraState(PREVIEW_STOPPED);
		LogUtils.logD(TAG, "stopPreview)=");
		// mFocusManager.onPreviewStopped();
	}

	private void setPreviewDisplay(SurfaceHolder holder) {
		try {
			mCameraDevice.setPreviewDisplay(holder);
		} catch (Throwable ex) {
			closeCamera();
			throw new RuntimeException("setPreviewDisplay failed", ex);
		}
	}

	// We separate the parameters into several subsets, so we can update only
	// the subsets actually need updating. The PREFERENCE set needs extra
	// locking because the preference can be changed from GLThread as well.
	private void setCameraParameters(int updateSet) {
		mParameters = mCameraDevice.getParameters();

		mParameters.setPictureFormat(PixelFormat.JPEG);

		// add huruilong
//		mParameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		// end
		if ((updateSet & UPDATE_PARAM_INITIALIZE) != 0) {
			updateCameraParametersInitialize();
		}

		setPicturePreview(mParameters);
		mCameraDevice.setParameters(mParameters);
	}

	private void setDisplayOrientation() {
//		mDisplayRotation = Util.getDisplayRotation(this);
//		mDisplayOrientation = Util.getDisplayOrientation(mDisplayRotation,
//				mCameraId);
		mCameraDevice.setDisplayOrientation(90);
	}

	private static boolean isSupported(String value, List<String> supported) {
		return supported == null ? false : supported.indexOf(value) >= 0;
	}
	

	private void qcameraUtilProfile(String msg) {
		Log.e(TAG_PROFILE, " " + msg + ":" + System.currentTimeMillis()
				/ 1000.0);
	}

	private void closeCamera() {
		if (mCameraDevice != null) {
			CameraHolder.instance().release();
			mCameraDevice.setZoomChangeListener(null);
			mCameraDevice.setFaceDetectionListener(null);
			mCameraDevice.setErrorCallback(null);
			mCameraDevice = null;
			setCameraState(PREVIEW_STOPPED);
			// mFocusManager.onCameraReleased();
		}
		LogUtils.logD(TAG, "closeCamera)=");
	}

	private void setCameraState(int state) {
		mCameraState = state;
		switch (state) {
		case SNAPSHOT_IN_PROGRESS:
		case FOCUSING:
			// enableCameraControls(false);
			break;
		case IDLE:
			// mHandler.removeMessages(ENABLE_CAMERA_CONTROL);
			// mHandler.sendEmptyMessageDelayed(ENABLE_CAMERA_CONTROL, 1000);
			break;
		// end
		case PREVIEW_STOPPED:
			// enableCameraControls(true);
			break;
		}
	}

	private void updateCameraParametersInitialize() {
		// Reset preview frame rate to the maximum because it may be lowered by
		// video camera application.
		List<Integer> frameRates = mParameters.getSupportedPreviewFrameRates();
		if (frameRates != null) {
			Integer max = Collections.max(frameRates);
			mParameters.setPreviewFrameRate(max);
		}

		mParameters.setRecordingHint(false);

		// Disable video stabilization. Convenience methods not available in API
		// level <= 14
		String vstabSupported = mParameters
				.get("video-stabilization-supported");
		if ("true".equals(vstabSupported)) {
			mParameters.set("video-stabilization", "false");
		}
	}

	private void stopCamera() {
        LogUtils.logD(TAG, "stopCamera");	
		// if (mGraphView != null) {
		// mGraphView.setCameraObject(null);
		// mGraphView.releaseMemory();
		// }
		stopPreview();
		// Close the camera now because other activities may need to use it.
		closeCamera();
		resetScreenOn();

		// mFocusManager.removeMessages();
	}

	private void resetScreenOn() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void initializeIndicatorControl() {
		// setting the indicator buttons.
		boolean flashMode = false;
		FeatureInfo[] features = getPackageManager()
				.getSystemAvailableFeatures();
		for (FeatureInfo info : features) {
			if (PackageManager.FEATURE_CAMERA_FLASH.equals(info.name)) {
				flashMode = true;
				break;
			}
		}
	}

	// Snapshots can only be taken after this is called. It should be called
	// once only. We could have done these things in onCreate() but we want to
	// make preview screen appear as soon as possible.
	private void initializeFirstTime() {
		if (mFirstTimeInitialized)
			return;

		// Create orientation listenter. This should be done first because it
		// takes some time to get first orientation.
		//mOrientationListener = new MyOrientationEventListener(this);
		//mOrientationListener.enable();

//        mFocusIndicator = (ImageView) findViewById(R.id.onscreen_focus_indicator);

		// Initialize focus UI.
		mPreviewFrame = findViewById(R.id.camera_preview);
//		mPreviewFrame.setOnTouchListener(this);
//		mFocusAreaIndicator = (RotateLayout) findViewById(R.id.focus_indicator_rotate_layout);
		CameraInfo info = CameraHolder.instance().getCameraInfo()[mCameraId];
		boolean mirror = (info.facing == CameraInfo.CAMERA_FACING_FRONT);
//		mFocusManager.initialize(mFocusAreaIndicator, mPreviewFrame, mFaceView,
//				this, mirror, mDisplayOrientation);
		Util.initializeScreenBrightness(getWindow(), getContentResolver());
//		mGraphView = (GraphView) findViewById(R.id.graph_view);
//		if (mGraphView == null)
//			Log.e(TAG, "mGraphView is null");
//		mGraphView.setCameraObject(Camera.this);

		updateOnScreenIndicators();

		// Show the tap to focus toast if this is the first start.
		// if (mFocusAreaSupported &&
		// mPreferences.getBoolean(CameraSettings.KEY_CAMERA_FIRST_USE_HINT_SHOWN,
		// true)) {
		// // Delay the toast for one second to wait for orientation.
		// mHandler.sendEmptyMessageDelayed(SHOW_TAP_TO_FOCUS_TOAST, 1000);
		// }

		mFirstTimeInitialized = true;
	}
	   
	private void setOrientationIndicator(int orientation) {
//        setOrientation(orientation);
    }   
	
	private void updateOnScreenIndicators() {
        updateFocusOnScreenIndicator(mParameters.getFocusMode());
    }
	
	private void updateFocusOnScreenIndicator(String value) {
//        if (mFocusIndicator == null || (mParameters.getSupportedFocusModes().size() <= 1)) {
//            return;
//        }
//        if (Parameters.FOCUS_MODE_INFINITY.equals(value)) {
//            mFocusIndicator.setImageResource(R.drawable.ic_indicators_landscape);
//            mFocusIndicator.setVisibility(View.VISIBLE);
//        } else if (Parameters.FOCUS_MODE_MACRO.equals(value)) {
//            mFocusIndicator.setImageResource(R.drawable.ic_indicators_macro);
//            mFocusIndicator.setVisibility(View.VISIBLE);
//        } else {
//            mFocusIndicator.setVisibility(View.GONE);
//        }
    }
    
	// If the activity is paused and resumed, this method will be called in
    // onResume.
    private void initializeSecondTime() {
        // Start orientation listener as soon as possible because it takes
        // some time to get first orientation.
        //mOrientationListener.enable();

        updateOnScreenIndicators();

//        if(mGraphView != null)
//          mGraphView.setCameraObject(Camera.this);

    }
    
    private void keepScreenOnAwhile() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
