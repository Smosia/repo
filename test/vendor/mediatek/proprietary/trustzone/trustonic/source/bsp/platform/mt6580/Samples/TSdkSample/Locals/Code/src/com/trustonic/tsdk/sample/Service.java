/*
 * Copyright (c) 2013 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

package com.trustonic.tsdk.sample;

import android.os.AsyncTask;
import android.util.Log;

import com.trustonic.tbase.ota.sppa.ifc.AppInfo;
import com.trustonic.tbase.ota.sppa.ifc.SpAppCbService;
import com.trustonic.tbase.ota.sppa.ifc.SpPa;
import com.trustonic.tbase.ota.sppa.ifc.SpPaService;

// Class implementing Service Provider Application (SPApp) operation with the
// Service Provider Provisioning Agent (SPPA).
public class Service implements SpPaService {
    private SpPa mSpPa = null;
    AsyncTask<Void, Void, Boolean> mTask = null;
    ServiceCb mServiceCb = null;
    ProgressCb mProvisionPgCb = null;
    String mProvisionPkgName = null;
    SpAppCbService mProvisionPkgCb = null;

    // SPPA callback for SPApp.
    public class ServiceCb implements ProgressCb {
        public void progress(ProgressState state) {
            if(mProvisionPgCb == null) {
                Log.d(Constants.LOG_TAG,
                        "Service received from SPPA progress state " +
                        state + ".");
            }

            switch(state) {
                case CONNECTED_ROOTPA:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    break;
                case CONNECTING_SE:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    break;
                case CREATING_ROOT_CONTAINER:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    break;
                case CREATING_SP_CONTAINER:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    break;
                case FINISHED_PROVISIONING:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    // Continue with Trusted Application (TA) and container
                    // installation.
                    installTa();
                    break;
                case ERROR_ROOTPA:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    mTask = null;
                    break;
                case CONNECTING_SM:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    break;
                case EXECUTE_SM:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    break;
                case FINISHED_SM:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    mTask = null;
                    break;
                case ERROR_SPPA:
                    if(mProvisionPgCb != null) mProvisionPgCb.progress(state);
                    mTask = null;
                    break;
                default:
                    Log.e(Constants.LOG_TAG,
                            "Error: unknow progress state received from SPPA.");
                    mTask = null;
            }
        }
    }

    // Service.
    public Service(SpPa sppa) {
        mSpPa = sppa;

        mServiceCb = new ServiceCb();
    }

    // Return SPApp info from SPPA.
    public AppInfo queryAppInfo() {
        if(mSpPa == null || !mSpPa.isReady()) {
            Log.e(Constants.LOG_TAG, "Error: can't access to SPPA service.");
            return null;
        }

        return mSpPa.queryAppInfo();
    }

    // Intall TA and container.
    private void installTa() {
        Log.d(Constants.LOG_TAG, "installTa");

        if(!mSpPa.isReady()) {
            Log.e(Constants.LOG_TAG, "Error: can't access to SPPA service.");
            return;
        }

        AsyncTask<Void, Void, Boolean> task =
            new AsyncTask<Void, Void, Boolean>() {
                protected Boolean doInBackground(Void... params) {
                    // Provision SPApp TA container.
                    return mSpPa.installTa(mServiceCb, mProvisionPkgName,
                            mProvisionPkgCb);
                }

                protected void onPostExecute(Boolean ret) {
                    if(ret == false) {
                        Log.e(Constants.LOG_TAG,
                                "Error: installTa failed.");
                        mServiceCb.progress(ProgressState.ERROR_SPPA);
                    } else {
                        Log.d(Constants.LOG_TAG,
                                "installTa sucessfull.");
                         mServiceCb.progress(ProgressState.FINISHED_SM);
                    }
                }

                protected void onPreExecute() {}
            };

        task.execute();
    }

    // Install SPApp TA.
    public void install(ProgressCb pgCb, String pkgName,
            SpAppCbService pkgCb) {
        Log.d(Constants.LOG_TAG, "install.");

        if(mSpPa == null || !mSpPa.isReady()) {
            Log.e(Constants.LOG_TAG, "Error: can't access to SPPA service.");
            return;
        }

        if((mTask != null)) {
            Log.e(Constants.LOG_TAG, "Error: SPPA service is busy.");
            return;
        }

        mProvisionPgCb = pgCb;
        mProvisionPkgName = pkgName;
        mProvisionPkgCb = pkgCb;

        mTask =
            new AsyncTask<Void, Void, Boolean>() {
                protected Boolean doInBackground(Void... params) {
                    // Provision SPApp Root and SP containers.
                    return mSpPa.provisionContainers(mServiceCb);
                }

                protected void onPostExecute(Boolean ret) {
                    if(ret == false) {
                        Log.e(Constants.LOG_TAG,
                                "Error: provisionContainers failed.");
                        return;
                    }
                }

                protected void onPreExecute() {}
            };

        mTask.execute();
    }

    // Uninstall SPApp TA.
    public void uninstall(ProgressCb cb, String pkgName,
            SpAppCbService pkgCb) {
        Log.d(Constants.LOG_TAG, "uninstall.");

        if(mSpPa == null || !mSpPa.isReady()) {
            Log.e(Constants.LOG_TAG, "Error: can't access to SPPA service.");
            return;
        }

        if(mTask != null) {
            Log.e(Constants.LOG_TAG, "Error: SPPA service is busy.");
            return;
        }

        mProvisionPgCb = cb;
        mProvisionPkgName = pkgName;
        mProvisionPkgCb = pkgCb;

        mTask =
            new AsyncTask<Void, Void, Boolean>() {
                protected Boolean doInBackground(Void... params) {
                    // Uninstall TA and container.
                    return mSpPa.uninstallTa(mServiceCb, mProvisionPkgName,
                            mProvisionPkgCb);
                }

                protected void onPostExecute(Boolean ret) {
                    if(ret == false) {
                        Log.e(Constants.LOG_TAG,
                                "Error: uninstallTaContainer failed.");
                        mServiceCb.progress(ProgressState.ERROR_SPPA);
                    } else {
                        mServiceCb.progress(ProgressState.FINISHED_SM);
                    }
                }

                protected void onPreExecute() {}
            };

        mTask.execute();
    }
}
