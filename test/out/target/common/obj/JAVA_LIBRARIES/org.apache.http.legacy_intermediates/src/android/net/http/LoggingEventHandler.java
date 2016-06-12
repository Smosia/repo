package android.net.http;
public class LoggingEventHandler
  implements android.net.http.EventHandler
{
public  LoggingEventHandler() { throw new RuntimeException("Stub!"); }
public  void requestSent() { throw new RuntimeException("Stub!"); }
public  void status(int major_version, int minor_version, int code, java.lang.String reason_phrase) { throw new RuntimeException("Stub!"); }
public  void headers(android.net.http.Headers headers) { throw new RuntimeException("Stub!"); }
public  void locationChanged(java.lang.String newLocation, boolean permanent) { throw new RuntimeException("Stub!"); }
public  void data(byte[] data, int len) { throw new RuntimeException("Stub!"); }
public  void endData() { throw new RuntimeException("Stub!"); }
public  void certificate(android.net.http.SslCertificate certificate) { throw new RuntimeException("Stub!"); }
public  void error(int id, java.lang.String description) { throw new RuntimeException("Stub!"); }
public  boolean handleSslErrorRequest(android.net.http.SslError error) { throw new RuntimeException("Stub!"); }
}
