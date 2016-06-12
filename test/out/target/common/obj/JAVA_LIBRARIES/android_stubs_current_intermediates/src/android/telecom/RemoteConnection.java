package android.telecom;
public final class RemoteConnection
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public  void onStateChanged(android.telecom.RemoteConnection connection, int state) { throw new RuntimeException("Stub!"); }
public  void onDisconnected(android.telecom.RemoteConnection connection, android.telecom.DisconnectCause disconnectCause) { throw new RuntimeException("Stub!"); }
public  void onRingbackRequested(android.telecom.RemoteConnection connection, boolean ringback) { throw new RuntimeException("Stub!"); }
public  void onConnectionCapabilitiesChanged(android.telecom.RemoteConnection connection, int connectionCapabilities) { throw new RuntimeException("Stub!"); }
public  void onPostDialWait(android.telecom.RemoteConnection connection, java.lang.String remainingPostDialSequence) { throw new RuntimeException("Stub!"); }
public  void onPostDialChar(android.telecom.RemoteConnection connection, char nextChar) { throw new RuntimeException("Stub!"); }
public  void onVoipAudioChanged(android.telecom.RemoteConnection connection, boolean isVoip) { throw new RuntimeException("Stub!"); }
public  void onStatusHintsChanged(android.telecom.RemoteConnection connection, android.telecom.StatusHints statusHints) { throw new RuntimeException("Stub!"); }
public  void onAddressChanged(android.telecom.RemoteConnection connection, android.net.Uri address, int presentation) { throw new RuntimeException("Stub!"); }
public  void onCallerDisplayNameChanged(android.telecom.RemoteConnection connection, java.lang.String callerDisplayName, int presentation) { throw new RuntimeException("Stub!"); }
public  void onVideoStateChanged(android.telecom.RemoteConnection connection, int videoState) { throw new RuntimeException("Stub!"); }
public  void onDestroyed(android.telecom.RemoteConnection connection) { throw new RuntimeException("Stub!"); }
public  void onConferenceableConnectionsChanged(android.telecom.RemoteConnection connection, java.util.List<android.telecom.RemoteConnection> conferenceableConnections) { throw new RuntimeException("Stub!"); }
public  void onVideoProviderChanged(android.telecom.RemoteConnection connection, android.telecom.RemoteConnection.VideoProvider videoProvider) { throw new RuntimeException("Stub!"); }
public  void onConferenceChanged(android.telecom.RemoteConnection connection, android.telecom.RemoteConference conference) { throw new RuntimeException("Stub!"); }
public  void onExtrasChanged(android.telecom.RemoteConnection connection, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
}
public static class VideoProvider
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public  void onSessionModifyRequestReceived(android.telecom.RemoteConnection.VideoProvider videoProvider, android.telecom.VideoProfile videoProfile) { throw new RuntimeException("Stub!"); }
public  void onSessionModifyResponseReceived(android.telecom.RemoteConnection.VideoProvider videoProvider, int status, android.telecom.VideoProfile requestedProfile, android.telecom.VideoProfile responseProfile) { throw new RuntimeException("Stub!"); }
public  void onCallSessionEvent(android.telecom.RemoteConnection.VideoProvider videoProvider, int event) { throw new RuntimeException("Stub!"); }
public  void onPeerDimensionsChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, int width, int height) { throw new RuntimeException("Stub!"); }
public  void onCallDataUsageChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, long dataUsage) { throw new RuntimeException("Stub!"); }
public  void onCameraCapabilitiesChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) { throw new RuntimeException("Stub!"); }
public  void onVideoQualityChanged(android.telecom.RemoteConnection.VideoProvider videoProvider, int videoQuality) { throw new RuntimeException("Stub!"); }
}
VideoProvider() { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.telecom.RemoteConnection.VideoProvider.Callback l) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.telecom.RemoteConnection.VideoProvider.Callback l) { throw new RuntimeException("Stub!"); }
public  void setCamera(java.lang.String cameraId) { throw new RuntimeException("Stub!"); }
public  void setPreviewSurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public  void setDisplaySurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public  void setDeviceOrientation(int rotation) { throw new RuntimeException("Stub!"); }
public  void setZoom(float value) { throw new RuntimeException("Stub!"); }
public  void sendSessionModifyRequest(android.telecom.VideoProfile fromProfile, android.telecom.VideoProfile toProfile) { throw new RuntimeException("Stub!"); }
public  void sendSessionModifyResponse(android.telecom.VideoProfile responseProfile) { throw new RuntimeException("Stub!"); }
public  void requestCameraCapabilities() { throw new RuntimeException("Stub!"); }
public  void requestCallDataUsage() { throw new RuntimeException("Stub!"); }
public  void setPauseImage(android.net.Uri uri) { throw new RuntimeException("Stub!"); }
}
RemoteConnection() { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.telecom.RemoteConnection.Callback callback) { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.telecom.RemoteConnection.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.telecom.RemoteConnection.Callback callback) { throw new RuntimeException("Stub!"); }
public  int getState() { throw new RuntimeException("Stub!"); }
public  android.telecom.DisconnectCause getDisconnectCause() { throw new RuntimeException("Stub!"); }
public  int getConnectionCapabilities() { throw new RuntimeException("Stub!"); }
public  boolean isVoipAudioMode() { throw new RuntimeException("Stub!"); }
public  android.telecom.StatusHints getStatusHints() { throw new RuntimeException("Stub!"); }
public  android.net.Uri getAddress() { throw new RuntimeException("Stub!"); }
public  int getAddressPresentation() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getCallerDisplayName() { throw new RuntimeException("Stub!"); }
public  int getCallerDisplayNamePresentation() { throw new RuntimeException("Stub!"); }
public  int getVideoState() { throw new RuntimeException("Stub!"); }
public final  android.telecom.RemoteConnection.VideoProvider getVideoProvider() { throw new RuntimeException("Stub!"); }
public final  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  boolean isRingbackRequested() { throw new RuntimeException("Stub!"); }
public  void abort() { throw new RuntimeException("Stub!"); }
public  void answer() { throw new RuntimeException("Stub!"); }
public  void reject() { throw new RuntimeException("Stub!"); }
public  void hold() { throw new RuntimeException("Stub!"); }
public  void unhold() { throw new RuntimeException("Stub!"); }
public  void disconnect() { throw new RuntimeException("Stub!"); }
public  void playDtmfTone(char digit) { throw new RuntimeException("Stub!"); }
public  void stopDtmfTone() { throw new RuntimeException("Stub!"); }
public  void postDialContinue(boolean proceed) { throw new RuntimeException("Stub!"); }
public  void setCallAudioState(android.telecom.CallAudioState state) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.telecom.RemoteConnection> getConferenceableConnections() { throw new RuntimeException("Stub!"); }
public  android.telecom.RemoteConference getConference() { throw new RuntimeException("Stub!"); }
}
