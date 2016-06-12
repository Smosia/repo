package java.util.concurrent;
public interface TransferQueue<E>
  extends java.util.concurrent.BlockingQueue<E>
{
public abstract  boolean tryTransfer(E e);
public abstract  void transfer(E e) throws java.lang.InterruptedException;
public abstract  boolean tryTransfer(E e, long timeout, java.util.concurrent.TimeUnit unit) throws java.lang.InterruptedException;
public abstract  boolean hasWaitingConsumer();
public abstract  int getWaitingConsumerCount();
}
