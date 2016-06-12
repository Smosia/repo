/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IMMSdkService.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IMMSdkService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IMMSdkService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IMMSdkService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IMMSdkService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IMMSdkService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IMMSdkService))) {
return ((com.mediatek.mmsdk.IMMSdkService)iin);
}
return new com.mediatek.mmsdk.IMMSdkService.Stub.Proxy(obj);
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
case TRANSACTION_connectImageTransformUser:
{
data.enforceInterface(DESCRIPTOR);
android.hardware.camera2.utils.BinderHolder _arg0;
_arg0 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.connectImageTransformUser(_arg0);
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
case TRANSACTION_connectEffect:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.hardware.camera2.utils.BinderHolder _arg1;
_arg1 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.connectEffect(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
if ((_arg1!=null)) {
reply.writeInt(1);
_arg1.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_connectGesture:
{
data.enforceInterface(DESCRIPTOR);
android.hardware.camera2.utils.BinderHolder _arg0;
_arg0 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.connectGesture(_arg0);
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
case TRANSACTION_connectHeartrate:
{
data.enforceInterface(DESCRIPTOR);
android.hardware.camera2.utils.BinderHolder _arg0;
_arg0 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.connectHeartrate(_arg0);
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
case TRANSACTION_disconnectHeartrate:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.disconnectHeartrate();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_connectFeatureManager:
{
data.enforceInterface(DESCRIPTOR);
android.hardware.camera2.utils.BinderHolder _arg0;
_arg0 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.connectFeatureManager(_arg0);
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
private static class Proxy implements com.mediatek.mmsdk.IMMSdkService
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

@Override public int connectImageTransformUser(android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_connectImageTransformUser, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
client.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int connectEffect(java.lang.String clientName, android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(clientName);
mRemote.transact(Stub.TRANSACTION_connectEffect, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
client.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int connectGesture(android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_connectGesture, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
client.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int connectHeartrate(android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_connectHeartrate, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
client.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int disconnectHeartrate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disconnectHeartrate, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int connectFeatureManager(android.hardware.camera2.utils.BinderHolder featureManager) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_connectFeatureManager, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
featureManager.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_connectImageTransformUser = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_connectEffect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_connectGesture = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_connectHeartrate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_disconnectHeartrate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_connectFeatureManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IMMSdkService.h	
     */// rest of 'int' return values in this file are actually status_t

public int connectImageTransformUser(android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException;
public int connectEffect(java.lang.String clientName, android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException;
public int connectGesture(android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException;
public int connectHeartrate(android.hardware.camera2.utils.BinderHolder client) throws android.os.RemoteException;
public int disconnectHeartrate() throws android.os.RemoteException;
public int connectFeatureManager(android.hardware.camera2.utils.BinderHolder featureManager) throws android.os.RemoteException;
}
