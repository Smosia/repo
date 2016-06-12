package android.widget;
public class CalendarView
  extends android.widget.FrameLayout
{
public static interface OnDateChangeListener
{
public abstract  void onSelectedDayChange(android.widget.CalendarView view, int year, int month, int dayOfMonth);
}
public  CalendarView(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  CalendarView(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  CalendarView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  CalendarView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setShownWeekCount(int count) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getShownWeekCount() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setSelectedWeekBackgroundColor(int color) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getSelectedWeekBackgroundColor() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setFocusedMonthDateColor(int color) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getFocusedMonthDateColor() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setUnfocusedMonthDateColor(int color) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getUnfocusedMonthDateColor() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setWeekNumberColor(int color) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getWeekNumberColor() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setWeekSeparatorLineColor(int color) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  int getWeekSeparatorLineColor() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setSelectedDateVerticalBar(int resourceId) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  void setSelectedDateVerticalBar(android.graphics.drawable.Drawable drawable) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  android.graphics.drawable.Drawable getSelectedDateVerticalBar() { throw new RuntimeException("Stub!"); }
public  void setWeekDayTextAppearance(int resourceId) { throw new RuntimeException("Stub!"); }
public  int getWeekDayTextAppearance() { throw new RuntimeException("Stub!"); }
public  void setDateTextAppearance(int resourceId) { throw new RuntimeException("Stub!"); }
public  int getDateTextAppearance() { throw new RuntimeException("Stub!"); }
public  long getMinDate() { throw new RuntimeException("Stub!"); }
public  void setMinDate(long minDate) { throw new RuntimeException("Stub!"); }
public  long getMaxDate() { throw new RuntimeException("Stub!"); }
public  void setMaxDate(long maxDate) { throw new RuntimeException("Stub!"); }
public  void setShowWeekNumber(boolean showWeekNumber) { throw new RuntimeException("Stub!"); }
public  boolean getShowWeekNumber() { throw new RuntimeException("Stub!"); }
public  int getFirstDayOfWeek() { throw new RuntimeException("Stub!"); }
public  void setFirstDayOfWeek(int firstDayOfWeek) { throw new RuntimeException("Stub!"); }
public  void setOnDateChangeListener(android.widget.CalendarView.OnDateChangeListener listener) { throw new RuntimeException("Stub!"); }
public  long getDate() { throw new RuntimeException("Stub!"); }
public  void setDate(long date) { throw new RuntimeException("Stub!"); }
public  void setDate(long date, boolean animate, boolean center) { throw new RuntimeException("Stub!"); }
protected  void onConfigurationChanged(android.content.res.Configuration newConfig) { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getAccessibilityClassName() { throw new RuntimeException("Stub!"); }
}
