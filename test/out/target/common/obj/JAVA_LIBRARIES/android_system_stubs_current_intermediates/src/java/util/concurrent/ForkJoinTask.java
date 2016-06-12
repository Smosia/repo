package java.util.concurrent;
public abstract class ForkJoinTask<V>
  implements java.util.concurrent.Future<V>, java.io.Serializable
{
public  ForkJoinTask() { throw new RuntimeException("Stub!"); }
public final  java.util.concurrent.ForkJoinTask<V> fork() { throw new RuntimeException("Stub!"); }
public final  V join() { throw new RuntimeException("Stub!"); }
public final  V invoke() { throw new RuntimeException("Stub!"); }
public static  void invokeAll(java.util.concurrent.ForkJoinTask<?> t1, java.util.concurrent.ForkJoinTask<?> t2) { throw new RuntimeException("Stub!"); }
public static  void invokeAll(java.util.concurrent.ForkJoinTask<?>... tasks) { throw new RuntimeException("Stub!"); }
public static <T extends java.util.concurrent.ForkJoinTask<?>> java.util.Collection<T> invokeAll(java.util.Collection<T> tasks) { throw new RuntimeException("Stub!"); }
public  boolean cancel(boolean mayInterruptIfRunning) { throw new RuntimeException("Stub!"); }
public final  boolean isDone() { throw new RuntimeException("Stub!"); }
public final  boolean isCancelled() { throw new RuntimeException("Stub!"); }
public final  boolean isCompletedAbnormally() { throw new RuntimeException("Stub!"); }
public final  boolean isCompletedNormally() { throw new RuntimeException("Stub!"); }
public final  java.lang.Throwable getException() { throw new RuntimeException("Stub!"); }
public  void completeExceptionally(java.lang.Throwable ex) { throw new RuntimeException("Stub!"); }
public  void complete(V value) { throw new RuntimeException("Stub!"); }
public final  V get() throws java.lang.InterruptedException, java.util.concurrent.ExecutionException { throw new RuntimeException("Stub!"); }
public final  V get(long timeout, java.util.concurrent.TimeUnit unit) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException { throw new RuntimeException("Stub!"); }
public final  void quietlyJoin() { throw new RuntimeException("Stub!"); }
public final  void quietlyInvoke() { throw new RuntimeException("Stub!"); }
public static  void helpQuiesce() { throw new RuntimeException("Stub!"); }
public  void reinitialize() { throw new RuntimeException("Stub!"); }
public static  java.util.concurrent.ForkJoinPool getPool() { throw new RuntimeException("Stub!"); }
public static  boolean inForkJoinPool() { throw new RuntimeException("Stub!"); }
public  boolean tryUnfork() { throw new RuntimeException("Stub!"); }
public static  int getQueuedTaskCount() { throw new RuntimeException("Stub!"); }
public static  int getSurplusQueuedTaskCount() { throw new RuntimeException("Stub!"); }
public abstract  V getRawResult();
protected abstract  void setRawResult(V value);
protected abstract  boolean exec();
protected static  java.util.concurrent.ForkJoinTask<?> peekNextLocalTask() { throw new RuntimeException("Stub!"); }
protected static  java.util.concurrent.ForkJoinTask<?> pollNextLocalTask() { throw new RuntimeException("Stub!"); }
protected static  java.util.concurrent.ForkJoinTask<?> pollTask() { throw new RuntimeException("Stub!"); }
public static  java.util.concurrent.ForkJoinTask<?> adapt(java.lang.Runnable runnable) { throw new RuntimeException("Stub!"); }
public static <T> java.util.concurrent.ForkJoinTask<T> adapt(java.lang.Runnable runnable, T result) { throw new RuntimeException("Stub!"); }
public static <T> java.util.concurrent.ForkJoinTask<T> adapt(java.util.concurrent.Callable<? extends T> callable) { throw new RuntimeException("Stub!"); }
}
