package android.media;
public final class MediaExtractor
{
public  MediaExtractor() { throw new RuntimeException("Stub!"); }
public final native  void setDataSource(android.media.MediaDataSource dataSource) throws java.io.IOException;
public final  void setDataSource(android.content.Context context, android.net.Uri uri, java.util.Map<java.lang.String, java.lang.String> headers) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public final  void setDataSource(java.lang.String path, java.util.Map<java.lang.String, java.lang.String> headers) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public final  void setDataSource(java.lang.String path) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public final  void setDataSource(java.io.FileDescriptor fd) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public final native  void setDataSource(java.io.FileDescriptor fd, long offset, long length) throws java.io.IOException;
protected  void finalize() { throw new RuntimeException("Stub!"); }
public final native  void release();
public final native  int getTrackCount();
public  java.util.Map<java.util.UUID, byte[]> getPsshInfo() { throw new RuntimeException("Stub!"); }
public  android.media.MediaFormat getTrackFormat(int index) { throw new RuntimeException("Stub!"); }
public native  void selectTrack(int index);
public native  void unselectTrack(int index);
public native  void seekTo(long timeUs, int mode);
public native  boolean advance();
public native  int readSampleData(java.nio.ByteBuffer byteBuf, int offset);
public native  int getSampleTrackIndex();
public native  long getSampleTime();
public native  int getSampleFlags();
public native  boolean getSampleCryptoInfo(android.media.MediaCodec.CryptoInfo info);
public native  long getCachedDuration();
public native  boolean hasCacheReachedEndOfStream();
public static final int SAMPLE_FLAG_ENCRYPTED = 2;
public static final int SAMPLE_FLAG_SYNC = 1;
public static final int SEEK_TO_CLOSEST_SYNC = 2;
public static final int SEEK_TO_NEXT_SYNC = 1;
public static final int SEEK_TO_PREVIOUS_SYNC = 0;
}
