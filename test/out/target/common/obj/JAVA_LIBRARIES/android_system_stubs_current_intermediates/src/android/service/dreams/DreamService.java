package android.service.dreams;
public class DreamService
  extends android.app.Service
  implements android.view.Window.Callback
{
public  DreamService() { throw new RuntimeException("Stub!"); }
public  boolean dispatchKeyEvent(android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchKeyShortcutEvent(android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchTouchEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchTrackballEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchGenericMotionEvent(android.view.MotionEvent event) { throw new RuntimeException("Stub!"); }
public  boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent event) { throw new RuntimeException("Stub!"); }
public  android.view.View onCreatePanelView(int featureId) { throw new RuntimeException("Stub!"); }
public  boolean onCreatePanelMenu(int featureId, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onPreparePanel(int featureId, android.view.View view, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onMenuOpened(int featureId, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onMenuItemSelected(int featureId, android.view.MenuItem item) { throw new RuntimeException("Stub!"); }
public  void onWindowAttributesChanged(android.view.WindowManager.LayoutParams attrs) { throw new RuntimeException("Stub!"); }
public  void onContentChanged() { throw new RuntimeException("Stub!"); }
public  void onWindowFocusChanged(boolean hasFocus) { throw new RuntimeException("Stub!"); }
public  void onAttachedToWindow() { throw new RuntimeException("Stub!"); }
public  void onDetachedFromWindow() { throw new RuntimeException("Stub!"); }
public  void onPanelClosed(int featureId, android.view.Menu menu) { throw new RuntimeException("Stub!"); }
public  boolean onSearchRequested(android.view.SearchEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onSearchRequested() { throw new RuntimeException("Stub!"); }
public  android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) { throw new RuntimeException("Stub!"); }
public  android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int type) { throw new RuntimeException("Stub!"); }
public  void onActionModeStarted(android.view.ActionMode mode) { throw new RuntimeException("Stub!"); }
public  void onActionModeFinished(android.view.ActionMode mode) { throw new RuntimeException("Stub!"); }
public  android.view.WindowManager getWindowManager() { throw new RuntimeException("Stub!"); }
public  android.view.Window getWindow() { throw new RuntimeException("Stub!"); }
public  void setContentView(int layoutResID) { throw new RuntimeException("Stub!"); }
public  void setContentView(android.view.View view) { throw new RuntimeException("Stub!"); }
public  void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams params) { throw new RuntimeException("Stub!"); }
public  void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams params) { throw new RuntimeException("Stub!"); }
public  android.view.View findViewById(int id) { throw new RuntimeException("Stub!"); }
public  void setInteractive(boolean interactive) { throw new RuntimeException("Stub!"); }
public  boolean isInteractive() { throw new RuntimeException("Stub!"); }
public  void setFullscreen(boolean fullscreen) { throw new RuntimeException("Stub!"); }
public  boolean isFullscreen() { throw new RuntimeException("Stub!"); }
public  void setScreenBright(boolean screenBright) { throw new RuntimeException("Stub!"); }
public  boolean isScreenBright() { throw new RuntimeException("Stub!"); }
public  void onCreate() { throw new RuntimeException("Stub!"); }
public  void onDreamingStarted() { throw new RuntimeException("Stub!"); }
public  void onDreamingStopped() { throw new RuntimeException("Stub!"); }
public  void onWakeUp() { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public final  void finish() { throw new RuntimeException("Stub!"); }
public final  void wakeUp() { throw new RuntimeException("Stub!"); }
public  void onDestroy() { throw new RuntimeException("Stub!"); }
protected  void dump(java.io.FileDescriptor fd, java.io.PrintWriter pw, java.lang.String[] args) { throw new RuntimeException("Stub!"); }
public static final java.lang.String DREAM_META_DATA = "android.service.dream";
public static final java.lang.String SERVICE_INTERFACE = "android.service.dreams.DreamService";
}
