/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetoothle/pxp/IProximityProfileServiceCallback.aidl
 */
package com.mediatek.bluetoothle.pxp;
public interface IProximityProfileServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback))) {
return ((com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback)iin);
}
return new com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback.Stub.Proxy(obj);
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
case TRANSACTION_onDistanceValueChange:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.onDistanceValueChange(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_onAlertStatusChange:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onAlertStatusChange(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetoothle.pxp.IProximityProfileServiceCallback
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
@Override public void onDistanceValueChange(java.lang.String address, int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_onDistanceValueChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onAlertStatusChange(java.lang.String address, boolean isAlert) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(address);
_data.writeInt(((isAlert)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onAlertStatusChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onDistanceValueChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onAlertStatusChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void onDistanceValueChange(java.lang.String address, int value) throws android.os.RemoteException;
public void onAlertStatusChange(java.lang.String address, boolean isAlert) throws android.os.RemoteException;
}
