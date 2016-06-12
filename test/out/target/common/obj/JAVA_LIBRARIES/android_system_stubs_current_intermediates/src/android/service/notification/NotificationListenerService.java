package android.service.notification;
public abstract class NotificationListenerService
  extends android.app.Service
{
public static class Ranking
{
public  Ranking() { throw new RuntimeException("Stub!"); }
public  java.lang.String getKey() { throw new RuntimeException("Stub!"); }
public  int getRank() { throw new RuntimeException("Stub!"); }
public  boolean isAmbient() { throw new RuntimeException("Stub!"); }
public  boolean matchesInterruptionFilter() { throw new RuntimeException("Stub!"); }
}
public static class RankingMap
  implements android.os.Parcelable
{
RankingMap() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getOrderedKeys() { throw new RuntimeException("Stub!"); }
public  boolean getRanking(java.lang.String key, android.service.notification.NotificationListenerService.Ranking outRanking) { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.service.notification.NotificationListenerService.RankingMap> CREATOR;
static { CREATOR = null; }
}
public  NotificationListenerService() { throw new RuntimeException("Stub!"); }
public  void onNotificationPosted(android.service.notification.StatusBarNotification sbn) { throw new RuntimeException("Stub!"); }
public  void onNotificationPosted(android.service.notification.StatusBarNotification sbn, android.service.notification.NotificationListenerService.RankingMap rankingMap) { throw new RuntimeException("Stub!"); }
public  void onNotificationRemoved(android.service.notification.StatusBarNotification sbn) { throw new RuntimeException("Stub!"); }
public  void onNotificationRemoved(android.service.notification.StatusBarNotification sbn, android.service.notification.NotificationListenerService.RankingMap rankingMap) { throw new RuntimeException("Stub!"); }
public  void onListenerConnected() { throw new RuntimeException("Stub!"); }
public  void onNotificationRankingUpdate(android.service.notification.NotificationListenerService.RankingMap rankingMap) { throw new RuntimeException("Stub!"); }
public  void onListenerHintsChanged(int hints) { throw new RuntimeException("Stub!"); }
public  void onInterruptionFilterChanged(int interruptionFilter) { throw new RuntimeException("Stub!"); }
@Deprecated
public final  void cancelNotification(java.lang.String pkg, java.lang.String tag, int id) { throw new RuntimeException("Stub!"); }
public final  void cancelNotification(java.lang.String key) { throw new RuntimeException("Stub!"); }
public final  void cancelAllNotifications() { throw new RuntimeException("Stub!"); }
public final  void cancelNotifications(java.lang.String[] keys) { throw new RuntimeException("Stub!"); }
public final  void setNotificationsShown(java.lang.String[] keys) { throw new RuntimeException("Stub!"); }
public final  void setOnNotificationPostedTrim(int trim) { throw new RuntimeException("Stub!"); }
public  android.service.notification.StatusBarNotification[] getActiveNotifications() { throw new RuntimeException("Stub!"); }
public  android.service.notification.StatusBarNotification[] getActiveNotifications(int trim) { throw new RuntimeException("Stub!"); }
public  android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String[] keys) { throw new RuntimeException("Stub!"); }
public  android.service.notification.StatusBarNotification[] getActiveNotifications(java.lang.String[] keys, int trim) { throw new RuntimeException("Stub!"); }
public final  int getCurrentListenerHints() { throw new RuntimeException("Stub!"); }
public final  int getCurrentInterruptionFilter() { throw new RuntimeException("Stub!"); }
public final  void requestListenerHints(int hints) { throw new RuntimeException("Stub!"); }
public final  void requestInterruptionFilter(int interruptionFilter) { throw new RuntimeException("Stub!"); }
public  android.service.notification.NotificationListenerService.RankingMap getCurrentRanking() { throw new RuntimeException("Stub!"); }
public  android.os.IBinder onBind(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void registerAsSystemService(android.content.Context context, android.content.ComponentName componentName, int currentUser) throws android.os.RemoteException { throw new RuntimeException("Stub!"); }
public  void unregisterAsSystemService() throws android.os.RemoteException { throw new RuntimeException("Stub!"); }
public static final int HINT_HOST_DISABLE_EFFECTS = 1;
public static final int INTERRUPTION_FILTER_ALARMS = 4;
public static final int INTERRUPTION_FILTER_ALL = 1;
public static final int INTERRUPTION_FILTER_NONE = 3;
public static final int INTERRUPTION_FILTER_PRIORITY = 2;
public static final int INTERRUPTION_FILTER_UNKNOWN = 0;
public static final java.lang.String SERVICE_INTERFACE = "android.service.notification.NotificationListenerService";
public static final int TRIM_FULL = 0;
public static final int TRIM_LIGHT = 1;
}
