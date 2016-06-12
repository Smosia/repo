package com.rgk.pedometer;

import java.util.ArrayList;

import android.content.Context;

public class StepPersister implements StepListener {

	private Context mContext;
	private int mCount = 0;
    
    
    // for Calories
    private static double METRIC_RUNNING_FACTOR = 1.02784823;
    private static double IMPERIAL_RUNNING_FACTOR = 0.75031498;

    private static double METRIC_WALKING_FACTOR = 0.708;
    private static double IMPERIAL_WALKING_FACTOR = 0.517;

    private float mCalories = 0;
    private boolean mIsMetric;
    private boolean mIsRunning;
    private int mStepLength;
    private int mBodyWeight;
    
    
    //for distance
    private float mDistance = 0;

    public StepPersister(Context context) {
        mContext = context;
        reloadSettings();
        notifyListener();
    }

    public synchronized void setValues(int steps, float calories, float distance) {
        mCount = steps;
        mCalories = calories;
        mDistance = distance;
        notifyListener();
    }
    
    public int getSteps() {
        return mCount;
    }
    
    public float getCalories() {
    	return mCalories;
    }
    
    public float getDistance() {
    	return mDistance;
    }
    
    public synchronized void onStep() {
    	// 1. calculate step
        mCount++;
        
        // 2. calculate calories
        if (mIsMetric) {
            mCalories += 
                (mBodyWeight * (mIsRunning ? METRIC_RUNNING_FACTOR : METRIC_WALKING_FACTOR))
                // Distance:
                * mStepLength // centimeters
                / 100000.0; // centimeters/kilometer
        } else {
            mCalories += 
                (mBodyWeight * (mIsRunning ? IMPERIAL_RUNNING_FACTOR : IMPERIAL_WALKING_FACTOR))
                // Distance:
                * mStepLength // inches
                / 63360.0; // inches/mile            
        }
        
        // 3. calculate distance
        if (mIsMetric) {
            mDistance += (float)(// kilometers
                mStepLength // centimeters
                / 100000.0); // centimeters/kilometer
        } else {
            mDistance += (float)(// miles
                mStepLength // inches
                / 63360.0); // inches/mile
        }
        
        notifyListener();
    }
    public void reloadSettings() {
    	mIsMetric = SharePreferenceUtils.isMetric(mContext);
        mIsRunning = SharePreferenceUtils.isRunning(mContext);
        mStepLength = SharePreferenceUtils.getStepLength(mContext);
        mBodyWeight = SharePreferenceUtils.getBodyWeightValue(mContext);
    }
    public void passValue() {
    }
    
    public float getCaloriesPerStep() {
    	float calories = 0;
    	if (mIsMetric) {
    		calories += 
                (mBodyWeight * (mIsRunning ? METRIC_RUNNING_FACTOR : METRIC_WALKING_FACTOR))
                // Distance:
                * mStepLength // centimeters
                / 100000.0; // centimeters/kilometer
        } else {
        	calories += 
                (mBodyWeight * (mIsRunning ? IMPERIAL_RUNNING_FACTOR : IMPERIAL_WALKING_FACTOR))
                // Distance:
                * mStepLength // inches
                / 63360.0; // inches/mile            
        }
    	
    	return calories;
    }
    
    public float getDistancePerStep() {
    	float distance = 0;
    	if (mIsMetric) {
    		distance = (float)(// kilometers
                mStepLength // centimeters
                / 100000.0); // centimeters/kilometer
        } else {
        	distance = (float)(// miles
                mStepLength // inches
                / 63360.0); // inches/mile
        }
    	
    	return distance;
    }
    
    

    //-----------------------------------------------------
    // Listener
    
    public interface Listener {
        public void stepsChanged(int steps, float calories, float distance);
        //public void passValue();
    }
    private ArrayList<Listener> mListeners = new ArrayList<Listener>();

    public void addListener(Listener l) {
        mListeners.add(l);
    }
    
    public void cancelListener() {
    	mListeners.clear();
    }
    
    public void notifyListener() {
        for (Listener listener : mListeners) {
            listener.stepsChanged(mCount, mCalories, mDistance);
        }
    }

}
