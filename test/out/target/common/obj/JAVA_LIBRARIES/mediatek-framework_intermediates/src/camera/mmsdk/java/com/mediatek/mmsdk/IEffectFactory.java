/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IEffectFactory.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IEffectFactory extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IEffectFactory
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IEffectFactory";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IEffectFactory interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IEffectFactory asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IEffectFactory))) {
return ((com.mediatek.mmsdk.IEffectFactory)iin);
}
return new com.mediatek.mmsdk.IEffectFactory.Stub.Proxy(obj);
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
case TRANSACTION_createEffectHal:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.EffectHalVersion _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.EffectHalVersion.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.hardware.camera2.utils.BinderHolder _arg1;
_arg1 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.createEffectHal(_arg0, _arg1);
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
case TRANSACTION_createEffectHalClient:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.EffectHalVersion _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.EffectHalVersion.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.hardware.camera2.utils.BinderHolder _arg1;
_arg1 = new android.hardware.camera2.utils.BinderHolder();
int _result = this.createEffectHalClient(_arg0, _arg1);
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
case TRANSACTION_getSupportedVersion:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<com.mediatek.mmsdk.EffectHalVersion> _arg1;
_arg1 = new java.util.ArrayList<com.mediatek.mmsdk.EffectHalVersion>();
int _result = this.getSupportedVersion(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
reply.writeTypedList(_arg1);
return true;
}
case TRANSACTION_getAllSupportedEffectHal:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = new java.util.ArrayList<java.lang.String>();
int _result = this.getAllSupportedEffectHal(_arg0);
reply.writeNoException();
reply.writeInt(_result);
reply.writeStringList(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IEffectFactory
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
//@see    void setFindAccessibilityNodeInfosResult(in List<AccessibilityNodeInfo> infos,

@Override public int createEffectHal(com.mediatek.mmsdk.EffectHalVersion nameVersion, android.hardware.camera2.utils.BinderHolder effectHal) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((nameVersion!=null)) {
_data.writeInt(1);
nameVersion.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_createEffectHal, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
effectHal.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int createEffectHalClient(com.mediatek.mmsdk.EffectHalVersion nameVersion, android.hardware.camera2.utils.BinderHolder effectHalClient) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((nameVersion!=null)) {
_data.writeInt(1);
nameVersion.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_createEffectHalClient, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
effectHalClient.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getSupportedVersion(java.lang.String effectName, java.util.List<com.mediatek.mmsdk.EffectHalVersion> versions) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(effectName);
mRemote.transact(Stub.TRANSACTION_getSupportedVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
_reply.readTypedList(versions, com.mediatek.mmsdk.EffectHalVersion.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//@todo implement this - wait chengtian
//int getSupportedVersion(in List<String> effectNames, out List<List<EffectHalVersion>> versions);  //@todo

@Override public int getAllSupportedEffectHal(java.util.List<java.lang.String> version) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAllSupportedEffectHal, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
_reply.readStringList(version);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_createEffectHal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_createEffectHalClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getSupportedVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getAllSupportedEffectHal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IMMSdkService.h	
     */// rest of 'int' return values in this file are actually status_t
//@see    void setFindAccessibilityNodeInfosResult(in List<AccessibilityNodeInfo> infos,

public int createEffectHal(com.mediatek.mmsdk.EffectHalVersion nameVersion, android.hardware.camera2.utils.BinderHolder effectHal) throws android.os.RemoteException;
public int createEffectHalClient(com.mediatek.mmsdk.EffectHalVersion nameVersion, android.hardware.camera2.utils.BinderHolder effectHalClient) throws android.os.RemoteException;
public int getSupportedVersion(java.lang.String effectName, java.util.List<com.mediatek.mmsdk.EffectHalVersion> versions) throws android.os.RemoteException;
//@todo implement this - wait chengtian
//int getSupportedVersion(in List<String> effectNames, out List<List<EffectHalVersion>> versions);  //@todo

public int getAllSupportedEffectHal(java.util.List<java.lang.String> version) throws android.os.RemoteException;
}
