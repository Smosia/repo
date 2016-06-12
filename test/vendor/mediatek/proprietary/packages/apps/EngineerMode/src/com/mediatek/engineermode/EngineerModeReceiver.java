/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 */
/* MediaTek Inc. (C) 2010. All rights reserved.
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

package com.mediatek.engineermode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.mediatek.engineermode.softwareversion.SoftwareVersion;
import com.mediatek.engineermode.softwareversion.RealSoftwareVersion;
import com.mediatek.engineermode.hardwareversion.HardwareVersionInfo;
import com.mediatek.engineermode.hardwareversion.PCBAVersion;


public final class EngineerModeReceiver extends BroadcastReceiver {

    private static final String TAG = "EM/SECRET_CODE";
    private static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
    // process *#*#3646633#*#*
    private final Uri mEmUri = Uri.parse("android_secret_code://3646633");
	private final Uri mEmUri2 = Uri.parse("android_secret_code://9646633");
	private final Uri mSoftUri = Uri.parse("android_secret_code://9375");
	private final Uri mRealSoftUri = Uri.parse("android_secret_code://9875");
	private final Uri mOtherHardwareInfoUri = Uri.parse("android_secret_code://888");
	private final Uri mPCBAVersionUri = Uri.parse("android_secret_code://999");

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null) {
            Log.i("@M_" + TAG, "Null action");
            return;
        }
        if (intent.getAction().equals(SECRET_CODE_ACTION)) {
            Uri uri = intent.getData();
            Log.i("@M_" + TAG, "getIntent success in if");
			String custom = context.getResources().getString(R.string.custom);
            if (uri.equals(mEmUri)|| uri.equals(mEmUri2)) {
                Intent intentEm = new Intent(context, EngineerMode.class);
                intentEm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i("@M_" + TAG, "Before start EM activity");
                context.startActivity(intentEm);
				} else if(uri.equals(mSoftUri)){
				Intent intentSo = new Intent(context, SoftwareVersion.class);
                intentSo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.i(TAG, "Before start EM activity");
                context.startActivity(intentSo);
	    	} else if(uri.equals(mRealSoftUri)){
				Intent intentRealSo = new Intent(context, RealSoftwareVersion.class);
                intentRealSo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentRealSo.putExtra("host", intent.getStringExtra("host"));
                Log.i(TAG, "Before start EM activity");
                context.startActivity(intentRealSo);
	  		} else if(custom.equals("blu")){
				if (uri.equals(mOtherHardwareInfoUri)) {
					Intent intentOtHardInfo = new Intent(context, HardwareVersionInfo.class);
					intentOtHardInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Log.i(TAG, "Before start EM activity");
					context.startActivity(intentOtHardInfo);
				} else if (uri.equals(mPCBAVersionUri)) {
					Intent intentPCBAInfo = new Intent(context, PCBAVersion.class);
					intentPCBAInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Log.i(TAG, "Before start EM activity");
					context.startActivity(intentPCBAInfo);
				}
            } else {
                Log.i("@M_" + TAG, "No matched URI!");
            }
        } else {
            Log.i("@M_" + TAG, "Not SECRET_CODE_ACTION!");
        }
    }
}
