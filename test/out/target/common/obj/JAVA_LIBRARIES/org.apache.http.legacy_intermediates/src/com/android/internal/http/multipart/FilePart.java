package com.android.internal.http.multipart;
public class FilePart
  extends com.android.internal.http.multipart.PartBase
{
public  FilePart(java.lang.String name, com.android.internal.http.multipart.PartSource partSource, java.lang.String contentType, java.lang.String charset) { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public  FilePart(java.lang.String name, com.android.internal.http.multipart.PartSource partSource) { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public  FilePart(java.lang.String name, java.io.File file) throws java.io.FileNotFoundException { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public  FilePart(java.lang.String name, java.io.File file, java.lang.String contentType, java.lang.String charset) throws java.io.FileNotFoundException { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public  FilePart(java.lang.String name, java.lang.String fileName, java.io.File file) throws java.io.FileNotFoundException { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
public  FilePart(java.lang.String name, java.lang.String fileName, java.io.File file, java.lang.String contentType, java.lang.String charset) throws java.io.FileNotFoundException { super((java.lang.String)null,(java.lang.String)null,(java.lang.String)null,(java.lang.String)null); throw new RuntimeException("Stub!"); }
protected  void sendDispositionHeader(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected  void sendData(java.io.OutputStream out) throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected  com.android.internal.http.multipart.PartSource getSource() { throw new RuntimeException("Stub!"); }
protected  long lengthOfData() { throw new RuntimeException("Stub!"); }
public static final java.lang.String DEFAULT_CHARSET = "ISO-8859-1";
public static final java.lang.String DEFAULT_CONTENT_TYPE = "application/octet-stream";
public static final java.lang.String DEFAULT_TRANSFER_ENCODING = "binary";
protected static final java.lang.String FILE_NAME = "; filename=";
}
