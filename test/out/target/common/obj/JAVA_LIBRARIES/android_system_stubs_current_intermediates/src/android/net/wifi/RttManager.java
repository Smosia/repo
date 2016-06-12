package android.net.wifi;
public class RttManager
{
@java.lang.Deprecated()
public class Capabilities
{
public  Capabilities() { throw new RuntimeException("Stub!"); }
public int supportedPeerType;
public int supportedType;
}
public static class RttCapabilities
  implements android.os.Parcelable
{
public  RttCapabilities() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public int bwSupported;
public boolean lciSupported;
public boolean lcrSupported;
public boolean oneSidedRttSupported;
public int preambleSupported;
@java.lang.Deprecated()
public boolean supportedPeerType;
@java.lang.Deprecated()
public boolean supportedType;
public boolean twoSided11McRttSupported;
}
public static class RttParams
{
public  RttParams() { throw new RuntimeException("Stub!"); }
public boolean LCIRequest;
public boolean LCRRequest;
public int bandwidth;
public java.lang.String bssid;
public int burstTimeout;
public int centerFreq0;
public int centerFreq1;
public int channelWidth;
public int deviceType;
public int frequency;
public int interval;
public int numRetriesPerFTMR;
public int numRetriesPerMeasurementFrame;
public int numSamplesPerBurst;
@java.lang.Deprecated()
public int num_retries;
@java.lang.Deprecated()
public int num_samples;
public int numberBurst;
public int preamble;
public int requestType;
}
public static class ParcelableRttParams
  implements android.os.Parcelable
{
ParcelableRttParams() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public android.net.wifi.RttManager.RttParams[] mParams = null;
}
public static class WifiInformationElement
{
public  WifiInformationElement() { throw new RuntimeException("Stub!"); }
public byte[] data = null;
public byte id;
}
public static class RttResult
{
public  RttResult() { throw new RuntimeException("Stub!"); }
public android.net.wifi.RttManager.WifiInformationElement LCI;
public android.net.wifi.RttManager.WifiInformationElement LCR;
public java.lang.String bssid;
public int burstDuration;
public int burstNumber;
public int distance;
public int distanceSpread;
public int distanceStandardDeviation;
@java.lang.Deprecated()
public int distance_cm;
@java.lang.Deprecated()
public int distance_sd_cm;
@java.lang.Deprecated()
public int distance_spread_cm;
public int frameNumberPerBurstPeer;
public int measurementFrameNumber;
public int measurementType;
public int negotiatedBurstNum;
@java.lang.Deprecated()
public int requestType;
public int retryAfterDuration;
public int rssi;
public int rssiSpread;
@java.lang.Deprecated()
public int rssi_spread;
public long rtt;
public long rttSpread;
public long rttStandardDeviation;
@java.lang.Deprecated()
public long rtt_ns;
@java.lang.Deprecated()
public long rtt_sd_ns;
@java.lang.Deprecated()
public long rtt_spread_ns;
public int rxRate;
public int status;
public int successMeasurementFrameNumber;
public long ts;
public int txRate;
@java.lang.Deprecated()
public int tx_rate;
}
public static class ParcelableRttResults
  implements android.os.Parcelable
{
public  ParcelableRttResults(android.net.wifi.RttManager.RttResult[] results) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public android.net.wifi.RttManager.RttResult[] mResults = null;
}
public static interface RttListener
{
public abstract  void onSuccess(android.net.wifi.RttManager.RttResult[] results);
public abstract  void onFailure(int reason, java.lang.String description);
public abstract  void onAborted();
}
RttManager() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.net.wifi.RttManager.Capabilities getCapabilities() { throw new RuntimeException("Stub!"); }
public  android.net.wifi.RttManager.RttCapabilities getRttCapabilities() { throw new RuntimeException("Stub!"); }
public  void startRanging(android.net.wifi.RttManager.RttParams[] params, android.net.wifi.RttManager.RttListener listener) { throw new RuntimeException("Stub!"); }
public  void stopRanging(android.net.wifi.RttManager.RttListener listener) { throw new RuntimeException("Stub!"); }
public static final int BASE = 160256;
public static final int CMD_OP_ABORTED = 160260;
public static final int CMD_OP_FAILED = 160258;
public static final int CMD_OP_START_RANGING = 160256;
public static final int CMD_OP_STOP_RANGING = 160257;
public static final int CMD_OP_SUCCEEDED = 160259;
public static final java.lang.String DESCRIPTION_KEY = "android.net.wifi.RttManager.Description";
public static final int PREAMBLE_HT = 2;
public static final int PREAMBLE_LEGACY = 1;
public static final int PREAMBLE_VHT = 4;
public static final int REASON_INVALID_LISTENER = -3;
public static final int REASON_INVALID_REQUEST = -4;
public static final int REASON_NOT_AVAILABLE = -2;
public static final int REASON_PERMISSION_DENIED = -5;
public static final int REASON_UNSPECIFIED = -1;
public static final int RTT_BW_10_SUPPORT = 2;
public static final int RTT_BW_160_SUPPORT = 32;
public static final int RTT_BW_20_SUPPORT = 4;
public static final int RTT_BW_40_SUPPORT = 8;
public static final int RTT_BW_5_SUPPORT = 1;
public static final int RTT_BW_80_SUPPORT = 16;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_10 = 6;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_160 = 3;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_20 = 0;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_40 = 1;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_5 = 5;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_80 = 2;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_80P80 = 4;
@java.lang.Deprecated()
public static final int RTT_CHANNEL_WIDTH_UNSPECIFIED = -1;
public static final int RTT_PEER_NAN = 5;
public static final int RTT_PEER_P2P_CLIENT = 4;
public static final int RTT_PEER_P2P_GO = 3;
public static final int RTT_PEER_TYPE_AP = 1;
public static final int RTT_PEER_TYPE_STA = 2;
@java.lang.Deprecated()
public static final int RTT_PEER_TYPE_UNSPECIFIED = 0;
public static final int RTT_STATUS_ABORTED = 8;
public static final int RTT_STATUS_FAILURE = 1;
public static final int RTT_STATUS_FAIL_AP_ON_DIFF_CHANNEL = 6;
public static final int RTT_STATUS_FAIL_BUSY_TRY_LATER = 12;
public static final int RTT_STATUS_FAIL_FTM_PARAM_OVERRIDE = 15;
public static final int RTT_STATUS_FAIL_INVALID_TS = 9;
public static final int RTT_STATUS_FAIL_NOT_SCHEDULED_YET = 4;
public static final int RTT_STATUS_FAIL_NO_CAPABILITY = 7;
public static final int RTT_STATUS_FAIL_NO_RSP = 2;
public static final int RTT_STATUS_FAIL_PROTOCOL = 10;
public static final int RTT_STATUS_FAIL_REJECTED = 3;
public static final int RTT_STATUS_FAIL_SCHEDULE = 11;
public static final int RTT_STATUS_FAIL_TM_TIMEOUT = 5;
public static final int RTT_STATUS_INVALID_REQ = 13;
public static final int RTT_STATUS_NO_WIFI = 14;
public static final int RTT_STATUS_SUCCESS = 0;
@java.lang.Deprecated()
public static final int RTT_TYPE_11_MC = 4;
@java.lang.Deprecated()
public static final int RTT_TYPE_11_V = 2;
public static final int RTT_TYPE_ONE_SIDED = 1;
public static final int RTT_TYPE_TWO_SIDED = 2;
@java.lang.Deprecated()
public static final int RTT_TYPE_UNSPECIFIED = 0;
}
