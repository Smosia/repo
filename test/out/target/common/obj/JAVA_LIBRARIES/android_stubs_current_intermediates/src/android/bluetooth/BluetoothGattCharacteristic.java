package android.bluetooth;
public class BluetoothGattCharacteristic
{
public  BluetoothGattCharacteristic(java.util.UUID uuid, int properties, int permissions) { throw new RuntimeException("Stub!"); }
public  boolean addDescriptor(android.bluetooth.BluetoothGattDescriptor descriptor) { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothGattService getService() { throw new RuntimeException("Stub!"); }
public  java.util.UUID getUuid() { throw new RuntimeException("Stub!"); }
public  int getInstanceId() { throw new RuntimeException("Stub!"); }
public  int getProperties() { throw new RuntimeException("Stub!"); }
public  int getPermissions() { throw new RuntimeException("Stub!"); }
public  int getWriteType() { throw new RuntimeException("Stub!"); }
public  void setWriteType(int writeType) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.bluetooth.BluetoothGattDescriptor> getDescriptors() { throw new RuntimeException("Stub!"); }
public  android.bluetooth.BluetoothGattDescriptor getDescriptor(java.util.UUID uuid) { throw new RuntimeException("Stub!"); }
public  byte[] getValue() { throw new RuntimeException("Stub!"); }
public  java.lang.Integer getIntValue(int formatType, int offset) { throw new RuntimeException("Stub!"); }
public  java.lang.Float getFloatValue(int formatType, int offset) { throw new RuntimeException("Stub!"); }
public  java.lang.String getStringValue(int offset) { throw new RuntimeException("Stub!"); }
public  boolean setValue(byte[] value) { throw new RuntimeException("Stub!"); }
public  boolean setValue(int value, int formatType, int offset) { throw new RuntimeException("Stub!"); }
public  boolean setValue(int mantissa, int exponent, int formatType, int offset) { throw new RuntimeException("Stub!"); }
public  boolean setValue(java.lang.String value) { throw new RuntimeException("Stub!"); }
public static final int FORMAT_FLOAT = 52;
public static final int FORMAT_SFLOAT = 50;
public static final int FORMAT_SINT16 = 34;
public static final int FORMAT_SINT32 = 36;
public static final int FORMAT_SINT8 = 33;
public static final int FORMAT_UINT16 = 18;
public static final int FORMAT_UINT32 = 20;
public static final int FORMAT_UINT8 = 17;
public static final int PERMISSION_READ = 1;
public static final int PERMISSION_READ_ENCRYPTED = 2;
public static final int PERMISSION_READ_ENCRYPTED_MITM = 4;
public static final int PERMISSION_WRITE = 16;
public static final int PERMISSION_WRITE_ENCRYPTED = 32;
public static final int PERMISSION_WRITE_ENCRYPTED_MITM = 64;
public static final int PERMISSION_WRITE_SIGNED = 128;
public static final int PERMISSION_WRITE_SIGNED_MITM = 256;
public static final int PROPERTY_BROADCAST = 1;
public static final int PROPERTY_EXTENDED_PROPS = 128;
public static final int PROPERTY_INDICATE = 32;
public static final int PROPERTY_NOTIFY = 16;
public static final int PROPERTY_READ = 2;
public static final int PROPERTY_SIGNED_WRITE = 64;
public static final int PROPERTY_WRITE = 8;
public static final int PROPERTY_WRITE_NO_RESPONSE = 4;
public static final int WRITE_TYPE_DEFAULT = 2;
public static final int WRITE_TYPE_NO_RESPONSE = 1;
public static final int WRITE_TYPE_SIGNED = 4;
protected java.util.List<android.bluetooth.BluetoothGattDescriptor> mDescriptors;
}
