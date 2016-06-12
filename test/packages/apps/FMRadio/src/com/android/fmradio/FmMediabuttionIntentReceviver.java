package com.android.fmradio;


import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;

public class FmMediabuttionIntentReceviver extends BroadcastReceiver {
	 private static final String TAG = "FmMediabuttionIntentReceviver";
	 private static long mEventTime =0;
	    private static long mEventTime2=0;
	    private static long mTime_difference =0;
	
	@Override
	public void onReceive( final Context context, Intent intent) {
		// TODO Auto-generated method stub	       
				if(FMRadioCITActivity.mInCITMode){
					return ;
				}
	            String action = intent.getAction();	           
	            KeyEvent event = (KeyEvent)
	            intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT); 
	                      
	           
	           
	            if (Intent.ACTION_MEDIA_BUTTON.equals(action)) {
	                if (KeyEvent.KEYCODE_HEADSETHOOK == event.getKeyCode() && event.getAction() == KeyEvent.ACTION_UP) {
	                	
	                   	 //add JWBLB-1220 gaoxueyan 20150806 start
	                    if(context.getResources().getBoolean(com.android.internal.R.bool.def_longpress_headhook_music))
	                    { long downtime=event.getDownTime();
	                    long current=event.getEventTime();
	                    Log.d(TAG, "downtime="+downtime+"current="+current);                   
	                    if(current-downtime>500)	                    
	                    {       Log.d(TAG, "intent2.setAction"+FmService.FM_SEEK_NEXT);
								 Intent intent2=new Intent(context,FmService.class);	
								intent2.setAction(FmService.FM_SEEK_NEXT);
								context.startServiceAsUser(intent2, UserHandle.CURRENT);
	                    	
	                    }else{
                        Log.d(TAG, "intent1.setAction"+FmService.CMDPAUSE_PLAY);
	            				Intent intent1=new Intent(context,FmService.class);	  
								intent1.setAction(FmService.CMDPAUSE_PLAY);
								context.startServiceAsUser(intent1, UserHandle.CURRENT);
	                       }
	                    }else{
	                  ////add JWBLB-1220 gaoxueyan 20150806 end
	                    
	                  //modify Bug_id:JLLJ-141 chenchunyong 20141126 (start)
						mEventTime = mEventTime2;
						mEventTime2 = event.getEventTime();
						mTime_difference = mEventTime2 - mEventTime;
						TimerTask task = new TimerTask() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (mTime_difference <= 500) {
									Intent intent1=new Intent(context,FmService.class);	  
									intent1.setAction(FmService.FM_SEEK_NEXT);
									context.startServiceAsUser(intent1, UserHandle.CURRENT);
									/*if ((null != mService)&&(mService.getPowerStatus() == FmService.POWER_UP)) {
				                    	Log.d(TAG, "onMediaButtonCall.power down fm");				                    	
				                        mService.powerDownAsync();
				                    	 
				                    }
				                    if((null != mService)&&(mService.getPowerStatus() == FmService.POWER_DOWN))
				                    {
				                    	Log.d(TAG, "onMediaButtonCall.power up fm");
				                    	
				                    	
				                        mService.powerUpAsync(FmUtils.computeFrequency(mCurrentStation));
				                    }*/
								} else {
									Intent intent2=new Intent(context,FmService.class);	 
									intent2.setAction(FmService.CMDPAUSE_PLAY);
									context.startServiceAsUser(intent2, UserHandle.CURRENT);
									
					            
									/*if((null != mService)&&(mService.getPowerStatus() == FmService.POWER_DOWN))
									{
										powerUpFm();
									}
									 seekStation(mCurrentStation, true);*/
								} 
							}
						};
							Timer timer = new Timer();
							timer.schedule(task, 501);
							if (mTime_difference <= 500) {
								timer.cancel();
							 }
							}
								if (isOrderedBroadcast()) {
				                    abortBroadcast();
				                }
	                }
	            }
			}
}