package android.animation;
public abstract class Animator
  implements java.lang.Cloneable
{
public static interface AnimatorListener
{
public abstract  void onAnimationStart(android.animation.Animator animation);
public abstract  void onAnimationEnd(android.animation.Animator animation);
public abstract  void onAnimationCancel(android.animation.Animator animation);
public abstract  void onAnimationRepeat(android.animation.Animator animation);
}
public static interface AnimatorPauseListener
{
public abstract  void onAnimationPause(android.animation.Animator animation);
public abstract  void onAnimationResume(android.animation.Animator animation);
}
public  Animator() { throw new RuntimeException("Stub!"); }
public  void start() { throw new RuntimeException("Stub!"); }
public  void cancel() { throw new RuntimeException("Stub!"); }
public  void end() { throw new RuntimeException("Stub!"); }
public  void pause() { throw new RuntimeException("Stub!"); }
public  void resume() { throw new RuntimeException("Stub!"); }
public  boolean isPaused() { throw new RuntimeException("Stub!"); }
public abstract  long getStartDelay();
public abstract  void setStartDelay(long startDelay);
public abstract  android.animation.Animator setDuration(long duration);
public abstract  long getDuration();
public abstract  void setInterpolator(android.animation.TimeInterpolator value);
public  android.animation.TimeInterpolator getInterpolator() { throw new RuntimeException("Stub!"); }
public abstract  boolean isRunning();
public  boolean isStarted() { throw new RuntimeException("Stub!"); }
public  void addListener(android.animation.Animator.AnimatorListener listener) { throw new RuntimeException("Stub!"); }
public  void removeListener(android.animation.Animator.AnimatorListener listener) { throw new RuntimeException("Stub!"); }
public  java.util.ArrayList<android.animation.Animator.AnimatorListener> getListeners() { throw new RuntimeException("Stub!"); }
public  void addPauseListener(android.animation.Animator.AnimatorPauseListener listener) { throw new RuntimeException("Stub!"); }
public  void removePauseListener(android.animation.Animator.AnimatorPauseListener listener) { throw new RuntimeException("Stub!"); }
public  void removeAllListeners() { throw new RuntimeException("Stub!"); }
public  android.animation.Animator clone() { throw new RuntimeException("Stub!"); }
public  void setupStartValues() { throw new RuntimeException("Stub!"); }
public  void setupEndValues() { throw new RuntimeException("Stub!"); }
public  void setTarget(java.lang.Object target) { throw new RuntimeException("Stub!"); }
}
