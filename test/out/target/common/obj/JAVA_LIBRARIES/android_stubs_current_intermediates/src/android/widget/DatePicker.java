package android.widget;
public class DatePicker
  extends android.widget.FrameLayout
{
public static interface OnDateChangedListener
{
public abstract  void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth);
}
public  DatePicker(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  DatePicker(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  DatePicker(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  DatePicker(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  void init(int year, int monthOfYear, int dayOfMonth, android.widget.DatePicker.OnDateChangedListener onDateChangedListener) { throw new RuntimeException("Stub!"); }
public  void updateDate(int year, int month, int dayOfMonth) { throw new RuntimeException("Stub!"); }
public  int getYear() { throw new RuntimeException("Stub!"); }
public  int getMonth() { throw new RuntimeException("Stub!"); }
public  int getDayOfMonth() { throw new RuntimeException("Stub!"); }
public  long getMinDate() { throw new RuntimeException("Stub!"); }
public  void setMinDate(long minDate) { throw new RuntimeException("Stub!"); }
public  long getMaxDate() { throw new RuntimeException("Stub!"); }
public  void setMaxDate(long maxDate) { throw new RuntimeException("Stub!"); }
public  void setEnabled(boolean enabled) { throw new RuntimeException("Stub!"); }
public  boolean isEnabled() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getAccessibilityClassName() { throw new RuntimeException("Stub!"); }
protected  void onConfigurationChanged(android.content.res.Configuration newConfig) { throw new RuntimeException("Stub!"); }
public  void setFirstDayOfWeek(int firstDayOfWeek) { throw new RuntimeException("Stub!"); }
public  int getFirstDayOfWeek() { throw new RuntimeException("Stub!"); }
public  boolean getCalendarViewShown() { throw new RuntimeException("Stub!"); }
public  android.widget.CalendarView getCalendarView() { throw new RuntimeException("Stub!"); }
public  void setCalendarViewShown(boolean shown) { throw new RuntimeException("Stub!"); }
public  boolean getSpinnersShown() { throw new RuntimeException("Stub!"); }
public  void setSpinnersShown(boolean shown) { throw new RuntimeException("Stub!"); }
protected  void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> container) { throw new RuntimeException("Stub!"); }
protected  android.os.Parcelable onSaveInstanceState() { throw new RuntimeException("Stub!"); }
protected  void onRestoreInstanceState(android.os.Parcelable state) { throw new RuntimeException("Stub!"); }
}