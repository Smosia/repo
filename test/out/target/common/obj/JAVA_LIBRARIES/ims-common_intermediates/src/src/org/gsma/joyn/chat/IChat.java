/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/chat/IChat.aidl
 */
package org.gsma.joyn.chat;
/**
 * Chat interface
 */
public interface IChat extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.chat.IChat
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.chat.IChat";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.chat.IChat interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.chat.IChat asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.chat.IChat))) {
return ((org.gsma.joyn.chat.IChat)iin);
}
return new org.gsma.joyn.chat.IChat.Stub.Proxy(obj);
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
case TRANSACTION_getRemoteContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getRemoteContact();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendMessage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendDisplayedDeliveryReport:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendDisplayedDeliveryReport(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_sendIsComposingEvent:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.sendIsComposingEvent(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addEventListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.IChatListener _arg0;
_arg0 = org.gsma.joyn.chat.IChatListener.Stub.asInterface(data.readStrongBinder());
this.addEventListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeEventListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.IChatListener _arg0;
_arg0 = org.gsma.joyn.chat.IChatListener.Stub.asInterface(data.readStrongBinder());
this.removeEventListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addSpamReportListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.ISpamReportListener _arg0;
_arg0 = org.gsma.joyn.chat.ISpamReportListener.Stub.asInterface(data.readStrongBinder());
this.addSpamReportListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeSpamReportListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.ISpamReportListener _arg0;
_arg0 = org.gsma.joyn.chat.ISpamReportListener.Stub.asInterface(data.readStrongBinder());
this.removeSpamReportListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_sendGeoloc:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.chat.Geoloc _arg0;
if ((0!=data.readInt())) {
_arg0 = org.gsma.joyn.chat.Geoloc.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.lang.String _result = this.sendGeoloc(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_resendMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.resendMessage(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_reSendMultiMessageByPagerMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.reSendMultiMessageByPagerMode(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_sendMessageByLargeMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendMessageByLargeMode(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendCloudMessageByLargeMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendCloudMessageByLargeMode(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendPublicAccountMessageByLargeMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendPublicAccountMessageByLargeMode(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendMessageByPagerMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _arg2;
_arg2 = (0!=data.readInt());
boolean _arg3;
_arg3 = (0!=data.readInt());
boolean _arg4;
_arg4 = (0!=data.readInt());
java.util.List<java.lang.String> _arg5;
_arg5 = data.createStringArrayList();
java.lang.String _result = this.sendMessageByPagerMode(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendSpamMessageByPagerMode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.sendSpamMessageByPagerMode(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_sendOnetoMultiMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _arg1;
_arg1 = data.createStringArrayList();
java.lang.String _result = this.sendOnetoMultiMessage(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendOnetoMultiEmoticonsMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _arg1;
_arg1 = data.createStringArrayList();
java.lang.String _result = this.sendOnetoMultiEmoticonsMessage(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendEmoticonShopMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendEmoticonShopMessage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendCloudMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendCloudMessage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendPagerModeBurnMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendPagerModeBurnMessage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_sendLargeModeBurnMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.sendLargeModeBurnMessage(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getState:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.getState(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_sendBurnDeliveryReport:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendBurnDeliveryReport(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.chat.IChat
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
@Override public java.lang.String getRemoteContact() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRemoteContact, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendMessage(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void sendDisplayedDeliveryReport(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_sendDisplayedDeliveryReport, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void sendIsComposingEvent(boolean status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((status)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_sendIsComposingEvent, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addEventListener(org.gsma.joyn.chat.IChatListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addEventListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeEventListener(org.gsma.joyn.chat.IChatListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeEventListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addSpamReportListener(org.gsma.joyn.chat.ISpamReportListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addSpamReportListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeSpamReportListener(org.gsma.joyn.chat.ISpamReportListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeSpamReportListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String sendGeoloc(org.gsma.joyn.chat.Geoloc geoloc) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((geoloc!=null)) {
_data.writeInt(1);
geoloc.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_sendGeoloc, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int resendMessage(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_resendMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int reSendMultiMessageByPagerMode(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_reSendMultiMessageByPagerMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendMessageByLargeMode(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendMessageByLargeMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendCloudMessageByLargeMode(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendCloudMessageByLargeMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendPublicAccountMessageByLargeMode(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendPublicAccountMessageByLargeMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendMessageByPagerMode(java.lang.String message, boolean isBurnMessage, boolean isPublicMessage, boolean isMultiMessage, boolean isPayEmoticon, java.util.List<java.lang.String> participants) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
_data.writeInt(((isBurnMessage)?(1):(0)));
_data.writeInt(((isPublicMessage)?(1):(0)));
_data.writeInt(((isMultiMessage)?(1):(0)));
_data.writeInt(((isPayEmoticon)?(1):(0)));
_data.writeStringList(participants);
mRemote.transact(Stub.TRANSACTION_sendMessageByPagerMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void sendSpamMessageByPagerMode(java.lang.String contact, java.lang.String messageId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(messageId);
mRemote.transact(Stub.TRANSACTION_sendSpamMessageByPagerMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String sendOnetoMultiMessage(java.lang.String message, java.util.List<java.lang.String> participants) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
_data.writeStringList(participants);
mRemote.transact(Stub.TRANSACTION_sendOnetoMultiMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendOnetoMultiEmoticonsMessage(java.lang.String message, java.util.List<java.lang.String> participants) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
_data.writeStringList(participants);
mRemote.transact(Stub.TRANSACTION_sendOnetoMultiEmoticonsMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//String sendOnetoMultiCloudMessage(in String message, in List<String> participants);

@Override public java.lang.String sendEmoticonShopMessage(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendEmoticonShopMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendCloudMessage(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendCloudMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendPagerModeBurnMessage(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendPagerModeBurnMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.lang.String sendLargeModeBurnMessage(java.lang.String message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(message);
mRemote.transact(Stub.TRANSACTION_sendLargeModeBurnMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getState(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_getState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void sendBurnDeliveryReport(java.lang.String msgId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
mRemote.transact(Stub.TRANSACTION_sendBurnDeliveryReport, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getRemoteContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_sendMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_sendDisplayedDeliveryReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_sendIsComposingEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_addEventListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_removeEventListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_addSpamReportListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_removeSpamReportListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_sendGeoloc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_resendMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_reSendMultiMessageByPagerMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_sendMessageByLargeMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_sendCloudMessageByLargeMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_sendPublicAccountMessageByLargeMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_sendMessageByPagerMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_sendSpamMessageByPagerMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_sendOnetoMultiMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_sendOnetoMultiEmoticonsMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_sendEmoticonShopMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_sendCloudMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_sendPagerModeBurnMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_sendLargeModeBurnMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_sendBurnDeliveryReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
}
public java.lang.String getRemoteContact() throws android.os.RemoteException;
public java.lang.String sendMessage(java.lang.String message) throws android.os.RemoteException;
public void sendDisplayedDeliveryReport(java.lang.String msgId) throws android.os.RemoteException;
public void sendIsComposingEvent(boolean status) throws android.os.RemoteException;
public void addEventListener(org.gsma.joyn.chat.IChatListener listener) throws android.os.RemoteException;
public void removeEventListener(org.gsma.joyn.chat.IChatListener listener) throws android.os.RemoteException;
public void addSpamReportListener(org.gsma.joyn.chat.ISpamReportListener listener) throws android.os.RemoteException;
public void removeSpamReportListener(org.gsma.joyn.chat.ISpamReportListener listener) throws android.os.RemoteException;
public java.lang.String sendGeoloc(org.gsma.joyn.chat.Geoloc geoloc) throws android.os.RemoteException;
public int resendMessage(java.lang.String msgId) throws android.os.RemoteException;
public int reSendMultiMessageByPagerMode(java.lang.String msgId) throws android.os.RemoteException;
public java.lang.String sendMessageByLargeMode(java.lang.String message) throws android.os.RemoteException;
public java.lang.String sendCloudMessageByLargeMode(java.lang.String message) throws android.os.RemoteException;
public java.lang.String sendPublicAccountMessageByLargeMode(java.lang.String message) throws android.os.RemoteException;
public java.lang.String sendMessageByPagerMode(java.lang.String message, boolean isBurnMessage, boolean isPublicMessage, boolean isMultiMessage, boolean isPayEmoticon, java.util.List<java.lang.String> participants) throws android.os.RemoteException;
public void sendSpamMessageByPagerMode(java.lang.String contact, java.lang.String messageId) throws android.os.RemoteException;
public java.lang.String sendOnetoMultiMessage(java.lang.String message, java.util.List<java.lang.String> participants) throws android.os.RemoteException;
public java.lang.String sendOnetoMultiEmoticonsMessage(java.lang.String message, java.util.List<java.lang.String> participants) throws android.os.RemoteException;
//String sendOnetoMultiCloudMessage(in String message, in List<String> participants);

public java.lang.String sendEmoticonShopMessage(java.lang.String message) throws android.os.RemoteException;
public java.lang.String sendCloudMessage(java.lang.String message) throws android.os.RemoteException;
public java.lang.String sendPagerModeBurnMessage(java.lang.String message) throws android.os.RemoteException;
public java.lang.String sendLargeModeBurnMessage(java.lang.String message) throws android.os.RemoteException;
public int getState(java.lang.String msgId) throws android.os.RemoteException;
public void sendBurnDeliveryReport(java.lang.String msgId) throws android.os.RemoteException;
}
