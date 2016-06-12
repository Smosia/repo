package android.service.trust;
public class TrustAgentService
  extends android.app.Service
{
public  TrustAgentService() { throw new RuntimeException("Stub!"); }
public  void onCreate() { throw new RuntimeException("Stub!"); }
public  void onUnlockAttempt(boolean successful) { throw new RuntimeException("Stub!"); }
public  void onTrustTimeout() { throw new RuntimeException("Stub!"); }
public  void onDeviceLocked() { throw new RuntimeException("Stub!"); }
public  void onDeviceUnlocked() { throw new RuntimeException("Stub!"); }
public  boolean onConfigure(java.util.List<android.os.PersistableBundle> options) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public final  void grantTrust(java.lang.CharSequence message, long durationMs, boolean initiatedByUser) { throw new RuntimeException("Stub!"); }
public final  void grantTrust(java.lang.CharSequence message, long durationMs, int flags) { throw new RuntimeException("Stub!"); }
public final  void revokeTrust() { throw new RuntimeException("Stub!"); }
public final  void setManagingTrust(boolean managingTrust) { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public static final int FLAG_GRANT_TRUST_DISMISS_KEYGUARD = 2;
public static final int FLAG_GRANT_TRUST_INITIATED_BY_USER = 1;
public static final java.lang.String SERVICE_INTERFACE = "android.service.trust.TrustAgentService";
public static final java.lang.String TRUST_AGENT_META_DATA = "android.service.trust.trustagent";
}
