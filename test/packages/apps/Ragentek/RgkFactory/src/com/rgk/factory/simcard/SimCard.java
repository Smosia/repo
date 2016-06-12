/**
 * @author shiguang.du
 * @date 2014.06.17
 */
package com.rgk.factory.simcard;

import com.rgk.factory.Config;
import com.rgk.factory.R;
import com.rgk.factory.ControlCenter.AutoTestHandle;
import com.rgk.factory.ControlCenter.ResultHandle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.IntentFilter;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
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
public class SimCard extends Activity implements View.OnClickListener {
	
    public static String TAG = "SimCard";
	private ITelephonyEx mTelephonyEx;
	private ITelephony mTelephony;
	private boolean hadSendBroadcast = false;
    private Context context;
    boolean bRet = false;
    private BroadcastReceiver mReceiver = null;
    private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    TextView sim1,sim2;
	private static final boolean MTK_GEMINI_SUPPORT =
	    "1".equals(SystemProperties.get("ro.mtk_gemini_support"));
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
        setTitle(R.string.SIM_Card);
        setContentView(R.layout.simcard);
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.simcard_layout);
	    mLayout.setSystemUiVisibility(0x00002000);
		mTelephony = ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
        mTelephonyEx = ITelephonyEx.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE_EX));

        sim1 = (TextView)findViewById(R.id.sim1);
        sim2 = (TextView)findViewById(R.id.sim2);
        
        ((Button)findViewById(R.id.sim_pass)).setOnClickListener(this);
        ((Button)findViewById(R.id.sim_fail)).setOnClickListener(this);
        if(!MTK_GEMINI_SUPPORT)
        {
        	((TextView)findViewById(R.id.sim2)).setVisibility(View.GONE);
        }
        IntentFilter filter = new IntentFilter(ACTION_SIM_STATE_CHANGED);
        mReceiver = new SimStateReceive();
        registerReceiver(mReceiver, filter);
    }
    
    @Override
	protected void onResume() {        
        sim1.setText(hasIccCard(0) ? R.string.sim1_find : R.string.sim1_notfind);
        sim2.setText(hasIccCard(1) ? R.string.sim2_find : R.string.sim2_notfind);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	public boolean hasIccCard(int slot) {
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
    
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		   case R.id.sim_pass:
			   SendBroadcast(Config.PASS);
			   break;
		   case R.id.sim_fail:
			   SendBroadcast(Config.FAIL);
			   break;
		}
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
	
	class SimStateReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(ACTION_SIM_STATE_CHANGED)) {
				sim1.setText(hasIccCard(0) ? R.string.sim1_find : R.string.sim1_notfind);
		        sim2.setText(hasIccCard(1) ? R.string.sim2_find : R.string.sim2_notfind);
			}
		}
	}
}