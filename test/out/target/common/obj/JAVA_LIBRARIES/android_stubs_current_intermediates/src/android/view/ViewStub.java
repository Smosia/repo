package android.view;
@android.widget.RemoteViews.RemoteView()
public final class ViewStub
  extends android.view.View
{
public static interface OnInflateListener
{
public abstract  void onInflate(android.view.ViewStub stub, android.view.View inflated);
}
public  ViewStub(android.content.Context context) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  ViewStub(android.content.Context context, int layoutResource) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  ViewStub(android.content.Context context, android.util.AttributeSet attrs) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  ViewStub(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  ViewStub(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) { super((android.content.Context)null,(android.util.AttributeSet)null,0,0); throw new RuntimeException("Stub!"); }
public  int getInflatedId() { throw new RuntimeException("Stub!"); }
public  void setInflatedId(int inflatedId) { throw new RuntimeException("Stub!"); }
public  int getLayoutResource() { throw new RuntimeException("Stub!"); }
public  void setLayoutResource(int layoutResource) { throw new RuntimeException("Stub!"); }
public  void setLayoutInflater(android.view.LayoutInflater inflater) { throw new RuntimeException("Stub!"); }
public  android.view.LayoutInflater getLayoutInflater() { throw new RuntimeException("Stub!"); }
protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { throw new RuntimeException("Stub!"); }
public  void draw(android.graphics.Canvas canvas) { throw new RuntimeException("Stub!"); }
protected  void dispatchDraw(android.graphics.Canvas canvas) { throw new RuntimeException("Stub!"); }
public  void setVisibility(int visibility) { throw new RuntimeException("Stub!"); }
public  android.view.View inflate() { throw new RuntimeException("Stub!"); }
public  void setOnInflateListener(android.view.ViewStub.OnInflateListener inflateListener) { throw new RuntimeException("Stub!"); }
}