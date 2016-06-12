package com.gcl.cltorch;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class TorchSettings extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
	
	public final static String PRE_FULL_SCREEN_SWITCH_STRING = "fullscreen_switch"; 
	public final static String PRE_NOTIFY_STRING = "notify"; 
	public final static String PRE_SOS_FREQUENCY = "sos_frequency"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		ListPreference listPref = (ListPreference) findPreference(PRE_SOS_FREQUENCY);
		updateSummary(listPref, listPref.getValue());
	    listPref.setOnPreferenceChangeListener(this);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		String key = preference.getKey();
		if (PRE_NOTIFY_STRING.equals(key)) {
			Intent intent = new Intent("com.android.torch_notify");
			sendBroadcast(intent);
		}
		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (PRE_SOS_FREQUENCY.equals(preference.getKey())) {
	         updateSummary(preference, (String) newValue);
        }
		return true;
	}
	
    private void updateSummary(Preference listPref, String value) {
    	String[] frequency_entries = getResources().getStringArray(R.array.sos_frequency_entries);
        String[] frequency_values = getResources().getStringArray(R.array.sos_frequency_values);
    	for(int i=0; i<frequency_values.length; i++) {
    		if(value.equals(frequency_values[i])) {
    			listPref.setSummary(frequency_entries[i]);
    		}
    	}
    }	
}
