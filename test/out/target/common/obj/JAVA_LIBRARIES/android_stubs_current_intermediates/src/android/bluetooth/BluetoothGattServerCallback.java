package android.bluetooth;
public abstract class BluetoothGattServerCallback
{
public  BluetoothGattServerCallback() { throw new RuntimeException("Stub!"); }
public  void onConnectionStateChange(android.bluetooth.BluetoothDevice device, int status, int newState) { throw new RuntimeException("Stub!"); }
public  void onServiceAdded(int status, android.bluetooth.BluetoothGattService service) { throw new RuntimeException("Stub!"); }
public  void onCharacteristicReadRequest(android.bluetooth.BluetoothDevice device, int requestId, int offset, android.bluetooth.BluetoothGattCharacteristic characteristic) { throw new RuntimeException("Stub!"); }
public  void onCharacteristicWriteRequest(android.bluetooth.BluetoothDevice device, int requestId, android.bluetooth.BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) { throw new RuntimeException("Stub!"); }
public  void onDescriptorReadRequest(android.bluetooth.BluetoothDevice device, int requestId, int offset, android.bluetooth.BluetoothGattDescriptor descriptor) { throw new RuntimeException("Stub!"); }
public  void onDescriptorWriteRequest(android.bluetooth.BluetoothDevice device, int requestId, android.bluetooth.BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) { throw new RuntimeException("Stub!"); }
public  void onExecuteWrite(android.bluetooth.BluetoothDevice device, int requestId, boolean execute) { throw new RuntimeException("Stub!"); }
public  void onNotificationSent(android.bluetooth.BluetoothDevice device, int status) { throw new RuntimeException("Stub!"); }
public  void onMtuChanged(android.bluetooth.BluetoothDevice device, int mtu) { throw new RuntimeException("Stub!"); }
}
