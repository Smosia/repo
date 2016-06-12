/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetoothle/bleservice/IProfileServiceManager.aidl
 */
package com.mediatek.bluetoothle.bleservice;
/**
 * Interface for IProfileServiceManager
 *
 * @hide
 */
public interface IProfileServiceManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetoothle.bleservice.IProfileServiceManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetoothle.bleservice.IProfileServiceManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetoothle.bleservice.IProfileServiceManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetoothle.bleservice.IProfileServiceManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetoothle.bleservice.IProfileServiceManager))) {
return ((com.mediatek.bluetoothle.bleservice.IProfileServiceManager)iin);
}
return new com.mediatek.bluetoothle.bleservice.IProfileServiceManager.Stub.Proxy(obj);
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
case TRANSACTION_getCurSupportedServerProfiles:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.getCurSupportedServerProfiles();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_getProfileServerState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getProfileServerState(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setBackgroundMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.setBackgroundMode(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isBackgroundModeEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isBackgroundModeEnabled();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_launchServices:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.launchServices();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_shutdownServices:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.shutdownServices();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback _arg0;
_arg0 = com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback _arg0;
_arg0 = com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetoothle.bleservice.IProfileServiceManager
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
     * Get all supported Ble profile servers in the platform
     *
     * @return array of id in the BleProfile.java
     */
@Override public int[] getCurSupportedServerProfiles() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurSupportedServerProfiles, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Query the status of the server by using profile id defined in the BleProfile.java
     *
     * @param profile the profile id
     *
     * @return current state of the profile server
     */
@Override public int getProfileServerState(int profile) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(profile);
mRemote.transact(Stub.TRANSACTION_getProfileServerState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Enable/Disable Bluetooth LE services in the background mode
     *
     * @param bEnabled if true, it enables the background mode
     *          otherwise, it disables the background mode
     */
@Override public boolean setBackgroundMode(boolean bEnabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((bEnabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setBackgroundMode, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Querty the status of the background mode
     *
     * @return true, if background mode is enabled
     *         otherwise, if background mode is disabled
     */
@Override public boolean isBackgroundModeEnabled() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isBackgroundModeEnabled, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Launch Bluetooth LE services when background mode is disabled
     * Pre-condition: Bluetooth must be turn on
     *
     * @return true,if this operation starts
     *         false,if this operation fails
     */
@Override public boolean launchServices() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_launchServices, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Shutdown Bluetooth LE services when background mode is disabled
     * Pre-condition: Bluetooth must be turn on
     *
     * @return true,if this operation starts
     *         false,if this operation fails
     */
@Override public boolean shutdownServices() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_shutdownServices, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * register callback
     *
     * @param callback callback
     */
@Override public void registerCallback(com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * unregister callback
     *
     * @param callback callback
     */
@Override public void unregisterCallback(com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getCurSupportedServerProfiles = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getProfileServerState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setBackgroundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isBackgroundModeEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_launchServices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_shutdownServices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
/**
     * Get all supported Ble profile servers in the platform
     *
     * @return array of id in the BleProfile.java
     */
public int[] getCurSupportedServerProfiles() throws android.os.RemoteException;
/**
     * Query the status of the server by using profile id defined in the BleProfile.java
     *
     * @param profile the profile id
     *
     * @return current state of the profile server
     */
public int getProfileServerState(int profile) throws android.os.RemoteException;
/**
     * Enable/Disable Bluetooth LE services in the background mode
     *
     * @param bEnabled if true, it enables the background mode
     *          otherwise, it disables the background mode
     */
public boolean setBackgroundMode(boolean bEnabled) throws android.os.RemoteException;
/**
     * Querty the status of the background mode
     *
     * @return true, if background mode is enabled
     *         otherwise, if background mode is disabled
     */
public boolean isBackgroundModeEnabled() throws android.os.RemoteException;
/**
     * Launch Bluetooth LE services when background mode is disabled
     * Pre-condition: Bluetooth must be turn on
     *
     * @return true,if this operation starts
     *         false,if this operation fails
     */
public boolean launchServices() throws android.os.RemoteException;
/**
     * Shutdown Bluetooth LE services when background mode is disabled
     * Pre-condition: Bluetooth must be turn on
     *
     * @return true,if this operation starts
     *         false,if this operation fails
     */
public boolean shutdownServices() throws android.os.RemoteException;
/**
     * register callback
     *
     * @param callback callback
     */
public void registerCallback(com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback callback) throws android.os.RemoteException;
/**
     * unregister callback
     *
     * @param callback callback
     */
public void unregisterCallback(com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback callback) throws android.os.RemoteException;
}
