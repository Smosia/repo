/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/multiwindow/IMWAmsCallback.aidl
 */
package com.mediatek.common.multiwindow;
/**
 * System private API for talking with the activity manager service.
 *
 * {@hide}
 */
public interface IMWAmsCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.multiwindow.IMWAmsCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.multiwindow.IMWAmsCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.multiwindow.IMWAmsCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.multiwindow.IMWAmsCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.multiwindow.IMWAmsCallback))) {
return ((com.mediatek.common.multiwindow.IMWAmsCallback)iin);
}
return new com.mediatek.common.multiwindow.IMWAmsCallback.Stub.Proxy(obj);
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
case TRANSACTION_restoreStack:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.restoreStack(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_moveActivityTaskToFront:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _result = this.moveActivityTaskToFront(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchMultiWindowMode:
{
data.enforceInterface(DESCRIPTOR);
this.switchMultiWindowMode();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.multiwindow.IMWAmsCallback
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
///for AMS

@Override public void restoreStack(android.os.IBinder token, boolean toMax) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
_data.writeInt(((toMax)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_restoreStack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean moveActivityTaskToFront(android.os.IBinder token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
mRemote.transact(Stub.TRANSACTION_moveActivityTaskToFront, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// Add for split-screen mode

@Override public void switchMultiWindowMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_switchMultiWindowMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_restoreStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_moveActivityTaskToFront = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_switchMultiWindowMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
///for AMS

public void restoreStack(android.os.IBinder token, boolean toMax) throws android.os.RemoteException;
public boolean moveActivityTaskToFront(android.os.IBinder token) throws android.os.RemoteException;
// Add for split-screen mode

public void switchMultiWindowMode() throws android.os.RemoteException;
}
