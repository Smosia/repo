package android.webkit;
public final class WebViewDelegate
{
public static interface OnTraceEnabledChangeListener
{
public abstract  void onTraceEnabledChange(boolean enabled);
}
WebViewDelegate() { throw new RuntimeException("Stub!"); }
public  void setOnTraceEnabledChangeListener(android.webkit.WebViewDelegate.OnTraceEnabledChangeListener listener) { throw new RuntimeException("Stub!"); }
public  boolean isTraceTagEnabled() { throw new RuntimeException("Stub!"); }
public  boolean canInvokeDrawGlFunctor(android.view.View containerView) { throw new RuntimeException("Stub!"); }
public  void invokeDrawGlFunctor(android.view.View containerView, long nativeDrawGLFunctor, boolean waitForCompletion) { throw new RuntimeException("Stub!"); }
public  void callDrawGlFunction(android.graphics.Canvas canvas, long nativeDrawGLFunctor) { throw new RuntimeException("Stub!"); }
public  void detachDrawGlFunctor(android.view.View containerView, long nativeDrawGLFunctor) { throw new RuntimeException("Stub!"); }
public  int getPackageId(android.content.res.Resources resources, java.lang.String packageName) { throw new RuntimeException("Stub!"); }
public  android.app.Application getApplication() { throw new RuntimeException("Stub!"); }
public  java.lang.String getErrorString(android.content.Context context, int errorCode) { throw new RuntimeException("Stub!"); }
public  void addWebViewAssetPath(android.content.Context context) { throw new RuntimeException("Stub!"); }
}
