/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/IRequestedPermissionCallback.aidl
 */
package com.mediatek.common.mom;
/**
 * The interface is designed for listening permission related events,
 * and can register callback function through
 * registerPermissionCallback(IPermissionCallback callback) in MobileManagerService.
 * @hide
 */
public interface IRequestedPermissionCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.IRequestedPermissionCallback
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.IRequestedPermissionCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.IRequestedPermissionCallback interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.IRequestedPermissionCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.IRequestedPermissionCallback))) {
return ((com.mediatek.common.mom.IRequestedPermissionCallback)iin);
}
return new com.mediatek.common.mom.IRequestedPermissionCallback.Stub.Proxy(obj);
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
case TRANSACTION_onPermissionCheckResult:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
this.onPermissionCheckResult(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.IRequestedPermissionCallback
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
     * The callback will be triggered when permission checking has done.
     * Set the callback
     * 
     * @param permissionName The permission binding to the API.
     * @param uid The user id of the package while checking.
     * @param result Returns MobileManager.PERMISSION_GRANTED when user allows this operation,
     *               otherwise, MobileManager.PERMISSION_DENIED should be returned.
     */
@Override public void onPermissionCheckResult(java.lang.String permissionName, int uid, int result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(permissionName);
_data.writeInt(uid);
_data.writeInt(result);
mRemote.transact(Stub.TRANSACTION_onPermissionCheckResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onPermissionCheckResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * The callback will be triggered when permission checking has done.
     * Set the callback
     * 
     * @param permissionName The permission binding to the API.
     * @param uid The user id of the package while checking.
     * @param result Returns MobileManager.PERMISSION_GRANTED when user allows this operation,
     *               otherwise, MobileManager.PERMISSION_DENIED should be returned.
     */
public void onPermissionCheckResult(java.lang.String permissionName, int uid, int result) throws android.os.RemoteException;
}
