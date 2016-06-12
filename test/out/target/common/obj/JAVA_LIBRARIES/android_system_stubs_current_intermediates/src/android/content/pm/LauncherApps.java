package android.content.pm;
public class LauncherApps
{
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public abstract  void onPackageRemoved(java.lang.String packageName, android.os.UserHandle user);
public abstract  void onPackageAdded(java.lang.String packageName, android.os.UserHandle user);
public abstract  void onPackageChanged(java.lang.String packageName, android.os.UserHandle user);
public abstract  void onPackagesAvailable(java.lang.String[] packageNames, android.os.UserHandle user, boolean replacing);
public abstract  void onPackagesUnavailable(java.lang.String[] packageNames, android.os.UserHandle user, boolean replacing);
}
LauncherApps() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.content.pm.LauncherActivityInfo> getActivityList(java.lang.String packageName, android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  android.content.pm.LauncherActivityInfo resolveActivity(android.content.Intent intent, android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  void startMainActivity(android.content.ComponentName component, android.os.UserHandle user, android.graphics.Rect sourceBounds, android.os.Bundle opts) { throw new RuntimeException("Stub!"); }
public  void startAppDetailsActivity(android.content.ComponentName component, android.os.UserHandle user, android.graphics.Rect sourceBounds, android.os.Bundle opts) { throw new RuntimeException("Stub!"); }
public  boolean isPackageEnabled(java.lang.String packageName, android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  boolean isActivityEnabled(android.content.ComponentName component, android.os.UserHandle user) { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.content.pm.LauncherApps.Callback callback) { throw new RuntimeException("Stub!"); }
public  void registerCallback(android.content.pm.LauncherApps.Callback callback, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void unregisterCallback(android.content.pm.LauncherApps.Callback callback) { throw new RuntimeException("Stub!"); }
}
