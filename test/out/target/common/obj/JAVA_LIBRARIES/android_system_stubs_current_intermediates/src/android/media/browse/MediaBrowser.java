package android.media.browse;
public final class MediaBrowser
{
public static class MediaItem
  implements android.os.Parcelable
{
public  MediaItem(android.media.MediaDescription description, int flags) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int getFlags() { throw new RuntimeException("Stub!"); }
public  boolean isBrowsable() { throw new RuntimeException("Stub!"); }
public  boolean isPlayable() { throw new RuntimeException("Stub!"); }
public  android.media.MediaDescription getDescription() { throw new RuntimeException("Stub!"); }
public  java.lang.String getMediaId() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.media.browse.MediaBrowser.MediaItem> CREATOR;
public static final int FLAG_BROWSABLE = 1;
public static final int FLAG_PLAYABLE = 2;
static { CREATOR = null; }
}
public static class ConnectionCallback
{
public  ConnectionCallback() { throw new RuntimeException("Stub!"); }
public  void onConnected() { throw new RuntimeException("Stub!"); }
public  void onConnectionSuspended() { throw new RuntimeException("Stub!"); }
public  void onConnectionFailed() { throw new RuntimeException("Stub!"); }
}
public abstract static class SubscriptionCallback
{
public  SubscriptionCallback() { throw new RuntimeException("Stub!"); }
public  void onChildrenLoaded(java.lang.String parentId, java.util.List<android.media.browse.MediaBrowser.MediaItem> children) { throw new RuntimeException("Stub!"); }
public  void onError(java.lang.String parentId) { throw new RuntimeException("Stub!"); }
}
public abstract static class ItemCallback
{
public  ItemCallback() { throw new RuntimeException("Stub!"); }
public  void onItemLoaded(android.media.browse.MediaBrowser.MediaItem item) { throw new RuntimeException("Stub!"); }
public  void onError(java.lang.String itemId) { throw new RuntimeException("Stub!"); }
}
public  MediaBrowser(android.content.Context context, android.content.ComponentName serviceComponent, android.media.browse.MediaBrowser.ConnectionCallback callback, android.os.Bundle rootHints) { throw new RuntimeException("Stub!"); }
public  void connect() { throw new RuntimeException("Stub!"); }
public  void disconnect() { throw new RuntimeException("Stub!"); }
public  boolean isConnected() { throw new RuntimeException("Stub!"); }
public  android.content.ComponentName getServiceComponent() { throw new RuntimeException("Stub!"); }
public  java.lang.String getRoot() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  android.media.session.MediaSession.Token getSessionToken() { throw new RuntimeException("Stub!"); }
public  void subscribe(java.lang.String parentId, android.media.browse.MediaBrowser.SubscriptionCallback callback) { throw new RuntimeException("Stub!"); }
public  void unsubscribe(java.lang.String parentId) { throw new RuntimeException("Stub!"); }
public  void getItem(java.lang.String mediaId, android.media.browse.MediaBrowser.ItemCallback cb) { throw new RuntimeException("Stub!"); }
}
