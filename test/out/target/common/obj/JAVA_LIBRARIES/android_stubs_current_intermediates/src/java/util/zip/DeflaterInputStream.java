package java.util.zip;
public class DeflaterInputStream
  extends java.io.FilterInputStream
{
public  DeflaterInputStream(java.io.InputStream in) { super((java.io.InputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterInputStream(java.io.InputStream in, java.util.zip.Deflater deflater) { super((java.io.InputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterInputStream(java.io.InputStream in, java.util.zip.Deflater deflater, int bufferSize) { super((java.io.InputStream)null); throw new RuntimeException("Stub!"); }
public  void close() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  int read() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  int read(byte[] buffer, int byteOffset, int byteCount) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  long skip(long byteCount) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  int available() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  boolean markSupported() { throw new RuntimeException("Stub!"); }
public  void mark(int limit) { throw new RuntimeException("Stub!"); }
public  void reset() throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected final byte[] buf = null;
protected final java.util.zip.Deflater def;
}