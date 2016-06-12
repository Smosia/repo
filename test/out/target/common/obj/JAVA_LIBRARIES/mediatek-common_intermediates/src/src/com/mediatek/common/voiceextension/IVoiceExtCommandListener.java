/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/voiceextension/IVoiceExtCommandListener.aidl
 */
package com.mediatek.common.voiceextension;
public interface IVoiceExtCommandListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.voiceextension.IVoiceExtCommandListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.voiceextension.IVoiceExtCommandListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.voiceextension.IVoiceExtCommandListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.voiceextension.IVoiceExtCommandListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.voiceextension.IVoiceExtCommandListener))) {
return ((com.mediatek.common.voiceextension.IVoiceExtCommandListener)iin);
}
return new com.mediatek.common.voiceextension.IVoiceExtCommandListener.Stub.Proxy(obj);
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
case TRANSACTION_onStartRecognition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onStartRecognition(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onStopRecognition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onStopRecognition(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onPauseRecognition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onPauseRecognition(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onResumeRecognition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onResumeRecognition(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onCommandRecognized:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.onCommandRecognized(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onError(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onSetCommands:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onSetCommands(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.voiceextension.IVoiceExtCommandListener
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
    Callback from Voice Command Service for start result
    */
@Override public void onStartRecognition(int retCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
mRemote.transact(Stub.TRANSACTION_onStartRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    Callback from Voice Command Service for stopped result
    */
@Override public void onStopRecognition(int retCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
mRemote.transact(Stub.TRANSACTION_onStopRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    Callback from Voice Command Service for paused result
    */
@Override public void onPauseRecognition(int retCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
mRemote.transact(Stub.TRANSACTION_onPauseRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    Callback from Voice Command Service for resumed result
    */
@Override public void onResumeRecognition(int retCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
mRemote.transact(Stub.TRANSACTION_onResumeRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    Callback from Voice Command Service for recognized result
    */
@Override public void onCommandRecognized(int retCode, int commandId, java.lang.String commandStr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
_data.writeInt(commandId);
_data.writeString(commandStr);
mRemote.transact(Stub.TRANSACTION_onCommandRecognized, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    Callback from Voice Command Service when error happened
    */
@Override public void onError(int retCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
mRemote.transact(Stub.TRANSACTION_onError, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    Callback from Voice Command Service for commands setting result
    */
@Override public void onSetCommands(int retCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(retCode);
mRemote.transact(Stub.TRANSACTION_onSetCommands, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onStartRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onStopRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onPauseRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onResumeRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onCommandRecognized = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onSetCommands = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
/**
    Callback from Voice Command Service for start result
    */
public void onStartRecognition(int retCode) throws android.os.RemoteException;
/**
    Callback from Voice Command Service for stopped result
    */
public void onStopRecognition(int retCode) throws android.os.RemoteException;
/**
    Callback from Voice Command Service for paused result
    */
public void onPauseRecognition(int retCode) throws android.os.RemoteException;
/**
    Callback from Voice Command Service for resumed result
    */
public void onResumeRecognition(int retCode) throws android.os.RemoteException;
/**
    Callback from Voice Command Service for recognized result
    */
public void onCommandRecognized(int retCode, int commandId, java.lang.String commandStr) throws android.os.RemoteException;
/**
    Callback from Voice Command Service when error happened
    */
public void onError(int retCode) throws android.os.RemoteException;
/**
    Callback from Voice Command Service for commands setting result
    */
public void onSetCommands(int retCode) throws android.os.RemoteException;
}
