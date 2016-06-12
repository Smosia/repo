/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/IPackageInstallCallback.aidl
 */
package com.mediatek.common.mom;
/**
 * Callbacks for package installation from MobileManagerService.
 * @hide
 */
public interface IPackageInstallCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.IPackageInstallCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.IPackageInstallCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.IPackageInstallCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.IPackageInstallCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.IPackageInstallCallback))) {
return ((com.mediatek.common.mom.IPackageInstallCallback)iin);
}
return new com.mediatek.common.mom.IPackageInstallCallback.Stub.Proxy(obj);
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
case TRANSACTION_onPackageInstalled:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.onPackageInstalled(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.IPackageInstallCallback
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
     * The callback will be triggered when package installed.
     *
     * @param packageName The installed package name.
     * @param returnCode Installation return code: this is passed to the {@link IPackageInstallCallback} by
     *                   {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}.
     *
     */
@Override public void onPackageInstalled(java.lang.String packageName, int returnCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeInt(returnCode);
mRemote.transact(Stub.TRANSACTION_onPackageInstalled, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onPackageInstalled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * The callback will be triggered when package installed.
     *
     * @param packageName The installed package name.
     * @param returnCode Installation return code: this is passed to the {@link IPackageInstallCallback} by
     *                   {@link #installPackage(android.net.Uri, IPackageInstallObserver, int)}.
     *
     */
public void onPackageInstalled(java.lang.String packageName, int returnCode) throws android.os.RemoteException;
}
