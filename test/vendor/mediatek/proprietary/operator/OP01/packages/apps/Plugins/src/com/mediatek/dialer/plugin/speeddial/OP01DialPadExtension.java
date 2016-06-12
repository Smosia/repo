package com.mediatek.dialer.plugin.speeddial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.mediatek.common.PluginImpl;
import com.mediatek.dialer.ext.DefaultDialPadExtension;
import com.mediatek.dialer.ext.IDialPadExtension;
import com.mediatek.internal.telephony.ITelephonyEx;
import com.mediatek.op01.plugin.R;

import java.util.List;

@PluginImpl(interfaceName="com.mediatek.dialer.ext.IDialPadExtension")
public class OP01DialPadExtension extends DefaultDialPadExtension implements View.OnLongClickListener{

    private static final String TAG = "OP01DialPadExtension";

    private Activity mHostActivity;
    private String mHostPackage;
    private Resources mHostResources;
    private EditText mEditText;
    private Context mContext;
    private int mMenuId = 0x1000;
    /**
     * for op01
     * @param durationView the duration text
     */

    public OP01DialPadExtension (Context context) {
        super();
        mContext = context;
    }

    @Override
    public void buildOptionsMenu(final Activity activity, Menu menu){
        int index = menu.size();
        MenuItem speedDialMenu = menu.add(Menu.NONE,
                index, 0, mContext.getText(R.string.call_speed_dial));
        speedDialMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "SpeedDial onMenuItemClick");
                SpeedDialController.getInstance().enterSpeedDial(activity);
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(Activity activity, View view) {
        Log.d(TAG, "onViewCreated.");
        mHostActivity = activity;
    
        mHostPackage = activity.getPackageName();
        mHostResources = activity.getResources();

        View two = (View) view.findViewById(mHostResources.getIdentifier("two",
                                "id", mHostPackage));
        two.setOnLongClickListener(this);
        
        View three = (View) view.findViewById(mHostResources.getIdentifier("three",
                                "id", mHostPackage));
        three.setOnLongClickListener(this);
        
        View four = (View) view.findViewById(mHostResources.getIdentifier("four",
                                "id", mHostPackage));
        four.setOnLongClickListener(this);
        
        View five = (View) view.findViewById(mHostResources.getIdentifier("five",
                                "id", mHostPackage));
        five.setOnLongClickListener(this);
        
        View six = (View) view.findViewById(mHostResources.getIdentifier("six",
                                "id", mHostPackage));
        six.setOnLongClickListener(this);
        
        View seven = (View) view.findViewById(mHostResources.getIdentifier("seven",
                                "id", mHostPackage));
        seven.setOnLongClickListener(this);
        
        View eight = (View) view.findViewById(mHostResources.getIdentifier("eight",
                                "id", mHostPackage));
        eight.setOnLongClickListener(this);
        
        View nine = (View) view.findViewById(mHostResources.getIdentifier("nine",
                                "id", mHostPackage));
        nine.setOnLongClickListener(this);

        mEditText = (EditText) view.findViewById(mHostResources.getIdentifier("digits",
                                "id", mHostPackage));
    }

    @Override
    public boolean onLongClick(View view) {
        int id = view.getId();

        int key = 0;
        if (id == mHostResources.getIdentifier("two","id", mHostPackage)) {
            key = 2;
        }
        else if (id == mHostResources.getIdentifier("three","id", mHostPackage)) {
            key = 3;
        }
        else if (id == mHostResources.getIdentifier("four","id", mHostPackage)) {
            key = 4;
        }
        else if (id == mHostResources.getIdentifier("five","id", mHostPackage)) {
            key = 5;
        }
        else if (id == mHostResources.getIdentifier("six","id", mHostPackage)) {
            key = 6;
        }
        else if (id == mHostResources.getIdentifier("seven","id", mHostPackage)) {
            key = 7;
        }
        else if (id == mHostResources.getIdentifier("eight","id", mHostPackage)) {
            key = 8;
        }
        else if (id == mHostResources.getIdentifier("nine","id", mHostPackage)) {
            key = 9;
        }

        if (key > 0 && key < 10 && mEditText.getText().length() <= 1) {
            SpeedDialController.getInstance().handleKeyLongProcess(mHostActivity, mContext, key);
            mEditText.getText().clear();
            return true;
        }
        return false;
    }

