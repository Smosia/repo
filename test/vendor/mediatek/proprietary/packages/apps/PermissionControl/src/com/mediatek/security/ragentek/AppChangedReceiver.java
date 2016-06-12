package com.mediatek.security.ragentek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager; 
import android.content.SharedPreferences;  
import android.util.Log;

import com.mediatek.common.mom.IMobileManager;
import com.mediatek.common.mom.ReceiverRecord;
import java.util.ArrayList;
import java.util.List;

public class AppChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "AppChangedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Log.d("@M_" + TAG, "onReceive with action = " + action);            
          
            if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {  
                String packageName = intent.getData().getSchemeSpecificPart();  
                Log.d("@M_" + TAG, packageName + "--------install success !");  
                
                IMobileManager mMoMService = (IMobileManager) context.getSystemService(Context.MOBILE_SERVICE);
                List<ReceiverRecord> recordList = mMoMService.getBootReceiverList();
                if(recordList != null && recordList.size() > 0) {
                    for(ReceiverRecord item : recordList) {
                        String pkg = item.packageName;
                        if(packageName.equals(pkg)) {
                            //Log.d("@M_" + TAG, "pkg need disable packageName=" + pkg );
                            mMoMService.setBootReceiverEnabledSetting(pkg, false);
                        }
                    }
                }
            } else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {  
                String packageName = intent.getData().getSchemeSpecificPart(); 
                Log.d("@M_" + TAG, packageName + "--------replace success !");
                
            } else if("android.intent.action.ACTION_BOOT_IPO".equals(action) || Intent.ACTION_BOOT_COMPLETED.equals(action)) {
				// first boot, disable all app's auto-start feature
				SharedPreferences mSharedPreferences = context.getSharedPreferences("com.mediatek.security", 0);
				int isFirstBoot = mSharedPreferences.getInt("isFirstBoot", 0);
				if(isFirstBoot == 0) {
					IMobileManager mMoMService = (IMobileManager) context.getSystemService(Context.MOBILE_SERVICE);
					List<ReceiverRecord> recordList = mMoMService.getBootReceiverList();
					if(recordList != null && recordList.size() > 0) {
				 	    for(ReceiverRecord item : recordList) {
				 	        String pkg = item.packageName;
				 	        mMoMService.setBootReceiverEnabledSetting(pkg, false);
				  	    }
					}
					SharedPreferences.Editor mEditor = mSharedPreferences.edit();
					mEditor.putInt("isFirstBoot", 1);
					mEditor.commit();
				}
				
				killBackService(context);
			}
        }
    }
    
    private void showNotifiDialog(Context context, String packageName) {
        Intent intent = new Intent(context, AutoStartCheckActivity.class);
        intent.putExtra("packageName", packageName);
        context.startActivity(intent);
    }
    
    private void killBackService(Context context) {
    	IMobileManager mMoMService = (IMobileManager) context.getSystemService(Context.MOBILE_SERVICE);
		List<ReceiverRecord> recordList = mMoMService.getBootReceiverList();
		if(recordList != null && recordList.size() > 0) {
	 	    for(ReceiverRecord item : recordList) {
	 	        String pkg = item.packageName;
			    boolean allowed = mMoMService.getBootReceiverEnabledSetting(pkg);
			    if (!allowed) {
			    	Log.d("@M_" + TAG, "kill back service:" + pkg);   
			    	mMoMService.forceStopPackage(pkg);
			    }
	  	    }
		}
    }
}