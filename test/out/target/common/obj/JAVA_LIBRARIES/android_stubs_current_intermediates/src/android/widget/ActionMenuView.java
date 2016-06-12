package android.widget;
public class ActionMenuView
  extends android.widget.LinearLayout
{
public static interface OnMenuItemClickListener
{
public abstract  boolean onMenuItemClick(android.view.MenuItem item);
}
public static class LayoutParams
  extends android.widget.LinearLayout.LayoutParams
{
public  LayoutParams(android.content.Context c, android.util.AttributeSet attrs) { super((android.widget.LinearLayout.LayoutParams)null); throw new RuntimeException("Stub!"); }
public  LayoutParams(android.view.ViewGroup.LayoutParams other) { super((android.widget.LinearLayout.LayoutParams)null); throw new RuntimeException("Stub!"); }
public  LayoutParams(android.widget.ActionMenuView.LayoutParams other) { super((android.widget.LinearLayout.LayoutParams)null); throw new RuntimeException("Stub!"); }
public  LayoutParams(int width, int height) { super((android.widget.LinearLayout.LayoutParams)null); throw new RuntimeException("Stub!"); }
}
public  ActionMenuView(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  ActionMenuView(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  void setPopupTheme(int resId) { throw new RuntimeException("Stub!"); }
public  int getPopupTheme() { throw new RuntimeException("Stub!"); }
public  void onConfigurationChanged(android.content.res.Configuration newConfig) { throw new RuntimeException("Stub!"); }
public  void setOnMenuItemClickListener(android.widget.ActionMenuView.OnMenuItemClickListener listener) { throw new RuntimeException("Stub!"); }
protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { throw new RuntimeException("Stub!"); }
protected  void onLayout(boolean changed, int left, int top, int right, int bottom) { throw new RuntimeException("Stub!"); }
public  void onDetachedFromWindow() { throw new RuntimeException("Stub!"); }
public  void setOverflowIcon(android.graphics.drawable.Drawable icon) { throw new RuntimeException("Stub!"); }
public  android.graphics.drawable.Drawable getOverflowIcon() { throw new RuntimeException("Stub!"); }
protected  android.widget.ActionMenuView.LayoutParams generateDefaultLayoutParams() { throw new RuntimeException("Stub!"); }
public  android.widget.ActionMenuView.LayoutParams generateLayoutParams(android.util.AttributeSet attrs) { throw new RuntimeException("Stub!"); }
protected  android.widget.ActionMenuView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) { throw new RuntimeException("Stub!"); }
protected  boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) { throw new RuntimeException("Stub!"); }
public  android.view.Menu getMenu() { throw new RuntimeException("Stub!"); }
public  boolean showOverflowMenu() { throw new RuntimeException("Stub!"); }
public  boolean hideOverflowMenu() { throw new RuntimeException("Stub!"); }
public  boolean isOverflowMenuShowing() { throw new RuntimeException("Stub!"); }
public  void dismissPopupMenus() { throw new RuntimeException("Stub!"); }
}
