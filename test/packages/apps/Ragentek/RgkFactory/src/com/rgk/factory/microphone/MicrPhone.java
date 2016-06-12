
//file create by liunianliang 20130718~20130724

package com.rgk.factory.microphone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.rgk.factory.Config;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Message;
public class MicrPhone extends Activity implements View.OnClickListener,MediaPlayer.OnCompletionListener {
    
	public static String TAG = "MicrPhone";
	
	private Button mRecord,mPass;
	private TextView mRecordTime;
	private AudioManager mAudioManager;
	private File mRecAudioFile;
	private File mRecAudioPath;
	private MediaRecorder mMediaRecorder;
	private String	strTempFile	= "record_";
	private MediaPlayer mMediaPlayer = null;
	private boolean playfinish = false;
	private boolean hadSendBroadcast = false;
	private File file;
	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mRecord.setEnabled(true);
			super.handleMessage(msg);
		}
		
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
       // M: huangkunming 2014.12.20 @{
       // since window flags full, change FLAG_HOMEKEY_DISPATCHED to privateFlags
    	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);
    	getWindow().getAttributes().privateFlags |= WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED;
	// @}
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setTitle(R.string.Microphone);
        setContentView(R.layout.microphone);
        LinearLayout mLayout = (LinearLayout) findViewById(R.id.microphone_layout);
        mLayout.setSystemUiVisibility(0x00002000);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // D:JWLYB-427 huangkunming 20141015 {
        // mAudioManager.setMode(AudioManager.MODE_RINGTONE);
        // D:}
        
        
        mRecord = (Button)findViewById(R.id.microphone_info);
        mRecordTime = (TextView)findViewById(R.id.microphone_time);
        mRecordTime.setVisibility(View.INVISIBLE);
        mPass = (Button)findViewById(R.id.microphone_pass);      
        ((Button)findViewById(R.id.microphone_pass)).setOnClickListener(this);
        ((Button)findViewById(R.id.microphone_fail)).setOnClickListener(this);
        mRecord.setOnClickListener(this);
        
        file = new File("/sdcard/CitRecord/");
        file.mkdir();
    }


	private Handler mHandler;

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
			case R.id.microphone_pass:
				SendBroadcast(Config.PASS);
				break;
			case R.id.microphone_fail:
				SendBroadcast(Config.FAIL);
				break;
			case R.id.microphone_info:
			    HandlerRecord();
				break;
		}	
	}

	private void HandlerRecord() {
		String Text = mRecord.getText().toString();
		mRecord.setText(Text.equals(getResources().getString(R.string.start_record)) ? 
		           R.string.stop_record : R.string.start_record);
		if(Text.equals(getResources().getString(R.string.start_record))){
			mRecord.setEnabled(false);
			myHandler.sendEmptyMessageDelayed(0, 3000);
			try
			{
				//start to record
				if(mMediaPlayer != null) {
					mMediaPlayer.release();
					mMediaPlayer = null;
				}
				playfinish = false;
				mRecordTime.setVisibility(View.INVISIBLE);
				mRecAudioFile = File.createTempFile(strTempFile, ".amr", file);
				Log.e(TAG,"mRecAudioFile="+mRecAudioFile);
				mMediaRecorder = new MediaRecorder();
				mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
				mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
				mMediaRecorder.setOutputFile(mRecAudioFile.getPath());
				mMediaRecorder.prepare();
				mMediaRecorder.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}else{
			mPass.setEnabled(true);
			try {
				mMediaRecorder.stop();
				mMediaRecorder.release();
			} catch(RuntimeException re) {
				
			}
			
	        try {
				mMediaRecorder = null;
				mMediaPlayer = new MediaPlayer();
				//start to play
				//mRecordTime.setVisibility(View.VISIBLE);
				mMediaPlayer = MediaPlayer.create(getApplicationContext(),
						Uri.parse(mRecAudioFile.getPath()));
				mMediaPlayer.setLooping(false);
				mMediaPlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        mMediaPlayer.start();        
	        mMediaPlayer.setOnCompletionListener(this);
	        DisplayTime();
		}
		
	}

	private void DisplayTime() {
        mHandler = new Handler();
        //mHandler.post(update);	
	}
	
	private Runnable update = new Runnable() {
		
		@Override
		public void run() {
			if(!playfinish){
				if(mMediaPlayer != null) 
				mRecordTime.setText(makeTimeString(mMediaPlayer.getCurrentPosition()));
				mHandler.postDelayed(update, 1);
			}
		}
	};

	@Override
	public void onCompletion(MediaPlayer mp) {
		mMediaPlayer.release();	
		playfinish = true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	private void delete(File file) {
		Log.i(TAG,"delete file:"+file);
		if(file.isFile()) {
			file.delete();
			return;
		}
		if(file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if(childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}
			for(int i=0; i<childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	@Override
	public void onDestroy() {
        if(mMediaPlayer != null) mMediaPlayer.release();
        if(mMediaRecorder != null) mMediaRecorder.release();
		delete(file);
		super.onDestroy();
	}
	
	public static String makeTimeString(long milliSecs) {
	       StringBuffer sb = new StringBuffer();
	       long m = milliSecs / (60 * 1000);
	       sb.append(m < 10 ? "0" + m : m);
	       sb.append(":");
	       long s = (milliSecs % (60 * 1000)) / 1000;
	       sb.append(s < 10 ? "0" + s : s);
	       return sb.toString();
	     }
	
	public void SendBroadcast(String result){
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			AutoTestHandle.autoTest(this, TAG);
			//sendBroadcast(new Intent(Config.ACTION_START_AUTO_TEST).putExtra("test_item", TAG));
			finish();
		}
	}
}