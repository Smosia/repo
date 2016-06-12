/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/ft/INewFileTransferListener.aidl
 */
package org.gsma.joyn.ft;
/**
 * Callback method for new file transfer invitations and delivery reports
 */
public interface INewFileTransferListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.ft.INewFileTransferListener
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.ft.INewFileTransferListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.ft.INewFileTransferListener interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.ft.INewFileTransferListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.ft.INewFileTransferListener))) {
return ((org.gsma.joyn.ft.INewFileTransferListener)iin);
}
return new org.gsma.joyn.ft.INewFileTransferListener.Stub.Proxy(obj);
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
case TRANSACTION_onNewFileTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onNewFileTransfer(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportFileDelivered:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportFileDelivered(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onReportFileDisplayed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.onReportFileDisplayed(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onFileDeliveredReport:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onFileDeliveredReport(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onFileDisplayedReport:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onFileDisplayedReport(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onNewFileTransferReceived:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _arg2;
_arg2 = (0!=data.readInt());
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
int _arg5;
_arg5 = data.readInt();
this.onNewFileTransferReceived(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
return true;
}
case TRANSACTION_onNewPublicAccountChatFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _arg2;
_arg2 = (0!=data.readInt());
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.String _arg4;
_arg4 = data.readString();
this.onNewPublicAccountChatFile(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_onNewBurnFileTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
this.onNewBurnFileTransfer(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.ft.INewFileTransferListener
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
@Override public void onNewFileTransfer(java.lang.String transferId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
mRemote.transact(Stub.TRANSACTION_onNewFileTransfer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportFileDelivered(java.lang.String transferId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
mRemote.transact(Stub.TRANSACTION_onReportFileDelivered, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onReportFileDisplayed(java.lang.String transferId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
mRemote.transact(Stub.TRANSACTION_onReportFileDisplayed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onFileDeliveredReport(java.lang.String transferId, java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onFileDeliveredReport, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onFileDisplayedReport(java.lang.String transferId, java.lang.String contact) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
_data.writeString(contact);
mRemote.transact(Stub.TRANSACTION_onFileDisplayedReport, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNewFileTransferReceived(java.lang.String transferId, boolean isAutoAccept, boolean isGroup, java.lang.String chatSessionId, java.lang.String ChatId, int timeLen) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
_data.writeInt(((isAutoAccept)?(1):(0)));
_data.writeInt(((isGroup)?(1):(0)));
_data.writeString(chatSessionId);
_data.writeString(ChatId);
_data.writeInt(timeLen);
mRemote.transact(Stub.TRANSACTION_onNewFileTransferReceived, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNewPublicAccountChatFile(java.lang.String transferId, boolean isAutoAccept, boolean isGroup, java.lang.String chatSessionId, java.lang.String ChatId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
_data.writeInt(((isAutoAccept)?(1):(0)));
_data.writeInt(((isGroup)?(1):(0)));
_data.writeString(chatSessionId);
_data.writeString(ChatId);
mRemote.transact(Stub.TRANSACTION_onNewPublicAccountChatFile, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onNewBurnFileTransfer(java.lang.String transferId, boolean isGroup, java.lang.String chatSessionId, java.lang.String ChatId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
_data.writeInt(((isGroup)?(1):(0)));
_data.writeString(chatSessionId);
_data.writeString(ChatId);
mRemote.transact(Stub.TRANSACTION_onNewBurnFileTransfer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onNewFileTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onReportFileDelivered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_onReportFileDisplayed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_onFileDeliveredReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_onFileDisplayedReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_onNewFileTransferReceived = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_onNewPublicAccountChatFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_onNewBurnFileTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
public void onNewFileTransfer(java.lang.String transferId) throws android.os.RemoteException;
public void onReportFileDelivered(java.lang.String transferId) throws android.os.RemoteException;
public void onReportFileDisplayed(java.lang.String transferId) throws android.os.RemoteException;
public void onFileDeliveredReport(java.lang.String transferId, java.lang.String contact) throws android.os.RemoteException;
public void onFileDisplayedReport(java.lang.String transferId, java.lang.String contact) throws android.os.RemoteException;
public void onNewFileTransferReceived(java.lang.String transferId, boolean isAutoAccept, boolean isGroup, java.lang.String chatSessionId, java.lang.String ChatId, int timeLen) throws android.os.RemoteException;
public void onNewPublicAccountChatFile(java.lang.String transferId, boolean isAutoAccept, boolean isGroup, java.lang.String chatSessionId, java.lang.String ChatId) throws android.os.RemoteException;
public void onNewBurnFileTransfer(java.lang.String transferId, boolean isGroup, java.lang.String chatSessionId, java.lang.String ChatId) throws android.os.RemoteException;
}
