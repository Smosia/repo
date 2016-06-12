package android.webkit;
public interface WebResourceRequest
{
public abstract  android.net.Uri getUrl();
public abstract  boolean isForMainFrame();
public abstract  boolean hasGesture();
public abstract  java.lang.String getMethod();
public abstract  java.util.Map<java.lang.String, java.lang.String> getRequestHeaders();
}
