package android.hardware.hdmi;
public final class HdmiPortInfo
  implements android.os.Parcelable
{
public  HdmiPortInfo(int id, int type, int address, boolean cec, boolean mhl, boolean arc) { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  int getType() { throw new RuntimeException("Stub!"); }
public  int getAddress() { throw new RuntimeException("Stub!"); }
public  boolean isCecSupported() { throw new RuntimeException("Stub!"); }
public  boolean isMhlSupported() { throw new RuntimeException("Stub!"); }
public  boolean isArcSupported() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.hardware.hdmi.HdmiPortInfo> CREATOR;
public static final int PORT_INPUT = 0;
public static final int PORT_OUTPUT = 1;
static { CREATOR = null; }
}
