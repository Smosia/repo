package android.telecom;
public final class ConnectionRequest
  implements android.os.Parcelable
{
public  ConnectionRequest(android.telecom.PhoneAccountHandle accountHandle, android.net.Uri handle, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  ConnectionRequest(android.telecom.PhoneAccountHandle accountHandle, android.net.Uri handle, android.os.Bundle extras, int videoState) { throw new RuntimeException("Stub!"); }
public  android.telecom.PhoneAccountHandle getAccountHandle() { throw new RuntimeException("Stub!"); }
public  android.net.Uri getAddress() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  int getVideoState() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel destination, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.telecom.ConnectionRequest> CREATOR;
static { CREATOR = null; }
}
