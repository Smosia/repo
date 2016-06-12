package android.media.midi;
public final class MidiDeviceStatus
  implements android.os.Parcelable
{
MidiDeviceStatus() { throw new RuntimeException("Stub!"); }
public  android.media.midi.MidiDeviceInfo getDeviceInfo() { throw new RuntimeException("Stub!"); }
public  boolean isInputPortOpen(int portNumber) { throw new RuntimeException("Stub!"); }
public  int getOutputPortOpenCount(int portNumber) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.media.midi.MidiDeviceStatus> CREATOR;
static { CREATOR = null; }
}
