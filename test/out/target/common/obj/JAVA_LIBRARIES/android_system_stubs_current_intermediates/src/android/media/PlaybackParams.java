package android.media;
public final class PlaybackParams
  implements android.os.Parcelable
{
public  PlaybackParams() { throw new RuntimeException("Stub!"); }
public  android.media.PlaybackParams allowDefaults() { throw new RuntimeException("Stub!"); }
public  android.media.PlaybackParams setAudioFallbackMode(int audioFallbackMode) { throw new RuntimeException("Stub!"); }
public  int getAudioFallbackMode() { throw new RuntimeException("Stub!"); }
public  android.media.PlaybackParams setPitch(float pitch) { throw new RuntimeException("Stub!"); }
public  float getPitch() { throw new RuntimeException("Stub!"); }
public  android.media.PlaybackParams setSpeed(float speed) { throw new RuntimeException("Stub!"); }
public  float getSpeed() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final int AUDIO_FALLBACK_MODE_DEFAULT = 0;
public static final int AUDIO_FALLBACK_MODE_FAIL = 2;
public static final int AUDIO_FALLBACK_MODE_MUTE = 1;
public static final android.os.Parcelable.Creator<android.media.PlaybackParams> CREATOR;
static { CREATOR = null; }
}
