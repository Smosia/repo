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

package com.android.settings.deviceinfo;

import android.annotation.Nullable;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import com.android.settings.R;
import android.hardware.usb.UsbManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
/**
 * UI for the USB chooser dialog.
 *
 */
public class UsbModeChooserActivity extends Activity {
	
	//A:DWYQLSSMY-411 shubin 20160411 start
    public static final int[] DEFAULT_MODES_ARCHOS = {
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_NONE,
        //UsbBackend.MODE_POWER_SOURCE | UsbBackend.MODE_DATA_NONE,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MTP,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_PTP,
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MIDI,
        /// M: Add for Built-in CD-ROM and USB Mass Storage @{
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MASS_STORAGE
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_BICR

        /// M: @}
    };
    //A:DWYQLSSMY-411 shubin 20160411 end

    //A:DWYSLM-569 xiangkezhu 20160411 start
    public static final int[] DEFAULT_MODES_QMOBILE = {
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_NONE,
        //UsbBackend.MODE_POWER_SOURCE | UsbBackend.MODE_DATA_NONE,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MTP,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_PTP,
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MIDI,
        /// M: Add for Built-in CD-ROM and USB Mass Storage @{
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MASS_STORAGE
        //UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_BICR

        /// M: @}
    };
    //A:DWYSLM-569 xiangkezhu 20160411 end
    public static final int[] DEFAULT_MODES = {
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_NONE,
        UsbBackend.MODE_POWER_SOURCE | UsbBackend.MODE_DATA_NONE,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MTP,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_PTP,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MIDI,
        /// M: Add for Built-in CD-ROM and USB Mass Storage @{
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MASS_STORAGE,
        UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_BICR
        /// M: @}
    };

    private UsbBackend mBackend;
    private AlertDialog mDialog;
    private LayoutInflater mLayoutInflater;
		
	//A: DELSLMY-232 shubin 20150307 (start)
	private final BroadcastReceiver mStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(UsbManager.ACTION_USB_STATE)) {
               if(!intent.getBooleanExtra(UsbManager.USB_CONNECTED,false)){
					UsbModeChooserActivity.this.finish();
			   }
            }
        }
    };
	//A: DELSLMY-232 shubin 20150307 (end)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mLayoutInflater = LayoutInflater.from(this);

        mDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.usb_use)
                .setView(R.layout.usb_dialog_container)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        mDialog.show();

        LinearLayout container = (LinearLayout) mDialog.findViewById(R.id.container);

        mBackend = new UsbBackend(this);
        int current = mBackend.getCurrentMode();
        //A:DWYSLM-569 xiangkezhu 20160409(start)
        if(getResources().getBoolean(R.bool.config_usb_mode)){
            for (int i = 0; i < DEFAULT_MODES_QMOBILE.length; i++) {
                if (mBackend.isModeSupported(DEFAULT_MODES_QMOBILE[i])) {
                    inflateOption(DEFAULT_MODES_QMOBILE[i], current == DEFAULT_MODES_QMOBILE[i], container);
                }
            }
        }else if(getResources().getBoolean(R.bool.config_usb_mode_archos)){
        	for (int i = 0; i < DEFAULT_MODES_ARCHOS.length; i++) {
                if (mBackend.isModeSupported(DEFAULT_MODES_ARCHOS[i])) {
                    inflateOption(DEFAULT_MODES_ARCHOS[i], current == DEFAULT_MODES_ARCHOS[i], container);
                }
            }
        	
        }else{
           for (int i = 0; i < DEFAULT_MODES.length; i++) {
                if (mBackend.isModeSupported(DEFAULT_MODES[i])) {
                    inflateOption(DEFAULT_MODES[i], current == DEFAULT_MODES[i], container);
                }
            }
        }
        //A:DWYSLM-569 xiangkezhu 20160409(end)
    }
	//A: DELSLMY-232 shubin 20150307 (start)
	@Override
    public void onResume() {
        super.onResume();
        getBaseContext().registerReceiver(mStateReceiver, new IntentFilter(UsbManager.ACTION_USB_STATE));
    }  
	//A: DELSLMY-232 shubin 20150307 (end)
    private void inflateOption(final int mode, boolean selected, LinearLayout container) {
        View v = mLayoutInflater.inflate(R.layout.radio_with_summary, container, false);

        ((TextView) v.findViewById(android.R.id.title)).setText(getTitle(mode));
        ((TextView) v.findViewById(android.R.id.summary)).setText(getSummary(mode));

        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ActivityManager.isUserAMonkey()) {
                    mBackend.setMode(mode);
                }
                mDialog.dismiss();
                finish();
            }
        });
        ((Checkable) v).setChecked(selected);
        container.addView(v);
    }

    private static int getSummary(int mode) {
        switch (mode) {
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_NONE:
                return R.string.usb_use_charging_only_desc;
            case UsbBackend.MODE_POWER_SOURCE | UsbBackend.MODE_DATA_NONE:
                return R.string.usb_use_power_only_desc;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MTP:
                return R.string.usb_use_file_transfers_desc;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_PTP:
                return R.string.usb_use_photo_transfers_desc;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MIDI:
                return R.string.usb_use_MIDI_desc;
            /// M: Add for Built-in CD-ROM and USB Mass Storage @{
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MASS_STORAGE:
                return R.string.usb_ums_summary;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_BICR:
                return R.string.usb_bicr_summary;
            /// M: @}
        }
        return 0;
    }

    private static int getTitle(int mode) {
        switch (mode) {
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_NONE:
                return R.string.usb_use_charging_only;
            case UsbBackend.MODE_POWER_SOURCE | UsbBackend.MODE_DATA_NONE:
                return R.string.usb_use_power_only;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MTP:
                return R.string.usb_use_file_transfers;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_PTP:
                return R.string.usb_use_photo_transfers;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MIDI:
                return R.string.usb_use_MIDI;
            /// M: Add for Built-in CD-ROM and USB Mass Storage @{
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_MASS_STORAGE:
                return R.string.usb_use_mass_storage;
            case UsbBackend.MODE_POWER_SINK | UsbBackend.MODE_DATA_BICR:
                return R.string.usb_use_built_in_cd_rom;
            /// M: @}
        }
        return 0;
    }
}
