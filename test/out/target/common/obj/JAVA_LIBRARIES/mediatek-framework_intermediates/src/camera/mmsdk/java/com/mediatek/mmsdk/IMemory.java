/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IMemory.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IMemory extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IMemory
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IMemory";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IMemory interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IMemory asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IMemory))) {
return ((com.mediatek.mmsdk.IMemory)iin);
}
return new com.mediatek.mmsdk.IMemory.Stub.Proxy(obj);
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
case TRANSACTION_getMemory:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
com.mediatek.mmsdk.IMemoryHeap _result = this.getMemory(_arg0, _arg1);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IMemory
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
@Override public com.mediatek.mmsdk.IMemoryHeap getMemory(int offset, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.mmsdk.IMemoryHeap _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(offset);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_getMemory, _data, _reply, 0);
_reply.readException();
_result = com.mediatek.mmsdk.IMemoryHeap.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getMemory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Keep up-to-date with frameworks/native/include/binder/IMemory.h	
     */
public com.mediatek.mmsdk.IMemoryHeap getMemory(int offset, int size) throws android.os.RemoteException;
}
