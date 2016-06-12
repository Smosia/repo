package android.telecom;
public abstract class ConnectionService
  extends android.app.Service
{
public  ConnectionService() { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  boolean onUnbind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public final  android.telecom.RemoteConnection createRemoteIncomingConnection(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, android.telecom.ConnectionRequest request) { throw new RuntimeException("Stub!"); }
public final  android.telecom.RemoteConnection createRemoteOutgoingConnection(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, android.telecom.ConnectionRequest request) { throw new RuntimeException("Stub!"); }
public final  void conferenceRemoteConnections(android.telecom.RemoteConnection remoteConnection1, android.telecom.RemoteConnection remoteConnection2) { throw new RuntimeException("Stub!"); }
public final  void addConference(android.telecom.Conference conference) { throw new RuntimeException("Stub!"); }
public final  void addExistingConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.Connection connection) { throw new RuntimeException("Stub!"); }
public final  java.util.Collection<android.telecom.Connection> getAllConnections() { throw new RuntimeException("Stub!"); }
public  android.telecom.Connection onCreateIncomingConnection(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, android.telecom.ConnectionRequest request) { throw new RuntimeException("Stub!"); }
public  android.telecom.Connection onCreateOutgoingConnection(android.telecom.PhoneAccountHandle connectionManagerPhoneAccount, android.telecom.ConnectionRequest request) { throw new RuntimeException("Stub!"); }
public  void onConference(android.telecom.Connection connection1, android.telecom.Connection connection2) { throw new RuntimeException("Stub!"); }
public  void onRemoteConferenceAdded(android.telecom.RemoteConference conference) { throw new RuntimeException("Stub!"); }
public  void onRemoteExistingConnectionAdded(android.telecom.RemoteConnection connection) { throw new RuntimeException("Stub!"); }
public static final java.lang.String SERVICE_INTERFACE = "android.telecom.ConnectionService";
}
