package android.app.usage;
public final class NetworkStats
  implements java.lang.AutoCloseable
{
public static class Bucket
{
public  Bucket() { throw new RuntimeException("Stub!"); }
public  int getUid() { throw new RuntimeException("Stub!"); }
public  int getState() { throw new RuntimeException("Stub!"); }
public  long getStartTimeStamp() { throw new RuntimeException("Stub!"); }
public  long getEndTimeStamp() { throw new RuntimeException("Stub!"); }
public  long getRxBytes() { throw new RuntimeException("Stub!"); }
public  long getTxBytes() { throw new RuntimeException("Stub!"); }
public  long getRxPackets() { throw new RuntimeException("Stub!"); }
public  long getTxPackets() { throw new RuntimeException("Stub!"); }
public static final int STATE_ALL = -1;
public static final int STATE_DEFAULT = 1;
public static final int STATE_FOREGROUND = 2;
public static final int UID_ALL = -1;
public static final int UID_REMOVED = -4;
public static final int UID_TETHERING = -5;
}
NetworkStats() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public  boolean getNextBucket(android.app.usage.NetworkStats.Bucket bucketOut) { throw new RuntimeException("Stub!"); }
public  boolean hasNextBucket() { throw new RuntimeException("Stub!"); }
public  void close() { throw new RuntimeException("Stub!"); }
}
