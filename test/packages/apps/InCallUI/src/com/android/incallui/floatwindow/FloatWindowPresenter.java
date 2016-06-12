package com.android.incallui.floatwindow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.android.incallui.Call;
import com.android.incallui.CallCardPresenter;
import com.android.incallui.CallList;
import com.android.incallui.CallerInfoUtils;
import com.android.incallui.ContactInfoCache;
import com.android.incallui.FloatWindowAdapter;
import com.android.incallui.InCallPresenter;
import com.android.incallui.CallCardPresenter.ContactLookupCallback;
import com.android.incallui.ContactInfoCache.ContactCacheEntry;
import com.android.incallui.ContactInfoCache.ContactInfoCacheCallback;
import com.android.incallui.InCallPresenter.InCallDetailsListener;
import com.android.incallui.InCallPresenter.InCallEventListener;
import com.android.incallui.InCallPresenter.InCallState;
import com.android.incallui.InCallPresenter.InCallStateListener;
import com.android.incallui.InCallPresenter.IncomingCallListener;
import com.android.incallui.InCallPresenter.PhoneRecorderListener;
import com.mediatek.incallui.InCallTrace;
import com.mediatek.incallui.ext.ExtensionManager;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.telecom.Call.Details;
import android.telecom.PhoneAccountHandle;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.provider.Settings;

import android.telecom.PhoneAccount;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import com.android.incallui.R;

