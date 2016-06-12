/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/gba/IGbaService.aidl
 */
package com.mediatek.gba;
/**
 * @hide
 */
public interface IGbaService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.gba.IGbaService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.gba.IGbaService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.gba.IGbaService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.gba.IGbaService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.gba.IGbaService))) {
return ((com.mediatek.gba.IGbaService)iin);
}
return new com.mediatek.gba.IGbaService.Stub.Proxy(obj);
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
case TRANSACTION_getGbaSupported:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getGbaSupported();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getGbaSupportedForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getGbaSupportedForSubscriber(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isGbaKeyExpired:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
boolean _result = this.isGbaKeyExpired(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isGbaKeyExpiredForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
int _arg2;
_arg2 = data.readInt();
boolean _result = this.isGbaKeyExpiredForSubscriber(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_runGbaAuthentication:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
boolean _arg2;
_arg2 = (0!=data.readInt());
com.mediatek.gba.NafSessionKey _result = this.runGbaAuthentication(_arg0, _arg1, _arg2);
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
case TRANSACTION_runGbaAuthenticationForSubscriber:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
boolean _arg2;
_arg2 = (0!=data.readInt());
int _arg3;
_arg3 = data.readInt();
com.mediatek.gba.NafSessionKey _result = this.runGbaAuthenticationForSubscriber(_arg0, _arg1, _arg2, _arg3);
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
case TRANSACTION_setNetwork:
{
data.enforceInterface(DESCRIPTOR);
android.net.Network _arg0;
if ((0!=data.readInt())) {
_arg0 = android.net.Network.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setNetwork(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.gba.IGbaService
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
@Override public int getGbaSupported() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getGbaSupported, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getGbaSupportedForSubscriber(int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_getGbaSupportedForSubscriber, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isGbaKeyExpired(java.lang.String nafFqdn, byte[] nafSecurProtocolId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(nafFqdn);
_data.writeByteArray(nafSecurProtocolId);
mRemote.transact(Stub.TRANSACTION_isGbaKeyExpired, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isGbaKeyExpiredForSubscriber(java.lang.String nafFqdn, byte[] nafSecurProtocolId, int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(nafFqdn);
_data.writeByteArray(nafSecurProtocolId);
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_isGbaKeyExpiredForSubscriber, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.mediatek.gba.NafSessionKey runGbaAuthentication(java.lang.String nafFqdn, byte[] nafSecurProtocolId, boolean forceRun) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.gba.NafSessionKey _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(nafFqdn);
_data.writeByteArray(nafSecurProtocolId);
_data.writeInt(((forceRun)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_runGbaAuthentication, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.gba.NafSessionKey.CREATOR.createFromParcel(_reply);
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
@Override public com.mediatek.gba.NafSessionKey runGbaAuthenticationForSubscriber(java.lang.String nafFqdn, byte[] nafSecurProtocolId, boolean forceRun, int subId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.gba.NafSessionKey _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(nafFqdn);
_data.writeByteArray(nafSecurProtocolId);
_data.writeInt(((forceRun)?(1):(0)));
_data.writeInt(subId);
mRemote.transact(Stub.TRANSACTION_runGbaAuthenticationForSubscriber, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.gba.NafSessionKey.CREATOR.createFromParcel(_reply);
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
@Override public void setNetwork(android.net.Network network) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((network!=null)) {
_data.writeInt(1);
network.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_setNetwork, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getGbaSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getGbaSupportedForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_isGbaKeyExpired = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isGbaKeyExpiredForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_runGbaAuthentication = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_runGbaAuthenticationForSubscriber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setNetwork = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
}
public int getGbaSupported() throws android.os.RemoteException;
public int getGbaSupportedForSubscriber(int subId) throws android.os.RemoteException;
public boolean isGbaKeyExpired(java.lang.String nafFqdn, byte[] nafSecurProtocolId) throws android.os.RemoteException;
public boolean isGbaKeyExpiredForSubscriber(java.lang.String nafFqdn, byte[] nafSecurProtocolId, int subId) throws android.os.RemoteException;
public com.mediatek.gba.NafSessionKey runGbaAuthentication(java.lang.String nafFqdn, byte[] nafSecurProtocolId, boolean forceRun) throws android.os.RemoteException;
public com.mediatek.gba.NafSessionKey runGbaAuthenticationForSubscriber(java.lang.String nafFqdn, byte[] nafSecurProtocolId, boolean forceRun, int subId) throws android.os.RemoteException;
public void setNetwork(android.net.Network network) throws android.os.RemoteException;
}
