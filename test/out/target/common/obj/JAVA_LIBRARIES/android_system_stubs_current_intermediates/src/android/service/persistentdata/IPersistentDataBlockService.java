package android.service.persistentdata;
public interface IPersistentDataBlockService
  extends android.os.IInterface
{
public abstract  int write(byte[] data) throws android.os.RemoteException;
public abstract  byte[] read() throws android.os.RemoteException;
public abstract  void wipe() throws android.os.RemoteException;
public abstract  int getDataBlockSize() throws android.os.RemoteException;
public abstract  long getMaximumDataBlockSize() throws android.os.RemoteException;
public abstract  void setOemUnlockEnabled(boolean enabled) throws android.os.RemoteException;
public abstract  boolean getOemUnlockEnabled() throws android.os.RemoteException;
}
