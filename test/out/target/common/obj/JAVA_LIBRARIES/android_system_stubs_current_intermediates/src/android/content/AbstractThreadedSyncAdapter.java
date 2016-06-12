package android.content;
public abstract class AbstractThreadedSyncAdapter
{
public  AbstractThreadedSyncAdapter(android.content.Context context, boolean autoInitialize) { throw new RuntimeException("Stub!"); }
public  AbstractThreadedSyncAdapter(android.content.Context context, boolean autoInitialize, boolean allowParallelSyncs) { throw new RuntimeException("Stub!"); }
public  android.content.Context getContext() { throw new RuntimeException("Stub!"); }
public final  android.os.IBinder getSyncAdapterBinder() { throw new RuntimeException("Stub!"); }
public abstract  void onPerformSync(android.accounts.Account account, android.os.Bundle extras, java.lang.String authority, android.content.ContentProviderClient provider, android.content.SyncResult syncResult);
public  void onSecurityException(android.accounts.Account account, android.os.Bundle extras, java.lang.String authority, android.content.SyncResult syncResult) { throw new RuntimeException("Stub!"); }
public  void onSyncCanceled() { throw new RuntimeException("Stub!"); }
public  void onSyncCanceled(java.lang.Thread thread) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public static final int LOG_SYNC_DETAILS = 2743;
}
