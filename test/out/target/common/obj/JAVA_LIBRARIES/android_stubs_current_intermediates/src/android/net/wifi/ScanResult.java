package android.net.wifi;
public class ScanResult
  implements android.os.Parcelable
{
ScanResult() { throw new RuntimeException("Stub!"); }
public  boolean is80211mcResponder() { throw new RuntimeException("Stub!"); }
public  boolean isPasspointNetwork() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public java.lang.String BSSID;
public static final int CHANNEL_WIDTH_160MHZ = 3;
public static final int CHANNEL_WIDTH_20MHZ = 0;
public static final int CHANNEL_WIDTH_40MHZ = 1;
public static final int CHANNEL_WIDTH_80MHZ = 2;
public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 4;
public java.lang.String SSID;
public java.lang.String capabilities;
public int centerFreq0;
public int centerFreq1;
public int channelWidth;
public int frequency;
public int level;
public java.lang.CharSequence operatorFriendlyName;
public long timestamp;
public java.lang.CharSequence venueName;
}
