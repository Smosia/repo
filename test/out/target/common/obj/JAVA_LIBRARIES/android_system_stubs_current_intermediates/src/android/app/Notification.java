package android.app;
public class Notification
  implements android.os.Parcelable
{
public static class Action
  implements android.os.Parcelable
{
public static final class Builder
{
@java.lang.Deprecated()
public  Builder(int icon, java.lang.CharSequence title, android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  Builder(android.graphics.drawable.Icon icon, java.lang.CharSequence title, android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  Builder(android.app.Notification.Action action) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.Builder addExtras(android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.Builder addRemoteInput(android.app.RemoteInput remoteInput) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.Builder extend(android.app.Notification.Action.Extender extender) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action build() { throw new RuntimeException("Stub!"); }
}
public static interface Extender
{
public abstract  android.app.Notification.Action.Builder extend(android.app.Notification.Action.Builder builder);
}
public static final class WearableExtender
  implements android.app.Notification.Action.Extender
{
public  WearableExtender() { throw new RuntimeException("Stub!"); }
public  WearableExtender(android.app.Notification.Action action) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.Builder extend(android.app.Notification.Action.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.WearableExtender clone() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.WearableExtender setAvailableOffline(boolean availableOffline) { throw new RuntimeException("Stub!"); }
public  boolean isAvailableOffline() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.WearableExtender setInProgressLabel(java.lang.CharSequence label) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getInProgressLabel() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.WearableExtender setConfirmLabel(java.lang.CharSequence label) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getConfirmLabel() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action.WearableExtender setCancelLabel(java.lang.CharSequence label) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getCancelLabel() { throw new RuntimeException("Stub!"); }
}
@java.lang.Deprecated()
public  Action(int icon, java.lang.CharSequence title, android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Icon getIcon() { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
public  android.app.RemoteInput[] getRemoteInputs() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Action clone() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.app.Notification.Action> CREATOR;
public android.app.PendingIntent actionIntent;
@java.lang.Deprecated()
public int icon;
public java.lang.CharSequence title;
static { CREATOR = null; }
}
public static class Builder
{
public  Builder(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setWhen(long when) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setShowWhen(boolean show) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setUsesChronometer(boolean b) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSmallIcon(int icon) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSmallIcon(int icon, int level) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSmallIcon(android.graphics.drawable.Icon icon) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setContentTitle(java.lang.CharSequence title) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setContentText(java.lang.CharSequence text) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSubText(java.lang.CharSequence text) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setNumber(int number) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setContentInfo(java.lang.CharSequence info) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setProgress(int max, int progress, boolean indeterminate) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setContent(android.widget.RemoteViews views) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setContentIntent(android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setDeleteIntent(android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setFullScreenIntent(android.app.PendingIntent intent, boolean highPriority) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setTicker(java.lang.CharSequence tickerText) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.app.Notification.Builder setTicker(java.lang.CharSequence tickerText, android.widget.RemoteViews views) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setLargeIcon(android.graphics.Bitmap b) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setLargeIcon(android.graphics.drawable.Icon icon) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSound(android.net.Uri sound) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.app.Notification.Builder setSound(android.net.Uri sound, int streamType) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSound(android.net.Uri sound, android.media.AudioAttributes audioAttributes) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setVibrate(long[] pattern) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setLights(int argb, int onMs, int offMs) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setOngoing(boolean ongoing) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setOnlyAlertOnce(boolean onlyAlertOnce) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setAutoCancel(boolean autoCancel) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setLocalOnly(boolean localOnly) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setDefaults(int defaults) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setPriority(int pri) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setCategory(java.lang.String category) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder addPerson(java.lang.String uri) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setGroup(java.lang.String groupKey) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setGroupSummary(boolean isGroupSummary) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setSortKey(java.lang.String sortKey) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder addExtras(android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setExtras(android.os.Bundle extras) { throw new RuntimeException("Stub!"); }
public  android.os.Bundle getExtras() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.app.Notification.Builder addAction(int icon, java.lang.CharSequence title, android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder addAction(android.app.Notification.Action action) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setStyle(android.app.Notification.Style style) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setVisibility(int visibility) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setPublicVersion(android.app.Notification n) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder extend(android.app.Notification.Extender extender) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder setColor(int argb) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.app.Notification getNotification() { throw new RuntimeException("Stub!"); }
public  android.app.Notification build() { throw new RuntimeException("Stub!"); }
}
public abstract static class Style
{
public  Style() { throw new RuntimeException("Stub!"); }
protected  void internalSetBigContentTitle(java.lang.CharSequence title) { throw new RuntimeException("Stub!"); }
protected  void internalSetSummaryText(java.lang.CharSequence cs) { throw new RuntimeException("Stub!"); }
public  void setBuilder(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
protected  void checkBuilder() { throw new RuntimeException("Stub!"); }
protected  android.widget.RemoteViews getStandardView(int layoutId) { throw new RuntimeException("Stub!"); }
public  android.app.Notification build() { throw new RuntimeException("Stub!"); }
protected android.app.Notification.Builder mBuilder;
}
public static class BigPictureStyle
  extends android.app.Notification.Style
{
public  BigPictureStyle() { throw new RuntimeException("Stub!"); }
public  BigPictureStyle(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigPictureStyle setBigContentTitle(java.lang.CharSequence title) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigPictureStyle setSummaryText(java.lang.CharSequence cs) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigPictureStyle bigPicture(android.graphics.Bitmap b) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigPictureStyle bigLargeIcon(android.graphics.Bitmap b) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigPictureStyle bigLargeIcon(android.graphics.drawable.Icon icon) { throw new RuntimeException("Stub!"); }
}
public static class BigTextStyle
  extends android.app.Notification.Style
{
public  BigTextStyle() { throw new RuntimeException("Stub!"); }
public  BigTextStyle(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigTextStyle setBigContentTitle(java.lang.CharSequence title) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigTextStyle setSummaryText(java.lang.CharSequence cs) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.BigTextStyle bigText(java.lang.CharSequence cs) { throw new RuntimeException("Stub!"); }
}
public static class InboxStyle
  extends android.app.Notification.Style
{
public  InboxStyle() { throw new RuntimeException("Stub!"); }
public  InboxStyle(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.InboxStyle setBigContentTitle(java.lang.CharSequence title) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.InboxStyle setSummaryText(java.lang.CharSequence cs) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.InboxStyle addLine(java.lang.CharSequence cs) { throw new RuntimeException("Stub!"); }
}
public static class MediaStyle
  extends android.app.Notification.Style
{
public  MediaStyle() { throw new RuntimeException("Stub!"); }
public  MediaStyle(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.MediaStyle setShowActionsInCompactView(int... actions) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.MediaStyle setMediaSession(android.media.session.MediaSession.Token token) { throw new RuntimeException("Stub!"); }
}
public static interface Extender
{
public abstract  android.app.Notification.Builder extend(android.app.Notification.Builder builder);
}
public static final class WearableExtender
  implements android.app.Notification.Extender
{
public  WearableExtender() { throw new RuntimeException("Stub!"); }
public  WearableExtender(android.app.Notification notif) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder extend(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender clone() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender addAction(android.app.Notification.Action action) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender addActions(java.util.List<android.app.Notification.Action> actions) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender clearActions() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.app.Notification.Action> getActions() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setDisplayIntent(android.app.PendingIntent intent) { throw new RuntimeException("Stub!"); }
public  android.app.PendingIntent getDisplayIntent() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender addPage(android.app.Notification page) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender addPages(java.util.List<android.app.Notification> pages) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender clearPages() { throw new RuntimeException("Stub!"); }
public  java.util.List<android.app.Notification> getPages() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setBackground(android.graphics.Bitmap background) { throw new RuntimeException("Stub!"); }
public  android.graphics.Bitmap getBackground() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setContentIcon(int icon) { throw new RuntimeException("Stub!"); }
public  int getContentIcon() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setContentIconGravity(int contentIconGravity) { throw new RuntimeException("Stub!"); }
public  int getContentIconGravity() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setContentAction(int actionIndex) { throw new RuntimeException("Stub!"); }
public  int getContentAction() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setGravity(int gravity) { throw new RuntimeException("Stub!"); }
public  int getGravity() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setCustomSizePreset(int sizePreset) { throw new RuntimeException("Stub!"); }
public  int getCustomSizePreset() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setCustomContentHeight(int height) { throw new RuntimeException("Stub!"); }
public  int getCustomContentHeight() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setStartScrollBottom(boolean startScrollBottom) { throw new RuntimeException("Stub!"); }
public  boolean getStartScrollBottom() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setContentIntentAvailableOffline(boolean contentIntentAvailableOffline) { throw new RuntimeException("Stub!"); }
public  boolean getContentIntentAvailableOffline() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setHintHideIcon(boolean hintHideIcon) { throw new RuntimeException("Stub!"); }
public  boolean getHintHideIcon() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setHintShowBackgroundOnly(boolean hintShowBackgroundOnly) { throw new RuntimeException("Stub!"); }
public  boolean getHintShowBackgroundOnly() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setHintAvoidBackgroundClipping(boolean hintAvoidBackgroundClipping) { throw new RuntimeException("Stub!"); }
public  boolean getHintAvoidBackgroundClipping() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.WearableExtender setHintScreenTimeout(int timeout) { throw new RuntimeException("Stub!"); }
public  int getHintScreenTimeout() { throw new RuntimeException("Stub!"); }
public static final int SCREEN_TIMEOUT_LONG = -1;
public static final int SCREEN_TIMEOUT_SHORT = 0;
public static final int SIZE_DEFAULT = 0;
public static final int SIZE_FULL_SCREEN = 5;
public static final int SIZE_LARGE = 4;
public static final int SIZE_MEDIUM = 3;
public static final int SIZE_SMALL = 2;
public static final int SIZE_XSMALL = 1;
public static final int UNSET_ACTION_INDEX = -1;
}
public static final class CarExtender
  implements android.app.Notification.Extender
{
public static class UnreadConversation
{
UnreadConversation() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getMessages() { throw new RuntimeException("Stub!"); }
public  android.app.RemoteInput getRemoteInput() { throw new RuntimeException("Stub!"); }
public  android.app.PendingIntent getReplyPendingIntent() { throw new RuntimeException("Stub!"); }
public  android.app.PendingIntent getReadPendingIntent() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getParticipants() { throw new RuntimeException("Stub!"); }
public  java.lang.String getParticipant() { throw new RuntimeException("Stub!"); }
public  long getLatestTimestamp() { throw new RuntimeException("Stub!"); }
}
public static class Builder
{
public  Builder(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender.Builder addMessage(java.lang.String message) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender.Builder setReplyAction(android.app.PendingIntent pendingIntent, android.app.RemoteInput remoteInput) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender.Builder setReadPendingIntent(android.app.PendingIntent pendingIntent) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender.Builder setLatestTimestamp(long timestamp) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender.UnreadConversation build() { throw new RuntimeException("Stub!"); }
}
public  CarExtender() { throw new RuntimeException("Stub!"); }
public  CarExtender(android.app.Notification notif) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.Builder extend(android.app.Notification.Builder builder) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender setColor(int color) { throw new RuntimeException("Stub!"); }
public  int getColor() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender setLargeIcon(android.graphics.Bitmap largeIcon) { throw new RuntimeException("Stub!"); }
public  android.graphics.Bitmap getLargeIcon() { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender setUnreadConversation(android.app.Notification.CarExtender.UnreadConversation unreadConversation) { throw new RuntimeException("Stub!"); }
public  android.app.Notification.CarExtender.UnreadConversation getUnreadConversation() { throw new RuntimeException("Stub!"); }
}
public  Notification() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  Notification(int icon, java.lang.CharSequence tickerText, long when) { throw new RuntimeException("Stub!"); }
public  Notification(android.os.Parcel parcel) { throw new RuntimeException("Stub!"); }
public  java.lang.String getGroup() { throw new RuntimeException("Stub!"); }
public  java.lang.String getSortKey() { throw new RuntimeException("Stub!"); }
public  android.app.Notification clone() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel parcel, int flags) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Icon getSmallIcon() { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Icon getLargeIcon() { throw new RuntimeException("Stub!"); }
public static final android.media.AudioAttributes AUDIO_ATTRIBUTES_DEFAULT;
public static final java.lang.String CATEGORY_ALARM = "alarm";
public static final java.lang.String CATEGORY_CALL = "call";
public static final java.lang.String CATEGORY_EMAIL = "email";
public static final java.lang.String CATEGORY_ERROR = "err";
public static final java.lang.String CATEGORY_EVENT = "event";
public static final java.lang.String CATEGORY_MESSAGE = "msg";
public static final java.lang.String CATEGORY_PROGRESS = "progress";
public static final java.lang.String CATEGORY_PROMO = "promo";
public static final java.lang.String CATEGORY_RECOMMENDATION = "recommendation";
public static final java.lang.String CATEGORY_REMINDER = "reminder";
public static final java.lang.String CATEGORY_SERVICE = "service";
public static final java.lang.String CATEGORY_SOCIAL = "social";
public static final java.lang.String CATEGORY_STATUS = "status";
public static final java.lang.String CATEGORY_SYSTEM = "sys";
public static final java.lang.String CATEGORY_TRANSPORT = "transport";
public static final int COLOR_DEFAULT = 0;
public static final android.os.Parcelable.Creator<android.app.Notification> CREATOR;
public static final int DEFAULT_ALL = -1;
public static final int DEFAULT_LIGHTS = 4;
public static final int DEFAULT_SOUND = 1;
public static final int DEFAULT_VIBRATE = 2;
public static final java.lang.String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
public static final java.lang.String EXTRA_BIG_TEXT = "android.bigText";
public static final java.lang.String EXTRA_COMPACT_ACTIONS = "android.compactActions";
public static final java.lang.String EXTRA_INFO_TEXT = "android.infoText";
public static final java.lang.String EXTRA_LARGE_ICON = "android.largeIcon";
public static final java.lang.String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
public static final java.lang.String EXTRA_MEDIA_SESSION = "android.mediaSession";
public static final java.lang.String EXTRA_PEOPLE = "android.people";
public static final java.lang.String EXTRA_PICTURE = "android.picture";
public static final java.lang.String EXTRA_PROGRESS = "android.progress";
public static final java.lang.String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
public static final java.lang.String EXTRA_PROGRESS_MAX = "android.progressMax";
public static final java.lang.String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
public static final java.lang.String EXTRA_SHOW_WHEN = "android.showWhen";
public static final java.lang.String EXTRA_SMALL_ICON = "android.icon";
public static final java.lang.String EXTRA_SUB_TEXT = "android.subText";
public static final java.lang.String EXTRA_SUMMARY_TEXT = "android.summaryText";
public static final java.lang.String EXTRA_TEMPLATE = "android.template";
public static final java.lang.String EXTRA_TEXT = "android.text";
public static final java.lang.String EXTRA_TEXT_LINES = "android.textLines";
public static final java.lang.String EXTRA_TITLE = "android.title";
public static final java.lang.String EXTRA_TITLE_BIG = "android.title.big";
public static final int FLAG_AUTO_CANCEL = 16;
public static final int FLAG_FOREGROUND_SERVICE = 64;
public static final int FLAG_GROUP_SUMMARY = 512;
@Deprecated
public static final int FLAG_HIGH_PRIORITY = 128;
public static final int FLAG_INSISTENT = 4;
public static final int FLAG_LOCAL_ONLY = 256;
public static final int FLAG_NO_CLEAR = 32;
public static final int FLAG_ONGOING_EVENT = 2;
public static final int FLAG_ONLY_ALERT_ONCE = 8;
public static final int FLAG_SHOW_LIGHTS = 1;
public static final java.lang.String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
public static final int PRIORITY_DEFAULT = 0;
public static final int PRIORITY_HIGH = 1;
public static final int PRIORITY_LOW = -1;
public static final int PRIORITY_MAX = 2;
public static final int PRIORITY_MIN = -2;
@java.lang.Deprecated()
public static final int STREAM_DEFAULT = -1;
public static final int VISIBILITY_PRIVATE = 0;
public static final int VISIBILITY_PUBLIC = 1;
public static final int VISIBILITY_SECRET = -1;
public android.app.Notification.Action[] actions = null;
public android.media.AudioAttributes audioAttributes;
@java.lang.Deprecated()
public int audioStreamType;
public android.widget.RemoteViews bigContentView;
public java.lang.String category;
public int color;
public android.app.PendingIntent contentIntent;
public android.widget.RemoteViews contentView;
public int defaults;
public android.app.PendingIntent deleteIntent;
public android.os.Bundle extras;
public int flags;
public android.app.PendingIntent fullScreenIntent;
public android.widget.RemoteViews headsUpContentView;
@java.lang.Deprecated()
public int icon;
public int iconLevel;
@java.lang.Deprecated()
public android.graphics.Bitmap largeIcon;
public int ledARGB;
public int ledOffMS;
public int ledOnMS;
public int number;
public int priority;
public android.app.Notification publicVersion;
public android.net.Uri sound;
public java.lang.CharSequence tickerText;
@java.lang.Deprecated()
public android.widget.RemoteViews tickerView;
public long[] vibrate = null;
public int visibility;
public long when;
static { AUDIO_ATTRIBUTES_DEFAULT = null; CREATOR = null; }
}
