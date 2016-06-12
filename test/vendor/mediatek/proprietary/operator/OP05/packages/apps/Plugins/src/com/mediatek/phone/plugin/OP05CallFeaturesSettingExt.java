package com.mediatek.phone.plugin;

import com.android.internal.telephony.CommandException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncResult;
import android.preference.Preference;
import android.util.Log;
import android.view.WindowManager;

import com.mediatek.common.PluginImpl;
import com.mediatek.op05.plugin.R;

import com.mediatek.phone.ext.DefaultCallFeaturesSettingExt;

@PluginImpl(interfaceName="com.mediatek.phone.ext.ICallFeaturesSettingExt")
public class OP05CallFeaturesSettingExt extends DefaultCallFeaturesSettingExt {
    private static final String TAG = "OP05CallFeaturesSettingExt";
    private Context mContext;

    public OP05CallFeaturesSettingExt(Context context) {
        mContext = context;
    }

    @Override
    public boolean needShowOpenMobileDataDialog(Context context, int subId) {
        Log.d(TAG, "needShowOpenMobileDataDialog false");
        return false;
    }

    @Override
    public boolean handleErrorDialog(Context context, AsyncResult ar, Preference preference) {
        boolean result = false;
        boolean exception = false;
        if (ar.exception != null) {
            if (ar.exception instanceof CommandException) {
                CommandException ce = (CommandException) ar.exception;
                Log.d(TAG, " handleErrorDialog, ce.getCommandError()" + ce.getCommandError());
                if (ce.getCommandError() == CommandException.Error.UT_XCAP_404_NOT_FOUND) {
                    exception = true;
                } else if (ce.getCommandError() == CommandException.Error.UT_XCAP_409_CONFLICT) {
                    showDialog(context);
                    exception = true;
                }
            }
        }
        if(exception) {
            preference.setEnabled(false);
            Log.d(TAG, " handleErrorDialog");
            result = true;
        }
        return result;
    }

    private AlertDialog mWarningDialog = null;
    private void showDialog(Context context) {
        Log.d(TAG, " showDialog" );
        if (mWarningDialog == null) {
            mWarningDialog = new AlertDialog.Builder(context).create();
        }
        if (mWarningDialog != null && !mWarningDialog.isShowing()) {
            mWarningDialog.setTitle(mContext.getText(R.string.call_setting_error));
            mWarningDialog.setMessage(mContext.getText(R.string.setting_not_supported));
            mWarningDialog.setCancelable(false);

            mWarningDialog.setButton(mContext.getText(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mWarningDialog.dismiss();
                        mWarningDialog = null;
                    }
                });

            // make the dialog more obvious by blurring the background.
            mWarningDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            mWarningDialog.show();
        }
    }

}
