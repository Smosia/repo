package android.net.wifi;
public class WifiConnectionStatistics
  implements android.os.Parcelable
{
public  WifiConnectionStatistics() { throw new RuntimeException("Stub!"); }
public  WifiConnectionStatistics(android.net.wifi.WifiConnectionStatistics source) { throw new RuntimeException("Stub!"); }
public  void incrementOrAddUntrusted(java.lang.String SSID, int connection, int usage) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.net.wifi.WifiConnectionStatistics> CREATOR;
public int num24GhzConnected;
public int num5GhzConnected;
public int numAutoJoinAttempt;
public int numAutoRoamAttempt;
public int numWifiManagerJoinAttempt;
public java.util.HashMap<java.lang.String, android.net.wifi.WifiNetworkConnectionStatistics> untrustedNetworkHistory;
static { CREATOR = null; }
}
