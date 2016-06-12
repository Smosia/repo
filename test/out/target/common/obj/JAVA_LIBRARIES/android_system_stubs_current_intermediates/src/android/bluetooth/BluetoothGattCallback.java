package android.bluetooth;
public abstract class BluetoothGattCallback
{
public  BluetoothGattCallback() { throw new RuntimeException("Stub!"); }
public  void onConnectionStateChange(android.bluetooth.BluetoothGatt gatt, int status, int newState) { throw new RuntimeException("Stub!"); }
public  void onServicesDiscovered(android.bluetooth.BluetoothGatt gatt, int status) { throw new RuntimeException("Stub!"); }
public  void onCharacteristicRead(android.bluetooth.BluetoothGatt gatt, android.bluetooth.BluetoothGattCharacteristic characteristic, int status) { throw new RuntimeException("Stub!"); }
public  void onCharacteristicWrite(android.bluetooth.BluetoothGatt gatt, android.bluetooth.BluetoothGattCharacteristic characteristic, int status) { throw new RuntimeException("Stub!"); }
public  void onCharacteristicChanged(android.bluetooth.BluetoothGatt gatt, android.bluetooth.BluetoothGattCharacteristic characteristic) { throw new RuntimeException("Stub!"); }
public  void onDescriptorRead(android.bluetooth.BluetoothGatt gatt, android.bluetooth.BluetoothGattDescriptor descriptor, int status) { throw new RuntimeException("Stub!"); }
public  void onDescriptorWrite(android.bluetooth.BluetoothGatt gatt, android.bluetooth.BluetoothGattDescriptor descriptor, int status) { throw new RuntimeException("Stub!"); }
public  void onReliableWriteCompleted(android.bluetooth.BluetoothGatt gatt, int status) { throw new RuntimeException("Stub!"); }
public  void onReadRemoteRssi(android.bluetooth.BluetoothGatt gatt, int rssi, int status) { throw new RuntimeException("Stub!"); }
public  void onMtuChanged(android.bluetooth.BluetoothGatt gatt, int mtu, int status) { throw new RuntimeException("Stub!"); }
}
