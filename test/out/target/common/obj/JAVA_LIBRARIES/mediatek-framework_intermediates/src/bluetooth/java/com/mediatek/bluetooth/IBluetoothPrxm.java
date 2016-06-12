/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetooth/IBluetoothPrxm.aidl
 */
package com.mediatek.bluetooth;
/**
 * @hide
 */
public interface IBluetoothPrxm extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetooth.IBluetoothPrxm
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetooth.IBluetoothPrxm";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetooth.IBluetoothPrxm interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetooth.IBluetoothPrxm asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetooth.IBluetoothPrxm))) {
return ((com.mediatek.bluetooth.IBluetoothPrxm)iin);
}
return new com.mediatek.bluetooth.IBluetoothPrxm.Stub.Proxy(obj);
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
case TRANSACTION_getRegisteredDevices:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.bluetooth.BluetoothPrxmDevice[] _result = this.getRegisteredDevices();
reply.writeNoException();
reply.writeTypedArray(_result, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
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
case TRANSACTION_registerDevice:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.registerDevice(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_unregisterDevice:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.unregisterDevice(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_registerDeviceCallback:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.ResultReceiver _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.ResultReceiver.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.registerDeviceCallback(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_unregisterDeviceCallback:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.unregisterDeviceCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getDeviceInfo:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.mediatek.bluetooth.BluetoothPrxmDevice _result = this.getDeviceInfo(_arg0);
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
case TRANSACTION_configPathLossLevel:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte _arg1;
_arg1 = data.readByte();
int _result = this.configPathLossLevel(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_configPathLossThreshold:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte _arg1;
_arg1 = data.readByte();
int _result = this.configPathLossThreshold(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_connectByProfileManager:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.connectByProfileManager(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_connect:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.connect(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_disconnect:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.disconnect(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getRemoteCapability:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.getRemoteCapability(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getRemoteTxPower:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.getRemoteTxPower(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPathLoss:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte _arg1;
_arg1 = data.readByte();
int _result = this.setPathLoss(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setLinkLoss:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte _arg1;
_arg1 = data.readByte();
int _result = this.setLinkLoss(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetooth.IBluetoothPrxm
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
// **************************************************************************
// Sync API - don't need responses from remote device
// **************************************************************************
// get registered device list

@Override public com.mediatek.bluetooth.BluetoothPrxmDevice[] getRegisteredDevices() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.bluetooth.BluetoothPrxmDevice[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRegisteredDevices, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArray(com.mediatek.bluetooth.BluetoothPrxmDevice.CREATOR);
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
// register / unregister bluetooth device 

@Override public boolean registerDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_registerDevice, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void unregisterDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_unregisterDevice, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
// register / unregister device callback (ResultReceiver)

@Override public boolean registerDeviceCallback(java.lang.String bdaddr, android.os.ResultReceiver callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
if ((callback!=null)) {
_data.writeInt(1);
callback.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_registerDeviceCallback, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void unregisterDeviceCallback(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_unregisterDeviceCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
// get state

@Override public com.mediatek.bluetooth.BluetoothPrxmDevice getDeviceInfo(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.bluetooth.BluetoothPrxmDevice _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_getDeviceInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.bluetooth.BluetoothPrxmDevice.CREATOR.createFromParcel(_reply);
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
// config local attribute

@Override public int configPathLossLevel(java.lang.String bdaddr, byte level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
_data.writeByte(level);
mRemote.transact(Stub.TRANSACTION_configPathLossLevel, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int configPathLossThreshold(java.lang.String bdaddr, byte threshold) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
_data.writeByte(threshold);
mRemote.transact(Stub.TRANSACTION_configPathLossThreshold, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// **************************************************************************
// Async API - need responses from remote device
// **************************************************************************

@Override public int connectByProfileManager(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_connectByProfileManager, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXM_CONNECT_REQ / MSG_ID_BT_PRXM_CONNECT_CNF
// @return response code

@Override public int connect(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_connect, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXM_DISCONNECT_REQ / MSG_ID_BT_PRXM_DISCONNECT_IND
// @return response code

@Override public int disconnect(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
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
// MSG_ID_BT_PRXM_GET_CAPABILITY_REQ / MSG_ID_BT_PRXM_GET_CAPABILITY_CNF
// @return capabilities

@Override public int getRemoteCapability(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_getRemoteCapability, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXM_GET_REMOTE_TXPOWER_REQ / MSG_ID_BT_PRXM_GET_REMOTE_TXPOWER_CNF
// @return tx power

@Override public int getRemoteTxPower(java.lang.String bdaddr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
mRemote.transact(Stub.TRANSACTION_getRemoteTxPower, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXM_SET_PATHLOSS_REQ / MSG_ID_BT_PRXM_SET_PATHLOSS_CNF
// @return response code

@Override public int setPathLoss(java.lang.String bdaddr, byte level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
_data.writeByte(level);
mRemote.transact(Stub.TRANSACTION_setPathLoss, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// MSG_ID_BT_PRXM_SET_LINKLOSS_REQ / MSG_ID_BT_PRXM_SET_LINKLOSS_CNF
// @return response code

@Override public int setLinkLoss(java.lang.String bdaddr, byte level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(bdaddr);
_data.writeByte(level);
mRemote.transact(Stub.TRANSACTION_setLinkLoss, _data, _reply, 0);
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
static final int TRANSACTION_getRegisteredDevices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getConnectedDevices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_registerDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unregisterDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_registerDeviceCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_unregisterDeviceCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getDeviceInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getProfileManagerState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_configPathLossLevel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_configPathLossThreshold = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_connectByProfileManager = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_connect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_disconnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getRemoteCapability = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getRemoteTxPower = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_setPathLoss = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_setLinkLoss = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
}
// **************************************************************************
// Sync API - don't need responses from remote device
// **************************************************************************
// get registered device list

public com.mediatek.bluetooth.BluetoothPrxmDevice[] getRegisteredDevices() throws android.os.RemoteException;
public android.bluetooth.BluetoothDevice[] getConnectedDevices() throws android.os.RemoteException;
// register / unregister bluetooth device 

public boolean registerDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public void unregisterDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
// register / unregister device callback (ResultReceiver)

public boolean registerDeviceCallback(java.lang.String bdaddr, android.os.ResultReceiver callback) throws android.os.RemoteException;
public void unregisterDeviceCallback(java.lang.String bdaddr) throws android.os.RemoteException;
// get state

public com.mediatek.bluetooth.BluetoothPrxmDevice getDeviceInfo(java.lang.String bdaddr) throws android.os.RemoteException;
public int getProfileManagerState(java.lang.String bdaddr) throws android.os.RemoteException;
// config local attribute

public int configPathLossLevel(java.lang.String bdaddr, byte level) throws android.os.RemoteException;
public int configPathLossThreshold(java.lang.String bdaddr, byte threshold) throws android.os.RemoteException;
// **************************************************************************
// Async API - need responses from remote device
// **************************************************************************

public int connectByProfileManager(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
// MSG_ID_BT_PRXM_CONNECT_REQ / MSG_ID_BT_PRXM_CONNECT_CNF
// @return response code

public int connect(java.lang.String bdaddr) throws android.os.RemoteException;
// MSG_ID_BT_PRXM_DISCONNECT_REQ / MSG_ID_BT_PRXM_DISCONNECT_IND
// @return response code

public int disconnect(java.lang.String bdaddr) throws android.os.RemoteException;
// MSG_ID_BT_PRXM_GET_CAPABILITY_REQ / MSG_ID_BT_PRXM_GET_CAPABILITY_CNF
// @return capabilities

public int getRemoteCapability(java.lang.String bdaddr) throws android.os.RemoteException;
// MSG_ID_BT_PRXM_GET_REMOTE_TXPOWER_REQ / MSG_ID_BT_PRXM_GET_REMOTE_TXPOWER_CNF
// @return tx power

public int getRemoteTxPower(java.lang.String bdaddr) throws android.os.RemoteException;
// MSG_ID_BT_PRXM_SET_PATHLOSS_REQ / MSG_ID_BT_PRXM_SET_PATHLOSS_CNF
// @return response code

public int setPathLoss(java.lang.String bdaddr, byte level) throws android.os.RemoteException;
// MSG_ID_BT_PRXM_SET_LINKLOSS_REQ / MSG_ID_BT_PRXM_SET_LINKLOSS_CNF
// @return response code

public int setLinkLoss(java.lang.String bdaddr, byte level) throws android.os.RemoteException;
}
