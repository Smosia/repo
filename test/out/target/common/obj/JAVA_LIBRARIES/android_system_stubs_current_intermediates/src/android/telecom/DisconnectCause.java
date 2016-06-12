package android.telecom;
public final class DisconnectCause
  implements android.os.Parcelable
{
public  DisconnectCause(int code) { throw new RuntimeException("Stub!"); }
public  DisconnectCause(int code, java.lang.String reason) { throw new RuntimeException("Stub!"); }
public  DisconnectCause(int code, java.lang.CharSequence label, java.lang.CharSequence description, java.lang.String reason) { throw new RuntimeException("Stub!"); }
public  DisconnectCause(int code, java.lang.CharSequence label, java.lang.CharSequence description, java.lang.String reason, int toneToPlay) { throw new RuntimeException("Stub!"); }
public  int getCode() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getLabel() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getDescription() { throw new RuntimeException("Stub!"); }
public  java.lang.String getReason() { throw new RuntimeException("Stub!"); }
public  int getTone() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel destination, int flags) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int BUSY = 7;
public static final int CANCELED = 4;
public static final int CONNECTION_MANAGER_NOT_SUPPORTED = 10;
public static final android.os.Parcelable.Creator<android.telecom.DisconnectCause> CREATOR;
public static final int ERROR = 1;
public static final int LOCAL = 2;
public static final int MISSED = 5;
public static final int OTHER = 9;
public static final int REJECTED = 6;
public static final int REMOTE = 3;
public static final int RESTRICTED = 8;
public static final int UNKNOWN = 0;
static { CREATOR = null; }
}
