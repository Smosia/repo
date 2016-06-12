package android.graphics;
public class SurfaceTexture
{
public static interface OnFrameAvailableListener
{
public abstract  void onFrameAvailable(android.graphics.SurfaceTexture surfaceTexture);
}
@java.lang.SuppressWarnings(value={"serial"})
@java.lang.Deprecated()
public static class OutOfResourcesException
  extends java.lang.Exception
{
public  OutOfResourcesException() { throw new RuntimeException("Stub!"); }
public  OutOfResourcesException(java.lang.String name) { throw new RuntimeException("Stub!"); }
}
public  SurfaceTexture(int texName) { throw new RuntimeException("Stub!"); }
public  SurfaceTexture(int texName, boolean singleBufferMode) { throw new RuntimeException("Stub!"); }
public  void setOnFrameAvailableListener(android.graphics.SurfaceTexture.OnFrameAvailableListener listener) { throw new RuntimeException("Stub!"); }
public  void setOnFrameAvailableListener(android.graphics.SurfaceTexture.OnFrameAvailableListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void setDefaultBufferSize(int width, int height) { throw new RuntimeException("Stub!"); }
public  void updateTexImage() { throw new RuntimeException("Stub!"); }
public  void releaseTexImage() { throw new RuntimeException("Stub!"); }
public  void detachFromGLContext() { throw new RuntimeException("Stub!"); }
public  void attachToGLContext(int texName) { throw new RuntimeException("Stub!"); }
public  void getTransformMatrix(float[] mtx) { throw new RuntimeException("Stub!"); }
public  long getTimestamp() { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
}
