package android.media.tv;
public class TvView
  extends android.view.ViewGroup
{
public abstract static class TimeShiftPositionCallback
{
public  TimeShiftPositionCallback() { throw new RuntimeException("Stub!"); }
public  void onTimeShiftStartPositionChanged(java.lang.String inputId, long timeMs) { throw new RuntimeException("Stub!"); }
public  void onTimeShiftCurrentPositionChanged(java.lang.String inputId, long timeMs) { throw new RuntimeException("Stub!"); }
}
public abstract static class TvInputCallback
{
public  TvInputCallback() { throw new RuntimeException("Stub!"); }
public  void onConnectionFailed(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onDisconnected(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onChannelRetuned(java.lang.String inputId, android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public  void onTracksChanged(java.lang.String inputId, java.util.List<android.media.tv.TvTrackInfo> tracks) { throw new RuntimeException("Stub!"); }
public  void onTrackSelected(java.lang.String inputId, int type, java.lang.String trackId) { throw new RuntimeException("Stub!"); }
public  void onVideoSizeChanged(java.lang.String inputId, int width, int height) { throw new RuntimeException("Stub!"); }
public  void onVideoAvailable(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onVideoUnavailable(java.lang.String inputId, int reason) { throw new RuntimeException("Stub!"); }
public  void onContentAllowed(java.lang.String inputId) { throw new RuntimeException("Stub!"); }
public  void onContentBlocked(java.lang.String inputId, android.media.tv.TvContentRating rating) { throw new RuntimeException("Stub!"); }
public  void onTimeShiftStatusChanged(java.lang.String inputId, int status) { throw new RuntimeException("Stub!"); }
}
public static interface OnUnhandledInputEventListener
{
public abstract  boolean onUnhandledInputEvent(android.view.InputEvent event);
}
public  TvView(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  TvView(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  TvView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  void setCallback(android.media.tv.TvView.TvInputCallback callback) { throw new RuntimeException("Stub!"); }
public  void setStreamVolume(float volume) { throw new RuntimeException("Stub!"); }
public  void tune(java.lang.String inputId, android.net.Uri channelUri) { throw new RuntimeException("Stub!"); }
public  void reset() { throw new RuntimeException("Stub!"); }
public  void setCaptionEnabled(boolean enabled) { throw new RuntimeException("Stub!"); }
public  void selectTrack(int type, java.lang.String trackId) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.tv.TvTrackInfo> getTracks(int type) { throw new RuntimeException("Stub!"); }
public  java.lang.String getSelectedTrack(int type) { throw new RuntimeException("Stub!"); }
public  void timeShiftPause() { throw new RuntimeException("Stub!"); }
public  void timeShiftResume() { throw new RuntimeException("Stub!"); }
public  void timeShiftSeekTo(long timeMs) { throw new RuntimeException("Stub!"); }
public  void timeShiftSetPlaybackParams(android.media.PlaybackParams params) { throw new RuntimeException("Stub!"); }
public  void setTimeShiftPositionCallback(android.media.tv.TvView.TimeShiftPositionCallback callback) { throw new RuntimeException("Stub!"); }
public  boolean dispatchUnhandledInputEvent(android.view.InputEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onUnhandledInputEvent(android.view.InputEvent event) { throw new RuntimeException("Stub!"); }
public  void setOnUnhandledInputEventListener(android.media.tv.TvView.OnUnhandledInputEventListener listener) { throw new RuntimeException("Stub!"); }
public  boolean dispatchKeyEvent(android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchTouchEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchTrackballEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchGenericMotionEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  void dispatchWindowFocusChanged(boolean hasFocus) { throw new RuntimeException("Stub!"); }
protected  void onAttachedToWindow() { throw new RuntimeException("Stub!"); }
protected  void onDetachedFromWindow() { throw new RuntimeException("Stub!"); }
protected  void onLayout(boolean changed, int left, int top, int right, int bottom) { throw new RuntimeException("Stub!"); }
protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { throw new RuntimeException("Stub!"); }
public  boolean gatherTransparentRegion(android.graphics.Region region) { throw new RuntimeException("Stub!"); }
public  void draw(android.graphics.Canvas canvas) { throw new RuntimeException("Stub!"); }
protected  void dispatchDraw(android.graphics.Canvas canvas) { throw new RuntimeException("Stub!"); }
protected  void onVisibilityChanged(android.view.View changedView, int visibility) { throw new RuntimeException("Stub!"); }
}
