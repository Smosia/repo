/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 */
/* MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek Software")
 * have been modified by MediaTek Inc. All revisions are subject to any receiver's
 * applicable license agreements with MediaTek Inc.
 */

//New file added by delong.liu@archermind.com

package com.mediatek.telecom.recording;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import com.mediatek.storage.StorageManagerEx;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//add DWYQLSSMY-70 songqingming 20160324 (start)
import android.telephony.PhoneNumberUtils;
import android.provider.Settings;
import com.android.internal.telephony.CallerInfo;
import com.android.internal.telephony.CallerInfoAsyncQuery;
import com.android.internal.telephony.CallerInfoAsyncQuery.OnQueryCompleteListener;
import android.text.TextUtils;
import android.telephony.SubscriptionManager;
import com.android.server.telecom.R;

import android.os.storage.StorageVolume;
import android.os.storage.StorageManager;
import android.os.Environment;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IMountService;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
//add DWYQLSSMY-70 songqingming 20160324 (end)

public class Recorder implements OnErrorListener {
    private static final String TAG = "Recorder";

    static final String SAMPLE_PREFIX = "recording";
    static final String SAMPLE_PATH_KEY = "sample_path";
    static final String SAMPLE_LENGTH_KEY = "sample_length";

    public static final int IDLE_STATE = 0;
    public static final int RECORDING_STATE = 1;

    int mState = IDLE_STATE;

    public static final int NO_ERROR = 0;
    public static final int SDCARD_ACCESS_ERROR = 1;
    public static final int INTERNAL_ERROR = 2;
    public static final int STORAGE_FULL = 3;
    public static final int SUCCESS = 4;
    public static final int STORAGE_UNMOUNTED = 5;

    public interface OnStateChangedListener {
        /**
         *
         * @param state
         */
        void onStateChanged(int state);

        void onError(int error);
        void onFinished(int cause, String data);
    }

    OnStateChangedListener mOnStateChangedListener;

    long mSampleStart; // time at which latest record or play operation
    // started
    long mSampleLength; // length of current sample
    File mSampleFile;
    MediaRecorder mRecorder;
    // the path where saved recording file.
    private String mRecordStoragePath;

    public Recorder() {
    }

    /**
     *
     * @param recorderState
     */
    public void saveState(Bundle recorderState) {
        recorderState.putString(SAMPLE_PATH_KEY, mSampleFile.getAbsolutePath());
        recorderState.putLong(SAMPLE_LENGTH_KEY, mSampleLength);
    }

    /**
     *
     * @return int
     */
    public int getMaxAmplitude() {
        if (mState != RECORDING_STATE) {
            return 0;
        }
        return mRecorder.getMaxAmplitude();
    }

    /**
     *
     * @param recorderState
     */
    public void restoreState(Bundle recorderState) {
        String samplePath = recorderState.getString(SAMPLE_PATH_KEY);
        if (samplePath == null) {
            return;
        }
        long sampleLength = recorderState.getLong(SAMPLE_LENGTH_KEY, -1);
        if (sampleLength == -1) {
            return;
        }
        File file = new File(samplePath);
        if (!file.exists()) {
            return;
        }
        if (mSampleFile != null
                && mSampleFile.getAbsolutePath().compareTo(file.getAbsolutePath()) == 0) {
            return;
        }
        delete();
        mSampleFile = file;
        mSampleLength = sampleLength;

        signalStateChanged(IDLE_STATE);
    }

    /**
     *
     * @param listener
     */
    public void setOnStateChangedListener(OnStateChangedListener listener) {
        mOnStateChangedListener = listener;
    }

    /**
     *
     * @return
     */
    public int state() {
        return mState;
    }

    /**
     *
     * @return
     */
    public int progress() {
        if (mState == RECORDING_STATE) {
            return (int) ((System.currentTimeMillis() - mSampleStart) / 1000);
        }
        return 0;
    }

    /**
     *
     * @return
     */
    public long sampleLength() {
        return mSampleLength;
    }

    /**
     *
     * @return
     */
    public File sampleFile() {
        return mSampleFile;
    }

    /**
     * Resets the recorder state. If a sample was recorded, the file is deleted.
     */
    public void delete() {
        stop();

        if (mSampleFile != null) {
            mSampleFile.delete();
        }
        mSampleFile = null;
        mSampleLength = 0L;

    }

    /**
     * Resets the recorder state. If a sample was recorded, the file is left on
     * disk and will be reused for a new recording.
     */
    public void clear() {
        stop();

        mSampleLength = 0L;

    }

