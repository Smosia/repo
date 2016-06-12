package android.net;
public class ScoredNetwork
  implements android.os.Parcelable
{
public  ScoredNetwork(android.net.NetworkKey networkKey, android.net.RssiCurve rssiCurve) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.ScoredNetwork> CREATOR;
public final android.net.NetworkKey networkKey;
public final android.net.RssiCurve rssiCurve;
static { CREATOR = null; }
}
