/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IEffectListener.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IEffectListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IEffectListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IEffectListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IEffectListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IEffectListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IEffectListener))) {
return ((com.mediatek.mmsdk.IEffectListener)iin);
}
return new com.mediatek.mmsdk.IEffectListener.Stub.Proxy(obj);
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
case TRANSACTION_onPrepared:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectHalClient _arg0;
_arg0 = com.mediatek.mmsdk.IEffectHalClient.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.onPrepared(_arg0, _arg1);
return true;
}
case TRANSACTION_onInputFrameProcessed:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectHalClient _arg0;
_arg0 = com.mediatek.mmsdk.IEffectHalClient.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mediatek.mmsdk.BaseParameters _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
this.onInputFrameProcessed(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_onOutputFrameProcessed:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectHalClient _arg0;
_arg0 = com.mediatek.mmsdk.IEffectHalClient.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mediatek.mmsdk.BaseParameters _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
this.onOutputFrameProcessed(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_onCompleted:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectHalClient _arg0;
_arg0 = com.mediatek.mmsdk.IEffectHalClient.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
long _arg2;
_arg2 = data.readLong();
this.onCompleted(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_onAborted:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectHalClient _arg0;
_arg0 = com.mediatek.mmsdk.IEffectHalClient.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.onAborted(_arg0, _arg1);
return true;
}
case TRANSACTION_onFailed:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectHalClient _arg0;
_arg0 = com.mediatek.mmsdk.IEffectHalClient.Stub.asInterface(data.readStrongBinder());
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.onFailed(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IEffectListener
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

@Override public void onPrepared(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
if ((result!=null)) {
_data.writeInt(1);
result.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onPrepared, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onInputFrameProcessed(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters parameter, com.mediatek.mmsdk.BaseParameters partialResult) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((partialResult!=null)) {
_data.writeInt(1);
partialResult.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onInputFrameProcessed, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onOutputFrameProcessed(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters parameter, com.mediatek.mmsdk.BaseParameters partialResult) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((partialResult!=null)) {
_data.writeInt(1);
partialResult.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onOutputFrameProcessed, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onCompleted(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters partialResult, long uid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
if ((partialResult!=null)) {
_data.writeInt(1);
partialResult.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeLong(uid);
mRemote.transact(Stub.TRANSACTION_onCompleted, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onAborted(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
if ((result!=null)) {
_data.writeInt(1);
result.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onAborted, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onFailed(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((effect!=null))?(effect.asBinder()):(null)));
if ((result!=null)) {
_data.writeInt(1);
result.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onFailed, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onPrepared = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onInputFrameProcessed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onOutputFrameProcessed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onCompleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onAborted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IMMSdkService.h	
     */// rest of 'int' return values in this file are actually status_t

public void onPrepared(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters result) throws android.os.RemoteException;
public void onInputFrameProcessed(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters parameter, com.mediatek.mmsdk.BaseParameters partialResult) throws android.os.RemoteException;
public void onOutputFrameProcessed(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters parameter, com.mediatek.mmsdk.BaseParameters partialResult) throws android.os.RemoteException;
public void onCompleted(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters partialResult, long uid) throws android.os.RemoteException;
public void onAborted(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters result) throws android.os.RemoteException;
public void onFailed(com.mediatek.mmsdk.IEffectHalClient effect, com.mediatek.mmsdk.BaseParameters result) throws android.os.RemoteException;
}
