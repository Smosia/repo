package android.media;
public final class MediaSync
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public abstract  void onAudioBufferConsumed(android.media.MediaSync sync, java.nio.ByteBuffer audioBuffer, int bufferId);
}
public static interface OnErrorListener
{
public abstract  void onError(android.media.MediaSync sync, int what, int extra);
}
public  MediaSync() { throw new RuntimeException("Stub!"); }
protected  void finalize() { throw new RuntimeException("Stub!"); }
public final  void release() { throw new RuntimeException("Stub!"); }
public  void setCallback(android.media.MediaSync.Callback cb, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void setOnErrorListener(android.media.MediaSync.OnErrorListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void setSurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public  void setAudioTrack(android.media.AudioTrack audioTrack) { throw new RuntimeException("Stub!"); }
public final native  android.view.Surface createInputSurface();
public  void setPlaybackParams(android.media.PlaybackParams params) { throw new RuntimeException("Stub!"); }
public native  android.media.PlaybackParams getPlaybackParams();
public  void setSyncParams(android.media.SyncParams params) { throw new RuntimeException("Stub!"); }
public native  android.media.SyncParams getSyncParams();
public  void flush() { throw new RuntimeException("Stub!"); }
public  android.media.MediaTimestamp getTimestamp() { throw new RuntimeException("Stub!"); }
public  void queueAudio(java.nio.ByteBuffer audioData, int bufferId, long presentationTimeUs) { throw new RuntimeException("Stub!"); }
public static final int MEDIASYNC_ERROR_AUDIOTRACK_FAIL = 1;
public static final int MEDIASYNC_ERROR_SURFACE_FAIL = 2;
}
