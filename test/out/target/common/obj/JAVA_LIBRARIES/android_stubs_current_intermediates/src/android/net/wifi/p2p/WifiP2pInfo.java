package android.net.wifi.p2p;
public class WifiP2pInfo
  implements android.os.Parcelable
{
public  WifiP2pInfo() { throw new RuntimeException("Stub!"); }
public  WifiP2pInfo(android.net.wifi.p2p.WifiP2pInfo source) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.wifi.p2p.WifiP2pInfo> CREATOR;
public boolean groupFormed;
public java.net.InetAddress groupOwnerAddress;
public boolean isGroupOwner;
static { CREATOR = null; }
}
