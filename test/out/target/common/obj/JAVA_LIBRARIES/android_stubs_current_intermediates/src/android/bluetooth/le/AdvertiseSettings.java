package android.bluetooth.le;
public final class AdvertiseSettings
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseSettings.Builder setAdvertiseMode(int advertiseMode) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseSettings.Builder setTxPowerLevel(int txPowerLevel) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseSettings.Builder setConnectable(boolean connectable) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseSettings.Builder setTimeout(int timeoutMillis) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseSettings build() { throw new RuntimeException("Stub!"); }
}
AdvertiseSettings() { throw new RuntimeException("Stub!"); }
public  int getMode() { throw new RuntimeException("Stub!"); }
public  int getTxPowerLevel() { throw new RuntimeException("Stub!"); }
public  boolean isConnectable() { throw new RuntimeException("Stub!"); }
public  int getTimeout() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final int ADVERTISE_MODE_BALANCED = 1;
public static final int ADVERTISE_MODE_LOW_LATENCY = 2;
public static final int ADVERTISE_MODE_LOW_POWER = 0;
public static final int ADVERTISE_TX_POWER_HIGH = 3;
public static final int ADVERTISE_TX_POWER_LOW = 1;
public static final int ADVERTISE_TX_POWER_MEDIUM = 2;
public static final int ADVERTISE_TX_POWER_ULTRA_LOW = 0;
public static final android.os.Parcelable.Creator<android.bluetooth.le.AdvertiseSettings> CREATOR;
static { CREATOR = null; }
}
