/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/ICoreServiceWrapper.aidl
 */
package org.gsma.joyn;
/**
 * This interface will handle remote calling about Registration status.
  */
public interface ICoreServiceWrapper extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.ICoreServiceWrapper
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.ICoreServiceWrapper";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.ICoreServiceWrapper interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.ICoreServiceWrapper asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.ICoreServiceWrapper))) {
return ((org.gsma.joyn.ICoreServiceWrapper)iin);
}
return new org.gsma.joyn.ICoreServiceWrapper.Stub.Proxy(obj);
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
case TRANSACTION_getChatServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getChatServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getFileTransferServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getFileTransferServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getCapabilitiesServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getCapabilitiesServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getContactsServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getContactsServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getGeolocServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getGeolocServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getVideoSharingServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getVideoSharingServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getImageSharingServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getImageSharingServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getIPCallServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getIPCallServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getMultimediaSessionServiceBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getMultimediaSessionServiceBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
case TRANSACTION_getNetworkConnectivityApiBinder:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _result = this.getNetworkConnectivityApiBinder();
reply.writeNoException();
reply.writeStrongBinder(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.ICoreServiceWrapper
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
     * Get Window Binder. 
     */
@Override public android.os.IBinder getChatServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getChatServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get Window Binder. 
     */
@Override public android.os.IBinder getFileTransferServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFileTransferServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get Window Binder. 
     */
@Override public android.os.IBinder getCapabilitiesServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCapabilitiesServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get Window Binder. 
     */
@Override public android.os.IBinder getContactsServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getContactsServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.IBinder getGeolocServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getGeolocServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.IBinder getVideoSharingServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVideoSharingServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.IBinder getImageSharingServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getImageSharingServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.IBinder getIPCallServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getIPCallServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.IBinder getMultimediaSessionServiceBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMultimediaSessionServiceBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public android.os.IBinder getNetworkConnectivityApiBinder() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.os.IBinder _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getNetworkConnectivityApiBinder, _data, _reply, 0);
_reply.readException();
_result = _reply.readStrongBinder();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getChatServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getFileTransferServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getCapabilitiesServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getContactsServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getGeolocServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getVideoSharingServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getImageSharingServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getIPCallServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getMultimediaSessionServiceBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getNetworkConnectivityApiBinder = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
}
/**
     * Get Window Binder. 
     */
public android.os.IBinder getChatServiceBinder() throws android.os.RemoteException;
/**
     * Get Window Binder. 
     */
public android.os.IBinder getFileTransferServiceBinder() throws android.os.RemoteException;
/**
     * Get Window Binder. 
     */
public android.os.IBinder getCapabilitiesServiceBinder() throws android.os.RemoteException;
/**
     * Get Window Binder. 
     */
public android.os.IBinder getContactsServiceBinder() throws android.os.RemoteException;
public android.os.IBinder getGeolocServiceBinder() throws android.os.RemoteException;
public android.os.IBinder getVideoSharingServiceBinder() throws android.os.RemoteException;
public android.os.IBinder getImageSharingServiceBinder() throws android.os.RemoteException;
public android.os.IBinder getIPCallServiceBinder() throws android.os.RemoteException;
public android.os.IBinder getMultimediaSessionServiceBinder() throws android.os.RemoteException;
public android.os.IBinder getNetworkConnectivityApiBinder() throws android.os.RemoteException;
}
