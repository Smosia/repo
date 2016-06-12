/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/ft/IFileTransferService.aidl
 */
package org.gsma.joyn.ft;
/**
 * File transfer service API
 */
public interface IFileTransferService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.ft.IFileTransferService
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.ft.IFileTransferService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.ft.IFileTransferService interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.ft.IFileTransferService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.ft.IFileTransferService))) {
return ((org.gsma.joyn.ft.IFileTransferService)iin);
}
return new org.gsma.joyn.ft.IFileTransferService.Stub.Proxy(obj);
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
case TRANSACTION_isServiceRegistered:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isServiceRegistered();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_addServiceRegistrationListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.IJoynServiceRegistrationListener _arg0;
_arg0 = org.gsma.joyn.IJoynServiceRegistrationListener.Stub.asInterface(data.readStrongBinder());
this.addServiceRegistrationListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeServiceRegistrationListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.IJoynServiceRegistrationListener _arg0;
_arg0 = org.gsma.joyn.IJoynServiceRegistrationListener.Stub.asInterface(data.readStrongBinder());
this.removeServiceRegistrationListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getConfiguration:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.ft.FileTransferServiceConfiguration _result = this.getConfiguration();
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
case TRANSACTION_getFileTransfers:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<android.os.IBinder> _result = this.getFileTransfers();
reply.writeNoException();
reply.writeBinderList(_result);
return true;
}
case TRANSACTION_getFileTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
org.gsma.joyn.ft.IFileTransfer _result = this.getFileTransfer(_arg0);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.transferFile(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_resumeFileTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg1;
_arg1 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.resumeFileTransfer(_arg0, _arg1);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferBurnFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.transferBurnFile(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferGeoLocFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.transferGeoLocFile(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferPublicChatFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
int _arg4;
_arg4 = data.readInt();
org.gsma.joyn.ft.IFileTransfer _result = this.transferPublicChatFile(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferLargeModeFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.transferLargeModeFile(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferLargeModeBurnFile:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.transferLargeModeBurnFile(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferFileToGroup:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<java.lang.String> _arg1;
_arg1 = data.createStringArrayList();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
int _arg4;
_arg4 = data.readInt();
org.gsma.joyn.ft.IFileTransferListener _arg5;
_arg5 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.transferFileToGroup(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferMedia:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
int _arg4;
_arg4 = data.readInt();
org.gsma.joyn.ft.IFileTransfer _result = this.transferMedia(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_transferFileToMultirecepient:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<java.lang.String> _arg0;
_arg0 = data.createStringArrayList();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _arg2;
_arg2 = (0!=data.readInt());
org.gsma.joyn.ft.IFileTransferListener _arg3;
_arg3 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
int _arg4;
_arg4 = data.readInt();
org.gsma.joyn.ft.IFileTransfer _result = this.transferFileToMultirecepient(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_addNewFileTransferListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.ft.INewFileTransferListener _arg0;
_arg0 = org.gsma.joyn.ft.INewFileTransferListener.Stub.asInterface(data.readStrongBinder());
this.addNewFileTransferListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeNewFileTransferListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.ft.INewFileTransferListener _arg0;
_arg0 = org.gsma.joyn.ft.INewFileTransferListener.Stub.asInterface(data.readStrongBinder());
this.removeNewFileTransferListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getServiceVersion:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getServiceVersion();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_initiateFileSpamReport:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.initiateFileSpamReport(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_addFileSpamReportListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.ft.IFileSpamReportListener _arg0;
_arg0 = org.gsma.joyn.ft.IFileSpamReportListener.Stub.asInterface(data.readStrongBinder());
this.addFileSpamReportListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeFileSpamReportListener:
{
data.enforceInterface(DESCRIPTOR);
org.gsma.joyn.ft.IFileSpamReportListener _arg0;
_arg0 = org.gsma.joyn.ft.IFileSpamReportListener.Stub.asInterface(data.readStrongBinder());
this.removeFileSpamReportListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getMaxFileTransfers:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMaxFileTransfers();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_resumeGroupFileTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg2;
_arg2 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
org.gsma.joyn.ft.IFileTransfer _result = this.resumeGroupFileTransfer(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
case TRANSACTION_resumePublicFileTransfer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
org.gsma.joyn.ft.IFileTransferListener _arg1;
_arg1 = org.gsma.joyn.ft.IFileTransferListener.Stub.asInterface(data.readStrongBinder());
int _arg2;
_arg2 = data.readInt();
org.gsma.joyn.ft.IFileTransfer _result = this.resumePublicFileTransfer(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeStrongBinder((((_result!=null))?(_result.asBinder()):(null)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.ft.IFileTransferService
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
@Override public boolean isServiceRegistered() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isServiceRegistered, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void addServiceRegistrationListener(org.gsma.joyn.IJoynServiceRegistrationListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addServiceRegistrationListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeServiceRegistrationListener(org.gsma.joyn.IJoynServiceRegistrationListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeServiceRegistrationListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public org.gsma.joyn.ft.FileTransferServiceConfiguration getConfiguration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.FileTransferServiceConfiguration _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getConfiguration, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = org.gsma.joyn.ft.FileTransferServiceConfiguration.CREATOR.createFromParcel(_reply);
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
@Override public java.util.List<android.os.IBinder> getFileTransfers() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<android.os.IBinder> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getFileTransfers, _data, _reply, 0);
_reply.readException();
_result = _reply.createBinderArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer getFileTransfer(java.lang.String transferId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(transferId);
mRemote.transact(Stub.TRANSACTION_getFileTransfer, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_transferFile, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer resumeFileTransfer(java.lang.String fileTranferId, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(fileTranferId);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_resumeFileTransfer, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferBurnFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_transferBurnFile, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferGeoLocFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_transferGeoLocFile, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferPublicChatFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
_data.writeInt(timeLen);
mRemote.transact(Stub.TRANSACTION_transferPublicChatFile, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferLargeModeFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_transferLargeModeFile, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferLargeModeBurnFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_transferLargeModeBurnFile, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferFileToGroup(java.lang.String chatId, java.util.List<java.lang.String> contacts, java.lang.String filename, java.lang.String fileicon, int timeLen, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(chatId);
_data.writeStringList(contacts);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeInt(timeLen);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_transferFileToGroup, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferMedia(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(filename);
_data.writeString(fileicon);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
_data.writeInt(timeLen);
mRemote.transact(Stub.TRANSACTION_transferMedia, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer transferFileToMultirecepient(java.util.List<java.lang.String> contacts, java.lang.String filename, boolean fileIcon, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringList(contacts);
_data.writeString(filename);
_data.writeInt(((fileIcon)?(1):(0)));
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
_data.writeInt(timeLen);
mRemote.transact(Stub.TRANSACTION_transferFileToMultirecepient, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void addNewFileTransferListener(org.gsma.joyn.ft.INewFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addNewFileTransferListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeNewFileTransferListener(org.gsma.joyn.ft.INewFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeNewFileTransferListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getServiceVersion() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getServiceVersion, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void initiateFileSpamReport(java.lang.String contact, java.lang.String messageId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(messageId);
mRemote.transact(Stub.TRANSACTION_initiateFileSpamReport, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addFileSpamReportListener(org.gsma.joyn.ft.IFileSpamReportListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addFileSpamReportListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeFileSpamReportListener(org.gsma.joyn.ft.IFileSpamReportListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeFileSpamReportListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getMaxFileTransfers() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxFileTransfers, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer resumeGroupFileTransfer(java.lang.String chatId, java.lang.String fileTranferId, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(chatId);
_data.writeString(fileTranferId);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_resumeGroupFileTransfer, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public org.gsma.joyn.ft.IFileTransfer resumePublicFileTransfer(java.lang.String fileTranferId, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
org.gsma.joyn.ft.IFileTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(fileTranferId);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
_data.writeInt(timeLen);
mRemote.transact(Stub.TRANSACTION_resumePublicFileTransfer, _data, _reply, 0);
_reply.readException();
_result = org.gsma.joyn.ft.IFileTransfer.Stub.asInterface(_reply.readStrongBinder());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_isServiceRegistered = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_addServiceRegistrationListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_removeServiceRegistrationListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_getConfiguration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getFileTransfers = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getFileTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_transferFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_resumeFileTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_transferBurnFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_transferGeoLocFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_transferPublicChatFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_transferLargeModeFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_transferLargeModeBurnFile = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_transferFileToGroup = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_transferMedia = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_transferFileToMultirecepient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_addNewFileTransferListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_removeNewFileTransferListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getServiceVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_initiateFileSpamReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_addFileSpamReportListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_removeFileSpamReportListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getMaxFileTransfers = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_resumeGroupFileTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_resumePublicFileTransfer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
}
public boolean isServiceRegistered() throws android.os.RemoteException;
public void addServiceRegistrationListener(org.gsma.joyn.IJoynServiceRegistrationListener listener) throws android.os.RemoteException;
public void removeServiceRegistrationListener(org.gsma.joyn.IJoynServiceRegistrationListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.FileTransferServiceConfiguration getConfiguration() throws android.os.RemoteException;
public java.util.List<android.os.IBinder> getFileTransfers() throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer getFileTransfer(java.lang.String transferId) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer resumeFileTransfer(java.lang.String fileTranferId, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferBurnFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferGeoLocFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferPublicChatFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferLargeModeFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferLargeModeBurnFile(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferFileToGroup(java.lang.String chatId, java.util.List<java.lang.String> contacts, java.lang.String filename, java.lang.String fileicon, int timeLen, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferMedia(java.lang.String contact, java.lang.String filename, java.lang.String fileicon, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer transferFileToMultirecepient(java.util.List<java.lang.String> contacts, java.lang.String filename, boolean fileIcon, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException;
public void addNewFileTransferListener(org.gsma.joyn.ft.INewFileTransferListener listener) throws android.os.RemoteException;
public void removeNewFileTransferListener(org.gsma.joyn.ft.INewFileTransferListener listener) throws android.os.RemoteException;
public int getServiceVersion() throws android.os.RemoteException;
public void initiateFileSpamReport(java.lang.String contact, java.lang.String messageId) throws android.os.RemoteException;
public void addFileSpamReportListener(org.gsma.joyn.ft.IFileSpamReportListener listener) throws android.os.RemoteException;
public void removeFileSpamReportListener(org.gsma.joyn.ft.IFileSpamReportListener listener) throws android.os.RemoteException;
public int getMaxFileTransfers() throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer resumeGroupFileTransfer(java.lang.String chatId, java.lang.String fileTranferId, org.gsma.joyn.ft.IFileTransferListener listener) throws android.os.RemoteException;
public org.gsma.joyn.ft.IFileTransfer resumePublicFileTransfer(java.lang.String fileTranferId, org.gsma.joyn.ft.IFileTransferListener listener, int timeLen) throws android.os.RemoteException;
}
