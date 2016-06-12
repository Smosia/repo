/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/packages/services/WifiOffload/lib/src/com/mediatek/wfo/IWifiOffloadService.aidl
 */
package com.mediatek.wfo;
public interface IWifiOffloadService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.wfo.IWifiOffloadService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.wfo.IWifiOffloadService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.wfo.IWifiOffloadService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.wfo.IWifiOffloadService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.wfo.IWifiOffloadService))) {
return ((com.mediatek.wfo.IWifiOffloadService)iin);
}
return new com.mediatek.wfo.IWifiOffloadService.Stub.Proxy(obj);
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
case TRANSACTION_registerForHandoverEvent:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.wfo.IWifiOffloadListener _arg0;
_arg0 = com.mediatek.wfo.IWifiOffloadListener.Stub.asInterface(data.readStrongBinder());
this.registerForHandoverEvent(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterForHandoverEvent:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.wfo.IWifiOffloadListener _arg0;
_arg0 = com.mediatek.wfo.IWifiOffloadListener.Stub.asInterface(data.readStrongBinder());
this.unregisterForHandoverEvent(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getRatType:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getRatType();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getDisconnectCause:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.wfo.DisconnectCause _result = this.getDisconnectCause();
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
case TRANSACTION_setEpdgFqdn:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setEpdgFqdn(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.wfo.IWifiOffloadService
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
@Override public void registerForHandoverEvent(com.mediatek.wfo.IWifiOffloadListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerForHandoverEvent, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterForHandoverEvent(com.mediatek.wfo.IWifiOffloadListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterForHandoverEvent, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getRatType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRatType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mediatek.wfo.DisconnectCause getDisconnectCause() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.wfo.DisconnectCause _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDisconnectCause, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.wfo.DisconnectCause.CREATOR.createFromParcel(_reply);
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
@Override public void setEpdgFqdn(java.lang.String fqdn, boolean wfcEnabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(fqdn);
_data.writeInt(((wfcEnabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setEpdgFqdn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerForHandoverEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterForHandoverEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getRatType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getDisconnectCause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setEpdgFqdn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public void registerForHandoverEvent(com.mediatek.wfo.IWifiOffloadListener listener) throws android.os.RemoteException;
public void unregisterForHandoverEvent(com.mediatek.wfo.IWifiOffloadListener listener) throws android.os.RemoteException;
public int getRatType() throws android.os.RemoteException;
public com.mediatek.wfo.DisconnectCause getDisconnectCause() throws android.os.RemoteException;
public void setEpdgFqdn(java.lang.String fqdn, boolean wfcEnabled) throws android.os.RemoteException;
}
