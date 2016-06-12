package android.media.tv;
public abstract class TvInputService
  extends android.app.Service
{
public abstract static class Session
  implements android.view.KeyEvent.Callback
{
public  Session(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  void setOverlayViewEnabled(boolean enable) { throw new RuntimeException("Stub!"); }
public  void notifyChannelRetuned(android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public  void notifyTracksChanged(java.util.List<android.media.tv.TvTrackInfo> tracks) { throw new RuntimeException("Stub!"); }
public  void notifyTrackSelected(int type, java.lang.String trackId) { throw new RuntimeException("Stub!"); }
public  void notifyVideoAvailable() { throw new RuntimeException("Stub!"); }
public  void notifyVideoUnavailable(int reason) { throw new RuntimeException("Stub!"); }
public  void notifyContentAllowed() { throw new RuntimeException("Stub!"); }
public  void notifyContentBlocked(android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
public  void notifyTimeShiftStatusChanged(int status) { throw new RuntimeException("Stub!"); }
public  void layoutSurface(int left, int top, int right, int bottom) { throw new RuntimeException("Stub!"); }
public abstract  void onRelease();
public abstract  boolean onSetSurface(android.view.Surface surface);
public  void onSurfaceChanged(int format, int width, int height) { throw new RuntimeException("Stub!"); }
public  void onOverlayViewSizeChanged(int width, int height) { throw new RuntimeException("Stub!"); }
public abstract  void onSetStreamVolume(float volume);
public abstract  boolean onTune(android.net.Uri channelUri);
public abstract  void onSetCaptionEnabled(boolean enabled);
public  void onUnblockContent(android.media.tv.TvContentRating unblockedRating) { throw new RuntimeException("Stub!"); }
public  boolean onSelectTrack(int type, java.lang.String trackId) { throw new RuntimeException("Stub!"); }
public  android.view.View onCreateOverlayView() { throw new RuntimeException("Stub!"); }
public  void onTimeShiftPause() { throw new RuntimeException("Stub!"); }
public  void onTimeShiftResume() { throw new RuntimeException("Stub!"); }
public  void onTimeShiftSeekTo(long timeMs) { throw new RuntimeException("Stub!"); }
public  void onTimeShiftSetPlaybackParams(android.media.PlaybackParams params) { throw new RuntimeException("Stub!"); }
public  long onTimeShiftGetStartPosition() { throw new RuntimeException("Stub!"); }
public  long onTimeShiftGetCurrentPosition() { throw new RuntimeException("Stub!"); }
public  boolean onKeyDown(int keyCode, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onKeyLongPress(int keyCode, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onKeyMultiple(int keyCode, int count, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onKeyUp(int keyCode, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onTouchEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onTrackballEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onGenericMotionEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
}
public abstract static class HardwareSession
  extends android.media.tv.TvInputService.Session
{
public  HardwareSession(android.content.Context context) { super((android.content.Context)null); throw new RuntimeException("Stub!"); }
public abstract  java.lang.String getHardwareInputId();
public final  boolean onSetSurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public  void onHardwareVideoAvailable() { throw new RuntimeException("Stub!"); }
public  void onHardwareVideoUnavailable(int reason) { throw new RuntimeException("Stub!"); }
}
public  TvInputService() { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public abstract  android.media.tv.TvInputService.Session onCreateSession(java.lang.String inputId);
public static final java.lang.String SERVICE_INTERFACE = "android.media.tv.TvInputService";
public static final java.lang.String SERVICE_META_DATA = "android.media.tv.input";
}
