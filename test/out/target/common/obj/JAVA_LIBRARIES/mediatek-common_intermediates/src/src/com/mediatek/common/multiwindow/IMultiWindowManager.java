/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/multiwindow/IMultiWindowManager.aidl
 */
package com.mediatek.common.multiwindow;
/**
 * @hide
 */
public interface IMultiWindowManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.multiwindow.IMultiWindowManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.multiwindow.IMultiWindowManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.multiwindow.IMultiWindowManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.multiwindow.IMultiWindowManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.multiwindow.IMultiWindowManager))) {
return ((com.mediatek.common.multiwindow.IMultiWindowManager)iin);
}
return new com.mediatek.common.multiwindow.IMultiWindowManager.Stub.Proxy(obj);
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
case TRANSACTION_moveActivityTaskToFront:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.moveActivityTaskToFront(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_closeWindow:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.closeWindow(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_restoreWindow:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.restoreWindow(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setAMSCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.multiwindow.IMWAmsCallback _arg0;
_arg0 = com.mediatek.common.multiwindow.IMWAmsCallback.Stub.asInterface(data.readStrongBinder());
this.setAMSCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSystemUiCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.multiwindow.IMWSystemUiCallback _arg0;
_arg0 = com.mediatek.common.multiwindow.IMWSystemUiCallback.Stub.asInterface(data.readStrongBinder());
this.setSystemUiCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stickWindow:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.stickWindow(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isFloatingStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isFloatingStack(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setWMSCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.multiwindow.IMWWmsCallback _arg0;
_arg0 = com.mediatek.common.multiwindow.IMWWmsCallback.Stub.asInterface(data.readStrongBinder());
this.setWMSCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isStickStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isStickStack(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isInMiniMax:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.isInMiniMax(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
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
case TRANSACTION_showRestoreButton:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.showRestoreButton(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isSticky:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _result = this.isSticky(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_activityCreated:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.activityCreated(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onTaskAdded:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onTaskAdded(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onTaskRemoved:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onTaskRemoved(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addToken:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
android.os.IBinder _arg1;
_arg1 = data.readStrongBinder();
this.addToken(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_removeToken:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.removeToken(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAppTokenAdded:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
int _arg1;
_arg1 = data.readInt();
this.onAppTokenAdded(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onAppTokenRemoved:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
int _arg1;
_arg1 = data.readInt();
this.onAppTokenRemoved(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.addStack(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_removeStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.removeStack(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_isFloatingByAppToken:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _result = this.isFloatingByAppToken(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isFloatingByWinToken:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
boolean _result = this.isFloatingByWinToken(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getStackPosition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getStackPosition(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_computeStackPosition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.computeStackPosition(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_resetStackPosition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.resetStackPosition(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isSplitMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSplitMode();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_switchMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.switchMode(_arg0);
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
case TRANSACTION_getNextFloatStack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getNextFloatStack(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.multiwindow.IMultiWindowManager
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
@Override public void moveActivityTaskToFront(android.os.IBinder token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
mRemote.transact(Stub.TRANSACTION_moveActivityTaskToFront, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void closeWindow(android.os.IBinder token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
mRemote.transact(Stub.TRANSACTION_closeWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void restoreWindow(android.os.IBinder token, boolean toMax) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
_data.writeInt(((toMax)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_restoreWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setAMSCallback(com.mediatek.common.multiwindow.IMWAmsCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setAMSCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSystemUiCallback(com.mediatek.common.multiwindow.IMWSystemUiCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setSystemUiCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stickWindow(android.os.IBinder token, boolean isSticky) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
_data.writeInt(((isSticky)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_stickWindow, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isFloatingStack(int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_isFloatingStack, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// for Window Manager Service

@Override public void setWMSCallback(com.mediatek.common.multiwindow.IMWWmsCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setWMSCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isStickStack(int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_isStickStack, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isInMiniMax(int taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(taskId);
mRemote.transact(Stub.TRANSACTION_isInMiniMax, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
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
// for showing restore button on systemUI module

@Override public void showRestoreButton(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_showRestoreButton, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isSticky(android.os.IBinder token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
mRemote.transact(Stub.TRANSACTION_isSticky, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void activityCreated(android.os.IBinder token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
mRemote.transact(Stub.TRANSACTION_activityCreated, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onTaskAdded(int taskId, int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(taskId);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_onTaskAdded, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onTaskRemoved(int taskId, int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(taskId);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_onTaskRemoved, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addToken(android.os.IBinder winToken, android.os.IBinder appToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(winToken);
_data.writeStrongBinder(appToken);
mRemote.transact(Stub.TRANSACTION_addToken, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeToken(android.os.IBinder winToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(winToken);
mRemote.transact(Stub.TRANSACTION_removeToken, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAppTokenAdded(android.os.IBinder appToken, int taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(appToken);
_data.writeInt(taskId);
mRemote.transact(Stub.TRANSACTION_onAppTokenAdded, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAppTokenRemoved(android.os.IBinder appToken, int taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(appToken);
_data.writeInt(taskId);
mRemote.transact(Stub.TRANSACTION_onAppTokenRemoved, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addStack(int stackId, boolean isFloating) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
_data.writeInt(((isFloating)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_addStack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeStack(int stackId, boolean isFloating) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
_data.writeInt(((isFloating)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_removeStack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isFloatingByAppToken(android.os.IBinder appToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(appToken);
mRemote.transact(Stub.TRANSACTION_isFloatingByAppToken, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isFloatingByWinToken(android.os.IBinder winToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(winToken);
mRemote.transact(Stub.TRANSACTION_isFloatingByWinToken, _data, _reply, 0);
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

@Override public int getStackPosition(int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_getStackPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int computeStackPosition(int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_computeStackPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void resetStackPosition(int stackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(stackId);
mRemote.transact(Stub.TRANSACTION_resetStackPosition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isSplitMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSplitMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void switchMode(boolean isSplit) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isSplit)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_switchMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
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
@Override public int getNextFloatStack(int curStackId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(curStackId);
mRemote.transact(Stub.TRANSACTION_getNextFloatStack, _data, _reply, 0);
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
static final int TRANSACTION_moveActivityTaskToFront = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_closeWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_restoreWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setAMSCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setSystemUiCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_stickWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isFloatingStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setWMSCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_isStickStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_isInMiniMax = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_moveFloatingWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_resizeFloatingWindow = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_enableFocusedFrame = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_miniMaxTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_showRestoreButton = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_isSticky = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_activityCreated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_onTaskAdded = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_onTaskRemoved = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_addToken = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_removeToken = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_onAppTokenAdded = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_onAppTokenRemoved = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_addStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_removeStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_isFloatingByAppToken = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_isFloatingByWinToken = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getStackPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_computeStackPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_resetStackPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_isSplitMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_switchMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_resizeAndMoveStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getNextFloatStack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
}
public void moveActivityTaskToFront(android.os.IBinder token) throws android.os.RemoteException;
public void closeWindow(android.os.IBinder token) throws android.os.RemoteException;
public void restoreWindow(android.os.IBinder token, boolean toMax) throws android.os.RemoteException;
public void setAMSCallback(com.mediatek.common.multiwindow.IMWAmsCallback cb) throws android.os.RemoteException;
public void setSystemUiCallback(com.mediatek.common.multiwindow.IMWSystemUiCallback cb) throws android.os.RemoteException;
public void stickWindow(android.os.IBinder token, boolean isSticky) throws android.os.RemoteException;
public boolean isFloatingStack(int stackId) throws android.os.RemoteException;
// for Window Manager Service

public void setWMSCallback(com.mediatek.common.multiwindow.IMWWmsCallback cb) throws android.os.RemoteException;
public boolean isStickStack(int stackId) throws android.os.RemoteException;
public boolean isInMiniMax(int taskId) throws android.os.RemoteException;
/// called  by window manager policy

public void moveFloatingWindow(int disX, int disY) throws android.os.RemoteException;
public void resizeFloatingWindow(int direction, int deltaX, int deltaY) throws android.os.RemoteException;
public void enableFocusedFrame(boolean enable) throws android.os.RemoteException;
/// called by ActivityStackSupervisor

public void miniMaxTask(int taskId) throws android.os.RemoteException;
// for showing restore button on systemUI module

public void showRestoreButton(boolean flag) throws android.os.RemoteException;
public boolean isSticky(android.os.IBinder token) throws android.os.RemoteException;
public void activityCreated(android.os.IBinder token) throws android.os.RemoteException;
public void onTaskAdded(int taskId, int stackId) throws android.os.RemoteException;
public void onTaskRemoved(int taskId, int stackId) throws android.os.RemoteException;
public void addToken(android.os.IBinder winToken, android.os.IBinder appToken) throws android.os.RemoteException;
public void removeToken(android.os.IBinder winToken) throws android.os.RemoteException;
public void onAppTokenAdded(android.os.IBinder appToken, int taskId) throws android.os.RemoteException;
public void onAppTokenRemoved(android.os.IBinder appToken, int taskId) throws android.os.RemoteException;
public void addStack(int stackId, boolean isFloating) throws android.os.RemoteException;
public void removeStack(int stackId, boolean isFloating) throws android.os.RemoteException;
public boolean isFloatingByAppToken(android.os.IBinder appToken) throws android.os.RemoteException;
public boolean isFloatingByWinToken(android.os.IBinder winToken) throws android.os.RemoteException;
// Add for split-screen mode

public int getStackPosition(int stackId) throws android.os.RemoteException;
public int computeStackPosition(int stackId) throws android.os.RemoteException;
public void resetStackPosition(int stackId) throws android.os.RemoteException;
public boolean isSplitMode() throws android.os.RemoteException;
public void switchMode(boolean isSplit) throws android.os.RemoteException;
public void resizeAndMoveStack(int stackId) throws android.os.RemoteException;
public int getNextFloatStack(int curStackId) throws android.os.RemoteException;
}
