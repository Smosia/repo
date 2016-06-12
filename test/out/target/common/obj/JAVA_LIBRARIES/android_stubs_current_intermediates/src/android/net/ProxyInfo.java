package android.net;
public class ProxyInfo
  implements android.os.Parcelable
{
ProxyInfo() { throw new RuntimeException("Stub!"); }
public static  android.net.ProxyInfo buildDirectProxy(java.lang.String host, int port) { throw new RuntimeException("Stub!"); }
public static  android.net.ProxyInfo buildDirectProxy(java.lang.String host, int port, java.util.List<java.lang.String> exclList) { throw new RuntimeException("Stub!"); }
public static  android.net.ProxyInfo buildPacProxy(android.net.Uri pacUri) { throw new RuntimeException("Stub!"); }
public  android.net.Uri getPacFileUrl() { throw new RuntimeException("Stub!"); }
public  java.lang.String getHost() { throw new RuntimeException("Stub!"); }
public  int getPort() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getExclusionList() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.ProxyInfo> CREATOR;
static { CREATOR = null; }
}
