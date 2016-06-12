package org.apache.http.client;
@java.lang.Deprecated()
public interface HttpRequestRetryHandler
{
public abstract  boolean retryRequest(java.io.IOException exception, int executionCount, org.apache.http.protocol.HttpContext context);
}
