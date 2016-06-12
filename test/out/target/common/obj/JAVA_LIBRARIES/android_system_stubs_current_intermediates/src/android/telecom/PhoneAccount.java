package android.telecom;
public final class PhoneAccount
  implements android.os.Parcelable
{
public static class Builder
{
public  Builder(android.telecom.PhoneAccountHandle accountHandle, java.lang.CharSequence label) { throw new RuntimeException("Stub!"); }
public  Builder(android.telecom.PhoneAccount phoneAccount) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setAddress(android.net.Uri value) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setSubscriptionAddress(android.net.Uri value) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setCapabilities(int value) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setIcon(android.graphics.drawable.Icon icon) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setHighlightColor(int value) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setShortDescription(java.lang.CharSequence value) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder addSupportedUriScheme(java.lang.String uriScheme) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder setSupportedUriSchemes(java.util.List<java.lang.String> uriSchemes) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount build() { throw new RuntimeException("Stub!"); }
}
PhoneAccount() { throw new RuntimeException("Stub!"); }
public static  android.telecom.PhoneAccount.Builder builder(android.telecom.PhoneAccountHandle accountHandle, java.lang.CharSequence label) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccount.Builder toBuilder() { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccountHandle getAccountHandle() { throw new RuntimeException("Stub!"); }
public  android.net.Uri getAddress() { throw new RuntimeException("Stub!"); }
public  android.net.Uri getSubscriptionAddress() { throw new RuntimeException("Stub!"); }
public  int getCapabilities() { throw new RuntimeException("Stub!"); }
public  boolean hasCapabilities(int capability) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getLabel() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getShortDescription() { throw new RuntimeException("Stub!"); }
public  java.util.List<java.lang.String> getSupportedUriSchemes() { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Icon getIcon() { throw new RuntimeException("Stub!"); }
public  boolean isEnabled() { throw new RuntimeException("Stub!"); }
public  boolean supportsUriScheme(java.lang.String uriScheme) { throw new RuntimeException("Stub!"); }
public  int getHighlightColor() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int CAPABILITY_CALL_PROVIDER = 2;
public static final int CAPABILITY_CALL_SUBJECT = 64;
public static final int CAPABILITY_CONNECTION_MANAGER = 1;
public static final int CAPABILITY_MULTI_USER = 32;
public static final int CAPABILITY_PLACE_EMERGENCY_CALLS = 16;
public static final int CAPABILITY_SIM_SUBSCRIPTION = 4;
public static final int CAPABILITY_VIDEO_CALLING = 8;
public static final android.os.Parcelable.Creator<android.telecom.PhoneAccount> CREATOR;
public static final int NO_HIGHLIGHT_COLOR = 0;
public static final int NO_RESOURCE_ID = -1;
public static final java.lang.String SCHEME_SIP = "sip";
public static final java.lang.String SCHEME_TEL = "tel";
public static final java.lang.String SCHEME_VOICEMAIL = "voicemail";
static { CREATOR = null; }
}
