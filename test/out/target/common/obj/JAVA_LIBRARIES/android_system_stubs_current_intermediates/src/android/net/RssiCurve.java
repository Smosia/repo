package android.net;
public class RssiCurve
  implements android.os.Parcelable
{
public  RssiCurve(int start, int bucketWidth, byte[] rssiBuckets) { throw new RuntimeException("Stub!"); }
public  RssiCurve(int start, int bucketWidth, byte[] rssiBuckets, int activeNetworkRssiBoost) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  byte lookupScore(int rssi) { throw new RuntimeException("Stub!"); }
public  byte lookupScore(int rssi, boolean isActiveNetwork) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.RssiCurve> CREATOR;
public final int activeNetworkRssiBoost;
public final int bucketWidth;
public final byte[] rssiBuckets = null;
public final int start;
static { CREATOR = null; }
}
