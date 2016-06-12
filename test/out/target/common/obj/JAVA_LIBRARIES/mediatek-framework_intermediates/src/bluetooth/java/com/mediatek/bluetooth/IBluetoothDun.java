/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetooth/IBluetoothDun.aidl
 */
package com.mediatek.bluetooth;
/** {@hide} 
 * Interface for binding to BluetoothDunService.
 */
public interface IBluetoothDun extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetooth.IBluetoothDun
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetooth.IBluetoothDun";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetooth.IBluetoothDun interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetooth.IBluetoothDun asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetooth.IBluetoothDun))) {
return ((com.mediatek.bluetooth.IBluetoothDun)iin);
}
return new com.mediatek.bluetooth.IBluetoothDun.Stub.Proxy(obj);
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
case TRANSACTION_dunDisconnect:
{
data.enforceInterface(DESCRIPTOR);
this.dunDisconnect();
reply.writeNoException();
return true;
}
case TRANSACTION_dunGetState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.dunGetState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_dunGetConnectedDevice:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _result = this.dunGetConnectedDevice();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_isTetheringOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isTetheringOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setBluetoothTethering:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setBluetoothTethering(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetooth.IBluetoothDun
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
@Override public void dunDisconnect() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dunDisconnect, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int dunGetState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dunGetState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.bluetooth.BluetoothDevice dunGetConnectedDevice() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.bluetooth.BluetoothDevice _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dunGetConnectedDevice, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isTetheringOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isTetheringOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setBluetoothTethering(boolean value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((value)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setBluetoothTethering, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_dunDisconnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_dunGetState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_dunGetConnectedDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isTetheringOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setBluetoothTethering = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void dunDisconnect() throws android.os.RemoteException;
public int dunGetState() throws android.os.RemoteException;
public android.bluetooth.BluetoothDevice dunGetConnectedDevice() throws android.os.RemoteException;
public boolean isTetheringOn() throws android.os.RemoteException;
public void setBluetoothTethering(boolean value) throws android.os.RemoteException;
}
