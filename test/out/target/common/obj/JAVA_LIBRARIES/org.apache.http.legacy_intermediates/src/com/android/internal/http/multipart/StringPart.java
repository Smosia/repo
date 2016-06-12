package com.android.internal.http.multipart;
public class StringPart
  extends com.android.internal.http.multipart.PartBase
{
public  StringPart(java.lang.String name, java.lang.String value, java.lang.String charset) { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public  StringPart(java.lang.String name, java.lang.String value) { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
protected  void sendData(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected  long lengthOfData() { throw new RuntimeException("Stub!"); }
public  void setCharSet(java.lang.String charSet) { throw new RuntimeException("Stub!"); }
public static final java.lang.String DEFAULT_CHARSET = "US-ASCII";
public static final java.lang.String DEFAULT_CONTENT_TYPE = "text/plain";
public static final java.lang.String DEFAULT_TRANSFER_ENCODING = "8bit";
}
