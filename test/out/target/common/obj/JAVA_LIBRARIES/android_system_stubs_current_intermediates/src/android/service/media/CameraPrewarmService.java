package android.service.media;
public abstract class CameraPrewarmService
  extends android.app.Service
{
public  CameraPrewarmService() { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  boolean onUnbind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public abstract  void onPrewarm();
public abstract  void onCooldown(boolean cameraIntentFired);
}
