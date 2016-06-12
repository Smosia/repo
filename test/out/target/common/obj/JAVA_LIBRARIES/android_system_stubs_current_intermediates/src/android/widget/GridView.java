package android.widget;
@android.widget.RemoteViews.RemoteView()
public class GridView
  extends android.widget.AbsListView
{
public  GridView(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  GridView(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  GridView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  GridView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  android.widget.ListAdapter getAdapter() { throw new RuntimeException("Stub!"); }
public  void setRemoteViewsAdapter(android.content.Intent intent) { throw new RuntimeException("Stub!"); }
public  void setAdapter(android.widget.ListAdapter adapter) { throw new RuntimeException("Stub!"); }
public  void smoothScrollToPosition(int position) { throw new RuntimeException("Stub!"); }
public  void smoothScrollByOffset(int offset) { throw new RuntimeException("Stub!"); }
protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { throw new RuntimeException("Stub!"); }
protected  void attachLayoutAnimationParameters(android.view.View child, android.view.ViewGroup.LayoutParams params, int index, int count) { throw new RuntimeException("Stub!"); }
protected  void layoutChildren() { throw new RuntimeException("Stub!"); }
public  void setSelection(int position) { throw new RuntimeException("Stub!"); }
public  boolean onKeyDown(int keyCode, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onKeyMultiple(int keyCode, int repeatCount, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
public  boolean onKeyUp(int keyCode, android.view.KeyEvent event) { throw new RuntimeException("Stub!"); }
protected  void onFocusChanged(boolean gainFocus, int direction, android.graphics.Rect previouslyFocusedRect) { throw new RuntimeException("Stub!"); }
public  void setGravity(int gravity) { throw new RuntimeException("Stub!"); }
public  int getGravity() { throw new RuntimeException("Stub!"); }
public  void setHorizontalSpacing(int horizontalSpacing) { throw new RuntimeException("Stub!"); }
public  int getHorizontalSpacing() { throw new RuntimeException("Stub!"); }
public  int getRequestedHorizontalSpacing() { throw new RuntimeException("Stub!"); }
public  void setVerticalSpacing(int verticalSpacing) { throw new RuntimeException("Stub!"); }
public  int getVerticalSpacing() { throw new RuntimeException("Stub!"); }
public  void setStretchMode(int stretchMode) { throw new RuntimeException("Stub!"); }
public  int getStretchMode() { throw new RuntimeException("Stub!"); }
public  void setColumnWidth(int columnWidth) { throw new RuntimeException("Stub!"); }
public  int getColumnWidth() { throw new RuntimeException("Stub!"); }
public  int getRequestedColumnWidth() { throw new RuntimeException("Stub!"); }
public  void setNumColumns(int numColumns) { throw new RuntimeException("Stub!"); }
@android.view.ViewDebug.ExportedProperty()
public  int getNumColumns() { throw new RuntimeException("Stub!"); }
protected  int computeVerticalScrollExtent() { throw new RuntimeException("Stub!"); }
protected  int computeVerticalScrollOffset() { throw new RuntimeException("Stub!"); }
protected  int computeVerticalScrollRange() { throw new RuntimeException("Stub!"); }
public  java.lang.CharSequence getAccessibilityClassName() { throw new RuntimeException("Stub!"); }
public  void onInitializeAccessibilityNodeInfoForItem(android.view.View view, int position, android.view.accessibility.AccessibilityNodeInfo info) { throw new RuntimeException("Stub!"); }
public static final int AUTO_FIT = -1;
public static final int NO_STRETCH = 0;
public static final int STRETCH_COLUMN_WIDTH = 2;
public static final int STRETCH_SPACING = 1;
public static final int STRETCH_SPACING_UNIFORM = 3;
}