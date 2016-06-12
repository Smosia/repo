package android.hardware.radio;
public final class RadioMetadata
  implements android.os.Parcelable
{
public static final class Builder
{
public  Builder() { throw new RuntimeException("Stub!"); }
public  Builder(android.hardware.radio.RadioMetadata source) { throw new RuntimeException("Stub!"); }
public  android.hardware.radio.RadioMetadata.Builder putString(java.lang.String key, java.lang.String value) { throw new RuntimeException("Stub!"); }
public  android.hardware.radio.RadioMetadata.Builder putInt(java.lang.String key, int value) { throw new RuntimeException("Stub!"); }
public  android.hardware.radio.RadioMetadata.Builder putBitmap(java.lang.String key, android.graphics.Bitmap value) { throw new RuntimeException("Stub!"); }
public  android.hardware.radio.RadioMetadata build() { throw new RuntimeException("Stub!"); }
}
RadioMetadata() { throw new RuntimeException("Stub!"); }
public  boolean containsKey(java.lang.String key) { throw new RuntimeException("Stub!"); }
public  java.lang.String getString(java.lang.String key) { throw new RuntimeException("Stub!"); }
public  int getInt(java.lang.String key) { throw new RuntimeException("Stub!"); }
public  android.graphics.Bitmap getBitmap(java.lang.String key) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  int size() { throw new RuntimeException("Stub!"); }
public  java.util.Set<java.lang.String> keySet() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.hardware.radio.RadioMetadata> CREATOR;
public static final java.lang.String METADATA_KEY_ALBUM = "android.hardware.radio.metadata.ALBUM";
public static final java.lang.String METADATA_KEY_ART = "android.hardware.radio.metadata.ART";
public static final java.lang.String METADATA_KEY_ARTIST = "android.hardware.radio.metadata.ARTIST";
public static final java.lang.String METADATA_KEY_GENRE = "android.hardware.radio.metadata.GENRE";
public static final java.lang.String METADATA_KEY_ICON = "android.hardware.radio.metadata.ICON";
public static final java.lang.String METADATA_KEY_RBDS_PTY = "android.hardware.radio.metadata.RBDS_PTY";
public static final java.lang.String METADATA_KEY_RDS_PI = "android.hardware.radio.metadata.RDS_PI";
public static final java.lang.String METADATA_KEY_RDS_PS = "android.hardware.radio.metadata.RDS_PS";
public static final java.lang.String METADATA_KEY_RDS_PTY = "android.hardware.radio.metadata.RDS_PTY";
public static final java.lang.String METADATA_KEY_RDS_RT = "android.hardware.radio.metadata.RDS_RT";
public static final java.lang.String METADATA_KEY_TITLE = "android.hardware.radio.metadata.TITLE";
static { CREATOR = null; }
}
