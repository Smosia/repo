/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek Software")
 * have been modified by MediaTek Inc. All revisions are subject to any receiver's
 * applicable license agreements with MediaTek Inc.
 */
package com.mediatek.settings.plugin;

import android.content.Context;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings.System;
import android.util.Log;


import com.mediatek.common.PluginImpl;
import com.mediatek.op01.plugin.R;
import com.mediatek.settings.ext.DefaultDisplaySettingsExt;


/**
 * OP01 plugin implementation of display settings feature.
 */
@PluginImpl(interfaceName = "com.mediatek.settings.ext.IDisplaySettingsExt")
public class Op01DisplaySettingsExt extends DefaultDisplaySettingsExt {
    private static final String TAG = "Op01DisplaySettingsExt";

    private static final String KEY_LOCK_TYPE = "op01_lock_screen_type";
    private static final String LOCK_SCREEN_TYPE = "op01_lock_screen_type";

    private Context mContext;
    static final int LOCK_TYPE_ON = 1;
    static final int LOCK_TYPE_OFF = 0;


    /**
     * Op01DisplaySettingsExt.
     * @param context Context
     */
    public Op01DisplaySettingsExt(Context context) {
        super(context);
        mContext = context;
        mContext.setTheme(R.style.SettingsPluginBase);
        Log.d("@M_" + TAG, "Op01DisplayExt");
    }

    @Override
    public void addPreference(Context context, PreferenceScreen screen) {
        if (SystemProperties.get("ro.mtk_op01_low_power").equals("1")) {
            Log.d("@M_" + TAG, "addDisplayExtView()");
            SwitchPreference lockTypePref = new SwitchPreference(mContext);
            lockTypePref.setTitle(mContext.getString(R.string.lock_screen_type));
            lockTypePref.setKey(KEY_LOCK_TYPE);
            lockTypePref.setOnPreferenceChangeListener(mPreferenceChangeListener);
            screen.addPreference(lockTypePref);
            if (lockTypePref != null) {
                lockTypePref.setChecked(System.getInt(mContext.getContentResolver(),
                        LOCK_SCREEN_TYPE,
                        LOCK_TYPE_ON) == LOCK_TYPE_ON);
            }
        }
    }

    private OnPreferenceChangeListener mPreferenceChangeListener =
            new OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            Log.d("@M_" + TAG, "key=" + key);
            if (KEY_LOCK_TYPE.equals(key)) {
                boolean checked = ((Boolean) newValue).booleanValue();
                Log.d("@M_" + TAG, "checked=" + checked);
                System.putInt(mContext.getContentResolver(),
                    LOCK_SCREEN_TYPE,
                    checked ? LOCK_TYPE_ON : LOCK_TYPE_OFF);
            }
            return true;
        }
    };
}

