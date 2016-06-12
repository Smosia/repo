package android.service.notification;
public abstract class ConditionProviderService
  extends android.app.Service
{
public  ConditionProviderService() { throw new RuntimeException("Stub!"); }
public abstract  void onConnected();
public abstract  void onRequestConditions(int relevance);
public abstract  void onSubscribe(android.net.Uri conditionId);
public abstract  void onUnsubscribe(android.net.Uri conditionId);
public final  void notifyCondition(android.service.notification.Condition condition) { throw new RuntimeException("Stub!"); }
public final  void notifyConditions(android.service.notification.Condition... conditions) { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final java.lang.String SERVICE_INTERFACE = "android.service.notification.ConditionProviderService";
}
