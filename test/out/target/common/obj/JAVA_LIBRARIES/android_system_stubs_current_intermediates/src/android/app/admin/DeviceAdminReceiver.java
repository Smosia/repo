package android.app.admin;
public class DeviceAdminReceiver
  extends android.content.BroadcastReceiver
{
public  DeviceAdminReceiver() { throw new RuntimeException("Stub!"); }
public  android.app.admin.DevicePolicyManager getManager(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  android.content.ComponentName getWho(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  void onEnabled(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence onDisableRequested(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onDisabled(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onPasswordChanged(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onPasswordFailed(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onPasswordSucceeded(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onPasswordExpiring(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onProfileProvisioningComplete(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onReadyForUserInitialization(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void onLockTaskModeEntering(android.content.Context context, android.content.Intent intent, java.lang.String pkg) { throw new RuntimeException("Stub!"); }
public  void onLockTaskModeExiting(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  java.lang.String onChoosePrivateKeyAlias(android.content.Context context, android.content.Intent intent, int uid, android.net.Uri uri, java.lang.String alias) { throw new RuntimeException("Stub!"); }
public  void onSystemUpdatePending(android.content.Context context, android.content.Intent intent, long receivedTime) { throw new RuntimeException("Stub!"); }
public  void onReceive(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_DEVICE_ADMIN_DISABLED = "android.app.action.DEVICE_ADMIN_DISABLED";
public static final java.lang.String ACTION_DEVICE_ADMIN_DISABLE_REQUESTED = "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED";
public static final java.lang.String ACTION_DEVICE_ADMIN_ENABLED = "android.app.action.DEVICE_ADMIN_ENABLED";
public static final java.lang.String ACTION_LOCK_TASK_ENTERING = "android.app.action.LOCK_TASK_ENTERING";
public static final java.lang.String ACTION_LOCK_TASK_EXITING = "android.app.action.LOCK_TASK_EXITING";
public static final java.lang.String ACTION_PASSWORD_CHANGED = "android.app.action.ACTION_PASSWORD_CHANGED";
public static final java.lang.String ACTION_PASSWORD_EXPIRING = "android.app.action.ACTION_PASSWORD_EXPIRING";
public static final java.lang.String ACTION_PASSWORD_FAILED = "android.app.action.ACTION_PASSWORD_FAILED";
public static final java.lang.String ACTION_PASSWORD_SUCCEEDED = "android.app.action.ACTION_PASSWORD_SUCCEEDED";
public static final java.lang.String ACTION_PROFILE_PROVISIONING_COMPLETE = "android.app.action.PROFILE_PROVISIONING_COMPLETE";
public static final java.lang.String DEVICE_ADMIN_META_DATA = "android.app.device_admin";
public static final java.lang.String EXTRA_DISABLE_WARNING = "android.app.extra.DISABLE_WARNING";
public static final java.lang.String EXTRA_LOCK_TASK_PACKAGE = "android.app.extra.LOCK_TASK_PACKAGE";
}
