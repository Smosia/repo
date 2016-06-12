/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/msgmonitorservice/IMessageLogger.aidl
 */
package com.mediatek.msgmonitorservice;
/**
  * M:Message Monitor Service for dump message history
  *
  * @hide
  *
  * @internal
  */
public interface IMessageLogger extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.msgmonitorservice.IMessageLogger
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.msgmonitorservice.IMessageLogger";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.msgmonitorservice.IMessageLogger interface,
 * generating a proxy if needed.
 */
public static com.mediatek.msgmonitorservice.IMessageLogger asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.msgmonitorservice.IMessageLogger))) {
return ((com.mediatek.msgmonitorservice.IMessageLogger)iin);
}
return new com.mediatek.msgmonitorservice.IMessageLogger.Stub.Proxy(obj);
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
case TRANSACTION_registerMsgLogger:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
com.mediatek.msgmonitorservice.IMessageLoggerWrapper _arg3;
_arg3 = com.mediatek.msgmonitorservice.IMessageLoggerWrapper.Stub.asInterface(data.readStrongBinder());
this.registerMsgLogger(_arg0, _arg1, _arg2, _arg3);
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
int _arg0;
_arg0 = data.readInt();
this.dumpAllMessageHistory(_arg0);
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
case TRANSACTION_dumpCallStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.dumpCallStack(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.msgmonitorservice.IMessageLogger
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
@Override public void registerMsgLogger(java.lang.String msgLoggerName, int pid, int tid, com.mediatek.msgmonitorservice.IMessageLoggerWrapper callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgLoggerName);
_data.writeInt(pid);
_data.writeInt(tid);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerMsgLogger, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/**
      * M:UnRegister Message Logger for message history dump
      *
      * @internal
      */
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
/**
      * M:Dump message history
      *
      * @internal
      */
@Override public void dumpAllMessageHistory(int pid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pid);
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
@Override public void dumpCallStack(int Pid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(Pid);
mRemote.transact(Stub.TRANSACTION_dumpCallStack, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_registerMsgLogger = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterMsgLogger = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_dumpAllMessageHistory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_dumpMSGHistorybyName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_dumpCallStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void registerMsgLogger(java.lang.String msgLoggerName, int pid, int tid, com.mediatek.msgmonitorservice.IMessageLoggerWrapper callback) throws android.os.RemoteException;
/**
      * M:UnRegister Message Logger for message history dump
      *
      * @internal
      */
public void unregisterMsgLogger(java.lang.String msgLoggerName) throws android.os.RemoteException;
/**
      * M:Dump message history
      *
      * @internal
      */
public void dumpAllMessageHistory(int pid) throws android.os.RemoteException;
public void dumpMSGHistorybyName(java.lang.String msgLoggerName) throws android.os.RemoteException;
public void dumpCallStack(int Pid) throws android.os.RemoteException;
}
