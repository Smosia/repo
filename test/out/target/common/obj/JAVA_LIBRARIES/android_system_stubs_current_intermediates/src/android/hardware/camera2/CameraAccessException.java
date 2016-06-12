package android.hardware.camera2;
public class CameraAccessException
  extends android.util.AndroidException
{
public  CameraAccessException(int problem) { throw new RuntimeException("Stub!"); }
public  CameraAccessException(int problem, java.lang.String message) { throw new RuntimeException("Stub!"); }
public  CameraAccessException(int problem, java.lang.String message, java.lang.Throwable cause) { throw new RuntimeException("Stub!"); }
public  CameraAccessException(int problem, java.lang.Throwable cause) { throw new RuntimeException("Stub!"); }
public final  int getReason() { throw new RuntimeException("Stub!"); }
public static final int CAMERA_DISABLED = 1;
public static final int CAMERA_DISCONNECTED = 2;
public static final int CAMERA_ERROR = 3;
public static final int CAMERA_IN_USE = 4;
public static final int MAX_CAMERAS_IN_USE = 5;
}
