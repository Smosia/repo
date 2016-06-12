package android.service.restrictions;
public abstract class RestrictionsReceiver
  extends android.content.BroadcastReceiver
{
public  RestrictionsReceiver() { throw new RuntimeException("Stub!"); }
public abstract  void onRequestPermission(android.content.Context context, java.lang.String packageName, java.lang.String requestType, java.lang.String requestId, android.os.PersistableBundle request);
public  void onReceive(android.content.Context context, android.content.Intent intent) { throw new RuntimeException("Stub!"); }
}
