package android.hardware.camera2;
public final class DngCreator
  implements java.lang.AutoCloseable
{
public  DngCreator(android.hardware.camera2.CameraCharacteristics characteristics, android.hardware.camera2.CaptureResult metadata) { throw new RuntimeException("Stub!"); }
public  android.hardware.camera2.DngCreator setOrientation(int orientation) { throw new RuntimeException("Stub!"); }
public  android.hardware.camera2.DngCreator setThumbnail(android.graphics.Bitmap pixels) { throw new RuntimeException("Stub!"); }
public  android.hardware.camera2.DngCreator setThumbnail(android.media.Image pixels) { throw new RuntimeException("Stub!"); }
public  android.hardware.camera2.DngCreator setLocation(android.location.Location location) { throw new RuntimeException("Stub!"); }
public  android.hardware.camera2.DngCreator setDescription(java.lang.String description) { throw new RuntimeException("Stub!"); }
public  void writeInputStream(java.io.OutputStream dngOutput, android.util.Size size, java.io.InputStream pixels, long offset) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void writeByteBuffer(java.io.OutputStream dngOutput, android.util.Size size, java.nio.ByteBuffer pixels, long offset) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void writeImage(java.io.OutputStream dngOutput, android.media.Image pixels) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void close() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public static final int MAX_THUMBNAIL_DIMENSION = 256;
}
