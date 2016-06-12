package android.net;
public class NetworkKey
  implements android.os.Parcelable
{
public  NetworkKey(android.net.WifiKey wifiKey) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.NetworkKey> CREATOR;
public static final int TYPE_WIFI = 1;
public final int type;
public final android.net.WifiKey wifiKey;
static { CREATOR = null; }
}
