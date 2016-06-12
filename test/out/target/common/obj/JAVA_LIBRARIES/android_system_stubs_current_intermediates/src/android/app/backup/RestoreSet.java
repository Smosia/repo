package android.app.backup;
public class RestoreSet
  implements android.os.Parcelable
{
public  RestoreSet() { throw new RuntimeException("Stub!"); }
public  RestoreSet(java.lang.String _name, java.lang.String _dev, long _token) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.app.backup.RestoreSet> CREATOR;
public java.lang.String device;
public java.lang.String name;
public long token;
static { CREATOR = null; }
}
