package com.kayun.smartmotion;

import java.io.IOException;

import com.kayun.smartmotion.ext.SensorListener;
import com.kayun.smartmotion.ext.SensorListener.SensorCallback;
import com.kayun.smartmotion.utils.Constants;
import com.kayun.smartmotion.utils.SmartMotionFactory;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartMotionTryActivity extends Activity implements SensorCallback, OnLoadCompleteListener{

    private static final String TAG = "SmartMotionTryActivity";

    private static final int MAX_STREAMS = 1;
    private static final String UNLOCK_NOTIFICATION_PATH = "system/media/audio/ui/Unlock.ogg";

    private int ID;
    private boolean isNormal;
    private boolean isPlayOgg;
    private boolean isLargeAudio;

    private ImageView mImageView;
    private TextView mTextView;

    private SensorListener mSensorListener;

    private MediaPlayer mMediaPlayer;
    private SoundPool mSoundPool;
    private int mSoundID;
    private int mSoundType;
    private String mSoundPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DLOG("onCreate - start");
        // for full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.smart_motion_try_activity);

        ID = getIntent().getIntExtra("ID", Constants.INIT_INT_VALUE);

        mImageView = (ImageView) findViewById(R.id.image_view);
        mTextView = (TextView) findViewById(R.id.exception_text);

        initView();
        mSensorListener = new SensorListener(this, this);
        initSoundTypeAndPath();
        playTone();
        DLOG("onCreate - end -> ID = " + ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
	}

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        DLOG("onResume - start");
        if(mMediaPlayer != null){
            mMediaPlayer.start();
        }
        if(mSensorListener != null) mSensorListener.enable(true);
        super.onResume();
        DLOG("onResume - end");
    }

    @Override
    protected void onPause() {
        DLOG("onPause - start");
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
        super.onPause();
        DLOG("onPause - end");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        DLOG("onDestroy - start");
        super.onDestroy();
        if(mSoundPool != null){
            mSoundPool = null;
        }
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        isNormal = false;
        isPlayOgg = false;
        isLargeAudio = false;
        DLOG("onDestroy - end");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        DLOG("onKeyDown - keyCode = " + keyCode);
        if(keyCode == KeyEvent.KEYCODE_BACK){
            DLOG("onKeyDown - KEYCODE_BACK.");
            if(mSensorListener != null){
                mSensorListener.enable(false);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void playTone(){
        if(!isPlayOgg){
            DLOG("playTone - do not need play tone now.");
            return;
        }
        if(mSoundType == AudioManager.SCO_AUDIO_STATE_ERROR){
            DLOG("playTone - do not need play any tone.");
            return;
        }
        if(mSoundPath == null){
            DLOG("playTone - the sound path is null.");
            return;
        }
        if(!isLargeAudio){
            playBySoundPool();
        } else {
            playByMediaPlayer();
        }
    }

    private void playBySoundPool(){
        if(mSoundPool == null) mSoundPool = build();
        mSoundID = mSoundPool.load(mSoundPath, 1);
        mSoundPool.setOnLoadCompleteListener(this);
        DLOG("playInSoundPool - load tone.");
    }

    private void playByMediaPlayer(){
        mMediaPlayer = new MediaPlayer();
        Uri uri = Uri.parse(mSoundPath);
        try {
            mMediaPlayer.setDataSource(this, uri);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            DLOG(e.toString());
        } catch (SecurityException e) {
            DLOG(e.toString());
        } catch (IllegalStateException e) {
            DLOG(e.toString());
        } catch (IOException e) {
            DLOG(e.toString());
        }
        DLOG("playByMediaPlayer - play tone.");
    }

    private SoundPool build(){
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setLegacyStreamType(mSoundType);
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(MAX_STREAMS);
        builder.setAudioAttributes(attrBuilder.build());
        return builder.build();
    }

    private void initSoundTypeAndPath(){
        mSoundType = AudioManager.SCO_AUDIO_STATE_ERROR;
        mSoundPath = null;
        switch (ID) {
        case Constants.ACTION_UNLOCK:
            mSoundType = AudioManager.STREAM_NOTIFICATION;
            mSoundPath = UNLOCK_NOTIFICATION_PATH;
            isLargeAudio = false;
            isPlayOgg = false;
            break;
        case Constants.ANSWER_BY_SWING:
        case Constants.SMART_ANSWER:
        /*case Constants.SMART_CALL:*/
        case Constants.TURN_TO_SILENCE:
            mSoundType = AudioManager.STREAM_RING;
            mSoundPath = RingtoneManager.getDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE).toString();
            //mSoundPath = "system/media/audio/ringtones/Eastern_Sky.ogg";
            isLargeAudio = true;
            isPlayOgg = true;
            break;
        case Constants.TURN_TO_SNOOZE:
            mSoundType = AudioManager.STREAM_ALARM;
            mSoundPath = RingtoneManager.getDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM).toString();
            //mSoundPath = "system/media/audio/alarms/Argon.ogg";
            isLargeAudio = true;
            isPlayOgg = true;
            break;
        //case Constants.SMART_SWITCH:
        default:
            isLargeAudio = false;
            isPlayOgg = false;
            break;
        }
        DLOG("initSoundTypeAndPath - mSoundType = " + mSoundType + ", mSoundPath = " + mSoundPath);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if(mSoundPool != null) {
            if(mSoundPool.play(mSoundID, 1, 1, 1, 0, 2.0f) == 0){
                DLOG("onLoadComplete - play sound failed.");
            }
        }
    }

    @Override
    public void onUnlock() {
        DLOG("onUnlock - ID = " + ID);
        switch (ID) {
        case Constants.ACTION_UNLOCK:
            isPlayOgg = true;
            update();
            break;
        default:
            isPlayOgg = false;
            break;
        }
        DLOG("onUnlock - isPlayOgg = " + isPlayOgg);
        playTone();
    }

    @Override
    public void onSwing() {
        DLOG("onSwing - ID = " + ID);
        switch (ID) {
        case Constants.ANSWER_BY_SWING:
            update();
            break;
        default:
            break;
        }
        isPlayOgg = false;
        DLOG("onSwing - isPlayOgg = " + isPlayOgg);
    }

    @Override
    public void onCalling() {
        DLOG("onCalling - ID = " + ID);
        switch (ID) {
        case Constants.SMART_ANSWER:
        case Constants.SMART_CALL:
        case Constants.SMART_SWITCH:
            update();
            break;
        default:
            break;
        }
        isPlayOgg = false;
        DLOG("onCalling - isPlayOgg = " + isPlayOgg);
    }

    @Override
    public void onRotate() {
        DLOG("onRotate - ID = " + ID);
        switch (ID) {
        case Constants.TURN_TO_SILENCE:
        case Constants.TURN_TO_SNOOZE:
            update();
            break;
        default:
            break;
        }
        isPlayOgg = false;
        DLOG("onRotate - isPlayOgg = " + isPlayOgg);
    }

    private void initView(){
        int res = SmartMotionFactory.getComingAnimById(ID);
        isNormal = res != Constants.INIT_INT_VALUE;
        if(isNormal){
            mImageView.setBackgroundResource(res);
            startAnimation();
        } else {
            DLOG("init - invalid resource id.");
        }
        setVisibility();
    }

    private void update(){
        int res = SmartMotionFactory.getGoingAnimById(ID);
        isNormal = res != Constants.INIT_INT_VALUE;
        if(isNormal){
            mImageView.setBackgroundResource(res);
            if(ID == Constants.TURN_TO_SILENCE){
                startAnimation();
            }
        } else {
            DLOG("update - invalid resource id.");
        }
        setVisibility();
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()) mMediaPlayer.stop();
        if(mSensorListener != null) mSensorListener.enable(false);
    }

    private void startAnimation(){
        AnimationDrawable drawable = (AnimationDrawable) mImageView.getBackground();
        if(drawable != null) drawable.start();
    }

    private void setVisibility(){
        mImageView.setVisibility(isNormal ? View.VISIBLE : View.GONE);
        mTextView.setVisibility(isNormal ? View.GONE : View.VISIBLE);
    }

    private void DLOG(String log){
        Log.d(TAG, log);
    }

}
