package android.net.http;
 abstract class Connection
{
protected  Connection(android.content.Context context, org.apache.http.HttpHost host, android.net.http.RequestFeeder requestFeeder) { throw new RuntimeException("Stub!"); }
public synchronized  java.lang.String toString() { throw new RuntimeException("Stub!"); }
protected android.net.http.SslCertificate mCertificate;
protected android.net.http.AndroidHttpClientConnection mHttpClientConnection;
}
