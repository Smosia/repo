package android.location;
public class GpsClock
  implements android.os.Parcelable
{
GpsClock() { throw new RuntimeException("Stub!"); }
public  void set(android.location.GpsClock clock) { throw new RuntimeException("Stub!"); }
public  void reset() { throw new RuntimeException("Stub!"); }
public  byte getType() { throw new RuntimeException("Stub!"); }
public  void setType(byte value) { throw new RuntimeException("Stub!"); }
public  boolean hasLeapSecond() { throw new RuntimeException("Stub!"); }
public  short getLeapSecond() { throw new RuntimeException("Stub!"); }
public  void setLeapSecond(short leapSecond) { throw new RuntimeException("Stub!"); }
public  void resetLeapSecond() { throw new RuntimeException("Stub!"); }
public  long getTimeInNs() { throw new RuntimeException("Stub!"); }
public  void setTimeInNs(long timeInNs) { throw new RuntimeException("Stub!"); }
public  boolean hasTimeUncertaintyInNs() { throw new RuntimeException("Stub!"); }
public  double getTimeUncertaintyInNs() { throw new RuntimeException("Stub!"); }
public  void setTimeUncertaintyInNs(double timeUncertaintyInNs) { throw new RuntimeException("Stub!"); }
public  void resetTimeUncertaintyInNs() { throw new RuntimeException("Stub!"); }
public  boolean hasFullBiasInNs() { throw new RuntimeException("Stub!"); }
public  long getFullBiasInNs() { throw new RuntimeException("Stub!"); }
public  void setFullBiasInNs(long value) { throw new RuntimeException("Stub!"); }
public  void resetFullBiasInNs() { throw new RuntimeException("Stub!"); }
public  boolean hasBiasInNs() { throw new RuntimeException("Stub!"); }
public  double getBiasInNs() { throw new RuntimeException("Stub!"); }
public  void setBiasInNs(double biasInNs) { throw new RuntimeException("Stub!"); }
public  void resetBiasInNs() { throw new RuntimeException("Stub!"); }
public  boolean hasBiasUncertaintyInNs() { throw new RuntimeException("Stub!"); }
public  double getBiasUncertaintyInNs() { throw new RuntimeException("Stub!"); }
public  void setBiasUncertaintyInNs(double biasUncertaintyInNs) { throw new RuntimeException("Stub!"); }
public  void resetBiasUncertaintyInNs() { throw new RuntimeException("Stub!"); }
public  boolean hasDriftInNsPerSec() { throw new RuntimeException("Stub!"); }
public  double getDriftInNsPerSec() { throw new RuntimeException("Stub!"); }
public  void setDriftInNsPerSec(double driftInNsPerSec) { throw new RuntimeException("Stub!"); }
public  void resetDriftInNsPerSec() { throw new RuntimeException("Stub!"); }
public  boolean hasDriftUncertaintyInNsPerSec() { throw new RuntimeException("Stub!"); }
public  double getDriftUncertaintyInNsPerSec() { throw new RuntimeException("Stub!"); }
public  void setDriftUncertaintyInNsPerSec(double driftUncertaintyInNsPerSec) { throw new RuntimeException("Stub!"); }
public  void resetDriftUncertaintyInNsPerSec() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.location.GpsClock> CREATOR;
public static final byte TYPE_GPS_TIME = 2;
public static final byte TYPE_LOCAL_HW_TIME = 1;
public static final byte TYPE_UNKNOWN = 0;
static { CREATOR = null; }
}
