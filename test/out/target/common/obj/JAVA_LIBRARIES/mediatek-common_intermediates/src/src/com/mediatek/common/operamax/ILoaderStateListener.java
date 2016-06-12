/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/operamax/ILoaderStateListener.aidl
 */
package com.mediatek.common.operamax;
public interface ILoaderStateListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.operamax.ILoaderStateListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.operamax.ILoaderStateListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.operamax.ILoaderStateListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.operamax.ILoaderStateListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.operamax.ILoaderStateListener))) {
return ((com.mediatek.common.operamax.ILoaderStateListener)iin);
}
return new com.mediatek.common.operamax.ILoaderStateListener.Stub.Proxy(obj);
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
case TRANSACTION_onTunnelState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onTunnelState(_arg0);
return true;
}
case TRANSACTION_onSavingState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onSavingState(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.operamax.ILoaderStateListener
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
     * Called when the tunnel state is changed.
     *
     * @param state, could be:
     *     1: opened
     *     2: closed
     */
@Override public void onTunnelState(int state) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(state);
mRemote.transact(Stub.TRANSACTION_onTunnelState, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
/**
     * Called when the saving state is changed. Saving will be paused when no available network or using WiFi network.
     *
     * @param state, could be:
     *     1: started
     *     2: stopped
     *     3: paused
     */
@Override public void onSavingState(int state) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(state);
mRemote.transact(Stub.TRANSACTION_onSavingState, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onTunnelState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onSavingState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * Called when the tunnel state is changed.
     *
     * @param state, could be:
     *     1: opened
     *     2: closed
     */
public void onTunnelState(int state) throws android.os.RemoteException;
/**
     * Called when the saving state is changed. Saving will be paused when no available network or using WiFi network.
     *
     * @param state, could be:
     *     1: started
     *     2: stopped
     *     3: paused
     */
public void onSavingState(int state) throws android.os.RemoteException;
}
