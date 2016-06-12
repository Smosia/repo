package android.location;
public interface IFusedGeofenceHardware
  extends android.os.IInterface
{
public abstract  boolean isSupported() throws android.os.RemoteException;
public abstract  void addGeofences(android.hardware.location.GeofenceHardwareRequestParcelable[] geofenceRequestsArray) throws android.os.RemoteException;
public abstract  void removeGeofences(int[] geofenceIds) throws android.os.RemoteException;
public abstract  void pauseMonitoringGeofence(int geofenceId) throws android.os.RemoteException;
public abstract  void resumeMonitoringGeofence(int geofenceId, int monitorTransitions) throws android.os.RemoteException;
public abstract  void modifyGeofenceOptions(int geofenceId, int lastTransition, int monitorTransitions, int notificationResponsiveness, int unknownTimer, int sourcesToUse) throws android.os.RemoteException;
}
