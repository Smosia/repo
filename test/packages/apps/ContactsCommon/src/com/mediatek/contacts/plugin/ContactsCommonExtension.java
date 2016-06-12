package com.mediatek.contacts.plugin;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract.Intents.Insert;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.method.DialerKeyListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.EditText;

import com.mediatek.internal.telephony.ITelephonyEx;
import com.mediatek.contacts.ext.DefaultOp01Extension;
import com.mediatek.common.PluginImpl;
import com.android.contacts.common.R;

@PluginImpl(interfaceName="com.mediatek.contacts.ext.IOp01Extension")
public class ContactsCommonExtension extends DefaultOp01Extension {
    private static final String TAG = "ContactsCommonExtension";
    private Context mContext;
    private static SubscriptionManager mManager;
    private static Context mContextHost;
    private static final int MENU_ID_BASE = 9999;
    private static final int MENU_SIM_STORAGE = MENU_ID_BASE + 1;
    private static final int MENU_ID_BLACKLIST = MENU_ID_BASE + 2;
    private static final String BLACKLIST_ACTIVITY_INTENT =
                                    "com.mediatek.rcs.blacklist.BlacklistManagerActivity";

    public ContactsCommonExtension(Context context) {
        mContext = context;
        mManager = SubscriptionManager.from(context);
    }

    @Override
    public void addOptionsMenu(Context context, Menu menu) {
        Log.i(TAG, "addOptionsMenu");
        mContextHost = context;

        MenuItem itemBlacklist = menu.findItem(MENU_ID_BLACKLIST);
        if (itemBlacklist == null) {
            String string = mContext.getResources().getString(R.string.menu_blacklist);
            menu.add(0, MENU_ID_BLACKLIST, 0, string).setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(BLACKLIST_ACTIVITY_INTENT);
                        intent.setClassName("com.mediatek.rcs.blacklist",
                                    "com.mediatek.rcs.blacklist.BlacklistManagerActivity");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            mContext.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Log.i(TAG, "not found application");
                            e.printStackTrace();
                        }
                        return true;
                    }
            });
        }
    }



    @Override
    public int getMultiChoiceLimitCount(int defaultCount) {
        Log.i(TAG, "[getMultiChoiceLimitCount]");
        return 5000;
    }

    @Override
    public String formatNumber(String number, Bundle bundle) {
        String result = number;
        if (bundle != null) {
            String phone = bundle.getString(Insert.PHONE);
            if (phone != null && !TextUtils.isEmpty(phone)) {
                phone = phone.replaceAll(" ", "");
                Log.i(TAG, "[formatNumber]" + phone);
                bundle.putString(Insert.PHONE, phone);
            }
            return result;
        }
        if (result != null && !TextUtils.isEmpty(result)) {
            result = result.replaceAll(" ", "");          
        }
        Log.i(TAG, "[formatNumber]" + result);
        return result;
    }

    @Override
    public void setViewKeyListener(EditText fieldView) {
        Log.i(TAG, "[setViewKeyListener] fieldView : " + fieldView);
        if (fieldView != null) {
            fieldView.setKeyListener(SIMKeyListener.getInstance());
        } else {
            Log.e(TAG, "[setViewKeyListener]fieldView is null");
        }
    }

    public static class SIMKeyListener extends DialerKeyListener {
        private static SIMKeyListener sKeyListener;
        /**
         * The characters that are used.
         *
         * @see KeyEvent#getMatch
         * @see #getAcceptedChars
         */
        public static final char[] CHARACTERS = new char[] { '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9', '+', '*', '#', 'P', 'W', 'p', 'w', ',', ';'};

        @Override
        protected char[] getAcceptedChars() {
            return CHARACTERS;
        }

        public static SIMKeyListener getInstance() {
            if (sKeyListener == null) {
                sKeyListener = new SIMKeyListener();
            }
            return sKeyListener;
        }

    }
}