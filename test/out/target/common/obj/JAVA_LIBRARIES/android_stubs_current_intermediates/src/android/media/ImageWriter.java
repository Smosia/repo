package android.media;
public class ImageWriter
  implements java.lang.AutoCloseable
{
public static interface OnImageReleasedListener
{
public abstract  void onImageReleased(android.media.ImageWriter writer);
}
ImageWriter() { throw new RuntimeException("Stub!"); }
public static  android.media.ImageWriter newInstance(android.view.Surface surface, int maxImages) { throw new RuntimeException("Stub!"); }
public  int getMaxImages() { throw new RuntimeException("Stub!"); }
public  android.media.Image dequeueInputImage() { throw new RuntimeException("Stub!"); }
public  void queueInputImage(android.media.Image image) { throw new RuntimeException("Stub!"); }
public  int getFormat() { throw new RuntimeException("Stub!"); }
public  void setOnImageReleasedListener(android.media.ImageWriter.OnImageReleasedListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void close() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
}
