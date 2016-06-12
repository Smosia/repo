package android.location;
public abstract class SettingInjectorService
  extends android.app.Service
{
public  SettingInjectorService(java.lang.String name) { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public final  void onStart(android.content.Intent intent, int startId) { throw new RuntimeException("Stub!"); }
public final  int onStartCommand(android.content.Intent intent, int flags, int startId) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
protected abstract  java.lang.String onGetSummary();
protected abstract  boolean onGetEnabled();
public static final java.lang.String ACTION_INJECTED_SETTING_CHANGED = "android.location.InjectedSettingChanged";
public static final java.lang.String ACTION_SERVICE_INTENT = "android.location.SettingInjectorService";
public static final java.lang.String ATTRIBUTES_NAME = "injected-location-setting";
public static final java.lang.String META_DATA_NAME = "android.location.SettingInjectorService";
}