public class FloatWindowPresenter implements InCallStateListener, IncomingCallListener, 
        InCallDetailsListener,InCallEventListener, PhoneRecorderListener{

	private final static String TAG = "FloatWindow";

	private Handler handler = new Handler();

	private Timer timer;
	
	private Context mContext;
	
	private int preCallState = -1;
    private int currentCallState = -1;
    private final static int FLOAT_WINDOW_READY = 1;
    private final static int FLOAT_WINDOW_RUNNING = 2;
    private final static int FLOAT_WINDOW_STOP = 3;
    
    private Call mPrimary;
    private ContactCacheEntry mPrimaryContactInfo;
    
    private MyWindowManager mMyWindowManager;

    private FloatWindowAdapter mFloatWindowAdapter;

    private FloatWindowAdapter.AudioModeListener audioModeListener = 
    	new FloatWindowAdapter.AudioModeListener() {
    	
    	@Override
    	public void onAudioMode(int newMode) {
    		mMyWindowManager.setAudio(newMode);
    	}
    };
    
    private View.OnClickListener clickListener = new View.OnClickListener() {
    	
    	@Override
    	public void onClick(View v) {
    		switch(v.getId()) {
    		case R.id.hangup:
    			Log.d(TAG, "clickListener: hangup");
    			if (mPrimary != null) {
    				FloatWindowAdapter.hangup(mPrimary.getId());
    			}
    			break;
    			
    		case R.id.out_sound_mode:
    			Log.d(TAG, "clickListener: out sound");
    			FloatWindowAdapter.toggleSpeakerphone();
    			break;
    		}
    	}
    };

    private View.OnClickListener floatWindowClickListener = new View.OnClickListener() {
    	
    	@Override
    	public void onClick(View v) {
		Log.d(TAG, "floatWindowClickListener: v="+v);
    		final Intent intent = InCallPresenter.getInstance().getInCallIntent(
                    false /* showDialpad */, false /* newOutgoingCall */);
    		mContext.startActivity(intent);

    	}
    };
    
    // Use to mark which contact info and view needs to be updated.
    private enum CallEnum {
        PRIMARY,
        SECONDARY,
        THIRD,
        NULL;
    }
	
	public FloatWindowPresenter(Context context) {
		Log.d(TAG, "FloatWindowPresenter=>create:");

		mContext = context;
		mMyWindowManager = MyWindowManager.getInstance();
		InCallPresenter.getInstance().addListener(this);

		mFloatWindowAdapter = new FloatWindowAdapter();
		mFloatWindowAdapter.setListener(audioModeListener);
	}
	
	public void destroy() {
		Log.d(TAG, "FloatWindowPresenter=>destroy:");
		InCallPresenter.getInstance().removeListener(this);

		destroyTimer();
		mFloatWindowAdapter.destroy();
		// mMyWindowManager = null;
		mFloatWindowAdapter = null;
	}

	private void createTimer() {
		Log.d(TAG, "createTimer: timer="+timer);
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 1000);
		}
	}

	private void destroyTimer() {
		Log.d(TAG, "destroyTimer:");
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	class RefreshTask extends TimerTask {

		@Override
		public void run() {
			if(getFloatWindowValue() != 1) {
				return;
			}

			// When in home UI and float window not visible, then show float window.
			if (isHome() && !mMyWindowManager.isWindowShowing()) {
				Log.d(TAG, "RefreshTask:--------show float window.");
				handler.post(new Runnable() {
					@Override
					public void run() {
						mMyWindowManager.createFloatWindow(mContext);
						mMyWindowManager.setOnClickListener(clickListener);
						mMyWindowManager.setOnFloatWindowClickListener(floatWindowClickListener);

						updatePrimaryDisplayInfo();
					}
				});
			}
			// When not in home UI and float window is visible,then remove the float window.
			else if (!isHome() && mMyWindowManager.isWindowShowing()) {
				Log.d(TAG, "RefreshTask:--------remove float window.");
				handler.post(new Runnable() {
					@Override
					public void run() {
						mMyWindowManager.removeFloatWindow(mContext);
					}
				});
			}
			// When in home UI and float window is visible,then should update time
			else if (isHome() && mMyWindowManager.isWindowShowing()) {
				Log.d(TAG, "RefreshTask:--------update call duration.");
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (mPrimary != null) {
							long callStart = mPrimary.getConnectTimeMillis();
							final long duration = System.currentTimeMillis() - callStart;
							String callTimeElapsed = DateUtils.formatElapsedTime(duration / 1000);
							mMyWindowManager.updateCallDuration(callTimeElapsed);
						}
					}
				});
			}
		}

	}

	private int getFloatWindowValue() {
        	int value = Settings.System.getInt(mContext.getContentResolver(),
                    	Settings.System.FLOAT_WINDOW_WHEN_CALLING, 0);
        	android.util.Log.d("sqm", "getFloatWindowValue(): value="+value);
        	return value;
    	}

	/**
	 * If home UI, return true, or return false.
	 */
	private boolean isHome() {
		ActivityManager mActivityManager = (ActivityManager) mContext
		        .getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
		//Log.d("test", "isHome():"+rti.get(0).topActivity.getPackageName());
		return getHomes().contains(rti.get(0).topActivity.getPackageName());
	}

	/**
	 * Get packages
	 * 
	 */
	private List<String> getHomes() {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = mContext.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		//Log.d("test", "getHomes():size="+resolveInfo.size());
		for (ResolveInfo ri : resolveInfo) {
			//Log.d("test", "getHomes():"+ri.activityInfo.packageName);
			names.add(ri.activityInfo.packageName);
		}
		return names;
	}

	@Override
	public void onUpdateRecordState(int state, int customValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFullscreenModeChanged(boolean isFullScreenVideo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDetailsChanged(Call call, Details details) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIncomingCall(InCallState oldState, InCallState newState,
			Call call) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStateChange(InCallState oldState, InCallState newState,
			CallList callList) {
		Log.d(TAG, "onStateChange: oldState=" + oldState + ", newState="+newState);
		
		updateCallState(oldState, newState, callList);
		if (currentCallState != FLOAT_WINDOW_RUNNING) {
			return;
		}
		
		Call primary = null;
		if (newState == InCallState.INCOMING) {
            primary = callList.getIncomingCall();
        } else if (newState == InCallState.PENDING_OUTGOING || newState == InCallState.OUTGOING) {
            primary = callList.getOutgoingCall();
            if (primary == null) {
                primary = callList.getPendingOutgoingCall();
            }
        } else if (newState == InCallState.INCALL) {
            primary = getCallToDisplay(callList, null, false);
        }
		Log.d(TAG, "Primary call: " + primary);
		
		final boolean primaryChanged = !Call.areSame(mPrimary, primary);

		mPrimary = primary;

		if (mPrimary != null && primaryChanged) {
			mPrimaryContactInfo = ContactInfoCache.buildCacheEntryFromCall(mContext, mPrimary,
                    mPrimary.getState() == Call.State.INCOMING);
            updatePrimaryDisplayInfo();
            maybeStartSearch(mPrimary, CallEnum.PRIMARY);
		}
		
	}
	
	private void maybeStartSearch(Call call, CallEnum type) {
        // no need to start search for conference calls which show generic info.
        if (call != null && !call.isConferenceCall()) {
            startContactInfoSearch(call, type, call.getState() == Call.State.INCOMING);
        }
    }
	
	/**
     * Starts a query for more contact data for the save primary and secondary calls.
     */
    private void startContactInfoSearch(final Call call, CallEnum type,
            boolean isIncoming) {
        final ContactInfoCache cache = ContactInfoCache.getInstance(mContext);

        cache.findInfo(call, isIncoming, new ContactLookupCallback(this, type));
    }
	
	private void updatePrimaryDisplayInfo() {
        if (mPrimary == null) {
            // Clear the primary display info.
            
            return;
        }

        if (!mPrimary.isConferenceCall() && mPrimaryContactInfo != null) {
        	Log.d(TAG, "Update primary display info for " + mPrimaryContactInfo);

            String name = getNameForCall(mPrimaryContactInfo);
            String number = getNumberForCall(mPrimaryContactInfo);
            boolean nameIsNumber = name != null && name.equals(mPrimaryContactInfo.number);
            mMyWindowManager.updatePrimaryInfo(
                    number,
                    name,
                    nameIsNumber,
                    mPrimaryContactInfo.label,
                    mPrimaryContactInfo.photo,
                    mPrimaryContactInfo.isSipCall);
        } else if (mPrimary.isConferenceCall()) {
        	mMyWindowManager.updatePrimaryInfo(
                    null,
                    mContext.getString(R.string.confCall),
                    false,
                    null,
                    mContext.getDrawable(R.drawable.img_conference),
                    false);
        }

    }
	
	/**
     * Gets the name to display for the call.
     */
    private static String getNameForCall(ContactCacheEntry contactInfo) {
        if (TextUtils.isEmpty(contactInfo.name)) {
            return contactInfo.number;
        }
        return contactInfo.name;
    }

    /**
     * Gets the number to display for a call.
     */
    private static String getNumberForCall(ContactCacheEntry contactInfo) {
        // If the name is empty, we use the number for the name...so dont show a second
        // number in the number field
        if (TextUtils.isEmpty(contactInfo.name)) {
            return contactInfo.location;
        }
        return contactInfo.number;
    }
	
	private Call getCallToDisplay(CallList callList, Call ignore, boolean skipDisconnected) {

        // Active calls come second.  An active call always gets precedent.
        Call retval = callList.getActiveCall();
        if (retval != null && retval != ignore) {
            return retval;
        }

        // Disconnected calls get primary position if there are no active calls
        // to let user know quickly what call has disconnected. Disconnected
        // calls are very short lived.
        if (!skipDisconnected) {
            retval = callList.getDisconnectingCall();
            if (retval != null && retval != ignore) {
                return retval;
            }
            retval = callList.getDisconnectedCall();
            if (retval != null && retval != ignore) {
                return retval;
            }
        }

        // Then we go to background call (calls on hold)
        retval = callList.getBackgroundCall();
        if (retval != null && retval != ignore) {
            return retval;
        }

        // Lastly, we go to a second background call.
        retval = callList.getSecondBackgroundCall();

        return retval;
    }
	
	private void updateCallState(InCallState oldState, InCallState newState, CallList callList) {
    	if ((newState == InCallState.OUTGOING || newState == InCallState.INCOMING) && 
    			callList.getActiveOrBackgroundCall() == null) {
    		currentCallState = FLOAT_WINDOW_READY;
    	} else if ((callList.getDisconnectedCall() != null || callList.getDisconnectingCall() != null) && 
    			callList.getActiveOrBackgroundCall() == null) {
    		currentCallState = FLOAT_WINDOW_STOP;
    	} else if (callList.getActiveCall() != null) {
    		currentCallState = FLOAT_WINDOW_RUNNING;
    	}
    	
    	Call call = callList.getActiveOrBackgroundCall();
    	int slot = -1;
    	int subId = SubscriptionManager.getDefaultSubId();
    	if (call != null) {
    		PhoneAccountHandle phoneAccountHandle = call.getAccountHandle();
            final TelecomManager telecomManager = TelecomManager.from(mContext);
            final TelephonyManager telephonyManager = TelephonyManager.from(mContext);
            final PhoneAccount phoneAccount = telecomManager.getPhoneAccount(phoneAccountHandle);
    		if (phoneAccountHandle != null && TextUtils.isDigitsOnly(phoneAccountHandle.getId())) {
                subId = telephonyManager.getSubIdForPhoneAccount(phoneAccount);
    		}
    		slot = SubscriptionManager.getSlotId(subId);
    	}
    	if (preCallState == FLOAT_WINDOW_READY && currentCallState == FLOAT_WINDOW_RUNNING) {
    		createTimer();
    	} else if (preCallState == FLOAT_WINDOW_RUNNING && currentCallState == FLOAT_WINDOW_STOP) {
    		destroyTimer();
    		if (mMyWindowManager.isWindowShowing()) {
    			mMyWindowManager.removeFloatWindow(mContext);
    		}
    	}
    	preCallState = currentCallState;
    }
	
	private void onContactInfoComplete(String callId, ContactCacheEntry entry, CallEnum type) {
        /// M: Here need to check the finished querying callId is what kind of call@{
        CallEnum newType = reCalculateContactInfoType(callId);
        if (newType != CallEnum.NULL) {
            updateContactEntry(entry, newType, false);
            if (entry.name != null) {
                Log.d(TAG, "Contact found: " + entry);
            }
            /*if (entry.contactUri != null) {
                CallerInfoUtils.sendViewNotification(mContext, entry.contactUri);
            }*/
        }
    }
    /// @}

    private void onImageLoadComplete(String callId, ContactCacheEntry entry) {
        /*if (getUi() == null) {
            return;
        }*/

        if (entry.photo != null) {
            if (mPrimary != null && callId.equals(mPrimary.getId())) {
                //getUi().setPrimaryImage(entry.photo);
            }
        }
    }
    
    private void updateContactEntry(ContactCacheEntry entry, CallEnum type, boolean isConference) {
        Log.d(TAG, "updateContactEntry, type = " + type + "; entry = " + entry);
        switch (type) {
            case PRIMARY:
                mPrimaryContactInfo = entry;
                updatePrimaryDisplayInfo();
                break;
            /*case SECONDARY:
                mSecondaryContactInfo = entry;
                updateSecondaryDisplayInfo();
                break;
            case THIRD:
                mThirdContactInfo = entry;
                updateThirdDisplayInfo(isConference);
                break;*/
            default:
                break;
        }
    }
    
    private CallEnum reCalculateContactInfoType(String callId) {
        CallEnum callEnum;
        if (mPrimary != null && mPrimary.getId() == callId) {
            callEnum = CallEnum.PRIMARY;
        }/* else if(mSecondary != null && mSecondary.getId() == callId) {
            callEnum = CallEnum.SECONDARY;
        } else if(mThird != null && mThird.getId() == callId) {
            callEnum = CallEnum.THIRD;
        }*/ else {
            callEnum = CallEnum.NULL;
        }
        Log.d(TAG, "reCalculateContactInfoType... callId =, callType = " + callId + ", " + callEnum);
        return callEnum;
    }
	
	public static class ContactLookupCallback implements ContactInfoCacheCallback {
        private final WeakReference<FloatWindowPresenter> mFloatWindowPresenter;
        private final CallEnum mType;

        public ContactLookupCallback(FloatWindowPresenter floatWindowPresenter, CallEnum type) {
            mFloatWindowPresenter = new WeakReference<FloatWindowPresenter>(floatWindowPresenter);
            mType = type;
        }

        @Override
        public void onContactInfoComplete(String callId, ContactCacheEntry entry) {
        	FloatWindowPresenter presenter = mFloatWindowPresenter.get();
            if (presenter != null) {
                presenter.onContactInfoComplete(callId, entry, mType);
            }
        }

        @Override
        public void onImageLoadComplete(String callId, ContactCacheEntry entry) {
        	FloatWindowPresenter presenter = mFloatWindowPresenter.get();
            if (presenter != null) {
                presenter.onImageLoadComplete(callId, entry);
            }
        }

    }
}
