/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetoothle/anp/IAlertNotificationProfileService.aidl
 */
package com.mediatek.bluetoothle.anp;
public interface IAlertNotificationProfileService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetoothle.anp.IAlertNotificationProfileService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetoothle.anp.IAlertNotificationProfileService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetoothle.anp.IAlertNotificationProfileService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetoothle.anp.IAlertNotificationProfileService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetoothle.anp.IAlertNotificationProfileService))) {
return ((com.mediatek.bluetoothle.anp.IAlertNotificationProfileService)iin);
}
return new com.mediatek.bluetoothle.anp.IAlertNotificationProfileService.Stub.Proxy(obj);
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
case TRANSACTION_getDeviceSettings:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int[] _arg1;
_arg1 = data.createIntArray();
int[] _result = this.getDeviceSettings(_arg0, _arg1);
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_getRemoteSettings:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int[] _arg1;
_arg1 = data.createIntArray();
int[] _result = this.getRemoteSettings(_arg0, _arg1);
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_updateDeviceSettings:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int[] _arg1;
_arg1 = data.createIntArray();
int[] _arg2;
_arg2 = data.createIntArray();
boolean _result = this.updateDeviceSettings(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetoothle.anp.IAlertNotificationProfileService
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
@Override public int[] getDeviceSettings(java.lang.String address, int[] categoryArray) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeIntArray(categoryArray);
mRemote.transact(Stub.TRANSACTION_getDeviceSettings, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int[] getRemoteSettings(java.lang.String address, int[] categoryArray) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeIntArray(categoryArray);
mRemote.transact(Stub.TRANSACTION_getRemoteSettings, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean updateDeviceSettings(java.lang.String address, int[] categoryArray, int[] valueArray) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeIntArray(categoryArray);
_data.writeIntArray(valueArray);
mRemote.transact(Stub.TRANSACTION_updateDeviceSettings, _data, _reply, 0);
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
static final int TRANSACTION_getDeviceSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getRemoteSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_updateDeviceSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public int[] getDeviceSettings(java.lang.String address, int[] categoryArray) throws android.os.RemoteException;
public int[] getRemoteSettings(java.lang.String address, int[] categoryArray) throws android.os.RemoteException;
public boolean updateDeviceSettings(java.lang.String address, int[] categoryArray, int[] valueArray) throws android.os.RemoteException;
}
