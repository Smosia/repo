package android.hardware.location;
public interface IGeofenceHardwareCallback
  extends android.os.IInterface
{
public abstract  void onGeofenceTransition(int geofenceId, int transition, android.location.Location location, long timestamp, int monitoringType) throws android.os.RemoteException;
public abstract  void onGeofenceAdd(int geofenceId, int status) throws android.os.RemoteException;
public abstract  void onGeofenceRemove(int geofenceId, int status) throws android.os.RemoteException;
public abstract  void onGeofencePause(int geofenceId, int status) throws android.os.RemoteException;
public abstract  void onGeofenceResume(int geofenceId, int status) throws android.os.RemoteException;
}
