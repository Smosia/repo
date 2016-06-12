package android.net;
public final class RouteInfo
  implements android.os.Parcelable
{
RouteInfo() { throw new RuntimeException("Stub!"); }
public  android.net.IpPrefix getDestination() { throw new RuntimeException("Stub!"); }
public  java.net.InetAddress getGateway() { throw new RuntimeException("Stub!"); }
public  java.lang.String getInterface() { throw new RuntimeException("Stub!"); }
public  boolean isDefaultRoute() { throw new RuntimeException("Stub!"); }
public  boolean matches(java.net.InetAddress destination) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.RouteInfo> CREATOR;
static { CREATOR = null; }
}
