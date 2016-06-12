package android.opengl;
public class EGLExt
{
public  EGLExt() { throw new RuntimeException("Stub!"); }
public static native  boolean eglPresentationTimeANDROID(android.opengl.EGLDisplay dpy, android.opengl.EGLSurface sur, long time);
public static final int EGL_CONTEXT_FLAGS_KHR = 12540;
public static final int EGL_CONTEXT_MAJOR_VERSION_KHR = 12440;
public static final int EGL_CONTEXT_MINOR_VERSION_KHR = 12539;
public static final int EGL_OPENGL_ES3_BIT_KHR = 64;
}
