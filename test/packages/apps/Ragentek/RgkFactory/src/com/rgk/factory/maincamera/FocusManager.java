/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.rgk.factory.maincamera;

import android.graphics.Point;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera.Area;
import android.hardware.Camera.Parameters;
import android.media.MediaActionSound;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.rgk.factory.R;

public class FocusManager implements MainCamera.OnOrientationListener,
		MainCamera.OnParametersReadyListener {
    private static final String TAG = "FocusManager";

    private static final int RESET_TOUCH_FOCUS = 0;
    private static final int RESET_TOUCH_FOCUS_DELAY = 3000;

    private int mState = STATE_UNKNOWN;
    private static final int STATE_UNKNOWN = -1;
    private static final int STATE_IDLE = 0; // Focus is not active.
    private static final int STATE_FOCUSING = 1; // Focus is in progress.
    // Focus is in progress and the camera should take a picture after focus finishes.
    private static final int STATE_FOCUSING_SNAP_ON_FINISH = 2;
    private static final int STATE_SUCCESS = 3; // Focus finishes and succeeds.
    private static final int STATE_FAIL = 4; // Focus finishes and fails.

    private boolean mInitialized;
    private boolean mFocusAreaSupported;
    private boolean mLockAeNeeded = true;
    private boolean mAeLock;
    private boolean mAwbLock;
    private Matrix mMatrix;
    private Matrix mObjextMatrix;

    // The parent layout that includes only the focus indicator.
    private FocusIndicatorRotateLayout mFocusIndicatorRotateLayout;
    // The focus indicator view that holds the image resource.
    private View mFocusIndicator;
    private int mPreviewWidth; // The width of the preview frame layout.
    private int mPreviewHeight; // The height of the preview frame layout.
    private boolean mMirror; // true if the camera is front-facing.
    private int mDisplayOrientation;
    private List<Area> mFocusArea; // focus area in driver format
    private List<Area> mMeteringArea; // metering area in driver format
    private String mFocusMode;
    private String[] mDefaultFocusModes;
    private String mOverrideFocusMode;
    private Parameters mParameters;
    private Handler mHandler;
    Listener mListener;
    //private  boolean mIsVideoFocusMode = false;
    // M: used as a flag to playsound at the first focus time
    private boolean mFirstFocusDone = false;
    private static boolean sNeedReset = false;

    private static final int FOCUS_FRAME_DELAY = 1000;
    
    private class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
        	LogUtils.logV(TAG, "handleMessage " + msg.what);
            switch (msg.what) {
            case RESET_TOUCH_FOCUS:
                cancelAutoFocus();
                break;
            default:
                break;
            }
        }
    }

    public void setFocusAreaIndicator(View view) {
    	LogUtils.logV(TAG, "setFocusAreaIndicator ");
        mFocusIndicatorRotateLayout = (FocusIndicatorRotateLayout) view;
        mFocusIndicator = view.findViewById(R.id.focus_indicator);

        // Put focus indicator to the center.
        RelativeLayout.LayoutParams p =
                (RelativeLayout.LayoutParams) mFocusIndicatorRotateLayout.getLayoutParams();
        int[] rules = p.getRules();
        rules[RelativeLayout.CENTER_IN_PARENT] = RelativeLayout.TRUE;
    }

