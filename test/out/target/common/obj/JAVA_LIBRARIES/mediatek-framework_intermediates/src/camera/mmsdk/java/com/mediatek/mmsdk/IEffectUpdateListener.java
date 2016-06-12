/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IEffectUpdateListener.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IEffectUpdateListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IEffectUpdateListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IEffectUpdateListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IEffectUpdateListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IEffectUpdateListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IEffectUpdateListener))) {
return ((com.mediatek.mmsdk.IEffectUpdateListener)iin);
}
return new com.mediatek.mmsdk.IEffectUpdateListener.Stub.Proxy(obj);
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
case TRANSACTION_onEffectUpdated:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectUser _arg0;
_arg0 = com.mediatek.mmsdk.IEffectUser.Stub.asInterface(data.readStrongBinder());
int _arg1;
_arg1 = data.readInt();
this.onEffectUpdated(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IEffectUpdateListener
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
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IEffectUpdateListener.h	
     */
@Override public void onEffectUpdated(com.mediatek.mmsdk.IEffectUser effect, int info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
_data.writeInt(info);
mRemote.transact(Stub.TRANSACTION_onEffectUpdated, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onEffectUpdated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IEffectUpdateListener.h	
     */
public void onEffectUpdated(com.mediatek.mmsdk.IEffectUser effect, int info) throws android.os.RemoteException;
}
