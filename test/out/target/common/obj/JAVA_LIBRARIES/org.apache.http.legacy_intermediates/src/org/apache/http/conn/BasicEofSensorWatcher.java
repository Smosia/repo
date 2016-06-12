package org.apache.http.conn;
@java.lang.Deprecated()
public class BasicEofSensorWatcher
  implements org.apache.http.conn.EofSensorWatcher
{
public  BasicEofSensorWatcher(org.apache.http.conn.ManagedClientConnection conn, boolean reuse) { throw new RuntimeException("Stub!"); }
public  boolean eofDetected(java.io.InputStream wrapped) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  boolean streamClosed(java.io.InputStream wrapped) throws java.io.IOException { throw new RuntimeException("Stub!"); }
public  boolean streamAbort(java.io.InputStream wrapped) throws java.io.IOException { throw new RuntimeException("Stub!"); }
protected boolean attemptReuse;
protected org.apache.http.conn.ManagedClientConnection managedConn;
}
