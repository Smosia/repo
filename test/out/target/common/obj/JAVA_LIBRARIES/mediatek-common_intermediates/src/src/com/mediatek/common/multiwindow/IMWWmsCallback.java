/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/multiwindow/IMWWmsCallback.aidl
 */
package com.mediatek.common.multiwindow;
/**
 * System private API for talking with the window manager service.
 *
 * {@hide}
 */
public interface IMWWmsCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.multiwindow.IMWWmsCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.multiwindow.IMWWmsCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.multiwindow.IMWWmsCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.multiwindow.IMWWmsCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.multiwindow.IMWWmsCallback))) {
return ((com.mediatek.common.multiwindow.IMWWmsCallback)iin);
}
return new com.mediatek.common.multiwindow.IMWWmsCallback.Stub.Proxy(obj);
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
case TRANSACTION_moveFloatingWindow:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.moveFloatingWindow(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_resizeFloatingWindow:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.resizeFloatingWindow(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_enableFocusedFrame:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.enableFocusedFrame(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_miniMaxTask:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.miniMaxTask(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_resizeAndMoveStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.resizeAndMoveStack(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.multiwindow.IMWWmsCallback
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
///for WMS
/// called  by window manager policy

@Override public void moveFloatingWindow(int disX, int disY) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(disX);
_data.writeInt(disY);
mRemote.transact(Stub.TRANSACTION_moveFloatingWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/// called  by window manager policy

@Override public void resizeFloatingWindow(int direction, int deltaX, int deltaY) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(direction);
_data.writeInt(deltaX);
_data.writeInt(deltaY);
mRemote.transact(Stub.TRANSACTION_resizeFloatingWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/// called  by window manager policy

@Override public void enableFocusedFrame(boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enableFocusedFrame, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/// called by ActivityStackSupervisor

@Override public void miniMaxTask(int taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(taskId);
mRemote.transact(Stub.TRANSACTION_miniMaxTask, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
// Add for split-screen mode

@Override public void resizeAndMoveStack(int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_resizeAndMoveStack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_moveFloatingWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_resizeFloatingWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_enableFocusedFrame = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_miniMaxTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_resizeAndMoveStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
///for WMS
/// called  by window manager policy

public void moveFloatingWindow(int disX, int disY) throws android.os.RemoteException;
/// called  by window manager policy

public void resizeFloatingWindow(int direction, int deltaX, int deltaY) throws android.os.RemoteException;
/// called  by window manager policy

public void enableFocusedFrame(boolean enable) throws android.os.RemoteException;
/// called by ActivityStackSupervisor

public void miniMaxTask(int taskId) throws android.os.RemoteException;
// Add for split-screen mode

public void resizeAndMoveStack(int stackId) throws android.os.RemoteException;
}
