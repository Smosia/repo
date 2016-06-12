/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/multiwindow/IMWBlackList.aidl
 */
package com.mediatek.common.multiwindow;
/**
 * System private API for talking with the alarm manager service.
 *
 * {@hide}
 */
public interface IMWBlackList extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.multiwindow.IMWBlackList
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.multiwindow.IMWBlackList";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.multiwindow.IMWBlackList interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.multiwindow.IMWBlackList asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.multiwindow.IMWBlackList))) {
return ((com.mediatek.common.multiwindow.IMWBlackList)iin);
}
return new com.mediatek.common.multiwindow.IMWBlackList.Stub.Proxy(obj);
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
case TRANSACTION_shouldChangeConfig:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.shouldChangeConfig(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_shouldRestartWhenMiniMax:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.shouldRestartWhenMiniMax(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getWhiteList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _result = this.getWhiteList();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
case TRANSACTION_inWhiteList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.inWhiteList(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_addIntoWhiteList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.addIntoWhiteList(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addMoreIntoWhiteList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
this.addMoreIntoWhiteList(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeFromWhiteList:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.removeFromWhiteList(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.multiwindow.IMWBlackList
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
@Override public boolean shouldChangeConfig(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_shouldChangeConfig, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean shouldRestartWhenMiniMax(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_shouldRestartWhenMiniMax, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<java.lang.String> getWhiteList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<java.lang.String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWhiteList, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean inWhiteList(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_inWhiteList, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void addIntoWhiteList(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_addIntoWhiteList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addMoreIntoWhiteList(java.util.List<java.lang.String> packageList) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(packageList);
mRemote.transact(Stub.TRANSACTION_addMoreIntoWhiteList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeFromWhiteList(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_removeFromWhiteList, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_shouldChangeConfig = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_shouldRestartWhenMiniMax = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getWhiteList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_inWhiteList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_addIntoWhiteList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_addMoreIntoWhiteList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_removeFromWhiteList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public boolean shouldChangeConfig(java.lang.String packageName) throws android.os.RemoteException;
public boolean shouldRestartWhenMiniMax(java.lang.String packageName) throws android.os.RemoteException;
public java.util.List<java.lang.String> getWhiteList() throws android.os.RemoteException;
public boolean inWhiteList(java.lang.String packageName) throws android.os.RemoteException;
public void addIntoWhiteList(java.lang.String packageName) throws android.os.RemoteException;
public void addMoreIntoWhiteList(java.util.List<java.lang.String> packageList) throws android.os.RemoteException;
public void removeFromWhiteList(java.lang.String packageName) throws android.os.RemoteException;
}
