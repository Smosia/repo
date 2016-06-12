/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.android.systemui.statusbar.policy;

import android.telephony.SubscriptionInfo;

import com.android.systemui.statusbar.policy.NetworkController.IconState;
import com.android.systemui.statusbar.policy.NetworkController.SignalCallback;
import com.mediatek.systemui.statusbar.defaultaccount.DefaultAccountStatus;

import java.util.List;


/**
 * Provides empty implementations of SignalCallback for those that only want some of
 * the callbacks.
 */
public class SignalCallbackAdapter implements SignalCallback {

    @Override
    public void setWifiIndicators(boolean enabled, IconState statusIcon, IconState qsIcon,
            boolean activityIn, boolean activityOut, String description) {
    }
    /** M: Support[Network Type on StatusBar].
     * Add one more parameter networkIcon to signal view and show the network type beside
     * the signal. */
    @Override
    public void setMobileDataIndicators(IconState statusIcon, IconState qsIcon, int statusType,
            int networkIcon, int qsType, boolean activityIn, boolean activityOut,
            /// M: Add for CT6M. add activity icon @{
            int dataActivity,
            int primarySimIcon,
            /// @}
            String typeContentDescription, String description, boolean isWide, int subId) {
    }

    @Override
    public void setSubs(List<SubscriptionInfo> subs) {
    }

    @Override
    public void setNoSims(boolean show) {
    }

    @Override
    public void setEthernetIndicators(IconState icon) {
    }

    @Override
    public void setIsAirplaneMode(IconState icon) {
    }

    @Override
    public void setMobileDataEnabled(boolean enabled) {
    }

    @Override
    public void setDefaultAccountStatus(DefaultAccountStatus status) {
    }
    @Override
    public void setVolteStatusIcon(int iconId) {
    }

}