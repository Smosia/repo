package android.app.admin;
public class SystemUpdatePolicy
  implements android.os.Parcelable
{
SystemUpdatePolicy() { throw new RuntimeException("Stub!"); }
public static  android.app.admin.SystemUpdatePolicy createAutomaticInstallPolicy() { throw new RuntimeException("Stub!"); }
public static  android.app.admin.SystemUpdatePolicy createWindowedInstallPolicy(int startTime, int endTime) { throw new RuntimeException("Stub!"); }
public static  android.app.admin.SystemUpdatePolicy createPostponeInstallPolicy() { throw new RuntimeException("Stub!"); }
public  int getPolicyType() { throw new RuntimeException("Stub!"); }
public  int getInstallWindowStart() { throw new RuntimeException("Stub!"); }
public  int getInstallWindowEnd() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.app.admin.SystemUpdatePolicy> CREATOR;
public static final int TYPE_INSTALL_AUTOMATIC = 1;
public static final int TYPE_INSTALL_WINDOWED = 2;
public static final int TYPE_POSTPONE = 3;
static { CREATOR = null; }
}
