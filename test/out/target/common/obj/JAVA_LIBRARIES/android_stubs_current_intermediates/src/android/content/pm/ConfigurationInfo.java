package android.content.pm;
public class ConfigurationInfo
  implements android.os.Parcelable
{
public  ConfigurationInfo() { throw new RuntimeException("Stub!"); }
public  ConfigurationInfo(android.content.pm.ConfigurationInfo orig) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public  java.lang.String getGlEsVersion() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.ConfigurationInfo> CREATOR;
public static final int GL_ES_VERSION_UNDEFINED = 0;
public static final int INPUT_FEATURE_FIVE_WAY_NAV = 2;
public static final int INPUT_FEATURE_HARD_KEYBOARD = 1;
public int reqGlEsVersion;
public int reqInputFeatures;
public int reqKeyboardType;
public int reqNavigation;
public int reqTouchScreen;
static { CREATOR = null; }
}
