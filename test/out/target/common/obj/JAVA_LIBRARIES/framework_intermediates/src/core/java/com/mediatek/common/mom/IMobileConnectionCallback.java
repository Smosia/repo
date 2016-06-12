/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/IMobileConnectionCallback.aidl
 */
package com.mediatek.common.mom;
/**
 * The interface is designed for handling the case that manager app has been changed.
 * MobileManagerService only support one management app at the same time, and each
 * management app are asigned with a prority to decide who can attach successfully.
 * The callback fucntion is set through attach() in MobileManagerService.
 * @hide
 */
public interface IMobileConnectionCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.IMobileConnectionCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.IMobileConnectionCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.IMobileConnectionCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.IMobileConnectionCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.IMobileConnectionCallback))) {
return ((com.mediatek.common.mom.IMobileConnectionCallback)iin);
}
return new com.mediatek.common.mom.IMobileConnectionCallback.Stub.Proxy(obj);
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
case TRANSACTION_onConnectionEnded:
{
data.enforceInterface(DESCRIPTOR);
this.onConnectionEnded();
reply.writeNoException();
return true;
}
case TRANSACTION_onConnectionResume:
{
data.enforceInterface(DESCRIPTOR);
this.onConnectionResume();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.IMobileConnectionCallback
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
     * The callback will be triggered when the connection between
     * Manager app and MobileManagerService has been terminated. 
     */
@Override public void onConnectionEnded() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onConnectionEnded, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * The callback will be triggered when current connection has been terminated,
     * and each management app had been invoked attach() will have chance to
     * attach again.
     */
@Override public void onConnectionResume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onConnectionResume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onConnectionEnded = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onConnectionResume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * The callback will be triggered when the connection between
     * Manager app and MobileManagerService has been terminated. 
     */
public void onConnectionEnded() throws android.os.RemoteException;
/**
     * The callback will be triggered when current connection has been terminated,
     * and each management app had been invoked attach() will have chance to
     * attach again.
     */
public void onConnectionResume() throws android.os.RemoteException;
}
