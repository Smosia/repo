package android.hardware.display;
public final class DisplayManager
{
public static interface DisplayListener
{
public abstract  void onDisplayAdded(int displayId);
public abstract  void onDisplayRemoved(int displayId);
public abstract  void onDisplayChanged(int displayId);
}
DisplayManager() { throw new RuntimeException("Stub!"); }
public  android.view.Display getDisplay(int displayId) { throw new RuntimeException("Stub!"); }
public  android.view.Display[] getDisplays() { throw new RuntimeException("Stub!"); }
public  android.view.Display[] getDisplays(java.lang.String category) { throw new RuntimeException("Stub!"); }
public  void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterDisplayListener(android.hardware.display.DisplayManager.DisplayListener listener) { throw new RuntimeException("Stub!"); }
public  android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String name, int width, int height, int densityDpi, android.view.Surface surface, int flags) { throw new RuntimeException("Stub!"); }
public  android.hardware.display.VirtualDisplay createVirtualDisplay(java.lang.String name, int width, int height, int densityDpi, android.view.Surface surface, int flags, android.hardware.display.VirtualDisplay.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public static final java.lang.String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
public static final int VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR = 16;
public static final int VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY = 8;
public static final int VIRTUAL_DISPLAY_FLAG_PRESENTATION = 2;
public static final int VIRTUAL_DISPLAY_FLAG_PUBLIC = 1;
public static final int VIRTUAL_DISPLAY_FLAG_SECURE = 4;
}
