package android.hardware.usb;
public class UsbConfiguration
  implements android.os.Parcelable
{
UsbConfiguration() { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  java.lang.String getName() { throw new RuntimeException("Stub!"); }
public  boolean isSelfPowered() { throw new RuntimeException("Stub!"); }
public  boolean isRemoteWakeup() { throw new RuntimeException("Stub!"); }
public  int getMaxPower() { throw new RuntimeException("Stub!"); }
public  int getInterfaceCount() { throw new RuntimeException("Stub!"); }
public  android.hardware.usb.UsbInterface getInterface(int index) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.hardware.usb.UsbConfiguration> CREATOR;
static { CREATOR = null; }
}
