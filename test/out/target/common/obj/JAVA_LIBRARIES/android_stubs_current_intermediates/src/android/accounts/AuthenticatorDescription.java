package android.accounts;
public class AuthenticatorDescription
  implements android.os.Parcelable
{
public  AuthenticatorDescription(java.lang.String type, java.lang.String packageName, int labelId, int iconId, int smallIconId, int prefId, boolean customTokens) { throw new RuntimeException("Stub!"); }
public  AuthenticatorDescription(java.lang.String type, java.lang.String packageName, int labelId, int iconId, int smallIconId, int prefId) { throw new RuntimeException("Stub!"); }
public static  android.accounts.AuthenticatorDescription newKey(java.lang.String type) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.accounts.AuthenticatorDescription> CREATOR;
public final int accountPreferencesId;
public final boolean customTokens;
public final int iconId;
public final int labelId;
public final java.lang.String packageName;
public final int smallIconId;
public final java.lang.String type;
static { CREATOR = null; }
}
