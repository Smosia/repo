package android.net;
public final class LinkProperties
  implements android.os.Parcelable
{
LinkProperties() { throw new RuntimeException("Stub!"); }
public  java.lang.String getInterfaceName() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.net.LinkAddress> getLinkAddresses() { throw new RuntimeException("Stub!"); }
public  java.util.List<java.net.InetAddress> getDnsServers() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDomains() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.net.RouteInfo> getRoutes() { throw new RuntimeException("Stub!"); }
public  android.net.ProxyInfo getHttpProxy() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.LinkProperties> CREATOR;
static { CREATOR = null; }
}
