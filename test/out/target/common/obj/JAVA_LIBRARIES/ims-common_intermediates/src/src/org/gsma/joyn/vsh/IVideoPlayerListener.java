/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/vsh/IVideoPlayerListener.aidl
 */
package org.gsma.joyn.vsh;
/**
 * Video player event listener interface
 */
public interface IVideoPlayerListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.vsh.IVideoPlayerListener
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.vsh.IVideoPlayerListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.vsh.IVideoPlayerListener interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.vsh.IVideoPlayerListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.vsh.IVideoPlayerListener))) {
return ((org.gsma.joyn.vsh.IVideoPlayerListener)iin);
}
return new org.gsma.joyn.vsh.IVideoPlayerListener.Stub.Proxy(obj);
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
case TRANSACTION_onPlayerOpened:
{
data.enforceInterface(DESCRIPTOR);
this.onPlayerOpened();
reply.writeNoException();
return true;
}
case TRANSACTION_onPlayerStarted:
{
data.enforceInterface(DESCRIPTOR);
this.onPlayerStarted();
reply.writeNoException();
return true;
}
case TRANSACTION_onPlayerStopped:
{
data.enforceInterface(DESCRIPTOR);
this.onPlayerStopped();
reply.writeNoException();
return true;
}
case TRANSACTION_onPlayerClosed:
{
data.enforceInterface(DESCRIPTOR);
this.onPlayerClosed();
reply.writeNoException();
return true;
}
case TRANSACTION_onPlayerFailed:
{
data.enforceInterface(DESCRIPTOR);
this.onPlayerFailed();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.vsh.IVideoPlayerListener
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
@Override public void onPlayerOpened() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onPlayerOpened, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPlayerStarted() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onPlayerStarted, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPlayerStopped() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onPlayerStopped, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPlayerClosed() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onPlayerClosed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onPlayerFailed() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onPlayerFailed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onPlayerOpened = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onPlayerStarted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onPlayerStopped = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onPlayerClosed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onPlayerFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void onPlayerOpened() throws android.os.RemoteException;
public void onPlayerStarted() throws android.os.RemoteException;
public void onPlayerStopped() throws android.os.RemoteException;
public void onPlayerClosed() throws android.os.RemoteException;
public void onPlayerFailed() throws android.os.RemoteException;
}
