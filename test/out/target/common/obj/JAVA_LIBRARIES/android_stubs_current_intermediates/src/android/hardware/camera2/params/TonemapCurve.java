package android.hardware.camera2.params;
public final class TonemapCurve
{
public  TonemapCurve(float[] red, float[] green, float[] blue) { throw new RuntimeException("Stub!"); }
public  int getPointCount(int colorChannel) { throw new RuntimeException("Stub!"); }
public  android.graphics.PointF getPoint(int colorChannel, int index) { throw new RuntimeException("Stub!"); }
public  void copyColorCurve(int colorChannel, float[] destination, int offset) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int CHANNEL_BLUE = 2;
public static final int CHANNEL_GREEN = 1;
public static final int CHANNEL_RED = 0;
public static final float LEVEL_BLACK = 0.0f;
public static final float LEVEL_WHITE = 1.0f;
public static final int POINT_SIZE = 2;
}
