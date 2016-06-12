package android.service.media;
public abstract class MediaBrowserService
  extends android.app.Service
{
public class Result<T>
{
Result() { throw new RuntimeException("Stub!"); }
public  void sendResult(T result) { throw new RuntimeException("Stub!"); }
public  void detach() { throw new RuntimeException("Stub!"); }
}
public static final class BrowserRoot
{
public  BrowserRoot(java.lang.String rootId, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  java.lang.String getRootId() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
}
public  MediaBrowserService() { throw new RuntimeException("Stub!"); }
public  void onCreate() { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void dump(java.io.FileDescriptor fd, java.io.PrintWriter writer, java.lang.String[] args) { throw new RuntimeException("Stub!"); }
public abstract  android.service.media.MediaBrowserService.BrowserRoot onGetRoot(java.lang.String clientPackageName, int clientUid, android.os.Bundle rootHints);
public abstract  void onLoadChildren(java.lang.String parentId, android.service.media.MediaBrowserService.Result<java.util.List<android.media.browse.MediaBrowser.MediaItem>> result);
public  void onLoadItem(java.lang.String itemId, android.service.media.MediaBrowserService.Result<android.media.browse.MediaBrowser.MediaItem> result) { throw new RuntimeException("Stub!"); }
public  void setSessionToken(android.media.session.MediaSession.Token token) { throw new RuntimeException("Stub!"); }
public  android.media.session.MediaSession.Token getSessionToken() { throw new RuntimeException("Stub!"); }
public  void notifyChildrenChanged(java.lang.String parentId) { throw new RuntimeException("Stub!"); }
public static final java.lang.String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
}
