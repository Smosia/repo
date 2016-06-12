/**
 * @author shiguang.du
 * @date 2014.06.17
 */
package com.rgk.factory.mservice;
import com.rgk.factory.Config;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;
import android.util.Log;
import android.content.ContentResolver;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.telephony.ITelephony;
import com.mediatek.internal.telephony.ITelephonyEx;
import com.android.internal.telephony.PhoneConstants;
import android.os.ServiceManager;
import android.content.Context;
import android.os.RemoteException;
import com.mediatek.telephony.TelephonyManagerEx;
import android.content.Context;
import android.os.SystemProperties;
public class SimCard extends Service {
	
    public static String TAG = "SimCard";
	private ITelephonyEx mTelephonyEx;
	private ITelephony mTelephony;
	private boolean hadSendBroadcast = false;
    private Context context;
    private boolean hasSim1 = false;
    private boolean hasSim2 = false;
	private static final boolean MTK_GEMINI_SUPPORT =
	    "1".equals(SystemProperties.get("ro.mtk_gemini_support"));
    @Override
    public void onCreate() {
    	
    	Log.d("dsg", "=====SimCard==>>onCreate()");
		mTelephony = ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
       // mTelephonyEx = ITelephonyEx.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICEEX));L
       
        hasSim1 = hasIccCard(0);
        hasSim2 = hasIccCard(1);
        Log.d("SimCard", "MTK_GEMINI_SUPPORT="+MTK_GEMINI_SUPPORT+" hasSim1="+hasSim1+" hasSim2="+hasSim2);
        if(MTK_GEMINI_SUPPORT) {
            if(hasSim1 && hasSim2) {
                SendBroadcast(Config.PASS);
            } else {
                SendBroadcast(Config.FAIL);
            }
        } else {
            if(hasSim1 || hasSim2) {
                SendBroadcast(Config.PASS);
            } else {
                SendBroadcast(Config.FAIL);
            }
        }
        
    }
    
    public boolean hasIccCard(int slot) {
        boolean bRet = false;
		int i;
            try {
                final TelephonyManagerEx iTelephony1 = new TelephonyManagerEx(context);
                //TelephonyManagerEx iTelephony1 = new TelephonyManagerEx(context);
                if (null != iTelephony1) {
                    //bRet = iTelephony1.isSimInsert(slot);
                    i = iTelephony1.getSimState(slot);
					if(i == 5) {
						bRet = true;
		            } else {
		            	bRet = false;
					}
                }
            } catch (Exception ex) {
                Log.d(TAG, "isSimInsert: fail");
                ex.printStackTrace();
            }	  
        return bRet;
    }
    	
		public void SendBroadcast(String result){
		if (!hadSendBroadcast) {
			hadSendBroadcast = true;
			ResultHandle.SaveResultToSystem(result, TAG);
			stopSelf();
		}
	}

	    public int getSimInof(int slotId) {
            return MTK_GEMINI_SUPPORT ? getSimIndicatorGemini(slotId) : getSimIndicator();
    }

	    public int getSimIndicator() {
				Log.d(TAG,"getSimIndicator for single");
				int indicator = -1;
					try {
						TelephonyManagerEx iTelephony = new TelephonyManagerEx(this);
						indicator = iTelephony.getSimState(0);
					} catch (Exception e) {
					}
				return indicator;
    }
	public int getSimIndicatorGemini(int slotId) {
			int indicator = -1;
				try {
					TelephonyManagerEx iTelephony = new TelephonyManagerEx(this);
					indicator = iTelephony.getSimState(slotId);
				} catch (Exception e) {
				} 
			return indicator;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		AutoTestHandle.autoBacktest(this, TAG);
		//sendBroadcast(new Intent(Config.ACTION_AUTO_BACKTEST).putExtra("test_item", TAG));
        super.onDestroy();
	}


}