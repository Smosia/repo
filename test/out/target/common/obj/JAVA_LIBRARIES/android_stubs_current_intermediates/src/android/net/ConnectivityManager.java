package android.net;
public class ConnectivityManager
{
public static interface OnNetworkActiveListener
{
public abstract  void onNetworkActive();
}
public static class NetworkCallback
{
public  NetworkCallback() { throw new RuntimeException("Stub!"); }
public  void onAvailable(android.net.Network network) { throw new RuntimeException("Stub!"); }
public  void onLosing(android.net.Network network, int maxMsToLive) { throw new RuntimeException("Stub!"); }
public  void onLost(android.net.Network network) { throw new RuntimeException("Stub!"); }
public  void onCapabilitiesChanged(android.net.Network network, android.net.NetworkCapabilities networkCapabilities) { throw new RuntimeException("Stub!"); }
public  void onLinkPropertiesChanged(android.net.Network network, android.net.LinkProperties linkProperties) { throw new RuntimeException("Stub!"); }
}
ConnectivityManager() { throw new RuntimeException("Stub!"); }
@Deprecated
public static  boolean isNetworkTypeValid(int networkType) { throw new RuntimeException("Stub!"); }
@Deprecated
public  void setNetworkPreference(int preference) { throw new RuntimeException("Stub!"); }
@Deprecated
public  int getNetworkPreference() { throw new RuntimeException("Stub!"); }
public  android.net.NetworkInfo getActiveNetworkInfo() { throw new RuntimeException("Stub!"); }
public  android.net.Network getActiveNetwork() { throw new RuntimeException("Stub!"); }
@Deprecated
public  android.net.NetworkInfo getNetworkInfo(int networkType) { throw new RuntimeException("Stub!"); }
public  android.net.NetworkInfo getNetworkInfo(android.net.Network network) { throw new RuntimeException("Stub!"); }
@Deprecated
public  android.net.NetworkInfo[] getAllNetworkInfo() { throw new RuntimeException("Stub!"); }
public  android.net.Network[] getAllNetworks() { throw new RuntimeException("Stub!"); }
public  android.net.LinkProperties getLinkProperties(android.net.Network network) { throw new RuntimeException("Stub!"); }
public  android.net.NetworkCapabilities getNetworkCapabilities(android.net.Network network) { throw new RuntimeException("Stub!"); }
@Deprecated
public  int startUsingNetworkFeature(int networkType, java.lang.String feature) { throw new RuntimeException("Stub!"); }
@Deprecated
public  int stopUsingNetworkFeature(int networkType, java.lang.String feature) { throw new RuntimeException("Stub!"); }
@Deprecated
public  boolean requestRouteToHost(int networkType, int hostAddress) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  boolean getBackgroundDataSetting() { throw new RuntimeException("Stub!"); }
public  void addDefaultNetworkActiveListener(android.net.ConnectivityManager.OnNetworkActiveListener l) { throw new RuntimeException("Stub!"); }
public  void removeDefaultNetworkActiveListener(android.net.ConnectivityManager.OnNetworkActiveListener l) { throw new RuntimeException("Stub!"); }
public  boolean isDefaultNetworkActive() { throw new RuntimeException("Stub!"); }
@Deprecated
public  void reportBadNetwork(android.net.Network network) { throw new RuntimeException("Stub!"); }
public  void reportNetworkConnectivity(android.net.Network network, boolean hasConnectivity) { throw new RuntimeException("Stub!"); }
public  android.net.ProxyInfo getDefaultProxy() { throw new RuntimeException("Stub!"); }
public  boolean isActiveNetworkMetered() { throw new RuntimeException("Stub!"); }
public  void requestNetwork(android.net.NetworkRequest request, android.net.ConnectivityManager.NetworkCallback networkCallback) { throw new RuntimeException("Stub!"); }
public  void requestNetwork(android.net.NetworkRequest request, android.app.PendingIntent operation) { throw new RuntimeException("Stub!"); }
public  void releaseNetworkRequest(android.app.PendingIntent operation) { throw new RuntimeException("Stub!"); }
public  void registerNetworkCallback(android.net.NetworkRequest request, android.net.ConnectivityManager.NetworkCallback networkCallback) { throw new RuntimeException("Stub!"); }
public  void registerNetworkCallback(android.net.NetworkRequest request, android.app.PendingIntent operation) { throw new RuntimeException("Stub!"); }
public  boolean requestBandwidthUpdate(android.net.Network network) { throw new RuntimeException("Stub!"); }
public  void unregisterNetworkCallback(android.net.ConnectivityManager.NetworkCallback networkCallback) { throw new RuntimeException("Stub!"); }
public  void unregisterNetworkCallback(android.app.PendingIntent operation) { throw new RuntimeException("Stub!"); }
public  boolean bindProcessToNetwork(android.net.Network network) { throw new RuntimeException("Stub!"); }
@Deprecated
public static  boolean setProcessDefaultNetwork(android.net.Network network) { throw new RuntimeException("Stub!"); }
public  android.net.Network getBoundNetworkForProcess() { throw new RuntimeException("Stub!"); }
@Deprecated
public static  android.net.Network getProcessDefaultNetwork() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public static final java.lang.String ACTION_BACKGROUND_DATA_SETTING_CHANGED = "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED";
public static final java.lang.String ACTION_CAPTIVE_PORTAL_SIGN_IN = "android.net.conn.CAPTIVE_PORTAL";
public static final java.lang.String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
@java.lang.Deprecated()
public static final int DEFAULT_NETWORK_PREFERENCE = 1;
public static final java.lang.String EXTRA_CAPTIVE_PORTAL = "android.net.extra.CAPTIVE_PORTAL";
public static final java.lang.String EXTRA_EXTRA_INFO = "extraInfo";
public static final java.lang.String EXTRA_IS_FAILOVER = "isFailover";
public static final java.lang.String EXTRA_NETWORK = "android.net.extra.NETWORK";
@java.lang.Deprecated()
public static final java.lang.String EXTRA_NETWORK_INFO = "networkInfo";
public static final java.lang.String EXTRA_NETWORK_REQUEST = "android.net.extra.NETWORK_REQUEST";
public static final java.lang.String EXTRA_NETWORK_TYPE = "networkType";
public static final java.lang.String EXTRA_NO_CONNECTIVITY = "noConnectivity";
public static final java.lang.String EXTRA_OTHER_NETWORK_INFO = "otherNetwork";
public static final java.lang.String EXTRA_REASON = "reason";
public static final int TYPE_BLUETOOTH = 7;
public static final int TYPE_DUMMY = 8;
public static final int TYPE_ETHERNET = 9;
public static final int TYPE_MOBILE = 0;
public static final int TYPE_MOBILE_DUN = 4;
@Deprecated
public static final int TYPE_MOBILE_HIPRI = 5;
@Deprecated
public static final int TYPE_MOBILE_MMS = 2;
@Deprecated
public static final int TYPE_MOBILE_SUPL = 3;
public static final int TYPE_VPN = 17;
public static final int TYPE_WIFI = 1;
public static final int TYPE_WIMAX = 6;
}
