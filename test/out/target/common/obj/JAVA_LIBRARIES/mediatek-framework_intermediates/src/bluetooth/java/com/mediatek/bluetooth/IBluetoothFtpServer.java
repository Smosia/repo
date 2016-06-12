/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetooth/IBluetoothFtpServer.aidl
 */
package com.mediatek.bluetooth;
/** {@hide} 
 * Interface for binding to BluetoothFtpService.
 */
public interface IBluetoothFtpServer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetooth.IBluetoothFtpServer
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetooth.IBluetoothFtpServer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetooth.IBluetoothFtpServer interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetooth.IBluetoothFtpServer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetooth.IBluetoothFtpServer))) {
return ((com.mediatek.bluetooth.IBluetoothFtpServer)iin);
}
return new com.mediatek.bluetooth.IBluetoothFtpServer.Stub.Proxy(obj);
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
case TRANSACTION_getName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.bluetooth.IBluetoothFtpServerCallback _arg0;
_arg0 = com.mediatek.bluetooth.IBluetoothFtpServerCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.bluetooth.IBluetoothFtpServerCallback _arg0;
_arg0 = com.mediatek.bluetooth.IBluetoothFtpServerCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_enable:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.enable();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_disable:
{
data.enforceInterface(DESCRIPTOR);
this.disable();
reply.writeNoException();
return true;
}
case TRANSACTION_getStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setPermission:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setPermission(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getPermission:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPermission();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setRootDir:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.setRootDir(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetooth.IBluetoothFtpServer
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
/** {@hide} 
     */
@Override public java.lang.String getName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** {@hide} 
     * register the callback interface
     */
@Override public void registerCallback(com.mediatek.bluetooth.IBluetoothFtpServerCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/** {@hide} 
     *  unregister the callback interface
     */
@Override public void unregisterCallback(com.mediatek.bluetooth.IBluetoothFtpServerCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/** {@hide} 
     * enable FTP server
     */
@Override public boolean enable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enable, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** {@hide} 
     * disable FTP server
     */
@Override public void disable() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/** {@hide} 
     * get status of FTP server
     */
@Override public int getStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** {@hide} 
     * set access-permission of FTP server
     */
@Override public boolean setPermission(int permission) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(permission);
mRemote.transact(Stub.TRANSACTION_setPermission, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** {@hide} 
     * get access-permission of FTP server
     */
@Override public int getPermission() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPermission, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** {@hide} 
     * set root directory of FTP server
     */
@Override public boolean setRootDir(java.lang.String rootDir) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(rootDir);
mRemote.transact(Stub.TRANSACTION_setRootDir, _data, _reply, 0);
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
static final int TRANSACTION_getName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_enable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_disable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setPermission = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getPermission = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_setRootDir = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
}
/** {@hide} 
     */
public java.lang.String getName() throws android.os.RemoteException;
/** {@hide} 
     * register the callback interface
     */
public void registerCallback(com.mediatek.bluetooth.IBluetoothFtpServerCallback cb) throws android.os.RemoteException;
/** {@hide} 
     *  unregister the callback interface
     */
public void unregisterCallback(com.mediatek.bluetooth.IBluetoothFtpServerCallback cb) throws android.os.RemoteException;
/** {@hide} 
     * enable FTP server
     */
public boolean enable() throws android.os.RemoteException;
/** {@hide} 
     * disable FTP server
     */
public void disable() throws android.os.RemoteException;
/** {@hide} 
     * get status of FTP server
     */
public int getStatus() throws android.os.RemoteException;
/** {@hide} 
     * set access-permission of FTP server
     */
public boolean setPermission(int permission) throws android.os.RemoteException;
/** {@hide} 
     * get access-permission of FTP server
     */
public int getPermission() throws android.os.RemoteException;
/** {@hide} 
     * set root directory of FTP server
     */
public boolean setRootDir(java.lang.String rootDir) throws android.os.RemoteException;
}