    /**
     *
     * @param outputfileformat
     * @param extension
     * @throws IOException
     */
    public void startRecording(int outputfileformat, String extension) throws IOException {
        log("startRecording");
        stop();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String prefix = dateFormat.format(new Date());
        File sampleDir = new File(StorageManagerEx.getDefaultPath());

        if (!sampleDir.canWrite()) {
            Log.i(TAG, "----- file can't write!! ---");
            // Workaround for broken sdcard support on the device.
            sampleDir = new File("/sdcard/sdcard");
        }

        sampleDir = new File(sampleDir.getAbsolutePath() + "/PhoneRecord");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }

        /// For ALPS01000670. @{
        // get the current path where saved recording files.
        mRecordStoragePath = sampleDir.getCanonicalPath();
        /// @}

        try {
            mSampleFile = File.createTempFile(prefix, extension, sampleDir);
        } catch (IOException e) {
            setError(SDCARD_ACCESS_ERROR);
            Log.i(TAG, "----***------- can't access sdcard !! " + e);
            throw e;
        }

        log("finish creating temp file, start to record");

        mRecorder = new MediaRecorder();
        mRecorder.setOnErrorListener(this);
        ///M: ALPS02374165
        // change audio source according to system property
        // so that to test different record type @{
        //mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        String recordType = SystemProperties.get("persist.incallrec.audiosource", "-1");
        log("recordType is: " + Integer.parseInt(recordType));
        if (recordType.equals("-1")) {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            mRecorder.setAudioChannels(2);
        } else {
            mRecorder.setAudioSource(Integer.parseInt(recordType));
            if (recordType.equals("4")) {
                mRecorder.setAudioChannels(2);
            } else {
                mRecorder.setAudioChannels(1);
            }
        }
        /// @}

        mRecorder.setOutputFormat(outputfileformat);
        /// ALPS01426963 @{
        // change record encoder format for AMR_NB to ACC, so that improve the record quality.
        //modify DWYQLSSMY-70 songqingming 20160324 (start)
        //mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.VORBIS);
        //modify DWYQLSSMY-70 songqingming 20160324 (end)
        mRecorder.setAudioEncodingBitRate(24000);
        mRecorder.setAudioSamplingRate(16000);
        /// @}
        mRecorder.setOutputFile(mSampleFile.getAbsolutePath());

