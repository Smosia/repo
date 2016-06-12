/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/datashaping/IDataShapingManager.aidl
 */
package com.mediatek.datashaping;
/** {@hide} */
public interface IDataShapingManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.datashaping.IDataShapingManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.datashaping.IDataShapingManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.datashaping.IDataShapingManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.datashaping.IDataShapingManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.datashaping.IDataShapingManager))) {
return ((com.mediatek.datashaping.IDataShapingManager)iin);
}
return new com.mediatek.datashaping.IDataShapingManager.Stub.Proxy(obj);
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
case TRANSACTION_enableDataShaping:
{
data.enforceInterface(DESCRIPTOR);
this.enableDataShaping();
reply.writeNoException();
return true;
}
case TRANSACTION_disableDataShaping:
{
data.enforceInterface(DESCRIPTOR);
this.disableDataShaping();
reply.writeNoException();
return true;
}
case TRANSACTION_openLteDataUpLinkGate:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.openLteDataUpLinkGate(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setDeviceIdleMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setDeviceIdleMode(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.datashaping.IDataShapingManager
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
@Override public void enableDataShaping() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_enableDataShaping, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void disableDataShaping() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disableDataShaping, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean openLteDataUpLinkGate(boolean isForce) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isForce)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_openLteDataUpLinkGate, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/// M: integrate Doze and App Standby

@Override public void setDeviceIdleMode(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setDeviceIdleMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_enableDataShaping = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_disableDataShaping = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_openLteDataUpLinkGate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setDeviceIdleMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void enableDataShaping() throws android.os.RemoteException;
public void disableDataShaping() throws android.os.RemoteException;
public boolean openLteDataUpLinkGate(boolean isForce) throws android.os.RemoteException;
/// M: integrate Doze and App Standby

public void setDeviceIdleMode(boolean enabled) throws android.os.RemoteException;
}
