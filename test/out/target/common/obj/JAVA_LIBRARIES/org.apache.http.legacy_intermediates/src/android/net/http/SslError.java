package android.net.http;
public class SslError
{
@java.lang.Deprecated()
public  SslError(int arg0, android.net.http.SslCertificate arg1) { throw new RuntimeException("Stub!"); }
@java.lang.Deprecated()
public  SslError(int arg0, java.security.cert.X509Certificate arg1) { throw new RuntimeException("Stub!"); }
public  SslError(int arg0, android.net.http.SslCertificate arg1, java.lang.String arg2) { throw new RuntimeException("Stub!"); }
public  SslError(int arg0, java.security.cert.X509Certificate arg1, java.lang.String arg2) { throw new RuntimeException("Stub!"); }
public  android.net.http.SslCertificate getCertificate() { throw new RuntimeException("Stub!"); }
public  java.lang.String getUrl() { throw new RuntimeException("Stub!"); }
public  boolean addError(int arg0) { throw new RuntimeException("Stub!"); }
public  boolean hasError(int arg0) { throw new RuntimeException("Stub!"); }
public  int getPrimaryError() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public static final int SSL_DATE_INVALID = 4;
public static final int SSL_EXPIRED = 1;
public static final int SSL_IDMISMATCH = 2;
public static final int SSL_INVALID = 5;
@java.lang.Deprecated()
public static final int SSL_MAX_ERROR = 6;
public static final int SSL_NOTYETVALID = 0;
public static final int SSL_UNTRUSTED = 3;
}
