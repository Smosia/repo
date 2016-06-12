package android.media;
public class ImageReader
  implements java.lang.AutoCloseable
{
public static interface OnImageAvailableListener
{
public abstract  void onImageAvailable(android.media.ImageReader reader);
}
ImageReader() { throw new RuntimeException("Stub!"); }
public static  android.media.ImageReader newInstance(int width, int height, int format, int maxImages) { throw new RuntimeException("Stub!"); }
public  int getWidth() { throw new RuntimeException("Stub!"); }
public  int getHeight() { throw new RuntimeException("Stub!"); }
public  int getImageFormat() { throw new RuntimeException("Stub!"); }
public  int getMaxImages() { throw new RuntimeException("Stub!"); }
public  android.view.Surface getSurface() { throw new RuntimeException("Stub!"); }
public  android.media.Image acquireLatestImage() { throw new RuntimeException("Stub!"); }
public  android.media.Image acquireNextImage() { throw new RuntimeException("Stub!"); }
public  void setOnImageAvailableListener(android.media.ImageReader.OnImageAvailableListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void close() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
}
