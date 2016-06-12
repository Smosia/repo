package android.print;
public final class PrinterInfo
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder(android.print.PrinterId printerId, java.lang.String name, int status) { throw new RuntimeException("Stub!"); }
public  Builder(android.print.PrinterInfo other) { throw new RuntimeException("Stub!"); }
public  android.print.PrinterInfo.Builder setStatus(int status) { throw new RuntimeException("Stub!"); }
public  android.print.PrinterInfo.Builder setName(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  android.print.PrinterInfo.Builder setDescription(java.lang.String description) { throw new RuntimeException("Stub!"); }
public  android.print.PrinterInfo.Builder setCapabilities(android.print.PrinterCapabilitiesInfo capabilities) { throw new RuntimeException("Stub!"); }
public  android.print.PrinterInfo build() { throw new RuntimeException("Stub!"); }
}
PrinterInfo() { throw new RuntimeException("Stub!"); }
public  android.print.PrinterId getId() { throw new RuntimeException("Stub!"); }
public  java.lang.String getName() { throw new RuntimeException("Stub!"); }
public  int getStatus() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDescription() { throw new RuntimeException("Stub!"); }
public  android.print.PrinterCapabilitiesInfo getCapabilities() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.print.PrinterInfo> CREATOR;
public static final int STATUS_BUSY = 2;
public static final int STATUS_IDLE = 1;
public static final int STATUS_UNAVAILABLE = 3;
static { CREATOR = null; }
}
