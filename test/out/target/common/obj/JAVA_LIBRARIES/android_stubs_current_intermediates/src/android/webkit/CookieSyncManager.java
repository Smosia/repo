package android.webkit;
@java.lang.Deprecated()
public final class CookieSyncManager
  extends android.webkit.WebSyncManager
{
CookieSyncManager() { super((android.content.Context)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public static synchronized  android.webkit.CookieSyncManager getInstance() { throw new RuntimeException("Stub!"); }
public static synchronized  android.webkit.CookieSyncManager createInstance(android.content.Context context) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void sync() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
protected  void syncFromRamToFlash() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void resetSync() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void startSync() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void stopSync() { throw new RuntimeException("Stub!"); }
protected static final java.lang.String LOGTAG = "websync";
protected android.webkit.WebViewDatabase mDataBase;
protected android.os.Handler mHandler;
}
