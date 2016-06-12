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

package com.rgk.factory.maincamera;

import android.content.Context;
import android.graphics.Region;
import android.util.AttributeSet;

import android.util.Log;

import com.rgk.factory.R;

// A view that indicates the focus area or the metering area.
public class FocusIndicatorRotateLayout extends RotateLayout implements FocusIndicator {
    private static final String TAG = "FocusIndicatorRotateLayout";
    // Sometimes continuous autofucus starts and stops several times quickly.
    // These states are used to make sure the animation is run for at least some
    // time.
    private int mState;
    private static final int STATE_IDLE = 0;
    private static final int STATE_FOCUSING = 1;
    private static final int STATE_FINISHING = 2;

    private Runnable mDisappear = new Disappear();
    private Runnable mEndAction = new EndAction();
    private static final int SCALING_UP_TIME = 1000;
    private static final int SCALING_DOWN_TIME = 200;
    private static final int DISAPPEAR_TIMEOUT = 200;

    public FocusIndicatorRotateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void setDrawable(int resid) {
        mChild.setBackgroundDrawable(getResources().getDrawable(resid));
    }

    @Override
    public void showStart() {
        LogUtils.logD(TAG, "showStart mState = " + mState);
        if (mState == STATE_IDLE) {//0
            setDrawable(R.drawable.ic_focus_focusing);
            animate().withLayer().setDuration(SCALING_UP_TIME)
                    .scaleX(1.5f).scaleY(1.5f);
            mState = STATE_FOCUSING;//1
        }
    }

    @Override
    public void showSuccess(boolean timeout) {
    	LogUtils.logD(TAG, "showSuccess mState = " + mState);
        if (mState == STATE_FOCUSING) {//1
            setDrawable(R.drawable.ic_focus_focused);
            animate().withLayer().setDuration(SCALING_DOWN_TIME).scaleX(1f)
                    .scaleY(1f).withEndAction(timeout ? mEndAction : null);
            mState = STATE_FINISHING;//2
        }
    }

    @Override
    public void showFail(boolean timeout) {
    	LogUtils.logD(TAG, "showFail mState = " + mState);
        if (mState == STATE_FOCUSING) {//1
            setDrawable(R.drawable.ic_focus_failed);
            animate().withLayer().setDuration(SCALING_DOWN_TIME).scaleX(1f)
                    .scaleY(1f).withEndAction(timeout ? mEndAction : null);
            mState = STATE_FINISHING;
        }
    }

    @Override
    public void clear() {
    	LogUtils.logD(TAG, "clear mState = " + mState);
        animate().cancel();
        removeCallbacks(mDisappear);
        mDisappear.run();
        setScaleX(1f);
        setScaleY(1f);
    }

    private class EndAction implements Runnable {
        @Override
        public void run() {
            // Keep the focus indicator for some time.
        	LogUtils.logD(TAG, "EndAction mState = " + mState);
            postDelayed(mDisappear, DISAPPEAR_TIMEOUT);
        }
    }

    private class Disappear implements Runnable {
        @Override
        public void run() {
            mChild.setBackgroundDrawable(null);
            mState = STATE_IDLE;
            LogUtils.logD(TAG, "Disappear run mState = " + mState);
        }
    }

    public boolean isFocusing() {
        return mState != STATE_IDLE;
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        LogUtils.logE(TAG, "gatherTransparentRegion = " + region);
        if (region !=null) {
            final int[] location = new int[2];
            int width = getWidth();
            int height = getHeight();
            getLocationInWindow(location);
            int l = location[0] + width / 2 - width;
            int t = location[1] + height / 2 - height;
            int r = l + width * 2;
            int b = t + height * 2;
            region.op(l, t, r, b, Region.Op.DIFFERENCE);
        }
        return true;
    }
}
