/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/base/bluetooth/java/com/mediatek/bluetoothle/bleservice/IProfileServiceManagerCallback.aidl
 */
package com.mediatek.bluetoothle.bleservice;
/**
 * Interface for IProfileServiceManagerCallback
 *
 * @hide
 */
public interface IProfileServiceManagerCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback))) {
return ((com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback)iin);
}
return new com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback.Stub.Proxy(obj);
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
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.bluetoothle.bleservice.IProfileServiceManagerCallback
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
}
}
}
