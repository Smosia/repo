package android.net.http;
public class DelegatingSSLSession
  implements javax.net.ssl.SSLSession
{
public static class CertificateWrap
  extends android.net.http.DelegatingSSLSession
{
public  CertificateWrap(java.security.cert.Certificate certificate) { throw new RuntimeException("Stub!"); }
public  java.security.cert.Certificate[] getPeerCertificates() throws javax.net.ssl.SSLPeerUnverifiedException { throw new RuntimeException("Stub!"); }
}
protected  DelegatingSSLSession() { throw new RuntimeException("Stub!"); }
public  int getApplicationBufferSize() { throw new RuntimeException("Stub!"); }
public  java.lang.String getCipherSuite() { throw new RuntimeException("Stub!"); }
public  long getCreationTime() { throw new RuntimeException("Stub!"); }
public  byte[] getId() { throw new RuntimeException("Stub!"); }
public  long getLastAccessedTime() { throw new RuntimeException("Stub!"); }
public  java.security.cert.Certificate[] getLocalCertificates() { throw new RuntimeException("Stub!"); }
public  java.security.Principal getLocalPrincipal() { throw new RuntimeException("Stub!"); }
public  int getPacketBufferSize() { throw new RuntimeException("Stub!"); }
public  javax.security.cert.X509Certificate[] getPeerCertificateChain() throws javax.net.ssl.SSLPeerUnverifiedException { throw new RuntimeException("Stub!"); }
public  java.security.cert.Certificate[] getPeerCertificates() throws javax.net.ssl.SSLPeerUnverifiedException { throw new RuntimeException("Stub!"); }
public  java.lang.String getPeerHost() { throw new RuntimeException("Stub!"); }
public  int getPeerPort() { throw new RuntimeException("Stub!"); }
public  java.security.Principal getPeerPrincipal() throws javax.net.ssl.SSLPeerUnverifiedException { throw new RuntimeException("Stub!"); }
public  java.lang.String getProtocol() { throw new RuntimeException("Stub!"); }
public  javax.net.ssl.SSLSessionContext getSessionContext() { throw new RuntimeException("Stub!"); }
public  java.lang.Object getValue(java.lang.String name) { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getValueNames() { throw new RuntimeException("Stub!"); }
public  void invalidate() { throw new RuntimeException("Stub!"); }
public  boolean isValid() { throw new RuntimeException("Stub!"); }
public  void putValue(java.lang.String name, java.lang.Object value) { throw new RuntimeException("Stub!"); }
public  void removeValue(java.lang.String name) { throw new RuntimeException("Stub!"); }
}
