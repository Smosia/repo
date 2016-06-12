package android.bluetooth;
public final class BluetoothGattServer
  implements android.bluetooth.BluetoothProfile
{
BluetoothGattServer() { throw new RuntimeException("Stub!"); }
public  void close() { throw new RuntimeException("Stub!"); }
public  boolean connect(android.bluetooth.BluetoothDevice device, boolean autoConnect) { throw new RuntimeException("Stub!"); }
public  void cancelConnection(android.bluetooth.BluetoothDevice device) { throw new RuntimeException("Stub!"); }
public  boolean sendResponse(android.bluetooth.BluetoothDevice device, int requestId, int status, int offset, byte[] value) { throw new RuntimeException("Stub!"); }
public  boolean notifyCharacteristicChanged(android.bluetooth.BluetoothDevice device, android.bluetooth.BluetoothGattCharacteristic characteristic, boolean confirm) { throw new RuntimeException("Stub!"); }
public  boolean addService(android.bluetooth.BluetoothGattService service) { throw new RuntimeException("Stub!"); }
public  boolean removeService(android.bluetooth.BluetoothGattService service) { throw new RuntimeException("Stub!"); }
public  void clearServices() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothGattService> getServices() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothGattService getService(java.util.UUID uuid) { throw new RuntimeException("Stub!"); }
public  int getConnectionState(android.bluetooth.BluetoothDevice device) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothDevice> getConnectedDevices() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothDevice> getDevicesMatchingConnectionStates(int[] states) { throw new RuntimeException("Stub!"); }
}
