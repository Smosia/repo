package com.mediatek.contacts.ext;

import android.content.Context;
import android.telecom.PhoneAccountHandle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public interface IQuickContactExtension {
    /**
     * for op01
     * called when updating call list, plug-in should customize the duration view if needed
     * @param durationView the duration text
     */
    public void setDurationViewVisibility(TextView durationView);

    /**
     * for op01
     * called when updating call list, plug-in should customize the date view if needed
     * @param context
     * @param dateView the date text
     * @param date calldetail date
     */
    public void setDateView(Context context, TextView dateView, long date);

    /**
     * for op01,add for "blacklist" in call detail.
     * @param menu blacklist menu.
     * @param number phone number.
     * @param name contact name.
     */
    public void onPrepareOptionsMenu(Context context, Menu menu, long id);
}