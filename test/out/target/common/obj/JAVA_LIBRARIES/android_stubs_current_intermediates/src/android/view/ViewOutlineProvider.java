package android.view;
public abstract class ViewOutlineProvider
{
public  ViewOutlineProvider() { throw new RuntimeException("Stub!"); }
public abstract  void getOutline(android.view.View view, android.graphics.Outline outline);
public static final android.view.ViewOutlineProvider BACKGROUND;
public static final android.view.ViewOutlineProvider BOUNDS;
public static final android.view.ViewOutlineProvider PADDED_BOUNDS;
static { BACKGROUND = null; BOUNDS = null; PADDED_BOUNDS = null; }
}
