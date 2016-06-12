package android.webkit;
@java.lang.Deprecated()
public abstract class WebIconDatabase
{
@java.lang.Deprecated()
public static interface IconListener
{
public abstract  void onReceivedIcon(java.lang.String url, android.graphics.Bitmap icon);
}
public  WebIconDatabase() { throw new RuntimeException("Stub!"); }
public abstract  void open(java.lang.String path);
public abstract  void close();
public abstract  void removeAllIcons();
public abstract  void requestIconForPageUrl(java.lang.String url, android.webkit.WebIconDatabase.IconListener listener);
public abstract  void retainIconForPageUrl(java.lang.String url);
public abstract  void releaseIconForPageUrl(java.lang.String url);
public static  android.webkit.WebIconDatabase getInstance() { throw new RuntimeException("Stub!"); }
}
