/**
 * @author mingwei.xue
 * @date 2015.09.10
 */
package com.rgk.factory.simsignal;

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
import android.os.ServiceManager;
import android.content.Context;
import android.os.RemoteException;
import android.content.Context;
import android.os.SystemProperties;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;

import com.android.internal.telephony.ITelephony;
//import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
//import com.android.internal.telephony.PhoneFactory;

import com.mediatek.internal.telephony.ITelephonyEx;
import com.mediatek.telephony.TelephonyManagerEx;


import java.util.ArrayList;
import java.util.List;

public class SimSignal extends Activity implements View.OnClickListener {
	
    public static String TAG = "SimSignal";
	
	
	private static final boolean MTK_GEMINI_SUPPORT =
	    "1".equals(SystemProperties.get("ro.mtk_gemini_support"));
	private int level = -1;
    /** @hide */
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    /** @hide */
    public static final int SIGNAL_STRENGTH_POOR = 1;
    /** @hide */
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    /** @hide */
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    /** @hide */
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    /** @hide */
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    /** @hide */
    public static final String[] SIGNAL_STRENGTH_NAMES = {
        "none", "poor", "moderate", "good", "great"
    };
	
	private static final int SIM_SLOT_1 = 0;
	private static final int SIM_SLOT_2 = 1;
	
	
	private ITelephonyEx mTelephonyEx;
	private ITelephony mTelephony;
	private boolean hadSendBroadcast = false;
    private Context context;
    boolean bRet = false;
    private BroadcastReceiver mReceiver = null;
    private final static String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
	private TelephonyManager mTelephonyManager;
	private List<SubscriptionInfo> mSelectableSubInfos;
	private PhoneStateListener mPhoneStateListener;
	private PhoneStateListener mPhoneStateListener2;
	
	
    private TextView sim1;
	private TextView sim2;
	private Button failBtn;
	private Button passBtn;
	
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
		Log.d(TAG,"onCreate");
        setTitle(R.string.SIM_Signal);
        setContentView(R.layout.simsignal);
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.simsignal_layout);
	    mLayout.setSystemUiVisibility(0x00002000);
		
		mTelephony = ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));
        mTelephonyEx = ITelephonyEx.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE_EX));

        mSelectableSubInfos = new ArrayList<SubscriptionInfo>();
        mTelephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		
		sim1 = (TextView)findViewById(R.id.sim1signal);
        sim2 = (TextView)findViewById(R.id.sim2signal);
        passBtn = (Button)findViewById(R.id.sim_pass);
		passBtn.setOnClickListener(this);
        failBtn = (Button)findViewById(R.id.sim_fail);
		failBtn.setOnClickListener(this);
		
        if(!MTK_GEMINI_SUPPORT) {
        	sim2.setVisibility(View.GONE);
        }
		
		
		initPhoneStateListener(SimSignal.this,SIM_SLOT_1);
		initPhoneStateListener(SimSignal.this,SIM_SLOT_2);
	
    }
    
    @Override
	protected void onResume() {        
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public boolean hasIccCard(int slot) {
		int i;
            try {
                final TelephonyManagerEx iTelephony1 = new TelephonyManagerEx(context);
                if (null != iTelephony1) {
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
	
	private void initPhoneStateListener(Context context, int slotId) {
		Log.d(TAG,"initPhoneStateListener-slotId:[" + slotId+"]");
		SubscriptionInfo mSir = findRecordBySlotId(context, slotId);
	    PhoneStateListener phoneStateListener;
        if (mSir != null) {
			final int subId = mSir.getSubscriptionId();
            Log.d(TAG,"initPhoneStateListener-subId:[" + subId+"]");
            if (SubscriptionManager.isValidSubscriptionId(subId)) {	
				switch(slotId) {
				case SIM_SLOT_1:
				   phoneStateListener = mPhoneStateListener;
				   break;
				case SIM_SLOT_2:
				   phoneStateListener = mPhoneStateListener2;
				   break;
				default:
				   Log.e(TAG,"initPhoneStateListener > slot id, error, " + slotId);
				   return;
				}
				
				if (phoneStateListener != null ) {
                    Log.d(TAG,"remove the phone state listener phoneStateListener = " + phoneStateListener);
                    mTelephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
                }
				

                phoneStateListener = new PhoneStateListener(subId) {
                    @Override
                    public void onDataConnectionStateChanged(int state) {
                    }

                    @Override
                    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                        //updateSignalStrength(signalStrength);
						Log.d(TAG,"onSignalStrengthsChanged subId = " + subId);
						SubscriptionInfo sir = SubscriptionManager.from(SimSignal.this).getSubscriptionInfo(subId);
						int slotId = sir.getSimSlotIndex();
						Log.d(TAG,"onSignalStrengthsChanged slotId = " + slotId);
						
						int signalDbm = signalStrength.getDbm();
						int signalAsu = signalStrength.getAsuLevel();
						switch(slotId) {
						case SIM_SLOT_1:
							sim1.setText("SIM1 Dbm:"+signalDbm+", Asu:"+signalAsu);
						   break;
						case SIM_SLOT_2:
							sim2.setText("SIM2 Dbm:"+signalDbm+", Asu:"+signalAsu);
						   break;
						default:
						   Log.e(TAG,"onSignalStrengthsChanged > slot id, error, " + slotId);
						   return;
						}
						
                    }

                    @Override
                    public void onServiceStateChanged(ServiceState serviceState) {
                        Log.d(TAG,"onServiceStateChanged subId = " + subId);
                    }
                };
				
				mTelephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            }
        } else {
			Log.i(TAG,"initPhoneStateListener-slotId:[" + slotId+"], NO SIM");
			switch(slotId) {
			case SIM_SLOT_1:
				sim1.setText("SIM1: no signal");
				break;
			case SIM_SLOT_2:
				sim2.setText("SIM2: no signal");
				break;
			default:
			   Log.e(TAG,"initPhoneStateListener > slot id, error, " + slotId);
			   return;
			}
		}
    }

	
	/**
     * finds a record with slotId.
     * Since the number of SIMs are few, an array is fine.
     */
    public static SubscriptionInfo findRecordBySlotId(Context context, final int slotId) {
        final List<SubscriptionInfo> subInfoList =
                SubscriptionManager.from(context).getActiveSubscriptionInfoList();
        if (subInfoList != null) {
            final int subInfoLength = subInfoList.size();

            for (int i = 0; i < subInfoLength; ++i) {
                final SubscriptionInfo sir = subInfoList.get(i);
                if (sir.getSimSlotIndex() == slotId) {
                    //Right now we take the first subscription on a SIM.
                    return sir;
                }
            }
        }

        return null;
    }
}