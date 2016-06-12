package android.os;
public final class PowerManager
{
public final class WakeLock
{
WakeLock() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public  void setReferenceCounted(boolean value) { throw new RuntimeException("Stub!"); }
public  void acquire() { throw new RuntimeException("Stub!"); }
public  void acquire(long timeout) { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
public  void release(int flags) { throw new RuntimeException("Stub!"); }
public  boolean isHeld() { throw new RuntimeException("Stub!"); }
public  void setWorkSource(android.os.WorkSource ws) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
}
PowerManager() { throw new RuntimeException("Stub!"); }
public  android.os.PowerManager.WakeLock newWakeLock(int levelAndFlags, java.lang.String tag) { throw new RuntimeException("Stub!"); }
public  boolean isWakeLockLevelSupported(int level) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  boolean isScreenOn() { throw new RuntimeException("Stub!"); }
public  boolean isInteractive() { throw new RuntimeException("Stub!"); }
public  void reboot(java.lang.String reason) { throw new RuntimeException("Stub!"); }
public  boolean isPowerSaveMode() { throw new RuntimeException("Stub!"); }
public  boolean isDeviceIdleMode() { throw new RuntimeException("Stub!"); }
public  boolean isIgnoringBatteryOptimizations(java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public static final int ACQUIRE_CAUSES_WAKEUP = 268435456;
public static final java.lang.String ACTION_DEVICE_IDLE_MODE_CHANGED = "android.os.action.DEVICE_IDLE_MODE_CHANGED";
public static final java.lang.String ACTION_POWER_SAVE_MODE_CHANGED = "android.os.action.POWER_SAVE_MODE_CHANGED";
@java.lang.Deprecated()
public static final int FULL_WAKE_LOCK = 26;
public static final int ON_AFTER_RELEASE = 536870912;
public static final int PARTIAL_WAKE_LOCK = 1;
public static final int PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
public static final int RELEASE_FLAG_WAIT_FOR_NO_PROXIMITY = 1;
@java.lang.Deprecated()
public static final int SCREEN_BRIGHT_WAKE_LOCK = 10;
@java.lang.Deprecated()
public static final int SCREEN_DIM_WAKE_LOCK = 6;
}
