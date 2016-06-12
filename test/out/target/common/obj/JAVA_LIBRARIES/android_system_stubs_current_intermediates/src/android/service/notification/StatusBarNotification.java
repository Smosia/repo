package android.service.notification;
public class StatusBarNotification
  implements android.os.Parcelable
{
public  StatusBarNotification(java.lang.String pkg, java.lang.String opPkg, int id, java.lang.String tag, int uid, int initialPid, int score, android.app.Notification notification, android.os.UserHandle user, long postTime) { throw new RuntimeException("Stub!"); }
public  StatusBarNotification(android.os.Parcel in) { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  android.service.notification.StatusBarNotification clone() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  boolean isOngoing() { throw new RuntimeException("Stub!"); }
public  boolean isClearable() { throw new RuntimeException("Stub!"); }
@Deprecated
public  int getUserId() { throw new RuntimeException("Stub!"); }
public  java.lang.String getPackageName() { throw new RuntimeException("Stub!"); }
public  int getId() { throw new RuntimeException("Stub!"); }
public  java.lang.String getTag() { throw new RuntimeException("Stub!"); }
public  android.app.Notification getNotification() { throw new RuntimeException("Stub!"); }
public  android.os.UserHandle getUser() { throw new RuntimeException("Stub!"); }
public  long getPostTime() { throw new RuntimeException("Stub!"); }
public  java.lang.String getKey() { throw new RuntimeException("Stub!"); }
public  java.lang.String getGroupKey() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.service.notification.StatusBarNotification> CREATOR;
static { CREATOR = null; }
}
