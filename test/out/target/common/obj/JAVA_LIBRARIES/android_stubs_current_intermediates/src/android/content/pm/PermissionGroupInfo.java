package android.content.pm;
public class PermissionGroupInfo
  extends android.content.pm.PackageItemInfo
  implements android.os.Parcelable
{
public  PermissionGroupInfo() { throw new RuntimeException("Stub!"); }
public  PermissionGroupInfo(android.content.pm.PermissionGroupInfo orig) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence loadDescription(android.content.pm.PackageManager pm) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.PermissionGroupInfo> CREATOR;
public static final int FLAG_PERSONAL_INFO = 1;
public int descriptionRes;
public int flags;
public java.lang.CharSequence nonLocalizedDescription;
public int priority;
static { CREATOR = null; }
}
