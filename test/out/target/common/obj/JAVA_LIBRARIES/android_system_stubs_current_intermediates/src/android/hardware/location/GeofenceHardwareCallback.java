package android.hardware.location;
public abstract class GeofenceHardwareCallback
{
public  GeofenceHardwareCallback() { throw new RuntimeException("Stub!"); }
public  void onGeofenceTransition(int geofenceId, int transition, android.location.Location location, long timestamp, int monitoringType) { throw new RuntimeException("Stub!"); }
public  void onGeofenceAdd(int geofenceId, int status) { throw new RuntimeException("Stub!"); }
public  void onGeofenceRemove(int geofenceId, int status) { throw new RuntimeException("Stub!"); }
public  void onGeofencePause(int geofenceId, int status) { throw new RuntimeException("Stub!"); }
public  void onGeofenceResume(int geofenceId, int status) { throw new RuntimeException("Stub!"); }
}
