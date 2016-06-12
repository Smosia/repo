/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/audioprofile/IAudioProfileListener.aidl
 */
package com.mediatek.common.audioprofile;
/**
 * {@hide}
 */
public interface IAudioProfileListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.audioprofile.IAudioProfileListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.audioprofile.IAudioProfileListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.audioprofile.IAudioProfileListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.audioprofile.IAudioProfileListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.audioprofile.IAudioProfileListener))) {
return ((com.mediatek.common.audioprofile.IAudioProfileListener)iin);
}
return new com.mediatek.common.audioprofile.IAudioProfileListener.Stub.Proxy(obj);
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
case TRANSACTION_onProfileChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onProfileChanged(_arg0);
return true;
}
case TRANSACTION_onRingerModeChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onRingerModeChanged(_arg0);
return true;
}
case TRANSACTION_onRingerVolumeChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.onRingerVolumeChanged(_arg0, _arg1, _arg2);
return true;
}
case TRANSACTION_onVibrateSettingChanged:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onVibrateSettingChanged(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.audioprofile.IAudioProfileListener
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
@Override public void onProfileChanged(java.lang.String profileKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(profileKey);
mRemote.transact(Stub.TRANSACTION_onProfileChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onRingerModeChanged(int ringerMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ringerMode);
mRemote.transact(Stub.TRANSACTION_onRingerModeChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onRingerVolumeChanged(int oldVolume, int newVolume, java.lang.String extra) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(oldVolume);
_data.writeInt(newVolume);
_data.writeString(extra);
mRemote.transact(Stub.TRANSACTION_onRingerVolumeChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void onVibrateSettingChanged(int vibrateType, int vibrateSetting) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(vibrateType);
_data.writeInt(vibrateSetting);
mRemote.transact(Stub.TRANSACTION_onVibrateSettingChanged, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onProfileChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onRingerModeChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onRingerVolumeChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onVibrateSettingChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void onProfileChanged(java.lang.String profileKey) throws android.os.RemoteException;
public void onRingerModeChanged(int ringerMode) throws android.os.RemoteException;
public void onRingerVolumeChanged(int oldVolume, int newVolume, java.lang.String extra) throws android.os.RemoteException;
public void onVibrateSettingChanged(int vibrateType, int vibrateSetting) throws android.os.RemoteException;
}
