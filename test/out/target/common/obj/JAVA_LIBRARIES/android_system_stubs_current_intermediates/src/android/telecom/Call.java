package android.telecom;
public final class Call
{
public static class Details
{
Details() { throw new RuntimeException("Stub!"); }
public static  boolean can(int capabilities, int capability) { throw new RuntimeException("Stub!"); }
public  boolean can(int capability) { throw new RuntimeException("Stub!"); }
public static  java.lang.String capabilitiesToString(int capabilities) { throw new RuntimeException("Stub!"); }
public static  boolean hasProperty(int properties, int property) { throw new RuntimeException("Stub!"); }
public  boolean hasProperty(int property) { throw new RuntimeException("Stub!"); }
public static  java.lang.String propertiesToString(int properties) { throw new RuntimeException("Stub!"); }
public  android.net.Uri getHandle() { throw new RuntimeException("Stub!"); }
public  int getHandlePresentation() { throw new RuntimeException("Stub!"); }
public  java.lang.String getCallerDisplayName() { throw new RuntimeException("Stub!"); }
public  int getCallerDisplayNamePresentation() { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccountHandle getAccountHandle() { throw new RuntimeException("Stub!"); }
public  int getCallCapabilities() { throw new RuntimeException("Stub!"); }
public  int getCallProperties() { throw new RuntimeException("Stub!"); }
public  android.telecom.DisconnectCause getDisconnectCause() { throw new RuntimeException("Stub!"); }
public final  long getConnectTimeMillis() { throw new RuntimeException("Stub!"); }
public  android.telecom.GatewayInfo getGatewayInfo() { throw new RuntimeException("Stub!"); }
public  int getVideoState() { throw new RuntimeException("Stub!"); }
public  android.telecom.StatusHints getStatusHints() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getIntentExtras() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public static final int CAPABILITY_CAN_PAUSE_VIDEO = 1048576;
public static final int CAPABILITY_DISCONNECT_FROM_CONFERENCE = 8192;
public static final int CAPABILITY_HOLD = 1;
public static final int CAPABILITY_MANAGE_CONFERENCE = 128;
public static final int CAPABILITY_MERGE_CONFERENCE = 4;
public static final int CAPABILITY_MUTE = 64;
public static final int CAPABILITY_RESPOND_VIA_TEXT = 32;
public static final int CAPABILITY_SEPARATE_FROM_CONFERENCE = 4096;
public static final int CAPABILITY_SUPPORTS_VT_LOCAL_BIDIRECTIONAL = 768;
public static final int CAPABILITY_SUPPORTS_VT_LOCAL_RX = 256;
public static final int CAPABILITY_SUPPORTS_VT_LOCAL_TX = 512;
public static final int CAPABILITY_SUPPORTS_VT_REMOTE_BIDIRECTIONAL = 3072;
public static final int CAPABILITY_SUPPORTS_VT_REMOTE_RX = 1024;
public static final int CAPABILITY_SUPPORTS_VT_REMOTE_TX = 2048;
public static final int CAPABILITY_SUPPORT_HOLD = 2;
public static final int CAPABILITY_SWAP_CONFERENCE = 8;
public static final int PROPERTY_CONFERENCE = 1;
public static final int PROPERTY_EMERGENCY_CALLBACK_MODE = 4;
public static final int PROPERTY_GENERIC_CONFERENCE = 2;
public static final int PROPERTY_HIGH_DEF_AUDIO = 16;
public static final int PROPERTY_WIFI = 8;
}
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public  void onStateChanged(android.telecom.Call call, int state) { throw new RuntimeException("Stub!"); }
public  void onParentChanged(android.telecom.Call call, android.telecom.Call parent) { throw new RuntimeException("Stub!"); }
public  void onChildrenChanged(android.telecom.Call call, java.util.List<android.telecom.Call> children) { throw new RuntimeException("Stub!"); }
public  void onDetailsChanged(android.telecom.Call call, android.telecom.Call.Details details) { throw new RuntimeException("Stub!"); }
public  void onCannedTextResponsesLoaded(android.telecom.Call call, java.util.List<java.lang.String> cannedTextResponses) { throw new RuntimeException("Stub!"); }
public  void onPostDialWait(android.telecom.Call call, java.lang.String remainingPostDialSequence) { throw new RuntimeException("Stub!"); }
public  void onVideoCallChanged(android.telecom.Call call, android.telecom.InCallService.VideoCall videoCall) { throw new RuntimeException("Stub!"); }
public  void onCallDestroyed(android.telecom.Call call) { throw new RuntimeException("Stub!"); }
public  void onConferenceableCallsChanged(android.telecom.Call call, java.util.List<android.telecom.Call> conferenceableCalls) { throw new RuntimeException("Stub!"); }
}
@java.lang.Deprecated()
public abstract static class Listener
  extends android.telecom.Call.Callback
{
public  Listener() { throw new RuntimeException("Stub!"); }
}
Call() { throw new RuntimeException("Stub!"); }
public  java.lang.String getRemainingPostDialSequence() { throw new RuntimeException("Stub!"); }
public  void answer(int videoState) { throw new RuntimeException("Stub!"); }
public  void reject(boolean rejectWithMessage, java.lang.String textMessage) { throw new RuntimeException("Stub!"); }
public  void disconnect() { throw new RuntimeException("Stub!"); }
public  void hold() { throw new RuntimeException("Stub!"); }
public  void unhold() { throw new RuntimeException("Stub!"); }
public  void playDtmfTone(char digit) { throw new RuntimeException("Stub!"); }
public  void stopDtmfTone() { throw new RuntimeException("Stub!"); }
public  void postDialContinue(boolean proceed) { throw new RuntimeException("Stub!"); }
public  void phoneAccountSelected(android.telecom.PhoneAccountHandle accountHandle, boolean setDefault) { throw new RuntimeException("Stub!"); }
public  void conference(android.telecom.Call callToConferenceWith) { throw new RuntimeException("Stub!"); }
public  void splitFromConference() { throw new RuntimeException("Stub!"); }
public  void mergeConference() { throw new RuntimeException("Stub!"); }
public  void swapConference() { throw new RuntimeException("Stub!"); }
public  android.telecom.Call getParent() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.telecom.Call> getChildren() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.telecom.Call> getConferenceableCalls() { throw new RuntimeException("Stub!"); }
public  int getState() { throw new RuntimeException("Stub!"); }
public  java.util.List<java.lang.String> getCannedTextResponses() { throw new RuntimeException("Stub!"); }
public  android.telecom.InCallService.VideoCall getVideoCall() { throw new RuntimeException("Stub!"); }
public  android.telecom.Call.Details getDetails() { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.telecom.Call.Callback callback) { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.telecom.Call.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.telecom.Call.Callback callback) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void addListener(android.telecom.Call.Listener listener) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void removeListener(android.telecom.Call.Listener listener) { throw new RuntimeException("Stub!"); }
public static final java.lang.String AVAILABLE_PHONE_ACCOUNTS = "selectPhoneAccountAccounts";
public static final int STATE_ACTIVE = 4;
public static final int STATE_CONNECTING = 9;
public static final int STATE_DIALING = 1;
public static final int STATE_DISCONNECTED = 7;
public static final int STATE_DISCONNECTING = 10;
public static final int STATE_HOLDING = 3;
public static final int STATE_NEW = 0;
@java.lang.Deprecated()
public static final int STATE_PRE_DIAL_WAIT = 8;
public static final int STATE_RINGING = 2;
public static final int STATE_SELECT_PHONE_ACCOUNT = 8;
}
