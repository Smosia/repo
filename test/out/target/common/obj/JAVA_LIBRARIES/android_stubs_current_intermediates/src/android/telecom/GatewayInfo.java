package android.telecom;
public class GatewayInfo
  implements android.os.Parcelable
{
public  GatewayInfo(java.lang.String packageName, android.net.Uri gatewayUri, android.net.Uri originalAddress) { throw new RuntimeException("Stub!"); }
public  java.lang.String getGatewayProviderPackageName() { throw new RuntimeException("Stub!"); }
public  android.net.Uri getGatewayAddress() { throw new RuntimeException("Stub!"); }
public  android.net.Uri getOriginalAddress() { throw new RuntimeException("Stub!"); }
public  boolean isEmpty() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel destination, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.telecom.GatewayInfo> CREATOR;
static { CREATOR = null; }
}
