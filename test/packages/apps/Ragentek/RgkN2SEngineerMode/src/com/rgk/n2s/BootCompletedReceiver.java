package com.rgk.n2s;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;



public class BootCompletedReceiver extends BroadcastReceiver {
    private AudioManager mAudioManager;
	@Override
	public void onReceive(Context context, Intent intent) {
	    
		new Util(context).initValue();
		mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		new MyAsyncTask().execute();
		
	}
    class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.i("xiangkezhu", "doInBackground()");
            
            int nv_hz=NvRAMAgentHelper.readNVData(808); 
            Log.d("xiangkezhu", "nv="+nv_hz);
            if(nv_hz > 0){
                mAudioManager.setParameters("SET_VIBSPK_HZ="+String.valueOf(nv_hz));
            }
            int nv_gain=NvRAMAgentHelper.readNVData(809);
            Log.d("xiangkezhu", "nv_gain="+nv_gain);
            if(nv_gain > 0){
                mAudioManager.setParameters("SET_VIBSPK_GAIN="+String.valueOf(nv_gain));
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.i("xiangkezhu", "onPostExecute():result="+result);
            super.onPostExecute(result);
        }
        
    }

}
	
	
