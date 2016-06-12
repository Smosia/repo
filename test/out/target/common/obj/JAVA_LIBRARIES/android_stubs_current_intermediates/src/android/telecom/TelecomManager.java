package android.telecom;
public class TelecomManager
{
TelecomManager() { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccountHandle getDefaultOutgoingPhoneAccount(java.lang.String uriScheme) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccountHandle getSimCallManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccounts() { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount getPhoneAccount(android.telecom.PhoneAccountHandle account) { throw new RuntimeException("Stub!"); }
public  void registerPhoneAccount(android.telecom.PhoneAccount account) { throw new RuntimeException("Stub!"); }
public  void unregisterPhoneAccount(android.telecom.PhoneAccountHandle accountHandle) { throw new RuntimeException("Stub!"); }
public  java.lang.String getDefaultDialerPackage() { throw new RuntimeException("Stub!"); }
public  boolean isVoiceMailNumber(android.telecom.PhoneAccountHandle accountHandle, java.lang.String number) { throw new RuntimeException("Stub!"); }
public  java.lang.String getVoiceMailNumber(android.telecom.PhoneAccountHandle accountHandle) { throw new RuntimeException("Stub!"); }
public  java.lang.String getLine1Number(android.telecom.PhoneAccountHandle accountHandle) { throw new RuntimeException("Stub!"); }
public  boolean isInCall() { throw new RuntimeException("Stub!"); }
public  void silenceRinger() { throw new RuntimeException("Stub!"); }
public  void addNewIncomingCall(android.telecom.PhoneAccountHandle phoneAccount, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  boolean handleMmi(java.lang.String dialString) { throw new RuntimeException("Stub!"); }
public  boolean handleMmi(java.lang.String dialString, android.telecom.PhoneAccountHandle accountHandle) { throw new RuntimeException("Stub!"); }
public  android.net.Uri getAdnUriForPhoneAccount(android.telecom.PhoneAccountHandle accountHandle) { throw new RuntimeException("Stub!"); }
public  void cancelMissedCallsNotification() { throw new RuntimeException("Stub!"); }
public  void showInCallScreen(boolean showDialpad) { throw new RuntimeException("Stub!"); }
public  void placeCall(android.net.Uri address, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_CHANGE_DEFAULT_DIALER = "android.telecom.action.CHANGE_DEFAULT_DIALER";
public static final java.lang.String ACTION_CHANGE_PHONE_ACCOUNTS = "android.telecom.action.CHANGE_PHONE_ACCOUNTS";
public static final java.lang.String ACTION_CONFIGURE_PHONE_ACCOUNT = "android.telecom.action.CONFIGURE_PHONE_ACCOUNT";
public static final java.lang.String ACTION_DEFAULT_DIALER_CHANGED = "android.telecom.action.DEFAULT_DIALER_CHANGED";
public static final java.lang.String ACTION_INCOMING_CALL = "android.telecom.action.INCOMING_CALL";
public static final java.lang.String ACTION_SHOW_CALL_ACCESSIBILITY_SETTINGS = "android.telecom.action.SHOW_CALL_ACCESSIBILITY_SETTINGS";
public static final java.lang.String ACTION_SHOW_CALL_SETTINGS = "android.telecom.action.SHOW_CALL_SETTINGS";
public static final java.lang.String ACTION_SHOW_RESPOND_VIA_SMS_SETTINGS = "android.telecom.action.SHOW_RESPOND_VIA_SMS_SETTINGS";
public static final char DTMF_CHARACTER_PAUSE = 44;
public static final char DTMF_CHARACTER_WAIT = 59;
public static final java.lang.String EXTRA_CALL_BACK_NUMBER = "android.telecom.extra.CALL_BACK_NUMBER";
public static final java.lang.String EXTRA_CALL_DISCONNECT_CAUSE = "android.telecom.extra.CALL_DISCONNECT_CAUSE";
public static final java.lang.String EXTRA_CALL_DISCONNECT_MESSAGE = "android.telecom.extra.CALL_DISCONNECT_MESSAGE";
public static final java.lang.String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";
public static final java.lang.String EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME = "android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME";
public static final java.lang.String EXTRA_INCOMING_CALL_ADDRESS = "android.telecom.extra.INCOMING_CALL_ADDRESS";
public static final java.lang.String EXTRA_INCOMING_CALL_EXTRAS = "android.telecom.extra.INCOMING_CALL_EXTRAS";
public static final java.lang.String EXTRA_OUTGOING_CALL_EXTRAS = "android.telecom.extra.OUTGOING_CALL_EXTRAS";
public static final java.lang.String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.PHONE_ACCOUNT_HANDLE";
public static final java.lang.String EXTRA_START_CALL_WITH_SPEAKERPHONE = "android.telecom.extra.START_CALL_WITH_SPEAKERPHONE";
public static final java.lang.String EXTRA_START_CALL_WITH_VIDEO_STATE = "android.telecom.extra.START_CALL_WITH_VIDEO_STATE";
public static final java.lang.String GATEWAY_ORIGINAL_ADDRESS = "android.telecom.extra.GATEWAY_ORIGINAL_ADDRESS";
public static final java.lang.String GATEWAY_PROVIDER_PACKAGE = "android.telecom.extra.GATEWAY_PROVIDER_PACKAGE";
public static final java.lang.String METADATA_IN_CALL_SERVICE_UI = "android.telecom.IN_CALL_SERVICE_UI";
public static final int PRESENTATION_ALLOWED = 1;
public static final int PRESENTATION_PAYPHONE = 4;
public static final int PRESENTATION_RESTRICTED = 2;
public static final int PRESENTATION_UNKNOWN = 3;
}
