/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/voiceextension/IVoiceExtCommandManager.aidl
 */
package com.mediatek.common.voiceextension;
public interface IVoiceExtCommandManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.voiceextension.IVoiceExtCommandManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.voiceextension.IVoiceExtCommandManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.voiceextension.IVoiceExtCommandManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.voiceextension.IVoiceExtCommandManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.voiceextension.IVoiceExtCommandManager))) {
return ((com.mediatek.common.voiceextension.IVoiceExtCommandManager)iin);
}
return new com.mediatek.common.voiceextension.IVoiceExtCommandManager.Stub.Proxy(obj);
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
case TRANSACTION_createCommandSet:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.createCommandSet(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isCommandSetCreated:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.isCommandSetCreated(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCommandSetSelected:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getCommandSetSelected();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_deleteCommandSet:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.deleteCommandSet(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_selectCurrentCommandSet:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.selectCurrentCommandSet(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setCommandsStrArray:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _arg0;
_arg0 = data.createStringArray();
this.setCommandsStrArray(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setCommandsFile:
{
data.enforceInterface(DESCRIPTOR);
android.os.ParcelFileDescriptor _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.ParcelFileDescriptor.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.setCommandsFile(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_registerListener:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.voiceextension.IVoiceExtCommandListener _arg0;
_arg0 = com.mediatek.common.voiceextension.IVoiceExtCommandListener.Stub.asInterface(data.readStrongBinder());
int _result = this.registerListener(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCommands:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getCommands();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getCommandSets:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getCommandSets();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_startRecognition:
{
data.enforceInterface(DESCRIPTOR);
this.startRecognition();
reply.writeNoException();
return true;
}
case TRANSACTION_stopRecognition:
{
data.enforceInterface(DESCRIPTOR);
this.stopRecognition();
reply.writeNoException();
return true;
}
case TRANSACTION_pauseRecognition:
{
data.enforceInterface(DESCRIPTOR);
this.pauseRecognition();
reply.writeNoException();
return true;
}
case TRANSACTION_resumeRecognition:
{
data.enforceInterface(DESCRIPTOR);
this.resumeRecognition();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.voiceextension.IVoiceExtCommandManager
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
@Override public int createCommandSet(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_createCommandSet, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int isCommandSetCreated(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_isCommandSetCreated, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getCommandSetSelected() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCommandSetSelected, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int deleteCommandSet(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_deleteCommandSet, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int selectCurrentCommandSet(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_selectCurrentCommandSet, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setCommandsStrArray(java.lang.String[] commands) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringArray(commands);
mRemote.transact(Stub.TRANSACTION_setCommandsStrArray, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setCommandsFile(android.os.ParcelFileDescriptor pFd, int offset, int length) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((pFd!=null)) {
_data.writeInt(1);
pFd.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(offset);
_data.writeInt(length);
mRemote.transact(Stub.TRANSACTION_setCommandsFile, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int registerListener(com.mediatek.common.voiceextension.IVoiceExtCommandListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
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
@Override public java.lang.String[] getCommands() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCommands, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String[] getCommandSets() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCommandSets, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void startRecognition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopRecognition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void pauseRecognition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pauseRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resumeRecognition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resumeRecognition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_createCommandSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_isCommandSetCreated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getCommandSetSelected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_deleteCommandSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_selectCurrentCommandSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setCommandsStrArray = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setCommandsFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_registerListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getCommands = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getCommandSets = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_startRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_stopRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_pauseRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_resumeRecognition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
public int createCommandSet(java.lang.String name) throws android.os.RemoteException;
public int isCommandSetCreated(java.lang.String name) throws android.os.RemoteException;
public java.lang.String getCommandSetSelected() throws android.os.RemoteException;
public int deleteCommandSet(java.lang.String name) throws android.os.RemoteException;
public int selectCurrentCommandSet(java.lang.String name) throws android.os.RemoteException;
public void setCommandsStrArray(java.lang.String[] commands) throws android.os.RemoteException;
public void setCommandsFile(android.os.ParcelFileDescriptor pFd, int offset, int length) throws android.os.RemoteException;
public int registerListener(com.mediatek.common.voiceextension.IVoiceExtCommandListener listener) throws android.os.RemoteException;
public java.lang.String[] getCommands() throws android.os.RemoteException;
public java.lang.String[] getCommandSets() throws android.os.RemoteException;
public void startRecognition() throws android.os.RemoteException;
public void stopRecognition() throws android.os.RemoteException;
public void pauseRecognition() throws android.os.RemoteException;
public void resumeRecognition() throws android.os.RemoteException;
}
