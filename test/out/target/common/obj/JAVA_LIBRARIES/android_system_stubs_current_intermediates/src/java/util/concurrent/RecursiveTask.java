package java.util.concurrent;
public abstract class RecursiveTask<V>
  extends java.util.concurrent.ForkJoinTask<V>
{
public  RecursiveTask() { throw new RuntimeException("Stub!"); }
protected abstract  V compute();
public final  V getRawResult() { throw new RuntimeException("Stub!"); }
protected final  void setRawResult(V value) { throw new RuntimeException("Stub!"); }
protected final  boolean exec() { throw new RuntimeException("Stub!"); }
}
