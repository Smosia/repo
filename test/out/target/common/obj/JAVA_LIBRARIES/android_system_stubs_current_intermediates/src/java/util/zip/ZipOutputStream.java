package java.util.zip;
public class ZipOutputStream
  extends java.util.zip.DeflaterOutputStream
{
public  ZipOutputStream(java.io.OutputStream os) { super((java.io.OutputStream)null,(java.util.zip.Deflater)null,0,false); throw new RuntimeException("Stub!"); }
public  void close() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void closeEntry() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void finish() throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void putNextEntry(java.util.zip.ZipEntry ze) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  void setComment(java.lang.String comment) { throw new RuntimeException("Stub!"); }
public  void setLevel(int level) { throw new RuntimeException("Stub!"); }
public  void setMethod(int method) { throw new RuntimeException("Stub!"); }
public  void write(byte[] buffer, int offset, int byteCount) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public static final int CENATT = 36;
public static final int CENATX = 38;
public static final int CENCOM = 32;
public static final int CENCRC = 16;
public static final int CENDSK = 34;
public static final int CENEXT = 30;
public static final int CENFLG = 8;
public static final int CENHDR = 46;
public static final int CENHOW = 10;
public static final int CENLEN = 24;
public static final int CENNAM = 28;
public static final int CENOFF = 42;
public static final long CENSIG = 33639248L;
public static final int CENSIZ = 20;
public static final int CENTIM = 12;
public static final int CENVEM = 4;
public static final int CENVER = 6;
public static final int DEFLATED = 8;
public static final int ENDCOM = 20;
public static final int ENDHDR = 22;
public static final int ENDOFF = 16;
public static final long ENDSIG = 101010256L;
public static final int ENDSIZ = 12;
public static final int ENDSUB = 8;
public static final int ENDTOT = 10;
public static final int EXTCRC = 4;
public static final int EXTHDR = 16;
public static final int EXTLEN = 12;
public static final long EXTSIG = 134695760L;
public static final int EXTSIZ = 8;
public static final int LOCCRC = 14;
public static final int LOCEXT = 28;
public static final int LOCFLG = 6;
public static final int LOCHDR = 30;
public static final int LOCHOW = 8;
public static final int LOCLEN = 22;
public static final int LOCNAM = 26;
public static final long LOCSIG = 67324752L;
public static final int LOCSIZ = 18;
public static final int LOCTIM = 10;
public static final int LOCVER = 4;
public static final int STORED = 0;
}
