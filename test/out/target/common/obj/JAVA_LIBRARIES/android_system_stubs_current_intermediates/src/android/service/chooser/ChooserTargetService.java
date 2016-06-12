package android.service.chooser;
public abstract class ChooserTargetService
  extends android.app.Service
{
public  ChooserTargetService() { throw new RuntimeException("Stub!"); }
public abstract  java.util.List<android.service.chooser.ChooserTarget> onGetChooserTargets(android.content.ComponentName targetActivityName, android.content.IntentFilter matchedFilter);
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String BIND_PERMISSION = "android.permission.BIND_CHOOSER_TARGET_SERVICE";
public static final java.lang.String META_DATA_NAME = "android.service.chooser.chooser_target_service";
public static final java.lang.String SERVICE_INTERFACE = "android.service.chooser.ChooserTargetService";
}
