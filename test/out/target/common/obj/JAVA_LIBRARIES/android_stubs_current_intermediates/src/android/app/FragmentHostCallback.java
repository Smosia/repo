package android.app;
public abstract class FragmentHostCallback<E>
  extends android.app.FragmentContainer
{
public  FragmentHostCallback(android.content.Context context, android.os.Handler handler, int windowAnimations) { throw new RuntimeException("Stub!"); }
public  void onDump(java.lang.String prefix, java.io.FileDescriptor fd, java.io.PrintWriter writer, java.lang.String[] args) { throw new RuntimeException("Stub!"); }
public  boolean onShouldSaveFragmentState(android.app.Fragment fragment) { throw new RuntimeException("Stub!"); }
public  android.view.LayoutInflater onGetLayoutInflater() { throw new RuntimeException("Stub!"); }
public  boolean onUseFragmentManagerInflaterFactory() { throw new RuntimeException("Stub!"); }
public abstract  E onGetHost();
public  void onInvalidateOptionsMenu() { throw new RuntimeException("Stub!"); }
public  void onStartActivityFromFragment(android.app.Fragment fragment, android.content.Intent intent, int requestCode, android.os.Bundle options) { throw new RuntimeException("Stub!"); }
public  void onRequestPermissionsFromFragment(android.app.Fragment fragment, java.lang.String[] permissions, int requestCode) { throw new RuntimeException("Stub!"); }
public  boolean onHasWindowAnimations() { throw new RuntimeException("Stub!"); }
public  int onGetWindowAnimations() { throw new RuntimeException("Stub!"); }
public  void onAttachFragment(android.app.Fragment fragment) { throw new RuntimeException("Stub!"); }
public  android.view.View onFindViewById(int id) { throw new RuntimeException("Stub!"); }
public  boolean onHasView() { throw new RuntimeException("Stub!"); }
}
