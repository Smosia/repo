package org.apache.http;
@java.lang.Deprecated()
public interface HttpRequestInterceptor
{
public abstract  void process(org.apache.http.HttpRequest request, org.apache.http.protocol.HttpContext context) throws org.apache.http.HttpException, java.io.IOException;
}
