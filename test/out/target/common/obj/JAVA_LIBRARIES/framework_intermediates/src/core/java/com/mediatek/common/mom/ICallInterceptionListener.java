/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/ICallInterceptionListener.aidl
 */
package com.mediatek.common.mom;
/** @hide */
public interface ICallInterceptionListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.ICallInterceptionListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.ICallInterceptionListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.ICallInterceptionListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.ICallInterceptionListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.ICallInterceptionListener))) {
return ((com.mediatek.common.mom.ICallInterceptionListener)iin);
}
return new com.mediatek.common.mom.ICallInterceptionListener.Stub.Proxy(obj);
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
case TRANSACTION_onIncomingCallCheck:
{
data.enforceInterface(DESCRIPTOR);
android.os.Bundle _arg0;
if ((0!=data.readInt())) {
_arg0 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.onIncomingCallCheck(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.ICallInterceptionListener
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
    * To check if the incoming call can be accepted or not.
    *
    *  @param callInformation Contains phone number(String), call type(integer) and slot id(integer).
    *  @return   Return true if the call can be accepted, else return false.
    */
@Override public boolean onIncomingCallCheck(android.os.Bundle callInformation) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((callInformation!=null)) {
_data.writeInt(1);
callInformation.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onIncomingCallCheck, _data, _reply, 0);
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
static final int TRANSACTION_onIncomingCallCheck = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
    * To check if the incoming call can be accepted or not.
    *
    *  @param callInformation Contains phone number(String), call type(integer) and slot id(integer).
    *  @return   Return true if the call can be accepted, else return false.
    */
public boolean onIncomingCallCheck(android.os.Bundle callInformation) throws android.os.RemoteException;
}
