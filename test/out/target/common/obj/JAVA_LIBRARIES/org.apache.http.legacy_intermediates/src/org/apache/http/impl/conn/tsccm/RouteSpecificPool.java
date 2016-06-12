package org.apache.http.impl.conn.tsccm;
@java.lang.Deprecated()
public class RouteSpecificPool
{
public  RouteSpecificPool(org.apache.http.conn.routing.HttpRoute route, int maxEntries) { throw new RuntimeException("Stub!"); }
public final  org.apache.http.conn.routing.HttpRoute getRoute() { throw new RuntimeException("Stub!"); }
public final  int getMaxEntries() { throw new RuntimeException("Stub!"); }
public  boolean isUnused() { throw new RuntimeException("Stub!"); }
public  int getCapacity() { throw new RuntimeException("Stub!"); }
public final  int getEntryCount() { throw new RuntimeException("Stub!"); }
public  org.apache.http.impl.conn.tsccm.BasicPoolEntry allocEntry(java.lang.Object state) { throw new RuntimeException("Stub!"); }
public  void freeEntry(org.apache.http.impl.conn.tsccm.BasicPoolEntry entry) { throw new RuntimeException("Stub!"); }
public  void createdEntry(org.apache.http.impl.conn.tsccm.BasicPoolEntry entry) { throw new RuntimeException("Stub!"); }
public  boolean deleteEntry(org.apache.http.impl.conn.tsccm.BasicPoolEntry entry) { throw new RuntimeException("Stub!"); }
public  void dropEntry() { throw new RuntimeException("Stub!"); }
public  void queueThread(org.apache.http.impl.conn.tsccm.WaitingThread wt) { throw new RuntimeException("Stub!"); }
public  boolean hasThread() { throw new RuntimeException("Stub!"); }
public  org.apache.http.impl.conn.tsccm.WaitingThread nextThread() { throw new RuntimeException("Stub!"); }
public  void removeThread(org.apache.http.impl.conn.tsccm.WaitingThread wt) { throw new RuntimeException("Stub!"); }
protected final java.util.LinkedList<org.apache.http.impl.conn.tsccm.BasicPoolEntry> freeEntries;
protected final int maxEntries;
protected int numEntries;
protected final org.apache.http.conn.routing.HttpRoute route;
protected final java.util.Queue<org.apache.http.impl.conn.tsccm.WaitingThread> waitingThreads;
}
