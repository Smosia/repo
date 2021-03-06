package java.util.zip;
public class Deflater
{
public  Deflater() { throw new RuntimeException("Stub!"); }
public  Deflater(int level) { throw new RuntimeException("Stub!"); }
public  Deflater(int level, boolean noHeader) { throw new RuntimeException("Stub!"); }
public  int deflate(byte[] buf) { throw new RuntimeException("Stub!"); }
public synchronized  int deflate(byte[] buf, int offset, int byteCount) { throw new RuntimeException("Stub!"); }
public synchronized  int deflate(byte[] buf, int offset, int byteCount, int flush) { throw new RuntimeException("Stub!"); }
public synchronized  void end() { throw new RuntimeException("Stub!"); }
protected  void finalize() { throw new RuntimeException("Stub!"); }
public synchronized  void finish() { throw new RuntimeException("Stub!"); }
public synchronized  boolean finished() { throw new RuntimeException("Stub!"); }
public synchronized  int getAdler() { throw new RuntimeException("Stub!"); }
public synchronized  int getTotalIn() { throw new RuntimeException("Stub!"); }
public synchronized  int getTotalOut() { throw new RuntimeException("Stub!"); }
public synchronized  boolean needsInput() { throw new RuntimeException("Stub!"); }
public synchronized  void reset() { throw new RuntimeException("Stub!"); }
public  void setDictionary(byte[] dictionary) { throw new RuntimeException("Stub!"); }
public synchronized  void setDictionary(byte[] buf, int offset, int byteCount) { throw new RuntimeException("Stub!"); }
public  void setInput(byte[] buf) { throw new RuntimeException("Stub!"); }
public synchronized  void setInput(byte[] buf, int offset, int byteCount) { throw new RuntimeException("Stub!"); }
public synchronized  void setLevel(int level) { throw new RuntimeException("Stub!"); }
public synchronized  void setStrategy(int strategy) { throw new RuntimeException("Stub!"); }
public synchronized  long getBytesRead() { throw new RuntimeException("Stub!"); }
public synchronized  long getBytesWritten() { throw new RuntimeException("Stub!"); }
public static final int BEST_COMPRESSION = 9;
public static final int BEST_SPEED = 1;
public static final int DEFAULT_COMPRESSION = -1;
public static final int DEFAULT_STRATEGY = 0;
public static final int DEFLATED = 8;
public static final int FILTERED = 1;
public static final int FULL_FLUSH = 3;
public static final int HUFFMAN_ONLY = 2;
public static final int NO_COMPRESSION = 0;
public static final int NO_FLUSH = 0;
public static final int SYNC_FLUSH = 2;
}
