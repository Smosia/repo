package android.telecom;
public final class RemoteConference
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public  void onStateChanged(android.telecom.RemoteConference conference, int oldState, int newState) { throw new RuntimeException("Stub!"); }
public  void onDisconnected(android.telecom.RemoteConference conference, android.telecom.DisconnectCause disconnectCause) { throw new RuntimeException("Stub!"); }
public  void onConnectionAdded(android.telecom.RemoteConference conference, android.telecom.RemoteConnection connection) { throw new RuntimeException("Stub!"); }
public  void onConnectionRemoved(android.telecom.RemoteConference conference, android.telecom.RemoteConnection connection) { throw new RuntimeException("Stub!"); }
public  void onConnectionCapabilitiesChanged(android.telecom.RemoteConference conference, int connectionCapabilities) { throw new RuntimeException("Stub!"); }
public  void onConferenceableConnectionsChanged(android.telecom.RemoteConference conference, java.util.List<android.telecom.RemoteConnection> conferenceableConnections) { throw new RuntimeException("Stub!"); }
public  void onDestroyed(android.telecom.RemoteConference conference) { throw new RuntimeException("Stub!"); }
public  void onExtrasChanged(android.telecom.RemoteConference conference, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
}
RemoteConference() { throw new RuntimeException("Stub!"); }
public final  java.util.List<android.telecom.RemoteConnection> getConnections() { throw new RuntimeException("Stub!"); }
public final  int getState() { throw new RuntimeException("Stub!"); }
public final  int getConnectionCapabilities() { throw new RuntimeException("Stub!"); }
public final  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  void disconnect() { throw new RuntimeException("Stub!"); }
public  void separate(android.telecom.RemoteConnection connection) { throw new RuntimeException("Stub!"); }
public  void merge() { throw new RuntimeException("Stub!"); }
public  void swap() { throw new RuntimeException("Stub!"); }
public  void hold() { throw new RuntimeException("Stub!"); }
public  void unhold() { throw new RuntimeException("Stub!"); }
public  android.telecom.DisconnectCause getDisconnectCause() { throw new RuntimeException("Stub!"); }
public  void playDtmfTone(char digit) { throw new RuntimeException("Stub!"); }
public  void stopDtmfTone() { throw new RuntimeException("Stub!"); }
public  void setCallAudioState(android.telecom.CallAudioState state) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.telecom.RemoteConnection> getConferenceableConnections() { throw new RuntimeException("Stub!"); }
public final  void registerCallback(android.telecom.RemoteConference.Callback callback) { throw new RuntimeException("Stub!"); }
public final  void registerCallback(android.telecom.RemoteConference.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public final  void unregisterCallback(android.telecom.RemoteConference.Callback callback) { throw new RuntimeException("Stub!"); }
}
