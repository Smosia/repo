package org.apache.http.client;
@java.lang.Deprecated()
public interface RequestDirector
{
public abstract  org.apache.http.HttpResponse execute(org.apache.http.HttpHost target, org.apache.http.HttpRequest request, org.apache.http.protocol.HttpContext context) throws org.apache.http.HttpException, java.io.IOException;
}
