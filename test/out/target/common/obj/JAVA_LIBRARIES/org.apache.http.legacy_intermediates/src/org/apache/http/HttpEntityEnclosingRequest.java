package org.apache.http;
@java.lang.Deprecated()
public interface HttpEntityEnclosingRequest
  extends org.apache.http.HttpRequest
{
public abstract  boolean expectContinue();
public abstract  void setEntity(org.apache.http.HttpEntity entity);
public abstract  org.apache.http.HttpEntity getEntity();
}