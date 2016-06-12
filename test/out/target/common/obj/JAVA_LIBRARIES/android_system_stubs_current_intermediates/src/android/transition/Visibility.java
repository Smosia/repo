package android.transition;
public abstract class Visibility
  extends android.transition.Transition
{
public  Visibility() { throw new RuntimeException("Stub!"); }
public  Visibility(android.content.Context context, android.util.AttributeSet attrs) { throw new RuntimeException("Stub!"); }
public  void setMode(int mode) { throw new RuntimeException("Stub!"); }
public  int getMode() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getTransitionProperties() { throw new RuntimeException("Stub!"); }
public  void captureStartValues(android.transition.TransitionValues transitionValues) { throw new RuntimeException("Stub!"); }
public  void captureEndValues(android.transition.TransitionValues transitionValues) { throw new RuntimeException("Stub!"); }
public  boolean isVisible(android.transition.TransitionValues values) { throw new RuntimeException("Stub!"); }
public  android.animation.Animator createAnimator(android.view.ViewGroup sceneRoot, android.transition.TransitionValues startValues, android.transition.TransitionValues endValues) { throw new RuntimeException("Stub!"); }
public  android.animation.Animator onAppear(android.view.ViewGroup sceneRoot, android.transition.TransitionValues startValues, int startVisibility, android.transition.TransitionValues endValues, int endVisibility) { throw new RuntimeException("Stub!"); }
public  android.animation.Animator onAppear(android.view.ViewGroup sceneRoot, android.view.View view, android.transition.TransitionValues startValues, android.transition.TransitionValues endValues) { throw new RuntimeException("Stub!"); }
public  android.animation.Animator onDisappear(android.view.ViewGroup sceneRoot, android.transition.TransitionValues startValues, int startVisibility, android.transition.TransitionValues endValues, int endVisibility) { throw new RuntimeException("Stub!"); }
public  boolean isTransitionRequired(android.transition.TransitionValues startValues, android.transition.TransitionValues newValues) { throw new RuntimeException("Stub!"); }
public  android.animation.Animator onDisappear(android.view.ViewGroup sceneRoot, android.view.View view, android.transition.TransitionValues startValues, android.transition.TransitionValues endValues) { throw new RuntimeException("Stub!"); }
public static final int MODE_IN = 1;
public static final int MODE_OUT = 2;
}
