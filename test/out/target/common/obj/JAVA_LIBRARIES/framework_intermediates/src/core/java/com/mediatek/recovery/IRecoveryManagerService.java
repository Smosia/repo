/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/recovery/IRecoveryManagerService.aidl
 */
package com.mediatek.recovery;
/** @hide */
public interface IRecoveryManagerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.recovery.IRecoveryManagerService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.recovery.IRecoveryManagerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.recovery.IRecoveryManagerService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.recovery.IRecoveryManagerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.recovery.IRecoveryManagerService))) {
return ((com.mediatek.recovery.IRecoveryManagerService)iin);
}
return new com.mediatek.recovery.IRecoveryManagerService.Stub.Proxy(obj);
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
case TRANSACTION_systemReady:
{
data.enforceInterface(DESCRIPTOR);
this.systemReady();
reply.writeNoException();
return true;
}
case TRANSACTION_backupSingleFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.backupSingleFile(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getVersion:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getVersion();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_startBootMonitor:
{
data.enforceInterface(DESCRIPTOR);
this.startBootMonitor();
reply.writeNoException();
return true;
}
case TRANSACTION_stopBootMonitor:
{
data.enforceInterface(DESCRIPTOR);
this.stopBootMonitor();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.recovery.IRecoveryManagerService
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
@Override public void systemReady() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_systemReady, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * Interface other module to perform backup in RecoveryManager
	 */
@Override public int backupSingleFile(java.lang.String moduleName, java.lang.String fileName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(moduleName);
_data.writeString(fileName);
mRemote.transact(Stub.TRANSACTION_backupSingleFile, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get the version of RecoveryManagerService
     *
     * @return Returns version of RecoveryManagerService.
     */
@Override public java.lang.String getVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//void parseException(java.lang.Throwable e, boolean throwOnError);

@Override public void startBootMonitor() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startBootMonitor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopBootMonitor() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopBootMonitor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_systemReady = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_backupSingleFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_startBootMonitor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_stopBootMonitor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void systemReady() throws android.os.RemoteException;
/**
	 * Interface other module to perform backup in RecoveryManager
	 */
public int backupSingleFile(java.lang.String moduleName, java.lang.String fileName) throws android.os.RemoteException;
/**
     * Get the version of RecoveryManagerService
     *
     * @return Returns version of RecoveryManagerService.
     */
public java.lang.String getVersion() throws android.os.RemoteException;
//void parseException(java.lang.Throwable e, boolean throwOnError);

public void startBootMonitor() throws android.os.RemoteException;
public void stopBootMonitor() throws android.os.RemoteException;
}
