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

package com.android.launcher3.compat;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.android.launcher3.R;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LauncherAppsCompatVL extends LauncherAppsCompat {

    private LauncherApps mLauncherApps;

    private Map<OnAppsChangedCallbackCompat, WrappedCallback> mCallbacks
            = new HashMap<OnAppsChangedCallbackCompat, WrappedCallback>();

    //Add by zhaopenglin for hide app in launcher20160330(start)
    private ArrayList<LauncherActivityInfoCompat> hidedCompatList =
        new ArrayList<LauncherActivityInfoCompat>(4);
    private Context mContext;
    private List<String> hideappsArrayList = new ArrayList<String>();
    //Add by zhaopenglin for hide app in launcher20160330(end)

    LauncherAppsCompatVL(Context context) {
        super();
        mLauncherApps = (LauncherApps) context.getSystemService("launcherapps");
        mContext = context;//Add by zhaopenglin for hide app in launcher20160330
    }

    public List<LauncherActivityInfoCompat> getActivityList(String packageName,
            UserHandleCompat user) {
        List<LauncherActivityInfo> list = mLauncherApps.getActivityList(packageName,
                user.getUser());
        if (list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList<LauncherActivityInfoCompat> compatList =
                new ArrayList<LauncherActivityInfoCompat>(list.size());
        //Add by zhaopenglin for hide app in launcher20160330(start)
        hideappsArrayList= Arrays.asList(mContext.getResources().getStringArray(R.array.hide_apps_arraylist));
        Log.i("hideapps","hideappsArrayList:"+hideappsArrayList);
        if(hideappsArrayList.size() > 0){
            for (LauncherActivityInfo info : list) {
                LauncherActivityInfoCompatVL launcherActivityInfoCompatVL =new LauncherActivityInfoCompatVL(info);
                if(!hideappsArrayList.contains((launcherActivityInfoCompatVL).getApplicationInfo().packageName)){
                    compatList.add(launcherActivityInfoCompatVL);
                }else {
                    hidedCompatList.add(launcherActivityInfoCompatVL);
                }
            }
        }else{
        //Add by zhaopenglin for hide app in launcher20160330(end)
            for (LauncherActivityInfo info : list) {
                compatList.add(new LauncherActivityInfoCompatVL(info));
            }
        }
        return compatList;
    }
    //Add by zhaopenglin for hide app in launcher20160330(start)
    public List<LauncherActivityInfoCompat> getHidedActivityList() {
        return hidedCompatList;
    }
    //Add by zhaopenglin for hide app in launcher20160330(end)
    public LauncherActivityInfoCompat resolveActivity(Intent intent, UserHandleCompat user) {
        LauncherActivityInfo activity = mLauncherApps.resolveActivity(intent, user.getUser());
        if (activity != null) {
            return new LauncherActivityInfoCompatVL(activity);
        } else {
            return null;
        }
    }

    public void startActivityForProfile(ComponentName component, UserHandleCompat user,
            Rect sourceBounds, Bundle opts) {
        mLauncherApps.startMainActivity(component, user.getUser(), sourceBounds, opts);
    }

    public void showAppDetailsForProfile(ComponentName component, UserHandleCompat user) {
        mLauncherApps.startAppDetailsActivity(component, user.getUser(), null, null);
    }

    public void addOnAppsChangedCallback(LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
        WrappedCallback wrappedCallback = new WrappedCallback(callback);
        synchronized (mCallbacks) {
            mCallbacks.put(callback, wrappedCallback);
        }
        mLauncherApps.registerCallback(wrappedCallback);
    }

    public void removeOnAppsChangedCallback(
            LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
        WrappedCallback wrappedCallback = null;
        synchronized (mCallbacks) {
            wrappedCallback = mCallbacks.remove(callback);
        }
        if (wrappedCallback != null) {
            mLauncherApps.unregisterCallback(wrappedCallback);
        }
    }

    public boolean isPackageEnabledForProfile(String packageName, UserHandleCompat user) {
        return mLauncherApps.isPackageEnabled(packageName, user.getUser());
    }

    public boolean isActivityEnabledForProfile(ComponentName component, UserHandleCompat user) {
        return mLauncherApps.isActivityEnabled(component, user.getUser());
    }

    private static class WrappedCallback extends LauncherApps.Callback {
        private LauncherAppsCompat.OnAppsChangedCallbackCompat mCallback;

        public WrappedCallback(LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
            mCallback = callback;
        }

        public void onPackageRemoved(String packageName, UserHandle user) {
            mCallback.onPackageRemoved(packageName, UserHandleCompat.fromUser(user));
        }

        public void onPackageAdded(String packageName, UserHandle user) {
            mCallback.onPackageAdded(packageName, UserHandleCompat.fromUser(user));
        }

        public void onPackageChanged(String packageName, UserHandle user) {
            mCallback.onPackageChanged(packageName, UserHandleCompat.fromUser(user));
        }

        public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {
            mCallback.onPackagesAvailable(packageNames, UserHandleCompat.fromUser(user), replacing);
        }

        public void onPackagesUnavailable(String[] packageNames, UserHandle user,
                boolean replacing) {
            mCallback.onPackagesUnavailable(packageNames, UserHandleCompat.fromUser(user),
                    replacing);
        }
    }
}

