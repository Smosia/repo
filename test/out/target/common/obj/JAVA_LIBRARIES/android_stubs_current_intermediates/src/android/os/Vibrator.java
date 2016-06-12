package android.os;
public abstract class Vibrator
{
Vibrator() { throw new RuntimeException("Stub!"); }
public abstract  boolean hasVibrator();
public  void vibrate(long milliseconds) { throw new RuntimeException("Stub!"); }
public  void vibrate(long milliseconds, android.media.AudioAttributes attributes) { throw new RuntimeException("Stub!"); }
public  void vibrate(long[] pattern, int repeat) { throw new RuntimeException("Stub!"); }
public  void vibrate(long[] pattern, int repeat, android.media.AudioAttributes attributes) { throw new RuntimeException("Stub!"); }
public abstract  void cancel();
}
