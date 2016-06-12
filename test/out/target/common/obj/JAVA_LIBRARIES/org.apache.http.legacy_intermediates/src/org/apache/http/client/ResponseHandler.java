package org.apache.http.client;
@java.lang.Deprecated()
public interface ResponseHandler<T>
{
public abstract  T handleResponse(org.apache.http.HttpResponse response) throws org.apache.http.client.ClientProtocolException, java.io.IOException;
}
