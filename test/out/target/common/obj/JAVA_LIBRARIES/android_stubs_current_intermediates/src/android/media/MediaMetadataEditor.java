package android.media;
@java.lang.Deprecated()
public abstract class MediaMetadataEditor
{
MediaMetadataEditor() { throw new RuntimeException("Stub!"); }
public abstract  void apply();
public synchronized  void clear() { throw new RuntimeException("Stub!"); }
public synchronized  void addEditableKey(int key) { throw new RuntimeException("Stub!"); }
public synchronized  void removeEditableKeys() { throw new RuntimeException("Stub!"); }
public synchronized  int[] getEditableKeys() { throw new RuntimeException("Stub!"); }
public synchronized  android.media.MediaMetadataEditor putString(int key, java.lang.String value) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  android.media.MediaMetadataEditor putLong(int key, long value) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  android.media.MediaMetadataEditor putBitmap(int key, android.graphics.Bitmap bitmap) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  android.media.MediaMetadataEditor putObject(int key, java.lang.Object value) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  long getLong(int key, long defaultValue) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  java.lang.String getString(int key, java.lang.String defaultValue) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  android.graphics.Bitmap getBitmap(int key, android.graphics.Bitmap defaultValue) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public synchronized  java.lang.Object getObject(int key, java.lang.Object defaultValue) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public static final int BITMAP_KEY_ARTWORK = 100;
public static final int RATING_KEY_BY_OTHERS = 101;
public static final int RATING_KEY_BY_USER = 268435457;
}
