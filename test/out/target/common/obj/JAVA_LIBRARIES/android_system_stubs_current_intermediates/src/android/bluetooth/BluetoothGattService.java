package android.bluetooth;
public class BluetoothGattService
{
public  BluetoothGattService(java.util.UUID uuid, int serviceType) { throw new RuntimeException("Stub!"); }
public  boolean addService(android.bluetooth.BluetoothGattService service) { throw new RuntimeException("Stub!"); }
public  boolean addCharacteristic(android.bluetooth.BluetoothGattCharacteristic characteristic) { throw new RuntimeException("Stub!"); }
public  java.util.UUID getUuid() { throw new RuntimeException("Stub!"); }
public  int getInstanceId() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothGattService> getIncludedServices() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothGattCharacteristic> getCharacteristics() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothGattCharacteristic getCharacteristic(java.util.UUID uuid) { throw new RuntimeException("Stub!"); }
public static final int SERVICE_TYPE_PRIMARY = 0;
public static final int SERVICE_TYPE_SECONDARY = 1;
protected java.util.List<android.bluetooth.BluetoothGattCharacteristic> mCharacteristics;
protected java.util.List<android.bluetooth.BluetoothGattService> mIncludedServices;
}
