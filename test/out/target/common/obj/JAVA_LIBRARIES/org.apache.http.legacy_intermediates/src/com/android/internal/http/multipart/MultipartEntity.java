package com.android.internal.http.multipart;
public class MultipartEntity
  extends org.apache.http.entity.AbstractHttpEntity
{
public  MultipartEntity(com.android.internal.http.multipart.Part[] parts, org.apache.http.params.HttpParams params) { throw new RuntimeException("Stub!"); }
public  MultipartEntity(com.android.internal.http.multipart.Part[] parts) { throw new RuntimeException("Stub!"); }
protected  byte[] getMultipartBoundary() { throw new RuntimeException("Stub!"); }
public  boolean isRepeatable() { throw new RuntimeException("Stub!"); }
public  void writeTo(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  org.apache.http.Header getContentType() { throw new RuntimeException("Stub!"); }
public  long getContentLength() { throw new RuntimeException("Stub!"); }
public  java.io.InputStream getContent() throws java.io.IOException, java.lang.IllegalStateException { throw new RuntimeException("Stub!"); }
public  boolean isStreaming() { throw new RuntimeException("Stub!"); }
public static final java.lang.String MULTIPART_BOUNDARY = "http.method.multipart.boundary";
protected com.android.internal.http.multipart.Part[] parts = null;
}
