/*
 * Copyright (C) 2014 The Android Open Source Project
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
 * limitations under the License
 */

package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSTile;
import com.android.systemui.statusbar.policy.FlashlightController;

/** Quick settings tile: Control flashlight **/
public class FlashlightTile extends QSTile<QSTile.BooleanState> implements
        FlashlightController.FlashlightListener {

    /** Grace period for which we consider the flashlight
     * still available because it was recently on. */
    /// M:Fix [ALPS01838137] Modify timeout to 3000 
    private static final long RECENTLY_ON_DURATION_MILLIS = 3000;
    //private static final long RECENTLY_ON_DURATION_MILLIS = 500;
    private static final String FLASH_LIGHT_STATE = "flash_light_state";
    //A:DWQBBV-358 huangerhui 20151216 
    private static final String FLASH_LIGHT_STATE2 = "flash_light_state2";
    private boolean mFlashlightControlSupport;

    private final AnimationIcon mEnable
            = new AnimationIcon(R.drawable.ic_signal_flashlight_enable_animation);
    private final AnimationIcon mDisable
            = new AnimationIcon(R.drawable.ic_signal_flashlight_disable_animation);
    private final FlashlightController mFlashlightController;
    private long mWasLastOn;
  //A:DWQBBV-358 huangerhui 20151216 start{
    private Uri mUri;
  //A:DWQBBV-358 huangerhui 20151216 end}
    public FlashlightTile(Host host) {
        super(host);
      //A:DWQBBV-358 huangerhui 20151216 start{
        mUri = Settings.System.getUriFor(FLASH_LIGHT_STATE2);
        mContext.getContentResolver().registerContentObserver(mUri, true, mObserver);
      //A:DWQBBV-358 huangerhui 20151216 end}
        mFlashlightController = host.getFlashlightController();
        mFlashlightController.addListener(this);
        mFlashlightControlSupport = mContext.getResources().getBoolean(R.bool.config_flashlight_control); 
    }

    @Override
    protected void handleDestroy() {
        super.handleDestroy();
        mFlashlightController.removeListener(this);
    }

    @Override
    protected BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    public void setListening(boolean listening) {
    }

    @Override
    protected void handleUserSwitch(int newUserId) {
    }

    @Override
    protected void handleClick() {
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        MetricsLogger.action(mContext, getMetricsCategory(), !mState.value);
        boolean newState = !mState.value;
        mFlashlightController.setFlashlight(newState);
        refreshState(newState ? UserBoolean.USER_TRUE : UserBoolean.USER_FALSE);
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        if (state.value) {
            mWasLastOn = SystemClock.uptimeMillis();
        }

        if (arg instanceof UserBoolean) {
            state.value = ((UserBoolean) arg).value;
        }

        if (!state.value && mWasLastOn != 0) {
            if (SystemClock.uptimeMillis() > mWasLastOn + RECENTLY_ON_DURATION_MILLIS) {
                mWasLastOn = 0;
            } else {
                mHandler.removeCallbacks(mRecentlyOnTimeout);
                mHandler.postAtTime(mRecentlyOnTimeout, mWasLastOn + RECENTLY_ON_DURATION_MILLIS);
            }
        }

        // Always show the tile when the flashlight is or was recently on. This is needed because
        // the camera is not available while it is being used for the flashlight.
        state.visible = mWasLastOn != 0 || mFlashlightController.isAvailable();
        state.label = mHost.getContext().getString(R.string.quick_settings_flashlight_label);
        final AnimationIcon icon = state.value ? mEnable : mDisable;
        icon.setAllowAnimation(arg instanceof UserBoolean && ((UserBoolean) arg).userInitiated);
        state.icon = icon;
        int onOrOffId = state.value
                ? R.string.accessibility_quick_settings_flashlight_on
                : R.string.accessibility_quick_settings_flashlight_off;
        state.contentDescription = mContext.getString(onOrOffId);
        if(mFlashlightControlSupport){
            Settings.System.putInt(mContext.getContentResolver(), FLASH_LIGHT_STATE, state.value ? 1 : 0);
        }
    }

    
    @Override
    public void onFlashlightStateChanged(boolean enabled) {
        if(mFlashlightControlSupport){
            refreshState(enabled ? UserBoolean.USER_TRUE : UserBoolean.USER_FALSE);
        }
    };

    @Override
    public int getMetricsCategory() {
        return MetricsLogger.QS_FLASHLIGHT;
    }

    @Override
    protected String composeChangeAnnouncement() {
        if (mState.value) {
            return mContext.getString(R.string.accessibility_quick_settings_flashlight_changed_on);
        } else {
            return mContext.getString(R.string.accessibility_quick_settings_flashlight_changed_off);
        }
    }

    @Override
    public void onFlashlightOff() {
        refreshState(UserBoolean.BACKGROUND_FALSE);
    }

    @Override
    public void onFlashlightError() {
        refreshState(UserBoolean.BACKGROUND_FALSE);
    }

    @Override
    public void onFlashlightAvailabilityChanged(boolean available) {
        refreshState();
    }

    private Runnable mRecentlyOnTimeout = new Runnable() {
        @Override
        public void run() {
            refreshState();
        }
    };
    //A:DWQBBV-358 huangerhui 20151216 start{
    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean selfChange) {
            boolean enabled = isEnabled();
            mFlashlightController.setFlashlight(enabled);
        };
    };
    private boolean isEnabled() {
        return Settings.System.getInt(mContext.getContentResolver(),
                FLASH_LIGHT_STATE2, 0) != 0;
        
    }
    //A:DWQBBV-358 huangerhui 20151216 end}
    
}
