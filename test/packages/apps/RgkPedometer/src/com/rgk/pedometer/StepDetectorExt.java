package com.rgk.pedometer;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

public class StepDetectorExt extends StepDetector {

    public final long INTERVAL_MS = 1000/30;
	
	// Context needed to get access to sensor service
	// private Context context;
	
	private static final int vhSize = 6;
	private double[] values_history = new double[vhSize];
	private int vhPointer = 0;
	
	private double a;
	private double peak;
	private int step_timeout_ms;
	private long last_step_ts = 0;
//	private double old_z = 0.0;
	
	// last acc is low pass filtered
	private double[] lastAcc = new double[] {0.0, 0.0, 0.0};
	// last comp is untouched
	private double[] lastComp = new double[] {0.0, 0.0, 0.0};
	
	private int round = 0;
	
	private Timer timer;

	public StepDetectorExt(double a, double peak, int step_timeout_ms) {
		super();
		this.a = a;
		this.peak = peak;
		this.step_timeout_ms = step_timeout_ms;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			// just update the oldest z value
			lastAcc[0] = ToolBox.lowpassFilter(lastAcc[0], event.values[0], a);
			lastAcc[1] = ToolBox.lowpassFilter(lastAcc[1], event.values[1], a);
			lastAcc[2] = ToolBox.lowpassFilter(lastAcc[2], event.values[2], a);
			break;
		case Sensor.TYPE_ORIENTATION:
			lastComp[0] = event.values[0];
			lastComp[1] = event.values[1];
			lastComp[2] = event.values[2];
			break;
		default:
		}// switch (event.sensor.getType())
	}
	
	/**
	 * Enable step detection
	 */
	@Override
	public void start(){
		// Register timer
		timer = new Timer("UpdateData", false);
		TimerTask task = new TimerTask(){

			@Override
			public void run() {
				updateData();
			}
		};
		timer.schedule(task, 0, INTERVAL_MS);
	}
	
	/**
	 * Disable step detection
	 */
	@Override
	public void stop(){
		timer.cancel();
		timer.purge();
		timer = null;
	}
	
	/**
	 * This is called every INTERVAL_MS ms from the TimerTask. 
	 */
	private void updateData(){
		// Get current time for time stamps
		long now_ms = System.currentTimeMillis();
		
		// Create local value for compass and old_z, such that it is consistent during logs
		// (It might change in between, which is circumvented by this)

		// array.clone() does not work here!!
		// this does not work as well!!
//		double[] oldAcc = {lastAcc[0],lastAcc[1],lastAcc[2]};
//		double[] oldComp = {lastComp[0],lastComp[1],lastComp[2]};
		double[] oldAcc = new double[3];
		System.arraycopy(lastAcc, 0, oldAcc, 0, 3);
		double[] oldComp = new double[3];
		System.arraycopy(lastComp, 0, oldComp, 0, 3);
		// double lCompass = oldComp[0];
		double lOld_z = oldAcc[2];
		
		addData(lOld_z);
		
		// Check if a step is detected upon data
		if((now_ms - last_step_ts) > step_timeout_ms && checkForStep(peak)){
			// Set latest detected step to "now"
			last_step_ts = now_ms;
			
			// mStepListeners.onStep();
			for (StepListener stepListener : mStepListeners) {
				stepListener.onStep();
			}
			Log.i("FOOTPATH", "Detected step  in  round = " + round  + " @ "+ now_ms);
		
		}
		round++;
	}

	private void addData(double value){
		values_history[vhPointer % vhSize] = value;
		vhPointer++;
		vhPointer = vhPointer % vhSize;
	}
	
	private boolean checkForStep(double peakSize) {
		// Add value to values_history

		int lookahead = 5;
		double diff = peakSize;
		
		
		for( int t = 1; t <= lookahead; t++){
			if((values_history[(vhPointer - 1 - t + vhSize + vhSize) % vhSize] - 
					values_history[(vhPointer - 1 + vhSize) % vhSize]
			                   > diff)){
				Log.i("FOOTPATH", "Detected step with t = " + t + ", diff = " + diff + " < " + (values_history[(vhPointer - 1 - t + vhSize + vhSize) % vhSize] - 
						values_history[(vhPointer - 1 + vhSize) % vhSize]));
				return true;
			}
		}
		return false;
	}

	public double getA() {
		return a;
	}

	public double getPeak() {
		return peak;
	}

	public int getStep_timeout_ms() {
		return step_timeout_ms;
	}

	public void setA(double a) {
		this.a = a;
	}

	public void setPeak(double peak) {
		this.peak = peak;
	}

	public void setStep_timeout_ms(int stepTimeoutMs) {
		step_timeout_ms = stepTimeoutMs;
	}

}
