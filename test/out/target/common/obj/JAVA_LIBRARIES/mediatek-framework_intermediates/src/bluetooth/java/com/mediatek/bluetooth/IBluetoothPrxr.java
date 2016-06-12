/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetooth/IBluetoothPrxr.aidl
 */
package com.mediatek.bluetooth;
/**
 * @hide
 */
public interface IBluetoothPrxr extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetooth.IBluetoothPrxr
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetooth.IBluetoothPrxr";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetooth.IBluetoothPrxr interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetooth.IBluetoothPrxr asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetooth.IBluetoothPrxr))) {
return ((com.mediatek.bluetooth.IBluetoothPrxr)iin);
}
return new com.mediatek.bluetooth.IBluetoothPrxr.Stub.Proxy(obj);
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
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
android.os.ResultReceiver _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.ResultReceiver.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
android.os.ResultReceiver _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.ResultReceiver.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.unregisterCallback(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableService:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.enableService();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_disableService:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.disableService();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getConnectedDevices:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice[] _result = this.getConnectedDevices();
reply.writeNoException();
reply.writeTypedArray(_result, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
return true;
}
case TRANSACTION_getServiceState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getServiceState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getProfileManagerState:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.getProfileManagerState(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_responseAuthorizeInd:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
byte _arg1;
_arg1 = data.readByte();
int _result = this.responseAuthorizeInd(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_disconnect:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.disconnect(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_disconnectByAddr:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.disconnectByAddr(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetooth.IBluetoothPrxr
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
@Override public void registerCallback(android.os.ResultReceiver callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((callback!=null)) {
_data.writeInt(1);
callback.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean unregisterCallback(android.os.ResultReceiver callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((callback!=null)) {
_data.writeInt(1);
callback.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int enableService() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enableService, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int disableService() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disableService, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.bluetooth.BluetoothDevice[] getConnectedDevices() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.bluetooth.BluetoothDevice[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getConnectedDevices, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArray(android.bluetooth.BluetoothDevice.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getServiceState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getProfileManagerState(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_getProfileManagerState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXR_CONNECT_IND / MSG_ID_BT_PRXR_CONNECT_RSP

@Override public int responseAuthorizeInd(int connId, byte rspcode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(connId);
_data.writeByte(rspcode);
mRemote.transact(Stub.TRANSACTION_responseAuthorizeInd, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXR_DISCONNECT_REQ / MSG_ID_BT_PRXR_DISCONNECT_IND

@Override public int disconnect(int connId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(connId);
mRemote.transact(Stub.TRANSACTION_disconnect, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int disconnectByAddr(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_disconnectByAddr, _data, _reply, 0);
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
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_enableService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_disableService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getConnectedDevices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getServiceState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getProfileManagerState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_responseAuthorizeInd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_disconnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_disconnectByAddr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
}
public void registerCallback(android.os.ResultReceiver callback) throws android.os.RemoteException;
public boolean unregisterCallback(android.os.ResultReceiver callback) throws android.os.RemoteException;
public int enableService() throws android.os.RemoteException;
public int disableService() throws android.os.RemoteException;
public android.bluetooth.BluetoothDevice[] getConnectedDevices() throws android.os.RemoteException;
public int getServiceState() throws android.os.RemoteException;
public int getProfileManagerState(java.lang.String bdaddr) throws android.os.RemoteException;
// MSG_ID_BT_PRXR_CONNECT_IND / MSG_ID_BT_PRXR_CONNECT_RSP

public int responseAuthorizeInd(int connId, byte rspcode) throws android.os.RemoteException;
// MSG_ID_BT_PRXR_DISCONNECT_REQ / MSG_ID_BT_PRXR_DISCONNECT_IND

public int disconnect(int connId) throws android.os.RemoteException;
public int disconnectByAddr(java.lang.String bdaddr) throws android.os.RemoteException;
}
