/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
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

package com.android.incallui;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.InCallService;
import com.mediatek.incallui.InCallUtils;
import com.mediatek.incallui.ext.ExtensionManager;

//add DWYBL-2577 songqingming 20151022 (start)
import com.android.incallui.floatwindow.FloatWindowPresenter;
import android.provider.Settings;
//add DWYBL-2577 songqingming 20151022 (end)


/**
 * Used to receive updates about calls from the Telecomm component.  This service is bound to
 * Telecomm while there exist calls which potentially require UI. This includes ringing (incoming),
 * dialing (outgoing), and active calls. When the last call is disconnected, Telecomm will unbind to
 * the service triggering InCallActivity (via CallList) to finish soon after.
 */
public class InCallServiceImpl extends InCallService {

    //add DWYBL-2577 songqingming 20151022 (start)
    private FloatWindowPresenter mFloatWindowPresenter;
    //add DWYBL-2577 songqingming 20151022 (end)

    @Override
    public void onCallAudioStateChanged(CallAudioState audioState) {
        AudioModeProvider.getInstance().onAudioStateChanged(audioState);
    }

    @Override
    public void onBringToForeground(boolean showDialpad) {
        InCallPresenter.getInstance().onBringToForeground(showDialpad);
    }

    @Override
    public void onCallAdded(Call call) {
        ///M: FIXME when in upgrade progress, we can't add another call in CallList. @{
        if (CallList.getInstance().getVideoUpgradeRequestCall() != null
                || CallList.getInstance().getSendingVideoUpgradeRequestCall() != null) {
            call.disconnect();
            InCallUtils.showOutgoingFailMsg(getApplicationContext(), call);
        } else {
            CallList.getInstance().onCallAdded(call);
            InCallPresenter.getInstance().onCallAdded(call);
        }
        ///@}
    }

    /// M: Add phone record interface @{
    @Override
    public void onUpdateRecordState(final int state, final int customValue) {
        CallList.getInstance().onUpdateRecordState(state, customValue);
    }

    @Override
    public void onStorageFull() {
        CallList.getInstance().onStorageFull();
    }
    /// @}

    @Override
    public void onCallRemoved(Call call) {
        CallList.getInstance().onCallRemoved(call);
        InCallPresenter.getInstance().onCallRemoved(call);
    }

    @Override
    public void onCanAddCallChanged(boolean canAddCall) {
        InCallPresenter.getInstance().onCanAddCallChanged(canAddCall);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(this, "onBind");
        final Context context = getApplicationContext();
        /// M: [plugin]ensure a context is valid.
        ExtensionManager.registerApplicationContext(context);
        final ContactInfoCache contactInfoCache = ContactInfoCache.getInstance(context);
        InCallPresenter.getInstance().setUp(
                getApplicationContext(),
                CallList.getInstance(),
                AudioModeProvider.getInstance(),
                new StatusBarNotifier(context, contactInfoCache),
                contactInfoCache,
                new ProximitySensor(context, AudioModeProvider.getInstance())
                );
        InCallPresenter.getInstance().onServiceBind();
        InCallPresenter.getInstance().maybeStartRevealAnimation(intent);
        TelecomAdapter.getInstance().setInCallService(this);

        //add DWYBL-2577 songqingming 20151022 (start)
        if (getApplicationContext().getResources().getBoolean(R.bool.float_window_when_calling_support)
                && getFloatWindowValue() == 1) {
            mFloatWindowPresenter = new FloatWindowPresenter(getApplicationContext());
        }
    	//add DWYBL-2577 songqingming 20151022 (end)
		
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        /// M: optimize process. @{
        /*
         * Google code:
        super.onUnbind(intent);

        InCallPresenter.getInstance().onServiceUnbind();
        tearDown();

        return false;
         */
        Log.d(this, "onUnbind");
        InCallPresenter.getInstance().onServiceUnbind();
        tearDown();
		
        //add DWYBL-2577 songqingming 20151022 (start)
        if (mFloatWindowPresenter != null) {
            mFloatWindowPresenter.destroy();
    	    mFloatWindowPresenter = null;
        }
    	//add DWYBL-2577 songqingming 20151022 (end)

        return super.onUnbind(intent);
        /// @}
    }

    private void tearDown() {
        Log.v(this, "tearDown");
        // Tear down the InCall system
        TelecomAdapter.getInstance().clearInCallService();
        CallList.getInstance().clearOnDisconnect();
        InCallPresenter.getInstance().tearDown();
    }
	
	
    //add DWYBL-2577 songqingming 20151022 (start)
    private int getFloatWindowValue() {
        int value = Settings.System.getInt(getApplicationContext().getContentResolver(),
                Settings.System.FLOAT_WINDOW_WHEN_CALLING, 0);
        android.util.Log.d("sqm", "getFloatWindowValue(): value="+value);
        return value;
    }
    //add DWYBL-2577 songqingming 20151022 (end)
}
