package android.media.projection;
public final class MediaProjection
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public  void onStop() { throw new RuntimeException("Stub!"); }
}
MediaProjection() { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.media.projection.MediaProjection.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.media.projection.MediaProjection.Callback callback) { throw new RuntimeException("Stub!"); }
public  android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String name, int width, int height, int dpi, int flags, android.view.Surface surface, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void stop() { throw new RuntimeException("Stub!"); }
}
