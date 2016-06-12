/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/msgmonitorservice/IMessageLoggerWrapper.aidl
 */
package com.mediatek.msgmonitorservice;
/** @hide */
public interface IMessageLoggerWrapper extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.msgmonitorservice.IMessageLoggerWrapper
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.msgmonitorservice.IMessageLoggerWrapper";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.msgmonitorservice.IMessageLoggerWrapper interface,
 * generating a proxy if needed.
 */
public static com.mediatek.msgmonitorservice.IMessageLoggerWrapper asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.msgmonitorservice.IMessageLoggerWrapper))) {
return ((com.mediatek.msgmonitorservice.IMessageLoggerWrapper)iin);
}
return new com.mediatek.msgmonitorservice.IMessageLoggerWrapper.Stub.Proxy(obj);
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
case TRANSACTION_unregisterMsgLogger:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.unregisterMsgLogger(_arg0);
return true;
}
case TRANSACTION_dumpAllMessageHistory:
{
data.enforceInterface(DESCRIPTOR);
this.dumpAllMessageHistory();
return true;
}
case TRANSACTION_dumpMSGHistorybyName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.dumpMSGHistorybyName(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.msgmonitorservice.IMessageLoggerWrapper
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
@Override public void unregisterMsgLogger(java.lang.String msgLoggerName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgLoggerName);
mRemote.transact(Stub.TRANSACTION_unregisterMsgLogger, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void dumpAllMessageHistory() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dumpAllMessageHistory, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void dumpMSGHistorybyName(java.lang.String msgLoggerName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgLoggerName);
mRemote.transact(Stub.TRANSACTION_dumpMSGHistorybyName, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_unregisterMsgLogger = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_dumpAllMessageHistory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_dumpMSGHistorybyName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void unregisterMsgLogger(java.lang.String msgLoggerName) throws android.os.RemoteException;
public void dumpAllMessageHistory() throws android.os.RemoteException;
public void dumpMSGHistorybyName(java.lang.String msgLoggerName) throws android.os.RemoteException;
}
