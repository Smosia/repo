package android.media.midi;
public final class MidiManager
{
public static class DeviceCallback
{
public  DeviceCallback() { throw new RuntimeException("Stub!"); }
public  void onDeviceAdded(android.media.midi.MidiDeviceInfo device) { throw new RuntimeException("Stub!"); }
public  void onDeviceRemoved(android.media.midi.MidiDeviceInfo device) { throw new RuntimeException("Stub!"); }
public  void onDeviceStatusChanged(android.media.midi.MidiDeviceStatus status) { throw new RuntimeException("Stub!"); }
}
public static interface OnDeviceOpenedListener
{
public abstract  void onDeviceOpened(android.media.midi.MidiDevice device);
}
MidiManager() { throw new RuntimeException("Stub!"); }
public  void registerDeviceCallback(android.media.midi.MidiManager.DeviceCallback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterDeviceCallback(android.media.midi.MidiManager.DeviceCallback callback) { throw new RuntimeException("Stub!"); }
public  android.media.midi.MidiDeviceInfo[] getDevices() { throw new RuntimeException("Stub!"); }
public  void openDevice(android.media.midi.MidiDeviceInfo deviceInfo, android.media.midi.MidiManager.OnDeviceOpenedListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void openBluetoothDevice(android.bluetooth.BluetoothDevice bluetoothDevice, android.media.midi.MidiManager.OnDeviceOpenedListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
}
