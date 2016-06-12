package java.util.concurrent;
public class Phaser
{
public  Phaser() { throw new RuntimeException("Stub!"); }
public  Phaser(int parties) { throw new RuntimeException("Stub!"); }
public  Phaser(java.util.concurrent.Phaser parent) { throw new RuntimeException("Stub!"); }
public  Phaser(java.util.concurrent.Phaser parent, int parties) { throw new RuntimeException("Stub!"); }
public  int register() { throw new RuntimeException("Stub!"); }
public  int bulkRegister(int parties) { throw new RuntimeException("Stub!"); }
public  int arrive() { throw new RuntimeException("Stub!"); }
public  int arriveAndDeregister() { throw new RuntimeException("Stub!"); }
public  int arriveAndAwaitAdvance() { throw new RuntimeException("Stub!"); }
public  int awaitAdvance(int phase) { throw new RuntimeException("Stub!"); }
public  int awaitAdvanceInterruptibly(int phase) throws java.lang.InterruptedException { throw new RuntimeException("Stub!"); }
public  int awaitAdvanceInterruptibly(int phase, long timeout, java.util.concurrent.TimeUnit unit) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException { throw new RuntimeException("Stub!"); }
public  void forceTermination() { throw new RuntimeException("Stub!"); }
public final  int getPhase() { throw new RuntimeException("Stub!"); }
public  int getRegisteredParties() { throw new RuntimeException("Stub!"); }
public  int getArrivedParties() { throw new RuntimeException("Stub!"); }
public  int getUnarrivedParties() { throw new RuntimeException("Stub!"); }
public  java.util.concurrent.Phaser getParent() { throw new RuntimeException("Stub!"); }
public  java.util.concurrent.Phaser getRoot() { throw new RuntimeException("Stub!"); }
public  boolean isTerminated() { throw new RuntimeException("Stub!"); }
protected  boolean onAdvance(int phase, int registeredParties) { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
}
