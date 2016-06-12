/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/multiwindow/IMWSystemUiCallback.aidl
 */
package com.mediatek.common.multiwindow;
/**
 * System private API for talking with the SystemUI.
 *
 * {@hide}
 */
public interface IMWSystemUiCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.multiwindow.IMWSystemUiCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.multiwindow.IMWSystemUiCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.multiwindow.IMWSystemUiCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.multiwindow.IMWSystemUiCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.multiwindow.IMWSystemUiCallback))) {
return ((com.mediatek.common.multiwindow.IMWSystemUiCallback)iin);
}
return new com.mediatek.common.multiwindow.IMWSystemUiCallback.Stub.Proxy(obj);
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
case TRANSACTION_showRestoreButton:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.showRestoreButton(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.multiwindow.IMWSystemUiCallback
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
///for SystemUI

@Override public void showRestoreButton(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_showRestoreButton, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_showRestoreButton = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
///for SystemUI

public void showRestoreButton(boolean flag) throws android.os.RemoteException;
}
