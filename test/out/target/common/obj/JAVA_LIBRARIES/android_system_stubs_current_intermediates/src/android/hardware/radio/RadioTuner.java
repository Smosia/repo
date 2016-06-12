package android.hardware.radio;
public abstract class RadioTuner
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public  void onError(int status) { throw new RuntimeException("Stub!"); }
public  void onConfigurationChanged(android.hardware.radio.RadioManager.BandConfig config) { throw new RuntimeException("Stub!"); }
public  void onProgramInfoChanged(android.hardware.radio.RadioManager.ProgramInfo info) { throw new RuntimeException("Stub!"); }
public  void onMetadataChanged(android.hardware.radio.RadioMetadata metadata) { throw new RuntimeException("Stub!"); }
public  void onTrafficAnnouncement(boolean active) { throw new RuntimeException("Stub!"); }
public  void onAntennaState(boolean connected) { throw new RuntimeException("Stub!"); }
public  void onControlChanged(boolean control) { throw new RuntimeException("Stub!"); }
}
public  RadioTuner() { throw new RuntimeException("Stub!"); }
public abstract  void close();
public abstract  int setConfiguration(android.hardware.radio.RadioManager.BandConfig config);
public abstract  int getConfiguration(android.hardware.radio.RadioManager.BandConfig[] config);
public abstract  int setMute(boolean mute);
public abstract  boolean getMute();
public abstract  int step(int direction, boolean skipSubChannel);
public abstract  int scan(int direction, boolean skipSubChannel);
public abstract  int tune(int channel, int subChannel);
public abstract  int cancel();
public abstract  int getProgramInformation(android.hardware.radio.RadioManager.ProgramInfo[] info);
public abstract  boolean isAntennaConnected();
public abstract  boolean hasControl();
public static final int DIRECTION_DOWN = 1;
public static final int DIRECTION_UP = 0;
public static final int ERROR_CANCELLED = 2;
public static final int ERROR_CONFIG = 4;
public static final int ERROR_HARDWARE_FAILURE = 0;
public static final int ERROR_SCAN_TIMEOUT = 3;
public static final int ERROR_SERVER_DIED = 1;
}
