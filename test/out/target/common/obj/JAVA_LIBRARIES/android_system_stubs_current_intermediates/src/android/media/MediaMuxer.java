package android.media;
public final class MediaMuxer
{
public static final class OutputFormat
{
OutputFormat() { throw new RuntimeException("Stub!"); }
public static final int MUXER_OUTPUT_MPEG_4 = 0;
public static final int MUXER_OUTPUT_WEBM = 1;
}
public  MediaMuxer(java.lang.String path, int format) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void setOrientationHint(int degrees) { throw new RuntimeException("Stub!"); }
public  void setLocation(float latitude, float longitude) { throw new RuntimeException("Stub!"); }
public  void start() { throw new RuntimeException("Stub!"); }
public  void stop() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public  int addTrack(android.media.MediaFormat format) { throw new RuntimeException("Stub!"); }
public  void writeSampleData(int trackIndex, java.nio.ByteBuffer byteBuf, android.media.MediaCodec.BufferInfo bufferInfo) { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
}
