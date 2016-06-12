package android.webkit;
public abstract class WebHistoryItem
  implements java.lang.Cloneable
{
public  WebHistoryItem() { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public abstract  int getId();
public abstract  java.lang.String getUrl();
public abstract  java.lang.String getOriginalUrl();
public abstract  java.lang.String getTitle();
public abstract  android.graphics.Bitmap getFavicon();
protected abstract  android.webkit.WebHistoryItem clone();
}
