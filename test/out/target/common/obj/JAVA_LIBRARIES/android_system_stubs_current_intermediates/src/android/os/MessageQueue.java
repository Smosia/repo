package android.os;
public final class MessageQueue
{
public static interface IdleHandler
{
public abstract  boolean queueIdle();
}
public static interface OnFileDescriptorEventListener
{
public abstract  int onFileDescriptorEvents(java.io.FileDescriptor fd, int events);
public static final int EVENT_ERROR = 4;
public static final int EVENT_INPUT = 1;
public static final int EVENT_OUTPUT = 2;
}
MessageQueue() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public  boolean isIdle() { throw new RuntimeException("Stub!"); }
public  void addIdleHandler(android.os.MessageQueue.IdleHandler handler) { throw new RuntimeException("Stub!"); }
public  void removeIdleHandler(android.os.MessageQueue.IdleHandler handler) { throw new RuntimeException("Stub!"); }
public  void addOnFileDescriptorEventListener(java.io.FileDescriptor fd, int events, android.os.MessageQueue.OnFileDescriptorEventListener listener) { throw new RuntimeException("Stub!"); }
public  void removeOnFileDescriptorEventListener(java.io.FileDescriptor fd) { throw new RuntimeException("Stub!"); }
}
