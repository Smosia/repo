package android.media.tv;
public final class TvInputManager
{
public abstract static class SessionCallback
{
public  SessionCallback() { throw new RuntimeException("Stub!"); }
public  void onSessionCreated(android.media.tv.TvInputManager.Session session) { throw new RuntimeException("Stub!"); }
public  void onSessionReleased(android.media.tv.TvInputManager.Session session) { throw new RuntimeException("Stub!"); }
public  void onChannelRetuned(android.media.tv.TvInputManager.Session session, android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public  void onTracksChanged(android.media.tv.TvInputManager.Session session, java.util.List<android.media.tv.TvTrackInfo> tracks) { throw new RuntimeException("Stub!"); }
public  void onTrackSelected(android.media.tv.TvInputManager.Session session, int type, java.lang.String trackId) { throw new RuntimeException("Stub!"); }
public  void onVideoSizeChanged(android.media.tv.TvInputManager.Session session, int width, int height) { throw new RuntimeException("Stub!"); }
public  void onVideoAvailable(android.media.tv.TvInputManager.Session session) { throw new RuntimeException("Stub!"); }
public  void onVideoUnavailable(android.media.tv.TvInputManager.Session session, int reason) { throw new RuntimeException("Stub!"); }
public  void onContentAllowed(android.media.tv.TvInputManager.Session session) { throw new RuntimeException("Stub!"); }
public  void onContentBlocked(android.media.tv.TvInputManager.Session session, android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
public  void onLayoutSurface(android.media.tv.TvInputManager.Session session, int left, int top, int right, int bottom) { throw new RuntimeException("Stub!"); }
public  void onSessionEvent(android.media.tv.TvInputManager.Session session, java.lang.String eventType, android.os.Bundle eventArgs) { throw new RuntimeException("Stub!"); }
public  void onTimeShiftStatusChanged(android.media.tv.TvInputManager.Session session, int status) { throw new RuntimeException("Stub!"); }
public  void onTimeShiftStartPositionChanged(android.media.tv.TvInputManager.Session session, long timeMs) { throw new RuntimeException("Stub!"); }
public  void onTimeShiftCurrentPositionChanged(android.media.tv.TvInputManager.Session session, long timeMs) { throw new RuntimeException("Stub!"); }
}
public abstract static class TvInputCallback
{
public  TvInputCallback() { throw new RuntimeException("Stub!"); }
public  void onInputStateChanged(java.lang.String inputId, int state) { throw new RuntimeException("Stub!"); }
public  void onInputAdded(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onInputRemoved(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onInputUpdated(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
}
public abstract static class HardwareCallback
{
public  HardwareCallback() { throw new RuntimeException("Stub!"); }
public abstract  void onReleased();
public abstract  void onStreamConfigChanged(android.media.tv.TvStreamConfig[] configs);
}
public static final class Session
{
Session() { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
public  void setSurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public  void dispatchSurfaceChanged(int format, int width, int height) { throw new RuntimeException("Stub!"); }
public  void setStreamVolume(float volume) { throw new RuntimeException("Stub!"); }
public  void tune(android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public  void tune(android.net.Uri channelUri, android.os.Bundle params) { throw new RuntimeException("Stub!"); }
public  void setCaptionEnabled(boolean enabled) { throw new RuntimeException("Stub!"); }
public  void selectTrack(int type, java.lang.String trackId) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvTrackInfo> getTracks(int type) { throw new RuntimeException("Stub!"); }
public  java.lang.String getSelectedTrack(int type) { throw new RuntimeException("Stub!"); }
public  void sendAppPrivateCommand(java.lang.String action, android.os.Bundle data) { throw new RuntimeException("Stub!"); }
}
public static final class Hardware
{
Hardware() { throw new RuntimeException("Stub!"); }
public  boolean setSurface(android.view.Surface surface, android.media.tv.TvStreamConfig config) { throw new RuntimeException("Stub!"); }
public  void setStreamVolume(float volume) { throw new RuntimeException("Stub!"); }
public  boolean dispatchKeyEventToHdmi(android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  void overrideAudioSink(int audioType, java.lang.String audioAddress, int samplingRate, int channelMask, int format) { throw new RuntimeException("Stub!"); }
}
TvInputManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvInputInfo> getTvInputList() { throw new RuntimeException("Stub!"); }
public  android.media.tv.TvInputInfo getTvInputInfo(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  int getInputState(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.media.tv.TvInputManager.TvInputCallback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.media.tv.TvInputManager.TvInputCallback callback) { throw new RuntimeException("Stub!"); }
public  boolean isParentalControlsEnabled() { throw new RuntimeException("Stub!"); }
public  void setParentalControlsEnabled(boolean enabled) { throw new RuntimeException("Stub!"); }
public  boolean isRatingBlocked(android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvContentRating> getBlockedRatings() { throw new RuntimeException("Stub!"); }
public  void addBlockedRating(android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
public  void removeBlockedRating(android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvContentRatingSystemInfo> getTvContentRatingSystemList() { throw new RuntimeException("Stub!"); }
public  void createSession(java.lang.String inputId, android.media.tv.TvInputManager.SessionCallback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  boolean captureFrame(java.lang.String inputId, android.view.Surface surface, android.media.tv.TvStreamConfig config) { throw new RuntimeException("Stub!"); }
public  boolean isSingleSessionActive() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() { throw new RuntimeException("Stub!"); }
public  android.media.tv.TvInputManager.Hardware acquireTvInputHardware(int deviceId, android.media.tv.TvInputManager.HardwareCallback callback, android.media.tv.TvInputInfo info) { throw new RuntimeException("Stub!"); }
public  void releaseTvInputHardware(int deviceId, android.media.tv.TvInputManager.Hardware hardware) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_BLOCKED_RATINGS_CHANGED = "android.media.tv.action.BLOCKED_RATINGS_CHANGED";
public static final java.lang.String ACTION_PARENTAL_CONTROLS_ENABLED_CHANGED = "android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED";
public static final java.lang.String ACTION_QUERY_CONTENT_RATING_SYSTEMS = "android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS";
public static final int INPUT_STATE_CONNECTED = 0;
public static final int INPUT_STATE_CONNECTED_STANDBY = 1;
public static final int INPUT_STATE_DISCONNECTED = 2;
public static final java.lang.String META_DATA_CONTENT_RATING_SYSTEMS = "android.media.tv.metadata.CONTENT_RATING_SYSTEMS";
public static final long TIME_SHIFT_INVALID_TIME = -9223372036854775808L;
public static final int TIME_SHIFT_STATUS_AVAILABLE = 3;
public static final int TIME_SHIFT_STATUS_UNAVAILABLE = 2;
public static final int TIME_SHIFT_STATUS_UNKNOWN = 0;
public static final int TIME_SHIFT_STATUS_UNSUPPORTED = 1;
public static final int VIDEO_UNAVAILABLE_REASON_AUDIO_ONLY = 4;
public static final int VIDEO_UNAVAILABLE_REASON_BUFFERING = 3;
public static final int VIDEO_UNAVAILABLE_REASON_TUNING = 1;
public static final int VIDEO_UNAVAILABLE_REASON_UNKNOWN = 0;
public static final int VIDEO_UNAVAILABLE_REASON_WEAK_SIGNAL = 2;
}
