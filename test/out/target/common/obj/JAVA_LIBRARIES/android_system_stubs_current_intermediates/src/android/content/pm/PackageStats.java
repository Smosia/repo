package android.content.pm;
public class PackageStats
  implements android.os.Parcelable
{
public  PackageStats(java.lang.String pkgName) { throw new RuntimeException("Stub!"); }
public  PackageStats(android.os.Parcel source) { throw new RuntimeException("Stub!"); }
public  PackageStats(android.content.pm.PackageStats pStats) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int parcelableFlags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.content.pm.PackageStats> CREATOR;
public long cacheSize;
public long codeSize;
public long dataSize;
public long externalCacheSize;
public long externalCodeSize;
public long externalDataSize;
public long externalMediaSize;
public long externalObbSize;
public java.lang.String packageName;
static { CREATOR = null; }
}