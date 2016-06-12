package android.media.tv;
public final class TvInputManager
{
public abstract static class TvInputCallback
{
public  TvInputCallback() { throw new RuntimeException("Stub!"); }
public  void onInputStateChanged(java.lang.String inputId, int state) { throw new RuntimeException("Stub!"); }
public  void onInputAdded(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onInputRemoved(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
}
TvInputManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvInputInfo> getTvInputList() { throw new RuntimeException("Stub!"); }
public  android.media.tv.TvInputInfo getTvInputInfo(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  int getInputState(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.media.tv.TvInputManager.TvInputCallback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.media.tv.TvInputManager.TvInputCallback callback) { throw new RuntimeException("Stub!"); }
public  boolean isParentalControlsEnabled() { throw new RuntimeException("Stub!"); }
public  boolean isRatingBlocked(android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
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
