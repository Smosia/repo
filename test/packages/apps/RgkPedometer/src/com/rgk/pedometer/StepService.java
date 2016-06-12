/*
 *  Pedometer - Android App
 *  Copyright (C) 2009 Levente Bagi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rgk.pedometer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.rgk.pedometer.model.StepInfo;
import com.rgk.pedometer.model.StepInfoDao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;



/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application.  The {@link StepServiceController}
 * and {@link StepServiceBinding} classes show how to interact with the
 * service.
 *
 * <p>Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service.  This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 */
public class StepService extends Service {
	private static final String TAG = PedometerApplication.TAG;
    private SharedPreferences mSettings;
    private PedometerSettings mPedometerSettings;
//    private SharedPreferences mState;
//    private SharedPreferences.Editor mStateEditor;
//    private Utils mUtils;
    private SensorManager mSensorManager;
    // private Sensor mSensor;
    private StepDetector mStepDetector;
    // private StepBuzzer mStepBuzzer; // used for debugging
//    private StepDisplayer mStepDisplayer;
//    private PaceNotifier mPaceNotifier;
//    private DistanceNotifier mDistanceNotifier;
//    private SpeedNotifier mSpeedNotifier;
//    private CaloriesNotifier mCaloriesNotifier;
//    private SpeakingTimer mSpeakingTimer;
    
    private StepPersister mStepPersister;
    
    private PowerManager.WakeLock wakeLock;
    private NotificationManager mNM;

    private int mSteps;
    private int mPace;
    private float mDistance;
    private float mSpeed;
    private float mCalories;
    
    private List<Sensor> mSensors; // List of all sensors
    
    private volatile Looper mThreadLooper;
    private volatile ThreadHandler mThreadHandler;
    private static final int MSG_STEP_CHANGED = 1;
    
    private StepsQueryTask.Listener mQueryListener = new StepsQueryTask.Listener() {
		
		@Override
		public void onQueryFinish(Object ret, int type) {
			if (ret == null || mStepPersister == null) {
				mStepPersister.setValues(0, 0, 0);
				return;
			}
			
			if (StepsQueryTask.TYPE_TODAY == type) {
				StepInfo info = (StepInfo)ret;
				mStepPersister.setValues(info.walkSteps, 
						Float.parseFloat(info.calories), 
						Float.parseFloat(info.distance));
			}
		}
	};
    
    class StepCache {
    	int steps;
    	float calories;
    	float distance;
    	
    	StepCache(int st, float cal, float dis) {
    		steps = st;
    		calories = cal;
    		distance = dis;
    	}
    }
    
    private final class ThreadHandler extends Handler {
        public ThreadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
            case MSG_STEP_CHANGED:
            	StepCache obj = (StepCache)msg.obj;
            	long time = System.currentTimeMillis();
            	Date date = new Date(time);
            	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            	String formatDate = format.format(date);
            	StepInfoDao sid = StepInfoDao.getInstance();
            	StepInfo info = new StepInfo();
            	info.formatDate = formatDate;
            	if (sid.hasSteps(formatDate)) {
            		info.runSteps = 0;
                	info.walkSteps = obj.steps;
                	info.calories = String.valueOf(obj.calories);
                	info.distance = String.valueOf(obj.distance);
            		sid.update(info);
            	} else {
            		info.runSteps = 0;
                	info.walkSteps = 1;
                	float calories = mStepPersister.getCaloriesPerStep();
                	float distance = mStepPersister.getDistancePerStep();
                	mStepPersister.setValues(1, calories, distance);
                	info.calories = String.valueOf(calories);
                	info.distance = String.valueOf(distance);
            		info.date = time;
                	Calendar c = Calendar.getInstance();
                	c.setTimeInMillis(time);
                	//Log.d("sqm", "dayInWeek-----------------"+c.get(Calendar.DAY_OF_WEEK));
                	info.dayInWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            		sid.insert(info);
            	}
            	break;
            }
        }
    }
    
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class StepBinder extends Binder {
        StepService getService() {
            return StepService.this;
        }
    }
    
    @Override
    public void onCreate() {
    	//Log.d("sqm", "onCreate " + Thread.currentThread().getName());
        Log.d(TAG, "[SERVICE] onCreate");
        super.onCreate();
        
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();
        
        newHandlerThread();
        
        // Load settings
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        mPedometerSettings = new PedometerSettings(mSettings);
//        mState = getSharedPreferences("state", 0);
//
//        mUtils = Utils.getInstance();
//        mUtils.setService(this);
//        mUtils.initTTS();

        acquireWakeLock();
        
        // Start detecting
        mStepDetector = newStepDetector();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        registerDetector();

        // Register our receiver for the ACTION_SCREEN_OFF action. This will make our receiver
        // code be called whenever the phone enters standby mode.
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);

        //Log.d("sqm", "start-----------1-------------1");
        mStepPersister = new StepPersister(this);
        initValues(mStepPersister);
        mStepPersister.addListener(mStepPersisterListener);
        // Ringtone notify when walk the target steps.
        mStepPersister.addListener(new StepNotifier(this));
        mStepDetector.addStepListener(mStepPersister);
        //Log.d("sqm", "start------------2------------2");
        
