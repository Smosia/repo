package android.webkit;
public abstract class WebMessagePort
{
public abstract static class WebMessageCallback
{
public  WebMessageCallback() { throw new RuntimeException("Stub!"); }
public  void onMessage(android.webkit.WebMessagePort port, android.webkit.WebMessage message) { throw new RuntimeException("Stub!"); }
}
WebMessagePort() { throw new RuntimeException("Stub!"); }
public abstract  void postMessage(android.webkit.WebMessage message);
public abstract  void close();
public abstract  void setWebMessageCallback(android.webkit.WebMessagePort.WebMessageCallback callback);
public abstract  void setWebMessageCallback(android.webkit.WebMessagePort.WebMessageCallback callback, android.os.Handler handler);
}
