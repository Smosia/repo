package java.util.concurrent;
public abstract class RecursiveAction
  extends java.util.concurrent.ForkJoinTask<java.lang.Void>
{
public  RecursiveAction() { throw new RuntimeException("Stub!"); }
protected abstract  void compute();
public final  java.lang.Void getRawResult() { throw new RuntimeException("Stub!"); }
protected final  void setRawResult(java.lang.Void mustBeNull) { throw new RuntimeException("Stub!"); }
protected final  boolean exec() { throw new RuntimeException("Stub!"); }
}
