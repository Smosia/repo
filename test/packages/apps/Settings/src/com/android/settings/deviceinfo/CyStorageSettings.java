/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.settings.deviceinfo;




import android.app.ActivityManagerNative;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.format.Formatter.BytesResult;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.internal.logging.MetricsLogger;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.ManageApplications;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settings.search.SearchIndexableRaw;
import com.mediatek.settings.deviceinfo.StorageSettingsExts;
import android.app.ActivityManager.MemoryInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Panel showing both internal storage (both built-in storage and private
 * volumes) and removable storage (public volumes).
 */
public class CyStorageSettings extends SettingsPreferenceFragment implements Indexable {
    static final String TAG = "StorageSettings";

    private static final String TAG_VOLUME_UNMOUNTED = "volume_unmounted";
    private static final String TAG_DISK_INIT = "disk_init";
    private static boolean sHasOpened;

    static final int COLOR_PUBLIC = Color.parseColor("#ff00cd66");
    static final int COLOR_WARNING = Color.parseColor("#fff4511e");

    static final int[] COLOR_PRIVATE = new int[] {
            Color.parseColor("#ff1c86ee"),
            Color.parseColor("#ffab47bc"),
            Color.parseColor("#ff9acd32"),
            Color.parseColor("#ffec407a"),
            Color.parseColor("#ffc0ca33"),
    };

    private StorageManager mStorageManager;
    private Context context;
    private PreferenceCategory mInternalCategory;
    private PreferenceCategory mExternalCategory;
    //private CyMemoryPreference mPhoneMemPre;
    //private CyMemoryPreference mSdMemPre;
    private MemoryCleanPreference mCleanPre;
    private CyStorageRamPreference pref;
    private PreferenceScreen mRoot; 
    private StorageSummaryPreference mInternalSummary;
    private long totalSize;
    private StorageSettingsExts mCustomizationCategory;
    private ActivityManager mAm;
	private List<ActivityManager.RunningAppProcessInfo> allProcess;
    private long currentCacheMemory;
    private long bfKill;
    @Override
    protected int getMetricsCategory() {
        return MetricsLogger.DEVICEINFO_STORAGE;
    }

    @Override
    protected int getHelpResource() {
        return R.string.help_uri_storage;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        context = getActivity();

        mStorageManager = context.getSystemService(StorageManager.class);
        //mAm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        /// M: repeated register
        //mStorageManager.registerListener(mStorageListener);

        addPreferencesFromResource(R.xml.cy_device_info_storage);

        mInternalCategory = (PreferenceCategory) findPreference("storage_internal");
        mExternalCategory = (PreferenceCategory) findPreference("storage_external");
      
        //mInternalSummary = new StorageSummaryPreference(context);
        //mPhoneMemPre = (CyMemoryPreference)findPreference("cy_phone");
        //mSdMemPre = (CyMemoryPreference)findPreference("cy_sdcard");
        setHasOptionsMenu(true);
        /**
        mCleanPre =(MemoryCleanPreference) findPreference("cy_clear_memory");
        mCleanPre.setOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "aaaaaa", Toast.LENGTH_SHORT).show();
				killBackgroundProcesses();
			}
		});
        */
        mCustomizationCategory = new StorageSettingsExts(getActivity(),
              getPreferenceScreen(), mStorageManager);

        mCustomizationCategory.initCustomizationCategory();
    }

    private final StorageEventListener mStorageListener = new StorageEventListener() {
        @Override
        public void onVolumeStateChanged(VolumeInfo vol, int oldState, int newState) {
            if (isInteresting(vol)) {
                refresh();
            }
        }
        /// M: ALPS02316229 refresh UI when plug in or out SD card. @{
        @Override
        public void onDiskScanned(DiskInfo disk, int volumeCount) {
            refresh();
        }

        @Override
        public void onDiskDestroyed(DiskInfo disk) {
            refresh();
        }
        /// @}
    };

    private static boolean isInteresting(VolumeInfo vol) {
        switch(vol.getType()) {
            case VolumeInfo.TYPE_PRIVATE:
            case VolumeInfo.TYPE_PUBLIC:
                return true;
            default:
                return false;
        }
    }

