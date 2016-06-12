package android.app;
public class ActivityOptions
{
ActivityOptions() { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeCustomAnimation(android.content.Context context, int enterResId, int exitResId) { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeScaleUpAnimation(android.view.View source, int startX, int startY, int width, int height) { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeClipRevealAnimation(android.view.View source, int startX, int startY, int width, int height) { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeThumbnailScaleUpAnimation(android.view.View source, android.graphics.Bitmap thumbnail, int startX, int startY) { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeSceneTransitionAnimation(android.app.Activity activity, android.view.View sharedElement, java.lang.String sharedElementName) { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeSceneTransitionAnimation(android.app.Activity activity, android.util.Pair<android.view.View, java.lang.String>... sharedElements) { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeTaskLaunchBehind() { throw new RuntimeException("Stub!"); }
public static  android.app.ActivityOptions makeBasic() { throw new RuntimeException("Stub!"); }
public  void update(android.app.ActivityOptions otherOptions) { throw new RuntimeException("Stub!"); }
public  android.os.Bundle toBundle() { throw new RuntimeException("Stub!"); }
public  void requestUsageTimeReport(android.app.PendingIntent receiver) { throw new RuntimeException("Stub!"); }
public static final java.lang.String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
public static final java.lang.String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
}
