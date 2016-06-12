package android.media;
public abstract class Image
  implements java.lang.AutoCloseable
{
public abstract static class Plane
{
Plane() { throw new RuntimeException("Stub!"); }
public abstract  int getRowStride();
public abstract  int getPixelStride();
public abstract  java.nio.ByteBuffer getBuffer();
}
Image() { throw new RuntimeException("Stub!"); }
public abstract  int getFormat();
public abstract  int getWidth();
public abstract  int getHeight();
public abstract  long getTimestamp();
public  void setTimestamp(long timestamp) { throw new RuntimeException("Stub!"); }
public  android.graphics.Rect getCropRect() { throw new RuntimeException("Stub!"); }
public  void setCropRect(android.graphics.Rect cropRect) { throw new RuntimeException("Stub!"); }
public abstract  android.media.Image.Plane[] getPlanes();
public abstract  void close();
}
