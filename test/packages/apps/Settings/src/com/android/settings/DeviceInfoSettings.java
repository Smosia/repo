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

package com.android.settings;

import com.android.settings.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.SELinux;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.StatFs;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.telephony.CarrierConfigManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.util.MemInfoReader;
import com.android.internal.logging.MetricsLogger;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Index;
import com.android.settings.search.Indexable;

import com.mediatek.settings.deviceinfo.DeviceInfoSettingsExts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//fota start
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
//fota end

public class DeviceInfoSettings extends SettingsPreferenceFragment implements Indexable {

    private static final String LOG_TAG = "DeviceInfoSettings";
    private static final String FILENAME_PROC_VERSION = "/proc/version";
    private static final String FILENAME_MSV = "/sys/board_properties/soc/msv";

    private static final String KEY_REGULATORY_INFO = "regulatory_info";
    private static final String KEY_SYSTEM_UPDATE_SETTINGS = "system_update_settings";
    private static final String PROPERTY_URL_SAFETYLEGAL = "ro.url.safetylegal";
    private static final String PROPERTY_SELINUX_STATUS = "ro.build.selinux";
    private static final String KEY_KERNEL_VERSION = "kernel_version";
    private static final String KEY_BUILD_NUMBER = "build_number";
    private static final String KEY_DEVICE_MODEL = "device_model";
    private static final String KEY_SELINUX_STATUS = "selinux_status";
    private static final String KEY_BASEBAND_VERSION = "baseband_version";
    private static final String KEY_FIRMWARE_VERSION = "firmware_version";
    private static final String KEY_SECURITY_PATCH = "security_patch";
    private static final String KEY_UPDATE_SETTING = "additional_system_update_settings";
    private static final String KEY_EQUIPMENT_ID = "fcc_equipment_id";
    private static final String PROPERTY_EQUIPMENT_ID = "ro.ril.fccid";
    private static final String KEY_DEVICE_FEEDBACK = "device_feedback";
    private static final String KEY_SAFETY_LEGAL = "safetylegal";
    //A: kuangyunfeng 20160111(start)
    private static final String KEY_RGK_OTA_UPDATE_SETTINGS = "OTA_update_settings";
    //A: kuangyunfeng 20160111(end)
	private static final String KEY_CUSTOM_BUILD_VERSION = "custom_build_version";
	//A by shubin for DWYQLSSB-111(start)
	private static final String KEY_ROM="rom";
    private static final String KEY_RAM="ram";
    private static final String KEY_BUILD_TIME="build_time";
	//A by shubin for DWYQLSSB-111(end)
    static final int TAPS_TO_BE_A_DEVELOPER = 7;

    long[] mHits = new long[3];
    int mDevHitCountdown;
    Toast mDevHitToast;

