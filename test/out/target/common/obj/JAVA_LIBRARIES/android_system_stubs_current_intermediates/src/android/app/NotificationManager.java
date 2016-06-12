package android.app;
public class NotificationManager
{
public static class Policy
  implements android.os.Parcelable
{
public  Policy(int priorityCategories, int priorityCallSenders, int priorityMessageSenders) { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  int hashCode() { throw new RuntimeException("Stub!"); }
public  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static  java.lang.String priorityCategoriesToString(int priorityCategories) { throw new RuntimeException("Stub!"); }
public static  java.lang.String prioritySendersToString(int prioritySenders) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.app.NotificationManager.Policy> CREATOR;
public static final int PRIORITY_CATEGORY_CALLS = 8;
public static final int PRIORITY_CATEGORY_EVENTS = 2;
public static final int PRIORITY_CATEGORY_MESSAGES = 4;
public static final int PRIORITY_CATEGORY_REMINDERS = 1;
public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 16;
public static final int PRIORITY_SENDERS_ANY = 0;
public static final int PRIORITY_SENDERS_CONTACTS = 1;
public static final int PRIORITY_SENDERS_STARRED = 2;
public final int priorityCallSenders;
public final int priorityCategories;
public final int priorityMessageSenders;
static { CREATOR = null; }
}
NotificationManager() { throw new RuntimeException("Stub!"); }
public  void notify(int id, android.app.Notification notification) { throw new RuntimeException("Stub!"); }
public  void notify(java.lang.String tag, int id, android.app.Notification notification) { throw new RuntimeException("Stub!"); }
public  void cancel(int id) { throw new RuntimeException("Stub!"); }
public  void cancel(java.lang.String tag, int id) { throw new RuntimeException("Stub!"); }
public  void cancelAll() { throw new RuntimeException("Stub!"); }
public  boolean isNotificationPolicyAccessGranted() { throw new RuntimeException("Stub!"); }
public  android.app.NotificationManager.Policy getNotificationPolicy() { throw new RuntimeException("Stub!"); }
public  void setNotificationPolicy(android.app.NotificationManager.Policy policy) { throw new RuntimeException("Stub!"); }
public  android.service.notification.StatusBarNotification[] getActiveNotifications() { throw new RuntimeException("Stub!"); }
public final  int getCurrentInterruptionFilter() { throw new RuntimeException("Stub!"); }
public final  void setInterruptionFilter(int interruptionFilter) { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_INTERRUPTION_FILTER_CHANGED = "android.app.action.INTERRUPTION_FILTER_CHANGED";
public static final java.lang.String ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED = "android.app.action.NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED";
public static final java.lang.String ACTION_NOTIFICATION_POLICY_CHANGED = "android.app.action.NOTIFICATION_POLICY_CHANGED";
public static final int INTERRUPTION_FILTER_ALARMS = 4;
public static final int INTERRUPTION_FILTER_ALL = 1;
public static final int INTERRUPTION_FILTER_NONE = 3;
public static final int INTERRUPTION_FILTER_PRIORITY = 2;
public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
}
