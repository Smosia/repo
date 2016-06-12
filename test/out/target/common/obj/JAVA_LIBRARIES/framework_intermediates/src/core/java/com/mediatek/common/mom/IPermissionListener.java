/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/IPermissionListener.aidl
 */
package com.mediatek.common.mom;
/**
 * The interface is designed for listening permission related events,
 * and can register callback function through
 * registerPermissionListener(IPermissionListener listener) in MobileManagerService.
 * @hide
 */
public interface IPermissionListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.IPermissionListener
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.IPermissionListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.IPermissionListener interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.IPermissionListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.IPermissionListener))) {
return ((com.mediatek.common.mom.IPermissionListener)iin);
}
return new com.mediatek.common.mom.IPermissionListener.Stub.Proxy(obj);
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
case TRANSACTION_onPermissionCheck:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.mom.PermissionRecord _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.common.mom.PermissionRecord.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
android.os.Bundle _arg3;
if ((0!=data.readInt())) {
_arg3 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
boolean _result = this.onPermissionCheck(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_onPermissionChange:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.mom.PermissionRecord _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.common.mom.PermissionRecord.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onPermissionChange(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.IPermissionListener
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
     * The callback will be triggered when monitored API is invoked.
     * 
     * @param record The permission record for this checking.
     *               mPackageName: The package invokes the API.
     *               mPermissionName: The permission binding to the API.
     *               mStatus: The status of permission when checking.
     *                        MobileManager.PERMISSION_STATUS_CHECK: The checking requires user's confirmation.
     *                        MobileManager.PERMISSION_STATUS_DENIED: The API is revoked for this package.
     *                        MobileManager.PERMISSION_STATUS_GRANTED won't trigger this function.
     * @param flag The attributes of the permission.
     *             MobileManager.PERMISSION_FLAG_USERCONFIRM: This permission always requires user's confirmation.
     * 
     * @param uid The user id of the package.
     * @param data The addition information for checking
     * @return Returns true when user allows this operation,
     *         otherwise, false should be returned.
     */
@Override public boolean onPermissionCheck(com.mediatek.common.mom.PermissionRecord record, int flag, int uid, android.os.Bundle data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((record!=null)) {
_data.writeInt(1);
record.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(flag);
_data.writeInt(uid);
if ((data!=null)) {
_data.writeInt(1);
data.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onPermissionCheck, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * The callback will be triggered when a permission had been revoked/granted
     * to a package. This will be triggered only by "system" authority user.
     * 
     * @param record The permission record for this checking.
     *               mPackageName: The package invokes the API.
     *               mPermissionName: The permission binding to the API.
     */
@Override public void onPermissionChange(com.mediatek.common.mom.PermissionRecord record) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((record!=null)) {
_data.writeInt(1);
record.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onPermissionChange, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onPermissionCheck = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onPermissionChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * The callback will be triggered when monitored API is invoked.
     * 
     * @param record The permission record for this checking.
     *               mPackageName: The package invokes the API.
     *               mPermissionName: The permission binding to the API.
     *               mStatus: The status of permission when checking.
     *                        MobileManager.PERMISSION_STATUS_CHECK: The checking requires user's confirmation.
     *                        MobileManager.PERMISSION_STATUS_DENIED: The API is revoked for this package.
     *                        MobileManager.PERMISSION_STATUS_GRANTED won't trigger this function.
     * @param flag The attributes of the permission.
     *             MobileManager.PERMISSION_FLAG_USERCONFIRM: This permission always requires user's confirmation.
     * 
     * @param uid The user id of the package.
     * @param data The addition information for checking
     * @return Returns true when user allows this operation,
     *         otherwise, false should be returned.
     */
public boolean onPermissionCheck(com.mediatek.common.mom.PermissionRecord record, int flag, int uid, android.os.Bundle data) throws android.os.RemoteException;
/**
     * The callback will be triggered when a permission had been revoked/granted
     * to a package. This will be triggered only by "system" authority user.
     * 
     * @param record The permission record for this checking.
     *               mPackageName: The package invokes the API.
     *               mPermissionName: The permission binding to the API.
     */
public void onPermissionChange(com.mediatek.common.mom.PermissionRecord record) throws android.os.RemoteException;
}
