package android.bluetooth;
public final class BluetoothSocket
  implements java.io.Closeable
{
BluetoothSocket() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothDevice getRemoteDevice() { throw new RuntimeException("Stub!"); }
public  java.io.InputStream getInputStream() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.io.OutputStream getOutputStream() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  boolean isConnected() { throw new RuntimeException("Stub!"); }
public  void connect() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void close() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  int getMaxTransmitPacketSize() { throw new RuntimeException("Stub!"); }
public  int getMaxReceivePacketSize() { throw new RuntimeException("Stub!"); }
public  int getConnectionType() { throw new RuntimeException("Stub!"); }
public static final int TYPE_L2CAP = 3;
public static final int TYPE_RFCOMM = 1;
public static final int TYPE_SCO = 2;
}
