package android.content.pm;
public class ServiceInfo
  extends android.content.pm.ComponentInfo
  implements android.os.Parcelable
{
public  ServiceInfo() { throw new RuntimeException("Stub!"); }
public  ServiceInfo(android.content.pm.ServiceInfo orig) { throw new RuntimeException("Stub!"); }
public  void dump(android.util.Printer pw, java.lang.String prefix) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.ServiceInfo> CREATOR;
public static final int FLAG_ISOLATED_PROCESS = 2;
public static final int FLAG_SINGLE_USER = 1073741824;
public static final int FLAG_STOP_WITH_TASK = 1;
public int flags;
public java.lang.String permission;
static { CREATOR = null; }
}
