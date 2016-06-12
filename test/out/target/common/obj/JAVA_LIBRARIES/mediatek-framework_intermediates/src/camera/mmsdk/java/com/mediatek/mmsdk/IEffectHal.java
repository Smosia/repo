/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IEffectHal.aidl
 */
package com.mediatek.mmsdk;
/** @hide */
public interface IEffectHal extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IEffectHal
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IEffectHal";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IEffectHal interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IEffectHal asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IEffectHal))) {
return ((com.mediatek.mmsdk.IEffectHal)iin);
}
return new com.mediatek.mmsdk.IEffectHal.Stub.Proxy(obj);
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
case TRANSACTION_init:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.init();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_uninit:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.uninit();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_configure:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.configure();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_unconfigure:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.unconfigure();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_start:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.start();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_abort:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.BaseParameters _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.abort(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getNameVersion:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.EffectHalVersion _arg0;
_arg0 = new com.mediatek.mmsdk.EffectHalVersion();
int _result = this.getNameVersion(_arg0);
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
case TRANSACTION_setEffectListener:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.IEffectListener _arg0;
_arg0 = com.mediatek.mmsdk.IEffectListener.Stub.asInterface(data.readStrongBinder());
int _result = this.setEffectListener(_arg0);
reply.writeNoException();
reply.writeInt(_result);
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
case TRANSACTION_getCaptureRequirement:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.BaseParameters _arg0;
_arg0 = new com.mediatek.mmsdk.BaseParameters();
int _result = this.getCaptureRequirement(_arg0);
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
case TRANSACTION_prepare:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.prepare();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_release:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.release();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addInputFrame:
{
data.enforceInterface(DESCRIPTOR);
android.view.GraphicBuffer _arg0;
if ((0!=data.readInt())) {
_arg0 = android.view.GraphicBuffer.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _result = this.addInputFrame(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addOutputFrame:
{
data.enforceInterface(DESCRIPTOR);
android.view.GraphicBuffer _arg0;
if ((0!=data.readInt())) {
_arg0 = android.view.GraphicBuffer.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _result = this.addOutputFrame(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IEffectHal
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

@Override public int init() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int uninit() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_uninit, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int configure() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_configure, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int unconfigure() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unconfigure, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public long start() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_start, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int abort(com.mediatek.mmsdk.BaseParameters effectParameter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((effectParameter!=null)) {
_data.writeInt(1);
effectParameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_abort, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getNameVersion(com.mediatek.mmsdk.EffectHalVersion version) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getNameVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
version.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setEffectListener(com.mediatek.mmsdk.IEffectListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setEffectListener, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
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
//@todo fix this

@Override public int getCaptureRequirement(com.mediatek.mmsdk.BaseParameters requirement) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCaptureRequirement, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
if ((0!=_reply.readInt())) {
requirement.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int prepare() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_prepare, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int release() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_release, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addInputFrame(android.view.GraphicBuffer frame, com.mediatek.mmsdk.BaseParameters parameter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((frame!=null)) {
_data.writeInt(1);
frame.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_addInputFrame, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addOutputFrame(android.view.GraphicBuffer frame, com.mediatek.mmsdk.BaseParameters parameter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((frame!=null)) {
_data.writeInt(1);
frame.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_addOutputFrame, _data, _reply, 0);
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
static final int TRANSACTION_init = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_uninit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_configure = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unconfigure = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_start = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_abort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getNameVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setEffectListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getCaptureRequirement = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_prepare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_release = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_addInputFrame = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_addOutputFrame = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
}
/**
     * Keep up-to-date with vendor/mediatek/proprietary/frameworks/av/include/mmsdk/IMMSdkService.h	
     */// rest of 'int' return values in this file are actually status_t

public int init() throws android.os.RemoteException;
public int uninit() throws android.os.RemoteException;
public int configure() throws android.os.RemoteException;
public int unconfigure() throws android.os.RemoteException;
public long start() throws android.os.RemoteException;
public int abort(com.mediatek.mmsdk.BaseParameters effectParameter) throws android.os.RemoteException;
public int getNameVersion(com.mediatek.mmsdk.EffectHalVersion version) throws android.os.RemoteException;
public int setEffectListener(com.mediatek.mmsdk.IEffectListener listener) throws android.os.RemoteException;
public int setParameter(java.lang.String key, java.lang.String value) throws android.os.RemoteException;
//@todo fix this

public int getCaptureRequirement(com.mediatek.mmsdk.BaseParameters requirement) throws android.os.RemoteException;
public int prepare() throws android.os.RemoteException;
public int release() throws android.os.RemoteException;
public int addInputFrame(android.view.GraphicBuffer frame, com.mediatek.mmsdk.BaseParameters parameter) throws android.os.RemoteException;
public int addOutputFrame(android.view.GraphicBuffer frame, com.mediatek.mmsdk.BaseParameters parameter) throws android.os.RemoteException;
}