//        mStepDisplayer = new StepDisplayer(mPedometerSettings, mUtils);
//        mStepDisplayer.setSteps(mSteps = mState.getInt("steps", 0));
//        mStepDisplayer.addListener(mStepListener);
        //mStepDetector.addStepListener(mStepDisplayer);

//        mPaceNotifier     = new PaceNotifier(mPedometerSettings, mUtils);
//        mPaceNotifier.setPace(mPace = mState.getInt("pace", 0));
//        mPaceNotifier.addListener(mPaceListener);
        //mStepDetector.addStepListener(mPaceNotifier);

//        mDistanceNotifier = new DistanceNotifier(mDistanceListener, mPedometerSettings, mUtils);
//        mDistanceNotifier.setDistance(mDistance = mState.getFloat("distance", 0));
        //mStepDetector.addStepListener(mDistanceNotifier);
        
//        mSpeedNotifier    = new SpeedNotifier(mSpeedListener,    mPedometerSettings, mUtils);
//        mSpeedNotifier.setSpeed(mSpeed = mState.getFloat("speed", 0));
//        mPaceNotifier.addListener(mSpeedNotifier);
        
//        mCaloriesNotifier = new CaloriesNotifier(mCaloriesListener, mPedometerSettings, mUtils);
//        mCaloriesNotifier.setCalories(mCalories = mState.getFloat("calories", 0));
        //mStepDetector.addStepListener(mCaloriesNotifier);
        
//        mSpeakingTimer = new SpeakingTimer(mPedometerSettings, mUtils);
        //mSpeakingTimer.addListener(mStepDisplayer);
        //mSpeakingTimer.addListener(mPaceNotifier);
        //mSpeakingTimer.addListener(mDistanceNotifier);
        //mSpeakingTimer.addListener(mSpeedNotifier);
        //mSpeakingTimer.addListener(mCaloriesNotifier);
        //mStepDetector.addStepListener(mSpeakingTimer);
        
        // Used when debugging:
        // mStepBuzzer = new StepBuzzer(this);
        // mStepDetector.addStepListener(mStepBuzzer);

        // Start voice
        reloadSettings();

        // Tell the user we started.
        //Toast.makeText(this, getText(R.string.started), Toast.LENGTH_SHORT).show();
    }

	private void initValues(StepPersister persister) {
		new StepsQueryTask(mQueryListener).execute(StepsQueryTask.TYPE_TODAY);
	}

	private void newHandlerThread() {
		HandlerThread thread = new HandlerThread("StepsSaveThread");
        thread.start();

        mThreadLooper = thread.getLooper();
        mThreadHandler = new ThreadHandler(mThreadLooper);
		
	}

	private StepDetector newStepDetector() {
		// return new MyStepDetector();
		return new StepDetectorExt(0.5, 3.0, 300);
	}
    
    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "[SERVICE] onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "[SERVICE] onDestroy");
//        mUtils.shutdownTTS();

        // Unregister our receiver.
        unregisterReceiver(mReceiver);
        unregisterDetector();
        
