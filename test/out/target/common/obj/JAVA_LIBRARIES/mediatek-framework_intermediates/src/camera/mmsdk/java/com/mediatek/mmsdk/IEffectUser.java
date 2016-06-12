/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IEffectUser.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IEffectUser extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IEffectUser
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IEffectUser";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IEffectUser interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IEffectUser asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IEffectUser))) {
return ((com.mediatek.mmsdk.IEffectUser)iin);
}
return new com.mediatek.mmsdk.IEffectUser.Stub.Proxy(obj);
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
case TRANSACTION_getName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_apply:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.ImageInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.ImageInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.mmsdk.IMemory _arg1;
_arg1 = com.mediatek.mmsdk.IMemory.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.ImageInfo _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.mmsdk.ImageInfo.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.mmsdk.IMemory _arg3;
_arg3 = com.mediatek.mmsdk.IMemory.Stub.asInterface(data.readStrongBinder());
boolean _result = this.apply(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setParameter:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.setParameter(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setUpdateListener:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectUpdateListener _arg0;
_arg0 = com.mediatek.mmsdk.IEffectUpdateListener.Stub.asInterface(data.readStrongBinder());
this.setUpdateListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_release:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.release();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IEffectUser
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
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IEffectUser.h	
     */
@Override public java.lang.String getName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean apply(com.mediatek.mmsdk.ImageInfo rSrcImage, com.mediatek.mmsdk.IMemory srcData, com.mediatek.mmsdk.ImageInfo rDestImage, com.mediatek.mmsdk.IMemory destData) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((rSrcImage!=null)) {
_data.writeInt(1);
rSrcImage.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((srcData!=null))?(srcData.asBinder()):(null)));
if ((rDestImage!=null)) {
_data.writeInt(1);
rDestImage.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((destData!=null))?(destData.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_apply, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setParameter(java.lang.String parameterKey, int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(parameterKey);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_setParameter, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setUpdateListener(com.mediatek.mmsdk.IEffectUpdateListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setUpdateListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean release() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_release, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_apply = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setUpdateListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_release = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IEffectUser.h	
     */
public java.lang.String getName() throws android.os.RemoteException;
public boolean apply(com.mediatek.mmsdk.ImageInfo rSrcImage, com.mediatek.mmsdk.IMemory srcData, com.mediatek.mmsdk.ImageInfo rDestImage, com.mediatek.mmsdk.IMemory destData) throws android.os.RemoteException;
public boolean setParameter(java.lang.String parameterKey, int value) throws android.os.RemoteException;
public void setUpdateListener(com.mediatek.mmsdk.IEffectUpdateListener listener) throws android.os.RemoteException;
public boolean release() throws android.os.RemoteException;
}
