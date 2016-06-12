package android.media.midi;
public final class MidiDeviceInfo
  implements android.os.Parcelable
{
public static final class PortInfo
{
PortInfo() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  int getPortNumber() { throw new RuntimeException("Stub!"); }
public  java.lang.String getName() { throw new RuntimeException("Stub!"); }
public static final int TYPE_INPUT = 1;
public static final int TYPE_OUTPUT = 2;
}
MidiDeviceInfo() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  int getInputPortCount() { throw new RuntimeException("Stub!"); }
public  int getOutputPortCount() { throw new RuntimeException("Stub!"); }
public  android.media.midi.MidiDeviceInfo.PortInfo[] getPorts() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getProperties() { throw new RuntimeException("Stub!"); }
public  boolean isPrivate() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.media.midi.MidiDeviceInfo> CREATOR;
public static final java.lang.String PROPERTY_BLUETOOTH_DEVICE = "bluetooth_device";
public static final java.lang.String PROPERTY_MANUFACTURER = "manufacturer";
public static final java.lang.String PROPERTY_NAME = "name";
public static final java.lang.String PROPERTY_PRODUCT = "product";
public static final java.lang.String PROPERTY_SERIAL_NUMBER = "serial_number";
public static final java.lang.String PROPERTY_USB_DEVICE = "usb_device";
public static final java.lang.String PROPERTY_VERSION = "version";
public static final int TYPE_BLUETOOTH = 3;
public static final int TYPE_USB = 1;
public static final int TYPE_VIRTUAL = 2;
static { CREATOR = null; }
}
