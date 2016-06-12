package android.hardware.camera2.params;
public final class LensShadingMap
{
LensShadingMap() { throw new RuntimeException("Stub!"); }
public  int getRowCount() { throw new RuntimeException("Stub!"); }
public  int getColumnCount() { throw new RuntimeException("Stub!"); }
public  int getGainFactorCount() { throw new RuntimeException("Stub!"); }
public  float getGainFactor(int colorChannel, int column, int row) { throw new RuntimeException("Stub!"); }
public  android.hardware.camera2.params.RggbChannelVector getGainFactorVector(int column, int row) { throw new RuntimeException("Stub!"); }
public  void copyGainFactors(float[] destination, int offset) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object obj) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final float MINIMUM_GAIN_FACTOR = 1.0f;
}