    @Override
    public List<String> getSingleIMEI(List<String> list) {
        Log.d(TAG, "getSingleIMEI");
        ITelephonyEx iTel = ITelephonyEx.Stub.asInterface(
                ServiceManager.getService(Context.TELEPHONY_SERVICE_EX));
        if (isC2KSupport()) {
            if (iTel != null) {
                list.clear();
                try {
                    int phoneCount = TelephonyManager.getDefault().getPhoneCount();
                    for (int i = 0; i < phoneCount; i++) {
                        String imei = iTel.getSvlteImei(i);
                        Log.d(TAG, "getSingleIMEI, imei = " + imei);
                        if (imei != null) {
                            list.add("IMEI:" + imei);
                        }
                    }
                    String meid = iTel.getMeid();
                    Log.d(TAG, "getSingleIMEI, meid = " + meid);
                    meid = "MEID:" + meid;
                    list.add(meid);
                } catch(RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }else if (isSigleImeiEnabled()) {
            if (list.size() > 1) {
                for (int i = list.size() - 1; i < list.size(); i++) {
                    list.remove(i);
                }
            }
        }
        return list;
    }

    private boolean isC2KSupport() {
        return "1".equals(SystemProperties.get("ro.mtk_c2k_support"));
    }

    private boolean isSigleImeiEnabled() {
        return SystemProperties.get("ro.mtk_single_imei").equals("1");
    }

    @Override
    public void constructPopupMenu(PopupMenu popupMenu, View anchorView, Menu menu) {
        MenuItem item = menu.findItem(mMenuId);
        boolean canStart = canStartVideoCall();
        String number = mEditText.getText().toString();
        Log.d(TAG, "constructPopupMenu, canStartVideoCall = "
                                + canStart + ", number = " + number);

        if ((!canStart) || TextUtils.isEmpty(number)) {
            //Can not start video call, remove video call item
            menu.removeItem(mMenuId);
            return;
        } else {
            //Meaning already have video call item, just return
            if (item != null) {
                return;
            }
        }

        MenuItem videoMenu = menu.add(Menu.NONE,
                mMenuId, 0, mContext.getText(R.string.start_video_dial));
        videoMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "video onMenuItemClick");
                placeOutgoingVideoCall();
                return true;
            }
        });
    }

    private Uri getCallUri(String number) {
        Log.d(TAG, "getCallUri, number = " + number);
        if (PhoneNumberUtils.isUriNumber(number)) {
             return Uri.fromParts(PhoneAccount.SCHEME_SIP, number, null);
        }
        return Uri.fromParts(PhoneAccount.SCHEME_TEL, number, null);
     }

    private void placeOutgoingVideoCall() {
        TelecomManager telecommMgr = (TelecomManager)
                mContext.getSystemService(Context.TELECOM_SERVICE);
        if (telecommMgr == null) {
            return;
        }

        if (mHostActivity == null || mEditText == null) {
            return;
        }

        final Intent intent = new Intent(Intent.ACTION_CALL,
                        getCallUri(mEditText.getText().toString()));
        intent.putExtra(TelecomManager.EXTRA_START_CALL_WITH_VIDEO_STATE,
                VideoProfile.STATE_BIDIRECTIONAL);

        telecommMgr.placeCall(intent.getData(), intent.getExtras());
        mEditText.getText().clear();
    }

    private boolean canStartVideoCall() {
        TelecomManager telecommMgr = (TelecomManager)
                mContext.getSystemService(Context.TELECOM_SERVICE);
        if (telecommMgr == null) {
            return false;
        }

        List<PhoneAccountHandle> accountHandles = telecommMgr.getCallCapablePhoneAccounts();
        for (PhoneAccountHandle accountHandle : accountHandles) {
            PhoneAccount account = telecommMgr.getPhoneAccount(accountHandle);
            if (account != null && account.hasCapabilities(
                    PhoneAccount.CAPABILITY_VIDEO_CALLING)) {
                return true;
            }
        }
        return false;
    }
}

