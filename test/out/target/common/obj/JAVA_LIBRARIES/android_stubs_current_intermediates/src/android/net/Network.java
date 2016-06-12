package android.net;
public class Network
  implements android.os.Parcelable
{
Network() { throw new RuntimeException("Stub!"); }
public  java.net.InetAddress[] getAllByName(java.lang.String host) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }
public  java.net.InetAddress getByName(java.lang.String host) throws java.net.UnknownHostException { throw new RuntimeException("Stub!"); }
public  javax.net.SocketFactory getSocketFactory() { throw new RuntimeException("Stub!"); }
public  java.net.URLConnection openConnection(java.net.URL url) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.net.URLConnection openConnection(java.net.URL url, java.net.Proxy proxy) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void bindSocket(java.net.DatagramSocket socket) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void bindSocket(java.net.Socket socket) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void bindSocket(java.io.FileDescriptor fd) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  long getNetworkHandle() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.Network> CREATOR;
static { CREATOR = null; }
}
