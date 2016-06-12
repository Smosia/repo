package com.ragentek.wakegesture;
import com.ragentek.wakegesture.R;
import com.ragentek.wakegesture.R.string;
import com.ragentek.wakegesture.constant.Constants;
import com.ragentek.wakegesture.constant.KEY_EVENT_WAKE;
import com.ragentek.wakegesture.receiver.PackageRemoveReceiver;
import com.ragentek.wakegesture.sqlite.SqliteManager;
import com.ragentek.wakegesture.util.Util;

import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private ImageView  iv_frame;
    private AnimationDrawable   frame_Drawable;
    private long   frame_duration=0l;
	private int keyCode;
	private String[] str_key;
	private SqliteManager sqliteManager;
	private PowerManager pm;
	private boolean isQuary = true;
    private int  type=-1;
    private  Handler  mHandler=new  Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what==1){
		if(type==1){
		  Log.e("WakeGesture","type====only unlock ------------");
			finish();
			return;
		}		
	    iv_frame.setVisibility(View.INVISIBLE);		
		String  packageString=null;
		String  activityName=null;
		if(str_key!=null){
			packageString=str_key[1];
			activityName=str_key[2];
		}
		if(keyCode==KEY_EVENT_WAKE.KEYCODE_DPAD_DOWN){
			Intent  intent_call=new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"));
			intent_call.addFlags(
	                Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent_call);
			finish();
			return;
		}
		if(packageString!=null && activityName!=null){
		Intent intent = new Intent();
		intent.setComponent(new ComponentName(packageString,
		        		activityName));
		intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		//overridePendingTransition(0, 0);
		finish();
		}
		finish();
			}
		else if(msg.what==2){
			if(type==0){
				Log.e("WakeGesture","type====only light on ------------");
					finish();
					return;
				}
			else{
			   setFrameBackground(keyCode);
			   if(frame_Drawable!=null){
				  Log.e("WakeGesture","start------------");	
				  frame_Drawable.start();
				  mHandler.sendEmptyMessageDelayed(1, frame_duration);
					}
			     }
			
			  }
		   }
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("WakeGesture","onCreate()------------");
		//add   BUG_ID:JLLB-2739  xieshanlin  20140731(start)
		keyCode = Settings.Global.getInt(getContentResolver(),
				Settings.Global.BLACK_GESTURE_WAKE_KEYEVENT, 0);
		if(keyCode!=KeyEvent.KEYCODE_F && keyCode!=KeyEvent.KEYCODE_DPAD_LEFT &&  keyCode!=KeyEvent.KEYCODE_DPAD_RIGHT){
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
		| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		//modify   BUG_ID:JLLB-3665  xieshanlin  20140813(start)
		/*******
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
		| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		******/
		//modify   BUG_ID:JLLB-3665  xieshanlin  20140813(end)
		}
		//add   BUG_ID:JLLB-2739  xieshanlin  20140731(end)
		View  decorverview=getWindow().getDecorView();
        decorverview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		iv_frame=(ImageView) findViewById(R.id.iv_frame);
		Log.v("------","dddljldldjlj");
		Log.e("WakeGesture","event  change------------");
		Log.e("WakeGesture","keyCode======="+keyCode);
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		
		//action  type
		//int   type=-1;
		boolean  left_right_music_cherry=getResources().getBoolean(R.bool.left_right_music_cherry);
		if(keyCode==KEY_EVENT_WAKE.KEYCODE_H){
			type=0;
		  }else if (keyCode == KEY_EVENT_WAKE.KEYCODE_DPAD_LEFT) {
	        if(left_right_music_cherry){
	        	switchNextMusic();
	        }else{
	        	switchPreMusic();
	        }
		  } else if (keyCode == KEY_EVENT_WAKE.KEYCODE_DPAD_RIGHT) {
			  if(left_right_music_cherry){
				  switchPreMusic();
		        }else{
		          switchNextMusic();
		        }
		   }else  if(keyCode == KEY_EVENT_WAKE.KEYCODE_DPAD_UP) {
			   // do  unlock
			   type=1;
		   }else  if(keyCode == KEY_EVENT_WAKE.KEYCODE_DPAD_DOWN){
			   //lanuch into  Dial
			   type=2;
		   }else{
			   type=2;
			   sqliteManager = new SqliteManager(this);
			   str_key = sqliteManager.queryKey(keyCode);
		   }
		if(type!=-1 ){
			if (!pm.isScreenOn()) {
				Log.e("WakeGesture","sendKeyEvent------------");
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						Log.e("WakeGesture","light on  thread------------");
						sendKeyEvent();
						mHandler.sendEmptyMessage(2);
					}
				}.start();
			}
			else {
				finish();
				return;
			     }
				}else{
					finish();
					return;
				}
	}
    
    private void  switchPreMusic(){
    	Intent  intent=new Intent();
        intent.setClassName("com.android.music", "com.android.music.GesturePreNextReceiver");
        intent.setAction("com.ragentek.previous.music");
        sendBroadcast(intent);
    }
	
    private void  switchNextMusic(){
    	Intent  intent=new Intent();
        intent.setClassName("com.android.music", "com.android.music.GesturePreNextReceiver");
        intent.setAction("com.ragentek.next.music");
        sendBroadcast(intent);
    }
	private void setFrameBackground(int  keyCode){
		int  id=0;
		switch(keyCode){
		case  KEY_EVENT_WAKE.KEYCODE_C:
			id=R.anim.animation_frame_c;
			break;
		case  KEY_EVENT_WAKE.KEYCODE_E:
			id=R.anim.animation_frame_e;
			break;
		case  KEY_EVENT_WAKE.KEYCODE_M:
			id=R.anim.animation_frame_m;
			break;
		case  KEY_EVENT_WAKE.KEYCODE_O:
			id=R.anim.animation_frame_o;
			break;
		case  KEY_EVENT_WAKE.KEYCODE_W:
			id=R.anim.animation_frame_w;
			break;
		case  KEY_EVENT_WAKE.KEYCODE_DPAD_UP:
			id=R.anim.animation_frame_up;
			break;
		case  KEY_EVENT_WAKE.KEYCODE_DPAD_DOWN:
			id=R.anim.animation_frame_down;
			break;	
			
		}
		if(id!=0){
		iv_frame.setBackgroundResource(id);
		frame_Drawable=(AnimationDrawable) iv_frame.getBackground();
		frame_Drawable.setOneShot(true);
		frame_duration=getTotalFrameDuration();
		    }else{
			  finish();
		  }
		
	}
	
	private  long  getTotalFrameDuration(){
		int  totalTime=0;
		int  frameCount=frame_Drawable.getNumberOfFrames();
		return frameCount*frame_Drawable.getDuration(0);
	}
	
	private  boolean   isLightOn(int  keycode){
		return  keyCode != KEY_EVENT_WAKE.KEYCODE_DPAD_LEFT && keyCode != KEY_EVENT_WAKE.KEYCODE_DPAD_RIGHT;
	}
	
	/*******
	private void disableKeyguard() {
		getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock keyguardLock = keyguardManager
				.newKeyguardLock("MainActivity");
		if(keyguardManager.isKeyguardLocked()) {
			keyguardLock.disableKeyguard();
		}
	}
   
	private void lightScreenOn() {
		// light on Screen
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		if (!pm.isScreenOn()) {
			pm.wakeUp(5000);
		}
	}
     *******/
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("WakeGesture","onStart()------------");
		//registerScreenOnOff();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.e("WakeGesture","onRestart()------------");
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.e("WakeGesture","onResume()------------");
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		/******
		if (screenOnOffReceiver != null) {
			unregisterReceiver(screenOnOffReceiver);
		}******/
		Log.e("WakeGesture","onDestroy------------");
		Settings.Global.putInt(
		        getContentResolver(),Settings.Global.BLACK_GESTURE_WAKE_KEYEVENT,-1 );
		mHandler.removeMessages(1);
		mHandler.removeMessages(2);
		//iv_frame.setBackground(null);
		iv_frame.setBackgroundResource(0);
		if(frame_Drawable!=null){
			frame_Drawable=null;
		}
		System.gc();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	 private void sendKeyEvent() {
	        long now = SystemClock.uptimeMillis();
	            injectKeyEvent(new KeyEvent(now, now, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_POWER, 0, 0,
	            KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_KEYBOARD));
	            injectKeyEvent(new KeyEvent(now, now, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_POWER, 0, 0,
	                    KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_KEYBOARD));          
	    }
	    
	    
	private void injectKeyEvent(KeyEvent event) {
	        InputManager.getInstance().injectInputEvent(event,
	            InputManager.INJECT_INPUT_EVENT_MODE_ASYNC);
	    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return  true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
