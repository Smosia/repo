package com.mediatek.settings.fuelgauge;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;
import com.mediatek.settings.FeatureOption;

public class PowerUsageExts {

    private static final String TAG = "PowerUsageSummary";

    private static final String KEY_BACKGROUND_POWER_SAVING = "background_power_saving";
    // Declare the first preference BgPowerSavingPrf order here,
    // other preference order over this value.
    private static final int PREFERENCE_ORDER_FIRST = -100;
    private Context mContext;
    private PreferenceScreen mPowerUsageScreen;
    private SwitchPreference mBgPowerSavingPrf;
    //A:DELSLMY-686 guoshuai 20160308(start)
    private SwitchPreference mBatteryPercentPrf;
    private static final String KEY_BATTERY_PERCENTAGE = "battery_percentage";
    private static final String BATTERY_PERCENTAGE_ENABLE = "battery_percentage_enable";
    //A:DELSLMY-686 guoshuai 20160308(end)
    public PowerUsageExts(Context context, PreferenceScreen appListGroup) {
        mContext = context;
        mPowerUsageScreen = appListGroup;
    }

    // init power usage extends items
    public void initPowerUsageExtItems() {
        // background power saving
        if (FeatureOption.MTK_BG_POWER_SAVING_SUPPORT
                && FeatureOption.MTK_BG_POWER_SAVING_UI_SUPPORT) {
            mBgPowerSavingPrf = new SwitchPreference(mContext);
            mBgPowerSavingPrf.setKey(KEY_BACKGROUND_POWER_SAVING);
            mBgPowerSavingPrf.setTitle(R.string.bg_power_saving_title);
            mBgPowerSavingPrf.setOrder(PREFERENCE_ORDER_FIRST);
            mBgPowerSavingPrf.setChecked(Settings.System.getInt(mContext.getContentResolver(),
                    Settings.System.BG_POWER_SAVING_ENABLE, 1) != 0);
            mPowerUsageScreen.addPreference(mBgPowerSavingPrf);
        }
        //A:DELSLMY-686 guoshuai 20160308(start)
        if(mContext.getResources().getBoolean(R.bool.config_battery_percentage_support)){
            mBatteryPercentPrf = new SwitchPreference(mContext);
            mBatteryPercentPrf.setKey(KEY_BATTERY_PERCENTAGE);
            mBatteryPercentPrf.setTitle(R.string.battery_percent);
            mBatteryPercentPrf.setOrder(-3);
            if(mContext.getResources().getBoolean(R.bool.config_battery_percentage_display)){
                mBatteryPercentPrf.setChecked(Settings.System.getInt(mContext.getContentResolver(), 
                    BATTERY_PERCENTAGE_ENABLE, 1) != 0);
            }else{
                mBatteryPercentPrf.setChecked(Settings.System.getInt(mContext.getContentResolver(),
                    BATTERY_PERCENTAGE_ENABLE, 0) != 0);
            }
            mPowerUsageScreen.addPreference(mBatteryPercentPrf);
        }
        //A:DELSLMY-686 guoshuai 20160308(end)
    }

    // on click
    public boolean onPowerUsageExtItemsClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (KEY_BACKGROUND_POWER_SAVING.equals(preference.getKey())) {
            if (preference instanceof SwitchPreference) {
                SwitchPreference pref = (SwitchPreference) preference;
                int bgState = pref.isChecked() ? 1 : 0;
                Log.d(TAG, "background power saving state: " + bgState);
                Settings.System.putInt(mContext.getContentResolver(),
                        Settings.System.BG_POWER_SAVING_ENABLE, bgState);
                if (mBgPowerSavingPrf != null) {
                    mBgPowerSavingPrf.setChecked(pref.isChecked());
                }
            }
            // If user click on PowerSaving preference just return here
            return true;
        //A:DELSLMY-686 guoshuai 20160308(start)
        } else if(KEY_BATTERY_PERCENTAGE.equals(preference.getKey())){
            if(preference instanceof SwitchPreference){
                SwitchPreference pref = (SwitchPreference) preference;
                int state = pref.isChecked() ? 1 : 0;
                Settings.System.putInt(mContext.getContentResolver(), BATTERY_PERCENTAGE_ENABLE, state);
                if (mBatteryPercentPrf != null) {
                    mBatteryPercentPrf.setChecked(pref.isChecked());
                }
            }
            return true;
        }
        //A:DELSLMY-686 guoshuai 20160308(end)
        return false;
    }
}
