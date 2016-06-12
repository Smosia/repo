/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/hdmi/IMtkHdmiManager.aidl
 */
package com.mediatek.hdmi;
/**
 * @hide
 */
public interface IMtkHdmiManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.hdmi.IMtkHdmiManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.hdmi.IMtkHdmiManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.hdmi.IMtkHdmiManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.hdmi.IMtkHdmiManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.hdmi.IMtkHdmiManager))) {
return ((com.mediatek.hdmi.IMtkHdmiManager)iin);
}
return new com.mediatek.hdmi.IMtkHdmiManager.Stub.Proxy(obj);
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
case TRANSACTION_enableHdmi:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.enableHdmi(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableHdmiPower:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.enableHdmiPower(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setVideoResolution:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setVideoResolution(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setVideoScale:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setVideoScale(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setColorAndDeep:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.setColorAndDeep(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getResolutionMask:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.getResolutionMask();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_isSignalOutputting:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSignalOutputting();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSupportedResolutions:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.getSupportedResolutions();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_getDisplayType:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDisplayType();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_notifyHdVideoState:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.notifyHdVideoState(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_needSwDrmProtect:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.needSwDrmProtect();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_hasCapability:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.hasCapability(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getAudioParameter:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.getAudioParameter(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.hdmi.IMtkHdmiManager
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
@Override public boolean enableHdmi(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enableHdmi, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean enableHdmiPower(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enableHdmiPower, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setVideoResolution(int resolution) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(resolution);
mRemote.transact(Stub.TRANSACTION_setVideoResolution, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setVideoScale(int scale) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scale);
mRemote.transact(Stub.TRANSACTION_setVideoScale, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setColorAndDeep(int color, int deep) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(color);
_data.writeInt(deep);
mRemote.transact(Stub.TRANSACTION_setColorAndDeep, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int[] getResolutionMask() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getResolutionMask, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isSignalOutputting() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSignalOutputting, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int[] getSupportedResolutions() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSupportedResolutions, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getDisplayType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisplayType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void notifyHdVideoState(boolean playing) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((playing)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_notifyHdVideoState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean needSwDrmProtect() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_needSwDrmProtect, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean hasCapability(int mask) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mask);
mRemote.transact(Stub.TRANSACTION_hasCapability, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getAudioParameter(int mask, int offsets) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mask);
_data.writeInt(offsets);
mRemote.transact(Stub.TRANSACTION_getAudioParameter, _data, _reply, 0);
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
static final int TRANSACTION_enableHdmi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_enableHdmiPower = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setVideoResolution = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setVideoScale = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setColorAndDeep = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getResolutionMask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_isSignalOutputting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getSupportedResolutions = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getDisplayType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_notifyHdVideoState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_needSwDrmProtect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_hasCapability = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getAudioParameter = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public boolean enableHdmi(boolean enabled) throws android.os.RemoteException;
public boolean enableHdmiPower(boolean enabled) throws android.os.RemoteException;
public boolean setVideoResolution(int resolution) throws android.os.RemoteException;
public boolean setVideoScale(int scale) throws android.os.RemoteException;
public boolean setColorAndDeep(int color, int deep) throws android.os.RemoteException;
public int[] getResolutionMask() throws android.os.RemoteException;
public boolean isSignalOutputting() throws android.os.RemoteException;
public int[] getSupportedResolutions() throws android.os.RemoteException;
public int getDisplayType() throws android.os.RemoteException;
public void notifyHdVideoState(boolean playing) throws android.os.RemoteException;
public boolean needSwDrmProtect() throws android.os.RemoteException;
public boolean hasCapability(int mask) throws android.os.RemoteException;
public int getAudioParameter(int mask, int offsets) throws android.os.RemoteException;
}
