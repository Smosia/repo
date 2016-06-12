package android.os;
public interface ITpGesture
  extends android.os.IInterface
{
public abstract static class Stub
  extends android.os.Binder
  implements android.os.ITpGesture
{
public  Stub() { throw new RuntimeException("Stub!"); }
public static  android.os.ITpGesture asInterface(android.os.IBinder obj) { throw new RuntimeException("Stub!"); }
public  android.os.IBinder asBinder() { throw new RuntimeException("Stub!"); }
public  boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException { throw new RuntimeException("Stub!"); }
}
public abstract  boolean getTpGestureEnabled() throws android.os.RemoteException;
public abstract  void setTpGestureEnabled(boolean on) throws android.os.RemoteException;
}
