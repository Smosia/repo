package org.apache.http.conn.routing;
@java.lang.Deprecated()
public final class HttpRoute
  implements org.apache.http.conn.routing.RouteInfo
{
public  HttpRoute(org.apache.http.HttpHost target, java.net.InetAddress local, org.apache.http.HttpHost[] proxies, boolean secure, org.apache.http.conn.routing.RouteInfo.TunnelType tunnelled, org.apache.http.conn.routing.RouteInfo.LayerType layered) { throw new RuntimeException("Stub!"); }
public  HttpRoute(org.apache.http.HttpHost target, java.net.InetAddress local, org.apache.http.HttpHost proxy, boolean secure, org.apache.http.conn.routing.RouteInfo.TunnelType tunnelled, org.apache.http.conn.routing.RouteInfo.LayerType layered) { throw new RuntimeException("Stub!"); }
public  HttpRoute(org.apache.http.HttpHost target, java.net.InetAddress local, boolean secure) { throw new RuntimeException("Stub!"); }
public  HttpRoute(org.apache.http.HttpHost target) { throw new RuntimeException("Stub!"); }
public  HttpRoute(org.apache.http.HttpHost target, java.net.InetAddress local, org.apache.http.HttpHost proxy, boolean secure) { throw new RuntimeException("Stub!"); }
public final  org.apache.http.HttpHost getTargetHost() { throw new RuntimeException("Stub!"); }
public final  java.net.InetAddress getLocalAddress() { throw new RuntimeException("Stub!"); }
public final  int getHopCount() { throw new RuntimeException("Stub!"); }
public final  org.apache.http.HttpHost getHopTarget(int hop) { throw new RuntimeException("Stub!"); }
public final  org.apache.http.HttpHost getProxyHost() { throw new RuntimeException("Stub!"); }
public final  org.apache.http.conn.routing.RouteInfo.TunnelType getTunnelType() { throw new RuntimeException("Stub!"); }
public final  boolean isTunnelled() { throw new RuntimeException("Stub!"); }
public final  org.apache.http.conn.routing.RouteInfo.LayerType getLayerType() { throw new RuntimeException("Stub!"); }
public final  boolean isLayered() { throw new RuntimeException("Stub!"); }
public final  boolean isSecure() { throw new RuntimeException("Stub!"); }
public final  boolean equals(java.lang.Object o) { throw new RuntimeException("Stub!"); }
public final  int hashCode() { throw new RuntimeException("Stub!"); }
public final  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  java.lang.Object clone() throws java.lang.CloneNotSupportedException { throw new RuntimeException("Stub!"); }
}