package android.renderscript;
public class RenderScript
{
public static enum ContextType
{
DEBUG(),
NORMAL(),
PROFILE();
}
public static class RSMessageHandler
  implements java.lang.Runnable
{
public  RSMessageHandler() { throw new RuntimeException("Stub!"); }
public  void run() { throw new RuntimeException("Stub!"); }
protected int[] mData = null;
protected int mID;
protected int mLength;
}
public static class RSErrorHandler
  implements java.lang.Runnable
{
public  RSErrorHandler() { throw new RuntimeException("Stub!"); }
public  void run() { throw new RuntimeException("Stub!"); }
protected java.lang.String mErrorMessage;
protected int mErrorNum;
}
public static enum Priority
{
LOW(),
NORMAL();
}
RenderScript() { throw new RuntimeException("Stub!"); }
public static  long getMinorVersion() { throw new RuntimeException("Stub!"); }
public  void setMessageHandler(android.renderscript.RenderScript.RSMessageHandler msg) { throw new RuntimeException("Stub!"); }
public  android.renderscript.RenderScript.RSMessageHandler getMessageHandler() { throw new RuntimeException("Stub!"); }
public  void sendMessage(int id, int[] data) { throw new RuntimeException("Stub!"); }
public  void setErrorHandler(android.renderscript.RenderScript.RSErrorHandler msg) { throw new RuntimeException("Stub!"); }
public  android.renderscript.RenderScript.RSErrorHandler getErrorHandler() { throw new RuntimeException("Stub!"); }
public  void setPriority(android.renderscript.RenderScript.Priority p) { throw new RuntimeException("Stub!"); }
public final  android.content.Context getApplicationContext() { throw new RuntimeException("Stub!"); }
public static  android.renderscript.RenderScript create(android.content.Context ctx) { throw new RuntimeException("Stub!"); }
public static  android.renderscript.RenderScript create(android.content.Context ctx, android.renderscript.RenderScript.ContextType ct) { throw new RuntimeException("Stub!"); }
public static  android.renderscript.RenderScript create(android.content.Context ctx, android.renderscript.RenderScript.ContextType ct, int flags) { throw new RuntimeException("Stub!"); }
public static  void releaseAllContexts() { throw new RuntimeException("Stub!"); }
public static  android.renderscript.RenderScript createMultiContext(android.content.Context ctx, android.renderscript.RenderScript.ContextType ct, int flags, int API_number) { throw new RuntimeException("Stub!"); }
public  void contextDump() { throw new RuntimeException("Stub!"); }
public  void finish() { throw new RuntimeException("Stub!"); }
protected  void finalize() throws java.lang.Throwable { throw new RuntimeException("Stub!"); }
public  void destroy() { throw new RuntimeException("Stub!"); }
public static final int CREATE_FLAG_LOW_LATENCY = 2;
public static final int CREATE_FLAG_LOW_POWER = 4;
public static final int CREATE_FLAG_NONE = 0;
}
