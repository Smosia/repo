package android.net.wifi;
public class WifiManager
{
public abstract static class WpsCallback
{
public  WpsCallback() { throw new RuntimeException("Stub!"); }
public abstract  void onStarted(java.lang.String pin);
public abstract  void onSucceeded();
public abstract  void onFailed(int reason);
}
public class WifiLock
{
WifiLock() { throw new RuntimeException("Stub!"); }
public  void acquire() { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
public  void setReferenceCounted(boolean refCounted) { throw new RuntimeException("Stub!"); }
public  boolean isHeld() { throw new RuntimeException("Stub!"); }
public  void setWorkSource(android.os.WorkSource ws) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
}
public class MulticastLock
{
MulticastLock() { throw new RuntimeException("Stub!"); }
public  void acquire() { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
public  void setReferenceCounted(boolean refCounted) { throw new RuntimeException("Stub!"); }
public  boolean isHeld() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
}
WifiManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.net.wifi.WifiConfiguration> getConfiguredNetworks() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.net.wifi.WifiConfiguration> getPrivilegedConfiguredNetworks() { throw new RuntimeException("Stub!"); }
public  android.net.wifi.WifiConnectionStatistics getConnectionStatistics() { throw new RuntimeException("Stub!"); }
public  int addNetwork(android.net.wifi.WifiConfiguration config) { throw new RuntimeException("Stub!"); }
public  int updateNetwork(android.net.wifi.WifiConfiguration config) { throw new RuntimeException("Stub!"); }
public  boolean removeNetwork(int netId) { throw new RuntimeException("Stub!"); }
public  boolean enableNetwork(int netId, boolean disableOthers) { throw new RuntimeException("Stub!"); }
public  boolean disableNetwork(int netId) { throw new RuntimeException("Stub!"); }
public  boolean disconnect() { throw new RuntimeException("Stub!"); }
public  boolean reconnect() { throw new RuntimeException("Stub!"); }
public  boolean reassociate() { throw new RuntimeException("Stub!"); }
public  boolean pingSupplicant() { throw new RuntimeException("Stub!"); }
public  boolean is5GHzBandSupported() { throw new RuntimeException("Stub!"); }
public  boolean isP2pSupported() { throw new RuntimeException("Stub!"); }
public  boolean isPortableHotspotSupported() { throw new RuntimeException("Stub!"); }
public  boolean isWifiScannerSupported() { throw new RuntimeException("Stub!"); }
public  boolean isDeviceToDeviceRttSupported() { throw new RuntimeException("Stub!"); }
public  boolean isDeviceToApRttSupported() { throw new RuntimeException("Stub!"); }
public  boolean isPreferredNetworkOffloadSupported() { throw new RuntimeException("Stub!"); }
public  boolean isTdlsSupported() { throw new RuntimeException("Stub!"); }
public  boolean isEnhancedPowerReportingSupported() { throw new RuntimeException("Stub!"); }
public  boolean startScan() { throw new RuntimeException("Stub!"); }
public  boolean startScan(android.os.WorkSource workSource) { throw new RuntimeException("Stub!"); }
public  boolean startLocationRestrictedScan(android.os.WorkSource workSource) { throw new RuntimeException("Stub!"); }
public  boolean isBatchedScanSupported() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.net.wifi.BatchedScanResult> getBatchedScanResults() { throw new RuntimeException("Stub!"); }
public  android.net.wifi.WifiInfo getConnectionInfo() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.net.wifi.ScanResult> getScanResults() { throw new RuntimeException("Stub!"); }
public  boolean isScanAlwaysAvailable() { throw new RuntimeException("Stub!"); }
public  boolean saveConfiguration() { throw new RuntimeException("Stub!"); }
public  android.net.DhcpInfo getDhcpInfo() { throw new RuntimeException("Stub!"); }
public  boolean setWifiEnabled(boolean enabled) { throw new RuntimeException("Stub!"); }
public  int getWifiState() { throw new RuntimeException("Stub!"); }
public  boolean isWifiEnabled() { throw new RuntimeException("Stub!"); }
public static  int calculateSignalLevel(int rssi, int numLevels) { throw new RuntimeException("Stub!"); }
public static  int compareSignalLevel(int rssiA, int rssiB) { throw new RuntimeException("Stub!"); }
public  void setTdlsEnabled(java.net.InetAddress remoteIPAddress, boolean enable) { throw new RuntimeException("Stub!"); }
public  void setTdlsEnabledWithMacAddress(java.lang.String remoteMacAddress, boolean enable) { throw new RuntimeException("Stub!"); }
public  void startWps(android.net.wifi.WpsInfo config, android.net.wifi.WifiManager.WpsCallback listener) { throw new RuntimeException("Stub!"); }
public  void cancelWps(android.net.wifi.WifiManager.WpsCallback listener) { throw new RuntimeException("Stub!"); }
public  android.net.wifi.WifiManager.WifiLock createWifiLock(int lockType, java.lang.String tag) { throw new RuntimeException("Stub!"); }
public  android.net.wifi.WifiManager.WifiLock createWifiLock(java.lang.String tag) { throw new RuntimeException("Stub!"); }
public  android.net.wifi.WifiManager.MulticastLock createMulticastLock(java.lang.String tag) { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_PICK_WIFI_NETWORK = "android.net.wifi.PICK_WIFI_NETWORK";
public static final java.lang.String ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE = "android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE";
public static final int CHANGE_REASON_ADDED = 0;
public static final int CHANGE_REASON_CONFIG_CHANGE = 2;
public static final int CHANGE_REASON_REMOVED = 1;
public static final java.lang.String CONFIGURED_NETWORKS_CHANGED_ACTION = "android.net.wifi.CONFIGURED_NETWORKS_CHANGE";
public static final int ERROR_AUTHENTICATING = 1;
public static final java.lang.String EXTRA_BSSID = "bssid";
public static final java.lang.String EXTRA_CHANGE_REASON = "changeReason";
public static final java.lang.String EXTRA_MULTIPLE_NETWORKS_CHANGED = "multipleChanges";
public static final java.lang.String EXTRA_NETWORK_INFO = "networkInfo";
public static final java.lang.String EXTRA_NEW_RSSI = "newRssi";
public static final java.lang.String EXTRA_NEW_STATE = "newState";
public static final java.lang.String EXTRA_PREVIOUS_WIFI_STATE = "previous_wifi_state";
public static final java.lang.String EXTRA_RESULTS_UPDATED = "resultsUpdated";
public static final java.lang.String EXTRA_SUPPLICANT_CONNECTED = "connected";
public static final java.lang.String EXTRA_SUPPLICANT_ERROR = "supplicantError";
public static final java.lang.String EXTRA_WIFI_CONFIGURATION = "wifiConfiguration";
public static final java.lang.String EXTRA_WIFI_CREDENTIAL_EVENT_TYPE = "et";
public static final java.lang.String EXTRA_WIFI_CREDENTIAL_SSID = "ssid";
public static final java.lang.String EXTRA_WIFI_INFO = "wifiInfo";
public static final java.lang.String EXTRA_WIFI_STATE = "wifi_state";
public static final java.lang.String NETWORK_IDS_CHANGED_ACTION = "android.net.wifi.NETWORK_IDS_CHANGED";
public static final java.lang.String NETWORK_STATE_CHANGED_ACTION = "android.net.wifi.STATE_CHANGE";
public static final java.lang.String RSSI_CHANGED_ACTION = "android.net.wifi.RSSI_CHANGED";
public static final java.lang.String SCAN_RESULTS_AVAILABLE_ACTION = "android.net.wifi.SCAN_RESULTS";
public static final java.lang.String SUPPLICANT_CONNECTION_CHANGE_ACTION = "android.net.wifi.supplicant.CONNECTION_CHANGE";
public static final java.lang.String SUPPLICANT_STATE_CHANGED_ACTION = "android.net.wifi.supplicant.STATE_CHANGE";
public static final java.lang.String WIFI_CREDENTIAL_CHANGED_ACTION = "android.net.wifi.WIFI_CREDENTIAL_CHANGED";
public static final int WIFI_CREDENTIAL_FORGOT = 1;
public static final int WIFI_CREDENTIAL_SAVED = 0;
public static final int WIFI_MODE_FULL = 1;
public static final int WIFI_MODE_FULL_HIGH_PERF = 3;
public static final int WIFI_MODE_SCAN_ONLY = 2;
public static final java.lang.String WIFI_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";
public static final int WIFI_STATE_DISABLED = 1;
public static final int WIFI_STATE_DISABLING = 0;
public static final int WIFI_STATE_ENABLED = 3;
public static final int WIFI_STATE_ENABLING = 2;
public static final int WIFI_STATE_UNKNOWN = 4;
public static final int WPS_AUTH_FAILURE = 6;
public static final int WPS_OVERLAP_ERROR = 3;
public static final int WPS_TIMED_OUT = 7;
public static final int WPS_TKIP_ONLY_PROHIBITED = 5;
public static final int WPS_WEP_PROHIBITED = 4;
}
