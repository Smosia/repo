package android.bluetooth.le;
public final class ScanFilter
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setDeviceName(java.lang.String deviceName) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setDeviceAddress(java.lang.String deviceAddress) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setServiceUuid(android.os.ParcelUuid serviceUuid) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setServiceUuid(android.os.ParcelUuid serviceUuid, android.os.ParcelUuid uuidMask) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setServiceData(android.os.ParcelUuid serviceDataUuid, byte[] serviceData) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setServiceData(android.os.ParcelUuid serviceDataUuid, byte[] serviceData, byte[] serviceDataMask) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setManufacturerData(int manufacturerId, byte[] manufacturerData) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter.Builder setManufacturerData(int manufacturerId, byte[] manufacturerData, byte[] manufacturerDataMask) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.le.ScanFilter build() { throw new RuntimeException("Stub!"); }
}
ScanFilter() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String getDeviceName() { throw new RuntimeException("Stub!"); }
public  android.os.ParcelUuid getServiceUuid() { throw new RuntimeException("Stub!"); }
public  android.os.ParcelUuid getServiceUuidMask() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDeviceAddress() { throw new RuntimeException("Stub!"); }
public  byte[] getServiceData() { throw new RuntimeException("Stub!"); }
public  byte[] getServiceDataMask() { throw new RuntimeException("Stub!"); }
public  android.os.ParcelUuid getServiceDataUuid() { throw new RuntimeException("Stub!"); }
public  int getManufacturerId() { throw new RuntimeException("Stub!"); }
public  byte[] getManufacturerData() { throw new RuntimeException("Stub!"); }
public  byte[] getManufacturerDataMask() { throw new RuntimeException("Stub!"); }
public  boolean matches(android.bluetooth.le.ScanResult scanResult) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.bluetooth.le.ScanFilter> CREATOR;
static { CREATOR = null; }
}
