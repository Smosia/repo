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
 * limitations under the License.
 */

package com.mediatek.systemui.statusbar.util;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;

import com.android.systemui.R;
import com.android.systemui.statusbar.policy.NetworkController.MobileDataController;

//add  BUG_ID: DWYBL-1141   zhangyuwen  20150422(start)

public class SimDataConnectionDialog {
    private static String TAG = "SimDataConnectionDialog";
    private Context mContext;
    private Dialog mDataConnectionDialog;
    private TelephonyManager mTelephonyManager;
    private IntentFilter mIntentFilter;
    private List<SimInfo> mSimInfoList;
    private MobileDataController mController;

    public SimDataConnectionDialog(Context context) {
        mContext = context;
        init(context);
    }

    public void setMobileDataController(MobileDataController controller) {
        mController = controller;
    }

    private void init(Context context) {
        mTelephonyManager = TelephonyManager.from(context);
        mIntentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        context.registerReceiver(mSubReceiver, mIntentFilter);
        mSimInfoList = new ArrayList<SimInfo>();
    }

    public List<SubscriptionInfo> getSubInfoList() {
        SubscriptionManager subscriptionManager = SubscriptionManager
                .from(mContext);
        List<SubscriptionInfo> subInfoList = subscriptionManager
                .getActiveSubscriptionInfoList();
        return subInfoList;
    }

