package com.android.settings.emergencyassistant;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.*;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ListView;
import android.widget.*;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.R;

import com.android.internal.logging.MetricsLogger;

public class EmergencyAssistant extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private static final String TAG = "EmergencyAssistant";

    private static final int FALLBACK_EMERGENCY_INTERVAL_VALUE = 1;

    private static final String KEY_EDIT_EMERGENCY_MSG = "edit_emergency_message";
    private static final String KEY_EMERGENCY_MSG_TIMEOUT = "emergency_message_timeout";
    private static final String KEY_ADD_EMERGENCY_CONTACT = "add_emergency_contact";
    private static final String KEY_EMERGENCY_SWITCH = "emergency_switch";

    public static final String EMERGENCY_INTERVAL = Settings.Global.EMERGENCY_ASSISTANT_INTERVAL;
    public static final String EMERGENCY_ASSISTANT_STATE = Settings.Global.EMERGENCY_ASSISTANT_STATE;
    //private static int currentTimeout = 0;

    private Preference mEditEmergencyMsg;
    private Preference mAddEmergencyContact;
    private ListPreference mMsgIntervalPreference;
    private ListPreference mEmergencySwitchPreference;


    //private IntentFilter mIntentFilter;
    private Context mContext;

    public EmergencyAssistant(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.emergency_assistant);

        mEditEmergencyMsg = (Preference) findPreference(KEY_EDIT_EMERGENCY_MSG);
        mMsgIntervalPreference = (ListPreference) findPreference(KEY_EMERGENCY_MSG_TIMEOUT);
        mAddEmergencyContact = (Preference) findPreference(KEY_ADD_EMERGENCY_CONTACT);
        mEmergencySwitchPreference = (ListPreference) findPreference(KEY_EMERGENCY_SWITCH);

        final long currentTimeout = getTimoutValue();

        mMsgIntervalPreference.setValue(String.valueOf(currentTimeout));
        mMsgIntervalPreference.setOnPreferenceChangeListener(this);
        mMsgIntervalPreference.setOnPreferenceClickListener(this);
        updateTimeoutPreferenceDescription(currentTimeout);
        
        
        
        int currentValue = 
        	Settings.Global.getInt(getContentResolver(), EMERGENCY_ASSISTANT_STATE, -1);
        if (currentValue != 0 && currentValue != 1) {
        	currentValue = 0;
        }
        mEmergencySwitchPreference.setValue(String.valueOf(currentValue));
        mEmergencySwitchPreference.setOnPreferenceChangeListener(this);
        mEmergencySwitchPreference.setOnPreferenceClickListener(this);
    }

   
    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.EMERGENCY_ASSISTANT;
    }


    @Override
    public void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(Settings.Global.getUriFor(EMERGENCY_INTERVAL),
                false, mEmergencyIntervalObserver);
        final int currentTimeout = getTimoutValue();
        updateTimeoutPreference(currentTimeout);
    }

    @Override
    public void onPause() {
        super.onPause();     
        getContentResolver().unregisterContentObserver(mEmergencyIntervalObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private ContentObserver mEmergencyIntervalObserver = new ContentObserver(new Handler()){
        @Override
        public void onChange(boolean selfChange) {        
            int value=Settings.Global.getInt(getContentResolver(), EMERGENCY_INTERVAL, FALLBACK_EMERGENCY_INTERVAL_VALUE);
            updateTimeoutPreference(value);
        }

    };


    private int getTimoutValue() {
        int currentValue = Settings.Global.getInt(getContentResolver(), EMERGENCY_INTERVAL,
                FALLBACK_EMERGENCY_INTERVAL_VALUE);
       
        Log.d(TAG, "getTimoutValue()---currentValue=" + currentValue);
        int bestMatch = 0;
        int timeout = 0;
        final CharSequence[] valuesTimeout = mMsgIntervalPreference
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
        //Log.d(TAG, "getTimoutValue()---bestMatch=" + bestMatch);
        return Integer.parseInt(valuesTimeout[bestMatch].toString());

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void updateTimeoutPreference(int currentTimeout) {
        Log.d(TAG, "updateTimeoutPreference currentTimeout=" + currentTimeout);
        mMsgIntervalPreference.setValue(String.valueOf(currentTimeout));
        updateTimeoutPreferenceDescription(currentTimeout);
        AlertDialog dlg = (AlertDialog)mMsgIntervalPreference.getDialog();
        if (dlg == null || !dlg.isShowing()) {
            return;
        }
        ListView listview = dlg.getListView();
        int checkedItem = mMsgIntervalPreference.findIndexOfValue(
                mMsgIntervalPreference.getValue());
        if (checkedItem > -1) {
            listview.setItemChecked(checkedItem, true);
            listview.setSelection(checkedItem);
        }
    }

    private void updateTimeoutPreferenceDescription(long currentTimeout) {
        ListPreference preference = mMsgIntervalPreference;
        String summary;
        if (currentTimeout < 0) {
            // Unsupported value
            summary = "";
        } else {
            final CharSequence[] entries = preference.getEntries();
            final CharSequence[] values = preference.getEntryValues();
            if (entries == null || entries.length == 0) {
                summary = "";
            } else {
                int best = 0;
                for (int i = 0; i < values.length; i++) {
                    long timeout = Long.parseLong(values[i].toString());
                    if (currentTimeout >= timeout) {
                        best = i;
                    }
                }
                ///M: to prevent index out of bounds @{
                if (entries.length != 0) {
                    summary = preference.getContext().getString(
                            R.string.emergency_interval_summary, entries[best]);
                } else {
                    summary = "";
                }
                ///M: @}

            }
        }
        preference.setSummary(summary);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        //Log.d(TAG,"onPreferenceChange key=" + key + ", mMsgIntervalPreference=" + mMsgIntervalPreference);
        if (KEY_EMERGENCY_MSG_TIMEOUT.equals(key)) {
            int value = Integer.parseInt((String) objValue);
            Log.d(TAG,"KEY_EMERGENCY_MSG_TIMEOUT: onPreferenceChange value = " + value);
            try {
                Settings.Global.putInt(getContentResolver(), EMERGENCY_INTERVAL, value);
                //currentTimeout = value;
                updateTimeoutPreferenceDescription(value);
            } catch (NumberFormatException e) {
                Log.e(TAG, "could not persist screen timeout setting", e);
            }
        } else if (KEY_EMERGENCY_SWITCH.equals(key)) {
        	Log.d(TAG,"onPreferenceChange:key = KEY_EMERGENCY_SWITCH");
        	int value = Integer.parseInt((String) objValue);
        	Log.d(TAG,"KEY_EMERGENCY_SWITCH: onPreferenceChange value = " + value);
        	try {
        		Settings.Global.putInt(getContentResolver(), EMERGENCY_ASSISTANT_STATE, value);
        	} catch (NumberFormatException e) {
        		Log.d(TAG,"could not persist emergency assistant state setting", e);
        	}	
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        Intent intent;
        if (preference == mEditEmergencyMsg) {
            intent = new Intent(getActivity(), EmergencyEditMsgActivity.class);
            startActivity(intent);

        } else if (preference == mMsgIntervalPreference) {
            Log.d(TAG, "EmergencyAssistant : onPreferenceTreeClick");

        } else if (preference == mAddEmergencyContact) {
            intent = new Intent(getActivity(), EmergencyAddContactActivity.class);
            startActivity(intent);
        }

        return true;
    }

    
}
