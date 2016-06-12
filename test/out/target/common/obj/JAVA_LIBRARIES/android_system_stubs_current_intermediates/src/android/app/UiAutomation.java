package android.app;
public final class UiAutomation
{
public static interface OnAccessibilityEventListener
{
public abstract  void onAccessibilityEvent(android.view.accessibility.AccessibilityEvent event);
}
public static interface AccessibilityEventFilter
{
public abstract  boolean accept(android.view.accessibility.AccessibilityEvent event);
}
UiAutomation() { throw new RuntimeException("Stub!"); }
public  void setOnAccessibilityEventListener(android.app.UiAutomation.OnAccessibilityEventListener listener) { throw new RuntimeException("Stub!"); }
public final  boolean performGlobalAction(int action) { throw new RuntimeException("Stub!"); }
public  android.view.accessibility.AccessibilityNodeInfo findFocus(int focus) { throw new RuntimeException("Stub!"); }
public final  android.accessibilityservice.AccessibilityServiceInfo getServiceInfo() { throw new RuntimeException("Stub!"); }
public final  void setServiceInfo(android.accessibilityservice.AccessibilityServiceInfo info) { throw new RuntimeException("Stub!"); }
public  java.util.List<android.view.accessibility.AccessibilityWindowInfo> getWindows() { throw new RuntimeException("Stub!"); }
public  android.view.accessibility.AccessibilityNodeInfo getRootInActiveWindow() { throw new RuntimeException("Stub!"); }
public  boolean injectInputEvent(android.view.InputEvent event, boolean sync) { throw new RuntimeException("Stub!"); }
public  boolean setRotation(int rotation) { throw new RuntimeException("Stub!"); }
public  android.view.accessibility.AccessibilityEvent executeAndWaitForEvent(java.lang.Runnable command, android.app.UiAutomation.AccessibilityEventFilter filter, long timeoutMillis) throws java.util.concurrent.TimeoutException { throw new RuntimeException("Stub!"); }
public  void waitForIdle(long idleTimeoutMillis, long globalTimeoutMillis) throws java.util.concurrent.TimeoutException { throw new RuntimeException("Stub!"); }
public  android.graphics.Bitmap takeScreenshot() { throw new RuntimeException("Stub!"); }
public  void setRunAsMonkey(boolean enable) { throw new RuntimeException("Stub!"); }
public  boolean clearWindowContentFrameStats(int windowId) { throw new RuntimeException("Stub!"); }
public  android.view.WindowContentFrameStats getWindowContentFrameStats(int windowId) { throw new RuntimeException("Stub!"); }
public  void clearWindowAnimationFrameStats() { throw new RuntimeException("Stub!"); }
public  android.view.WindowAnimationFrameStats getWindowAnimationFrameStats() { throw new RuntimeException("Stub!"); }
public  android.os.ParcelFileDescriptor executeShellCommand(java.lang.String command) { throw new RuntimeException("Stub!"); }
public static final int ROTATION_FREEZE_0 = 0;
public static final int ROTATION_FREEZE_180 = 2;
public static final int ROTATION_FREEZE_270 = 3;
public static final int ROTATION_FREEZE_90 = 1;
public static final int ROTATION_FREEZE_CURRENT = -1;
public static final int ROTATION_UNFREEZE = -2;
}
