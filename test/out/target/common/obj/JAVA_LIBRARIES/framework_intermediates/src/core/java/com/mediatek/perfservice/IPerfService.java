/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: frameworks/base/core/java/com/mediatek/perfservice/IPerfService.aidl
 */
package com.mediatek.perfservice;
/** @hide */
public interface IPerfService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.perfservice.IPerfService
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.perfservice.IPerfService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.perfservice.IPerfService interface,
 * generating a proxy if needed.
 */
public static com.mediatek.perfservice.IPerfService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.perfservice.IPerfService))) {
return ((com.mediatek.perfservice.IPerfService)iin);
}
return new com.mediatek.perfservice.IPerfService.Stub.Proxy(obj);
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
case TRANSACTION_boostEnable:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.boostEnable(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_boostDisable:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.boostDisable(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_boostEnableTimeout:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.boostEnableTimeout(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_boostEnableTimeoutMs:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.boostEnableTimeoutMs(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_notifyAppState:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
this.notifyAppState(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_userReg:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _result = this.userReg(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_userRegBigLittle:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
int _arg5;
_arg5 = data.readInt();
int _result = this.userRegBigLittle(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_userUnreg:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.userUnreg(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_userGetCapability:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.userGetCapability(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_userRegScn:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _result = this.userRegScn(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_userRegScnConfig:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
int _arg5;
_arg5 = data.readInt();
this.userRegScnConfig(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
reply.writeNoException();
return true;
}
case TRANSACTION_userUnregScn:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.userUnregScn(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_userEnable:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.userEnable(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_userEnableTimeout:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.userEnableTimeout(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_userEnableTimeoutMs:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.userEnableTimeoutMs(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_userDisable:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.userDisable(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_userResetAll:
{
data.enforceInterface(DESCRIPTOR);
this.userResetAll();
reply.writeNoException();
return true;
}
case TRANSACTION_userDisableAll:
{
data.enforceInterface(DESCRIPTOR);
this.userDisableAll();
reply.writeNoException();
return true;
}
case TRANSACTION_userRestoreAll:
{
data.enforceInterface(DESCRIPTOR);
this.userRestoreAll();
reply.writeNoException();
return true;
}
case TRANSACTION_dumpAll:
{
data.enforceInterface(DESCRIPTOR);
this.dumpAll();
reply.writeNoException();
return true;
}
case TRANSACTION_setFavorPid:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setFavorPid(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_restorePolicy:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.restorePolicy(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getPackName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getPackName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getLastBoostPid:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getLastBoostPid();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_notifyFrameUpdate:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.notifyFrameUpdate(_arg0);
return true;
}
case TRANSACTION_notifyDisplayType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.notifyDisplayType(_arg0);
return true;
}
case TRANSACTION_notifyUserStatus:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.notifyUserStatus(_arg0, _arg1);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.perfservice.IPerfService
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
@Override public void boostEnable(int scenario) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scenario);
mRemote.transact(Stub.TRANSACTION_boostEnable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void boostDisable(int scenario) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scenario);
mRemote.transact(Stub.TRANSACTION_boostDisable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void boostEnableTimeout(int scenario, int timeout) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scenario);
_data.writeInt(timeout);
mRemote.transact(Stub.TRANSACTION_boostEnableTimeout, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void boostEnableTimeoutMs(int scenario, int timeout_ms) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scenario);
_data.writeInt(timeout_ms);
mRemote.transact(Stub.TRANSACTION_boostEnableTimeoutMs, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void notifyAppState(java.lang.String packName, java.lang.String className, int state, int pid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(packName);
_data.writeString(className);
_data.writeInt(state);
_data.writeInt(pid);
mRemote.transact(Stub.TRANSACTION_notifyAppState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int userReg(int scn_core, int scn_freq, int pid, int tid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scn_core);
_data.writeInt(scn_freq);
_data.writeInt(pid);
_data.writeInt(tid);
mRemote.transact(Stub.TRANSACTION_userReg, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int userRegBigLittle(int scn_core_big, int scn_freq_big, int scn_core_little, int scn_freq_little, int pid, int tid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(scn_core_big);
_data.writeInt(scn_freq_big);
_data.writeInt(scn_core_little);
_data.writeInt(scn_freq_little);
_data.writeInt(pid);
_data.writeInt(tid);
mRemote.transact(Stub.TRANSACTION_userRegBigLittle, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void userUnreg(int handle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
mRemote.transact(Stub.TRANSACTION_userUnreg, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int userGetCapability(int cmd) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(cmd);
mRemote.transact(Stub.TRANSACTION_userGetCapability, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int userRegScn(int pid, int tid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pid);
_data.writeInt(tid);
mRemote.transact(Stub.TRANSACTION_userRegScn, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void userRegScnConfig(int handle, int cmd, int param_1, int param_2, int param_3, int param_4) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
_data.writeInt(cmd);
_data.writeInt(param_1);
_data.writeInt(param_2);
_data.writeInt(param_3);
_data.writeInt(param_4);
mRemote.transact(Stub.TRANSACTION_userRegScnConfig, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userUnregScn(int handle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
mRemote.transact(Stub.TRANSACTION_userUnregScn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userEnable(int handle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
mRemote.transact(Stub.TRANSACTION_userEnable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userEnableTimeout(int handle, int timeout) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
_data.writeInt(timeout);
mRemote.transact(Stub.TRANSACTION_userEnableTimeout, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userEnableTimeoutMs(int handle, int timeout_ms) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
_data.writeInt(timeout_ms);
mRemote.transact(Stub.TRANSACTION_userEnableTimeoutMs, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userDisable(int handle) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(handle);
mRemote.transact(Stub.TRANSACTION_userDisable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userResetAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_userResetAll, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userDisableAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_userDisableAll, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void userRestoreAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_userRestoreAll, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void dumpAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_dumpAll, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setFavorPid(int pid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pid);
mRemote.transact(Stub.TRANSACTION_setFavorPid, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void restorePolicy(int pid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(pid);
mRemote.transact(Stub.TRANSACTION_restorePolicy, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getPackName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPackName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getLastBoostPid() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLastBoostPid, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void notifyFrameUpdate(int level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(level);
mRemote.transact(Stub.TRANSACTION_notifyFrameUpdate, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void notifyDisplayType(int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_notifyDisplayType, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void notifyUserStatus(int type, int status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
_data.writeInt(status);
mRemote.transact(Stub.TRANSACTION_notifyUserStatus, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_boostEnable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_boostDisable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_boostEnableTimeout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_boostEnableTimeoutMs = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_notifyAppState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_userReg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_userRegBigLittle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_userUnreg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_userGetCapability = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_userRegScn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_userRegScnConfig = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_userUnregScn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_userEnable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_userEnableTimeout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_userEnableTimeoutMs = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_userDisable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_userResetAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_userDisableAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_userRestoreAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_dumpAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_setFavorPid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_restorePolicy = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getPackName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_getLastBoostPid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_notifyFrameUpdate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_notifyDisplayType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_notifyUserStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
}
public void boostEnable(int scenario) throws android.os.RemoteException;
public void boostDisable(int scenario) throws android.os.RemoteException;
public void boostEnableTimeout(int scenario, int timeout) throws android.os.RemoteException;
public void boostEnableTimeoutMs(int scenario, int timeout_ms) throws android.os.RemoteException;
public void notifyAppState(java.lang.String packName, java.lang.String className, int state, int pid) throws android.os.RemoteException;
public int userReg(int scn_core, int scn_freq, int pid, int tid) throws android.os.RemoteException;
public int userRegBigLittle(int scn_core_big, int scn_freq_big, int scn_core_little, int scn_freq_little, int pid, int tid) throws android.os.RemoteException;
public void userUnreg(int handle) throws android.os.RemoteException;
public int userGetCapability(int cmd) throws android.os.RemoteException;
public int userRegScn(int pid, int tid) throws android.os.RemoteException;
public void userRegScnConfig(int handle, int cmd, int param_1, int param_2, int param_3, int param_4) throws android.os.RemoteException;
public void userUnregScn(int handle) throws android.os.RemoteException;
public void userEnable(int handle) throws android.os.RemoteException;
public void userEnableTimeout(int handle, int timeout) throws android.os.RemoteException;
public void userEnableTimeoutMs(int handle, int timeout_ms) throws android.os.RemoteException;
public void userDisable(int handle) throws android.os.RemoteException;
public void userResetAll() throws android.os.RemoteException;
public void userDisableAll() throws android.os.RemoteException;
public void userRestoreAll() throws android.os.RemoteException;
public void dumpAll() throws android.os.RemoteException;
public void setFavorPid(int pid) throws android.os.RemoteException;
public void restorePolicy(int pid) throws android.os.RemoteException;
public java.lang.String getPackName() throws android.os.RemoteException;
public int getLastBoostPid() throws android.os.RemoteException;
public void notifyFrameUpdate(int level) throws android.os.RemoteException;
public void notifyDisplayType(int type) throws android.os.RemoteException;
public void notifyUserStatus(int type, int status) throws android.os.RemoteException;
}