    private void refresh() {
        final Context context = getActivity();

        getPreferenceScreen().removeAll();
        mInternalCategory.removeAll();
        mExternalCategory.removeAll();
       
        //mCustomizationCategory.updateCustomizationCategory();
        //getPreferenceScreen().addPreference(mCleanPre);
        //mInternalCategory.addPreference(mInternalSummary);

        int privateCount = 0;
        long privateUsedBytes = 0;
        long privateTotalBytes = 0;

        final List<VolumeInfo> volumes = mStorageManager.getVolumes();
        Collections.sort(volumes, VolumeInfo.getDescriptionComparator());

        for (VolumeInfo vol : volumes) {
         int color = COLOR_PRIVATE[privateCount++ % COLOR_PRIVATE.length];
            if (vol.getType() == VolumeInfo.TYPE_PRIVATE) {
            
            	CyStorageVolumePreference Cypref =new CyStorageVolumePreference(context, vol, color);
            	Cypref.setOrder(privateCount);
            	getPreferenceScreen().addPreference(Cypref);
                /**
                if (vol.isMountedReadable()) {
                    final File path = vol.getPath();
                    privateUsedBytes += path.getTotalSpace() - path.getFreeSpace();
                    privateTotalBytes += path.getTotalSpace();
                }
                */
            } else if (vol.getType() == VolumeInfo.TYPE_PUBLIC) {
            	CyStorageVolumePreference csVpref =new CyStorageVolumePreference(context, vol, color);
            	csVpref.setOrder(privateCount);
            	getPreferenceScreen().addPreference(csVpref);
            }
        }
        
        
        // Show missing private volumes
        
        final List<VolumeRecord> recs = mStorageManager.getVolumeRecords();
        for (VolumeRecord rec : recs) {
            if (rec.getType() == VolumeInfo.TYPE_PRIVATE
                    && mStorageManager.findVolumeByUuid(rec.getFsUuid()) == null) {
                // TODO: add actual storage type to record
                final Drawable icon = context.getDrawable(R.drawable.ic_sim_sd);
                icon.mutate();
                icon.setTint(COLOR_PUBLIC);

                final Preference prefer = new Preference(context);
                prefer.setKey(rec.getFsUuid());
                prefer.setTitle(rec.getNickname());
                prefer.setSummary(com.android.internal.R.string.ext_media_status_missing);
                //prefer.setIcon(icon);
                prefer.setOrder(++privateCount);
                getPreferenceScreen().addPreference(prefer);
            }
        }

        // Show unsupported disks to give a chance to init
        final List<DiskInfo> disks = mStorageManager.getDisks();
        for (DiskInfo disk : disks) {
            if (disk.volumeCount == 0 && disk.size > 0) {
                final Preference preference = new Preference(context);
                preference.setKey(disk.getId());
                preference.setTitle(disk.getDescription());
                preference.setSummary(com.android.internal.R.string.ext_media_status_unsupported);
                //preference.setIcon(R.drawable.ic_sim_sd);
                preference.setOrder(++privateCount);
                getPreferenceScreen().addPreference(preference);
            }
        }
        pref = new CyStorageRamPreference(context, COLOR_PUBLIC,getAvailMemory());
        pref.setOrder(++privateCount);
        totalSize = pref.updateTotalRamSize();
        getPreferenceScreen().addPreference(pref);
        
        mCustomizationCategory.updateCyCustomizationCategory(++privateCount);
        /**
        mCleanPre.setOrder(++privateCount);
        getPreferenceScreen().addPreference(mCleanPre);
        */
		/**
        final BytesResult result = Formatter.formatBytes(getResources(), privateUsedBytes, 0);
        mInternalSummary.setTitle(TextUtils.expandTemplate(getText(R.string.storage_size_large),
                result.value, result.units));
        mInternalSummary.setSummary(getString(R.string.storage_volume_used_total,
                Formatter.formatFileSize(context, privateTotalBytes)));
		*/
        if (mInternalCategory.getPreferenceCount() > 0) {
            getPreferenceScreen().addPreference(mInternalCategory);
        }
        if (mExternalCategory.getPreferenceCount() > 0) {
            getPreferenceScreen().addPreference(mExternalCategory);
        }
        if (mInternalCategory.getPreferenceCount() == 2
                && mExternalCategory.getPreferenceCount() == 0 && !sHasOpened) {
            // Only showing primary internal storage, so just shortcut
            final Bundle args = new Bundle();
            args.putString(VolumeInfo.EXTRA_VOLUME_ID, VolumeInfo.ID_PRIVATE_INTERNAL);
            startFragment(this, CyPrivateVolumeSettings.class.getCanonicalName(),
                    -1, 0, args);
            sHasOpened = true;
            finish();
        }
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver(){
    	public void onReceive(Context context, Intent intent) {
    		//killProcesses();
    		refresh();
    		Log.i("shubin", "onreceiver");
    	}
    };
    private void killProcesses(){
    	new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... arg0) {
				// TODkillBackgroundProcessesO Auto-generated method stub
				killBackgroundProcesses();
				Log.i("shubin", "doinback");
				return bfKill+"";
			}
			
			@Override
			protected void onPostExecute(String result) {
				Log.i("shubin", "onpost");
				// TODO Auto-generated method stub
				long afKill = currentCacheMemory;
				
				if ((afKill - bfKill) >= 0) {
					Toast.makeText(context, R.string.cy_toast_free_mem_none,
							Toast.LENGTH_SHORT).show();
				} else {
					//ramMemPre.setRightValue(formatFileSize(AvailabelRamSize()));
					pref.updateCleanRam(getAvailMemory());
					Toast.makeText(
							context,
							context.getString(R.string.cy_toast_free_mem)
									+ formatFileSize(bfKill - afKill),
							Toast.LENGTH_SHORT).show();
				}	
					
				}
			}.execute();
    }
   	private long getAvailMemory(){
           if(mAm == null)
   		//ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
           mAm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
           MemoryInfo mi = new MemoryInfo();
           mAm.getMemoryInfo(mi);
           //Xlog.d(TAG,"[getFreeMemory] availMem = " + mi.availMem);
           return mi.availMem;		
    	
    }
    @Override
    public void onResume() {
        super.onResume();
        mStorageManager.registerListener(mStorageListener);
        sHasOpened = false;
        Log.i("shus", "refresh start");
        refresh();
        Log.i("shus", "refresh end");
        showDetails();
        //currentCacheMemory = getBackProSize();
        //currentCacheMemory = 0;
        getActivity().registerReceiver(receiver, new IntentFilter("clean.click"));
        Log.i("shus", "currentCacheMemory end");
    }
    private void showDetails() {
		new AsyncTask<Void, Void, Void>() {

			protected void onPreExecute() {
				
			};

			@Override
			protected Void doInBackground(Void... params) {
				
				currentCacheMemory = getBackProSize();
				
				return null;
			}

			protected void onPostExecute(Void result) {
			
			};

		}.execute();
    }
    @Override
    public void onPause() {
        super.onPause();
        mStorageManager.unregisterListener(mStorageListener);
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference pref) {
        final String key = pref.getKey();
        Log.i("shubin", "key=="+key);
        if (pref instanceof CyStorageVolumePreference) {
            // Picked a normal volume
            final VolumeInfo vol = mStorageManager.findVolumeById(key);

            if (vol.getState() == VolumeInfo.STATE_UNMOUNTED) {
                VolumeUnmountedFragment.show(this, vol.getId());
                return true;
            } else if (vol.getState() == VolumeInfo.STATE_UNMOUNTABLE) {
                DiskInitFragment.show(this, R.string.storage_dialog_unmountable, vol.getDiskId());
                return true;
            }

            if (vol.getType() == VolumeInfo.TYPE_PRIVATE) {
                final Bundle args = new Bundle();
                args.putString(VolumeInfo.EXTRA_VOLUME_ID, vol.getId());
                startFragment(this, CyPrivateVolumeSettings.class.getCanonicalName(),
                        -1, 0, args);
                return true;

            } else if (vol.getType() == VolumeInfo.TYPE_PUBLIC) {
                if (vol.isMountedReadable()) {
                	Log.i("shubin", "2222222");
                    startActivity(vol.buildBrowseIntent());
                    return true;
                } else {
                    final Bundle args = new Bundle();
                    args.putString(VolumeInfo.EXTRA_VOLUME_ID, vol.getId());
                    startFragment(this, PublicVolumeSettings.class.getCanonicalName(),
                            -1, 0, args);
                    return true;
                }
            }

        }else if(pref instanceof CyStorageRamPreference){
        	startFragment(this, ManageApplications.class.getCanonicalName(), -1, 0, null);
        	
        }else if (key.startsWith("disk:")) {
            // Picked an unsupported disk
            DiskInitFragment.show(this, R.string.storage_dialog_unsupported, key);
            return true;

        } else {

            ///M:
            if (key.startsWith(StorageSettingsExts.KEY_WRITE_DISK_ITEM)) {
                return false;
            }
            
            // Picked a missing private volume
            final Bundle args = new Bundle();
            args.putString(VolumeRecord.EXTRA_FS_UUID, key);
            startFragment(this, PrivateVolumeForget.class.getCanonicalName(),
                    R.string.storage_menu_forget, 0, args);
            return true;
        }

        return false;
    }

    public static class MountTask extends AsyncTask<Void, Void, Exception> {
        private final Context mContext;
        private final StorageManager mStorageManager;
        private final String mVolumeId;
        private final String mDescription;

        public MountTask(Context context, VolumeInfo volume) {
            mContext = context.getApplicationContext();
            mStorageManager = mContext.getSystemService(StorageManager.class);
            mVolumeId = volume.getId();
            mDescription = mStorageManager.getBestVolumeDescription(volume);
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                mStorageManager.mount(mVolumeId);
                return null;
            } catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception e) {
            if (e == null) {
                Toast.makeText(mContext, mContext.getString(R.string.storage_mount_success,
                        mDescription), Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed to mount " + mVolumeId, e);
                Toast.makeText(mContext, mContext.getString(R.string.storage_mount_failure,
                        mDescription), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class UnmountTask extends AsyncTask<Void, Void, Exception> {
        private final Context mContext;
        private final StorageManager mStorageManager;
        private final String mVolumeId;
        private final String mDescription;

        public UnmountTask(Context context, VolumeInfo volume) {
            mContext = context.getApplicationContext();
            mStorageManager = mContext.getSystemService(StorageManager.class);
            mVolumeId = volume.getId();
            mDescription = mStorageManager.getBestVolumeDescription(volume);
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                mStorageManager.unmount(mVolumeId);
                return null;
            } catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Exception e) {
            if (e == null) {
                Toast.makeText(mContext, mContext.getString(R.string.storage_unmount_success,
                        mDescription), Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed to unmount " + mVolumeId, e);
                Toast.makeText(mContext, mContext.getString(R.string.storage_unmount_failure,
                        mDescription), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class VolumeUnmountedFragment extends DialogFragment {
        public static void show(Fragment parent, String volumeId) {
            final Bundle args = new Bundle();
            args.putString(VolumeInfo.EXTRA_VOLUME_ID, volumeId);

            final VolumeUnmountedFragment dialog = new VolumeUnmountedFragment();
            dialog.setArguments(args);
            dialog.setTargetFragment(parent, 0);
            dialog.show(parent.getFragmentManager(), TAG_VOLUME_UNMOUNTED);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Context context = getActivity();
            final StorageManager sm = context.getSystemService(StorageManager.class);

            final String volumeId = getArguments().getString(VolumeInfo.EXTRA_VOLUME_ID);
            final VolumeInfo vol = sm.findVolumeById(volumeId);

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(TextUtils.expandTemplate(
                    getText(R.string.storage_dialog_unmounted), vol.getDisk().getDescription()));

            builder.setPositiveButton(R.string.storage_menu_mount,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new MountTask(context, vol).execute();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);

            return builder.create();
        }
    }

    public static class DiskInitFragment extends DialogFragment {
        public static void show(Fragment parent, int resId, String diskId) {
            final Bundle args = new Bundle();
            args.putInt(Intent.EXTRA_TEXT, resId);
            args.putString(DiskInfo.EXTRA_DISK_ID, diskId);

            final DiskInitFragment dialog = new DiskInitFragment();
            dialog.setArguments(args);
            dialog.setTargetFragment(parent, 0);
            dialog.show(parent.getFragmentManager(), TAG_DISK_INIT);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Context context = getActivity();
            final StorageManager sm = context.getSystemService(StorageManager.class);

            final int resId = getArguments().getInt(Intent.EXTRA_TEXT);
            final String diskId = getArguments().getString(DiskInfo.EXTRA_DISK_ID);
            final DiskInfo disk = sm.findDiskById(diskId);

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(TextUtils.expandTemplate(getText(resId), disk.getDescription()));

            builder.setPositiveButton(R.string.storage_menu_set_up,
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Intent intent = new Intent(context, StorageWizardInit.class);
                    intent.putExtra(DiskInfo.EXTRA_DISK_ID, diskId);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton(R.string.cancel, null);

            return builder.create();
        }
    }
    /**
     * get all background pro size
     * @return
     */
 	private long getBackProSize() {
 		long totalBackSize = 0;
 		if(mAm == null)
 	        mAm = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
 		List<ActivityManager.RunningAppProcessInfo> processes = mAm
 				.getRunningAppProcesses();
 		int pids[] = new int[processes.size()];
 		for (int i = 0; i < processes.size(); i++) {
 			pids[i] = processes.get(i).pid;
 		}
 		try {
 			long[] pss = ActivityManagerNative.getDefault().getProcessPss(pids);
 			long[] pswap = ActivityManagerNative.getDefault().getProcessPswap(
 					pids);
 			float zramAdjust = (float) (totalSize)
 					/ (float) (totalSize + Process.getZramExtraTotalSize());
 			for (int i = 0; i < processes.size(); i++) {
 				if (processes.get(i).importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
 					long procSize = (long) ((pss[i] + pswap[i]) * zramAdjust);
 					totalBackSize = totalBackSize + procSize;
 					Log.d(TAG, "now process is:" + processes.get(i).processName
 							+ ",and is:" + procSize);
 				}
 			}
 		} catch (RemoteException e) {
 			e.printStackTrace();
 			return totalBackSize;
 		}
 		Log.d(TAG, "totalBackSize is:" + totalBackSize * 1024);
 		return totalBackSize * 1024;
 	}
    /**
     * clear RAM
     */
    
    public void killBackgroundProcesses() {
		bfKill = currentCacheMemory;
		allProcess = mAm.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo temp : allProcess) {
			if (temp.importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
				Log.e(TAG, "proceess name is:" + temp.processName);
				for (String packageName : temp.pkgList) {
					if ("com.ragentek.greenorangehome".equals(packageName)) {
						continue;
					}
					mAm.killBackgroundProcesses(packageName);
					Log.e(TAG, "cymemory kill background,packName is:"
							+ packageName);
				}
			}

		}
		allProcess = null;
		currentCacheMemory = getBackProSize();
		/**
		long afKill = currentCacheMemory;
		
		if ((afKill - bfKill) >= 0) {
			Toast.makeText(context, R.string.cy_toast_free_mem_none,
					Toast.LENGTH_SHORT).show();
		} else {
			//ramMemPre.setRightValue(formatFileSize(AvailabelRamSize()));
			//pref.updateCleanRam();
			Toast.makeText(
					context,
					context.getString(R.string.cy_toast_free_mem)
							+ formatFileSize(bfKill - afKill),
					Toast.LENGTH_SHORT).show();
		}
		*/
		
	}
    private String formatFileSize(long size) {
		return Formatter.formatFileSize(context, size);
	}
    /**
     * Enable indexing of searchable data
     */
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {
            @Override
            public List<SearchIndexableRaw> getRawDataToIndex(Context context, boolean enabled) {
                final List<SearchIndexableRaw> result = new ArrayList<SearchIndexableRaw>();

                SearchIndexableRaw data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.storage_settings);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.internal_storage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                final StorageManager storage = context.getSystemService(StorageManager.class);
                final List<VolumeInfo> vols = storage.getVolumes();
                for (VolumeInfo vol : vols) {
                    if (isInteresting(vol)) {
                        data.title = storage.getBestVolumeDescription(vol);
                        data.screenTitle = context.getString(R.string.storage_settings);
                        result.add(data);
                    }
                }

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_size);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_available);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_apps_usage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_dcim_usage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_music_usage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_downloads_usage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_media_cache_usage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                data = new SearchIndexableRaw(context);
                data.title = context.getString(R.string.memory_media_misc_usage);
                data.screenTitle = context.getString(R.string.storage_settings);
                result.add(data);

                return result;
            }
        };
}
