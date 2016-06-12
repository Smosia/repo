package android.net.http;
public class RequestQueue
  implements android.net.http.RequestFeeder
{
public  RequestQueue(android.content.Context context) { throw new RuntimeException("Stub!"); }
public  RequestQueue(android.content.Context context, int connectionCount) { throw new RuntimeException("Stub!"); }
public synchronized  void enablePlatformNotifications() { throw new RuntimeException("Stub!"); }
public synchronized  void disablePlatformNotifications() { throw new RuntimeException("Stub!"); }
public  org.apache.http.HttpHost getProxyHost() { throw new RuntimeException("Stub!"); }
public  android.net.http.RequestHandle queueRequest(java.lang.String url, java.lang.String method, java.util.Map<java.lang.String, java.lang.String> headers, android.net.http.EventHandler eventHandler, java.io.InputStream bodyProvider, int bodyLength) { throw new RuntimeException("Stub!"); }
public  android.net.http.RequestHandle queueRequest(java.lang.String url, android.net.compatibility.WebAddress uri, java.lang.String method, java.util.Map<java.lang.String, java.lang.String> headers, android.net.http.EventHandler eventHandler, java.io.InputStream bodyProvider, int bodyLength) { throw new RuntimeException("Stub!"); }
public  android.net.http.RequestHandle queueSynchronousRequest(java.lang.String url, android.net.compatibility.WebAddress uri, java.lang.String method, java.util.Map<java.lang.String, java.lang.String> headers, android.net.http.EventHandler eventHandler, java.io.InputStream bodyProvider, int bodyLength) { throw new RuntimeException("Stub!"); }
public synchronized  android.net.http.Request getRequest() { throw new RuntimeException("Stub!"); }
public synchronized  android.net.http.Request getRequest(org.apache.http.HttpHost host) { throw new RuntimeException("Stub!"); }
public synchronized  boolean haveRequest(org.apache.http.HttpHost host) { throw new RuntimeException("Stub!"); }
public  void requeueRequest(android.net.http.Request request) { throw new RuntimeException("Stub!"); }
public  void shutdown() { throw new RuntimeException("Stub!"); }
protected synchronized  void queueRequest(android.net.http.Request request, boolean head) { throw new RuntimeException("Stub!"); }
public  void startTiming() { throw new RuntimeException("Stub!"); }
public  void stopTiming() { throw new RuntimeException("Stub!"); }
}
