package android.graphics.drawable;
public interface Animatable2
  extends android.graphics.drawable.Animatable
{
public abstract static class AnimationCallback
{
public  AnimationCallback() { throw new RuntimeException("Stub!"); }
public  void onAnimationStart(android.graphics.drawable.Drawable drawable) { throw new RuntimeException("Stub!"); }
public  void onAnimationEnd(android.graphics.drawable.Drawable drawable) { throw new RuntimeException("Stub!"); }
}
public abstract  void registerAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback callback);
public abstract  boolean unregisterAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback callback);
public abstract  void clearAnimationCallbacks();
}
