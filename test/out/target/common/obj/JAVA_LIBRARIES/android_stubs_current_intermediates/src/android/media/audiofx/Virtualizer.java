package android.media.audiofx;
public class Virtualizer
  extends android.media.audiofx.AudioEffect
{
public static interface OnParameterChangeListener
{
public abstract  void onParameterChange(android.media.audiofx.Virtualizer effect, int status, int param, short value);
}
public static class Settings
{
public  Settings() { throw new RuntimeException("Stub!"); }
public  Settings(java.lang.String settings) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public short strength;
}
public  Virtualizer(int priority, int audioSession) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException, java.lang.RuntimeException { throw new RuntimeException("Stub!"); }
public  boolean getStrengthSupported() { throw new RuntimeException("Stub!"); }
public  void setStrength(short strength) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  short getRoundedStrength() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  boolean canVirtualize(int inputChannelMask, int virtualizationMode) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  boolean getSpeakerAngles(int inputChannelMask, int virtualizationMode, int[] angles) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  boolean forceVirtualizationMode(int virtualizationMode) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  int getVirtualizationMode() throws java.lang.IllegalStateException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  void setParameterListener(android.media.audiofx.Virtualizer.OnParameterChangeListener listener) { throw new RuntimeException("Stub!"); }
public  android.media.audiofx.Virtualizer.Settings getProperties() throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public  void setProperties(android.media.audiofx.Virtualizer.Settings settings) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException, java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
public static final int PARAM_STRENGTH = 1;
public static final int PARAM_STRENGTH_SUPPORTED = 0;
public static final int VIRTUALIZATION_MODE_AUTO = 1;
public static final int VIRTUALIZATION_MODE_BINAURAL = 2;
public static final int VIRTUALIZATION_MODE_OFF = 0;
public static final int VIRTUALIZATION_MODE_TRANSAURAL = 3;
}