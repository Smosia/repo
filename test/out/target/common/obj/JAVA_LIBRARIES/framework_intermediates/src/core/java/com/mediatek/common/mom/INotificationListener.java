/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/INotificationListener.aidl
 */
package com.mediatek.common.mom;
/**
 * The interface is designed for notification interception.
 * The callback fucntion is set through
 * registerNotificationListener() in MobileManagerService.
 * @hide
 */
public interface INotificationListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.INotificationListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.INotificationListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.INotificationListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.INotificationListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.INotificationListener))) {
return ((com.mediatek.common.mom.INotificationListener)iin);
}
return new com.mediatek.common.mom.INotificationListener.Stub.Proxy(obj);
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
case TRANSACTION_onNotificationBlocked:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
android.os.Bundle _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.onNotificationBlocked(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.INotificationListener
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
     * The callback will be triggered when notification blocking has done.
     * 
     * @param packageName The package invokes the API.
     * @param notification The information of the notification.
     */
@Override public void onNotificationBlocked(java.lang.String packageName, android.os.Bundle notification) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
if ((notification!=null)) {
_data.writeInt(1);
notification.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onNotificationBlocked, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onNotificationBlocked = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * The callback will be triggered when notification blocking has done.
     * 
     * @param packageName The package invokes the API.
     * @param notification The information of the notification.
     */
public void onNotificationBlocked(java.lang.String packageName, android.os.Bundle notification) throws android.os.RemoteException;
}
