package android.location;
public interface IGpsGeofenceHardware
  extends android.os.IInterface
{
public abstract  boolean isHardwareGeofenceSupported() throws android.os.RemoteException;
public abstract  boolean addCircularHardwareGeofence(int geofenceId, double latitude, double longitude, double radius, int lastTransition, int monitorTransition, int notificationResponsiveness, int unknownTimer) throws android.os.RemoteException;
public abstract  boolean removeHardwareGeofence(int geofenceId) throws android.os.RemoteException;
public abstract  boolean pauseHardwareGeofence(int geofenceId) throws android.os.RemoteException;
public abstract  boolean resumeHardwareGeofence(int geofenceId, int monitorTransition) throws android.os.RemoteException;
}
