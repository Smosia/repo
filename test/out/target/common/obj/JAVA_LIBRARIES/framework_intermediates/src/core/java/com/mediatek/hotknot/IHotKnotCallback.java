/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/hotknot/IHotKnotCallback.aidl
 */
package com.mediatek.hotknot;
/**
 * @hide
 */
public interface IHotKnotCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.hotknot.IHotKnotCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.hotknot.IHotKnotCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.hotknot.IHotKnotCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.hotknot.IHotKnotCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.hotknot.IHotKnotCallback))) {
return ((com.mediatek.hotknot.IHotKnotCallback)iin);
}
return new com.mediatek.hotknot.IHotKnotCallback.Stub.Proxy(obj);
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
case TRANSACTION_createMessage:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.hotknot.HotKnotMessage _result = this.createMessage();
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
case TRANSACTION_getUris:
{
data.enforceInterface(DESCRIPTOR);
android.net.Uri[] _result = this.getUris();
reply.writeNoException();
reply.writeTypedArray(_result, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
return true;
}
case TRANSACTION_onHotKnotComplete:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onHotKnotComplete(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getClientId:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getClientId();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.hotknot.IHotKnotCallback
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
@Override public com.mediatek.hotknot.HotKnotMessage createMessage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.hotknot.HotKnotMessage _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_createMessage, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.hotknot.HotKnotMessage.CREATOR.createFromParcel(_reply);
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
@Override public android.net.Uri[] getUris() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
android.net.Uri[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUris, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArray(android.net.Uri.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void onHotKnotComplete(int clientId, int reason) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(clientId);
_data.writeInt(reason);
mRemote.transact(Stub.TRANSACTION_onHotKnotComplete, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getClientId() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getClientId, _data, _reply, 0);
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
static final int TRANSACTION_createMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getUris = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onHotKnotComplete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getClientId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public com.mediatek.hotknot.HotKnotMessage createMessage() throws android.os.RemoteException;
public android.net.Uri[] getUris() throws android.os.RemoteException;
public void onHotKnotComplete(int clientId, int reason) throws android.os.RemoteException;
public int getClientId() throws android.os.RemoteException;
}
