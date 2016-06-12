/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetooth/IBleDeviceManagerCallback.aidl
 */
package com.mediatek.bluetooth;
/**
 * System private API for receiving callback from BleClientManagerService.
 *
 * {@hide}
 */
public interface IBleDeviceManagerCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetooth.IBleDeviceManagerCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetooth.IBleDeviceManagerCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetooth.IBleDeviceManagerCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetooth.IBleDeviceManagerCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetooth.IBleDeviceManagerCallback))) {
return ((com.mediatek.bluetooth.IBleDeviceManagerCallback)iin);
}
return new com.mediatek.bluetooth.IBleDeviceManagerCallback.Stub.Proxy(obj);
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
case TRANSACTION_onConnectionStateChange:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.onConnectionStateChange(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onServicesChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.onServicesChanged(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onCharacteristicRead:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
int _arg3;
_arg3 = data.readInt();
this.onCharacteristicRead(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_onCharacteristicWrite:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
int _arg3;
_arg3 = data.readInt();
this.onCharacteristicWrite(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_onCharacteristicChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
this.onCharacteristicChanged(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onDescriptorRead:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
int _arg3;
_arg3 = data.readInt();
this.onDescriptorRead(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_onDescriptorWrite:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor _arg2;
if ((0!=data.readInt())) {
_arg2 = com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
int _arg3;
_arg3 = data.readInt();
this.onDescriptorWrite(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_onReliableWriteCompleted:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.onReliableWriteCompleted(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onReadRemoteRssi:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.onReadRemoteRssi(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetooth.IBleDeviceManagerCallback
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
@Override public void onConnectionStateChange(java.lang.String address, int status, int newState) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(status);
_data.writeInt(newState);
mRemote.transact(Stub.TRANSACTION_onConnectionStateChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onServicesChanged(java.lang.String address, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onServicesChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCharacteristicRead(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
if ((characteristic!=null)) {
_data.writeInt(1);
characteristic.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onCharacteristicRead, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCharacteristicWrite(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
if ((characteristic!=null)) {
_data.writeInt(1);
characteristic.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onCharacteristicWrite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onCharacteristicChanged(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
if ((characteristic!=null)) {
_data.writeInt(1);
characteristic.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onCharacteristicChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onDescriptorRead(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
if ((descriptor!=null)) {
_data.writeInt(1);
descriptor.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onDescriptorRead, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onDescriptorWrite(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
if ((descriptor!=null)) {
_data.writeInt(1);
descriptor.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onDescriptorWrite, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReliableWriteCompleted(java.lang.String address, int profileID, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onReliableWriteCompleted, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReadRemoteRssi(java.lang.String address, int profileID, int rssi, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(profileID);
_data.writeInt(rssi);
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_onReadRemoteRssi, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onConnectionStateChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onServicesChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onCharacteristicRead = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onCharacteristicWrite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onCharacteristicChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onDescriptorRead = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onDescriptorWrite = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onReliableWriteCompleted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_onReadRemoteRssi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
}
public void onConnectionStateChange(java.lang.String address, int status, int newState) throws android.os.RemoteException;
public void onServicesChanged(java.lang.String address, int status) throws android.os.RemoteException;
public void onCharacteristicRead(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic, int status) throws android.os.RemoteException;
public void onCharacteristicWrite(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic, int status) throws android.os.RemoteException;
public void onCharacteristicChanged(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattCharacteristic characteristic) throws android.os.RemoteException;
public void onDescriptorRead(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor, int status) throws android.os.RemoteException;
public void onDescriptorWrite(java.lang.String address, int profileID, com.mediatek.bluetooth.parcel.ParcelBluetoothGattDescriptor descriptor, int status) throws android.os.RemoteException;
public void onReliableWriteCompleted(java.lang.String address, int profileID, int status) throws android.os.RemoteException;
public void onReadRemoteRssi(java.lang.String address, int profileID, int rssi, int status) throws android.os.RemoteException;
}
