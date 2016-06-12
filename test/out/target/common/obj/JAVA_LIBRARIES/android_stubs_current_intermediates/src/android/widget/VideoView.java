package android.widget;
public class VideoView
  extends android.view.SurfaceView
  implements android.widget.MediaController.MediaPlayerControl
{
public  VideoView(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  VideoView(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  VideoView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  VideoView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getAccessibilityClassName() { throw new RuntimeException("Stub!"); }
public  int resolveAdjustedSize(int desiredSize, int measureSpec) { throw new RuntimeException("Stub!"); }
public  void setVideoPath(java.lang.String path) { throw new RuntimeException("Stub!"); }
public  void setVideoURI(android.net.Uri uri) { throw new RuntimeException("Stub!"); }
public  void setVideoURI(android.net.Uri uri, java.util.Map<java.lang.String, java.lang.String> headers) { throw new RuntimeException("Stub!"); }
public  void addSubtitleSource(java.io.InputStream is, android.media.MediaFormat format) { throw new RuntimeException("Stub!"); }
public  void stopPlayback() { throw new RuntimeException("Stub!"); }
public  void setMediaController(android.widget.MediaController controller) { throw new RuntimeException("Stub!"); }
public  void setOnPreparedListener(android.media.MediaPlayer.OnPreparedListener l) { throw new RuntimeException("Stub!"); }
public  void setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener l) { throw new RuntimeException("Stub!"); }
public  void setOnErrorListener(android.media.MediaPlayer.OnErrorListener l) { throw new RuntimeException("Stub!"); }
public  void setOnInfoListener(android.media.MediaPlayer.OnInfoListener l) { throw new RuntimeException("Stub!"); }
public  boolean onTouchEvent(android.view.MotionEvent ev) { throw new RuntimeException("Stub!"); }
public  boolean onTrackballEvent(android.view.MotionEvent ev) { throw new RuntimeException("Stub!"); }
public  boolean onKeyDown(int keyCode, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  void start() { throw new RuntimeException("Stub!"); }
public  void pause() { throw new RuntimeException("Stub!"); }
public  void suspend() { throw new RuntimeException("Stub!"); }
public  void resume() { throw new RuntimeException("Stub!"); }
public  int getDuration() { throw new RuntimeException("Stub!"); }
public  int getCurrentPosition() { throw new RuntimeException("Stub!"); }
public  void seekTo(int msec) { throw new RuntimeException("Stub!"); }
public  boolean isPlaying() { throw new RuntimeException("Stub!"); }
public  int getBufferPercentage() { throw new RuntimeException("Stub!"); }
public  boolean canPause() { throw new RuntimeException("Stub!"); }
public  boolean canSeekBackward() { throw new RuntimeException("Stub!"); }
public  boolean canSeekForward() { throw new RuntimeException("Stub!"); }
public  int getAudioSessionId() { throw new RuntimeException("Stub!"); }
protected  void onAttachedToWindow() { throw new RuntimeException("Stub!"); }
protected  void onDetachedFromWindow() { throw new RuntimeException("Stub!"); }
protected  void onLayout(boolean changed, int left, int top, int right, int bottom) { throw new RuntimeException("Stub!"); }
public  void draw(android.graphics.Canvas canvas) { throw new RuntimeException("Stub!"); }
}
