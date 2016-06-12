package android.media;
public class AudioTrack
{
public static class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack.Builder setAudioAttributes(android.media.AudioAttributes attributes) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack.Builder setAudioFormat(android.media.AudioFormat format) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack.Builder setBufferSizeInBytes(int bufferSizeInBytes) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack.Builder setTransferMode(int mode) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack.Builder setSessionId(int sessionId) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  android.media.AudioTrack build() throws java.lang.UnsupportedOperationException { throw new RuntimeException("Stub!"); }
}
public static interface OnRoutingChangedListener
{
public abstract  void onRoutingChanged(android.media.AudioTrack audioTrack);
}
public static interface OnPlaybackPositionUpdateListener
{
public abstract  void onMarkerReached(android.media.AudioTrack track);
public abstract  void onPeriodicNotification(android.media.AudioTrack track);
}
public  AudioTrack(int streamType, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes, int mode) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  AudioTrack(int streamType, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes, int mode, int sessionId) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  AudioTrack(android.media.AudioAttributes attributes, android.media.AudioFormat format, int bufferSizeInBytes, int mode, int sessionId) throws java.lang.IllegalArgumentException { throw new RuntimeException("Stub!"); }
public  void release() { throw new RuntimeException("Stub!"); }
protected  void finalize() { throw new RuntimeException("Stub!"); }
public static  float getMinVolume() { throw new RuntimeException("Stub!"); }
public static  float getMaxVolume() { throw new RuntimeException("Stub!"); }
public  int getSampleRate() { throw new RuntimeException("Stub!"); }
public  int getPlaybackRate() { throw new RuntimeException("Stub!"); }
public  android.media.PlaybackParams getPlaybackParams() { throw new RuntimeException("Stub!"); }
public  int getAudioFormat() { throw new RuntimeException("Stub!"); }
public  int getStreamType() { throw new RuntimeException("Stub!"); }
public  int getChannelConfiguration() { throw new RuntimeException("Stub!"); }
public  android.media.AudioFormat getFormat() { throw new RuntimeException("Stub!"); }
public  int getChannelCount() { throw new RuntimeException("Stub!"); }
public  int getState() { throw new RuntimeException("Stub!"); }
public  int getPlayState() { throw new RuntimeException("Stub!"); }
public  int getBufferSizeInFrames() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
protected  int getNativeFrameCount() { throw new RuntimeException("Stub!"); }
public  int getNotificationMarkerPosition() { throw new RuntimeException("Stub!"); }
public  int getPositionNotificationPeriod() { throw new RuntimeException("Stub!"); }
public  int getPlaybackHeadPosition() { throw new RuntimeException("Stub!"); }
public static  int getNativeOutputSampleRate(int streamType) { throw new RuntimeException("Stub!"); }
public static  int getMinBufferSize(int sampleRateInHz, int channelConfig, int audioFormat) { throw new RuntimeException("Stub!"); }
public  int getAudioSessionId() { throw new RuntimeException("Stub!"); }
public  boolean getTimestamp(android.media.AudioTimestamp timestamp) { throw new RuntimeException("Stub!"); }
public  void setPlaybackPositionUpdateListener(android.media.AudioTrack.OnPlaybackPositionUpdateListener listener) { throw new RuntimeException("Stub!"); }
public  void setPlaybackPositionUpdateListener(android.media.AudioTrack.OnPlaybackPositionUpdateListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
@Deprecated
public  int setStereoVolume(float leftGain, float rightGain) { throw new RuntimeException("Stub!"); }
public  int setVolume(float gain) { throw new RuntimeException("Stub!"); }
public  int setPlaybackRate(int sampleRateInHz) { throw new RuntimeException("Stub!"); }
public  void setPlaybackParams(android.media.PlaybackParams params) { throw new RuntimeException("Stub!"); }
public  int setNotificationMarkerPosition(int markerInFrames) { throw new RuntimeException("Stub!"); }
public  int setPositionNotificationPeriod(int periodInFrames) { throw new RuntimeException("Stub!"); }
public  int setPlaybackHeadPosition(int positionInFrames) { throw new RuntimeException("Stub!"); }
public  int setLoopPoints(int startInFrames, int endInFrames, int loopCount) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
protected  void setState(int state) { throw new RuntimeException("Stub!"); }
public  void play() throws java.lang.IllegalStateException { throw new RuntimeException("Stub!"); }
public  void stop() throws java.lang.IllegalStateException { throw new RuntimeException("Stub!"); }
public  void pause() throws java.lang.IllegalStateException { throw new RuntimeException("Stub!"); }
public  void flush() { throw new RuntimeException("Stub!"); }
public  int write(byte[] audioData, int offsetInBytes, int sizeInBytes) { throw new RuntimeException("Stub!"); }
public  int write(byte[] audioData, int offsetInBytes, int sizeInBytes, int writeMode) { throw new RuntimeException("Stub!"); }
public  int write(short[] audioData, int offsetInShorts, int sizeInShorts) { throw new RuntimeException("Stub!"); }
public  int write(short[] audioData, int offsetInShorts, int sizeInShorts, int writeMode) { throw new RuntimeException("Stub!"); }
public  int write(float[] audioData, int offsetInFloats, int sizeInFloats, int writeMode) { throw new RuntimeException("Stub!"); }
public  int write(java.nio.ByteBuffer audioData, int sizeInBytes, int writeMode) { throw new RuntimeException("Stub!"); }
public  int write(java.nio.ByteBuffer audioData, int sizeInBytes, int writeMode, long timestamp) { throw new RuntimeException("Stub!"); }
public  int reloadStaticData() { throw new RuntimeException("Stub!"); }
public  int attachAuxEffect(int effectId) { throw new RuntimeException("Stub!"); }
public  int setAuxEffectSendLevel(float level) { throw new RuntimeException("Stub!"); }
public  boolean setPreferredDevice(android.media.AudioDeviceInfo deviceInfo) { throw new RuntimeException("Stub!"); }
public  android.media.AudioDeviceInfo getPreferredDevice() { throw new RuntimeException("Stub!"); }
public  android.media.AudioDeviceInfo getRoutedDevice() { throw new RuntimeException("Stub!"); }
public  void addOnRoutingChangedListener(android.media.AudioTrack.OnRoutingChangedListener listener, android.os.Handler handler) { throw new RuntimeException("Stub!"); }
public  void removeOnRoutingChangedListener(android.media.AudioTrack.OnRoutingChangedListener listener) { throw new RuntimeException("Stub!"); }
public static final int ERROR = -1;
public static final int ERROR_BAD_VALUE = -2;
public static final int ERROR_INVALID_OPERATION = -3;
public static final int MODE_STATIC = 0;
public static final int MODE_STREAM = 1;
public static final int PLAYSTATE_PAUSED = 2;
public static final int PLAYSTATE_PLAYING = 3;
public static final int PLAYSTATE_STOPPED = 1;
public static final int STATE_INITIALIZED = 1;
public static final int STATE_NO_STATIC_DATA = 2;
public static final int STATE_UNINITIALIZED = 0;
public static final int SUCCESS = 0;
public static final int WRITE_BLOCKING = 0;
public static final int WRITE_NON_BLOCKING = 1;
}
