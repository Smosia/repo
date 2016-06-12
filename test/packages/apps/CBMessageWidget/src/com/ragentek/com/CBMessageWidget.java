/*
 * modify history:
 * 1.no.1:disappera CB message when in airplane mode or network is not 2G
 */


package com.ragentek.com;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.telephony.TelephonyManager;
import com.mediatek.telephony.TelephonyManagerEx;
import android.telephony.ServiceState;
import android.provider.Telephony;
import android.app.Activity;
import android.provider.Settings;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.TelephonyIntents;
import android.telephony.PhoneStateListener;
import com.android.internal.telephony.TelephonyIntents;
import android.telephony.SubscriptionManager;


public class CBMessageWidget extends AppWidgetProvider {

	public static final String RECEIVER_STR = "com.ragentek.receiver.cbmessage";
	public final static String CLICK_SETTING = "com.ragentek.action.cellbroadcast";
	public final static String START_CB_SETTING_DOUBLE_SIM = "com.ragentek.action.double_sim_cb_setting";
	public final static String START_CB_SETTING_ONE_SIM = "com.ragentek.action.one_sim_cb_setting";
	public final static String SUBID_KEY = "subId_key";
	public final static String MESSAGE_KEY = "message_key";

	private final static String TAG = "RGK/CBMessageWidget";

	private boolean mAirplaneMode;
	private static RemoteViews mRv;
	private Context mContext;
	public static final int SIM_STATE_ABSENT = 1;
	private String mNetworkName0;
	private String mNetworkName1;
	private String mNetworkNameSeparator;
	private TelephonyManagerEx mTelephonyManagerEx;
	private int mServiceState1 = ServiceState.STATE_POWER_OFF ;
	private int mServiceState2 = ServiceState.STATE_POWER_OFF ;
	
    

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.v(TAG, "============onDeleted===============");
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.v(TAG, "============onDisabled===============");
		Intent intent = new Intent(context, CBService.class);
		context.stopService(intent);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.v(TAG, "============onEnabled===============");
		/*
		Log.d(TAG, "mAirplaneMode(before) is :" + mAirplaneMode);
		Log.v(TAG, "============onEnabled===============");
		
		mAirplaneMode = isAirplaneModeOn(context);
		*/
		
		Intent intent = new Intent(context, CBService.class);
		context.startService(intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.v(TAG, "============onUpdate===============");
		mContext = context;
		mRv = new RemoteViews(context.getPackageName(), R.layout.main);

		Intent iClick = new Intent(CLICK_SETTING);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, iClick, 0);
		mRv.setOnClickPendingIntent(R.id.ivSettings, pi);
		
		updateDisplayOperatorName(mRv);
		registerPhoneServiceState(mContext);

