package android.bluetooth;
public final class BluetoothManager
{
BluetoothManager() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothAdapter getAdapter() { throw new RuntimeException("Stub!"); }
public  int getConnectionState(android.bluetooth.BluetoothDevice device, int profile) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothDevice> getConnectedDevices(int profile) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothDevice> getDevicesMatchingConnectionStates(int profile, int[] states) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothGattServer openGattServer(android.content.Context context, android.bluetooth.BluetoothGattServerCallback callback) { throw new RuntimeException("Stub!"); }
}
