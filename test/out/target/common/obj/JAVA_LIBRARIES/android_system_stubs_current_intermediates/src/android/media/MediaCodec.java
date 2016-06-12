package android.media;
public final class MediaCodec
{
public static final class BufferInfo
{
public  BufferInfo() { throw new RuntimeException("Stub!"); }
public  void set(int newOffset, int newSize, long newTimeUs, int newFlags) { throw new RuntimeException("Stub!"); }
public int flags;
public int offset;
public long presentationTimeUs;
public int size;
}
public static final class CodecException
  extends java.lang.IllegalStateException
{
CodecException() { throw new RuntimeException("Stub!"); }
public  boolean isTransient() { throw new RuntimeException("Stub!"); }
public  boolean isRecoverable() { throw new RuntimeException("Stub!"); }
public  int getErrorCode() { throw new RuntimeException("Stub!"); }
public  java.lang.String getDiagnosticInfo() { throw new RuntimeException("Stub!"); }
public static final int ERROR_INSUFFICIENT_RESOURCE = 1100;
public static final int ERROR_RECLAIMED = 1101;
}
public static final class CryptoException
  extends java.lang.RuntimeException
{
public  CryptoException(int errorCode, java.lang.String detailMessage) { throw new RuntimeException("Stub!"); }
public  int getErrorCode() { throw new RuntimeException("Stub!"); }
public static final int ERROR_INSUFFICIENT_OUTPUT_PROTECTION = 4;
public static final int ERROR_KEY_EXPIRED = 2;
public static final int ERROR_NO_KEY = 1;
public static final int ERROR_RESOURCE_BUSY = 3;
public static final int ERROR_SESSION_NOT_OPENED = 5;
}
public static final class CryptoInfo
{
public  CryptoInfo() { throw new RuntimeException("Stub!"); }
public  void set(int newNumSubSamples, int[] newNumBytesOfClearData, int[] newNumBytesOfEncryptedData, byte[] newKey, byte[] newIV, int newMode) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public byte[] iv = null;
public byte[] key = null;
public int mode;
public int[] numBytesOfClearData = null;
public int[] numBytesOfEncryptedData = null;
public int numSubSamples;
}
public static interface OnFrameRenderedListener
{
public abstract  void onFrameRendered(android.media.MediaCodec codec, long presentationTimeUs, long nanoTime);
}
public abstract static class Callback
{
public  Callback() { throw new RuntimeException("Stub!"); }
public abstract  void onInputBufferAvailable(android.media.MediaCodec codec, int index);
public abstract  void onOutputBufferAvailable(android.media.MediaCodec codec, int index, android.media.MediaCodec.BufferInfo info);
public abstract  void onError(android.media.MediaCodec codec, android.media.MediaCodec.CodecException e);
public abstract  void onOutputFormatChanged(android.media.MediaCodec codec, android.media.MediaFormat format);
}
MediaCodec() { throw new RuntimeException("Stub!"); }
public static  android.media.MediaCodec createDecoderByType(java.lang.String type) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public static  android.media.MediaCodec createEncoderByType(java.lang.String type) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public static  android.media.MediaCodec createByCodecName(java.lang.String name) throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected  void finalize() { throw new RuntimeException("Stub!"); }
public final  void reset() { throw new RuntimeException("Stub!"); }
public final  void release() { throw new RuntimeException("Stub!"); }
public  void configure(android.media.MediaFormat format, android.view.Surface surface, android.media.MediaCrypto crypto, int flags) { throw new RuntimeException("Stub!"); }
public  void setOutputSurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public static  android.view.Surface createPersistentInputSurface() { throw new RuntimeException("Stub!"); }
public  void setInputSurface(android.view.Surface surface) { throw new RuntimeException("Stub!"); }
public final native  android.view.Surface createInputSurface();
public final  void start() { throw new RuntimeException("Stub!"); }
public final  void stop() { throw new RuntimeException("Stub!"); }
public final  void flush() { throw new RuntimeException("Stub!"); }
public final  void queueInputBuffer(int index, int offset, int size, long presentationTimeUs, int flags) throws android.media.MediaCodec.CryptoException { throw new RuntimeException("Stub!"); }
public final  void queueSecureInputBuffer(int index, int offset, android.media.MediaCodec.CryptoInfo info, long presentationTimeUs, int flags) throws android.media.MediaCodec.CryptoException { throw new RuntimeException("Stub!"); }
public final  int dequeueInputBuffer(long timeoutUs) { throw new RuntimeException("Stub!"); }
public final  int dequeueOutputBuffer(android.media.MediaCodec.BufferInfo info, long timeoutUs) { throw new RuntimeException("Stub!"); }
public final  void releaseOutputBuffer(int index, boolean render) { throw new RuntimeException("Stub!"); }
public final  void releaseOutputBuffer(int index, long renderTimestampNs) { throw new RuntimeException("Stub!"); }
public final native  void signalEndOfInputStream();
public final  android.media.MediaFormat getOutputFormat() { throw new RuntimeException("Stub!"); }
public final  android.media.MediaFormat getInputFormat() { throw new RuntimeException("Stub!"); }
public final  android.media.MediaFormat getOutputFormat(int index) { throw new RuntimeException("Stub!"); }
@Deprecated
public  java.nio.ByteBuffer[] getInputBuffers() { throw new RuntimeException("Stub!"); }
@Deprecated
public  java.nio.ByteBuffer[] getOutputBuffers() { throw new RuntimeException("Stub!"); }
public  java.nio.ByteBuffer getInputBuffer(int index) { throw new RuntimeException("Stub!"); }
public  android.media.Image getInputImage(int index) { throw new RuntimeException("Stub!"); }
public  java.nio.ByteBuffer getOutputBuffer(int index) { throw new RuntimeException("Stub!"); }
public  android.media.Image getOutputImage(int index) { throw new RuntimeException("Stub!"); }
public final native  void setVideoScalingMode(int mode);
public final native  java.lang.String getName();
public final  void setParameters(android.os.Bundle params) { throw new RuntimeException("Stub!"); }
public  void setCallback(android.media.MediaCodec.Callback cb, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void setCallback(android.media.MediaCodec.Callback cb) { throw new RuntimeException("Stub!"); }
public  void setOnFrameRenderedListener(android.media.MediaCodec.OnFrameRenderedListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  android.media.MediaCodecInfo getCodecInfo() { throw new RuntimeException("Stub!"); }
public static final int BUFFER_FLAG_CODEC_CONFIG = 2;
public static final int BUFFER_FLAG_END_OF_STREAM = 4;
public static final int BUFFER_FLAG_KEY_FRAME = 1;
@Deprecated
public static final int BUFFER_FLAG_SYNC_FRAME = 1;
public static final int CONFIGURE_FLAG_ENCODE = 1;
public static final int CRYPTO_MODE_AES_CTR = 1;
public static final int CRYPTO_MODE_UNENCRYPTED = 0;
@Deprecated
public static final int INFO_OUTPUT_BUFFERS_CHANGED = -3;
public static final int INFO_OUTPUT_FORMAT_CHANGED = -2;
public static final int INFO_TRY_AGAIN_LATER = -1;
public static final java.lang.String PARAMETER_KEY_REQUEST_SYNC_FRAME = "request-sync";
public static final java.lang.String PARAMETER_KEY_SUSPEND = "drop-input-frames";
public static final java.lang.String PARAMETER_KEY_VIDEO_BITRATE = "video-bitrate";
public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
}
