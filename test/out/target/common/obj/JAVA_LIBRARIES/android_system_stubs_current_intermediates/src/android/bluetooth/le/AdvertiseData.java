package android.bluetooth.le;
public final class AdvertiseData
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseData.Builder addServiceUuid(android.os.ParcelUuid serviceUuid) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseData.Builder addServiceData(android.os.ParcelUuid serviceDataUuid, byte[] serviceData) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseData.Builder addManufacturerData(int manufacturerId, byte[] manufacturerSpecificData) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseData.Builder setIncludeTxPowerLevel(boolean includeTxPowerLevel) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseData.Builder setIncludeDeviceName(boolean includeDeviceName) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.AdvertiseData build() { throw new RuntimeException("Stub!"); }
}
AdvertiseData() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.os.ParcelUuid> getServiceUuids() { throw new RuntimeException("Stub!"); }
public  android.util.SparseArray<byte[]> getManufacturerSpecificData() { throw new RuntimeException("Stub!"); }
public  java.util.Map<android.os.ParcelUuid, byte[]> getServiceData() { throw new RuntimeException("Stub!"); }
public  boolean getIncludeTxPowerLevel() { throw new RuntimeException("Stub!"); }
public  boolean getIncludeDeviceName() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.bluetooth.le.AdvertiseData> CREATOR;
static { CREATOR = null; }
}
