package android.transition;
public abstract class TransitionPropagation
{
public  TransitionPropagation() { throw new RuntimeException("Stub!"); }
public abstract  long getStartDelay(android.view.ViewGroup sceneRoot, android.transition.Transition transition, android.transition.TransitionValues startValues, android.transition.TransitionValues endValues);
public abstract  void captureValues(android.transition.TransitionValues transitionValues);
public abstract  java.lang.String[] getPropagationProperties();
}
