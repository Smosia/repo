package android.bluetooth;
public class BluetoothGattDescriptor
{
public  BluetoothGattDescriptor(java.util.UUID uuid, int permissions) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothGattCharacteristic getCharacteristic() { throw new RuntimeException("Stub!"); }
public  java.util.UUID getUuid() { throw new RuntimeException("Stub!"); }
public  int getPermissions() { throw new RuntimeException("Stub!"); }
public  byte[] getValue() { throw new RuntimeException("Stub!"); }
public  boolean setValue(byte[] value) { throw new RuntimeException("Stub!"); }
public static final byte[] DISABLE_NOTIFICATION_VALUE = null;
public static final byte[] ENABLE_INDICATION_VALUE = null;
public static final byte[] ENABLE_NOTIFICATION_VALUE = null;
public static final int PERMISSION_READ = 1;
public static final int PERMISSION_READ_ENCRYPTED = 2;
public static final int PERMISSION_READ_ENCRYPTED_MITM = 4;
public static final int PERMISSION_WRITE = 16;
public static final int PERMISSION_WRITE_ENCRYPTED = 32;
public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 64;
public static final int PERMISSION_WRITE_SIGNED = 128;
public static final int PERMISSION_WRITE_SIGNED_MITM = 256;
}
