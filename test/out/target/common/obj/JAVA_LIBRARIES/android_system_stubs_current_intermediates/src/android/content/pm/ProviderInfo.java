package android.content.pm;
public final class ProviderInfo
  extends android.content.pm.ComponentInfo
  implements android.os.Parcelable
{
public  ProviderInfo() { throw new RuntimeException("Stub!"); }
public  ProviderInfo(android.content.pm.ProviderInfo orig) { throw new RuntimeException("Stub!"); }
public  void dump(android.util.Printer pw, java.lang.String prefix) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.ProviderInfo> CREATOR;
public static final int FLAG_SINGLE_USER = 1073741824;
public java.lang.String authority;
public int flags;
public boolean grantUriPermissions;
public int initOrder;
@java.lang.Deprecated()
public boolean isSyncable;
public boolean multiprocess;
public android.content.pm.PathPermission[] pathPermissions = null;
public java.lang.String readPermission;
public android.os.PatternMatcher[] uriPermissionPatterns = null;
public java.lang.String writePermission;
static { CREATOR = null; }
}
