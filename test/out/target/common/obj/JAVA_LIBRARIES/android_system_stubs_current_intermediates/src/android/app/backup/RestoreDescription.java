package android.app.backup;
public class RestoreDescription
  implements android.os.Parcelable
{
public  RestoreDescription(java.lang.String packageName, int dataType) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  java.lang.String getPackageName() { throw new RuntimeException("Stub!"); }
public  int getDataType() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.app.backup.RestoreDescription> CREATOR;
public static final android.app.backup.RestoreDescription NO_MORE_PACKAGES;
public static final int TYPE_FULL_STREAM = 2;
public static final int TYPE_KEY_VALUE = 1;
static { CREATOR = null; NO_MORE_PACKAGES = null; }
}
