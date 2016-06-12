package android.media.tv;
public final class TvTrackInfo
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder(int type, java.lang.String id) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setLanguage(java.lang.String language) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setDescription(java.lang.CharSequence description) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setAudioChannelCount(int audioChannelCount) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setAudioSampleRate(int audioSampleRate) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setVideoWidth(int videoWidth) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setVideoHeight(int videoHeight) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setVideoFrameRate(float videoFrameRate) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setVideoPixelAspectRatio(float videoPixelAspectRatio) { throw new RuntimeException("Stub!"); }
public final  android.media.tv.TvTrackInfo.Builder setExtra(android.os.Bundle extra) { throw new RuntimeException("Stub!"); }
public  android.media.tv.TvTrackInfo build() { throw new RuntimeException("Stub!"); }
}
TvTrackInfo() { throw new RuntimeException("Stub!"); }
public final  int getType() { throw new RuntimeException("Stub!"); }
public final  java.lang.String getId() { throw new RuntimeException("Stub!"); }
public final  java.lang.String getLanguage() { throw new RuntimeException("Stub!"); }
public final  java.lang.CharSequence getDescription() { throw new RuntimeException("Stub!"); }
public final  int getAudioChannelCount() { throw new RuntimeException("Stub!"); }
public final  int getAudioSampleRate() { throw new RuntimeException("Stub!"); }
public final  int getVideoWidth() { throw new RuntimeException("Stub!"); }
public final  int getVideoHeight() { throw new RuntimeException("Stub!"); }
public final  float getVideoFrameRate() { throw new RuntimeException("Stub!"); }
public final  float getVideoPixelAspectRatio() { throw new RuntimeException("Stub!"); }
public final  android.os.Bundle getExtra() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.media.tv.TvTrackInfo> CREATOR;
public static final int TYPE_AUDIO = 0;
public static final int TYPE_SUBTITLE = 2;
public static final int TYPE_VIDEO = 1;
static { CREATOR = null; }
}