		for (int appWidgetid : appWidgetIds) {
			appWidgetManager.updateAppWidget(appWidgetid, mRv);
		}
	}
	
	
	private void registerPhoneServiceState(Context context) {
		mTelephonyManagerEx = new TelephonyManagerEx(context);
              mTelephonyManagerEx.listen(mPhoneStateListener1,
                    PhoneStateListener.LISTEN_SERVICE_STATE,
                    1);
              mTelephonyManagerEx.listen(mPhoneStateListener2,
                    PhoneStateListener.LISTEN_SERVICE_STATE,
                    2);
	}
	
	
	/* M: phone state listner @{
	 * public static final int STATE_OUT_OF_SERVICE = 1;
	 */
    PhoneStateListener mPhoneStateListener1 = new PhoneStateListener() {
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            Log.i(TAG, "PhoneStateListener1.onServiceStateChanged: serviceState=" + serviceState);
            mServiceState1 = serviceState.getState();
            if (mServiceState1 == ServiceState.STATE_OUT_OF_SERVICE) {
            	mRv.setTextViewText(R.id.obmsg_sim1_info, "");
            }
          
        }            

    };

    PhoneStateListener mPhoneStateListener2 = new PhoneStateListener() {
        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            Log.i(TAG, "PhoneStateListener2.onServiceStateChanged: serviceState=" + serviceState);
            mServiceState2 = serviceState.getState();
            if (mServiceState2 == ServiceState.STATE_OUT_OF_SERVICE) {
            	mRv.setTextViewText(R.id.obmsg_sim2_info, "");
            }
        }                
    };

	private void showSimName() {
		int [] subIdList1 = SubscriptionManager.getSubId(0);
		int [] subIdList2 = SubscriptionManager.getSubId(1);
		
		String simOneName = getNetworkOperatorName(subIdList1[0]);
		//String simOneName2 = getNetworkOperatorName(subIdList3[0]);
		if (simOneName != null && !simOneName.equals("")) {
			mRv.setTextViewText(R.id.obmsg_sim1, "SIM1:" + simOneName);
		//} else if (simOneName2 != null && !simOneName2.equals("")) {
		//	mRv.setTextViewText(R.id.obmsg_sim1, "SIM1:" + simOneName2);
		} else {
			mRv.setTextViewText(R.id.obmsg_sim1,
					"SIM1:" + mContext.getString(R.string.no_service));
		}

		String simTwoName = getNetworkOperatorName(subIdList2[0]);
		//String simTwoName2 = getNetworkOperatorName(subIdList4[0]);
		if (simTwoName != null && !simTwoName.equals("")) {
			mRv.setTextViewText(R.id.obmsg_sim2, "SIM2:" + simTwoName);
		//} else if (simTwoName2 != null && !simTwoName2.equals("")) {
		//	mRv.setTextViewText(R.id.obmsg_sim2, "SIM2:" + simTwoName2);
		} else {
			mRv.setTextViewText(R.id.obmsg_sim2,
					"SIM2:" + mContext.getString(R.string.no_service));
		}
	}

	private String getNetworkOperatorName(int sub) {
		//TelephonyManager tm = (TelephonyManager) mContext
		//		.getSystemService(Context.TELEPHONY_SERVICE);
		//String name = tm.getSimOperatorName(sub);
		// String name = tm.getSimOperatorGemini(sub);
		
		//String name = SubscriptionManager.getSubInfoForSubscriber(sub).displayName;
		String name = null;
		if(SubscriptionManager.from(mContext).getActiveSubscriptionInfo(sub) != null) {
			name = SubscriptionManager.from(mContext).getActiveSubscriptionInfo(sub).getDisplayName().toString();
		}
		
		if(name == null) {
			name = "";
		}
		Log.v(TAG, "getNetworkOperatorName,sub->" + sub + "-> name is :->" + name);
		return name;
	}

	private String getSimCardCount() {
		TelephonyManagerEx tmEx = TelephonyManagerEx.getDefault();
		

		int sim1 = tmEx.getSimState(0);
		int sim2 = tmEx.getSimState(1);
		

		if (SIM_STATE_ABSENT == sim1 && SIM_STATE_ABSENT == sim2) {
			return "no_sim";
		}

		if (SIM_STATE_ABSENT != sim1 && SIM_STATE_ABSENT != sim2) {
			return "double_sim";
		}

		if (SIM_STATE_ABSENT != sim1 && SIM_STATE_ABSENT == sim2) {
			return "sim1";
		}

		return "sim2";
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.v(TAG, "===============onReceive=============");

		if (mContext == null)
			mContext = context;

		if (mRv == null) {
			Log.i(TAG, "mRv == null");
			mRv = new RemoteViews(context.getPackageName(), R.layout.main);
			//mAirplaneMode = isAirplaneModeOn(context);
			updateDisplayOperatorName(mRv);
		}

		Log.v(TAG, "==========setOnClickPendingIntent onReceive==========");
		Intent iClick = new Intent(CLICK_SETTING);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, iClick, 0);
		mRv.setOnClickPendingIntent(R.id.ivSettings, pi);

		String action = intent.getAction();
		Log.v(TAG, "onReceiver, action:" + action);

		if (CLICK_SETTING.equals(action)) {
			// startActivity cellbroadcastsetting
			String simState = getSimCardCount();
			Log.d(TAG,"onReceive,simState:" + simState);
			if ("no_sim".equals(simState)) {
				return;
			} else {

				Intent i = new Intent();
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				if ("double_sim".equals(simState)) {// duoble sim card
					i.setAction(START_CB_SETTING_DOUBLE_SIM);
					i.putExtra("PREFERENCE_KEY", "pref_key_cell_broadcast");
					i.putExtra("PREFERENCE_TITLE", 0);
					i.putExtra("preferenceInfo", "from_cb_widget");
					Log.v(TAG, "double_sim:");
				} else if ("sim1".equals(simState)) {// sim1
					i.setAction(START_CB_SETTING_ONE_SIM);
					//i.putExtra("simId", 0);
					int [] subIdList = SubscriptionManager.getSubId(0);
					i.putExtra(PhoneConstants.SUBSCRIPTION_KEY, subIdList[0]);
					i.putExtra("sub_title_name", getNetworkOperatorName(subIdList[0]));
					Log.v(TAG, "sim1:");
				} else if ("sim2".equals(simState)) {// sim2
					i.setAction(START_CB_SETTING_ONE_SIM);
					//i.putExtra("simId", 1);
					int [] subIdList = SubscriptionManager.getSubId(1);
					i.putExtra(PhoneConstants.SUBSCRIPTION_KEY, subIdList[0]);
					i.putExtra("sub_title_name", getNetworkOperatorName(subIdList[0]));
					Log.v(TAG, "sim2:");
				}

				mContext.startActivity(i);
			}

		} else if (RECEIVER_STR.equals(action)) {
			// action from mms
			String sub = intent.getExtras().getString(SUBID_KEY);
			String msg = intent.getExtras().getString(MESSAGE_KEY);

			Log.v(TAG, "sub:" + sub + "  " + "msg:" + msg);
// modify  BUG_ID: JWLWKK-2093   zhangyuwen  20140924(start)

//			if ("0".equals(sub)) {
			if ("0".equals(sub) && !"Oi RJ 21".equals(msg)) {
// modify  BUG_ID: JWLWKK-2093   zhangyuwen  20140924(end)
				mRv.setTextViewText(R.id.obmsg_sim1_info, msg);
// modify  BUG_ID: JWLWKK-2093   zhangyuwen  20140924(start)
//			} else if ("1".equals(sub)) {
			} else if ("1".equals(sub) && !"Oi RJ 21".equals(msg)) {
// modify  BUG_ID: JWLWKK-2093   zhangyuwen  20140924(end)
				mRv.setTextViewText(R.id.obmsg_sim2_info, msg);
			}
			//mAirplaneMode = isAirplaneModeOn(context);
			updateDisplayOperatorName(mRv);
		} else if (action.equals(Intent.ACTION_LOCALE_CHANGED)) {
			// system language change
			Log.v(TAG, "action: Intent.ACTION_LOCALE_CHANGED ");
			//mAirplaneMode = isAirplaneModeOn(context);
			updateDisplayOperatorName(mRv);
		} else if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
			Log.v(TAG, "action: Intent.ACTION_AIRPLANE_MODE_CHANGED");
			mAirplaneMode = isAirplaneModeOn(mContext);
			updateDisplayOperatorName(mRv);
			//begin [add by zhouzhuobin for no.1 20131119]
			//setCbMessage(mRv);
			//end [add by zhouzhuobin for no.1 20131119]
		} else if (action.equals(TelephonyIntents.SPN_STRINGS_UPDATED_ACTION)) {
			// network operator changes
			Log.v(TAG, "action: TelephonyIntents.SPN_STRINGS_UPDATED_ACTION");
			//mAirplaneMode = isAirplaneModeOn(context);
				// updateDisplayOperatorName(mRv);
				int subId = (int)intent.getExtras().get(PhoneConstants.SUBSCRIPTION_KEY);
				int slotId = SubscriptionManager.getSlotId(subId);
				Log.v(TAG,"subId->" + subId + "->slotId->" + slotId);
				updateNetworkName(slotId, intent.getBooleanExtra(
						TelephonyIntents.EXTRA_SHOW_SPN, false),
						intent.getStringExtra(TelephonyIntents.EXTRA_SPN),
						intent.getBooleanExtra(
								TelephonyIntents.EXTRA_SHOW_PLMN, false),
						intent.getStringExtra(TelephonyIntents.EXTRA_PLMN),
						mRv);
		} else if (action.equals("com.rgk.networkTypeChanged")) {
			Log.v(TAG,"action:com.rgk.networkTypeChanged");
			String msg = intent.getExtras().getString("NetworkTypeChanged");
			if (msg != null && msg.equals("NetworkTypeChanged")) {
				mRv.setTextViewText(R.id.obmsg_sim1_info, "");
				mRv.setTextViewText(R.id.obmsg_sim2_info, "");
			}
		}

		AppWidgetManager awm = AppWidgetManager.getInstance(context);
		int[] appIds = awm.getAppWidgetIds(new ComponentName(context,
				CBMessageWidget.class));
		awm.updateAppWidget(appIds, mRv);
	}
	
	
	
	private void updateNetworkName(int slotId, boolean showSpn, String spn,
			boolean showPlmn, String plmn, RemoteViews rv) {
		mAirplaneMode = isAirplaneModeOn(mContext);
		Log.d(TAG,"mAirplaneMode is:" + mAirplaneMode);
		Log.d(TAG, "updateNetworkName(" + slotId + "), showSpn=" + showSpn
				+ " spn=" + spn + " showPlmn=" + showPlmn + " plmn=" + plmn);

		if (mAirplaneMode) {
			Log.v(TAG, "updateNetworkName,in mAirplaneMode");
			rv.setTextViewText(R.id.obmsg_sim1,
					"SIM1:" + mContext.getString(R.string.airplane_mode_on_message));
			rv.setTextViewText(R.id.obmsg_sim2,
					"SIM2:" + mContext.getString(R.string.airplane_mode_on_message));
		} else {

			StringBuilder str = new StringBuilder();
			boolean something = false;
			mNetworkNameSeparator = mContext.getString(R.string.status_bar_network_name_separator);

			if ("no_sim".equals(getSimCardCount())) {
				rv.setTextViewText(R.id.obmsg_sim1,
						"SIM1:" + mContext.getString(R.string.no_service));
				rv.setTextViewText(R.id.obmsg_sim2,
						"SIM2:" + mContext.getString(R.string.no_service));
				Log.d(TAG,"no sim card(0&&1)");
			} else {

				if (0 == slotId) {
					if (showPlmn && plmn != null) {
						str.append(plmn);
						something = true;
					}
					if (showSpn && spn != null) {
						if (something) {
							str.append(mNetworkNameSeparator);
						}
						str.append(spn);
						something = true;
					}

					if (something) {
						mNetworkName0 = str.toString();
					} else {
						mNetworkName0 = mContext.getString(R.string.no_service);
					}
					Log.d(TAG, "updateNetworkName(" + slotId + ")"
							+ "mNetworkName0:" + mNetworkName0);
					rv.setTextViewText(R.id.obmsg_sim1, "SIM1:" + mNetworkName0);
				}

				if (1 == slotId) {
					if (showPlmn && plmn != null) {
						str.append(plmn);
						something = true;
					}
					if (showSpn && spn != null) {
						if (something) {
							str.append(mNetworkNameSeparator);
						}
						str.append(spn);
						something = true;

					}

					if (something) {
						mNetworkName1 = str.toString();
					} else {
						mNetworkName1 = mContext.getString(R.string.no_service);
					}
					Log.d(TAG, "updateNetworkName(" + slotId + ")"
							+ "mNetworkName1:" + mNetworkName1);
					rv.setTextViewText(R.id.obmsg_sim2, "SIM2:" + mNetworkName1);
				}

			}
		}

	}

	private void updateDisplayOperatorName(RemoteViews rv) {
		mAirplaneMode = isAirplaneModeOn(mContext);
		Log.d(TAG, "updateDisplayOperatorName,mAirplaneMode is" + mAirplaneMode);
		if (mAirplaneMode) {
			Log.v(TAG, "updateDisplayOperatorName,mAirplaneMode");
			//begin modify by zhouzhuobin for JWLWKK-2105 20141011
			rv.setTextViewText(R.id.obmsg_sim1, "SIM1:" + mContext.getString(R.string.airplane_mode_on_message));
			rv.setTextViewText(R.id.obmsg_sim1_info, "");
			
			rv.setTextViewText(R.id.obmsg_sim2, "SIM2:" + mContext.getString(R.string.airplane_mode_on_message));
			rv.setTextViewText(R.id.obmsg_sim2_info, "");
			//end modify by zhouzhuobinf for JWLWKK-2105 20141011
		} else {
			showSimName();
		}
	}
	
	//begin [add by zhouzhuobin for no.1 20131119]
	private void setCbMessageNull(RemoteViews rv) {
		Log.d(TAG, "setCbMessage to null");	
		mAirplaneMode = isAirplaneModeOn(mContext);
		
	   if (mAirplaneMode) {
			rv.setTextViewText(R.id.obmsg_sim1_info, "");
			rv.setTextViewText(R.id.obmsg_sim2_info, "");
		} 
		
		Log.d(TAG,"mAirplaneMode is:" + mAirplaneMode);
		
		
	}
	//end [add by zhouzhuobin for no.1 20131119]

	private static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}
}
