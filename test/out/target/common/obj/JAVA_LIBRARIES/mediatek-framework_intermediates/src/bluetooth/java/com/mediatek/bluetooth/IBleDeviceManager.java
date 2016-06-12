/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetooth/IBleDeviceManager.aidl
 */
package com.mediatek.bluetooth;
/**
 * System private API for talking with the BleDeviceManagerService.
 *
 * {@hide}
 */
public interface IBleDeviceManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetooth.IBleDeviceManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetooth.IBleDeviceManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetooth.IBleDeviceManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetooth.IBleDeviceManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetooth.IBleDeviceManager))) {
return ((com.mediatek.bluetooth.IBleDeviceManager)iin);
}
return new com.mediatek.bluetooth.IBleDeviceManager.Stub.Proxy(obj);
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
case TRANSACTION_registerClient:
{
data.enforceInterface(DESCRIPTOR);
android.os.ParcelUuid _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.ParcelUuid.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.bluetooth.BluetoothDevice _arg1;
if ((0!=data.readInt())) {
_arg1 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mediatek.bluetooth.IBleDeviceManagerCallback _arg2;
_arg2 = com.mediatek.bluetooth.IBleDeviceManagerCallback.Stub.asInterface(data.readStrongBinder());
int _result = this.registerClient(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_unregisterClient:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.unregisterClient(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_connectDevice:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.bluetooth.BluetoothDevice _arg1;
if ((0!=data.readInt())) {
_arg1 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.connectDevice(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_disconnectDevice:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.bluetooth.BluetoothDevice _arg1;
if ((0!=data.readInt())) {
_arg1 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.disconnectDevice(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_discoverServices:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.bluetooth.BluetoothDevice _arg1;
if ((0!=data.readInt())) {
_arg1 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.discoverServices(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getServices:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.util.List<com.mediatek.bluetooth.parcel.ParcelBluetoothGattService> _result = this.getServices(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getService:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
android.os.ParcelUuid _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.ParcelUuid.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
com.mediatek.bluetooth.parcel.ParcelBluetoothGattService _result = this.getService(_arg0, _arg1);
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
case TRANSACTION_getState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.bluetooth.BluetoothDevice _arg1;
if ((0!=data.readInt())) {
_arg1 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _result = this.getState(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_readCharacteristic:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic _arg3;
if ((0!=data.readInt())) {
_arg3 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _result = this.readCharacteristic(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_writeCharacteristic:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic _arg3;
if ((0!=data.readInt())) {
_arg3 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _result = this.writeCharacteristic(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readDescriptor:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor _arg3;
if ((0!=data.readInt())) {
_arg3 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _result = this.readDescriptor(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_writeDescriptor:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor _arg3;
if ((0!=data.readInt())) {
_arg3 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _result = this.writeDescriptor(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setCharacteristicNotification:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic _arg3;
if ((0!=data.readInt())) {
_arg3 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _arg4;
_arg4 = (0!=data.readInt());
boolean _result = this.setCharacteristicNotification(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readRemoteRssi:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
boolean _result = this.readRemoteRssi(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_beginReliableWrite:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
boolean _result = this.beginReliableWrite(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_executeReliableWrite:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
boolean _result = this.executeReliableWrite(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_abortReliableWrite:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
android.bluetooth.BluetoothDevice _arg2;
if ((0!=data.readInt())) {
_arg2 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
this.abortReliableWrite(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_addGattDevice:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.addGattDevice(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_deleteGattDevice:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.deleteGattDevice(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetooth.IBleDeviceManager
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
@Override public int registerClient(android.os.ParcelUuid appId, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.IBleDeviceManagerCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((appId!=null)) {
_data.writeInt(1);
appId.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerClient, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void unregisterClient(int clientIf) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientIf);
mRemote.transact(Stub.TRANSACTION_unregisterClient, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
///Interface for device control

@Override public boolean connectDevice(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientIf);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_connectDevice, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean disconnectDevice(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientIf);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_disconnectDevice, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean discoverServices(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientIf);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_discoverServices, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.mediatek.bluetooth.parcel.ParcelBluetoothGattService> getServices(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mediatek.bluetooth.parcel.ParcelBluetoothGattService> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getServices, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mediatek.bluetooth.parcel.ParcelBluetoothGattService.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mediatek.bluetooth.parcel.ParcelBluetoothGattService getService(android.bluetooth.BluetoothDevice device, android.os.ParcelUuid uuid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.bluetooth.parcel.ParcelBluetoothGattService _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((uuid!=null)) {
_data.writeInt(1);
uuid.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getService, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.bluetooth.parcel.ParcelBluetoothGattService.CREATOR.createFromParcel(_reply);
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
@Override public int getState(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientIf);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
///Interface for profile client

@Override public boolean readCharacteristic(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((characteristic!=null)) {
_data.writeInt(1);
characteristic.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_readCharacteristic, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean writeCharacteristic(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((characteristic!=null)) {
_data.writeInt(1);
characteristic.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_writeCharacteristic, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean readDescriptor(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((descriptor!=null)) {
_data.writeInt(1);
descriptor.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_readDescriptor, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean writeDescriptor(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((descriptor!=null)) {
_data.writeInt(1);
descriptor.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_writeDescriptor, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setCharacteristicNotification(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic, boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((characteristic!=null)) {
_data.writeInt(1);
characteristic.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setCharacteristicNotification, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean readRemoteRssi(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_readRemoteRssi, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean beginReliableWrite(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_beginReliableWrite, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean executeReliableWrite(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_executeReliableWrite, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void abortReliableWrite(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientID);
_data.writeInt(profileID);
if ((device!=null)) {
_data.writeInt(1);
device.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_abortReliableWrite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addGattDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_addGattDevice, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void deleteGattDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_deleteGattDevice, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_connectDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_disconnectDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_discoverServices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getServices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_readCharacteristic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_writeCharacteristic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_readDescriptor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_writeDescriptor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_setCharacteristicNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_readRemoteRssi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_beginReliableWrite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_executeReliableWrite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_abortReliableWrite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_addGattDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_deleteGattDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
}
public int registerClient(android.os.ParcelUuid appId, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.IBleDeviceManagerCallback callback) throws android.os.RemoteException;
public void unregisterClient(int clientIf) throws android.os.RemoteException;
///Interface for device control

public boolean connectDevice(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean disconnectDevice(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean discoverServices(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public java.util.List<com.mediatek.bluetooth.parcel.ParcelBluetoothGattService> getServices(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public com.mediatek.bluetooth.parcel.ParcelBluetoothGattService getService(android.bluetooth.BluetoothDevice device, android.os.ParcelUuid uuid) throws android.os.RemoteException;
public int getState(int clientIf, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
///Interface for profile client

public boolean readCharacteristic(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic) throws android.os.RemoteException;
public boolean writeCharacteristic(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic) throws android.os.RemoteException;
public boolean readDescriptor(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor) throws android.os.RemoteException;
public boolean writeDescriptor(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor) throws android.os.RemoteException;
public boolean setCharacteristicNotification(int clientID, int profileID, android.bluetooth.BluetoothDevice device, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic, boolean enable) throws android.os.RemoteException;
public boolean readRemoteRssi(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean beginReliableWrite(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean executeReliableWrite(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public void abortReliableWrite(int clientID, int profileID, android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public void addGattDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public void deleteGattDevice(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
}
