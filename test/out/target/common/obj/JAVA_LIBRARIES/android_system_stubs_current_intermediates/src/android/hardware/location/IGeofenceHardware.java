package android.hardware.location;
public interface IGeofenceHardware
  extends android.os.IInterface
{
public abstract  void setGpsGeofenceHardware(android.location.IGpsGeofenceHardware service) throws android.os.RemoteException;
public abstract  void setFusedGeofenceHardware(android.location.IFusedGeofenceHardware service) throws android.os.RemoteException;
public abstract  int[] getMonitoringTypes() throws android.os.RemoteException;
public abstract  int getStatusOfMonitoringType(int monitoringType) throws android.os.RemoteException;
public abstract  boolean addCircularFence(int monitoringType, android.hardware.location.GeofenceHardwareRequestParcelable request, android.hardware.location.IGeofenceHardwareCallback callback) throws android.os.RemoteException;
public abstract  boolean removeGeofence(int id, int monitoringType) throws android.os.RemoteException;
public abstract  boolean pauseGeofence(int id, int monitoringType) throws android.os.RemoteException;
public abstract  boolean resumeGeofence(int id, int monitoringType, int monitorTransitions) throws android.os.RemoteException;
public abstract  boolean registerForMonitorStateChangeCallback(int monitoringType, android.hardware.location.IGeofenceHardwareMonitorCallback callback) throws android.os.RemoteException;
public abstract  boolean unregisterForMonitorStateChangeCallback(int monitoringType, android.hardware.location.IGeofenceHardwareMonitorCallback callback) throws android.os.RemoteException;
}
