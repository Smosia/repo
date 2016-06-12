package org.apache.http.conn.scheme;
public interface SocketFactory
{
public abstract  java.net.Socket createSocket() throws java.io.IOException;
public abstract  java.net.Socket connectSocket(java.net.Socket arg0, java.lang.String arg1, int arg2, java.net.InetAddress arg3, int arg4, org.apache.http.params.HttpParams arg5) throws java.io.IOException, java.net.UnknownHostException, org.apache.http.conn.ConnectTimeoutException;
public abstract  boolean isSecure(java.net.Socket arg0) throws java.lang.IllegalArgumentException;
}
