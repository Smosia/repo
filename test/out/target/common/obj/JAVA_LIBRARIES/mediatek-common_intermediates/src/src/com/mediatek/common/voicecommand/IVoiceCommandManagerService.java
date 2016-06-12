/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/voicecommand/IVoiceCommandManagerService.aidl
 */
package com.mediatek.common.voicecommand;
public interface IVoiceCommandManagerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.voicecommand.IVoiceCommandManagerService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.voicecommand.IVoiceCommandManagerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.voicecommand.IVoiceCommandManagerService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.voicecommand.IVoiceCommandManagerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.voicecommand.IVoiceCommandManagerService))) {
return ((com.mediatek.common.voicecommand.IVoiceCommandManagerService)iin);
}
return new com.mediatek.common.voicecommand.IVoiceCommandManagerService.Stub.Proxy(obj);
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
case TRANSACTION_registerListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.mediatek.common.voicecommand.IVoiceCommandListener _arg1;
_arg1 = com.mediatek.common.voicecommand.IVoiceCommandListener.Stub.asInterface(data.readStrongBinder());
int _result = this.registerListener(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_unregisterListener:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.mediatek.common.voicecommand.IVoiceCommandListener _arg1;
_arg1 = com.mediatek.common.voicecommand.IVoiceCommandListener.Stub.asInterface(data.readStrongBinder());
int _result = this.unregisterListener(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_sendCommand:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
android.os.Bundle _arg3;
if ((0!=data.readInt())) {
_arg3 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
int _result = this.sendCommand(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.voicecommand.IVoiceCommandManagerService
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
@Override public int registerListener(java.lang.String pkgName, com.mediatek.common.voicecommand.IVoiceCommandListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pkgName);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerListener, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int unregisterListener(java.lang.String pkgName, com.mediatek.common.voicecommand.IVoiceCommandListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pkgName);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterListener, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int sendCommand(java.lang.String pkgName, int mainAction, int subAction, android.os.Bundle extraData) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(pkgName);
_data.writeInt(mainAction);
_data.writeInt(subAction);
if ((extraData!=null)) {
_data.writeInt(1);
extraData.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_sendCommand, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_registerListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_sendCommand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public int registerListener(java.lang.String pkgName, com.mediatek.common.voicecommand.IVoiceCommandListener listener) throws android.os.RemoteException;
public int unregisterListener(java.lang.String pkgName, com.mediatek.common.voicecommand.IVoiceCommandListener listener) throws android.os.RemoteException;
public int sendCommand(java.lang.String pkgName, int mainAction, int subAction, android.os.Bundle extraData) throws android.os.RemoteException;
}
