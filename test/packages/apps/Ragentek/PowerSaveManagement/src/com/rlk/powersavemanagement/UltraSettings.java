package com.rlk.powersavemanagement;

import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

import java.util.List;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
public class UltraSettings extends PreferenceActivity implements
Preference.OnPreferenceChangeListener
{
  private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 30000;
  private static final String KEY_SCREEN_TIMEOUT = "screen_timeout";
  private static final String PROFILE_KEY = "profiles_set";
  private static final String ULTRA_SETTING_KEY = "ultra_setting";
  private static final String TAG = "PowerSave/UltraSettings";
  private PreferenceScreen mUltraPreScreen;
  private Preference mProfilePre;
  private ListPreference mScreenTimeoutPreference;
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ActionBar actionbar = getActionBar();
    if(actionbar!=null){
    	actionbar.setHomeButtonEnabled(true);
		//actionbar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
		//		| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE
		//		| ActionBar.DISPLAY_USE_LOGO);
		actionbar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE);
		actionbar.setDisplayHomeAsUpEnabled(true);
    }
    addPreferencesFromResource(R.xml.setting);
    mUltraPreScreen = (PreferenceScreen) findPreference(ULTRA_SETTING_KEY);
    mProfilePre = findPreference(PROFILE_KEY);
    mScreenTimeoutPreference = (ListPreference) findPreference(KEY_SCREEN_TIMEOUT);
    final long currentTimeout = getTimoutValue();
    Log.d(TAG,"currentTimeout=" + currentTimeout);
    mScreenTimeoutPreference.setValue(String.valueOf(currentTimeout));
    mScreenTimeoutPreference.setOnPreferenceChangeListener(this);
    mUltraPreScreen.removePreference(mScreenTimeoutPreference);
    //if(FeatureOption.RLK_TECNO_J7_SUPPORT){
    //mProfilePre.setTitle(getLabel());
    //}
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
	  switch(paramMenuItem.getItemId()){
		case android.R.id.home:
			finish();
			break;
		default:
		 break;
		}
    return super.onOptionsItemSelected(paramMenuItem);
  }

  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    if (PROFILE_KEY.equals(paramPreference.getKey()))
    {
      Intent localIntent = new Intent();
      if(/*FeatureOption.RLK_TECNO_J7_SUPPORT*/false){
    	  localIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$EditprofileSettingsActivity"));  
      }else{
	      localIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AudioProfileSettingsActivity"));
      }	  
      localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(localIntent);
    }
    return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
  }
  private String getLabel(){ 	
  	PackageManager mPm = getPackageManager();
  	    final Intent mainIntent = new Intent("com.rlk.powersave.PROFILE_SETTING", null);  
     	mainIntent.addCategory(Intent.CATEGORY_DEFAULT);
     	mainIntent.setPackage("com.android.settings");
     	List<ResolveInfo> appsList = mPm.queryIntentActivities(mainIntent, 0);
     	if(appsList!=null&&appsList.size()>0){
     		ResolveInfo info = appsList.get(0);
     		CharSequence label = info.activityInfo.loadLabel(getPackageManager());
     		return (String) label;
     	}
     	return getPackageName();
  }
  private ContentObserver mScreenTimeoutObserver = new ContentObserver(new Handler()){
      @Override
      public void onChange(boolean selfChange) {
          Log.d(TAG,"mScreenTimeoutObserver omChanged");
          int value=Settings.System.getInt(
                  getContentResolver(), SCREEN_OFF_TIMEOUT, FALLBACK_SCREEN_TIMEOUT_VALUE);
          mScreenTimeoutPreference.setValue(String.valueOf(value));
      }
  };
  private int getTimoutValue() {
      int currentValue = Settings.System.getInt(getContentResolver(), SCREEN_OFF_TIMEOUT,
              FALLBACK_SCREEN_TIMEOUT_VALUE);
      Log.d(TAG, "getTimoutValue()---currentValue=" + currentValue);
      int bestMatch = 0;
      int timeout = 0;
      final CharSequence[] valuesTimeout = mScreenTimeoutPreference
              .getEntryValues();
      for (int i = 0; i < valuesTimeout.length; i++) {
          timeout = Integer.parseInt(valuesTimeout[i].toString());
          if (currentValue == timeout) {
              return currentValue;
          } else {
              if (currentValue > timeout) {
                  bestMatch = i;
              }
          }
      }
      Log.d(TAG, "getTimoutValue()---bestMatch=" + bestMatch);
      return Integer.parseInt(valuesTimeout[bestMatch].toString());

  }

@Override
public boolean onPreferenceChange(Preference preference, Object newValue) {
	final String key = preference.getKey();
    if (KEY_SCREEN_TIMEOUT.equals(key)) {
        int value = Integer.parseInt((String) newValue);
        try {
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, value);
            mScreenTimeoutPreference.setValue(String.valueOf(value));
        } catch (NumberFormatException e) {
            Log.e(TAG, "could not persist screen timeout setting", e);
        }
    } 
	return true;
}
}
