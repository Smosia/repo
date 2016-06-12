package com.mediatek.phone.plugin;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.provider.CallLog.Calls;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Telephony;
import android.telecom.PhoneAccountHandle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallerInfo;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.TelephonyCapabilities;
import com.mediatek.common.PluginImpl;
import com.mediatek.phone.ext.DefaultIncomingCallExt;

import android.os.Message;
import com.android.internal.telephony.PhoneProxy;
import android.os.AsyncResult;
import com.android.internal.telephony.CallStateException;
import com.android.phone.PhoneUtils;
import android.telecom.VideoProfile;

@PluginImpl(interfaceName="com.mediatek.phone.ext.IIncomingCallExt")
public class BlockCallPhoneMiscExt extends DefaultIncomingCallExt {
    private static final String LOG_TAG = "BlockCallPhoneMiscExt";
    private static final boolean DBG = true;

    private static final int EVENT_NEW_RINGING_CONNECTION = 100;
	
    public static final int VOICE_CALL_REJECT_MODE = TelephonyManager.PHONE_TYPE_GSM;
    private static final String CALL_REJECT_MODE_KEY = Settings.System.VOICE_CALL_REJECT_MODE;
    private static final int OFF = 0;
    private static final int ALL_NUMBERS = 1;
    //private static final String CMCC_BLACK_LIST_URI = "content://reject/list";
    private static final String RCS_BLACK_LIST_URI = "content://com.cmcc.ccs.black_list";
    protected Context mContext;
    private Connection mConnection;
	private PhoneProxy mPhoneProxy;

    private static final String[] RCS_BLACK_LIST_PROJECTION = {
        "PHONE_NUMBER"
    };

    public BlockCallPhoneMiscExt () {
        super();
        log("BlockCallPhoneMiscExt");
    }
	
    public BlockCallPhoneMiscExt (Context context) {
        super();
        log("BlockCallPhoneMiscExt");
        mContext = context;
    }

    public boolean handlePhoneEvent(Message msg, PhoneProxy phone) {
        log("handlePhoneEvent, msg = " + msg.what);
		mPhoneProxy = phone;
        switch(msg.what) {
            case EVENT_NEW_RINGING_CONNECTION:
                return handleRingEvent((AsyncResult) msg.obj, phone);
            default:
                return false;
        }
    }

    private void hangupRingingCall(Call call) {
        log("hangup ringing call");
        Call.State state = call.getState();
        if (state == Call.State.INCOMING) {
            try {
                call.hangup();
                log("hangupRingingCall(): regular incoming call: hangup()");
            } catch (CallStateException ex) {
                log("Call hangup: caught " + ex);
            }
        } else {
            log("hangupRingingCall: no INCOMING or WAITING call");
        }
    }

    private CallerInfo getCallerInfoFromConnection(Context context, Connection conn) {
        CallerInfo ci = null;
        Object o = conn.getUserData();
        if ((o == null) || (o instanceof CallerInfo)) {
            ci = (CallerInfo) o;
        } else if (o instanceof Uri) {
            ci = CallerInfo.getCallerInfo(context, (Uri) o);
        } else {
            ci = ((PhoneUtils.CallerInfoToken) o).currentInfo;
        }

        return ci;
    }

    private boolean handleRingEvent(AsyncResult asyncResult, PhoneProxy phone) {
        Connection connection = (Connection) asyncResult.result;
        if (connection != null) {
            Call call = connection.getCall();
            if (call != null && shouldBlockNumber(connection)) {
                hangupRingingCall(call);
                CallerInfo info = getCallerInfoFromConnection(phone.getContext(), connection);
                PhoneAccountHandle account = PhoneUtils.makePstnPhoneAccountHandle(phone);
                addRejectCallLog(info, account, connection);
                return true;
            }
        }
        return false;
    }
    /**
     * Log the message
     * @param msg the message will be printed
     */
    void log(String msg) {
        Log.d(LOG_TAG, msg);
    }

