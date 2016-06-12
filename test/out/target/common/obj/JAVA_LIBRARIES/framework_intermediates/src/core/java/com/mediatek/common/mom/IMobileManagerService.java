/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/common/mom/IMobileManagerService.aidl
 */
package com.mediatek.common.mom;
/** @hide */
public interface IMobileManagerService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.mom.IMobileManagerService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.mom.IMobileManagerService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.mom.IMobileManagerService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.mom.IMobileManagerService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.mom.IMobileManagerService))) {
return ((com.mediatek.common.mom.IMobileManagerService)iin);
}
return new com.mediatek.common.mom.IMobileManagerService.Stub.Proxy(obj);
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
case TRANSACTION_systemReady:
{
data.enforceInterface(DESCRIPTOR);
this.systemReady();
reply.writeNoException();
return true;
}
case TRANSACTION_getVersionName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getVersionName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_attach:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.mom.IMobileConnectionCallback _arg0;
_arg0 = com.mediatek.common.mom.IMobileConnectionCallback.Stub.asInterface(data.readStrongBinder());
boolean _result = this.attach(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_detach:
{
data.enforceInterface(DESCRIPTOR);
this.detach();
reply.writeNoException();
return true;
}
case TRANSACTION_clearAllSettings:
{
data.enforceInterface(DESCRIPTOR);
this.clearAllSettings();
reply.writeNoException();
return true;
}
case TRANSACTION_clearPackageSettings:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.clearPackageSettings(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_registerManagerApListener:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.IBinder _arg1;
_arg1 = data.readStrongBinder();
this.registerManagerApListener(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_triggerManagerApListener:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.Bundle _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _arg2;
_arg2 = data.readInt();
int _result = this.triggerManagerApListener(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_triggerManagerApListenerAsync:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
android.os.Bundle _arg1;
if ((0!=data.readInt())) {
_arg1 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
int _arg2;
_arg2 = data.readInt();
android.os.IBinder _arg3;
_arg3 = data.readStrongBinder();
this.triggerManagerApListenerAsync(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_enablePermissionController:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.enablePermissionController(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getInstalledPackages:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<android.content.pm.PackageInfo> _result = this.getInstalledPackages();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_getPackageGrantedPermissions:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.util.List<com.mediatek.common.mom.Permission> _result = this.getPackageGrantedPermissions(_arg0);
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_setPermissionRecord:
{
data.enforceInterface(DESCRIPTOR);
com.mediatek.common.mom.PermissionRecord _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mediatek.common.mom.PermissionRecord.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.setPermissionRecord(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setPermissionRecords:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mediatek.common.mom.PermissionRecord> _arg0;
_arg0 = data.createTypedArrayList(com.mediatek.common.mom.PermissionRecord.CREATOR);
this.setPermissionRecords(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setPermissionCache:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mediatek.common.mom.PermissionRecord> _arg0;
_arg0 = data.createTypedArrayList(com.mediatek.common.mom.PermissionRecord.CREATOR);
this.setPermissionCache(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_checkPermission:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _result = this.checkPermission(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_checkPermissionAsync:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
com.mediatek.common.mom.IRequestedPermissionCallback _arg2;
_arg2 = com.mediatek.common.mom.IRequestedPermissionCallback.Stub.asInterface(data.readStrongBinder());
this.checkPermissionAsync(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_checkPermissionWithData:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
android.os.Bundle _arg2;
if ((0!=data.readInt())) {
_arg2 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
int _result = this.checkPermissionWithData(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_checkPermissionAsyncWithData:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
android.os.Bundle _arg2;
if ((0!=data.readInt())) {
_arg2 = android.os.Bundle.CREATOR.createFromParcel(data);
}
else {
_arg2 = null;
}
com.mediatek.common.mom.IRequestedPermissionCallback _arg3;
_arg3 = com.mediatek.common.mom.IRequestedPermissionCallback.Stub.asInterface(data.readStrongBinder());
this.checkPermissionAsyncWithData(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_getParentPermission:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getParentPermission(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getUserConfirmTime:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
long _arg1;
_arg1 = data.readLong();
long _result = this.getUserConfirmTime(_arg0, _arg1);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getBootReceiverList:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mediatek.common.mom.ReceiverRecord> _result = this.getBootReceiverList();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
case TRANSACTION_setBootReceiverEnabledSettings:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mediatek.common.mom.ReceiverRecord> _arg0;
_arg0 = data.createTypedArrayList(com.mediatek.common.mom.ReceiverRecord.CREATOR);
this.setBootReceiverEnabledSettings(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setBootReceiverEnabledSetting:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setBootReceiverEnabledSetting(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getBootReceiverEnabledSetting:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.getBootReceiverEnabledSetting(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_filterReceiver:
{
data.enforceInterface(DESCRIPTOR);
android.content.Intent _arg0;
if ((0!=data.readInt())) {
_arg0 = android.content.Intent.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
java.util.List<android.content.pm.ResolveInfo> _arg1;
_arg1 = data.createTypedArrayList(android.content.pm.ResolveInfo.CREATOR);
int _arg2;
_arg2 = data.readInt();
this.filterReceiver(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeTypedList(_arg1);
return true;
}
case TRANSACTION_startMonitorBootReceiver:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.startMonitorBootReceiver(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopMonitorBootReceiver:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.stopMonitorBootReceiver(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_forceStopPackage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.forceStopPackage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_installPackage:
{
data.enforceInterface(DESCRIPTOR);
android.net.Uri _arg0;
if ((0!=data.readInt())) {
_arg0 = android.net.Uri.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
com.mediatek.common.mom.IPackageInstallCallback _arg1;
_arg1 = com.mediatek.common.mom.IPackageInstallCallback.Stub.asInterface(data.readStrongBinder());
this.installPackage(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_deletePackage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.deletePackage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_cancelNotification:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.cancelNotification(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setNotificationEnabledSetting:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.setNotificationEnabledSetting(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getNotificationEnabledSetting:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.getNotificationEnabledSetting(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setNotificationCache:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mediatek.common.mom.NotificationCacheRecord> _arg0;
_arg0 = data.createTypedArrayList(com.mediatek.common.mom.NotificationCacheRecord.CREATOR);
this.setNotificationCache(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_enableInterceptionController:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.enableInterceptionController(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getInterceptionEnabledSetting:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getInterceptionEnabledSetting();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setFirewallPolicy:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
boolean _arg2;
_arg2 = (0!=data.readInt());
this.setFirewallPolicy(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.mom.IMobileManagerService
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
@Override public void systemReady() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_systemReady, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Get the version of MobileManagerService
     *
     * @return Returns version name of MobileManagerService.
     *
     */
@Override public java.lang.String getVersionName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVersionName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Attach to MobileManagerService
     *
     * @param callback The callback will be triggered when the connection terminated or resumed.
     * @return Returns the result of attachment.
     */
@Override public boolean attach(com.mediatek.common.mom.IMobileConnectionCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_attach, _data, _reply, 0);
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
     * Detach from MobileManagerService
     *
     */
@Override public void detach() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_detach, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Clear all settings
     *
     */
@Override public void clearAllSettings() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearAllSettings, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Clear setting for package
     *
     * @param packageName The specific package that setting will be erased.
     */
@Override public void clearPackageSettings(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_clearPackageSettings, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void registerManagerApListener(int controllerID, android.os.IBinder listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(controllerID);
_data.writeStrongBinder(listener);
mRemote.transact(Stub.TRANSACTION_registerManagerApListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int triggerManagerApListener(int ControllerID, android.os.Bundle params, int defaultResult) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ControllerID);
if ((params!=null)) {
_data.writeInt(1);
params.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(defaultResult);
mRemote.transact(Stub.TRANSACTION_triggerManagerApListener, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void triggerManagerApListenerAsync(int ControllerID, android.os.Bundle params, int defaultResult, android.os.IBinder callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ControllerID);
if ((params!=null)) {
_data.writeInt(1);
params.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(defaultResult);
_data.writeStrongBinder(callback);
mRemote.transact(Stub.TRANSACTION_triggerManagerApListenerAsync, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * [Permission Controller Functions]
     */
@Override public void enablePermissionController(boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enablePermissionController, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<android.content.pm.PackageInfo> getInstalledPackages() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<android.content.pm.PackageInfo> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getInstalledPackages, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(android.content.pm.PackageInfo.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public java.util.List<com.mediatek.common.mom.Permission> getPackageGrantedPermissions(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mediatek.common.mom.Permission> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_getPackageGrantedPermissions, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mediatek.common.mom.Permission.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setPermissionRecord(com.mediatek.common.mom.PermissionRecord record) throws android.os.RemoteException
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
mRemote.transact(Stub.TRANSACTION_setPermissionRecord, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setPermissionRecords(java.util.List<com.mediatek.common.mom.PermissionRecord> records) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(records);
mRemote.transact(Stub.TRANSACTION_setPermissionRecords, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setPermissionCache(java.util.List<com.mediatek.common.mom.PermissionRecord> cache) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(cache);
mRemote.transact(Stub.TRANSACTION_setPermissionCache, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int checkPermission(java.lang.String permissionName, int uid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(permissionName);
_data.writeInt(uid);
mRemote.transact(Stub.TRANSACTION_checkPermission, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void checkPermissionAsync(java.lang.String permissionName, int uid, com.mediatek.common.mom.IRequestedPermissionCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(permissionName);
_data.writeInt(uid);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_checkPermissionAsync, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int checkPermissionWithData(java.lang.String permissionName, int uid, android.os.Bundle data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(permissionName);
_data.writeInt(uid);
if ((data!=null)) {
_data.writeInt(1);
data.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_checkPermissionWithData, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void checkPermissionAsyncWithData(java.lang.String permissionName, int uid, android.os.Bundle data, com.mediatek.common.mom.IRequestedPermissionCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(permissionName);
_data.writeInt(uid);
if ((data!=null)) {
_data.writeInt(1);
data.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_checkPermissionAsyncWithData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getParentPermission(java.lang.String subPermissionName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(subPermissionName);
mRemote.transact(Stub.TRANSACTION_getParentPermission, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public long getUserConfirmTime(int userId, long timeBound) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(userId);
_data.writeLong(timeBound);
mRemote.transact(Stub.TRANSACTION_getUserConfirmTime, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * [Receiver Controller Functions]
     */
@Override public java.util.List<com.mediatek.common.mom.ReceiverRecord> getBootReceiverList() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mediatek.common.mom.ReceiverRecord> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBootReceiverList, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mediatek.common.mom.ReceiverRecord.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setBootReceiverEnabledSettings(java.util.List<com.mediatek.common.mom.ReceiverRecord> list) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(list);
mRemote.transact(Stub.TRANSACTION_setBootReceiverEnabledSettings, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setBootReceiverEnabledSetting(java.lang.String packageName, boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setBootReceiverEnabledSetting, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getBootReceiverEnabledSetting(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_getBootReceiverEnabledSetting, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void filterReceiver(android.content.Intent intent, java.util.List<android.content.pm.ResolveInfo> resolveList, int userId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((intent!=null)) {
_data.writeInt(1);
intent.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeTypedList(resolveList);
_data.writeInt(userId);
mRemote.transact(Stub.TRANSACTION_filterReceiver, _data, _reply, 0);
_reply.readException();
_reply.readTypedList(resolveList, android.content.pm.ResolveInfo.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void startMonitorBootReceiver(java.lang.String cause) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(cause);
mRemote.transact(Stub.TRANSACTION_startMonitorBootReceiver, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopMonitorBootReceiver(java.lang.String cause) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(cause);
mRemote.transact(Stub.TRANSACTION_stopMonitorBootReceiver, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * [Package Controller Functions]
     *//**
     * Forcestop the specified package.
     * Protection Level: License
     *
     * @param packageName The name of the package to be forcestoped.
     */
@Override public void forceStopPackage(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_forceStopPackage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Install a package. Since this may take a little while, the result will
     * be posted back to the given callback.
     * Protection Level: License
     *
     * @param packageURI The location of the package file to install.  This can be a 'file:' or a 'content:' URI.
     * @param callback An callback to get notified when the package installation is complete.
     */
@Override public void installPackage(android.net.Uri packageURI, com.mediatek.common.mom.IPackageInstallCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((packageURI!=null)) {
_data.writeInt(1);
packageURI.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_installPackage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Attempts to delete a package.  Since this may take a little while, the result will
     * be posted back to the given callback.
     * Protection Level: License
     *
     * @param packageName The name of the package to delete
     */
@Override public void deletePackage(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_deletePackage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * [Notification Controller Functions]
     */
@Override public void cancelNotification(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_cancelNotification, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setNotificationEnabledSetting(java.lang.String packageName, boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setNotificationEnabledSetting, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getNotificationEnabledSetting(java.lang.String packageName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packageName);
mRemote.transact(Stub.TRANSACTION_getNotificationEnabledSetting, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setNotificationCache(java.util.List<com.mediatek.common.mom.NotificationCacheRecord> cache) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeTypedList(cache);
mRemote.transact(Stub.TRANSACTION_setNotificationCache, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * [Interception Controller Functions]
     */
@Override public void enableInterceptionController(boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_enableInterceptionController, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean getInterceptionEnabledSetting() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getInterceptionEnabledSetting, _data, _reply, 0);
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
     * Sets the firewall rule for application over mobile or Wi-Fi data connection.
     *
     * @param appUid The user id of application
     * @param networkType Specify over mobile or Wi-Fi data connection
     * @param enable Enable or disable firewall rule to restrict application data usage
     */
@Override public void setFirewallPolicy(int appUid, int networkType, boolean enable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(appUid);
_data.writeInt(networkType);
_data.writeInt(((enable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setFirewallPolicy, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_systemReady = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getVersionName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_attach = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_detach = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_clearAllSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_clearPackageSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_registerManagerApListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_triggerManagerApListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_triggerManagerApListenerAsync = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_enablePermissionController = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getInstalledPackages = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getPackageGrantedPermissions = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_setPermissionRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setPermissionRecords = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_setPermissionCache = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_checkPermission = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_checkPermissionAsync = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_checkPermissionWithData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_checkPermissionAsyncWithData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_getParentPermission = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_getUserConfirmTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_getBootReceiverList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_setBootReceiverEnabledSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setBootReceiverEnabledSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_getBootReceiverEnabledSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_filterReceiver = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_startMonitorBootReceiver = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_stopMonitorBootReceiver = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_forceStopPackage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_installPackage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_deletePackage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_cancelNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_setNotificationEnabledSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getNotificationEnabledSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_setNotificationCache = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_enableInterceptionController = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_getInterceptionEnabledSetting = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_setFirewallPolicy = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
}
public void systemReady() throws android.os.RemoteException;
/**
     * Get the version of MobileManagerService
     *
     * @return Returns version name of MobileManagerService.
     *
     */
public java.lang.String getVersionName() throws android.os.RemoteException;
/**
     * Attach to MobileManagerService
     *
     * @param callback The callback will be triggered when the connection terminated or resumed.
     * @return Returns the result of attachment.
     */
public boolean attach(com.mediatek.common.mom.IMobileConnectionCallback callback) throws android.os.RemoteException;
/**
     * Detach from MobileManagerService
     *
     */
public void detach() throws android.os.RemoteException;
/**
     * Clear all settings
     *
     */
public void clearAllSettings() throws android.os.RemoteException;
/**
     * Clear setting for package
     *
     * @param packageName The specific package that setting will be erased.
     */
public void clearPackageSettings(java.lang.String packageName) throws android.os.RemoteException;
public void registerManagerApListener(int controllerID, android.os.IBinder listener) throws android.os.RemoteException;
public int triggerManagerApListener(int ControllerID, android.os.Bundle params, int defaultResult) throws android.os.RemoteException;
public void triggerManagerApListenerAsync(int ControllerID, android.os.Bundle params, int defaultResult, android.os.IBinder callback) throws android.os.RemoteException;
/**
     * [Permission Controller Functions]
     */
public void enablePermissionController(boolean enable) throws android.os.RemoteException;
public java.util.List<android.content.pm.PackageInfo> getInstalledPackages() throws android.os.RemoteException;
public java.util.List<com.mediatek.common.mom.Permission> getPackageGrantedPermissions(java.lang.String packageName) throws android.os.RemoteException;
public void setPermissionRecord(com.mediatek.common.mom.PermissionRecord record) throws android.os.RemoteException;
public void setPermissionRecords(java.util.List<com.mediatek.common.mom.PermissionRecord> records) throws android.os.RemoteException;
public void setPermissionCache(java.util.List<com.mediatek.common.mom.PermissionRecord> cache) throws android.os.RemoteException;
public int checkPermission(java.lang.String permissionName, int uid) throws android.os.RemoteException;
public void checkPermissionAsync(java.lang.String permissionName, int uid, com.mediatek.common.mom.IRequestedPermissionCallback callback) throws android.os.RemoteException;
public int checkPermissionWithData(java.lang.String permissionName, int uid, android.os.Bundle data) throws android.os.RemoteException;
public void checkPermissionAsyncWithData(java.lang.String permissionName, int uid, android.os.Bundle data, com.mediatek.common.mom.IRequestedPermissionCallback callback) throws android.os.RemoteException;
public java.lang.String getParentPermission(java.lang.String subPermissionName) throws android.os.RemoteException;
public long getUserConfirmTime(int userId, long timeBound) throws android.os.RemoteException;
/**
     * [Receiver Controller Functions]
     */
public java.util.List<com.mediatek.common.mom.ReceiverRecord> getBootReceiverList() throws android.os.RemoteException;
public void setBootReceiverEnabledSettings(java.util.List<com.mediatek.common.mom.ReceiverRecord> list) throws android.os.RemoteException;
public void setBootReceiverEnabledSetting(java.lang.String packageName, boolean enable) throws android.os.RemoteException;
public boolean getBootReceiverEnabledSetting(java.lang.String packageName) throws android.os.RemoteException;
public void filterReceiver(android.content.Intent intent, java.util.List<android.content.pm.ResolveInfo> resolveList, int userId) throws android.os.RemoteException;
public void startMonitorBootReceiver(java.lang.String cause) throws android.os.RemoteException;
public void stopMonitorBootReceiver(java.lang.String cause) throws android.os.RemoteException;
/**
     * [Package Controller Functions]
     *//**
     * Forcestop the specified package.
     * Protection Level: License
     *
     * @param packageName The name of the package to be forcestoped.
     */
public void forceStopPackage(java.lang.String packageName) throws android.os.RemoteException;
/**
     * Install a package. Since this may take a little while, the result will
     * be posted back to the given callback.
     * Protection Level: License
     *
     * @param packageURI The location of the package file to install.  This can be a 'file:' or a 'content:' URI.
     * @param callback An callback to get notified when the package installation is complete.
     */
public void installPackage(android.net.Uri packageURI, com.mediatek.common.mom.IPackageInstallCallback callback) throws android.os.RemoteException;
/**
     * Attempts to delete a package.  Since this may take a little while, the result will
     * be posted back to the given callback.
     * Protection Level: License
     *
     * @param packageName The name of the package to delete
     */
public void deletePackage(java.lang.String packageName) throws android.os.RemoteException;
/**
     * [Notification Controller Functions]
     */
public void cancelNotification(java.lang.String packageName) throws android.os.RemoteException;
public void setNotificationEnabledSetting(java.lang.String packageName, boolean enable) throws android.os.RemoteException;
public boolean getNotificationEnabledSetting(java.lang.String packageName) throws android.os.RemoteException;
public void setNotificationCache(java.util.List<com.mediatek.common.mom.NotificationCacheRecord> cache) throws android.os.RemoteException;
/**
     * [Interception Controller Functions]
     */
public void enableInterceptionController(boolean enable) throws android.os.RemoteException;
public boolean getInterceptionEnabledSetting() throws android.os.RemoteException;
/**
     * Sets the firewall rule for application over mobile or Wi-Fi data connection.
     *
     * @param appUid The user id of application
     * @param networkType Specify over mobile or Wi-Fi data connection
     * @param enable Enable or disable firewall rule to restrict application data usage
     */
public void setFirewallPolicy(int appUid, int networkType, boolean enable) throws android.os.RemoteException;
}