//        mStateEditor = mState.edit();
//        mStateEditor.putInt("steps", mSteps);
//        mStateEditor.putInt("pace", mPace);
//        mStateEditor.putFloat("distance", mDistance);
//        mStateEditor.putFloat("speed", mSpeed);
//        mStateEditor.putFloat("calories", mCalories);
//        mStateEditor.commit();
        
        mNM.cancel(R.string.app_name);

        wakeLock.release();
        
        mThreadLooper.quit(); //Quit looper thread
        
        super.onDestroy();
        
        // Stop detecting
        // mSensorManager.unregisterListener(mStepDetector);

        // Tell the user we stopped.
        //Toast.makeText(this, getText(R.string.stopped), Toast.LENGTH_SHORT).show();
    }

    private void registerDetector() {
        //mSensor = mSensorManager.getDefaultSensor(
        //    Sensor.TYPE_ACCELEROMETER /*| 
        //    Sensor.TYPE_MAGNETIC_FIELD | 
        //    Sensor.TYPE_ORIENTATION*/);
        //mSensorManager.registerListener(mStepDetector,
        //    mSensor,
        //    SensorManager.SENSOR_DELAY_UI); // SensorManager.SENSOR_DELAY_FASTEST
        
    	mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    	int size = mSensors.size();
		for (int i = 0; i < size; i++) {
			// Register only compass and accelerometer
			Sensor sensor = mSensors.get(i);
			if (sensor.getType() == Sensor.TYPE_ACCELEROMETER
					|| sensor.getType() == Sensor.TYPE_ORIENTATION) {
				mSensorManager.registerListener(mStepDetector, sensor, SensorManager.SENSOR_DELAY_FASTEST);
			}
		}
		mStepDetector.start();
    }

    private void unregisterDetector() {
    	mStepDetector.stop();
    	mStepPersister.cancelListener();
    	mStepDetector.cancelStepListener();
        mSensorManager.unregisterListener(mStepDetector);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "[SERVICE] onBind");
        return mBinder;
    }

    /**
     * Receives messages from activity.
     */
    private final IBinder mBinder = new StepBinder();

    public interface ICallback {
        public void stepsChanged(int value);
        public void stepsChanged(int value, float calories, float distance);
        public void paceChanged(int value);
        public void distanceChanged(float value);
        public void speedChanged(float value);
        public void caloriesChanged(float value);
    }
    
    private ICallback mCallback;

    public void registerCallback(ICallback cb) {
        mCallback = cb;
        //mStepDisplayer.passValue();
        //mPaceListener.passValue();
    }
    
    private int mDesiredPace;
    private float mDesiredSpeed;
    
    /**
     * Called by activity to pass the desired pace value, 
     * whenever it is modified by the user.
     * @param desiredPace
     */
    public void setDesiredPace(int desiredPace) {
        mDesiredPace = desiredPace;
//        if (mPaceNotifier != null) {
//            mPaceNotifier.setDesiredPace(mDesiredPace);
//        }
    }
    /**
     * Called by activity to pass the desired speed value, 
     * whenever it is modified by the user.
     * @param desiredSpeed
     */
    public void setDesiredSpeed(float desiredSpeed) {
        mDesiredSpeed = desiredSpeed;
//        if (mSpeedNotifier != null) {
//            mSpeedNotifier.setDesiredSpeed(mDesiredSpeed);
//        }
    }
    
    public void reloadSettings() {
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        
        if (mStepDetector != null) { 
            mStepDetector.setSensitivity(
                    Float.valueOf(mSettings.getString("sensitivity", "10"))
            );
        }
        
//        if (mStepDisplayer    != null) mStepDisplayer.reloadSettings();
//        if (mPaceNotifier     != null) mPaceNotifier.reloadSettings();
//        if (mDistanceNotifier != null) mDistanceNotifier.reloadSettings();
//        if (mSpeedNotifier    != null) mSpeedNotifier.reloadSettings();
//        if (mCaloriesNotifier != null) mCaloriesNotifier.reloadSettings();
//        if (mSpeakingTimer    != null) mSpeakingTimer.reloadSettings();
        
        if (mStepPersister    != null) mStepPersister.reloadSettings();
    }
    
    public void resetValues() {
//        mStepDisplayer.setSteps(0);
//        mPaceNotifier.setPace(0);
//        mDistanceNotifier.setDistance(0);
//        mSpeedNotifier.setSpeed(0);
//        mCaloriesNotifier.setCalories(0);
        
        mStepPersister.setValues(0, 0, 0);
    }
    
    private StepPersister.Listener mStepPersisterListener = new StepPersister.Listener() {
        public void stepsChanged(int steps, float calories, float distance) {
        	Log.d(TAG, "mStepPersisterListener:stepsChanged");
        	calories = get3Decimal(calories);
        	distance = get3Decimal(distance);
        	StepCache obj = new StepCache(steps, calories, distance);
            mThreadHandler.obtainMessage(MSG_STEP_CHANGED, obj).sendToTarget();
            
            passValue(steps, calories, distance);
        }
        public void passValue(int steps, float calories, float distance) {
            if (mCallback != null) {
                mCallback.stepsChanged(steps, calories, distance);
            }
        }
    };
    
    private float get3Decimal(float f) {
    	float fint = f * 1000;
		double fd = Math.floor(fint);
		return (float)(fd / 1000);
    }
    
    /**
     * Forwards pace values from PaceNotifier to the activity. 
     */
    private StepDisplayer.Listener mStepListener = new StepDisplayer.Listener() {
        public void stepsChanged(int value) {
            mSteps = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.stepsChanged(mSteps);
            }
        }
    };
    /**
     * Forwards pace values from PaceNotifier to the activity. 
     */
    private PaceNotifier.Listener mPaceListener = new PaceNotifier.Listener() {
        public void paceChanged(int value) {
            mPace = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.paceChanged(mPace);
            }
        }
    };
    /**
     * Forwards distance values from DistanceNotifier to the activity. 
     */
    private DistanceNotifier.Listener mDistanceListener = new DistanceNotifier.Listener() {
        public void valueChanged(float value) {
            mDistance = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.distanceChanged(mDistance);
            }
        }
    };
    /**
     * Forwards speed values from SpeedNotifier to the activity. 
     */
    private SpeedNotifier.Listener mSpeedListener = new SpeedNotifier.Listener() {
        public void valueChanged(float value) {
            mSpeed = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.speedChanged(mSpeed);
            }
        }
    };
    /**
     * Forwards calories values from CaloriesNotifier to the activity. 
     */
    private CaloriesNotifier.Listener mCaloriesListener = new CaloriesNotifier.Listener() {
        public void valueChanged(float value) {
            mCalories = value;
            passValue();
        }
        public void passValue() {
            if (mCallback != null) {
                mCallback.caloriesChanged(mCalories);
            }
        }
    };
    
    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setWhen(System.currentTimeMillis());
        builder.setOngoing(true);
        Intent pedometerIntent = new Intent();
        pedometerIntent.setComponent(new ComponentName(this, MainPedometerActivity.class));
        pedometerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                pedometerIntent, 0);
        builder.setContentIntent(contentIntent);
        builder.setContentTitle(getText(R.string.app_name));
        builder.setContentText(getText(R.string.notification_subtitle));
        Notification notification = builder.build();

        mNM.notify(R.string.app_name, notification);
    }


    // BroadcastReceiver for handling ACTION_SCREEN_OFF.
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Check action just to be on the safe side.
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                // Unregisters the listener and registers it again.
                /*StepService.this.unregisterDetector();
                StepService.this.registerDetector();
                if (mPedometerSettings.wakeAggressively()) {
                    wakeLock.release();
                    acquireWakeLock();
                }*/
            }
        }
    };

    private void acquireWakeLock() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        int wakeFlags;
        if (mPedometerSettings.wakeAggressively()) {
            wakeFlags = PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;
        }
        else if (mPedometerSettings.keepScreenOn()) {
            wakeFlags = PowerManager.SCREEN_DIM_WAKE_LOCK;
        }
        else {
            wakeFlags = PowerManager.PARTIAL_WAKE_LOCK;
        }
        wakeLock = pm.newWakeLock(wakeFlags, TAG);
        wakeLock.acquire();
    }
    
    public int getSteps() {
    	return mStepPersister.getSteps();
    }
    
    public float getCalories() {
    	return get3Decimal(mStepPersister.getCalories());
    }
    
    public float getDistance() {
    	return get3Decimal(mStepPersister.getDistance());
    }

}
