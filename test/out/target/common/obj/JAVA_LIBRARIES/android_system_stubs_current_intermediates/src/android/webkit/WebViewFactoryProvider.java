package android.webkit;
public interface WebViewFactoryProvider
{
public static interface Statics
{
public abstract  java.lang.String findAddress(java.lang.String addr);
public abstract  java.lang.String getDefaultUserAgent(android.content.Context context);
public abstract  void freeMemoryForTests();
public abstract  void setWebContentsDebuggingEnabled(boolean enable);
public abstract  void clearClientCertPreferences(java.lang.Runnable onCleared);
public abstract  void enableSlowWholeDocumentDraw();
public abstract  android.net.Uri[] parseFileChooserResult(int resultCode, android.content.Intent intent);
}
public abstract  android.webkit.WebViewFactoryProvider.Statics getStatics();
public abstract  android.webkit.WebViewProvider createWebView(android.webkit.WebView webView, android.webkit.WebView.PrivateAccess privateAccess);
public abstract  android.webkit.GeolocationPermissions getGeolocationPermissions();
public abstract  android.webkit.CookieManager getCookieManager();
public abstract  android.webkit.WebIconDatabase getWebIconDatabase();
public abstract  android.webkit.WebStorage getWebStorage();
public abstract  android.webkit.WebViewDatabase getWebViewDatabase(android.content.Context context);
}
