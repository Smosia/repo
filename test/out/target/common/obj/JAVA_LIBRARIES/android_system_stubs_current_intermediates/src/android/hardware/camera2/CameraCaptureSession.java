package android.hardware.camera2;
public abstract class CameraCaptureSession
  implements java.lang.AutoCloseable
{
public abstract static class StateCallback
{
public  StateCallback() { throw new RuntimeException("Stub!"); }
public abstract  void onConfigured(android.hardware.camera2.CameraCaptureSession session);
public abstract  void onConfigureFailed(android.hardware.camera2.CameraCaptureSession session);
public  void onReady(android.hardware.camera2.CameraCaptureSession session) { throw new RuntimeException("Stub!"); }
public  void onActive(android.hardware.camera2.CameraCaptureSession session) { throw new RuntimeException("Stub!"); }
public  void onClosed(android.hardware.camera2.CameraCaptureSession session) { throw new RuntimeException("Stub!"); }
public  void onSurfacePrepared(android.hardware.camera2.CameraCaptureSession session, android.view.Surface surface) { throw new RuntimeException("Stub!"); }
}
public abstract static class CaptureCallback
{
public  CaptureCallback() { throw new RuntimeException("Stub!"); }
public  void onCaptureStarted(android.hardware.camera2.CameraCaptureSession session, android.hardware.camera2.CaptureRequest request, long timestamp, long frameNumber) { throw new RuntimeException("Stub!"); }
public  void onCaptureProgressed(android.hardware.camera2.CameraCaptureSession session, android.hardware.camera2.CaptureRequest request, android.hardware.camera2.CaptureResult partialResult) { throw new RuntimeException("Stub!"); }
public  void onCaptureCompleted(android.hardware.camera2.CameraCaptureSession session, android.hardware.camera2.CaptureRequest request, android.hardware.camera2.TotalCaptureResult result) { throw new RuntimeException("Stub!"); }
public  void onCaptureFailed(android.hardware.camera2.CameraCaptureSession session, android.hardware.camera2.CaptureRequest request, android.hardware.camera2.CaptureFailure failure) { throw new RuntimeException("Stub!"); }
public  void onCaptureSequenceCompleted(android.hardware.camera2.CameraCaptureSession session, int sequenceId, long frameNumber) { throw new RuntimeException("Stub!"); }
public  void onCaptureSequenceAborted(android.hardware.camera2.CameraCaptureSession session, int sequenceId) { throw new RuntimeException("Stub!"); }
}
public  CameraCaptureSession() { throw new RuntimeException("Stub!"); }
public abstract  android.hardware.camera2.CameraDevice getDevice();
public abstract  void prepare(android.view.Surface surface) throws android.hardware.camera2.CameraAccessException;
public abstract  int capture(android.hardware.camera2.CaptureRequest request, android.hardware.camera2.CameraCaptureSession.CaptureCallback listener, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  int captureBurst(java.util.List<android.hardware.camera2.CaptureRequest> requests, android.hardware.camera2.CameraCaptureSession.CaptureCallback listener, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  int setRepeatingRequest(android.hardware.camera2.CaptureRequest request, android.hardware.camera2.CameraCaptureSession.CaptureCallback listener, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  int setRepeatingBurst(java.util.List<android.hardware.camera2.CaptureRequest> requests, android.hardware.camera2.CameraCaptureSession.CaptureCallback listener, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  void stopRepeating() throws android.hardware.camera2.CameraAccessException;
public abstract  void abortCaptures() throws android.hardware.camera2.CameraAccessException;
public abstract  boolean isReprocessable();
public abstract  android.view.Surface getInputSurface();
public abstract  void close();
}
