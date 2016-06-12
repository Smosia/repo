package android.media;
public final class MediaFormat
{
public  MediaFormat() { throw new RuntimeException("Stub!"); }
public final  boolean containsKey(java.lang.String name) { throw new RuntimeException("Stub!"); }
public final  int getInteger(java.lang.String name) { throw new RuntimeException("Stub!"); }
public final  long getLong(java.lang.String name) { throw new RuntimeException("Stub!"); }
public final  float getFloat(java.lang.String name) { throw new RuntimeException("Stub!"); }
public final  java.lang.String getString(java.lang.String name) { throw new RuntimeException("Stub!"); }
public final  java.nio.ByteBuffer getByteBuffer(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  boolean getFeatureEnabled(java.lang.String feature) { throw new RuntimeException("Stub!"); }
public final  void setInteger(java.lang.String name, int value) { throw new RuntimeException("Stub!"); }
public final  void setLong(java.lang.String name, long value) { throw new RuntimeException("Stub!"); }
public final  void setFloat(java.lang.String name, float value) { throw new RuntimeException("Stub!"); }
public final  void setString(java.lang.String name, java.lang.String value) { throw new RuntimeException("Stub!"); }
public final  void setByteBuffer(java.lang.String name, java.nio.ByteBuffer bytes) { throw new RuntimeException("Stub!"); }
public  void setFeatureEnabled(java.lang.String feature, boolean enabled) { throw new RuntimeException("Stub!"); }
public static final  android.media.MediaFormat createAudioFormat(java.lang.String mime, int sampleRate, int channelCount) { throw new RuntimeException("Stub!"); }
public static final  android.media.MediaFormat createSubtitleFormat(java.lang.String mime, java.lang.String language) { throw new RuntimeException("Stub!"); }
public static final  android.media.MediaFormat createVideoFormat(java.lang.String mime, int width, int height) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final java.lang.String KEY_AAC_DRC_ATTENUATION_FACTOR = "aac-drc-cut-level";
public static final java.lang.String KEY_AAC_DRC_BOOST_FACTOR = "aac-drc-boost-level";
public static final java.lang.String KEY_AAC_DRC_HEAVY_COMPRESSION = "aac-drc-heavy-compression";
public static final java.lang.String KEY_AAC_DRC_TARGET_REFERENCE_LEVEL = "aac-target-ref-level";
public static final java.lang.String KEY_AAC_ENCODED_TARGET_LEVEL = "aac-encoded-target-level";
public static final java.lang.String KEY_AAC_MAX_OUTPUT_CHANNEL_COUNT = "aac-max-output-channel_count";
public static final java.lang.String KEY_AAC_PROFILE = "aac-profile";
public static final java.lang.String KEY_AAC_SBR_MODE = "aac-sbr-mode";
public static final java.lang.String KEY_AUDIO_SESSION_ID = "audio-session-id";
public static final java.lang.String KEY_BITRATE_MODE = "bitrate-mode";
public static final java.lang.String KEY_BIT_RATE = "bitrate";
public static final java.lang.String KEY_CAPTURE_RATE = "capture-rate";
public static final java.lang.String KEY_CHANNEL_COUNT = "channel-count";
public static final java.lang.String KEY_CHANNEL_MASK = "channel-mask";
public static final java.lang.String KEY_COLOR_FORMAT = "color-format";
public static final java.lang.String KEY_COMPLEXITY = "complexity";
public static final java.lang.String KEY_DURATION = "durationUs";
public static final java.lang.String KEY_FLAC_COMPRESSION_LEVEL = "flac-compression-level";
public static final java.lang.String KEY_FRAME_RATE = "frame-rate";
public static final java.lang.String KEY_HEIGHT = "height";
public static final java.lang.String KEY_IS_ADTS = "is-adts";
public static final java.lang.String KEY_IS_AUTOSELECT = "is-autoselect";
public static final java.lang.String KEY_IS_DEFAULT = "is-default";
public static final java.lang.String KEY_IS_FORCED_SUBTITLE = "is-forced-subtitle";
public static final java.lang.String KEY_I_FRAME_INTERVAL = "i-frame-interval";
public static final java.lang.String KEY_LANGUAGE = "language";
public static final java.lang.String KEY_LEVEL = "level";
public static final java.lang.String KEY_MAX_HEIGHT = "max-height";
public static final java.lang.String KEY_MAX_INPUT_SIZE = "max-input-size";
public static final java.lang.String KEY_MAX_WIDTH = "max-width";
public static final java.lang.String KEY_MIME = "mime";
public static final java.lang.String KEY_OPERATING_RATE = "operating-rate";
public static final java.lang.String KEY_PRIORITY = "priority";
public static final java.lang.String KEY_PROFILE = "profile";
public static final java.lang.String KEY_PUSH_BLANK_BUFFERS_ON_STOP = "push-blank-buffers-on-shutdown";
public static final java.lang.String KEY_REPEAT_PREVIOUS_FRAME_AFTER = "repeat-previous-frame-after";
public static final java.lang.String KEY_ROTATION = "rotation-degrees";
public static final java.lang.String KEY_SAMPLE_RATE = "sample-rate";
public static final java.lang.String KEY_SLICE_HEIGHT = "slice-height";
public static final java.lang.String KEY_STRIDE = "stride";
public static final java.lang.String KEY_TEMPORAL_LAYERING = "ts-schema";
public static final java.lang.String KEY_WIDTH = "width";
public static final java.lang.String MIMETYPE_AUDIO_AAC = "audio/mp4a-latm";
public static final java.lang.String MIMETYPE_AUDIO_AC3 = "audio/ac3";
public static final java.lang.String MIMETYPE_AUDIO_AMR_NB = "audio/3gpp";
public static final java.lang.String MIMETYPE_AUDIO_AMR_WB = "audio/amr-wb";
public static final java.lang.String MIMETYPE_AUDIO_EAC3 = "audio/eac3";
public static final java.lang.String MIMETYPE_AUDIO_FLAC = "audio/flac";
public static final java.lang.String MIMETYPE_AUDIO_G711_ALAW = "audio/g711-alaw";
public static final java.lang.String MIMETYPE_AUDIO_G711_MLAW = "audio/g711-mlaw";
public static final java.lang.String MIMETYPE_AUDIO_MPEG = "audio/mpeg";
public static final java.lang.String MIMETYPE_AUDIO_MSGSM = "audio/gsm";
public static final java.lang.String MIMETYPE_AUDIO_OPUS = "audio/opus";
public static final java.lang.String MIMETYPE_AUDIO_QCELP = "audio/qcelp";
public static final java.lang.String MIMETYPE_AUDIO_RAW = "audio/raw";
public static final java.lang.String MIMETYPE_AUDIO_VORBIS = "audio/vorbis";
public static final java.lang.String MIMETYPE_TEXT_CEA_608 = "text/cea-608";
public static final java.lang.String MIMETYPE_TEXT_VTT = "text/vtt";
public static final java.lang.String MIMETYPE_VIDEO_AVC = "video/avc";
public static final java.lang.String MIMETYPE_VIDEO_H263 = "video/3gpp";
public static final java.lang.String MIMETYPE_VIDEO_HEVC = "video/hevc";
public static final java.lang.String MIMETYPE_VIDEO_MPEG2 = "video/mpeg2";
public static final java.lang.String MIMETYPE_VIDEO_MPEG4 = "video/mp4v-es";
public static final java.lang.String MIMETYPE_VIDEO_RAW = "video/raw";
public static final java.lang.String MIMETYPE_VIDEO_VP8 = "video/x-vnd.on2.vp8";
public static final java.lang.String MIMETYPE_VIDEO_VP9 = "video/x-vnd.on2.vp9";
}
