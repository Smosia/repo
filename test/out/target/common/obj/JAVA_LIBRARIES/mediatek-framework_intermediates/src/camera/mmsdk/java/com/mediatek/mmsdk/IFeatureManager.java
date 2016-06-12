/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IFeatureManager.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IFeatureManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IFeatureManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IFeatureManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IFeatureManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IFeatureManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IFeatureManager))) {
return ((com.mediatek.mmsdk.IFeatureManager)iin);
}
return new com.mediatek.mmsdk.IFeatureManager.Stub.Proxy(obj);
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
case TRANSACTION_setParameter:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.setParameter(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getParameter:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getParameter(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setUp:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.EffectHalVersion _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.EffectHalVersion.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.setUp(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_tearDown:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.EffectHalVersion _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.EffectHalVersion.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.tearDown(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getEffectFactory:
{
data.enforceInterface(DESCRIPTOR);
android.hardware.camera2.utils.BinderHolder _arg0;
_arg0 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.getEffectFactory(_arg0);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg0!=null)) {
reply.writeInt(1);
_arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IFeatureManager
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
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IMMSdkService.h	
     */// rest of 'int' return values in this file are actually status_t

@Override public int setParameter(java.lang.String key, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_setParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String getParameter(java.lang.String key) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
mRemote.transact(Stub.TRANSACTION_getParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setUp(com.mediatek.mmsdk.EffectHalVersion version) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((version!=null)) {
_data.writeInt(1);
version.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setUp, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int tearDown(com.mediatek.mmsdk.EffectHalVersion version) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((version!=null)) {
_data.writeInt(1);
version.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_tearDown, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getEffectFactory(android.hardware.camera2.utils.BinderHolder effectFactory) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getEffectFactory, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
effectFactory.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_setParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_tearDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getEffectFactory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IMMSdkService.h	
     */// rest of 'int' return values in this file are actually status_t

public int setParameter(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
public java.lang.String getParameter(java.lang.String key) throws android.os.RemoteException;
public int setUp(com.mediatek.mmsdk.EffectHalVersion version) throws android.os.RemoteException;
public int tearDown(com.mediatek.mmsdk.EffectHalVersion version) throws android.os.RemoteException;
public int getEffectFactory(android.hardware.camera2.utils.BinderHolder effectFactory) throws android.os.RemoteException;
}
