package android.telecom;
public final class StatusHints
  implements android.os.Parcelable
{
@java.lang.Deprecated()
public  StatusHints(android.content.ComponentName packageName, java.lang.CharSequence label, int iconResId, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  StatusHints(java.lang.CharSequence label, android.graphics.drawable.Icon icon, android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.content.ComponentName getPackageName() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getLabel() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getIconResId() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.graphics.drawable.Drawable getIcon(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Icon getIcon() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object other) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.telecom.StatusHints> CREATOR;
static { CREATOR = null; }
}