    private DeviceInfoSettingsExts mExts;

    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.DEVICEINFO;
    }

    @Override
    protected int getHelpResource() {
        return R.string.help_uri_about;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.device_info_settings);
        //A by shubin for DWYQLSSB-111(start)
        if (getResources().getBoolean(R.bool.show_aboutphone_rom)) {
       	 MemInfoReader mMemInfoReader = new MemInfoReader();
            mMemInfoReader.readMemInfo();
            long date_time= SystemProperties.getLong("ro.build.date.utc",0);
            Date date=new Date(date_time);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(date_time);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date_date = new Date();
            try {
            	  date_date = sdf.parse(String.valueOf(date_time));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
         
            String date_string=sdf.format(date_date);
			setStringSummary(KEY_ROM, getTotalInternalMemorySize());
			long totalRam = mMemInfoReader.getTotalSize();
			 if(totalRam < 512*1024*1024){
		        	totalRam = 512*1024*1024;
		        }else if(totalRam < 1*1024*1024*1024){
		        	totalRam = 1*1024*1024*1024;
		        }else if(totalRam < 2*1024*1024*1024){
		        	totalRam = 2*1024*1024*1024;
		        }else if(totalRam < 4*1024*1024*1024){
		        	totalRam = 4*1024*1024*1024;
		        }
			setStringSummary(KEY_RAM,  Formatter.formatFileSize(getActivity(), totalRam));
			setStringSummary(KEY_BUILD_TIME, date_string );
		}else{
			removePreference(KEY_ROM);
			removePreference(KEY_RAM);
			removePreference(KEY_BUILD_TIME);
		}
        //A by shubin for DWYQLSSB-111(end)
        setStringSummary(KEY_FIRMWARE_VERSION, Build.VERSION.RELEASE);
        findPreference(KEY_FIRMWARE_VERSION).setEnabled(true);
        String patch = Build.VERSION.SECURITY_PATCH;
		String custom = getResources().getString(R.string.custom);
        if (!"".equals(patch) && !("qmobile").equals(custom)) {
            try {
                SimpleDateFormat template = new SimpleDateFormat("yyyy-MM-dd");
                Date patchDate = template.parse(patch);
                String format = DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy");
                patch = DateFormat.format(format, patchDate).toString();
            } catch (ParseException e) {
                // broken parse; fall through and use the raw string
            }
            setStringSummary(KEY_SECURITY_PATCH, patch);
        } else {
            getPreferenceScreen().removePreference(findPreference(KEY_SECURITY_PATCH));

        }
        setValueSummary(KEY_BASEBAND_VERSION, "gsm.version.baseband");
        setStringSummary(KEY_DEVICE_MODEL, Build.MODEL + getMsvSuffix());
        setValueSummary(KEY_EQUIPMENT_ID, PROPERTY_EQUIPMENT_ID);
        setStringSummary(KEY_DEVICE_MODEL, Build.MODEL);
        //A: DWYJLSYM-30 kuangyunfeng 20160406(start)
        boolean removeBuildNumber = getResources().getBoolean(R.bool.config_remove_build_number);
        if (removeBuildNumber) {
            getPreferenceScreen().removePreference(findPreference(KEY_BUILD_NUMBER));
            //A:DWYSLM-590 yejianming 20160419
            findPreference(KEY_KERNEL_VERSION).setEnabled(true);
        } else {
        //A: DWYJLSYM-30 kuangyunfeng 20160406(end)
        //M: by kuangyunfeng for blu build NO. and FOTA version NO. consistently{
        //setStringSummary(KEY_BUILD_NUMBER, Build.DISPLAY);
        if ("1".equals(SystemProperties.get("ro.adups_fota_support"))) {
            String s = SystemProperties.get("ro.blu.build.number.date","") + " " + SystemProperties.get("ro.blu.build.number.time","");
            setStringSummary(KEY_BUILD_NUMBER, Build.DISPLAY + "\n" + s);
        } else {
            setStringSummary(KEY_BUILD_NUMBER, Build.DISPLAY);
        }
        //M: by kuangyunfeng for blu build NO. and FOTA version NO. consistently}
        if(getResources().getBoolean(R.bool.config_custom_build_version)){
		        findPreference(KEY_CUSTOM_BUILD_VERSION).setEnabled(true);
	    } else {
        	findPreference(KEY_BUILD_NUMBER).setEnabled(true);
	    }
        //A: DWYJLSYM-30 kuangyunfeng 20160406(start)
        }
        //A: DWYJLSYM-30 kuangyunfeng 20160406(end)
        findPreference(KEY_KERNEL_VERSION).setSummary(getFormattedKernelVersion());

        if (!SELinux.isSELinuxEnabled()) {
            String status = getResources().getString(R.string.selinux_status_disabled);
            setStringSummary(KEY_SELINUX_STATUS, status);
        } else if (!SELinux.isSELinuxEnforced()) {
            String status = getResources().getString(R.string.selinux_status_permissive);
            setStringSummary(KEY_SELINUX_STATUS, status);
        }

        // Remove selinux information if property is not present
        removePreferenceIfPropertyMissing(getPreferenceScreen(), KEY_SELINUX_STATUS,
                PROPERTY_SELINUX_STATUS);

        // Remove Safety information preference if PROPERTY_URL_SAFETYLEGAL is not set
        removePreferenceIfPropertyMissing(getPreferenceScreen(), KEY_SAFETY_LEGAL,
                PROPERTY_URL_SAFETYLEGAL);

        // Remove Equipment id preference if FCC ID is not set by RIL
        removePreferenceIfPropertyMissing(getPreferenceScreen(), KEY_EQUIPMENT_ID,
                PROPERTY_EQUIPMENT_ID);

        // Remove Baseband version if wifi-only device
        if (Utils.isWifiOnly(getActivity()) ||("qmobile").equals(custom)) {
            getPreferenceScreen().removePreference(findPreference(KEY_BASEBAND_VERSION));
        }

        // Dont show feedback option if there is no reporter.
        if (TextUtils.isEmpty(getFeedbackReporterPackage(getActivity()))) {
            getPreferenceScreen().removePreference(findPreference(KEY_DEVICE_FEEDBACK));
        }

        //A: kuangyunfeng 20160111(start)
        if (!"1".equals(SystemProperties.get("ro.rgk_ota_support"))) {
            removePreference(KEY_RGK_OTA_UPDATE_SETTINGS);
        }
        //A: kuangyunfeng 20160111(end)

        /*
         * Settings is a generic app and should not contain any device-specific
         * info.
         */
        final Activity act = getActivity();

        // These are contained by the root preference screen
        PreferenceGroup parentPreference = getPreferenceScreen();
        if (UserHandle.myUserId() == UserHandle.USER_OWNER) {
            Utils.updatePreferenceToSpecificActivityOrRemove(act, parentPreference,
                    KEY_SYSTEM_UPDATE_SETTINGS,
                    Utils.UPDATE_PREFERENCE_FLAG_SET_TITLE_TO_MATCHING_ACTIVITY);
        } else {
            // Remove for secondary users
            removePreference(KEY_SYSTEM_UPDATE_SETTINGS);
        }

        // Read platform settings for additional system update setting
        removePreferenceIfBoolFalse(KEY_UPDATE_SETTING,
                R.bool.config_additional_system_update_setting_enable);

        // Remove regulatory information if none present.
        final Intent intent = new Intent(Settings.ACTION_SHOW_REGULATORY_INFO);
        if (getPackageManager().queryIntentActivities(intent, 0).isEmpty()) {
            Preference pref = findPreference(KEY_REGULATORY_INFO);
            if (pref != null) {
                getPreferenceScreen().removePreference(pref);
            }
        }

        ///M:
        mExts = new DeviceInfoSettingsExts(getActivity(), this);
        mExts.initMTKCustomization(getPreferenceScreen());
        //fota start
        if(!isApkExist(act, "com.adups.fota")){
            if(findPreference("adupsfota_software_update") != null){
        	    getPreferenceScreen().removePreference(findPreference("adupsfota_software_update"));
            }
        } else {
		    Preference preference = findPreference("adupsfota_software_update");
			if (preference != null) {
		        preference.setTitle(getAppName(act, "com.adups.fota"));
			}
		}
        //fota end
    }
    //A by shubin for DWYQLSSB-111 (start)
    private String getTotalInternalMemorySize(){
    	File path=Environment.getDataDirectory();
    	StatFs stat=new StatFs(path.getPath());
    	long blockSize=stat.getBlockSize();
    	long totalBlocks=stat.getBlockCount();
    	long total_rom= totalBlocks*blockSize;
    	if (total_rom <= 4294967500l) {
    		//4*1024*1024*1024
			total_rom = 4294967500l;
		}else if (total_rom <= 8589935000l) {
			//8*1024*1024*1024
			total_rom = 8589935000l;
		}else if (total_rom <= 17179870000l) {
			//16*1024*1024*1024
			total_rom =  17179870000l;
		}else if (total_rom <= 34359740000l) {
			//32*1024*1024*1024
			total_rom = 34359740000l;
		}else {
			total_rom=totalBlocks*blockSize;
		}
    
    	return Formatter.formatFileSize(getActivity(), total_rom);
    	
    }
    //A by shubin for DWYQLSSB-111 (end)
    @Override
    public void onResume() {
        super.onResume();
        mDevHitCountdown = getActivity().getSharedPreferences(DevelopmentSettings.PREF_FILE,
                Context.MODE_PRIVATE).getBoolean(DevelopmentSettings.PREF_SHOW,
                        android.os.Build.TYPE.equals("eng")) ? -1 : TAPS_TO_BE_A_DEVELOPER;
        mDevHitToast = null;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().equals(KEY_FIRMWARE_VERSION)) {
            System.arraycopy(mHits, 1, mHits, 0, mHits.length-1);
            mHits[mHits.length-1] = SystemClock.uptimeMillis();
            if (mHits[0] >= (SystemClock.uptimeMillis()-500)) {
                UserManager um = (UserManager) getActivity().getSystemService(Context.USER_SERVICE);
                if (um.hasUserRestriction(UserManager.DISALLOW_FUN)) {
                    Log.d(LOG_TAG, "Sorry, no fun for you!");
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("android",
                        com.android.internal.app.PlatLogoActivity.class.getName());
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Unable to start activity " + intent.toString());
                }
            }
            //M:DWYSLM-590 yejianming 20160419 (start)
        } else if (preference.getKey().equals(KEY_BUILD_NUMBER) ||
        preference.getKey().equals(KEY_KERNEL_VERSION)||preference.getKey().equals(KEY_CUSTOM_BUILD_VERSION)) {
            //M:DWYSLM-590 yejianming 20160419 (end)
            // Don't enable developer options for secondary users.
            if (UserHandle.myUserId() != UserHandle.USER_OWNER) return true;

            // Don't enable developer options until device has been provisioned
            if (Settings.Global.getInt(getActivity().getContentResolver(),
                    Settings.Global.DEVICE_PROVISIONED, 0) == 0) {
                return true;
            }

            final UserManager um = (UserManager) getSystemService(Context.USER_SERVICE);
            if (um.hasUserRestriction(UserManager.DISALLOW_DEBUGGING_FEATURES)) return true;

            if (mDevHitCountdown > 0) {
                mDevHitCountdown--;
                if (mDevHitCountdown == 0) {
                    getActivity().getSharedPreferences(DevelopmentSettings.PREF_FILE,
                            Context.MODE_PRIVATE).edit().putBoolean(
                                    DevelopmentSettings.PREF_SHOW, true).apply();
                    if (mDevHitToast != null) {
                        mDevHitToast.cancel();
                    }
                    mDevHitToast = Toast.makeText(getActivity(), R.string.show_dev_on,
                            Toast.LENGTH_LONG);
                    mDevHitToast.show();
                    // This is good time to index the Developer Options
                    Index.getInstance(
                            getActivity().getApplicationContext()).updateFromClassNameResource(
                                    DevelopmentSettings.class.getName(), true, true);

                } else if (mDevHitCountdown > 0
                        && mDevHitCountdown < (TAPS_TO_BE_A_DEVELOPER-2)) {
                    if (mDevHitToast != null) {
                        mDevHitToast.cancel();
                    }
                    mDevHitToast = Toast.makeText(getActivity(), getResources().getQuantityString(
                            R.plurals.show_dev_countdown, mDevHitCountdown, mDevHitCountdown),
                            Toast.LENGTH_SHORT);
                    mDevHitToast.show();
                }
            } else if (mDevHitCountdown < 0) {
                if (mDevHitToast != null) {
                    mDevHitToast.cancel();
                }
                mDevHitToast = Toast.makeText(getActivity(), R.string.show_dev_already,
                        Toast.LENGTH_LONG);
                mDevHitToast.show();
            }
        } else if (preference.getKey().equals(KEY_DEVICE_FEEDBACK)) {
            sendFeedback();
        } else if(preference.getKey().equals(KEY_SYSTEM_UPDATE_SETTINGS)) {
            CarrierConfigManager configManager =
                    (CarrierConfigManager) getSystemService(Context.CARRIER_CONFIG_SERVICE);
            PersistableBundle b = configManager.getConfig();
            if (b.getBoolean(CarrierConfigManager.KEY_CI_ACTION_ON_SYS_UPDATE_BOOL)) {
                ciActionOnSysUpdate(b);
            }
        }
        //A: kuangyunfeng 20160111(start)
        else if(preference.getKey().equals(KEY_RGK_OTA_UPDATE_SETTINGS)){
            Intent intent = new Intent(Intent.ACTION_MAIN);		
            if ("1".equals(SystemProperties.get("ro.rgk_ota_support"))) {
                intent.setClassName("com.android.ota","com.android.ota.UpdateSystem");
                startActivity(intent);
            }
        }
        //A: kuangyunfeng 20160111(end)
        ///M:
        mExts.onCustomizedPreferenceTreeClick(preference);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    /**
     * Trigger client initiated action (send intent) on system update
     */
    private void ciActionOnSysUpdate(PersistableBundle b) {
        String intentStr = b.getString(CarrierConfigManager.
                KEY_CI_ACTION_ON_SYS_UPDATE_INTENT_STRING);
        if (!TextUtils.isEmpty(intentStr)) {
            String extra = b.getString(CarrierConfigManager.
                    KEY_CI_ACTION_ON_SYS_UPDATE_EXTRA_STRING);
            String extraVal = b.getString(CarrierConfigManager.
                    KEY_CI_ACTION_ON_SYS_UPDATE_EXTRA_VAL_STRING);

            Intent intent = new Intent(intentStr);
            if (!TextUtils.isEmpty(extra)) {
                intent.putExtra(extra, extraVal);
            }
            Log.d(LOG_TAG, "ciActionOnSysUpdate: broadcasting intent " + intentStr +
                    " with extra " + extra + ", " + extraVal);
            getActivity().getApplicationContext().sendBroadcast(intent);
        }
    }

    private void removePreferenceIfPropertyMissing(PreferenceGroup preferenceGroup,
            String preference, String property ) {
        if (SystemProperties.get(property).equals("")) {
            // Property is missing so remove preference from group
            try {
                preferenceGroup.removePreference(findPreference(preference));
            } catch (RuntimeException e) {
                Log.d(LOG_TAG, "Property '" + property + "' missing and no '"
                        + preference + "' preference");
            }
        }
    }

    private void removePreferenceIfBoolFalse(String preference, int resId) {
        if (!getResources().getBoolean(resId)) {
            Preference pref = findPreference(preference);
            if (pref != null) {
                getPreferenceScreen().removePreference(pref);
            }
        }
    }

    private void setStringSummary(String preference, String value) {
        try {
            findPreference(preference).setSummary(value);
        } catch (RuntimeException e) {
            findPreference(preference).setSummary(
                getResources().getString(R.string.device_info_default));
        }
    }

    private void setValueSummary(String preference, String property) {
        try {
            findPreference(preference).setSummary(
                    SystemProperties.get(property,
                            getResources().getString(R.string.device_info_default)));
        } catch (RuntimeException e) {
            // No recovery
        }
    }

    private void sendFeedback() {
        String reporterPackage = getFeedbackReporterPackage(getActivity());
        if (TextUtils.isEmpty(reporterPackage)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_BUG_REPORT);
        intent.setPackage(reporterPackage);
        startActivityForResult(intent, 0);
    }

    /**
     * Reads a line from the specified file.
     * @param filename the file to read from
     * @return the first line, if any.
     * @throws IOException if the file couldn't be read
     */
    private static String readLine(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename), 256);
        try {
            return reader.readLine();
        } finally {
            reader.close();
        }
    }

    public static String getFormattedKernelVersion() {
        try {
            return formatKernelVersion(readLine(FILENAME_PROC_VERSION));

        } catch (IOException e) {
            Log.e(LOG_TAG,
                "IO Exception when getting kernel version for Device Info screen",
                e);

            return "Unavailable";
        }
    }

    public static String formatKernelVersion(String rawKernelVersion) {
        // Example (see tests for more):
        // Linux version 3.0.31-g6fb96c9 (android-build@xxx.xxx.xxx.xxx.com) \
        //     (gcc version 4.6.x-xxx 20120106 (prerelease) (GCC) ) #1 SMP PREEMPT \
        //     Thu Jun 28 11:02:39 PDT 2012

        final String PROC_VERSION_REGEX =
            "Linux version (\\S+) " + /* group 1: "3.0.31-g6fb96c9" */
            "\\((\\S+?)\\) " +        /* group 2: "x@y.com" (kernel builder) */
            "(?:\\(gcc.+? \\)) " +    /* ignore: GCC version information */
            "(#\\d+) " +              /* group 3: "#1" */
            "(?:.*?)?" +              /* ignore: optional SMP, PREEMPT, and any CONFIG_FLAGS */
            "((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)"; /* group 4: "Thu Jun 28 11:02:39 PDT 2012" */

        Matcher m = Pattern.compile(PROC_VERSION_REGEX).matcher(rawKernelVersion);
        if (!m.matches()) {
            Log.e(LOG_TAG, "Regex did not match on /proc/version: " + rawKernelVersion);
            return "Unavailable";
        } else if (m.groupCount() < 4) {
            Log.e(LOG_TAG, "Regex match on /proc/version only returned " + m.groupCount()
                    + " groups");
            return "Unavailable";
        }
        return m.group(1); /* + "\n" +                 // 3.0.31-g6fb96c9
            m.group(2) + " " + m.group(3) + "\n" + // x@y.com #1
            m.group(4);                            // Thu Jun 28 11:02:39 PDT 2012 */
    }

    /**
     * Returns " (ENGINEERING)" if the msv file has a zero value, else returns "".
     * @return a string to append to the model number description.
     */
    private String getMsvSuffix() {
        // Production devices should have a non-zero value. If we can't read it, assume it's a
        // production device so that we don't accidentally show that it's an ENGINEERING device.
        try {
            String msv = readLine(FILENAME_MSV);
            // Parse as a hex number. If it evaluates to a zero, then it's an engineering build.
            if (Long.parseLong(msv, 16) == 0) {
                return " (ENGINEERING)";
            }
        } catch (IOException ioe) {
            // Fail quietly, as the file may not exist on some devices.
        } catch (NumberFormatException nfe) {
            // Fail quietly, returning empty string should be sufficient
        }
        return "";
    }

    private static String getFeedbackReporterPackage(Context context) {
        final String feedbackReporter =
                context.getResources().getString(R.string.oem_preferred_feedback_reporter);
        if (TextUtils.isEmpty(feedbackReporter)) {
            // Reporter not configured. Return.
            return feedbackReporter;
        }
        // Additional checks to ensure the reporter is on system image, and reporter is
        // configured to listen to the intent. Otherwise, dont show the "send feedback" option.
        final Intent intent = new Intent(Intent.ACTION_BUG_REPORT);

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolvedPackages =
                pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
        for (ResolveInfo info : resolvedPackages) {
            if (info.activityInfo != null) {
                if (!TextUtils.isEmpty(info.activityInfo.packageName)) {
                    try {
                        ApplicationInfo ai = pm.getApplicationInfo(info.activityInfo.packageName, 0);
                        if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                            // Package is on the system image
                            if (TextUtils.equals(
                                        info.activityInfo.packageName, feedbackReporter)) {
                                return feedbackReporter;
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                         // No need to do anything here.
                    }
                }
            }
        }
        return null;
    }

    /**
     * For Search.
     */
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {

            @Override
            public List<SearchIndexableResource> getXmlResourcesToIndex(
                    Context context, boolean enabled) {
                final SearchIndexableResource sir = new SearchIndexableResource(context);
                sir.xmlResId = R.xml.device_info_settings;
                return Arrays.asList(sir);
            }

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                final List<String> keys = new ArrayList<String>();
                if (isPropertyMissing(PROPERTY_SELINUX_STATUS)) {
                    keys.add(KEY_SELINUX_STATUS);
                }
                if (isPropertyMissing(PROPERTY_URL_SAFETYLEGAL)) {
                    keys.add(KEY_SAFETY_LEGAL);
                }
                if (isPropertyMissing(PROPERTY_EQUIPMENT_ID)) {
                    keys.add(KEY_EQUIPMENT_ID);
                }
                // Remove Baseband version if wifi-only device
                if (Utils.isWifiOnly(context)) {
                    keys.add((KEY_BASEBAND_VERSION));
                }
                // Dont show feedback option if there is no reporter.
                if (TextUtils.isEmpty(getFeedbackReporterPackage(context))) {
                    keys.add(KEY_DEVICE_FEEDBACK);
                }
                if (UserHandle.myUserId() != UserHandle.USER_OWNER) {
                    keys.add(KEY_SYSTEM_UPDATE_SETTINGS);
                }
                if (!context.getResources().getBoolean(
                        R.bool.config_additional_system_update_setting_enable)) {
                    keys.add(KEY_UPDATE_SETTING);
                }
                return keys;
            }

            private boolean isPropertyMissing(String property) {
                return SystemProperties.get(property).equals("");
            }
        };

	
    //fota start
    private boolean isApkExist(Context ctx, String packageName){
        PackageManager pm = ctx.getPackageManager();
        PackageInfo packageInfo = null;
        String versionName = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("FotaUpdate", "isApkExist not found");
            return false;
        }
		
        if (versionName != null) {
            String[] names = versionName.split("\\.");
            if (names.length >= 4 && "9".equals(names[3])) {
                return false;
            }
        }
        Log.i("FotaUpdate", "isApkExist = true");
        return true;
    }

    public String getAppName(Context ctx, String packageName) {
        PackageManager pm = ctx.getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            appInfo = null;
        }
		
        return (String) pm.getApplicationLabel(appInfo);
    }
    //fota end
}


