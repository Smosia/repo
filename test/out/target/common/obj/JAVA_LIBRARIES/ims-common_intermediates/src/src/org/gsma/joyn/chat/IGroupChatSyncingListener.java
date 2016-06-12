/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/chat/IGroupChatSyncingListener.aidl
 */
package org.gsma.joyn.chat;
/**
 * Conference info listener
 */
public interface IGroupChatSyncingListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.chat.IGroupChatSyncingListener
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.chat.IGroupChatSyncingListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.chat.IGroupChatSyncingListener interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.chat.IGroupChatSyncingListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.chat.IGroupChatSyncingListener))) {
return ((org.gsma.joyn.chat.IGroupChatSyncingListener)iin);
}
return new org.gsma.joyn.chat.IGroupChatSyncingListener.Stub.Proxy(obj);
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
case TRANSACTION_onSyncStart:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onSyncStart(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onSyncInfo:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
org.gsma.joyn.chat.ConferenceEventData _arg1;
if ((0!=data.readInt())) {
_arg1 = org.gsma.joyn.chat.ConferenceEventData.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.onSyncInfo(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onSyncDone:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onSyncDone(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.chat.IGroupChatSyncingListener
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
@Override public void onSyncStart(int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_onSyncStart, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSyncInfo(java.lang.String chatId, org.gsma.joyn.chat.ConferenceEventData info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(chatId);
if ((info!=null)) {
_data.writeInt(1);
info.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onSyncInfo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSyncDone(int result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(result);
mRemote.transact(Stub.TRANSACTION_onSyncDone, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onSyncStart = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onSyncInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onSyncDone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void onSyncStart(int count) throws android.os.RemoteException;
public void onSyncInfo(java.lang.String chatId, org.gsma.joyn.chat.ConferenceEventData info) throws android.os.RemoteException;
public void onSyncDone(int result) throws android.os.RemoteException;
}
