/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/chat/IChatListener.aidl
 */
package org.gsma.joyn.chat;
/**
 * Chat event listener
 */
public interface IChatListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.chat.IChatListener
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.chat.IChatListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.chat.IChatListener interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.chat.IChatListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.chat.IChatListener))) {
return ((org.gsma.joyn.chat.IChatListener)iin);
}
return new org.gsma.joyn.chat.IChatListener.Stub.Proxy(obj);
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
case TRANSACTION_onNewMessage:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.ChatMessage _arg0;
if ((0!=data.readInt())) {
_arg0 = org.gsma.joyn.chat.ChatMessage.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onNewMessage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onNewGeoloc:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.GeolocMessage _arg0;
if ((0!=data.readInt())) {
_arg0 = org.gsma.joyn.chat.GeolocMessage.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onNewGeoloc(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportMessageDelivered:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportMessageDelivered(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportMessageDisplayed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportMessageDisplayed(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportMessageFailed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportMessageFailed(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onComposingEvent:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.onComposingEvent(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportFailedMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.onReportFailedMessage(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportSentMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportSentMessage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportDeliveredMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportDeliveredMessage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onNewBurnMessageArrived:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.ChatMessage _arg0;
if ((0!=data.readInt())) {
_arg0 = org.gsma.joyn.chat.ChatMessage.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onNewBurnMessageArrived(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.chat.IChatListener
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
@Override public void onNewMessage(org.gsma.joyn.chat.ChatMessage message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((message!=null)) {
_data.writeInt(1);
message.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onNewMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNewGeoloc(org.gsma.joyn.chat.GeolocMessage message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((message!=null)) {
_data.writeInt(1);
message.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onNewGeoloc, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportMessageDelivered(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_onReportMessageDelivered, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportMessageDisplayed(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_onReportMessageDisplayed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportMessageFailed(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_onReportMessageFailed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onComposingEvent(boolean status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((status)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onComposingEvent, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportFailedMessage(java.lang.String msgId, int errtype, java.lang.String statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
_data.writeInt(errtype);
_data.writeString(statusCode);
mRemote.transact(Stub.TRANSACTION_onReportFailedMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportSentMessage(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_onReportSentMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportDeliveredMessage(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_onReportDeliveredMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNewBurnMessageArrived(org.gsma.joyn.chat.ChatMessage message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((message!=null)) {
_data.writeInt(1);
message.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onNewBurnMessageArrived, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onNewMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onNewGeoloc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onReportMessageDelivered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onReportMessageDisplayed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onReportMessageFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onComposingEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onReportFailedMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onReportSentMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_onReportDeliveredMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_onNewBurnMessageArrived = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
}
public void onNewMessage(org.gsma.joyn.chat.ChatMessage message) throws android.os.RemoteException;
public void onNewGeoloc(org.gsma.joyn.chat.GeolocMessage message) throws android.os.RemoteException;
public void onReportMessageDelivered(java.lang.String msgId) throws android.os.RemoteException;
public void onReportMessageDisplayed(java.lang.String msgId) throws android.os.RemoteException;
public void onReportMessageFailed(java.lang.String msgId) throws android.os.RemoteException;
public void onComposingEvent(boolean status) throws android.os.RemoteException;
public void onReportFailedMessage(java.lang.String msgId, int errtype, java.lang.String statusCode) throws android.os.RemoteException;
public void onReportSentMessage(java.lang.String msgId) throws android.os.RemoteException;
public void onReportDeliveredMessage(java.lang.String msgId) throws android.os.RemoteException;
public void onNewBurnMessageArrived(org.gsma.joyn.chat.ChatMessage message) throws android.os.RemoteException;
}
