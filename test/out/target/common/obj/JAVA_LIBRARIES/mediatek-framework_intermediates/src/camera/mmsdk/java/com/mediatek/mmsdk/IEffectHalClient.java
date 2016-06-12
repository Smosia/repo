/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/camera/mmsdk/java/com/mediatek/mmsdk/IEffectHalClient.aidl
 */
package com.mediatek.mmsdk;
//@see IBatteryPropertiesListener.aidl
//@see ICameraServiceListener
/** @hide */
public interface IEffectHalClient extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.mmsdk.IEffectHalClient
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.mmsdk.IEffectHalClient";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.mmsdk.IEffectHalClient interface,
 * generating a proxy if needed.
 */
public static com.mediatek.mmsdk.IEffectHalClient asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.mmsdk.IEffectHalClient))) {
return ((com.mediatek.mmsdk.IEffectHalClient)iin);
}
return new com.mediatek.mmsdk.IEffectHalClient.Stub.Proxy(obj);
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
case TRANSACTION_setParameters:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.BaseParameters _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.setParameters(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCaptureRequirement:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.BaseParameters _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.util.List<com.mediatek.mmsdk.BaseParameters> _arg1;
_arg1 = new java.util.ArrayList<com.mediatek.mmsdk.BaseParameters>();
int _result = this.getCaptureRequirement(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
reply.writeTypedList(_arg1);
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
case TRANSACTION_getInputSurfaces:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<android.view.Surface> _arg0;
_arg0 = new java.util.ArrayList<android.view.Surface>();
int _result = this.getInputSurfaces(_arg0);
reply.writeNoException();
reply.writeInt(_result);
reply.writeTypedList(_arg0);
return true;
}
case TRANSACTION_setOutputSurfaces:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<android.view.Surface> _arg0;
_arg0 = data.createTypedArrayList(android.view.Surface.CREATOR);
java.util.List<com.mediatek.mmsdk.BaseParameters> _arg1;
_arg1 = data.createTypedArrayList(com.mediatek.mmsdk.BaseParameters.CREATOR);
int _result = this.setOutputSurfaces(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addInputParameter:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
long _arg2;
_arg2 = data.readLong();
boolean _arg3;
_arg3 = (0!=data.readInt());
int _result = this.addInputParameter(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addOutputParameter:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.mmsdk.BaseParameters _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
long _arg2;
_arg2 = data.readLong();
boolean _arg3;
_arg3 = (0!=data.readInt());
int _result = this.addOutputParameter(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setInputsyncMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
int _result = this.setInputsyncMode(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getInputsyncMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.getInputsyncMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setOutputsyncMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _arg1;
_arg1 = (0!=data.readInt());
int _result = this.setOutputsyncMode(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getOutputsyncMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.getOutputsyncMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBaseParameter:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.mmsdk.BaseParameters _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.mmsdk.BaseParameters.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.setBaseParameter(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_dequeueAndQueueBuf:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
int _result = this.dequeueAndQueueBuf(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.mmsdk.IEffectHalClient
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
@Override public int setParameter(java.lang.String key, java.lang.String paramValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(paramValue);
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

@Override public int setParameters(com.mediatek.mmsdk.BaseParameters parameter) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setParameters, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getCaptureRequirement(com.mediatek.mmsdk.BaseParameters effectParameter, java.util.List<com.mediatek.mmsdk.BaseParameters> requirement) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_getCaptureRequirement, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
_reply.readTypedList(requirement, com.mediatek.mmsdk.BaseParameters.CREATOR);
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
// int     addInputFrame(in GraphicBuffer frame, in BaseParameters parameter);
//int     addOutputFrame(in GraphicBuffer frame, in BaseParameters parameter);
//int     getInputSurfaces(out List<BinderHolder> input);

@Override public int getInputSurfaces(java.util.List<android.view.Surface> input) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getInputSurfaces, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
_reply.readTypedList(input, android.view.Surface.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setOutputSurfaces(java.util.List<android.view.Surface> output, java.util.List<com.mediatek.mmsdk.BaseParameters> parameters) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(output);
_data.writeTypedList(parameters);
mRemote.transact(Stub.TRANSACTION_setOutputSurfaces, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addInputParameter(int index, com.mediatek.mmsdk.BaseParameters parameter, long timestamp, boolean repeat) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeLong(timestamp);
_data.writeInt(((repeat)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_addInputParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addOutputParameter(int index, com.mediatek.mmsdk.BaseParameters parameter, long timestamp, boolean repeat) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
if ((parameter!=null)) {
_data.writeInt(1);
parameter.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeLong(timestamp);
_data.writeInt(((repeat)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_addOutputParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setInputsyncMode(int index, boolean sync) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
_data.writeInt(((sync)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setInputsyncMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean getInputsyncMode(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_getInputsyncMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setOutputsyncMode(int index, boolean sync) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
_data.writeInt(((sync)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setOutputsyncMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean getOutputsyncMode(int index) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(index);
mRemote.transact(Stub.TRANSACTION_getOutputsyncMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int setBaseParameter(com.mediatek.mmsdk.BaseParameters parameters) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((parameters!=null)) {
_data.writeInt(1);
parameters.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setBaseParameter, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int dequeueAndQueueBuf(long timestamp) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(timestamp);
mRemote.transact(Stub.TRANSACTION_dequeueAndQueueBuf, _data, _reply, 0);
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
static final int TRANSACTION_setParameters = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getCaptureRequirement = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_prepare = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_release = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getInputSurfaces = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_setOutputSurfaces = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_addInputParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_addOutputParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_setInputsyncMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getInputsyncMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setOutputsyncMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getOutputsyncMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setBaseParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_dequeueAndQueueBuf = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
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
public int setParameter(java.lang.String key, java.lang.String paramValue) throws android.os.RemoteException;
//@todo fix this

public int setParameters(com.mediatek.mmsdk.BaseParameters parameter) throws android.os.RemoteException;
public int getCaptureRequirement(com.mediatek.mmsdk.BaseParameters effectParameter, java.util.List<com.mediatek.mmsdk.BaseParameters> requirement) throws android.os.RemoteException;
public int prepare() throws android.os.RemoteException;
public int release() throws android.os.RemoteException;
// int     addInputFrame(in GraphicBuffer frame, in BaseParameters parameter);
//int     addOutputFrame(in GraphicBuffer frame, in BaseParameters parameter);
//int     getInputSurfaces(out List<BinderHolder> input);

public int getInputSurfaces(java.util.List<android.view.Surface> input) throws android.os.RemoteException;
public int setOutputSurfaces(java.util.List<android.view.Surface> output, java.util.List<com.mediatek.mmsdk.BaseParameters> parameters) throws android.os.RemoteException;
public int addInputParameter(int index, com.mediatek.mmsdk.BaseParameters parameter, long timestamp, boolean repeat) throws android.os.RemoteException;
public int addOutputParameter(int index, com.mediatek.mmsdk.BaseParameters parameter, long timestamp, boolean repeat) throws android.os.RemoteException;
public int setInputsyncMode(int index, boolean sync) throws android.os.RemoteException;
public boolean getInputsyncMode(int index) throws android.os.RemoteException;
public int setOutputsyncMode(int index, boolean sync) throws android.os.RemoteException;
public boolean getOutputsyncMode(int index) throws android.os.RemoteException;
public int setBaseParameter(com.mediatek.mmsdk.BaseParameters parameters) throws android.os.RemoteException;
public int dequeueAndQueueBuf(long timestamp) throws android.os.RemoteException;
}
