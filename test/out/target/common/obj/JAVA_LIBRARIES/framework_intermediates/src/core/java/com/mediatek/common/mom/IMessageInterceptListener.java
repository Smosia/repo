/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/IMessageInterceptListener.aidl
 */
package com.mediatek.common.mom;
/**
 * The interface is designed for message interception.
 * The callback fucntion is set through
 * registerMessageInterceptListener() in MobileManagerService.
 * @hide
 */
public interface IMessageInterceptListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.IMessageInterceptListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.IMessageInterceptListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.IMessageInterceptListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.IMessageInterceptListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.IMessageInterceptListener))) {
return ((com.mediatek.common.mom.IMessageInterceptListener)iin);
}
return new com.mediatek.common.mom.IMessageInterceptListener.Stub.Proxy(obj);
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
case TRANSACTION_onNewSMSCheck:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.onNewSMSCheck(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.IMessageInterceptListener
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
/**
     * The callback will be triggered when new message arrived completed.
     * 
     * @param packageName The package notifies the monitored notification.
     * @param notification The information of the nogification.
     */
@Override public boolean onNewSMSCheck(android.os.Bundle intent) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((intent!=null)) {
_data.writeInt(1);
intent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onNewSMSCheck, _data, _reply, 0);
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
static final int TRANSACTION_onNewSMSCheck = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * The callback will be triggered when new message arrived completed.
     * 
     * @param packageName The package notifies the monitored notification.
     * @param notification The information of the nogification.
     */
public boolean onNewSMSCheck(android.os.Bundle intent) throws android.os.RemoteException;
}
