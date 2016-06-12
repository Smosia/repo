package android.hardware.location;
public abstract class GeofenceHardwareMonitorCallback
{
public  GeofenceHardwareMonitorCallback() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void onMonitoringSystemChange(int monitoringType, boolean available, android.location.Location location) { throw new RuntimeException("Stub!"); }
public  void onMonitoringSystemChange(android.hardware.location.GeofenceHardwareMonitorEvent event) { throw new RuntimeException("Stub!"); }
}
