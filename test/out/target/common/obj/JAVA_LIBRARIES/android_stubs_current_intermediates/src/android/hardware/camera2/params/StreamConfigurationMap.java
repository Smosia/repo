package android.hardware.camera2.params;
public final class StreamConfigurationMap
{
StreamConfigurationMap() { throw new RuntimeException("Stub!"); }
public final  int[] getOutputFormats() { throw new RuntimeException("Stub!"); }
public final  int[] getValidOutputFormatsForInput(int inputFormat) { throw new RuntimeException("Stub!"); }
public final  int[] getInputFormats() { throw new RuntimeException("Stub!"); }
public  android.util.Size[] getInputSizes(int format) { throw new RuntimeException("Stub!"); }
public  boolean isOutputSupportedFor(int format) { throw new RuntimeException("Stub!"); }
public static <T> boolean isOutputSupportedFor(java.lang.Class<T> klass) { throw new RuntimeException("Stub!"); }
public  boolean isOutputSupportedFor(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public <T> android.util.Size[] getOutputSizes(java.lang.Class<T> klass) { throw new RuntimeException("Stub!"); }
public  android.util.Size[] getOutputSizes(int format) { throw new RuntimeException("Stub!"); }
public  android.util.Size[] getHighSpeedVideoSizes() { throw new RuntimeException("Stub!"); }
public  android.util.Range<java.lang.Integer>[] getHighSpeedVideoFpsRangesFor(android.util.Size size) { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"unchecked"})
public  android.util.Range<java.lang.Integer>[] getHighSpeedVideoFpsRanges() { throw new RuntimeException("Stub!"); }
public  android.util.Size[] getHighSpeedVideoSizesFor(android.util.Range<java.lang.Integer> fpsRange) { throw new RuntimeException("Stub!"); }
public  android.util.Size[] getHighResolutionOutputSizes(int format) { throw new RuntimeException("Stub!"); }
public  long getOutputMinFrameDuration(int format, android.util.Size size) { throw new RuntimeException("Stub!"); }
public <T> long getOutputMinFrameDuration(java.lang.Class<T> klass, android.util.Size size) { throw new RuntimeException("Stub!"); }
public  long getOutputStallDuration(int format, android.util.Size size) { throw new RuntimeException("Stub!"); }
public <T> long getOutputStallDuration(java.lang.Class<T> klass, android.util.Size size) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
}
