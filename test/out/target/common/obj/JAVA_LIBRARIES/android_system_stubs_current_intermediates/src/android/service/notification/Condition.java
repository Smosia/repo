package android.service.notification;
public class Condition
  implements android.os.Parcelable
{
public  Condition(android.net.Uri id, java.lang.String summary, java.lang.String line1, java.lang.String line2, int icon, int state, int flags) { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static  java.lang.String stateToString(int state) { throw new RuntimeException("Stub!"); }
public static  java.lang.String relevanceToString(int flags) { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  android.service.notification.Condition copy() { throw new RuntimeException("Stub!"); }
public static  android.net.Uri.Builder newId(android.content.Context context) { throw new RuntimeException("Stub!"); }
public static  boolean isValidId(android.net.Uri id, java.lang.String pkg) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.service.notification.Condition> CREATOR;
public static final int FLAG_RELEVANT_ALWAYS = 2;
public static final int FLAG_RELEVANT_NOW = 1;
public static final java.lang.String SCHEME = "condition";
public static final int STATE_ERROR = 3;
public static final int STATE_FALSE = 0;
public static final int STATE_TRUE = 1;
public static final int STATE_UNKNOWN = 2;
public final int flags;
public final int icon;
public final android.net.Uri id;
public final java.lang.String line1;
public final java.lang.String line2;
public final int state;
public final java.lang.String summary;
static { CREATOR = null; }
}
