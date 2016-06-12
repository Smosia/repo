/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/dm/DmAgent.aidl
 */
package com.mediatek.common.dm;
/**
 * Agent to set DM flag and status
 * @hide
 */
public interface DmAgent extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.dm.DmAgent
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.dm.DmAgent";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.dm.DmAgent interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.dm.DmAgent asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.dm.DmAgent))) {
return ((com.mediatek.common.dm.DmAgent)iin);
}
return new com.mediatek.common.dm.DmAgent.Stub.Proxy(obj);
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
case TRANSACTION_readDmTree:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readDmTree();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_writeDmTree:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.writeDmTree(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isLockFlagSet:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isLockFlagSet();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setLockFlag:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.setLockFlag(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_clearLockFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.clearLockFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readImsi:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readImsi();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_writeImsi:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.writeImsi(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readOperatorName:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readOperatorName();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_getRegisterSwitch:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getRegisterSwitch();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_setRegisterSwitch:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.setRegisterSwitch(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setRebootFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.setRebootFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getLockType:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getLockType();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getOperatorId:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getOperatorId();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getOperatorName:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getOperatorName();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_isHangMoCallLocking:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isHangMoCallLocking();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isHangMtCallLocking:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isHangMtCallLocking();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_clearRebootFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.clearRebootFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isBootRecoveryFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isBootRecoveryFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getUpgradeStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getUpgradeStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_restartAndroid:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.restartAndroid();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isWipeSet:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isWipeSet();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setWipeFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.setWipeFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_clearWipeFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.clearWipeFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readOtaResult:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.readOtaResult();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_clearOtaResult:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.clearOtaResult();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getSwitchValue:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getSwitchValue();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_setSwitchValue:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.setSwitchValue(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getDmSwitchValue:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getDmSwitchValue();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_setDmSwitchValue:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.setDmSwitchValue(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSmsRegSwitchValue:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getSmsRegSwitchValue();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_setSmsRegSwitchValue:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
boolean _result = this.setSmsRegSwitchValue(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setRegisterFlag:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.setRegisterFlag(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readRegisterFlag:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readRegisterFlag();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_writeImsi1:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.writeImsi1(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_writeImsi2:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.writeImsi2(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readImsi1:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readImsi1();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_readImsi2:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readImsi2();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_getMacAddr:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.getMacAddr();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_writeIccID1:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.writeIccID1(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_writeIccID2:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.writeIccID2(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readIccID1:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readIccID1();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_readIccID2:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readIccID2();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
case TRANSACTION_setSelfRegisterFlag:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _arg1;
_arg1 = data.readInt();
boolean _result = this.setSelfRegisterFlag(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_readSelfRegisterFlag:
{
data.enforceInterface(DESCRIPTOR);
byte[] _result = this.readSelfRegisterFlag();
reply.writeNoException();
reply.writeByteArray(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.dm.DmAgent
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
     * @deprecated
     * @hide
     */
@Override public byte[] readDmTree() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readDmTree, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * @deprecated
     * @hide
     */
@Override public boolean writeDmTree(byte[] tree) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(tree);
mRemote.transact(Stub.TRANSACTION_writeDmTree, _data, _reply, 0);
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
     * @return <code>true</code>if the DM lock flag is set, <code>false</code> otherwise
     * @internal
     */
@Override public boolean isLockFlagSet() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isLockFlagSet, _data, _reply, 0);
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
     * Set DM lock flag
     * @param lockType The DM lock type to set
     * @return <code>true</code> if DM lock flag set success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean setLockFlag(byte[] lockType) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(lockType);
mRemote.transact(Stub.TRANSACTION_setLockFlag, _data, _reply, 0);
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
     * Clear DM lock flag
     * @return <code>true</code> if DM lock flag cleared success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean clearLockFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearLockFlag, _data, _reply, 0);
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
     * Read the saved IMSI
     * @return the value of the registered SIM card's IMSI
     * @internal
     */
@Override public byte[] readImsi() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readImsi, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Save the registered SIM card's IMSI
     * @param imsi The registered SIM card's IMSI
     * @return <code>true</code> if IMSI written success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean writeImsi(byte[] imsi) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(imsi);
mRemote.transact(Stub.TRANSACTION_writeImsi, _data, _reply, 0);
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
     * @deprecated
     * @hide
     */
@Override public byte[] readOperatorName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readOperatorName, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Read Device Register switch value
     * @return current Device Register switch status,<code>'1'</code>Device Register enabled, <code>'0'</code> otherwise
     * @internal
     */
@Override public byte[] getRegisterSwitch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getRegisterSwitch, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set Device Register switch
     * @param registerSwitch Device Register switch, only can set <code>'0'</code> or <code>'1'</code>
     * @return <code>true</code> if Device Register switch set success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean setRegisterSwitch(byte[] registerSwitch) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(registerSwitch);
mRemote.transact(Stub.TRANSACTION_setRegisterSwitch, _data, _reply, 0);
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
     * Set reboot flag
     * @return <code>true</code> if reboot flag set success, <code>false</code> otherwise.
     * @internal
     */
@Override public boolean setRebootFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setRebootFlag, _data, _reply, 0);
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
     * Get DM lock Type
     * @return <code>-1</code> DM unlock, <code>0</code> DM partially lock, <code>1</code> DM fully lock.
     * @internal
     */
@Override public int getLockType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getLockType, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * @deprecated
     * @hide
     */
@Override public int getOperatorId() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOperatorId, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * @deprecated
     * @hide
     */
@Override public byte[] getOperatorName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getOperatorName, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * @return <code>true</code> if MO Call is NOT allowed, <code>false</code> otherwise.
     * @hide
     * @internal
     */
@Override public boolean isHangMoCallLocking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isHangMoCallLocking, _data, _reply, 0);
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
     * @return <code>true</code> if MT Call is NOT allowed, <code>false</code> otherwise.
     * @hide
     * @internal
     */
@Override public boolean isHangMtCallLocking() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isHangMtCallLocking, _data, _reply, 0);
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
     * Clear reboot flag
     * @return <code>true</code> if reboot flag cleared success, <code>false</code> otherwise.
     * @internal
     */
@Override public boolean clearRebootFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearRebootFlag, _data, _reply, 0);
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
     * @deprecated
     * @hide
     */
@Override public boolean isBootRecoveryFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isBootRecoveryFlag, _data, _reply, 0);
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
     * @deprecated
     * @hide
     */
@Override public int getUpgradeStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getUpgradeStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Restart Android
     * @return <code>true</code> if restart success, <code>false</code> otherwise.
     * @internal
     */
@Override public int restartAndroid() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_restartAndroid, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * @return <code>true</code> if DM Wipe flag is set, <code>false</code> otherwise.
     * @hide
     * @internal
     */
@Override public boolean isWipeSet() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isWipeSet, _data, _reply, 0);
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
     * Set DM Wipe flag
     * @return <code>true</code> if DM Wipe flag set success, <code>false</code> otherwise.
     * @hide
     * @internal
     */
@Override public boolean setWipeFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setWipeFlag, _data, _reply, 0);
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
     * Clear DM Wipe flag
     * @return <code>true</code> if DM Wipe flag cleared success, <code>false</code> otherwise.
     * @hide
     * @internal
     */
@Override public boolean clearWipeFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearWipeFlag, _data, _reply, 0);
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
     * Get OTA upgrade result.
     * @return <code>1</code> if OTA upgrade success, <code>0</code> otherwise.
     * @internal
     */
@Override public int readOtaResult() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readOtaResult, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Clear OTA upgrade result
     * @return <code>1</code> if clear OTA result success, <code>0</code> otherwise.
     * @internal
     */
@Override public int clearOtaResult() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearOtaResult, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get MediatekDM Productive/Test switch value.
     * @return <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     */
@Override public byte[] getSwitchValue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSwitchValue, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set MediatekDM Productive/Test switch value.
     * @param <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     * @return <code>true</code> if success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean setSwitchValue(byte[] registerSwitch) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(registerSwitch);
mRemote.transact(Stub.TRANSACTION_setSwitchValue, _data, _reply, 0);
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
     * Get the pending flag of Productive/Test switch for DM. DM should clear this flag after the
     * necessary action has been performed.
     * @return <code>'1'</code> if there is pending action for DM, <code>'0'</code> otherwise.
     */
@Override public byte[] getDmSwitchValue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDmSwitchValue, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set the pending flag of Productive/Test switch for DM.
     * @param <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     * @return <code>true</code> if success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean setDmSwitchValue(byte[] registerSwitch) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(registerSwitch);
mRemote.transact(Stub.TRANSACTION_setDmSwitchValue, _data, _reply, 0);
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
     * Get the pending flag of Productive/Test switch for SmsReg. SmsReg should clear this flag after
     * the necessary action has been performed.
     * @return <code>'1'</code> if there is pending action for SmsReg, <code>'0'</code> otherwise.
     */
@Override public byte[] getSmsRegSwitchValue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSmsRegSwitchValue, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set the pending flag of Productive/Test switch for SmsReg.
     * @param <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     * @return <code>true</code> if success, <code>false</code> otherwise
     * @internal
     */
@Override public boolean setSmsRegSwitchValue(byte[] registerSwitch) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(registerSwitch);
mRemote.transact(Stub.TRANSACTION_setSmsRegSwitchValue, _data, _reply, 0);
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
     * Set registered value for Smsreg.
     * @return write size of the imsi value
     * @internal
     */
@Override public boolean setRegisterFlag(byte[] flag, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(flag);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_setRegisterFlag, _data, _reply, 0);
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
     * Read registered value for Smsreg.
     * @return write size of the imsi value
     * @internal
     */
@Override public byte[] readRegisterFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readRegisterFlag, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Save register imsi value for simcard1.
     * @return write size of the imsi value
     * @internal
     */
@Override public boolean writeImsi1(byte[] imsi, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(imsi);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_writeImsi1, _data, _reply, 0);
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
     * Save register imsi value for simcard2.
     * @return write size of the imsi value
     * @internal
     */
@Override public boolean writeImsi2(byte[] imsi, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(imsi);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_writeImsi2, _data, _reply, 0);
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
     * Read register imsi value for simcard1.
     * @return write size of the imsi value
     * @internal
     */
@Override public byte[] readImsi1() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readImsi1, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Read register imsi value for simcard2.
     * @return write size of the imsi value
     * @internal
     */
@Override public byte[] readImsi2() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readImsi2, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Get MAC address.
     * @return six bytes of MAC address value.
     * @internal
     */
@Override public byte[] getMacAddr() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMacAddr, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Save register iccID value for simcard1.
     * @return write size of the iccID value
     * @internal
     */
@Override public boolean writeIccID1(byte[] iccID, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(iccID);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_writeIccID1, _data, _reply, 0);
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
     * Save register iccID value for simcard2.
     * @return write size of the iccID value
     * @internal
     */
@Override public boolean writeIccID2(byte[] iccID, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(iccID);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_writeIccID2, _data, _reply, 0);
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
     * Read register iccID value for simcard1.
     * @return write size of the iccID value
     * @internal
     */
@Override public byte[] readIccID1() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readIccID1, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Read register iccID value for simcard2.
     * @return write size of the iccID value
     * @internal
     */
@Override public byte[] readIccID2() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readIccID2, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Set registered value for SelfRegister.
     * @return true if set flag success.
     * @internal
     */
@Override public boolean setSelfRegisterFlag(byte[] flag, int size) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(flag);
_data.writeInt(size);
mRemote.transact(Stub.TRANSACTION_setSelfRegisterFlag, _data, _reply, 0);
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
     * Read registered value for SelfRegister.
     * @return the register flag value
     * @internal
     */
@Override public byte[] readSelfRegisterFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_readSelfRegisterFlag, _data, _reply, 0);
_reply.readException();
_result = _reply.createByteArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_readDmTree = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_writeDmTree = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_isLockFlagSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setLockFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_clearLockFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_readImsi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_writeImsi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_readOperatorName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getRegisterSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setRegisterSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_setRebootFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_getLockType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getOperatorId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_getOperatorName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_isHangMoCallLocking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_isHangMtCallLocking = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_clearRebootFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_isBootRecoveryFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_getUpgradeStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_restartAndroid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_isWipeSet = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setWipeFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_clearWipeFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_readOtaResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_clearOtaResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_getSwitchValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_setSwitchValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_getDmSwitchValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_setDmSwitchValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_getSmsRegSwitchValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_setSmsRegSwitchValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_setRegisterFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_readRegisterFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_writeImsi1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_writeImsi2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_readImsi1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_readImsi2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_getMacAddr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_writeIccID1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_writeIccID2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_readIccID1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_readIccID2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_setSelfRegisterFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_readSelfRegisterFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
}
/**
     * @deprecated
     * @hide
     */
public byte[] readDmTree() throws android.os.RemoteException;
/**
     * @deprecated
     * @hide
     */
public boolean writeDmTree(byte[] tree) throws android.os.RemoteException;
/**
     * @return <code>true</code>if the DM lock flag is set, <code>false</code> otherwise
     * @internal
     */
public boolean isLockFlagSet() throws android.os.RemoteException;
/**
     * Set DM lock flag
     * @param lockType The DM lock type to set
     * @return <code>true</code> if DM lock flag set success, <code>false</code> otherwise
     * @internal
     */
public boolean setLockFlag(byte[] lockType) throws android.os.RemoteException;
/**
     * Clear DM lock flag
     * @return <code>true</code> if DM lock flag cleared success, <code>false</code> otherwise
     * @internal
     */
public boolean clearLockFlag() throws android.os.RemoteException;
/**
     * Read the saved IMSI
     * @return the value of the registered SIM card's IMSI
     * @internal
     */
public byte[] readImsi() throws android.os.RemoteException;
/**
     * Save the registered SIM card's IMSI
     * @param imsi The registered SIM card's IMSI
     * @return <code>true</code> if IMSI written success, <code>false</code> otherwise
     * @internal
     */
public boolean writeImsi(byte[] imsi) throws android.os.RemoteException;
/**
     * @deprecated
     * @hide
     */
public byte[] readOperatorName() throws android.os.RemoteException;
/**
     * Read Device Register switch value
     * @return current Device Register switch status,<code>'1'</code>Device Register enabled, <code>'0'</code> otherwise
     * @internal
     */
public byte[] getRegisterSwitch() throws android.os.RemoteException;
/**
     * Set Device Register switch
     * @param registerSwitch Device Register switch, only can set <code>'0'</code> or <code>'1'</code>
     * @return <code>true</code> if Device Register switch set success, <code>false</code> otherwise
     * @internal
     */
public boolean setRegisterSwitch(byte[] registerSwitch) throws android.os.RemoteException;
/**
     * Set reboot flag
     * @return <code>true</code> if reboot flag set success, <code>false</code> otherwise.
     * @internal
     */
public boolean setRebootFlag() throws android.os.RemoteException;
/**
     * Get DM lock Type
     * @return <code>-1</code> DM unlock, <code>0</code> DM partially lock, <code>1</code> DM fully lock.
     * @internal
     */
public int getLockType() throws android.os.RemoteException;
/**
     * @deprecated
     * @hide
     */
public int getOperatorId() throws android.os.RemoteException;
/**
     * @deprecated
     * @hide
     */
public byte[] getOperatorName() throws android.os.RemoteException;
/**
     * @return <code>true</code> if MO Call is NOT allowed, <code>false</code> otherwise.
     * @hide
     * @internal
     */
public boolean isHangMoCallLocking() throws android.os.RemoteException;
/**
     * @return <code>true</code> if MT Call is NOT allowed, <code>false</code> otherwise.
     * @hide
     * @internal
     */
public boolean isHangMtCallLocking() throws android.os.RemoteException;
/**
     * Clear reboot flag
     * @return <code>true</code> if reboot flag cleared success, <code>false</code> otherwise.
     * @internal
     */
public boolean clearRebootFlag() throws android.os.RemoteException;
/**
     * @deprecated
     * @hide
     */
public boolean isBootRecoveryFlag() throws android.os.RemoteException;
/**
     * @deprecated
     * @hide
     */
public int getUpgradeStatus() throws android.os.RemoteException;
/**
     * Restart Android
     * @return <code>true</code> if restart success, <code>false</code> otherwise.
     * @internal
     */
public int restartAndroid() throws android.os.RemoteException;
/**
     * @return <code>true</code> if DM Wipe flag is set, <code>false</code> otherwise.
     * @hide
     * @internal
     */
public boolean isWipeSet() throws android.os.RemoteException;
/**
     * Set DM Wipe flag
     * @return <code>true</code> if DM Wipe flag set success, <code>false</code> otherwise.
     * @hide
     * @internal
     */
public boolean setWipeFlag() throws android.os.RemoteException;
/**
     * Clear DM Wipe flag
     * @return <code>true</code> if DM Wipe flag cleared success, <code>false</code> otherwise.
     * @hide
     * @internal
     */
public boolean clearWipeFlag() throws android.os.RemoteException;
/**
     * Get OTA upgrade result.
     * @return <code>1</code> if OTA upgrade success, <code>0</code> otherwise.
     * @internal
     */
public int readOtaResult() throws android.os.RemoteException;
/**
     * Clear OTA upgrade result
     * @return <code>1</code> if clear OTA result success, <code>0</code> otherwise.
     * @internal
     */
public int clearOtaResult() throws android.os.RemoteException;
/**
     * Get MediatekDM Productive/Test switch value.
     * @return <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     */
public byte[] getSwitchValue() throws android.os.RemoteException;
/**
     * Set MediatekDM Productive/Test switch value.
     * @param <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     * @return <code>true</code> if success, <code>false</code> otherwise
     * @internal
     */
public boolean setSwitchValue(byte[] registerSwitch) throws android.os.RemoteException;
/**
     * Get the pending flag of Productive/Test switch for DM. DM should clear this flag after the
     * necessary action has been performed.
     * @return <code>'1'</code> if there is pending action for DM, <code>'0'</code> otherwise.
     */
public byte[] getDmSwitchValue() throws android.os.RemoteException;
/**
     * Set the pending flag of Productive/Test switch for DM.
     * @param <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     * @return <code>true</code> if success, <code>false</code> otherwise
     * @internal
     */
public boolean setDmSwitchValue(byte[] registerSwitch) throws android.os.RemoteException;
/**
     * Get the pending flag of Productive/Test switch for SmsReg. SmsReg should clear this flag after
     * the necessary action has been performed.
     * @return <code>'1'</code> if there is pending action for SmsReg, <code>'0'</code> otherwise.
     */
public byte[] getSmsRegSwitchValue() throws android.os.RemoteException;
/**
     * Set the pending flag of Productive/Test switch for SmsReg.
     * @param <code>'1'</code> for Productive Environment, <code>'0'</code> for Test Environment.
     * @return <code>true</code> if success, <code>false</code> otherwise
     * @internal
     */
public boolean setSmsRegSwitchValue(byte[] registerSwitch) throws android.os.RemoteException;
/**
     * Set registered value for Smsreg.
     * @return write size of the imsi value
     * @internal
     */
public boolean setRegisterFlag(byte[] flag, int size) throws android.os.RemoteException;
/**
     * Read registered value for Smsreg.
     * @return write size of the imsi value
     * @internal
     */
public byte[] readRegisterFlag() throws android.os.RemoteException;
/**
     * Save register imsi value for simcard1.
     * @return write size of the imsi value
     * @internal
     */
public boolean writeImsi1(byte[] imsi, int size) throws android.os.RemoteException;
/**
     * Save register imsi value for simcard2.
     * @return write size of the imsi value
     * @internal
     */
public boolean writeImsi2(byte[] imsi, int size) throws android.os.RemoteException;
/**
     * Read register imsi value for simcard1.
     * @return write size of the imsi value
     * @internal
     */
public byte[] readImsi1() throws android.os.RemoteException;
/**
     * Read register imsi value for simcard2.
     * @return write size of the imsi value
     * @internal
     */
public byte[] readImsi2() throws android.os.RemoteException;
/**
     * Get MAC address.
     * @return six bytes of MAC address value.
     * @internal
     */
public byte[] getMacAddr() throws android.os.RemoteException;
/**
     * Save register iccID value for simcard1.
     * @return write size of the iccID value
     * @internal
     */
public boolean writeIccID1(byte[] iccID, int size) throws android.os.RemoteException;
/**
     * Save register iccID value for simcard2.
     * @return write size of the iccID value
     * @internal
     */
public boolean writeIccID2(byte[] iccID, int size) throws android.os.RemoteException;
/**
     * Read register iccID value for simcard1.
     * @return write size of the iccID value
     * @internal
     */
public byte[] readIccID1() throws android.os.RemoteException;
/**
     * Read register iccID value for simcard2.
     * @return write size of the iccID value
     * @internal
     */
public byte[] readIccID2() throws android.os.RemoteException;
/**
     * Set registered value for SelfRegister.
     * @return true if set flag success.
     * @internal
     */
public boolean setSelfRegisterFlag(byte[] flag, int size) throws android.os.RemoteException;
/**
     * Read registered value for SelfRegister.
     * @return the register flag value
     * @internal
     */
public byte[] readSelfRegisterFlag() throws android.os.RemoteException;
}
