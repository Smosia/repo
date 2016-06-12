/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: vendor/mediatek/proprietary/frameworks/common/src/com/mediatek/common/ppl/IPplManager.aidl
 */
package com.mediatek.common.ppl;
public interface IPplManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mediatek.common.ppl.IPplManager
{
private static final java.lang.String DESCRIPTOR = "com.mediatek.common.ppl.IPplManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mediatek.common.ppl.IPplManager interface,
 * generating a proxy if needed.
 */
public static com.mediatek.common.ppl.IPplManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mediatek.common.ppl.IPplManager))) {
return ((com.mediatek.common.ppl.IPplManager)iin);
}
return new com.mediatek.common.ppl.IPplManager.Stub.Proxy(obj);
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
case TRANSACTION_resetPassword:
{
data.enforceInterface(DESCRIPTOR);
this.resetPassword();
reply.writeNoException();
return true;
}
case TRANSACTION_needLock:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.needLock();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_lock:
{
data.enforceInterface(DESCRIPTOR);
this.lock();
reply.writeNoException();
return true;
}
case TRANSACTION_unlock:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.unlock(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mediatek.common.ppl.IPplManager
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
/*
	 * Reset the password and send the new password to trusted phone number via SMS.
	 */
@Override public void resetPassword() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resetPassword, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
	 * Whether Keyguard should lock the phone according to lock flag and sim status.
	 *
	 * @return 0 - no need to lock
	 *         1 - lock flag is set, need to lock
	 *         2 - sim info does not match, need to lock 
	 */
@Override public int needLock() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_needLock, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/*
	 * Lock the phone.
	 */
@Override public void lock() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_lock, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
	 * Try to unlock the phone.
	 *
	 * @return true  - password is accepted.
	 *         false - password is incorrect.
	 */
@Override public boolean unlock(java.lang.String password) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(password);
mRemote.transact(Stub.TRANSACTION_unlock, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_resetPassword = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_needLock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_lock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_unlock = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
/*
	 * Reset the password and send the new password to trusted phone number via SMS.
	 */
public void resetPassword() throws android.os.RemoteException;
/*
	 * Whether Keyguard should lock the phone according to lock flag and sim status.
	 *
	 * @return 0 - no need to lock
	 *         1 - lock flag is set, need to lock
	 *         2 - sim info does not match, need to lock 
	 */
public int needLock() throws android.os.RemoteException;
/*
	 * Lock the phone.
	 */
public void lock() throws android.os.RemoteException;
/*
	 * Try to unlock the phone.
	 *
	 * @return true  - password is accepted.
	 *         false - password is incorrect.
	 */
public boolean unlock(java.lang.String password) throws android.os.RemoteException;
}
