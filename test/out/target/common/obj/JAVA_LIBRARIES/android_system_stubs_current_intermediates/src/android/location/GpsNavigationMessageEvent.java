package android.location;
public class GpsNavigationMessageEvent
  implements android.os.Parcelable
{
public static interface Listener
{
public abstract  void onGpsNavigationMessageReceived(android.location.GpsNavigationMessageEvent event);
public abstract  void onStatusChanged(int status);
}
public  GpsNavigationMessageEvent(android.location.GpsNavigationMessage message) { throw new RuntimeException("Stub!"); }
public  android.location.GpsNavigationMessage getNavigationMessage() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.location.GpsNavigationMessageEvent> CREATOR;
public static int STATUS_GPS_LOCATION_DISABLED;
public static int STATUS_NOT_SUPPORTED;
public static int STATUS_READY;
static { CREATOR = null; }
}
