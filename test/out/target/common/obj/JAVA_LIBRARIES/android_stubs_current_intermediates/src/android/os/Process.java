package android.os;
public class Process
{
public  Process() { throw new RuntimeException("Stub!"); }
public static final native  long getElapsedCpuTime();
public static final  boolean is64Bit() { throw new RuntimeException("Stub!"); }
public static final  int myPid() { throw new RuntimeException("Stub!"); }
public static final  int myTid() { throw new RuntimeException("Stub!"); }
public static final  int myUid() { throw new RuntimeException("Stub!"); }
public static final  android.os.UserHandle myUserHandle() { throw new RuntimeException("Stub!"); }
public static final native  int getUidForName(java.lang.String name);
public static final native  int getGidForName(java.lang.String name);
public static final native  void setThreadPriority(int tid, int priority) throws java.lang.IllegalArgumentException, java.lang.SecurityException;
public static final native  void setThreadPriority(int priority) throws java.lang.IllegalArgumentException, java.lang.SecurityException;
public static final native  int getThreadPriority(int tid) throws java.lang.IllegalArgumentException;
@java.lang.Deprecated()
public static final  boolean supportsProcesses() { throw new RuntimeException("Stub!"); }
public static final  void killProcess(int pid) { throw new RuntimeException("Stub!"); }
public static final native  void sendSignal(int pid, int signal);
public static final int FIRST_APPLICATION_UID = 10000;
public static final int LAST_APPLICATION_UID = 19999;
public static final int PHONE_UID = 1001;
public static final int SIGNAL_KILL = 9;
public static final int SIGNAL_QUIT = 3;
public static final int SIGNAL_USR1 = 10;
public static final int SYSTEM_UID = 1000;
public static final int THREAD_PRIORITY_AUDIO = -16;
public static final int THREAD_PRIORITY_BACKGROUND = 10;
public static final int THREAD_PRIORITY_DEFAULT = 0;
public static final int THREAD_PRIORITY_DISPLAY = -4;
public static final int THREAD_PRIORITY_FOREGROUND = -2;
public static final int THREAD_PRIORITY_LESS_FAVORABLE = 1;
public static final int THREAD_PRIORITY_LOWEST = 19;
public static final int THREAD_PRIORITY_MORE_FAVORABLE = -1;
public static final int THREAD_PRIORITY_URGENT_AUDIO = -19;
public static final int THREAD_PRIORITY_URGENT_DISPLAY = -8;
}