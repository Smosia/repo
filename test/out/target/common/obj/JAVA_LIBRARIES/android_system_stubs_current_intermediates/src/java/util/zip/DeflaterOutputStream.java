package java.util.zip;
public class DeflaterOutputStream
  extends java.io.FilterOutputStream
{
public  DeflaterOutputStream(java.io.OutputStream os) { super((java.io.OutputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterOutputStream(java.io.OutputStream os, java.util.zip.Deflater def) { super((java.io.OutputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterOutputStream(java.io.OutputStream os, java.util.zip.Deflater def, int bufferSize) { super((java.io.OutputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterOutputStream(java.io.OutputStream os, boolean syncFlush) { super((java.io.OutputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterOutputStream(java.io.OutputStream os, java.util.zip.Deflater def, boolean syncFlush) { super((java.io.OutputStream)null); throw new RuntimeException("Stub!"); }
public  DeflaterOutputStream(java.io.OutputStream os, java.util.zip.Deflater def, int bufferSize, boolean syncFlush) { super((java.io.OutputStream)null); throw new RuntimeException("Stub!"); }
protected  void deflate() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void close() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void finish() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void write(int i) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void write(byte[] buffer, int offset, int byteCount) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void flush() throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected byte[] buf = null;
protected java.util.zip.Deflater def;
}
