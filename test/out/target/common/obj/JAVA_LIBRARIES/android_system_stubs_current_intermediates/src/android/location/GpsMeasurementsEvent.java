package android.location;
public class GpsMeasurementsEvent
  implements android.os.Parcelable
{
public static interface Listener
{
public abstract  void onGpsMeasurementsReceived(android.location.GpsMeasurementsEvent eventArgs);
public abstract  void onStatusChanged(int status);
}
public  GpsMeasurementsEvent(android.location.GpsClock clock, android.location.GpsMeasurement[] measurements) { throw new RuntimeException("Stub!"); }
public  android.location.GpsClock getClock() { throw new RuntimeException("Stub!"); }
public  java.util.Collection<android.location.GpsMeasurement> getMeasurements() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.location.GpsMeasurementsEvent> CREATOR;
public static final int STATUS_GPS_LOCATION_DISABLED = 2;
public static final int STATUS_NOT_SUPPORTED = 0;
public static final int STATUS_READY = 1;
static { CREATOR = null; }
}
