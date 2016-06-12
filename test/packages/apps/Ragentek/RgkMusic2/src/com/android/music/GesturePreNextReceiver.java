package com.android.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GesturePreNextReceiver extends BroadcastReceiver {
    public  final  String  REVIOUS_MUSICString="com.ragentek.previous.music";
    public  final  String  NEXT_MUSICString="com.ragentek.next.music";
	@Override
	public void onReceive(Context arg0, Intent intent) {
		// TODO Auto-generated method stub
        String action=intent.getAction();
        
        if(action.equals(REVIOUS_MUSICString)){
          Intent  preIntent=new Intent(MediaPlaybackService.PREVIOUS_ACTION);
          preIntent.setClass(arg0, MediaPlaybackService.class);
          arg0.startService(preIntent);
        }
        else if(action.equals(NEXT_MUSICString)){
          Intent  nextIntent=new Intent(MediaPlaybackService.NEXT_ACTION);
          nextIntent.setClass(arg0, MediaPlaybackService.class);
          arg0.startService(nextIntent);
        }
	}

}
