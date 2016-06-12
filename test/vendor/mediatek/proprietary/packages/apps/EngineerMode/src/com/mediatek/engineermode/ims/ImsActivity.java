/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein is
 * confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 * the prior written permission of MediaTek inc. and/or its licensors, any
 * reproduction, modification, use or disclosure of MediaTek Software, and
 * information contained herein, in whole or in part, shall be strictly
 * prohibited.
 *
 * MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 * ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 * RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 * INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 * TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 * RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 * OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 * SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 * RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 * ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 * RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 * MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 * CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek
 * Software") have been modified by MediaTek Inc. All revisions are subject to
 * any receiver's applicable license agreements with MediaTek Inc.
 */

package com.mediatek.engineermode.ims;

import android.app.Activity;
import android.content.Intent;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Telephony;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.mediatek.engineermode.R;

import java.util.ArrayList;

/**
 * Ims Configuration.
 */
public class ImsActivity extends Activity implements View.OnClickListener, OnItemClickListener {
    private static final String IMS_VOLTE_SETTING_SHAREPRE= "telephony_ims_volte_settings";
    private static final String TAG = "EM/Ims";
    private static final String PROP_APN = "volte.emergency.pdn.name";
    private static final String PROP_TYPE = "volte.emergency.pdn.protocol";
    private static final String PROP_SS_MODE = "persist.radio.ss.mode";
    private static final String PROP_SS_DISABLE_METHOD = "persist.radio.ss.xrdm";
    private static final String PROP_SS_CFNUM = "persist.radio.xcap.cfn";
    private static final String TYPE_IP = "IP";
    private static final String TYPE_IPV6 = "IPV6";
    private static final String TYPE_IPV4V6 = "IPV4V6";
    private static final String MODE_SS_XCAP = "Prefer XCAP";
    private static final String MODE_SS_CS = "Prefer CS";
    private static final int DISABLE_MODE_DELETE_RULE = 1;
    private static final int DISABLE_MODE_ADD_RULE_DEACTIVATED_TAG = 2;

    private static final String SET_OPERATOR_CODE = "operator_code";
    private static final String SET_IMS_SIGNAL = "ims_signaling_qci";
    private static final String SET_PRECONDITION = "UA_call_precondition";

    private static final String OPERATOR_CODE_VALUE = "16386";
    private static final String IMS_SIGNAL_VALUE = "5";
    private static final String PRECONDITION_VALUE = "0";

    private static final String OPERATOR_CODE_DEFAULT = "0";
    private static final String IMS_SIGNAL_DEFAULT = "5";
    private static final String PRECONDITION_DEFAULT = "1";

    private static final int MSG_SET_OPERATOR_CODE = 0;
    private static final int MSG_SET_IMS_SIGNAL = 1;
    private static final int MSG_SET_PRECONDITION = 2;
    private static final int MSG_SET_VOLTE_SETTING = 3;

    private static int mVolteSettingFlag = 0;

    private RadioButton mRadioIp;
    private RadioButton mRadioIpv6;
    private RadioButton mRadioIpv4v6;
    private RadioButton mRadioSSXcap;
    private RadioButton mRadioSSCs;
    private RadioButton mRadioSSDisableTag;
    private RadioButton mRadioSSDisableDel;
    private RadioButton mRadioSetVolteOff;
    private RadioButton mRadioSetVolteOn;
    private TextView mImsStatus;
    private EditText mApn;
    private EditText mXcapCFNum;
    private Button mButtonSetApn;
    private Button mButtonSetType;
    private Button mButtonSetSSMode;
    private Button mButtonSetSSDisable;
    private Button mButtonSetXcapCFNum;
    private Button mButtonSetVolte;
    private ListView mCategoryList;
    private Toast mToast = null;
    private Phone mPhone = null;
    private android.net.Uri  mUri = null;


