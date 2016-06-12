package android.media;
@java.lang.Deprecated()
public final class RemoteController
{
public static interface OnClientUpdateListener
{
public abstract  void onClientChange(boolean clearing);
public abstract  void onClientPlaybackStateUpdate(int state);
public abstract  void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed);
public abstract  void onClientTransportControlUpdate(int transportControlFlags);
public abstract  void onClientMetadataUpdate(android.media.RemoteController.MetadataEditor metadataEditor);
}
public class MetadataEditor
  extends android.media.MediaMetadataEditor
{
MetadataEditor() { throw new RuntimeException("Stub!"); }
public synchronized  void apply() { throw new RuntimeException("Stub!"); }
}
public  RemoteController(android.content.Context context, android.media.RemoteController.OnClientUpdateListener updateListener) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  RemoteController(android.content.Context context, android.media.RemoteController.OnClientUpdateListener updateListener, android.os.Looper looper) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  long getEstimatedMediaPosition() { throw new RuntimeException("Stub!"); }
public  boolean sendMediaKeyEvent(android.view.KeyEvent keyEvent) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  boolean seekTo(long timeMs) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  boolean setArtworkConfiguration(int width, int height) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  boolean clearArtworkConfiguration() { throw new RuntimeException("Stub!"); }
public  boolean setSynchronizationMode(int sync) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.RemoteController.MetadataEditor editMetadata() { throw new RuntimeException("Stub!"); }
public static final int POSITION_SYNCHRONIZATION_CHECK = 1;
public static final int POSITION_SYNCHRONIZATION_NONE = 0;
}
