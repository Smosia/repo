package android.view;
public final class Display
{
public static final class Mode
  implements android.os.Parcelable
{
Mode() { throw new RuntimeException("Stub!"); }
public  int getModeId() { throw new RuntimeException("Stub!"); }
public  int getPhysicalWidth() { throw new RuntimeException("Stub!"); }
public  int getPhysicalHeight() { throw new RuntimeException("Stub!"); }
public  float getRefreshRate() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object other) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int parcelableFlags) { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"hiding"})
public static final android.os.Parcelable.Creator<android.view.Display.Mode> CREATOR;
static { CREATOR = null; }
}
Display() { throw new RuntimeException("Stub!"); }
public  int getDisplayId() { throw new RuntimeException("Stub!"); }
public  boolean isValid() { throw new RuntimeException("Stub!"); }
public  int getFlags() { throw new RuntimeException("Stub!"); }
public  java.lang.String getName() { throw new RuntimeException("Stub!"); }
public  void getSize(android.graphics.Point outSize) { throw new RuntimeException("Stub!"); }
public  void getRectSize(android.graphics.Rect outSize) { throw new RuntimeException("Stub!"); }
public  void getCurrentSizeRange(android.graphics.Point outSmallestSize, android.graphics.Point outLargestSize) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getWidth() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getHeight() { throw new RuntimeException("Stub!"); }
public  int getRotation() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getOrientation() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getPixelFormat() { throw new RuntimeException("Stub!"); }
public  float getRefreshRate() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  float[] getSupportedRefreshRates() { throw new RuntimeException("Stub!"); }
public  android.view.Display.Mode getMode() { throw new RuntimeException("Stub!"); }
public  android.view.Display.Mode[] getSupportedModes() { throw new RuntimeException("Stub!"); }
public  long getAppVsyncOffsetNanos() { throw new RuntimeException("Stub!"); }
public  long getPresentationDeadlineNanos() { throw new RuntimeException("Stub!"); }
public  void getMetrics(android.util.DisplayMetrics outMetrics) { throw new RuntimeException("Stub!"); }
public  void getRealSize(android.graphics.Point outSize) { throw new RuntimeException("Stub!"); }
public  void getRealMetrics(android.util.DisplayMetrics outMetrics) { throw new RuntimeException("Stub!"); }
public  int getState() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int DEFAULT_DISPLAY = 0;
public static final int FLAG_PRESENTATION = 8;
public static final int FLAG_PRIVATE = 4;
public static final int FLAG_ROUND = 16;
public static final int FLAG_SECURE = 2;
public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 1;
public static final int INVALID_DISPLAY = -1;
public static final int STATE_DOZE = 3;
public static final int STATE_DOZE_SUSPEND = 4;
public static final int STATE_OFF = 1;
public static final int STATE_ON = 2;
public static final int STATE_UNKNOWN = 0;
}
