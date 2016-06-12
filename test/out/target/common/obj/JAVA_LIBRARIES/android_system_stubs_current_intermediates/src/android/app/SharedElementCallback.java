package android.app;
public abstract class SharedElementCallback
{
public static interface OnSharedElementsReadyListener
{
public abstract  void onSharedElementsReady();
}
public  SharedElementCallback() { throw new RuntimeException("Stub!"); }
public  void onSharedElementStart(java.util.List<java.lang.String> sharedElementNames, java.util.List<android.view.View> sharedElements, java.util.List<android.view.View> sharedElementSnapshots) { throw new RuntimeException("Stub!"); }
public  void onSharedElementEnd(java.util.List<java.lang.String> sharedElementNames, java.util.List<android.view.View> sharedElements, java.util.List<android.view.View> sharedElementSnapshots) { throw new RuntimeException("Stub!"); }
public  void onRejectSharedElements(java.util.List<android.view.View> rejectedSharedElements) { throw new RuntimeException("Stub!"); }
public  void onMapSharedElements(java.util.List<java.lang.String> names, java.util.Map<java.lang.String, android.view.View> sharedElements) { throw new RuntimeException("Stub!"); }
public  android.os.Parcelable onCaptureSharedElementSnapshot(android.view.View sharedElement, android.graphics.Matrix viewToGlobalMatrix, android.graphics.RectF screenBounds) { throw new RuntimeException("Stub!"); }
public  android.view.View onCreateSnapshotView(android.content.Context context, android.os.Parcelable snapshot) { throw new RuntimeException("Stub!"); }
public  void onSharedElementsArrived(java.util.List<java.lang.String> sharedElementNames, java.util.List<android.view.View> sharedElements, android.app.SharedElementCallback.OnSharedElementsReadyListener listener) { throw new RuntimeException("Stub!"); }
}
