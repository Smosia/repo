/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IMemoryHeap.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IMemoryHeap extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IMemoryHeap
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IMemoryHeap";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IMemoryHeap interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IMemoryHeap asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IMemoryHeap))) {
return ((com.mediatek.mmsdk.IMemoryHeap)iin);
}
return new com.mediatek.mmsdk.IMemoryHeap.Stub.Proxy(obj);
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
case TRANSACTION_getHeapID:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.getHeapID(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getBase:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getBase();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getSize:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getSize();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getFlags:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getFlags();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getOffset:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getOffset();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IMemoryHeap
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
     * Keep up-to-date with frameworks/native/include/binder/IMemory.h	
     */
@Override public int getHeapID(int offset, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(offset);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_getHeapID, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getBase() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBase, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getSize() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSize, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getFlags() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFlags, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getOffset() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOffset, _data, _reply, 0);
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
static final int TRANSACTION_getHeapID = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getBase = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getSize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getFlags = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getOffset = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
/**
     * Keep up-to-date with frameworks/native/include/binder/IMemory.h	
     */
public int getHeapID(int offset, int size) throws android.os.RemoteException;
public int getBase() throws android.os.RemoteException;
public int getSize() throws android.os.RemoteException;
public int getFlags() throws android.os.RemoteException;
public int getOffset() throws android.os.RemoteException;
}
