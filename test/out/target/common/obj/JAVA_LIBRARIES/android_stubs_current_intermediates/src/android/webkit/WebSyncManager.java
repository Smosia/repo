package android.webkit;
@java.lang.Deprecated()
 abstract class WebSyncManager
  implements java.lang.Runnable
{
protected  WebSyncManager(android.content.Context context, java.lang.String name) { throw new RuntimeException("Stub!"); }
protected  java.lang.Object clone() throws java.lang.CloneNotSupportedException { throw new RuntimeException("Stub!"); }
public  void run() { throw new RuntimeException("Stub!"); }
public  void sync() { throw new RuntimeException("Stub!"); }
public  void resetSync() { throw new RuntimeException("Stub!"); }
public  void startSync() { throw new RuntimeException("Stub!"); }
public  void stopSync() { throw new RuntimeException("Stub!"); }
protected  void onSyncInit() { throw new RuntimeException("Stub!"); }
protected static final java.lang.String LOGTAG = "websync";
protected android.webkit.WebViewDatabase mDataBase;
protected android.os.Handler mHandler;
}
