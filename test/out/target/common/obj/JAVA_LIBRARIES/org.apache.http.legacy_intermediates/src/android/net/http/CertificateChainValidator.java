package android.net.http;
public class CertificateChainValidator
{
CertificateChainValidator() { throw new RuntimeException("Stub!"); }
public static  android.net.http.CertificateChainValidator getInstance() { throw new RuntimeException("Stub!"); }
public  android.net.http.SslError doHandshakeAndValidateServerCertificates(android.net.http.HttpsConnection connection, javax.net.ssl.SSLSocket sslSocket, java.lang.String domain) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public static  android.net.http.SslError verifyServerCertificates(byte[][] certChain, java.lang.String domain, java.lang.String authType) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public static  void handleTrustStorageUpdate() { throw new RuntimeException("Stub!"); }
}
