/*
 * Copyright (c) 2014 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

package com.trustonic.tsdk.sample;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.lang.Exception;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.util.Base64;

import com.trustonic.tbase.ota.sppa.ifc.Utils;
import com.trustonic.tbase.ota.sppa.ifc.SpPa;
import com.trustonic.tbase.ota.sppa.ifc.SpPaService.ProgressCb;
import com.trustonic.tbase.ota.sppa.ifc.SpPaService.ProgressState;
import com.trustonic.tbase.ota.sppa.ifc.IfcConstants;
import com.trustonic.tbase.ota.sppa.ifc.SpAppCbService;

// Class for Service Provider Application (SPApp) to interact with Service
// Provider Provisioning Agent (SpPa).
public class Provision {
    private Activity mActivity = null;
    private SpPa mSpPa = null;
    private Service mService = null;
    private ProvisionCb mPgCb = null;
    private ProvisionAppCb mPkgCb = null;
    private boolean mFinishedConnect = false;
    private static final int WAIT_TRY = 20;
    private static final int WAIT_PERIOD = 200;
    private boolean mPendingInstall = false;
    private boolean mInstallError = false;
    private String mEnrollmentUrl = null;
    private String mSeUrl = null;
    private String mSmUrl = null;
    private int mPid = 1;
    private SharedPreferences mPrefs = null;

    // SpPa callback for SPApp.
    public class ProvisionCb implements ProgressCb {
        public void progress(ProgressState state) {

            Log.d(Constants.LOG_TAG,
                    "Provision received from SpPa progress state " +
                    state + ".");

            switch(state) {
                case CONNECTED_ROOTPA:
                    Log.d(Constants.LOG_TAG, "Connected to RootPA service.");
                    mFinishedConnect = true;
                    break;
                case DISCONNECTED_ROOTPA:
                    Log.d(Constants.LOG_TAG,
                            "Disconnected to RootPA service.");
                    mFinishedConnect = false;
                    break;
                case CONNECTING_SE:
                    Log.d(Constants.LOG_TAG, "Connecting to Service Enabler.");
                    break;
                case CREATING_ROOT_CONTAINER:
                    Log.d(Constants.LOG_TAG, "Creating Root container.");
                    break;
                case CREATING_SP_CONTAINER:
                    Log.d(Constants.LOG_TAG, "Creating Sp container.");
                    break;
                case FINISHED_PROVISIONING:
                    Log.d(Constants.LOG_TAG,
                            "Provisioning has finished successfully.");
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                             TextView t;

                             t=(TextView)mActivity.findViewById(R.id.provisioningText);
                             if (t != null)
                             {
                                 t.setText(R.string.provisioning_ok_text);
                                 t.setTextColor(Color.GREEN);
                             }
                        }
                    });
                    break;
                case ERROR_ROOTPA:
                    showError("RootPA and/or Service Enabler operation " +
                            "failed.", false, true);
                    Log.e(Constants.LOG_TAG,
                            "Error: RootPA and/or Service Enabler operation failed.");
                    mFinishedConnect = false;
                    mInstallError = true;
                    break;
                case CONNECTING_SM:
                    Log.d(Constants.LOG_TAG, "Connecting to Service Manager.");
                    break;
                case EXECUTE_SM:
                    Log.d(Constants.LOG_TAG, "Execute Service Manager operation.");
                    break;
                case FINISHED_SM:
                    Log.d(Constants.LOG_TAG,
                            "Service Manager operation finished successfully.");
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putBoolean("ProvisionDone", mPendingInstall);
                    editor.commit();
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            TextView t;
                            t=(TextView)mActivity.findViewById(R.id.provisioningText);
                            if (t != null)
                            {
                                t.setText(R.string.provisioning_ok_text);
                            }

                            Button b;
                            b = (Button)mActivity.findViewById(R.id.home_button_aes);
                            if (b != null)
                            {
                                b.setEnabled(mPendingInstall);
                            }
                            b = (Button)mActivity.findViewById(R.id.home_button_rot13);
                            if (b != null)
                            {
                                b.setEnabled(mPendingInstall);
                            }
                            b = (Button)mActivity.findViewById(R.id.home_button_rsa);
                            if (b != null)
                            {
                                b.setEnabled(mPendingInstall);
                            }
                            b = (Button)mActivity.findViewById(R.id.home_button_sha256);
                            if (b != null)
                            {
                                b.setEnabled(mPendingInstall);
                            }
                            mPendingInstall = false;
                            if(android.os.Build.MODEL.equals(IfcConstants.QEMU_MODEL) == false) {
                                mActivity.setProgressBarIndeterminateVisibility(false);
                            }
                        }
                    });
                    break;
                case ERROR_SPPA:
                    showError("SPPA and/or Service Manager operation failed.",
                            false, true);
                    Log.e(Constants.LOG_TAG,
                            "Error: SpPa and/or Service Manager operation failed.");
                    mPendingInstall = false;
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            if(Constants.PROGRESS_INDICATOR_ENABLED) {
                                mActivity.setProgressBarIndeterminateVisibility(false);
                            }
                            mInstallError = true;
                        }
                    });
                    break;
                default:
                    showError("Unknow progress state received from SPPA.",
                            true, true);
                    Log.e(Constants.LOG_TAG,
                            "Error: unknow progress state received from SpPa.");
                    mFinishedConnect = false;
                    mPendingInstall = false;
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            if(Constants.PROGRESS_INDICATOR_ENABLED) {
                                mActivity.setProgressBarIndeterminateVisibility(false);
                            }
                            mInstallError = true;
                        }
                    });
                    break;
            }
            if(mInstallError) {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        TextView t;

                        t=(TextView)mActivity.findViewById(R.id.provisioningText);
                        if (t != null)
                        {
                            t.setText(R.string.provisioning_ko_text);
                            t.setTextColor(Color.RED);
                        }
                    }
                });
            }
        }
    }

    // SPApp callback for saving/deleting TA and personalization data.
    public class ProvisionAppCb implements SpAppCbService {
        public void saveTrustedApplicationBinary(byte[] uuid, byte[] data) {
            Log.d(Constants.LOG_TAG, "saveTrustedApplicationBinary.");
            SharedPreferences.Editor editor = mPrefs.edit();
            String sUuid = Utils.bytesToString(uuid, Utils.StringType.NAME);
            String sData = Base64.encodeToString(data, Base64.NO_WRAP);
            editor.putString(sUuid, sData);
            editor.commit();
    }

        public void savePersonalizationData(byte[] uuid, int pid, byte[] data) {
            Log.d(Constants.LOG_TAG, "savePersonalizationData.");
            mPid = pid;
            SharedPreferences.Editor editor = mPrefs.edit();
            String sCont = Utils.bytesToString(uuid, Utils.StringType.NAME);
            sCont.concat("." + dataContPid());
            String sData = Base64.encodeToString(data, Base64.NO_WRAP);
            editor.putString(sCont, sData);
            editor.commit();
        }

        public void deleteTrustedApplicationBinary(byte[] uuid) {
            Log.d(Constants.LOG_TAG, "deleteTrustedApplicationBinary.");
            SharedPreferences.Editor editor = mPrefs.edit();
            String sUuid = Utils.bytesToString(uuid, Utils.StringType.NAME);
            editor.remove(sUuid);
            editor.commit();
        }

        public void deletePersonalizationData(byte[] uuid, int pid) {
            Log.d(Constants.LOG_TAG, "deletePersonalizationData.");
            SharedPreferences.Editor editor = mPrefs.edit();
            String sCont = Utils.bytesToString(uuid, Utils.StringType.NAME);
            sCont.concat("." + dataContPid());
            editor.remove(sCont);
            editor.commit();
            Button b;
            b = (Button)mActivity.findViewById(R.id.home_button_aes);
            if (b != null)
            {
                b.setEnabled(false);
            }
            b = (Button)mActivity.findViewById(R.id.home_button_rot13);
            if (b != null)
            {
                b.setEnabled(false);
            }
            b = (Button)mActivity.findViewById(R.id.home_button_rsa);
            if (b != null)
            {
                b.setEnabled(false);
            }
            b = (Button)mActivity.findViewById(R.id.home_button_sha256);
            if (b != null)
            {
                b.setEnabled(false);
            }
        }
    }

    // Provision.
    public Provision(Activity activity, SharedPreferences prefs) {
        // Disable button.
        Button b;

            b = (Button)activity.findViewById(R.id.home_button_aes);
            if (b != null)
            {
                b.setEnabled(false);
            }
            b = (Button)activity.findViewById(R.id.home_button_rot13);
            if (b != null)
            {
                b.setEnabled(false);
            }
            b = (Button)activity.findViewById(R.id.home_button_rsa);
            if (b != null)
            {
                b.setEnabled(false);
            }
            b = (Button)activity.findViewById(R.id.home_button_sha256);
            if (b != null)
            {
                b.setEnabled(false);
            }

// Read SPApp info resources.
        if(readInfo(activity) == false) {
            return;
        }

        // Use prefered Service Enabler (SE) and Service Manager (SM) values.
        mPrefs = prefs;
        String seUrl = mPrefs.getString("Se", null);
        if(seUrl != null && seUrl.isEmpty() == false) {
            mSeUrl = seUrl;
        }
        String smUrl = mPrefs.getString("Sm", null);
        if(smUrl != null && smUrl.isEmpty() == false) {
            mSmUrl = smUrl;
        }

        // Create SPApp callback.
        mPkgCb = new ProvisionAppCb();

        mActivity = activity;

        // Create SPPA.
        mSpPa = new SpPa(mActivity, mSeUrl, mSmUrl);
        if(mSpPa == null) {
            showError("Initializing SPPA failed.", false, true);
            return;
        }

        // Create SPPA service for SPApp.
        mService = new Service(mSpPa);
        if(mService == null) {
            Log.e(Constants.LOG_TAG, "Error: creating SPPA Service failed.");
            mSpPa = null;
            return;
        }

        // Create SPPA state callback for SPApp.
        mPgCb = new ProvisionCb();

        // Connect to SPPA.
        if(!mSpPa.connect(mPgCb)) {
            Log.e(Constants.LOG_TAG, "Error: SPPA connection failed.");
            mSpPa = null;
            mService = null;
            mPgCb = null;
            return;
        }
    }

    // Show Error dialog.
    private void showError(String message, boolean showConsole,
            boolean showDialog) {
        if(showConsole) {
            Log.e(Constants.LOG_TAG, "Error: " + message);
        }
        // Show a dialog if not testing.
            if(showDialog) {
                AlertDialog.Builder builder =
                    new AlertDialog.Builder(mActivity);
                builder.setTitle(R.string.dialog_error).setMessage(message);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
    }

    // Read SPApp info resources.
    private boolean readInfo(Activity a) {
        String r;

        // Read SE URL.
        r = a.getResources().getString(R.string.se_url);
        if(r == null) {
            Log.e(Constants.LOG_TAG,
                    "Error: failed to read se_url resource.");
            return false;
        }
        mSeUrl = r;

        // Read SM URL.
        r = a.getResources().getString(R.string.sm_url);
        if(r == null) {
            Log.e(Constants.LOG_TAG,
                    "Error: failed to read sm_url resource.");
            return false;
        }
        mSmUrl = r;

        return true;
    }

    // Notice SPPA that SPApp is terminating.
    public void exit() {
        if(mSpPa == null) {
            return;
        }

        if(!mSpPa.disconnect()) {
            Log.e(Constants.LOG_TAG, "Error: SPPA disconnection failed.");
        }

        mSpPa.exit();
    }

    // Install containers required by SPApp.
    public void install() {
        Log.d(Constants.LOG_TAG, "install.");

        if((mService== null) || (mPendingInstall != false)) {
            Log.e(Constants.LOG_TAG, "Error: can't access to SPPA service.");
            return;
        }

        // Wait SPApp to connect to SPPA.
        AsyncTask<Void, Void, Boolean> task =
            new AsyncTask<Void, Void, Boolean>() {
                protected Boolean doInBackground(Void... params) {
                    for(int i = 0; i < WAIT_TRY; i++) {
                        if(mFinishedConnect) {
                            break;
                        } else {
                            try {
                                Thread.sleep(WAIT_PERIOD);
                            } catch (Exception e) {
                                return mFinishedConnect;
                            }
                        }
                    }
                    return mFinishedConnect;
                }

                protected void onPostExecute(Boolean ret) {
                    if(ret == false) {
                        showError("Waiting SPPA connection failed.",
                                true, true);
                        mPendingInstall = false;
                        mActivity.runOnUiThread(new Runnable() {
                            public void run() {
                                if(android.os.Build.MODEL.equals(IfcConstants.QEMU_MODEL) == false) {
                                    mActivity.setProgressBarIndeterminateVisibility(false);
                                }
                            }
                        });
                        return;
                    }
                    // Install.
                    mService.install(mPgCb, null, mPkgCb);
                }

                protected void onPreExecute() {
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            if(android.os.Build.MODEL.equals(IfcConstants.QEMU_MODEL) == false) {
                                mActivity.setProgressBarIndeterminateVisibility(true);
                            }
                        }
                    });
                    mPendingInstall = true;
                }
            };

        task.execute();
    }

    // Uninstall TA container of SPApp.
    public void uninstall() {
        Log.d(Constants.LOG_TAG, "uninstall.");

        if(mService == null) {
            Log.e(Constants.LOG_TAG, "Error: can't access to SPPA service.");
            return;
        }

        // Uninstall.
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if(android.os.Build.MODEL.equals(IfcConstants.QEMU_MODEL) == false) {
                    mActivity.setProgressBarIndeterminateVisibility(true);
                }
            }
        });
        mService.uninstall(mPgCb, null, mPkgCb);
    }

    // Get SPID of SPApp.
    public int spid() {
        if(mService == null) {
            Log.e(Constants.LOG_TAG,
                    "Error: can't access to SPPA service.");
            return -1; // FREE SPID
        }
        return mService.queryAppInfo().spid();
    }

    // Get Data container pid of SPApp.
    public String dataContPid() {
        return Integer.toString(mPid);
    }

    // Verify that no provisioning is ongoing.
    public boolean isRunning() {
        return mPendingInstall;
    }

    // Get SE url.
    public String se() {
        return mSeUrl;
    }

    // Get SM url.
    public String sm() {
        return mSmUrl;
    }
}
