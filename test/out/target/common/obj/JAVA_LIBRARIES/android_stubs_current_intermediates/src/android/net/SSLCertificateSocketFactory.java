package android.net;
public class SSLCertificateSocketFactory
  extends javax.net.ssl.SSLSocketFactory
{
@java.lang.Deprecated()
public  SSLCertificateSocketFactory(int handshakeTimeoutMillis) { throw new RuntimeException("Stub!"); }
public static  javax.net.SocketFactory getDefault(int handshakeTimeoutMillis) { throw new RuntimeException("Stub!"); }
public static  javax.net.ssl.SSLSocketFactory getDefault(int handshakeTimeoutMillis, android.net.SSLSessionCache cache) { throw new RuntimeException("Stub!"); }
public static  javax.net.ssl.SSLSocketFactory getInsecure(int handshakeTimeoutMillis, android.net.SSLSessionCache cache) { throw new RuntimeException("Stub!"); }
public  void setTrustManagers(javax.net.ssl.TrustManager[] trustManager) { throw new RuntimeException("Stub!"); }
public  void setNpnProtocols(byte[][] npnProtocols) { throw new RuntimeException("Stub!"); }
public  byte[] getNpnSelectedProtocol(java.net.Socket socket) { throw new RuntimeException("Stub!"); }
public  void setKeyManagers(javax.net.ssl.KeyManager[] keyManagers) { throw new RuntimeException("Stub!"); }
public  void setUseSessionTickets(java.net.Socket socket, boolean useSessionTickets) { throw new RuntimeException("Stub!"); }
public  void setHostname(java.net.Socket socket, java.lang.String hostName) { throw new RuntimeException("Stub!"); }
public  java.net.Socket createSocket(java.net.Socket k, java.lang.String host, int port, boolean close) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.net.Socket createSocket() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.net.Socket createSocket(java.net.InetAddress addr, int port, java.net.InetAddress localAddr, int localPort) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.net.Socket createSocket(java.net.InetAddress addr, int port) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.net.Socket createSocket(java.lang.String host, int port, java.net.InetAddress localAddr, int localPort) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.net.Socket createSocket(java.lang.String host, int port) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getDefaultCipherSuites() { throw new RuntimeException("Stub!"); }
public  java.lang.String[] getSupportedCipherSuites() { throw new RuntimeException("Stub!"); }
}
