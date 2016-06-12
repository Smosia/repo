/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/opt/net/ims/src/org/gsma/joyn/ft/IFileSpamReportListener.aidl
 */
package org.gsma.joyn.ft;
/**
 * New chat invitation event listener
 */
public interface IFileSpamReportListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements org.gsma.joyn.ft.IFileSpamReportListener
{
private static final java.lang.String DESCRIPTOR = "org.gsma.joyn.ft.IFileSpamReportListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an org.gsma.joyn.ft.IFileSpamReportListener interface,
 * generating a proxy if needed.
 */
public static org.gsma.joyn.ft.IFileSpamReportListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof org.gsma.joyn.ft.IFileSpamReportListener))) {
return ((org.gsma.joyn.ft.IFileSpamReportListener)iin);
}
return new org.gsma.joyn.ft.IFileSpamReportListener.Stub.Proxy(obj);
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
case TRANSACTION_onFileSpamReportSuccess:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.onFileSpamReportSuccess(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onFileSpamReportFailed:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
this.onFileSpamReportFailed(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements org.gsma.joyn.ft.IFileSpamReportListener
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
@Override public void onFileSpamReportSuccess(java.lang.String contact, java.lang.String ftId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(ftId);
mRemote.transact(Stub.TRANSACTION_onFileSpamReportSuccess, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onFileSpamReportFailed(java.lang.String contact, java.lang.String ftId, int errorCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(contact);
_data.writeString(ftId);
_data.writeInt(errorCode);
mRemote.transact(Stub.TRANSACTION_onFileSpamReportFailed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onFileSpamReportSuccess = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onFileSpamReportFailed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void onFileSpamReportSuccess(java.lang.String contact, java.lang.String ftId) throws android.os.RemoteException;
public void onFileSpamReportFailed(java.lang.String contact, java.lang.String ftId, int errorCode) throws android.os.RemoteException;
}
