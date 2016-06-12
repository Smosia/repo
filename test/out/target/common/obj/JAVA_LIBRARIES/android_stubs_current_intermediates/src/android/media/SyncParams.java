package android.media;
public final class SyncParams
{
public  SyncParams() { throw new RuntimeException("Stub!"); }
public  android.media.SyncParams allowDefaults() { throw new RuntimeException("Stub!"); }
public  android.media.SyncParams setAudioAdjustMode(int audioAdjustMode) { throw new RuntimeException("Stub!"); }
public  int getAudioAdjustMode() { throw new RuntimeException("Stub!"); }
public  android.media.SyncParams setSyncSource(int syncSource) { throw new RuntimeException("Stub!"); }
public  int getSyncSource() { throw new RuntimeException("Stub!"); }
public  android.media.SyncParams setTolerance(float tolerance) { throw new RuntimeException("Stub!"); }
public  float getTolerance() { throw new RuntimeException("Stub!"); }
public  android.media.SyncParams setFrameRate(float frameRate) { throw new RuntimeException("Stub!"); }
public  float getFrameRate() { throw new RuntimeException("Stub!"); }
public static final int AUDIO_ADJUST_MODE_DEFAULT = 0;
public static final int AUDIO_ADJUST_MODE_RESAMPLE = 2;
public static final int AUDIO_ADJUST_MODE_STRETCH = 1;
public static final int SYNC_SOURCE_AUDIO = 2;
public static final int SYNC_SOURCE_DEFAULT = 0;
public static final int SYNC_SOURCE_SYSTEM_CLOCK = 1;
public static final int SYNC_SOURCE_VSYNC = 3;
}
