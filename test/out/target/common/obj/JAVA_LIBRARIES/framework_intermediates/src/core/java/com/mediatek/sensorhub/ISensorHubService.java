/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/sensorhub/ISensorHubService.aidl
 */
package com.mediatek.sensorhub;
/**
 * @hide
 */
public interface ISensorHubService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.sensorhub.ISensorHubService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.sensorhub.ISensorHubService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.sensorhub.ISensorHubService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.sensorhub.ISensorHubService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.sensorhub.ISensorHubService))) {
return ((com.mediatek.sensorhub.ISensorHubService)iin);
}
return new com.mediatek.sensorhub.ISensorHubService.Stub.Proxy(obj);
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
case TRANSACTION_getContextList:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.sensorhub.ParcelableListInteger _result = this.getContextList();
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
case TRANSACTION_requestAction:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.sensorhub.Condition _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.sensorhub.Condition.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.sensorhub.Action _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.sensorhub.Action.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _result = this.requestAction(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_cancelAction:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.cancelAction(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_updateCondition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.mediatek.sensorhub.Condition _arg1;
if ((0!=data.readInt())) {
_arg1 = com.mediatek.sensorhub.Condition.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
boolean _result = this.updateCondition(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_enableGestureWakeup:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.enableGestureWakeup(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.sensorhub.ISensorHubService
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
@Override public com.mediatek.sensorhub.ParcelableListInteger getContextList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.mediatek.sensorhub.ParcelableListInteger _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getContextList, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.mediatek.sensorhub.ParcelableListInteger.CREATOR.createFromParcel(_reply);
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
@Override public int requestAction(com.mediatek.sensorhub.Condition condition, com.mediatek.sensorhub.Action action) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((condition!=null)) {
_data.writeInt(1);
condition.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
if ((action!=null)) {
_data.writeInt(1);
action.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_requestAction, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean cancelAction(int requestId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(requestId);
mRemote.transact(Stub.TRANSACTION_cancelAction, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean updateCondition(int requestId, com.mediatek.sensorhub.Condition condition) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(requestId);
if ((condition!=null)) {
_data.writeInt(1);
condition.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_updateCondition, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean enableGestureWakeup(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enableGestureWakeup, _data, _reply, 0);
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
static final int TRANSACTION_getContextList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_requestAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_cancelAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_updateCondition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_enableGestureWakeup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
}
public com.mediatek.sensorhub.ParcelableListInteger getContextList() throws android.os.RemoteException;
public int requestAction(com.mediatek.sensorhub.Condition condition, com.mediatek.sensorhub.Action action) throws android.os.RemoteException;
public boolean cancelAction(int requestId) throws android.os.RemoteException;
public boolean updateCondition(int requestId, com.mediatek.sensorhub.Condition condition) throws android.os.RemoteException;
public boolean enableGestureWakeup(boolean enabled) throws android.os.RemoteException;
}
