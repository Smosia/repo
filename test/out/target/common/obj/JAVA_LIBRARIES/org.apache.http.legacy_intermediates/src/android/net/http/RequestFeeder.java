package android.net.http;
 interface RequestFeeder
{
public abstract  android.net.http.Request getRequest();
public abstract  android.net.http.Request getRequest(org.apache.http.HttpHost host);
public abstract  boolean haveRequest(org.apache.http.HttpHost host);
public abstract  void requeueRequest(android.net.http.Request request);
}
