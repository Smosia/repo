package android.media.session;
public final class MediaSessionManager
{
public static interface OnActiveSessionsChangedListener
{
public abstract  void onActiveSessionsChanged(java.util.List<android.media.session.MediaController> controllers);
}
MediaSessionManager() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.media.session.MediaController> getActiveSessions(android.content.ComponentName notificationListener) { throw new RuntimeException("Stub!"); }
public  void addOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener sessionListener, android.content.ComponentName notificationListener) { throw new RuntimeException("Stub!"); }
public  void addOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener sessionListener, android.content.ComponentName notificationListener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void removeOnActiveSessionsChangedListener(android.media.session.MediaSessionManager.OnActiveSessionsChangedListener listener) { throw new RuntimeException("Stub!"); }
}
