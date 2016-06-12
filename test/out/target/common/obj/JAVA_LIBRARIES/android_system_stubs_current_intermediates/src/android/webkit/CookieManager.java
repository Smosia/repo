package android.webkit;
public abstract class CookieManager
{
public  CookieManager() { throw new RuntimeException("Stub!"); }
protected  java.lang.Object clone() throws java.lang.CloneNotSupportedException { throw new RuntimeException("Stub!"); }
public static synchronized  android.webkit.CookieManager getInstance() { throw new RuntimeException("Stub!"); }
public abstract  void setAcceptCookie(boolean accept);
public abstract  boolean acceptCookie();
public abstract  void setAcceptThirdPartyCookies(android.webkit.WebView webview, boolean accept);
public abstract  boolean acceptThirdPartyCookies(android.webkit.WebView webview);
public abstract  void setCookie(java.lang.String url, java.lang.String value);
public abstract  void setCookie(java.lang.String url, java.lang.String value, android.webkit.ValueCallback<java.lang.Boolean> callback);
public abstract  java.lang.String getCookie(java.lang.String url);
public abstract  java.lang.String getCookie(java.lang.String url, boolean privateBrowsing);
public synchronized  java.lang.String getCookie(android.net.WebAddress uri) { throw new RuntimeException("Stub!"); }
@Deprecated
public abstract  void removeSessionCookie();
public abstract  void removeSessionCookies(android.webkit.ValueCallback<java.lang.Boolean> callback);
@java.lang.Deprecated()
public abstract  void removeAllCookie();
public abstract  void removeAllCookies(android.webkit.ValueCallback<java.lang.Boolean> callback);
public abstract  boolean hasCookies();
public abstract  boolean hasCookies(boolean privateBrowsing);
@java.lang.Deprecated()
public abstract  void removeExpiredCookie();
public abstract  void flush();
public static  boolean allowFileSchemeCookies() { throw new RuntimeException("Stub!"); }
protected abstract  boolean allowFileSchemeCookiesImpl();
public static  void setAcceptFileSchemeCookies(boolean accept) { throw new RuntimeException("Stub!"); }
protected abstract  void setAcceptFileSchemeCookiesImpl(boolean accept);
}
