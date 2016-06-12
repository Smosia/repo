package com.kayun.smartmotion.ext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

@SuppressLint("HandlerLeak")
public class SensorListener {

    private static final String TAG = "SensorListener";

    private static final int INTEGER_INIT_VALUE = 0;
    private static final long LONG_INIT_VALUE = 0;
    private static final float FLOAT_INIT_VALUE = 0.0f;

    //private Context mContext;
    private SensorCallback mSensorCallback;

    private SensorManager mSensorManager;

    private Sensor mAccelerometerSensor;
    private Sensor mProximitySensor;

    private long mAccelerometerX;
    private long mAccelerometerY;
    private long mAccelerometerZ;

    private float mProximityDistance;
    private float mProximityThreshold;

    //swing
    private int count = INTEGER_INIT_VALUE;
    private long lastTimeStamp = LONG_INIT_VALUE;

    //rotate
    private long delay = 80;
    private boolean isSendMessage = false;
    private boolean isHorizontalState = false;

    private SensorEventListener mAccelerometerSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            mAccelerometerX = (long) event.values[0];
            mAccelerometerY = (long) event.values[1];
            mAccelerometerZ = (long) event.values[2];

            Log.d(TAG,"mAccelerometerX = " + mAccelerometerX + 
                    ", mAccelerometerY = " + mAccelerometerY + 
                    ", mAccelerometerZ = " + mAccelerometerZ);

            //swinging.
            final boolean isSwing = (Math.abs(mAccelerometerX) > 5 || Math.abs(mAccelerometerZ) > 5)
                                  && Math.abs(mAccelerometerY) > 6;
            if(isSwing){
                Log.d(TAG, "the device is swinging. count = " + count + ", lastTimeStamp = " + lastTimeStamp);
                if(count == 0){
                    count++;
                    lastTimeStamp = event.timestamp;
                } else if(count == 1){
                    final long diff = event.timestamp - lastTimeStamp;
                    if(diff < 800 * 1000 * 1000){
                        mHandler.removeMessages(IS_SWING);
                        mHandler.sendEmptyMessage(IS_SWING);
                    }
                    count = SensorListener.INTEGER_INIT_VALUE;
                    lastTimeStamp = SensorListener.LONG_INIT_VALUE;
                } else {
                    count = SensorListener.INTEGER_INIT_VALUE;
                    lastTimeStamp = SensorListener.INTEGER_INIT_VALUE;
                }
            }

            //rotating.
            if(Math.abs(mAccelerometerX) < 3 && Math.abs(mAccelerometerY) < 3 && mAccelerometerZ > 5){
                isHorizontalState = true;
            }
            final boolean isRotate = mAccelerometerZ < -4;
            if(isHorizontalState && !isSendMessage && isRotate){
                Log.d(TAG, "the device is rotating.");
                mHandler.removeMessages(DELAY_SEND_MSG_FOR_ROTATE);
                Message msg = mHandler.obtainMessage(DELAY_SEND_MSG_FOR_ROTATE);
                mHandler.sendMessageDelayed(msg, delay);
                isSendMessage = true;
            }
            Log.d(TAG, "isHorizontalState = " + isHorizontalState + ", isRotate = " + isRotate);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // ignore
        }
    };

    private SensorEventListener mProximitySensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            mProximityDistance = event.values[0];

            //unlock
            if(mProximityDistance >= SensorListener.FLOAT_INIT_VALUE 
                    && mProximityDistance < mProximityThreshold){
                mHandler.removeMessages(IS_UNLOCK);
                mHandler.sendEmptyMessage(IS_UNLOCK);
            }

            //calling
            if(mProximityDistance >= SensorListener.FLOAT_INIT_VALUE 
                    && mProximityDistance < mProximityThreshold){
                mHandler.removeMessages(IS_CALLING);
                mHandler.sendEmptyMessage(IS_CALLING);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // ignore
        }
    };

    private static final int IS_UNLOCK = 1 << 0;
    private static final int IS_SWING =  1 << 1;
    private static final int IS_CALLING =1 << 2;
    private static final int IS_ROTATE = 1 << 3;
    private static final int DELAY_SEND_MSG_FOR_ROTATE = 1 << 4;

    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg) {
            Log.d(TAG, "handle message state is " + msg.what);
            switch (msg.what) {
            case IS_UNLOCK:
                mSensorCallback.onUnlock();
                break;
            case IS_SWING:
                mSensorCallback.onSwing();
                break;
            case IS_CALLING:
                mSensorCallback.onCalling();
                break;
            case IS_ROTATE:
                mSensorCallback.onRotate();
                break;
            case DELAY_SEND_MSG_FOR_ROTATE:
                sendRotateActionMessage();
                break;
            default:
                Log.d(TAG, "appeared illegal massage state value.");
                break;
            }
        }
    };

    private void sendRotateActionMessage(){
        if(mAccelerometerZ < -4){
            mHandler.removeMessages(IS_ROTATE);
            mHandler.sendEmptyMessage(IS_ROTATE);
        }
        isSendMessage = false;
    }

    public interface SensorCallback{
        public void onUnlock();	/*ActionUnlock*/
        public void onSwing();	/*AnswerBySwing*/
        public void onCalling();/*SmartAnswer & SmartCall & SmartSwitch*/
        public void onRotate();	/*TurnToSilence & TurnToSnooze*/
    }

    private static final float TYPICAL_PROXIMITY_THRESHOLD = 1.0f;

    public SensorListener(Context context, SensorCallback callback){
        //this.mContext = context;
        this.mSensorCallback = callback;

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mAccelerometerX = SensorListener.LONG_INIT_VALUE;
        mAccelerometerY = SensorListener.LONG_INIT_VALUE;
        mAccelerometerZ = SensorListener.LONG_INIT_VALUE;

        mProximityDistance = SensorListener.FLOAT_INIT_VALUE;

        if(mProximitySensor != null){
            mProximityThreshold = Math.min(mProximitySensor.getMaximumRange(),TYPICAL_PROXIMITY_THRESHOLD);
        }
    }

    /**
     * @category Register or unregister sensor listeners.
     * @param enable - The ability of listening.
     */
    public void enable(boolean enable){
        Log.d(TAG,"enable = " + enable);
        synchronized (this) {
            if(enable){
                //register sensor listener.
                mSensorManager.registerListener(mAccelerometerSensorListener,  
                        mAccelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
                mSensorManager.registerListener(mProximitySensorListener,
                        mProximitySensor,SensorManager.SENSOR_DELAY_NORMAL);

                isHorizontalState = false;
                isSendMessage = false;
            } else {
                //unregister sensor listener.
                mSensorManager.unregisterListener(mAccelerometerSensorListener);
                mSensorManager.unregisterListener(mProximitySensorListener);

                //remove all handler messages.
                mHandler.removeMessages(IS_CALLING);
                mHandler.removeMessages(IS_SWING);
                mHandler.removeMessages(IS_ROTATE);
                mHandler.removeMessages(IS_UNLOCK);
            }
        }
    }

}