        try {
            mRecorder.prepare();
            mRecorder.start();
            mSampleStart = System.currentTimeMillis();
            setState(RECORDING_STATE);
        } catch (IOException exception) {
            log("startRecording, IOException");
            handleException();
            throw exception;
        }
    }

    //add DWYQLSSMY-70 songqingming 20160324 (start)
    private Context mContext;
    private String mNumber;
    private int mSlot = -1;
    private boolean mIsConference;
    private File mFoundSameFolder;
    private int mQueryToken = 0;
    private CallerInfo mCallerInfo;
    private boolean isRecording = false;
    private boolean isFinding = false;

    private boolean contactNameOrNumberAsFolderEnabled = false;
    private boolean intexRecordEnabled = false;
    private boolean xoloRecordEnabled = false;

    private final OnQueryCompleteListener sCallerInfoQueryListener =
        new OnQueryCompleteListener() {

		@Override
		public void onQueryComplete(int token, Object cookie, CallerInfo callerInfo) {
			if (mQueryToken == token) {
				mCallerInfo = callerInfo;
				new Thread(mFindSameFolderRunnable).start();
                	}
		}
        };
    
    private void startCallerInfoLookup() {
        Log.d(TAG, "[startCallerInfoLookup] mNumber="+mNumber);
        mQueryToken++;  // Updated so that previous queries can no longer set the information.
        mCallerInfo = null;
        if (!TextUtils.isEmpty(mNumber)) {
            int subId = SubscriptionManager.getDefaultSubId();
            int[] subIds = SubscriptionManager.getSubId(mSlot);
            if (subIds != null && subIds.length > 0) {
            	subId = subIds[0];
            }
            CallerInfoAsyncQuery.startQuery(
                    mQueryToken,
                    mContext,
                    mNumber,
                    sCallerInfoQueryListener,
                    this, subId);
            isFinding = true;
        }
    }

    private Runnable mFindSameFolderRunnable = new Runnable() {

        @Override
	public void run() {
            Log.d(TAG, "[mFindSameFolderRunnable] start to find same folder.");
            String defaultPath = getDefaultStorage();
            if (defaultPath == null) {
                defaultPath = StorageManagerEx.getDefaultPath();
            }
            Log.d(TAG, "[mFindSameFolderRunnable] defaultPath="+defaultPath);
            
	    File dirs = new File(defaultPath+"/Call Recordings");
            if (intexRecordEnabled) {
                dirs = new File(defaultPath+"/Auto Call Record");
            }
	    if (!dirs.exists()) {
                Log.d(TAG, "[mFindSameFolderRunnable] dirs not exist");
		return;
	    }
	    File[] files = dirs.listFiles();
	    if (files == null) {
                Log.d(TAG, "[mFindSameFolderRunnable] files is null");
		return;
	    }

            boolean found = false;
	    String contactName = null;
	    if (mCallerInfo != null) {
		contactName = mCallerInfo.name;
	    }
            Log.d(TAG, "[mFindSameFolderRunnable] contactName="+contactName);

	    if (!TextUtils.isEmpty(contactName)) {
		for (File f : files) {
		    if (f.getName().equals(contactName)) {
			Log.d(TAG, "[mFindSameFolderRunnable] found same folder with contactName: "+contactName);
			mFoundSameFolder = f;
			found = true;
			break;
		    }
		}
		if (!found) {
		    Log.d(TAG, "[mFindSameFolderRunnable] not found same folder with contactName: "+contactName);
		    File tempFile = new File(dirs, contactName);
		    if (tempFile.mkdirs()) {
			mFoundSameFolder = tempFile;
		    }
		}
	    } else {
		for (File f : files) {
		    if (mNumber == null || mNumber.length() == 0) {
			if ("Unknow".equalsIgnoreCase(f.getName())) {
			    Log.d(TAG, "[mFindSameFolderRunnable] found same folder with: Unknow");
			    mFoundSameFolder = f;
			    found = true;
			    break;
			}
		    } else {
			boolean same = PhoneNumberUtils.compare(mNumber, f.getName());
			if (same) {
                            Log.d(TAG, "[mFindSameFolderRunnable] found same folder with: "+mNumber);
			    mFoundSameFolder = f;
			    found = true;
			    break;
			}
		    }
		}
		if (!found) {
		    Log.d(TAG, "[mFindSameFolderRunnable] not found same folder with mNumber: "+mNumber);
		    if (mNumber == null || mNumber.length() == 0) {
			File tempFile = new File(dirs, "Unknow");
			if (tempFile.mkdirs()) {
			    mFoundSameFolder = tempFile;
			}
		    } else {
			File tempFile = new File(dirs, mNumber);
			if (tempFile.mkdirs()) {
			    mFoundSameFolder = tempFile;
			}
		    }
		}
	    }
		
            Log.d(TAG, "[mFindSameFolderRunnable] --------mFoundSameFolder="+mFoundSameFolder);

            isFinding = false;

        if (contactNameOrNumberAsFolderEnabled && !isRecording) {
            Log.d(TAG, "[mFindSameFolderRunnable] --------to saveToRightFolder()");
		    saveToRightFolder();
	    }else if(intexRecordEnabled && !contactNameOrNumberAsFolderEnabled){
		    saveToRightFolder();
        }
	}
    	
    };

    protected void saveToRightFolder() {
        Log.d(TAG, "[saveToRightFolder] mFoundSameFolder="+mFoundSameFolder);
    	if (mFoundSameFolder != null) {
    		String newPath = mFoundSameFolder.getAbsolutePath()+"/"+mSampleFile.getName();
    		File newFile = new File(newPath);
    		if (newFile != null) {
    			mSampleFile.renameTo(newFile);
			mSampleFile = newFile;
    		}
                mFoundSameFolder = null;
    	}
    }

    protected void setContext(Context context) {
    	mContext = context;
        contactNameOrNumberAsFolderEnabled = mContext.getResources()
                .getBoolean(R.bool.contact_name_or_number_as_record_folder_enabled);
        intexRecordEnabled = mContext.getResources()
                .getBoolean(R.bool.intex_auto_call_record_enabled);
        xoloRecordEnabled = mContext.getResources()
                .getBoolean(R.bool.xolo_auto_call_record_enabled);
    }

    private int getAutoCallRecordValue() {
        int autoRecordValue = Settings.Global.getInt(mContext.getContentResolver(),
                    Settings.Global.TELEPHONY_AUTO_CALL_RECORD_CONFIG, 0);
        Log.d(TAG, ">>>getAutoCallRecordValue(): autoRecordValue="+autoRecordValue);
        return autoRecordValue;
    }

    public String getDefaultStorage() {
        Log.d(TAG, "[getDefaultStorage]");

        if (!intexRecordEnabled) {
            return null;
        }

        StorageManager mSM = (StorageManager) mContext.getSystemService(
        		Context.STORAGE_SERVICE);
        if (null == mSM) {
            Log.w(TAG, "[getDefaultStorage]SM is null!");
            return null;
        }
        StorageVolume volumes[] = mSM.getVolumeList();
        List<StorageVolume> storageList = new ArrayList<StorageVolume>();
        if (volumes != null) {
            Log.d(TAG, "[getDefaultStorage]volumes are " + volumes);
            for (StorageVolume volume : volumes) {
                if (!Environment.MEDIA_MOUNTED.equals(mSM.getVolumeState(volume.getPath()))) {
                    continue;
                }
                storageList.add(volume);
            }
        }

        String resultPath = null;
        if (storageList.size() == 1) {
            resultPath = storageList.get(0).getPath();
        }else if (storageList.size() > 1) {
            boolean found = false;
            StorageVolume canRemoveVolume = null;
            String path = Settings.Global.getString(mContext.getContentResolver(), 
    			Settings.Global.AUTO_CALL_RECORD_DEFAULT_STORAGE);
            for (StorageVolume sv : storageList) {
        	if (sv.getPath().equals(path)) {
        	    resultPath = path;
        	    found = true;
                    Log.d(TAG, "[getDefaultStorage] found ----resultPath="+resultPath);
        	    break;
                }
        	if (sv.isRemovable()) {
        	    canRemoveVolume = sv;
        	}
            }
            if (!found && canRemoveVolume != null) {
        	resultPath = canRemoveVolume.getPath();
                Log.d(TAG, "[getDefaultStorage] not found ----resultPath="+resultPath);
            }
        }
        Log.d(TAG, "[getDefaultStorage] end ----resultPath="+resultPath);
        
        return resultPath;
    }

    private boolean checkSDCardAvaliable(final String path) {
        if (TextUtils.isEmpty(path)) {
            Log.w(TAG, "[checkSDCardAvaliable]path is null!");
            return false;
        }

        String volumeState = "";
        try {
            IMountService mountService = IMountService.Stub.asInterface(ServiceManager
                    .getService("mount"));
            volumeState = mountService.getVolumeState(path);
        } catch (RemoteException e) {
            Log.e(TAG, "[checkSDCardAvaliable]catch exception:");
            e.printStackTrace();
        }

        return Environment.MEDIA_MOUNTED.equals(volumeState);
    }

    /**
     *
     * @param outputfileformat
     * @param extension
     * @throws IOException
     */
    public void startRecording(int outputfileformat, String extension,
            int slot, String number, boolean isConference) throws IOException {
        log("startRecording");
        log("startRecording: extension="+extension+", slot="+slot
                +", number="+number+", isConference="+isConference);
        stop();

        mNumber = number;
        mSlot = slot;
        mIsConference = isConference;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String prefix = dateFormat.format(new Date());

        //for file name
        if (xoloRecordEnabled || intexRecordEnabled) {
            if (slot == 0) {
                prefix = "SIM1_" + prefix;
            } else if (slot == 1) {
                prefix = "SIM2_" + prefix;
            }
        }

        String defaultPath = getDefaultStorage();
        if (defaultPath == null) {
            defaultPath = StorageManagerEx.getDefaultPath();
        }
        //File sampleDir = new File(StorageManagerEx.getDefaultPath());
        File sampleDir = new File(defaultPath);

        if (!sampleDir.canWrite()) {
            Log.i(TAG, "----- file can't write!! ---");
            // Workaround for broken sdcard support on the device.
            sampleDir = new File("/sdcard/sdcard");
        }

        //sampleDir = new File(sampleDir.getAbsolutePath() + "/PhoneRecord");
        
        if (xoloRecordEnabled) {
            if (mIsConference) {
                sampleDir = new File(sampleDir.getAbsolutePath() + "/Call Recordings/Conference Call");
            } else {
                sampleDir = new File(sampleDir.getAbsolutePath() + "/Call Recordings");
            }
        } else if (intexRecordEnabled) {
            sampleDir = new File(sampleDir.getAbsolutePath() + "/Auto Call Record");
        } else {
            sampleDir = new File(sampleDir.getAbsolutePath() + "/PhoneRecord");
        }

        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }

        /// For ALPS01000670. @{
        // get the current path where saved recording files.
        mRecordStoragePath = sampleDir.getCanonicalPath();
        /// @}

        try {
            //mSampleFile = File.createTempFile(prefix, extension, sampleDir);
            mSampleFile = new File(sampleDir, prefix + extension);
            mSampleFile.createNewFile();
        } catch (IOException e) {
            setError(SDCARD_ACCESS_ERROR);
            Log.i(TAG, "----***------- can't access sdcard !! " + e);
            throw e;
        }

        log("finish creating temp file, start to record");

        mRecorder = new MediaRecorder();
        mRecorder.setOnErrorListener(this);
        ///M: ALPS02374165
        // change audio source according to system property
        // so that to test different record type @{
        //mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        String recordType = SystemProperties.get("persist.incallrec.audiosource", "-1");
        log("recordType is: " + Integer.parseInt(recordType));
        if (recordType.equals("-1")) {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            mRecorder.setAudioChannels(2);
        } else {
            mRecorder.setAudioSource(Integer.parseInt(recordType));
            if (recordType.equals("4")) {
                mRecorder.setAudioChannels(2);
            } else {
                mRecorder.setAudioChannels(1);
            }
        }
        /// @}

        mRecorder.setOutputFormat(outputfileformat);
        /// ALPS01426963 @{
        // change record encoder format for AMR_NB to ACC, so that improve the record quality.
        //modify DWYQLSSMY-70 songqingming 20160324 (start)
        //mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.VORBIS);
        //modify DWYQLSSMY-70 songqingming 20160324 (end)
        mRecorder.setAudioEncodingBitRate(24000);
        mRecorder.setAudioSamplingRate(16000);
        /// @}
        mRecorder.setOutputFile(mSampleFile.getAbsolutePath());

        try {
            mRecorder.prepare();
            mRecorder.start();
            mSampleStart = System.currentTimeMillis();
            setState(RECORDING_STATE);
            isRecording = true;
        } catch (IOException exception) {
            isRecording = false;
            log("startRecording, IOException");
            handleException();
            throw exception;
        }
        isFinding = false;

        
        if (contactNameOrNumberAsFolderEnabled && !mIsConference) {
            startCallerInfoLookup();
        }else if (intexRecordEnabled && !contactNameOrNumberAsFolderEnabled) {
            new Thread(mFindSameFolderRunnable).start();
        }
    }
    //add DWYQLSSMY-70 songqingming 20160324 (end)

    private void handleException() {
        setError(INTERNAL_ERROR);
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }

    public void stopRecording() {
        log("stopRecording");
        if (mRecorder == null) {
            return;
        }
        mSampleLength = System.currentTimeMillis() - mSampleStart;
        try {
            mRecorder.stop();
        } catch (RuntimeException e) {
            // no output, use to delete the file
            if (mSampleFile != null) {
                mSampleFile.delete();
            }
        }

        mRecorder.release();
        mRecorder = null;

        setState(IDLE_STATE);
    }

    public void stop() {
        log("stop");
        stopRecording();
        //add DWYQLSSMY-70 songqingming 20160324 (start)
        isRecording = false;
        
        if (contactNameOrNumberAsFolderEnabled && !isFinding && !mIsConference) {
            Log.d(TAG, "[stop] --------to saveToRightFolder()");
            saveToRightFolder();
        }else if (intexRecordEnabled && !contactNameOrNumberAsFolderEnabled && getAutoCallRecordValue() == 1) {
            Log.d(TAG, "[stop] ----1----to saveToRightFolder()");
            saveToRightFolder();
        }
        //add DWYQLSSMY-70 songqingming 20160324 (end)
    }

    /**
     * @param state
     */
    private void setState(int state) {
        if (state == mState) {
            return;
        }
        mState = state;
        signalStateChanged(mState);
    }

    /**
     *
     * @param state
     */
    private void signalStateChanged(int state) {
        if (mOnStateChangedListener != null) {
            mOnStateChangedListener.onStateChanged(state);
        }
    }

    void signalFinished(int cause, String data) {
        if (mOnStateChangedListener != null) {
            log("signalFinished " + cause + "/" + data);
            mOnStateChangedListener.onFinished(cause, data);
        }
    }

    /**
     *
     * @param error
     */
    private void setError(int error) {
        if (mOnStateChangedListener != null) {
            mOnStateChangedListener.onError(error);
        }
    }

    /**
     * error listener
     */
    public void onError(MediaRecorder mp, int what, int extra) {
        log("onError");
        if (what == MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN) {
            stop();
            // TODO show hint view
        }
        return;
    }

    /**
     * Get the recording path.
     * @return
     */
    public String getRecordingPath() {
        return mRecordStoragePath;
    }

    private void log(String msg) {
        Log.d(TAG, msg);
    }
}
