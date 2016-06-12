/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.android.SettingOverride;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

/**
 * Application that sets the provisioned bit, like SetupWizard does.
 */
public class DefaultActivity extends Activity {
	
	public static final String TAG = "SettingOverride";

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        overriedSettings();

        // remove this activity from the package manager.
        PackageManager pm = getPackageManager();
        ComponentName name = new ComponentName(this, DefaultActivity.class);
        pm.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        //...
        startLauncher();
        // terminate the activity.
        finish();
    }
    
	private void startLauncher() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    
    private void overriedSettings(){
        Settings.System.putInt(getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT, getResources().getInteger(R.integer.def_screen_off_timeout));
         
        if (this.getResources().getBoolean(R.bool.def_location_off)) {		 
            setLocationEnabled();
            Settings.Secure.setLocationProviderEnabled(this.getContentResolver(),LocationManager.GPS_PROVIDER, false);
        }	
		//add bug_id:DELSLM-118 gaoxueyan 20160218 start
        if(getResources().getBoolean(R.bool.isSettingDefaultTimezone))
        {
        SharedPreferences settings=getSharedPreferences("defaulttimezone", 0);
        Boolean userfirst=settings.getBoolean("first", true);
         if(userfirst)
         {
        	settings.edit().putBoolean("first",false).commit();
        	final AlarmManager alarm =(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        	alarm.setTimeZone(getResources().getString(R.string.defaulttimezone));
        	
         }
        }
    }
    
    public boolean setLocationEnabled() {
    	Log.i(TAG,"setLocationEnabled");
        int currentUserId = ActivityManager.getCurrentUser();
        if (isUserLocationRestricted(currentUserId)) {
        	Log.i(TAG,"setLocationEnabled isUserLocationRestricted is true!");
            return false;
        }
        final ContentResolver cr = getContentResolver();

        return Settings.Secure
                .putIntForUser(cr, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF, currentUserId);
    }
    
    private boolean isUserLocationRestricted(int userId) {
        final UserManager um = (UserManager) getSystemService(Context.USER_SERVICE);
        return um.hasUserRestriction(
                UserManager.DISALLOW_SHARE_LOCATION,
                new UserHandle(userId));
    }
}