    private void updateSimInfoList(Context context) {
        mSimInfoList.clear();
        final SubscriptionManager subscriptionManager = SubscriptionManager
                .from(context);
        final List<SubscriptionInfo> subInfoList = subscriptionManager
                .getActiveSubscriptionInfoList();
        if (subInfoList == null)
            return;
        for (int i = 0; i < subInfoList.size(); i++) {
            SubscriptionInfo sir = subInfoList.get(i);
            SimInfo simInfo = new SimInfo(sir.getSubscriptionId(), sir
                    .getDisplayName().toString(), sir.getNumber(),
                    sir.createIconBitmap(mContext));
            mSimInfoList.add(simInfo);
        }
		//M:BUG_ID:DWLJE-870 shasha.fang 20150921(start)
        SimInfo closeSimInfo = new SimInfo(
                SubscriptionManager.INVALID_SUBSCRIPTION_ID, context.getResources().getString(R.string.data_closed), "",  BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_data_connection));
        //M:BUG_ID:DWLJE-870 shasha.fang 20150921(end)
		mSimInfoList.add(closeSimInfo);
        Log.d(TAG, " [updateSimInfoList ] = " + mSimInfoList);
		
    }
    public void dismiss() {
        if (mDataConnectionDialog != null) {
            mDataConnectionDialog.dismiss();
        }
    }

    private void setDefaultDataSubId(final Context context, final int subId) {
        if (TelecomManager.from(context).isInCall()) {
            Toast.makeText(context, R.string.default_data_switch_err_msg1,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final SubscriptionManager subscriptionManager = SubscriptionManager
                .from(context);
        subscriptionManager.setDefaultDataSubId(subId);

        final List<SubscriptionInfo> subInfoList = subscriptionManager
                .getActiveSubscriptionInfoList();
        final int selectableSubInfoLength = subInfoList == null ? 0
                : subInfoList.size();
        if (selectableSubInfoLength > 1) {
            Toast.makeText(context, R.string.data_switch_started,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setMobileDataEnabled(int subId, boolean enabled) {
        mTelephonyManager.setDataEnabled(subId, enabled);
    }

    private void disableDataForOtherSubscriptions(SubscriptionInfo currentSir) {
        final SubscriptionManager subscriptionManager = SubscriptionManager
                .from(mContext);
        final List<SubscriptionInfo> subInfoList = subscriptionManager
                .getActiveSubscriptionInfoList();
        if (subInfoList != null) {
            for (SubscriptionInfo subInfo : subInfoList) {
                if (subInfo != null && currentSir != null && subInfo.getSubscriptionId() != currentSir
                        .getSubscriptionId()) {
                    setMobileDataEnabled(subInfo.getSubscriptionId(), false);
                }
            }
        }
    }

    public Dialog createDialog(final Context context) {
        final ArrayList<String> list = new ArrayList<String>();
        updateSimInfoList(context);

        final int selectableSubInfoLength = mSimInfoList == null ? 0
                : mSimInfoList.size();

        final DialogInterface.OnClickListener selectionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int value) {

                final SimInfo si = mSimInfoList.get(value);
                Log.d(TAG, " simInfo = "+ si);
                if((mSimInfoList.size()==3 && value==2) || 
                        (mSimInfoList.size()==2 && value==1)) {
                    mController.setMobileDataEnabled(false);
                } else {
                    setDefaultDataSubId(context, si.mSubId);
                    final SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
                    //SubscriptionInfo currentSir = subscriptionManager.getSubscriptionInfo(si.mSubId);
                    SubscriptionInfo currentSir = subscriptionManager.getActiveSubscriptionInfo(si.mSubId);
                    mController.setMobileDataEnabled(true);
                    disableDataForOtherSubscriptions(currentSir);
                }
            }

        };

        Dialog.OnKeyListener keyListener = new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                    KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return true;
            }
        };
        for (int i = 0; i < selectableSubInfoLength; ++i) {
            final SimInfo si = mSimInfoList.get(i);
            CharSequence displayName = si.mDisplayName;
            if (displayName == null) {
                displayName = "";
            }
            list.add(displayName.toString());
        }

        String[] arr = list.toArray(new String[0]);

        //modify BUG_ID:DWYSLM-707 dushuaihui 20160420(start)
        AlertDialog.Builder builder;
        if(context.getResources().getBoolean(R.bool.config_white_background)){
            builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }else{
            builder = new AlertDialog.Builder(context);
        }
        //modify BUG_ID:DWYSLM-707 dushuaihui 20160420(start)

        ListAdapter adapter = new SelectAccountListAdapter(mSimInfoList,
                builder.getContext(), R.layout.select_account_list_item, arr);

        builder.setTitle(R.string.mobile);

        Dialog dialog = builder.setAdapter(adapter, selectionListener).create();
        dialog.setOnKeyListener(keyListener);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dismiss();
            }
        });
        dialog.getWindow().setType(
                WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL);
        mDataConnectionDialog = dialog;
        Log.d(TAG, "dialog = " + mDataConnectionDialog);
        return dialog;

    }

    class SimInfo {
        int mSubId;
        String mDisplayName;
        String mNumber;
        Bitmap mBitmap;

        public SimInfo(int mSubId, String mDisplayName, String mNumber,
                Bitmap mBitmap) {
            super();
            this.mSubId = mSubId;
            this.mDisplayName = mDisplayName;
            this.mNumber = mNumber;
            this.mBitmap = mBitmap;
        }

        @Override
        public String toString() {
            return "SimInfo [mSubId=" + mSubId + ", mDisplayName="
                    + mDisplayName + ", mNumber=" + mNumber + ", mBitmap="
                    + mBitmap + "]";
        }

    }

    private class SelectAccountListAdapter extends ArrayAdapter<String> {
        private Context mContext;
        private int mResId;
        private final float OPACITY = 0.54f;
        private List<SimInfo> mSimInfos;

        public SelectAccountListAdapter(List<SimInfo> simInfos,
                Context context, int resource, String[] arr) {
            super(context, resource, arr);
            mContext = context;
            mResId = resource;
            mSimInfos = simInfos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView;
            final ViewHolder holder;

            if (convertView == null) {
                // Cache views for faster scrolling
                rowView = inflater.inflate(mResId, null);
                holder = new ViewHolder();
                holder.title = (TextView) rowView.findViewById(R.id.title);
                holder.summary = (TextView) rowView.findViewById(R.id.summary);
                holder.icon = (ImageView) rowView.findViewById(R.id.icon);
                rowView.setTag(holder);
            } else {
                rowView = convertView;
                holder = (ViewHolder) rowView.getTag();
            }

            final SimInfo si = mSimInfos.get(position);
            if (si == null) {
                holder.title.setText(getItem(position));
                holder.summary.setText("");
                holder.icon.setImageDrawable(mContext.getResources()
                        .getDrawable(R.drawable.ic_live_help));

                holder.icon.setAlpha(OPACITY);
            } else {
                holder.title.setText(si.mDisplayName);
                holder.summary.setText(si.mNumber);
                holder.icon.setImageBitmap(si.mBitmap);
            }
            return rowView;
        }

        private class ViewHolder {
            TextView title;
            TextView summary;
            ImageView icon;
        }
    }

    // Receiver to handle different actions
    private BroadcastReceiver mSubReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "mSubReceiver action = " + action);
            dismiss();
        }
    };
}