//    public FocusManager(String[] defaultFocusModes,
//            View focusIndicatorRotate, Parameters parameters, Listener listener,
//            boolean mirror, Looper looper) {
//        mHandler = new MainHandler(looper);
//        mMatrix = new Matrix();
//        mObjextMatrix = new Matrix();
//
//        mDefaultFocusModes = defaultFocusModes;
//        setFocusAreaIndicator(focusIndicatorRotate);
//        setParameters(parameters);
//        mListener = listener;
//        setMirror(mirror);
//
//        if (sNeedReset) {
//            mHandler.sendEmptyMessage(RESET_TOUCH_FOCUS);
//            sNeedReset = false;
//        }
//    }
    
    public FocusManager(MainCamera context, String[] defaultFocusModes,
            View focusIndicatorRotate, Parameters parameters, 
            Listener listener, boolean mirror, Looper looper, 
            String continous) {
    	LogUtils.logV(TAG, "new FocusManager ");
     mContext = context;
     mHandler = new MainHandler(looper);
     mMatrix = new Matrix();
     mObjextMatrix = new Matrix();

     mDefaultFocusModes = defaultFocusModes;
     //Here we modify current mode continous focus mode.
     mContinousFocusMode = continous;
     mListener = listener;
     setFocusAreaIndicator(focusIndicatorRotate);
     setParameters(parameters);
     setMirror(mirror);
     
     mContext.addOnOrientationListener(this);
     mContext.addOnParametersReadyListener(this);
     if (sNeedReset) {
         mHandler.sendEmptyMessage(RESET_TOUCH_FOCUS);
         sNeedReset = false;
     }
     LogUtils.logD(TAG, "FocusManager(" + continous + ")");
     if (defaultFocusModes != null) {
         for (int i = 0, len = defaultFocusModes.length; i < len; i++) {
             //LogUtils.logD(TAG, "FocusManager() defaultFocusModes[" + i + "]=" + defaultFocusModes[i]);
         }
     }
 }

    public void setParameters(Parameters parameters) {
    	LogUtils.logV(TAG, "setParameters");
        mParameters = parameters;
		if (null == mParameters)
			return;
		mFocusAreaSupported = (mParameters.getMaxNumFocusAreas() > 0 && isSupported(
				Parameters.FOCUS_MODE_AUTO,
                        mParameters.getSupportedFocusModes()));
        //mLockAeAwbNeeded = (mInitialParameters.isAutoExposureLockSupported() ||
        //        mInitialParameters.isAutoWhiteBalanceLockSupported());

        mMeteringAreaSupported = (mParameters.getMaxNumMeteringAreas() > 0);
        mAeLockSupported = mParameters.isAutoExposureLockSupported();
        mAwbLockSupported = mParameters.isAutoWhiteBalanceLockSupported();
        mContinousFocusSupported = mParameters.getSupportedFocusModes()
                .contains(mContinousFocusMode);
        LogUtils.logD(TAG, "setParameters() mFocusAreaSupported[" + mFocusAreaSupported);
        LogUtils.logD(TAG, "setParameters() mMeteringAreaSupported[" + mMeteringAreaSupported);
        LogUtils.logD(TAG, "setParameters() mAeLockSupported[" + mAeLockSupported);
        LogUtils.logD(TAG, "setParameters() mAwbLockSupported[" + mAwbLockSupported);
        LogUtils.logD(TAG, "setParameters() mContinousFocusSupported[" + mContinousFocusSupported);
    }

    public void setPreviewSize(int previewWidth, int previewHeight) {
    	LogUtils.logV(TAG, "setPreviewSize");
        if (mPreviewWidth != previewWidth || mPreviewHeight != previewHeight) {
            mPreviewWidth = previewWidth;
            mPreviewHeight = previewHeight;
            setMatrix();

            // Set the length of focus indicator according to preview frame size.
            int len = Math.min(mPreviewWidth, mPreviewHeight) / 4;
            ViewGroup.LayoutParams layout = mFocusIndicator.getLayoutParams();
            layout.width = len;
            layout.height = len;
        }
    }

    public void setMirror(boolean mirror) {
    	LogUtils.logV(TAG, "setMirror");
        mMirror = mirror;
        setMatrix();
    }

    public void setDisplayOrientation(int displayOrientation) {
    	LogUtils.logV(TAG, "setDisplayOrientation");
        mDisplayOrientation = displayOrientation;
        setMatrix();
    }
    
    private void setMatrix() {
    	LogUtils.logV(TAG, "setMatrix");
        if (mPreviewWidth != 0 && mPreviewHeight != 0) {
            Matrix matrix = new Matrix();
            Util.prepareMatrix(matrix, mMirror, mDisplayOrientation,
                    mPreviewWidth, mPreviewHeight);
            // In face detection, the matrix converts the driver coordinates to UI
            // coordinates. In tap focus, the inverted matrix converts the UI
            // coordinates to driver coordinates.
            matrix.invert(mMatrix);

            Matrix objectMatrix = new Matrix();
            Util.prepareMatrix(objectMatrix, false, mDisplayOrientation,
                    mPreviewWidth, mPreviewHeight);
            // In Object track, the inverted matrix converts the UI
            // coordinates to driver coordinates.
            objectMatrix.invert(mObjextMatrix);
            mInitialized = true;
        }
    }

    public void onAutoFocus(boolean focused) {
        LogUtils.logD(TAG, "onAutoFocus focused=" + focused + " mState=" + mState + " mFocusMode=" + mFocusMode);
        if (mState == STATE_FOCUSING_SNAP_ON_FINISH) {
            // Take the picture no matter focus succeeds or fails. No need
            // to play the AF sound if we're about to play the shutter
            // sound.
            if (focused) {
                mState = STATE_SUCCESS;
            } else {
                mState = STATE_FAIL;
            }
            updateFocusUI();
        } else if (mState == STATE_FOCUSING) {
            // This happens when (1) user is half-pressing the focus key or
            // (2) touch focus is triggered. Play the focus tone. Do not
            // take the picture now.
            if (focused) {
                mState = STATE_SUCCESS;
                // Do not play the sound in continuous autofocus mode. It does
                // not do a full scan. The focus callback arrives before doSnap
                // so the state is always STATE_FOCUSING.
                if (!Parameters.FOCUS_MODE_CONTINUOUS_PICTURE.
                        equals(mFocusMode) || !mFirstFocusDone) {
                    mListener.playSound(MediaActionSound.FOCUS_COMPLETE);
                }
            } else {
                mState = STATE_FAIL;
            }
            updateFocusUI();
            // If this is triggered by touch focus, cancel focus after a
            // while.
            mHandler.sendEmptyMessageDelayed(RESET_TOUCH_FOCUS, FOCUS_FRAME_DELAY);
        } else if (mState == STATE_IDLE) {
            // User has released the focus key before focus completes.
            // Do nothing.
        }
        // M:after first time call back,set true
        mFirstFocusDone = true;
    }

    public void onAutoFocusMoving(boolean moving) {
        LogUtils.logD(TAG, "onAutoFocusMoving = " + moving + " mState " + mState);

        // Ignore if we have requested autofocus. This method only handles
        // continuous autofocus.
        if (mState != STATE_IDLE && mState != STATE_UNKNOWN) { //0 -1
            LogUtils.logD(TAG, "onAutoFocusMoving mState != 0 && mState != -1 return");
            return; 
        }//0 or -1

        //if current focus mode is INFINISTY, then don't show AF box
        if (getCurrentFocusMode().equals(Parameters.FOCUS_MODE_INFINITY)) {
            LogUtils.logD(TAG, "onAutoFocusMoving FOCUS_MODE_INFINITY return");
            return;
        }
        
        if (moving) {
            mFocusIndicatorRotateLayout.showStart();
        } else {
            mFocusIndicatorRotateLayout.showSuccess(true);
        }
    }

    public void onSingleTapUp(int x, int y) {
        LogUtils.logD(TAG,"onSingleTapUp x = " + x + " y = " + y);
        if (!mInitialized || mState == STATE_FOCUSING_SNAP_ON_FINISH || mState == STATE_UNKNOWN) {
            LogUtils.logD(TAG, "!mInitialized || mState == 2 || mState == -1 return");
            return; 
            
        }

        // Let users be able to cancel previous touch focus.
        if ((mFocusArea != null) && (mState == STATE_FOCUSING ||
                    mState == STATE_SUCCESS || mState == STATE_FAIL)) {
            cancelAutoFocus();
        }

        // Initialize variables.
        int focusWidth = mFocusIndicatorRotateLayout.getWidth();
        int focusHeight = mFocusIndicatorRotateLayout.getHeight();
        LogUtils.logD(TAG, "focusWidth " + focusWidth + " focusHeight " + focusHeight);
        if (focusWidth == 0 || focusHeight == 0) {
            LogUtils.logD(TAG, "UI Component not initialized, cancel this touch");
            return;
        }
        int previewWidth = mPreviewWidth;
        int previewHeight = mPreviewHeight;
        if (mFocusArea == null) {
            mFocusArea = new ArrayList<Area>();
            mFocusArea.add(new Area(new Rect(), 1));
            mMeteringArea = new ArrayList<Area>();
            mMeteringArea.add(new Area(new Rect(), 1));
        }

        // Convert the coordinates to driver format.
        // AE area is bigger because exposure is sensitive and
        // easy to over- or underexposure if area is too small.
        calculateTapArea(focusWidth, focusHeight, 1f, x, y, previewWidth, previewHeight,
                mFocusArea.get(0).rect);
        calculateTapArea(focusWidth, focusHeight, 1.5f, x, y, previewWidth, previewHeight,
                mMeteringArea.get(0).rect);

        // Use margin to set the focus indicator to the touched area.
        RelativeLayout.LayoutParams p =
                (RelativeLayout.LayoutParams) mFocusIndicatorRotateLayout.getLayoutParams();
        int left = Util.clamp(x - focusWidth / 2, 0, previewWidth - focusWidth);
        int top = Util.clamp(y - focusHeight / 2, 0, previewHeight - focusHeight);
        if (p.getLayoutDirection() != View.LAYOUT_DIRECTION_RTL) {
            p.setMargins(left, top, 0, 0);
        } else {
            // since in RTL language, framework will use marginRight as standard.
            int right = previewWidth - (left + focusWidth);
            p.setMargins(0, top, right, 0);
        }
        
        // Disable "center" rule because we no longer want to put it in the center.
        int[] rules = p.getRules();
        rules[RelativeLayout.CENTER_IN_PARENT] = 0;
        mFocusIndicatorRotateLayout.requestLayout();

        // Stop face detection because we want to specify focus and metering area.

        // Set the focus area and metering area.
        mContext.setFocusParameters();
        if (mFocusAreaSupported) {
            autoFocus();
        } else {  // Just show the indicator in all other cases.
            updateFocusUI();
            // Reset the metering area in 3 seconds.
            mHandler.removeMessages(RESET_TOUCH_FOCUS);
            mHandler.sendEmptyMessageDelayed(RESET_TOUCH_FOCUS, RESET_TOUCH_FOCUS_DELAY);
        }
        // M:
        mFirstFocusDone = false;
    }

    public void onPreviewStarted() {
        LogUtils.logD(TAG, "onPreviewStarted ");
        mState = STATE_IDLE;
    }

    public void onPreviewStopped() {
        LogUtils.logD(TAG, "onPreviewStopped ");
        mState = STATE_UNKNOWN;
        resetTouchFocus();
        // If auto focus was in progress, it would have been canceled.
        updateFocusUI();
    }

    public void onCameraReleased() {
        LogUtils.logD(TAG, "onCameraReleased ");
        onPreviewStopped();
    }

    private void autoFocus() {
        LogUtils.logD(TAG, "Start autofocus.");
        mListener.autoFocus();
        mState = STATE_FOCUSING;
        updateFocusUI();
        mHandler.removeMessages(RESET_TOUCH_FOCUS);
    }

    private void cancelAutoFocus() {
        LogUtils.logD(TAG, "Cancel autofocus.");
        // Reset the tap area before calling mListener.cancelAutofocus.
        // Otherwise, focus mode stays at auto and the tap area passed to the
        // driver is not reset.
        resetTouchFocus();
        mListener.cancelAutoFocus();
        mState = STATE_IDLE;
        updateFocusUI();
        mHandler.removeMessages(RESET_TOUCH_FOCUS);
    }

    public String getFocusMode() {
        LogUtils.logD(TAG, "getFocusMode() mOverrideFocusMode=" + mOverrideFocusMode
                + " mFocusArea=" + mFocusArea
                + " mFocusAreaSupported=" + mFocusAreaSupported);
        if (mOverrideFocusMode != null) {
            return mOverrideFocusMode;
        }
	  if(mParameters == null) return "infinity";
        List<String> supportedFocusModes = mParameters.getSupportedFocusModes();

        if (mFocusAreaSupported && mFocusArea != null) {
            // Always use autofocus in tap-to-focus.
            mFocusMode = Parameters.FOCUS_MODE_AUTO;
        } else {
            // The default is continuous autofocus.
            mFocusMode = mContinousFocusMode;
            
            // Try to find a supported focus mode from the default list.
            if (mFocusMode == null) {
                for (int i = 0; i < mDefaultFocusModes.length; i++) {
                    String mode = mDefaultFocusModes[i];
                    if (isSupported(mode, supportedFocusModes)) {
                        mFocusMode = mode;
                        break;
                    }
                }
            }
        }
        if (!isSupported(mFocusMode, supportedFocusModes)) {
            // For some reasons, the driver does not support the current
            // focus mode. Fall back to auto.
            if (isSupported(Parameters.FOCUS_MODE_AUTO,
                    mParameters.getSupportedFocusModes())) {
                mFocusMode = Parameters.FOCUS_MODE_AUTO;
            } else {
                mFocusMode = mParameters.getFocusMode();
            }
        }

        LogUtils.logD(TAG, "getFocusMode() return " + mFocusMode);
        return mFocusMode;
    }

    public List<Area> getFocusAreas() {
        return mFocusArea;
    }

    public List<Area> getMeteringAreas() {
        LogUtils.logD(TAG, "getMeteringAreas() " );
        return mMeteringArea;
    }

    public void updateFocusUI() {
        LogUtils.logD(TAG, "updateFocusUI() return " + mFocusMode + " mInitialized " + mInitialized);
        if (!mInitialized) { return; }

        // Show only focus indicator or face indicator.
        FocusIndicator focusIndicator = mFocusIndicatorRotateLayout;
        LogUtils.logD(TAG, "updateFocusUI, mState = " + mState
                + " mFocusArea = " + mFocusArea + " focusIndicator = " + focusIndicator);
        if (mState == STATE_IDLE || mState == STATE_UNKNOWN) {
            if (mFocusArea == null) {
                focusIndicator.clear();
            } else {
                // Users touch on the preview and the indicator represents the
                // metering area. Either focus area is not supported or
                // autoFocus call is not required.
                focusIndicator.showStart();
            }
        } else if (mState == STATE_FOCUSING || mState == STATE_FOCUSING_SNAP_ON_FINISH) {
            focusIndicator.showStart();
        } else {
            String focusMode = mOverrideFocusMode == null ? mFocusMode : mOverrideFocusMode;
            LogUtils.logD(TAG, "updateFocusUI() focusMode " + focusMode + " mOverrideFocusMode " + mOverrideFocusMode);
            if (mState == STATE_SUCCESS) {
                focusIndicator.showSuccess(false);
            } else if (mState == STATE_FAIL) {
                focusIndicator.showFail(false);
            }
        }
    }

    public void resetTouchFocus() {
        LogUtils.logD(TAG, "resetTouchFocus mInitialized = " + mInitialized);
        if (!mInitialized) { return; }

        // Put focus indicator to the center.
        RelativeLayout.LayoutParams p =
                (RelativeLayout.LayoutParams) mFocusIndicatorRotateLayout.getLayoutParams();
        int[] rules = p.getRules();
        rules[RelativeLayout.CENTER_IN_PARENT] = RelativeLayout.TRUE;
        p.setMargins(0, 0, 0, 0);
        mFocusIndicatorRotateLayout.clear();

        mFocusArea = null;
        mMeteringArea = null;
    }

    public void calculateTapArea(int focusWidth, int focusHeight, float areaMultiple,
            int x, int y, int previewWidth, int previewHeight, Rect rect) {
        int areaWidth = (int) (focusWidth * areaMultiple);
        int areaHeight = (int) (focusHeight * areaMultiple);
        int left = Util.clamp(x - areaWidth / 2, 0, previewWidth - areaWidth);
        int top = Util.clamp(y - areaHeight / 2, 0, previewHeight - areaHeight);

        RectF rectF = new RectF(left, top, left + areaWidth, top + areaHeight);
        mMatrix.mapRect(rectF);
        Util.rectFToRect(rectF, rect);
    }
    
    public int[] calculateTapPoint(int x, int y) {
        float[] pts = new float[2];
        pts[0] = (float) x;
        pts[1] = (float) y;
        mObjextMatrix.mapPoints(pts);
        return Util.pointFToPoint(pts);
    }

    public boolean isFocusCompleted() {
        LogUtils.logD(TAG, "isFocusCompleted" + mState);
        return mState == STATE_SUCCESS || mState == STATE_FAIL;
    }

    public boolean isFocusingSnapOnFinish() {
        LogUtils.logD(TAG, "isFocusingSnapOnFinish" + mState);
        return mState == STATE_FOCUSING_SNAP_ON_FINISH;
    }

    public void removeMessages() {
        LogUtils.logD(TAG, "removeMessages" );
        if (mHandler.hasMessages(RESET_TOUCH_FOCUS)) {
            sNeedReset = true;
            mHandler.removeMessages(RESET_TOUCH_FOCUS);
            LogUtils.logD(TAG, "removeMessages, we resend it next time");
        }
    }

    public void overrideFocusMode(String focusMode) {
        LogUtils.logD(TAG, "overrideFocusMode" + focusMode);
        mOverrideFocusMode = focusMode;
    }

    public void setAwbLock(boolean lock) {
        LogUtils.logD(TAG, "setAwbLock");
        mAwbLock = lock;
    }

    public void setLockAeNeeded(boolean neededLock) {
        LogUtils.logD(TAG, "setLockAeNeeded");
        mLockAeNeeded = neededLock;
    }

    public void setAeLock(boolean lock) {
        if (mLockAeNeeded) {
            mAeLock = lock;
        } else {
            mAeLock = false;
        }
    }

    public boolean getAwbLock() {
        return mAwbLock;
    }

    public boolean getAeLock() {
        return mAeLock;
    }

    private static boolean isSupported(String value, List<String> supported) {
        return supported == null ? false : supported.indexOf(value) >= 0;
    }
    
    // M:
    public boolean isCameraIdle() {
        LogUtils.logD(TAG, "isCameraIdle");
        return mState == STATE_IDLE;
    }

    public void clearFocusOnContinuous() {
        mFocusIndicatorRotateLayout.clear();
    }
    
    /// M: add for focus other capability
    //Note: google default setParameters() will be filled by initial parameters.
    private boolean mMeteringAreaSupported;
    private boolean mAeLockSupported;
    private boolean mAwbLockSupported;
    private boolean mContinousFocusSupported;
    private String mContinousFocusMode;
    
    private MainCamera mContext;
    private int mOrientation;
    
    public boolean getAeLockSupported() {
        return mAeLockSupported;
    }
    public boolean getAwbLockSupported() {
        return mAwbLockSupported;
    }
    public boolean getFocusAreaSupported() {
        return mFocusAreaSupported;
    }
    public boolean getMeteringAreaSupported() {
        return mMeteringAreaSupported;
    }
    public boolean getContinousFocusSupported() {
        return mContinousFocusSupported;
    }
    public String getCurrentFocusMode() {
    	try {
    		return mContext.getCameraDevice().getParameters().getFocusMode();
    	} catch(NullPointerException n){
    		return null;
    	}
    }
    
	@Override
    public void onOrientationChanged(int orientation) {
        if (mOrientation != orientation && mFocusIndicator != null) {
            mOrientation = orientation;
            mFocusIndicatorRotateLayout.setOrientation(mOrientation, true);
        }
    }
    
    @Override
    public void onCameraParameterReady() {
        if (mState == STATE_UNKNOWN) {
            mState = STATE_IDLE;
        }
    }
    public void release() {
        mContext.removeOnOrientationListener(this);
        mContext.removeOnParametersReadyListener(this);
    }
    
    public FocusIndicatorRotateLayout getFocusLayout() {
        LogUtils.logD(TAG, "getFocusLayout");
        return mFocusIndicatorRotateLayout;
    }
}
