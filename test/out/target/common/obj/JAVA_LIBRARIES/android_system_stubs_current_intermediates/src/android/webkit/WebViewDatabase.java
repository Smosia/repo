package android.webkit;
public abstract class WebViewDatabase
{
public  WebViewDatabase() { throw new RuntimeException("Stub!"); }
public static  android.webkit.WebViewDatabase getInstance(android.content.Context context) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public abstract  boolean hasUsernamePassword();
@java.lang.Deprecated()
public abstract  void clearUsernamePassword();
public abstract  boolean hasHttpAuthUsernamePassword();
public abstract  void clearHttpAuthUsernamePassword();
public abstract  boolean hasFormData();
public abstract  void clearFormData();
}
