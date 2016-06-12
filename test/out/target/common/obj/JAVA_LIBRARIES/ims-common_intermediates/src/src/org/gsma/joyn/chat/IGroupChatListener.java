/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/chat/IGroupChatListener.aidl
 */
package org.gsma.joyn.chat;
/**
 * Group chat event listener
 */
public interface IGroupChatListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.chat.IGroupChatListener
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.chat.IGroupChatListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.chat.IGroupChatListener interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.chat.IGroupChatListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.chat.IGroupChatListener))) {
return ((org.gsma.joyn.chat.IGroupChatListener)iin);
}
return new org.gsma.joyn.chat.IGroupChatListener.Stub.Proxy(obj);
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
case TRANSACTION_onSessionStarted:
{
data.enforceInterface(DESCRIPTOR);
this.onSessionStarted();
reply.writeNoException();
return true;
}
case TRANSACTION_onSessionAborted:
{
data.enforceInterface(DESCRIPTOR);
this.onSessionAborted();
reply.writeNoException();
return true;
}
case TRANSACTION_onSessionAbortedbyChairman:
{
data.enforceInterface(DESCRIPTOR);
this.onSessionAbortedbyChairman();
reply.writeNoException();
return true;
}
case TRANSACTION_onSessionError:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onSessionError(_arg0);
reply.writeNoException();
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
case TRANSACTION_onReportMessageDeliveredContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onReportMessageDeliveredContact(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportMessageDisplayedContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onReportMessageDisplayedContact(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportMessageFailedContact:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onReportMessageFailedContact(_arg0, _arg1);
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
case TRANSACTION_onGroupChatDissolved:
{
data.enforceInterface(DESCRIPTOR);
this.onGroupChatDissolved();
reply.writeNoException();
return true;
}
case TRANSACTION_onInviteParticipantsResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
this.onInviteParticipantsResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onComposingEvent:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onComposingEvent(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onParticipantJoined:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onParticipantJoined(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onParticipantLeft:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onParticipantLeft(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onParticipantDisconnected:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onParticipantDisconnected(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onSetChairmanResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onSetChairmanResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onChairmanChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onChairmanChanged(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onModifySubjectResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onModifySubjectResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onSubjectChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onSubjectChanged(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onModifyNickNameResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onModifyNickNameResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onNickNameChanged:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onNickNameChanged(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onRemoveParticipantResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.onRemoveParticipantResult(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportMeKickedOut:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportMeKickedOut(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportParticipantKickedOut:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportParticipantKickedOut(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onAbortConversationResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onAbortConversationResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onQuitConversationResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onQuitConversationResult(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onConferenceNotify:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<org.gsma.joyn.chat.ConferenceEventData.ConferenceUser> _arg1;
_arg1 = data.createTypedArrayList(org.gsma.joyn.chat.ConferenceEventData.ConferenceUser.CREATOR);
this.onConferenceNotify(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.chat.IGroupChatListener
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
@Override public void onSessionStarted() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSessionStarted, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSessionAborted() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSessionAborted, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSessionAbortedbyChairman() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onSessionAbortedbyChairman, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSessionError(int reason) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(reason);
mRemote.transact(Stub.TRANSACTION_onSessionError, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
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
@Override public void onReportMessageDeliveredContact(java.lang.String msgId, java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onReportMessageDeliveredContact, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportMessageDisplayedContact(java.lang.String msgId, java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onReportMessageDisplayedContact, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportMessageFailedContact(java.lang.String msgId, java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msgId);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onReportMessageFailedContact, _data, _reply, 0);
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
@Override public void onGroupChatDissolved() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_onGroupChatDissolved, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onInviteParticipantsResult(int errType, java.lang.String statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeString(statusCode);
mRemote.transact(Stub.TRANSACTION_onInviteParticipantsResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onComposingEvent(java.lang.String contact, boolean status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeInt(((status)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onComposingEvent, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onParticipantJoined(java.lang.String contact, java.lang.String contactDisplayname) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(contactDisplayname);
mRemote.transact(Stub.TRANSACTION_onParticipantJoined, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onParticipantLeft(java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onParticipantLeft, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onParticipantDisconnected(java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onParticipantDisconnected, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSetChairmanResult(int errType, int statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeInt(statusCode);
mRemote.transact(Stub.TRANSACTION_onSetChairmanResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onChairmanChanged(java.lang.String newChairman) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(newChairman);
mRemote.transact(Stub.TRANSACTION_onChairmanChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onModifySubjectResult(int errType, int statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeInt(statusCode);
mRemote.transact(Stub.TRANSACTION_onModifySubjectResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onSubjectChanged(java.lang.String newSubject) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(newSubject);
mRemote.transact(Stub.TRANSACTION_onSubjectChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onModifyNickNameResult(int errType, int statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeInt(statusCode);
mRemote.transact(Stub.TRANSACTION_onModifyNickNameResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNickNameChanged(java.lang.String contact, java.lang.String newNickName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(newNickName);
mRemote.transact(Stub.TRANSACTION_onNickNameChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onRemoveParticipantResult(int errType, int statusCode, java.lang.String participant) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeInt(statusCode);
_data.writeString(participant);
mRemote.transact(Stub.TRANSACTION_onRemoveParticipantResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportMeKickedOut(java.lang.String from) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(from);
mRemote.transact(Stub.TRANSACTION_onReportMeKickedOut, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportParticipantKickedOut(java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onReportParticipantKickedOut, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAbortConversationResult(int errType, int statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeInt(statusCode);
mRemote.transact(Stub.TRANSACTION_onAbortConversationResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onQuitConversationResult(int errType, int statusCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(errType);
_data.writeInt(statusCode);
mRemote.transact(Stub.TRANSACTION_onQuitConversationResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onConferenceNotify(java.lang.String confState, java.util.List<org.gsma.joyn.chat.ConferenceEventData.ConferenceUser> users) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(confState);
_data.writeTypedList(users);
mRemote.transact(Stub.TRANSACTION_onConferenceNotify, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onSessionStarted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onSessionAborted = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onSessionAbortedbyChairman = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onSessionError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onNewMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onNewGeoloc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onReportMessageDeliveredContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onReportMessageDisplayedContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_onReportMessageFailedContact = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_onReportMessageDelivered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_onReportMessageDisplayed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_onReportMessageFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_onReportFailedMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_onReportSentMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_onGroupChatDissolved = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_onInviteParticipantsResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_onComposingEvent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_onParticipantJoined = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_onParticipantLeft = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_onParticipantDisconnected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_onSetChairmanResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_onChairmanChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_onModifySubjectResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_onSubjectChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_onModifyNickNameResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_onNickNameChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_onRemoveParticipantResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_onReportMeKickedOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_onReportParticipantKickedOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_onAbortConversationResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_onQuitConversationResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_onConferenceNotify = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
}
public void onSessionStarted() throws android.os.RemoteException;
public void onSessionAborted() throws android.os.RemoteException;
public void onSessionAbortedbyChairman() throws android.os.RemoteException;
public void onSessionError(int reason) throws android.os.RemoteException;
public void onNewMessage(org.gsma.joyn.chat.ChatMessage message) throws android.os.RemoteException;
public void onNewGeoloc(org.gsma.joyn.chat.GeolocMessage message) throws android.os.RemoteException;
public void onReportMessageDeliveredContact(java.lang.String msgId, java.lang.String contact) throws android.os.RemoteException;
public void onReportMessageDisplayedContact(java.lang.String msgId, java.lang.String contact) throws android.os.RemoteException;
public void onReportMessageFailedContact(java.lang.String msgId, java.lang.String contact) throws android.os.RemoteException;
public void onReportMessageDelivered(java.lang.String msgId) throws android.os.RemoteException;
public void onReportMessageDisplayed(java.lang.String msgId) throws android.os.RemoteException;
public void onReportMessageFailed(java.lang.String msgId) throws android.os.RemoteException;
public void onReportFailedMessage(java.lang.String msgId, int errtype, java.lang.String statusCode) throws android.os.RemoteException;
public void onReportSentMessage(java.lang.String msgId) throws android.os.RemoteException;
public void onGroupChatDissolved() throws android.os.RemoteException;
public void onInviteParticipantsResult(int errType, java.lang.String statusCode) throws android.os.RemoteException;
public void onComposingEvent(java.lang.String contact, boolean status) throws android.os.RemoteException;
public void onParticipantJoined(java.lang.String contact, java.lang.String contactDisplayname) throws android.os.RemoteException;
public void onParticipantLeft(java.lang.String contact) throws android.os.RemoteException;
public void onParticipantDisconnected(java.lang.String contact) throws android.os.RemoteException;
public void onSetChairmanResult(int errType, int statusCode) throws android.os.RemoteException;
public void onChairmanChanged(java.lang.String newChairman) throws android.os.RemoteException;
public void onModifySubjectResult(int errType, int statusCode) throws android.os.RemoteException;
public void onSubjectChanged(java.lang.String newSubject) throws android.os.RemoteException;
public void onModifyNickNameResult(int errType, int statusCode) throws android.os.RemoteException;
public void onNickNameChanged(java.lang.String contact, java.lang.String newNickName) throws android.os.RemoteException;
public void onRemoveParticipantResult(int errType, int statusCode, java.lang.String participant) throws android.os.RemoteException;
public void onReportMeKickedOut(java.lang.String from) throws android.os.RemoteException;
public void onReportParticipantKickedOut(java.lang.String contact) throws android.os.RemoteException;
public void onAbortConversationResult(int errType, int statusCode) throws android.os.RemoteException;
public void onQuitConversationResult(int errType, int statusCode) throws android.os.RemoteException;
public void onConferenceNotify(java.lang.String confState, java.util.List<org.gsma.joyn.chat.ConferenceEventData.ConferenceUser> users) throws android.os.RemoteException;
}