    private final Handler mSettingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SET_VOLTE_SETTING) {
                set4gLte();
                setImsApn();
                if (mVolteSettingFlag == 1) {
                    showToast("Set CMW500 setting successful.");
                } else {
                    showToast("Set Default setting successful.");
                }
            }
        }
    };

    private final Handler mATHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SET_OPERATOR_CODE) {
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar.exception == null) {
                    Log.d("@M_" + TAG, "Set operator_code successful.");
                    sendCommand(SET_IMS_SIGNAL,
                        ((mVolteSettingFlag == 1) ? IMS_SIGNAL_VALUE:IMS_SIGNAL_DEFAULT),
                        MSG_SET_IMS_SIGNAL);
                } else {
                    showToast("Set operator_code failed.");
                }
            }else if (msg.what == MSG_SET_IMS_SIGNAL) {
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar.exception == null) {
                    Log.d("@M_" + TAG, "Set ims_signaling_qci successful.");
                    sendCommand(SET_PRECONDITION,
                        ((mVolteSettingFlag == 1) ? PRECONDITION_VALUE:PRECONDITION_DEFAULT),
                        MSG_SET_PRECONDITION);
                } else {
                    showToast("Set ims_signaling_qci failed.");
                }
            }else if (msg.what == MSG_SET_PRECONDITION) {
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar.exception == null) {
                    Log.d("@M_" + TAG, "Set UA_call_precondition successful.");
                    Message msgSetting = new Message();
                    msgSetting.what = MSG_SET_VOLTE_SETTING;
                    mSettingHandler.sendMessage(msgSetting);
                } else {
                    showToast("Set UA_call_precondition failed.");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("@M_" + TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ims);

        mImsStatus = (TextView) findViewById(R.id.ims_status);
        mApn = (EditText) findViewById(R.id.ims_pdn_apn);
        mXcapCFNum = (EditText) findViewById(R.id.ims_ss_cf_num);
        mButtonSetApn = (Button) findViewById(R.id.ims_pdn_set_apn);
        mButtonSetApn.setOnClickListener(this);
        mRadioIp = (RadioButton) findViewById(R.id.ims_pdn_type_ip);
        mRadioIpv6 = (RadioButton) findViewById(R.id.ims_pdn_type_ipv6);
        mRadioIpv4v6 = (RadioButton) findViewById(R.id.ims_pdn_type_ipv4v6);
        mButtonSetType = (Button) findViewById(R.id.ims_pdn_set_type);
        mButtonSetType.setOnClickListener(this);
        mRadioSSXcap = (RadioButton) findViewById(R.id.ims_ss_mode_xcap);
        mRadioSSCs = (RadioButton) findViewById(R.id.ims_ss_mode_cs);
        mButtonSetSSMode = (Button) findViewById(R.id.ims_ss_set_mode);
        mButtonSetSSMode.setOnClickListener(this);
        mRadioSSDisableTag = (RadioButton) findViewById(R.id.ims_ss_disable_tag);
        mRadioSSDisableDel = (RadioButton) findViewById(R.id.ims_ss_disable_del);
        mButtonSetSSDisable = (Button) findViewById(R.id.ims_ss_set_disable);
        mButtonSetSSDisable.setOnClickListener(this);
        mButtonSetXcapCFNum = (Button) findViewById(R.id.ims_set_ss_cf_num);
        mButtonSetXcapCFNum.setOnClickListener(this);
        mRadioSetVolteOff = (RadioButton) findViewById(R.id.volte_set_off);
        mRadioSetVolteOn = (RadioButton) findViewById(R.id.volte_set_on);
        mButtonSetVolte = (Button) findViewById(R.id.volte_set);
        mButtonSetVolte.setOnClickListener(this);
        final SharedPreferences volteSettingSh = getSharedPreferences(IMS_VOLTE_SETTING_SHAREPRE,
            android.content.Context.MODE_PRIVATE);
        boolean volteSetChecked = volteSettingSh.getBoolean(getString(
            R.string.volte_set_check), false);
        if (volteSetChecked) {
            mRadioSetVolteOn.setChecked(true);
        } else {
            mRadioSetVolteOff.setChecked(true);
        }
        mCategoryList = (ListView) findViewById(R.id.ims_category_list);
        ArrayList<String> items = new ArrayList<String>();
        items.add(getString(R.string.ims_category_common));
        items.add(getString(R.string.ims_category_registration));
        items.add(getString(R.string.ims_category_call));
        items.add(getString(R.string.ims_category_sms));
        items.add(getString(R.string.ims_category_bearer));
        items.add(getString(R.string.ims_category_pcscf));
        items.add(getString(R.string.ims_category_ussd));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, items);
        mCategoryList.setAdapter(adapter);
        mCategoryList.setOnItemClickListener(this);
        setListViewItemsHeight(mCategoryList);
    }

    private void setListViewItemsHeight(ListView listview) {
        if (listview == null) {
            return;
        }
        ListAdapter adapter = listview.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View itemView = adapter.getView(i, null, listview);
            itemView.measure(0, 0);
            totalHeight += itemView.getMeasuredHeight();
        }
        totalHeight += (adapter.getCount() - 1) * listview.getDividerHeight();
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight;
        listview.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent = new Intent(this, ConfigIMSActivity.class);
        switch (arg2) {
        case 0:
            intent.putExtra("category", getString(R.string.ims_category_common));
            break;
        case 1:
            intent.putExtra("category", getString(R.string.ims_category_registration));
            break;
        case 2:
            intent.putExtra("category", getString(R.string.ims_category_call));
            break;
        case 3:
            intent.putExtra("category", getString(R.string.ims_category_sms));
            break;
        case 4:
            intent.putExtra("category", getString(R.string.ims_category_bearer));
            break;
        case 5:
            intent.putExtra("category", getString(R.string.ims_category_pcscf));
            break;
        case 6:
            intent.putExtra("category", getString(R.string.ims_category_ussd));
            break;
        default:
            break;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Log.d("@M_" + TAG, "onResume()");
        super.onResume();

        int subId = SubscriptionManager.getDefaultDataSubId();
        Log.i("@M_" + TAG, "sub id " + subId);
        int phoneId = SubscriptionManager.getPhoneId(subId);
        Log.i("@M_" + TAG, "phone id " + phoneId);
        int phoneCount = TelephonyManager.getDefault().getPhoneCount();
        Log.i("@M_" + TAG, "phone count " + phoneCount);
        mPhone = PhoneFactory.getPhone(phoneId >= 0 && phoneId < phoneCount ? phoneId : 0);
        if (mPhone != null) {
            try {
                boolean status = ImsManager.getInstance(this, mPhone.getPhoneId()).getImsRegInfo();
                Log.d("@M_" + TAG, "getImsRegInfo(): " + status);
                mImsStatus.setText(getString(R.string.ims_status) + (status ? "true" : "false"));
            } catch (ImsException e) {
                mImsStatus.setText(getString(R.string.ims_status) + "failed to get");
            }
        } else {
            Log.e("@M_" + TAG, "getDefaultPhone() failed");
            showToast("Get IMS registration status failed");
        }

        String name = SystemProperties.get(PROP_APN, "");
        String type = SystemProperties.get(PROP_TYPE, TYPE_IPV4V6);
        String ssmode = SystemProperties.get(PROP_SS_MODE, MODE_SS_XCAP);
        String ssdisableMethod = SystemProperties.get(PROP_SS_DISABLE_METHOD,
                Integer.toString(DISABLE_MODE_ADD_RULE_DEACTIVATED_TAG));
        String xcapCFNum = SystemProperties.get(PROP_SS_CFNUM, "");
        Log.d("@M_" + TAG, PROP_APN + ": " + name);
        Log.d("@M_" + TAG, PROP_TYPE + ": " + type);
        Log.d("@M_" + TAG, PROP_SS_MODE + ": " + ssmode);
        Log.d("@M_" + TAG, PROP_SS_DISABLE_METHOD + ": " + ssdisableMethod);
        Log.d("@M_" + TAG, PROP_SS_CFNUM + ":" + xcapCFNum);

        mApn.setText(name);
        if (TYPE_IP.equals(type)) {
            mRadioIp.setChecked(true);
        } else if (TYPE_IPV6.equals(type)) {
            mRadioIpv6.setChecked(true);
        } else if (TYPE_IPV4V6.equals(type)) {
            mRadioIpv4v6.setChecked(true);
        } else {
            showToast("Got invalid IP type: \"" + type + "\"");
        }

        if (MODE_SS_XCAP.equals(ssmode)) {
            mRadioSSXcap.setChecked(true);
        } else if (MODE_SS_CS.equals(ssmode)) {
            mRadioSSCs.setChecked(true);
        } else {
            showToast("Got invalid SS Mode: \"" + ssmode + "\"");
        }

        if (DISABLE_MODE_ADD_RULE_DEACTIVATED_TAG == Integer.parseInt(ssdisableMethod)) {
            mRadioSSDisableTag.setChecked(true);
        } else if (DISABLE_MODE_DELETE_RULE == Integer.parseInt(ssdisableMethod)) {
            mRadioSSDisableDel.setChecked(true);
        } else {
            showToast("Got invalid SS Disable Method: \"" + ssdisableMethod + "\"");
        }

        mXcapCFNum.setText(xcapCFNum);
    }


    @Override
    public void onDestroy() {
        writeVolteSettingSharedPreference(mRadioSetVolteOn.isChecked());
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSetApn) {
            SystemProperties.set(PROP_APN, mApn.getText().toString());
            Log.d("@M_" + TAG, "Set " + PROP_APN + " = " + mApn.getText().toString());
            showToast("Set APN done");
        } else if (v == mButtonSetType) {
            String type = "";
            if (mRadioIp.isChecked()) {
                type = TYPE_IP;
            } else if (mRadioIpv6.isChecked()) {
                type = TYPE_IPV6;
            } else if (mRadioIpv4v6.isChecked()) {
                type = TYPE_IPV4V6;
            } else {
                return;
            }
            SystemProperties.set(PROP_TYPE, type);
            Log.d("@M_" + TAG, "Set " + PROP_TYPE + " = " + type);
            showToast("Set IP Type done");
        } else if (v == mButtonSetSSMode) {
            String ssmode = "";
            if (mRadioSSXcap.isChecked()) {
                ssmode = MODE_SS_XCAP;
            } else if (mRadioSSCs.isChecked()) {
                ssmode = MODE_SS_CS;
            } else {
                return;
            }
            SystemProperties.set(PROP_SS_MODE, ssmode);
            Log.d("@M_" + TAG, "Set " + PROP_SS_MODE + " = " + ssmode);
            showToast("Set SS Mode done");
        } else if (v == mButtonSetSSDisable) {
            String ssdisableMethod = "";
            if (mRadioSSDisableTag.isChecked()) {
                ssdisableMethod = Integer.toString(DISABLE_MODE_ADD_RULE_DEACTIVATED_TAG);
            } else if (mRadioSSDisableDel.isChecked()) {
                ssdisableMethod = Integer.toString(DISABLE_MODE_DELETE_RULE);
            } else {
                return;
            }
            SystemProperties.set(PROP_SS_DISABLE_METHOD, ssdisableMethod);
            Log.d("@M_" + TAG, "Set " + PROP_SS_DISABLE_METHOD + " = " + ssdisableMethod);
            showToast("Set SS Disable done");
        } else if (v == mButtonSetXcapCFNum) {
            SystemProperties.set(PROP_SS_CFNUM, mXcapCFNum.getText().toString());
            Log.d("@M_" + TAG, "Set " + PROP_SS_CFNUM + " = " + mXcapCFNum.getText().toString());
            showToast("Set SS CF Number done");
        } else if (v == mButtonSetVolte) {
            if (mRadioSetVolteOff.isChecked()) {
                mVolteSettingFlag = 0;
            } else if (mRadioSetVolteOn.isChecked()) {
                mVolteSettingFlag = 1;
            } else {
                return;
            }
            Log.d("@M_" + TAG, "Set VOLTE");
            sendCommand(SET_OPERATOR_CODE,
                ((mVolteSettingFlag == 1) ? OPERATOR_CODE_VALUE:OPERATOR_CODE_DEFAULT),
                MSG_SET_OPERATOR_CODE);
        }
    }

    private void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void sendCommand(String name, String value, int msgtype) {
        Message msg = mATHandler.obtainMessage(msgtype);
        if (mPhone != null) {
            mPhone.invokeOemRilRequestStrings(
                new String[] {"AT+ECFGSET=\"" + name + "\",\"" + value + "\"", ""}, msg);
        }
    }

    private void set4gLte() {
        Log.d("@M_" + TAG, "set4gLte mVolteSettingFlag = " + mVolteSettingFlag);
        if (mVolteSettingFlag == 1) {
            ImsManager.setEnhanced4gLteModeSetting(this, true);
        } else {
            ImsManager.setEnhanced4gLteModeSetting(this, false);
        }
    }

    private void setImsApn() {
        if (mVolteSettingFlag == 1) {
            insertImsApn();
        } else {
            resetImsApn();
        }
    }

    private void insertImsApn() {
        ContentValues values = new ContentValues();
        values.put(Telephony.Carriers.NAME,"ims");
        values.put(Telephony.Carriers.APN, "ims");
        values.put(Telephony.Carriers.TYPE, "ia,ims");
        values.put(Telephony.Carriers.PROTOCOL, "IPV6");
        values.put(Telephony.Carriers.ROAMING_PROTOCOL, "IPV6");

        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        // SUBID: the subId for the sim inserted
        int subId = SubscriptionManager.getDefaultDataSubId();
        Log.d("@M_" + TAG, "insertImsApn sub id " + subId);
        String numeric = "";
        String mcc = "";
        String mnc = "";
        numeric = tm.getSimOperator(subId);
        if (numeric != null && numeric.length() > 4) {
            mcc = numeric.substring(0, 3);
            mnc = numeric.substring(3);
        }
        values.put(Telephony.Carriers.MCC, mcc);
        values.put(Telephony.Carriers.MNC, mnc);
        values.put(Telephony.Carriers.NUMERIC, numeric);

        mUri = this.getContentResolver().insert(Telephony.Carriers.CONTENT_URI, values);
        Log.d("@M_" + TAG, "insertImsApn mUri " + mUri);
        writeImsApnSharedPreference(mUri.toString());
    }

    private void resetImsApn() {
        final SharedPreferences volteSettingSh = getSharedPreferences(IMS_VOLTE_SETTING_SHAREPRE,
            android.content.Context.MODE_PRIVATE);
        String uri = volteSettingSh.getString(getString(
            R.string.volte_set_ims_apn), null);
        mUri = android.net.Uri.parse(uri);
        Log.d("@M_" + TAG, "resetImsApn mUri " + mUri);
        if (mUri != null) {
            this.getContentResolver().delete(mUri, null,null);
            mUri = null;
        }
    }

    private void writeVolteSettingSharedPreference(boolean check) {
        final SharedPreferences volteSettingSh = getSharedPreferences(
                   IMS_VOLTE_SETTING_SHAREPRE, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = volteSettingSh.edit();
        editor.putBoolean(getString(R.string.volte_set_check), check);
        editor.commit();
    }

    private void writeImsApnSharedPreference(String imsApnUri) {
        final SharedPreferences volteSettingSh = getSharedPreferences(
                   IMS_VOLTE_SETTING_SHAREPRE, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = volteSettingSh.edit();
        editor.putString(getString(R.string.volte_set_ims_apn), imsApnUri);
        editor.commit();
    }

}
