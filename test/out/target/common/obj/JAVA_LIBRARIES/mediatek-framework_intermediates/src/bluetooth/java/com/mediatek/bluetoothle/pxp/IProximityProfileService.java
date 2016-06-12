/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetoothle/pxp/IProximityProfileService.aidl
 */
package com.mediatek.bluetoothle.pxp;
public interface IProximityProfileService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetoothle.pxp.IProximityProfileService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetoothle.pxp.IProximityProfileService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetoothle.pxp.IProximityProfileService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetoothle.pxp.IProximityProfileService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetoothle.pxp.IProximityProfileService))) {
return ((com.mediatek.bluetoothle.pxp.IProximityProfileService)iin);
}
return new com.mediatek.bluetoothle.pxp.IProximityProfileService.Stub.Proxy(obj);
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
case TRANSACTION_getPathLoss:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.getPathLoss(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isAlertOn:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.isAlertOn(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_stopRemoteAlert:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.stopRemoteAlert(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_registerStatusChangeCallback:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback _arg1;
_arg1 = com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback.Stub.asInterface(data.readStrongBinder());
boolean _result = this.registerStatusChangeCallback(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_unregisterStatusChangeCallback:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback _arg1;
_arg1 = com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback.Stub.asInterface(data.readStrongBinder());
boolean _result = this.unregisterStatusChangeCallback(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setPxpParameters:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
int _arg5;
_arg5 = data.readInt();
boolean _result = this.setPxpParameters(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPxpParameters:
{
data.enforceInterface(DESCRIPTOR);
android.bluetooth.BluetoothDevice _arg0;
if ((0!=data.readInt())) {
_arg0 = android.bluetooth.BluetoothDevice.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int[] _arg1;
int _arg1_length = data.readInt();
if ((_arg1_length<0)) {
_arg1 = null;
}
else {
_arg1 = new int[_arg1_length];
}
int[] _arg2;
int _arg2_length = data.readInt();
if ((_arg2_length<0)) {
_arg2 = null;
}
else {
_arg2 = new int[_arg2_length];
}
int[] _arg3;
int _arg3_length = data.readInt();
if ((_arg3_length<0)) {
_arg3 = null;
}
else {
_arg3 = new int[_arg3_length];
}
int[] _arg4;
int _arg4_length = data.readInt();
if ((_arg4_length<0)) {
_arg4 = null;
}
else {
_arg4 = new int[_arg4_length];
}
int[] _arg5;
int _arg5_length = data.readInt();
if ((_arg5_length<0)) {
_arg5 = null;
}
else {
_arg5 = new int[_arg5_length];
}
boolean _result = this.getPxpParameters(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
reply.writeIntArray(_arg1);
reply.writeIntArray(_arg2);
reply.writeIntArray(_arg3);
reply.writeIntArray(_arg4);
reply.writeIntArray(_arg5);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetoothle.pxp.IProximityProfileService
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
@Override public int getPathLoss(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_getPathLoss, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isAlertOn(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_isAlertOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean stopRemoteAlert(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_stopRemoteAlert, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean registerStatusChangeCallback(android.bluetooth.BluetoothDevice device, com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback callback) throws android.os.RemoteException
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
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerStatusChangeCallback, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean unregisterStatusChangeCallback(android.bluetooth.BluetoothDevice device, com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback callback) throws android.os.RemoteException
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
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterStatusChangeCallback, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean setPxpParameters(android.bluetooth.BluetoothDevice device, int alertEnabler, int rangeAlertEnabler, int rangeType, int rangeValue, int disconnectEnabler) throws android.os.RemoteException
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
_data.writeInt(alertEnabler);
_data.writeInt(rangeAlertEnabler);
_data.writeInt(rangeType);
_data.writeInt(rangeValue);
_data.writeInt(disconnectEnabler);
mRemote.transact(Stub.TRANSACTION_setPxpParameters, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean getPxpParameters(android.bluetooth.BluetoothDevice device, int[] alertEnabler, int[] rangeAlertEnabler, int[] rangeType, int[] rangeValue, int[] disconnectEnabler) throws android.os.RemoteException
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
if ((alertEnabler==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(alertEnabler.length);
}
if ((rangeAlertEnabler==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(rangeAlertEnabler.length);
}
if ((rangeType==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(rangeType.length);
}
if ((rangeValue==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(rangeValue.length);
}
if ((disconnectEnabler==null)) {
_data.writeInt(-1);
}
else {
_data.writeInt(disconnectEnabler.length);
}
mRemote.transact(Stub.TRANSACTION_getPxpParameters, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
_reply.readIntArray(alertEnabler);
_reply.readIntArray(rangeAlertEnabler);
_reply.readIntArray(rangeType);
_reply.readIntArray(rangeValue);
_reply.readIntArray(disconnectEnabler);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getPathLoss = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_isAlertOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_stopRemoteAlert = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_registerStatusChangeCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_unregisterStatusChangeCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setPxpParameters = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getPxpParameters = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public int getPathLoss(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean isAlertOn(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean stopRemoteAlert(android.bluetooth.BluetoothDevice device) throws android.os.RemoteException;
public boolean registerStatusChangeCallback(android.bluetooth.BluetoothDevice device, com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback callback) throws android.os.RemoteException;
public boolean unregisterStatusChangeCallback(android.bluetooth.BluetoothDevice device, com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback callback) throws android.os.RemoteException;
public boolean setPxpParameters(android.bluetooth.BluetoothDevice device, int alertEnabler, int rangeAlertEnabler, int rangeType, int rangeValue, int disconnectEnabler) throws android.os.RemoteException;
public boolean getPxpParameters(android.bluetooth.BluetoothDevice device, int[] alertEnabler, int[] rangeAlertEnabler, int[] rangeType, int[] rangeValue, int[] disconnectEnabler) throws android.os.RemoteException;
}
