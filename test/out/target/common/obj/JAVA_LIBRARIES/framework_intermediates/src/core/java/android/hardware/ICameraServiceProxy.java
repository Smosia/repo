/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/android/hardware/ICameraServiceProxy.aidl
 */
package android.hardware;
/**
 * Binder interface for the camera service proxy running in system_server.
 *
 * @hide
 */
public interface ICameraServiceProxy extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements android.hardware.ICameraServiceProxy
{
private static final java.lang.String DESCRIPTOR = "android.hardware.ICameraServiceProxy";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an android.hardware.ICameraServiceProxy interface,
 * generating a proxy if needed.
 */
public static android.hardware.ICameraServiceProxy asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof android.hardware.ICameraServiceProxy))) {
return ((android.hardware.ICameraServiceProxy)iin);
}
return new android.hardware.ICameraServiceProxy.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_pingForUserUpdate:
{
data.enforceInterface(DESCRIPTOR);
this.pingForUserUpdate();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements android.hardware.ICameraServiceProxy
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Ping the service proxy to update the valid users for the camera service.
     */
@Override public void pingForUserUpdate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pingForUserUpdate, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_pingForUserUpdate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Ping the service proxy to update the valid users for the camera service.
     */
public void pingForUserUpdate() throws android.os.RemoteException;
}
