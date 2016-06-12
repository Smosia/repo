package com.mediatek.contacts.ext;

import android.content.Context;
import android.telecom.PhoneAccountHandle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DefaultQuickContactExtension implements IQuickContactExtension {
    private static final String TAG = "DefaultQuickContactExtension";


    /**
     * for op01
     * @param durationView the duration text
     */
    @Override
    public void setDurationViewVisibility(TextView durationView) {
        log("setDurationViewVisibility");
    }

    /**
     * for op01,add for "blacklist" in call detail.
     * @param menu blacklist menu.
     * @param number phone number.
     * @param name contact name.
     */
    @Override
    public void onPrepareOptionsMenu(Context context, Menu menu, long id) {
        //do-nothing
    }
	
    /**
     * for op01
     * called when updating call list, plug-in should customize the date view if needed
     * @param context
     * @param dateView the date text
     * @param date calldetail date
     */
    public void setDateView(Context context, TextView dateView, long date) {
        log("setDateView");
    }

    public void log(String msg) {
        Log.d(TAG, msg);
    }
}
