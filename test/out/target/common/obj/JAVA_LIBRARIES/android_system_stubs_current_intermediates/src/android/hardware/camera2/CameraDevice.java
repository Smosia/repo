package android.hardware.camera2;
public abstract class CameraDevice
  implements java.lang.AutoCloseable
{
public abstract static class StateCallback
{
public  StateCallback() { throw new RuntimeException("Stub!"); }
public abstract  void onOpened(android.hardware.camera2.CameraDevice camera);
public  void onClosed(android.hardware.camera2.CameraDevice camera) { throw new RuntimeException("Stub!"); }
public abstract  void onDisconnected(android.hardware.camera2.CameraDevice camera);
public abstract  void onError(android.hardware.camera2.CameraDevice camera, int error);
public static final int ERROR_CAMERA_DEVICE = 4;
public static final int ERROR_CAMERA_DISABLED = 3;
public static final int ERROR_CAMERA_IN_USE = 1;
public static final int ERROR_CAMERA_SERVICE = 5;
public static final int ERROR_MAX_CAMERAS_IN_USE = 2;
}
CameraDevice() { throw new RuntimeException("Stub!"); }
public abstract  java.lang.String getId();
public abstract  void createCaptureSession(java.util.List<android.view.Surface> outputs, android.hardware.camera2.CameraCaptureSession.StateCallback callback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  void createReprocessableCaptureSession(android.hardware.camera2.params.InputConfiguration inputConfig, java.util.List<android.view.Surface> outputs, android.hardware.camera2.CameraCaptureSession.StateCallback callback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  void createConstrainedHighSpeedCaptureSession(java.util.List<android.view.Surface> outputs, android.hardware.camera2.CameraCaptureSession.StateCallback callback, android.os.Handler handler) throws android.hardware.camera2.CameraAccessException;
public abstract  android.hardware.camera2.CaptureRequest.Builder createCaptureRequest(int templateType) throws android.hardware.camera2.CameraAccessException;
public abstract  android.hardware.camera2.CaptureRequest.Builder createReprocessCaptureRequest(android.hardware.camera2.TotalCaptureResult inputResult) throws android.hardware.camera2.CameraAccessException;
public abstract  void close();
public static final int TEMPLATE_MANUAL = 6;
public static final int TEMPLATE_PREVIEW = 1;
public static final int TEMPLATE_RECORD = 3;
public static final int TEMPLATE_STILL_CAPTURE = 2;
public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;
}