    /**
     * called when an incoming call, need to check whether it is a black number
     * @param connection
     * @return true if it is a incoming black number.
     */
     public boolean shouldBlockNumber(Connection connection) {
        String address = connection.getAddress();
        return autoReject(address);
    }

    /**
     * called when an incoming call, add reject CallLog to db
     * @param ci
     * @param phoneAccountHandle
     */
    public void addRejectCallLog(CallerInfo ci,
            PhoneAccountHandle phoneAccountHandle, Connection connection) {
        int callLogType = Calls.AUTO_REJECT_TYPE;
        int videoState = connection.getVideoState();
        int features = getCallFeatures(videoState);
        final String number = connection.getAddress();
        final long start = connection.getCreateTime();
        final int duration = (int)connection.getDurationMillis();
        final Phone phone = connection.getCall().getPhone();
        // For international calls, 011 needs to be logged as +
        final int presentation = getPresentation(connection, ci);
        final boolean isOtaspNumber = TelephonyCapabilities.supportsOtasp(phone)
            && phone.isOtaSpNumber(number);
       // Don't log OTASP calls.
        if (!isOtaspNumber) {
            try {
                //Calls.addCall(ci, mContext, number, presentation,
				Calls.addCall(ci, mPhoneProxy.getContext(), number, presentation,
                        callLogType, features, phoneAccountHandle,
                        start, duration, null, true /* addForAllUsers */);
            } catch (Exception e) {
                // This is very rare but may happen in legitimate cases.
                // E.g. If the phone is encrypted and thus write request fails, it may cause
                // some kind of Exception (right now it is IllegalArgumentException, but this
                // might change).
                //
                // We don't want to crash the whole process just because of that, so just log
                // it instead.
                log("logCall-addCall:" + e + "Exception raised during adding CallLog entry.");
            }
        }
    }

    /**
     * check if the call should be rejected
     * @param number the incoming call number
     * @param type reject type
     * @return the result that the current number should be auto reject
     */
    public boolean autoReject(String number) {
        Cursor cursor = null;
        //cursor = mContext.getContentResolver().query(Uri.parse(RCS_BLACK_LIST_URI),
        cursor = mPhoneProxy.getContext().getContentResolver().query(Uri.parse(RCS_BLACK_LIST_URI),
                    RCS_BLACK_LIST_PROJECTION, null, null, null);

        if (cursor == null) {
            if (DBG) {
                log("cursor is null...");
            }
            return false;
        }
        String blockNumber;
        boolean result = false;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            blockNumber = cursor.getString(0);
            if (PhoneNumberUtils.compare(number, blockNumber)) {
                result = true;
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    /**
     * Get the presentation from the callerinfo if not null otherwise,
     * get it from the connection.
     *
     * @param conn The phone connection.
     * @param callerInfo The CallerInfo. Maybe null.
     * @return The presentation to use in the logs.
     */
    public int getPresentation(Connection conn, CallerInfo callerInfo) {
        int presentation;

        if (null == callerInfo) {
            presentation = conn.getNumberPresentation();
        } else {
            presentation = callerInfo.numberPresentation;
            log("- getPresentation(): ignoring connection's presentation: " +
                         conn.getNumberPresentation());
        }
        log("- getPresentation: presentation: " + presentation);
        return presentation;
    }

    /**
     * change the disconnect cause when user reject a call.
     * @param disconnectCause disconnectCause
     * @return disconnectCause after modified
     */
    public int changeDisconnectCause(int disconnectCause) {
        if (disconnectCause == android.telephony.DisconnectCause.INCOMING_REJECTED) {
            disconnectCause = android.telephony.DisconnectCause.INCOMING_MISSED;
            log("changeDisconnectCause() disconnectCause: " + disconnectCause);
        }
        return disconnectCause;
    }

    /**
     * get call features.
     * @param videoState videoState
     * @return call features(VIDEO or AUDIO)
     */
    private int getCallFeatures(int videoState) {
        log("getCallFeatures()--> videoState = " + videoState);
        if (VideoProfile.isVideo(videoState)) {
            return Calls.FEATURES_VIDEO;
        }
        return 0;
    }

}