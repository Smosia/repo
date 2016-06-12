package android.content.pm;
public class PackageInfo
  implements android.os.Parcelable
{
public  PackageInfo() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.PackageInfo> CREATOR;
public static final int INSTALL_LOCATION_AUTO = 0;
public static final int INSTALL_LOCATION_INTERNAL_ONLY = 1;
public static final int INSTALL_LOCATION_PREFER_EXTERNAL = 2;
public static final int REQUESTED_PERMISSION_GRANTED = 2;
public android.content.pm.ActivityInfo[] activities = null;
public android.content.pm.ApplicationInfo applicationInfo;
public int baseRevisionCode;
public android.content.pm.ConfigurationInfo[] configPreferences = null;
public android.content.pm.FeatureGroupInfo[] featureGroups = null;
public long firstInstallTime;
public int[] gids = null;
public int installLocation;
public android.content.pm.InstrumentationInfo[] instrumentation = null;
public long lastUpdateTime;
public java.lang.String packageName;
public android.content.pm.PermissionInfo[] permissions = null;
public android.content.pm.ProviderInfo[] providers = null;
public android.content.pm.ActivityInfo[] receivers = null;
public android.content.pm.FeatureInfo[] reqFeatures = null;
public java.lang.String[] requestedPermissions = null;
public int[] requestedPermissionsFlags = null;
public android.content.pm.ServiceInfo[] services = null;
public java.lang.String sharedUserId;
public int sharedUserLabel;
public android.content.pm.Signature[] signatures = null;
public java.lang.String[] splitNames = null;
public int[] splitRevisionCodes = null;
public int versionCode;
public java.lang.String versionName;
static { CREATOR = null; }
}
