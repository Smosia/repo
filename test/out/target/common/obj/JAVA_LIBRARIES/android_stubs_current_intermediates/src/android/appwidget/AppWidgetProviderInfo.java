package android.appwidget;
public class AppWidgetProviderInfo
  implements android.os.Parcelable
{
public  AppWidgetProviderInfo() { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"deprecation"})
public  AppWidgetProviderInfo(android.os.Parcel in) { throw new RuntimeException("Stub!"); }
public final  java.lang.String loadLabel(android.content.pm.PackageManager packageManager) { throw new RuntimeException("Stub!"); }
public final  android.graphics.drawable.Drawable loadIcon(android.content.Context context, int density) { throw new RuntimeException("Stub!"); }
public final  android.graphics.drawable.Drawable loadPreviewImage(android.content.Context context, int density) { throw new RuntimeException("Stub!"); }
public final  android.os.UserHandle getProfile() { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"deprecation"})
public  void writeToParcel(android.os.Parcel out, int flags) { throw new RuntimeException("Stub!"); }
@java.lang.SuppressWarnings(value={"deprecation"})
public  android.appwidget.AppWidgetProviderInfo clone() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.appwidget.AppWidgetProviderInfo> CREATOR;
public static final int RESIZE_BOTH = 3;
public static final int RESIZE_HORIZONTAL = 1;
public static final int RESIZE_NONE = 0;
public static final int RESIZE_VERTICAL = 2;
public static final int WIDGET_CATEGORY_HOME_SCREEN = 1;
public static final int WIDGET_CATEGORY_KEYGUARD = 2;
public static final int WIDGET_CATEGORY_SEARCHBOX = 4;
public int autoAdvanceViewId;
public android.content.ComponentName configure;
public int icon;
public int initialKeyguardLayout;
public int initialLayout;
@java.lang.Deprecated()
public java.lang.String label;
public int minHeight;
public int minResizeHeight;
public int minResizeWidth;
public int minWidth;
public int previewImage;
public android.content.ComponentName provider;
public int resizeMode;
public int updatePeriodMillis;
public int widgetCategory;
static { CREATOR = null; }
}
